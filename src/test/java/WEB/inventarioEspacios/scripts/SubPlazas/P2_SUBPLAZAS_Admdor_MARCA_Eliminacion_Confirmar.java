package WEB.inventarioEspacios.scripts.SubPlazas;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_SubPlaza;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.SubPlaza_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class P2_SUBPLAZAS_Admdor_MARCA_Eliminacion_Confirmar extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_090_P2_SUBPLAZAS_Admdor_MARCA_Eliminacion_Confirmar_Test(HashMap<String, String> data) throws Exception {
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
		String idSubPlaza = data.get("Id_SubPlaza");
		String idNombreSbPlaza = data.get("SubPlaza");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select SUBPLAZA_ID,PLAZA_ID,NOMBRE_SUBPLAZA,LAST_UPDATE_DATE,LAST_UPDATED_BY from XXINE.xxine_cat_subplazas WHERE SUBPLAZA_ID = '%s'";
			oxxoQuery = String.format(consulta, idSubPlaza);
			
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select SUBPLAZA_ID,PLAZA_ID,NOMBRE_SUBPLAZA,LAST_UPDATE_DATE,LAST_UPDATED_BY from XXINE_BA.xxine_cat_subplazas WHERE SUBPLAZA_ID = '%s'";
			baraQuery = String.format(consulta, idSubPlaza);
			
		}

		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
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
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		addStep("Seleccionar menu Administrador : SubPlazas");
		mp.clickAdministracion();
		SubPlaza_IEP sp = new SubPlaza_IEP(u, testCase);
		sp.clickSubPlaza();
		
		//                                 PASO 7
		addStep("\"\"Se muestra la pantalla con los filtros:\r\n"
				+ "Nombre Subplaza\r\n"
				+ "y los botones de \"\"Nuevo\"\",\"\"Limpiar\"\" y \"\"Buscar\"\"\"\r\n"
				+ "");		
		sp.clickCampos();

		//                                 PASO 8
		addStep("Ingresar filtros y dar click en Buscar");		
		sp.clickIngresaFiltroBuscar(idNombreSbPlaza);
		sp.clickBtnBuscar();
		
		//                                 PASO 9
		addStep("\"Se muestran los resultados en pantalla con los campos:\n"
				+ "ID \n"
				+ "NOMBRE \n"
				+ "CLAVE PLAZA \n"
				+ "NOMBRE PLAZA \n"
				+ "BOTON \"\"EDITAR");
		sp.clickResPasoNueve();

		//                                 PASO 10
		addStep("Seleccionar un registro y dar click en el boton \"Eliminar\"");
		Consulta_Movtos_Eliminacion_SubPlaza es = new Consulta_Movtos_Eliminacion_SubPlaza(u, testCase);
		es.clickSelSbPlaza(idSubPlaza,idNombreSbPlaza);
		//sp.clickBtnEditar(idSubPlaza);

		//                                 PASO 11
		addStep("\"Se abre una ventana modal con el siguiente mensaje:\r\n"
				+ "�Esta seguro que desea eliminar el registro? \r\n"
				+ "Y los botones \"\"Cancelar\"\" y \"\"Confirmar\"\".\"\r\n"
				+ "");
		es.clickVentanaModal();
//		Boolean band = false;
//		sp.clickNuevaPantalla(band);
        
		//                                 PASO 12
		addStep("\"Dar click en \"Confirmar\"");
		es.clickConfirmarEliminar();
//		sp.clickLlenaCamposDos(NombreDos,PlazaDos);
//		sp.clickBtnGuardar();

		//                                 PASO 13
		addStep("\"Se muestra un mensaje de 'Se han eliminado los registros correctamente.'");
		es.clickMsjEliminado();
		//sp.clickCampos();

		//                                 PASO 14
		addStep("\"Ingresar en la Tabla XXINE_CAT_SUBPLAZAS y validar que los datos no existan.");
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
//				testCase.addTextEvidenceCurrentStep("Se Elimino Correctamente el registro, Id SubPLaza: " + idSubPlaza + " Nombre SubPlaza: " + idNombreSbPlaza);
//			
//		
//			}						
//			assertTrue(empty, "No Se Elimino Correctamente el registro, Id SubPlaza: " + idSubPlaza + " de  la tabla XXINE_CAT_SUBPLAZAS");
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
//				testCase.addTextEvidenceCurrentStep("Se Elimino Correctamente el registro, Id SubPLaza: " + idSubPlaza + " Nombre SubPlaza: " + idNombreSbPlaza);
//			
//		
//			}						
//			assertTrue(empty, "No Se Elimino Correctamente el registro, Id SubPlaza: " + idSubPlaza + " de  la tabla XXINE_BA.xxine_CAT_SUBPLAZAS");
//	
//		
//		
//			//                                 PASO 15
//			addStep("\"Se valida que el registro ya no esta en base de datos.");
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//		}
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_090_P2_SUBPLAZAS_Admdor_MARCA_Eliminacion_Confirmar_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_090_P2_SUBPLAZAS_Admdor_MARCA_Eliminacion_Confirmar_Test";
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
