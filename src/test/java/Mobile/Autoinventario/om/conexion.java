package Mobile.Autoinventario.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.testng.Assert;

import Mobile.util.GlobalVariables;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.sql.SQLResult;


public class conexion {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private BaseExecution baseExecution;
	private Connection con;
	private String week;
	private String year;
	public static String fechaConsulta;
	private String items_sent;
	private String items_sentNube;
	private String crTienda;
	private String crPlaza;
	/*

	/* Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Conexion
	 * @author Joel Rodriguez
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public conexion(TestCase testCase, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.baseExecution = baseExecution;
	}

	/**
	 * Metodo
	 ****************************************************************************************/
	/**
	 * Conexion
	 * @return 
	 * @throws SQLException 
	 *
	 */


	public void conn() throws Exception {
		//Conexion a base de datos Satelites
		con = DriverManager.getConnection(GlobalVariables.urlPV10_111_Satelites, GlobalVariables.userDB10_111, 
				GlobalVariables.passBD10_111);
		if (con.isValid(0)==true) {
			testCase.addTextEvidenceCurrentStep("Se valida conexion a SQL");;
		} else {
			Assert.fail("No se pudo establecer la conexión a la base de datos");
		}
	}
	
	public void conn_XposStore() throws Exception {
		//Conexion a base de datos XposStore
		con = DriverManager.getConnection(GlobalVariables.urlPV10_111_XposStore, GlobalVariables.userDB10_111, 
				GlobalVariables.passBD10_111);
		if (con.isValid(0)==false) {
			Assert.fail("No se pudo establecer la conexión a la base de datos");
		}
	}
  
	public void obtenerCr () throws SQLException {
		//Cr de tienda y Plaza
		String query = "SELECT store.CODE as tienda, plaza.CODE as plaza\r\n"
				+ "FROM [XposStore].[dbo].BUSINESS_UNIT store  \r\n"
				+ "JOIN [XposStore].[dbo].BUSINESS_UNIT plaza   \r\n"
				+ "    ON store.parent_business_unit_id = plaza.business_unit_id   \r\n"
				+ "    AND store.business_type_id = 3   \r\n"
				+ "JOIN [XposStore].[dbo].BUSINESS_UNIT company  \r\n"
				+ "    ON plaza.parent_business_unit_id = company.business_unit_id";
		SQLResult res = new SQLResult(con, query);
		this.crTienda = res.getData(0, "tienda");
		this.crPlaza = res.getData(0,"plaza");
	}
	
	public void connPowerBi() throws Exception {
		//Conexion a base de datos BI
		con = DriverManager.getConnection(GlobalVariables.connPowerBi);
		if (con.isValid(0)==true) {
			testCase.addTextEvidenceCurrentStep("Se valida conexion a BD en la nube");;
		} else {
			Assert.fail("No se pudo establecer la conexión a la base de datos");
		}
	}

	public void query_PowerBi() throws SQLException {
		//Consulta para obtener la semana de preinventario
		String query = "SELECT TOP 10 cr_plaza, cr_tienda, week, sku, itemsSent FROM dbo.VIEW_INVENTARIO_PA "
				+ "WHERE WEEK =" + week + " AND cr_plaza = '" + crPlaza + "' AND cr_tienda = '" + crTienda+"'"; // Cambia la vista según el entorno
		SQLResult res = new SQLResult(con, query);
		this.items_sentNube = res.getData(0,"itemsSent");
		testCase.addQueryEvidenceCurrentStep(res);
	}
	
	public void valida_itemsSent() {
		//Valida cantidad de items
		if (items_sent.equals(items_sentNube)) {
			testCase.addTextEvidenceCurrentStep("Se valida coincide informacion de ambas Base de datos \r\n"
					+ "El valor del campo de articulos 'itemSent' de base de datos local es: " + items_sent);
		}else {
			Assert.fail("No coincide informacion");
		}
	}

