package IMA.CHO52024656;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Comprobar_que_el_contenido_de_componente_este_instalado_en_Microstrategy extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Comprobar_que_el_contenido_de_componente_este_instalado_en_Microstrategy_test(
			HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u, testCase);

		// paso 1
		addStep("Entrar a Microstrategy WEB, con usuario y password validos. ");
		ima.Login();

		// Paso 2
		addStep("Seguir la siguiente Ruta: Mantenimiento QA > Informes Compartidos > Control de Gastos > Refacciones� > Reporte Consolidado de refacciones por Proveedor.");
		ima.SelectFolder_ReporteConsolidadoRefaccionesPorProveedor();

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Comprobar_que_el_contenido_de_componente_este_instalado_en_Microstrategy_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Comprobar_que_el_contenido_de_componente_este_instalado_en_Microstrategy_test";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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

}
