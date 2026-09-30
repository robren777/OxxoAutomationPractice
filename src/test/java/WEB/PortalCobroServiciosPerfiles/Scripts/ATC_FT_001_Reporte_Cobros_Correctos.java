package WEB.PortalCobroServiciosPerfiles.Scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.PortalCobroServiciosPerfiles.om.MenuPrincipal_CobroServicios;
import WEB.PortalCobroServiciosPerfiles.om.ReporteCobrosCorrectos;
import WEB.PortalCobroServiciosPerfiles.om.convertFiles;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Se validar� que el Reporte de Cobros Correctos exporte en Excel todas las columnas que muestra en su versi�n HTML incluyendo las columnas: Numero Autorizaci�n y Folio WM
 * MTC-FT-001 Reporte Cobros correctos
 * Nota. Se cambiaron los pasos para que se generara primero el reporte Excel y despues el reporte HTML
 *       Para poder realizar la validaci�n de las columanas mas facilmente
 *       La columna 'Factura AP' en el html es lo mismo que la columna 'Factura' en el Excel
 *       La columna 'Contabilizado' en el html seria la columna 'GL' en el Excel
 *       La columna 'ESTATUS TRANSACCI�N WM' de la pantalla no se encuentra en el archivo Excel generado, por lo que esta columna no se compara.
 * @author Herbe Cauich
 * @date Diciembre 2022
 */
public class ATC_FT_001_Reporte_Cobros_Correctos extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_Reporte_Cobros_Correctos_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String estatus = data.get("estatus");
		String tipoFecha = data.get("tipoFecha");
		String anio = data.get("anio1");
		String mes	= data.get("mes1");
		String diaSel1 = data.get("dia1");
		String anio2 = data.get("anio2");
		String mes2	= data.get("mes2");
		String diaSel2 = data.get("dia2");
		String formato1 = data.get("formato1");
		String formato2 = data.get("formato2");
		String formatoFecha = "dd-MM-yyyy";
	
		//String TipoFecha = data.get("TipoFecha");
		//String Finicio = data.get("Finicio");
		//String Ffin = data.get("Ffin");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_CobroServicios mp = new MenuPrincipal_CobroServicios(u, testCase);
		ReporteCobrosCorrectos rcc = new ReporteCobrosCorrectos(u, testCase);
		convertFiles cf = new convertFiles(u, testCase);

		 /**
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Entrar a la siguiente liga: http://10.184.32.17:8000/AccessControl/pages/ac_apps_home.jsf "); // viene //
																											// excel
		u.get(url); 
		u.hardWait(3); // tiempo en segundos
		
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal(); // es para lo de la pagina no segura
		pa.cicloLogin(user, password);
		
		 /**
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Seleccionamos \"Portal Cobro Servicios Perfiles.");
		pa.clickPortalCobroServiciosPerfiles();
		
	
		 /**
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Clic al men� Cobro de servicios -> Reporte cobros correctos.");
		mp.clickPortalCobroServicios();
		u.hardWait(2);
		mp.clickReporteCobrosCorrectos();
		//System.out.println(estatus);
		//System.out.println(formato1);
		//System.out.println(formato2);
		
		
		 /**
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Llenamos los siguientes Filtros. Para el formato HTML"
				+ "Estatus:Seleccionamos un estatus "
				+ "Tipo de fecha: Seleccionamos un tipo de fecha" 
				+ "Fecha inicio: Seleccionamos una fecha inicio"
				+ "Fecha final: Seleccionamos una fecha final");
		rcc.SelecFormat(formato1);
		rcc.SelecFiltro(estatus, tipoFecha);
		rcc.SelecDate(mes, anio, mes2, anio2, diaSel1, diaSel2);
		 /**
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Clic en el bot�n Generar Reporte");
		rcc.clickGenerarReporte();
		u.waitForLoadPage();
		//rcc.validarEncabezado();
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		//Como se genera primero el reporte de Excel, ya no es necesario realizar este paso
		//addStep("Clic en la opci�n Regresar");
		//rcc.clickBtnRegresar();

		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/

		addStep("Llenamos los siguientes Filtros para el formato EXCEL"
				+ "Estatus: Mismo estatus paso 4"
				+ "Tipo de fecha: Seleccionamos mismo tipo de fecha que el paso 4" 
				+ "Fecha inicio: Seleccionamos misma  fecha inicio que el paso 4" 
				+ "Fecha final: Seleccionamos misma fecha final que el paso 4 ");
		rcc.SelecFormat(formato2);
		//No es necesario volver a seleccionarlos porque ya estan seleccionados
		//rcc.SelecFiltro(estatus, tipoFecha);
		//rcc.SelecDate(mes, anio, mes2, anio2, diaSel1, diaSel2);
		 /**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Clic en el bot�n Generar Reporte");
		rcc.clickGenerarReporte();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		//addStep("Clic en el bot�n guardar");
		//rcc.clickGenerarReporte();
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Abrimos el archivo excel descargado y comparamos con el reporte generado en pantalla (HTML)");
		u.hardWait(30);
		cf.csvToXLSX(formatoFecha);
		boolean validacionColumnas = rcc.validarDatoss();
		assertTrue(validacionColumnas, "No todas las columas coninciden");

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
		return "ATC_FT_001_Reporte_Cobros_Correctos_test";
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
