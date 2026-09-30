package WEB.simoti_Colombia.scripts.ReporteDeSeguimientoDePendientes;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.ReporteDeSeguimientoDePendientes_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se permita exportar a formato excel los resultados de la consulta del reporte.
 * Validar nomenclatura
 * Validar el contenido 
 * Que se permita abrir 
 * MTC-FT-009 Plaza_Reporte de Seguimiento de Pendientes_Exportar Reporte_Abrir
 * Nota. Falta terminar de construir los ultimos 7 pasos, debido a que no hay informaci�n en el sistema
 * @author 
 * @date Febrero 2023
 */
public class ATC_FT_009_Plaza_ExportarReporte_Abrir extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_Plaza_ExportarReporte_Abrir_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");	
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

		validacion = rsp.filtrosReporteSeguimiento(data.get("Plaza1"), data.get("FechaInicio1"), data.get("FechaFin1"));
		assertTrue(validacion, "No se insertaron correctamente los datos proporcionados");
		
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
		
		addStep("El usuario da clic en [Abrir]. El sistema permite guardar el archivo con el formato y nomenclatura correctamente.\r\n" + 
				"El nombre del archivo generado est�r� conformado por el mismo nombre del reporte separando cada palabra por el car�cter �_� " +
				"y la fecha de generaci�n con el formato ddmmaaaa, por ejemplo:\r\n" + 
				"Reporte_Seguimientos_Promociones_10022018.xlsx.");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario abre el documento excel que guardo y se deben mostrar de la siguiente manera>\r\n" + 
				"-En el encabezado se muestra:\r\n" + 
				"SIMOTI\r\n" + 
				"Transmisi�n de Datos\r\n" + 
				"Reporte de Seguimiento a Promociones\r\n" + 
				"- Secci�n de filtros:\r\n" + 
				"Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realiz� la consulta.\r\n" + 
				"-Se deben mostrar las columnas:\r\n" + 
				"1. No. \r\n" + 
				"2. Promoci�n,\r\n" + 
				"3. Descripci�n\r\n" + 
				"4. Fecha Inicio\r\n" + 
				"5. Fecha Fin");
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario valida que las columnas que se muestran el excel sean acorde a la siguiente regla:\r\n" + 
				"Regla nombrado de columnas en Tabla de resultados y archivo Excel:\r\n" + 
				"1. Si el campo tiene un solo nombre, la primera letra deber� est�r en may�sculas el resto en min�sculas, respetando acentuaci�n.\r\n" + 
				"2. Si el campo tiene dos o m�s nombres, la primera letra de cada palabra deber� est�r en may�sculas y el resto en min�sculas se parados por un espacio � � , respetando acentuaci�n. Por ejemplo: �Fecha de Recepci�n�, �Archivo Entrada�, �Fecha Orden�.\r\n" + 
				"3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en may�sculas, separados por un '_', sin acentuarse. Por ejemplo: 'SOURCE_ID', 'IFE', 'Folio WM'");
		
		//falta revisar si es posible automatizar este paso
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("El usuario va a la pantalla de consulta y nuevamente selecciona otros valores en los filtros, "
				+ "de manera que NO deber� dar clic en Consultar. Abrir nuevamente el excel. El sistema siempre "
				+ "generar� el archivo con la informaci�n que se vea en la secci�n de resultados, es decir al "
				+ "no haber dado clic en Consultar, se seguir�n mostrando en el excel los datos de la tabla de "
				+ "resultados.");
		
		validacion = rsp.filtrosReporteSeguimiento(data.get("Plaza2"), data.get("FechaInicio2"), data.get("FechaFin2"));
		assertTrue(validacion, "No se insertaron correctamente los datos proporcionados");
		
		rsp.clickExportar();
		
		//Se puede validar, validando que los datos de las fechas sean los mismos que los seleccionados
		//En caso de no se iguales, quiere decir que no se genero por no dar clic en Consultar

		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("El usuario va a la pantalla de consulta y selecciona otros valores en los filtros y da clic en Consultar."
				+ " El sistema muestra los resultados de la consulta.");
		
		validacion = rsp.filtrosReporteSeguimiento(data.get("Plaza3"), data.get("FechaInicio3"), data.get("FechaFin3"));
		assertTrue(validacion, "No se insertaron correctamente los datos proporcionados");
		
		rsp.clickConsultar();
		
		validacion = rsp.validarExistanResultados();
		assertTrue(validacion, "No se muestran resultados en el grid");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("El usuario da clic en Exportar. El sistema permite abrir el archivo con el formato y nomenclatura correctamente.");
		
		rsp.clickExportar();
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("El usuario da clic en Abrir. Se muestran los datos de acuerdo a la nueva consulta.");
		
		//Se puede validar, validando que los datos de las fechas sean los mismos que los seleccionados
		
		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Validar que se permita exportar a formato excel los resultados de la consulta del reporte.\r\n" + 
				" Validar nomenclatura\r\n" + 
				" Validar el contenido \r\n" + 
				" Que se permita abrir ";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_Plaza_ExportarReporte_Abrir_test";
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
