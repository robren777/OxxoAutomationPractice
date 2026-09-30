package WorkFlows.Sembrado.SembradoNexxoBloque2;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoNexxo;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_186_CrearCombinabilidad_Administrador extends BaseExecution{

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_186_CrearCombinabilidad_Administrador_test(HashMap<String, String> data) throws Exception {
	
	/****** Base de datos ******/ 
		String query = "select * from (SELECT planograma1_id, planograma2_id,last_update_date,"
				+ "last_update_by FROM xxva_siv3.XXVA_SEM_COMBINABILIDAD "
				+ "where last_update_date >= TRUNC(SYSDATE) order by CREATE_DATE desc) "
				+ "where rownum <=5";
		
		utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
				GlobalVariables.DB_USER2_XXVA_SIV3, 
				GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
 
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(),true);
		u.get(data.get("url"));

		pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u, testCase);
		
	// PASO 1	
		addStep("Inicia sesion");
		pomSembrado.loginGral(data.get("user"), data.get("ps"));
		testCase.addScreenShotCurrentStep("Login");
	
	// PASO 2		
		addStep("Seleccionar  en Comercial \"Sembrado Plaza\"");
		pomSembrado.sembradoPlaza();
	
	// PASO 3		
		addStep("Ingresar a \"Administrador\"> Seleccionar \"Combinabilidad\".");
		pomSembrado.ingresaCombinabilidad();
	
	// PASO 4		
		addStep("Seleccionar <\"Planogrupo1\">,Seleccionar <\"Planogrupo2\"> y Dar click en [Agregar].");
		pomSembrado.crearCombinacion(data.get("planoGrupo1"), data.get("planoGrupo2"));
		
		u.close();
		
	// PASO 5	
		addStep("Realizar consulta en la BD xxva_siv3");
		System.out.println(query);
		SQLResult combinacion = executeQuery(dbXXVA_SIV3, query);
		boolean ValidComb = combinacion.isEmpty();
		if (!ValidComb) {
			testCase.addTextEvidenceCurrentStep("Se encontro informacion de la combinacion en la tabla");
		}
		testCase.addQueryEvidenceCurrentStep(combinacion);
		assertFalse(ValidComb, "No se encontron registros en la tabla XXVA_SEM_COMBINABILIDAD");
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Tener acceso al aplicativo de sembrado\r\n" + 
				"Tener acceso a la BD xxva_siv3";
	}

	@Override
	public String setTestDescription() {
		return "Solo un usuario con perfil administrador podra entrar las opciones Conjuntos y "
				+ "Combinabilidad desde menu Administrador y acceder a la informacion de "
				+ "todas las regiones.";
	}

	@Override
	public String setTestDesigner() {
		return "Tania Dena";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}	