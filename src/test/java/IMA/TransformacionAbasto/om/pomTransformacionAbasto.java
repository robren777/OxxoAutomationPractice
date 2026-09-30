package IMA.TransformacionAbasto.om;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.TestCase;
import utils.sql.SQLResult;

public class pomTransformacionAbasto {
	private TestCase testCase;

	/**
	 * Constructor. Se manda en testCase utilizado en el test
	 * 
	 * @param testCase
	 * @author Esmeralda Garcia
	 */
	public pomTransformacionAbasto(TestCase testCase) {
		super();
		this.testCase = testCase;
	}

	/**
	 * Valida que el archivo csv cumpla con las caracteristicas solicitadas:
	 * Cantidad de campos en cada fila, formato por tipo de dato.
	 * 
	 * @param filePath
	 * @param numberColumns
	 * @param config
	 * @author Esmeralda Garcia
	 */
	public void validateFileCSV(String filePath, int numberColumns, String config, char separacion) {
		String log = "";
		String band = "";
		String logLineas = "";
		String expectedConfig = "";
		String csvFile = filePath;
		List<String> fieldsLine = new ArrayList<>();
		String[] configFields = config.split(",");

		// Abre el archivo para su lectura
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF8"))) {
			String line;
			int countLine = 0;

			String[] listConfig = config.split(",");
			int contConfig = 0;
			for (String confg : listConfig) {
				contConfig++;
				if (confg.contains("STRING(ENUM)")) {
					String[] auxDetails = confg.split("-");
					expectedConfig = expectedConfig + "Campo: " + contConfig + " - " + auxDetails[0] + "\n";

					String[] details = auxDetails[1].split("\\$");
					for (String det : details) {
						expectedConfig = expectedConfig + "\t" + "* " + det + "\n";
					}
				} else {
					expectedConfig = expectedConfig + "Campo: " + contConfig + " - " + confg + "\n";
				}
			}

			// Lee línea por línea
			while ((line = br.readLine()) != null) {
				countLine++;
				System.out.println("Procesando... " + countLine);

				// Se obtiene una lista con los campos de la línea.
				fieldsLine = splitFields(line, separacion);

				// Valida la cantidad de campos encontrados vs los esperados
				if (fieldsLine.size() != numberColumns) {
					log = log + "La línea: " + countLine + " - tiene " + fieldsLine.size() + " campos de "
							+ numberColumns + "\n";
				}

				// Se salta la primera línea ya que son los encabezados
				if (countLine != 1) {
					log = log + validationByType(fieldsLine, configFields, countLine);
				}

				// Si algún campo marca error, se evidencia toda la línea
				if (!band.equals(log)) {
					logLineas = logLineas + "Error en línea " + countLine + ": \n"
							+ Arrays.toString(fieldsLine.toArray()) + "\n\n";
					System.out.println(logLineas);
					testCase.addTextEvidenceCurrentStep(logLineas);
					logLineas = "";
					band = log;
				}

				if (countLine > 100000)
					break;
			}
			System.out.println("|" + log + "|");
//			System.out.println(">>>>>Listado de fallidos>>>>>\n\n" + fallidos + "\n>>>>>Listado de fallidos>>>>>\n");

			// Evidencia: el log solo guarda informacion de fallo
			if (!log.equals("")) {
				String filename = filePath.substring(filePath.lastIndexOf('\\') + 1);
				log = log + "\n\n\nArchivo validado: " + filename;
				logLineas = logLineas + "\n\n\nArchivo validado: " + filename;
				System.out.println("FAILED \n" + logLineas);
				throw new AssertionError("Archivo validado: " + filename);
			} else {
				DecimalFormat formato = new DecimalFormat("#,###,###");
				String totalLines = formato.format((countLine - 1));

				System.out.println("PASSED \n" + "Se validaron " + totalLines
						+ " filas: todas cumplen con la cantidad de columnas esperada, y cada campo tiene el formato correspondiente.\n"
						+ expectedConfig);
				testCase.addTextEvidenceCurrentStep("RESULTADO: Se validaron " + totalLines
						+ " filas: todas cumplen con la cantidad de columnas esperada, y cada campo tiene el formato correspondiente.\n"
						+ expectedConfig);
				testCase.passStep();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertionError("Algo salio mal al validar el formato de los campos ");
		}
	}

