package Mobile.preinventariosinteligentes.mvp5.om;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Menus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.AppiumUtil;

/**
 * Flujo MVP5 para crear plantillas de Conteos Propios por UI.
 */
public final class PlantillaConteosPropiosMvp5 {
    private final AppiumUtil appium;
    private final String nombrePaquete;
    private final Duration tiempoEspera;
    private final Menus menus;
    private final Comunes comunes;
    private final ConteosPropios conteosPropios;

    public PlantillaConteosPropiosMvp5(
            AppiumUtil appium,
            String nombrePaquete,
            Duration tiempoEspera,
            Menus menus,
            Comunes comunes,
            ConteosPropios conteosPropios) {
        this.appium = appium;
        this.nombrePaquete = nombrePaquete;
        this.tiempoEspera = tiempoEspera;
        this.menus = menus;
        this.comunes = comunes;
        this.conteosPropios = conteosPropios;
    }

    public void crearPlantillaConSkus(String nombrePlantilla, List<String> skus) {
        if (nombrePlantilla == null || nombrePlantilla.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la plantilla es obligatorio");
        }
        if (skus == null || skus.isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un SKU para crear la plantilla");
        }

        menus.clickBtnNuevaPlantilla(false);
        conteosPropios.clickBtnPlus(false);
        conteosPropios.clickComboColaboradores(false);
        conteosPropios.seleccionarColaborador(0, false);
        conteosPropios.seleccionarTurno("morning", false);
        conteosPropios.clickBtnContinuar(false);
        conteosPropios.capturarNombrePlantilla(nombrePlantilla.trim());
        capturarSkus(skus);
        conteosPropios.clickBtnGuardar(false);
        confirmarGuardadoSiVisible();
        conteosPropios.esperaSeOcultaMensajeGuardado();
    }

    private void capturarSkus(List<String> skus) {
        By campoSku = id("et_barcode");
        WebElement campo = esperarVisible(campoSku);
        for (String sku : skus) {
            if (sku == null || sku.trim().isEmpty()) {
                continue;
            }
            campo.click();
            campo.sendKeys(sku.trim());
            campo.click();
            driverAndroid().pressKey(new KeyEvent(AndroidKey.ENTER));
            esperarSkuAgregado(sku.trim());
            campo = esperarVisible(campoSku);
        }
    }

    private void esperarSkuAgregado(String sku) {
        By skuAgregado = By.xpath("//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tv_product_sku")
                + " and contains(@text," + literalXpath(sku) + ")]");
        new WebDriverWait(appium.driver, tiempoEspera)
                .until(ExpectedConditions.visibilityOfElementLocated(skuAgregado));
    }

    private void confirmarGuardadoSiVisible() {
        By aceptar = By.id("android:id/button1");
        try {
            new WebDriverWait(appium.driver, Duration.ofSeconds(4))
                    .until(ExpectedConditions.elementToBeClickable(aceptar))
                    .click();
        } catch (TimeoutException ignorada) {
            // En algunos builds el guardado no muestra modal y continua directo.
        }
    }

    private WebElement esperarVisible(By localizador) {
        return new WebDriverWait(appium.driver, tiempoEspera)
                .until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    private By id(String recurso) {
        return By.id(nombrePaquete + ":id/" + recurso);
    }

    private AndroidDriver driverAndroid() {
        if (!(appium.driver instanceof AndroidDriver)) {
            throw new IllegalStateException("La ejecucion MVP5 requiere AndroidDriver");
        }
        return (AndroidDriver) appium.driver;
    }

    private void ocultarTecladoSiAplica() {
        try {
            appium.driver.hideKeyboard();
        } catch (RuntimeException ignorada) {
            // Teclado no visible o no aplicable para el dispositivo.
        }
    }

    private static String literalXpath(String valor) {
        if (!valor.contains("'")) {
            return "'" + valor + "'";
        }
        if (!valor.contains("\"")) {
            return "\"" + valor + "\"";
        }
        String[] partes = valor.split("'", -1);
        StringBuilder resultado = new StringBuilder("concat(");
        for (int indice = 0; indice < partes.length; indice++) {
            if (indice > 0) {
                resultado.append(", \"'\", ");
            }
            resultado.append("'").append(partes[indice]).append("'");
        }
        return resultado.append(')').toString();
    }
}
