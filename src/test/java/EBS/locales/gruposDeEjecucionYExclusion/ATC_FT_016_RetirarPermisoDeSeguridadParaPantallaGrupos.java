package EBS.locales.gruposDeEjecucionYExclusion;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
 * DESCRIPCION: Clase de utileria para Retirar permiso de seguridad para pantalla GRUPOS
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_016_Retirar permiso de seguridad para pantalla GRUPOS
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Usar Microsoft Edge en modo Internet Explorer
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 */
public class ATC_FT_016_RetirarPermisoDeSeguridadParaPantallaGrupos extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_016_RetirarPermisoDeSeguridadParaPantallaGrupos_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String nombreApartado = data.get("nombreApartado");
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
        boolean statusNombreResponsabilidadAdmin = CommonMethodsSikuli.theUserSendTextIn("txtNombreRespAdmin", nombreApartado);
        assertTrue(statusNombreResponsabilidadAdmin);
        u.hardWait(3);
        boolean statusValorUsuario = CommonMethodsSikuli.theUserSendTextIn("txtValor", userName);
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
        //paso 5
        addStep("Regresar al navegador y cerrar sesion");
        boolean statusCambioAlNavegador = CommonMethodsSikuli.theUserClicksOn("appNavegadorEdge");
        assertTrue(statusCambioAlNavegador);
        testCase.addScreenShotCurrentStep("Se regresa al navegador");
        u.hardWait(6);
        sk.screen.click();//Se da click para poner foco en el navegador
        patternUtil.nUP(3); // se teclea 3 veces hacia arriba para ver la imagen de desconectarse
        testCase.addScreenShotCurrentStep("Se da click en el boton desconectarse");
        boolean statusDarClickEnDesconectarse = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnDesconectarse", "framesCreacionGrupos");
        assertTrue(statusDarClickEnDesconectarse);
        u.hardWait(20);
        testCase.addScreenShotCurrentStep("Se cierra sesion");
        //Paso 6
        addStep("Reingresar al login para corroborar  que no tengamos permisos para los grupos");
        boolean statusNombreUsuarioEBS = CommonMethodsSikuli.theUserSendValueIn("txtNombreUsuarioEBS", userName, "framesCreacionGrupos");
        assertTrue(statusNombreUsuarioEBS);
        u.hardWait(3);
        boolean statusPasswordEBS = CommonMethodsSikuli.theUserSendValueIn("txtPasswordEBS", password, "framesCreacionGrupos");
        assertTrue(statusPasswordEBS);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("Se reingresa usuario, password y damos click en el boton conectese");
        boolean statusConectarse = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnConectese", "framesCreacionGrupos");
        assertTrue(statusConectarse);
        u.hardWait(20);
        testCase.addScreenShotCurrentStep("Se reingreso al login");
        boolean statusDarClickEnResponsabilidadAR17ADM = CommonMethodsSikuli.theUserClicksOn("lblAR10MONTransacciones");
        assertTrue(statusDarClickEnResponsabilidadAR17ADM);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Se da click en las etiquetas de la responsanilidad y en la opcion frecuencia facturacion");
        boolean statusDarclickEnFrecuenciaFacturacion = CommonMethodsSikuli.theUserClicksOn("lblFrecuenciaFacturacion");
        assertTrue(statusDarclickEnFrecuenciaFacturacion);
        u.hardWait(6);
        testCase.addScreenShotCurrentStep("Se muestra el mensaje de error, ya que no se cuenta con permisos");
        boolean statusMensajeErrorPermisos = CommonMethodsSikuli.theUserWaitForImage("lblMensajeErrorNoTienesPermisoGrupos", "framesCreacionGrupos");
        assertTrue(statusMensajeErrorPermisos);
        u.hardWait(2);
        //Paso 7
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
        return "ATC_FT_016_RetirarPermisoDeSeguridadParaPantallaGrupos_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Retirar permiso de seguridad para pantalla GRUPOS";
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
