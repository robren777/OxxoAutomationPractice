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
 * Validar que Como usuario puedo buscar los comprobantes de compra a traves de los siguientes filtros: proveedor, fuente de abasto, zona, region, plaza, tienda y rango de fechas . Tomando en cuenta el perfil de usuario
 * MTC-FT-27-HU4- Validar con usuario DIRECTOS ADMIN OPERATIVO, que solo se tiene acceso en el grid a los 3 aï¿½os mï¿½s recientes a partir de la fecha actual
 * prerequisitos:Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n de perfil DIRECTOS (ADMIN OPERATIVO)
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_27_HU4_ValConUsuDIRECTOSADMIN_OPERATIVOsoloTieneAccesoA3AnosMasRecientes extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_27_HU4_ValConUsuDIRECTOSADMIN_OPERATIVOsoloTieneAccesoA3AnosMasRecientes_test (HashMap<String, String> data) throws Exception {
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
		addStep("Realizar una busqueda filtrando menos de 3 anos de antiguedad");
		boolean resultStep5 = pc.clickRangoDeFechas();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar un rango de fechas");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo seleccionar un rango de fechas");
		}
		assertTrue(resultStep5,"No se pudo seleccionar un rango de fechas");

		boolean resultStep5_1 = pc.select2024();
		if(resultStep5_1){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar 2024");

		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo seleccionar 2024");
		}
		assertTrue(resultStep5_1,"No se pudo seleccionar 2024");

		boolean resultStep5_2 = pc.select2021();
		if(resultStep5_2){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar 2021");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo seleccionar 2021");
		}
		assertTrue(resultStep5_2,"No se pudo seleccionar 2021");
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
		return "Contar con credenciales validas para el inicio de sesion de perfil DIRECTOS (ADMIN OPERATIVO)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que Como usuario puedo buscar los comprobantes de compra a traves de los siguientes filtros: proveedor, fuente de abasto, zona, region, plaza, tienda y rango de fechas . Tomando en cuenta el perfil de usuario";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Zarate";
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
}
