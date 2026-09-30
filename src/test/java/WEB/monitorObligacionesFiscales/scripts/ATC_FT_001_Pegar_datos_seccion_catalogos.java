package WEB.monitorObligacionesFiscales.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.monitorObligacionesFiscales.om.Catalogos;
import WEB.monitorObligacionesFiscales.om.MenuPrincipal_MonitorObligaciones;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Pegar_datos_seccion_catalogos extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_Pegar_datos_seccion_catalogos_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");

		String Plaza = data.get("Plaza");
		String RFC = data.get("");
		String RazonSocial = data.get("Razon_Social");
		String Estado = data.get("");
		String Ciudad = data.get("");
		String Colonia = data.get("");
		String Calle = data.get("");
		String Numero = data.get("");
		String Email = data.get("");
		String Telefono = data.get("");
		String Parametro = data.get("Parametro");
		String Dato = data.get("Dato");
		String Concepto = data.get("Concepto");
		String msg = "La operación fue exitosa.";

		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_MonitorObligaciones mo = new MenuPrincipal_MonitorObligaciones(u, testCase);
		Catalogos moC = new Catalogos(u,testCase);
		//Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion;
		
    // Paso 1
		addStep("Ingresar al portal de aplicaciones");

		u.get(url);
		u.hardWait(3);
		pa.proceedToPortalIEsecureSite();


		// Paso 2
		addStep("El usuario ingresa usuario y contraseÃ±a y da clic en el botÃ³n <Iniciar SesiÃ³n>.");

		pa.cicloLogin(user, password);
		// Paso 3
		addStep("El usuario da clic en opciÃ³n de Monitor de Obligaciones Fiscales.");
		pa.clickPortalMonitorObligacionesFiscales(1);
		
	
		 /**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar la opción Catálogos --> Despachos");
		mo.clickCatalogos();
		u.hardWait(2);
		mo.clickOpcionDespachos();
		moC.llenarInformacionDespachos(Plaza, RazonSocial);
		assertTrue(moC.validarMensajeSuccess(msg),"El mensaje no coincide.");
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Seleccionar la opción Catálogos --> Parametros");
		mo.clickCatalogos();
		u.hardWait(2);
		mo.clickOpcionParametros();
		moC.crearParametros(Parametro, Dato);
		assertTrue(moC.validarMensajeSuccess(msg),"El mensaje no coincide.");
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Seleccionar la opción Catálogos --> Deducciones");
		mo.clickCatalogos();
		u.hardWait(2);
		mo.clickOpcionDeducciones();
		moC.crearDeduccion(Concepto);
		assertTrue(moC.validarMensajeSuccess(msg),"El mensaje no coincide.");
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
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	public static void main(String[] args) {
		String password = PasswordUtil.decryptPassword("B687471984488B062C0E0333190EA5D9");
		System.out.println(password);
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}
