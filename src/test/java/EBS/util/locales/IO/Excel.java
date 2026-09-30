package EBS.util.locales.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {


	/**
	 * Metodo para creacion de un archivo xls
	 * @param excelFilePath
	 * @param sheetName
	 * @param metaData
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void createExcelFile(String excelFilePath, String sheetName, ResultSetMetaData metaData) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			XSSFSheet sheet = workbook.createSheet(sheetName);

			XSSFRow headerRow = sheet.createRow(0);
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
			}

			workbook.write(fos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para escribir en una fila en un archivo xls
	 * @param file
	 * @param sheetName
	 * @param rowData
	 * @param rowNumber
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void writeRowToExcel(File file, String sheetName, Map<String, String> rowData, int rowNumber) {

		try (FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(file)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException("No se encontro la hoja con el nombre: " + sheetName);
			}

			XSSFRow row = sheet.createRow(rowNumber);
			int columnIndex = 0;

			for (String value : rowData.values()) {
				XSSFCell cell = row.createCell(columnIndex++);
				cell.setCellValue(value != null ? value : "NULL");
			}

			workbook.write(fos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para genera el formato de fecha en columna
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnIndex
	 * @param expectedHeader
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void formatDateColumn(String excelFilePath, String sheetName, int columnIndex,
			String expectedHeader) {

		try (FileInputStream fis = new FileInputStream(excelFilePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			if (sheet == null) {

				throw new RuntimeException("No se encontr� la hoja con el nombre: " + sheetName);

			}

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

			for (int i = 1; 1 <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;

				Cell cell = row.getCell(columnIndex);
				if (cell == null || cell.getCellType() == CellType.BLANK) {
					System.out.println("Se encontr� una celda vacia en la fila " + (i + 1) + "Deteiendo el proceso");
					continue;
				}

				try {
					String dateStr = cell.getStringCellValue().trim();
					Date date = inputFormat.parse(dateStr);
					String formattedDate = outputFormat.format(date);

					cell.setCellValue(formattedDate);

				} catch (Exception e) {
					System.out.println("Error al procesar la fecha en la fila " + (i + 1) + ": " + e.getMessage());
					break;
				}
			}

			workbook.write(fos);
			System.out.println("Las fechas en la columna han sido formateadas correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para limpiar texto nulo en columna en un archivo xls
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnIndex
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void cleanNullTextInColumn(String excelFilePath, String sheetName, int columnIndex) {

		try (FileInputStream fis = new FileInputStream(excelFilePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {

				throw new RuntimeException("No se encontr� la hoja con el nombre: " + sheetName);

			}

			for (int i = 1; 1 <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				if (row == null)
					continue;

				XSSFCell cell = row.getCell(columnIndex);
				if (cell == null) {
					break;
				}

				if (cell.getCellType() == CellType.BLANK || cell.getStringCellValue().trim().isEmpty()) {
					break;
				}

				if ("NULL".equalsIgnoreCase(cell.getStringCellValue().trim())) {
					cell.setCellValue("");

				}

			}
			workbook.write(fos);
			System.out.println("Las celdas con NULL han sido limpiadas");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para eliminar columnas en un archivo xls.
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnIndex
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void deleteColumn(String excelFilePath, String sheetName, int columnIndex) {

		try (FileInputStream fis = new FileInputStream(excelFilePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException("No se encontr� la hoja con el nombre: " + sheetName);
			}

			sheet.forEach(row -> {
				if (row.getCell(columnIndex) != null) {
					row.removeCell(row.getCell(columnIndex));
				}
			});

			workbook.write(fos);
			System.out.println("La columna ha sido eliminada");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * Metodo para generar un renombre de las columnas.
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnMapping
	 * @author Francisco Javier Mendoza Tamayo
	 */
	public static void renameColumns(String excelFilePath, String sheetName, Map<String, String> columnMapping) {

		try (FileInputStream fis = new FileInputStream(excelFilePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {

				throw new RuntimeException("No se encontr� la hoja con el nombre: " + sheetName);

			}

			XSSFRow headerRow = sheet.getRow(0);
			if (headerRow == null) {
				throw new RuntimeException("No se encontr� la fila de encabezado.");
			}

			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				XSSFCell cell = headerRow.getCell(i);
				if (cell != null) {
					String currentHeader = cell.getStringCellValue();
					if (columnMapping.containsKey(currentHeader)) {
						cell.setCellValue(columnMapping.get(currentHeader));
					}
				}
			}

			workbook.write(fos);
			System.out.println("Los encabezados de las columnas han sido renombreados correctamente");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Metodo para generar una comparacion entre 2 archivos xls
	 * @param filePath1
	 * @param sheetName1
	 * @param headerRowFile1
	 * @param filePath2
	 * @param sheetName2
	 * @param headerRowFile2
	 * @author Francisco Javier Mendoza Tamayo
	 */
	public static void compareExcelFiles(String filePath1, String sheetName1, int headerRowFile1, String filePath2,
			String sheetName2, int headerRowFile2) {

		try {

			Workbook workbook1 = loadWorkbook(filePath1);
			Workbook workbook2 = loadWorkbook(filePath2);

			Sheet sheet1 = workbook1.getSheet(sheetName1);
			Sheet sheet2 = workbook2.getSheet(sheetName2);

			if (sheet1 == null || sheet2 == null) {

				throw new RuntimeException("No se encontr� la hoja especificada en uno de los archivos.");

			}

			List<String> headers1 = readHeaders(sheet1, headerRowFile1);
			List<String> headers2 = readHeaders(sheet2, headerRowFile2);

			if (!headers1.equals(headers2)) {
				System.out.println("Los encabezados no coinciden.");
				System.out.println("Encabezados archivo 1: " + headers1);
				System.out.println("Encabezados archivo 2: " + headers2);
				return;
			}

			List<List<String>> data1 = readData(sheet1, headerRowFile1 + 1);
			List<List<String>> data2 = readData(sheet2, headerRowFile2 + 1);

			if (data1.equals(data2)) {
				System.out.println("Los datos en ambos archivos son identicos");

			} else {
				System.out.println("Los datos no coinciden.");
				findDifferences(data1, data2);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para leer los encabezados de las filas
	 * @param sheet
	 * @param headerRowIndex
	 * @return
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static List<String> readHeaders(Sheet sheet, int headerRowIndex) {

		Row headerRow = sheet.getRow(headerRowIndex);
		if (headerRow == null) {
			throw new RuntimeException("No se encontr� la fila de encabezados en la fila: " + headerRowIndex);
		}

		List<String> headers = new ArrayList<>();
		for (Cell cell : headerRow) {
			headers.add(cell.getStringCellValue().trim());
		}
		return headers;
	}


	/**
	 * Metodo para generar la lectura de la informacion
	 * @param sheet
	 * @param startRowIndex
	 * @return data
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static List<List<String>> readData(Sheet sheet, int startRowIndex) {

		List<List<String>> data = new ArrayList<>();
		for (int i = startRowIndex; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;

			List<String> rowData = new ArrayList<>();
			for (Cell cell : row) {
				rowData.add(getCellValue(cell));
			}
			data.add(rowData);
		}
		return data;
	}


	/**
	 * Metodo para obtener el valor de una celda
	 * @param cell
	 * @return String el valor de la celda
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static String getCellValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {

		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			} else {
				return String.valueOf(cell.getNumericCellValue()).trim();
			}
		case BOOLEAN:
			return String.valueOf(cell.getNumericCellValue()).trim();
		default:
			return "";

		}

	}


	/**
	 * Metodo para generar una busqueda de direncias entre las listas de datos
	 * @param data1
	 * @param data2
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static void findDifferences(List<List<String>> data1, List<List<String>> data2) {

		Set<List<String>> set1 = new HashSet<>(data1);
		Set<List<String>> set2 = new HashSet<>(data2);

		Set<List<String>> differences1 = new HashSet<>(set1);
		differences1.removeAll(set2);

		Set<List<String>> differences2 = new HashSet<>(set2);
		differences1.removeAll(set1);

		if (!differences1.isEmpty()) {
			System.out.println("Datos en archivo 1 pero no en archivo 2:");
			for (List<String> diff : differences1) {
				System.out.println(diff);
			}
		}

		if (!differences2.isEmpty()) {
			System.out.println("Datos en archivo 2 pero no en archivo 1:");
			for (List<String> diff : differences2) {
				System.out.println(diff);
			}
		}
	}


	/**
	 * Metodo para cargar libro de trabajo.
	 * @param filePath
	 * @return HSSFWorkbook
	 * @throws Exception
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static Workbook loadWorkbook(String filePath) throws Exception {

		FileInputStream fis = new FileInputStream(new File(filePath));

		if (filePath.endsWith(".xls")) {
			return new HSSFWorkbook(fis);
		} else if (filePath.endsWith(".xlsx")) {
			return new XSSFWorkbook(fis);
		} else {
			throw new IllegalArgumentException("Formato de archivo no soportado: " + filePath);
		}
	}


	/**
	 * Metodo para convertir un archivo xls a un archivo xlsx
	 * @param inputFilePath
	 * @param outputFilePath
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void covertXlsToXlsx(String inputFilePath, String outputFilePath) {

		try {
			Workbook workbookOld = detectAndLoadWorkbook(inputFilePath);

			try (Workbook workbookNew = new XSSFWorkbook();
					FileOutputStream fos = new FileOutputStream(outputFilePath)) {

				for (int i = 0; i < workbookOld.getNumberOfSheets(); i++) {
					Sheet sheetOld = workbookOld.getSheetAt(i);
					Sheet sheetNew = workbookNew.createSheet(sheetOld.getSheetName());
					copySheetContent(sheetOld, sheetNew);
				}
				workbookNew.write(fos);
				System.out.println("El archivo se ha convertido correctamente a formato .xlsx " + outputFilePath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para detectar y cargar un libro de trabajo.
	 * @param filePath
	 * @return HSSFWorkbook
	 * @throws Exception
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static Workbook detectAndLoadWorkbook(String filePath) throws Exception {

		File file = new File(filePath);

		if (!file.exists()) {

			throw new FileNotFoundException("El archivo no se encontr�:" + filePath);

		}

		try (FileInputStream fis = new FileInputStream(file)) {

			try {
				return new HSSFWorkbook(fis);

			} catch (Exception e) {
				System.out.println("El archivo no es un .xls v�lido");
			}

			try (FileInputStream fis2 = new FileInputStream(file)) {
				return new XSSFWorkbook(fis2);

			} catch (Exception e) {
				throw new IllegalArgumentException(

						"El archivo no es un archivo Excel v�lido o est� da�ado: " + filePath, e);

			}
		}
	}


	/**
	 * Metodo para buscar un nombre parcial de un archivo
	 * @param partialName
	 * @param directoryPath
	 * @return null
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static String findFilePartialName(String partialName, String directoryPath) {
		File dir = new File(directoryPath);
		if (!dir.isDirectory()) {
			System.out.println("La ruta especificada no es un directorio");
			return null;
		}

		File[] files = dir.listFiles((dir1, name) -> name.contains(partialName) && name.endsWith(".xls"));
		if (files != null && files.length > 0) {
			return files[0].getAbsolutePath();
		}
		return null;
	}

	/**
	 * Metodo para convertir un archivo a otro nuevo.
	 * @param inputFilePath
	 * @param outputFilePath
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static void convertFile(String inputFilePath, String outputFilePath) {

		try (FileInputStream fis = new FileInputStream(inputFilePath);
				Workbook workbookOld = new HSSFWorkbook(fis);
				Workbook workbookNew = new XSSFWorkbook();
				FileOutputStream fos = new FileOutputStream(outputFilePath)) {

			for (int i = 0; i < workbookOld.getNumberOfSheets(); i++) {
				Sheet sheetOld = workbookOld.getSheetAt(i);
				Sheet sheetNew = workbookNew.createSheet(sheetOld.getSheetName());
				copySheetContent(sheetOld, sheetNew);
			}

			workbookNew.write(fos);
			System.out.println("El archivo se ha convertido correctamente a formato .xlsx:" + outputFilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para copiar el contenido de una hoja
	 * @param sheetOld
	 * @param sheetNew
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static void copySheetContent(Sheet sheetOld, Sheet sheetNew) {
		for (int i = 0; i <= sheetOld.getLastRowNum(); i++) {
			Row rowOld = sheetOld.getRow(i);
			if (rowOld == null)
				continue;

			Row rowNew = sheetNew.createRow(i);

			for (int j = 0; j < rowOld.getLastCellNum(); j++) {
				Cell cellOld = rowOld.getCell(j);
				if (cellOld == null)
					continue;

				Cell cellNew = rowNew.createCell(j);
				copyCellValue(cellOld, cellNew);
			}
		}

	}


	/**
	 * Metodo para copiar el valor de una celda.
	 * @param cellOld
	 * @param cellNew
	 * @author Francisco Javier Mendoza Tamayo
	 */

	private static void copyCellValue(Cell cellOld, Cell cellNew) {

		switch (cellOld.getCellType()) {
		case STRING:
			cellNew.setCellValue(cellOld.getStringCellValue());
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cellOld)) {
				cellNew.setCellValue(cellOld.getDateCellValue());
			} else {
				cellNew.setCellValue(cellOld.getNumericCellValue());
			}
			break;
		case BOOLEAN:
			cellNew.setCellValue(cellOld.getBooleanCellValue());
			break;
		case FORMULA:
			cellNew.setCellFormula(cellOld.getCellFormula());
			break;
		case BLANK:
			cellNew.setBlank();
			break;
		default:
			cellNew.setCellValue(cellOld.toString());
		}
	}


	/**
	 * Metodo para obtener el formato de fecha por DDMMYY obteniendo solo el numero de fecha sin caracteres especiales y año solo 2 cifras.
	 * @return El valor de la fecha en formato cadena
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static String getFormattedDate() {
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		return formatter.format(currentDate);
	}


	/**
	 * Metodo para buscar un dato en especifico dentro de un archivo xls.
	 * @param filePath
	 * @param data
	 * @return false/true
	 * @throws IOException
	 * @author Francisco Javier Mendoza Tamayo
	 * @Mejora sobre el proceso de busqueda
	 * @author David Quiroz Rodriguez
	 */

	public static boolean searchDataExcel(String filePath, String data) throws IOException {
		File file = new File(filePath);

		if (!file.exists() || !file.canRead()) {
			System.err.println("El archivo no existe o no se puede leer: " + filePath);
			return false;
		}

		StringBuilder contentBuilder = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				contentBuilder.append(line).append(System.lineSeparator());
			}

		} catch (IOException e) {
			System.err.println("Error al leer el archivo: " + filePath);
			e.printStackTrace();
			return false;

		}

		String htmlContent = contentBuilder.toString();
		if (htmlContent.contains(data)){
			System.out.println("El valor "+ data+" se encuentra en el archivo");
			return true;
		}
		else {
			System.out.println("El valor que estas buscando "+data+" no existe en el documento revisa tu valor agregado");
			return false;
		}

    }

	/**
	 * Metodo para validar la fecha del archivo xls.
	 * @param excelFilePath
	 * @param validationFilePath
	 * @return
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static boolean validateExcelDate(String excelFilePath, String validationFilePath) {

		boolean allValidationsPassed = true;

		try {

			File file = new File(excelFilePath);
			if (!file.exists() || !file.canRead()) {
				System.out.println("El archivo no existe o no se puede leer" + excelFilePath);
				return false;
			}

			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);

			Sheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {

				System.err.println("No se encontro ninguna hoja en el archivo Excel.");

				return false;
			}

			for (Row row : sheet) {
				for (Cell cell : row) {
					String cellValue = getCellValue(cell);

					if (!cellValue.isEmpty()) {
						boolean exists = searchDataExcel(validationFilePath, cellValue);
						if (exists) {

							System.out.println("El valor " + cellValue + " existe en el archivo de validaci�n");
						} else {
							System.err.println("El valor " + cellValue + " No existe en el archivo de validaci�n");

							allValidationsPassed = false;
						}
					}
				}
			}
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return allValidationsPassed;
	}


	/**
	 * Metodo para generar el formato numerico en columna
	 * @param excelFilaePath
	 * @param sheetName
	 * @param columnIndex
	 * @author Francisco Javier Mendoza Tamayo
	 */

	@SuppressWarnings("deprecation")
	public static void formatNumericColumn(String excelFilaePath, String sheetName, int columnIndex) {
		try (FileInputStream fis = new FileInputStream(excelFilaePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilaePath)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			if (sheet == null) {

				throw new RuntimeException("La hoja especificada no se encontr�: " + sheetName);

			}

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				if (row == null)
					continue;

				Cell cell = row.getCell(columnIndex);

				if (cell == null || cell.getCellType() == CellType.BLANK) {

					System.out.println("Se encontro una celda vac�a en la fila" + (i + 1) + "...Omitiendo");

					continue;
				}

				String cellValue = cell.toString().trim();

				try {
					double numericValue = Double.parseDouble(cellValue.replace(",", ""));
					String formmatedValue = decimalFormat.format(numericValue);

					cell.setCellType(CellType.STRING);
					cell.setCellValue(formmatedValue);
				} catch (NumberFormatException e) {
					System.out.println("El valor en la fila " + (i + 1) + "...no es numerico " + cellValue);
				}

			}

			workbook.write(fos);
			System.out.println("Formato numerico aplicado correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * Metodo para generar el formato de fecha a mayusculas
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnIndex
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void formatMonthToUppercase(String excelFilePath, String sheetName, int columnIndex) {

		try (FileInputStream fis = new FileInputStream(excelFilePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			if (sheet == null) {

				throw new RuntimeException("No se encontr� la hoja con el nombre: " + sheetName);

			}

			SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				if (row == null)
					continue;

				Cell cell = row.getCell(columnIndex);

				if (cell == null || cell.getCellType() == CellType.BLANK) {


					System.out.println("Se encontro una celda vac�a en la fila" + (i + 1) + "...Omitiendo");

					continue;
				}

				try {
					String dateStr = cell.getStringCellValue().trim();
					Date date = inputFormat.parse(dateStr);

					String formattedDate = outputFormat.format(date);
					cell.setCellValue(formattedDate);

				} catch (ParseException e) {
					System.err.println("Error al procesar la fecha en la fila " + (i + 1) + e.getMessage());
				}
			}
			workbook.write(fos);

			System.out.println("Formato del mes convertido a may�sculas");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metodo para añadir espacios en blanco a una columna
	 * @param excelFilePath
	 * @param sheetName
	 * @param columnIndex
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void addNbspToColumn(String excelFilePath, String sheetName, int columnIndex) {
		try (FileInputStream fis = new FileInputStream(excelFilePath);
				Workbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			Sheet sheet = workbook.getSheet(sheetName);

			for (int i = 0; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Cell cell = row.getCell(columnIndex);
				if (cell == null || cell.getCellType() != CellType.STRING)
					continue;

				String cellValue = cell.getStringCellValue();
				if (cellValue.contains("  ")) {
					String updatedValue = cellValue.replace("  ", "&nbsp; ");
					cell.setCellValue(updatedValue);
				}
			}
			workbook.write(fos);
			System.out.println("El archivo ha sido actualizado");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Erro procesando el archivo " + e.getMessage());

		}
	}


	/**
	 * Metodo para eliminar la primera fila.
	 * @param excelFilePath
	 * @param sheetName
	 * @author Francisco Javier Mendoza Tamayo
	 */

	public static void deleteFirstRow(String excelFilePath, String sheetName) {
		try (FileInputStream fis = new FileInputStream(excelFilePath);
				Workbook workbook = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(excelFilePath)) {

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new RuntimeException("No se encontro la hoja con el nombre: " + sheetName);
			}

			if (sheet.getLastRowNum() > 0) {
				sheet.shiftRows(1, sheet.getLastRowNum(), -1);
			} else {
				System.err.println("La hoja no tiene filas para eliminar");
			}

			workbook.write(fos);
			System.out.println("La primera fila ha sido eliminada");

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error procesando el archivo" + e.getMessage());
		}
	}


	/**
	 * Metodo para convertir el formato de fecha de dd-mm-yyyy a yyyy/mm/dd
	 * @param actualDate
	 * @return
	 * @author Francisco Javier Mendoza Tamayo
	 
	public static String convertDateFormat(String actualDate) {
		try {
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		Date date = inputFormat.parse(actualDate);
		
		return outputFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	 */
	/**
	 * Metodo para convertir el formato de fecha de dd-mm-yyyy a yyyy/mm/dd
	 *
	 * @param actualDate
	 * @return
	 * @author Francisco Javier Mendoza Tamayo
	 * @Actualizacion David Quiroz Rodriguez
	 */
	public static String convertDateFormat(String actualDate) {
		String fechafinal="";
		try{
			String mesABuscar = actualDate.toUpperCase().replaceAll("[^a-z, A-Z]", "");
			String[] MesesEspaniol = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
			String[] MesesING = {"JAN", "APR", "AUG", "DEC"};
			for (String mesesEsp : Arrays.asList(MesesEspaniol)) {
				if (mesesEsp.equals(mesABuscar)) {
					SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
					SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", new Locale("es", "ES"));
					Date date = inputFormat.parse(actualDate);
					fechafinal=outputFormat.format(date);
				}
			}
			for (String mesesIng : Arrays.asList(MesesING)) {
				if (mesesIng.equals(mesABuscar)) {
					SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
					SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
					Date date = inputFormat.parse(actualDate);
					fechafinal=outputFormat.format(date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  fechafinal;
	}

	/**
	 * Metodo para convertir el formato de fecha dd-mmm-yyyy a dd/mm/yyyy
	 * @param actualDate
	 * @return
	 * @author David Quiroz Rodriguez
	 */
	public static String convertDataFormatDDMMAAAA(String actualDate){
		try{
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = inputFormat.parse(actualDate);
			return outputFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	/**
	 * Metodo para eliminar un archivo en la ruta que se le indique
	 * @param path
	 * @return true/false
	 * @author Francisco Javier Mendoza Tamayo
	 */
	public static boolean deleteFile(String path) {
		File file = new File(path);
		if(!file.exists()) {
			System.out.println("El archivo no existe: " + path);
			return false;
		}
		
		if(file.delete()) {
			System.out.println("Archivo eliminado exitosamente: " + path);
			return true;
		}else {
			System.out.println("No se pudo eliminar el archivo: " + path);
			return false;
		}
	}


	/**
	 * Metodo para extraer el valor de una celda de un archivo XLSX o XLS donde le indicas la fila y columna de donde obtenerlo
	 * @param filePath
	 * @param rowIndex
	 * @param columIndex
	 * @param nameSheet
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void getTheSpecificValueOfAnExcelCell(String filePath, int rowIndex, int columIndex, String nameSheet) throws IOException, InvalidFormatException {
		String path = filePath;
		File file = new File(path);
		String extension = FilenameUtils.getExtension(file.getName());
		System.out.println(extension);
		Iterator<Row> rows;
		if (extension.equals("xlsx") || extension.equals("xls")) {
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheet(nameSheet);
			rows = sheet.rowIterator();
		} else {
			HSSFWorkbook wb = new HSSFWorkbook(POIFSFileSystem.create(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			rows = sheet.iterator();
		}
		List<Row> list = getRows(rows);//guarda las filas en una lista
		System.out.println(valueCells(list, rowIndex, columIndex));
	}

	/**
	 * Metodo para obtener las rows de un documento de excel
	 * @param rows
	 * @return
	 * @author David Quiroz
	 */
	public static List<Row> getRows(Iterator<Row> rows) {
		XSSFRow row;
		List<Row> list = new ArrayList<>();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			list.add(row);
		}
		return list;
	}

	/**
	 * Metodo para obtener los valores de una celda de un archivo excel
	 * @param rows
	 * @param numRow
	 * @param numColumn
	 * @return
	 * @author David Quiroz Rodriguez
	 */
	public static String valueCells(List<Row> rows, int numRow, int numColumn) {
		String result = "";
		Row row = rows.get(numRow);//obtiene la fila especifica
		if (row != null) {
			Iterator<Cell> cells = row.cellIterator();
			while (cells.hasNext()) {
				XSSFCell cel = (XSSFCell) cells.next();
				if (cel.getColumnIndex() == numColumn) {//obtiene la celda especifica
					if (cel.getCellType() != CellType.NUMERIC) { //si el valor es numerico
						result = cel.getStringCellValue();
					} else {
						int num = (int) cel.getNumericCellValue();
						result = Integer.toString(num);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Metodo para convertir el formato de fecha dd-mmm-yyyy a dd/mm/yyyy
	 * @param actualDate
	 * @return
	 * @author David Quiroz Rodriguez
	 */
	public static String convertDataFormatDDMMAAAAConGionMedio(String actualDate){
		try{
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date date = inputFormat.parse(actualDate);
			return outputFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public static boolean validateCellByCoordinates(String excelFilePath, int sheetIndex, int rowIndex, int cellIndex, String value) {
		//String excelFilePath = "ruta/a/tu/archivo.xlsx";
		//sheetIndex, Indice de la hoja (0 para la primera hoja)
		//rowIndex Indice de la fila (0 para la primer fila)
		//cellIndex Indice de la columna (0 para primer columna)
		boolean result=false;

		try (FileInputStream fis = new FileInputStream(excelFilePath);
				Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Row row = sheet.getRow(rowIndex);
			Cell cell = row.getCell(cellIndex);
			String cellValue = cell.getStringCellValue();
			if (value.equals(cellValue)) {
				System.out.println("La celda contiene el valor específico");
				return true;
			} else {
				System.out.println("La celda no contiene el valor específico");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<String> validateExcelDateReturnValues(String excelFilePath, String validationFilePath) {

		ArrayList<String> dataValidation = new ArrayList<>();

		try {

			File file = new File(excelFilePath);
			if (!file.exists() || !file.canRead()) {
				System.out.println("El archivo no existe o no se puede leer" + excelFilePath);
				dataValidation.add("El archivo no existe o no se puede leer" + excelFilePath);
				return dataValidation;
			}

			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);

			Sheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				System.err.println("No se encontr� ninguna hoja en el archivo Excel.");
				dataValidation.add("No se encontr� ninguna hoja en el archivo Excel.");
				return dataValidation;
			}

			for (Row row : sheet) {
				for (Cell cell : row) {
					String cellValue = getCellValue(cell);

					if (!cellValue.isEmpty()) {
						boolean exists = searchDataExcel(validationFilePath, cellValue);
						if (exists) {
							System.out.println("El valor " + cellValue + " existe en el archivo de validacion");
							dataValidation.add("El valor " + cellValue + " existe en el archivo de validacion");
							
						} else {
							System.err.println("El valor " + cellValue + " No existe en el archivo de validacion");
							dataValidation.add("El valor " + cellValue + " No existe en el archivo de validacion");
						}
					}
				}
			}
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataValidation;
	}
}

