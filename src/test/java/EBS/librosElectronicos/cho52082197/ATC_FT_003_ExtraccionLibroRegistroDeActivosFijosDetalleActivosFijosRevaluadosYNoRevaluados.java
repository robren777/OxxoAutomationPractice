package EBS.librosElectronicos.cho52082197;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialInternacional;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ExtraccionLibroRegistroDeActivosFijosDetalleActivosFijosRevaluadosYNoRevaluados extends BaseExecution {
    @Test(dataProvider = "data-provider")

    public void ATC_FT_003_ExtraccionLibroRegistroDeActivosFijosDetalleActivosFijosRevaluadosYNoRevaluados_test(
            HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String responsabilidad = data.get("responsabilidad");
        String carpeta = data.get("carpeta");
        String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");

        String ebsPattern = data.get("ebsPattern");

        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        ArrayList<String> ListImage = new ArrayList<String>();
        Pattern pathimg = new Pattern();

        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        SikuliUtil sk = new SikuliUtil();
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicialInternacional paginaInicial = new EBSPaginaInicialInternacional(u, testCase);
        GenericMethods action = new GenericMethods(u);
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

        /*
         * PASOS DEL CASO DE PRUEBA
         */
        // PASO 1
        addStep("Ingresar al ambiente de EBS.");
        // PASO 2
        addStep("Seleccionar idioma: Espanol Latinoamericano.");
        // PASO 3
        addStep("Ingresar usuario y contrasena.");
        // PASO 4
        addStep("Dar clic en el boton de 'Conectese'.");
        inicio.loginInternacional();
        paginaInicial.setLanguage("Latin American Spanish");
        paginaInicial.home();

    }

    @Override
    public String setTestFullName() {
        return null;
    }

    @Override
    public String setTestDescription() {
        return null;
    }

    @Override
    public String setTestDesigner() {
        return null;
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
