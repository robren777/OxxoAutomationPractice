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

public class ATC_FT_003_ConsultaDeOrdenesDeCompra extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_ConsultaDeOrdenesDeCompra_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.get3076719EbsUsr;
        String password = GlobalVariables.get3076719EbsPwd;

        String ebsPattern = data.get("ebsPattern");
        String responsabilidad =  /*data.get("responsabilidad")*/"MXOXXO-PO10QRI-Compras";
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
        // PASO 6
        addStep("Dar clic en Consulta Ordenes de Compra");
        paginaInicial.consultaOrdenesDeCompra.click();
        testCase.addScreenShotCurrentStep(u, "consultaOrdenesDeCompra");
        u.hardWait(5);
        patternUtil.runApplet();
        u.hardWait(30);
        // PASO 7
        addStep("Captura el numero de orden de compra");
        assertTrue(patternUtil.validatePattern("ocNumeroDeOcEncontrar"));
        patternUtil.textBoxTypeRight("ocNumeroDeOcEncontrar", 80, ocDesde);
        testCase.addScreenShotCurrentStep("orden de compra");
        u.hardWait(2);
        patternUtil.keyAlt("t");
        u.hardWait(30);
        // PASO 8
        addStep("Se visualiza la OC (orden de compra)");
        testCase.addScreenShotCurrentStep("orden de compra encontrada");
        assertTrue(patternUtil.validatePattern("ocAbrir"));
        patternUtil.clickPattern("ocAbrir", 10 );
        u.hardWait(30);
        // PASO 9
        addStep("Orden de compra encontrada");
        testCase.addScreenShotCurrentStep("OC Encontrada");
        patternUtil.closeWindow();
        u.hardWait(5);
        patternUtil.validatePattern("aceptarWarning");
        patternUtil.keyAlt("a");
        u.close();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_ConsultaDeOrdenesDeCompra_test";
    }

    @Override
    public String setTestDescription() {
        return "Consultar Orden De Compra";
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
        return "Numero de OC (Orden de compra)";
    }

    @Override
    public void beforeTest() {

    }
}
