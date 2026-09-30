package WM.EvolucionRetiroSinTarjeta.HU12;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * Configuracion de lookups de XXFC_MPC_METODO_PAGO en EBS
 * Desc:
 * Validar la configuracion de lookups de XXFC_MPC_METODO_PAGO en EBS
 * @author Miguel Escamilla
 * @date 25/01/2024
 **/
public class ATC_FT_001_Validar_configuracion_de_lookups_de_XXFC_MPC_METODO_PAGO_en_EBS extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Validar_configuracion_de_lookups_de_XXFC_MPC_METODO_PAGO_en_EBS(HashMap<String, String> data) throws Exception {
    	 
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.userMiguelEscamilla;
        String password = GlobalVariables.pwdMiguelEscamilla;


        /*
         * Utilerias
         *********************************************************************/

        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);

        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);

        MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");
        addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");
        inicio.login();
        testCase.addScreenShotCurrentStep(u, "Portal de EBS");

        System.out.println(
                "***************************************** PASO 3 y 4 ************************************************");

        addStep("Ingresar a la responsabilidad de Application Developer y luego a las carpetas /Application/Lookups/ y después abrir Common para que se descargue el aplicativo frmservlet");

        paginaInicial.clickOnResponsabilidad("Desarrollador de Aplicaciones");
        paginaInicial.clickOnResponsabilidad("Aplicación");
        paginaInicial.clickOnResponsabilidad("Consultas");


        testCase.addScreenShotCurrentStep(u, "Common");
        paginaInicial.clickOnResponsabilidad("Común");

        System.out.println(
                "***************************************** PASO 5 ************************************************");

        addStep("Ejecutar el aplicativo frmservlet desde la carpeta de Descargas");
        patternUtil.runApplet();


        testCase.addScreenShotCurrentStep("Applicativo frmservlet");

        System.out.println(
                "***************************************** PASO 6 y 7 ************************************************");

        addStep("En el campo de Find debemos buscar las configuraciones de XXFC_MPC_METODO_PAGO y darle clic en el botón Find");
        p.lamparaBusqueda();
        testCase.addScreenShotCurrentStep("Busqueda");
        p.buscarNombreSolicitud("XXFC_MPC_METODO_PAGO");


        for (int i = 0; i <= 13; i++) {

            patternUtil.doubleClickPattern("ocDownArrow", 10);
        }

        addStep("En la ventana XXFC_MPC_METODO_PAGO, debemos buscar los campos Code y MEANING con los siguientes valores configurados.\n" +
                "Code = 'DB', MEANING ='Tarjeta de débito' \n" +
                "Code = 'CR', MEANING ='Tarjeta de crédito'");
        testCase.addScreenShotCurrentStep("Metodos de Pago");

        System.out.println("***********************Se sale de oracle*************************");

        p.cerrarApplet();
        // cierra los navegadores
        u.quit();

    }

    @Override
    public String setTestFullName() {
        return "001 - Validar configuración de lookups de XXFC_MPC_METODO_PAGO en EBS";
    }

    @Override
    public String setTestDescription() {
        return "Validar configuración de lookups de XXFC_MPC_METODO_PAGO en EBS";
    }

    @Override
    public String setTestDesigner() {
        return "Miguel Angel Escamilla Reyes";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Acceso a Portal EBS";
    }

    @Override
    public void beforeTest() {

    }
}
