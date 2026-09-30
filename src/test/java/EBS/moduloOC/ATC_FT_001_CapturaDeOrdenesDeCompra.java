package EBS.moduloOC;

import static org.testng.Assert.assertFalse;
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

public class ATC_FT_001_CapturaDeOrdenesDeCompra extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_CapturaDeOrdenesDeCompra_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.get3076719EbsUsr;
        String password = GlobalVariables.get3076719EbsPwd;
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");//"MXOXXO-PO10QRI-Compras";
        String proveedor = data.get("proveedor"); //"MEDINA GARCIA VICTOR MANUEL";
        String descripcion = data.get("descripcion");
        String cr = data.get("cr"); //"31QRO";
        String articulo = data.get("articulo");
        String cantidad = data.get("cantidad");
        String precio = data.get("precio");
        String pactado = data.get("pactado");
        String necesarioPara = data.get("necesarioPara");

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
        u.hardWait(15);
        patternUtil.runApplet();
        u.hardWait(30);
        testCase.addScreenShotCurrentStep("ocPantallaCaptura");
        // PASO 6
        addStep("Ingresar el proveedor");
        assertTrue(patternUtil.validatePattern("ocProveedor"));
        patternUtil.textBoxTypeRight("ocProveedor", 80, proveedor);
        u.hardWait(1);
        // PASO 7
        addStep("Ingresar Descripcion ");
        assertTrue(patternUtil.validatePattern("ocDescripcion"));
        patternUtil.textBoxTypeRight("ocDescripcion", 80, descripcion);
        testCase.addScreenShotCurrentStep("ocDatosProvDesc");
        u.hardWait(2);    
        // PASO 8
        addStep("Ingresar OC CR");
        assertTrue(patternUtil.validatePattern("ocCR"));
        patternUtil.clickPattern("ocCR", 10);
        testCase.addScreenShotCurrentStep("ocCR");
        u.hardWait(1);
        // PASO 9
        addStep("Ingresar el CR");
        assertTrue(patternUtil.validatePattern("ocOracleCR"));
        patternUtil.textBoxTypeRight("ocOracleCR", 80, cr);
        u.hardWait(5);
        assertTrue(patternUtil.validatePattern("ocOracleCrAceptar"));
        testCase.addScreenShotCurrentStep("CR");
        patternUtil.clickPattern("ocOracleCrAceptar", 10);
        u.hardWait(3);
        //testCase.addScreenShotCurrentStep("CR2");
        // PASO 10
        addStep("Ingresar Articulos ");
        assertTrue(patternUtil.validatePattern("ocArticulo"));
        patternUtil.textBoxTypeRight("ocTipoArticulo", 40, articulo);
        u.hardWait(1);
        assertTrue(patternUtil.validatePattern("ocCantidad"));
        patternUtil.clickPattern("ocCantidad", 10);
        patternUtil.enterText(cantidad);
        patternUtil.nTAB(1);
        u.hardWait(1);
        patternUtil.enterText(precio);
        patternUtil.nTAB(1);
        u.hardWait(1);
        patternUtil.enterText(pactado);
        testCase.addScreenShotCurrentStep("ocArticuloDatos1");
        patternUtil.nTAB(1);
        u.hardWait(1);
        patternUtil.enterText(necesarioPara);
        testCase.addScreenShotCurrentStep("ocArticuloDatos2");
        patternUtil.nTAB(1);      
        assertTrue(patternUtil.validatePattern("ocCrLineas"));
        patternUtil.clickPattern("ocCrLineas", 10);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("ocArticuloDatos3");
        patternUtil.keyAlt("a");
        u.hardWait(10);
        // PASO 11
        addStep("Grabar la orden de compra");
        patternUtil.clickPattern("botonGrabar", 10);
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("ocGrabarOrden");
        //Forma 1 copiar texto
        //String ocRev = patternUtil.copyTextBoxRight("numOrdenCompra", 13);
        //Forma 2 copiar texto
        String ocRev = patternUtil.doubleClickAndCopyTextBoxRight("numOrdenCompra", 13);
        /*//Forma 3 copiar texto
        patternUtil.doubleClickPattern("numOrdenCompra1", 10);
        u.hardWait(2);
        patternUtil.keyAlt("e");
        u.hardWait(2);
        patternUtil.nDOWN(2);
        patternUtil.nEnter(1);*/
        u.hardWait(1);      
        testCase.addBoldTextEvidenceCurrentStep("Numero de Orden de Compra: " + ocRev);
        patternUtil.clickPattern("ocAprobar", 10);
        //testCase.addScreenShotCurrentStep("ocAprobarOrden");
        u.hardWait(5);
        
        // PASO 12
        addStep("Desmarcar la opcion de Email ");
        //assertTrue(patternUtil.validatePattern("ocEmailChecked"));
        if(patternUtil.validatePattern("ocEmailChecked")) {
        	patternUtil.keyAlt("m");
            u.hardWait(5);
        }    
        testCase.addScreenShotCurrentStep("ocEmail");
        assertFalse(patternUtil.validatePattern("ocEmailChecked"));
        patternUtil.clickPattern("ocAceptarK", 10);
        u.hardWait(30);
        testCase.addScreenShotCurrentStep("ocAceptar");
        
        // PASO 13
        addStep("Estado de compra ");
        /*
            Es necesario sobreescribir la imagen porque no se mostraba con el estado de aprobado
            La imagen corresponde al estado "incompleto"
            Actualizaci�n: se agrega al codigo los pasos para consultar la orden de compra y poder validar el estatus aprobado
         */        
        patternUtil.closeWindowInsideApplet();
        u.hardWait(10);
        patternUtil.nDOWN(2);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("consultarOC");
        patternUtil.keyAlt("b");
        
        u.hardWait(20);
        assertTrue(patternUtil.validatePattern("ocNumeroDeOcEncontrar"));
        //Forma 1 pegar texto
        patternUtil.textBoxTypeRight("ocNumeroDeOcEncontrar", 80, ocRev);
        //Forma 2 pegar texto
        //patternUtil.textBoxClickAndPasteRight("ocNumeroDeOcEncontrar", 80);
        u.hardWait(1);
        testCase.addScreenShotCurrentStep("orden de compra");
        u.hardWait(3);
        patternUtil.keyAlt("t");
        u.hardWait(30);
        patternUtil.closeWindowInsideApplet();
        u.hardWait(1);
        patternUtil.keyAlt("t");
        u.hardWait(15);
        assertTrue(patternUtil.validatePattern("ocAbrir"));
        patternUtil.clickPattern("ocAbrir", 10 );
        u.hardWait(20);
        
        
        assertTrue(patternUtil.validatePattern("ocEstadoAprobada"));
        testCase.addScreenShotCurrentStep("ocEstadoAprobada");
        patternUtil.closeWindow();
        u.hardWait(5);
        patternUtil.validatePattern("aceptarWarning");
        patternUtil.keyAlt("a");
        u.close();

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_CapturaDeOrdenesDeCompra_test";
    }

    @Override
    public String setTestDescription() {
        return "Realizar una orden de compra";
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
        return "Es Necesaria la resposabilidad de la plaza a probar, el usuario a probar debe estar dado de alta como trabajador";
    }

    @Override
    public void beforeTest() {

    }
}
