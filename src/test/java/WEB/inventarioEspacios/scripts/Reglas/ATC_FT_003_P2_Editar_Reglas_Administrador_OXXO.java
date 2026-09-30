package WEB.inventarioEspacios.scripts.Reglas;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Reglas_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_P2_Editar_Reglas_Administrador_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_P2_Editar_Reglas_Administrador_OXXO_test(HashMap<String, String> data) throws Exception {
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
		String idRegla = data.get("Id_Regla");
		String NomReglaBuscar = data.get("Nombre_Regla_Buscar");
		String NombreRegla = data.get("Nombre_Regla");
		String descripcionTxt = data.get("Descripcion");
		String descripBuscar = data.get("Descripcion_buscar");
		String Obligatoria = data.get("Obligatoria");
		String EstatusTxt = data.get("Estatus");
		String estatusBuscar = data.get("Estatus_buscar");
		String nomTienda = data.get("NOMBRE_TIENDA");
		String plaza = data.get("Plaza");
		String sbPlaza = data.get("SubPlaza");
		String borraPlaza = data.get("Borra_plaza");
		String espacio = data.get("Select_Espacios");
		String cantidPosCond = data.get("Cantidad");
		String Mensaje = data.get("Mensaje");

		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.toUpperCase().equals("OXXO")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,ESTATUS,OBLIGATORIA,PRECONDICION,LAST_UPDATE_DATE from XXINE.xxine_cat_reglas where REGLA_ID = '%s'";
			oxxoQuery = String.format(consulta, idRegla);
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,ESTATUS,OBLIGATORIA,PRECONDICION,LAST_UPDATE_DATE from XXINE_BA.xxine_cat_reglas where REGLA_ID = '%s'";
			baraQuery = String.format(consulta, idRegla);
			
		}
		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
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
		addStep(" Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		String query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		boolean validacionInfo = mp.verificarInformacion(nameUser, negocio, perfil);
			
		if (validacionInfo) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacionInfo, "No se muestra la informaci�n esperada");
		
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
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		//                                 PASO 6
		addStep("Seleccionar menu Administrador : Reglas");
		Reglas_IEP dc = new Reglas_IEP(u, testCase);
		mp.clickAdministracion();
		mp.clickReglas();
		
		//                                 PASO 7
		addStep("Se visualizan los filtros y campos:\r\n"
				+ "Nombre: \r\n"
				+ "Descripci�n:\r\n"
				+ "Estatus:\r\n"
				+ "Fecha Creaci�n: \r\n"
				+ "Botones: Nuevo, Exportar, Limpiar y buscar\r\n"
				+ "Id Regla: Muestra el ID que le asign� autom�ticamente el sistema\r\n"
				+ "Nombre: Muestra el nombre que se le asign� a la regla\r\n"
				+ "Descripci�n: Muestra la descripci�n que se le asign� a la regla\r\n"
				+ "Obligatoria: Muestra el valor Si / No que se le asign� desde el alta de la regla\r\n"
				+ "Estatus: Muestra el valor Activo / Inactivo que se le asign� desde el alta de la regla\r\n"
				+ "Fecha Creaci�n: Muestra la fecha en que se cre� la regla\r\n"
				+ "Por cada regla deber� mostrar las acciones Eliminar y Editar \r\n"
				+ "\r\n"
				+ "\r\n"
				+ "");
		dc.clickIdConsultaReglas();
		

		//                                 PASO 8
		addStep("Seleccionar el boton editar (Lapiz) de la regla que se modificara");
		Boolean band = false;
		dc.clickIdReglatxt(NomReglaBuscar,band);
		dc.clickDescripTxt(descripBuscar);
		dc.clickStatus(estatusBuscar);
		dc.clickBotonBuscar();
		dc.clickSelReglaEditar(idRegla);
		
//		dc.clickpDatosGrales(descripcionTxt, Obligatoria, EstatusTxt, nomTienda, plaza, sbPlaza);		
		
		//                                 PASO 9
		addStep("El sistema habilita la pantalla Captura de Regla, al dar clic en el bot�n Editar. \r\n"
				+ "La pantalla contiene los botones: \r\n"
				+ "Actualizar / Limpiar / Cancelar \r\n"
				+ "\r\n"
				+ "La pantalla se divide en 5 pesta�as:\r\n"
				+ "General / Condici�n / Postcondici�n / Aplicar a / Mensajes\r\n"
				+ "Al ingresar autom�ticamente la pantalla abre en la pesta�a General, con la informaci�n precargada de la regla:\r\n"
				+ "Id Regla: este campo no es editable\r\n"
				+ "Nombre: Obligatorio, campo de texto abierto alfanum�rico \r\n"
				+ "Descripci�n: Obligatorio, campo de texto abierto alfanum�rico\r\n"
				+ "Obligatoria: Obligatorio, campo de selecci�n que muestra los valores Si / No. Por default aparece seleccionada la opci�n Si. \r\n"
				+ "Estatus: Obligatorio, campo de selecci�n que muestra los valores Activo / Inactivo. Por default aparece seleccionada la opci�n Activo.\r\n"
				+ "Pesta�a Condici�n: Muestra la formula configurada para la regla, es posible editarla\r\n"
				+ "Pesta�a Postcondici�n: Muestra las postcondiciones configuradas para la regla, es posible editarlas \r\n"
				+ "Pesta�a Aplicar A: Muestra la configuraci�n de las plazas, plazas ccm, subplazas y tiendas configurados para la regla, es posible editarlos\r\n"
				+ "Pesta�a Mensaje: Muestra la configuraci�n del mensaje a desplegar cuando la regla no se cumpla, es posible editarlo\r\n"
				+ "");
	    dc.clickPanCapturaRegla();
	    //band = false;

		//                                 PASO 10
		addStep("Editar las 5 pesta�as y guardar los cambios.");
		String op = "E";
		dc.clickNomReglaEditar(NombreRegla);
		dc.clickpDatosGrales(descripcionTxt, Obligatoria, EstatusTxt, nomTienda, plaza, sbPlaza);
		dc.clickPesCondicion(nomTienda);
		dc.clickPesPosCondicion();
		dc.clickMuestraPesPoscondicion(cantidPosCond,espacio,op);
		dc.clickBorraPlazaSubPlaza(borraPlaza);
		dc.clickPesAplicarA(plaza, sbPlaza);
		dc.clickPesMensaje(Mensaje);
		//dc.clickMuestraPesMensaje();
		dc.clickBtnGuardarAplicar();


//    									  PASO 11	
		addStep("El sistema env�a mensaje de confirmaci�n \"Regla editada correctamente\",  carga la pantalla principal de Reglas y guarda los datos en las tablas  XXINE_CAT_REGLAS, XXINE_POSTCONDICION_REGLA, XXINE_ESPACIOS_REGLA, XXINE_PLAZAS_CCM_REGLA, XXINE_SUBPLAZAS_REGLA\r\n"
				+ "");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta

			if (empty) {
			
				assertFalse(empty, "No Se guardo el registro " + idRegla + " de  la tabla XXINE_CAT_REGLAS");
		
			}						
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Id Regla " + idRegla);
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			

			if (empty) {
			
				assertFalse(empty, "No Se guardo el registro " + idRegla + " de  la tabla XXINE_BA.xxine__CAT_REGLAS");
		
			}						
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Id Regla " + idRegla);
			
		}

	}
 
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_003_P2_Editar_Reglas_Administrador_OXXO_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_P2_Editar_Reglas_Administrador_OXXO_test";
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
		u.getDriver().quit();
	}

}