	public void query_Semana() throws SQLException {
		//Consulta para obtener la semana de preinventario
		String query = "SELECT TOP 1 year(II_STARTDATE) as YEAR, II_WEEKNUMBER, II_FOLIO, II_STARTDATE, format(II_ENDDATE,'dd-MM-yyyy HH:mm:ss') AS II_ENDDATE,"
				+ "II_CLASSIFICATION, II_STATUS, II_DESCRIPTION FROM HC.II_HEADER WITH(NOLOCK) WHERE II_TYPE = 'I' "
				+ "and II_CLASSIFICATION='G' order by II_STARTDATE desc\r\n";
		SQLResult res = new SQLResult(con, query);
		testCase.addQueryEvidenceCurrentStep(res);
		this.week = res.getData(0,"II_WEEKNUMBER"); //Valor de numero de la semana
		this.year= res.getData(0, "YEAR");
		fechaConsulta =res.getData(0,"II_ENDDATE");
		comando.nuevaFechaHora = fechaConsulta;
		testCase.addTextEvidenceCurrentStep("Se obtiene registro de la semana "+ week);
	}

	public void query_infoSemana() throws SQLException {
		//Consulta de informacion para la semana obtenida y validar no haya registros en fin de semana
		String query = "DECLARE @ID_HEADER AS INT \r\n"
				+ "SET @ID_HEADER = (	SELECT ID_SHIPPING_HEADER FROM HC.PI_SHIPPING_HEADER WHERE YEAR =" + year + " AND WEEK = " + week + ")\r\n"
				+ "	SELECT * FROM HC.AI_PROCESS_BINNACLE_CRONJOB WHERE DATENAME(WEEKDAY, DATE_SENT) IN ('Thursday', 'Friday', 'Saturday')\r\n";
		SQLResult res = new SQLResult(con, query);
		baseExecution.addStep("Ejecutar Script Consulta informacion con datos de semana de preinventario");
		testCase.addQueryEvidenceCurrentStep(res);
		if (res.isEmpty()) {
			testCase.addTextEvidenceCurrentStep("No se encuentra informacion de dias Jueves, Viernes y Sabado");
		}else {
			Assert.fail("Error, se encontraron datos");
		}
	}

	public void limpiarInfo() throws SQLException {
		//Query de limpieza de informacion en tablas de preinventario
		String query = "TRUNCATE TABLE hc.PI_SHIPPING_HEADER\r\n"
				+ "	TRUNCATE TABLE hc.AI_PROCESS_BINNACLE_CRONJOB \r\n"
				+ "	TRUNCATE TABLE hc.PI_SHIPPING_DETAIL\r\n"
				+ "SELECT ID_SHIPPING_HEADER AS ID_H, YEAR AS AÑO, WEEK AS SEMANA, PREINVENTORY_START_DATE AS Fecha_Ini,"
				+ "PREINVENTORY_LAST_DATE AS Fecha_Fin, STATUS_SENT AS Status, ITEMS_SENT AS Articulos "
				+ "FROM HC.PI_SHIPPING_HEADER\r\n";
		SQLResult res = new SQLResult(con, query);
		testCase.addTextEvidenceCurrentStep("Se ejecuta query de limpieza");
		if (res.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(res);
			testCase.addTextEvidenceCurrentStep("Se limpian registros de tabla de Encabezado");
		} else {
			Assert.fail("No se realizo limpieza de tabla de Encabezado");
		}
		String query2 = "SELECT ID_SHIPPING_DETAIL AS ID_D, ID_SHIPPING_HEADER AS ID_H, SKU, PLANOGRAM_GROUP_ID AS GRUPO,"
				+ "UNIT_PRICE AS PRECIO FROM HC.PI_SHIPPING_DETAIL";
		res = new SQLResult(con, query2);
		if (res.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(res);
			testCase.addTextEvidenceCurrentStep("Se limpian registros de tabla Detalle");
		} else {
			Assert.fail("No se realizo limpieza de tabla Detalle");
		}
		String query3 = "SELECT AI_PROCESS_ID AS ID, CODE, ID_HEADER AS ID_H, RESPONSE, DATE_SENT AS FECHA,"
				+ "STATUS_CODE AS STATUS, MESSAGE AS MENSAJE FROM HC.AI_PROCESS_BINNACLE_CRONJOB";
		res = new SQLResult(con, query3);
		if (res.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(res);
			testCase.addTextEvidenceCurrentStep("Se limpian registros de tabla de Cronjob");
		} else {
			Assert.fail("No se realizo limpieza de tabla de Cronjob");
		}
	}

