package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_215_Indicadores_por_Totales_Exportar_Excel_Guardar_Oficina_Central extends BaseExecution {

	SeleniumUtil u;
	MetodosExtra me;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_215_Indicadores_por_Totales_Exportar_Excel_Guardar_Oficina_Central_Test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		
		SQLUtil dbSimoti = new SQLUtil(GlobalVariables.DB_HOST_OIWMQA_QAVIEW, GlobalVariables.DB_USER_OIWMQA_QAVIEW, GlobalVariables.DB_PASSWORD_OIWMQA_QAVIEW);
		testCase.setTest_Description(cp);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String mes = data.get("mes");
		String anio = data.get("anio");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		MenuPrincipal_SimotiPeru mp = new MenuPrincipal_SimotiPeru(u, testCase);
		Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion;
		String Query = "select" + 
				"color," + 
				"sum (decode(TO_CHAR(fecha,'DD'),'01',no_recs,0) ) D01, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'02',no_recs,0) ) D02, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'03',no_recs,0) ) D03, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'04',no_recs,0) ) D04, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'05',no_recs,0) ) D05, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'06',no_recs,0) ) D06, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'07',no_recs,0) ) D07, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'08',no_recs,0) ) D08, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'09',no_recs,0) ) D09, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'10',no_recs,0) ) D10, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'11',no_recs,0) ) D11, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'12',no_recs,0) ) D12, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'13',no_recs,0) ) D13, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'14',no_recs,0) ) D14, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'15',no_recs,0) ) D15, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'16',no_recs,0) ) D16, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'17',no_recs,0) ) D17, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'18',no_recs,0) ) D18, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'19',no_recs,0) ) D19, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'20',no_recs,0) ) D20, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'21',no_recs,0) ) D21, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'22',no_recs,0) ) D22, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'23',no_recs,0) ) D23, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'24',no_recs,0) ) D24, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'25',no_recs,0) ) D25, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'26',no_recs,0) ) D26, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'27',no_recs,0) ) D27, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'28',no_recs,0) ) D28, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'29',no_recs,0) ) D29, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'30',no_recs,0) ) D30, " + 
				"sum (decode(TO_CHAR(fecha,'DD'),'31',no_recs,0) ) D31" + 
				"from(select " + 
				"fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_TIC,NET_SAL,TOT_SYB," + 
				"TOT_HEF,DIF_TIC_SAL,DIF_VTA_HEF,AC_SAL,AC_TIC_SAL,AC_VTA_HEF)) color, count(*) no_recs" + 
				"from XXFC_TOTALES_ACC_V a" + 
				"where " + 
				"PLAZA=�10MON�" + 
				"and trunc(a.FECHA)>=TO_DATE('01'||lpad(?,2,'0')||?,'DDMMYYYY') " + 
				"and trunc(fecha)<=last_day(TO_DATE('01'||lpad(?,2,'0')||?,'DDMMYYYY'))" + 
				"group by fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_TIC,NET_SAL,TOT_SYB,TOT_HEF," + 
				"DIF_TIC_SAL,DIF_VTA_HEF,AC_SAL,AC_TIC_SAL,AC_VTA_HEF))" + 
				")" + 
				"where color <>'no_color'" + 
				"group by color";

		 /**
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones e Ingresar el usuario, password y dar clic en entrar"); // viene //
																											// excel
		u.get(url); 
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal(); // es para lo de la pagina no segura
		
		 /**
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		 /**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickPortalSIMOTI_PERU();
		 /**
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("El usuario despliega el menú: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores por Totales.");
		it.MAX();
		it.clickIntegridadDatos();
		u.hardWait(3);
		it.clickIndicadoresPorTotales();
		
		 /**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Consultar]");
		
		it.selectMes(mes);
		u.hardWait(3);
		it.selectAnio(anio);
		it.clickBtnConsultar();
		
		 /**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en la opci�n: [Exportar]");
		
		it.clickBtnExportar();
		it.Loading();
		//it.MensajeSinInformacionExportar();

		
		 /**
		 *                                             PASO 7
		 ******************************************************************************************/
		// No aplica para chrome
		//addStep("El usuario da clic en [Guardar]");
		
		

		 /**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:" + 
				"1. No. (Tipo de dato: Number, debe ser consecutivo)" + 
				"2. Color " + 
				"3. D�as (Tipo de dato: Number)" + 
				"**Validar que se muestren los dias del mes y por cada d�a un color de acuerdo a los resultados de la consulta y su equivalente:" + 
				"Green" + 
				"Yellow" + 
				"Black" + 
				"White" + 
				"Red" + 
				"no_color");
		
		 it.validarEncabezados();
		 
		 /**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del" +  Query);
		
		SQLResult ResultConsulta = executeQuery(dbSimoti, Query);
		
		boolean validacionPRM = ResultConsulta.isEmpty();
		
		if (!validacionPRM) {
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_215_Indicadores_por_Totales_Exportar_Excel_Guardar_Oficina_Central";
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
