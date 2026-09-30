package WEB.inventarioEspacios.scripts.Usuarios;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.UsuariosIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_ConsultaUsuarios extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ConsultaUsuarios_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Utiler�as
		 ******************************************************************************************/
		
		utils.sql.SQLUtil dbFCIASQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
        /*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String usuario = data.get("Usuario");
		String nomEmp = data.get("NombreEmpleado");
		
		testCase.setTest_Description("Agregar Informaci�n Faltante POP y Plaza - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		UsuariosIEP us = new UsuariosIEP(u, testCase);
		
		u.getDriver().manage().window().maximize();
		u.get(pa.getUrl());
		
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
		SQLResult ExecuteQuery = executeQuery(dbFCIASQA, query);
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
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep(" Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(data.get("Negocio"));
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No Muestra la informaci�n correcta");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar menu->Administracion-> Usuarios");
	    //mp.clickMenus();
		mp.clickAdministracion();
		mp.clickUsuarios();

		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se muestra la pantalla con los filtros:\r\n" + 
				"Usuario\r\n" + 
				"Plaza\r\n" + 
				"Numero de empleado\r\n" + 
				"Cargo\r\n" + 
				"deber� mostrar los registros existentes ordenados por la columna �Nombre de empleado� de forma ascendente por default al ingresar a la pantalla.");
		
		boolean validacion = false;
		boolean existenFiltros = us.visualizarFiltros();
        boolean registrosEx = us.visualizarDatosTabla();
        System.out.print(existenFiltros);
        System.out.print(registrosEx);
		
        if(existenFiltros == true && registrosEx == true) {
        	validacion = true;
        }
		
		assertTrue(validacion, "No se visualizan todos los fi/tros en la pantalla");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Realizar busqueda  por plaza y numero de empleado");
		
		us.typeUsuario(usuario);
		us.typeNombreEmpleado(nomEmp);
		
		us.clickGuardarBtn();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Se muestra el resultado de la busqueda con los campos:\r\n" + 
				"Nombre del Usuario\r\n" + 
				"Numero de empleado\r\n" + 
				"Plaza\r\n" + 
				"Contacto\r\n" + 
				"Cargo/Puesto");


		boolean validarResultados = us.visualizarResultadosBusqueda();

		assertTrue(validarResultados, "No se muestra el resultado");
	
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Validar que el empleado exista en la vista de BD XXINE_USERS_EMPLEADOS_V");
	
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		
		String queryUsers = "select * "+
		        " from "+db+".XXINE_USERS_EMPLEADOS_V" + 
				" where MDM_EMPLEADO_FULL_NAME = '"+nomEmp+"'" + 
				" and XXAC_USER_ID = '"+usuario+"'";
		
		String queryUsers1 = "select XXAC_USER_ID, XXAC_USERNAME, MDM_EMPLEADO_FULL_NAME, KEY_NEGOCIO_ID_VALUE"+
		        " from "+db+".XXINE_USERS_EMPLEADOS_V" + 
				" where MDM_EMPLEADO_FULL_NAME = '"+nomEmp+"'" + 
				" and XXAC_USER_ID = '"+usuario+"'";
		
		String queryUsers2 = "select MDM_EMPLEADO_POSICION, KEY_ROL_BANDEJA_VALUE, KEY_USER_EMAIL_VALUE "+
		        " from "+db+".XXINE_USERS_EMPLEADOS_V" + 
				" where MDM_EMPLEADO_FULL_NAME = '"+nomEmp+"'" + 
				" and XXAC_USER_ID = '"+usuario+"'";
		
		String queryUsers3 = "select KEY_PLAZA_VALUE_LIST "+
		        " from "+db+".XXINE_USERS_EMPLEADOS_V" + 
				" where MDM_EMPLEADO_FULL_NAME = '"+nomEmp+"'" + 
				" and XXAC_USER_ID = '"+usuario+"'";
		
		System.out.println(queryUsers1);
		
		SQLResult queryResults1 = executeQuery(dbFCIASQA, queryUsers1);
		SQLResult queryResults2 = executeQuery(dbFCIASQA, queryUsers2);
		SQLResult queryResults3 = executeQuery(dbFCIASQA, queryUsers3);
		
		boolean consultaVacia = queryResults1.isEmpty();


		testCase.addTextEvidenceCurrentStep(queryUsers);
		testCase.addQueryEvidenceCurrentStep(queryResults1, false);
		testCase.addQueryEvidenceCurrentStep(queryResults2, false);
		testCase.addQueryEvidenceCurrentStep(queryResults3, false);

		assertFalse(consultaVacia, "El empleado no existe en la vista XXINE_USERS_EMPLEADOS_V");
		
		
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
		return "ATC_FT_001_ConsultaUsuarios_test";
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
//		u.getDriver().quit();
	}
}