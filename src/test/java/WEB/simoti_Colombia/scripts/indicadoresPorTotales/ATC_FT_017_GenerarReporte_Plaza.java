package WEB.simoti_Colombia.scripts.indicadoresPorTotales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.IndicadoresPorTotales_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_017_GenerarReporte_Plaza extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_GenerarReporte_Plaza_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		IndicadoresPorTotales_SimotiColombia it = new IndicadoresPorTotales_SimotiColombia (u, testCase);
		Boolean validacion = false;
		
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
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el boton Iniciar Sesion.");
		
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opcion de SIMOTI.");
		
		pa.clickSimotiColombia();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el menu: INTEGRIDAD DE DATOS y selecciona el reporte: Indicadores"
				+ " por Totales.");
		
		mp.clickIntegridadDatos();
		u.hardWait(3);
		mp.clickIndicadoresTotales();
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario selecciona los siguientes valores: " + 
				"*Mes: <mayor a mes actual>" + 
				"*A�o: <a�o actual>. El sistema aplica la validacion:" + 
				"'Estimado usuario no se permite la seleccion de una fecha posterior a la actual'");
		
		if(data.get("Plaza")!=null)
		{
			validacion = it.selectPlaza(data.get("Plaza"));
			assertTrue(validacion, "No se puede seleccionar la plaza");
		}
		
		it.selectMes(data.get("Mes"));
		
		it.selectAnio(data.get("Anio"));
		assertTrue(validacion, "No se puede seleccionar el a�o proporcionado");
		
		it.clickConsultarBtn();
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\r\n"
				+ "1. No. (Tipo de dato: Number, debe ser consecutivo)\r\n"
				+ "2. Color \r\n"
				+ "3. Dias (Tipo de dato: Number)\r\n"
				+ "**Validar que se muestren los dias del mes y por cada dia un color de acuerdo a los resultados de la consulta y su equivalente:\r\n"
				+ "Green\r\n"
				+ "Yellow\r\n"
				+ "Black\r\n"
				+ "White\r\n"
				+ "Red\r\n"
				+ "no_color\r\n");
		
		it.validarEncabazadosMes();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query.\r\n"
				+ "select\r\n"
				+ "color,\r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'01',no_recs,0) ) D01, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'02',no_recs,0) ) D02, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'03',no_recs,0) ) D03, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'04',no_recs,0) ) D04, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'05',no_recs,0) ) D05, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'06',no_recs,0) ) D06, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'07',no_recs,0) ) D07, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'08',no_recs,0) ) D08, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'09',no_recs,0) ) D09, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'10',no_recs,0) ) D10, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'11',no_recs,0) ) D11, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'12',no_recs,0) ) D12, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'13',no_recs,0) ) D13, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'14',no_recs,0) ) D14, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'15',no_recs,0) ) D15, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'16',no_recs,0) ) D16, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'17',no_recs,0) ) D17, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'18',no_recs,0) ) D18, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'19',no_recs,0) ) D19, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'20',no_recs,0) ) D20, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'21',no_recs,0) ) D21, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'22',no_recs,0) ) D22, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'23',no_recs,0) ) D23, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'24',no_recs,0) ) D24, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'25',no_recs,0) ) D25, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'26',no_recs,0) ) D26, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'27',no_recs,0) ) D27, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'28',no_recs,0) ) D28, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'29',no_recs,0) ) D29, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'30',no_recs,0) ) D30, \r\n"
				+ "sum (decode(TO_CHAR(fecha,'DD'),'31',no_recs,0) ) D31\r\n"
				+ "from( \r\n"
				+ "select \r\n"
				+ "fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_TIC,NET_SAL,TOT_SYB,\r\n"
				+ "TOT_HEF,DIF_TIC_SAL,DIF_VTA_HEF,AC_SAL,AC_TIC_SAL,AC_VTA_HEF)) color, count(*) no_recs\r\n"
				+ "from XXFC_TOTALES_ACC_V a\r\n"
				+ "where \r\n"
				+ "PLAZA='10MON'\r\n"
				+ "and trunc(a.FECHA)>=TO_DATE('01'||lpad(?,2,'0')||?,'DDMMYYYY') \r\n"
				+ "and trunc(fecha)<=last_day(TO_DATE('01'||lpad(?,2,'0')||?,'DDMMYYYY'))\r\n"
				+ "group by fecha, PR50_AUDIT.getColorPondTot(PR50_AUDIT.ponderarTotales(NET_TIC,NET_SAL,TOT_SYB,TOT_HEF,\r\n"
				+ "DIF_TIC_SAL,DIF_VTA_HEF,AC_SAL,AC_TIC_SAL,AC_VTA_HEF))\r\n"
				+ ")\r\n"
				+ "where color <>'no_color'\r\n"
				+ "group by color \r\n"
				+ "");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario valida que se muestre la informacion de acuerdo a lo siguiente:\r\n"
				+ "1. Si el campo tiene un solo nombre, la primera letra debera estar en mayusculas el resto en minusculas, respetando acentuacion.\r\n"
				+ "2. Si el campo tiene dos o mas nombres, la primera letra de cada palabra debera estar en mayusculas y el resto en minusculas se parados por un espacio , respetando acentuacion. Por ejemplo: Fecha de Recepcion, Archivo Entrada, Fecha Orden.\r\n"
				+ "3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en mayusculas, separados por un _, sin acentuarse. Por ejemplo: SOURCE_ID, IFE, Folio WM.");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. ATC_FT_017_GenerarReporte_Plaza_CEDIS";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_GenerarReporte_Plaza_CEDIS_test";
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