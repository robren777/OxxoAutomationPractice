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

public class ATC_FT_006_DetallePedidoRechazar  extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_006_DetallePedidoRechazar_Test(HashMap<String, String> data) throws Exception {
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
		
		testCase.setTest_Description("ATC_FT_006_DetallePedidoRechazar - "+ perfil +" " + negocio + " " + data.get("TipoPedido") );
		
		//									PASO 1
		
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
		
		
		
		//        							PASO 2
		
		addStep("Seleccionar Inventario de Espacios V2");
		pa.clickPortalInventarioEspacios();
		u.waitForLoadPage();
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		validacion = mp.accesoCorrectoIEP();
		assertTrue(validacion, "No se ingresa al Inventario de espacios correctamente.");
		
		//        							PASO 3
		
		addStep("Verificar que se muestre la informaci�n del usuario, el negocio y el perfil");
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
		
		addStep("Seleccionar Menu->Pedidos->Bandeja de pedidos");
		mp.clickPedidosM();
		mp.clickBandejaPedidos();
		
		//									PASO 7
		
		addStep("Realizar la busqueda con todos los filtros, estatus \"por autorizar\" y tipo_Pedido: " + data.get("TipoPedido"));
		validacion = bp.busquedaTipoPedido(data.get("TipoPedido"), data.get("Estatus"), data.get("Plaza"));
		assertTrue(validacion, "No se pudo realizar la busqueda con los filtros Tipo pedido y Estatus");
		
		bp.clickBuscarBPBtn();
		
		validacion = bp.resultadosBusquedaBP(10);
		assertTrue(validacion, "No se muestran los resultados de la busqueda y/o las columnas no se habilitaron");
		
		//									PASO 8
		
		addStep("Dar clic en el folio del pedido");
		String folio = bp.getFolio("1");
		bp.clickFolio("1");
		
		//									PASO 9
		
		addStep("Validar que se abra la pantalla \"Detalle de pedidos\" con los datos preguardados del pedido y estatus por autorizar.\r\n" + 
				"Detalle de Ambientaci�n de tienda:\r\n" + 
				"*KIT (Aplica solo ambientaci�n de tiendas)\r\n" + 
				"*Id POP\r\n" + 
				"*POP\r\n" + 
				"*Contenido\r\n" + 
				"*Imagen\r\n" + 
				"*Descripci�n\r\n" + 
				"*Precio\r\n" + 
				"*Cantidad\r\n" + 
				"*Total\r\n" + 
				"*Precio Imprimir\r\n" + 
				"*Segmento  (Aplica solo ambientaci�n de tiendas)\r\n" + 
				"*Comentarios\r\n" + 
				"*Total de pedido\r\n" + 
				"\r\n" + 
				"Detalle de Faltantes y Adicionales: \r\n" + 
				"*Tipo\r\n" + 
				"*tienda\r\n" + 
				"*Paquete\r\n" + 
				"*Promoci�n\r\n");
		
		validacion = bp.validarDatosPreguardados(folio, data.get("TipoPedido"), data.get("Estatus"));
		assertTrue(validacion, "Los datos preguardados no son correctos");
		
		//									PASO 10
		
		addStep("Dar clic en el boton Rechazar");
		bp.clickRechazarPedido();
		
		
		//									PASO 11
		
		addStep("Dar clic en confirmar");
		bp.clickConfirmar();
		
		
		//									PASO 12
		
		addStep("Validar que el sistema cambia el estatus del pedido a \"Rechazado\"y se le envia un correo al usuario que creo el pedido informandole que su pedido ha sido Autorizado.");
		validacion = bp.validarEstatus("Cancelado");
		assertTrue(validacion, "No se cambia el estatus del pedido");
		
		//									PASO 13
		
		addStep("Validar que se guarde el pedido correctamente en las tablas XXINE_PEDIDOS_HEADER/ XXINE_PEDIDOS_DETAIL con estatus autorizado (estatus=3).");
		
		query = "SELECT FOLIO_PEDIDO, PEDIDO_HEADER_ID, PERIODO, PLAZA_ID, ESTATUS, FECHA_PEDIDO, USUARIO_PEDIDO, COMENTARIOS "
				+ "FROM " + db + ".XXINE_PEDIDOS_HEADER "
				+ "WHERE FOLIO_PEDIDO = '" + folio + "'";
		ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String pedido = ExecuteQuery.getData(0, "PEDIDO_HEADER_ID");
		StatusQuery = ExecuteQuery.isEmpty();
		
		if (!StatusQuery) {
			if (ExecuteQuery.getData(0, "ESTATUS").equals("7")) {
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
				+ "WHERE PEDIDO_HEADER_ID = '" + pedido + "'";
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_DetallePedidoRechazar_Test";
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
