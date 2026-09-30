package WEB.sarv3.scripts.actualizacionTecnologicaBPM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.sarv3.om.CatalogoDelincuentesSARv3;
import WEB.sarv3.om.MenuPrincipalSAR;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_013_SARV3_CatalogoDelincuentesCrearDelincuenteExterno extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_SARV3_CatalogoDelincuentesCrearDelincuenteExterno_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		CatalogoDelincuentesSARv3 cd = new CatalogoDelincuentesSARv3(u, testCase);
		MenuPrincipalSAR mp = new MenuPrincipalSAR(u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("Ingresar a la aplicaci�n con el usuario y contrase�a v�lidos");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Dar clic en Sistema de administraci�n de Riesgos V3");
		
		pa.clickSARv3();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Dar clic en Cat�logo de Delincuentes e ir a la opci�n de Crear Delincuente Externo.");
		
		mp.clickCatDelincuentes();
		mp.clickCrearDelincuente();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Llenar la informaci�n solicitada del delincuente y dar clic al bot�n Guardar Cambios.");
		
		validacion = cd.datosGeneralesDelincuente(data.get("Sexo"), data.get("Alias"), data.get("EdadDe"), 
				data.get("EdadHasta"));
		assertTrue(validacion, "No se ingresaron correctamente los datos generales");
		
		validacion = cd.mediaFiliacionDelincuente(data.get("EstaturaDe"), data.get("EstaturaHasta"), 
				data.get("Ojos"), data.get("ColorCab"), data.get("TamCabello"), data.get("TipoCabello"),
				data.get("Tez"), data.get("Cejas"), data.get("Barba"), data.get("Bigote"));
		assertTrue(validacion, "No se ingresaron correctamente los datos media filiacion");
		
		validacion = cd.vestimentaDelincuente(data.get("Camisa"), data.get("Pantalon"), data.get("Saco"), 
				data.get("Gorra"));
		assertTrue(validacion, "No se ingresaron correctamente los datos vestimenta");
		
		validacion = cd.modusOperandiDelincuente(data.get("ModusOperandi"));
		assertTrue(validacion, "No se ingresaron correctamente los datos modus operandi");
		
		validacion = cd.senasParticularesDelincuente(data.get("SenasParticulares"), data.get("Descripcion"));
		assertTrue(validacion, "No se ingresaron correctamente los datos se�as particulares");
		cd.clickAgregarSPBtn();
		
		validacion = cd.armaDelincuente(data.get("TipoArma"), data.get("SubtipoArma"), data.get("Arma"), 
				data.get("ColorArma"));
		assertTrue(validacion, "No se ingresaron correctamente los datos arma del delincuente");
		cd.clickAgregarArmaBtn();
		
		validacion = cd.vehiculoDelincuente(data.get("Vehiculo"));
		assertTrue(validacion, "No se ingresaron correctamente los datos vehiculo");
		
		cd.clickGuardarCambiosBtn();
		u.waitForLoadPage();
		u.hardWait(30);
		
		/**
		 * 												PASO 6
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Vincular a Reporte.");
	
		cd.clickVincularReporteBtn();
		u.waitForLoadPage();
		
		/**
		 * 												PASO 7
		 *****************************************************************************************/
		
		addStep("Ingresar n�mero de carpeta de banda y dar clic al bot�n Buscar.");
		
		validacion = cd.buscarDelincuentes(data.get("Alias"), data.get("FechaDesdeBus"), data.get("FechaHastaBus"));
		assertTrue(validacion, "No se ingresaron correctamente los datos para la busqueda");
		
		cd.clickBuscar();
		u.waitForLoadPage();
		
		/**
		 * 												PASO 8
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Ir a la Carpeta de Banda.");
		
		cd.clickCarpetaBandaBtn();
		u.waitForLoadPage();
		
		/**
		 * 												PASO 9
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Agregar otro Delincuente.");
		
		cd.clickAgregarDelincuenteBtn();
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "AgregarDelincuente");
		
		/**
		 * 												PASO 10
		 *****************************************************************************************/
		
		addStep("Dar clic a Eliminar Reporte.");
		
		cd.clickEliminarReporteBtn();
		
		
		/**
		 * 												PASO 11
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n Aceptar.");
		
		validacion = cd.confirmarAlerta();
		assertTrue(validacion, "No se pudo dar click en Aceptar");
		
		/**
		 * 												PASO 12
		 *****************************************************************************************/
		
		addStep("Dar clic al bot�n OK");
		
		validacion = cd.confirmarAlerta();
		assertTrue(validacion, "No se pudo dar click en Ok");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Catalogo de Delincuentes - Crear Delincuente Externo";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_SARV3_CatalogoDelincuentesCrearDelincuenteExterno_Test";
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
