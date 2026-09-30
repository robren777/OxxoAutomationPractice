package IMA.CHO52024656;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_Generacion_de_reporte_Consolidado_de_Refacciones_por_Proveedor_con_errores
		extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Generacion_de_reporte_Consolidado_de_Refacciones_por_Proveedor_con_errores_test(
			HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u, testCase);

		// paso 1
		addStep("Entrar a Microstrategy WEB, con usuario y password validos. ");
		ima.Login();

		// Paso 2
		addStep("Seguir la siguiente Ruta: Mantenimiento QA > Informes Compartidos > Control de Gastos > Refacciones� > Reporte Consolidado de refacciones por Proveedor.");
		ima.SelectFolder_ReporteConsolidadoRefaccionesPorProveedor();

		// Paso 3 y 4
		addStep("Del Reporte Consolidado de Refacciones por Proveedor, se deber seleccionar datos que en la base de datos BIMTTOQA.FEMCOM.NET no existan, esto con la finalidad de que no se genera el reporte correctamente."
				+ "\r\n" + "Seleccionar rea." + "Seleccionar Anios Fin a Consultar."
				+ "Seleccionar Meses Fin Acumulados.");
		ima.Generate_ReporteConsolidadoRefaccionesPorProveedor(data.get("FiltroArea"), data.get("FiltroAno"),
				data.get("FiltroMes"));

		// Paso 5
		addStep("Ruta: Herramientas > Pagina de detalles del Informe."
				+ "Validar la sentencia que genera Microstrategy, con la finalidad de comprobar que no exista informacion en la DB BIMTTOQA.FEMCOM.NET ");

		ima.ValidarSentenciaDeBusqueda(data.get("FiltroArea"), data.get("FiltroAno"), data.get("FiltroMes"));

		// Paso 6
		// addStep("Entrar a la DB BIMTTOQA.FEMCOM.NET con� usuario� y password v�lidos,
		// en paralelo� con Microstrategy esto para corroborar� que no existan datos.");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_Generacion_de_reporte_Consolidado_de_Refacciones_por_Proveedor_con_errores_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_Generacion_de_reporte_Consolidado_de_Refacciones_por_Proveedor_con_errores_test";
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
