package WEB.inventarioEspacios.scripts.ConsultaPlazasCCM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.ConsultaPlazasCcmIEP;
import WEB.inventarioEspacios.om.EditarPlazaCcmIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_EditarPlazasCCM extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_EditarPlazasCCM_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/		

		
		String url = data.get("Url");
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		
		String filtro = data.get("Filtro");	
		String nomPlazaCCM1 = data.get("NombrePlazaCCM");		
		String plaza1 = data.get("Plaza");		
			
		String nomPlazaCCM2 = data.get("NuevoNombrePlazaCCM");		
		String plaza2 = data.get("NuevaPlaza");
		String msjPlazaActualizacion = data.get("MensajeActualizacion");

		testCase.setTest_Description("Editar Plazas CCM - "+ perfil+" " + negocio );
	
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+user);
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		ConsultaPlazasCcmIEP ccm = new ConsultaPlazasCcmIEP(u, testCase);
		EditarPlazaCcmIEP ep = new EditarPlazaCcmIEP(u, testCase);
		
		u.getDriver().manage().window().maximize();
		u.get(url);
		
		pa.proceedToPortal();
		u.waitForLoadPage();
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		 
		addStep("Seleccionar Inventario de Espacios");
		
		pa.clickPortalInventarioEspacios();
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		
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
		
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que visualice los MENUS:");
		
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				break;
			
			default:
			     break;

		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Men�->Administraci�n-> Consulta de plazas CCM");
		
	   // mp.clickMenus();
		
		switch(negocio.toUpperCase()) {
		   case "OXXO": 
			   mp.clickAdministracion();
			   break;
			   
		   case "BARA": 
			   mp.clickAdministracion();
			   break;
			   
		   case "MARCA": 
			    mp.clickAdministracionM();                
		        break;
		
		   default:
		        break;
	     }

		mp.clickConsultaPlazasCCMop();

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Se muestran los filtros para la b�squeda:\r\n" + 
				"1. Plaza CCM: \r\n" + 
				"2. Plaza:");
		
		boolean visualizaFiltros = ccm.visualizarCampos();
		
		System.out.print("\nSe muestran los filtros: "+visualizaFiltros);
		
		assertTrue(visualizaFiltros, "No se muestra los filtros");
	
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Se ingesa plaza CCM y/o plaza y se da clic en 'Buscar'");
				
		if(!nomPlazaCCM1.isEmpty()) {
			ccm.typeNombrePlazaCCM(nomPlazaCCM1);
		}
		
		if(!plaza1.isEmpty()) {
			ccm.selectPlaza(plaza1);
		}
		
		ccm.clickBuscarBtn();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("La consulta debe mostrar las siguientes columnas como resultado de la busqueda:\r\n" + 
				"1. ID Plaza CCM\r\n" + 
				"2. Nombre Plaza CCM\r\n" + 
				"3. Plaza ");
		
		boolean validarResultados = ccm.visualizarResultadosBusqueda();

		System.out.print("\nSe muestran los resultados de la busqueda: "+validarResultados);
		
		assertTrue(validarResultados, "No se muestra las columnas y los resultados de la busqueda");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Dar clic en el icono de 'Editar'");
		
		String tdcQueryPlazasCCM1 = "SELECT PLAZA_CCM_ID, PLAZA_ID, NOMBRE_PLAZA_CCM, CREATION_DATE, CREATED_BY"
				+ " FROM "+db+".XXINE_CAT_PLAZAS_CCM"
				+ " WHERE NOMBRE_PLAZA_CCM = '%s'"
				+ " AND PLAZA_ID = %s";
		
		String tdcQueryPlazasCCM2 = "SELECT PLAZA_CCM_ID, PLAZA_ID, NOMBRE_PLAZA_CCM, CREATION_DATE, CREATED_BY"
				+ " FROM "+db+".XXINE_CAT_PLAZAS_CCM"
				+ " WHERE NOMBRE_PLAZA_CCM = '%s'";
		
		String tdcQueryPlazasCCM3 = "SELECT PLAZA_CCM_ID, PLAZA_ID, NOMBRE_PLAZA_CCM, CREATION_DATE, CREATED_BY"
				+ " FROM "+db+".XXINE_CAT_PLAZAS_CCM"
				+ " WHERE PLAZA_ID = %s";
		
		String tdcQueryPlaza = "SELECT PLAZA_ID, NOMBRE_PLAZA, PLAZA_CVE"
				+ " FROM "+db+".XXINE_CAT_PLAZAS" 
				+ " WHERE PLAZA_CVE = '%s'";
		
		String plazaId = "", plazaCcmId = "";
		 
		if(!nomPlazaCCM1.isEmpty() && !plaza1.isEmpty()) {
            String plazaClv = plaza1.replace(" ", "").substring(plaza1.length()-7);	
			
			String queryPlaza = String.format(tdcQueryPlaza, plazaClv);
			SQLResult queryPlazaResult = executeQuery(dbFCIAS, queryPlaza);
			
			if(!queryPlazaResult.isEmpty())
				plazaId = queryPlazaResult.getData(0, "PLAZA_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaResult);
			
			String queryPlazaCcm = String.format(tdcQueryPlazasCCM1, nomPlazaCCM1, plazaId);
			SQLResult queryPlazaCcmResult = executeQuery(dbFCIAS, queryPlazaCcm);	
			
			if(!queryPlazaResult.isEmpty())
			    plazaCcmId = queryPlazaCcmResult.getData(0, "PLAZA_CCM_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult);	
			
			ccm.clickEditarRegistro(nomPlazaCCM1);
			
		}
		
		
		if(!nomPlazaCCM1.isEmpty( ) && plaza1.isEmpty()) {
			
			String queryPlazaCcm = String.format(tdcQueryPlazasCCM2, nomPlazaCCM1);
			SQLResult queryPlazaCcmResult = executeQuery(dbFCIAS, queryPlazaCcm);	
			
			if(!queryPlazaCcmResult.isEmpty())
			    plazaCcmId = queryPlazaCcmResult.getData(0, "PLAZA_CCM_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult);
			
			ccm.clickEditarRegistro(nomPlazaCCM1);
			
		}
		
		if(!plaza1.isEmpty() && nomPlazaCCM1.isEmpty()) {

            String plazaClv = plaza1.replace(" ", "").substring(plaza1.length()-7);	
			
			String queryPlaza = String.format(tdcQueryPlaza, plazaClv);
			SQLResult queryPlazaResult = executeQuery(dbFCIAS, queryPlaza);
			if(!queryPlazaResult.isEmpty())
				plazaId = queryPlazaResult.getData(0, "PLAZA_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaResult);		
			
			String queryPlazaCcm = String.format(tdcQueryPlazasCCM3, plazaId);
			SQLResult queryPlazaCcmResult = executeQuery(dbFCIAS, queryPlazaCcm);	
			
			if(!queryPlazaResult.isEmpty())
			    plazaCcmId = queryPlazaCcmResult.getData(0, "PLAZA_CCM_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult);
			
			plazaCcmId = ccm.clickEditarRegistro();
			
		}

		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Se abre la pantalla de edicion de plazas CCM donde se podra cambiar:\r\n" + 
				"1. Nombre de Plaza CCM\r\n" + 
				"2. Plaza: Mostrar lista desplegable de Plazas. Solo puede seleccionar una plaza");
		
		boolean validarCampos = ep.visualizarCampos();
		
		System.out.print("\nValidar campos: "+validarCampos);
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Se editan los valores correspondientes y se da guardar.");
		
		if(!nomPlazaCCM2.isEmpty()) {
			ep.typeNombrePlazaCCM(nomPlazaCCM2);
		}
		
		if(!plaza2.isEmpty()) {
			ep.selectPlaza(plaza2);
		}
		
		ep.clickGuardarBtn();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("El sistema muestra mensaje de confirmacion.");
		
		ep.validarPopUpGuardarCambios();
		ep.clickBtnConfirmarPopUpGuardar();
		
        //boolean validarMsg = ep.validarMsgPlazaCcmActualizado();
        boolean validarMsg = ep.validarMsgTexto(msjPlazaActualizacion);
		
        System.out.print("\nValidar msg confirmacion de cambios: "+validarMsg);
		assertTrue(validarMsg, "No se mostro el mensaje que confirma la actualizacion de los datos");
			
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Ingresar al esquema de BD XXINE y validar que se guarden los cambios en la plazas de la tabla XXINE_CAT_PLAZAS_CCM");
		
		String tdcQueryPlazasCCM4 = "SELECT PLAZA_CCM_ID, PLAZA_ID, NOMBRE_PLAZA_CCM, CREATION_DATE, CREATED_BY"
				+ " FROM "+db+".XXINE_CAT_PLAZAS_CCM"
				+ " WHERE PLAZA_CCM_ID = %s";
		
		
		boolean validacionBD = false;
		String nomPlazaBD = "", plazaBD = ""; 
		
		String queryPlazaCcm = String.format(tdcQueryPlazasCCM4, plazaCcmId);
		SQLResult queryPlazaCcmResult = executeQuery(dbFCIAS, queryPlazaCcm);
		
		if(!queryPlazaCcmResult.isEmpty()) {
			nomPlazaBD = queryPlazaCcmResult.getData(0, "NOMBRE_PLAZA_CCM");
			plazaBD = queryPlazaCcmResult.getData(0, "PLAZA_ID");
		}
			
		testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult);
		
		if(nomPlazaCCM2.isEmpty() == false && plaza2.isEmpty() == true) {
			System.out.print("\nEntro if 1 ");
			if(nomPlazaBD.equals(nomPlazaCCM2) == true)
				validacionBD = true;
		}
		
		if(plaza2.isEmpty() == false && nomPlazaCCM2.isEmpty() == true) {
			System.out.print("\nEntro if 2 ");
            String plazaClv = plaza2.replace(" ", "").substring(plaza2.length()-7);			
			String queryPlaza = String.format(tdcQueryPlaza, plazaClv);
			SQLResult queryPlazaResult = executeQuery(dbFCIAS, queryPlaza);
			
			if(!queryPlazaResult.isEmpty())
				plazaId = queryPlazaResult.getData(0, "PLAZA_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaResult);
			
			if(plazaBD.equals(plazaId) == true)
				validacionBD = true;
			
		}
		
		if(plaza2.isEmpty() == false && nomPlazaCCM2.isEmpty() == false) {
			System.out.print("\nEntro if 3 ");
			String plazaClv = plaza2.replace(" ", "").substring(plaza2.length()-7);			
			String queryPlaza = String.format(tdcQueryPlaza, plazaClv);
			SQLResult queryPlazaResult = executeQuery(dbFCIAS, queryPlaza);
			
			if(!queryPlazaResult.isEmpty())
				plazaId = queryPlazaResult.getData(0, "PLAZA_ID");
			
			testCase.addQueryEvidenceCurrentStep(queryPlazaResult);
			
			if(nomPlazaBD.equals(nomPlazaCCM2) == true&& plazaBD.equals(plazaId) == true)
				validacionBD = true;
		}

		
		System.out.print("\nSe reflejan los cambios: "+validacionBD);
			
		assertTrue(validacionBD, "No se guardaron los cambios en la tabla XXINE_CAT_PLAZAS_CCM");
			

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
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
