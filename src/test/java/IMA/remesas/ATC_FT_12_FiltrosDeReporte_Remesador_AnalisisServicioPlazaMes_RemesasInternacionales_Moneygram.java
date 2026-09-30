package IMA.remesas;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_12_FiltrosDeReporte_Remesador_AnalisisServicioPlazaMes_RemesasInternacionales_Moneygram
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_12_FiltrosDeReporte_Remesador_AnalisisServicioPlazaMes_RemesasInternacionales_Moneygram_test(
			HashMap<String, String> data) throws Exception {

		// Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String report = data.get("Report");
		IMATools ima = new IMATools(u, testCase);
		System.out.println(report);

		// paso 1
		addStep("Abrir navegador y loguearse en la pagina de MicroStrategy");
		ima.Login();

		// Paso 2
		addStep("Ir a carpeta Remesas");
		ima.goToRoute("Información Comercial QA",
				"Información Comercial/Telefonía y Servicios/Resultados Servicios/Plazas/Remesas");

		// Paso 3
		addStep("Seleccionar una opcion en cada filtro y Ejecutar Reporte");
		ima.EjecutarInformeReporteSeleccionado(report);

		// Paso 4 Aplicar Filtros - Proveedor Remesas y Moneygram

		u.hardWait(5);
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar informacion mostrada por un filtro en especifico";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_12_FiltrosDeReporte_Remesador_AnalisisServicioPlazaMes_RemesasInternacionales_Moneygram_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
