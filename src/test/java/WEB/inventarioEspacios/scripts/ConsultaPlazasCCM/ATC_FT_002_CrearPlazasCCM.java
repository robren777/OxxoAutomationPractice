package WEB.inventarioEspacios.scripts.ConsultaPlazasCCM;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.ConsultaPlazasCcmIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.NuevaPlazaCcmIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_CrearPlazasCCM extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CrearPlazasCCM_test(HashMap<String, String> data) throws Exception {
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
		String msjPlazaCreada = data.get("MensajeCreacion");
		
		String nomPlazaCCM = data.get("NombrePlazaCCM");		
		String plaza = data.get("Plaza");		
		

		testCase.setTest_Description("Crear Plazas CCM - "+ perfil+" " + negocio );
	
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+user);
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		ConsultaPlazasCcmIEP ccm = new ConsultaPlazasCcmIEP(u, testCase);
		NuevaPlazaCcmIEP np = new NuevaPlazaCcmIEP(u, testCase);
		
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
		
		addStep("Seleccionar Men�->Administraci�n-> Consulta de plazas");
		
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
		addStep("Dar clic en el boton 'Nuevo'");
		
		ccm.clickNuevoBtn();
		
	
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("El sistema muestra la pantalla de creacion de Plaza CCM con los campos:\r\n" + 
				"1. Nombre Plaza CCM\r\n" + 
				"2. Plaza: Lista desplegable de plazas. Solo puede seleccionar una plaza.");
				

	    boolean visualizarCampos = np.visualizarCampos();
	    
	    System.out.print("\nSe muestran los filtros pantalla creacion de plaza ccm: "+visualizarCampos);
		
	    assertTrue(visualizarCampos, "No se muestran los campos esperados");
		
	    /**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Ingresar los datos requeridos y dar clic en guardar");
		
		np.typeNombrePlazaCCM(nomPlazaCCM);
		np.selectPlaza(plaza);
		
		np.clickGuardarBtn();
	
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("El sistema Crea la nueva plaza CCM mostrando mensaje de creacion en pantalla.");
		
		np.validarPopUpGuardarCambios();
		np.clickBtnConfirmarPopUpGuardar();
		
		//boolean validarMsg = np.validarMsgPlazaCcmCreada();
		boolean validarMsg = np.validarMsgTexto(msjPlazaCreada);
		
		System.out.print("\nSe muestra el mensaje de creacion plaza ccm: "+validarMsg);
		assertTrue(validarMsg, "No se mostro el mensaje de creacion de plaza CCM");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Ingresar al esquema de BD XXINE y validar que se guarde la o las plazas en la tabla XXINE_CAT_PLAZAS_CCM");

		String tdcQueryPlazasCCM = "SELECT PLAZA_CCM_ID, PLAZA_ID, NOMBRE_PLAZA_CCM, CREATION_DATE, CREATED_BY"
				+ " FROM "+db+".XXINE_CAT_PLAZAS_CCM"
				+ " WHERE NOMBRE_PLAZA_CCM = '%s'";
		
		String tdcQueryPlaza = "SELECT PLAZA_ID, NOMBRE_PLAZA, PLAZA_CVE"
				+ " FROM "+db+".XXINE_CAT_PLAZAS" 
				+ " WHERE PLAZA_CVE = '%s'";
		
		String queryPlazaCcm = String.format(tdcQueryPlazasCCM, nomPlazaCCM);
		SQLResult queryPlazaCcmResult = executeQuery(dbFCIAS, queryPlazaCcm);			
		testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult);
		
		String plazaClv = plaza.replace(" ", "").substring(plaza.length()-7);		
		String queryPlaza = String.format(tdcQueryPlaza, plazaClv);
		SQLResult queryPlazaResult = executeQuery(dbFCIAS, queryPlaza);
		testCase.addQueryEvidenceCurrentStep(queryPlazaResult);		
		
		
		System.out.print("\nConsulta esta vacia: "+queryPlazaCcmResult.isEmpty());
			
		assertFalse(queryPlazaCcmResult.isEmpty(), "No se guardo la plaza en la tabla XXINE_CAT_PLAZAS_CCM");
			

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
		//u.getDriver().quit();
	}

}
