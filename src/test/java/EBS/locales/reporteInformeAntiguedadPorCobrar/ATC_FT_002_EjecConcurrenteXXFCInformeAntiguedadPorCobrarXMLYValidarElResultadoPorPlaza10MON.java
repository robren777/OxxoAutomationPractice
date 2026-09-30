package EBS.locales.reporteInformeAntiguedadPorCobrar;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.api.robot.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCI�N: Clase de utileria para generar informe de Antiguedad Por Cobrador XML y validar el resultado-REPORTE POR PLAZA 10 MON
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC_FT_002_Ejecución concurrente: XXFC-Informe Antiguedad Por Cobrador XML y validar el resultado-REPORTE POR PLAZA 10MON
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Usar Microsoft Edge en modo Internet Explorer
 * <p>
 * INSUMOS:
 * - Tener datos de informe de Antiguedad Por Cobrador XML en la responsabilidad 10MON en base de datos
 *
 * @author David Quiroz Rodriguez
 * @date 29/Enero/2025
 */

public class ATC_FT_002_EjecConcurrenteXXFCInformeAntiguedadPorCobrarXMLYValidarElResultadoPorPlaza10MON extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_EjecConcurrenteXXFCInformeAntiguedadPorCobrarXMLYValidarElResultadoPorPlaza10MON_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        //
        String nivelInformacion = data.get("nivelInformacion");
        String reportingContext = data.get("reportingContext");
        String resumenInforme = data.get("resumenInforme");
        String formatoInforme = data.get("formatoInforme");
        String aPartirDe = data.get("aPartirDe");
        String nombreDeBloqueoDeAntiguedad = data.get("nombreDeBloqueoDeAntiguedad");
        String mostrarEnCuenta = data.get("mostrarEnCuenta");
        String tipoFacturaInferior = data.get("tipoFacturaInferior");
        String tipoFacturaSuperior = data.get("tipoFacturaSuperior");
        String mostrarRecibosEnRiesgo = data.get("mostrarRecibosEnRiesgo");
        String mostrarReclamos = data.get("mostrarReclamos");

        String finFechaArchivo = Excel.getFormattedDate();
        //Arrays para validaciones
        ArrayList<String> valoresObtenidosDeQuerySQL = new ArrayList<>();
        //Archivo Reporte
        String reporteAValidar = System.getProperty("user.home") + "\\Downloads\\XXFC_Informe_Antigüedad_Por_C_" + finFechaArchivo + ".xls";

        String path = "\\Patterns\\Locales\\";
        /*Utilerias
         * */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        metodosLocales MTLocales = new metodosLocales(u, testCase, ebsUrl, userName, password);
        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("EBS\\"+"locales");
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);
        MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
        PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
        /*Utilerias SQL
         * */
        SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
                GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
        /*Variables SQL
         */
        //Paso 1
        addStep(" Ingresar a la Url de EBS y generar login");
        MTLocales.LoginIEEBS();
        u.hardWait(15);
        //Paso 2
        addStep("Seleccionar carpeta responsabilidad y app javaServerlet en pagina EBS");
        MTLocales.theUserSelectsTheValueOfTheEBSPage(carpetaResponsabilidad); //Seleccionamos la responsabilidad
        MTLocales.theUserSelectTheAppJavaUsuarios(carpetaResponsabilidad); // Se selecciona la app Java de la carpeta responsabilidad
        //Paso 3
        addStep("Abrir el appserveler");
        //Run app Serverlet java
        CommonMethodsSikuli.theUserWaitsToApproveAndRunApplication("checkboxIAcceptTheRiskAndWantToRunThisApplication", "buttonRun");
        testCase.addScreenShotCurrentStep("Abriendo la aplicacion java");
        //Paso 4
        addStep("Cambio de responsabilidad");
        boolean statusSeccionUsuarios = CommonMethodsSikuli.waitForImage("seccionUsuarios.png", path);
        assertTrue(statusSeccionUsuarios);
        testCase.addScreenShotCurrentStep("Visualizar apartado Usuarios");
        boolean statusCerrarSesionUsuarios = CommonMethodsSikuli.theUserClicksOn("btnCerrarApartadoUsuarios");
        assertTrue(statusCerrarSesionUsuarios);
        testCase.addScreenShotCurrentStep("Cerrar apartado Usuarios");
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Seleccionar el boton de cambio de responsabilidad ");
        boolean statusCambiarResponsabilidad = CommonMethodsSikuli.theUserClicksOn("cambiarResponsabilidad");
        assertTrue(statusCambiarResponsabilidad);
        testCase.addScreenShotCurrentStep("Seleccionar Responsabilidad");
        boolean statusSeleccionarResponsabilidad = CommonMethodsSikuli.theUserSendTextIn("txtIngresarResponsabilidad", cambioResponsabilidad);
        assertTrue(statusSeleccionarResponsabilidad);
        testCase.addScreenShotCurrentStep("Ingresar Responsabilidad");
        u.hardWait(3);
        sk.screen.type(Key.ENTER);
        u.hardWait(3);
        //Paso 5
        addStep("Ir a solicitudes");
        testCase.addScreenShotCurrentStep("Ir al menu Ver");
        boolean statusMenuVer = CommonMethodsSikuli.theUserClicksOn("optionMenuVer");
        assertTrue(statusMenuVer);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Seleccionar solicitudes");
        boolean statusSeleccionarSolicitudes = CommonMethodsSikuli.theUserClicksOn("optionSolicitudes_Ver");
        assertTrue(statusSeleccionarSolicitudes);
        u.hardWait(7);
        //Paso 6
        addStep("Generar una nueva solicitud");
        testCase.addScreenShotCurrentStep("Seleccionar boton Ejecutar Nueva Solicitud");
        boolean statusGenerarNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("EjecutarNuevaSolicitud");
        assertTrue(statusGenerarNuevaSolicitud);
        u.hardWait(4);
        boolean statusIngresarNombreReporte = CommonMethodsSikuli.theUserClicksOn("Nombre_EjecutarEstaSolicitud");
        assertTrue(statusIngresarNombreReporte);
        sk.screen.paste(nombreReporte);
        testCase.addScreenShotCurrentStep("Se ingresa nombre de reporte");
        u.hardWait(2);
        sk.screen.type(Key.TAB);
        u.hardWait(7);
        sk.screen.type(Key.ENTER);
        //Paso 7
        addStep("Ingresar parametros para solicitud");
        u.hardWait(7);
        boolean statusBotonLimpiar=CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        boolean statusNivelInformacion = CommonMethodsSikuli.theUserSendTextIn("inputNivelInformacion", nivelInformacion);
        assertTrue(statusNivelInformacion);
        sk.screen.type(Key.TAB);
        u.hardWait(3);
        boolean statusReportingContext = CommonMethodsSikuli.theUserSendTextIn("inputReportingContext", reportingContext);
        assertTrue(statusReportingContext);
        boolean statusResumenInforme = CommonMethodsSikuli.theUserSendTextIn("inputResumenInforme", resumenInforme);
        assertTrue(statusResumenInforme);
        boolean statusFormatoInforme=CommonMethodsSikuli.theUserSendTextIn("inputFormatoInforme",formatoInforme);
        assertTrue(statusFormatoInforme);
        boolean statusAPartirDe=CommonMethodsSikuli.theUserSendTextIn("inputApartirDe",aPartirDe);
        assertTrue(statusAPartirDe);
        boolean statusNombreDeBloqueoDeAntiguedad=CommonMethodsSikuli.theUserSendTextIn("inputNombreDeBloqueDeAntiguedad",nombreDeBloqueoDeAntiguedad);
        assertTrue(statusNombreDeBloqueoDeAntiguedad);
        boolean statusMostrarEnCuenta=CommonMethodsSikuli.theUserClicksOn("inputMostrarEnCuenta");
        assertTrue(statusMostrarEnCuenta);
        sk.screen.paste(mostrarEnCuenta);
        testCase.addScreenShotCurrentStep("Se ingresaron los primero parametros y se damos scroll");
        for(int inicio=0; inicio<8; inicio++) {
            CommonMethodsSikuli.theUserClicksOn("scrollDownParametosInformeAntiguedad");
        }
        boolean statusTipoFacturaInferior=CommonMethodsSikuli.theUserSendTextIn("inputTipoFacturaInferior",tipoFacturaInferior);
        assertTrue(statusTipoFacturaInferior);
        boolean statusTipoFacturaSuperior=CommonMethodsSikuli.theUserSendTextIn("inputTipoFacturaSuperior",tipoFacturaSuperior);
        assertTrue(statusTipoFacturaSuperior);
        boolean statusMostrarRecibosEnRiesgo=CommonMethodsSikuli.theUserSendTextIn("inputMostrarRecibosEnRiesgo",mostrarRecibosEnRiesgo);
        assertTrue(statusMostrarRecibosEnRiesgo);
        boolean statusMostrarReclamos=CommonMethodsSikuli.theUserSendTextIn("inputMostrarReclamos",mostrarReclamos);
        assertTrue(statusMostrarReclamos);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusAceptarParametros = CommonMethodsSikuli.theUserClicksOn("botonAceptarParametros");
        assertTrue(statusAceptarParametros);
        u.hardWait(3);
        //Paso 8
        addStep("Ejecutar Solicitud");
        boolean statusEjecutarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
        assertTrue(statusEjecutarSolicitud);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
        boolean statusClickBotonNo = CommonMethodsSikuli.theUserClicksOn("optionNo");
        assertTrue(statusClickBotonNo);
        u.hardWait(5);
        //Paso 9
        addStep("Encontrar Solicitud");
        testCase.addScreenShotCurrentStep("Selecionar boton encontrar");
        boolean statusBotonEncontrarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
        assertTrue(statusBotonEncontrarSolicitud);
        u.hardWait(15);
        //Paso 10
        addStep("Esperar a que se ejecute reporte");
        testCase.addScreenShotCurrentStep("El Reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        sk.screen.keyUp();
        //Paso 11
        addStep("Visualizar Output");
        boolean statusVisualizarOutput = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
        assertTrue(statusVisualizarOutput);
        u.hardWait(30);
        testCase.addScreenShotCurrentStep("Imagen del reporte descargado");
        //Paso 12
        addStep("Abrir reporte descargado");
        //Abrir reporte
        boolean statusAbrirReporte = CommonMethodsSikuli.theUserClicksOn("btnLinkAbrirReporte");
        assertTrue(statusAbrirReporte);
        u.hardWait(15);
        sk.screen.type(Key.ENTER);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Reporte abierto");
        sk.screen.click();
        for(int i=0; i <7; i++){
            patternUtil.nDOWN(50); //Cambiar a 2 cuando sea una ejecucion formal
            testCase.addScreenShotCurrentStep("Captura de informacion "+ i );
        }
        u.hardWait(10);
        boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
        assertTrue(statusCerrarReporte);
        // Cerrar reporte

        //Regresar a la aplicacion java
        boolean statusClickAppJava = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusClickAppJava);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java");
        System.out.println("Se Cambia al serverlet de java");
        u.hardWait(5);
        // PASO 13
        u.hardWait(3);
        addStep("Cerrar Oracle");
        u.hardWait(3);
        boolean statusCerrarOracle = CommonMethodsSikuli.theUserClicksOn("btnCerrarAppJava");
        assertTrue(statusCerrarOracle);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Cerrar aplicacion Java");
        boolean statusAceptarSalirOracleApplications = CommonMethodsSikuli.theUserClicksOn("botonAceptarSalirOracleApplications");
        assertTrue(statusAceptarSalirOracleApplications);
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
        Excel.deleteFile(reporteAValidar);
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_EjecConcurrenteXXFCInformeAntiguedadPorCobrarXMLYValidarElResultadoPorPlaza10MON_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se genere informe de Antiguedad Por Cobrador XML y validar el resultado-REPORTE POR PLAZA 10 MON";
    }

    @Override
    public String setTestDesigner() {
        return "davidq1@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Contar con ambientacion en BD para los registros de reporte";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }
}