package com.nttdata.stepsdefinitions;

import com.nttdata.steps.StoreLoginSteps;
import com.nttdata.steps.StoreProductsSteps;
import com.nttdata.steps.StoreCartSteps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

import static com.nttdata.core.DriverManager.*;

public class StoreStepsDef {

    private WebDriver driver;
    private StoreLoginSteps loginSteps;
    private StoreProductsSteps productsSteps;
    private StoreCartSteps cartSteps;
    private BigDecimal totalEsperado;

    @Dado("estoy en la página de la tienda")
    public void estoyEnLaPáginaDeLaTienda() {
        driver = getDriver();

        loginSteps = new StoreLoginSteps(driver);
        productsSteps = new StoreProductsSteps(driver);
        cartSteps = new StoreCartSteps(driver);

        loginSteps.ingresarTienda();

        screenShot();
    }

    @Y("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(String arg0, String arg1) {
        Assertions.assertNotNull(arg0, "Usuario no configurado");
        Assertions.assertNotNull(arg1, "Contraseña no configurada");

        loginSteps.iniciarSesion(arg0, arg1);

        Assertions.assertTrue(
                loginSteps.usuarioAutenticado(),
                "No se pudo iniciar sesión"
        );

        screenShot();
    }

    @Cuando("navego a la categoria {string} y subcategoria {string}")
    public void navegoALaCategoriaYSubcategoria(String arg0, String arg1) {
        productsSteps.navegarCategoria(arg0, arg1);

        int cantidadProductos = productsSteps.obtenerCantidadProductos();

        Assertions.assertTrue(
                cantidadProductos > 0,
                "No existen productos en la categoría"
        );

        screenShot();
    }

    @Y("agrego {int} unidades del primer producto al carrito")
    public void agregoUnidadesDelPrimerProductoAlCarrito(int arg0) {
        productsSteps.agregarPrimerProducto(arg0);

        totalEsperado = productsSteps.calcularTotalEsperado();

        screenShot();
    }

    @Entonces("valido en el popup la confirmación del producto agregado")
    public void validoEnElPopupLaConfirmaciónDelProductoAgregado() {
        String mensaje = productsSteps.obtenerMensajeConfirmacion();

        Assertions.assertFalse(
                mensaje.trim().isEmpty(),
                "No aparece confirmación del producto"
        );

        screenShot();
    }

    @Y("valido en el popup que el monto total sea calculado correctamente")
    public void validoEnElPopupQueElMontoTotalSeaCalculadoCorrectamente() {
        BigDecimal totalPopup = productsSteps.obtenerTotalPopup();

        Assertions.assertEquals(
                0,
                totalEsperado.compareTo(totalPopup),
                "El total del popup no coincide"
        );

        screenShot();
    }

    @Cuando("finalizo la compra")
    public void finalizoLaCompra() {
        productsSteps.finalizarCompra();

        screenShot();
    }

    @Entonces("valido el titulo de la pagina del carrito")
    public void validoElTituloDeLaPaginaDelCarrito() {
        String titulo = cartSteps.obtenerTituloCarrito();

        System.out.println("Título obtenido: " + titulo);

        Assertions.assertEquals(
                "CARRITO",
                titulo,
                "El título de la página del carrito es incorrecto"
        );

        screenShot();
    }

    @Y("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvoAValidarElCalculoDePreciosEnElCarrito() {
        BigDecimal subtotal = cartSteps.obtenerSubtotalCarrito();

        Assertions.assertEquals(
                0,
                totalEsperado.compareTo(subtotal),
                "El subtotal del carrito es incorrecto"
        );

        screenShot();
    }
}
