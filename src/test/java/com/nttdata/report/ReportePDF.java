
package com.nttdata.report;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestRunFinished;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportePDF implements ConcurrentEventListener {

    private final List<String> resultados = new ArrayList<>();

    private int aprobados = 0;
    private int fallidos = 0;

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestCaseFinished.class,
                this::registrarResultado
        );

        publisher.registerHandlerFor(
                TestRunFinished.class,
                evento -> generarPDF()
        );
    }

    private synchronized void registrarResultado(
            TestCaseFinished evento) {

        String escenario = evento.getTestCase().getName();

        String estado = evento.getResult()
                .getStatus().toString();

        if ("PASSED".equals(estado)) {
            aprobados++;
        } else {
            fallidos++;
        }

        resultados.add(escenario + " - " + estado);
    }

    private synchronized void generarPDF() {

        new File("target/reportes").mkdirs();

        try (PDDocument documento = new PDDocument()) {

            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            PDPageContentStream contenido =
                    new PDPageContentStream(documento, pagina);

            float y = 750;

            contenido.beginText();
            contenido.setFont(
                    PDType1Font.HELVETICA_BOLD, 15
            );
            contenido.newLineAtOffset(50, y);

            contenido.showText(
                    "REPORTE DE REGRESION - PRODUCT STORE"
            );

            contenido.endText();

            y -= 50;

            for (String resultado : resultados) {

                if (y < 60) {
                    contenido.close();

                    pagina = new PDPage();
                    documento.addPage(pagina);

                    contenido = new PDPageContentStream(
                            documento, pagina
                    );

                    y = 750;
                }

                contenido.beginText();
                contenido.setFont(
                        PDType1Font.HELVETICA, 10
                );
                contenido.newLineAtOffset(50, y);

                // Evita exceder el ancho de la pagina
                String linea = resultado.length() > 85
                        ? resultado.substring(0, 85)
                        : resultado;

                contenido.showText(linea);
                contenido.endText();

                y -= 25;
            }

            contenido.beginText();
            contenido.setFont(
                    PDType1Font.HELVETICA_BOLD, 11
            );
            contenido.newLineAtOffset(50, y - 20);

            contenido.showText(
                    "PASS: " + aprobados +
                            " | NO PASS: " + fallidos
            );

            contenido.endText();
            contenido.close();

            documento.save(
                    "target/reportes/ReporteStore.pdf"
            );

            System.out.println(
                    "Reporte PDF generado correctamente"
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error al generar PDF", e
            );
        }
    }
}
