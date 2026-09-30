package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que la pestaÃ±a de entrega en cedis contenga las siguientes columnas en el grid de registros:
 * Fecha, CR Tienda, Nombre Tienda, Transferencia, # Canastillas, Devueltas, Bols en Entrega, FUE,
 * Tira de Transferencia, Evidencias FotogrÃ¡ficas.
 * MTC-FT-82- Validar diseÃ±o de columnas en BÃºsqueda HistÃ³rico CEDIS.
 * @author Jorge Hernandez
 * @date Junio 2024
*/
public class ATC_FT_82_ValidarColumnasBusquedasHistoricoCedis extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_82_ValidarColumnasBusquedasHistoricoCedis_test(HashMap<String, String> data) throws Exception{
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

        /*
		 *                                             PASO 4
		 ******************************************************************************************/
        addStep("Ingresar a la pagina Recepcion de Transferencias y Devoluciones de CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Cedis");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Cedis");
        }
        assertTrue(resultStep4, "No se encontro el elemento Cedis");

        boolean resultStep4_1 = pc.cedisRecepciondTransferyDev();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Recepcion de Transferencias");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion de Transferencias");
        }
        assertTrue(resultStep4_1, "No se encontro el elemento Recepcion de Transferencias");

        /*
		 *                                             PASO 5
		 ******************************************************************************************/
        addStep("Verificar las columnas del grid en la busqueda por historico de cedis");
        boolean resultStep5 = pc.validarColumnasGridCedisRecepcion();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se valida que las columnas especificadas se muestran");
        } else {
            testCase.addTextEvidenceCurrentStep("No se muestran todas las columnas");
        }
        assertTrue(resultStep5, "No se muestran todas las columnas");

        u.quit();
    }

    @Override
    public void beforeTest() {

    }
    
    @AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }

    @Override
    public String setPrerequisites() {
        return "1. Contar con credenciales vÃ¡lidas para el perfil de Cedis.";
    }

    @Override
    public String setTestDescription() {
        return "Validar que la pestaÃ±a de entrega en cedis contenga las siguientes columnas en el grid de registros: Fecha, CR Tienda, Nombre Tienda, Transferencia, # Canastillas, Devueltas, Bols en Entrega, FUE, Tira de Transferencia, Evidencias FotogrÃ¡ficas.";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez Sanchez";
    }

    @Override
    public String setTestFullName() {
        return null;
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
