package WEB.seguridadSADEL.om;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;
import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PendientesanteIMSS {
    private final SeleniumUtil u;
    private final TestCase testCase;

    /**
	 * Localizadores
	 ******************************************************************************************/
    private final By botonexportarexcel = By.xpath("//img[@name='Image13']");
    private final By empleadosMenu = By.id("e0_0i");
    private final By pendientesIMSS = By.id("e0_3o");

    public PendientesanteIMSS(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }
    
    /**
     * Método para ingresar a la pantalla de Pendientes ante IMSS.
     *
     * @author Carlos Martínez Ortega
     * @date 04/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarPendientesAnteIMSS() {
        boolean validacion = false;
        try {
            u.waitWebElement(empleadosMenu, 25, 4);
            u.click(empleadosMenu);
            u.waitWebElement(pendientesIMSS, 25, 4);
            u.click(pendientesIMSS);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonexportarexcel));
            testCase.addScreenShotCurrentStep(u, "Se ingresó correctamente a la pantalla Pendientes ante IMSS");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Pendiente ante IMSS " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para exportar el Excel de la pantalla Pendientes ante IMSS.
     *
     * @author Carlos Martínez Ortega
     * @date 04/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarExportarExcel() {
        boolean validacion = false;
        try {
            u.waitWebElement(botonexportarexcel, 30, 5);
            WebElement botonExportar = u.getDriver().findElement(botonexportarexcel);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("arguments[0].click();", botonExportar);
            Thread.sleep(3000);

            java.awt.Robot robot = new java.awt.Robot();

            // Enviamos ALT + G (atajo nativo en Windows para presionar el botón "Guardar")
            robot.keyPress(java.awt.event.KeyEvent.VK_ALT);
            robot.keyPress(java.awt.event.KeyEvent.VK_G);
            robot.keyRelease(java.awt.event.KeyEvent.VK_G);
            robot.keyRelease(java.awt.event.KeyEvent.VK_ALT);

            testCase.addScreenShotCurrentStep(u, "Se exportó correctamente a Excel");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar Exportar Excel" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que se descargó el archivo de manera correcta.
     *
     * @author Carlos Martínez Ortega
     * @date 04/Junio/2026
     * @param rutaBase         Propiedad del sistema que contiene la ruta base
     * @param carpetaDescargas Nombre de la carpeta de descargas
     * @param nombreEsperado   Nombre exacto del archivo esperado (con extensión .xls)
     * @throws Exception
     */
    public boolean validarDescargaExcel(String rutaBase, String carpetaDescargas, String nombreEsperado) {
        boolean validacion = false;
        try {
            String rutaDescargas = System.getProperty(rutaBase) + "\\" + carpetaDescargas;
            File carpeta = new File(rutaDescargas);
            File[] archivos = carpeta.listFiles();

            boolean encontrado = false;

            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.getName().equals(nombreEsperado) && archivo.getName().endsWith(".xls")) {
                        System.out.println("Archivo encontrado: " + archivo.getAbsolutePath());
                        encontrado = true;
                        break;
                    }
                }
            }

            if (!encontrado) {
                throw new Exception("No se encontró el archivo esperado: " + nombreEsperado +
                                    " (formato .xls) en la carpeta: " + rutaDescargas);
            }

            testCase.addScreenShotCurrentStep(u, "El archivo se descargó de manera correcta");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró encontrar el excel descargado " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

}
