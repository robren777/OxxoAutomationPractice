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

/**Desc: Validar visibilidad de apartado Bols y los Bols de la tienda
 * MTC_FT_142_Validar apartado bols en tienda
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Febrero 2025
 */
public class ATC_FT_142_ValidarApartadosBolsTienda extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_142_ValidarApartadosBolsTienda_test(HashMap<String, String> data){
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
        addStep("Ingresar a la liga del portal");
        u.get(url);
        u.refresh();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();
        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion");
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
        addStep("Ingresar al portal Cometa desde access control");
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
        addStep("Ingresar a \"Recepcion de Transferencias y Devoluciones\"");
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
        addStep("Dar clic en el boton \"Liquidacion de Ruta\" para ingresar a la pantalla "
        		+ "\n \"Liquidacion de Ruta\"");
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
        addStep("Realizar una consulta de ruta");
        boolean resultStep7 = pc.buscarLiquidacionRuta(rutaDinamica);
        if(resultStep7){
 		   testCase.addTextEvidenceCurrentStep("Se realizo busqueda con los filtros proporcionados");
         } else {
 		   testCase.addTextEvidenceCurrentStep("No se realizo busqueda con los filtros proporcionados");
         }
         assertTrue(resultStep7, "No se realizo busqueda con los filtros seleccionados");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar visibilidad de apartado Bols en tienda");
        boolean resultStep8 = 	pc.validarElementoEnTiendas("Bols");
		if(resultStep8){
		testCase.addTextEvidenceCurrentStep("Los Bols son visibles");
		} else {
		testCase.addTextEvidenceCurrentStep("Los Bols no son visibles");
		}
		assertTrue(resultStep8, "No se ha podido validar los bols sean visibles");
		
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar visibilidad de los Bols debajo de el titulo de Bols");
        boolean resultStep9 = 	pc.contarTotalBols();
		if(resultStep9){
		testCase.addTextEvidenceCurrentStep("Los nÃºmeros de bols se muesrtan debajo del tÃ­tulo de Bols");
		} else {
		testCase.addTextEvidenceCurrentStep("Los nÃºmeros de bols se no muesrtan debajo del tÃ­tulo de Bols");
		}
		assertTrue(resultStep9, "Los nÃºmeros de bols no se muesrtan debajo del tÃ­tulo de Bols");
        u.getDriver().quit();
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod(){
       // u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar visibilidad de apartado Bols y los Bols de la tienda";
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