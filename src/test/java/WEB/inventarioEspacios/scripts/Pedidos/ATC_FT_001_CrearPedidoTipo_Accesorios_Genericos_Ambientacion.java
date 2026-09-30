package WEB.inventarioEspacios.scripts.Pedidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.PedidosAdministracionIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_CrearPedidoTipo_Accesorios_Genericos_Ambientacion extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_CrearPedidoTipo_Accesorios_Genericos_Ambientacion_test(HashMap<String, String> data) throws Exception {
		
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
				
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		
		//        							PASO 3
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
		
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
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		//									PASO 6
		mp.clickMenus();
		addStep("Crear Nuevo pedido. Seleccionar Material POP y Accesorios y dar clic en siguiente");
		switch(perfil.toUpperCase()) {
		case "ADMINISTRADOR": 
			if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				mp.clickPedidos();
			} else if (negocio.toUpperCase().equals("MARCA")) {
				mp.clickPedidosM();
			}
			
		break;
		
		}
		mp.clickCrearPedidosOp();
		u.waitForLoadPage();
		pedAdmin.seleccionarNuevoPedido(data.get("NuevoPedido"));
		pedAdmin.clickBoton(pedAdmin.getSiguienteBtnTP());
		
		//									PASO 7
		
		addStep("Validar que el sistema despliege la pantalla de  \"Datos Receptor\" y se visualice: Plaza, SubPlaza, Direccion y Agregar otra direccion");
		validacion = pedAdmin.validarCamposDatosReceptor();
		assertTrue(validacion, "No se visualizan los campos Plaza, SubPlaza, Direccion y Agregar otra direccion");
		
		//									PASO 8
		
		addStep("Llenar los campos requeridos con direccion de envio y dar clic en siguiente");
		validacion = pedAdmin.MPADatosReceptor(data.get("Plaza"), data.get("SubPlaza"), data.get("Direccion"));
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
		pedAdmin.clickSiguienteBtn2();
		
		//									PASO 9
		
		addStep("Validar que el sistema despliegue la pantalla de \"Seleccionar POPS\" y se visualice: Nombre de POP, Tipo de Pedido, ID de POP, Boton de busqueda");
		validacion = pedAdmin.validarPantallaSeleccionarPOPs();
		assertTrue(validacion, "No se muestran los filtros");
		
		//									PASO 10
		
		addStep("Realizar una busqueda de POP con <Tipo_Pedido>");
		pedAdmin.selectTipoPedido(data.get("TipoPedido"));
		
		//									PASO 11
		
		addStep("Validar que se visualicen los resultados de la busqueda con los datos, que se habiliten las columnas y se visualicen las funciones Guardar, Anterior y Siguiente");
		Integer col = 0;
		if (data.get("TipoPedido").equals("Ambientaci�n de Tiendas")) {
			col = 11;
		} else {
			col = 9;
		}
		validacion = pedAdmin.resultadosBusquedaPOPs(col);
		assertTrue(validacion, "No se muestran los resultados de la busqueda y/o las columnas no se habilitaron");
		validacion = pedAdmin.validarBotonesPOPs();
		assertTrue(validacion, "No se muestran los botones");
		
		//									PASO 12
		
		addStep("Ingresar una cantidad a los  POP's que se desee agregar y dar clic en el boton de \"Agregar\" y \"Guardar\".");
		validacion = pedAdmin.agregarCantidad(data.get("Cantidad"), data.get("NuevoPedido"));
		assertTrue(validacion, "No se pueden agregar POPs al pedido");
		pedAdmin.guardarPops();
		
		//									PASO 13
		
		addStep("Validar que el sistema muestre mensaje del folio");
		String cadena = pedAdmin.generarFolio(data.get("Plaza"), data.get("TipoPedido"));
		String folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
		}
		
		//									PASO 14
		
		addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador (estatus=1)");
		
		query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ESTATUS").equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//										PASO 15
		addStep("Validar que se visualize la pantalla de \"Confirmar Pedido\" donde se visualiza: Material Solicitado\r\n" + 
				"*Paquete\r\n" + 
				"*Promocion\r\n" + 
				"*POP\r\n" + 
				"*Cantidad\r\n" + 
				"*Comentarios\r\n" + 
				"y en la parte inferior derecha los botones de \" Guardar\" , \"Anterior\", \"Crear\"");
		
		pedAdmin.clickBoton(pedAdmin.getSiguienteBtnSP());
		validacion = pedAdmin.resultadosBusquedaGenerarPedido(6);
		assertTrue(validacion, "No se visualiza correctamente la pantalla de Confirmar Pedidos");
		
		//										PASO 16
		
		addStep("Agregar comentarios y dar click en boton \"Guardar\"");
		pedAdmin.typingInput(pedAdmin.getComentarios(), data.get("Comentarios"));
		pedAdmin.clickBoton(pedAdmin.getGuardarGP()); 
		pedAdmin.clickBoton(pedAdmin.getConfirmar());
		
		
		//										PASO 17
		
		addStep("Validar que se visualize el folio del pedido con la nomenclatura: PLAZA+TIPO  DE PEDIDO+ FECHA+FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA R�PIDA_DIC2014_12075.");
		cadena = pedAdmin.generarFolio(data.get("Plaza"), data.get("TipoPedido"));
		folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
		}
		
		//										PASO 18
		
		addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador  (estatus=1).");
		
		query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ESTATUS").equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		
		query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el periodo mostrado en pantalla");
		}
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
		//										PASO 19
		
		addStep("Dar clic en el boton \"Crear\"");
		pedAdmin.clickBoton(pedAdmin.getCrear());
		pedAdmin.clickBoton(pedAdmin.getConfirmar());
		
		//										PASO 20
		
		addStep("Validar que se visualize el folio del pedido con la nomenclatura: PLAZA+TIPO  DE PEDIDO+ FECHA+FOLIO EJEMPLO: 10POZ_ANDATTI Y COMIDA R�PIDA_DIC2014_12075  y el sistema mostrara la pantalla \"Bandeja de pedidos\".");
		cadena = pedAdmin.generarFolio(data.get("Plaza"), data.get("TipoPedido"));
		folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
			
		}
		
		validacion = pedAdmin.bandejaPedidos();
		assertTrue(validacion, "No se muestra la Bandeja de Pedidos");
		
		//										PASO 21
		
		addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus por autorizar (estatus=2).");
		query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
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
		
		
		query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
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
		return "Construido.  Crear Pedidos Material POP y Accesorios Tipo Accesorios y Genericos";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CrearPedidoTipo_Accesorios_Genericos_Ambientacion_test";
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
