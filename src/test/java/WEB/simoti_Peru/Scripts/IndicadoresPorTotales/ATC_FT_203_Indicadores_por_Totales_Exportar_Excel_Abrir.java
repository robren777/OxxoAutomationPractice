package WEB.simoti_Peru.Scripts.IndicadoresPorTotales;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.simoti_Peru.om.MenuPrincipal_SimotiPeru;
import WEB.simoti_Peru.om.Simoti_Peru;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se permita exportar a formato excel los resultados de la consulta del reporte.
*Validar nomenclatura
*Validar el contenido 
* MTC_FT_203.- Indicadores por Totales_Exportar a excel_abrir
* @author Herbe Cauich
* @date Noviembre 2022
*/
public class ATC_FT_203_Indicadores_por_Totales_Exportar_Excel_Abrir extends BaseExecution {

	SeleniumUtil u;
	MetodosExtra me;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_203_Indicadores_por_Totales_Exportar_Excel_Abrir_test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String mes = data.get("Mes");
		String anio = data.get("Anio");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		MenuPrincipal_SimotiPeru mp = new MenuPrincipal_SimotiPeru(u, testCase);
		Simoti_Peru it = new Simoti_Peru (u, testCase);
		Boolean validacion = false;
		

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
		
		it.clickIntegridadDatos();
		u.hardWait(3);
		it.clickIndicadoresPorTotales();
		
		 /**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario realiza selecci�n de valores en los filtros:" + 
				"Plaza: <se muestra la Mega Plaza del usuario>" + 
				"Mes: <seleccionar valor>" + 
				"A�o: <Seleccionar valor>" + 
				"Dar clic en [Consultar]");
		
		it.selectMes(mes);
		u.hardWait(3);
		it.selectAnio(anio);
		it.clickBtnConsultar();
		
		 /**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Exportar]");
		
		it.clickBtnExportar();
		//it.MensajeSinInformacionExportar();

		
		 /**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("El usuario da clic en [Abrir]");
		u.hardWait(3);
		it.selectMes(data.get("Mes"));
		u.hardWait(3);
		it.selectAnio(data.get("Anio"));
		

		 /**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El usuario abre el documento excel que guardo y se deben mostrar de la siguiente manera>" + 
				"-En el encabezado se muestra:" + 
				"SIMOTI" + 
				"Integridad de Datos" + 
				"Reporte de Indicadores por Totales" + 
				"- Secci�n de filtros:" + 
				"Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realiz� la consulta." + 
				"-Se deben mostrar las columnas:" + 
				"1. No. " + 
				"2. Color " + 
				"3. D�as");
		
		it.clickBtnConsultar();
		assertTrue(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_",""),"Los datos exportados no coinciden.");
		
		 /**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("El usuario valida que las columnas que se muestran el excel sean acorde a la siguiente regla:" + 
				"Regla nombrado de columnas en Tabla de resultados y archivo Excel:" + 
				"1. Si el campo tiene un solo nombre, la primera letra deber� est�r en may�sculas el resto en min�sculas, respetando acentuaci�n." + 
				"2. Si el campo tiene dos o m�s nombres, la primera letra de cada palabra deber� est�r en may�sculas y el resto en min�sculas se parados por un espacio � � , respetando acentuaci�n. Por ejemplo: �Fecha de Recepci�n�, �Archivo Entrada�, �Fecha Orden�." + 
				"3. Si el campo tiene por nombre un identificador, siglas o un valor abreviado todas van en may�sculas, separados por un �_�, sin acentuarse. Por ejemplo: �SOURCE_ID�, �IFE�, �Folio WM�.");
		

		
		 /**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("El usuario va a la pantalla de consulta y nuevamente selecciona otros valores en los filtros, de manera que NO deber� dar clic en Consultar. Abrir nuevamente el excel.");
		u.hardWait(3);
		it.selectMes(data.get("Mes 2"));
		u.hardWait(3);
		it.selectAnio(data.get("Anio 2"));
		

		it.clickBtnExportar();
		assertFalse(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_"," (1)"),"Los datos exportados coinciden.");
		


		 /**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("El usuario va a la pantalla de consulta y selecciona otros valores en los filtros y da clic en Consultar");
		u.hardWait(3);
		
		it.selectMes(data.get("Mes 2"));
		u.hardWait(3);
		it.selectAnio(data.get("Anio 2"));
		
		it.clickBtnConsultar();
		 /**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("El usuario da clic en Exportar.");
		it.clickBtnExportar();
		 /**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("El usuario da clic en Abrir.");
		u.hardWait(3);
		it.selectMes(data.get("Mes 2"));
		u.hardWait(3);
		it.selectAnio(data.get("Anio 2"));
		it.clickBtnConsultar();
		u.hardWait(10);

		assertTrue(it.validarDatosTablaExcel("Reporte_Indicadores_Totales_"," (2)"),"Los datos exportados no coinciden.");
	
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
		return "ATC_FT_203_Indicadores_por_Totales_Exportar_Excel_Abrir_test";
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
