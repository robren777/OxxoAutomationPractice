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

/**Desc: Validar que el filtro de ruta solo permita escritura alfanÃºmerica
 * MTC_FT_114_Realizar una busqueda de ruta con una cadena con espacios
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Febrero 2025
 */
public class ATC_FT_114_RealizarBusquedaRutaConCadenaConEspacios extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_114_RealizarBusquedaRutaConCadenaConEspacios_test(HashMap<String, String> data){
        testCase.setProject_Name("Portal Web: Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga del portal");
        u.get(url);
        u.refresh();
        u.hardWait(2);
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
        u.hardWait(3);
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
       addStep("Dar clic en el boton \"Liquidacion de Ruta\" para ingresar a la pantalla"
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
        addStep("Escribir una ruta con un espacio, por ejemplo: \"Ruta de Reparto\"");
        boolean resultStep7 = pc.validarInputRuta("Ruta de Reparto", true);
        if(resultStep7){
		   testCase.addTextEvidenceCurrentStep("Se intentÃ³ ingresar una cadena no vÃ¡lida (\"Ruta de Reparto\") y sÃ³lo aceptÃ³ los caracteres permitidos");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se pudo validar los caracteres no permitidos del campo");
        }
        assertTrue(resultStep6, "El campo no fue validado satisfactoriamente");

        
        u.getDriver().quit();

    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar que el filtro de ruta solo permita escritura alfanÃºmerica";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac E Garcia";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}

