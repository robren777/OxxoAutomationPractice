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

public class ATC_FT_002_GeneracionDelReporteParaValidarCuentasACargo extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_GeneracionDelReporteParaValidarCuentasACargo_test(HashMap<String, String> data) throws Exception {
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
        addStep("Dar clic en el botÃ³n de 'ConÃ©ctese'.");
        inicio.login();
        // PASO 5
        addStep("Ingresar a la responsabilidad '"+responsabilidad+"'.");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        paginaInicial.clickOrdenesDeCompra(responsabilidad);       
        u.hardWait(20);
        patternUtil.runApplet();
        u.hardWait(60);
        // PASO 6
        addStep("Ejecutar una nueva solicitud");
        patternUtil.keyAlt("x");
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("solicitudes"));
        testCase.addScreenShotCurrentStep("ocSolicitudes");
        patternUtil.clickPattern("solicitudes", 10);
        u.hardWait(30);
        assertTrue(patternUtil.validatePattern("ocSolicitudesOpciones"));
        testCase.addScreenShotCurrentStep("ocSolicitudesOpciones");
        assertTrue(patternUtil.validatePattern("ocEjecutarNuevaSolicitud"));
        patternUtil.clickPattern("ocEjecutarNuevaSolicitud", 10);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("ocPantallaEjecutarSolicitud");
        patternUtil.enterText(solicitud);
        patternUtil.nTAB(1);
        u.hardWait(5);
        assertTrue(patternUtil.validatePattern("ocInformeDetalladoDistribucionOrdenCompra"));
        testCase.addScreenShotCurrentStep("ocSeleccionInformeDetallado");
        patternUtil.doubleClickPattern("ocInformeDetalladoDistribucionOrdenCompra", 5);     
        u.hardWait(8);     
        // PASO 7
        addStep("Ingresar los parametros de la solicitud ");
        assertTrue(patternUtil.validatePattern("ocParametros"));
        patternUtil.textBoxTypeRight("ocNumerosDeOcDesde", 80, ocDesde);
        u.hardWait(1);
        patternUtil.enterText(ocA);
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("ocSoloFondosRechazados"));
        patternUtil.textBoxTypeRight("ocSoloFondosRechazados", 80, "n");
        u.hardWait(1);
        testCase.addScreenShotCurrentStep("ocParametrosSolicitud");
        patternUtil.keyAlt("a");
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("ocSolicitudDatosCompletos");
        patternUtil.clickPattern("ejecutar", 10);
        u.hardWait(8);  
        patternUtil.keyAlt("n");  
        testCase.addScreenShotCurrentStep("ocEjecutarSolicitud");
        u.hardWait(8);
        testCase.addScreenShotCurrentStep("ocEncontrarResultadosSolicitud");
        patternUtil.keyAlt("e");
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("ocSolicitudResultados");
        patternUtil.clickPattern("visualizaroutput", 10);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("output");
        patternUtil.closeWindow();
        u.hardWait(5);
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
        return "ATC_FT_002_GeneracionDelReporteParaValidarCuentasACargo_test";
    }

    @Override
    public String setTestDescription() {
        return "GENERACION DEL REPORTE PARA VALIDAR CUENTAS A CARGO";
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
        return "Numeros de Ordenes de compra";
    }

    @Override
    public void beforeTest() {

    }
}
