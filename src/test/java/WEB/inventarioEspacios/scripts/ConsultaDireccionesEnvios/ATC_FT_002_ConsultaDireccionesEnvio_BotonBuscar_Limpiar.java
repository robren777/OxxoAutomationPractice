package WEB.inventarioEspacios.scripts.ConsultaDireccionesEnvios;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_de_Direccion_de_Envio_IEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_ConsultaDireccionesEnvio_BotonBuscar_Limpiar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_ConsultaDireccionesEnvio_BotonBuscar_Limpiar_Test(HashMap<String, String> data) throws Exception {
	
		/*
		 * Utiler�as
		 *********************************************************************/
		
		SQLUtil dbFCIASQAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		Boolean validacion;
		String query;
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		Consulta_de_Direccion_de_Envio_IEP cde = new Consulta_de_Direccion_de_Envio_IEP(u, testCase);

		
		testCase.setTest_Description("ATC_FT_002_ConsultaDireccionesEnvio_BotonBuscar_Limpiar - "+ perfil +" " + negocio);

		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		validacion = pa.accesoCorrectoPortal(nameUser);
		assertTrue(validacion, "No se accede correctamente al portal de aplicaciones");
		
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep("Seleccionar Inventario de Espacios V2");
		pa.clickPortalInventarioEspacios();
		u.waitForLoadPage();
		validacion = mp.accesoCorrectoIEP();
		assertTrue(validacion, "No se ingresa al Inventario de espacios correctamente.");
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
		validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		if (validacion) {
		testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
		testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que se visualicen los MENUS");
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
		
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
			
		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu->Pedidos->Consulta de Direcciones de Env�o");
		
		switch(perfil.toUpperCase()) {
		case "ADMINISTRADOR": 
			if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				mp.clickPedidos();
			} else if (negocio.toUpperCase().equals("MARCA")) {
				mp.clickPedidosM();
			}
			
		break;
		
		case "ESTAFETA": mp.clickPedidosE();
		break;
		
		}
		
		mp.clickConsultaDirEnvios();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se abre la pantalla Consulta de Direcciones de Env�o con el siguiente contenido:\r\n" + 
				"-Filtros. \r\n" + 
				"-Nombre de direcci�n.\r\n" + 
				"-Plaza.\r\n" + 
				"-Tiendas.\r\n" + 
				"-Bot�n Nuevo.\r\n" + 
				"-Bot�n Limpiar.\r\n" + 
				"-Bot�n Buscar.\r\n" + 
				"-Grid principal con registros de direcciones.\r\n" + 
				"1. Plaza\r\n" + 
				"2. Nombre de Direcci�n de Env�o\r\n" + 
				"3. Calle\r\n" + 
				"4. N�mero\r\n" + 
				"5. Colonia\r\n" + 
				"6. Ciudad\r\n" + 
				"7. Estado\r\n" + 
				"8. C�digo Postal\r\n" + 
				"9. Nombre del Contacto\r\n" + 
				"10. Tel�fono del Contacto\r\n" + 
				"11. Entre calles: Mostrar los dos campos de Entre calles con la letra \"\"y\"\" separandolos\r\n" + 
				"12. Comentarios\r\n" + 
				"13. Tiendas: Deber� mostrar las tiendas a las que le ha sido relacionado la direcci�n de env�o.\r\n" + 
				"-Bot�n Editar.\r\n");
		
		validacion = cde.pantallaConsultaDireccionEnvio();
		assertTrue(validacion, "No se muestran correctamente los componentes en pantalla");
		
		validacion = cde.flechaInformacion();
		assertTrue(validacion, "No se muestran los Datos generales y Tiendas Asociadas");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Capturar nombre de direcci�n y seleccionar Plaza(realizar selecci�n m�ltiple de opciones)");
		
		validacion = cde.buscarDireccion(data.get("Nombre"), data.get("Plaza1"), data.get("Plaza2"), data.get("Plaza3"));
		assertTrue(validacion, "No se ingresaron correctamente los datos");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El sistema muestra el listado de todas las plazas del negocio del usuario.");
		
		validacion = cde.listadoPlazas();
		assertTrue(validacion, "No se muestra el listado de las plazas");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Seleccionar Tiendas y dar clic en Buscar.");
		
		validacion = cde.seleccionarTienda(data.get("Tienda"));
		assertTrue(validacion, "No se puede seleccionar la tienda " + data.get("Tienda"));
		cde.clickBuscar();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Dar clic en Limpiar");
		
		cde.clickLimpiar();
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Seleccionar nuevamente filtros de busqueda y dar clic en Buscar");
		
		validacion = cde.buscarDireccion(data.get("Nombre2"), data.get("Plaza4"), data.get("Tienda2"));
		assertTrue(validacion, "No se ingresaron correctamente los datos");
		
		cde.clickBuscar();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("El sistema deber� mostrar en el grid principal unicamente los registros que "
				+ "coincidan con los criterios de busqueda capturados y seleccionados");
		
		validacion = cde.resultadosBusqueda();
		assertTrue(validacion, "No se encontraron resultados con los datos proporcionados");
		
		
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
		return "ATC_FT_002_ConsultaDireccionesEnvio_BotonBuscar_Limpiar_Test";
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
