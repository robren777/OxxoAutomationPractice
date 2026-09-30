package WEB.inventarioEspacios.scripts.Estatus.Mensajeria;

import static org.testng.Assert.assertFalse;

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
import utils.sql.ISQLResult;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
//Nota: Para Probar con el Negocio BARA, Se tiene que cambiar Usuario,Password,Negocio y Perfil en el data provider
public class ATC_FT_228_P2_Editar_estatus_mensajeria_Admdor_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_228_P2_Editar_estatus_mensajeria_Admdor_OXXO_test(HashMap<String, String> data) throws Exception {
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
		String descripcion = data.get("Descripcion_Estatus_Modificar");
		String esFin = data.get("EsFin");
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.equals("Oxxo")) {
			String consulta = "select ESTATUS_ID,DESCRIPCION,ES_FIN,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE.xxine_CAT_ESTATUS_MENSAJERIA where ESTATUS_ID = '%s'";
			oxxoQuery = String.format(consulta, idEstatus);
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select ESTATUS_ID,DESCRIPCION,ES_FIN,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE_BA.xxine_CAT_ESTATUS_MENSAJERIA where ESTATUS_ID = '%s'";
			baraQuery = String.format(consulta, idEstatus);
			
		}
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
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
		
		//		                           PASO 7
		addStep("El sistema debe habilitar la p�gina Consulta de Estatus Mensajer�a al dar clic en el submen� \"Estatus Mensajer�a\". \r\n"
				+ "La estructura de la p�gina debe contar con: Filtro descripci�n, bot�n buscar, bot�n nuevo y tabla de informaci�n con acciones editar y eliminar.\r\n"
				+ "");
		em.clickMuestraFiltros();
		em.clickMuestraGrid();
		
		//      						   PASO 8
		addStep("Dar clic en el bot�n Editar");
		em.clickBuscaEstatus(buscaNombre);
		em.clickBotonEditar(idEstatus);
		
		//	  							   PASO 9	
		addStep("Se abre la p�gina de Edici�n de Estatus Mensajer�a con los siguientes campos cargados:\r\n"
				+ "Id Estatus: muestra el id que le asign� el sistema y no se puede editar.\r\n"
				+ "Descripci�n: muestra el nombre que se le hab�a capturado anteriormente, este campo se puede editar.\r\n"
				+ "Es fin: muestra seleccionada la opci�n \"si\" o \"no\", este campo se puede editar.\r\n"
				+ "Tambi�n se habilitan los botones de: \r\n"
				+ "Grabar: Al dar clic en esta opci�n los cambios que se hayan realizado se guardar�n en el sistema, la p�gina de edici�n se cierra y vuelve a la p�gina anterior mostrando el mensaje \"El Estatus de Mensajer�a se ha actualizado correctamente\" y los cambios se reflejan en el listado de estatus\r\n"
				+ "Cancelar: Al dar clic en esta opci�n se descartan los cambios que se hayan realizado y vuelve a la p�gina anterior. Si el usuario no desea realizar ning�n cambio puede volver a la p�gina anterior dando clic en este bot�n.\r\n"
				+ "");		
		Boolean bandera = false;
		em.clickMuestraPantBtnNvo(bandera);
		
		//	  							   PASO 10	
		addStep("Editar los campos y dar clic en guardar.");
		em.clickModificaDatos(descripcion, esFin);
		Boolean band = false;
		em.clickBotonGuardar(band);
		
		//	  							   PASO 11	
		addStep("El sistema muestra mensaje de confirmaci�n. Validar que se guarde en la taba XXINE_CAT_ESTATUS_MENSAJERIA.");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.equals("Oxxo")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				assertFalse(empty, "No Se Modifico el Id Estatus: " + idEstatus + " de  la tabla XXINE_CAT_ESTATUS_MENSAJERIA");
		
			}						
				testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
				testCase.addTextEvidenceCurrentStep("Se Modifico correctamente el registro, Id Estatus: " + idEstatus);
		}else if (negocio.equals("Bara")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
			
			SQLResult queryResultBara = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = ((ISQLResult) queryResultBara).isEmpty();

			if (empty) {
			
				assertFalse(empty, "No Se Modifico el Id Estatus: " + idEstatus + " de  la tabla XXINE_BA.xxine_CAT_ESTATUS_MENSAJERIA");
		
			}						
				testCase.addQueryEvidenceCurrentStep(queryResultBara); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
				testCase.addTextEvidenceCurrentStep("Se Modifico correctamente el registro, Descripcion Estatus: " + idEstatus);

			
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_228_P2_Editar_estatus_mensajer�a_Admdor_OXXO_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_228_P2_Editar_estatus_mensajeria_Admdor_OXXO_test";
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
