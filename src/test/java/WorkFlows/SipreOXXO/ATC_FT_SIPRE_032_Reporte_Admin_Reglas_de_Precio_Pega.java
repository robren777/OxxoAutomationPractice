package WorkFlows.SipreOXXO;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreOXXO.pomSipreOXXO;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.ExcelUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_032_Reporte_Admin_Reglas_de_Precio_Pega extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_SIPRE_032_Reporte_Admin_Reglas_de_Precio_Pega_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Sipre Oxxo");
	
	/**
	 * Variables
	 * 
	 */
		
		String region = data.get("Region");
		String plazaPrecio = data.get("PlazaPrecio");
		String rutaExcel = data.get("ExcelPath");
		String rutaExcel2 = data.get("ExcelSKUS");
	


	/**
	 * 
	 * Utilerias
	 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		
		ExcelUtil Excel = new ExcelUtil(rutaExcel);
		String windowUltimus = u.getDriver().getWindowHandle();
		pomSipreOXXO util = new pomSipreOXXO(u,testCase);
		pomUltimus ultimus = new pomUltimus (u);
		
		// *** Es importante cambiar la ruta del archivo descargado que se leerá durante la ejecución en el Campo "ExcelPath" del Data DataProvider
	
		// paso 1
		addStep("Ingresar a SIPRE");
		util.loginPega(GlobalVariables.userSipreOXXOAdminPEGA, GlobalVariables.passSipreOXXOUAdminPEGA);
		util.selectMenuSistemaPrecios();
		testCase.addScreenShotCurrentStep("Login");
		
// paso 2
		addStep("Seleccionar el Menú Reportes");
	
// paso 3
		addStep("Dar Click en la opción Reglas de Precio");
		util.clickMenuReportesPega("Admin Reglas de Precio");
		testCase.addScreenShotCurrentStep("Reglas de Precio");
// paso 4
		addStep("Seleccionar los filtros que necesites para la prueba Ninguno es obligatorio");
		util.GenerarReporReglasNegocion();
// paso 5
		addStep("Dar click al botón de Generar Reporte");
		testCase.addScreenShotCurrentStep("Generar Reporte Estructura");
		util.AbrirExcelExistente(rutaExcel);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Reporte Excel Estructura");
		util.leerCeldasExcel(rutaExcel,10);
		
		testCase.addScreenShotCurrentStep("Generar Reporte SKUS");
		util.AbrirExcelExistente(rutaExcel2);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Generar Reporte SKUS");
		util.leerCeldasExcel(rutaExcel2,18);

	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener Acceso Dominio Oxxo" + 
				"Contar con Orgchart de sipre" + 
				"Contar con usuarios configurados al orgchart" + 
				"Contar con JFG de perfil adecuado en Ultimus o Pega" + 
				"Contar con Acceso a Control -M" + 
				"Solicitar el Holdeo de los JOBS a ejecutar" + 
				"Contar con Acceso al esquema de XXWF_SIPRE2" + 
				"CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el Reporte de Reglas de Negocio se exporte correctamente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_032_Reporte_Admin_Reglas_de_Precio_Pega_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
