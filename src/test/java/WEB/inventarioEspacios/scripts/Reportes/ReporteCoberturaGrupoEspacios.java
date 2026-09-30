package WEB.inventarioEspacios.scripts.Reportes;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.ReporteCgeIEP;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ReporteCoberturaGrupoEspacios extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ReportesCoberturaGrupoEspacios_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/		
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		
		String sistema = data.get("Sistema");		
		String plaza = data.get("Plaza");		
		String subPlaza = data.get("SubPlaza");		
		String periodoA = data.get("PeriodoA");
		String periodoB = data.get("PeriodoB");

		
		testCase.setTest_Description("Reportes: Cobertura de grupos de espacios - "+ perfil+" " + negocio + " - " + sistema);
		
		
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+user);
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		ReporteCgeIEP rcge = new ReporteCgeIEP(u, testCase);
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
/*		
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
		/*
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
		/*
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		//assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar el Menu Reportes/Cobertura de grupos de espacios");
		
	    mp.clickMenus();
		
		switch(perfil.toUpperCase()) {
		   case "ADMINISTRADOR": 
			   if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				    mp.clickReportes(); 
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mp.clickReportesM();
				}
                
		        break;
		
		   case "PLAZA": mpo.menuPlaza();
		        mp.clickReportesPO();
		        break;
		
		   default:
		        break;
	     }

		mp.clickReporteCoberturaGrupoEspacios();
		

		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Se visualizan los filtros: Sistema, Plaza, Subplaza, Periodo A, Periodo B " + 
				"Checkbox de 'Ver subplazas' y Los botones : 'Exportar','Limpiar','Buscar'");
		
		boolean visualizaFiltros = rcge.visualizarCampos();//revisa lo del checkbox de ver plazas
		
		assertTrue(visualizaFiltros, "No se visualizan los filtros y/o botones");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Llenar los campos para la busqueda y dar click en el boton 'Buscar'");
		
	    rcge.selectSistema(sistema);
	    rcge.selectPlaza(plaza);
	    rcge.selectSubplaza(subPlaza);
	    rcge.selectPeriodoA(periodoA);
	    rcge.selectPeriodoB(periodoB);
	    	    
	    rcge.clickBuscarBtn();
	    
	    boolean validarLlenado = rcge.validarMsgFaltaLlenarFiltros();
	    	
	   // assertFalse(validarLlenado, "Faltaron filtro por ingresar");
			
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		/*addStep("Al seleccionar la opci�n 'Buscar' el sistema muestra la informaci�n de acuerdo a los filtros de Busqueda, "
				+ "la cual se encuentra conformada de la siguiente manera Plaza/Tienda, " + 
				"Subplaza: Si se selecciona el checkbox de seleccion se muestra la subplaza, " + 
				"Nombre del Grupo de espacios, " + 
				"% de cobertura periodo A, " + 
				"% De cobertura periodo B, " + 
				"Total de tiendas, " + 
				"Total de  Gpo de espacios configurados, " + 
				"La informaci�n podra ser descargada en formato Excel selecciondo la opci�n Exportar a Excel");
		
		boolean visualizarResultados = rcge.visualizarResultados();
		
	
		 assertTrue(visualizarResultados, "No se visualizan los resultados");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		/*addStep("Validar que las opciones que aparecen en ambos periodos sean hasta dos a�os anteriores");
		
		boolean validarPeriodos = false;
		
		boolean periodA = rcge.validarPeriodoA();
		boolean periodB = rcge.validarPeriodoB();
		
		if( periodA == true && periodB == true)
			validarPeriodos = true;
		
		
		System.out.print("\nValidacion periodos A y B: "+validarPeriodos);
		 //assertTrue(validarPeriodos, "Las opciones que aparecen no cumplen con el rango");
			
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Seleccionar Hipervinculo  de la plaza.");
		
		rcge.clickPlazaLink();
	
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Se muestra el grid desplegado con los siguientes datos: Grupo de espacios, " + 
				"Cobertura periodo A y Cobertura periodo B");
		
	    boolean visualizarGridDesplegado = rcge.visualizarResultadosGrupoEspacios(false);
	    
	    System.out.print("\nSe muestra el grid desplegado: "+visualizarGridDesplegado);
	    
	    assertTrue(visualizarGridDesplegado, "No se visualizan los datos");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Dar clic en Exportar Excel");
		
		rcge.clickExportarBtn();		
	    u.hardWait(10);
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("El sistema exporta el reporte a excel y contiene la informacion correcta mostrada en pantalla.");
			
		MetodosExtra m = new MetodosExtra(u, testCase);
		
		boolean validarAbrirArchivo = m.abrirArchivo("Reporte_Plaza_Grupos_Espacios_Tiendas", "Edge");

	    assertTrue(validarAbrirArchivo, "No se encontro el archivo");

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
		return "ATC_FT_001_ReportesCoberturaEspacios_test";
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
