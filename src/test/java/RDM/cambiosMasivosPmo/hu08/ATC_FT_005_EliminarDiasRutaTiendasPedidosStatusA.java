package RDM.cambiosMasivosPmo.hu08;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.CambiosMasivosQueries;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCIÓN: Eliminar solo algunos días de generación de una ruta que tiene tiendas con pedido en status A
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_005_EliminarDiasRutaTiendasPedidosStatusA
 * 
 * PREREQUISITOS:
 * 1. Edge
 * 2.-Tener acceso a la aplicación
 * 3.- Tener datos actualizados en PMO Route y PMO Tienda
 * 4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)
 * 5.- Tener acceso a las tablas xxfc_base_tipo_distro, XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG
 * 
 * INSUMOS:
 * Perfil: Coordinador y/o Analista de Sistemas
 * Usuario
 * Contraseña
 * RUTA
 * PLAZA
 * 
 * @author Jorge Hernandez
 * @date 08/Enero/2025
 */
public class ATC_FT_005_EliminarDiasRutaTiendasPedidosStatusA extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_005_EliminarDiasRutaTiendasPedidosStatusA_test(HashMap<String, String> data) throws Exception{
        testCase.setProject_Name("I23084_Cambios_Masivos_PMO");
        /*
         * VARIABLES
         */
        String plaza = data.get("plaza");
        String ruta = data.get("ruta");
        /*
         * UTILERIAS
         */
        SQLUtil dbFCRD71CU = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRD71CU,
                GlobalVariables.DB_USER_FCRD71CU, GlobalVariables.DB_PASSWORD_FCRD71CU);
        CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);
        SikuliUtil sk = new SikuliUtil();
        u = new SeleniumUtil(new IETest());
        String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);
        CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);
        /*
         * PASOS DEL CASO DE PRUEBA
         */
        // PASO 1
        addStep("Ingresar a la liga http://10.180.49.86:8071");
        System.out.println("PASO 1");

        // PASO 2
        addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas"
                + "Capturar:"
                + "- Usuario"
                + "- Contraseña"
                + "Y dar clic al boton de Logon");
        System.out.println("PASO 2");
        assertTrue(c.logInCambiosMasivos(), "Error al loguear en pagina inicial");
        // PASO 3
        addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Cambios_Masivos");
        System.out.println("PASO 3");
        assertTrue(c.clickPmoCambiosMasivos(), "No se pudo seleccionar menu");
        assertTrue(c.validarPantallaInicio(), "Pantalla validada");
        // PASO 4
        addStep("Seleccionar la opcion 'Eliminar Dias Generacion'");
        assertTrue(c.seleccionarEliminarDiasGeneracion(), "No se selecciono la opcion de 'Eliminar Dias Generacion'");
        c.click("plaza");
        testCase.addScreenShotCurrentStep("");
        // PASO 5
        addStep("Ingresar una plaza y ruta en los filtros y ejecutar busqueda");
        assertTrue(c.buscarPorPlazaRuta(plaza, ruta), "No se pudo realizar la busqeuda de registros");
        // PASO 6
        addStep("Selecciona un registro que no cuente con una tienda con pedido estatus A");
        assertTrue(c.validatePattern("checkgrid"), "No exiate el check para seleccionar registro del listado");
        testCase.addScreenShotCurrentStep("");
        c.click("checkgrid");
        testCase.addScreenShotCurrentStep("");
        u.hardWait(3);
        // PASO 7
        addStep("Selecciona un registro que cuente con una tienda con pedido estatus A");
        assertTrue(c.validatePattern("checkgrid"), "No exiate el check para seleccionar registro del listado");
        testCase.addScreenShotCurrentStep("");
        c.click("checkgrid");
        testCase.addScreenShotCurrentStep("");
        u.hardWait(3);
        // PASO 8
        addStep("Aplicar cambios");
        assertTrue(c.validatePattern("btnTiendasPMO"), "No se visualiza el boton de Tiendas PMO");
        c.click("btnTiendasPMO");
        testCase.addScreenShotCurrentStep("");
        // PASO 9
        addStep("Dar clic en el boton de 'Yes'");
        assertTrue(c.clicBtnYes(), "No se pudo dar clic en el boton de el mensaje");
        c.clicBtnYes();
        testCase.addScreenShotCurrentStep("");
        // PASO 10
        addStep("Mostrar tabla log error");
        assertTrue(c.mostrarLogError(), "No se pudo mostrar la tabla de log error");
        // PASO 11
        addStep("Verificar en base de datos que las tiendas que no tenian pedidos en status A se borraron");
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute01(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute02(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute03(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute04(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute05(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute06(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute07(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoRoute08(ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoTienda01(plaza, ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoTienda02(plaza, ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoTienda03(plaza, ruta)).isEmpty());
        assertFalse(executeQuery(dbFCRD71CU, cq.queryPmoTienda04(plaza, ruta)).isEmpty());
        cq.consultaRutaTienda(dbFCRD71CU, plaza, ruta);
        dbFCRD71CU.close();
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_005_EliminarDiasRutaTiendasPedidosStatusA";
    }
    @Override
    public String setTestDescription() {
        return "Eliminar solo algunos días de generación de una ruta que tiene tiendas con pedido en status A";
    }
    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez";
    }
    @Override
    public String setTestInstanceID() {
        return "";
    }
    @Override
    public String setPrerequisites() {
        return "1.- Edge\n" +
                "\n" +
                "2.- Tener acceso a la aplicación\n" +
                "\n" +
                "3.- Tener datos actualizados en PMO Route y PMO Tienda\n" +
                "\n" +
                "4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n" +
                "\n" +
                "5.- Tener acceso a las tablas xxfc_base_tipo_distro, XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_CARGA_STG, XXFC_PMO_ROUTE_LOG, XXFC_PMO_TIENDA_LOG";
    }
    @Override
    public void beforeTest() {

    }
    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}
