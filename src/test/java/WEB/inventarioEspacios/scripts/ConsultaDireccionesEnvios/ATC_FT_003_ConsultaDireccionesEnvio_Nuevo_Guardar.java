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

public class ATC_FT_003_ConsultaDireccionesEnvio_Nuevo_Guardar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_ConsultaDireccionesEnvio_Nuevo_Guardar_Test(HashMap<String, String> data) throws Exception {
	
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
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		Consulta_de_Direccion_de_Envio_IEP cde = new Consulta_de_Direccion_de_Envio_IEP(u, testCase);
		String query;
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		
		testCase.setTest_Description("ATC_FT_003_ConsultaDireccionesEnvio_Nuevo_Guardar - "+ perfil +" " + negocio);

		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		SQLResult ExecuteQuery = null;
		/*
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		validacion = pa.accesoCorrectoPortal(nameUser);
		assertTrue(validacion, "No se accede correctamente al portal de aplicaciones");
		*/
		
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
		/*
		validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		if (validacion) {
		testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
		testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		*/
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
				"     -Nombre de direcci�n.\r\n" + 
				"     -Plaza.\r\n" + 
				"     -Tiendas.\r\n" + 
				"-Bot�n Nuevo.\r\n" + 
				"-Bot�n Limpiar.\r\n" + 
				"-Bot�n Buscar.\r\n" + 
				"-Grid principal con registros de direcciones.\r\n" + 
				"-Bot�n Editar");
		
		validacion = cde.pantallaConsultaDireccionEnvio();
		assertTrue(validacion, "No se muestran correctamente los componentes en pantalla");
		
		validacion = cde.flechaInformacion();
		assertTrue(validacion, "No se muestran los Datos generales y Tiendas Asociadas");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Dar clic en el bot�n Nuevo");
		
		cde.clickNuevoBtn();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El sistema deber� mostrar la pantalla de Captura de Direcciones de Env�o con el siguiente contenido:\r\n" + 
				"1. Plaza: Lista desplegable de Plazas.\r\n" + 
				"2. Nombre de Direcci�n de Env�o:\r\n" + 
				"3. Calle:\r\n" + 
				"4. N�mero:\r\n" + 
				"5. Colonia:\r\n" + 
				"6. Estado: Lista desplegable de Estados de M�xico.\r\n" + 
				"7. Ciudad: Lista desplegable de Ciudades, de acuerdo al estado seleccionado previamente.\r\n" + 
				"8. C�digo Postal:\r\n" + 
				"9. Nombre del Contacto:\r\n" + 
				"10. Tel�fono del contacto: Debe validar la captura a 10 d�gitos num�ricos\r\n" + 
				"11. Entres Calles: Dos campos para capturar dos calles separados por una \"\"y\"\"\r\n" + 
				"12. Comentarios:\r\n" + 
				"13. Tiendas Existentes/Tiendas Asociadas: Deber� mostrar las tiendas de la plaza seleccionada que no tengan una direcci�n de env�o para que pueda moverlas a Tiendas Asociadas si se desea que esa sea su nueva direcci�n de env�o.\r\n" + 
				"Todos los campos son obligatorios, excepto Comentarios y Tiendas.");
		
		validacion = cde.pantallaNuevoDireccionEnvio();
		assertTrue(validacion, "No se muestran correctamente los componentes");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Capturar datos de direcci�n y seleccionar opciones de estado y ciudad\r\n");
		
		validacion = cde.capturarDatosNuevaDireccion(data.get("Plaza"), data.get("NomDireccion"), data.get("CP"), 
				data.get("Calle"), data.get("EntreCalle"), data.get("YEntreCalle"), data.get("NombreContacto"),
				data.get("TelefonoContacto"));
		assertTrue(validacion, "No se ingresaron correctamente los datos proporcionados");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Dar clic en  Tiendas");
		
		cde.clickTiendasAsociadas();
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("El sistema deber� mostrar la pesta�a Tiendas con el siguiente contenido:\r\n" + 
				"Lista desplegable de Plazas.\r\n" + 
				"Tiendas Existentes/Tiendas Asociadas: Deber� mostrar las tiendas de la plaza seleccionada que no tengan una direcci�n de env�o para que pueda moverlas a Tiendas Asociadas si se desea que esa sea su nueva direcci�n de env�o. \r\n" + 
				"En Tiendas Existentes se mostrar� la opci�n Seleccionar todas y por cada registro deber� mostrarse un checkbox.\r\n" + 
				"En tiendas Asociadas se mostrar� la opci�n Quitar todas, por cada registro se deber� mostrar una tachita de Eliminaci�n.");
		
		validacion = cde.pestanaTiendas();
		assertTrue(validacion, "No se muestran los componentes");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("Seleccionar Tiendas para asociar(selecci�n multiple de opciones)");
		
		validacion = cde.seleccionMultipleTiendas();
		assertTrue(validacion, "No se pueden seleccionar tiendas");
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		
		addStep("El sistema mostrar� en el contenedor Tiendas asociadas, las tiendas que fueron seleccionadas.");
		
		validacion = cde.tiendasSeleccionadas();
		assertTrue(validacion, "No se muestran las tiendas seleccionadas en el contendeor Tiendas asociadas");
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		
		addStep("Dar clic en Datos Generales");
		
		cde.clickDatosGenerales();
		
		/**
		 *                                             PASO 16
		 ******************************************************************************************/
		
		addStep("El sistema muestra nuevamente la pesta�a de Datos generales con la informaci�n anteriormente capturada");
		
		u.hardWait(5);
		validacion = cde.datosCapturados(data.get("Plaza"), data.get("NomDireccion"), data.get("CP"), 
				data.get("Calle"), data.get("EntreCalle"), data.get("YEntreCalle"), data.get("NombreContacto"),
				data.get("TelefonoContacto"));
		validacion = true;
		assertTrue(validacion, "No se muestran los datos capturados");
		
		/**
		 *                                             PASO 17
		 ******************************************************************************************/
		
		addStep("Dar clic en Guardar");
		
		cde.clickGuardarNuevoBtn();
		cde.clickConfirmar();
		
		/**
		 *                                             PASO 18
		 ******************************************************************************************/
		
		addStep("El sistema debe validar que no exista el mismo nombre de direcci�n de env�o. En caso de capturar el"
				+ " mismo nombre, mostrar el siguiente mensaje: \"\"Ya existe una direcci�n de env�o con el mismo "
				+ "nombre\"\" y no permitir guardar el registro hasta que se capture uno diferente. La validaci�n "
				+ "debe ser por Plaza, Nombre de Direcci�n de Env�o, es decir puede existir el mismo nombre de "
				+ "direcci�n de env�o para diferentes plazas.\r\n" + 
				"En caso de cumplir con las validaciones, el sistema deber� guardar exitosamente el registro y "
				+ "volver a la pantalla anterior.");
		
		validacion = cde.mensajeConfirmacionNuevo();
		assertTrue(validacion, "No se guardo correctamente la direccion de env�o");
		
		/**
		 *                                             PASO 19
		 ******************************************************************************************/
		
		addStep("Ingresar al esquema de BD XXINE a consultar la tabla XXINE_CAT_ENVIOS y validar que se genere el nuevo registro.");
		
		query = "SELECT ENVIO_ID, NOMBRE_ENVIO, CALLE, NUMERO, COLONIA, CIUDAD, ESTADO, CODIGO_POSTAL "
				+ "FROM " + db + ".XXINE_CAT_ENVIOS " + 
				"WHERE NOMBRE_ENVIO = '"+ data.get("NomDireccion") +"' AND CALLE = '"+ data.get("Calle") +"'";
						
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se encontr� la direccion de envio en la BD");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontr� la direccion de envio en la BD");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
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
		return "ATC_FT_003_ConsultaDireccionesEnvio_Nuevo_Guardar_Test";
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