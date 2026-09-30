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
 * Validar que me permita eliminar atributos seleccionados individualmente o varias de ellas sin eliminar todas
 * MTC-FT-93-HU6- Validar eliminar atributos seleccionados individualmente en el campo Zonas
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_93_HU6_ValidarEliminarAtributosSeleccionadosIndividualmenteEnElCampoZonas extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_93_HU6_ValidarEliminarAtributosSeleccionadosIndividualmenteEnElCampoZonas_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
		String user = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		u = new SeleniumUtil(new ChromeTest(), true);
		pom = new PortalCometa(u, testCase);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal web");
		u.get(data.get("Url"));
		u.refresh();
		pom.PrivatePortal();
		pom.validarAccesoLogin();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes para el inicio de sesion");
		pom.IniciarSesion(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar a la pagina de Comprobantes de Compra");
		boolean resultStep3 = pom.seleccionarPortalCometaQA();
		if(resultStep3){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
		} else{
			testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
		}
		assertTrue(resultStep3, "No es visible el elemento de Cometa");

		boolean resultStep3_1 = pom.clicBotonCometaDirectos();
		if(resultStep3_1){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
		}
		assertTrue(resultStep3_1, "No se encontro el elemento Directos");

		boolean resultStep3_2 = pom.ClicBotonComprobantesDeCompra();
		if(resultStep3_2){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
		}
		assertTrue(resultStep3_2,"No se encontro el elemento Comprobantes de Compra");
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Dar clic en el campo de Zona y agregar una mediante auto complete");
		boolean resultStep4 = pom.seleccionFiltroZona();
		if(resultStep4){
			testCase.addTextEvidenceCurrentStep("Se muestran las opciones de el filtro Zona");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo seleccionar el filtro de Zona");
		}
		assertTrue(resultStep4,"No se pudo seleccionar el filtro de Zona");

		boolean resultStep4_1 = pom.seleccionOpcionZona("1");
		if(resultStep4_1){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar la opcion de el filtro");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo seleccionar la opcion de el filtro");
		}
		assertTrue(resultStep4_1,"No se pudo seleccionar la opcion de el filtro");
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Agregar mas zonas mediante autocomplete");
		boolean resultStep5 = pom.seleccionAutocompleteZona();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se pudo agregar mas zonas mediante autocomplete");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo agregar mas opciones con el autocomplete");
		}
		assertTrue(resultStep5,"No se pudo agregar mas opciones con el autocomplete");

		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Eliminar zonas individualmente dando clic en la palomita");
		boolean resultStep6 = pom.limpiarSeleccionZonaOp("1");
		if(resultStep6){
			testCase.addTextEvidenceCurrentStep("Se pudo realizar la deseleccion de la opcion de el filtro");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realizar la deseleccion de la opcion de el filtro");
		}
		assertTrue(resultStep6,"No se pudo realizar la deseleccion de la opcion de el filtro");

		boolean resultStep6_1 = pom.limpiarSeleccionZonaOp("4");
		if(resultStep6_1){
			testCase.addTextEvidenceCurrentStep("Se pudo realizar la deseleccion de la opcion de el filtro");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realizar la deseleccion de la opcion de el filtro");
		}
		assertTrue(resultStep6_1,"No se pudo realizar la deseleccion de la opcion de el filtro");
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
		return "Validar que me permita eliminar atributos seleccionados individualmente o varias de ellas sin eliminar todas";
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
