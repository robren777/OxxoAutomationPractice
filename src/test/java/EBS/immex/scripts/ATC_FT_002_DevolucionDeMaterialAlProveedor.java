package EBS.immex.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import EBS.immex.om.Immex;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_DevolucionDeMaterialAlProveedor extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_DevolucionDeMaterialAlProveedor_test(HashMap<String, String> data) throws Exception {
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		Boolean validacion;
		u = new SeleniumUtil(new IETest(), true);
		Immex i = new Immex(u, testCase);
		
		testCase.setTest_Description("ATC_FT_002_DevolucionDeMaterialAlProveedor - " + user);

		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar a la aplicaci�n Oracle (IMMEX)");
		
		u.get(url);
		validacion = i.loginImmex(user, password);
		assertTrue(validacion, "No se puede ingresar a la aplicacion Oracle");

		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep("Seleccionar la Responsabilidad que contenga el CR de la plaza a la cual pertenece el "
				+ "usuario y la frase Compras - Seleccionar la opci�n �rdenes de Compra - Returns");
		
		validacion = i.selectResponsabilidadReturns();
		assertTrue(validacion, "No se encontro alguna de las opciones");
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Capturar el n�mero de la orden de compra - Presionar el bot�n Encontrar ");
		
		validacion = i.findExpectedReturns(data.get("Order"));
		assertTrue(validacion, "No se realizo correctamente la busqueda");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Seleccionar la l�nea a devolver. - Capturar la cantidad a devolver. "
				+ "- En el campo Devolver A seleccionar el valor Proveedor");
		
		validacion = i.receivingReturns();
		assertTrue(validacion, "No se ingresaron los datos de linea a devolver correctamente");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Guardar la devoluci�n");
		
		validacion = i.clickGuardar();
		assertTrue(validacion, "No se guardo correctamente la devolucion");
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_DevolucionDeMaterialAlProveedor_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
