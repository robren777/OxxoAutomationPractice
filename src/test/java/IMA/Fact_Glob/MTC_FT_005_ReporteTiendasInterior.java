package IMA.Fact_Glob;

import java.util.HashMap;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_005_ReporteTiendasInterior extends BaseExecution {
	public void ATC_FT_005_ReporteTiendasInterior_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u, testCase);

		// paso 1
		addStep("Ingresar a MicroStrategy  http://10.185.176.16/MicroStrategy");
		ima.Login();
		// Paso 2
		addStep("Ingresar en la ruta: TICKETS QA/ INFORMES COMPARTIDOS/FACTURACION GLOBAL");
		ima.Select_FolderRefaccionesConsolidado();

		// Paso 3
		addStep("Validar los elementos elementos de creacion del reporte consolidado de refacciones");
		//ima.generarReporteRefaccionesConsolidado(data.get("FiltroTienda"), data.get("FiltroAno"),
				//data.get("FiltroMes"));

		// Paso 4 no se ven los filtros
		// Paso 5 no especifica base de datos
		// Paso 6 no especifica base de datos
		// Paso 7 no especifica base de datos
		// Paso 8 no especifica base de datos
		// Paso 9 no especifica base de datos
		// Paso 10 no se puede realizar por falta de informacion en los pasos anteriores
		// Paso 11 no se puede realizar por falta de informacion en los pasos anteriores
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
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
