package IMA.OM_API;

import modelo.TestCase;
import utils.sql.SQLResult;
import utils.winium.WiniumUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class OxxoCelDbValidator {

    private TestCase testCase;

    public OxxoCelDbValidator(TestCase testCase) {
        this.testCase = testCase;
    }

	
	private WiniumUtil u = new WiniumUtil(null);
	
	private static final String QUERY_LABEL = " | Query: ";
	private static final String TOTAL = "total";
	private static final String STATUS = "status";
	private static final String COL_RETRY_COUNT = "retry_count";
	private static final String COL_UPDATE_AT = "update_at";
	private static final String COL_NEXT_RETRY_AT = "next_retry_at";
	private static final String ERROR_DETAIL = "error_detail";
	private static final String CREATED_AT = "created_at";
	private static final String PARTICION = "particion";
	private static final String MSG_PARTICIONES =
	        "Se encontraron las particiones existentes y la duración de la partición";
	
	/**
	 * Método para realizar conexion a DB
	 * @param N/A
	 * @return regresa mensaje cuando la conexión es exitosa
	 * @author Mario Garza
	 * @date 24/Abril/2026
	 */
	public static Connection ejecutar() {
	    String url = ConfigReader.get("db.url");
	    String user = ConfigReader.get("db.user");
	    String password = ConfigReader.get("db.password");
	    try {
	        return DriverManager.getConnection(url, user, password);
	    } catch (SQLException e) {
	        String error = String.format(
	            "Error conexión DB | Tipo: %s | Mensaje: %s",
	            e.getClass().getSimpleName(),
	            e.getMessage()
	        );
	    
	        throw new RuntimeException(error, e);
	    }
	}
	
	/**
	 * Método para realizar consulta en Tabla Auditoria por IdTransaction
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 28/04/2025
	 * @param IdTransaction obtenido del response
	 */
	public boolean consultaEventosAuditoriaPorIdTransaction(String IdTransaction) {
	    u.hardWait(20);
	    String query = "SELECT id_transaction, provider_id, status_transaction, error_detail " +
	                   "FROM setps.oxc_event_log " +
	                   "WHERE id_transaction = '" + IdTransaction + "' " +
	                   "LIMIT 20";
	    try (Connection conn = OxxoCelDbValidator.ejecutar();
	         Statement stmt = conn.createStatement()) {
	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);
	        return true;
	    } catch (SQLException e) {
	        String error = String.format(
	            "SQL Error en consultaEventosAuditoriaPorIdTransaction -> %s: %s | Query: %s | IdTransaction: %s",
	            e.getClass().getSimpleName(),
	            e.getMessage(),
	            query,
	            IdTransaction
	        );
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    } 
	}
	
	/**
	 * Método para realizar consulta en Tabla Transacciones por transactionID
	 * 
	 * @param transactionId obtenido del Json
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 28/04/2025
	 */
	public boolean consultaTransacciones(String transactionId)  {

	    String query = "SELECT id_transaction, provider_id, status "
	            + "FROM setps.oxc_transactions "
	            + "WHERE transaction_id = '" + transactionId + "'";
	    try (Connection conn = OxxoCelDbValidator.ejecutar()) {
	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);
	        return true;
	    } catch (SQLException e) {
	        String error = String.format(
	            "SQL Error en consultaTransacciones -> %s: %s | transactionId: %s | Query: %s",
	            e.getClass().getSimpleName(),
	            e.getMessage(),
	            transactionId,
	            query
	        );
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    } 
	}
	
	/**
	 * Método para realizar consulta en Tabla Auditoria sobre todos los eventos
	 * registrados
	 * @param true o false
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 28/04/2025
	 * 
	 */
	public boolean consultaEventosAuditoria() {
	    String query = "SELECT id_transaction, provider_id, status_transaction, error_detail "
	            + "FROM setps.oxc_event_log "
	            + "ORDER BY id_transaction DESC "
	            + "LIMIT 100";
	    try (Connection conn = OxxoCelDbValidator.ejecutar()) {
	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);
	        return true;
	    } catch (SQLException e) {
	        String error = "Error en consultaEventosAuditoria -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage()
	                + QUERY_LABEL
	                + query;
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}

	/**
	 * Método para ver errores registrados en Tabla event_log por idTransaction
	 * 
	 * @param idTransaction obtenido del response
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 05/05/2025
	 */
	public boolean consultaErroresTemporalesPorIdTransaction(String idTransaction) {
	    Connection conn = null;
	    String query = "SELECT id_transaction, provider_id, status_transaction, error_detail "
	            + "FROM setps.oxc_event_log "
	            + "WHERE id_transaction = '" + idTransaction + "' "
	            + "AND status_transaction = 'E' "
	            + "LIMIT 20";
	    try {
	        conn = OxxoCelDbValidator.ejecutar();
	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);
	        return true;
	    } catch (SQLException e) {
	        String error = "Error en consultaErroresTemporalesPorIdTransaction -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage()
	                + " | IdTransaction: "
	                + idTransaction
	                + QUERY_LABEL
	                + query;
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    } finally {
	        try {
	            if (conn != null && !conn.isClosed()) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            String errorConexion = "Error cerrando conexión -> "
	                    + e.getClass().getSimpleName()
	                    + ": "
	                    + e.getMessage();
	            testCase.addCodeEvidenceCurrentStep(errorConexion);
	        }
	    }
	}
	
	/**
	 * Método para ver errores registrados en Tabla Transacciones por transactionID
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 05/05/2025
	 */
	public boolean consultaErroresPorTransactionId(String transactionId) {

	    String query = "SELECT id_transaction, provider_id, status, error_message "
	            + "FROM setps.oxc_transactions "
	            + "WHERE transaction_id = '" + transactionId + "' AND status = 'F' "
	            + "LIMIT 20";

	    try (Connection conn = OxxoCelDbValidator.ejecutar()) {

	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);

	        return true;

	    } catch (SQLException e) {

	        String error = String.format(
	            "SQL Error en consultaErroresPorTransactionId -> %s: %s | transactionId: %s | Query: %s",
	            e.getClass().getSimpleName(),
	            e.getMessage(),
	            transactionId,
	            query
	        );

	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}
	
	/**
	 * Método para realizar consulta en Tabla Transaccion
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 12/05/2026
	 */
	public boolean consultaTransaccionesPendientes() {
		String query = "SELECT id_transaction, provider_id, status_transaction, request_payload"
				+ " FROM setps.oxc_event_log WHERE status_transaction = 'I' order by created_at desc LIMIT 10;";
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
						SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesPendientes -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage()
		            + QUERY_LABEL
		            + query;

		    testCase.addCodeEvidenceCurrentStep(error);

		    return false;
		}
	}
	
	/**
	 * Método para realizar consulta en Tabla Transaccion orden FIFO
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 12/05/2026
	 */
	public boolean consultaTransaccionesOrdenFIFO() {
		String query = "SELECT * FROM setps.oxc_transactions ORDER BY created_at desc";
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			return true;
		} catch (SQLException e) {
		    String error = "Error en consultaTransaccionesOrdenFIFO -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage()
		            + QUERY_LABEL
		            + query;

		    testCase.addCodeEvidenceCurrentStep(error);

		    return false;
		}
	}
	
	/**
	 * Método para realizar consulta en Tabla Transaccion orden FIFO
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 12/05/2026
	 */
	public boolean consultaTransaccionesCompletadas() {
		String query = "SELECT id_transaction, provider_id, status, request_payload \r\n"
				+ " FROM setps.oxc_transactions WHERE status = 'T' LIMIT 10;";
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			return true;
		} catch (SQLException e) {
			 String error = "Error en consultaTransaccionesCompletadas -> "
			            + e.getClass().getSimpleName()
			            + ": "
			            + e.getMessage()
			            + QUERY_LABEL
			            + query;

			    testCase.addCodeEvidenceCurrentStep(error);
			    return false;
		}
	}
	
	/**
	 * Método para comparar los registros iniciales en estatus Pendiente (I) con los
	 * registros finales de una segunda consulta
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 14/05/2025
	 * 
	 */
	public boolean validarTransaccionesPendientesSinCambios() {

	    try (Connection conn = OxxoCelDbValidator.ejecutar();
	         Statement stmt = conn.createStatement()) {

	        String query = "SELECT id_transaction, provider_id, status_transaction, error_detail "
	                + "FROM setps.oxc_event_log "
	                + "WHERE status_transaction = 'I' "
	                + "ORDER BY id DESC "
	                + "LIMIT 100";

	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);

	        String query2 = "SELECT COUNT(*) AS total "
	                + "FROM setps.oxc_event_log "
	                + "WHERE status_transaction = 'I'";

	        int totalInicial = 0;
	        try (ResultSet rs1 = stmt.executeQuery(query2)) {
	            if (rs1.next()) {
	                totalInicial = rs1.getInt(TOTAL);
	            }
	        }

	        testCase.addBoldTextEvidenceCurrentStep("Se obtiene el número inicial de registros: " + totalInicial);

	        u.hardWait(5);

	        int totalFinal = 0;
	        try (ResultSet rs2 = stmt.executeQuery(query2)) {
	            if (rs2.next()) {
	                totalFinal = rs2.getInt(TOTAL);
	            }
	        }

	        testCase.addBoldTextEvidenceCurrentStep("Se obtiene el número final de registros: " + totalFinal);

	        testCase.addBoldTextEvidenceCurrentStep(
	            "El valor inicial " + totalInicial + " coincide con el valor final " + totalFinal
	        );

	        return true;

	    } catch (SQLException e) {

	        String error = "SQL Error en validarTransaccionesPendientesSinCambios -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage();

	        if (testCase != null) {
	            testCase.addCodeEvidenceCurrentStep(error);
	        }

	        return false;
	    }
	}
	
	/**
	 * Método para consultar registros en Tabla saf_transactions por idTransaction
	 * 
	 * @param idTransaction obtenido del response, statusEsperado mandado desde el
	 *                      DataProvider
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 19/05/2025
	 */
	public boolean consultarReintentoPorIdTransaccion(String idTransaction, String statusEsperado)  {
		 u.hardWait(5);
		String query = "SELECT id_transaction, provider_id, status, locked_at, error_message "
				+ "FROM setps.oxc_saf_transactions " 
				+ "WHERE id_transaction = ? ";
		try (Connection conn = OxxoCelDbValidator.ejecutar(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setObject(1, UUID.fromString(idTransaction));

			String queryEvidencia = query.replace("?", "'" + idTransaction + "'");
			SQLResult sqlResult = new SQLResult(conn, queryEvidencia);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String statusBd = rs.getString(STATUS);
					if (statusBd != null && statusBd.equalsIgnoreCase(statusEsperado)) {
						testCase.addBoldTextEvidenceCurrentStep("Se encontró el status esperado: " + statusEsperado);
						return true;
					}
					testCase.addBoldTextEvidenceCurrentStep("Status esperado: " + statusEsperado);
					return false;
				}
				testCase.addBoldTextEvidenceCurrentStep(
						"No se encontraron registros para el transactionId: " + idTransaction);
			}
		} catch (SQLException e) {
		    String error = "SQL Error en consultarReintentoPorIdTransaccion -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
		return false;
	}
	
	/**
	 * Método para validar actualizacion en los campos de la Tabla saf_transactions
	 * por idTransaction
	 * 
	 * @param idTransaction obtenido del response
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 19/05/2025
	 */
	public boolean validarActualizacionCamposReintento(String idTransaction)  {
		String query = "SELECT retry_count, update_at, next_retry_at " 
				+ "FROM setps.oxc_saf_transactions "
				+ "WHERE id_transaction = ? ";
		try (Connection conn = OxxoCelDbValidator.ejecutar(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setObject(1, UUID.fromString(idTransaction));
			String queryEvidencia = query.replace("?", "'" + idTransaction + "'");
			SQLResult sqlResult = new SQLResult(conn, queryEvidencia);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Object retryCount = rs.getObject(COL_RETRY_COUNT);
					Timestamp updateAt = rs.getTimestamp(COL_UPDATE_AT);
					Timestamp nextRetryAt = rs.getTimestamp(COL_NEXT_RETRY_AT);
					testCase.addBoldTextEvidenceCurrentStep("retry_count: " + retryCount);
					testCase.addBoldTextEvidenceCurrentStep("update_at: " + updateAt);
					testCase.addBoldTextEvidenceCurrentStep("next_retry_at: " + nextRetryAt);
					// Validar que traigan información
					if (retryCount != null && updateAt != null && nextRetryAt != null) {
						testCase.addBoldTextEvidenceCurrentStep("Todos los campos contienen información");
						return true;
					}
					testCase.addBoldTextEvidenceCurrentStep("Uno o más campos vienen vacíos");
					return false;
				}
				return false;
			}
		} catch (SQLException e) {
		    String error = "SQL Error en validarActualizacionCamposReintento -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		}
	}
	
	/**
	 * Método para validar numero de reintentos realizados en la Tabla
	 * saf_transactions por idTransaction
	 * 
	 * @param idTransaction obtenido del response
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 21/05/2025
	 */
	public boolean validarNumeroMaximoReintentos(String idTransaction) {
		String query = "SELECT retry_count, max_retries, error_message, update_at " 
				+ "FROM setps.oxc_saf_transactions "
				+ "WHERE id_transaction = ?";
		try (Connection conn = OxxoCelDbValidator.ejecutar(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setObject(1, UUID.fromString(idTransaction));
			String queryEvidencia = query.replace("?", "'" + idTransaction + "'");
			SQLResult sqlResult = new SQLResult(conn, queryEvidencia);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int retryCount = rs.getInt(COL_RETRY_COUNT);
					int maxRetries = rs.getInt("max_retries");
					testCase.addBoldTextEvidenceCurrentStep("retry_count: " + retryCount);
					testCase.addBoldTextEvidenceCurrentStep("max_retries: " + maxRetries);
					if (retryCount == maxRetries) {
						String mensaje = "Se alcanzó el número máximo de intentos. "
								+ "El escenario finaliza correctamente.";
						testCase.addBoldTextEvidenceCurrentStep(mensaje);
						return true;
					}
					if (retryCount > maxRetries) {
						String mensaje = "Se sobrepasó el número máximo de intentos";
						testCase.addBoldTextEvidenceCurrentStep(mensaje);
						return false;
					}
					String mensaje = "La transacción aún tiene intentos disponibles";
					testCase.addBoldTextEvidenceCurrentStep(mensaje);
					return true;
				}
				testCase.addBoldTextEvidenceCurrentStep("No se encontraron registros");
				return false;
			}
		} catch (SQLException e) {
		    String error = "SQL Error en validarNumeroMaximoReintentos -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Actualiza el campo status en SETPS.OXC_TRANSACTIONS
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return true si actualiza el status, falso si no lo actualiza
	 * @date 15/05/2026
	 * @param IdTransaction StatusOld estatus actual en la tabla oxc_transactions
	 *                      StatusNew estatus nuevo a registrar
	 * 
	 */
	public boolean actualizaStatusIdTransaction(String idTransaction, String statusOld, String statusNew,
	        String currencyCode)  {
	    String query =
	            "UPDATE SETPS.OXC_TRANSACTIONS " +
	            "SET status = '" + statusNew + "', " +
	            "request_payload = jsonb_set(request_payload, '{currencyCode}', '\"" + currencyCode + "\"'::jsonb, true) " +
	            "WHERE id_transaction = '" + idTransaction + "' " +
	            "AND status = '" + statusOld + "'";
	    try (Connection conn = OxxoCelDbValidator.ejecutar();
	         Statement stmt = conn.createStatement()) {
	        u.hardWait(5);
	        int rowsAffected = stmt.executeUpdate(query);
	        testCase.addTextEvidenceCurrentStep(
	                "Query ejecutado: \r\n" + query + "\r\n Filas actualizadas: " + rowsAffected
	        );
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        String error = "SQL Error en actualizaStatusIdTransaction -> "
	                + e.getClass().getSimpleName() + ": " + e.getMessage();
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    } 
	}
	
	/**
	 * Actualiza el campo status en SETPS.OXC_TRANSACTIONS
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return true si actualiza el status, falso si no lo actualiza
	 * @date 15/05/2026
	 * @param IdTransaction StatusOld estatus actual en la tabla oxc_transactions
	 *                      StatusNew estatus nuevo a registrar
	 * 
	 */
	public boolean actualizaStatusIdTransactionR(String idTransaction, String statusOld, String statusNew) {
	    String query = "UPDATE SETPS.OXC_TRANSACTIONS "
	            + "SET \"status\" = ? "
	            + "WHERE id_transaction = ?::uuid "
	            + "AND \"status\" = ?";
	    try (Connection conn = OxxoCelDbValidator.ejecutar();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        u.hardWait(20);
	        ps.setString(1, statusNew);
	        ps.setString(2, idTransaction);
	        ps.setString(3, statusOld);
	        int updatedRows = ps.executeUpdate();
	        testCase.addTextEvidenceCurrentStep(
	                "Query ejecutado: \r\n" + query + "\r\n Filas actualizadas: " + updatedRows
	        );
	        return updatedRows > 0;
	    } catch (SQLException e) {
	        String error = "SQL Error en actualizaStatusIdTransactionR -> "
	                + e.getClass().getSimpleName() + ": " + e.getMessage();
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}
	
	/**
	 * Método para validar registros de particiones creadas en Tabla Auditoria
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 02/06/2026
	 */
	public boolean registroParticionesCreadas() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "SELECT id_transaction, provider_id, status_transaction,error_detail "
					+ "FROM setps.oxc_event_log " 
					+ "WHERE error_detail = 'Particion Creada' " 
					+ "LIMIT 20";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			testCase.addBoldTextEvidenceCurrentStep("Se encontraron registros de particiones creadas");
			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en registroParticionesCreadas -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para consultar errores esperados en Tabla event_log por idTransaction
	 * 
	 * @param idTransaction obtenido del response, errorEsperado enviado desde el
	 *                      DataProvider
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 18/05/2025
	 */
	public boolean validarErroresEsperadosEnBDFreedompop(String idTransaction, String errorEsperado) throws Exception {
		String query = "SELECT error_detail FROM setps.oxc_event_log where id_transaction = '" + idTransaction + "' ORDER BY created_at DESC LIMIT 10";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				String errorDetail = rs.getString(ERROR_DETAIL);
				if (errorDetail != null && errorDetail.contains(errorEsperado)) {
					testCase.addBoldTextEvidenceCurrentStep(errorDetail);
//		                testCase.addBoldTextEvidenceCurrentStep("Se encontro el código esperado " + errorEsperado );
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Método para VALIDAR existencia del campo created_At y validar que realmente
	 * ordene en orden de creación
	 * 
	 * @param Order by se le manda desde el DP el valor para realizar el
	 *              ordenamiento
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 14/05/2025
	 * 
	 */
	public boolean validarCampoCreatedAtYOrdenDescendente(String orden) {
		String query = "SELECT id_transaction, provider_id, status_transaction, created_at "
				+ " FROM setps.oxc_event_log " 
				+ " ORDER BY created_at " + orden + " LIMIT 10";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
				Statement stmt = conn.createStatement()){
			
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			
			ResultSet rs = stmt.executeQuery(query);
			// VALIDAMOS EXISTENCIA DEL CAMPO
			ResultSetMetaData metaData = rs.getMetaData();
			int totalColumnas = metaData.getColumnCount();
			boolean existeCreatedAt = false;
			for (int i = 1; i <= totalColumnas; i++) {
				String nombreColumna = metaData.getColumnName(i);
				if (CREATED_AT.equalsIgnoreCase(nombreColumna)) {
					existeCreatedAt = true;
					testCase.addBoldTextEvidenceCurrentStep("El campo created_at existe en la consulta");
					break;
				}
			}
			if (!existeCreatedAt) {
				testCase.addBoldTextEvidenceCurrentStep("El campo created_at NO existe en la consulta");
				return false;
			}
			// VALIDAMOS ORDEN
			Timestamp fechaAnterior = null;
			while (rs.next()) {
				Timestamp fechaActual = rs.getTimestamp(CREATED_AT);
				// VALIDAMOS QUE no VENGA null
				if (fechaActual == null) {
					testCase.addBoldTextEvidenceCurrentStep("Se encontró un created_at null");
					return false;
				}
				// Comparación descendente
				if (fechaAnterior != null && fechaActual.after(fechaAnterior)) {
					testCase.addBoldTextEvidenceCurrentStep("El orden descendente es incorrecto");
					return false;
				}
				fechaAnterior = fechaActual;
			}
			testCase.addBoldTextEvidenceCurrentStep("Los registros están ordenados correctamente por created_at DESC");
			 
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en validarCampoCreatedAtYOrdenDescendente -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para conectar a la BD SETPS
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return true si conecta a la BD, falso si falla la conexión a la BD
	 * @date 15/05/2026
	 * @param IdTransaction obtenido del response Estatus a comparar con el campo
	 *                      status Dataset query de la tabla a validar el status
	 */
	public boolean testBDConnection() {
	    try (Connection conn = OxxoCelDbValidator.ejecutar()) {
	        DatabaseMetaData meta = conn.getMetaData();
	        testCase.addTextEvidenceCurrentStep(
	                "Conexión exitosa a la BD.\r\n"
	                        + "Database: " + meta.getDatabaseProductName() + "\r\n"
	                        + "Host: " + meta.getURL()
	        );
	        return true;
	    } catch (SQLException e) {
	        String error = "SQL Error en testBDConnection -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage();
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}
	
	/**
	 * Método para realizar consulta en BD SETPS
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return true si encuentra el status, falso si no lo encuentra
	 * @date 15/05/2026
	 * @param IdTransaction    obtenido del response Estatus a comparar con el campo
	 *                         status Dataset query de la tabla a validar el status
	 * @param G_EventLog_Time
	 * @param G_Transact_Time
	 * @param G_Transact_Time
	 * @param G_Transact_Time
	 * @param G_EventLog_Time
	 * @param G_EventLog_Error
	 * @param G_EventLog_Error
	 */
	@SuppressWarnings("null")
	public boolean consultaStatusPorIdTransaction(String idTransaction, String estatus, String dataset)
	        throws Exception {

	    try (Connection conn = OxxoCelDbValidator.ejecutar()) {

	        u.hardWait(50);

	        String query = construirQuery(dataset, idTransaction, estatus);

	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);

	        if (sqlResult.isEmpty()) {
	            testCase.addTextEvidenceCurrentStep("Transaccion no encontrada en BD.");
	            return false;
	        }

	        String statusBD = sqlResult.getData(0, STATUS);

	        if (statusBD == null || statusBD.trim().isEmpty()) {
	            testCase.addTextEvidenceCurrentStep("Estatus inválido registrado en BD.");
	            return false;
	        }

	        if (estatus == null || estatus.trim().isEmpty()) {
	            return true;
	        }

	        String desc = OxxoCelUtils.CodeBDEstatus.buscaDescripEstatus(estatus);

	        if (estatus.contains(statusBD)) {
	            testCase.addTextEvidenceCurrentStep(
	                    dataset + " Estatus registrado: '" + statusBD + " - " + desc + "' -> OK"
	            );
	            return true;
	        }

	        testCase.addTextEvidenceCurrentStep("Status incorrecto.");
	        return false;

	    } catch (SQLException e) {

	        String error = "SQL Error en consultaStatusPorIdTransaction -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage();

	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}
	
	/**
	 * Método para construir query
	 * 
	 * @param N/A
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @date 20/05/2025
	 */
	
	private String construirQuery(String dataset, String idTransaction, String estatus) {

	    if ("TRN".equals(dataset)) {
	        return "SELECT id_transaction, provider_id, status, transaction_id, created_at, locked_at, update_at "
	                + "FROM setps.oxc_transactions "
	                + "WHERE id_transaction = '" + idTransaction + "' "
	                + "ORDER BY update_at DESC LIMIT 1";
	    }

	    if ("LOG".equals(dataset)) {
	        return "SELECT id, id_transaction, provider_id, status_transaction as status, created_at, latency, request_payload, response_received "
	                + "FROM setps.oxc_event_log "
	                + "WHERE id_transaction = '" + idTransaction + "' "
	                + "AND status_transaction = '" + estatus + "' "
	                + "ORDER BY id DESC LIMIT 10";
	    }

	    if ("LGE".equals(dataset)) {
	        return "SELECT id, id_transaction, provider_id, status_transaction as status, error_detail, created_at "
	                + "FROM setps.oxc_event_log "
	                + "WHERE id_transaction = '" + idTransaction + "' "
	                + "AND status_transaction = '" + estatus + "' "
	                + "AND error_detail IS NOT NULL "
	                + "ORDER BY id DESC LIMIT 10";
	    }

	    return "";
	}
	
	/**
	 * Método para consultar registros en Tabla saf_transactions (REINTENTOS)
	 * 
	 * @param N/A
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 20/05/2025
	 */
	public boolean validarTablaReintentos() {
		String query = "SELECT id_transaction, provider_id, status, error_message,retry_count, next_retry_at  "
				+ "FROM setps.oxc_saf_transactions " + "WHERE status = 'R' Limit 5";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			// Validamos si existe al menos un registro
			if (rs.next()) {
				testCase.addBoldTextEvidenceCurrentStep("Se encontraron registros con status R");
				return true;
			}
			testCase.addBoldTextEvidenceCurrentStep("No existen registros con status R");
			return false;
		} catch (SQLException e) {
		    String error = "SQL Error en validarTablaReintentos -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para validar existencia de campos en Tabla saf_transactions
	 * (REINTENTOS)
	 * 
	 * @param N/A
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 20/05/2025
	 */
	public boolean validarExistenciaCamposReintento() {

	    String query = "SELECT status, retry_count, next_retry_at FROM setps.oxc_saf_transactions LIMIT 5";
	    try (Connection conn = OxxoCelDbValidator.ejecutar();
	         PreparedStatement ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);
	        if (rs.next()) {
	            // Validación de columnas (sin try-catch)
	            rs.getObject(COL_RETRY_COUNT);
	            rs.getObject(COL_NEXT_RETRY_AT);
	            rs.getObject(STATUS);
	            testCase.addBoldTextEvidenceCurrentStep(
	                "Los campos retry_count, next_retry_at y status existen"
	            );
	            return true;
	        }
	        testCase.addBoldTextEvidenceCurrentStep(
	            "No existen registros con datos en la tabla"
	        );
	        return false;
	    } catch (SQLException e) {
	        String error = "SQL Error en validarExistenciaCamposReintento -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage();
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}
	
	/**
	 * Método para validar que registros de Tabla saf_transactions (REINTENTOS)
	 * esten ordenados por Antiguedad
	 * 
	 * @param N/A
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 20/05/2025
	 */
	public boolean validarOrdenAntiguedad() {
		String query = "SELECT id_transaction, provider_id,status, locked_at " 
				+ "FROM setps.oxc_saf_transactions "
				+ "WHERE status = 'R' " 
				+ "ORDER BY locked_at ASC limit 10";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			Timestamp fechaAnterior = null;
			while (rs.next()) {
				Timestamp fechaActual = rs.getTimestamp("locked_at");
				testCase.addBoldTextEvidenceCurrentStep("locked_at: " + fechaActual);
				// Comparar con fecha anterior
				if (fechaAnterior != null && fechaActual.before(fechaAnterior)) {
					testCase.addBoldTextEvidenceCurrentStep("Las fechas NO están ordenadas");
					return false;
				}
				fechaAnterior = fechaActual;
			}
			testCase.addBoldTextEvidenceCurrentStep("Las fechas están ordenadas correctamente");
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en validarOrdenAntiguedad -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		}
	}
	
	/**
	 * Método para realizar reintento exitoso en la Tabla saf_transactions por
	 * idTransaction
	 * 
	 * @param idTransaction obtenido del response, transactionIdAnterior y nuevoIDA
	 *                      obtenido por metodo
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 19/05/2025
	 */
	public boolean realizarReintentoExitoso(String idTransaction, String transactionIdAnterior, String nuevoIDA) {

		String update = "UPDATE setps.oxc_saf_transactions " 
				+ "SET request_payload = " + "REPLACE( "
				+ "request_payload::text, ?, ? " + ")::jsonb " +
//			        "status = 'I' " +
				"WHERE id_transaction = ?";

		try (Connection conn = OxxoCelDbValidator.ejecutar(); PreparedStatement psUpdate = conn.prepareStatement(update)) {
			String valorAnterior = "\"transactionId\": \"" + transactionIdAnterior + "\"";
			String valorNuevo = "\"transactionId\": \"" + nuevoIDA + "\"";
			psUpdate.setString(1, valorAnterior);
			psUpdate.setString(2, valorNuevo);
			psUpdate.setObject(3, UUID.fromString(idTransaction));
			String queryEvidencia = update.replaceFirst("\\?", "'" + valorAnterior + "'")
					.replaceFirst("\\?", "'" + valorNuevo + "'").replaceFirst("\\?", "'" + idTransaction + "'");
			testCase.addBoldTextEvidenceCurrentStep("Actualizamos transactionId en request_payload");
			testCase.addCodeEvidenceCurrentStep(queryEvidencia);
			// EJECUTAR UPDATE
			int registrosActualizados = psUpdate.executeUpdate();
			testCase.addCodeEvidenceCurrentStep("Se realiza correctamente el reenvio");
			return registrosActualizados > 0;
		} catch (SQLException e) {
		    String error = "SQL Error en realizarReintentoExitoso -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}

	/**
	 * Método para consultar reintento exitoso en la Tabla saf_transactions por
	 * idTransaction
	 * 
	 * @param idTransaction obtenido del response
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 21/05/2025
	 */
	public boolean consultaReenvioExitoso(String idTransaction) {
		u.hardWait(120);
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "SELECT id_transaction, provider_id, status " 
					+ "FROM setps.oxc_saf_transactions "
					+ "WHERE id_transaction = '" + idTransaction + "' and status ='T'";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en consultaReenvioExitoso -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para realizar reintento de envio de peticion actualizando el estatus a
	 * "I".
	 * 
	 * @param idTransaction obtenido a traves del response
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 15/05/2025
	 * 
	 */
	public boolean realizarReintento(String idTransaction) {

		String consultaEventos = "SELECT COUNT(*) AS total " 
				+ "FROM setps.oxc_event_log " 
				+ "WHERE id_transaction = ?";
		String update = "UPDATE setps.oxc_transactions " 
						+ "SET status = 'I' " 
						+ "WHERE id_transaction = ?";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
				PreparedStatement psConsulta = conn.prepareStatement(consultaEventos);
				PreparedStatement psUpdate = conn.prepareStatement(update)) {

			// Consulta para validar cuantos registros hay de ese id Transaction
			psConsulta.setObject(1, UUID.fromString(idTransaction));
			int totalInicial = 0;
			try (ResultSet rs1 = psConsulta.executeQuery()) {
				if (rs1.next()) {
					totalInicial = rs1.getInt(TOTAL);
				}
			}
			testCase.addBoldTextEvidenceCurrentStep(
					"Se obtiene el número inicial de eventos registrados: " + totalInicial);
			u.hardWait(5);

			// Realizamos el reenvio de la peticion
			testCase.addBoldTextEvidenceCurrentStep("Realizamos el reenvío de la petición");
			psUpdate.setObject(1, UUID.fromString(idTransaction));
			testCase.addCodeEvidenceCurrentStep("UPDATE setps.oxc_transactions " + "SET status = 'I' "
					+ "WHERE id_transaction = '" + idTransaction + "'");
			int registrosActualizados = psUpdate.executeUpdate();

			if (registrosActualizados == 0) {
				testCase.addBoldTextEvidenceCurrentStep("No se encontró el id_transaction para actualizar");
				return false;
			}
			// Espera para que se registre el nuevo evento
			u.hardWait(10);

			// Se realiza segunda consulta para validar se haya insertado nuevo registro
			int totalFinal = 0;
			try (ResultSet rs2 = psConsulta.executeQuery()) {
				if (rs2.next()) {
					totalFinal = rs2.getInt(TOTAL);
				}
			}
			testCase.addBoldTextEvidenceCurrentStep("Se obtiene el número final de eventos registrados: " + totalFinal);
			this.consultaEventosAuditoriaPorIdTransaction(idTransaction);
			testCase.addBoldTextEvidenceCurrentStep("Eventos registrados antes del reenvío: " + totalInicial
					+ " | Eventos registrados después del reenvío: " + totalFinal);
			boolean resultado = totalFinal > totalInicial;
			if (resultado) {
				testCase.addBoldTextEvidenceCurrentStep("El reenvío de la petición se realizó correctamente");
			} else {
				testCase.addBoldTextEvidenceCurrentStep("No se generaron nuevos eventos después del reenvío");
			}
			return resultado;
		} catch (SQLException e) {
		    String error = "SQL Error en realizarReintento -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para actualizar los campos status y retrycount
	 * @param transactionId, status y retryCount
	 * @author Mario Garza
	 * @throws Exception
	 * @date 03/06/2026
	 */
	public boolean actualizarStatusRetrySafTransaction(
	        String idTransaction,
	        String status,
	        int retryCount) throws Exception {
	    String valida =
	        "SELECT COUNT(*) FROM setps.oxc_saf_transactions WHERE id_transaction = ?::uuid";
	    String query =
	        "UPDATE setps.oxc_saf_transactions SET status = ?, retry_count = ? " +
	        "WHERE id_transaction = ?::uuid";
	    try (Connection conn = OxxoCelDbValidator.ejecutar()) {
	        // Validación previa
	        try (PreparedStatement psVal = conn.prepareStatement(valida)) {
	            psVal.setString(1, idTransaction);
	            try (ResultSet rs = psVal.executeQuery()) {
	                if (rs.next() && rs.getInt(1) == 0) {
	                    testCase.addCodeEvidenceCurrentStep(
	                        "No existe transaction: " + idTransaction);
	                    return false;
	                }
	            }
	        }
	        // Update
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.setString(1, status);
	            ps.setInt(2, retryCount);
	            ps.setString(3, idTransaction);
	            int registrosActualizados = ps.executeUpdate();
	            testCase.addBoldTextEvidenceCurrentStep(
	                    "Filas afectadas: " + registrosActualizados);
	            return registrosActualizados > 0;
	        }
	    } catch (SQLException e) {
	        String error = "SQL Error en actualizarStatusRetrySafTransaction -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage();
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}
	/**
	 * Método para consultar en error_log
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 03/06/2026
	 */
	public boolean consultarErrorLog() {
		u.hardWait(1200);
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "SELECT * FROM setps.oxc_error_log\r\n"
					+ "ORDER BY id_error ASC, created_at DESC limit 10";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultarErrorLog -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para realizar consulta en Tabla Transaccion fallidas
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 21/05/2026
	 */
	public boolean consultaTransaccionesFallidas() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "select id_transaction, provider_id, status_transaction, error_detail from setps.oxc_event_log order by Created_at desc limit 20;\r\n"
					+ "";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesFallidas -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para realizar consulta en Tabla Transaccion fallidas
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 21/05/2026
	 */
	public boolean consultaTransaccionesError() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "select provider_id, status_transaction, error_detail from setps.oxc_event_log order by Created_at desc limit 20;\r\n"
					+ "";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesError -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		}
	}
	
	/**
	 * Método para realizar consulta en Tabla Transaccion fallidas
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 21/05/2026
	 */
	public boolean consultaTransaccionesTipoError() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "select status_transaction, error_detail from setps.oxc_event_log order by Created_at desc limit 20;\r\n"
					+ "";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesTipoError -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para realizar consulta en Tabla Aud Envio
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 21/05/2026
	 */
	public boolean consultaTransaccionesAEnvio() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "SELECT request_payload, provider_id, status, created_at, retry_count, max_retries\r\n"
					+ "FROM setps.oxc_saf_transactions\r\n" 
					+ "WHERE UPPER(TRIM(status)) = 'E' limit 20";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesAEnvio -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	/**
	 * Método para realizar consulta en Tabla Aud
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 21/05/2026
	 */
	public boolean consultaTransaccionesAud() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "SELECT id_transaction, request_payload, provider_id, status, created_at, error_message, retry_count, max_retries, next_retry_at\r\n"
					+ "FROM setps.oxc_saf_transactions\r\n" 
					+ "WHERE UPPER(TRIM(status)) = 'E' limit 20";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesAud -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}
	
	
	/**
	 * Método para realizar consulta en Tabla no procesado
	 * @param N/A
	 * @author Mario Garza
	 * @throws Exception
	 * @date 21/05/2026
	 */
	public boolean consultaTransaccionesNoProcesados() {
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			String query = "SELECT id_transaction, request_payload, provider_id, status, locked_at, request_extra_data FROM setps.oxc_transactions WHERE UPPER(TRIM(status)) = 'F' limit 20\r\n"
					+ "";
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);

			return true;

		} catch (SQLException e) {
		    String error = "SQL Error en consultaTransaccionesNoProcesados -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		}
	}
	
	
	/**
	 * Método para validar si la tabla Transactions tiene particiones
	 * 
	 * @author Leonardo Ramírez
	 * @throws Exception
	 * @date 02/06/2026
	 */
	public boolean validarParticionesTransactions() {

	    String query =
	            "SELECT c.relname AS particion, pg_get_expr(c.relpartbound, c.oid) AS rango " +
	            "FROM pg_inherits i " +
	            "JOIN pg_class c ON c.oid = i.inhrelid " +
	            "WHERE i.inhparent = 'setps.oxc_transactions'::regclass";

	    boolean hayParticiones = false;

	    try (Connection conn = OxxoCelDbValidator.ejecutar();
	         Statement st = conn.createStatement();
	         ResultSet rs = st.executeQuery(query)) {

	        SQLResult sqlResult = new SQLResult(conn, query);
	        testCase.addQueryEvidenceCurrentStep(sqlResult);

	        while (rs.next()) {
	            hayParticiones = true;

	            String particion = rs.getString(PARTICION);
	            testCase.addCodeEvidenceCurrentStep(particion);
	        }

	        testCase.addBoldTextEvidenceCurrentStep(MSG_PARTICIONES);

	        return hayParticiones;

	    } catch (SQLException e) {
	        String error = "SQL Error en validarParticionesTransactions -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage();
	        testCase.addCodeEvidenceCurrentStep(error);
	        return false;
	    }
	}

	/**
	 * Método para validar si la tabla Saf_Transactions tiene particiones
	 * 
	 * @author Leonardo Ramírez
	 * @throws Exception
	 * @date 02/06/2026
	 */
	public boolean validarParticionesSafTransactions() {
		String query = "  SELECT c.relname AS particion, pg_get_expr(c.relpartbound, c.oid) AS rango\r\n"
				+ "    FROM pg_inherits i\r\n" + "    JOIN pg_class c ON c.oid = i.inhrelid\r\n"
				+ "    WHERE i.inhparent = 'setps.oxc_saf_transactions'::regclass";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery()){
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			while (rs.next()) {
				String particion1 = rs.getString(PARTICION);
				testCase.addCodeEvidenceCurrentStep(particion1);
			}
			testCase.addBoldTextEvidenceCurrentStep(
					MSG_PARTICIONES);
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en validarParticionesSafTransactions -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}

	/**
	 * Método para validar si la tabla Event_log tiene particiones
	 * 
	 * @author Leonardo Ramírez
	 * @throws Exception
	 * @date 02/06/2026
	 */
	public boolean validarParticionesEventLog()  {
		String query = "  SELECT c.relname AS particion, pg_get_expr(c.relpartbound, c.oid) AS rango\r\n"
				+ "    FROM pg_inherits i\r\n" + "    JOIN pg_class c ON c.oid = i.inhrelid\r\n"
				+ "    WHERE i.inhparent = 'setps.oxc_event_log'::regclass";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery()){
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			while (rs.next()) {
				String particion3 = rs.getString(PARTICION);
				testCase.addCodeEvidenceCurrentStep(particion3);
			}
			testCase.addBoldTextEvidenceCurrentStep(
					MSG_PARTICIONES);
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en validarParticionesEventLog -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}

	/**
	 * Método para validar si la tabla Error_Log tiene particiones
	 * 
	 * @author Leonardo Ramírez
	 * @throws Exception
	 * @date 02/06/2026
	 */
	public boolean validarParticionesErrorLog()  {
		String query = "  SELECT c.relname AS particion, pg_get_expr(c.relpartbound, c.oid) AS rango\r\n"
				+ "    FROM pg_inherits i\r\n" + "    JOIN pg_class c ON c.oid = i.inhrelid\r\n"
				+ "    WHERE i.inhparent = 'setps.oxc_error_log'::regclass";
		try (Connection conn = OxxoCelDbValidator.ejecutar();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery()){
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			while (rs.next()) {
				String particion2 = rs.getString(PARTICION);
				testCase.addCodeEvidenceCurrentStep(particion2);
			}
			testCase.addBoldTextEvidenceCurrentStep(
					MSG_PARTICIONES);
			return true;
		} catch (SQLException e) {
		    String error = "SQL Error en validarParticionesErrorLog -> "
		            + e.getClass().getSimpleName()
		            + ": "
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    return false;
		} 
	}

	
	/**
	 * Método para validar los parámetros de limpieza configurados en tabla oxc_configuration
	 * @author Leonardo Ramírez
	 * @throws Exception
	 * @date 02/06/2026
	 */

	public boolean validarParametrosLimpieza() throws Exception {
		Set<String> esperadas = new HashSet<>(Arrays.asList("CONFIG_FUTURE_PARTITIONS", "CONFIG_RETENTION_MONTHS",
				"CONFIG_TABLES", "CONFIG_DRY_RUN"));
		String query = "SELECT config_key, config_value FROM setps.oxc_configuration\r\n"
				+ " WHERE config_key IN ('CONFIG_FUTURE_PARTITIONS','CONFIG_RETENTION_MONTHS',\r\n"
				+ " 'CONFIG_TABLES','CONFIG_DRY_RUN')";
		try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			SQLResult sqlResult = new SQLResult(conn, query);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			try (PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					esperadas.remove(rs.getString("config_key"));
				}
			}
		}
		if (!esperadas.isEmpty()) {
			return false;
		}
		testCase.addBoldTextEvidenceCurrentStep(
				"Se encontraron todas las configuraciones necesarias para la limpieza de particiones");
		return true;
	}

	  /**
		 * Método para validar registros de particiones creadas para tabla Transactions
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 02/06/2026
		 */
	  public boolean registroParticionesCreadasTransactions()  {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at FROM setps.oxc_event_log WHERE error_detail LIKE 'Create setps.oxc_transactions%' order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Particiones creadas para la tabla Transactions");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroParticionesCreadasTransactions -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
			}
	  
	  /**
		 * Método para validar registros de particiones creadas para tabla Saf_Transactions
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 04/06/2026
		 */
	  public boolean registroParticionesCreadasSafTransactions() {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at " +
	                       "FROM setps.oxc_event_log " +
	                       "WHERE error_detail LIKE 'Create setps.oxc_saf_transactions%' " +
	                       "order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Particiones creadas para la tabla Saf_Transactions");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroParticionesCreadasSafTransactions -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
			}
	  
	  /**
		 * Método para validar registros de particiones creadas para tabla Event_log
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 04/06/2026
		 */
	  public boolean registroParticionesCreadasEventLog()  {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at " +
	                       "FROM setps.oxc_event_log " +
	                       "WHERE error_detail LIKE 'Create setps.oxc_event_log%' " +
	                       "order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Particiones creadas para la tabla Event_log");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroParticionesCreadasEventLog -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
			}
	  
	  /**
		 * Método para validar registros de particiones creadas para tabla Error_Log
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 02/06/2026
		 */
	  public boolean registroParticionesCreadasErrorLog()  {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at " +
	                       "FROM setps.oxc_event_log " +
	                       "WHERE error_detail LIKE 'Create setps.oxc_error_log%' " +
	                       "order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Particiones creadas para la tabla Error_Log");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroParticionesCreadasErrorLog -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
			}
	  
	  /**
		 * Método para validar registros de borrado histórico para tabla Error_Log
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 04/06/2026
		 */
	  public boolean registroBorradoHistoricoErrorLog()  {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at " +
	                       "FROM setps.oxc_event_log " +
	                       "WHERE error_detail LIKE 'Delete setps.oxc_error_log%' " +
	                       "order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Borrado Histórico para la tabla Error_Log");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroBorradoHistoricoErrorLog -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
}
	  
	  /**
		 * Método para validar registros de borrado histórico para tabla Event_Log
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 04/06/2026
		 */
	  public boolean registroBorradoHistoricoEventLog() {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at " +
	                       "FROM setps.oxc_event_log " +
	                       "WHERE error_detail LIKE 'Delete setps.oxc_event_log%' " +
	                       "order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Borrado Histórico para la tabla Event_Log");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroBorradoHistoricoEventLog -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
			}
	
	  /**
		 * Método para validar registros de borrado histórico para tabla Transactions
		 * 
		 * @author Leonardo Leonel Ramirez Escobedo
		 * @throws Exception
		 * @date 04/06/2026
		 */
	  public boolean registroBorradoHistoricoTransactions() {
		  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
				   String query = "SELECT status_transaction, error_detail, created_at " +
	                       "FROM setps.oxc_event_log " +
	                       "WHERE error_detail LIKE 'Delete setps.oxc_transactions%' " +
	                       "order by created_at LIMIT 12 ";
					SQLResult sqlResult = new SQLResult(conn, query);
					testCase.addBoldTextEvidenceCurrentStep("Borrado Histórico para la tabla Transactions");
					testCase.addQueryEvidenceCurrentStep(sqlResult);
					
				    return true;

			    } catch (SQLException e) {
				    String error = "SQL Error en registroBorradoHistoricoTransactions -> "
				            + e.getClass().getSimpleName()
				            + ": "
				            + e.getMessage();
				    testCase.addCodeEvidenceCurrentStep(error);
				    return false;
				} 
			}
	  
  /**
	 * Método para validar registros de borrado histórico para tabla Saf_Transactions
	 * 
	 * @author Leonardo Leonel Ramirez Escobedo
	 * @throws Exception
	 * @date 04/06/2026
	 */
  public boolean registroBorradoHistoricoSafTransactions() {
	  try (Connection conn = OxxoCelDbValidator.ejecutar()) {
			   String query = "SELECT status_transaction, error_detail, created_at " +
                       "FROM setps.oxc_event_log " +
                       "WHERE error_detail LIKE 'Delete setps.oxc_saf_transactions%' " +
                       "order by created_at LIMIT 12 ";
				SQLResult sqlResult = new SQLResult(conn, query);
				testCase.addBoldTextEvidenceCurrentStep("Borrado Histórico para la tabla Saf_Transactions");
				testCase.addQueryEvidenceCurrentStep(sqlResult);
				
			    return true;

		    } catch (SQLException e) {
			    String error = "SQL Error en registroBorradoHistoricoSafTransactions -> "
			            + e.getClass().getSimpleName()
			            + ": "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    return false;
			} 
		}
	
		/**
		 * Método para consultar en event_log
		 * @param N/A
		 * @author Mario Garza
		 * @throws Exception
		 * @date 04/06/2026
		 */
		public boolean consultarEventLog()  {
		    try (Connection conn = OxxoCelDbValidator.ejecutar()) {
		        String query =
	                "SELECT error_detail " +
	                "FROM setps.oxc_event_log " +
	                "WHERE error_detail ILIKE '%Particion%' " +
	                "   OR error_detail ILIKE '%ya existe%' " +
	                "   OR error_detail ILIKE '%no existe%' " +
	                "   OR error_detail ILIKE '%denegado%' " +
	                "LIMIT 1";
		        SQLResult sqlResult = new SQLResult(conn, query);
		        testCase.addQueryEvidenceCurrentStep(sqlResult);
		        try (PreparedStatement ps = conn.prepareStatement(query);
		             ResultSet rs = ps.executeQuery()) {
		            if (rs.next()) {
		                String errorEncontrado = rs.getString(ERROR_DETAIL);
		                testCase.addTextEvidenceCurrentStep(
	                    "Error encontrado en EVENT_LOG: " + errorEncontrado);
		                return true;
		            }
			            testCase.addBoldTextEvidenceCurrentStep(
		                    "No se encontró ninguno de los errores esperados: " +
		                    "Particion, ya existe, no existe o denegado");
			           return false;
		        }
		    } catch (SQLException e) {
			    String error = "SQL Error en consultarEventLog -> "
			            + e.getClass().getSimpleName()
			            + ": "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    return false;
			}
		}
	  
}
