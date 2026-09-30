package EBS.locales.gruposDeEjecucionYExclusion;

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
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Retirar permiso de seguridad para concurrente de facturacion
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_017_Retirar permiso de seguridad para concurrente de facturacion
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Usar Microsoft Edge en modo Internet Explorer
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 */
public class ATC_FT_017_RetirarPermisoDeSeguridadPAraConcurrenteDeFacturacion  extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_017_RetirarPermisoDeSeguridadPAraConcurrenteDeFacturacion_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String nombreApartado = data.get("nombreApartado");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String fechaInicial = data.get("fechaInicial");
        String fechaFinal = data.get("fechaFinal");
        String agrupacionFactura = data.get("agrupacionFactura");
        String path = "\\Patterns\\Locales\\";
        /*Utilerias
         * */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);
        metodosLocales MTLocales = new metodosLocales(u, testCase, ebsUrl, userName, password);
        SikuliUtil sk = new SikuliUtil();
        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
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
        addStep(" Ingresar a la Url de EBS y generar login");
        MTLocales.LoginIEEBS();
        u.hardWait(25);
        //Paso 2
        addStep("Seleccionar carpeta responsabilidad, seleccionar apartado de pagina y que se muestre el apartado en EBS");
        MTLocales.theUserSelectTheAppJavaValores();
        //Paso 3
        addStep("Abrir el appserveler");
        //Run app Serverlet java
        CommonMethodsSikuli.theUserWaitsToApproveAndRunApplication("checkboxIAcceptTheRiskAndWantToRunThisApplication", "buttonRun");
        testCase.addScreenShotCurrentStep("Abriendo la aplicacion java");
        u.hardWait(20);
        //Paso 4
        addStep("Ingresar el nombre del apartado a buscar y quitamos el permiso");
        boolean statusNombreResponsabilidadAdmin = CommonMethodsSikuli.theUserSendTextIn("txtNombreRespAdmin",nombreApartado);
        assertTrue(statusNombreResponsabilidadAdmin);
        u.hardWait(3);
        boolean statusValorUsuario = CommonMethodsSikuli.theUserSendTextIn("txtValor",userName);
        assertTrue(statusValorUsuario);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros para la busqueda y se selecciona el boton encontrar");
        boolean statusBtnEncontrar = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
        assertTrue(statusBtnEncontrar);
        u.hardWait(10);
        boolean statusLblEtiquetaUser = CommonMethodsSikuli.theUserClicksOn("lblNombreUsuarioAQuitarPermiso");
        assertTrue(statusLblEtiquetaUser);
        boolean statusCheckBox = CommonMethodsSikuli.theUserClicksOn("btnCheckBox");
        assertTrue(statusCheckBox);
        testCase.addScreenShotCurrentStep("Se desmarca el checkbox");
        u.hardWait(5);
        boolean statusGuardarCambios = CommonMethodsSikuli.theUserClicksOn("btnGrabar");
        assertTrue(statusGuardarCambios);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Se Guardaron los cambios");
        u.hardWait(2);
        boolean statusCerrarOracle = CommonMethodsSikuli.theUserClicksOn("btnCerrarAppJava");
        assertTrue(statusCerrarOracle);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Cerrar aplicacion Java");
        boolean statusAceptarSalirOracleApplications = CommonMethodsSikuli.theUserClicksOn("botonAceptarSalirOracleApplications");
        assertTrue(statusAceptarSalirOracleApplications);
        u.hardWait(10);
        sk.screen.type(Key.ENTER);
        u.hardWait(25);
        //paso 5
        addStep("Regresar al navegador y cerrar sesion");
        testCase.addScreenShotCurrentStep("Se regresa al navegador");
        sk.screen.click();//Se da click para poner foco en el navegador
        patternUtil.nUP(3); // se teclea 3 veces hacia arriba para ver la imagen de desconectarse
        testCase.addScreenShotCurrentStep("Se da click en el boton desconectarse");
        boolean statusDarClickEnDesconectarse = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnDesconectarse","framesCreacionGrupos");
        assertTrue(statusDarClickEnDesconectarse);
        u.hardWait(20);
        testCase.addScreenShotCurrentStep("Se cierra sesion");
        //Paso 6
        addStep("Reingresar al login para corroborar  que no tengamos permisos para los grupos");
        boolean statusNombreUsuarioEBS=CommonMethodsSikuli.theUserSendValueIn("txtNombreUsuarioEBS",userName,"framesCreacionGrupos");
        assertTrue(statusNombreUsuarioEBS);
        u.hardWait(3);
        boolean statusPasswordEBS =CommonMethodsSikuli.theUserSendValueIn("txtPasswordEBS",password,"framesCreacionGrupos");
        assertTrue(statusPasswordEBS);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("Se reingresa usuario, password y damos click en el boton conectese");
        boolean statusConectarse = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnConectese","framesCreacionGrupos");
        assertTrue(statusConectarse);
        u.hardWait(20);
        testCase.addScreenShotCurrentStep("Se reingreso al login");
        sk.screen.click();//Se da click para poner foco en el navegador
        patternUtil.nDOWN(3);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Se da click en las etiquetas de la responsanilidad Help desk y en la opcion Usuarios.");
        boolean statusDarclickEnHelpDesk = CommonMethodsSikuli.theUserClicksOn("lblHelpDesk");
        assertTrue(statusDarclickEnHelpDesk);
        u.hardWait(6);
        boolean statusDarclickEnUsuarios = CommonMethodsSikuli.theUserClicksOn("lblUsuarios");
        assertTrue(statusDarclickEnUsuarios);
        u.hardWait(2);
        //Paso 8
        addStep("Abrir el appserveler");
        //Run app Serverlet java
        CommonMethodsSikuli.theUserWaitsToApproveAndRunApplication("checkboxIAcceptTheRiskAndWantToRunThisApplication", "buttonRun");
        testCase.addScreenShotCurrentStep("Abriendo la aplicacion java");
        //Paso 9
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
        sk.screen.type(org.sikuli.api.robot.Key.ENTER);
        u.hardWait(3);
        //Paso 10
        addStep("Ir a solicitudes");
        testCase.addScreenShotCurrentStep("Ir al menu Ver");
        boolean statusMenuVer = CommonMethodsSikuli.theUserClicksOn("optionMenuVer");
        assertTrue(statusMenuVer);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Seleccionar solicitudes");
        boolean statusSeleccionarSolicitudes = CommonMethodsSikuli.theUserClicksOn("optionSolicitudes_Ver");
        assertTrue(statusSeleccionarSolicitudes);
        u.hardWait(7);
        //Paso 11
        addStep("Generar una nueva solicitud");
        testCase.addScreenShotCurrentStep("Seleccionar boton Ejecutar Nueva Solicitud");
        boolean statusGenerarNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("EjecutarNuevaSolicitud");
        assertTrue(statusGenerarNuevaSolicitud);
        u.hardWait(4);
        boolean statusIngresarNombreReporte = CommonMethodsSikuli.theUserSendTextIn("Nombre_EjecutarEstaSolicitud", nombreReporte);
        assertTrue(statusIngresarNombreReporte);
        testCase.addScreenShotCurrentStep("Se ingresa nombre de reporte");
        u.hardWait(2);
        sk.screen.type(org.sikuli.api.robot.Key.TAB);
        u.hardWait(3);
        //Paso 12
        addStep("Ingresar parametros para solicitud");
        boolean statusBotonLimpiar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        u.hardWait(3);
        boolean statusFechaInicial = CommonMethodsSikuli.theUserSendTextIn("txtFechaInicial", fechaInicial);
        assertTrue(statusFechaInicial);
        u.hardWait(3);
        sk.screen.type(org.sikuli.api.robot.Key.BACKSPACE);
        boolean statusFechaFinal = CommonMethodsSikuli.theUserSendTextIn("txtFechaFinal", fechaFinal);
        assertTrue(statusFechaFinal);
        u.hardWait(3);
        boolean statusAgrupacionFactura = CommonMethodsSikuli.theUserSendTextIn("txtAgrupacionFactura", agrupacionFactura);
        assertTrue(statusAgrupacionFactura);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusClickBotonAceptarParametros = CommonMethodsSikuli.theUserClicksOn("btnAceptar");
        assertTrue(statusClickBotonAceptarParametros);
        u.hardWait(6);
        //Paso 13
        addStep("Ejecutar Solicitud");
        boolean statusEjecutarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
        assertTrue(statusEjecutarSolicitud);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
        boolean statusClickBotonNo = CommonMethodsSikuli.theUserClicksOn("optionNo");
        assertTrue(statusClickBotonNo);
        u.hardWait(5);
        //Paso 14
        addStep("Encontrar Solicitud");
        testCase.addScreenShotCurrentStep("Selecionar boton encontrar");
        boolean statusBotonEncontrarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
        assertTrue(statusBotonEncontrarSolicitud);
        u.hardWait(20);
        //Paso 15
        addStep("Esperar a que se ejecute reporte");
        testCase.addScreenShotCurrentStep("El Reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte y se muestra con error");
        u.hardWait(12);
        //Paso 16
        addStep("Visualizar la informacion del registro");
        boolean statusVisualizarRegistro = CommonMethodsSikuli.theUserClicksOn("btnVisualizarRegistro");
        assertTrue(statusVisualizarRegistro);
        u.hardWait(20);
        //Cerrar ventana adicional de autentificacion
        sk.screen.click();
        CommonMethodsSikuli.closeTab();
        System.out.println("Se genero  un ctrl w para cerrar la pestaña extra");
        //obtener la factura linea
        u.hardWait(25);
        testCase.addScreenShotCurrentStep("Se muestra el mensaje de que no se tienen permisos para ejecutar el concurrente");
        //Paso 17
        addStep("Cerrar Oracle");
        //Cambiarte al aplicativo de Java
        boolean statusRegresarpJava2 = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusRegresarpJava2);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java para cerrar el aplicativo");
        System.out.println("Se Cambia al serverlet de java");
        u.hardWait(5);
        boolean statusCerrarOracle2 = CommonMethodsSikuli.theUserClicksOn("btnCerrarAppJava");
        assertTrue(statusCerrarOracle2);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Cerrar aplicacion Java");
        boolean statusAceptarSalirOracleApplications2 = CommonMethodsSikuli.theUserClicksOn("botonAceptarSalirOracleApplications");
        assertTrue(statusAceptarSalirOracleApplications2);
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_017_RetirarPermisoDeSeguridadPAraConcurrenteDeFacturacion_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Retirar permiso de seguridad para concurrente de facturacion";
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
        return " * \t- Acceso al portal web EBS\n" +
                " * \t- Usar Microsoft Edge en modo Internet Explorer\n" +
                " *  - Tener responsabilidad AR a nivel plaza.\n";
    }

    @Override
    public void beforeTest() {

    }
}
