package EBS.locales.reporteDetalleTransaccionalidadProveedores;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Clase de utileria para generar reporte de Detalle de transaccionalidad de proveedores por fechas, Factura AP y Factura AR
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_005_Ejecutar Concurrente XXFC - Detalle de transaccionalidad de proveedores por fechas, Factura AP y Factura AR
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

public class ATC_FT_005_EjecConcurrenteXXFCDetalleDeTansaccionalidadDeProveedoresFechasFacturaAPFacturaAR extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_005_EjecConcurrenteXXFCDetalleDeTansaccionalidadDeProveedoresFechasFacturaAPFacturaAR_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String facturaAR = data.get("facturaAR");
        String facturaAP = data.get("facturaAP");
        String fechaInicialAdministrativa = data.get("fechaInicialAdministrativa");
        String fechaFinalAdministrativa = data.get("fechaFinalAdministrativa");
        String leyendaABuscarError=data.get("leyendaABuscarError");
        //Variables de leyenda convertida
        String seperador = ",";
        String cadenaintermedia=leyendaABuscarError.replace(","," ");
        String cadenaPreliminar=cadenaintermedia.replace("  "," ");
        String nuevacadena = cadenaPreliminar.replace(" ", ",");
        System.out.println(nuevacadena);
        String[] arr = nuevacadena.split(java.util.regex.Pattern.quote(seperador));
        ArrayList<String> nuevaLeyendaAValidar = new ArrayList<>();
        String textoObtenidoDelLOG;
        //Fin de leyenda convertida

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
        boolean statusFacturaAR = CommonMethodsSikuli.theUserSendTextIn("inputFacturaAR", facturaAR);
        assertTrue(statusFacturaAR);
        u.hardWait(3);
        boolean statusFacturaAP = CommonMethodsSikuli.theUserSendTextIn("inputFacturaAP", facturaAP);
        assertTrue(statusFacturaAP);
        u.hardWait(3);
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
        testCase.addScreenShotCurrentStep("Imagen del error");
        sk.screen.type(org.sikuli.script.Key.ENTER);
        u.hardWait(15);
        //Paso 12
        addStep("Validar que se muestre el mensaje de error en el navegador");
        boolean statusVisualizarRegistro = CommonMethodsSikuli.theUserClicksOn("btnVisualizarRegistro");
        assertTrue(statusVisualizarRegistro);
        u.hardWait(15);
        //Cerrar ventana adicional de autentificacion
        sk.screen.click();
        CommonMethodsSikuli.closeTabCiclico("lblMensajeAutentificacionFailed");
        System.out.println("Se genero  un ctrl w para cerrar la pestaña extra");
        //fin de cerrar pestaña adicional
        testCase.addScreenShotCurrentStep("Se revisa el contenido del Log para procesar  y validar que se muestre el mensaje de error");
        //obtener los mensajes del log
        u.hardWait(15);
        for (String ele : arr) {
            nuevaLeyendaAValidar.add(ele);
            System.out.println(ele);
        }
        System.out.println(nuevaLeyendaAValidar);
        //obtener el texto completo del LOG
        textoObtenidoDelLOG = CommonMethodsSikuli.getAllTextPage();//Obtenemos el texto del log
        System.out.println(textoObtenidoDelLOG);
        for (String valorLeyendaDividida : nuevaLeyendaAValidar) { //Sacamos los valores del log y buscamos el error para validar que si se muestre
            boolean resultadoDeBusqueda=textoObtenidoDelLOG.contains(valorLeyendaDividida);
            if (resultadoDeBusqueda) { //Validamos que si el valor del arreglo existe imprimimos la leyenda
                System.out.println("El valor " + valorLeyendaDividida + " Si se encuentra en el LOG"); //Imprimimos las referencias
                testCase.addTextEvidenceCurrentStep("El valor " + valorLeyendaDividida + " Si se encuentra en el LOG");
            } else { //En caso de que no terminamos la validacion asi como el programa
                System.out.println("El valor " + valorLeyendaDividida + " No se encuentra en el LOG"); //Imprimimos las referencias
                testCase.addTextEvidenceCurrentStep("El valor " + valorLeyendaDividida + " No se encuentra en el LOG");
                System.exit(0);
            }
        }
        // Fin de validacion de error
        //Cambiarte al aplicativo de Java
        boolean statusClickAppJava = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusClickAppJava);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java");
        System.out.println("Se Cambia al serverlet de java");
        u.hardWait(5);
        // PASO 13
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

    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_005_EjecConcurrenteXXFCDetalleDeTansaccionalidadDeProveedoresFechasFacturaAPFacturaAR_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se genere reporte de Detalle de transaccionalidad de proveedores por fechas, Factura AP y Factura AR";
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
