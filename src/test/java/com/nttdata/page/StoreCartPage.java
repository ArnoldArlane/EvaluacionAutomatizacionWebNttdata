package com.nttdata.page;

import org.openqa.selenium.By;

public class StoreCartPage {

    public static By cartTitle =
            By.cssSelector(".cart-grid-body .cart-container h1");

    public static By unitPrice =
            By.cssSelector(".product-price");

    public static By quantityInput =
            By.cssSelector(".js-cart-line-product-quantity");

    public static By totalPrice =
            By.cssSelector(".cart-total .value");

    public static By subtotal =
            By.cssSelector("#cart-subtotal-products .value");
}