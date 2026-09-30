package IMA.TimbradoFactGlobales;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ReporteTicketsTardiosDetalle extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ReporteTicketsTardiosDetalle_Test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		IMATools ima = new IMATools(u,testCase);	
		
		// Paso 1
		u.get("http://fcobiwqa01/microstrategy/asp/main.aspx");
		addStep("Ingresar a microstrategy mediante la siguiente liga: http://fcobiwqa01/microstrategy/asp/main.aspx");
		ima.Login();
		
		// Paso 2
		addStep("En microstrategy seguir la siguiente ruta para accesar al reporte de tickets tardios, TICKETS QA/ INFORMES COMPARTIDOS/FACTURACION GLOBAL");
		ima.gotoRoute("Tickets QA/Facturacion Global/Reporte Ticket Tardios - Detallado");
		
		// Paso 3
		addStep("Ingresar al reporte de tickets tard�os con detalle utilizando los filtros requeridos");
		testCase.addScreenShotCurrentStep(u, "Se valida el acceso al reporte de tickets tard�os con detalle utilizando los filtros requeridos.");
		String[] dataInput = {"20200807","10CTL:Coatzacoalcos"/*data.get("fechaRecep")*/};
		ima.capturaPrompt(dataInput);
		
		// Paso 4
		addStep("Filtra por fecha de recepci�n el reporte de tickets tard�os");		
		ima.resalta("20200807");
		testCase.addScreenShotCurrentStep(u, "Filtra por fecha de recepci�n el reporte de tickets tard�os");		
		
		// Paso 5
		addStep("Filtra por tipo el reporte de tickets tard�os");		
		testCase.addScreenShotCurrentStep(u, "Filtra por tipo el reporte de tickets tard�os");
		
		// Paso 6
		addStep("Filtra por fecha de periodo el reporte de tickets tard�os");
		testCase.addScreenShotCurrentStep(u, "Filtra por fecha de periodo el reporte de tickets tard�os");
		
		// Paso 7
		addStep("Filtra por  plaza el reporte de tickets tard�os");		
		ima.resalta("10CTL:Coatzacoalcos");
		testCase.addScreenShotCurrentStep(u, "Filtra por  plaza el reporte de tickets tard�os");
		
		// Paso 8
		addStep("Filtra por tipo de negocio (OXXO,BARA,MATDOR) el reporte de tickets tard�os");		
		ima.ejecutarInforme();
		
		// Paso 9
		addStep("En la pantalla principal del reporte de tickets tard�os, presionar el bot�n Exportar.");		
		ima.exportar("Excel");
		
		// Paso 10
		addStep("Comparar los encabezados del reporte en Excel con los de la tabla en el portal.");		
		// La comparaci�n es valida solo si fuera un archivo CSV, de otra forma abrir un archivo excel es mas complicado
	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ReporteTicketsTardiosDetalle_Test";
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
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
