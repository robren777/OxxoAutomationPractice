package EBS.moduloAP.mx.validacionDeFacturas;

import static org.testng.Assert.assertEquals;
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

public class ATC_FT_003_RetencionDeFacturas extends BaseExecution {


    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_RetencionDeFacturas_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.getMduenasHexEbsUsr;
        String password = GlobalVariables.getMduenasHexEbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");
        String nombre = data.get("nombre");
        String sucursalProveedor = data.get("sucursalProveedor");
        String numeroDeFactura = data.get("numeroDeFactura");
        String fechaFactura = data.get("fechaFactura");
        String unidadOperativa = data.get("unidadOperativa");
        String motivoDeRetencion = data.get("motivoDeRetencion");

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
        paginaInicial.carpetaFacturasElement.click();
        paginaInicial.carpetaIngreso.click();
        paginaInicial.appletFacturas.click();
        testCase.addScreenShotCurrentStep("seleccionarFactura");
        u.hardWait(90);
        // PASO 6
        addStep("Se abre la pantalla de EBS");
        patternUtil.runApplet();
        u.hardWait(40);
        testCase.addScreenShotCurrentStep("pantallaFactura");
        // PASO 7
        addStep("Da clic en el icono de busqueda");
        patternUtil.clickPattern("lamparabusqueda", 60);
        testCase.addScreenShotCurrentStep("busqueda");
        u.hardWait(10);
        // PASO 8
        addStep("Ingresa el nombre del socio comercial");
        patternUtil.textBoxTypeRight("facturaSocioComercialNombre", 30, nombre);
        patternUtil.nTAB(1);
        // PASO 9
        addStep("Escribe la sucursal");
        patternUtil.enterText(sucursalProveedor);
        patternUtil.nTAB(3);
        // PASO 10
        addStep("Escribe el numero de factura");
        patternUtil.enterText(numeroDeFactura);
        testCase.addScreenShotCurrentStep("datosBusqueda");
        // PASO 11
        addStep("Da clic en el boton de encontrar");
        patternUtil.clickPattern("encontrarBotonO", 10);
        testCase.addScreenShotCurrentStep("botonEncontrar");
        // PASO 12
        addStep("Se visualiza la factura a consultar");
        u.hardWait(5);
        patternUtil.nTAB(7);
        u.hardWait(5);
        String numFactura = patternUtil.textCopy();
        assertEquals(numFactura, numeroDeFactura, "La primera factura NO concuerda");
        testCase.addScreenShotCurrentStep("factura");
        testCase.addTextEvidenceCurrentStep("Factura " + numFactura + " encontrada");
        patternUtil.keyAlt("5");
        u.hardWait(5);
        assertTrue(patternUtil.validatePattern("apPagosProgramados"));
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("apPagosProgramados");
        assertTrue(patternUtil.validatePattern("apCheckboxRetencionesUncheck"));
        patternUtil.clickPattern("apCheckboxRetencionesUncheck", 10);
        u.hardWait(10);
        patternUtil.clickPattern("apCheckboxRetencionesUncheck", 10);
        u.hardWait(10);
        assertTrue(patternUtil.validatePattern("apCheckboxRetenciones"));
        patternUtil.nTAB(1);
        patternUtil.enterText(motivoDeRetencion);
        testCase.addScreenShotCurrentStep("checkYmotivo");
        assertTrue(patternUtil.validatePattern("botonGrabar"));
        patternUtil.clickPattern("botonGrabar",10);
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("apPagosProgramados2");
        patternUtil.closeWindow();
        u.hardWait(5);
        patternUtil.validatePattern("aceptarWarning");
        patternUtil.keyAlt("a");
        u.close();
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_RetencionDeFacturas_test";
    }

    @Override
    public String setTestDescription() {
        return "Retencion de facturas";
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
        return null;
    }

    @Override
    public void beforeTest() {

    }
}