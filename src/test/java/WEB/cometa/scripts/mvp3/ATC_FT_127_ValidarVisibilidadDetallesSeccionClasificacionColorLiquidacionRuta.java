package WEB.cometa.scripts.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar los detalles de la seccion Clasificacion por color en Liquidacion de Ruta al realizar consulta
 * MTC_FT_127_Validar visibilidad de los detalles de la seccion Clasificacion por Color en Liquidacion de Ruta
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Febrero 2025
 */
public class ATC_FT_127_ValidarVisibilidadDetallesSeccionClasificacionColorLiquidacionRuta extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_127_ValidarVisibilidadDetallesSeccionClasificacionColorLiquidacionRuta_test(HashMap<String, String> data) {
        testCase.setProject_Name("Portal Web: Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        String rutaDinamica = data.get("rutaDinamica");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        u.get(url);
        u.refresh();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();

        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
        if(resultStep2){
            testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
        }
        assertTrue(resultStep2, "No se pudo iniciar sesion");

        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc.seleccionarPortalCometaQA();
        if(resultStep3){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep3, "No es visible el elemento de Cometa");
        u.hardWait(4);
        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
 	   	if(resultStep4){
 		   testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Cedis");
 		} else {
 		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento Cedis");
 		}
 	   	assertTrue(resultStep4, "No se encontro el elemento Cedis");

        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a 'RecepciÃ³n de Transferencias y Devoluciones'");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
		   testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Recepcion de Transferencias");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion de Transferencias");
        }
        assertTrue(resultStep5, "No se encontro el elemento Recepcion de Transferencias");
        
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic en el boton 'Liquidacion de Ruta' para ingresar a la pantalla 'Liquidacion de Ruta'");
        boolean resultStep6 = pc.CedisRecepcionLiquidacionRuta();
        if(resultStep6){
		   testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en LiquidaciÃ³n de Ruta");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento LiquidaciÃ³n de Ruta");
        }
        assertTrue(resultStep6, "No se encontro el elemento LiquidaciÃ³n de Ruta");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar una Ruta y dar clic en Consultar");
        boolean resultStep7 = pc.buscarLiquidacionRuta(rutaDinamica);
        if(resultStep7){
		   testCase.addTextEvidenceCurrentStep("Se pudo realizar una consulta");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se pudo realizar una consulta");
        }
        assertTrue(resultStep7, "No se pudo realizar una consulta al seleccionar una ruta");
        
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar los la informacion de los detalles de Clasificacion por color");
        boolean resultStep8 = 	pc.VisibilidadElementoLiquidacionRuta("ClasificacionColorInfo", false);
        

		if(resultStep8){
		testCase.addTextEvidenceCurrentStep("Los elementos de filtrado son visibles");
		} else {
		testCase.addTextEvidenceCurrentStep("Los elementos de filtrado no son visibles");
		}
		assertTrue(resultStep8, "Los elementos de filtrado no se encontraron o no son visibles");
		
        u.getDriver().quit();
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod() {
        u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar los detalles de la seccion Clasificacion por color en Liquidacion de Ruta al realizar consulta";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac E. Garcia";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}