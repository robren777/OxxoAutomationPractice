package IMA.TransformacionAbasto.scripts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.TransformacionAbasto.om.pomTransformacionAbasto;
import modelo.BaseExecution;

/**
 * PRE-REQUISITOS: Contar con el archivo campaigns_yyyy-mm-dd_{unique id}.csv
 * 
 * DESCRIPCION: 
 * A) Validar que el archivo contenga los encabezados correspondientes
 * B) Validar que el contenido del archivo se apegue a las especificaciones de formato.
 * 
 * @author esmeraldag@hexaware.com
 */

public class ATC_FT_011_ValidacionCSV_InterfaceCampaigns_TransformAbasto extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_ValidacionCSV_InterfaceCampaigns_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22141-TI01_TransformacionAbastoPronostico");

		// Inicializar el pom
		pomTransformacionAbasto pom = new pomTransformacionAbasto(testCase);

		// Ruta del archivo
		final String BASE_PATH = System.getProperty("user.dir");
		String fileName = "campaigns_2024-03-15_136.csv";
		String path = BASE_PATH + "\\Config\\Input\\Files\\TransformacionAbasto\\" + fileName;

		// Datos de configuracion
		char separacion = ';';
		int numColumns = 10;
		String headers = "campaign,name,start_date,end_date,campaign_class,category,type,comunicacion,exhibicion,ventasugerida";
		String config = "STRING,STRING,STRING,STRING,STRING,STRING,STRING,STRING,STRING,BOOLEAN";

		// Toma de tiempo: inicio de prueba
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ahoraIn = LocalDateTime.now();
		String inicio = ahoraIn.format(formato);

		// **** PASO 1 **** Validacion de encabezados
		addStep("Validar que el encabezado sea: \n" + headers);
		pom.headerValidation(path, numColumns, headers, separacion);

		// **** PASO 2 **** Validacion de formato
		addStep("Validar que el contenido del archivo .csv cumpla con los siguientes puntos:");
		testCase.addTextEvidenceCurrentStep("1.- Cada linea del archivo es una fila.\n"
				+ "2.- Los campos están separados por un punto y coma (;).\n"
				+ "3.- Todas las filas deben tener la misma cantidad de campos.\n"
				+ "4.- Tipo STRING: es obligatorio que este entre comillas dobles (\"\"\"\")\n"
				+ "5.- Tipo DOUBLE: acepta enteros y decimales. Puede estar o no entre comillas dobles (\"\"\"\"). Acepta numeros negativos. No acepta espacios en blanco.\n"
				+ "6.- Tipo BOOLEAN: false o true. Puede estar o no entre comillas dobles (\"\"\"\"). Acepta espacios en blanco.\n"
				+ "7.- Tipo DATE: el formato es YYYYMMDD. Puede estar o no entre comillas dobles (\"\"\"\"). No acepta espacios en blanco.\n"
				+ "8.- Tipo STRING(ENUM): el dato que se muestre debe estar dentro de un rango de opciones establecidas. Es obligatorio que este entre comillas dobles (\"\"\"\").\n"
				+ "9.- Los campos pueden estar vacios, ya sea que se muestren solo comillas dobles (;\"\"\"\";) o vacio (;;).\n"
				+ "");
		pom.validateFileCSV(path, numColumns, config, separacion);

		// Toma de tiempo: fin de prueba
		LocalDateTime ahoraFin = LocalDateTime.now();
		String fin = ahoraFin.format(formato);

		// Resultado
		testCase.addBoldTextEvidenceCurrentStep("Archivo validado: " + fileName);
		testCase.addTextEvidenceCurrentStep("Inicio:\t" + inicio + "\nFin: \t" + fin);

		System.out.println("\n>>> CAMPAIGNS <<<");
		System.out.println("Archivo validado: " + fileName);
		System.out.println("Inicio:\t" + inicio + "\nFin: \t" + fin);
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con el archivo campaigns_yyyy-mm-dd_{unique_id}.csv a validar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "A) Validar que el archivo contenga los encabezados correspondientes\n"
				+ "B) Validar que el contenido del archivo se apegue a las especificaciones de formato.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_011_ValidacionCSV_InterfaceCampaigns_test";
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

}
