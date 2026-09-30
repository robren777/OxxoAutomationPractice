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

public class ATC_FT_001_RecepcionDeOrdenesDeCompra extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_RecepcionDeOrdenesDeCompra_test(HashMap<String, String> data) throws Exception {
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		Boolean validacion;
		u = new SeleniumUtil(new IETest(), true);
		Immex i = new Immex(u, testCase);
		
		testCase.setTest_Description("ATC_FT_001_RecepcionDeOrdenesDeCompra - " + user);

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
				+ "usuario y la frase Compras - Seleccionar la opci�n �rdenes de Compra - Receipts");
		
		validacion = i.selectResponsabilidadReceipts();
		assertTrue(validacion, "No se encontro alguna de las opciones");
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Seleccionar la opci�n Proveedor de la lista Tipo Origen - Capturar el n�mero de la orden de compra");
		
		validacion = i.findExpectedReceipts(data.get("Order"));
		assertTrue(validacion, "No se realizo correctamente la busqueda");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Capturar un comentario de la recepci�n - Capturar el n�mero de factura que ampara la compra");
		
		validacion = i.receiptHeader(data.get("Comentarios"), data.get("Order"));
		assertTrue(validacion, "No se capturo correctamente el comentario y el numero de factura");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Seleccionar la casilla del lado izquierdo para indicar la l�nea a recibir. - "
				+ "Capturar el Subinventario");
		
		validacion = i.receipts(data.get("Subinventory"));
		assertTrue(validacion, "No se capturaron correctamente los datos");		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Capturar N�mero de factura que ampara a la l�nea. - Presionar el bot�n Aceptar");
		
		validacion = i.typeReceivingTransaction(data.get("Order"));
		assertTrue(validacion, "No se capturo correctamente el numero de factura");	
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Presionar guardar para registrar la recepci�n. - Despu�s de guardar la recepci�n presionar el "
				+ "bot�n Cabecera.");
		
		validacion = i.clickGuardarCabecera();
		assertTrue(validacion, "No se pueden presionar los botones");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Al guardar los cambios, el sistema asigna un n�mero de Recepci�n.");
		
		validacion = i.showWindowReceiptHeader("IMMEX", "ReceiptHeader");
		assertTrue(validacion, "No se muestra la ventana Receipt Header");
		
		
		
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
		return "ATC_FT_001_RecepcionDeOrdenesDeCompra_test";
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
		//u.getDriver().quit();
	}
}
