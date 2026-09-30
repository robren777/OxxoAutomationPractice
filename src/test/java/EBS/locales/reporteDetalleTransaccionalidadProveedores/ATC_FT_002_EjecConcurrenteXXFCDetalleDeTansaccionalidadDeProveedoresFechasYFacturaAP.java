package EBS.locales.reporteDetalleTransaccionalidadProveedores;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.SQL.QuerysOracleLocales;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para generar reporte de Detalle de transaccionalidad de proveedores por fechas y  Factura AP
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_002_Ejecutar Concurrente XXFC - Detalle de transaccionalidad de proveedores por fechas y Factura AP
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS.
 * - Usar Microsoft Edge en modo Internet Explorer.
 * - Tener responsabilidad GL a nivel plaza.
 * - Deben existir transacciones de servicios locales en la tabla de pago de servicios.
 * - Fecha inicio y fin son obligatorias.
 * INSUMOS:
 * - Tener datos de transaccionalidad de proveedores locales en Base de Datos
 *
 * @author David Quiroz Rodriguez
 * @date 22/Enero/2025
 */

public class ATC_FT_002_EjecConcurrenteXXFCDetalleDeTansaccionalidadDeProveedoresFechasYFacturaAP extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_EjecConcurrenteXXFCDetalleDeTansaccionalidadDeProveedoresFechasYFacturaAP_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String facturaAP = data.get("facturaAP");
        String fechaInicialAdministrativa = data.get("fechaInicialAdministrativa");
        String fechaFinalAdministrativa = data.get("fechaFinalAdministrativa");
        String finFechaArchivo = Excel.getFormattedDate();
        String obtenerValorDeCasislla = ""; // Se declara para obtener los datos de lotes para generar una validacion
        String obtenerValorEncasoDeSerVacio = ""; //Se declara para que en caso de que no se encuentre en lote tomar otro valor
        String fechaInicialConsultaQuery = data.get("fechaInicialConsultaQuery");
        String fechaFinalConsultaQuery = data.get("fechaFinalConsultaQuery");
        String plaza = data.get("plaza");
        int totalRows;
        //Arrays para script
        ArrayList<String> valoresObtenidosDeQuerySQL = new ArrayList<>();
        //Archivo Reporte
        String reporteAValidar = System.getProperty("user.home") + "\\Downloads\\XXFC___Detalle_de_transacciona_" + finFechaArchivo + ".xls";

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
        String Query1 = QuerysOracleLocales.queryReporteDetalleTransaccionalidadFechasFacturaAP1(fechaInicialConsultaQuery, fechaFinalConsultaQuery, plaza, facturaAP);
        String Query2 = QuerysOracleLocales.queryReporteDetalleTransaccionalidadFechasFacturaAP2(fechaInicialConsultaQuery, fechaFinalConsultaQuery, plaza, facturaAP);
        String Query3 = QuerysOracleLocales.queryReporteDetalleTransaccionalidadFechasFacturaAP3(fechaInicialConsultaQuery, fechaFinalConsultaQuery, plaza, facturaAP);

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
        //Paso 7
        addStep("Ingresar parametros para solicitud");
        boolean statusBotonLimpiar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        u.hardWait(5);
        boolean statusFacturaAP = CommonMethodsSikuli.theUserSendTextIn("inputFacturaAP", facturaAP);
        assertTrue(statusFacturaAP);
        boolean statusFechaInicialAdministrativa = CommonMethodsSikuli.theUserSendTextIn("inputFechaAdministrativaInicial_Parametros", fechaInicialAdministrativa);
        assertTrue(statusFechaInicialAdministrativa);
        u.hardWait(3);
        sk.screen.type(Key.BACKSPACE);
        boolean statusFechaFinalAdministrativa = CommonMethodsSikuli.theUserSendTextIn("inputFechaAdministrativaFinal_Parametros", fechaFinalAdministrativa);
        assertTrue(statusFechaFinalAdministrativa);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusClickBotonAceptarParametros = CommonMethodsSikuli.theUserClicksOn("btnAceptar");
        assertTrue(statusClickBotonAceptarParametros);
        u.hardWait(6);
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
        u.hardWait(5);
        //Paso 10
        addStep("Esperar a que se ejecute reporte");
        testCase.addScreenShotCurrentStep("El Reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        //Paso 11
        addStep("Visualizar Output");
        boolean statusVisualizarOutput = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
        assertTrue(statusVisualizarOutput);
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("Imagen del reporte descargado");
        //Paso 12
        addStep("Extraer la informacion de la base de datos");
        System.out.println(GlobalVariables.DB_HOST_AVEBQA); //Imprimimos a la base de datos que deseamos ingresar
        System.out.println(Query1);
        //Generamos la consulta en la base de datos
        SQLResult ValidQuery1 = dbAvebqa.executeQuery(Query1);
        SQLResult ValidQuery2 = dbAvebqa.executeQuery(Query2);
        SQLResult ValidQuery3 = dbAvebqa.executeQuery(Query3);
        if (ValidQuery1.isEmpty()) { //Si la consulta viene vacia generamos lo siguiente
            testCase.addTextEvidenceCurrentStep("La consulta que se genero viene vacia "); // Agregamos el texto que la query viene vacia
            testCase.addQueryEvidenceCurrentStep(ValidQuery1); // Agregamos el resultado de la query
            testCase.addTextEvidenceCurrentStep("Revisar las fechas ingresadas, ya no contienen datos No facturados"); // Agregamos el texto que la query viene vacia
            System.exit(0);
        } else { //En caso de que si tenga resultados entonces
            totalRows = ValidQuery1.getRowCount(); //Obtenemos el numero total de rows que tiene la consulta
            testCase.addTextEvidenceCurrentStep("Resultado obtenido de la consulta "); //Agregamos esta leyenda en el template de evidencias
            // Agregamos el resultado de la query en el template
            testCase.addQueryEvidenceCurrentStep(ValidQuery1);
            testCase.addQueryEvidenceCurrentStep(ValidQuery2);
            testCase.addQueryEvidenceCurrentStep(ValidQuery3);
            System.out.println("Numero de columnas total " + totalRows);
            for (int inicioRecolecion = 0; inicioRecolecion < totalRows; inicioRecolecion++) {
                obtenerValorDeCasislla = ValidQuery1.getData(inicioRecolecion, 0);
                if (obtenerValorDeCasislla == null) { //Se revisa si el valor obtenido es nulo tenemos que extraer el numero de transaccion
                    obtenerValorEncasoDeSerVacio = ValidQuery2.getData(inicioRecolecion, 5);//Se obtiene el numero de transaccion.
                    valoresObtenidosDeQuerySQL.add(obtenerValorEncasoDeSerVacio); //Se obtiene el numero de transaciones que se guarda en el arreglo
                } else {
                    valoresObtenidosDeQuerySQL.add(obtenerValorDeCasislla); //Se obtiene el numero de lote que se guarda en el arreglo.
                }
            }
        }
        System.out.println("Valores obtenidos en el arreglo: " + valoresObtenidosDeQuerySQL);
        //Paso 13
        addStep("Validar los datos de base de datos vs la del reporte");
        //Abrir reporte
        boolean statusAbrirReporte = CommonMethodsSikuli.theUserClicksOn("btnLinkAbrirReporte");
        assertTrue(statusAbrirReporte);
        u.hardWait(15);
        sk.screen.type(org.sikuli.api.robot.Key.ENTER);
        u.hardWait(10);
        //Fase de copiar todo lo del excel
        //obtener el texto completo del Documento
        String texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
        System.out.println(texto);
        for (String datosDelaConsultaSQL : valoresObtenidosDeQuerySQL) { //Sacamos los valores de la nueva leyenda a validar
            boolean resultadoDeBusqueda = texto.contains(datosDelaConsultaSQL);
            if (resultadoDeBusqueda) { //Validamos que si el valor del arreglo existe imprimimos la leyenda
                System.out.println("El valor " + datosDelaConsultaSQL + " Si se encuentra en el documento descargado"); //Imprimimos las referencias
                testCase.addTextEvidenceCurrentStep("El valor " + datosDelaConsultaSQL + " Si se encuentra en el documento descargado");
            } else { //En caso de que no terminamos la validacion asi como el programa
                System.out.println("El valor " + datosDelaConsultaSQL + " No se encuentra en el documento descargado"); //Imprimimos las referencias
                testCase.addTextEvidenceCurrentStep("El valor " + datosDelaConsultaSQL + " No se encuentra en el documento descargado");
                System.out.println("Se cierra ejecucion ya que no existe valor ");
                System.exit(0);
            }
        }

        // Fin de fase de copiado
        testCase.addScreenShotCurrentStep("Reporte abierto");
        boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
        assertTrue(statusCerrarReporte);
        // Cerrar reporte
        //Cambiarte al aplicativo de Java
        boolean statusClickAppJava = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusClickAppJava);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java");
        System.out.println("Se Cambia al serverlet de java");
        u.hardWait(5);
        // PASO 14
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
        return "ATC_FT_002_EjecConcurrenteXXFCDetalleDeTansaccionalidadDeProveedoresFechasYFacturaAP_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se genere reporte de Detalle de transaccionalidad de proveedores por fechas  y factrua AP";
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
        return " - Acceso al portal web EBS.\n" +
                " - Usar Microsoft Edge en modo Internet Explorer.\n" +
                " - Tener responsabilidad GL a nivel plaza.\n" +
                " - Deben existir transacciones de servicios locales en la tabla de pago de servicios.\n" +
                " - Fecha inicio y fin son obligatorias.";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }
}