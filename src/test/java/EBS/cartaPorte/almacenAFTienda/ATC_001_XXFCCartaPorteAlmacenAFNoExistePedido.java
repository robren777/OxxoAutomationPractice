package EBS.cartaPorte.almacenAFTienda;

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

public class ATC_001_XXFCCartaPorteAlmacenAFNoExistePedido extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Validarlacargacorrectadefacturas_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.getJhernandezHexEbsUsr;
        String password = GlobalVariables.getJhernandezHexEbsPwd;

        String ebsPattern = "EBS\\"+data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");

        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();

        /*
         * UTILERiAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        SikuliUtil sk = new SikuliUtil();
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
        String pathImg = sk.PatternPath(ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

        /*
         * PASOS DEL CASO DE PRUEBA
         */
        // PASO 1
        addStep("Ingresar EBS");
        inicio.login();
        // PASO 2
        addStep("Ingresar a la responsabilidad: MXOXXO-WM17FAA-COORDINADOR EQP FRM");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        testCase.addScreenShotCurrentStep(u, "MXOXXOWM17FAACOORDINADOREQPFRM");
        paginaInicial.clickStockLocators();
        testCase.addScreenShotCurrentStep(u, "clickStockLocators");
        u.hardWait(20);
        patternUtil.runApplet();
        u.hardWait(20);
        assertTrue(patternUtil.validatePattern("encontrarLocalizadores"));
        testCase.addScreenShotCurrentStep("encontrarLocalizadores");
        assertTrue(patternUtil.validatePattern("ver"));
        patternUtil.clickPattern("ver", 10);
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("solicitudes"));
        patternUtil.clickPattern("solicitudes", 10);
        u.hardWait(20);
        assertTrue(patternUtil.validatePattern("encontrarSolicitudes"));
        testCase.addScreenShotCurrentStep("solicitudes");
        assertTrue(patternUtil.validatePattern("ejecutarNuevaSolicitudButton"));
        patternUtil.clickPattern("ejecutarNuevaSolicitudButton", 10);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("ejecutarNuevaSolicitudButton");
        // PASO 3
        addStep("Ir a solicitudes y buscar concurrente: XXFC-Carta Porte Almac�n AF Tienda (ingresar datos y ejecutar)");
        assertTrue(patternUtil.validatePattern("nombreTextBoxRequiered"));
        patternUtil.textBoxTypeRight("nombreTextBoxRequiered", 80, "XXFC-Carta Porte Almacen AF");
        testCase.addScreenShotCurrentStep("nombreTextBoxRequiered");
        u.hardWait(2);
        assertTrue(patternUtil.validatePattern("parametrosXXFCCartaPorteAlmacenAF"));
        testCase.addScreenShotCurrentStep("parametrosXXFCCartaPorteAlmacenAF");
        assertTrue(patternUtil.validatePattern("noPedidoXXFCCartaPorteAlmacenAF"));
        patternUtil.textBoxTypeRight("noPedidoXXFCCartaPorteAlmacenAF", 80, "10009");
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("ubicacionOrigenXXFCCartaPorteAlmacenAF"));
        patternUtil.textBoxTypeRight("ubicacionOrigenXXFCCartaPorteAlmacenAF", 80, "50FAA");
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("fechaHoraDeSalidaXXFCCartaPorteAlmacenAF"));
        patternUtil.textBoxTypeRight("fechaHoraDeSalidaXXFCCartaPorteAlmacenAF", 80, "23-nov-2021");
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("distamciaRecorridaXXFCCartaPorteAlmacenAF"));
        patternUtil.textBoxTypeRight("distamciaRecorridaXXFCCartaPorteAlmacenAF", 80, "700");
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("emailsXXFCCartaPorteAlmacenAF"));
        patternUtil.textBoxTypeRight("emailsXXFCCartaPorteAlmacenAF", 80, "hexaware18@oxxo.com");
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("aceptararchivo"));
        testCase.addScreenShotCurrentStep("aceptararchivo");
        patternUtil.clickPattern("aceptararchivo", 10);
        u.hardWait(2);
        assertTrue(patternUtil.validatePattern("ejecutar"));
        patternUtil.clickPattern("ejecutar", 10);
        u.hardWait(5);
        patternUtil.keyAlt("n");
        u.hardWait(5);
        assertTrue(patternUtil.validatePattern("encontrarSolicitudes"));
        assertTrue(patternUtil.validatePattern("encontrar"));
        patternUtil.clickPattern("encontrar", 10);
        u.hardWait(3);
        // PASO 4
        addStep("Validar Output del concurrente");
        assertTrue(patternUtil.validatePattern("visualizaroutput"));
        patternUtil.clickPattern("visualizaroutput", 10);
        u.hardWait(8);
        testCase.addScreenShotCurrentStep("output");
        String text = patternUtil.getTextDelNavegador("programaXXFCCartaPorteAlmacenAFTienda", "No existe informacion del Pedido");
        System.out.println(text);
        assertEquals(text, "No existe informacion del Pedido");
        patternUtil.closeWindow();
        u.hardWait(10);
        assertTrue(patternUtil.validatePattern("OracleLogo"));
        patternUtil.clickPattern("OracleLogo", 10);
        u.hardWait(1);
        patternUtil.closeWindow();
        u.hardWait(5);
        assertTrue(patternUtil.validatePattern("deseaSalir"));
        patternUtil.clickPattern("deseaSalir", 10);
        assertTrue(patternUtil.validatePattern("aceptarcerrar"));
        patternUtil.clickPattern("aceptarcerrar", 10);
        u.close();

    }

    @Override
    public String setTestFullName() {
        return null;
    }

    @Override
    public String setTestDescription() {
        return "Se intenta procesar un pedido que no existe";
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
        return "Datos de un pedido inexistente";
    }

    @Override
    public void beforeTest() {

    }
}
