package RDM.cambiosMasivosPmo.hu07;

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
 * DESCRIPCIÓN: Realizar una prueba donde se pueda agregar un nuevo día de generación sin que se afecten los horarios del registro previo de la ruta y este se pueda visualizar en la tabla \"PMO_Ruta y PMO_Tienda
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-001 Agregar un nuevo Día de Generación 
 * 
 * PREREQUISITOS:
 * 1. Edge
 * 2.-Tener acceso a la aplicación
 * 3.- Tener datos actualizados en PMO Route y PMO Tienda
 * 4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)
 * 5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA
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

public class ATC_FT_001_AgregarNuevoDiaGeneracion extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_AgregarNuevoDiaGeneracion_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I23084_Cambios_Masivos_PMO");
        /*
         * VARIABLES
         */
        String url = data.get("server");
        String plaza = data.get("plaza");
        String ruta = data.get("ruta");
        String diaGenPedido = data.get("diaGenPedido");
        String diaVenEntrega = data.get("diaVenEntrega");

        /*
         * UTILERIAS
         */
        SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
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
        assertTrue(c.clickPmoCambiosMasivos(), "No se visualiza el menu requerido");
        assertTrue(c.validarPantallaInicio(),"Pantalla validada");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
        // PASO 4
        addStep("Ingresa los filtros: Plaza, Ruta");
        System.out.println("PASO 4");
        c.asignarPlazaRuta(plaza, ruta);
        // PASO 5
        addStep("Seleccionar opcion: Agregar dias Generacion");
        System.out.println("PASO 5");
        assertTrue(c.agregarDiasGeneracion(), "No se pudo seleccionar la opcion Agregar Dias Generacion");
        assertTrue(c.realizarBusqueda(), "No se pudo realizar la busqueda de registros");
        // PASO 6
        addStep("Capturar los campos: Dia de 'Generacion Pedido' con datos validos y 'Dia Ventana de Entrega' con datos validos");
        System.out.println("PASO 6");
        assertTrue(c.validatePattern("diaGenPedInputBox"), "No se visualiza el campo de Dia de Generación Pedido");
        testCase.addScreenShotCurrentStep(" ");
        c.typeInput("diaGenPedInputBox", diaGenPedido);
        testCase.addScreenShotCurrentStep(" ");
        assertTrue(c.validatePattern("diaVentEntInputBox"), "No se visualiza el campo de Dia de Ventana de Entrega");
        testCase.addScreenShotCurrentStep(" ");
        c.typeInput("diaVentEntInputBox", diaVenEntrega);
        testCase.addScreenShotCurrentStep(" ");
        assertTrue(c.validatePattern("checkgrid"), "No se visualiza el check sin seleccionar");
        testCase.addScreenShotCurrentStep(" ");
        c.click("checkgrid");
        testCase.addScreenShotCurrentStep(" ");
        assertTrue(c.validatePattern("aplicarCambios"), "No se visualiza el boton de Aplicar Cambios");
        testCase.addScreenShotCurrentStep(" ");
        c.aplicarCambios();
        testCase.addScreenShotCurrentStep(" ");
        assertTrue(c.validatePattern("alertaRegistrosSeleccionados"), "Registros seleccionados correctamente");
        testCase.addScreenShotCurrentStep(" ");
        c.clicBtnYes();
        testCase.addScreenShotCurrentStep(" ");
        assertTrue(c.validatePattern("seAplicaronLosCambios"), "Se aplicaron los cambios correctamente");
        testCase.addScreenShotCurrentStep(" ");
        c.clicBtnYes();
        testCase.addScreenShotCurrentStep(" ");
        // Paso 7
        addStep("Validar en BD FCRD71CU - Tlajomulco");
        System.out.println("PASO 7");
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute01(plaza)).isEmpty());
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute02(plaza)).isEmpty());
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute03(plaza)).isEmpty());
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute04(plaza)).isEmpty());
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute05(plaza)).isEmpty());
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute06(plaza)).isEmpty());
        assertFalse(executeQuery(dbConecta, cq.getHu07PmoRoute07(plaza)).isEmpty());
        cq.consultaPmoRouteHu07(dbConecta , plaza, false);
        dbConecta.close();
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_AgregarNuevoDiaGeneracion";
    }
    @Override
    public String setTestDescription() {
        return "Realizar una prueba donde se pueda agregar un nuevo día de generación sin que se afecten los horarios del registro previo de la ruta y este se pueda visualizar en la tabla \"PMO_Ruta y PMO_Tienda";
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
                "5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA";
    }
    @Override
    public void beforeTest() {

    }
    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}
