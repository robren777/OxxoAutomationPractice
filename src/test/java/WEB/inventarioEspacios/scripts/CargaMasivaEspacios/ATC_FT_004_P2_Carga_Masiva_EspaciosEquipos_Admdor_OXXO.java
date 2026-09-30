package WEB.inventarioEspacios.scripts.CargaMasivaEspacios;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.CargaMasivaEspaciosEquipos;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_P2_Carga_Masiva_EspaciosEquipos_Admdor_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_004_P2_Carga_Masiva_EspaciosEquipos_Admdor_OXXO_test(HashMap<String, String> data) throws Exception {
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
		int GrupoEspacio = Integer.parseInt(data.get("Grupo_Espacio"));
		
		String oxxoQuery = "";
		String baraQuery = "";

		if (negocio.toUpperCase().equals("OXXO")) {
			String consulta = "SELECT  ESPACIO_ID,NOMBRE_ESPACIO,SOLO_PLAZAS,NEGOCIO_ID,NOMBRE_SOLICITANTE from XXINE.xxine_CAT_ESPACIOS WHERE trunc(LAST_UPDATE_DATE) = trunc(Sysdate)";    //IN('%s')
			oxxoQuery = String.format(consulta);
			
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "SELECT  ESPACIO_ID,NOMBRE_ESPACIO,GRUPO_ESPACIOS_ID,SOLO_PLAZAS,NEGOCIO_ID from XXINE_BA.xxine_CAT_ESPACIOS WHERE trunc(LAST_UPDATE_DATE) = trunc(Sysdate)";
			baraQuery = String.format(consulta);
			
		}
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
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
		addStep("Seleccionar men� ->Espacios->Carga masiva de Espacios/Equipos");
		CargaMasivaEspaciosEquipos cm = new CargaMasivaEspaciosEquipos(u, testCase);
		cm.clickMenuEspaciosCargaMasivaEspacios();
		
		//      						   PASO 7
		addStep("Se abre la pantalla de Carga masiva de Espacios/Equipo con los campos:\r\n"
				+ "Seleccionar Archivos, Bot�n \"Subir Archivo\"\r\n"
				+ "Filtros de b�squeda por fecha, botones Limpiar y Buscar.\r\n"
				+ "Campos: #, Archivo, Fecha inicio, Fecha fin, Conteo, Estatus.");
		cm.clickMuestraCampos();
		cm.clickMuestraGrid();
		
		//	  							   PASO 8	
		addStep("Cargar archivo Excel de Espacios/Equipos y dar clic en el bot�n subir.");
		cm.clickSelArchivo();

		//		                           PASO 9
		addStep("El sistema sube el archivo al servidor y genera una l�nea de procesamiento con estatus pasando por los estatus: \"Pendiente\"->\"Validado\"-> \"En proceso\".");
		cm.clickSubirArchivo();
		
		//	  							   PASO 10	
		addStep("El proceso de carga termina en estatus  \"Procesado\" y muestra conteo final.");
		cm.clickLineaProcesamiento();
		
		//								   PASO 11
		addStep("Validar que los espacios/equipos se encuentren en la tabla XXINE_CAT_ESPACIOS");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		
		// Consulta Uno
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
			//boolean empty = ((ISQLResult) queryResult).isEmpty();
			boolean empty = queryResult.isEmpty();
			
			if (empty) {		
				testCase.addTextEvidenceCurrentStep("La informacion no fue cargada en la tabla XXINE.XXINE_CAT_ESPACIOS");			
			}	else {				
				testCase.addTextEvidenceCurrentStep("Informacion cargada en La Tabla XXINE.XXINE_CAT_ESPACIOS");				
			}
			
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta	
			assertFalse(empty, "La Informacion No Fue Cargada En La Tabla XXINE.XXINE_CAT_ESPACIOS");
			
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
			//boolean empty = ((ISQLResult) queryResult).isEmpty();
			boolean empty = queryResult.isEmpty();
			

            if (empty) {				
				testCase.addTextEvidenceCurrentStep("La informacion no fue cargada en la tabla XXINE.XXINE_CAT_ESPACIOS");				
			}	else {				
				testCase.addTextEvidenceCurrentStep("Informacion cargada en La Tabla XXINE.XXINE_CAT_ESPACIOS");				
			}
			
            testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			assertFalse(empty, "La Informacion No Fue Cargada En La Tabla XXINE.XXINE_CAT_ESPACIOS");		
			
			
		}
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_004_P2_Carga_Masiva_EspaciosEquipos_Admdor_OXXO_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_P2_Carga_Masiva_EspaciosEquipos_Admdor_OXXO_test";
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
