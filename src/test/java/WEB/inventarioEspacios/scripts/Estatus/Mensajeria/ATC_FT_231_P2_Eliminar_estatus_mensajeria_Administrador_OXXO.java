package WEB.inventarioEspacios.scripts.Estatus.Mensajeria;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_Estatus_mensajeria;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_231_P2_Eliminar_estatus_mensajeria_Administrador_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_231_P2_Eliminar_estatus_mensajeria_Administrador_OXXO_test(HashMap<String, String> data) throws Exception {
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
		String idEstatus = data.get("Id_Estatus");
		String buscaNombre = data.get("Busca_Nombre_Estatus");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select ESTATUS_ID,DESCRIPCION,ES_FIN,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE.xxine_CAT_ESTATUS_MENSAJERIA where ESTATUS_ID = '%s'";
			oxxoQuery = String.format(consulta, idEstatus);
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select ESTATUS_ID,DESCRIPCION,ES_FIN,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE_BA.XXINE_CAT_ESTATUS_MENSAJERIA where ESTATUS_ID = '%s'";
			baraQuery = String.format(consulta, idEstatus);
			
		}
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar chrome
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
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
		Consulta_Movtos_Eliminacion_Estatus_mensajeria em = new Consulta_Movtos_Eliminacion_Estatus_mensajeria(u, testCase);
		addStep("Seleccionar menu ->Administraci�n->Estatus Mensajeria");
		em.clickEstatusMensajeria();
		
		//								   PASO 7
		addStep("El sistema debe habilitar la p�gina Consulta de Estatus Mensajer�a al dar clic en el submen� \"Estatus Mensajer�a\". \r\n"
				+ "La estructura de la p�gina debe contar con: Filtro descripci�n, bot�n buscar, bot�n nuevo y tabla de informaci�n con acciones editar y eliminar.\r\n"
				+ "");
		em.clickMuestraFiltros();
		em.clickMuestraGrid();
				
		//      						   PASO 8
		addStep("Dar clic en el check de los estatus mensajer�a que se desean eliminar");
		em.clickBuscaEstatus(buscaNombre);
		em.clickSelEstatus(idEstatus);
		
		//	  							   PASO 9	
		addStep("Se habilita el boton Eliminar");
		em.clickHabilitaBtnEliminar();
		
		//	  							   PASO 10	
		addStep("Dar clic en el Bot�n Eliminar y confirmar.");
		em.clickBtnEliminarConfirmar();
		
		//		                           PASO 11
		addStep("El sistema muestra mensaje de confirmaci�n. Validar que ya no exista en la tabla XXINE_CAT_ESTATUS_MENSAJERIA.");
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
//				assertTrue(empty, "No Se elimino el Id Estatus: " + idEstatus + " de  la tabla XXINE_CAT_ESTATUS_MENSAJERIA.");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el registro, Id Estatus Eliminado: " + idEstatus);
//		}else if (negocio.equals("Bara")) {
//			System.out.println("Entro por Bara");
//			
//			System.out.println(baraQuery);
//			
//			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
//			
//			boolean empty = ((ISQLResult) queryResult).isEmpty();
//
//			if (empty) {
//				
//				assertTrue(empty, "No Se elimino el Id Estatus: " + idEstatus + " de  la tabla XXINE_BA.XXINE_CAT_ESTATUS_MENSAJERIA.");
//			
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el registro, Id Estatus Eliminado: " + idEstatus);
//		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_231_P2_Eliminar_estatus_mensajer�a_Administrador_OXXO_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_231_P2_Eliminar_estatus_mensajeria_Administrador_OXXO_test";
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
