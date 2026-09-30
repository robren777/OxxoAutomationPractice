package WEB.simoti_Chile.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Chile.om.IndicadoresPorTotales_SimotiChile;
import WEB.simoti_Chile.om.MenuPrincipal_SimotiChile;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/**Desc:Validar que se genere el reporte y que se muestren los datos correctamente de acuerdo a 
* los filtros seleccionados. PLAZA norte centro.
* MTC_FT_004.Generar Reporte Plaza norte
* @author Marichuy Due�as/Yair L
* @date Octubre 2022
*/
public class ATC_FT_004_GenerarReporte_Plaza_norte_centro extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_GenerarReporte_Plaza_norte_centro_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new MEdgeTest(), true);	
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiChile mp = new MenuPrincipal_SimotiChile(u, testCase);
		IndicadoresPorTotales_SimotiChile it = new IndicadoresPorTotales_SimotiChile (u, testCase);
		Boolean validacion;
		SQLUtil dbSimotiChile = new SQLUtil(GlobalVariables.DB_HOST_OCHWMQA_POSUSER, GlobalVariables.DB_USER_OCHWMQA_POSUSER, GlobalVariables.DB_PASSWORD_OCHWMQA_POSUSER);
		String Query = "select\n"+
				"color,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'01',no_recs,0) ) D01,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'02',no_recs,0) ) D02,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'03',no_recs,0) ) D03,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'04',no_recs,0) ) D04,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'05',no_recs,0) ) D05,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'06',no_recs,0) ) D06,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'07',no_recs,0) ) D07,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'08',no_recs,0) ) D08,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'09',no_recs,0) ) D09,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'10',no_recs,0) ) D10,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'11',no_recs,0) ) D11,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'12',no_recs,0) ) D12,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'13',no_recs,0) ) D13,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'14',no_recs,0) ) D14,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'15',no_recs,0) ) D15,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'16',no_recs,0) ) D16,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'17',no_recs,0) ) D17,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'18',no_recs,0) ) D18,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'19',no_recs,0) ) D19,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'20',no_recs,0) ) D20,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'21',no_recs,0) ) D21,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'22',no_recs,0) ) D22,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'23',no_recs,0) ) D23,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'24',no_recs,0) ) D24,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'25',no_recs,0) ) D25,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'26',no_recs,0) ) D26,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'27',no_recs,0) ) D27,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'28',no_recs,0) ) D28,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'29',no_recs,0) ) D29,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'30',no_recs,0) ) D30,\n"+
				"sum (decode(TO_CHAR(fecha,'DD'),'31',no_recs,0) ) D31\n"+
				"from(\n"+
				"select\n"+
				"fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_SAL,--,TOT_SYB,NET_TIC\n"+
				"TOT_HEF,DIF_VTA_HEF,AC_SAL,AC_VTA_HEF)) color, count(*) no_recs--DIF_TIC_SAL,AC_TIC_SAL,\n"+
				"from XXFC_TOTALES_ACC_V a\n"+
				"where\n"+
				"PLAZA='10CLP'\n"+
				"and trunc(a.FECHA)>=TO_DATE('01'||lpad('01',2,'0')||'2023','DDMMYYYY')\n"+
				"and trunc(fecha)<=last_day(TO_DATE('31'||lpad('01',2,'0')||'2023','DDMMYYYY'))\n"+
				"group by fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_SAL,--,TOT_SYB,NET_TIC\n"+
				"TOT_HEF,DIF_VTA_HEF,AC_SAL,AC_VTA_HEF))--DIF_TIC_SAL,AC_TIC_SAL,\n"+
				")\n"+
				"where color <>'no_color'\n"+
				"group by color ";
		
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
		
		pa.clickSimotiChile();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros: " + 
				"Plaza: <se muestra la Plaza del usuario> " + 
				"Mes: <seleccionar valor> " + 
				"A�o: <Seleccionar valor> " + 
				"Dar clic en [Consultar]");

		validacion = it.selectMes(data.get("Mes"));
		assertTrue(validacion, "No se puede seleccionar el mes");
		
		validacion = it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado: " + 
				"1. No. (Tipo de dato: Number, debe ser consecutivo) " + 
				"2. Color " + 
				"3. D�as (Tipo de dato: Number) ");
			
			it.validarEncabazadosIPT();
		

		
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
			addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query.\n" +
			"select\n" +   
			"color,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'01',no_recs,0) ) D01,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'02',no_recs,0) ) D02,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'03',no_recs,0) ) D03,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'04',no_recs,0) ) D04,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'05',no_recs,0) ) D05,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'06',no_recs,0) ) D06,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'07',no_recs,0) ) D07,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'08',no_recs,0) ) D08,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'09',no_recs,0) ) D09,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'10',no_recs,0) ) D10,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'11',no_recs,0) ) D11,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'12',no_recs,0) ) D12,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'13',no_recs,0) ) D13,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'14',no_recs,0) ) D14,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'15',no_recs,0) ) D15,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'16',no_recs,0) ) D16,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'17',no_recs,0) ) D17,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'18',no_recs,0) ) D18,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'19',no_recs,0) ) D19,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'20',no_recs,0) ) D20,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'21',no_recs,0) ) D21,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'22',no_recs,0) ) D22,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'23',no_recs,0) ) D23,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'24',no_recs,0) ) D24,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'25',no_recs,0) ) D25,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'26',no_recs,0) ) D26,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'27',no_recs,0) ) D27,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'28',no_recs,0) ) D28,\n" +
			"sum (decode(TO_CHAR(fecha,'DD'),'29',no_recs,0) ) D29,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'30',no_recs,0) ) D30,\n" + 
			"sum (decode(TO_CHAR(fecha,'DD'),'31',no_recs,0) ) D31\n" +   
			"from(\n" +
			"select\n" + 
			"fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_SAL,--,TOT_SYB,NET_TIC\n" + 
			"TOT_HEF,DIF_VTA_HEF,AC_SAL,AC_VTA_HEF)) color, count(*) no_recs--DIF_TIC_SAL,AC_TIC_SAL,\n" + 
			"from XXFC_TOTALES_ACC_V a\n" + 
			"where\n" + 
			"PLAZA='10CLP'\n" + 
			"and trunc(a.FECHA)>=TO_DATE('01'||lpad('12',2,'0')||'2022','DDMMYYYY')\n" + 
			"and trunc(fecha)<=last_day(TO_DATE('31'||lpad('12',2,'0')||'2022','DDMMYYYY'))\n" +
			"group by fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_SAL,--,TOT_SYB,NET_TIC\n" + 
			"TOT_HEF,DIF_VTA_HEF,AC_SAL,AC_VTA_HEF))--DIF_TIC_SAL,AC_TIC_SAL,\n" + 
			")\n" +      "where color <>'no_color'\n" +      "group by color ;");
			
			SQLResult ResultConsulta = executeQuery(dbSimotiChile, Query);
			System.out.print("Consulta" + Query);

			boolean validacionPlaza = ResultConsulta.isEmpty();

			if (!validacionPlaza) {
				testCase.addQueryEvidenceCurrentStep(ResultConsulta);
			} else {
				testCase.addTextEvidenceCurrentStep("No existen registros");

			}
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_004_GenerarReporte_Plaza_norte_centro";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_GenerarReporte_Plaza_norte_centro_test";
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
