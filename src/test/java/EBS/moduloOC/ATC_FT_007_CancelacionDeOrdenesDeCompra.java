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

public class ATC_FT_007_CancelacionDeOrdenesDeCompra extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_007_CancelacionDeOrdenesDeCompra_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.get3076719EbsUsr;
        String password = GlobalVariables.get3076719EbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad =data.get("responsabilidad");//"MXOXXO-PO10QRI-Compras";
        String oc = data.get("oc");
        String comentario = data.get("comentario");
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
        // PASO 6
        addStep("Abrir la pantalla de Consulta Ordendes de Compra");
        paginaInicial.appletConsultaOrdenesDeCompra.click();
        testCase.addScreenShotCurrentStep("consultaOrdenesDeCompra");
        u.hardWait(15);
        patternUtil.runApplet();
        u.hardWait(35);
        testCase.addScreenShotCurrentStep("appletConsultaOrdenesDeCompra");
        // PASO 7
        addStep("Ingresar el numero de orden de compra a cancelar en el campo 'NÃºmero'");
        assertTrue(patternUtil.validatePattern("ocNumeroDeOcEncontrar"));
        patternUtil.textBoxTypeRight("ocNumeroDeOcEncontrar", 80, oc);
        testCase.addScreenShotCurrentStep("ocIngresarOrdenDeCompra");
        // PASO 8
        addStep("Dar clic en el boton Encontrar");
        patternUtil.keyAlt("t");
        u.hardWait(8);
        testCase.addScreenShotCurrentStep("ocNumeroDeOcEncontrar");
        // PASO 9
        addStep("Dar clic en el menu 'Herramientas'");
        patternUtil.keyAlt("h");
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("ocMenuHerramientas");
        u.hardWait(3);
        // PASO 10
        addStep("Dar click en la opcion 'Controlar'");
        assertTrue(patternUtil.validatePattern("ocControlar"));
        patternUtil.clickPattern("ocControlar", 10);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("ocControlar");
        // PASO 11
        addStep("Seleccionar la opcion 'Cancelar OC'");
        assertTrue(patternUtil.validatePattern("ocCancelarOC"));
        patternUtil.doubleClickPattern("ocCancelarOC", 10);
        testCase.addScreenShotCurrentStep("ocCancelarOC");
        // PASO 12
        addStep("Ingresar un motivo");
        assertTrue(patternUtil.validatePattern("ocMotivo"));
        patternUtil.textBoxTypeRight("ocMotivo", 80, comentario);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("ocMotivoCancelacion");
        // PASO 13
        addStep("Aceptar la cancelacion");
        assertTrue(patternUtil.validatePattern("ocCancelarAceptar"));
        testCase.addScreenShotCurrentStep("ocAceptarCancelacion");
        patternUtil.clickPattern("ocCancelarAceptar", 10);
        // PASO 14
        addStep("Confirmar la cancelacion");
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("confirmarCancelacion");
        assertTrue(patternUtil.validatePattern("ocCancelacionAlerta"));
        patternUtil.keyAlt("a");
        u.hardWait(5);
        patternUtil.keyAlt("a");
        testCase.addScreenShotCurrentStep("confirmacion1");
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("confirmacion2");
        patternUtil.closeWindow();
        u.hardWait(5);
        patternUtil.validatePattern("aceptarWarning");
        patternUtil.keyAlt("a");
        u.close();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_007_CancelacionDeOrdenesDeCompra";
    }

    @Override
    public String setTestDescription() {
        return "Cancelar una Orden de compra ";
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
        return "Contar con un numero de Orden de compra a cancelar";
    }

    @Override
    public void beforeTest() {

    }
}