	public void tablaEncabezado() throws SQLException {
		//Consulta de informacion para tabla de Encabezado preinventario
		String query = "DECLARE @TIENDA AS VARCHAR(10) = '" + crTienda + "'\r\n"
				+ "DECLARE @PLAZA AS VARCHAR(10) = '" + crPlaza + "'\r\n" 
				+ "SELECT @TIENDA AS TIENDA,  @PLAZA AS PLAZA, ID_SHIPPING_HEADER AS ID_H, YEAR AS AÑO, WEEK AS SEMANA, PREINVENTORY_START_DATE AS Fecha_Ini,"
				+ "PREINVENTORY_LAST_DATE AS Fecha_Fin, STATUS_SENT AS Status, ITEMS_SENT "
				+ "FROM HC.PI_SHIPPING_HEADER WHERE YEAR =" + year + " AND WEEK = " + week;
		SQLResult res = new SQLResult(con, query);
		while (res.isEmpty()) {
			try {
				Thread.sleep(5000);
				res = new SQLResult(con, query);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new SQLException("El hilo fue interrumpido mientras esperaba resultados.", e);
			}
		}
		this.items_sent = res.getData(0,"ITEMS_SENT");
		testCase.addQueryEvidenceCurrentStep(res);		
	}

	public void tablaDetalle() throws SQLException {
		//Consulta de informacion para tabla de Detalle preinventario
		String query = "DECLARE @ID_HEADER AS INT \r\n"
				+"SET @ID_HEADER = (SELECT ID_SHIPPING_HEADER FROM HC.PI_SHIPPING_HEADER WHERE YEAR =" + year + " AND WEEK = " + week + ")\r\n"
				+ "	SELECT TOP 10 ID_SHIPPING_DETAIL AS ID_D, ID_SHIPPING_HEADER AS ID_H, SKU, PLANOGRAM_GROUP_ID AS GRUPO,"
				+ "UNIT_PRICE AS PRECIO FROM HC.PI_SHIPPING_DETAIL WHERE ID_SHIPPING_HEADER = @ID_HEADER ORDER BY SKU";
		SQLResult res = new SQLResult(con, query);
		while (res.isEmpty()) {
			try {
				Thread.sleep(5000);
				res = new SQLResult(con, query);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new SQLException("El hilo fue interrumpido mientras esperaba resultados.", e);
			}
		}
		testCase.addQueryEvidenceCurrentStep(res);
	}

