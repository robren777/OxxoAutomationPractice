package WEB.inventarioEspacios.scripts.BandejaDePedidos;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.BandejaPedidos;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_NumeroDeGuia extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_007_NumeroDeGuia_Test(HashMap<String, String> data) throws Exception {
		 /*
		  *  Utiler�as
		 *********************************************************************/
		
		SQLUtil dbFCIASQAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String nuevo = "Nuevo";
		String enviado = "Enviado";
		String ruta = "En Ruta";
		Boolean validacion;
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		BandejaPedidos bp = new BandejaPedidos(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		String query;
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		/*
		 * 								        PASO 1
		 *****************************************************************************************/
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		validacion = pa.accesoCorrectoPortal(nameUser);
		assertTrue(validacion, "No se accede correctamente al portal de aplicaciones");
		
		
		/*
		 * 								        PASO 2
		 *****************************************************************************************/
		
		addStep("Seleccionar Inventario de Espacios V2");
		pa.clickPortalInventarioEspacios();
		u.waitForLoadPage();
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		validacion = mp.accesoCorrectoIEP();
		//assertTrue(validacion, "No se ingresa al Inventario de espacios correctamente.");
		
		/*
		 * 								        PASO 3
		 *****************************************************************************************/
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
		validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		if (validacion) {
		testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
		testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		//assertTrue(validacion, "No Muestra la informaci�n");
		
		/*
		 * 								        PASO 4
		 *****************************************************************************************/
		
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
		
		/*
		 * 								        PASO 5
		 *****************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		/*
		 * 								        PASO 6
		 *****************************************************************************************/
		
		addStep("Seleccionar Menu->Pedidos->Bandeja de pedidos");
		
		switch(perfil.toUpperCase()) {
		
		case "ADMINISTRADOR": 
			if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				mp.clickPedidos();
			} else if (negocio.toUpperCase().equals("MARCA")) {
				mp.clickPedidosM();
			}	
		break;
		
		case "ESTAFETA":
			mp.clickPedidosE();
		break;
		
		}
		
		mp.clickBandejaPedidos();
		
		/*
		 * 								        PASO 7
		 *****************************************************************************************/
		
		addStep("Realizar la busqueda con todos los filtros, estatus \"por autorizar\" y tipo_Pedido: " + data.get("TipoPedido"));
		validacion = bp.busquedaTipoPedido(data.get("TipoPedido"), data.get("Estatus"), data.get("Plaza"));
		assertTrue(validacion, "No se pudo realizar la busqueda con los filtros Tipo pedido y Estatus");
		
		bp.clickBuscarBPBtn();
		
		validacion = bp.resultadosBusquedaBP(10);
		assertTrue(validacion, "No se muestran los resultados de la busqueda y/o las columnas no se habilitaron");
		
		/*
		 * 								        PASO 8
		 *****************************************************************************************/
		
		addStep("Dar clic en el folio del pedido");
		String folio = bp.getFolio("1");
		bp.clickFolio("1");
		
		/*
		 * 								        PASO 9
		 *****************************************************************************************/
		
		addStep("Se agrega numero de Guia para el pedido seleccionado con estatus \"Nuevo\"");
		
		validacion = bp.camposGuia(data.get("Guia"));
		u.hardWait(5);
		
		/*
		 * 								        PASO 10
		 *****************************************************************************************/
		
		//addStep("Validar que se replicara dicho numero de guia para todos los proveedores que "
			//	+ "correspondan al pedido de Empaque.");
		
		//assertTrue(validacion, "No se puede ingresar la informacion");
		
		/*
		 * 								        PASO 11
		 *****************************************************************************************/
		
		addStep("Validar que se guarde el numero de Guia para los proveedores y estatus=1 en la tabla XXINE_GUIAS_PEDIDO");
		
		String pedido = bp.getPedido(folio);
		query = "SELECT GUIAS_PEDIDO_ID, PEDIDO_HEADER_ID, NUMERO_GUIA, RECIBIDO_POR, ESTATUS_ID, PROVEEDOR_ID, FECHA_RECIBIDO "
				+ "FROM " + db + ".XXINE_GUIAS_PEDIDO "
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "' AND ESTATUS_ID = '1'";
		System.out.println(query);
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		if (!StatusQuery) {
			//testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_GUIAS_PEDIDO");
			System.out.println("se tiene el estatus 1");
		} else {			
			assertTrue(false, "No se encontro ningun registro con el estatus mostrado en pantalla");
		}
		u.hardWait(5);
		
		
		
		/*
		 * 								        PASO 12
		 *****************************************************************************************/
		
		addStep("Una vez que se envia el pedido el usuario cambia el estatus a \"Enviado\".");
		
		//validacion = 
				bp.cambiarEstatus(enviado);
				System.out.println("Se cambio a  enviado");
		//assertTrue(validacion, "No se puede cambiar el estatus de la guia");
		u.hardWait(5);
		/*
		 * 								        PASO 13
		 *****************************************************************************************/
		
		addStep("Validar que se guarde el estatus=2 para el numero de guia del pedido en la tabla XXINE_GUIAS_PEDIDO.");
		
		query = "SELECT GUIAS_PEDIDO_ID, PEDIDO_HEADER_ID, NUMERO_GUIA, RECIBIDO_POR, ESTATUS_ID, PROVEEDOR_ID, FECHA_RECIBIDO "
				+ "FROM " + db + ".XXINE_GUIAS_PEDIDO "
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "' AND ESTATUS_ID = '2'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_GUIAS_PEDIDO");
			System.out.println("se tiene el estatus 2");
		} else {			
			assertTrue(false, "No se encontro ningun registro con el estatus mostrado en pantalla");
		}
	
		u.hardWait(5);
		
		/*
		 * 								        PASO 14
		 *****************************************************************************************/
		
		addStep("Una vez que sale a ruta el pedido, el usuario cambia el estatus a \"En Ruta\"");
		
		//validacion = 
		bp.cambiarEstatus(ruta);
		System.out.println("Se cambio a  en ruta");
		u.hardWait(5);
		//bp.cambiarEstatus(enviado);
		//System.out.println("Se cambio a  enviado");
		//u.hardWait(5);
		assertTrue(validacion, "No se puede cambiar el estatus de la guia");
		
		/*
		 * 								        PASO 15
		 *****************************************************************************************/
		
		addStep("Validar que se guarde el estatus =3 para el numero de guia del pedido en la tabla XXINE_GUIAS_PEDIDO.");
		
		query = "SELECT GUIAS_PEDIDO_ID, PEDIDO_HEADER_ID, NUMERO_GUIA, RECIBIDO_POR, ESTATUS_ID, PROVEEDOR_ID, FECHA_RECIBIDO "
				+ "FROM " + db + ".XXINE_GUIAS_PEDIDO "
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "' AND ESTATUS_ID = '3'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_GUIAS_PEDIDO");
			System.out.println("se tiene el estatus 3");
		} else {			
			assertTrue(false, "No se encontro ningun registro con el estatus mostrado en pantalla");
		}
		
		u.hardWait(5);
		
		/*
		 * 								        PASO 16
		 *****************************************************************************************/
		
		addStep("Una vez que llegue el pedido a su destino el usuario agrega los datos de entrega:\r\n" + 
				"Recibido por:\r\n" + 
				"Fecha:\r\n" + 
				"Y cambia el estatus a recibido");
		
		validacion = bp.agregarDatosEntrega(data.get("RecibidoPor"), data.get("Fecha"));
		u.hardWait(5);
		assertTrue(validacion, "No se pueden ingresar los datos de entrega");
		
		validacion = bp.cambiarEstatus("Recibido");
		u.hardWait(5);
		assertTrue(validacion, "No se puede cambiar el estatus de la guia");
		
		/*
		 * 								        PASO 17
		 *****************************************************************************************/
		
		addStep("Validar que se guarde el nombre de la persona que recibio el pedido, los datos de fecha "
				+ "y estatus =4 para el numero de guia del pedido en la tabla XXINE_GUIAS_PEDIDO.");
		
		query = "SELECT GUIAS_PEDIDO_ID, PEDIDO_HEADER_ID, NUMERO_GUIA, RECIBIDO_POR, ESTATUS_ID, PROVEEDOR_ID, FECHA_RECIBIDO "
				+ "FROM " + db + ".XXINE_GUIAS_PEDIDO "
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "' AND ESTATUS_ID = '4'";
		
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_GUIAS_PEDIDO");
			System.out.println("se tiene el estatus 4");
		} else {			
			assertTrue(false, "No se encontro ningun registro con el estatus mostrado en pantalla");
		}
		
		//u.hardWait(5);
		
		/*
		 * 								        PASO 18
		 *****************************************************************************************/
		
		addStep("Cambiar el estatus del pedido a entregado");
		
		validacion = bp.validarEstatus("Entregado");
		u.hardWait(5);
		assertTrue(validacion, "No se cambio el estado del pedido");
		
		/*
		 * 								        PASO 19
		 *****************************************************************************************/
		
		//addStep("Validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER con estatus Entregado (estatus=6).");
		
		//query = "SELECT GUIAS_PEDIDO_ID, PEDIDO_HEADER_ID, NUMERO_GUIA, RECIBIDO_POR, ESTATUS_ID, PROVEEDOR_ID, FECHA_RECIBIDO "
			//	+ "FROM " + db + ".XXINE_GUIAS_PEDIDO "
			//	+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "' AND ESTATUS_ID = '6'";
		
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		//testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		//if (!StatusQuery) {
		//	testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_GUIAS_PEDIDO");
	//	} else {	
		//	assertTrue(false, "No se encontro ningun registro con el estatus mostrado en pantalla");
	//	}
	//	
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Numero de Guia";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_NumeroDeGuia_Test";
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

