package com.nttdata.steps;

import com.nttdata.page.StoreLoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class StoreLoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    public StoreLoginSteps(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void ingresarTienda() {
        driver.get("https://qalab.bensg.com/store");
    }

    public void abrirLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(
                StoreLoginPage.loginLink)).click();
    }

    public void escribirUsuario(String usuario) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                StoreLoginPage.userInput)).sendKeys(usuario);
    }

    public void escribirPassword(String password) {
        driver.findElement(StoreLoginPage.passInput)
                .sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(StoreLoginPage.loginButton).click();
    }

    public void iniciarSesion(String usuario, String password) {
        abrirLogin();
        escribirUsuario(usuario);
        escribirPassword(password);
        clickLogin();
    }

    public boolean usuarioAutenticado() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    StoreLoginPage.userAccount));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}