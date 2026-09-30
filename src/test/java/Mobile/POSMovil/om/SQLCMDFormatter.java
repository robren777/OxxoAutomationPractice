package Mobile.POSMovil.om;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCMDFormatter {

	/**
	 * Método - Da formato de texto a un ResulSet para mostrarlo en consola
	 * 
	 * @author Marcos Morales
	 * @param rs ResulSet al que se aplica el formato
	 * @date 26/06/2025
	 */
	public static String formatResultSet(ResultSet rs) throws SQLException {
		StringBuilder output = new StringBuilder();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		// 1. Obtener nombres de columnas y determinar el ancho máximo por columna
		List<String> columnNames = new ArrayList<>();
		List<Integer> columnWidths = new ArrayList<>();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = metaData.getColumnName(i);
			columnNames.add(columnName);
			columnWidths.add(columnName.length());
		}

		// 2. Procesar los datos y determinar el ancho real necesario
		List<List<String>> rows = new ArrayList<>();
		while (rs.next()) {
			List<String> row = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				String value = rs.getString(i) == null ? "NULL" : rs.getString(i);
				row.add(value);
				if (value.length() > columnWidths.get(i - 1)) {
					columnWidths.set(i - 1, value.length());
				}
			}
			rows.add(row);
		}

		// 3. Construir la línea de separación
		StringBuilder separatorLine = new StringBuilder("+");
		for (int width : columnWidths) {
			separatorLine.append("-".repeat(width + 2)).append("+");
		}
		separatorLine.append("\n");

		// 4. Construir el encabezado
		output.append(separatorLine);
		output.append("|");
		for (int i = 0; i < columnCount; i++) {
			output.append(" ").append(padRight(columnNames.get(i), columnWidths.get(i))).append(" |");
		}
		output.append("\n");
		output.append(separatorLine);

		// 5. Agregar las filas de datos
		for (List<String> row : rows) {
			output.append("|");
			for (int i = 0; i < columnCount; i++) {
				output.append(" ").append(padRight(row.get(i), columnWidths.get(i))).append(" |");
			}
			output.append("\n");
		}
		output.append(separatorLine);

		return output.toString();
	}

	/**
	 * Método - Rellena espacios a la derecha en caso de requerirse
	 * 
	 * @author Marcos Morales
	 * @param s      Cadena de texto que se evalua
	 * @param length Longitud total deseada para la cadena resultante
	 * @date 26/06/2025
	 */
	private static String padRight(String s, int length) {
		return String.format("%-" + length + "s", s);
	}

}