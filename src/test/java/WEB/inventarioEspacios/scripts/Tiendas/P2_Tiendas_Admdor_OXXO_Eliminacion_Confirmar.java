package WEB.inventarioEspacios.scripts.Tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_Tienda;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Tiendas_IEP;
import WEB.inventarioEspacios.om.Tiendas_Nuevo_Limpiar_Confirmar;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class P2_Tiendas_Admdor_OXXO_Eliminacion_Confirmar extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_123_P2_Tiendas_Admdor_OXXO_Eliminacion_Confirmar_Test(HashMap<String, String> data) throws Exception {
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
		String idTienda = data.get("Tienda_Id");
		String descripTienda = data.get("Nombre_Tienda");
		String nomPlaza = data.get("Plaza");
		String modelo = data.get("Tienda_Modelo");
		String year = data.get("A�o");
		String mes = data.get("Mes");
		String diaInicial = data.get("Dia_Inicial");
		String diaFinal = data.get("Dia_Final");
		String tipoMovto = data.get("Tipo_Movto");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select FECHA,TIENDA_ID,NOMBRE_TIENDA,NOMBRE_ASESOR,LAST_UPDATE_DATE from XXINE.xxine_cat_tiendas where tienda_id = '%s'";
			oxxoQuery = String.format(consulta, idTienda);
		
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select FECHA,TIENDA_ID,NOMBRE_TIENDA,NOMBRE_ASESOR,LAST_UPDATE_DATE from XXINE_BA.xxine_cat_tiendas where tienda_id = '%s'";
			baraQuery = String.format(consulta, idTienda);
			
		}
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
//		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
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
//		mp.clickMenus();
//		switch(perfil.toUpperCase()) {
//		
//			case "ADMINISTRADOR": 
//				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
//					mpo.menuAdministrador();
//				} else if (negocio.toUpperCase().equals("MARCA")) {
//					mpo.menuAdministradorM();
//				}
//				
//			break;
//			
//			case "PLAZA": mpo.menuPlaza();
//			break;
//			
//			case "ESTAFETA": mpo.menuEstafeta();
//			break;
//			
//		}
                      
		//                                 PASO 5
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		Tiendas_Nuevo_Limpiar_Confirmar tl = new Tiendas_Nuevo_Limpiar_Confirmar(u, testCase);
		addStep("Seleccionar menu Administrador : Tiendas");
		tl.clicMenuAdmonTdas(negocio);                       
		
		//								   PASO 7
		addStep("\"Se muestra la pantalla con los filtros:\r\n"
				+ "PLAZA\r\n"
				+ "MODELO\r\n"
				+ "DESCRIPCION TIENDA\r\n"
				+ "y los botones de \"\"% Caja Z\"\",\"\"Nuevo\"\",\"\"Limpiar\"\" y \"\"Buscar\"\"\"\r\n"
				+ "");
		tl.clickMuestraFiltros();  
				
		//      						   PASO 8
		addStep("Ingresar filtros y dar click en \"Buscar\"");
		Tiendas_IEP dt = new Tiendas_IEP(u, testCase);
		dt.clickIngresaFiltrosTda(nomPlaza,modelo,descripTienda);
		dt.clickBusquedaTda();
		
		//	  							   PASO 9	
		addStep("\"\"\"Se muestran los resultados en pantalla con los campos:\r\n"
				+ "ID\r\n"
				+ "NOMBRE\r\n"
				+ "PLAZA\r\n"
				+ "SUBPLAZA\r\n"
				+ "PLAZA CCM\r\n"
				+ "CR\r\n"
				+ "ASESOR\r\n"
				+ "SEGMENTO\r\n"
				+ "SEGMENTO MAESTRO\r\n"
				+ "Boton \"\"Editar\"\"\"\r\n"
				+ "");
		dt.clickResultadosBtonBuscar();

		//		                           PASO 10
		addStep("Seleccionar la tienda a eliminar.");
		Consulta_Movtos_Eliminacion_Tienda et = new Consulta_Movtos_Eliminacion_Tienda(u, testCase);
		et.clickSeleccionTdaEliminar(idTienda);

		//		                           PASO 11
		addStep("Se muestra el boton de eliminar.");
		et.muestraBotonEliminar();

		//		                           PASO 12
		addStep("Dar click en \"Eliminar\"");
		et.clicBtnEliminar();

		//		                           PASO 13
		addStep(" Se muestra mensaje en una ventana modal con el mesnaje : \"Esta seguro que desea eliminar los registros seleccionados?\" y los botones : \"Cancelar\" y \"Confirmar\".");
		et.clickMuestraMsj();

		//		                           PASO 14
		addStep("Dar click en \"Confirmar\"");
		et.clickBtnConfirmarEliminar();

		//		                           PASO 15
		addStep("Se muestra un mensaje de que ha sido eliminado satisfactoriamente..");


		//		                           PASO 16
		addStep("Ingresar a la base de datos a la tabla XXINE_CAT_TIENDAS y buscar que el registro ya no exista.");
		
//		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
//		if (negocio.equals("Oxxo")) {
//			System.out.println("Entro por Oxxo");
//			System.out.println(oxxoQuery);
//		
//			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
//		
//			boolean empty = ((ISQLResult) queryResult).isEmpty();
//
//			if (empty) {
//			
//				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el registro, Tienda Id " + idTienda + " Descripcion Tienda: " + descripTienda);
//			
//		
//			}
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			assertTrue(empty, "No Se Modifico la Tienda Id " + idTienda + " de  la tabla XXINE_MOVIMIENTOS_SISTEMA");
//		}else if (negocio.equals("Bara")) {
//			System.out.println("Entro por Bara");
//			System.out.println(baraQuery);
//		
//			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
//		
//			boolean empty = ((ISQLResult) queryResult).isEmpty();
//
//			if (empty) {
//			
//				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el registro, Tienda Id " + idTienda + " Descripcion Tienda: " + descripTienda);
//			
//		
//			}
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			assertTrue(empty, "No Se Modifico la Tienda Id " + idTienda + " de  la tabla XXINE_MOVIMIENTOS_SISTEMA");
//			
//		}
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_123_P2_Tiendas_Admdor_OXXO_Eliminacion_Confirmar_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_123_P2_Tiendas_Admdor_OXXO_Eliminacion_Confirmar_Test";
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