	/**
	 * Extrae los campos de una fila/linea usando un delimitador. Toma en cuenta que
	 * dentro del campo puede haber comas, pero este debe estar entre comillas.
	 * También toma en cuenta que puede haber comillas que vienen precedidas por una
	 * diagonal invertida.
	 * 
	 * @param line
	 * @param separacion
	 * @return Lista con los campos obtenidos
	 * @author Esmeralda Garcia
	 */
	private List<String> splitFields(String line, char separacion) { // Obtiene la linea\fila a validar
		List<String> fields = new ArrayList<>();
		String auxField = "";
		Boolean doubleQuotes = false;
		Boolean slash = false;
		try {
			/**
			 * Revisa la linea caracter por caracter, por medio de banderas identifica si
			 * hay comillas abiertas o cerradas, así como identifica si hay comillas dobles
			 * dentro de comillas (las precede un slash) esto para separar los campos.
			 */
			for (char letter : line.toCharArray()) {
				if (letter == '\\') {
					slash = true;
				}
				if (letter == '"') {
					if (doubleQuotes && !slash) {
						doubleQuotes = false;
					} else {
						doubleQuotes = true;
						slash = false;
					}
				}
				if (letter == separacion && doubleQuotes == false) {
					fields.add(auxField);
					auxField = "";
					continue;
				}
				auxField = auxField + letter;
			}
			fields.add(auxField);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertionError("Algo salio mal al identificar y serparar los campos");
		}
		return fields;
	}