	public void tablaCronjob() throws SQLException {
		//Consulta de informacion para tabla de Cronjob
		String query = "DECLARE @ID_HEADER AS INT \r\n"
				+ "SET @ID_HEADER = (	SELECT ID_SHIPPING_HEADER FROM HC.PI_SHIPPING_HEADER WHERE YEAR =" + year + " AND WEEK = " + week + ")\r\n"
				+ "SELECT AI_PROCESS_ID AS ID, CODE,RESPONSE, DATE_SENT AS FECHA, STATUS_CODE AS STATUS, MESSAGE AS MENSAJE "
				+ "FROM HC.AI_PROCESS_BINNACLE_CRONJOB WHERE ID_HEADER = @ID_HEADER AND STATUS_CODE='201'";
		SQLResult res = new SQLResult(con,query);
		while (res.isEmpty()) {
			try {
				Thread.sleep(5000);
				res = new SQLResult(con, query);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new SQLException("El hilo fue interrumpido mientras esperaba resultados.", e);
			}
		}
		testCase.addQueryEvidenceCurrentStep(res);
		String response = res.getData(0, "RESPONSE");
		testCase.addTextEvidenceCurrentStep("El resultado de la ejecucion del cronjob con informacion de tienda es: " + response);
	}

	
	public void qryExtraccion() throws SQLException{
		//Consulta query extraccion de informacion
		String query = "DECLARE @WEEK_NUMBER AS INT = " + week + "\r\n"
				+ "    DECLARE @YEAR AS INT = " + year + "\r\n"
				+ "    DECLARE @TYPE_PREINVENTORY VARCHAR(1) = 'I'\r\n"
				+ "\r\n"
				+ "	  --INFORMACION DE TIENDA\r\n"
				+ "    SELECT store.code AS CR_STORE,plaza.code AS CR_PLACE INTO #STORE_DATA\r\n"
				+ "     FROM [XposStore].[dbo].BUSINESS_UNIT store  \r\n"
				+ "     JOIN [XposStore].[dbo].BUSINESS_UNIT plaza   \r\n"
				+ "       ON store.parent_business_unit_id = plaza.business_unit_id   \r\n"
				+ "      AND store.business_type_id = 3 JOIN [XposStore].[dbo].BUSINESS_UNIT company  \r\n"
				+ "       ON plaza.parent_business_unit_id = company.business_unit_id  \r\n"
				+ "\r\n"
				+ "	---SACAMOS LOS SKUS DE IMA DATA DE LA MUESTRA\r\n"
				+ "        ;WITH CTE AS (SELECT HC.II_IMA_DATA.II_SKU, HC.II_IMA_DATA.II_ITEM_NAME, HC.II_IMA_DATA.II_FURNITURE_ID,hc.II_IMA_DATA.II_PLANOGRAM_ID,\r\n"
				+ "           ROW_NUMBER() OVER (PARTITION BY II_SKU ORDER BY (SELECT NULL)) AS row_num\r\n"
				+ "    FROM HC.II_IMA_DATA WITH (NOLOCK) WHERE II_WEEKNUMBER = 17 AND II_YEAR = 2024)\r\n"
				+ "SELECT * INTO #IMA_DATA FROM CTE WHERE row_num = 1;\r\n"
				+ "\r\n"
				+ "		--- SACAMOS LOS FOLIOS DE INVENTARIO OPERATIVO AFECTADOS Y LOS FOLIOS HEADER DEL PREINVENTARIO INTELIGENTE\r\n"
				+ "		SELECT R.IO_FOLIO, R.II_FOLIO, R.II_FINISHED_COUNT_DATE, R.II_TOTAL_ONHAND, R.II_TOTAL_ONHANDREAL\r\n"
				+ "			, IH.II_STARTDATE , IH.II_ENDDATE, IH.II_STATUS, IH.II_WEEKNUMBER , IH.II_DESCRIPTION\r\n"
				+ "			,IH.II_TYPE INTO #FOLIO_IO_DATA FROM HC.II_HEADER_IO_HEADER R\r\n"
				+ "        INNER JOIN hc.II_HEADER IH ON IH.II_FOLIO = R.II_FOLIO\r\n"
				+ "        INNER JOIN hc.io_header ioH ON R.IO_FOLIO = IOH.IO_Folio\r\n"
				+ "        WHERE IH.II_WEEKNUMBER = @WEEK_NUMBER AND IH.II_TYPE = @TYPE_PREINVENTORY AND YEAR(IH.II_STARTDATE) = @YEAR\r\n"
				+ "\r\n"
				+ "		---SACAMOS LOS PRECIOS DE VENTA DE LOS SKUS ACTIVOS\r\n"
				+ "		  SELECT VP.Sku, VP.Amount, VP.BU_Code, VP.Price_Status_Name\r\n"
				+ "		  INTO #SKUS_PRICES FROM [XposStore].[dbo].[ProductCacheDataVw] VP WITH (NOLOCK)\r\n"
				+ "\r\n"
				+ "		  declare @totalProductsInStore float = 0\r\n"
				+ " \r\n"
				+ "		--TOTAL DE ARTICULOS POR TIENDA ACTIVOS\r\n"
				+ "		;with cte (Sku) as (select distinct Sku from [XposStore].[dbo].[ProductCacheDataVw]\r\n"
				+ "		where Item_Status_Name='ACTIVE_ITEM_STATUS' AND Item_Type_Value = 'PRODUCT_ITEM' AND Product_Type_Code_Name = 'MREGUL_PRODUCT')\r\n"
				+ "		select @totalProductsInStore = COUNT(Sku) from cte\r\n"
				+ "\r\n"
				+ "--PREINVENTARIO HEADER INFORMACION\r\n"
				+ "		SELECT STORE_DATA.CR_PLACE As Plaza, STORE_DATA.CR_STORE As Tienda, IH.II_WEEKNUMBER as Week,IH.II_STATUS AS STATUS\r\n"
				+ "		, MAX(R.TM_CRT) AS FecINI, DATEDIFF(HOUR, MAX(R.TM_CRT), MAX(R.II_FINISHED_COUNT_DATE)) AS Horas\r\n"
				+ "		, (SELECT COUNT(*) FROM #IMA_DATA) AS ITEMS_SENT, @totalProductsInStore AS TOTAL\r\n"
				+ "	, ROUND(CAST(((SELECT COUNT(*) FROM HC.II_DETAIL WHERE II_FOLIO = IH.II_FOLIO) * 100.0) / @totalProductsInStore AS DECIMAL(10, 2)), 2) AS [%Cobertura]\r\n"
				+ "\r\n"
				+ "	FROM HC.II_HEADER IH WITH(NOLOCK)\r\n"
				+ "	INNER JOIN #STORE_DATA STORE_DATA WITH(NOLOCK) ON STORE_DATA.CR_PLACE = STORE_DATA.CR_PLACE AND  STORE_DATA.CR_PLACE = STORE_DATA.CR_PLACE\r\n"
				+ "	INNER JOIN HC.II_HEADER_IO_HEADER R ON IH.II_FOLIO = R.II_FOLIO \r\n"
				+ "	INNER JOIN hc.io_header ioH ON R.IO_FOLIO = IOH.IO_Folio\r\n"
				+ "	INNER JOIN HC.II_DETAIL IDETAIL ON IDETAIL.IO_FOLIO = R.IO_FOLIO AND IDETAIL.II_FOLIO = IH.II_FOLIO\r\n"
				+ "	WHERE IH.II_WEEKNUMBER = @WEEK_NUMBER AND IH.II_TYPE = @TYPE_PREINVENTORY AND YEAR(IH.II_STARTDATE) = @YEAR\r\n"
				+ "	GROUP BY STORE_DATA.CR_PLACE, STORE_DATA.CR_STORE, IH.II_WEEKNUMBER, IH.II_STARTDATE, IH.II_ENDDATE,"
				+ " IH.II_STATUS, CASE WHEN IH.II_STATUS = 1 THEN 'IN_PROGRESS' WHEN IH.II_STATUS = 2 THEN 'COMPLETED' ELSE"
				+ " 'INCOMPLETED' END, IH.II_FOLIO";
		SQLResult res = new SQLResult(con,query);
		if (res.isEmpty()) {
			Assert.fail("No se encontro informacion en tabla");
		} else {
			testCase.addQueryEvidenceCurrentStep(res);
		}
		this.items_sentNube = res.getData(0, "ITEMS_SENT");
		String query2 = "SELECT TOP 10 IO_DETAIL.IO_Folio AS FOLIO, IO_DETAIL.IO_SKU_REF AS SKU_REF, IO_DETAIL.IO_SKU_Order AS ORDEN, IO_DETAIL.IO_Vendor, \r\n"
				+ "CASE WHEN IO_DETAIL.IO_OnHand IS NOT NULL THEN IO_DETAIL.IO_OnHand ELSE 0 END AS ONHAND, \r\n"
				+ "CASE WHEN IO_DETAIL.IO_OnHandReal IS NOT NULL THEN IO_DETAIL.IO_OnHandReal ELSE 0 END AS ONHAND_REAL, \r\n"
				+ "CASE WHEN IO_DETAIL.IO_Dif IS NOT NULL THEN IO_DETAIL.IO_Dif ELSE 0 END AS DIF, IMA_DATA.II_SKU AS SKU,PRICES.AMOUNT\r\n"
				+ "FROM HC.io_detail IO_DETAIL WITH (NOLOCK)\r\n"
				+ "INNER JOIN #FOLIO_IO_DATA FOLIO_IO_DATA WITH (NOLOCK) ON FOLIO_IO_DATA.IO_FOLIO = IO_DETAIL.IO_Folio \r\n"
				+ "INNER JOIN #IMA_DATA  IMA_DATA WITH (NOLOCK) ON IMA_DATA.II_SKU = IO_DETAIL.IO_SKU_REF\r\n"
				+ "INNER JOIN #SKUS_PRICES PRICES WITH (NOLOCK) ON IMA_DATA.II_SKU = PRICES.SKU\r\n"
				+ "LEFT JOIN HC.II_DETAIL IDETAIL WITH (NOLOCK) ON IDETAIL.II_FOLIO = FOLIO_IO_DATA.II_FOLIO AND IMA_DATA.II_SKU = IDETAIL.IO_SKU_REF Order by SKU\r\n"
				+ "\r\n"
				+ "DROP TABLE #IMA_DATA;\r\n"
				+ "DROP TABLE #SKUS_PRICES\r\n"
				+ "DROP TABLE #FOLIO_IO_DATA\r\n"
				+ "DROP TABLE #STORE_DATA";
		SQLResult res2 = new SQLResult(con,query2);
		if (res2.isEmpty()) {
			Assert.fail("No se encontro informacion en tabla");
		} else {
			testCase.addQueryEvidenceCurrentStep(res2);
		}
	}
	
	
	public void Close() throws SQLException {
		con.close();
	}

}
