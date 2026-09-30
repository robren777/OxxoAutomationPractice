package WEB.inventarioEspacios.scripts.Tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Tiendas_Nuevo_Limpiar_Confirmar;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class P2_Tiendas_Admdor_OXXO_Nuevo_Limpiar_Confirmar extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_103_P2_Tdas_Admdor_OXXO_Nuevo_Limpiar_Confirmar_Test(HashMap<String, String> data) throws Exception {
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
		String nombreTienda = data.get("Nombre_Tienda");
		String plaza = data.get("Plaza");
		String RetekId = data.get("RetekId");
		String SubPlaza = data.get("SubPlaza");
		String Asesor = data.get("Asesor");
		String PlazaCCM = data.get("Plaza_CCM");
		String MetrosCuadrados = data.get("Metros_Cuadrados");
		String Distritos = data.get("Distritos");
		String Segmentacion = data.get("Segmentacion");
		String TiendaModelo = data.get("Tienda_Modelo");
		String SegmentoMtro = data.get("Segmento_Mtro");
		String Opcionestienda = data.get("Opciones_tienda");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select FECHA,NOMBRE_TIENDA,TIENDA_ID,RETEK_LOC_ID,NOMBRE_ASESOR,NOMBRE_SEGMENTO,RETEK_REGION_NAME,METROS_2,TIENDA_MODELO,SEGMENTO_MAESTRO from XXINE.xxine_cat_tiendas where NOMBRE_TIENDA = '%s'";
			oxxoQuery = String.format(consulta, nombreTienda);
		
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select FECHA,NOMBRE_TIENDA,TIENDA_ID,RETEK_LOC_ID,NOMBRE_ASESOR,NOMBRE_SEGMENTO,RETEK_REGION_NAME,METROS_2,TIENDA_MODELO,SEGMENTO_MAESTRO from XXINE_BA.xxine_cat_tiendas where NOMBRE_TIENDA = '%s'";
			baraQuery = String.format(consulta, nombreTienda);
			
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
		//								   PASO 8
		addStep("Ingresar filtros y dar click en \"Nuevo\"");
		tl.clickBotonNuevo();
//										   PASO 9
		addStep("\"Se muestran los resultados en pantalla con los campos:\r\n"
				+ "NOMBRE DE TIENDA\r\n"
				+ "RETEKID\r\n"
				+ "ASESOR\r\n"
				+ "METROS CUADRADOS\r\n"
				+ "SEGMENTACION\r\n"
				+ "SEGMENTO MAESTRO\r\n"
				+ "PLAZA\r\n"
				+ "SUBPLAZA\r\n"
				+ "PLAZA CCM\r\n"
				+ "DISTRITOS\r\n"
				+ "TIENDA MODELO\r\n"
				+ "OPCIONES DE TIENDA: (\r\n"
				+ "ALTA, BAJA, SIN ,CERVEZA,GAS,ACTIVA)\r\n"
				+ "TIENDA MIXTA O EXCLUSIVA\r\n"
				+ "TIPO DE EMBOTELLADORA\r\n"
				+ "RESTRICCION POP EN GASOLINERA\"\r\n"
				+ "");
		Boolean band = true;
		tl.clickMuestraCamposPNueve(band);
//										   PASO 10		
		addStep("Llenar los campos y dar click en \"Limpiar\"");
		tl.ClickLlenaCampos(nombreTienda, plaza, RetekId, SubPlaza, Asesor, PlazaCCM, MetrosCuadrados, Distritos, Segmentacion, TiendaModelo, SegmentoMtro, Opcionestienda);
//		   								   PASO 11		
		addStep("Los campos se limpian correctamente.");
		tl.Limpia();
//										   PASO 12		
		addStep("Llenar los campos de nuevo  y dar click en \"Guardar\"");
		tl.ClickLlenaCampos(nombreTienda, plaza, RetekId, SubPlaza, Asesor, PlazaCCM, MetrosCuadrados, Distritos, Segmentacion, TiendaModelo, SegmentoMtro, Opcionestienda);
		//tl.clickMuestraCamposPNueve();
//										   PASO 13		
		addStep("Se muestra un mensaje de guardado exitosamente y te regresa a la pantalla principal.");
//		tl.clickBotonGuardar();
		
		//								   PASO 14
		addStep("Ingresar a la base de datos a la tabla XXINE_CAT_TIENDAS y buscar los segudos datos guardados.");
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
//				assertFalse(empty, "No Se Guardo el nombre de la tienda " + nombreTienda + " en  la tabla XXINE_MOVIMIENTOS_SISTEMA");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Nombre Tienda " + nombreTienda);
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
//				assertFalse(empty, "No Se Guardo el nombre de la tienda " + nombreTienda + " en  la tabla XXINE_MOVIMIENTOS_SISTEMA");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Nombre Tienda " + nombreTienda);
//			
//		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_103_P2_Tdas_Admdor_OXXO_Nuevo_Limpiar_Confirmar_Test + perfil +  negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_103_P2_Tdas_Admdor_OXXO_Nuevo_Limpiar_Confirmar_Test";
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
