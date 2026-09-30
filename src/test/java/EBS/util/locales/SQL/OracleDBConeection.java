package EBS.util.locales.SQL;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import EBS.util.locales.IO.Excel;

public class OracleDBConeection {

	public static boolean extractingInformationFromTheDatabase(String username, String password, String excelFilePath, String sheetName, String startDate, String endDate, String initialIndicator) throws IOException {

		String url = "jdbc:oracle:thin:@//10.184.48.60:1532/AVEBQA";
		String query = "SELECT   s.lote,\r\n"
				+ "                                       s.xxfc_fecha_administrativa f_admin,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_date\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END f_fact,\r\n"
				+ "                                       s.plaza cr,\r\n"
				+ "                                       s.servicio item,\r\n"
				+ "                                       xit.SUP_NAME proveedor,\r\n"
				+ "                                       aps.segment1 rfc,\r\n"
				+ "                                       aps.vendor_name razon_soc,\r\n"
				+ "                                       count(*) num_transacciones,\r\n"
				+ "                                       SUM(s.valor) monto_transacciones,\r\n"
				+ "                                       CASE WHEN xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          '$ '||xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          xit.comision_oxxo||' %'\r\n"
				+ "                                       END comision,\r\n"
				+ "                                       CASE WHEN  xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          count(*) * xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          ROUND(SUM(s.valor) * (xit.comision_oxxo/100),2)\r\n"
				+ "                                       END monto_factura,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             'N/A'\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_number\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END nro_factura,\r\n"
				+ "                                       xit.sup_name  FROM    apps.xxfc_pago_servicios         s,\r\n"
				+ "                                 apps.xxfc_retek_item_servicios_v xit,\r\n"
				+ "                                 apps.ap_suppliers                aps,\r\n"
				+ "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\r\n"
				+ "                           AND    s.servicio = xit.item\r\n"
				+ "                           AND    aps.vendor_id = apss.vendor_id\r\n"
				+ "                           AND    s.plaza = xit.site\r\n"
				+ "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\r\n"
				+ "                           AND    apss.vendor_site_code = xit.site  AND (s.factura_ar IS NULL\r\n"
				+ "                                                    OR s.factura_ar = 'P'\r\n"
				+ "                                                    OR s.factura_ar = 'N/A')  AND s.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + startDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + endDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + initialIndicator + "' GROUP BY s.lote,\r\n"
				+ "                                                 s.xxfc_fecha_administrativa,\r\n"
				+ "                                                 s.plaza,\r\n"
				+ "                                                 s.servicio,\r\n"
				+ "                                                 xit.SUP_NAME,\r\n"
				+ "                                                 aps.segment1,\r\n"
				+ "                                                 aps.vendor_name,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 s.factura_ar,\r\n"
				+ "                                                 xit.sup_name,\r\n"
				+ "                                                 aps.vendor_id,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 xit.porc_cantidad";

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			File excelFile = new File(excelFilePath);
			if (!excelFile.exists()) {
				Excel.createExcelFile(excelFilePath, sheetName, metaData);

			}

			int rowNumber = 1;
			while (resultSet.next()) {

				Map<String, String> rowData = new LinkedHashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columnValue = resultSet.getString(columnName);
					rowData.put(columnName, columnValue);
				}

				System.out.println("Procesando fila:");
				for (Map.Entry<String, String> entry : rowData.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}

				Excel.writeRowToExcel(excelFile, sheetName, rowData, rowNumber);
				rowNumber++;
			}
			System.out.println("Datos escritos en el archivo Excel: " + excelFilePath);
			return true;

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean extractingInformationFromTheDatabaseInvoiced(String username, String password, String excelFilePath, String sheetName, String startDate, String endDate, String initialIndicator) throws IOException {

		String url = "jdbc:oracle:thin:@//10.184.48.60:1532/AVEBQA";
		String query = "SELECT   s.lote,\r\n"
				+ "                                       s.xxfc_fecha_administrativa f_admin,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_date\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END f_fact,\r\n"
				+ "                                       s.plaza cr,\r\n"
				+ "                                       s.servicio item,\r\n"
				+ "                                       xit.SUP_NAME proveedor,\r\n"
				+ "                                       aps.segment1 rfc,\r\n"
				+ "                                       aps.vendor_name razon_soc,\r\n"
				+ "                                       count(*) num_transacciones,\r\n"
				+ "                                       SUM(s.valor) monto_transacciones,\r\n"
				+ "                                       CASE WHEN xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          '$ '||xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          xit.comision_oxxo||' %'\r\n"
				+ "                                       END comision,\r\n"
				+ "                                       CASE WHEN  xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          count(*) * xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          ROUND(SUM(s.valor) * (xit.comision_oxxo/100),2)\r\n"
				+ "                                       END monto_factura,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             'N/A'\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_number\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END nro_factura,\r\n"
				+ "                                       xit.sup_name  FROM    apps.xxfc_pago_servicios         s,\r\n"
				+ "                                 apps.xxfc_retek_item_servicios_v xit,\r\n"
				+ "                                 apps.ap_suppliers                aps,\r\n"
				+ "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\r\n"
				+ "                           AND    s.servicio = xit.item\r\n"
				+ "                           AND    aps.vendor_id = apss.vendor_id\r\n"
				+ "                           AND    s.plaza = xit.site\r\n"
				+ "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\r\n"
				+ "                           AND    apss.vendor_site_code = xit.site  AND s.factura_ar IS NOT NULL\r\n"
				+ "                                               AND s.factura_ar <> 'P'\r\n"
				+ "                                               AND s.factura_ar <> 'N/A'  AND s.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + startDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + endDate +" 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + initialIndicator + "' GROUP BY s.lote,\r\n"
				+ "                                                 s.xxfc_fecha_administrativa,\r\n"
				+ "                                                 s.plaza,\r\n"
				+ "                                                 s.servicio,\r\n"
				+ "                                                 xit.SUP_NAME,\r\n"
				+ "                                                 aps.segment1,\r\n"
				+ "                                                 aps.vendor_name,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 s.factura_ar,\r\n"
				+ "                                                 xit.sup_name,\r\n"
				+ "                                                 aps.vendor_id,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 xit.porc_cantidad";

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			File excelFile = new File(excelFilePath);
			if (!excelFile.exists()) {
				Excel.createExcelFile(excelFilePath, sheetName, metaData);

			}

			int rowNumber = 1;
			while (resultSet.next()) {

				Map<String, String> rowData = new LinkedHashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columnValue = resultSet.getString(columnName);
					rowData.put(columnName, columnValue);
				}

				System.out.println("Procesando fila:");
				for (Map.Entry<String, String> entry : rowData.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}

				Excel.writeRowToExcel(excelFile, sheetName, rowData, rowNumber);
				rowNumber++;
			}
			System.out.println("Datos escritos en el archivo Excel: " + excelFilePath);
			return true;

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean extractingInformationFromTheDatabaseDates(String username, String password, String excelFilePath, String sheetName, String startDate, String endDate, String initialIndicator) throws IOException {

		String url = "jdbc:oracle:thin:@//10.184.48.60:1532/AVEBQA";
		String query = "SELECT   s.lote,\r\n"
				+ "                                       s.xxfc_fecha_administrativa f_admin,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_date\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END f_fact,\r\n"
				+ "                                       s.plaza cr,\r\n"
				+ "                                       s.servicio item,\r\n"
				+ "                                       xit.SUP_NAME proveedor,\r\n"
				+ "                                       aps.segment1 rfc,\r\n"
				+ "                                       aps.vendor_name razon_soc,\r\n"
				+ "                                       count(*) num_transacciones,\r\n"
				+ "                                       SUM(s.valor) monto_transacciones,\r\n"
				+ "                                       CASE WHEN xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          '$ '||xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          xit.comision_oxxo||' %'\r\n"
				+ "                                       END comision,\r\n"
				+ "                                       CASE WHEN  xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          count(*) * xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          ROUND(SUM(s.valor) * (xit.comision_oxxo/100),2)\r\n"
				+ "                                       END monto_factura,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             'N/A'\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_number\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END nro_factura,\r\n"
				+ "                                       xit.sup_name  FROM    apps.xxfc_pago_servicios         s,\r\n"
				+ "                                 apps.xxfc_retek_item_servicios_v xit,\r\n"
				+ "                                 apps.ap_suppliers                aps,\r\n"
				+ "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\r\n"
				+ "                           AND    s.servicio = xit.item\r\n"
				+ "                           AND    aps.vendor_id = apss.vendor_id\r\n"
				+ "                           AND    s.plaza = xit.site\r\n"
				+ "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\r\n"
				+ "                           AND    apss.vendor_site_code = xit.site  AND s.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + startDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + endDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + initialIndicator + "' GROUP BY s.lote,\r\n"
				+ "                                                 s.xxfc_fecha_administrativa,\r\n"
				+ "                                                 s.plaza,\r\n"
				+ "                                                 s.servicio,\r\n"
				+ "                                                 xit.SUP_NAME,\r\n"
				+ "                                                 aps.segment1,\r\n"
				+ "                                                 aps.vendor_name,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 s.factura_ar,\r\n"
				+ "                                                 xit.sup_name,\r\n"
				+ "                                                 aps.vendor_id,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 xit.porc_cantidad";

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			File excelFile = new File(excelFilePath);
			if (!excelFile.exists()) {
				Excel.createExcelFile(excelFilePath, sheetName, metaData);

			}

			int rowNumber = 1;
			while (resultSet.next()) {

				Map<String, String> rowData = new LinkedHashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columnValue = resultSet.getString(columnName);
					rowData.put(columnName, columnValue);
				}

				System.out.println("Procesando fila:");
				for (Map.Entry<String, String> entry : rowData.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}

				Excel.writeRowToExcel(excelFile, sheetName, rowData, rowNumber);
				rowNumber++;
			}
			System.out.println("Datos escritos en el archivo Excel: " + excelFilePath);
			return true;

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean extractingInformationFromTheDatabaseRFC(String username, String password, String excelFilePath, String sheetName, String startDate, String endDate, String initialIndicator) throws IOException {

		String url = "jdbc:oracle:thin:@//10.184.48.60:1532/AVEBQA";
		String query = "SELECT   s.lote,\r\n"
				+ "                                       s.xxfc_fecha_administrativa f_admin,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_date\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END f_fact,\r\n"
				+ "                                       s.plaza cr,\r\n"
				+ "                                       s.servicio item,\r\n"
				+ "                                       xit.SUP_NAME proveedor,\r\n"
				+ "                                       aps.segment1 rfc,\r\n"
				+ "                                       aps.vendor_name razon_soc,\r\n"
				+ "                                       count(*) num_transacciones,\r\n"
				+ "                                       SUM(s.valor) monto_transacciones,\r\n"
				+ "                                       CASE WHEN xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          '$ '||xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          xit.comision_oxxo||' %'\r\n"
				+ "                                       END comision,\r\n"
				+ "                                       CASE WHEN  xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          count(*) * xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          ROUND(SUM(s.valor) * (xit.comision_oxxo/100),2)\r\n"
				+ "                                       END monto_factura,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             'N/A'\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_number\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END nro_factura,\r\n"
				+ "                                       xit.sup_name  FROM    apps.xxfc_pago_servicios         s,\r\n"
				+ "                                 apps.xxfc_retek_item_servicios_v xit,\r\n"
				+ "                                 apps.ap_suppliers                aps,\r\n"
				+ "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\r\n"
				+ "                           AND    s.servicio = xit.item\r\n"
				+ "                           AND    aps.vendor_id = apss.vendor_id\r\n"
				+ "                           AND    s.plaza = xit.site\r\n"
				+ "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\r\n"
				+ "                           AND    apss.vendor_site_code = xit.site  AND apss.vendor_site_code_alt = 61888  AND s.factura_ar IS NOT NULL\r\n"
				+ "                                               AND s.factura_ar <> 'P'\r\n"
				+ "                                               AND s.factura_ar <> 'N/A'  AND s.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + startDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + endDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + initialIndicator + "' GROUP BY s.lote,\r\n"
				+ "                                                 s.xxfc_fecha_administrativa,\r\n"
				+ "                                                 s.plaza,\r\n"
				+ "                                                 s.servicio,\r\n"
				+ "                                                 xit.SUP_NAME,\r\n"
				+ "                                                 aps.segment1,\r\n"
				+ "                                                 aps.vendor_name,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 s.factura_ar,\r\n"
				+ "                                                 xit.sup_name,\r\n"
				+ "                                                 aps.vendor_id,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 xit.porc_cantidad";

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			File excelFile = new File(excelFilePath);
			if (!excelFile.exists()) {
				Excel.createExcelFile(excelFilePath, sheetName, metaData);

			}

			int rowNumber = 1;
			while (resultSet.next()) {

				Map<String, String> rowData = new LinkedHashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columnValue = resultSet.getString(columnName);
					rowData.put(columnName, columnValue);
				}

				System.out.println("Procesando fila:");
				for (Map.Entry<String, String> entry : rowData.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}

				Excel.writeRowToExcel(excelFile, sheetName, rowData, rowNumber);
				rowNumber++;
			}
			System.out.println("Datos escritos en el archivo Excel: " + excelFilePath);
			return true;

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean extractingInformationFromTheDatabaseExecutionGroup(String username, String password, String excelFilePath, String sheetName, String startDate, String endDate, String initialIndicator) throws IOException {

		String url = "jdbc:oracle:thin:@//10.184.48.60:1532/AVEBQA";
		String query = "SELECT   s.lote,\r\n"
				+ "                                       s.xxfc_fecha_administrativa f_admin,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_date\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END f_fact,\r\n"
				+ "                                       s.plaza cr,\r\n"
				+ "                                       s.servicio item,\r\n"
				+ "                                       xit.SUP_NAME proveedor,\r\n"
				+ "                                       aps.segment1 rfc,\r\n"
				+ "                                       aps.vendor_name razon_soc,\r\n"
				+ "                                       count(*) num_transacciones,\r\n"
				+ "                                       SUM(s.valor) monto_transacciones,\r\n"
				+ "                                       CASE WHEN xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          '$ '||xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          xit.comision_oxxo||' %'\r\n"
				+ "                                       END comision,\r\n"
				+ "                                       CASE WHEN  xit.porc_cantidad = '$' THEN\r\n"
				+ "                                          count(*) * xit.comision_oxxo\r\n"
				+ "                                       ELSE\r\n"
				+ "                                          ROUND(SUM(s.valor) * (xit.comision_oxxo/100),2)\r\n"
				+ "                                       END monto_factura,\r\n"
				+ "                                       CASE WHEN s.factura_ar = 'N/A' THEN\r\n"
				+ "                                             'N/A'\r\n"
				+ "                                          WHEN s.factura_ar = 'P' THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          WHEN s.factura_ar IS NULL THEN\r\n"
				+ "                                             NULL\r\n"
				+ "                                          ELSE\r\n"
				+ "                                                (SELECT trx.trx_number\r\n"
				+ "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\r\n"
				+ "                                                WHERE trx.CT_REFERENCE = s.factura_ar\r\n"
				+ "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\r\n"
				+ "                                                AND ROWNUM = 1)\r\n"
				+ "                                       END nro_factura,\r\n"
				+ "                                       xit.sup_name  FROM    apps.xxfc_pago_servicios         s,\r\n"
				+ "                                 apps.xxfc_retek_item_servicios_v xit,\r\n"
				+ "                                 apps.ap_suppliers                aps,\r\n"
				+ "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\r\n"
				+ "                           AND    s.servicio = xit.item\r\n"
				+ "                           AND    aps.vendor_id = apss.vendor_id\r\n"
				+ "                           AND    s.plaza = xit.site\r\n"
				+ "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\r\n"
				+ "                           AND    apss.vendor_site_code = xit.site  AND s.factura_ar IS NOT NULL\r\n"
				+ "                                               AND s.factura_ar <> 'P'\r\n"
				+ "                                               AND s.factura_ar <> 'N/A'  AND s.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + startDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + endDate + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + initialIndicator + "'  AND EXISTS (SELECT 1\r\n"
				+ "                                                           FROM xxfc_ar_gpos_ejec_snc_all xgs\r\n"
				+ "                                                           WHERE id_grupo = TO_NUMBER(1992224) AND xgs.servicio = s.servicio  UNION ALL  SELECT 1  FROM  xxfc_ar_gpos_ejec_snc_all xgs  WHERE xgs.rfc = xit.supplier||''  AND   xgs.id_grupo = TO_NUMBER(1992224)) GROUP BY s.lote,\r\n"
				+ "                                                 s.xxfc_fecha_administrativa,\r\n"
				+ "                                                 s.plaza,\r\n"
				+ "                                                 s.servicio,\r\n"
				+ "                                                 xit.SUP_NAME,\r\n"
				+ "                                                 aps.segment1,\r\n"
				+ "                                                 aps.vendor_name,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 s.factura_ar,\r\n"
				+ "                                                 xit.sup_name,\r\n"
				+ "                                                 aps.vendor_id,\r\n"
				+ "                                                 xit.comision_oxxo,\r\n"
				+ "                                                 xit.porc_cantidad";

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			File excelFile = new File(excelFilePath);
			if (!excelFile.exists()) {
				Excel.createExcelFile(excelFilePath, sheetName, metaData);

			}

			int rowNumber = 1;
			while (resultSet.next()) {

				Map<String, String> rowData = new LinkedHashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columnValue = resultSet.getString(columnName);
					rowData.put(columnName, columnValue);
				}

				System.out.println("Procesando fila:");
				for (Map.Entry<String, String> entry : rowData.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}

				Excel.writeRowToExcel(excelFile, sheetName, rowData, rowNumber);
				rowNumber++;
			}
			System.out.println("Datos escritos en el archivo Excel: " + excelFilePath);
			return true;

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
			return false;
		}
	}
}
