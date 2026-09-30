package WEB.inventarioEspacios.scripts.Tipo.Pedido;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Pedidos_Autorizacion_Automatica;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.ISQLResult;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_208_P2_Autorizacion_Automat_Ambientacion_tiendas_Admdor_BARA extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_208_P2_Autorizacion_Automat_Ambientacion_tiendas_Admdor_BARA_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String UserDos = data.get("UserDos");
		String PasswordDos = PasswordUtil.decryptPassword(data.get("PasswordDos"));
		String nombrePedido = data.get("Nombre_Pedido");
		String TipoPedido = data.get("Tipo_Pedido");
		String plaza = data.get("Plaza");
		String subPlaza = data.get("SubPlaza");
		String tiendaEntrega = data.get("SubPlaza");
		String direccionEnvio = data.get("Direccion_Envio");
		String direccionNueva = data.get("Direccion_Nueva");
		String TipoPed = data.get("Tipo_Ped");
		String cantidad = data.get("Cantidad");
		String cantidadDos = data.get("Cantidad_Dos");
		String folio = data.get("Folio");
		String comentarios = data.get("Comentarios");
		
		String oxxoQuery = "";
		String oxxoQueryDos = "";
		String baraQuery = "";
		String baraQueryDos = "";
	
		if (negocio.toUpperCase().equals("OXXO")) {
			String consulta = "SELECT FOLIO_PEDIDO,TIPO_PEDIDO_ID,PLAZA_ID,ESTATUS,LAST_UPDATE_DATE from XXINE.xxine_PEDIDOS_HEADER WHERE  TRUNC(LAST_UPDATE_DATE) = TRUNC(Sysdate)";
			oxxoQuery = String.format(consulta);
			
			String consultaDos = "SELECT PEDIDO_DETAIL_ID,PEDIDO_HEADER_ID,POP_ID,TIPO_POP_ID,CANTIDAD,LAST_UPDATE_DATE from XXINE.xxine_PEDIDOS_DETAIL WHERE TRUNC(LAST_UPDATE_DATE) = TRUNC(Sysdate)";
			oxxoQueryDos = String.format(consultaDos);
			
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "SELECT FOLIO_PEDIDO,TIPO_PEDIDO_ID,PLAZA_ID,ESTATUS,LAST_UPDATE_DATE from XXINE_BA.xxine_PEDIDOS_HEADER WHERE  TRUNC(LAST_UPDATE_DATE) = TRUNC(Sysdate)";
			baraQuery = String.format(consulta);
			
			String consultaDos = "SELECT PEDIDO_DETAIL_ID,PEDIDO_HEADER_ID,POP_ID,TIPO_POP_ID,CANTIDAD,LAST_UPDATE_DATE from XXINE_BA.xxine_PEDIDOS_DETAIL WHERE TRUNC(LAST_UPDATE_DATE) = TRUNC(Sysdate)";
			baraQueryDos = String.format(consultaDos);
		}
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		//SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		//		                            PASO 2
		
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();

		//                                  PASO 3
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		mp.verificarInformacion(user, negocio, perfil);

		//                                 PASO 4
		addStep(" Validar que visualice los MENUS:");
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
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
		                      
		//                                 PASO 5
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		addStep("Seleccionar menu ->Pedidos->Tipos de pedido.");
		Pedidos_Autorizacion_Automatica aa = new Pedidos_Autorizacion_Automatica(u, testCase);
		aa.clickMenuSbTiposPedido();
		//								   PASO 7
		addStep("El sistema debe habilitar el checkbox \"Autorizaci�n Autom�tica\" para cada uno de los tipos de pedido que se muestran en la tabla de la pantalla Tipos de pedido.");
		aa.clickMuestraGrid();
		
		//	  							   PASO 8
		addStep("Seleccionar la autorizacion automatica para el <<Tipo de pedido>>");
		aa.clickSeleccionPedido(nombrePedido);

		//	  							   PASO 9	
		addStep("El sistema muestra el mensaje \"�Desea ejecutar la autorizaci�n autom�tica para el tipo de pedido <nombre del tipo de pedido>?\", se da Aceptar y se cierra la ventana.");
		aa.clickMensajeVentanaModal();
        
		//		                           PASO 10
		addStep("Ingresar con Usuario Plaza <<Comercio>> y generar un pedido con <<Tipo de Pedido>> habilitado para autorizacion automatica.");
        aa.clickCerrarSesion();
        pa.proceedToPortal();
        pa.cicloLogin(UserDos, PasswordDos);
        pa.clickPortalInventarioEspacios();
        Boolean band = false;
        aa.clickCrearPedidoPasoUno(TipoPedido,band);
        
		//		                           PASO 11 
		addStep("Validar que en el paso 3 la cantidad contenga la validaci�n de mayor a 100 art�culos.\r\n"
				+ "Si el Usuario ingresa una cantidad igual o mayor a 100, el sistema deber� mostrar el mensaje \"�Es correcta la cantidad?\" y las opciones Si y No. Si el usuario da clic en \"Si\", el mensaje se cierra y puede continuar con la captura del pedido. Si el usuario da clic en el \"No\", el mensaje se cierra y se posiciona en el campo cantidad para su correcci�n. \r\n"
				+ "");
		Boolean bandera = true;
		String opcion = "ACCESORIOS";   //AMBIENTACION_TIENDAS
		aa.clickDatosReceptor(plaza, subPlaza, direccionEnvio, direccionNueva);
		aa.clickSiguienteDos();
		aa.clickSeleccionarPops(TipoPed);
		aa.clickBuscarPops();
		aa.clickCantidadAmbientacionTiendas(cantidad, folio,TipoPed);
		aa.clickSiguienteTres();
		//aa.clickCrearPedido(plaza,subPlaza,direccionEnvio,direccionNueva,TipoPed,cantidad,cantidadDos,tiendaEntrega,folio,bandera,opcion);


		//		                           PASO 12
		addStep("Dar clic en el bot�n crear del paso 4");
		aa.clickBtnCrearPedido(comentarios);
		
		//		                           PASO 13
		addStep("Al dar clic en el bot�n Crear, en el paso 4 Generar pedido de la pantalla Crear pedido, el sistema deber� mostrar el mensaje \"�est� seguro que quiere generar el pedido con los POP's solicitados?\" y las opciones: \r\n"
				+ "Cancelar: \r\n"
				+ "Confirmar: \r\n"
				+ "Al confirmar el sistema asigna un folio en autom�tico, se redirecciona a la pantalla Bandeja de Pedidos y mostrar el estatus \"Autorizado\" para ese pedido.");
		aa.clickVentanaModalConfirmarPedido();
		
		//		                           PASO 14
		addStep("Validar que se guarde el pedido en las tablas XXINE_PEDIDOS_DETAIL, XXINE_PEDIDOS_HEADER");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				
				assertFalse(empty, "No Se guardaron Datos del Pedido en  la tabla XXINE.xxine_PEDIDOS_HEADER");
		
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el/los registro(s)");
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
				
				assertFalse(empty, "No Se guardaron Datos del Pedido Bara, en  la tabla XXINE_BA.xxine_PEDIDOS_HEADER");
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el/los registro(s), Bara");
			
		}
		
		// Consulta 2
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQueryDos);		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQueryDos);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				
				assertFalse(empty, "No Se guardaron Datos del Pedido en la tabla XXINE.xxine_PEDIDOS_DETAIL");
		
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el/los registro(s)");
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQueryDos);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQueryDos);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
				
				assertFalse(empty, "No Se guardaron Datos del Pedido Bara, en  la tabla XXINE_BA.xxine_PEDIDOS_DETAIL");
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el/los registro(s), Bara");
			
		}
		//		                           PASO 15
//		addStep("Se guarda el pedido correctamente con estatus= 3");

	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_208_P2_Autorizacion_Automatica_Ambientaci�n_tiendas_Admdor_BARA_Test + Prefil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_208_P2_Autorizacion_Automat_Ambientacion_tiendas_Admdor_BARA_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}


}
