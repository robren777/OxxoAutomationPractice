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

public class P2_Consulta_estatus_mensajeria_Boton_Nuevo_Admdor_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_225_P2_Consulta_estatus_mensajeria_Boton_Nuevo_Admdor_OXXO_Test(HashMap<String, String> data) throws Exception {
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
		String descripcion = data.get("Descripcion_Estatus");
		String esFin = data.get("EsFin");
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.equals("Oxxo")) {
			
			String consulta = "select ESTATUS_ID,DESCRIPCION,ES_FIN,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE.xxine_CAT_ESTATUS_MENSAJERIA where DESCRIPCION = '%s'";
			oxxoQuery = String.format(consulta, descripcion);
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select ESTATUS_ID,DESCRIPCION,ES_FIN,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE_BA.xxine_CAT_ESTATUS_MENSAJERIA where DESCRIPCION = '%s'";
			baraQuery = String.format(consulta, descripcion);
			
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
	
		//      						   PASO 8
		addStep("Llenar el filtro descripci�n y posteriormente dar clic en el bot�n Buscar");
		em.clickBuscaEstatus(buscaNombre);
		
		//	  							   PASO 9	
		addStep("En la parte inferior se muestra la siguiente informaci�n al ingresar a la p�gina:\r\n"
				+ "Id Estatus: El sistema asigna en autom�tico el ID cuando se da de alta un nuevo estatus\r\n"
				+ "Descripci�n: Muestra el nombre con el que se dio de alta el estatus\r\n"
				+ "Es Fin: Muestra el valor si / no que se le asign� desde la creaci�n\r\n"
				+ "Al final de cada l�nea se muestran las acciones:\r\n"
				+ "Eliminar: Al dar clic sobre esta acci�n el sistema muestra el mensaje \"�Est� seguro de que desea eliminar el registro?\" y las opciones Aceptar y Cancelar. al dar clic en Aceptar se cierra la ventana y aparece el mensaje \"El estatus de mensajer�a se ha eliminado correctamente\", el estatus desaparece del listado inicial. Al dar clic en Cancelar la ventana se cierra. \r\n"
				+ "Editar: Al dar clic sobre esta acci�n abre la p�gina de edici�n con los campos\r\n"
				+ "\r\n"
				+ "");		
		em.clickMuestraGrid();
		
		//	  							   PASO 10	
		addStep("Dar clic en el boton \"Nuevo\"");
		em.clickBotonNuevo();
		
		//	  							   PASO 11	
		addStep("Al dar clic en este bot�n abre la p�gina de Captura de Estatus Mensajer�a con los siguientes campos cargados:\r\n"
				+ "Descripci�n: muestra un campo alfanum�rico, obligatorio. \r\n"
				+ "Es fin: muestra dos radio bottons con los valores si / no, por defecto aparece seleccionada la opci�n \"No\", el usuario podr� dejar esa opci�n o seleccionar la opci�n \"Si\". \r\n"
				+ "Al finalizar el usuario podr� dar clic en: \r\n"
				+ "Grabar: Al dar clic en esta opci�n los cambios que se hayan realizado se guardar�n en el sistema, la p�gina de captura se cierra y vuelve a la p�gina anterior mostrando el mensaje \" El Estatus de Mensajer�a se ha guardado correctamente.\" y los cambios se reflejan en el listado de estatus\r\n"
				+ "Cancelar: Al dar clic en esta opci�n se descarta la captura que se hayan realizado y vuelve a la p�gina anterior. Si el usuario no desea crear un estatus puede volver a la p�gina anterior dando clic en este bot�n.\r\n"
				+ "");
		Boolean bandera = true;
		em.clickMuestraPantBtnNvo(bandera);
		
		//	  							   PASO 12	
		addStep("Ingresar los datos requeridos y dar clic en Guardar");
		em.clickCapturaNuevoEstatus(descripcion, esFin);
		bandera = true;
		em.clickBotonGuardar(bandera);
		
		//								   PASO 13
		addStep("El sistema muestra mensaje de confirmaci�n. Validar que se guarda en la tabla XXINE_CAT_ESTATUS_MENSAJERIA.");
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
//				assertFalse(empty, "No Se Guardo la Descripcion Estatus: " + descripcion + " de  la tabla XXINE_CAT_ESTATUS_MENSAJERIA");
//		
//			}						
//				testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//				testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Descripcion Estatus: " + descripcion);
//		}else if (negocio.equals("Bara")) {
//			System.out.println("Entro por Bara");
//			System.out.println(baraQuery);
//			
//			SQLResult queryResultBara = executeQuery(dbFCIAS, baraQuery);
//		
//			boolean empty = ((ISQLResult) queryResultBara).isEmpty();
//
//			if (empty) {
//			
//				assertFalse(empty, "No Se Guardo la Descripcion Estatus: " + descripcion + " de  la tabla XXINE_BA.XXINE_CAT_ESTATUS_MENSAJERIA");
//		
//			}						
//				testCase.addQueryEvidenceCurrentStep(queryResultBara); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//				testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Descripcion Estatus: " + descripcion);
//		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_225_P2_Consulta_estatus_mensajeria_Boton_Nuevo_Admdor_OXXO_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_225_P2_Consulta_estatus_mensajeria_Boton_Nuevo_Admdor_OXXO_Test";
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
