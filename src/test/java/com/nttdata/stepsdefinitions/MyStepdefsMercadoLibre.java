package com.nttdata.stepsdefinitions;

import com.nttdata.steps.InventorySteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static com.nttdata.core.DriverManager.getDriver;
import static com.nttdata.core.DriverManager.screenShot;

public class MyStepdefsMercadoLibre {
    private WebDriver driver;
    private InventorySteps inventorySteps(WebDriver driver){
        return new InventorySteps(driver);
    }
    @Given("estoy en la página de MercadoLibre")
    public void estoyEnLaPáginaDeMercadoLibre() {
        driver = getDriver();
        driver.get("https://www.mercadolibre.com.pe/");
        screenShot();
    }

    @When("busco un producto {string}")
    public void buscoUnProducto(String arg0) {
    }

    @Then("una lista de resultados")
    public void unaListaDeResultados() {
    }

    @And("valido el primer titulo del resultado sea {string}")
    public void validoElPrimerTituloDelResultadoSea(String arg0) {
    }
}
