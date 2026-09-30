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
 * DESCRIPCIÓN: Realizar una prueba que nos permita agregar varios días y varias rutas en una sola prueba, sin que se afecten los horarios del registro previo de la ruta y este se pueda visualizar en la tabla "PMO_Ruta y PMO_Tienda
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-014 Agregar varios dias para varias rutas en una sola prueba
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
public class ATC_FT_014_AgregarVariosDiasParaVariasRutas extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_014_AgregarVariosDiasParaVariasRutas_test(HashMap<String, String> data) throws Exception{
        testCase.setProject_Name("I23084_Cambios_Masivos_PMO");
        /*
         * VARIABLES
         */
        String plaza = data.get("plaza");
        String ruta = data.get("ruta");
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
        c.clickPmoCambiosMasivos();
        assertTrue(c.validarPantallaInicio(), "Pantalla validada");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
        // PASO 4
        addStep("Ingresa los filtros: Plaza, Ruta");
        assertTrue(c.buscarPorPlazaRuta(plaza, ruta), "Error al validar la busqueda");
        // PASO 5
        addStep("Seleccionar opcion: Agregar dias Generacion");
        assertTrue(c.agregarDiasGeneracion(), "No se visualizan filtros");
        assertTrue(c.validarInformacionSobrePlaza10GUD(plaza), "Filtros Validados correctamente");
        // PASO 6
        addStep("Capturar los caompo de varios registros y rutas diferentes");
        assertTrue(c.asignarDiaGeneracionYVentanaEntregaValido("LU", "MA"), "No se encontraron registros con el dia");
        assertTrue(c.asignarDiaGeneracionYVentanaEntregaValido("DO", "MI"), "No se encontraron registros con el dia");
        // PASO 7
        addStep("Validar en BD FCRD71CU - Tlajomulco");
        System.out.println("PASO 7");
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
        return "ATC_FT_014_AgregarVariosDiasParaVariasRutas";
    }
    @Override
    public String setTestDescription() {
        return "Realizar una prueba que nos permita agregar varios días y varias rutas en una sola prueba, sin que se afecten los horarios del registro previo de la ruta y este se pueda visualizar en la tabla PMO_Ruta y PMO_Tienda";
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
