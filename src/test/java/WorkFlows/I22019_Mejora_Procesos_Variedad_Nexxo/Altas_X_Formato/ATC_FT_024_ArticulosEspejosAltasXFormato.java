package WorkFlows.I22019_Mejora_Procesos_Variedad_Nexxo.Altas_X_Formato;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.AltasXFormato.PomAltasXFormatoNexxo;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
* PREREQUISITOS: 
* - En el DataProvider ingresar en la columna articulo el valor a consultar
*
* DESCRIPCION: Validar que en la pantalla de Replenishment se pueda consultar los items configurados por el Job de Monitoring-Domingo
* @author elberth.ruiz@grupotimex.onmicrosoft.com
*/
public class ATC_FT_024_ArticulosEspejosAltasXFormato extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_024_ValidacionArticulosEspejosAltasXFormatoNexxo_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Framework WorkFlows --> Altas por Formato");
		
		SQLResult queryResult;
		String articulo = data.get("articulo");
		
		//Conexiones a BD
		//PIEVE***
		utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER2_XXVA_SIV3, 
				GlobalVariables.DB_PASSWORD2_XXVA_SIV3);

		//QUery para consultar los ITEMS espejo en las tabla RPL_ITEM_ESPEJO
		String QUERY_SIV3_XXVA_ITEM_ESPEJO = "SELECT ITEM, PLAZA_ID, PLAZA_ESPEJO_ID, ITEM_ESPEJO, PORCENTAJE_VENTA\n"
				+ "FROM XXVA_SIV3.XXVA_RPL_ITEM_ESPEJO where ITEM = '"+articulo+"'\n"
				+ "AND ROWNUM <= 1 ORDER BY LAST_UPDATE_DATE";

//*******************************************************************************************
	
	u = new SeleniumUtil(new FirefoxTest(),true);
	u.get(data.get("url"));
	
	PomAltasXFormatoNexxo pomAltasXFormato = new PomAltasXFormatoNexxo(u, testCase);

	//Paso 1
	addStep("Ingresar al aplicativo REPLENISHMENT");
	pomAltasXFormato.login(data.get("user"), data.get("ps"));//Ingreso al AC y selecciono la APP Altas por Formato
	
	//Paso 2
	addStep("Se ingresa a Altas por Formato --> Altas por Formato y se busca el articulo");
	pomAltasXFormato.buscarArticulo(articulo);
	
	//Paso 3
	addStep("Resultado en BD");
	try {
		queryResult = executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXVA_ITEM_ESPEJO);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		Boolean validacion = false;
		if (queryResult.getRowCount() > 0) 
			validacion = true;
		assertTrue(validacion, "No se encontraro el codigo de Barras");

	} catch (SQLException e) {
		System.out.println(" -> " + e);
		throw new AssertionError("Fallo al ejecutar el query");
	}
	
	//u.getDriver().quit();
	}
		
	@AfterMethod
	public void afterMethod() {
	u.getDriver().quit();
	
	}
	
	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar que al ejecutar el JOB en el dia especifico se realice la asignacion de articulos espejo a articulos que no contaban con uno";
	}

	@Override
	public String setTestDesigner() {
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_024_ValidacionArticulosEspejosAltasXFormatoNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
