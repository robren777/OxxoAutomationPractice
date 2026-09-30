package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.Login;
import WEB.simoti_Colombia.om.TransaccionesTDC_SimotiColombia;
import WEB.simoti_Colombia.om.transaccionesElectronicas.transaccionesTDC.Transacciones_TDC;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_009_Administrador_Transacciones_TDC_Exportar_a_excel_Folio_Devolucion extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_Administrador_Transacciones_TDC_Exportar_a_excel_Folio_Devolucion_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String folio = data.get("Folio");
		String transaccion = data.get("Transaccion");
		
		
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(URL);
		Login pa = new Login(u, testCase);	
		Transacciones_TDC ttdc = new Transacciones_TDC(u, testCase);
		TransaccionesTDC_SimotiColombia ttdc2 = new TransaccionesTDC_SimotiColombia(u, testCase);
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.logginPlatform(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.click_on_simoti();
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Transacciones TDC");
		pa.transaccionesTDC();
		
		// Paso 5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\r\n"
				+ "-Opci�n: Folio\r\n"
				+ "*Tipo de Transacci�n: <D Devoluci�n>\r\n"
				+ "*Folio WebMethods: <Capturar Valor>\r\n"
				+ "Dar clic en [Consultar]");
		
		pa.busquedaFolio();
		u.hardWait(5);
		//ttdc.selectTipoTransaccion();
		pa.capturaDatosFolio(folio,transaccion);
		
		// Paso 6
		addStep("El usuario da clic en la opci�n: [Exportar]");
		ttdc.clickByXpath("//*[@id=\"formEnvDineroTdc:btnExportar\"]");
		
		// Paso 7
		addStep("El usuario valida que el excel muestre las columnas y la informaci�n de manera correcta.\r\n"
				+ "-En el encabezado se muestra:\r\n"
				+ "SIMOTI\r\n"
				+ "Transacciones\r\n"
				+ "TDC\r\n"
				+ "- Secci�n de filtros:\r\n"
				+ "Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realiz� la consulta.\r\n"
				+ "-Se deben mostrar las columnas:\r\n"
				+ "1. No.\r\n"
				+ "2. Folio\r\n"
				+ "3. Fecha\r\n"
				+ "4. Plaza\r\n"
				+ "5. Tienda\r\n"
				+ "6. Caja\r\n"
				+ "7. Afiliaci�n\r\n"
				+ "8. Tarjeta\r\n"
				+ "9. Emisor\r\n"
				+ "10. Monto\r\n"
				+ "11. Autorizaci�n\r\n"
				+ "12. Reversado\r\n"
				+ "13. est�tus\r\n"
				+ "14. Error\r\n"
				+ "15. Casa Valera ");
		u.hardWait(5);
		ttdc.validarHeadersTabla();
		u.hardWait(5);
		u.close();
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_Administrador_Transacciones_TDC_Exportar_a_excel_Folio_Devolucion_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}