package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que el botÃ³n de descargar guarde en el dispositivo un pdf con la informaciÃ³n de las Tiras de Transferencias.
* MTC-FT-81- Validar descarga masiva de Tira de Transferencia.
* @author Jorge Hernandez
* @date Julio 2024
*/
public class ATC_FT_81_ValidarDescargaMasivaTiraTrasnferencia extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_81_ValidarDescargaMasivaTiraTrasnferencia_test(HashMap<String, String> data) throws Exception{
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
        addStep("Realizar un filtrado"); //para poder realizar la descarga masiva en el paso 4
		boolean resultStep5 = pc.filtradoCedisPlaza(); //3 para retroceder a mayo estando en agosto
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se realizo una consulta usando filtros");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realizar la consulta");
		}
		assertTrue(resultStep5, "No se pudo realizar la consulta");


        /*
		 *                                             PASO 6
		 ******************************************************************************************/
         addStep("Click boton descargar del apartado descarga masiva de Tira de Transferencias");
         boolean resultStep6 = pc.cedisDescargaMasivaTiraTransf();
		 if(resultStep6){
			 testCase.addTextEvidenceCurrentStep("Se valida el boton y se realiza la descarga de el archivo");
		 } else {
			 testCase.addTextEvidenceCurrentStep("No se encontro el boton para realizar la descarga");
		 }
		 assertTrue(resultStep6, "No se encontro el boton para realizar la descarga");

		 boolean resultStep6_1 = pc.validarDescargaComprobanteCedisTiraTransferencia();
		 if(resultStep6_1){
			 testCase.addTextEvidenceCurrentStep("Se valida que se descargara el archivo");
		 } else {
			 testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		 }
		 assertTrue(resultStep6_1, "No se encontro el archivo");

		 boolean resultStep6_2 = pc.openLastFileDownloadFolder("ZIP");
		 if(resultStep6_2){
			 testCase.addTextEvidenceCurrentStep("Se abre el archivo descargado");
		 } else {
			 testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		 }
		 assertTrue(resultStep6_2, "No se encontro el archivo");

        u.quit();
    }

    @Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con credenciales vÃ¡lidas para el perfil de Cedis.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el botÃ³n de descargar guarde en el dispositivo un pdf \n" +
				"con la informaciÃ³n de las Tiras de Transferencias.";
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
