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
 * DESCRIPCIÓN: Realizar una prueba que nos permita visualizar el mensaje de error al no capturar el campo de 'Día de Generación Nuevo', al dar clic al botón de 'Aplicar Cambios'
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC-FT-003 Intentar agregar un nuevo Día de Generación "sin capturar" / "Con Carácter Especial" / "Con un día incorrecto", en el campo de "Día de Generación nuevo"
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

public class ATC_FT_003_IntentarAgregarDiaGeneracionSinCapturar extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_IntentarAgregarDiaGeneracionSinCapturar_test(HashMap<String, String> data) throws Exception{
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
        addStep("Captura el campo 'Dia de Generacion Pedido' sin capturar");
        assertTrue(c.asignarDiaGeneracionYVentanaEntregaEnBlanco("", ""), "Se muestra el mesaje de datos invalidos");
        // PASO 7
        addStep("Captura el campo 'Dia de Generacion Pedido' con Caracter Especial");
        assertTrue(c.asignarDiaGeneracionInvalido("$%"), "Se muestra el mesaje de datos invalidos");
        // PASO 8
        addStep("Captura el campo 'Dia de Generacion Pedido' con un dia incorrecto");
        assertTrue(c.asignarDiaGeneracionInvalido("XV"), "Se muestra el mesaje de datos invalidos");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_IntentarAgregarDiaGeneracionSinCapturar";
    }

    @Override
    public String setTestDescription() {
        return "Realizar una prueba que nos permita visualizar el mensaje de error al no capturar el campo de 'Día de Generación Nuevo', al dar clic al botón de 'Aplicar Cambios'";
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
