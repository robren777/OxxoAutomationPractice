package EBS.moduloOC;

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

public class ATC_FT_004_ImpresionDeLaOrdenDeCompra extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_004_ImpresionDeLaOrdenDeCompra_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.get3076719EbsUsr;
        String password = GlobalVariables.get3076719EbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad"); //"MXOXXO-PO10QRI-Compras";
        String ocDesde = data.get("ocDesde");
        String ocA = data.get("ocA");
        String cr = data.get("cr");
        String articulo = data.get("articulo");
        String cantidad = data.get("cantidad");
        String precio = data.get("precio");
        String pactado = data.get("pactado");
        String necesarioPara = data.get("necesarioPara");
        String solicitud = data.get("solicitud");

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
        addStep("Seleccionar idioma: EspaÃ±ol Latinoamericano.");
        // PASO 3
        addStep("Ingresar usuario y contraseÃ±a.");
        // PASO 4
        addStep("Dar clic en el boton de 'Conectese'.");
        inicio.login();
        // PASO 5
        addStep("Ingresar a la responsabilidad '"+responsabilidad+"'.");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        // PASO 6
        addStep("Dar clic en Consulta Ordenes de Compra");
        paginaInicial.consultaOrdenesDeCompra.click();
        testCase.addScreenShotCurrentStep(u, "consultaOrdenesDeCompra");
        u.hardWait(15);
        patternUtil.runApplet();
        u.hardWait(35);
        // PASO 7
        addStep("Ejecutar nueva solicitud: Clic en el menu Ver(x)");
        patternUtil.keyAlt("x");
        u.hardWait(1);
        testCase.addScreenShotCurrentStep("ocMenuVer");
        // PASO 8
        addStep("Seleccionar la opcion 'Solicitudes'");
        patternUtil.clickPattern("solicitudes", 10);
        u.hardWait(30);  
        testCase.addScreenShotCurrentStep("ocSolicitudesOpciones");
        // PASO 9
        addStep("Dar clic en la opcion 'Ejecutar Nueva Solicitud'");
        assertTrue(patternUtil.validatePattern("ocSolicitudesOpciones"));
        testCase.addScreenShotCurrentStep("ocSolicitudesOpciones");
        patternUtil.clickPattern("ejecutarNuevaSolicitudButton", 10);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("ocPantallaEjecucionSolicitudes");
        // PASO 10
        addStep("Escribe la solicitud 'XXFC_ADQ: Impresion de Orden de Compra Abierta' y presiona la tecla 'TAB'");
        patternUtil.enterText(solicitud);
        u.hardWait(1);
        patternUtil.nTAB(1);
        u.hardWait(3);
        assertTrue(patternUtil.validatePattern("XXFCADQImpresionOrdenCompra"));
        testCase.addScreenShotCurrentStep("XXFCADQImpresionOrdenCompra");
        patternUtil.doubleClickPattern("XXFCADQImpresionOrdenCompra", 10);
        u.hardWait(5);
        // PASO 11
        addStep("Ingresa los parametros de la solicitud");
        patternUtil.enterText("Todo");
        patternUtil.nTAB(1);
        patternUtil.textBoxTypeRight("ocNumeroDeComprador", 80, ocDesde);
        patternUtil.enterText(ocDesde);
        patternUtil.doubleClickPattern("ocDownArrow", 10);
        u.hardWait(2);
        assertTrue(patternUtil.validatePattern("ocImprimirLineasCanceladas"));
        patternUtil.textBoxTypeRight("ocImprimirLineasCanceladas", 80, "no");
        testCase.addScreenShotCurrentStep("ocImprimirLineasCanceladas");
        patternUtil.keyAlt("a");
        // PASO 12
        addStep("Ejecuta la solicitud dando clic en el boton 'Ejecutar'");
        assertTrue(patternUtil.validatePattern("ejecutar"));
        testCase.addScreenShotCurrentStep("ocSolicitudDatosCompletos");
        patternUtil.clickPattern("ejecutar", 10);
        u.hardWait(2);
        patternUtil.keyAlt("n");
        testCase.addScreenShotCurrentStep("ocEjecutarSolicitud");
        u.hardWait(10);
        // PASO 13
        addStep("Encuentra la solicitud dando clic en el boton 'Encontrar'");
        assertTrue(patternUtil.validatePattern("ocSolicitudesOpciones"));
        testCase.addScreenShotCurrentStep("ocEncontrarSolicitud");
        patternUtil.clickPattern("encontrar", 10);
        u.hardWait(10);
        assertTrue(patternUtil.validatePattern("ocRefrescarDatos"));
        patternUtil.clickPattern("ocRefrescarDatos",10);
        testCase.addScreenShotCurrentStep("visualizarOutput");
        // PASO 14
        addStep("Visualizar output");
        patternUtil.clickPattern("visualizaroutput", 10);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("pdfOutput");
        patternUtil.closeWindow();
        assertTrue(patternUtil.validatePattern("ocLogo"));
        patternUtil.clickPattern("ocLogo",10);
        patternUtil.closeWindow();
        u.hardWait(5);
        patternUtil.validatePattern("aceptarWarning");
        patternUtil.keyAlt("a");
        u.close();


    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_004_ImpresionDeLaOrdenDeCompra_test";
    }

    @Override
    public String setTestDescription() {
        return "Impresion de ordenes de compra para entregar al proveedor";
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
        return "Ordenes de compra previamente aprobadas";
    }

    @Override
    public void beforeTest() {

    }
}
