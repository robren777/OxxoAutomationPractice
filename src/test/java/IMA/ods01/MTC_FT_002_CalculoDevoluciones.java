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

public class MTC_FT_002_CalculoDevoluciones extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CalculoDevoluciones(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		//String user = data.get("user"); // Eliminados ya que se modifico la estructura del constructor de IMATools y se pasaron user y pass a globalVariables.
		//String password = data.get("password");
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		
		IMATools ima = new IMATools(u,testCase);	
		String SumarizadoMensual = "SELECT * FROM BIDMMKTCOL.FACT_109_REC_DEV_CPRAS_MM"; // Solo existe la tabla por DD no por MM
		
		// paso 1
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();
		
		
		// Paso 2 
		addStep("Seleccionar la carpeta de compras y devoluciones");
		ima.Select_FolderComprasyDevoluciones();
		
		// Paso 3 y 4 
		addStep("Seleccionar el reporte, Seleccionar un proveedor con art�culos de licores, vinos o cigarros");
		ima.generarReporte("CIGARRILLOS");
		
		// Paso 5
		addStep("Obtener el valor de la columna Devoluciones - Mes Actual para uno de los productos listados en el reporte. ");
		// No existe la columna devoluciones 
		
		// Paso 6
		addStep("Validar el c�lculo de la columna $ Devoluciones - Mes Actual");
		System.out.println(SumarizadoMensual);		
		SQLResult connectionResult = executeQuery(dbBi, SumarizadoMensual);

		boolean connection = false;
		int i = 0;
		List<String> calcu = new ArrayList<String>();
		String tmp = connectionResult.getData(i, "DEVOLUCIONES_MA");
		while(tmp != null) {
			calcu.add(tmp);
			i++;
		}
			
		if(i != 0)
			connection = true;

		assertTrue(connection, "La tabla no contiene informaci�n.");			
	}
	
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
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
