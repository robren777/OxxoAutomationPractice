package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.ebsInternational.ActualizarCuentaBancaria;
import EBS.util.pageObjectModel.ebsInternational.AdministracionCuentasBancarias;
import EBS.util.pageObjectModel.ebsInternational.BuscarSeleccionarHabilitadaParaPagoEnKyriba;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_01_identificacionCuentasBancariasOperacionKyriba extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_01_identificacionCuentasBancariasOperacionKyriba_test(HashMap<String, String> data) {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getEbsInternacional;
        String userName = GlobalVariables.getJhernandezHexEbsIntUsr;
        String password = GlobalVariables.getJhernandezHexEbsIntPwd;
        String responsabilidad = data.get("responsabilidad");
        String numeroCuenta = data.get("numeroCuenta");

        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
        AdministracionCuentasBancarias cuentasBancarias = new AdministracionCuentasBancarias(u, testCase);
        ActualizarCuentaBancaria actualizarCuentaBancaria = new ActualizarCuentaBancaria(u, testCase);
        BuscarSeleccionarHabilitadaParaPagoEnKyriba buscarSeleccionarHabilitadaParaPagoEnKyriba = new BuscarSeleccionarHabilitadaParaPagoEnKyriba(u, testCase);

        /*
         * PASOS DEL CASO DE PRUEBA
         */

        // PASO 1
        addStep("Ingresar EBS internacional " + GlobalVariables.getEbsInternacional);
        logger.info("EBS URL: " + ebsUrl);
        System.out.println("EBS URL: " + ebsUrl);
        logger.info("User Name: " + userName);
        System.out.println("User Name: " + userName);
        inicio.loginInternacional();
        // PASO 2
        addStep("Dirigirse a la responsabilidad '"+responsabilidad+"'");
        paginaInicial.clicEnResponsabilidad(responsabilidad);
        // PASO 3
        addStep("Configuracion");
        paginaInicial.peOxxoCMxxxxxSuperUsuarioConfiguracionElement.click();
        testCase.addScreenShotCurrentStep(u, "configuracion");
        // PASO 4
        addStep("Bancos");
        paginaInicial.peCmSuUsConfigBancosElement.click();
        testCase.addScreenShotCurrentStep(u, "bancos");
        // PASO 5
        addStep("Cuentas Bancarias");    
        paginaInicial.peCmSuUsConfigCuentasBancariasElement.click();
        testCase.addScreenShotCurrentStep(u, "cuentasBancarias");
        u.waitForLoadPage();
        // PASO 6
        addStep("Colocar el numero de cuenta en el campo 'Numero Cuenta': " + "'" + numeroCuenta + "'");
        //testCase.addScreenShotCurrentStep(u, "numeroDeCuenta");
        cuentasBancarias.numeroDeCuenta.sendKeys(numeroCuenta);
        testCase.addScreenShotCurrentStep(u, "numeroDeCuentaInput");
        // PASO 7
        addStep("Dar clic en 'Ir'");
        cuentasBancarias.ir.click();
        testCase.addScreenShotCurrentStep(u, "clickIr");
        u.waitForLoadPage();
        // PASO 8
        addStep("Dar doble clic sobre el campo nombre de la cuenta");
        cuentasBancarias.primerSelectCuenta.click();
        testCase.addScreenShotCurrentStep(u, "primerSelectCuenta");
        u.hardWait(5);
        // PASO 9
        addStep("Dar clic en 'Actualizar' Cuenta");
        cuentasBancarias.actualizarCuentas.click();
        testCase.addScreenShotCurrentStep(u, "actualizarCuenta");
        u.hardWait(2);
        u.waitForLoadPage();
        u.hardWait(5);
        // PASO 10
        addStep("Dar clic en 'Informacion de Cuenta'");
        actualizarCuentaBancaria.informacionDeCuenta.click();
        u.waitForLoadPage();
        testCase.addScreenShotCurrentStep(u, "informacionDeCuenta");      
        assertEquals(actualizarCuentaBancaria.habilitadaParaPagoEnKyriba.getText(), "* Habilitada para pago en Kyriba");  
        u.scrollToElement(actualizarCuentaBancaria.habilitadaParaPagoEnKyribaWeb);   
        testCase.addTextEvidenceCurrentStep("Campo: '" + actualizarCuentaBancaria.habilitadaParaPagoEnKyriba.getText() + "'");
        //Store the ID of the original window
        String originalWindow = u.getDriver().getWindowHandle();
        System.out.println("Original Window: " + originalWindow);
        //Check we don't have other windows open already
        assert u.getDriver().getWindowHandles().size() == 1;
        testCase.addScreenShotCurrentStep(u, "informacionDeCuentaVentana");
        //Click the link which opens in a new window
        // PASO 11
        addStep("Dar clic en el icono de buscar en el campo " + actualizarCuentaBancaria.habilitadaParaPagoEnKyriba.getText());
        actualizarCuentaBancaria.buscarHabilitadaParaPagoEnKyriba.click();
        testCase.addScreenShotCurrentStep(u, "clickHabilitarPagoKyriba");
        //Wait for the new window or tab
        u.hardWait(10);
        Set<String> s = u.getDriver().getWindowHandles();
        List<String> sq = new ArrayList<>(s);
        assert u.getDriver().getWindowHandles().size() == 2;
        //Cambiamos a la nueva ventana
        u.getDriver().switchTo().window(sq.get(1));
        //Maximizamos
        u.getDriver().manage().window().maximize();
        //Esperamos que cargue la página
        u.waitForLoadPage();
        testCase.addScreenShotCurrentStep(u, "terminoBusquedaWeb1");
        //Obtenemos el identificador de la página actual
        String childWindow = u.getDriver().getWindowHandle();
        System.out.println("Child window: " + childWindow);
        u.hardWait(10);
        System.out.println("Windows: " + sq);
        int size = u.getDriver().findElements(By.tagName("iframe")).size();
        System.out.println("Numero de frames: " + size);
        //Cambiamos al Frame de la nueva ventana
        u.getDriver().switchTo().frame(0);
        u.getDriver().manage().window().maximize();
        u.highLight(buscarSeleccionarHabilitadaParaPagoEnKyriba.terminoBusquedaWeb);
        //testCase.addScreenShotCurrentStep(u, "terminoBusquedaWeb2");
        // PASO 12
        addStep("Borramos la busqueda actual");
        buscarSeleccionarHabilitadaParaPagoEnKyriba.terminoDeBusqueda.clear();
        testCase.addScreenShotCurrentStep(u, "borrarBusquedaActual");
        // PASO 13
        addStep("Dar clic en el boton 'Ir'");
        buscarSeleccionarHabilitadaParaPagoEnKyriba.irButton.click();
        u.waitForLoadPage();
        u.hardWait(2);
        testCase.addScreenShotCurrentStep(u, "clickIr");
        // PASO 14
        addStep("Validar que el flexfield 'Habilitadas para Pago Kyriba' tenga dos opciones");
        u.highLight(buscarSeleccionarHabilitadaParaPagoEnKyriba.nValueWeb);
        assertTrue(buscarSeleccionarHabilitadaParaPagoEnKyriba.nValue.isDisplayed());
        u.highLight(buscarSeleccionarHabilitadaParaPagoEnKyriba.yValueWeb);
        assertTrue(buscarSeleccionarHabilitadaParaPagoEnKyriba.yValue.isDisplayed());
        testCase.addScreenShotCurrentStep(u, "kyribaOptions");
        testCase.addBoldTextEvidenceCurrentStep("El flexfield 'Habilitadas para Pago Kyriba' tiene dos opciones 'Y' y 'N'");
        u.close();


    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_01_identificacionCuentasBancariasOperacionKyriba_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar la habilitación de un flexfield (Habilitada para Kyriba) en la pantalla de configuración de la cuenta bancaria el cual debe tener dos opciones (Si/No)";
    }

    @Override
    public String setTestDesigner() {
        return "JesusH@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "*Contar con acceso a EBS INTERNACIONAL";
    }

    @Override
    public void beforeTest() {

    }
}
