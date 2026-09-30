package WEB.inventarioEspacios.scripts.Pedidos;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.BandejaPedidos;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.PedidosAdministracionIEP;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_006_CrearPedidoTipoFaltanteAutoguardado extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_006_CrearPedidoTipoFaltanteAutoguardado_test(HashMap<String, String> data) throws Exception {
		
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
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		PedidosAdministracionIEP pedAdmin = new PedidosAdministracionIEP(u, testCase);
		BandejaPedidos bp = new BandejaPedidos(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
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
		
		//									PASO 1
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		
		//        							PASO 2
				
		addStep("Seleccionar Inventario de Espacios V2");
		pa.clickPortalInventarioEspacios();
		
		//        							PASO 3
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		
		validacion = mp.verificarInformacion(nameUser, negocio, perfil);
		
		if (validacion) {
		testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
		testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n");
		
		//									PASO 4
		
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
		
		
		//									PASO 5
		
		addStep("El sistema muestra el Negocio, periodo y estatus del periodo.");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		//									PASO 6
		
		addStep("Seleccionar Menu->Pedidos->Crear Pedido");
		mp.clickPedidos();
		mp.clickCrearPedidosOp();	
		
		//									PASO 7
		
		addStep("Ingresar datos requeridos para para crear el <Tipo_Pedido> hasta llegar al paso 3, buscamos y agregamos pop�s al pedido.");
		u.waitForLoadPage();
		pedAdmin.seleccionarNuevoPedido(data.get("NuevoPedido"));
		pedAdmin.clickBoton(pedAdmin.getSiguienteBtnTP());
		
		//Datos Receptor
		validacion = pedAdmin.FDatosReceptor(data.get("Periodo"),data.get("Plaza"), data.get("SubPlaza"), data.get("Tienda"), data.get("Direccion"));
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
		pedAdmin.clickSiguienteBtn2();
		
		//Seleccionar Pops
		pedAdmin.typingNombrePop(data.get("NombrePop"));
		
		validacion = pedAdmin.resultadosBusquedaPOPs(6);
		assertTrue(validacion, "No se muestran los resultados de la busqueda y/o las columnas no se habilitaron");
		
		pedAdmin.agregarCantidad(data.get("Cantidad"), data.get("NuevoPedido"));
		
		//									PASO 8
		
		addStep("Damos clic en anterior");
		me.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		pedAdmin.clickBoton(pedAdmin.getAnteriorBtnSP());
		u.hardWait(1);
		
		//									PASO 9
		
		addStep("Damos clic en siguiente");
		me.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		pedAdmin.clickSiguienteBtn2();
		u.hardWait(1);
		
		//									PASO 10
		
		addStep("Validar que se active el  pasando los 20 seg despues de presionar el boton \"Agregar\" y se visualiza Folio del pedido.");
		String cadena = pedAdmin.generarFolio(data.get("Plaza"), data.get("TipoPedido"));
		String folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
		}
		
		//									PASO 11
		
		addStep("Validar que no se cree duplicado en BD en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador (estatus=1)");
		
		query = "SELECT count(*) FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		
		Integer row = Integer.parseInt(ExecuteQuery.getData(0, "COUNT(*)"));
		
		query = "SELECT FOLIO_PEDIDO, PEDIDO_HEADER_ID, PERIODO, PLAZA_ID, ESTATUS, FECHA_PEDIDO, USUARIO_PEDIDO, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_HEADER "
				+ "WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String pedido = ExecuteQuery.getData(0, "PEDIDO_HEADER_ID");
		if (row < 2) {
			if (ExecuteQuery.getData(0, "ESTATUS").equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("Se cre� dupliado en la base de datos");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		query = "SELECT count(*) FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + pedido + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		
		row = Integer.parseInt(ExecuteQuery.getData(0, "COUNT(*)"));
		
		query = "SELECT PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, POP_ID, CANTIDAD, CREATION_DATE, CREATED_BY, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_DETAIL "
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		
		if (row < 2) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("Se cre� dupliado en la base de datos");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//											PASO 12
		
		addStep("Agregar otro POP al pedido y dar clic en guardar.");
		
		Integer pops = pedAdmin.cantidadPops(9);
		
		if (pops >= 2) {
			pedAdmin.agregarCantidad(data.get("Cantidad"), data.get("TipoPedido"));
			me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			pedAdmin.guardarPops();
			cadena = pedAdmin.generarFolio(data.get("Plaza"), data.get("TipoPedido"));
			folio = pedAdmin.mensajeFolio(cadena);
			if (folio.length() > 1) {
				testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
			} else {
				assertTrue(false, "El sistema no muestra correctamente mensaje del folio");
			}
		}
		
		//											PASO 13
		
		addStep("Salir de la pantalla/sistema.");
		mp.clickMenus();
		mp.clickPedidos();
		mp.clickAdministracion();
		mp.clickPeriodoOp();
		
		//											PASO 14
		
		addStep("Validar que no se guarden duplicados en BD en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador (estatus=1)");
		
		query = "SELECT count(*) FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		
		row = Integer.parseInt(ExecuteQuery.getData(0, "COUNT(*)"));
		
		query = "SELECT FOLIO_PEDIDO, PEDIDO_HEADER_ID, PERIODO, PLAZA_ID, ESTATUS, FECHA_PEDIDO, USUARIO_PEDIDO, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_HEADER "
				+ "WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		pedido = ExecuteQuery.getData(0, "PEDIDO_HEADER_ID");
		if (row < 2) {
			if (ExecuteQuery.getData(0, "ESTATUS").equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("Se cre� dupliado en la base de datos");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		query = "SELECT count(*) FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + pedido + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		
		row = Integer.parseInt(ExecuteQuery.getData(0, "COUNT(*)"));
		
		query = "SELECT PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, POP_ID, CANTIDAD, CREATION_DATE, CREATED_BY, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_DETAIL "
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		
		if (row < 2) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("Se cre� dupliado en la base de datos");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//										PASO 15
		
		addStep("Ingresar al  Menu->Pedidos->Bandeja de pedidos");
		mp.clickMenus();
		mp.clickAdministracion();
		mp.clickPedidos();
		mp.clickBandejaPedidos();
		
		//											PASO 16
		
		addStep("Realizar la busqueda del pedido guardado con estatus borrador.");
		u.hardWait(5);
		validacion = bp.busquedaTipoPedido(data.get("TipoPedido"), data.get("Estatus"), data.get("Plaza"));
		assertTrue(validacion, "No se pudo realizar la busqueda con los filtros Tipo pedido y Estatus");
		
		bp.clickBuscarBPBtn();
		
		//											PASO 17
		
		addStep("Abrir el pedido dando clic en el folio");
		me.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		validacion = bp.buscarPedido(folio);
		assertTrue(validacion, "No se encontro el pedido");
		
		//											PASO 18
		
		addStep("Validar que se abra \"Detalle de pedido\" con los datos guardados en estatus borrador.");
		
		validacion = bp.validarDatosDetallePedidos(folio, data.get("TipoPedido"), data.get("Estatus"));
		assertTrue(validacion, "Los datos no corresponden al pedido");
		
		//											PASO 19
		
		addStep("Dar clic en editar pedido donde nos llevara al paso 3 de la creacion, terminar de crear el pedido dando guardar en el ultimo paso y despues crear.");
		
		bp.clickEditarPedido();
		me.explicitWaitToBeInvisible(Duration.ofSeconds(5));
		me.scrollToElement(pedAdmin.getSiguienteBtnTP());
		pedAdmin.clickBoton(pedAdmin.getSiguienteBtnTP());
		pedAdmin.typingComentarios(1, data.get("Comentarios"));
		//pedAdmin.typingComentarios(2, data.get("Comentarios"));
		pedAdmin.clickBoton(pedAdmin.getGuardarGP2()); 
		pedAdmin.clickBoton(pedAdmin.getConfirmar());
		
		
		//											PASO 20
		
		addStep("Validar que los folios se creen correctamente.");
		
		folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
			
		}
		
		u.hardWait(10);
		pedAdmin.clickBoton(pedAdmin.getCrear2());
		pedAdmin.clickBoton(pedAdmin.getConfirmar());
		
		//											PASO 21
		
		addStep("Validar que se cree el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus por autorizar (estatus=2).");
		
		query = "SELECT FOLIO_PEDIDO, PEDIDO_HEADER_ID, PERIODO, PLAZA_ID, ESTATUS, FECHA_PEDIDO, USUARIO_PEDIDO, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_HEADER "
				+ "WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ESTATUS").equals("2")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		query = "SELECT PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, POP_ID, CANTIDAD, CREATION_DATE, CREATED_BY, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_DETAIL "
				+ "WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Crear Pedidos Faltante Autoguardar/Boton guardar/anterior/siguiente";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_CrearPedidoTipoFaltanteAutoguardado_test";
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

