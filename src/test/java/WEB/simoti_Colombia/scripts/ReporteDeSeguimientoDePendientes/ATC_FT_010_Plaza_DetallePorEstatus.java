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

/**Desc: Validar que se genere el reporte y que se muestren los datos correctamente de acuerdo a los filtros seleccionados de usuario plaza 
 * MTC-FT-011 Plaza_Reporte de Seguimiento de Pendientes_Generar Reporte_Detalle por estatus
 * Nota. Falta terminar de construir los �ltimos 3 pasos, debido a que no hay informaci�n en el sistema
 * @author 
 * @date Febrero 2023
 */
public class ATC_FT_010_Plaza_DetallePorEstatus extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_Plaza_DetallePorEstatus_test(HashMap<String, String> data) throws Exception {
		
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
		String mensajeError = "No se ha encontrado informaci�n con los filtros seleccionados.";
		
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
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: Transmisi�n de Datos y selecciona el reporte: Reporte "
				+ "Promociones");
		
		mp.clickTransmisionDatos();
		u.hardWait(3);
		mp.clickReportePromociones();
		
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros:\r\n" + 
				"Plaza: <se muestra la Plaza del usuario>\r\n" + 
				"Fecha Inicio: <seleccionar valor>\r\n" + 
				"Fecha Fin: <Seleccionar valor>\r\n" + 
				"Dar clic en [Consultar]");
		u.hardWait(3);
		validacion=rsp.Datosplaza(data.get("Plaza"));
		assertTrue(validacion, "No se insertaro correctamente la plaza");
		validacion = rsp.filtrosReporteSeguimiento (data.get("FechaInicio"), data.get("FechaFin"));
		assertTrue(validacion, "No se insertaron correctamente los datos proporcionados");
		
		rsp.clickConsultar();
		u.hardWait(1);
		
		validacion = rsp.validarExistanResultados();
		assertTrue(validacion, "No se muestran resultados en el grid");

		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query.\r\n" + 
				"select distinct prom.promotion, ph.PROM_NAME,\r\n" + 
				"       to_char( prom.START_DATE, 'dd/mm/yyyy'),\r\n" + 
				"       to_char( prom.END_DATE, 'dd/mm/yyyy')\r\n" + 
				"  from promstore prom, store st, promhead ph\r\n" + 
				" where prom.STORE = st.STORE \r\n" + 
				"   and prom.START_DATE >= to_date('01/09/2017','DD/MM/YYYY')\r\n" + 
				"   and prom.END_DATE <= to_date('01/10/2017','DD/MM/YYYY')\r\n" + 
				"   and substr( st.STORE_NAME10,1,5 ) = '10MEX'\r\n" + 
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
		
		//Falta realizar validacion sobre que la informacion es la misma en la BD y en la pantalla
		
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Dar clic en una promoci�n con estatus <L>\r\n" + 
				"El sistema muestra la pantalla con las siguientes caracteristicas.\r\n" + 
				"Se muestra el Titulo de la Pantalla: Reporte de Consultar Promociones  \r\n" + 
				"Consulta detalle promoci�n N�mero de Promoci�n\r\n" + 
				"ESTATUS DE LA PROMOCI�N       PARA LA PLAZA: CR_PLAZA\r\n" + 
				"Se muestra la tabla con la informaci�n de las siguientes columnas\r\n" + 
				"1.	LOAD_BATCH_ID:Number\r\n" + 
				"2.	BATCH_ID:Number\r\n" + 
				"3.	WM_PRM_TARGET: String: Reporte127.xml\r\n" + 
				"4.	WM_PRM_STATUS: String:\r\n" + 
				"Muestra alguno de       estos iconos, correspondiente a su estado (L, E, P)");
		
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Validar la integridad de los resultados vs BD\r\n" + 
				"   SELECT DISTINCT a.BATCH_ID, a.LOAD_BATCH_ID, a.WM_PRM_STATUS, a.WM_TARGET_PRM, substr(C.store_NAME10,6,5)\r\n" + 
				"  FROM WMUSER.POS_ITM_PRM_HEAD A, WMUSER.FEM_POS_MODS_STG B, RMS100.STORE C \r\n" + 
				" WHERE B.LOAD_BATCH_ID = A.LOAD_BATCH_ID\r\n" + 
				"   AND B.LOAD_WEEK = A.LOAD_WEEK\r\n" + 
				"   AND B.PROMOTION  = 1615119\r\n" + 
				"   AND B.STORE = A.LOCATION\r\n" + 
				"   AND C.STORE = B.STORE");
		
		String NoPromotion = data.get("NoPromotion");

		String query2 = "SELECT DISTINCT a.BATCH_ID, a.LOAD_BATCH_ID, a.WM_PRM_STATUS, a.WM_TARGET_PRM, substr(C.store_NAME10,6,5)\r\n" + 
				"FROM WMUSER.POS_ITM_PRM_HEAD A, WMUSER.FEM_POS_MODS_STG B, RMS100.STORE C \r\n" + 
				"WHERE B.LOAD_BATCH_ID = A.LOAD_BATCH_ID\r\n" + 
				"AND B.LOAD_WEEK = A.LOAD_WEEK\r\n" + 
				"AND B.PROMOTION  = '"+NoPromotion+"'\r\n" + 
				"AND B.STORE = A.LOCATION\r\n" + 
				"AND C.STORE = B.STORE";		
        System.out.println(query2);
        
		SQLResult queryResults2 = executeQuery(dbRmsCol, query);
		
		boolean empty2 = queryResults2.isEmpty();
		testCase.addQueryEvidenceCurrentStep(queryResults2);

		//assertFalse(empty2, "No se muestra informacion en la consulta");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_010_Plaza_DetallePorEstatus";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_Plaza_DetallePorEstatus_test";
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
