package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

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
/**Desc: *Validar que se desplieguen todos los valores y nomenclatura adecuados 
*Validar que se genere el reporte y que se muestren los datos correctamente de acuerdo a los filtros seleccionados. PLAZA
* MTC_FT_198.- Indicadores por Totales_Generar Reporte_Plaza_norte
* @author Herbe Cauich
* @date Noviembre 2022
*/
public class ATC_FT_198_Indicadores_por_Totales_Generar_Reporte_Plaza_Norte extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_198_Indicadores_Por_Totales_Generar_Reporte_Plaza_Norte_test(HashMap<String, String> data)throws Exception {
		SQLUtil dbOIWMQA = new SQLUtil(GlobalVariables.DB_HOST_OIWMQA_QAVIEW, GlobalVariables.DB_USER_OIWMQA_QAVIEW,GlobalVariables.DB_PASSWORD_OIWMQA_QAVIEW);
		
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		String user = data.get("User");
		String mes = data.get("Mes");
		String anio = data.get("Anio");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiPeru mp = new MenuPrincipal_SimotiPeru(u, testCase);
		Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion;
		String queryReporte = "select\r\n"
				+ "color,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'01',no_recs,0) ) D01,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'02',no_recs,0) ) D02,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'03',no_recs,0) ) D03,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'04',no_recs,0) ) D04,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'05',no_recs,0) ) D05,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'06',no_recs,0) ) D06,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'07',no_recs,0) ) D07,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'08',no_recs,0) ) D08,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'09',no_recs,0) ) D09,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'10',no_recs,0) ) D10,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'11',no_recs,0) ) D11,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'12',no_recs,0) ) D12,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'13',no_recs,0) ) D13,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'14',no_recs,0) ) D14,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'15',no_recs,0) ) D15,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'16',no_recs,0) ) D16,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'17',no_recs,0) ) D17,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'18',no_recs,0) ) D18,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'19',no_recs,0) ) D19,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'20',no_recs,0) ) D20,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'21',no_recs,0) ) D21,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'22',no_recs,0) ) D22,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'23',no_recs,0) ) D23,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'24',no_recs,0) ) D24,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'25',no_recs,0) ) D25,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'26',no_recs,0) ) D26,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'27',no_recs,0) ) D27,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'28',no_recs,0) ) D28,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'29',no_recs,0) ) D29,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'30',no_recs,0) ) D30,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'31',no_recs,0) ) D31\r\n"
				+ "from(\r\n"
				+ "select\r\n"
				+ "fecha, POSUSER.PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_TIC,NET_SAL,TOT_SYB,\r\n"
				+ "TOT_HEF,DIF_TIC_SAL,DIF_VTA_HEF,AC_SAL,AC_TIC_SAL,AC_VTA_HEF)) color, count(*) no_recs\r\n"
				+ "from POSUSER.XXFC_TOTALES_ACC_V a\r\n"
				+ "where\r\n"
				+ "PLAZA IN ('"+data.get("Plaza").substring(0,data.get("Plaza").indexOf(" "))+"')\r\n"
				+ "and trunc(a.FECHA)>=TO_DATE('01'||lpad('"+it.numeroDeMes(data.get("Mes"))+"',2,'0')||'"+data.get("Anio")+"','DDMMYYYY')\r\n"
				+ "and trunc(fecha)<=last_day(TO_DATE('"+it.diasenMes(data.get("Mes"))+"'||lpad('"+it.numeroDeMes(data.get("Mes"))+"',2,'0')||'2020','DDMMYYYY'))\r\n"
				+ "group by fecha, POSUSER.PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_TIC,NET_SAL,TOT_SYB,\r\n"
				+ "TOT_HEF,DIF_TIC_SAL,DIF_VTA_HEF,AC_SAL,AC_TIC_SAL,AC_VTA_HEF))\r\n"
				+ ")\r\n"
				+ "where color <>'no_color'\r\n"
				+ "group by color";
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
		
		addStep("El usuario realiza selecci�n de valores en los filtros:" + 
				"Plaza: <se muestra la Plaza del usuario>" + 
				"Mes: <seleccionar valor>" + 
				"A�o: <Seleccionar valor>" + 
				"Dar clic en [Consultar]");
		it.selectMes(mes);
		it.selectAnio(anio);
		u.hardWait(2);
		it.clickBtnConsultar();

		 /**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:" + 
				"1. No. (Tipo de dato: Number, debe ser consecutivo)" + 
				"2. Color" + 
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
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query " + queryReporte);
		
		SQLResult queryPopResult = executeQuery(dbOIWMQA, queryReporte);	
//		te.imprimeConsulta(queryPopResult);
		if(!queryPopResult.isEmpty())
			assertTrue(it.validarDatosQuery(queryPopResult),"Los datos no coinciden con la BD.");
		else
			assertTrue(false,"La consulta con la BD no funciono.");

		testCase.addQueryEvidenceCurrentStep(queryPopResult);

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
		return "ATC_FT_198_Indicadores_Por_Totales_Generar_Reporte_Plaza_Norte_test";
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
