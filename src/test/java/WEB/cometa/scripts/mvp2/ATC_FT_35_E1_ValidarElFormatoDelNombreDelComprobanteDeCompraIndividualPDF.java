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
 * Validar que al descargarse el comprobante de compra individual venga con el formato de nombre correcto:
	OC_Nï¿½mero de orden de compra_Fecha que esta adentro del json comprobante de compra en espaï¿½ol y en el orden que se pone en el ejemplo 
	Ejemplo: OC_1244280_24-07-2024
 * MTC-FT-35-E1- Validar el formato del nombre del comprobante de compra individual PDF
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_35_E1_ValidarElFormatoDelNombreDelComprobanteDeCompraIndividualPDF extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_35_E1_ValidarElFormatoDelNombreDelComprobanteDeCompraIndividualPDF_test (HashMap<String, String> data) throws Exception {
		
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
	        u.hardWait(3);
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
		pom.ClicPortalCometa();
		pom.clickDirectos();
		pom.clickComprobantesDeCompra();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Dar clic en un registro del grid para abrir el comprobante de compras");
		pom.validarGrid();
		pom.clickRegistro();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ir a la parte de descarga del comprobante de compra en formato PDF y dar clic en el boton");
		pom.clickDescargaPDF();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Validar que en la descarga del comprobante de compra en formato PDF venga con el formato de nombre correcto");
		pom.obtenerRegion();
		String orden = pom.obtenerTextoOrden();
		String fecha = pom.obtenerTextoFecha();
		assertTrue(pom.comparaNombre(orden, fecha), "El formato del nombre no es el correcto");
		testCase.addScreenShotCurrentStep("Formato correcto");
		u.getDriver().quit();
		
	}
	
	@AfterMethod
	public void afterMethod() {
		 // u.getDriver().quit();
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
		return "Validar que al descargarse el comprobante de compra individual venga con el formato de nombre correcto:\r\n"
				+ "OC_Numero de orden de compra_Fecha que esta adentro del json comprobante de compra en espaï¿½ol y en el orden que se pone en el ejemplo \r\n"
				+ "Ejemplo: OC_1244280_24-07-2024";
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
