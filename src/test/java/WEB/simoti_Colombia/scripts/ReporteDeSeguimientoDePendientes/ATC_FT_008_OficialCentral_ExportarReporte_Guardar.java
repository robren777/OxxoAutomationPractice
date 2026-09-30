package WEB.simoti_Colombia.scripts.ReporteDeSeguimientoDePendientes;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.ReporteDeSeguimientoDePendientes_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_008_OficialCentral_ExportarReporte_Guardar extends BaseExecution{
	/**Desc: Validar que se permita exportar a formato excel los resultados de la consulta del reporte con usuario asociado a un perfil Oficial Central
     *Validar nomenclatura
     *Validar el contenido 
     *Que se permita guardar
	 * MTC-FT-008 Oficial Central_Reporte de Seguimiento de Pendientes_Exportar Reporte_Guardar
	 * Nota. Falta terminar de construir los ultimos 3 pasos, debido a que no hay informaci�n en el sistema
	 * @author 
	 * @date Febrero 2023
	 */
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_OficialCentral_ExportarReporte_Guardar_test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil dbRmsCol = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA_QAVIEW, GlobalVariables.DB_USER_BDRMSCQA_QAVIEW,GlobalVariables.DB_PASSWORD_BDRMSCQA_QAVIEW);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		String cr_plaza = data.get("CR_Plaza");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ReporteDeSeguimientoDePendientes_SimotiColombia rsp = new ReporteDeSeguimientoDePendientes_SimotiColombia (u, testCase);
		Boolean validacion;
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiColombia();
		boolean validarMenusCedis = mp.validarMenuUsuarioCedis();//tambien funciona para el usuario plaza	
		assertTrue(validarMenusCedis, "No se muestran los menus especificados");
		u.hardWait(2);
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: Transmisi�n de Datos y selecciona el reporte: Reporte "
				+ "Promociones");
		
		mp.clickTransmisionDatosPlazaCedisCentral();
		u.hardWait(3);
		mp.clickReportePromociones();
		validacion = rsp.validarNombrePantalla();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
			
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros:\r\n" + 
				"Plaza: <se muestra la Plaza del usuario>\r\n" + 
				"Fecha Inicio: <seleccionar valor>\r\n" + 
				"Fecha Fin: <Seleccionar valor>\r\n" + 
				"Dar clic en [Consultar]");

		validacion = rsp.filtrosReporteSeguimiento(data.get("Plaza"), data.get("FechaInicio"), data.get("FechaFin"));
		
		rsp.clickConsultar(); 
		
		validacion = rsp.validarExistanResultados();
		assertTrue(validacion, "No se muestran resultados en el grid");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en la opci�n: [Exportar]");
		
		rsp.clickExportar();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Guardar]. El sistema permite guardar el archivo con el formato y nomenclatura correctamente.\r\n" + 
				"El nombre del archivo generado est�r� conformado por el mismo nombre del reporte separando cada palabra por el car�cter �_� y la fecha de generaci�n con el formato ddmmaaaa, por ejemplo:\r\n" + 
				"Reporte_Seguimientos_Promociones_10022018.xlsx.");
		
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\r\n" + 
				"1. No: Number\r\n" + 
				"2. Promoci�n: Number\r\n" + 
				"3. Descripci�n:String\r\n" + 
				"4. Fecha Inicio: Date dd/mm/aaaa\r\n" + 
				"5 .Fecha Fin:Date dd/mm/aaaa");
		
	
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query.\r\n" + 
				"\r\n" + 
				" select distinct prom.promotion, ph.PROM_NAME,\r\n" + 
				"       to_char( prom.START_DATE, 'dd/mm/yyyy'),\r\n" + 
				"       to_char( prom.END_DATE, 'dd/mm/yyyy'),\r\n" + 
				"       substr( st.STORE_NAME10,1,5 ) \r\n" + 
				"  from promstore prom, store st, promhead ph\r\n" + 
				" where prom.STORE = st.STORE \r\n" + 
				"   and prom.START_DATE >= to_date('08/01/2018','DD/MM/YYYY')\r\n" + 
				"   and prom.END_DATE <= to_date('01/03/2018','DD/MM/YYYY')\r\n" + 
				"   and substr( st.STORE_NAME10,1,5 ) = '10CAN'\r\n" + 
				"   and ph.PROMOTION = prom.PROMOTION");
		
		//Formato del data provider: 31*Ene*2023 - se debe cambiar el * a / para poder realizar la consulta
		//Faltar�a crear el metodo que permita cambiar los meses a su valor en numero
		String fechaInicio = data.get("FechaInicio").replace("*", "/");
		String fechaFin = data.get("FechaFin").replace("*", "/");
		
		String query = "select distinct prom.promotion, ph.PROM_NAME,\r\n" + 
				"to_char( prom.START_DATE, 'dd/mm/yyyy'),\r\n" + 
				"to_char( prom.END_DATE, 'dd/mm/yyyy')\r\n" + 
				"from promstore prom, store st, promhead ph\r\n" + 
				"where prom.STORE = st.STORE \r\n" + 
				"and prom.START_DATE >= to_date('"+fechaInicio+"','DD/MM/YYYY')\r\n" + 
				"and prom.END_DATE <= to_date('"+fechaFin+"','DD/MM/YYYY')\r\n" + 
				"and substr( st.STORE_NAME10,1,5 ) = '"+cr_plaza+"'\r\n" + 
				"and ph.PROMOTION = prom.PROMOTION";		
        System.out.println(query);
       
        
        
		SQLResult queryResults = executeQuery(dbRmsCol, query);
		
		boolean empty = queryResults.isEmpty();
		testCase.addQueryEvidenceCurrentStep(queryResults);

		assertFalse(empty, "No se muestra informacion en la consulta");
		
		//Falta realizar validacion sobre que la informacion es la misma en la BD y en el reporte
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Validar que se permita exportar a formato excel los resultados de la consulta del reporte con usuario asociado a un perfil Oficial Central\r\n" + 
				"*Validar nomenclatura\r\n" + 
				"*Validar el contenido \r\n" + 
				"*Que se permita guardar";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_OficialCentral_ExportarReporte_Guardar_test";
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
