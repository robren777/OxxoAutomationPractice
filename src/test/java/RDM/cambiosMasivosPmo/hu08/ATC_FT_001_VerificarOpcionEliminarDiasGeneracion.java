package RDM.cambiosMasivosPmo.hu08;

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
 * DESCRIPCIÓN: Verificar que esté disponible la opción para eliminar los días de generación
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_001_VerificarOpcionEliminarDiasGeneracion 
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
public class ATC_FT_001_VerificarOpcionEliminarDiasGeneracion extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_VerificarOpcionEliminarDiasGeneracion_test(HashMap<String, String> data) throws Exception{
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
        assertTrue(c.clickPmoCambiosMasivos(), "No se visualiza la opcion de cambios masivos");
        assertTrue(c.validarPantallaInicio(), "Pantalla validada");
        // PASO 4
        addStep("Validar que se visualice una seccion 'Tipo Movimiento'");
        assertTrue(c.validarTipoMovimiento(), "No se encontro la seccion Tipo Movimiento");
        // PASO 5
        addStep("Validar que se visualice una opcion de 'Eliminar Dias Generacion'");
        assertTrue(c.validarOpcionEliminarDiasGeneracion(), "No se encontro la opcion de 'Eliminar Dias Generacion'");
        // PASO 6
        addStep("Seleccionar la opcion 'Eliminar Dias Generacion");
        assertTrue(c.seleccionarEliminarDiasGeneracion(), "No se selecciono la opcion de 'Eliminar Dias Generacion'");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_VerificarOpcionEliminarDiasGeneracion";
    }
    @Override
    public String setTestDescription() {
        return "Verificar que esté disponible la opción para eliminar los días de generación";
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