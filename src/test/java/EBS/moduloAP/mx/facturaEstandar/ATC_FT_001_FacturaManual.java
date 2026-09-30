package EBS.moduloAP.mx.facturaEstandar;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_FacturaManual extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_FacturaManual_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.getMduenasHexEbsUsr;
        String password = GlobalVariables.getMduenasHexEbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String socioComercial = data.get("socioComercial");
        String nombreSucursal = data.get("nombreSucursal");
        String fechaFactura = data.get("fechaFactura");
        String numeroFactura = data.get("numeroFactura");
        String importe = data.get("importe");
        String descripcionFactura = data.get("descripcionFactura");
        String cuentaDistribucion = data.get("cuentaDistribucion");
        String importeLineas = data.get("importeLineas");

        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();

        /*
         * UTILERÃ�AS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        SikuliUtil sk = new SikuliUtil();
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

        /*
         * PASOS DEL CASO DE PRUEBA
         */
        // PASO 1
        addStep("Ingresar al ambiente de EBS.");
        // PASO 2
        addStep("Seleccionar idioma: Español Latinoamericano.");
        // PASO 3
        addStep("Ingresar usuario y contraseña.");
        // PASO 4
        addStep("Dar clic en el boton de 'Conectese'.");
        inicio.login();
        // PASO 5
        addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-USUARIO-FACTURA'.");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        testCase.addScreenShotCurrentStep("seleccionarResponsabilidad");
        u.hardWait(1);
        // PASO 6
        addStep("Ingresar a la opcion Comision -> Captura por Capacitacion.");
        paginaInicial.carpetaFacturasElement.click();
        u.hardWait(1);
        paginaInicial.carpetaIngreso.click();
        u.hardWait(1);
        paginaInicial.appletFacturas.click();
        testCase.addScreenShotCurrentStep("seleccionarFactura");
        u.hardWait(90);
        patternUtil.runApplet();
        u.hardWait(40);
        testCase.addScreenShotCurrentStep("pantallaFactura");
        // PASO 7
        addStep("Capturar los datos necesarios, dar clic en Registrar.");
        assertTrue(patternUtil.validatePattern("mxApSocioComercial"), "La aplicacion esta cargando mas lento de lo habitual, aun no se muestra la pantalla adecuada");
        u.hardWait(10);
        patternUtil.nTAB(4);
        // PASO 8
        addStep("Ingresar el Socio Comercial");
        patternUtil.enterText(socioComercial);
        patternUtil.nTAB(2);
        patternUtil.enterText(nombreSucursal);
        patternUtil.nTAB(1);
        patternUtil.enterText(fechaFactura);
        patternUtil.nTAB(1);
        patternUtil.enterText(numeroFactura);
        patternUtil.nTAB(2);
        patternUtil.enterText(importe);
        testCase.addScreenShotCurrentStep("facturaDatosGenerales");
        patternUtil.nTAB(8);
        u.hardWait(10);
        assertTrue(patternUtil.validatePattern("apColumnaDescripcion"));
        patternUtil.enterText(descripcionFactura);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("facturaDesc");
        // PASO 9
        addStep("Navegar a la pestaña 'Lineas'");
        patternUtil.keyAlt("2");
        u.hardWait(8);
        testCase.addScreenShotCurrentStep("Lineas");
        assertTrue(patternUtil.validatePattern("apTabLineas"));
        assertTrue(patternUtil.validatePattern("apImporte"));
        
        // PASO 10
        addStep("Ingresar los datos necesarios: Importe, Cuenta de distribucion por defecto");
        patternUtil.textBoxTypeBelow("apImporte",20,"1");
        patternUtil.nTAB(1);
        u.hardWait(3);
        patternUtil.enterText(importeLineas);
        assertTrue(patternUtil.validatePattern("apImporte"));
        testCase.addScreenShotCurrentStep("facturaLineasImporte");
        patternUtil.nTAB(12);
        u.hardWait(10);
        assertTrue(patternUtil.validatePattern("apCuentaDistribucionPorDefecto"));
        patternUtil.enterText(cuentaDistribucion);
        testCase.addScreenShotCurrentStep("facturaLineasCuentaDistribucionPorDefecto");
        patternUtil.nTAB(30);
        u.hardWait(10);
        assertTrue(patternUtil.validatePattern("apCodigoDeClasificacionDeImpuesto"));
        testCase.addScreenShotCurrentStep("facturaLineasCodigoDeClasificacionDeImpuesto");
        // PASO 11
        addStep("Dar clic en el boton de 'Calcular impuesto'");
        patternUtil.clickPattern("apBotonCalcularImpuesto", 10);
        u.hardWait(20);
        testCase.addScreenShotCurrentStep("ImpuestoCalculado");
        patternUtil.keyAlt("1");
        u.hardWait(10);
        // PASO 12
        addStep("Navegar a la pestaña 'General'");
        assertTrue(patternUtil.validatePattern("apTabGeneral"));
        testCase.addTextEvidenceCurrentStep("El estado de la factura debe ser 'Nunca Validado'");
        assertTrue(patternUtil.validatePattern("apEstadoNuncaValidado"));
        testCase.addScreenShotCurrentStep("facturaEstadoNuncaValidado");
        // PASO 13
        addStep("Hacer clic en el boton 'Acciones'");
        patternUtil.clickPattern("apBotonAcciones", 10);
        patternUtil.keyAlt("n");
        u.hardWait(4);
        testCase.addScreenShotCurrentStep("apBotonAcciones");
        // PASO 14
        addStep("Hacer clic en la opcion 'Validar'");
        patternUtil.keyAlt("v");
        u.hardWait(2);
        assertTrue(patternUtil.validatePattern("apCheckboxValidar"));
        testCase.addScreenShotCurrentStep("facturaCheckboxValidar");
        // PASO 15
        addStep("Hacer clic en el boton 'Aceptar'");
        patternUtil.keyAlt("t");
        testCase.addScreenShotCurrentStep("facturaValidarAceptar");
        u.hardWait(14);
        // PASO 16
        addStep("El estado actual de la factura se ha validado");
        u.hardWait(8);
        assertTrue(patternUtil.validatePattern("apEstadoValidado"));
        testCase.addScreenShotCurrentStep("apEstadoValidado");
        patternUtil.closeWindow();
        u.hardWait(5);
        patternUtil.validatePattern("aceptarWarning");
        patternUtil.keyAlt("a");
        u.close();
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_FacturaManual_test";
    }

    @Override
    public String setTestDescription() {
        return "Factura manual";
    }

    @Override
    public String setTestDesigner() {
        return "Automation QA";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}
