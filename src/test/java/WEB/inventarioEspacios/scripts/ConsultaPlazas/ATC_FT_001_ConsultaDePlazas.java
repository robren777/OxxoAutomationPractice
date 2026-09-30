package WEB.inventarioEspacios.scripts.ConsultaPlazas;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.ConsultaPlazasIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_ConsultaDePlazas extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ConsultaDePlazas_Test(HashMap<String, String> data) throws Exception {
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
		
		String filtro1 = data.get("FiltroSeleccionado1");	
		String nomPlaza1 = data.get("NombrePlaza1");		
		String clavePlaza1 = data.get("ClavePlaza1");		
		
		String filtro2 = data.get("FiltroSeleccionado2");	
		String nomPlaza2 = data.get("NombrePlaza2");		
		String clavePlaza2 = data.get("ClavePlaza2");	

		testCase.setTest_Description("Consulta De Plazas - "+ perfil+" " + negocio );
	
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+user);
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		ConsultaPlazasIEP cp = new ConsultaPlazasIEP(u, testCase);
		
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
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
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

		mp.clickConsultaPlazasOp();

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Se muestran los filtros para la b�squeda:\r\n" + 
				"1. Nombre de Plaza\r\n" + 
				"2. Clave de la Plaza");
		
		boolean visualizaFiltros = cp.visualizarCampos();
		
		System.out.print("\nSe muestran los filtros: "+visualizaFiltros);
		
		assertTrue(visualizaFiltros, "No se muestra los filtros");
	
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Se ingesa nombre de plaza o clave de plaza y se da clic en 'Buscar'");
				
		String valor1 = "";
		
		switch(filtro1.toUpperCase()) {
		
		   case "NOMBRE": 		
			   valor1 = nomPlaza1;
			   cp.typeNombrePlaza(nomPlaza1);
			   break;
			   
		   case "CLAVE": 
			   valor1 = nomPlaza1;
			   cp.typeClavePlaza(clavePlaza1);
			   break;
		
	     }
		
		cp.clickBuscarBtn();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("La consulta debe mostrar las siguientes columnas como resultado de la busqueda:\r\n" + 
				"1. Id Plaza\r\n" + 
				"2. Nombre de Plaza\r\n" + 
				"3. Clave de la Plaza");
		
		boolean validarResultados1 = cp.visualizarResultadosBusqueda(valor1, filtro1);
		
		System.out.print("\nSe muestran los resultados de la busqueda: "+validarResultados1);
		
		assertTrue(validarResultados1, "No se muestra las columnas y los resultados de la busqueda");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Dar clic en el boton 'Limpiar'");
		
		cp.clickLimpiarBtn();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Se limpian los datos de filtros y resultado de la busqueda");
		
		//boolean valFiltrosVacios = cp.validarFiltrosVacios();
		
		boolean valFiltrosVacios = cp.validarLimpiezaFiltrosGrid(valor1, filtro1);
		
		System.out.print("\nSe limpian los datos de los filtros: "+valFiltrosVacios);
		
		assertTrue(valFiltrosVacios, "No se limpian los campos ni los resultados de la busqueda");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Se ingesa nombre de plaza o clave de plaza y se da clic en 'Buscar'");
		
		String valor2 = "";
		switch(filtro2.toUpperCase()) {
		
		   case "NOMBRE": 		   
			   valor2 = nomPlaza2;
			   cp.typeNombrePlaza(nomPlaza2);
			   break;
			   
		   case "CLAVE": 
			   valor2 = clavePlaza2;
			   cp.typeClavePlaza(clavePlaza2);
			   break;
		
	     }
		
		cp.clickBuscarBtn();
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("La consulta debe mostrar las siguientes columnas como resultado de la busqueda:\r\n" + 
				"1. Id Plaza\r\n" + 
				"2. Nombre de Plaza\r\n" + 
				"3. Clave de la Plaza");		
	
        boolean validarResultados2 = cp.visualizarResultadosBusqueda(valor2, filtro2);
		
		System.out.print("\nSe muestran los resultados de la busqueda: "+validarResultados2);
		
		assertTrue(validarResultados2, "No se muestra las columnas y los resultados de la busqueda");
	

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
		return "ATC_FT_001_ConsultaDePlazas_Test";
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
