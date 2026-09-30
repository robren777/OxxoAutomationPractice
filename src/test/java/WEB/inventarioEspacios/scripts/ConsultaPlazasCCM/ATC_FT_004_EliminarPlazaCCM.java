package WEB.inventarioEspacios.scripts.ConsultaPlazasCCM;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.ConsultaPlazasCcmIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_EliminarPlazaCCM extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_EliminarPlazaCCM_test(HashMap<String, String> data) throws Exception {
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
		
		String nomPlazaCCM = data.get("NombrePlazaCCM");		
		String plaza = data.get("Plaza");		

		testCase.setTest_Description("Eliminar Plazas CCM - "+ perfil+" " + negocio );
	
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+user);
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		ConsultaPlazasCcmIEP ccm = new ConsultaPlazasCcmIEP(u, testCase);
		
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

		mp.clickConsultaPlazasCCMop();;

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Seleccionar una o mas Plazas CCM dando clic en el checkbox");
		
		if(!nomPlazaCCM.isEmpty()) {
			ccm.typeNombrePlazaCCM(nomPlazaCCM);
		}
		
		if(!plaza.isEmpty()) {
			ccm.selectPlaza(plaza);
		}
		
		ccm.clickBuscarBtn();
		
		
		ArrayList <String> valores = new ArrayList<String>();		
	    String plazas = "";
	    int cont = 0;
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("NombrePlazaEliminar:") && value.trim().length() > 0) {
		    	valores.add(value);
		    	if(cont > 0) {
		    		plazas += "','";
		    	}
		    	plazas += value;
		    	cont++;	
			}
		    
		}
		
		ccm.seleccionarRegistro(valores);
		
		String tdcQueryPlazasCCM = "SELECT PLAZA_CCM_ID, PLAZA_ID, NOMBRE_PLAZA_CCM, CREATION_DATE, CREATED_BY"
				+ " FROM "+db+".XXINE_CAT_PLAZAS_CCM"
				+ " WHERE NOMBRE_PLAZA_CCM IN ('%s')";
		
		String queryPlazaCcm = String.format(tdcQueryPlazasCCM, plazas);
		SQLResult queryPlazaCcmResult = executeQuery(dbFCIAS, queryPlazaCcm);
		testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult);
		
	
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Se muestra el boton Eliminar");
		
		boolean btnEliminar = ccm.visualizarBotonEliminar();				
		assertTrue(btnEliminar, "No se muestra el boton Eliminar");
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Dar clic en Eliminar");
		
		ccm.clickEliminarBtn();
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("El sistema muestra mensaje: '�Desea eliminar la Plaza CCM seleccionada?'");		
		
        boolean validarMsj1 = ccm.validarMsjConfirmacionTexto(data.get("MensajeConfirmacion"));      
        assertTrue(validarMsj1, "No se mostro mensaje notificando que se eliminaron los registros");
		
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Dar clic en confirmar");
		
		ccm.seleccinarOpcionMsjConfirmacion(data.get("OpcionMsjConfirmacion"));

		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("El sistema notifica que se elimino la o las Plazas CCM");
		
		boolean validarMsj2 = ccm.validarMsgTexto(data.get("MensajeEliminar"));
		assertTrue(validarMsj2, "No se mostro mensaje notificando que se eliminaron los registros");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Ingresar al esquema de BD XXINE y validar que se elimino la o las plazas en la tabla XXINE_CAT_PLAZAS_CCM");
		
		String queryPlazaCcm2 = String.format(tdcQueryPlazasCCM, plazas);
		SQLResult queryPlazaCcmResult2 = executeQuery(dbFCIAS, queryPlazaCcm2);
		testCase.addQueryEvidenceCurrentStep(queryPlazaCcmResult2);

		boolean validacionBD = queryPlazaCcmResult2.isEmpty();

		System.out.print("\nValidacion BD: "+validacionBD);
		assertTrue(validacionBD, "No se eliminaron los registros");
			

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
