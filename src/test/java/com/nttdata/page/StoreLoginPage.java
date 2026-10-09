package com.nttdata.page;

import org.openqa.selenium.By;

public class StoreLoginPage {

    public static By loginLink =
            By.cssSelector(".user-info a");

    public static By userInput =
            By.name("email");

    public static By passInput =
            By.name("password");

    public static By loginButton =
            By.id("submit-login");

    public static By userAccount =
            By.cssSelector(".user-info .account");

    public static By loginError =
            By.cssSelector(".alert-danger");
}