package Mobile.Mep.om;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import utils.sql.SQLResult;


public class UtilPostgreSql {
	
	public final String bd = "jdbc:postgresql://oxzposoadadq01.postgres.database.azure.com:5432/prom_oxxo_qa?sslmode=require";
	public final String user = "ivanyakab";
	
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private BaseExecution baseExecution;
	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase UtilPostgreSql
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public UtilPostgreSql(TestCase testCase, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.baseExecution = baseExecution;
    }
	

	
	/**
	 * Método para realizar consultas a BD SQL.
	 * 
	 * @author Roberto Renteria
	 * @date 26/Julio/2024
	 * @param query    - cadena de texto que contiene el query a ejecutar
	 * @param bd       - variable global de bd
	 * @param user     - variable global de user de bd
	 * @param password - variable global de password de bd
	 */
	public static SQLResult sqlExecute(String query, String bd, String user, String password) throws SQLException {
		try {
			// Crear conexión
			Connection conn = DriverManager.getConnection(bd, user, password);
			// Ejecutar consulta
			Statement statement = conn.createStatement();
 
			if (statement.execute(query)) {
				SQLResult res = new SQLResult(conn, query);
				conn.close();
				return res;
			} else {
				conn.close();
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
    /**
	 * Metodo para validar la existencia de reportes dentro de la tabla "Exhibition_reports", una vez validemos que se encuentre 1 o mas registros, eliminamos la tabla, en caso de que no exista ningun registro, no se realiza ninguna accion
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - CrTienda que hizo el reporte
	 * @date 09/10/2024
	 */
	public void ValidarExistenciaReportesYEliminar(String password, String crTienda) throws SQLException
	{
		
		String queryCount = "SELECT COUNT(*) FROM exhibition_reports WHERE store_id = '" + crTienda + "'";
		String queryDelete = "DELETE FROM exhibition_reports WHERE store_id = '" + crTienda +"'";
		
		SQLResult valorConsulta = sqlExecute(queryCount, bd, user, password);
		testCase.addQueryEvidenceCurrentStep(valorConsulta);

		
		int valorInt = Integer.parseInt(valorConsulta.getFirstRow().get(0).toString());
		
		if (valorInt > 0)
		{
			sqlExecute(queryDelete, bd, user, password);
			testCase.addTextEvidenceCurrentStep("Se han eliminado los registros de la tabla exhibition_reports");
		}
		
	}
	
	
	/**
	 * Metodo que consulta una tabla y valida que los registros que enviamos desde el data provider coincidan con lo que se guarda en la BD
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param cantidad - Cantidad que validaremos
	 * @param crTienda - CrTienda que hizo el reporte
	 * @param exhibitionType - Tipo de exhibicion
	 * @param createdBy - Fecha de creacion del reporte de exhibidor no autorizado
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void ValidarReporteExhibidorNoAutorizado(String password, String cantidad, String crTienda, String exhibitionType, String createdBy) throws SQLException
	{
		
		String queryCount = "SELECT COUNT(*) FROM exhibition_reports WHERE store_id = '" + crTienda + "'";
		String querySelect = "SELECT store_id, quantity, exhibition_type, created_by FROM public.exhibition_reports WHERE store_id = '" + crTienda + "'";
		
		String queryQuantity = "SELECT quantity FROM exhibition_reports WHERE store_id = '" + crTienda + "'";
		String queryStoreId = "SELECT store_id FROM exhibition_reports WHERE store_id = '" + crTienda + "'";
		String queryExhibitionType = "SELECT exhibition_type FROM exhibition_reports WHERE store_id = '" + crTienda + "'";
		String queryCreatedBy = "SELECT created_by FROM exhibition_reports WHERE store_id = '" + crTienda + "'";

		SQLResult valorConsulta = sqlExecute(queryCount, bd, user, password);
		SQLResult valorConsultaSelect = sqlExecute(querySelect, bd, user, password);
		
		SQLResult valorConsultaQuantity = sqlExecute(queryQuantity, bd, user, password);
		SQLResult valorConsultaStoreId = sqlExecute(queryStoreId, bd, user, password);
		SQLResult valorConsultaExhibitionType = sqlExecute(queryExhibitionType, bd, user, password);
		SQLResult valorConsultaCreatedBy = sqlExecute(queryCreatedBy, bd, user, password);

		testCase.addQueryEvidenceCurrentStep(valorConsulta);
		testCase.addQueryEvidenceCurrentStep(valorConsultaSelect);

		int valorInt = Integer.parseInt(valorConsulta.getFirstRow().get(0).toString());
		
		String valorCantidad = valorConsultaQuantity.getFirstRow().get(0).toString();
		String valorStoreId = valorConsultaStoreId.getFirstRow().get(0).toString();
		String valorExhibitionType = valorConsultaExhibitionType.getFirstRow().get(0).toString();
		String valorCreatedBy = valorConsultaCreatedBy.getFirstRow().get(0).toString();
		
		if (valorInt == 1)
		{
			if (valorCantidad == cantidad)
			{
				testCase.addQueryEvidenceCurrentStep(valorConsultaQuantity);
			}

			if (valorStoreId == crTienda)
			{
				testCase.addQueryEvidenceCurrentStep(valorConsultaStoreId);
			}

			if (valorExhibitionType == exhibitionType)
			{
				testCase.addQueryEvidenceCurrentStep(valorConsultaExhibitionType);
			}

			if (valorCreatedBy == createdBy)
			{
				testCase.addQueryEvidenceCurrentStep(valorConsultaCreatedBy);
			}
		}	
	}
	
	/**
	 * Metodo para activar y actualizar un periodo promocional
	 * @nota 	 Fecha ayer - Formula: Dia de Hoy - 1 Dia = Ayer
	 * 			 Tres meses despues - Formula: Dia de Hoy + 3 meses
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param idPeriod - Id del periodo al que queremos actualizar
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void activarPeriodoPromocional(String password, String idPeriod) throws SQLException {
	    // 1. Consultar si el registro ya existe
	    String queryCount = "SELECT COUNT(*) FROM promo_periods WHERE id = '" + idPeriod + "';";
	    SQLResult resultado = sqlExecute(queryCount, bd, user, password);

	    int cantidad = Integer.parseInt(resultado.getFirstRow().get(0).toString());
	    testCase.addTextEvidenceCurrentStep("Registros encontrados para id " + idPeriod + ": " + cantidad);
	    testCase.addQueryEvidenceCurrentStep(resultado);

	    // 2. Calcular fechas solo una vez
	    String fechaInicio = obtenerLaFechaDeAyerFormatoSQL();
	    String fechaFin = obtenerFechaTresMesesDespuesDesdeAyer();

	    // 3. Según la cantidad, ejecutar INSERT o UPDATE
	    String sql;
	    if (cantidad == 0) {
	        sql = String.format(
	            "INSERT INTO public.promo_periods (id, code, start_date, end_date) VALUES ('%s', 'P8', '%s', '%s');",
	            idPeriod, fechaInicio, fechaFin
	        );
	        System.out.println("No existia registro. Se insertara nuevo periodo promocional.");
	        testCase.addTextEvidenceCurrentStep("No existia registro. Se insertara nuevo periodo promocional.");
	    } else {
	        sql = String.format(
	            "UPDATE public.promo_periods SET start_date = '%s', end_date = '%s' WHERE id = '%s';",
	            fechaInicio, fechaFin, idPeriod
	        );
	        System.out.println("Registro existente. Se actualizaran las fechas del periodo promocional.");
	        testCase.addTextEvidenceCurrentStep("Registro existente. Se actualizaran las fechas del periodo promocional.");
	    }

	    // 4. Ejecutar la sentencia
	    sqlExecute(sql, bd, user, password);
	    testCase.addTextEvidenceCurrentStep(
	        String.format("Periodo promocional activado. Fecha inicio: %s, Fecha fin: %s", fechaInicio, fechaFin)
	    );

	    // 5. Validar que efectivamente exista despues del cambio
	    SQLResult validacionFinal = sqlExecute(queryCount, bd, user, password);
	    int cantidadFinal = Integer.parseInt(validacionFinal.getFirstRow().get(0).toString());

	    if (cantidadFinal == 0) {
	        testCase.addTextEvidenceCurrentStep("Prueba no exitosa. No existen registros en la tabla.");
	        throw new RuntimeException("No existen registros en la tabla después del intento de insercion/actualización.");
	    } 
	}

	
	/**
	 * Metodo para desactivar un periodo promocional siempre y cuando el id del periodo sea P825
	 * @nota 	 Fecha Dos Dias despues - Formula: Dia de Hoy + 2 Dias = Pasado manana
	 * 			 Tres meses despues - Formula: Dia de Hoy + 3 meses
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param idPeriod - Id del periodo al que queremos actualizar
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void desactivarPeriodoPromocional(String password, String idPeriod) throws SQLException
	{
		// 1. Consultar si el registro ya existe
	    String queryCount = "SELECT COUNT(*) FROM promo_periods WHERE id = '" + idPeriod + "';";
	    SQLResult resultado = sqlExecute(queryCount, bd, user, password);

	    int cantidad = Integer.parseInt(resultado.getFirstRow().get(0).toString());
	    testCase.addTextEvidenceCurrentStep("Registros encontrados para id " + idPeriod + ": " + cantidad);
	    testCase.addQueryEvidenceCurrentStep(resultado);

	    // 2. Calcular fechas solo una vez
	    String fechaDosDiasDespuesAHoy = obtenerFechaQuinceDespuesAHoy();
		String tresMesesDespues = obtenerFechaTresMesesDespuesDesdeAyer();

	    // 3. Según la cantidad, ejecutar INSERT o UPDATE
	    String sql;
	    if (cantidad == 0) {
	        sql = String.format(
	            "INSERT INTO public.promo_periods (id, code, start_date, end_date) VALUES ('%s', 'P8', '%s', '%s');",
	            idPeriod, fechaDosDiasDespuesAHoy, tresMesesDespues
	        );
	        System.out.println("No existia registro. Se insertara nuevo periodo promocional.");
	        testCase.addTextEvidenceCurrentStep("No existia registro. Se insertara nuevo periodo promocional.");
	    } else {
	        sql = String.format(
	            "UPDATE public.promo_periods SET start_date = '%s', end_date = '%s' WHERE id = '%s';",
	            fechaDosDiasDespuesAHoy, tresMesesDespues, idPeriod
	        );
	        System.out.println("Registro existente. Se actualizaran las fechas del periodo promocional.");
	        testCase.addTextEvidenceCurrentStep("Registro existente. Se actualizaran las fechas del periodo promocional.");
	    }

	    // 4. Ejecutar la sentencia
	    sqlExecute(sql, bd, user, password);
	    testCase.addTextEvidenceCurrentStep(
	        String.format("Periodo promocional activado. Fecha inicio: %s, Fecha fin: %s", fechaDosDiasDespuesAHoy, tresMesesDespues)
	    );

	    // 5. Validar que efectivamente exista despues del cambio
	    SQLResult validacionFinal = sqlExecute(queryCount, bd, user, password);
	    int cantidadFinal = Integer.parseInt(validacionFinal.getFirstRow().get(0).toString());

	    if (cantidadFinal == 0) {
	        testCase.addTextEvidenceCurrentStep("Prueba no exitosa. No existen registros en la tabla.");
	        throw new RuntimeException("No existen registros en la tabla después del intento de insercion/actualización.");
	    } 
	}
	
	/**
	 * Metodo para insertar un pop con estatus EXECUTABLE - Incompleto
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @param sqlInsertTablePromoLabel - Es el insert del Pop
	 * @param sqlInsertTablePromoLabelStatuses - Es el insert del status del Pop
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void insertarUnPopExecutable(String password, String crTienda, String sqlInsertTablePromoLabel, String sqlInsertTablePromoLabelStatuses) throws SQLException
	{
		deleteTableLabelValidationsByCrTienda(password, crTienda);
		DeleteRecordsStoreTablePromoLabels(password, crTienda);
		deleteTableLabelStatusesByCrTienda(password, crTienda);

		
		// Insertar un POP
		sqlExecute(sqlInsertTablePromoLabel, bd, user, password);
		
		
		// Insertar un Status a ese POP
		sqlExecute(sqlInsertTablePromoLabelStatuses, bd, user, password);
		
	}
	

	
	/**
	 * Metodo para insertar 4 pops con estatus EXECUTABLE - incompletos
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @param insertPopExecutableOne - Es el primer pop que se insertara en SQL
	 * @param insertPopExecutableTwo - Es el segundo pop que se insertara en SQL
	 * @param insertPopExecutableThree - Es el segundo pop que se insertara en SQL
	 * @param insertPopExecutableFour - Es el segundo pop que se insertara en SQL
	 * @param insertPopExecutableOneStatus - Es el primer pop que se insertara en SQL con estatus Executable
	 * @param insertPopExecutableTwoStatus - Es el segundo pop que se insertara en SQL con estatus Executable
	 * @param insertPopExecutableThreeStatus - Es el tercer pop que se insertara en SQL con estatus Executable
	 * @param insertPopExecutableFourStatus - Es el cuarto status del  pop que se insertara en SQL con estatus Executable
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void insertar4PopsExecutable(String password, String crTienda, String insertPopExecutableOne,  String insertPopExecutableTwo,  String insertPopExecutableThree,  String insertPopExecutableFour,  String insertPopExecutableOneStatus,  String insertPopExecutableTwoStatus, String insertPopExecutableThreeStatus, String insertPopExecutableFourStatus) throws SQLException
	{
		
		deleteTableLabelValidationsByCrTienda(password, crTienda);
		DeleteRecordsStoreTablePromoLabels(password, crTienda);
		deleteTableLabelStatusesByCrTienda(password, crTienda);

		
		sqlExecute(insertPopExecutableOne, bd, user, password);
		sqlExecute(insertPopExecutableTwo, bd, user, password);
		sqlExecute(insertPopExecutableThree, bd, user, password);
		sqlExecute(insertPopExecutableFour, bd, user, password);

		sqlExecute(insertPopExecutableOneStatus, bd, user, password);
		sqlExecute(insertPopExecutableTwoStatus, bd, user, password);
		sqlExecute(insertPopExecutableThreeStatus, bd, user, password);
		sqlExecute(insertPopExecutableFourStatus, bd, user, password);
	}
	
	/**
	 * Metodo para insertar 2 pops dentro de un grupo de espacio promocional, en este caso, Cabecera
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @param queryInsertRecordOne - Query de SQL para insertar un Pop
	 * @param queryInsertRecordTwo - Query de SQL para insertar un Pop
	 * @param queryInsertRecordOneStatus - Query de SQL para insertar un status a un Pop
	 * @param queryInsertRecorTwoStatus - Query de SQL para insertar un status a un Pop
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void insertarDosPops(String password, String crTienda, String queryInsertRecordOne, String queryInsertRecordTwo, String queryInsertRecordOneStatus, String queryInsertRecordTwoStatus) throws SQLException
	{
		
		deleteTableLabelValidationsByCrTienda(password, crTienda);
		DeleteRecordsStoreTablePromoLabels(password, crTienda);
		deleteTableLabelStatusesByCrTienda(password, crTienda);
		
		sqlExecute(queryInsertRecordOne, bd, user, password);
		sqlExecute(queryInsertRecordTwo, bd, user, password);

		sqlExecute(queryInsertRecordOneStatus, bd, user, password);
		sqlExecute(queryInsertRecordTwoStatus, bd, user, password);
		
	}

	/**
	 * Metodo para insertar 3 pops dentro de un grupo de espacio promocional, en este caso, Cabecera
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @param queryInsertRecordOne - Query de SQL para insertar un Pop
	 * @param queryInsertRecordTwo - Query de SQL para insertar un Pop
	 * @param queryInsertRecordThree - Query de SQL para insertar un Pop
	 * @param queryInsertRecordOneStatus - Query de SQL para insertar un status a un Pop
	 * @param queryInsertRecorTwoStatus - Query de SQL para insertar un status a un Pop
	 * @param queryInsertRecorThreeStatus - Query de SQL para insertar un status a un Pop
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void insertarTresPop(String password, String crTienda, String queryInsertRecordOne, String queryInsertRecordTwo, String queryInsertRecordThree, String queryInsertRecordOneStatus, String queryInsertRecordTwoStatus, String queryInsertRecordThreeStatus) throws SQLException
	{
		
		deleteTableLabelValidationsByCrTienda(password, crTienda);
		DeleteRecordsStoreTablePromoLabels(password, crTienda);
		deleteTableLabelStatusesByCrTienda(password, crTienda);
		
		sqlExecute(queryInsertRecordOne, bd, user, password);
		sqlExecute(queryInsertRecordTwo, bd, user, password);
		sqlExecute(queryInsertRecordThree, bd, user, password);

		sqlExecute(queryInsertRecordOneStatus, bd, user, password);
		sqlExecute(queryInsertRecordTwoStatus, bd, user, password);
		sqlExecute(queryInsertRecordThreeStatus, bd, user, password);
	}
	
	/**
	 * Metodo para insertar cuatro registros en la tienda que mandemos como parametro y cada pop tendra diferentes estatus como: REPORTED, COMPLETED, REPORTED_AS_INCOMPLETE, EXECUTABLE
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @param sqlInsertPopExecutable - Es el primer pop que se insertara en SQL
	 * @param sqlInsertPopCompleted - Es el segundo pop que se insertara en SQL
	 * @param sqlInsertPopReported - Es el segundo pop que se insertara en SQL
	 * @param sqlInsertPopReportedAsIncomplete - Es el segundo pop que se insertara en SQL
	 * @param sqlInsertPopExecutableStatus - Es el primer pop que se insertara en SQL con estatus Executable
	 * @param sqlInsertPopCompletedStatus - Es el segundo pop que se insertara en SQL con estatus Completed
	 * @param sqlInsertPopReportedStatus - Es el tercer pop que se insertara en SQL con estatus Reported
	 * @param sqlInsertPopReportedAsIncompleteStatus - Es el cuarto status del  pop que se insertara en SQL con estatus Reported As Incomplete
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void insertarPops4ConStatus(String password, String crTienda, String sqlInsertPopExecutable, String sqlInsertPopCompleted, String sqlInsertPopReported, String sqlInsertPopReportedAsIncomplete, String sqlInsertPopExecutableStatus, String sqlInsertPopCompletedStatus, String sqlInsertPopReportedStatus, String sqlInsertPopReportedAsIncompleteStatus) throws SQLException
	{
		
			deleteTableLabelValidationsByCrTienda(password, crTienda);
			DeleteRecordsStoreTablePromoLabels(password, crTienda);
			DeleteRecordsStoreTablePromoLabelsStatuses(password, crTienda);
			
			sqlExecute(sqlInsertPopExecutable, bd, user, password);
			sqlExecute(sqlInsertPopCompleted, bd, user, password);
			sqlExecute(sqlInsertPopReported, bd, user, password);
			sqlExecute(sqlInsertPopReportedAsIncomplete, bd, user, password);
			
			insertarPorcentajePops4Status(password, crTienda);
			
			sqlExecute(sqlInsertPopExecutableStatus, bd, user, password);
			sqlExecute(sqlInsertPopCompletedStatus, bd, user, password);
			sqlExecute(sqlInsertPopReportedStatus, bd, user, password);
			sqlExecute(sqlInsertPopReportedAsIncompleteStatus, bd, user, password);
	}
	
	
	/**
	 * Metodo que se utiliza despues del metodo 'insertarPops4Status' ya que este metodo ayuda a que el resultado de tienda este correcto al insertar los 4 pops con status diferentes
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	private void insertarPorcentajePops4Status(String password, String crTienda) throws SQLException
	{
		
		String queryInsertRecordInLabelValidationsTable = "INSERT INTO label_Validations (id, period_id, store_id, sku, photo_url, validation_day, validated_by, created_at) VALUES ('8cff915c-d3fc-4cc3-b515-840288920020', '25P8','" + crTienda + "','P8-TEST-2','photo_url1',1,'Jose Eduardo Cruz Mendez','2025-07-11 19:40:48.886857+00');";
		
		sqlExecute(queryInsertRecordInLabelValidationsTable, bd, user, password);
		
	}

	/**
	 * Metodo para mostrar los porcentages asignados de cada POP de la tienda 50GZA
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void mostrarPorcentagesPops(String password, String crTienda) throws SQLException
	{
		
		String queryCount = "SELECT COUNT(*) FROM exhibition_reports WHERE store_id = '" + crTienda + "'" + ";";
		String querySelectPercentages = "SELECT store_id, space_group, space, pop, sku, weight FROM public.promo_labels WHERE store_id = '" + crTienda + "'" + ";";
		String querySelectStatus = "SELECT store_id, sku, status FROM public.promo_label_statuses WHERE store_id = '" + crTienda + "'" + ";";

		SQLResult valorConsulta = sqlExecute(queryCount, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Conteo de Exhibition Reports: ");
		testCase.addQueryEvidenceCurrentStep(valorConsulta);
		
		SQLResult valorConsultaPercentages = sqlExecute(querySelectPercentages, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Consulta Select percentages: ");
		testCase.addQueryEvidenceCurrentStep(valorConsultaPercentages);
		
		SQLResult valorConsultaStatuses = sqlExecute(querySelectStatus, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Consulta Select statuses: ");
		testCase.addQueryEvidenceCurrentStep(valorConsultaStatuses);
		
	}
    
	/**
	 * Metodo para hacer delete la tabla label_validations basada en la tienda que mandemos en el parametro
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	public void deleteTableLabelValidationsByCrTienda(String password, String crTienda) throws SQLException
	{
		
		String queryDelete = "DELETE FROM label_validations WHERE store_id = '" + crTienda + "'";

		sqlExecute(queryDelete, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Se han eliminado los registros de la tienda " + crTienda + " de la tabla label_validations");
		
	}
	
	/**
	 * Metodo para hacer delete la tabla promo_label_statuses basada en la tienda que mandemos en el parametro
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @date 31/10/2025
	 * @throws SQLException
	 */
	public void deleteTableLabelStatusesByCrTienda(String password, String crTienda) throws SQLException
	{
		String queryDelete = "DELETE FROM public.promo_label_statuses WHERE store_id = '" + crTienda + "'";
		sqlExecute(queryDelete, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Se han eliminado los registros de la tienda " + crTienda + " de la tabla promo_label_statuses");
	}
	

	/**
	 * Metodo para borrar los registros de la tienda que enviemos en el parametro dentro de tabla promo_labels
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	private void DeleteRecordsStoreTablePromoLabels(String password, String crTienda) throws SQLException
	{
		
		String queryDelete = "DELETE FROM promo_labels WHERE store_id = '" + crTienda + "';";

		sqlExecute(queryDelete, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Se han eliminado los registros de la tienda " + crTienda + " de la tabla promo_labels");
		
	}
	
	/**
	 * Metodo para borrar los registros de la tienda que enviemos en el parametro dentro de tabla promo_labels_statuses
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @param crTienda - crTienda que se ejecutara la sentencia sql
	 * @date 09/10/2024
	 * @throws SQLException
	 */
	private void DeleteRecordsStoreTablePromoLabelsStatuses(String password, String crTienda) throws SQLException
	{
		
		String queryDelete = "DELETE FROM promo_label_statuses WHERE store_id = '" + crTienda + "';";

		sqlExecute(queryDelete, bd, user, password);
		testCase.addTextEvidenceCurrentStep("Se han eliminado los registros de la tienda " + crTienda + " de la tabla label_validations_statuses");
		
	}
	
	/**
	 * Metodo que retorna la fecha de ayer a partir del dia de hoy
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @date 09/10/2024
	 * @return Una fecha menos 1 dia y a su vez retorna la fecha con Hora segundo y minuto en 00
	 */
    private static String obtenerLaFechaDeAyerFormatoSQL() {
    	
    	// Obtener la fecha del dia de ayer
        LocalDate ayer = LocalDate.now(ZoneOffset.UTC).minusDays(1);

        // Crear un ZonedDateTime a las 00:00:00 en UTC
        ZonedDateTime fechaConHoraCero = ayer.atStartOfDay(ZoneOffset.UTC);

        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm'+00'");

        return fechaConHoraCero.format(formatter);
        
    }
    

	/**
     * Metodo que retorna una fecha 3 meses despues basada en la fecha de hoy
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @date 09/10/2024
	 * @return La fecha del dia de ayer menso 1 dia mas 3 meses y a su vez retorna la fecha con Hora segundo y minuto en 00
	 */
    private static String obtenerFechaTresMesesDespuesDesdeAyer() {
    	
    	// Obtener la fecha de ayer
        LocalDate ayer = LocalDate.now(ZoneOffset.UTC).minusDays(1).plusMonths(3);

        // Crear un ZonedDateTime a las 00:00:00 en UTC
        ZonedDateTime fechaConHoraCero = ayer.atStartOfDay(ZoneOffset.UTC);

        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm'+00'");

        return fechaConHoraCero.format(formatter);
        
    }
    

	/**
     * Metodo que retorna la fecha de dos dias despues a hoy
	 * @author Ivan Yakab
	 * @param password - Contrasena de la BD
	 * @date 09/10/2024
	 * @return la fecha del dia de hoy mas 15 dias y a su vez retorna la fecha con Hora segundo y minuto en 00
	 */
    private static String obtenerFechaQuinceDespuesAHoy() {
    	
    	// Obtener la fecha de quince dias a hoy
        LocalDate dosDiasDespues = LocalDate.now(ZoneOffset.UTC).plusDays(15);

        // Crear un ZonedDateTime a las 00:00:00 en UTC
        ZonedDateTime fechaConHoraCero = dosDiasDespues.atStartOfDay(ZoneOffset.UTC);

        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm'+00'");

        return fechaConHoraCero.format(formatter);
        
    }
    
	/**
	 * Método para ejecutar query para Insertar POP en BDD PromOxxoQA 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2026
	 * @throws Exception 
	 */	  
	public void sqlInsertPOPSinEv(String dbUsername, String dbPassword, String tienda) throws Exception {
	    try {
	    	String queryInsertPop =
	                "INSERT INTO public.promo_labels ( " +
	                "period_id, store_id, space_group, space, pop, " +
	                "pack, sku, label_content, label_name, aruco, uploaded_at, weight " +
	                ") VALUES ( " +
	                "'25P13', " +
	                "'" + tienda + "', " +
	                "'Cabeceras', " +
	                "'Cabecera 1', " +
	                "'Test POP Cabecera 1', " +
	                "'Test Pack', " +
	                "'P13-TEST-1', " +
	                "'Label content', " +
	                "'Label name', " +
	                "160, now(), 0.50 )";

	        String queryInsertStatus =
	                "INSERT INTO public.promo_label_statuses (period_id, store_id, sku, status) " +
	                "VALUES ('25P13', '" + tienda + "', 'P13-TEST-1', 'EXECUTABLE')";

	        sqlExecute(queryInsertPop, bd, dbUsername, dbPassword);
	        sqlExecute(queryInsertStatus, bd, dbUsername, dbPassword);
	        System.out.println("INSERT POP ejecutado correctamente\n");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para ejecutar query para ELIMINAR POP insertado con metodo sqlInsertarPOPSinEv() en BDD PromOxxoQA 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2026
	 * @throws Exception 
	 */	  
	public void sqlDeletePOPSinEv(String dbUsername, String dbPassword, String tienda) throws Exception {
	    try {
	        String deleteLabelValidations =
	                "DELETE FROM public.label_validations " +
	                "WHERE store_id = '" + tienda + "' " +
	                "AND sku = 'P13-TEST-1'";

	        String deleteLabelStatuses =
	                "DELETE FROM public.promo_label_statuses " +
	                "WHERE store_id = '" + tienda + "' " +
	                "AND sku = 'P13-TEST-1'";

	        String deleteLabels =
	                "DELETE FROM public.promo_labels " +
	                "WHERE store_id = '" + tienda + "' " +
	                "AND sku = 'P13-TEST-1'";

	        sqlExecute(deleteLabelValidations, bd, dbUsername, dbPassword);
	        sqlExecute(deleteLabelStatuses, bd, dbUsername, dbPassword);
	        sqlExecute(deleteLabels, bd, dbUsername, dbPassword);

	        System.out.println("\nDELETE POP ejecutado correctamente");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	};
}

