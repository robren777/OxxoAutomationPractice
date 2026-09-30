package WEB.inventarioEspacios.scripts.Pedidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.PedidosAdministracionIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_CrearPedidoTipoFaltante extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_CrearPedidoTipoFaltante_test(HashMap<String, String> data) throws Exception {
		
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
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
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
		
		addStep("Ingresar al portal: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <<<Usuario>>>");
		
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		u.waitForLoadPage();
		
		//        							PASO 2
		
		addStep("Se accede correctamente al portal de aplicaciones.");
		
		boolean validarAcceso = pa.validarAccesoPortalAplicaciones();
		
		assertTrue(validarAcceso, "No se pudo acceder al portal de aplicaciones");
		
		//        							PASO 3
				
		addStep("Seleccionar Inventario de Espacios Publicitarios V2");
	
		pa.clickPortalInventarioEspacios();
		u.waitForLoadPage();	
		
		//        							PASO 4
		
		addStep("Se ingresa al Inventario de espacios correctamente.");
		
		u.hardWait(5);

		if(pa.visualizarPortalAplicaciones()) {
			
			u.hardWait(5);
			pa.cicloLogin2(user, password);
			u.waitForLoadPage();
			u.hardWait(1);
			pa.clickPortalInventarioEspaciosSinScreen();
			u.waitForLoadPage();	
			
		}
		
		boolean validarAccesoIEP = mp.accesoCorrectoIEP();
		
		assertTrue(validarAccesoIEP, "No se pudo acceder al portal Inventario de Espacios Publicitarios V2");
		
		//        							PASO 3
		/*
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//boolean StatusQuery = ExecuteQuery.isEmpty();
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
		*/
		
		//									PASO 5
		addStep("Seleccionar Menu->Pedidos->Crear Pedido");
		
		mp.clickMenus();
		mp.clickPedidos();
		mp.clickCrearPedidosOp();
		u.waitForLoadPage();
		
		//									PASO 6	
		addStep("Se muestra pantalla de crear pedido");
		
		boolean validarAccesoPantalla = pedAdmin.visualizarPantalla();
		testCase.addScreenShotCurrentStep("accesoPantallaCrearPedido");
		assertTrue(validarAccesoPantalla, "No se muestra pantalla de crear pedido");
		
		
		//									PASO 7
		addStep("Seleccionar CheckBox->Faltante y dar clic en siguiente");
			
		pedAdmin.seleccionarNuevoPedido(data.get("NuevoPedido"));
		pedAdmin.clickBoton(pedAdmin.getSiguienteBtnTP());
		
		//									PASO 8
		
		addStep("Validar que el sistema despliege la pantalla de  \"Datos Receptor\" y se visualiza los siguientes campos de selecci�n obligatorios:\r\n" + 
				"Periodo: debe habilitar un campo de selecci�n y desplegar los periodos abiertos que se dieron de alta desde el m�dulo de Administraci�n \"Abrir Periodo\"\r\n" + 
				"Plaza: mostrar solo la plaza a la cual pertenece el Usuario plaza que ingres� y permitirle seleccionarla, el usuario Administrador puede seleccionar cualquier plaza del negocio correspondiente. \r\n" + 
				"Sub plaza: debe mostrar las subplazas ligadas a la plaza seleccionada en el primer filtro (solo aplica para las Mega)\r\n" + 
				"Tienda: una vez seleccionada la Plaza o Sub Plaza, debe habilitar un campo de selecci�n donde se desplieguen todas las tiendas que correspondan a la plaza seleccionada\r\n" + 
				"Direcci�n: este campo mostrar� el listado de direcciones validas para cada plaza, las cuales se administran desde el cat�logo \"Direcciones de Env�o\"\r\n" + 
				"Agregar otra direcci�n: este campo se utiliza para ingresar una direcci�n manualmente\r\n" + 
				"Agregar otra direcci�n: este campo se utiliza para ingresar una direcci�n manualmente");
		validacion = pedAdmin.validarCamposDatosReceptor();
		assertTrue(validacion, "No se visualizan los campos Plaza, SubPlaza, Direccion y Agregar otra direccion");
		
		//									PASO 9
		
		addStep("Llenar los campos requeridos con direccion de envio y dar clic en siguiente.");
		
		//validacion = pedAdmin.FDatosReceptor(data.get("Periodo"),data.get("Plaza"), data.get("SubPlaza"), data.get("Tienda"), data.get("Direccion"));
		validacion = pedAdmin.FDatosReceptorPorPosicionPeriodo(data.get("PosicionPeriodo"),data.get("Plaza"), data.get("SubPlaza"), data.get("Tienda"), data.get("Direccion"));
		String periodo = pedAdmin.obtenerPeriodoSeleccionado().replace(" ","");
		System.out.print("-"+periodo.replace(" ","")+"-");
		
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
		pedAdmin.clickSiguienteBtn2();
		
		//									PASO 10
		
		addStep("Validar que el El sistema despliege la pantalla de \"Seleccionar POPS\" y se visualiza lo siguiente:\r\n" + 
				"Nombre de POP\r\n" + 
				"Tipo de Pedido\r\n" + 
				"ID de POP \r\n" + 
				"Boton de Busqueda");
		
		validacion = pedAdmin.validarPantallaSeleccionarPOPs();
		assertTrue(validacion, "No se muestran los filtros");
		
		//									PASO 11
		
		addStep("Realizar una busqueda de POP con <Tipo_Pedido>");
		
		pedAdmin.typingNombrePop(data.get("NombrePop"));
		
		//									PASO 12
		
		addStep("Validar que Se visualizen los resultados de la busqueda con los datos:\r\n" + 
				"El sistema debe habilitar las siguientes columnas para el tipo de pedido <Tipo_Pedido>:\r\n" + 
				"Nombre de POP\r\n" + 
				"Boton de Busqueda\r\n" + 
				"Se despliega en el grid los POP�s que corresponden a Faltante  con los siguientes campos:\r\n" + 
				"Folio: (campo nuevo, muestra el FolioPrd)\r\n" + 
				"Paquete\r\n" + 
				"Promoci�n\r\n" + 
				"POP\r\n" + 
				"Cantidad: Campo num�rico para que el usuario capture, est� limitado a la cantidad m�xima que se carga en el archivo BUENOTE.");
		
		validacion = pedAdmin.resultadosBusquedaPOPs(6);
		assertTrue(validacion, "No se muestran los resultados de la busqueda y/o las columnas no se habilitaron");
		
		//									PASO 13
		
		addStep("Ingresar una cantidad a los  POP's que se desee agregar y dar clic en el boton de \"Agregar\" y \"Guardar\".");
		
		pedAdmin.agregarCantidad(data.get("Cantidad"), data.get("NuevoPedido"));
		pedAdmin.guardarPops();
		
		//									PASO 14 - cambiar lo del folio para que acepte el periodo
		
		addStep("Validar que el sistema muestre mensaje del folio: PERIODO+PLAZA+TIPO  DE PEDIDO+ FOLIO EJEMPLO: P12015_10CLJ_FALTANTE-ADICIONAL_12080 para el pedido guardado.");
		String cadena = pedAdmin.generarFolio3(periodo, data.get("Plaza"), data.get("TipoPedido"));
		String folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
		}
		
		//									PASO 15
		
		addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador (estatus=1)");
		
		u.hardWait(5);
		//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		String queryH1 = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, TIPO_PEDIDO_ID, ESTATUS FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		String queryH2 = "SELECT PLAZA_ID, PERIODO, FECHA_PEDIDO, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		String queryH3 = "SELECT SUBPLAZA_ID, CREATION_DATE, CREATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
			
		System.out.print(queryH1);
		
		SQLResult ExecuteQueryH1 = executeQuery(dbFCIASQAS, queryH1);
		SQLResult ExecuteQueryH2 = executeQuery(dbFCIASQAS, queryH2);
		SQLResult ExecuteQueryH3 = executeQuery(dbFCIASQAS, queryH3);
		boolean StatusQueryH = ExecuteQueryH1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH1);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH2);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH3);
		
		if (!StatusQueryH) {
			if (ExecuteQueryH1.getData(0, "ESTATUS").equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
				Assert.fail("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el pedido mostrado en pantalla");
			Assert.fail("No se guardo el pedido en la tabla XXINE_PEDIDOS_HEADER");
		}

		
		
		//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		String queryD1 = "SELECT PEDIDO_HEADER_ID, PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, PROMOCION_ID FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";	
		String queryD2 = "SELECT POP_ID, TIPO_POP_ID, CANTIDAD, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
		String queryD3 = "SELECT CREATION_DATE, CREATED_BY, COMENTARIOS FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
		
		System.out.print(queryD1);
		
		SQLResult ExecuteQueryD1 = executeQuery(dbFCIASQAS, queryD1);
		SQLResult ExecuteQueryD2 = executeQuery(dbFCIASQAS, queryD2);
		SQLResult ExecuteQueryD3 = executeQuery(dbFCIASQAS, queryD3);
		boolean StatusQueryD = ExecuteQueryD1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD1);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD2);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD3);
		
		if (!StatusQueryD) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el pedido mostrado en pantalla");
			Assert.fail("No se encontro el pedido en la tabla XXINE_PEDIDOS_DETAIL");
		}

		
		//										PASO 16
		addStep("Validar que se visualize la pantalla de \"Confirmar Pedido\" donde se visualiza :\r\n" + 
				"Material Solicitado\r\n" + 
				"*Paquete\r\n" + 
				"*Promocion\r\n" + 
				"*POP\r\n" + 
				"*Cantidad\r\n" + 
				"*Comentarios\r\n" + 
				"y en la parte inferior derecha los botones de \" Guardar\" , \"Anterior\", \"Crear\"");
		
		pedAdmin.clickBoton(pedAdmin.getSiguienteBtnSP());
		validacion = pedAdmin.resultadosBusquedaGenerarPedido(5);
		assertTrue(validacion, "No se visualiza correctamente la pantalla de Confirmar Pedidos");
		
		//										PASO 17
		
		addStep("Agregar comentarios y dar click en boton \"Guardar\"");
		pedAdmin.typingInput(pedAdmin.getComentarios(), data.get("Comentarios"));
		pedAdmin.clickBoton(pedAdmin.getGuardarGP()); 
		pedAdmin.clickBoton(pedAdmin.getConfirmar());
		
		
		//										PASO 18
		
		addStep("Validar que se visualize el folio del pedido con la nomenclatura: PERIODO+PLAZA+TIPO  DE PEDIDO+ FOLIO EJEMPLO: P12015_10CLJ_FALTANTE-ADICIONAL_12080.");
		cadena = pedAdmin.generarFolio3(periodo,data.get("Plaza"), data.get("TipoPedido"));
		folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
		}
		
		//										PASO 19
		
		addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus borrador  (estatus=1).");
		u.hardWait(5);
		//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		queryH1 = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, TIPO_PEDIDO_ID, ESTATUS FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		queryH2 = "SELECT PLAZA_ID, PERIODO, FECHA_PEDIDO, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		queryH3 = "SELECT SUBPLAZA_ID, CREATION_DATE, CREATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		
		ExecuteQueryH1 = executeQuery(dbFCIASQAS, queryH1);
		ExecuteQueryH2 = executeQuery(dbFCIASQAS, queryH2);
		ExecuteQueryH3 = executeQuery(dbFCIASQAS, queryH3);
		boolean StatusQueryH2 = ExecuteQueryH1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH1);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH2);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH3);
		
		if (!StatusQueryH2) {
			if (ExecuteQueryH1.getData(0, "ESTATUS").equals("1")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
				Assert.fail("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el pedido mostrado en pantalla");
			Assert.fail("No se guardo el pedido en la tabla XXINE_PEDIDOS_HEADER");
		}

		
		
		//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		queryD1 = "SELECT PEDIDO_HEADER_ID, PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, PROMOCION_ID FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";	
		queryD2 = "SELECT POP_ID, TIPO_POP_ID, CANTIDAD, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
		queryD3 = "SELECT CREATION_DATE, CREATED_BY, COMENTARIOS FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
		ExecuteQueryD1 = executeQuery(dbFCIASQAS, queryD1);
		ExecuteQueryD2 = executeQuery(dbFCIASQAS, queryD2);
		ExecuteQueryD3 = executeQuery(dbFCIASQAS, queryD3);
		boolean StatusQueryD2 = ExecuteQueryD1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD1);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD2);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD3);
		
		if (!StatusQueryD2) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el pedido mostrado en pantalla");
			Assert.fail("No se encontro el pedido en la tabla XXINE_PEDIDOS_DETAIL");
		}

		
		//										PASO 20
		
		addStep("Dar clic en el boton \"Crear\"");
		pedAdmin.clickBoton(pedAdmin.getCrear());
		pedAdmin.clickBoton(pedAdmin.getConfirmar());
		
		//										PASO 21
		
		addStep("Validar que se visualize el folio del pedido con la nomenclatura: PERIODO+PLAZA+TIPO  DE PEDIDO+ FOLIO EJEMPLO: P12015_10CLJ_FALTANTE-ADICIONAL_12080 y el sistema mostrara la pantalla \"Bandeja de pedidos\".");
		cadena = pedAdmin.generarFolio3(periodo,data.get("Plaza"), data.get("TipoPedido"));
		folio = pedAdmin.mensajeFolio(cadena);
		if (folio.length() > 1) {
			testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente mensaje del folio");
		} else {
			testCase.addTextEvidenceCurrentStep("El sistema no muestra correctamente mensaje del folio");
			
		}
		
		validacion = pedAdmin.bandejaPedidos();
		assertTrue(validacion, "No se muestra la Bandeja de Pedidos");
		
		//		*								PASO 22
		
		addStep("Ingresar a la BD del esquema XXINE y validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus por autorizar (estatus=2).");
		u.hardWait(5);
		//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		queryH1 = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, TIPO_PEDIDO_ID, ESTATUS FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		queryH2 = "SELECT PLAZA_ID, PERIODO, FECHA_PEDIDO, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		queryH3 = "SELECT SUBPLAZA_ID, CREATION_DATE, CREATED_BY FROM " + db + ".XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '" + folio + "'";
		
		ExecuteQueryH1 = executeQuery(dbFCIASQAS, queryH1);
		ExecuteQueryH2 = executeQuery(dbFCIASQAS, queryH2);
		ExecuteQueryH3 = executeQuery(dbFCIASQAS, queryH3);
		boolean StatusQueryH3 = ExecuteQueryH1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH1);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH2);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryH3);
		
		
		if (!StatusQueryH3) {
			if (ExecuteQueryH1.getData(0, "ESTATUS").equals("2")) {
				testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			
			} else {
				testCase.addTextEvidenceCurrentStep("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
				Assert.fail("No se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_HEADER");
			}
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el pedido mostrado en pantalla");
			Assert.fail("No se guardo el pedido en la tabla XXINE_PEDIDOS_HEADER");
		}

		
		
		//query = "SELECT * FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQuery.getData(0, "PEDIDO_HEADER_ID") + "'";
		//ExecuteQuery = executeQuery(dbFCIASQAS, query);
		//StatusQuery = ExecuteQuery.isEmpty();
		
		queryD1 = "SELECT PEDIDO_HEADER_ID, PEDIDO_HEADER_ID, TIENDA_ID, PAQUETE_ID, PROMOCION_ID FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";	
		queryD2 = "SELECT POP_ID, TIPO_POP_ID, CANTIDAD, LAST_UPDATE_DATE, LAST_UPDATED_BY FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";
		queryD3 = "SELECT CREATION_DATE, CREATED_BY, COMENTARIOS FROM " + db + ".XXINE_PEDIDOS_DETAIL WHERE PEDIDO_HEADER_ID = '" + ExecuteQueryH1.getData(0, "PEDIDO_HEADER_ID") + "'";		
		ExecuteQueryD1 = executeQuery(dbFCIASQAS, queryD1);
		ExecuteQueryD2 = executeQuery(dbFCIASQAS, queryD2);
		ExecuteQueryD3 = executeQuery(dbFCIASQAS, queryD3);
		boolean StatusQueryD3 = ExecuteQueryD1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD1);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD2);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryD3);
		
		if (!StatusQueryD3) {
			testCase.addTextEvidenceCurrentStep("Se guardo el pedido correctamente en la tabla XXINE_PEDIDOS_DETAIL");
		} else {			
			testCase.addTextEvidenceCurrentStep("No se encontro ningun registro con el pedido mostrado en pantalla");
			Assert.fail("No se encontro el pedido en la tabla XXINE_PEDIDOS_DETAIL");
		}

		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "* El portal Inventario de Espacios Publicitarios debe habilitar el submen� \"Crear Pedido\" dentro del men� principal \"Pedidos\".\r\n" + 
				"* El sistema debe habilitar una p�gina al dar clic en el submen� \"Crear Pedidos\" y mostrar las opciones: \r\n" + 
				"Material POP y Accesorios\r\n" + 
				"Faltante\r\n" + 
				"Adicional\r\n" + 
				"Apertura\r\n" + 
				"Al dar clic sobre el nombre de alguna de estas dos opciones deber� redireccionar a la p�gina correspondiente. \r\n" + 
				"* El sistema debe mostrar una ventana emergente cuando el Usuario posicione el cursor sobre el icono ?\r\n" + 
				"Los mensajes que se mostrar�n seg�n el tipo de pedido son: \r\n" + 
				"Para Material POP y Accesorios: Recuerda que estos pedidos se deben de hacer cada lunes, y tu pedido llegar� a m�s tardar el siguiente lunes, en caso de que hagas tu pedido en un d�a diferente, este se tomar� en cuenta para los pedidos del siguiente lunes  \r\n" + 
				"Para Faltante /Adicional / Apertura:  Los faltantes reportados hasta N+3 ser�n tomados en cuenta para el bolet�n del MEP\r\n" + 
				"\r\n" + 
				" \r\n" + 
				"\r\n" + 
				"* La opci�n Material POP y Accesorios se habilitar� para los roles de Plaza y Administrador.\r\n" + 
				"* La opci�n Faltante / Adicional / Apertura se habilitar� para Plaza y Administrador Oxxo \r\n" + 
				"\r\n" + 
				"Los pedidos Ambientaci�n de Tiendas y Andatti-Comida R�pida solo estar�n habilitados para el perfil \"Marca\" y los pedidos Accesorios, Faltante-Adicional y Pedidos Gen�ricos solo estar�n habilitados para el perfil \"Oxxo\". \r\n" + 
				"El perfil Bara tendr� habilitados todos los tipos de pedido.";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CrearPedidoTipoFaltante_test";
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