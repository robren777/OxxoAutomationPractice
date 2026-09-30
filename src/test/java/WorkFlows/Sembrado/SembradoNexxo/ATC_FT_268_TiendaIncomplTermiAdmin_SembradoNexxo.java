package WorkFlows.Sembrado.SembradoNexxo;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoNexxo;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_268_TiendaIncomplTermiAdmin_SembradoNexxo extends BaseExecution{


	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_268_TiendaIncomplTermiAdmin_SembradoNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Project: SEMBRADO NEXXO");
	//SeleniumUtil u = new SeleniumUtil(new MEdgeTest());


	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER2_XXVA_SIV3, 
			GlobalVariables.DB_PASSWORD2_XXVA_SIV3);

	String arrayEach[] = data.get("tienda").split(":");
	System.out.println("--------->"+arrayEach[0]);

	String QUERY_SIV3_XXVA_SEM_SEMBRADO = "SELECT PLAZA_ID, TIENDA_ID, STATUS, CREATE_DATE, LAST_UPDATE_DATE, LAST_UPDATE_BY\n"
			+ "FROM XXVA_SIV3.XXVA_SEM_SEMBRADO where TIENDA_ID = '"+arrayEach[0]+"' and \n"
			+ "(TRUNC(last_update_date)= TRUNC(SYSDATE) OR TRUNC(CREATE_DATE)= TRUNC(SYSDATE))";

	String QUERY_SIV3_LAST_UPDATE  = "SELECT PLAZA_ID, TIENDA_ID,STATUS, CREATE_DATE, LAST_UPDATE_DATE, LAST_UPDATE_BY"
			+ " FROM XXVA_SIV3.XXVA_SEM_SEMBRADO where TIENDA_ID = '"+arrayEach[0]+"' and\n"
			+ "last_update_date is not null order by LAST_UPDATE_DATE desc";
	
	String QUERY_SIV3_CREATE_DATE = "SELECT PLAZA_ID, TIENDA_ID, STATUS, CREATE_DATE, LAST_UPDATE_DATE, LAST_UPDATE_BY"
			+ " FROM XXVA_SIV3.XXVA_SEM_SEMBRADO where TIENDA_ID = '"+arrayEach[0]+"' order by CREATE_DATE desc";
	
	SeleniumUtil u = new SeleniumUtil(new ChromeTest());

	String url = data.get("url");
	SQLResult queryResult;
	u.get(url);
	
   //sembradoUtils sembrado = new sembradoUtils(u,testCase);
	pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u, testCase);

	/**
	 * Variables
	 * 
	 */

	addStep("Inicio Sesion");
	//sembrado.goOn();
	

	System.out.print(data.get("ps")); 
	pomSembrado.loginGral(data.get("user"), data.get("ps"));
	
	addStep("Ingresa a sembrado de tienda");
	pomSembrado.sembradoPlaza();
    //pomSembrado.ingresaSembradoTienda();
	
	addStep("Ingresa a administrador > legos");
	pomSembrado.ingresaLegos();
	
	addStep("Ingresar a la BD XXVA_SIV3 y realizar la consultas con fecha de hoy");
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXVA_SEM_SEMBRADO);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	assertTrue(queryResult.isEmpty(), "Se encontraron registros con la fecha actual");
	
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_LAST_UPDATE);
	testCase.addQueryEvidenceCurrentStep(queryResult);	
	
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_CREATE_DATE);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	
	addStep("Seleccionar Plaza, Tienda, Seccion y Fase de sembrado de una tienda"
			+ "con status 'TERMINADO'");
	pomSembrado.statusTienda(data.get("plaza"),data.get("tienda"), data.get("seccion"), data.get("fase"));

	
	addStep("Ingresar a \"Sembrado Plaza\"> Seleccionar \"Sembrado Masivo\".");
	pomSembrado.sembradoMasivo(data.get("plaza"),data.get("tienda"));
	
	addStep("Ingresar a la BD XXVA_SIV3 y realizar la consultas con fecha de hoy");
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXVA_SEM_SEMBRADO);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	assertTrue(queryResult.isEmpty(), "Se encontraron registros con la fecha actual");
	
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_LAST_UPDATE);
	testCase.addQueryEvidenceCurrentStep(queryResult);	
	
	queryResult= executeQuery(dbXXVA_SIV3, QUERY_SIV3_CREATE_DATE);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	addStep("Ingresa a sembrado de tienda");
	pomSembrado.ingresaSembradoTienda();
	
	addStep("Ingresar  a  \"Region / Plaza empoderada\" --> \"Sembrado\", realizar la busqueda de la tienda seleccionada "
			+ "y validar que la tienda no cambie de estatus de la tienda");
	pomSembrado.statusTienda(data.get("plaza"),data.get("tienda"), data.get("seccion"), data.get("fase"));

	u.quit();
	
	}

	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Identificar una tienda con status terminado";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. MTC-FT-268-Sembrado Masivo - Tiendas incompletas con sembrado terminado_dato null_Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_268_TiendaIncomplTermiAdmin_SembradoNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
