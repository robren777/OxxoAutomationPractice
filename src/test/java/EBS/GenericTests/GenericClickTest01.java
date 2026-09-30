package EBS.GenericTests;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import modelo.BaseExecution;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import utils.ExcelUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class GenericClickTest01 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarLaExistenciaDeLosCamposTipoPersonaYTipoTasa(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String tipo = data.get("tipo");
		String nivel = data.get("nivel");
		String tipoTasa = data.get("tipoTasa");
		String tipoPersona = data.get("tipoPersona");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		GenericPOM genericPom = new GenericPOM(u, testCase);
		GenericTools genericTools = new GenericTools(u, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		

		String excel = "C:\\Users\\edwin.ramirez\\Downloads\\prueba.xlsx";
		ExcelUtil hoja = new ExcelUtil(excel);
		hoja.open();
		String contenidoCelda = hoja.getCell(0, 7, 1);
		System.out.println("Contenido: " + contenidoCelda);

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarLaExistenciaDeLosCamposTipoPersonaYTipoTasa";
	}

	@Override
	public String setTestDescription() {
		return "Validar la existencia de los campos 'Tipo Persona' y 'Tipo tasa'  en los filtros de búsqueda de la pantalla 'Plantilla de Proveedores'.";
	}

	@Override
	public String setTestDesigner() {
		return "JesusH@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a la responsabilidad 'MXOXXO-APXXXXX-Super Usuario'.";
	}

	@Override
	public void beforeTest() {

	}
}
