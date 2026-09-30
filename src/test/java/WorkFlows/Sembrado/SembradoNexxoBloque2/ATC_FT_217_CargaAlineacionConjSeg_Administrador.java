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

public class ATC_FT_217_CargaAlineacionConjSeg_Administrador extends BaseExecution{

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_217_CargaAlineacionConjSeg_Administrador_test(HashMap<String, String> data) throws Exception {
	
	/****** Base de datos ******/ 
		String query = "select * from (SELECT filename,last_update_date,file_type FROM "
				+ "xxva_siv3.XXVA_SEM_CONTROL_CARGA_MASIVA where last_update_date >= TRUNC(SYSDATE)"
				+ " and filename='"+data.get("nomArch")+"'"+ " order by LAST_UPDATE_DATE desc)"
				+ " where rownum <=5";
		
		utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
				GlobalVariables.DB_USER2_XXVA_SIV3, 
				GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
 
		pomSembradoNexxo sembrado = new pomSembradoNexxo(testCase);
		
	// PASO 4		
		addStep("Verificar campos en la tabla del archivo");
		String [] columnas= {data.get("col1"),data.get("col2"),data.get("col3"),
				data.get("col4"),data.get("col5"),data.get("col6")};
		sembrado.validaArchivo(data.get("nomArch"),columnas);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(),true);	
		pomSembradoNexxo pomSembrado = new pomSembradoNexxo(u,testCase);
		u.get(data.get("url"));	
		
	// PASO 1	
		addStep("Inicia sesion");
		pomSembrado.loginGral(data.get("user"), data.get("ps"));
		testCase.addScreenShotCurrentStep("Login");
	
	// PASO 2		
		addStep("Seleccionar  en Comercial \"Sembrado Plaza\"");
		pomSembrado.sembradoPlaza();
		
	// PASO 3		
		addStep("Ingresar a \"Administrador\"> Seleccionar \"Carga de Archivos\"");
		pomSembrado.ingresaCargaArchivos();
		
	// PASO 5	
		addStep("Seleccionar  <\"Archivo a Cargar\">, Seleccionar <\"Alineaci�n de Conjuntos y "
				+ "Segmentos\">, Seleccionar <\"Seleccionar archivo\">, Elegir <\"Archivo\">,"
				+ "Seleccionar [Subir archivo].");
		pomSembrado.cargarArchivo(data.get("opArchivo"), data.get("nomArch"));
		
		u.close();
		
	// PASO 6	
		addStep("Realizar consulta en la BD xxva_siv3");
		System.out.println(query);
		SQLResult archivos = executeQuery(dbXXVA_SIV3, query);
		boolean ValidArch = archivos.isEmpty();
		if (!ValidArch) {
			testCase.addTextEvidenceCurrentStep("Se encontro informacion de carga de archivos "
					+ "con la fecha de hoy en la tabla");
		}
		testCase.addQueryEvidenceCurrentStep(archivos);
		assertFalse(ValidArch, "No se encontron registros con la fecha de hoy en la tabla "
				+ "XXVA_SEM_CONTROL_CARGA_MASIVA, por lo tanto el archivo no se cargo correctamente");
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Tener acceso al aplicativo de sembrado\r\n" + 
				"Tener archivo de carga ya creado.\r\n" + 
				"Tener acceso a la BD xxva_siv3.";
	}

	@Override
	public String setTestDescription() {
		return "Cambiar la etiqueta de Plaza por Region en los formatos de los archivos de carga que lo requieran. \r\n" + 
				"En las validaciones que se hacen en cada carga de archivos, el sistema debera validar el campo que antes "
				+ "era Plaza y ahora es Region en cada carga que lo requiera.\r\n" + 
				"1.	Alineacion de Conjuntos y Segmentos\r\n" + 
				"2.	Crear Legos\r\n" + 
				"3.	Sembrar Legos planogramables\r\n" + 
				"4.	Borrar Legos planogramables\r\n" + 
				"5.	Sembrar Legos NO planogramables\r\n" + 
				"6.	Borrar Legos NO planogramables\r\n" + 
				"7.	Eliminar Legos\r\n" + 
				"8.	Sembrado Minimo\r\n" + 
				"9.	Variantes de Seccion\r\n" + 
				"10. Segmento Tienda-Segmento Lego";
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