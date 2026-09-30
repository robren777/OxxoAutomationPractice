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
 * DESCRIPCIÓN: Realizar una prueba donde se pueda agregar un nuevo día de generación y validar que los días del registro previo de la ruta, sean iguales al nuevo 'Dia de Generación'
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-002 Agregar un nuevo Día de Generación y validar que los horarios sean iguales al del registro
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

public class ATC_FT_002_AgregarNuevoDiaGeneracionValidarHorariosIguales extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_AgregarNuevoDiaGeneracionValidarHorariosIguales_test(HashMap<String, String> data) throws Exception{
        testCase.setProject_Name("I23084_Cambios_Masivos_PMO");
        /*
         * VARIABLES
         */
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
        assertTrue(c.clickPmoCambiosMasivos(), "No se visualiza el menu Requerido");
        assertTrue(c.validarPantallaInicio(), "Pantalla validada");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
        // PASO 4
        addStep("Ingresa los filtros: Plaza, Ruta");
        System.out.println("PASO 4");
        assertTrue(c.asignarPlazaRuta(plaza, ruta), "No se pudo capturar ruta y plaza");
        testCase.addScreenShotCurrentStep(" ");
        // PASO 5
        addStep("Seleccionar opcion: Agregar dias Generacion");
        System.out.println("PASO 5");
        assertTrue(c.agregarDiasGeneracion(), "No se pudo seleccionar la opcion Agregar Dias Generacion");
        assertTrue(c.realizarBusqueda(), "No se pudo realizar la busqueda de registros");
        // PASO 6
        addStep("Capturar los campos 'Dia de Generacion Pedido y 'Dia Ventana de Entrega'");
        assertTrue(c.asignarDiaGeneracionYVentanaEntregaValido(diaGenPedido, diaVenEntrega), "No se encontraron registros con el dia LU");
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
        return "ATC_FT_002_AgregarNuevoDiaGeneracionValidarHorariosIguales";
    }
    @Override
    public String setTestDescription() {
        return "Realizar una prueba donde se pueda agregar un nuevo día de generación y validar que los días del registro previo de la ruta, sean iguales al nuevo 'Dia de Generación'";
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
