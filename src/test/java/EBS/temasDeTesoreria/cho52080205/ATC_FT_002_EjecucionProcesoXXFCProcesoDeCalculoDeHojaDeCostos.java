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

public class ATC_FT_002_EjecucionProcesoXXFCProcesoDeCalculoDeHojaDeCostos extends BaseExecution {

    @Test(dataProvider = "data-provider")

    public void ATC_FT_002_EjecucionProcesoXXFCProcesoDeCalculoDeHojaDeCostos_test(HashMap<String, String> data) throws Exception {

        /*
         * VARIABLES
         */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String responsabilidad = data.get("responsabilidad");
        String carpeta = data.get("carpeta");
        String nombreEjecutarSolicitud = data.get("nombreEjecutarSolicitud");
        String juegoDeLibros = data.get("juegoDeLibros");
        String entidadLegal = data.get("entidadLegal");

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
        addStep("Ingresar a la Responsabilidad MXOXXO - GL10POZ - USUARIO CTL TIENDAS");
        inicio.login();
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        // PASO 2
        addStep("Ejecución del concurrente: Ver / Solicitudes / Ejecutar una nueva Solicitud / Solicitud Única / Aceptar / 'XXFC-Proceso de Cálculo de Hoja de Costos.'");
        // PASO 3
        addStep("Ingresar los parámetros correspondientes a la Plaza migrada, 10 POZ");
        // PASO 4
        addStep("Validar que se finalice le concurrente sin Error.");
        // PASO 5
        addStep("Validar que se ejecute el proceso de Importación de Asientos y muestre movimientos de la Plaza que se migró.");
        // PASO 6
        addStep("Validar que se ejecute el proceso XXFC-Reporte de Hoja de Costos y muestre movimientos de la Plaza que se migró.");
        // PASO 7
        addStep("Validar que se ejecute el proceso XXFC - Reporte Comparativo de Hoja de Costos - Contabilidad y muestre movimientos de la Plaza que se migró.");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_EjecucionProcesoXXFCProcesoDeCalculoDeHojaDeCostos_test";
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
