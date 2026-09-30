package WEB.inventarioEspacios.scripts.BandejaDePedidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.BandejaPedidos;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_BusquedaBP_BtnLimpiarNuevo_Exportar extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_BusquedaBP_BtnLimpiarNuevo_Exportar_Test(HashMap<String, String> data) throws Exception {
		
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
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
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
		
		testCase.setTest_Description("ATC_FT_003_BusquedaBP_BtnLimpiarNuevo_Exportar - "+ perfil +" " + negocio + " " + data.get("TipoPedido") );
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
		//me.explicitWaitToBeInvisible(10);
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
		
		addStep("Seleccionar Menu->Pedidos->Bandeja de Pedidos");
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
		
		//  								PASO 8
		
		addStep("Validar que aparezcan los campos: Plaza, Subplaza, Estatus, Fecha, Tipo de pedido, Periodo.");
		validacion = bp.filtrosBP();
		assertTrue(validacion, "No se muestran los campos: Plaza, Subplaza, Estatus, Fecha, Tipo de pedido, Periodo.");
		
		//									PASO 7
		
		addStep("Realizar la busqueda con todos los filtros con estatus Por autorizar y Tipo_Pedido : " + data.get("TipoPedido"));
		validacion = bp.busquedaTipoPedido(data.get("TipoPedido"), data.get("Estatus"), data.get("Plaza"));
		assertTrue(validacion, "No se pudo realizar la busqueda con los filtros Tipo pedido y Estatus");
		
		//									PASO 9
		
		addStep("Dar clic en el boton buscar.");
		bp.clickBuscarBPBtn();
		
		//									PASO 10
		
		addStep("Validar que el sistema muestra el resultado de la busqueda con los siguientes campos: Folio, Periodo, Plaza, SubPlaza, Tipo Pedido, Fecha, Estatus, Accion.");
		validacion = bp.resultadosBusquedaBP(10);
		assertTrue(validacion, "No se muestran los resultados de la busqueda y/o las columnas no se habilitaron");
		
		//									PASO 11
		
		addStep("Dar clic en el boton/ icono exportar");
		bp.exportar("1");
		
		//									PASO 12
		
		addStep("Validar que se descargue el archivo con los datos del pedido seleccionado.");
		u.hardWait(10);
		validacion = bp.validarDescarga();
		assertTrue(validacion, "No se se descargo el archivo");
		
		validacion = bp.validarDatos("1", data.get("TipoPedido"));
		assertTrue(validacion, "Los datos no coinciden con el pedido seleccionado");
		
		//									PASO 13
		
		addStep("Dar clic en el boton limpiar");
		bp.regresar();
		bp.clickLimpiarBPBtn();
		u.hardWait(5);
		
		//									PASO 14
		
		addStep("Validar que se limpian los filtros de busqueda y el grid de resultado de la busqueda.");
		validacion = bp.validarBtnLimpiar();
		assertTrue(validacion, "No se limpiaron los filtros de busqueda y el grid de resultado de la busqueda");
		
		//									PASO 15
		
		addStep("Realizar la busqueda con todos los filtros y Tipo_Pedido : " + data.get("TipoPedido") + " y dar clic en buscar");
		
		validacion = bp.busquedaTipoPedido(data.get("TipoPedido"), data.get("Estatus"), data.get("Plaza"));
		assertTrue(validacion, "No se pudo realizar la busqueda con los filtros Tipo pedido y Estatus");
		bp.clickBuscarBPBtn();
		
		//									PASO 16
		
		addStep("Dar clic en el boton/ icono exportar");
		
		validacion = bp.validarCantPedidos();
		assertTrue(validacion, "No hay m�s pedidos de tipo: " + data.get("TipoPedido"));
		
		bp.exportar("2");
		
		//									PASO 17
		
		addStep("Validar que esta informacion del segundo archivo sea de la ultima busqueda y no de la anterior.");
		u.hardWait(10);
		validacion = bp.validarDescarga();
		assertTrue(validacion, "No se se descargo el archivo");
		
		validacion = bp.validarDatos("2", data.get("TipoPedido"));
		assertTrue(validacion, "Los datos no coinciden con el pedido seleccionado");
		
		//									PASO 18
		
		addStep("Dar clic en el boton limpiar");
		bp.regresar();
		bp.clickLimpiarBPBtn();
		u.hardWait(5);
		
		//									PASO 19
		
		addStep("Validar que se limpian los filtros de busqueda");
		validacion = bp.validarBtnLimpiar();
		assertTrue(validacion, "No se limpiaron los filtros de busqueda y el grid de resultado de la busqueda");
		
		//									PASO 20
		
		addStep("Dar clic en el boton nuevo");
		bp.clickNuevoBtn();
		
		//									PASO 21
		
		addStep("Validar que se muestra la pantalla principal de \"Crear Pedidos\" y el encabezado correcto.");
		validacion = bp.validarPantalla();
		assertTrue(validacion, "No se muestra correctamente la pantalla principal de \"Crear Pedidos\"");
		
		
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
		return "ATC_FT_003_BusquedaBP_BtnLimpiarNuevo_Exportar_Test";
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
