package EBS.util.neteos.DBConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import EBS.util.GlobalVariables;
import EBS.util.locales.IO.Excel;
import utils.sql.SQLResult;

public class OracleDBConnection {

	public static boolean extractingInformationFromTheDatabase_startEndDate_startEndRfc(String host, String username, String password, String excelFilePath, String sheetName, String startDate, String endDate, String startRfc, String endRfc) throws IOException {

		String url = "jdbc:oracle:thin:@//"+host;
		String query = "SELECT   /*+ MATERIALIZE  */ --12 DIC 2024\n"
				+ "        Aps.Segment1 Rfc\n"
				+ "      , Aps.Vendor_Name Razon_Social\n"
				+ "      /*, apss.vendor_site_Code_alt id_socio\n*/"
				+ "      , xps.trans_id\n"
				+ "			, xps.plaza\n"
				+ "			, xps.tienda\n"
				+ "			, xps.folio_transaccion\n"
				+ "			, xps.servicio\n"
				+ "			, TO_CHAR(xps.fecha_transaccion,'dd-mon-yyyy') fecha_transaccion\n"
				+ "			, xps.valor\n"
				+ "			, xps.comision\n"
				+ "			, TO_CHAR(xps.fecha_transmision,'dd-mon-yyyy')	fecha_transmision\n"
				+ "			, NVL(xps.atributo1,xps.factura) factura\n"
				+ "			, TO_CHAR(api.fecha_de_pago_ap,'dd-mon-yyyy') fecha_de_pago_ap\n"
				+ "			, xps.gl\n"
				+ "			, xps.poliza_os\n"
				+ "			, xps.carta_femsa\n"
				+ " 			, xps.folio_wm\n"
				+ "        FROM  apps.xxfc_pago_servicios xps\n"
				+ "            , apps.ap_suppliers aps\n"
				+ "            , apps.ap_supplier_sites_all apss\n"
				+ "            , apps.xxfc_retek_item_servicios_v xxris\n"
				+ "            , (SELECT /*+ INDEX  */\n"
				+ "                      apia.invoice_num\n"
				+ "                     ,MAX(DECODE(aila.line_type_lookup_code,'PREPAY',aila.accounting_date,aca.check_date)) fecha_de_pago_ap\n"
				+ "               FROM   apps.ap_invoices_all   apia\n"
				+ "                     ,apps.ap_invoice_lines_all aila\n"
				+ "                     ,apps.ap_invoices_all ana\n"
				+ "                     ,apps.ap_invoice_payments_all aipa\n"
				+ "                     ,apps.ap_checks_all aca\n"
				+ "               WHERE  aila.invoice_id = apia.invoice_id\n"
				+ "               AND    aila.prepay_invoice_id = ana.invoice_id(+)\n"
				+ "               AND    aipa.invoice_id(+) = decode(aila.line_type_lookup_code,'PREPAY',ana.invoice_id,apia.invoice_id)\n"
				+ "               AND    aca.check_id(+)  = aipa.check_id\n"
				+ "               AND    aca.status_lookup_code(+) != 'VOIDED'\n"
				+ "               GROUP BY apia.invoice_num\n"
				+ "               ) api\n"
				+ "			  WHERE estatus = 'E'\n"
				+ "            AND aps.segment1 BETWEEN '"+startRfc+"'  AND '"+endRfc+"'\n"
				+ "              AND   aps.vendor_id = apss.vendor_id\n"
				+ "                            AND   NVL(xps.atributo1,xps.factura) = api.invoice_num (+)\n"
				+ "                            AND   apss.vendor_site_Code_alt = TO_CHAR(xxris.supplier)\n"
				+ "                            AND   apss.vendor_site_Code = xxris.site\n"
				+ "                            AND   xxris.site  = xps.plaza\n"
				+ "                            AND   xxris.item = xps.servicio\n"
				+ "                            AND   apss.vendor_site_Code = xps.plaza\n"
				+ "                            AND   TRUNC(fecha_transmision) BETWEEN TO_DATE('"+startDate+"','DD-MON-YYYY') AND TO_DATE('"+endDate+"','DD-MON-YYYY')\n"
				+ "              ORDER BY  api.fecha_de_pago_ap\n"
				+ "                                     ,aps.segment1\n"
				+ "                                     ,xps.plaza\n"
				+ "                                     ,xps.tienda\n"
				+ "                                     ,xps.servicio\n"
				+ "                                     ,xps.fecha_transaccion";

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
	
	public static SQLResult executeQueryAndReturnFirstData(String startDate, String endDate, String startRfc, String endRfc) throws IOException {
		SQLResult values = null;
		String query = "SELECT   /*+ MATERIALIZE  */ --12 DIC 2024\n"
				+ "        Aps.Segment1 Rfc\n"
				+ "      , Aps.Vendor_Name Razon_Social\n"
				+ "      /*, apss.vendor_site_Code_alt id_socio\n*/"
				+ "      , xps.trans_id\n"
				+ "			, xps.plaza\n"
				+ "			, xps.tienda\n"
				+ "			, xps.folio_transaccion\n"
				+ "			, xps.servicio\n"
				+ "			, TO_CHAR(xps.fecha_transaccion,'dd-mon-yyyy') fecha_transaccion\n"
				+ "        FROM  apps.xxfc_pago_servicios xps\n"
				+ "            , apps.ap_suppliers aps\n"
				+ "            , apps.ap_supplier_sites_all apss\n"
				+ "            , apps.xxfc_retek_item_servicios_v xxris\n"
				+ "            , (SELECT /*+ INDEX  */\n"
				+ "                      apia.invoice_num\n"
				+ "                     ,MAX(DECODE(aila.line_type_lookup_code,'PREPAY',aila.accounting_date,aca.check_date)) fecha_de_pago_ap\n"
				+ "               FROM   apps.ap_invoices_all   apia\n"
				+ "                     ,apps.ap_invoice_lines_all aila\n"
				+ "                     ,apps.ap_invoices_all ana\n"
				+ "                     ,apps.ap_invoice_payments_all aipa\n"
				+ "                     ,apps.ap_checks_all aca\n"
				+ "               WHERE  aila.invoice_id = apia.invoice_id\n"
				+ "               AND    aila.prepay_invoice_id = ana.invoice_id(+)\n"
				+ "               AND    aipa.invoice_id(+) = decode(aila.line_type_lookup_code,'PREPAY',ana.invoice_id,apia.invoice_id)\n"
				+ "               AND    aca.check_id(+)  = aipa.check_id\n"
				+ "               AND    aca.status_lookup_code(+) != 'VOIDED'\n"
				+ "               GROUP BY apia.invoice_num\n"
				+ "               ) api\n"
				+ "			  WHERE estatus = 'E'\n"
				+ "            AND aps.segment1 BETWEEN '"+startRfc+"'  AND '"+endRfc+"'\n"
				+ "              AND   aps.vendor_id = apss.vendor_id\n"
				+ "                            AND   NVL(xps.atributo1,xps.factura) = api.invoice_num (+)\n"
				+ "                            AND   apss.vendor_site_Code_alt = TO_CHAR(xxris.supplier)\n"
				+ "                            AND   apss.vendor_site_Code = xxris.site\n"
				+ "                            AND   xxris.site  = xps.plaza\n"
				+ "                            AND   xxris.item = xps.servicio\n"
				+ "                            AND   apss.vendor_site_Code = xps.plaza\n"
				+ "                            AND   TRUNC(fecha_transmision) BETWEEN TO_DATE('"+startDate+"','DD-MON-YYYY') AND TO_DATE('"+endDate+"','DD-MON-YYYY')\n"
				+ "              ORDER BY  api.fecha_de_pago_ap\n"
				+ "                                     ,aps.segment1\n"
				+ "                                     ,xps.plaza\n"
				+ "                                     ,xps.tienda\n"
				+ "                                     ,xps.servicio\n"
				+ "                                     ,xps.fecha_transaccion";

		try {			
			utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
					GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
			values = AVANTE.executeQuery(query);

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
		}
		return values;
	}

	public static SQLResult executeQueryAndReturnSecoundData(String startDate, String endDate, String startRfc, String endRfc) throws IOException {
		SQLResult values = null;
		String query = "SELECT   /*+ MATERIALIZE  */ --12 DIC 2024\n"
				+ "			xps.valor\n"
				+ "			, xps.comision\n"
				+ "			, TO_CHAR(xps.fecha_transmision,'dd-mon-yyyy')	fecha_transmision\n"
				+ "			, NVL(xps.atributo1,xps.factura) factura\n"
				+ "			, TO_CHAR(api.fecha_de_pago_ap,'dd-mon-yyyy') fecha_de_pago_ap\n"
				+ "			, xps.gl\n"
				+ "			, xps.poliza_os\n"
				+ "			, xps.carta_femsa\n"
				+ " 			, xps.folio_wm\n"
				+ "        FROM  apps.xxfc_pago_servicios xps\n"
				+ "            , apps.ap_suppliers aps\n"
				+ "            , apps.ap_supplier_sites_all apss\n"
				+ "            , apps.xxfc_retek_item_servicios_v xxris\n"
				+ "            , (SELECT /*+ INDEX  */\n"
				+ "                      apia.invoice_num\n"
				+ "                     ,MAX(DECODE(aila.line_type_lookup_code,'PREPAY',aila.accounting_date,aca.check_date)) fecha_de_pago_ap\n"
				+ "               FROM   apps.ap_invoices_all   apia\n"
				+ "                     ,apps.ap_invoice_lines_all aila\n"
				+ "                     ,apps.ap_invoices_all ana\n"
				+ "                     ,apps.ap_invoice_payments_all aipa\n"
				+ "                     ,apps.ap_checks_all aca\n"
				+ "               WHERE  aila.invoice_id = apia.invoice_id\n"
				+ "               AND    aila.prepay_invoice_id = ana.invoice_id(+)\n"
				+ "               AND    aipa.invoice_id(+) = decode(aila.line_type_lookup_code,'PREPAY',ana.invoice_id,apia.invoice_id)\n"
				+ "               AND    aca.check_id(+)  = aipa.check_id\n"
				+ "               AND    aca.status_lookup_code(+) != 'VOIDED'\n"
				+ "               GROUP BY apia.invoice_num\n"
				+ "               ) api\n"
				+ "			  WHERE estatus = 'E'\n"
				+ "            AND aps.segment1 BETWEEN '"+startRfc+"'  AND '"+endRfc+"'\n"
				+ "              AND   aps.vendor_id = apss.vendor_id\n"
				+ "                            AND   NVL(xps.atributo1,xps.factura) = api.invoice_num (+)\n"
				+ "                            AND   apss.vendor_site_Code_alt = TO_CHAR(xxris.supplier)\n"
				+ "                            AND   apss.vendor_site_Code = xxris.site\n"
				+ "                            AND   xxris.site  = xps.plaza\n"
				+ "                            AND   xxris.item = xps.servicio\n"
				+ "                            AND   apss.vendor_site_Code = xps.plaza\n"
				+ "                            AND   TRUNC(fecha_transmision) BETWEEN TO_DATE('"+startDate+"','DD-MON-YYYY') AND TO_DATE('"+endDate+"','DD-MON-YYYY')\n"
				+ "              ORDER BY  api.fecha_de_pago_ap\n"
				+ "                                     ,aps.segment1\n"
				+ "                                     ,xps.plaza\n"
				+ "                                     ,xps.tienda\n"
				+ "                                     ,xps.servicio\n"
				+ "                                     ,xps.fecha_transaccion";

		try {
			utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
					GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
			values = AVANTE.executeQuery(query);
		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
		}
		return values;
	}

}
