package WorkFlows.SipreOXXO;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreOXXO.pomSipreOXXO;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_022_Reporte_Admin_Cambios_Masivos_Pega extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_SIPRE_022_Reporte_Admin_Cambios_Masivos_Pega_Test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Workflows Library: Sipre Oxxo");

		/**
		 * Variables
		 * 
		 */
		String region = data.get("Region");
		String plazaPrecio = data.get("PlazaPrecio");
		String rutaExcel = data.get("ExcelPath");

		/**
		 * 
		 * Utilerias
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);

		// ExcelUtil Excel = new ExcelUtil(rutaExcel);
		// String windowUltimus = u.getDriver().getWindowHandle();
		pomSipreOXXO util = new pomSipreOXXO(u, testCase);
		// pomUltimus ultimus = new pomUltimus (u);

		// *** Es importante cambiar la ruta del archivo descargado que se leerá durante
		// la ejecución en el Campo "ExcelPath" del Data DataProvider
		// paso 1
		addStep("Ingresar a SIPRE");
		util.loginPega(GlobalVariables.userSipreOXXOAdminPEGA, GlobalVariables.passSipreOXXOUAdminPEGA);
		util.selectMenuSistemaPrecios();
		testCase.addScreenShotCurrentStep("Login");

		// paso 2
		addStep("Seleccionar el Menú Reportes");

		// paso 3
		addStep("Dar Click en la opción Cambios Masivos");
		util.clickMenuReportesPega("Admin Cambios Masivos");
		testCase.addScreenShotCurrentStep("Reporte Admin Cambios Masivos");
		util.GenerarReporteAdminCambiosMasivos(region, plazaPrecio);

		// paso 4
		addStep("Dar click al botón de Generar Reporte");
		testCase.addScreenShotCurrentStep("Generar Reporte");
		util.AbrirExcelExistente(rutaExcel);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Reporte Excel");
		util.leerCeldasExcel(rutaExcel, 13);

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
		return "Tener Acceso Dominio Oxxo" + "Contar con Orgchart de sipre"
				+ "Contar con usuarios configurados al orgchart" + "Contar con JFG de perfil adecuado en Ultimus o Pega"
				+ "Contar con Acceso a Control -M" + "Solicitar el Holdeo de los JOBS a ejecutar"
				+ "Contar con Acceso al esquema de XXWF_SIPRE2" + "CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el Reporte de Cambios Masivos se exporte correctamente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_022_Reporte_Admin_Cambios_Masivos_Pega_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
