package EBS.temasDeTesoreria.cho52080205;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_EjecucionProcesoXXFCCierreMasivoDeInventariosTiendaNEXXO extends BaseExecution {

    @Test(dataProvider = "data-provider")

    public void ATC_FT_001_EjecucionProcesoXXFCCierreMasivoDeInventariosTiendaNEXXO_test(HashMap<String, String> data) throws Exception {

        /*
         * VARIABLES
         */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String responsabilidad = data.get("responsabilidad");
        String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");

        String ebsPattern = data.get("ebsPattern");

        HashMap<String, Pattern> ImgPatter = new HashMap<>();
        ArrayList<Pattern> patterns = new ArrayList<>();
        ArrayList<String> ListImage = new ArrayList<>();
        Pattern pathimg = new Pattern();

        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        SikuliUtil sk = new SikuliUtil();
        LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
        GenericMethods action = new GenericMethods(u);
        String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
        File Folder = new File(pathImg);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

        /*
         * PASOS DEL CASO DE PRUEBA
         */
        // PASO 1
        addStep("Ingresar a EBS AVEBQA a la siguiente ruta:" + ebsUrl);
        inicio.login();
        // PASO 2
        addStep("Ingresar a la Responsabilidad MXOXXO - GL10POZ - USUARIO CTL TIENDAS");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        // PASO 3
        addStep("Ejecución del concurrente: Ver / Solicitudes / Ejecutar una nueva Solicitud / Solicitud Única / Aceptar / 'XXFC-Cierre Masivo de Inventarios Tienda NEXXO'");
        paginaInicial.analisisDeSaldosElement.click();
        u.hardWait(10);
        patternUtil.runApplet();
        u.hardWait(10);
        patternUtil.encontrarSolicitudes();
        u.hardWait(5);
        patternUtil.keyAlt("n");
        patternUtil.typeAndTab(nombreEjecutarSolicitud);
        //if (patternUtil.validatePattern())


        // PASO 4
        addStep("Ingresar los parámetros correspondientes a la Plaza migrada, 10 POZ");
        // PASO 5
        addStep("Validar que se finalice le concurrente sin Error.");
        // PASO 6
        addStep("Validar que se ejecute en cascada el concurrente:XXFC - Reporte Saldo Diario (NEXXO) y finalice sin Error.");
        // PASO 7
        addStep("Validar que se muestren los movimientos y el SALDO DIARIO RESUMEN DE MOVIMIENTOS correspondientes a la Plaza que se migró.");

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_EjecucionProcesoXXFCCierreMasivoDeInventariosTiendaNEXXO_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se tome por el proceso de Cierre de Inventarios la plaza que ya ha sido migrada de POZ a VCZ. Acceso a la responsabilidad: 'MXOXXO-GL10POZ-USUARIO CTL TIENDAS' Acceso a BD de instancia AVEBQA. Contar con Plaza migrada de 10POZ hacia 10VCZ desde CNT.";
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
        return "Acceso a la responsabilidad: 'MXOXXO-GL10POZ-USUARIO CTL TIENDAS' Acceso a BD de instancia AVEBQA. Contar con Plaza migrada de 10POZ hacia 10VCZ desde CNT.";
    }

    @Override
    public void beforeTest() {

    }
}
