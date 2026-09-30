package EBS.neteos.Nacionales.InformeNoFacturadoEnTransito;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.IO.Excel;
import EBS.util.neteos.SikuliNeteos.MetodosSikuli;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Creacion de Reporte XXFC - Servicios nacionales y Corresponsalias por servicio en un rango de fechas.
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-007-Ejecución XXFC - Reporte de Información en tránsito Servicios Nacionales y Corresponsalías - Nacional por servicio en un rango de fechas.
 * <p>
 * PREREQUISITOS:
 * 1. Contar con la responsabilidad "MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS".
 * 2. Existan datos en las tablas xxfc.xxfc_pago_servicios y APPS.XXFC_PAGO_SERVICIOS_PRE
 * 3. Acceso al portal web EBS.
 *
 * @author Abraham Giovanni Nuñez Bautista
 * @date 21/Mayo/2025
 */
public class ATC_FT_007_EjecXXFCRepoDeInfoEnTransServNacyCoCorresponNacPorServicioEnUnRangoDeFechas extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_007_EjecXXFCRepoDeInfoEnTransServNacyCoCorresponNacPorServicioEnUnRangoDeFechas_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String tipoServicio = data.get("tipoServicio");
        String servicio = data.get("servicio");
        String fechaActualizacionInicio = data.get("fechaActualizacionInicio");
        String fechaActualizacionFin = data.get("fechaActualizacionFin");
        String grupoEjecucion =data.get("grupoEjecucion");
        String obtenerValorDeGrupoDeEjecucion = "";
        String ObtenerValorDeItem = "";
        String obtenerValorEncasoDeSerVacio = ""; //Se declara en caso de venir vacio la casilla
        ArrayList<String> gruposDeEjecucionObtenidosDelQuery = new ArrayList<>();
        ArrayList<String> itemsObtenidosDelQuery = new ArrayList<>();
        String path = "\\Patterns\\Locales\\";
        String finFechaArchivo = Excel.getFormattedDate();
        String fechaInicio=Excel.convertDateFormat(fechaActualizacionInicio);
        String fechaFin=Excel.convertDateFormat(fechaActualizacionFin);
        System.out.println(fechaInicio);
        System.out.println(fechaFin);
        int totalRows;
        finFechaArchivo = Excel.getFormattedDate();
        String reporteAValidar = System.getProperty("user.home") + "\\Downloads\\XXFC___Informe_de_Grupos_de_Ej_" + finFechaArchivo + ".xls";
        /*Utilerias
         * */
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        metodosLocales MTLocales = new metodosLocales(u, testCase, ebsUrl, userName, password);
        metodosNacionales MTNacionales = new metodosNacionales(u, testCase);
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

        /*Pasos para el caso de prueba
         * */
        //Paso 1
        /*
        addStep(" Ingresar a la Url de EBS y generar login");
        MTLocales.LoginIEEBS();
        u.hardWait(15);
        //Paso 2
        addStep("Seleccionar carpeta responsabilidad y app javaServerlet en pagina EBS");
        MTLocales.theUserSelectsTheValueOfTheEBSPage(carpetaResponsabilidad); //Seleccionamos la responsabilidad
        MTLocales.theUserSelectTheAppJavaUsuarios(carpetaResponsabilidad); // Se selecciona la app Java de la carpeta responsabilidad
        u.hardWait(5);
        MTNacionales.abrirServerletDeJavaDesdeDescargas();
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
        testCase.addScreenShotCurrentStep("Seleccionar el boton de sombrero ");
        boolean statusCambiarResponsabilidad = CommonMethodsSikuli.theUserClicksOn("cambiarResponsabilidad");
        assertTrue(statusCambiarResponsabilidad);
        testCase.addScreenShotCurrentStep("Seleccionar Responsabilidad");
        boolean statusSeleccionarResponsabilidad = CommonMethodsSikuli.theUserSendTextIn("txtIngresarResponsabilidad", cambioResponsabilidad);
        assertTrue(statusSeleccionarResponsabilidad);
        testCase.addScreenShotCurrentStep("Ingresar Responsabilidad");
        u.hardWait(3);
        sk.screen.type(org.sikuli.script.Key.ENTER);
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
        boolean statusIngresarNombreReporte = CommonMethodsSikuli.theUserSendTextIn("Nombre_EjecutarEstaSolicitud", nombreReporte);
        assertTrue(statusIngresarNombreReporte);
        testCase.addScreenShotCurrentStep("Se ingresa nombre de reporte");
        u.hardWait(2);
        sk.screen.type(org.sikuli.script.Key.TAB);
        u.hardWait(3);
        //Paso 7 Ingresar parametros
        addStep("Ingresar parametros para solicitud de reporte XXFC - Reporte de Información en tránsito Servicios Nacionales y Corresponsalías");
        boolean statusBotonLimpiar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        u.hardWait(3);
        boolean statusTipoServicio = CommonMethodsSikuli.theUserSendValueIn("TxtTipoServicio", tipoServicio, "InformeNoFacturadoEnTransito");
        assertTrue(statusTipoServicio);
        u.hardWait(3);
        boolean statusservicio = CommonMethodsSikuli.theUserSendValueIn("TxtServicio", servicio, "InformeNoFacturadoEnTransito");
        assertTrue(statusservicio);
        u.hardWait(3);
        boolean fechaDesde = CommonMethodsSikuli.theUserSendValueIn("TxtFechaDesde", fechaActualizacionInicio, "InformeNoFacturadoEnTransito");
        assertTrue(fechaDesde);
        u.hardWait(3);
        boolean fechaHasta = CommonMethodsSikuli.theUserSendValueIn("TxtFechaHasta", fechaActualizacionFin, "InformeNoFacturadoEnTransito");
        assertTrue(fechaHasta);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusAceptarParametrosDeReporteNoFacturado = CommonMethodsSikuli.theUserClicksOn("botonAceptarParametros");
        assertTrue(statusAceptarParametrosDeReporteNoFacturado);
        u.hardWait(3);
        //Paso 8
        addStep("Ejecutar la nueva Solicitud");
        boolean statusEjecutarNuevaSolicitudNoFacturado = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
        assertTrue(statusEjecutarNuevaSolicitudNoFacturado);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
        boolean statusClickBotonNoPorNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("optionNo");
        assertTrue(statusClickBotonNoPorNuevaSolicitud);
        u.hardWait(5);
        //Paso 9
        addStep("Encontrar Solicitud");
        testCase.addScreenShotCurrentStep("Selecionar boton encontrar");
        boolean statusBotonEncontrarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
        assertTrue(statusBotonEncontrarSolicitud);
        u.hardWait(15);
        //Paso 10
        addStep("Esperar a que se ejecute el reporte ");
        testCase.addScreenShotCurrentStep("El nuevo reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        //Paso 11
        addStep("Visualizar Output del reporte");
        boolean statusVisualizarOutputNoFacturado = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
        assertTrue(statusVisualizarOutputNoFacturado);
        u.hardWait(30);
        testCase.addScreenShotCurrentStep("Imagen del reporte descargado");
        //Paso 12
        addStep("Validacion de datos del reporte XXFC - Reporte de Información en tránsito Servicios Nacionales y Corresponsalías");
        MTNacionales.abrirReportesEnGeneralXXFC();
        u.hardWait(15);
        sk.screen.type(Key.ENTER);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Reporte abierto");
        String texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
        System.out.println(texto);
        boolean tipoServicioT = texto.contains(tipoServicio);
        if (tipoServicioT == true) {
            testCase.addTextEvidenceCurrentStep("El valor de tipo de servicio " + tipoServicio + " si esta en el reporte");
        } else {
            testCase.addTextEvidenceCurrentStep("El valor de tipo de servicio " + tipoServicio + " no se encuentra en el reporte verificar la consulta de la base de datos");
            System.out.println("El valor de tipo de servicio " + tipoServicio + " no se encuentra en el reporte verificar la consulta de la base de datos");
            System.exit(0);
        }
        boolean fchInicio = texto.contains(fechaInicio);
        if (fchInicio == true) {
            testCase.addTextEvidenceCurrentStep("El valor de fecha de inicio " + fechaInicio + " si esta en el reporte");
        } else {
            testCase.addTextEvidenceCurrentStep("El valor de fecha de inicio " + fechaInicio +  " no se encuentra en el reporte verificar la consulta de la base de datos");
            System.out.println("El valor de fecha de inicio " + fechaInicio +  " no se encuentra en el reporte verificar la consulta de la base de datos");
            System.exit(0);
        }
        boolean fchFin = texto.contains(fechaFin);
        if (fchFin == true) {
            testCase.addTextEvidenceCurrentStep("El valor de fecha de inicio " + fechaFin + " si esta en el reporte");
        } else {
            testCase.addTextEvidenceCurrentStep("El valor de fecha de inicio " + fechaFin +  " no se encuentra en el reporte verificar la consulta de la base de datos");
            System.out.println("El valor de fecha de inicio " + fechaInicio +  " no se encuentra en el reporte verificar la consulta de la base de datos");
            System.exit(0);
        }
        // Cerrar reporte
        boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
        assertTrue(statusCerrarReporte);
        //Cerrar aplicacion de java
        u.hardWait(3);
        //Paso 13
        addStep("Cerrar Oracle");
        //Cambiarte al aplicativo de Java
        boolean statusRegresarpJava2 = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusRegresarpJava2);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java para cerrar el aplicativo");
        System.out.println("Se Cambia al serverlet de java");
        u.hardWait(5);
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
        */
        addStep("ingresar EBS");
        addStep("Ejecutar concurrente");

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_007_EjecXXFCRepoDeInfoEnTransServNacyCoCorresponNacPorServicioEnUnRangoDeFechas_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se genere Reporte XXFC - Informe  Nacional por servicio en un rango de fechas.";
    }

    @Override
    public String setTestDesigner() {
        return "abrahamgiovannin@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return " - Acceso al portal web EBS.\n" +
                " - Usar Microsoft Edge en modo Internet Explorer.\n" +
                " - Existan datos historicos de los grupos de ejecucion XXFC_AP_GPOS_SNC_HIST_ALL";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }
}
