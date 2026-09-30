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

public class ATC_FT_011_Administrador_Transacciones_TDC_Exportar_a_excel_Datos_Generales_Venta extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Administrador_Transacciones_TDC_Exportar_a_excel_Datos_Generales_Venta_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plaza = data.get("plaza");
		String distrito = data.get("distrito");
		String tienda = data.get("tienda");
		String tipoTransaccion = data.get("tipoTransaccion");
		String tipoTarjeta = data.get("tipoTarjeta");
		String numeroTarjeta = data.get("numeroTarjeta");
		String numeroAutorizacion = data.get("numeroAutorizacion");
		String f_inicio = data.get("FechaInicial");
		String f_fin = data.get("FechaFinal");
		
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(URL);
		Login pa = new Login(u, testCase);		
		TransaccionesTDC_SimotiColombia ttdc = new TransaccionesTDC_SimotiColombia(u, testCase);
		Transacciones_TDC ttdc2 = new Transacciones_TDC(u, testCase);
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
		
		//Paso 5
				addStep("El usuario selecciona valor en los campos que se indique\r\n"
						+ "*Plaza: \r\n"
						+ "*Distrito: \r\n"
						+ "*Tienda: \r\n"
						+ "*Tipo de Transacci�n:� \r\n"
						+ "*Tipo de Tarjeta:  \r\n"
						+ "*Num. Tarjeta: \r\n"
						+ "*Num. Autorizaci�n: \r\n"
						+ "*Fecha Inicio: dd/mm/aaaa hh:mm\r\n"
						+ "*Fecha Fin: dd/mm/aaaa hh:mm\r\n"
						+ "Dar clic en [Consultar]");
				u.scroll();
				ttdc.busquedaDatosGenerales(plaza, distrito, tienda, tipoTransaccion,tipoTarjeta, tipoTransaccion, numeroTarjeta);
				ttdc.seleccionarFechas(f_inicio, f_fin);
				ttdc2.ingresarNumeroAutorizacion(numeroAutorizacion);
				u.hardWait(4);
				ttdc2.clickBTNBuscar();
				u.hardWait(5);
				
				//Paso 6
				addStep("El usuario da clic en la opci�n: [Exportar]");
				ttdc2.clickByXpath("//*[@id=\"formEnvDineroTdc:btnExportar\"]");
				
				//Paso 7
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
						+ "15. Casa Valera");
				u.hardWait(5);
				ttdc2.validarHeadersTabla();
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
		return "ATC_FT_011_Administrador_Transacciones_TDC_Exportar_a_excel_Datos_Generales_Venta";
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