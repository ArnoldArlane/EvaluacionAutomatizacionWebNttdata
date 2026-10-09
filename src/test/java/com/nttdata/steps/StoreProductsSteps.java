package com.nttdata.steps;

import com.nttdata.page.StoreProductsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;

public class StoreProductsSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    private BigDecimal precioUnitario;
    private int cantidad;

    public StoreProductsSteps(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navegarCategoria(String categoria, String subcategoria) {

        // Esperar hasta que la categoría sea visible
        WebElement categoriaElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreProductsPage.categoria(categoria)
                )
        );

        // Pasar el mouse sobre la categoría
        new Actions(driver)
                .moveToElement(categoriaElement)
                .perform();

        // Esperar hasta que la subcategoría sea clickeable
        WebElement subcategoriaElement = wait.until(
                ExpectedConditions.elementToBeClickable(
                        StoreProductsPage.subcategoria(subcategoria)
                )
        );

        // Seleccionar subcategoría
        subcategoriaElement.click();
    }

    public int obtenerCantidadProductos() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                StoreProductsPage.productsCards));

        return driver.findElements(
                StoreProductsPage.productsCards).size();
    }

    public void seleccionarPrimerProducto() {
        wait.until(ExpectedConditions.elementToBeClickable(
                StoreProductsPage.firstProduct)).click();
    }

    public void ingresarCantidad(int cantidad) {
        this.cantidad = cantidad;

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreProductsPage.quantityInput));

        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(String.valueOf(cantidad));
        input.sendKeys(Keys.TAB);
    }

    public BigDecimal convertirPrecio(String texto) {
        String limpio = texto.replaceAll("[^0-9,.-]", "");

        if (limpio.contains(",") && limpio.contains(".")) {
            if (limpio.lastIndexOf(",") > limpio.lastIndexOf(".")) {
                limpio = limpio.replace(".", "").replace(",", ".");
            } else {
                limpio = limpio.replace(",", "");
            }
        } else if (limpio.contains(",")) {
            limpio = limpio.replace(",", ".");
        }

        return new BigDecimal(limpio);
    }

    public void obtenerPrecioUnitario() {
        String precio = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreProductsPage.productPrice))
                .getText();

        precioUnitario = convertirPrecio(precio);
    }

    public void agregarAlCarrito() {
        wait.until(ExpectedConditions.elementToBeClickable(
                StoreProductsPage.addToCartButton)).click();
    }

    public void agregarPrimerProducto(int cantidad) {
        seleccionarPrimerProducto();
        obtenerPrecioUnitario();
        ingresarCantidad(cantidad);
        agregarAlCarrito();
    }

    public String obtenerMensajeConfirmacion() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreProductsPage.popupConfirmation))
                .getText();
    }

    public BigDecimal obtenerTotalPopup() {
        String total = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        StoreProductsPage.popupTotal))
                .getText();

        return convertirPrecio(total);
    }

    public BigDecimal calcularTotalEsperado() {
        return precioUnitario.multiply(
                BigDecimal.valueOf(cantidad));
    }

    public void finalizarCompra() {
        wait.until(ExpectedConditions.elementToBeClickable(
                StoreProductsPage.continueCheckout)).click();
    }
}