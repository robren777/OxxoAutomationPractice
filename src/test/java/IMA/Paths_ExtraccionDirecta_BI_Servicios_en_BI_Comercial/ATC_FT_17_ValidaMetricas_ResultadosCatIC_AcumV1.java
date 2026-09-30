package IMA.Paths_ExtraccionDirecta_BI_Servicios_en_BI_Comercial;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_17_ValidaMetricas_ResultadosCatIC_AcumV1 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_17_ValidaMetricas_ResultadosCatIC_AcumV1_Test(HashMap<String, String> data) throws Exception {

		/** VARIABLES *********************************************************************/	

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);		
		IMATools ima = new IMATools(u,testCase);	
		
		// Paso 1
		addStep("Dentro del Reporte  �Resultados Categor�as + IC (Acum) v1�  se localiza la m�trica �Unidades Netas Vendidas en Tiendas Acumulado�");
		ima.Login();
		ima.gotoRoute("Informaci�n Comercial QA/Informaci�n Comercial/Resultados del Negocio/Nacional/Resultados Categor�as/Resultados Categor�as + IC (Acum) v1");
		

		
	}
  
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_17_ValidaMetricas_ResultadosCatIC_AcumV1";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se deber� validar la actualizaci�n de la m�trica  �Unidades Netas Vendidas en Tiendas Acumulado� en el reporte  �Resultados Categor�as + IC (Acum) v1� ";
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