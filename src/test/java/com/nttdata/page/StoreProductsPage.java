package com.nttdata.page;

import org.openqa.selenium.By;

public class StoreProductsPage {

    public static By categoria(String categoria) {
        return By.xpath(
                "//a[@data-depth='0' and contains(normalize-space(.),'"
                        + categoria + "')]"
        );
    }

    public static By subcategoria(String subcategoria) {
        return By.xpath(
                "//a[@data-depth='1' and contains(normalize-space(.),'"
                        + subcategoria + "')]"
        );
    }

    public static By productsCards =
            By.cssSelector(".product-miniature");

    public static By firstProduct =
            By.cssSelector(".product-miniature a.product-thumbnail");

    public static By productPrice =
            By.cssSelector(".current-price span");

    public static By quantityInput =
            By.id("quantity_wanted");

    public static By addToCartButton =
            By.cssSelector(".add-to-cart");

    public static By popupConfirmation =
            By.cssSelector("#blockcart-modal .modal-title");

    public static By popupTotal =
            By.cssSelector("#blockcart-modal .cart-content .value");

    public static By continueCheckout =
            By.cssSelector("#blockcart-modal a.btn-primary");
}