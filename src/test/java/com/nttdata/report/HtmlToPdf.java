package com.nttdata.report;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class HtmlToPdf {

    public static void generarPDF() throws Exception {

        File html = new File(
                "target/cucumber/cucumber-report.html"
        );

        if (!html.exists()) {
            throw new RuntimeException(
                    "No existe el reporte HTML: " + html.getAbsolutePath()
            );
        }

        File carpeta = new File("target/reportes");
        carpeta.mkdirs();

        File pdf = new File(carpeta, "ReporteStore.pdf");

        Files.deleteIfExists(pdf.toPath());

        String chrome =
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";

        if (!new File(chrome).exists()) {
            chrome =
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
        }

        if (!new File(chrome).exists()) {
            throw new RuntimeException(
                    "No se encontró Chrome en las rutas configuradas"
            );
        }

        ProcessBuilder proceso = new ProcessBuilder(
                chrome,
                "--headless",
                "--disable-gpu",
                "--no-first-run",
                "--user-data-dir=" +
                        new File("target/chrome-pdf-profile").getAbsolutePath(),
                "--print-to-pdf=" + pdf.getAbsolutePath(),
                html.toURI().toString()
        );

        proceso.redirectErrorStream(true);

        Process p = proceso.start();

        boolean termino = p.waitFor(60, TimeUnit.SECONDS);

        if (!termino) {
            p.destroyForcibly();
            throw new RuntimeException(
                    "Chrome excedió el tiempo de generación del PDF"
            );
        }

        if (p.exitValue() != 0 || !pdf.exists()
                || pdf.length() == 0) {
            throw new RuntimeException(
                    "No se pudo convertir el HTML a PDF. Código: "
                            + p.exitValue()
            );
        }

        System.out.println(
                "PDF generado correctamente: " + pdf.getAbsolutePath()
        );
    }
}