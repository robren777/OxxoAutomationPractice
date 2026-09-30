package IMA.extracccionConexionDirecta_01InformacionComercial_ReporteNSP_04RevisionDeReportes;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import IMA.util.IMATools;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_PLAZA_ValidarEstructuraDeReporte_NivelDeServicioYVentaPerdida_Semanal extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_PLAZA_ValidarEstructuraDeReporte_NivelDeServicioYVentaPerdida_Semanal_test(
			HashMap<String, String> data) throws Exception {

		// Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String report = data.get("Report");

		IMATools ima = new IMATools(u, testCase);

		System.out.println(report);
		// paso 1
		addStep("Ingresar a la siguiente dirección: "
				+ "Información Comercial >Informes compartidos > NSP > Plaza en Microstrategy y "
				+ "seleccionar el reporte Nivel de Servicio y Venta Perdida (Semanal)");
		ima.Login();
		ima.goToRouteSubFolder("Información Comercial QA", "NSP/Plaza");

		addStep("Se llena el formulario seleccionando una opcion en cada filtro y se ejecuta el informe");
		ima.EjecutarInformeReporteSeleccionado(report);
		
		addStep("Validar las columnas en el reporte y validar que la informacion mostrada sea de la semana seleccionada");
		String[] input = {"Tienda","Producto","StockOut","Venta Perdida", "Nivel de Servicio", "Semana 22"};
		ima.validarColumnas(input);
		
		addStep("Regresar a la pantalla de creacion del reporte");
		u.click(By.xpath("//*[@id=\"tbBack0\"]"));
		
		addStep("Agregar 3 opciones al filtro semanas y ejecutar de nuevl el reporte");
		
		u.click(By.xpath("//span[1][text()='Seleccion de Semana a Consultar (Maximo 4).']"));
		By searchBox = By.id("id_mstr131_txt");
		By btnSearchBox = By.xpath("//*[@id=\"id_mstr131\"]/table/tbody/tr/td[2]/div/input");
		By opcion = By.xpath("//*[@id=\"id_mstr133ListContainer\"]/div/div");
		By btnAgregarOpcion = By.id("id_mstr135");
		
		String[] opciones = {"202123", "202124", "202125",};
		ima.modificarOpcionesFiltro(searchBox, btnSearchBox, opcion, btnAgregarOpcion, opciones);
		
		addStep("Validar que la informacion mostrada sea de las semanas seleccionadas");
		String[] inputM = {"Semana 22", "Semana 23", "Semana 24", "Semana 25"};
		ima.validarColumnas(inputM);
		
		u.hardWait(3);
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar estructura de reporte Nivel de Servicio y Venta Perdida (Semanal)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_PLAZA_ValidarEstructuraDeReporte_NivelDeServicioYVentaPerdida_Semanal";
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