package WEB.inventarioEspacios.scripts.SubPlazas;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.SubPlaza_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.ISQLResult;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
// NOTA: Se omitieron los pasos 2, 4 y 6 de este caso de prueba
public class P2_SUBPLAZAS_PLAZA_OXXO_Busqueda_Nuevo extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_066_P2_SUBPLAZAS_PLAZA_OXXO_Busqueda_Nuevo_Test(HashMap<String, String> data) throws Exception {
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
		String subplaza = data.get("SubPlaza");
		String Nombre = data.get("Nombre");
		String Plaza = data.get("Plaza");
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.equals("Oxxo")) {
			System.out.println("Entro al Select por Oxxo/Plaza");
			String consulta = "select SUBPLAZA_ID,PLAZA_ID,NOMBRE_SUBPLAZA,LAST_UPDATE_DATE,LAST_UPDATED_BY from XXINE.xxine_cat_subplazas WHERE NOMBRE_SUBPLAZA = '%s'";
			oxxoQuery = String.format(consulta, Nombre);
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select SUBPLAZA_ID,PLAZA_ID,NOMBRE_SUBPLAZA,LAST_UPDATE_DATE,LAST_UPDATED_BY from XXINE_BA.xxine_cat_subplazas WHERE NOMBRE_SUBPLAZA = '%s'";
			baraQuery = String.format(consulta, Nombre);
			
		}
		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		//		                            PASO 3
		addStep("Seleccionar Inventario de Espacios"); //En bara es el paso 2
		pa.clickPortalInventarioEspacios();
	
		//                                 PASO 5
		addStep(" Validar que visualice los MENUS:"); // En bara es el paso 4 
//		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
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
		                      
		//                                 PASO 7
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo."); //En bara es el paso 5
		mp.verificarEncabezado("Administrador");
	
		//                                 PASO 8
		addStep("El sistema muestra el Negocio, periodo y estatus del periodo."); //En bara es el paso 3
		mp.verificarInformacion(user, negocio, perfil);
		
		//                                 PASO 9
		addStep("Seleccionar menu Administrador : SubPlazas"); //En bara es el paso 6
		SubPlaza_IEP sp = new SubPlaza_IEP(u, testCase);
		sp.clickSubplazasPlaza();
		
		
		//                                 PASO 10
		addStep("\"\"Se muestra la pantalla con los filtros:\r\n"
				+ "Nombre Subplaza\r\n"
				+ "y los botones de \"\"Nuevo\"\",\"\"Limpiar\"\" y \"\"Buscar\"\"\"\r\n"
				+ "");																		//En bara es el paso 7
		sp.clickCampos();

		//                                 PASO 11
		addStep("Ingresar filtros y dar click en Buscar");	//En bara es el paso 8	
		sp.clickIngresaFiltroBuscar(subplaza);
		sp.clickBtnBuscar();
		
		//                                 PASO 12
		addStep("\"Se muestran los resultados en pantalla con los campos:\n" //En bara es el paso 9
				+ "ID \n"
				+ "NOMBRE \n"
				+ "CLAVE PLAZA \n"
				+ "NOMBRE PLAZA \n"
				+ "BOTON \"\"EDITAR");
		sp.clickResPasoNueve();

		//                                 PASO 13
		addStep("Dar click en el boton Nuevo"); //En bara es el paso 10
		sp.clickBtnNuevo();
		//sp.clickBtnEditar(idSubPlaza);

		//                                 PASO 14
		addStep("\"\"Se abre una nueva pantalla con los campos:\r\n"
				+ "Nombre\r\n"
				+ "Plaza\r\n"
				+ "Y los botones \"\"Cancelar\"\" y \"\"Guardar\"\"\"\r\n"
				+ "");														//En bara es el paso 11
		Boolean flag = false;
		sp.clickNuevaPantalla(flag);
        
		//                                 PASO 15
		addStep("Llenar los campos y dar click en el boton Guardar"); //En bara es el paso 12
		sp.clickLlenaCampos(Nombre,Plaza);

		//                                 PASO 16
		addStep("\"Se muestra un mensaje de cionfirmacion que dice Se ha guardado exitosamente"); //En bara es el paso 13
		flag = false;
		sp.clickBtnGuardar(flag);
		//sp.clickCampos();

		//                                 PASO 17
		addStep("Ingresar en la Tabla XXINE_CAT_SUBPLAZAS y validar que se haya creado el nuevo registro."); //En bara es el paso 14 y 15
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.equals("Oxxo")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				assertFalse(empty, "No Se Guardo la Descripcion SubPlaza: " + Nombre + " de  la tabla  XXINE_CAT_SUBPLAZAS");
		
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Descripcion SubPlaza: " + Nombre);
		}else if (negocio.equals("Bara")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
			
			SQLResult queryResultBara = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = ((ISQLResult) queryResultBara).isEmpty();

			if (empty) {
			
				assertFalse(empty, "No Se Guardo la Descripcion Estatus: " + Nombre + " de  la tabla XXINE_BA.xxine_CAT_ESTATUS_MENSAJERIA");
		
			}						
				testCase.addQueryEvidenceCurrentStep(queryResultBara); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
				testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Descripcion Estatus: " + Nombre);
			
		}

	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_066_P2_SUBPLAZAS_PLAZA_OXXO_Busqueda_Nuevo_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_066_P2_SUBPLAZAS_PLAZA_OXXO_Busqueda_Nuevo_Test";
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
