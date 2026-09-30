package EBS.locales.gruposDeEjecucionYExclusion;

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
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Clase de utileria para Item con proveedor y servicio-Nopermitido
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_015_Item con proveedor y servicio-Nopermitido
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 */
public class ATC_FT_015_ItemConProveedorYServicioNoPermitido extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_015_ItemConProveedorYServicioNoPermitido_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String grupoEjecucion = data.get("grupoEjecucion");
        String tipoServicio = data.get("tipoServicio");
        String numeroServicio = data.get("numeroServicio");
        String numeroProveedor = data.get("numeroProveedor");
        /*Utilerias
         * */
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
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
        /*Variables SQL
         */
        /*Pasos para el caso de prueba
         * */
        //Paso 1
        addStep(" Ingresar a la Url de EBS y generar login");
        MTLocales.LoginIEEBS();
        u.hardWait(15);
        //Paso 2
        addStep("Seleccionar carpeta responsabilidad, seleccionar apartado de pagina y que se muestre el apartado en EBS");
        MTLocales.theUserSelectsTheValueOfTheEBSPage(carpetaResponsabilidad);
        MTLocales.theUserSelectTheLinkFrecuenciaFacturacion();
        MTLocales.theUserWaitsLeyendaProveedores();
        //Paso 3
        addStep("Consultar el grupo de ejecucion");
        u.hardWait(5);
        boolean statusSendTextInGrupoEjecucion = CommonMethodsSikuli.theUserSendValueIn("inputGrupoEjecucionBusqueda", grupoEjecucion, "framesCreacionGrupos");
        assertTrue(statusSendTextInGrupoEjecucion);
        testCase.addScreenShotCurrentStep("Se ingresa el grupo de ejecucion");
        sk.screen.type(org.sikuli.script.Key.TAB);
        sk.screen.type(Key.ENTER);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Se da click en el boton Ir");
        boolean statusClickBtnIr = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("bntIr", "framesCreacionGrupos");
        assertTrue(statusClickBtnIr);
        u.hardWait(8);
        boolean statusClickRadioButton = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("radioButon", "framesCreacionGrupos");
        assertTrue(statusClickRadioButton);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se da click en el radiobutton y el boton seleccionar");
        boolean statusClickBtnSeleccionarOpcion = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnSeleccionarOpcion", "framesCreacionGrupos");
        assertTrue(statusClickBtnSeleccionarOpcion);
        u.hardWait(8);
        //Paso 4
        addStep("Consultar el grupo de ejecucion");
        boolean statusSentTextInTipoServicio = CommonMethodsSikuli.theUserSendValueIn("inputTipoServicio", tipoServicio, "framesCreacionGrupos");
        assertTrue(statusSentTextInTipoServicio);
        testCase.addScreenShotCurrentStep("Se ingresa el grupo de ejecucion");
        sk.screen.type(org.sikuli.script.Key.TAB);
        sk.screen.type(Key.ENTER);
        u.hardWait(7);
        boolean statusClickRadioButtonTipoServicio = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("radioButon", "framesCreacionGrupos");
        assertTrue(statusClickRadioButtonTipoServicio);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se da click en el radiobutton y el boton seleccionar");
        boolean statusClickBtnSeleccionarOpcionTipoServicio = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnSeleccionarOpcion", "framesCreacionGrupos");
        assertTrue(statusClickBtnSeleccionarOpcionTipoServicio);
        u.hardWait(10);
        //Paso 5
        addStep("Seleccionar el boton buscar y ver items ");
        boolean statusbntBusqar = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnBuscar", "framesCreacionGrupos");
        assertTrue(statusbntBusqar);
        u.hardWait(4);
        boolean statusItemBusqueda = CommonMethodsSikuli.theUserWaitForImage("btnVerItems", "framesCreacionGrupos");
        assertTrue(statusItemBusqueda);
        testCase.addScreenShotCurrentStep("Se muestra la tabla donde se aloja el grupo de ejecucion con sus caracteriticas");
        //Paso 6
        addStep("Seleccionar el boton buscar y ver items ");
        boolean statusClikVerItems = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnVerItems", "framesCreacionGrupos");
        assertTrue(statusClikVerItems);
        u.hardWait(6);
        boolean statusDefinicionTablas = CommonMethodsSikuli.theUserWaitForImage("bntDefinicionesDeTabla", "framesCreacionGrupos");
        assertTrue(statusDefinicionTablas);
        testCase.addScreenShotCurrentStep("Se muestran los items");
        boolean statusClickBtnDefinicionesDeTabla = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("bntDefinicionesDeTabla", "framesCreacionGrupos");
        assertTrue(statusClickBtnDefinicionesDeTabla);
        testCase.addScreenShotCurrentStep("Se muestran las definiciones de la tabla");
        u.hardWait(5);
        boolean statusClickbtnCrear = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnCrear", "framesCreacionGrupos");
        assertTrue(statusClickbtnCrear);
        //Paso 7
        addStep("Asignar servicio de para el grupo de ejecucion, la hora de ejecucion");
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("Se muestra el apartado para seleccionar servicios");
        boolean statusSendTextInServicio = CommonMethodsSikuli.theUserSendValueIn("txtServicio", numeroServicio, "framesCreacionGrupos");
        assertTrue(statusSendTextInServicio);
        sk.screen.type(org.sikuli.script.Key.TAB);
        sk.screen.type(Key.ENTER);
        u.hardWait(7);
        boolean statusClickRadioButtonServicio = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("radioButon", "framesCreacionGrupos");
        assertTrue(statusClickRadioButtonServicio);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se da click en el radiobutton y el boton seleccionar");
        boolean statusClickBtnSeleccionarOpcionServicio = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnSeleccionarOpcion", "framesCreacionGrupos");
        assertTrue(statusClickBtnSeleccionarOpcionServicio);
        u.hardWait(10);
        boolean statusSendTextInProovedor = CommonMethodsSikuli.theUserSendValueIn("txtProveedor", numeroProveedor, "framesCreacionGrupos");
        assertTrue(statusSendTextInProovedor);
        sk.screen.type(org.sikuli.script.Key.TAB);
        sk.screen.type(Key.ENTER);
        u.hardWait(7);
        boolean statusClickRadioButtonServicioProveedor = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("radioButon", "framesCreacionGrupos");
        assertTrue(statusClickRadioButtonServicioProveedor);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se da click en el radiobutton y el boton seleccionar");
        boolean statusClickBtnSeleccionarOpcionProveedor = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnSeleccionarOpcion", "framesCreacionGrupos");
        assertTrue(statusClickBtnSeleccionarOpcionProveedor);
        u.hardWait(10);
        boolean statusClickHora = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("txtSeleccionarHora", "framesCreacionGrupos");
        assertTrue(statusClickHora);
        patternUtil.nDOWN(6); //Bajamos 6 posiciones para seleccionar la hora 5
        sk.screen.type(Key.ENTER);
        testCase.addScreenShotCurrentStep("Se ingresan los parametros de servicio, proveedor , descripcion y la hora");
        //Paso 8
        addStep("Crear y confirmar que se muestre el mensaje de error ");
        boolean statusCrear = CommonMethodsSikuli.theUserClicksOnAnObjectOnTheEBSPage("btnCrearItem", "framesCreacionGrupos");
        assertTrue(statusCrear);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Se muestra mensaje de error de item ya asociado");
        boolean statusLblMesajeItemYaAgregado = CommonMethodsSikuli.theUserWaitForImage("lblMensajeDeErrorPorIngresarServiciosYproveedor", "framesCreacionGrupos");
        assertTrue(statusLblMesajeItemYaAgregado);
        //Paso 9
        addStep("Cerrar el navegador");
        testCase.addScreenShotCurrentStep("Se procede a cerrar el navegador");
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_015_ItemConProveedorYServicioNoPermitido_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Asignar Item con proveedor y servicio-Nopermitido";
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
                " *  - Tener responsabilidad AR a nivel plaza.\n";
    }

    @Override
    public void beforeTest() {

    }
}
