package EBS.kyribaOxxoInternacional;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_14_ValidarQueExistaElProcesoXxpeKyribaCargaDeEstadosDeCuenta extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_14_ValidarQueExistaElProcesoXxpeKyribaCargaDeEstadosDeCuenta_test(HashMap<String, String> data)
            throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getEbsInternacional;
        String userName = GlobalVariables.getJhernandezHexEbsIntUsr;
        String password = GlobalVariables.getJhernandezHexEbsIntPwd;
        String ebsPattern = data.get("ebsPattern");
        String responsabilidad = data.get("responsabilidad");

        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();

        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

        /*
         * PASOS DEL CASO DE PRUEBA
         */
        //PASO 1 Ingresar a EBS Internacional --> Dirigirse a la responsabilidad PEOXXO-CMXXXXX-SUPER USUARIO --> Otro --> Programas --> Ejecutar --> Solicitud Única --> Aceptar
        addStep("Ingresar a EBS Internacional " + GlobalVariables.getEbsInternacional);
        logger.info("EBS URL: " + ebsUrl);
        System.out.println("EBS URL: " + ebsUrl);
        logger.info("User Name: " + userName);
        System.out.println("User Name: " + userName);
        inicio.loginInternacional();
        u.hardWait(5);
        addStep("Dirigirse a la responsabilidad PEOXXO-CMXXXXX-SUPER USUARIO");
        paginaInicial.clicEnResponsabilidad(responsabilidad);
        addStep("Otro");
        paginaInicial.peCmSuUsOtro.click();
        addStep("Programas");
        paginaInicial.peCmSuUsProgramas.click();
        addStep("Ejecutar");
        paginaInicial.peCmSuUsOtroProgramasEjecutar.click();
        testCase.addScreenShotCurrentStep(u,"peCmSuUsOtroProgramasEjecutar");
        u.hardWait(10);
        patternUtil.runApplet();
        u.hardWait(3);
        patternUtil.runApplet();
        u.hardWait(30);
        patternUtil.windowMaximize();
        addStep("Solicitud Única");
        assertTrue(patternUtil.validatePattern("EBSInternacionalSolicitudUnicaSelected"));
        testCase.addScreenShotCurrentStep("EBSInternacionalSolicitudUnicaSelected");
        addStep("Aceptar");
        patternUtil.clickPattern("EBSInternacionalAceptar", 10);
        //PASO 2 "En el campo 'Nombre' dar clic en '…' --> En el campo 'Buscar' escribir '%Kyriba%'"
        addStep("En el campo 'Nombre' dar clic en '…' --> En el campo 'Buscar' escribir '%Kyriba%'");
        u.hardWait(10);
        patternUtil.textBoxTypeRight("EBSInternacionalNombre", 80, "%Kyriba%");
        u.hardWait(10);
        // Se visualiza el proceso "XXPE- Kyriba carga de estados de cuenta interface"
        testCase.addTextEvidenceCurrentStep(" Se visualiza el proceso 'XXPE- Kyriba carga de estados de cuenta interface'");
        assertTrue(patternUtil.validatePattern("EBSInternacionalKyribaXxpeCargaEstadosDeCuentaInterface"));
        patternUtil.clickPattern("EBSInternacionalKyribaXxpeCargaEstadosDeCuentaInterface", 10);
        testCase.addScreenShotCurrentStep("EBSInternacionalKyribaXxpeCargaEstadosDeCuentaInterface");
//        Cerrar el applet
        u.hardWait(2);
        patternUtil.keyAlt("c");
        u.hardWait(5);
        patternUtil.keyAlt("n");
        u.hardWait(5);
        patternUtil.keyAlt("c");
        u.hardWait(5);
        patternUtil.closeWindow();
        u.hardWait(4);
        assertTrue(patternUtil.validatePattern("EBSInternacionalPrecaucionWindow"));
        patternUtil.keyAlt("a");
//        Cerrar el navegador
        u.close();


    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_14_ValidarQueExistaElProcesoXxpeKyribaCargaDeEstadosDeCuenta_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que exista el proceso 'XXPE- Kyriba carga de estados de cuenta' en EBS INTERNACIONAL";
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
