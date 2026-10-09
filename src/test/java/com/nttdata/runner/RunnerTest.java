package com.nttdata.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;

import com.nttdata.report.HtmlToPdf;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber/cucumber-report.html",
                "json:target/cucumber/cucumber.json",
                "com.nttdata.report.GeneradorPdfPlugin"},
        stepNotifications = true,
        features = "src/test/resources/features",
        glue = "com.nttdata",
        tags = "@regresion"
)
public class RunnerTest {
}
