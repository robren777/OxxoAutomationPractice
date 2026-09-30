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

public class ATC_FT_226_CambioEtiquetaCargaSegmentoTiendaSegmentoLego_Administrador extends BaseExecution{


	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_226_CambioEtiquetaCargaSegmentoTiendaSegmentoLego_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Project: Sembrado Nexxo");
	
	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
	
	SQLResult queryResult;
	String ValidaArchivo = "SELECT control_id, filename, status, file_type,TO_CHAR(last_update_date, 'YY-MM-DD HH24:MI:SS') last_update_date\r\n"
			+ "FROM xxva_siv3.XXVA_SEM_CONTROL_CARGA_MASIVA \r\n"
			+ "where last_update_date>= TRUNC(SYSDATE)\r\n"
			+ "and filename = '"+data.get("nombreArchivo")+"'\r\n" //Formato de carga_Relacion Seg_Tienda-Seg_Lego
			+ "order by LAST_UPDATE_DATE desc";
		
	
	      pomSembradoNexxo sembrado = new pomSembradoNexxo(testCase);
	
       // Paso 4		
        addStep("Verificar la tabla del archivo con los campos:\r\n"
        		+ "REGION_ID\r\n"
        		+ "SECCION\r\n"
        		+ "SEGMENTO\r\n"
        		+ "SEGMENTO_LEGO_ID\r\n"
        		+ "CREADO_POR");
        String [] columnas= {data.get("col1"),data.get("col2"), data.get("col3"), data.get("col4"),
                  data.get("col5")};

           sembrado.validaArchivo(data.get("nomArch"),columnas);

           SeleniumUtil u = new SeleniumUtil(new ChromeTest(),true);	
           pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u,testCase);
           u.get(data.get("url"));
           
       // Paso 1	
			addStep("Inicia sesion");
			pomSembrado.loginGral(data.get("user"), data.get("ps"));
			testCase.addScreenShotCurrentStep("Login");
		
       // Paso 2		
			addStep("Seleccionar  en Comercial \"Sembrado Plaza\"");
			pomSembrado.sembradoPlaza();
			
       // Paso 3		
			addStep("Ingresar a \"Administrador\"> Seleccionar \"Carga de Archivos\"");
			pomSembrado.ingresaCargaArchivos();
			
		// Paso 5	
		 addStep("Seleccionar  <\"Archivo a Cargar\">, Seleccionar <\"Segmento Tienda\">, Seleccionar <\"Seleccionar archivo\">, "
		 		+ "Elegir <\"Archivo\">,Seleccionar [Subir archivo]");
			pomSembrado.cargarArchivo(data.get("opArchivo"), data.get("nomArch"));
	
	u.getDriver().quit();
	
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
		return "Cambio etiqueta_Carga Segmento Tienda-Segmento Lego_Administrador";
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
