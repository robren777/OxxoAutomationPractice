package RDM.cambiosMasivosPmo.hu07;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Realizar una prueba que nos permita visualizar más de un registro con información repetida en el campo de "Inicio De Ventana Entrega" y al capturar nuevamente un día repetido en el nuevo registro, este nos muestra un mensaje de error indicándonos el detalle. Tener en cuenta que este nuevo registro no deberá repetir nuevamente el día de los campos "Día de Generación" y "Inicio de Ventana Entrega"
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-013 Agregar Un nuevo registro capturando el campo "Inicio de Ventana Entrega Nuevo" de un registro previamente creado que cuente con un día repetido desde el origen de la información en el campo de "Inicio de Ventana Entrega"
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
public class ATC_FT_013_AgregarNuevoRegistroDiaVentanaEntregaExistente extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_013_AgregarNuevoRegistroDiaVentanaEntregaExistente_test(HashMap<String, String> data) throws Exception{
        testCase.setProject_Name("I23084_Cambios_Masivos_PMO");
        /*
         * VARIABLES
         */
        String plaza = data.get("plaza");
        String ruta = data.get("ruta");
        /*
         * UTILERIAS
         */
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
        assertTrue(c.logInCambiosMasivos(), "Problemas en el logIn");
        // PASO 3
        addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Cambios_Masivos");
        System.out.println("PASO 3");
        c.clickPmoCambiosMasivos();
        assertTrue(c.validarPantallaInicio(), "Pantalla validada");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
        // PASO 4
        addStep("Ingresa los filtros: Plaza, Ruta");
        System.out.println("PASO 4");
        assertTrue(c.asignarPlazaRuta(plaza, ruta), "Error al validar la busqueda");
        // PASO 5
        addStep("Seleccionar opcion: Agregar dias Generacion");
        System.out.println("PASO 5");
        assertTrue(c.agregarDiasGeneracion(), "No se pudo seleccionar la opcion Agregar Dias Generacion");
        assertTrue(c.realizarBusqueda(), "No se pudo realizar la busqueda de registros");
        // PASO 6
        addStep("Ubicar los registros que contengan en el campo de 'Dia Ventana de Entrega' el dia correspondiente a LU");
        assertTrue(c.ubicarLunesDiaVentanaEntrega(), "No se encontraron registros con el dia LU");
        // PASO 7
        addStep("Capturar el campo 'Dia Ventana de Entrega' con los siguientes datos: LU");
        assertTrue(c.asignarDiaVentanaEntregaRepetido("SA", "LU"), "No se pudo validar el mensaje de valor no puede ser igual al actual");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_013_AgregarNuevoRegistroDiaVentanaEntregaExistente";
    }
    @Override
    public String setTestDescription() {
        return "Realizar una prueba que nos permita visualizar más de un registro con información repetida en el campo de 'Inicio De Ventana Entrega' y al capturar nuevamente un día repetido en el nuevo registro, este nos muestra un mensaje de error indicándonos el detalle. Tener en cuenta que este nuevo registro no deberá repetir nuevamente el día de los campos 'Día de Generación' y 'Inicio de Ventana Entrega'";
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