	/**
	 * Valida que los encabezados que están en el archivo (linea 1) sean los
	 * correspondientes.
	 * 
	 * @param filePath
	 * @param numberColumns
	 * @param headers
	 * @author Esmeralda Garcia
	 */
	public void headerValidation(String filePath, int numberColumns, String headers, char separacion) {
		String log = "";
		String logOK = "\nCampo : Esperado - Archivo \n";
		String expectedHeaders = "";
		String csvFile = filePath;
		List<String> fieldsLine = new ArrayList<>();
		Boolean val = true;

		// Abre el archivo para su lectura.
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF8"))) {
			String line;
			int countLine = 0;

			// Lee l�nea por l�nea
			while ((line = br.readLine()) != null) {
				countLine++;
				System.out.println("Procesando... " + countLine);

				// Se obtiene una lista con los campos de la linea.
				fieldsLine = splitFields(line, separacion);

				// Valida la cantidad de campos encontrados vs los esperados
				if (fieldsLine.size() != numberColumns) {
					log = log + "Encabezado: Tiene " + fieldsLine.size() + " campos de " + numberColumns + "\n";
					val = false;
				}

				// Solo revisa la primera linea
				if (countLine == 1) {
					try {
						String[] headerList = headers.split(",");
						int i = 0;
						///
						for (String header : headerList) {
							expectedHeaders = expectedHeaders + (i + 1) + " : " + header.trim() + "\n";
							if (!header.trim().equalsIgnoreCase(fieldsLine.get(i).trim())) {
								log = log + "El encabezado no coincide en el campo: " + (i + 1) + " : se espera |"
										+ header.trim() + "| - se obtuvo |" + fieldsLine.get(i).trim() + "|\n";
								logOK = logOK + (i + 1) + " : " + header.trim() + " - " + fieldsLine.get(i).trim()
										+ " -FAIL \n";
								val = false;
							} else {
								logOK = logOK + (i + 1) + " : " + header.trim() + " - " + fieldsLine.get(i).trim()
										+ " -OK \n";
							}
							i++;
						}
					} catch (Exception e) {
						System.out.println("->" + e + " Fallo la validacion de los encabezados");
					}

					// Si la bandera es falsa, es porque encontr� uno o m�s campos que no coinciden
					// con los esperados.
					if (val) {
						System.out.println("PASSED \n " + "Los encabezados del archivo con correctos:\n" + logOK);
						testCase.addTextEvidenceCurrentStep(
								"RESULTADO: Los encabezados del archivo con correctos: \n" + logOK);
						// testCase.addTextEvidenceCurrentStep(headers);
						testCase.passStep();
					} else {
						System.out.println("FAILED \n" + log + "\n" + logOK + "\n ESPERADO \n" + expectedHeaders);
						throw new AssertionError(log + "\n" + logOK + "\n" + "\n**ESPERADO**\n" + expectedHeaders);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Valida por medio de expresiones regulares que el campo tenga el formato
	 * correspondiente dado el tipo de dato. String, Double, Boolean o String(Enum).
	 * 
	 * @param fieldsLine
	 * @param configFields
	 * @param countLine
	 * @author Esmeralda Garcia
	 */
	private String validationByType(List<String> fieldsLine, String[] configFields, int countLine) {
		String log = "";
		Pattern pat = null;
		Matcher mat = null;
		try {
			int countField = 0;
			String[] configFieldsDetails = null;
			String dataType = null;
			String[] details = null;

			// Revisa campo por campo
			for (String field : fieldsLine) {

				// Valida si las configuración de un dato trae detalles extras
				configFieldsDetails = configFields[countField].split("-");
				if (configFieldsDetails.length > 1) {
					dataType = configFieldsDetails[0];
					details = configFieldsDetails[1].split("\\$");
				} else {
					dataType = configFields[countField];
				}

				pat = null;
				mat = null;

				// Validacion de campos por tipo de dato
				switch (dataType.toUpperCase()) {

				/**
				 * Campo alfanumerico que acepta caracteres especiales. Es obligatorio que el
				 * texto este entre comillas dobles. Puede estar vacío.
				 */
				case "STRING":
					pat = Pattern.compile("^(\"[\\w\\s\"\\p{L}.,-_()+&$'\\\\!%#¿\\?°´\\u0000-\\uFFFF]*\\\"|)$");
					break;

				/**
				 * Puede o no estar entre comillas. Puede ser numero entero, decimal o negativo.
				 * Puede estar vacío.
				 */
				case "DOUBLE":
					pat = Pattern.compile("^\"?(-?\\d+(\\.\\d+)?)?\"?$");
					break;

				/**
				 * Puede ser número entero o decimal. No puede estar vacío
				 */
				case "DOUBLENOVACIO":
					pat = Pattern.compile("^(\\d+(\\.\\d+)?)$");
					break;

				/**
				 * Puede o no estar entre comillas. Puede ser true o false. Puede estar vacío.
				 */
				case "BOOLEAN":
					pat = Pattern.compile("^\"?(?i)(true|false)?\"?$");
					break;

				/**
				 * Puede o no estar entre comillas. Formato YYYYMMDD. Puede estar vacío.
				 */
				case "DATE":
					pat = Pattern.compile("^\"?((\\d{4})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))?\"?$");
					break;

				/**
				 * Puede o no estar entre comillas. Numero entero negativo o positivo. Puede
				 * estar vacío.
				 */
				case "INT":
					pat = Pattern.compile("^(\"?-?\\d+\"?|\"\"|)$");
					break;

				/**
				 * Número entero positivo. No puede estar vacío.
				 */
				case "INTNOVACIO":
					pat = Pattern.compile("^(\\d+)$");
					break;

				/**
				 * Es obligatorio que este entre comillas. Puede ser alguna de las opciones
				 * dadas en los detalles del tipo de dato
				 */
				case "STRING(ENUM)":
					String expReg = "^\"(?i)(";
					// System.out.println("#: " + details.length);
					for (int i = 0; i < details.length - 1; i++) {
						// System.out.println("->: " + details[i]);
						if (details[i] == "") {
							expReg = expReg + "\\\"\\\"";
						} else {
							expReg = expReg + details[i];
						}

						if (i != details.length - 2) {
							expReg = expReg + "|";
						}
					}
					expReg = expReg + ")\"$";

					pat = Pattern.compile(expReg);
					break;

				default:
					throw new AssertionError("No se reconoce la opcion del tipo de dato");
				}

				// Si no hace match la expresión regular y el campo que se está validando se
				// agregan los detalles al log.
				mat = pat.matcher(field);
				if (!mat.find()) {
					log = log + "Falló la línea: " + countLine + " - El campo " + (countField + 1) + " : " + field
							+ " no cumple con el formato de tipo " + configFields[countField] + "\n";
				}
				countField++;
			}
		} catch (Exception e) {
			throw new AssertionError("Falló la validación por tipo en la línea: " + countLine);
		}
		return log;
	}

	/**
	 * El metodo valida si existe un dato (obtenido de un query), en una o varias
	 * lineas del archivo. Y genera una lista con las coincidencias.
	 * 
	 * @param queryResult
	 * @param pathFile
	 * @return result
	 * @author Esmeralda Garcia
	 */
	public List<String> extraccionDatos(SQLResult queryResult, String pathFile) {
		List<String> result = null;
		try {
			BufferedReader file = new BufferedReader(new FileReader(pathFile));
			int i = 0;
			// Se valida cada registro del query
			while (queryResult.getRowCount() > i) {
				// Se obtiene el dato a comparar. Se especifica de qué columna se extraerá.
				String dataSearch = queryResult.getData(i, 1);

				// Se imprime en consola las primeras 10 lineas del archivo, esto es solo
				// seguimiento interno del desarrollo del script.
				if (i < 10) {
					System.out.println(i + ": > " + dataSearch);
				}

				// Se lee linea por linea el archivo
				String txtLine;
				while ((txtLine = file.readLine()) != null) {
					// Valida si dentro de la línea existe el dato a buscar. En caso de que sí,
					// agrega la línea al listado.
					if (txtLine.contains(dataSearch)) {
						result.add(txtLine);

						// Se imprime en consola las primeras 10 lineas del archivo, esto es solo
						// seguimiento interno del desarrollo del script.
						if (i < 10) {
							System.out.println(i + ": |" + txtLine);
						}
					}
				}
				i++;
			}
		} catch (Exception e) {
			System.out.println("-> " + e);
			throw new AssertionError("Algo salio mal al extraer los datos del archivo");
		}
		return result;
	}

	/**
	 * Valida cuantas líneas tiene un archivo.
	 * 
	 * @param pathFile
	 * @author Esmeralda Garcia
	 */
	public void conteoLineas(String pathFile) {
		// List<String> result = null;
		try {
			BufferedReader file = new BufferedReader(new FileReader(pathFile));
			int i = 0;
			Boolean band = false;
			// Lee el archivo linea por l�nea y se aunmenta el contador.
			while (file.readLine() != null) {
				i++;
				if (band) {
					System.out.println("Procesando... " + i);
				} else {
					// Imprime las primeas 20 lineas en consola.
					if (i < 21) {
						System.out.println("-> " + file.readLine());
					}
				}
			}
			// Se le asigna formato al número de resultado.
			DecimalFormat formato = new DecimalFormat("#,###,###");
			String totalLines = formato.format((i));
			testCase.addTextEvidenceCurrentStep("Lineas: " + totalLines);
		} catch (Exception e) {
			System.out.println("-> " + e);
			throw new AssertionError("Algo salió mal al contar las líneas");
		}
	}

}
