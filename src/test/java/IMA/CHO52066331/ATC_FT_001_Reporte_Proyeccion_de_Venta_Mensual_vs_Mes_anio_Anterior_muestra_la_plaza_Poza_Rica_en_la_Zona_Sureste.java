package IMA.CHO52066331;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Reporte_Proyeccion_de_Venta_Mensual_vs_Mes_anio_Anterior_muestra_la_plaza_Poza_Rica_en_la_Zona_Sureste
		extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Reporte_Proyeccion_de_Venta_Mensual_vs_Mes_anio_Anterior_muestra_la_plaza_Poza_Rica_en_la_Zona_Sureste_test(
			HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u, testCase);

		// paso 1
		addStep("Entrar a Microstrategy WEB, con usuario y password validos. ");
		ima.Login();

		// Paso 2 y 3
		addStep("\"Seguir la Ruta:Informacion Comercial QA\\Public Objects\\Reports\\Informacion Comercial\\Analisis de Proyecciones\\ Proyecci�n de Venta Mensual vs Mes a�o Anterior");
		ima.Select_ProyeccionVentaMensual_vs_MesAnioAnterior();

		// Paso 4
		addStep("Validar que el reporte Proyecci�n de Venta Mensual vs Mes aino Anterior muestre datos� de la plaza Poza Rica en la Zona Sureste.");
		ima.ValidarProyeccionVentaMensual_vs_MesAnioAnterior(data.get("textZona"), data.get("textPlaza"));

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Reporte_Proyeccion_de_Venta_Mensual_vs_Mes_anio_Anterior_muestra_la_plaza_Poza_Rica_en_la_Zona_Sureste_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Reporte_Proyeccion_de_Venta_Mensual_vs_Mes_anio_Anterior_muestra_la_plaza_Poza_Rica_en_la_Zona_Sureste_test";
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
