package IMA.saldazo;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_025_ValidacionDelReporte_3B_Saldazo_Compras_Categoria_Rango_Edad_Genero extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_025_ValidacionDelReporte_3B_Saldazo_Compras_Categoria_Rango_Edad_Genero_test(HashMap<String, String> data) throws Exception {

		// Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String report = data.get("Report");

		IMATools ima = new IMATools(u, testCase);

		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
		
		u.waitForLoadPage();
		// Paso 2
		addStep("Ir a carpeta de Saldazo");
		ima.goToRoute("Informaci�n Comercial QA","Informaci�n Comercial/Telefon�a y Servicios/Tarjeta Saldazo/Mes");

		// Paso 3
		addStep("Seleccionar el reporte de recepciones en tienda por fuente de abasto y ejecutar informe"); 
		ima.EjecutarInformeReporteSeleccionado(report);
		
		String[] columnas = {"Indicadores"};
		ima.validarColumnas(columnas);
		/**
		 * Se solicita en el Caso de prueba el contar con un numero de tarjeta activada y compararla en el reporte,
		 * sin embargo no se cuenta con ese dato y en el reporte no se muestran numeros
		 */
		 
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
		return "Validacion del reporte";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_025_ValidacionDelReporte_3B_Saldazo_Compras_Categoria_Rango_Edad_Genero_test";
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
