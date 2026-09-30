package EBS.neteos.Nacionales.reporteGruposEjecucion;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.api.robot.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.SQL.QuerysOracleNacionales;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.neteos.SikuliNeteos.MetodosSikuli;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Creacion de Reporte XXFC - Informe de Grupos de Ejecucion por grupo Martes , Tipo Servicio Nacional.
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-006-Ejecucion Reporte XXFC - Informe de Grupos de Ejecucion por grupo Martes , Tipo Servicio Nacional.
 * <p>
 * PREREQUISITOS:
 * 1. Contar con la responsabilidad "MXOXXO-GL17ADM-SERVICIOS".
 * 2. Existan datos en las tablas xxfc_ar_gpos_ejec_snc_all_h y xxfc_ar_gpos_ejec_snc_all.
 * 3. Acceso al portal web EBS.
 *
 * @author David Quiroz Rodriguez
 * @date 12/Abril/2025
 */
public class ATC_FT_006_EjecReporteXXFCInformeDeGruposDeEjecucionPorGrupoMartesServicioNacional extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_006_EjecReporteXXFCInformeDeGruposDeEjecucionPorGrupoMartesServicioNacional_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String tipoServicio = data.get("tipoServicio");
        String grupoEjecucion = data.get("grupoEjecucion");
        String obtenerValorDeGrupoDeEjecucion = "";
        String ObtenerValorDeItem = "";
        String obtenerValorEncasoDeSerVacio = ""; //Se declara en caso de venir vacio la casilla
        ArrayList<String> gruposDeEjecucionObtenidosDelQuery = new ArrayList<>();
        ArrayList<String> itemsObtenidosDelQuery = new ArrayList<>();
        String path = "\\Patterns\\Locales\\";
        int totalRows;
        String finFechaArchivo = Excel.getFormattedDate();
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
        String queryConsultaReporteCorresponsalias1 = QuerysOracleNacionales.queryConsultaInformesDeEjecucion1(tipoServicio, grupoEjecucion);
        String queryConsultaReporteCorresponsalias2 = QuerysOracleNacionales.queryConsultaInformesDeEjecucion2(tipoServicio, grupoEjecucion);
        System.out.println(queryConsultaReporteCorresponsalias1);
        System.out.println(queryConsultaReporteCorresponsalias2);
        /*Pasos para el caso de prueba
         * */
        //Paso 1
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
        testCase.addScreenShotCurrentStep("Seleccionar el boton de de sombrero ");
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
        addStep("Ingresar parametros para solicitud de reporte XXFC - Informe de Grupos de Ejecucion");
        boolean statusBotonLimpiar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        u.hardWait(3);
        boolean statusGrupoEjecucion = CommonMethodsSikuli.theUserSendTextIn("txtTipoServicio", tipoServicio);
        assertTrue(statusGrupoEjecucion);
        u.hardWait(3);
        boolean statusTipoServicio = CommonMethodsSikuli.theUserSendTextIn("txtGrupoEjecucion", grupoEjecucion);
        assertTrue(statusTipoServicio);
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
        addStep("Extraer la informacion de la base de datos");
        SQLResult validarQuery = dbAvebqa.executeQuery(queryConsultaReporteCorresponsalias1);
        SQLResult validarQuery2 = dbAvebqa.executeQuery(queryConsultaReporteCorresponsalias2);
        if (validarQuery.isEmpty()) {
            testCase.addTextEvidenceCurrentStep("La consulta que se genero viene vacia "); // Agregamos el texto que la query viene vacia
            testCase.addQueryEvidenceCurrentStep(validarQuery); // Agregamos el resultado de la query
            testCase.addTextEvidenceCurrentStep("Revisar las fechas ingresadas, ya no contienen datos"); // Agregamos el texto que la query viene vacia
        } else {
            totalRows = validarQuery.getRowCount(); //Obtenemos el numero total de rows que tiene la consulta
            testCase.addTextEvidenceCurrentStep("Resultado obtenido de la consulta "); //Agregamos esta leyenda en el template de evidencias
            // Agregamos el resultado de la query en el template
            testCase.addQueryEvidenceCurrentStep(validarQuery);
            testCase.addQueryEvidenceCurrentStep(validarQuery2);
            System.out.println("Numero de columnas total " + totalRows);
            for (int inicioRecolecion = 0; inicioRecolecion < totalRows; inicioRecolecion++) {
                obtenerValorDeGrupoDeEjecucion = validarQuery.getData(inicioRecolecion, 0);
                ObtenerValorDeItem = validarQuery.getData(inicioRecolecion, 3);
                if (obtenerValorDeGrupoDeEjecucion == null) { //Se revisa si el valor obtenido es nulo en caso de ser nulo se obtiene la descripcion
                    obtenerValorEncasoDeSerVacio = validarQuery.getData(inicioRecolecion, 4);//Se obtiene la descripcion
                    gruposDeEjecucionObtenidosDelQuery.add(obtenerValorEncasoDeSerVacio); //Se obtiene la descripcion
                } else {
                    gruposDeEjecucionObtenidosDelQuery.add(obtenerValorDeGrupoDeEjecucion); //Se obtiene el numero de lote que se guarda en el arreglo.
                    itemsObtenidosDelQuery.add(ObtenerValorDeItem);
                }
            }
        }
        System.out.println(gruposDeEjecucionObtenidosDelQuery);
        System.out.println(itemsObtenidosDelQuery);
        //Paso 13
        addStep("Validacion de base de datos VS Reporte Historico");
        MTNacionales.abrirReporteHistorialDeGruposEjecucion();
        u.hardWait(15);
        sk.screen.type(Key.ENTER);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Reporte abierto");
        String texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
        System.out.println(texto);
        for (String grpEjecucion : gruposDeEjecucionObtenidosDelQuery) {
            boolean expected = texto.contains(grpEjecucion);
            if (expected == true) {
                testCase.addTextEvidenceCurrentStep("El grupo " + grpEjecucion + " si esta en el reporte");
            } else {
                testCase.addTextEvidenceCurrentStep("El grupo " + grpEjecucion + " no se encuentra en el reporte verificar la consulta de la base de datos");
                System.out.println("El grupo " + grpEjecucion + " no se encuentra en el reporte verificar la consulta de la base de datos");
                System.exit(0);
            }
        }
        for (String item : itemsObtenidosDelQuery) {
            boolean expected = texto.contains(item);
            if (expected == true) {
                testCase.addTextEvidenceCurrentStep("El item " + item + " si esta en el reporte");
            } else {
                testCase.addTextEvidenceCurrentStep("El valor del item  " + item + " no se encuentra en el reporte verificar la consulta de la base de datos");
                System.out.println("El grupo " + item + " no se encuentra en el reporte verificar la consulta de la base de datos");
                System.exit(0);
            }
        }
        // Cerrar reporte
        boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
        assertTrue(statusCerrarReporte);
        //Cerrar aplicacion de java
        u.hardWait(3);
        //Paso 14
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
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_006_EjecReporteXXFCInformeDeGruposDeEjecucionPorGrupoMartesServicioNacional_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se genere Reporte XXFC - Informe de Grupos de Ejecucion por grupo Martes , Tipo Servicio Nacional.";
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
        return "* 1. Contar con la responsabilidad \"MXOXXO-GL17ADM-SERVICIOS\".\n" +
                " * 2. Existan datos en las tablas xxfc_ar_gpos_ejec_snc_all_h y xxfc_ar_gpos_ejec_snc_all.\n" +
                " * 3. Acceso al portal web EBS.";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }
}
