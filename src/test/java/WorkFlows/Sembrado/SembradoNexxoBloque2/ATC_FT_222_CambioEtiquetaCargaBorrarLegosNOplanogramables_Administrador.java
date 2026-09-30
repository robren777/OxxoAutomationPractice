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

public class ATC_FT_222_CambioEtiquetaCargaBorrarLegosNOplanogramables_Administrador extends BaseExecution{


	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_222_CambioEtiquetaCargaBorrarLegosNOplanogramables_Administrador_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Project: Sembrado Nexxo");
	
	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
	
	SQLResult queryResult;
	String ValidaArchivo = "SELECT control_id, filename, status, file_type,TO_CHAR(last_update_date, 'YY-MM-DD HH24:MI:SS') last_update_date\r\n"
			+ "FROM xxva_siv3.XXVA_SEM_CONTROL_CARGA_MASIVA \r\n"
			+ "where last_update_date>= TRUNC(SYSDATE)\r\n"
			+ "and filename = '"+data.get("nomArch")+"'\r\n" //4) Borrar Legos NP
			+ "order by LAST_UPDATE_DATE desc";
		
	
	pomSembradoNexxo pomSembrado = new pomSembradoNexxo(testCase);

	// Paso 4		
		addStep("Verificar la tabla del archivo con los campos:\r\n"
				+ "Region\r\n"
				+ "Tienda\r\n"
				+ "Seccion\r\n"
				+ "Mueble");
		
		String [] columnas= {data.get("col1"),data.get("col2"),data.get("col3"),
					data.get("col4")};
			pomSembrado.validaArchivo(data.get("nomArch"),columnas);
	
			
	//Paso 1
			SeleniumUtil u = new SeleniumUtil(new ChromeTest(),true);	
			pomSembradoNexxo pomSembradoNexxo = new pomSembradoNexxo(u,testCase);
			u.get(data.get("url"));	
			
			addStep("Ingresar al portal  con el <<<Usuario Administrador >>>");
			
			pomSembradoNexxo.loginGral(data.get("user"), data.get("ps"));
			testCase.addScreenShotCurrentStep("Login");
			
	//Paso 2
			
			addStep("Seleccionar  en Comercial \"Sembrado Plaza\"");
			pomSembradoNexxo.sembradoPlaza();
			
			
	//Paso 3
			addStep("Ingresar a \"Administrador\"> Seleccionar \"Carga de Archivos\"");
			pomSembradoNexxo.ingresaCargaArchivos();
			
	
	//Paso 5
			addStep("Seleccionar  <\"Archivo a Cargar\">, Seleccionar <\"Borrar Legos NO planogramables\">, Seleccionar <\"Seleccionar archivo\">, "
					+ "Elegir <\"Archivo\">,Seleccionar [Subir archivo]");
			pomSembradoNexxo.cargarArchivo(data.get("opArchivo"), data.get("nomArch"));
			
	
	u.getDriver().quit();
	
	//Paso 6
	addStep("Realizar la consulta en la BD");
	
	System.out.println(ValidaArchivo);
	queryResult= executeQuery(dbXXVA_SIV3, ValidaArchivo);
	testCase.addQueryEvidenceCurrentStep(queryResult);
	
	assertFalse(queryResult.isEmpty(), "No se encontraron registros");


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
		return "Cambio etiqueta_Carga Borrar Legos NO planogramables_Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Marisol Rodriguez Reyna";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}