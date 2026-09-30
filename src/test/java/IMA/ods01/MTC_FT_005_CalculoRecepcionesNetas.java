package IMA.ods01;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class MTC_FT_005_CalculoRecepcionesNetas extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_CalculoRecepcionesNetas_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		
		/** VARIABLES *********************************************************************/	
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);	
		
		String SumarizadoMensual = "SELECT * FROM BIDMMKTCOL.FACT_109_REC_DEV_CPRAS_MM"; 
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Ingresar a microstrategy.");
		ima.Login();
		
		/* PASO 2 *********************************************************************/	

		addStep("Seleccionar la carpeta de Reportes de Compras y devoluciones");
		ima.Select_FolderComprasyDevoluciones();
		
		/* PASO 3 y 4 *********************************************************************/	

		addStep("Seleccionar el reporte, Capturar los prompts y presionar Ok");
		ima.generarReporte("CIGARRILLOS");
		
		/* PASO 5 *********************************************************************/	

		addStep("Obtener el valor de la columna $ Recepciones Netas - Mes Actual para uno de los productos listados en el reporte. ");
		List<String> valores = ima.ObtenerVariacionDevoluciones();
		
		/* PASO 6 *********************************************************************/	

		addStep("Validar el c�lculo de la columna $ Recepciones Netas - Mes Actual. ");
		System.out.println(SumarizadoMensual);		
		SQLResult connectionResult = executeQuery(dbBi, SumarizadoMensual);

		boolean connection = false;
		int i = 0;
		List<String> calcu = new ArrayList<String>();
		String tmp = connectionResult.getData(i, "RecepcionesNetas");
		while(tmp != null) {
			calcu.add(tmp);
			i++;
		}
			
		if(i != 0)
			connection = true;

		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		/* PASO 7 *********************************************************************/	

		addStep("Repetir el paso anterior para validar el c�lculo de las Recepciones netas");
		
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QA Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}