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
 * Validar que al descargarse el resumen venga con el formato de nombre correcto: 
	OC_Nï¿½mero de orden de compra ( esta adentro de la orden de compra)_Fecha que esta adentro del comprobante de compra en espaï¿½ol y en el orden que se pone en el ejemplo 
	Ejemplo: OC_1244280_24-07-2024
 * MTC-FT-37-E1- Validar el formato de nombre del resumen
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_37_E1_ValidarElFormatoDeNombreDelResumen extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_37_E1_ValidarElFormatoDeNombreDelResumen_test (HashMap<String, String> data) throws Exception {
		
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
		addStep("Realizar una busqueda mediante el llenado de filtros");
		pom.validarGrid();
		pom.llenaFiltros();
		pom.consultarComprobantes();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ir a la parte de descargar resumen de todos los comprobantes filtrados");
		pom.clickDescargar();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Validar que en la descarga del resumen de todos los comprobantes filtrados venga con el formato de nombre correcto");
		assertTrue(pom.comparaNombreExcel("Resumen"), "El formato del nombre es distinto");
		testCase.addScreenShotCurrentStep("Captura del formato");
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
		return "Validar que al descargarse el resumen venga con el formato de nombre correcto: \r\n"
				+ "OC_Numero de orden de compra ( esta adentro de la orden de compra)_Fecha que esta adentro del comprobante de compra en español y en el orden que se pone en el ejemplo \r\n"
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
