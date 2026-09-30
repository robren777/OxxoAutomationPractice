package WEB.cometa.scripts.mvp2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;


/**
 * Validar que la informacion se vea correctamente en el comprobante de compra asi en CNT este desordenada 
 * MTC-FT-32-E1- Validar que en el comprobante de compra se visualice la regiï¿½n y plaza correcta
 * prerequisitos: Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_32_E1_ValidarQueEnElComprobanteDeCompraSeVisualiceLaRegionYplazaCorrecta extends BaseExecution {
	
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_32_E1_ValidarQueEnElComprobanteDeCompraSeVisualiceLaRegionYplazaCorrecta_test (HashMap<String, String> data) throws Exception {
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
		addStep("Ingresar a Comprobantes de compra y Resumen");
		boolean resultStep4 = pc.clicBotonCometaDirectos();
		if(resultStep4){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
		}
		assertTrue(resultStep4, "No se encontro el elemento Directos");

		boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
		if(resultStep4_1){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
		}
		assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		boolean resultStep5_1=pc.ConsultarGrid(8);
		if(resultStep5_1){
			testCase.addTextEvidenceCurrentStep("Se da clic en el registro del grid");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Grid");
		}
		assertTrue(resultStep5_1, "No se encontro el elemento Grid");
		
		addStep("Dar clic en un registro del grid para abrir el comprobante de compras");
		boolean resultStep5=pc.validarGrid();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se da clic en el registro del grid");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Grid");
		}
		assertTrue(resultStep5, "No se encontro el elemento Grid");

		

		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Validar que en el comprobante de compra se visualiza la region y plaza correcta");
		boolean resultStep6=pc.validarRegionComprobante();
		if(resultStep6){
			testCase.addTextEvidenceCurrentStep("Se valida la region del comprobante");
		} else {
			testCase.addTextEvidenceCurrentStep("Error al validar la region del comprobante");
		}
		assertTrue(resultStep6, "Error al validar la region del comprobante");
		
		u.getDriver().quit();
	}
	
	@AfterMethod
	public void afterMethod() {
	 u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales validas para el inicio de sesion de perfil DIRECTOS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la informacion se vea correctamente en el comprobante de compra asi en CNT este desordenad";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

}
