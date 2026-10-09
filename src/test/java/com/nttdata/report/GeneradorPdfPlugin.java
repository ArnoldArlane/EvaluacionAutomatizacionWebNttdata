package com.nttdata.report;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;

public class GeneradorPdfPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestRunFinished.class,
                evento -> {
                    try {
                        HtmlToPdf.generarPDF();
                    } catch (Exception e) {
                        throw new RuntimeException(
                                "Error generando PDF", e
                        );
                    }
                }
        );
    }
}