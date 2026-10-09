package com.nttdata.steps;

import com.nttdata.page.StoreCartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;

public class StoreCartSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public StoreCartSteps(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String obtenerTituloCarrito() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreCartPage.cartTitle))
                .getText();
    }

    public BigDecimal obtenerSubtotalCarrito() {
        String subtotal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreCartPage.subtotal))
                .getText();

        return new StoreProductsSteps(driver)
                .convertirPrecio(subtotal);
    }
}