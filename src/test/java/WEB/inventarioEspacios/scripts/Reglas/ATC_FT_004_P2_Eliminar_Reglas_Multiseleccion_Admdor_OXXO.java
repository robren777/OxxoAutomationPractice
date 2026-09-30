package WEB.inventarioEspacios.scripts.Reglas;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class ATC_FT_004_P2_Eliminar_Reglas_Multiseleccion_Admdor_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_004_P2_Eliminar_Reglas_Multiseleccion_Admdor_OXXO_test(HashMap<String, String> data) throws Exception {
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
		String idRegla = data.get("Id_Regla1");
		String idReglaDos = data.get("Id_Regla2");
		String idReglaTres = data.get("Id_Regla3");
//		double NumPaginas = Double.parseDouble(data.get("NumPaginas"));
//		int PaginasRedo = Integer.parseInt(data.get("PaginasRedo"));
		String StrRegla = "";
		
		String oxxoQuery = "";
		String oxxoQueryDos = "";
		String oxxoQueryTres = "";
		String oxxoQueryCuatro = "";
		String oxxoQueryCinco = "";
		String consultaDos = "";
		String consultaTres = "";
		String consultaCuatro = "";
		String consultaCinco = "";
		String baraQuery = "";
		String baraQueryDos = "";
		String baraQueryTres = "";
		String baraQueryCuatro = "";
		String baraQueryCinco = "";
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel

		u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		//SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.cicloLogin(user, password);
						
		//		                            PASO 2
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		//                                  PASO 3
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
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
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		//                                 PASO 6
		addStep("Seleccionar menu ->Administrador->Reglas.");
		Reglas_IEP dc = new Reglas_IEP(u, testCase);
		mp.clickAdministracion();
		mp.clickReglas();
		
		//      						   PASO 7
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
		
		//	  							   PASO 8	
		addStep("Seleccionar mas de 1 registro a eliminar con el chek y dar clic en el bot�n Eliminar");
		int cont = 0;
		ArrayList <String> Reglas = new ArrayList<String>();
		//System.out.println(NumProv);
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String encab = entry.getKey();
			String valor = entry.getValue();
			
			if (encab.contains("Id_Regla") && valor.trim().length() > 0 ) {
				Reglas.add(valor);
				if (cont > 0) { 
					
					StrRegla += ",";
				}
				StrRegla += valor;
				cont++;
			}
		}
		
		if (negocio.toUpperCase().equals("OXXO")) {
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,PRECONDICION,LAST_UPDATE_DATE from XXINE.xxine_CAT_REGLAS WHERE REGLA_ID IN(%s)";    //IN('%s')
			oxxoQuery = String.format(consulta, StrRegla);
			
		    consultaDos = "select * from XXINE.xxine_POSTCONDICION_REGLA WHERE REGLA_ID IN(%s)";
			oxxoQueryDos = String.format(consultaDos, StrRegla);
			
			consultaTres = "select * from XXINE.xxine_ESPACIOS_REGLA WHERE REGLA_ID IN(%s)";
			oxxoQueryTres = String.format(consultaTres, StrRegla);
			
			consultaCuatro = "select * from XXINE.xxine_PLAZAS_CCM_REGLA WHERE REGLA_ID IN(%s)";
			oxxoQueryCuatro = String.format(consultaCuatro, StrRegla);
			
			consultaCinco = "select * from XXINE.xxine_SUBPLAZAS_REGLA WHERE REGLA_ID IN(%s)";
			oxxoQueryCinco = String.format(consultaCinco, StrRegla);
			
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,PRECONDICION,LAST_UPDATE_DATE from XXINE_BA.xxine_CAT_REGLAS WHERE REGLA_ID IN(%s)";
			baraQuery = String.format(consulta, StrRegla);
			
			consultaDos = "select * from XXINE_BA.xxine_POSTCONDICION_REGLA WHERE REGLA_ID IN(%s)";
			baraQueryDos = String.format(consultaDos, StrRegla);
			
			consultaTres = "select * from XXINE_BA.xxine_ESPACIOS_REGLA WHERE REGLA_ID IN(%s)";
			baraQueryTres = String.format(consultaTres, StrRegla);
			
			consultaCuatro = "select * from XXINE_BA.xxine_PLAZAS_CCM_REGLA WHERE REGLA_ID IN(%s)";
			baraQueryCuatro = String.format(consultaCuatro, StrRegla);
			
			consultaCinco = "select * from XXINE_BA.xxine_SUBPLAZAS_REGLA WHERE REGLA_ID IN(%s)";
			baraQueryCinco = String.format(consultaCinco, StrRegla);
			
		}

		dc.clickSeleccionMultipleReglas(Reglas);
		dc.clickBotonEliminar();

		//		                           PASO 9
		addStep("El sistema mostrara mensaje de alerta \"�Est� seguro de que desea eliminar el/los registro(s) seleccionado(s)?\"");
		dc.clickMsjVentanaModal();
		
		//	  							   PASO 10	
		addStep("Dar clic en confirmar");
		dc.clickConfirEliminar();
		//falta la validacion del mensaje confirmando la eliminaci�n
		
		//								   PASO 11
		addStep("El sistema muestra mensaje de confirmaci�n \"La regla se ha eliminado correctamente\". Validar que las reglas se eliminaron de las tablas XXINE_CAT_REGLAS, XXINE_POSTCONDICION_REGLA, XXINE_ESPACIOS_REGLA, XXINE_PLAZAS_CCM_REGLA, XXINE_SUBPLAZAS_REGLA.\r\n"
				+ "");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		
		// Consulta Uno
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); 			

			if (empty) {							
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);
			}
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE.xxine_CAT_REGLAS");	
			
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); 			

			if (empty) {							
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);
			}
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE_BA.xxine_CAT_REGLAS");	
		}
		
		// Consulta Dos
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo Consulta Dos");
			System.out.println(oxxoQueryDos);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQueryDos);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta		

			if (empty) {
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);			
			}	
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE.xxine_POSTCONDICION_REGLA");
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara Consulta Dos");
			System.out.println(baraQueryDos);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQueryDos);		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta		

			if (empty) {
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);			
			}	
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE_BA.xxine_POSTCONDICION_REGLA");
		
		}
		
		// Consulta Tres
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo Consulta Tres");
			System.out.println(oxxoQueryTres);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQueryTres);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			
			if (empty) {
			     testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);
			}
					
		    assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE.xxine_ESPACIOS_REGLA");
										
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara Consulta Tres");
			System.out.println(baraQueryTres);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQueryTres);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			
			if (empty) {
			     testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);
			}
					
		    assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE_BA.xxine_ESPACIOS_REGLA");
	
		}
		
		// Consulta Cuatro
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo Consulta Cuatro");
			System.out.println(oxxoQueryCuatro);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQueryCuatro);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta	

			if (empty) {
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);				
			}	
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE.xxine_PLAZAS_CCM_REGLA");
			
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara Consulta Cuatro");
			System.out.println(baraQueryCuatro);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQueryCuatro);
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta	

			if (empty) {
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);				
			}	
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE_BA.xxine_PLAZAS_CCM_REGLA");
			
		}
		
		// Consulta Cinco
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo Consulta Cinco");
			System.out.println(oxxoQueryCinco);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQueryCinco);
		
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta

			if (empty) {		
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);		
			}	
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE.xxine_SUBPLAZAS_REGLA");
			
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara Consulta Cinco");
			System.out.println(baraQueryCinco);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQueryCinco);
			boolean empty = queryResult.isEmpty();
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta

			if (empty) {		
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Regla(s) Eliminado(s) " + StrRegla);		
			}	
			
			assertTrue(empty, "No Se elimino el/las Id Regla(s) " + StrRegla + " de  la tabla XXINE_BA.xxine_SUBPLAZAS_REGLA");
		}
		 
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_004_P2_Eliminar_Reglas_Multiseleccion_Admdor_OXXO";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_P2_Eliminar_Reglas_Multiseleccion_Admdor_OXXO_test";
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
