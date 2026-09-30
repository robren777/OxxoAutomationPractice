package Mobile.Mep.PromOxxoOffline.om;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import utils.sql.SQLResult;
import Mobile.simpEtiqueteo.om.SQLSimpEtiqueteo;

/**
 * DESCRIPCIÓN: Clase utilitaria para la gestión de consultas y operaciones
 * sobre la base de datos PostgreSQL en el contexto del módulo PromOXXO Offline.
 * Proporciona métodos para conexión, consultas y validaciones de datos de prueba.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline que requieren validación o
 *     preparación de datos en base de datos.
 *
 * PREREQUISITOS:
 *   - Acceso a la base de datos PostgreSQL del ambiente de QA.
 *   - Credenciales de base de datos configuradas en el framework.
 *
 * INSUMOS:
 *   - Credenciales de conexión a PostgreSQL (gestionadas por PasswordUtil)
 *
 * @author Mario A. De León Rdz.
 * @date 15/Diciembre/2025
 */
public class PostgreSQLOffline {
	/**
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	private SQLSimpEtiqueteo sqlSimpEtiq;

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PromOxxoOffline
	 * 
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Mario A. De León Rdz.
	 * @return PromOxxoOffline Instancia de la clase.
	 * @date 15/Diciembre/2025
	 */
	public PostgreSQLOffline(TestCase testCase, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.baseExecution = baseExecution;
		this.sqlSimpEtiq = new SQLSimpEtiqueteo(testCase, baseExecution);
	}

	public final String bdPromOxxoQA = "jdbc:postgresql://oxzposoadadq01.postgres.database.azure.com:5432/prom_oxxo_qa?sslmode=require";
	
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
	    	        "pack, sku, label_content, label_name, aruco, uploaded_at, weight, aruco_dictionary " +
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
	    	        "160, now(), 0.50, 'Original ArUco' )";

	        String queryInsertStatus =
	                "INSERT INTO public.promo_label_statuses (period_id, store_id, sku, status) " +
	                "VALUES ('25P13', '" + tienda + "', 'P13-TEST-1', 'EXECUTABLE')";

	        sqlSimpEtiq.sqlExecuteSinEv(queryInsertPop, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(queryInsertStatus, bdPromOxxoQA, dbUsername, dbPassword);
	        System.out.println("INSERT POP ejecutado correctamente\n");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método privado para ejecutar la inserción de un POP individual con su estatus.
	 * * @author Mario A. De León Rdz.
	 * @date 03/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda (store_id).
	 * @param spaceGroup Grupo de espacio al que pertenece el POP (ej. Cabeceras).
	 * @param space Espacio específico asignado (ej. Cabecera 1).
	 * @param sku Código de identificación del producto (Stock Keeping Unit).
	 * @param popName Nombre descriptivo del POP a insertar.
	 * @return void
	 * @throws Exception Si ocurre un error durante la ejecución de los queries.
	 */
	private synchronized void sqlInsertPOPSinEv(String dbUsername, String dbPassword, String periodId, String tienda, String spaceGroup, String space, String sku, String popName, String aruco) throws Exception {
	    try {
	        String queryInsertPop =
	                "INSERT INTO public.promo_labels ( " +
	                "period_id, store_id, space_group, space, pop, pack, sku, " +
	                "label_content, label_name, aruco, uploaded_at, weight, aruco_dictionary " +
	                ") VALUES ( " +
	                "'"+periodId+"', '" + tienda + "', '" + spaceGroup + "', '" + space + "', '" + popName + "', " +
	                "'Test Pack', '" + sku + "', 'Label content', 'Label name', "+aruco+", now(), 0.50, 'Original ArUco' )";

	        String queryInsertStatus =
	                "INSERT INTO public.promo_label_statuses (period_id, store_id, sku, status) " +
	                "VALUES ('"+periodId+"', '" + tienda + "', '" + sku + "', 'EXECUTABLE')";

	        sqlSimpEtiq.sqlExecuteSinEv(queryInsertPop, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(queryInsertStatus, bdPromOxxoQA, dbUsername, dbPassword);
	    } catch (Exception e) {
	        throw new Exception("Error al insertar POP individual: " + e.getMessage());
	    }
	}
	
	/**
	 * Método privado para ejecutar la inserción de un POP individual con su estatus.
	 * * @author Mario A. De León Rdz.
	 * @date 03/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda (store_id).
	 * @param spaceGroup Grupo de espacio al que pertenece el POP (ej. Cabeceras).
	 * @param space Espacio específico asignado (ej. Cabecera 1).
	 * @param sku Código de identificación del producto (Stock Keeping Unit).
	 * @param popName Nombre descriptivo del POP a insertar.
	 * @return void
	 * @throws Exception Si ocurre un error durante la ejecución de los queries.
	 */
	private void sqlInsertPOPSinEv(String dbUsername, String dbPassword, String tienda, String spaceGroup, String space, String sku, String popName, String aruco) throws Exception {
	    try {
	        String queryInsertPop =
	                "INSERT INTO public.promo_labels ( " +
	                "period_id, store_id, space_group, space, pop, pack, sku, " +
	                "label_content, label_name, aruco, uploaded_at, weight, aruco_dictionary " +
	                ") VALUES ( " +
	                "'25P13', '" + tienda + "', '" + spaceGroup + "', '" + space + "', '" + popName + "', " +
	                "'Test Pack', '" + sku + "', 'Label content', 'Label name', "+aruco+", now(), 0.50, 'Original ArUco' )";

	        String queryInsertStatus =
	                "INSERT INTO public.promo_label_statuses (period_id, store_id, sku, status) " +
	                "VALUES ('25P13', '" + tienda + "', '" + sku + "', 'EXECUTABLE')";

	        sqlSimpEtiq.sqlExecuteSinEv(queryInsertPop, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(queryInsertStatus, bdPromOxxoQA, dbUsername, dbPassword);
	    } catch (Exception e) {
	        throw new Exception("Error al insertar POP individual: " + e.getMessage());
	    }
	}

	/**
	 * Método para ejecutar la inserción combinada de un POP de Cabecera y un POP de Exhibidor.
	 * * @author Mario A. De León Rdz.
	 * @date 03/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda donde se insertarán ambos POPs.
	 * @return void
	 * @throws Exception Si falla alguna de las inserciones en la base de datos.
	 */
	public void sqlInsertPOPCabeceraYExhibidorSinEv(String dbUsername, String dbPassword, String tienda) throws Exception {
	    try {
	        // Inserción de Cabecera P13-TEST-1
	        sqlInsertPOPSinEv(dbUsername, dbPassword, tienda, "Cabeceras", "Cabecera 1", "P13-TEST-1", "Test POP Cabecera 1", "160");
	        
	        // Inserción de Banner P13-TEST-2
	        sqlInsertPOPSinEv(dbUsername, dbPassword, tienda, "Exhibidor", "Exhibidor 1", "P13-TEST-2", "Test POP Exhibidor 1", "100");
	        
	        System.out.println("INSERT POP (Cabecera y Exhibidor) ejecutado correctamente\n");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para ejecutar la inserción de dos POPs del grupo promocional Cabeceras
	 * @author Mario A. De León Rdz.
	 * @date 03/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda donde se insertarán ambos POPs.
	 * @return void
	 * @throws Exception Si falla alguna de las inserciones en la base de datos.
	 */
	public void sqlInsertDosPOPsCabecerasSinEv(String dbUsername, String dbPassword, String tienda) throws Exception {
	    try {
	        // Inserción de Cabecera P13-TEST-1
	        sqlInsertPOPSinEv(dbUsername, dbPassword, tienda, "Cabeceras", "Cabecera 1", "P13-TEST-1", "Test POP Cabecera 1", "160");
	        
	        // Inserción de Banner P13-TEST-2
	        sqlInsertPOPSinEv(dbUsername, dbPassword, tienda, "Cabeceras", "Cabecera 2", "P13-TEST-2", "Test POP Cabecera 2", "100");
	        
	        System.out.println("INSERT POP (Cabecera 1 y Cabecera 2) ejecutado correctamente\n");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para ejecutar la inserción combinada de un POP de Cabecera y un POP de Exhibidor.
	 * @author Mario A. De León Rdz.
	 * @date 03/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda donde se insertarán ambos POPs.
	 * @return void
	 * @throws Exception Si falla alguna de las inserciones en la base de datos.
	 */
	public void sqlInsertPOPCabeceraYExhibidorSinEv(String dbUsername, String dbPassword,String periodId, String tienda) throws Exception {
	    try {
	        // Inserción de Cabecera P13-TEST-1
	        sqlInsertPOPSinEv(dbUsername, dbPassword, periodId, tienda, "Cabeceras", "Cabecera 1", "P13-TEST-1", "Test POP Cabecera 1", "160");
	        
	        // Inserción de Banner P13-TEST-2
	        sqlInsertPOPSinEv(dbUsername, dbPassword, periodId, tienda, "Exhibidor", "Exhibidor 1", "P13-TEST-2", "Test POP Exhibidor 1", "100");
	        
	        System.out.println("INSERT POP (Cabecera y Exhibidor) ejecutado correctamente\n");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para ejecutar la inserción de dos POPs del grupo promocional Cabeceras
	 * @author Mario A. De León Rdz.
	 * @date 03/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda donde se insertarán ambos POPs.
	 * @return void
	 * @throws Exception Si falla alguna de las inserciones en la base de datos.
	 */
	public void sqlInsertDosPOPsCabecerasSinEv(String dbUsername, String dbPassword, String periodId, String tienda) throws Exception {
	    try {
	        // Inserción de Cabecera P13-TEST-1
	        sqlInsertPOPSinEv(dbUsername, dbPassword, periodId, tienda, "Cabeceras", "Cabecera 1", "P13-TEST-1", "Test POP Cabecera 1", "160");
	        
	        // Inserción de Banner P13-TEST-2
	        sqlInsertPOPSinEv(dbUsername, dbPassword, periodId, tienda, "Cabeceras", "Cabecera 2", "P13-TEST-2", "Test POP Cabecera 2", "100");
	        
	        System.out.println("INSERT POP (Cabecera 1 y Cabecera 2) ejecutado correctamente\n");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	}

	/**
	 * Método para ejecutar query para Insertar POP en BDD PromOxxoQA
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2026
	 * @throws Exception
	 */
	public void sqlInsertPOPBannerSinEv(String dbUsername, String dbPassword, String tienda, String periodId) throws Exception {
		try {
			String queryInsertPop =
					"INSERT INTO public.promo_labels ( " +
							"period_id, store_id, space_group, space, pop, " +
							"pack, sku, label_content, label_name, aruco, uploaded_at, weight " +
							") VALUES ( " +
							"'" + periodId + "', " +
							"'" + tienda + "', " +
							"'Banners', " +
							"'Banner 1', " +
							"'Test POP Banner 1', " +
							"'Test Pack', " +
							"'P13-TEST-1', " +
							"'Label content', " +
							"'Label name', " +
							"160, now(), 0.50 )";

			String queryInsertStatus =
					"INSERT INTO public.promo_label_statuses (period_id, store_id, sku, status) " +
							"VALUES ('" + periodId + "', '" + tienda + "', 'P13-TEST-1', 'EXECUTABLE')";

			sqlSimpEtiq.sqlExecuteSinEv(queryInsertPop, bdPromOxxoQA, dbUsername, dbPassword);
			sqlSimpEtiq.sqlExecuteSinEv(queryInsertStatus, bdPromOxxoQA, dbUsername, dbPassword);
			System.out.println("INSERT POP ejecutado correctamente\n");
		} catch (Exception e) {
			throw new Exception("Error: " + e.getMessage());
		}
	}
	


	/**
	 * Método para ejecutar la inserción de 30 POPs del grupo promocional Cabeceras
	 * VERSIÓN SÍNCRONA - Asegura que todas las inserciones se completen antes de retornar
	 * @author Mario A. De León Rdz.
	 * @date 09/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param periodId Identificador del periodo promocional.
	 * @param tienda Identificador de la tienda donde se insertarán los POPs.
	 * @return void
	 * @throws Exception Si falla alguna de las inserciones en la base de datos.
	 */
	public synchronized void sqlInsertTreintaPOPsCabecerasSincronoSinEv(String dbUsername, String dbPassword, String periodId, String tienda) throws Exception {
	    Connection connection = null;
	    int insertados = 0;
	    
	    try {
	        System.out.println("\n[INFO] Iniciando inserción de 30 POPs...");
	        
	        // Crear conexión única para todas las operaciones
	        connection = DriverManager.getConnection(bdPromOxxoQA, dbUsername, dbPassword);
	        connection.setAutoCommit(false); // Desactivar auto-commit
	        
	        // Inserción de 30 POPs de Cabeceras con códigos ArUco únicos
	        for (int i = 1; i <= 30; i++) {
	            String popNumber = String.format("%02d", i); // Formato 01, 02, 03... 30
	            String arucoCode = String.valueOf(160 + i - 1); // 160, 161, 162... 189

	            // Ejecutar insert usando la conexión compartida
	            sqlInsertPOPSinEvConConexion(
	                connection,
	                periodId,
	                tienda,
	                "Cabeceras",
	                "Cabecera " + i,
	                "P13-TEST-" + popNumber,
	                "Test POP Cabecera " + i,
	                arucoCode  // Código único para cada POP
	            );
	            
	            insertados++;
	            
	            // Log de progreso cada 10 POPs
	            if (i % 10 == 0) {
	                System.out.println("[PROGRESO] " + i + "/30 POPs insertados");
	            }
	        }
	        
	        // IMPORTANTE: Hacer commit para asegurar que los cambios se persistan
	        connection.commit();
	        
	        System.out.println("[OK] INSERT de 30 POPs (Cabeceras) ejecutado correctamente");
	        System.out.println("[INFO] Total POPs insertados: " + insertados);
	        
	        // Validar que se insertaron correctamente
	        if (!validarPOPsInsertados(connection, tienda, 30)) {
	            throw new Exception("La validación de POPs insertados falló");
	        }
	        
	    } catch (Exception e) {
	        // Rollback en caso de error
	        if (connection != null) {
	            try {
	                connection.rollback();
	                System.out.println("[ERROR] Rollback ejecutado. POPs insertados: " + insertados + "/30");
	            } catch (SQLException rollbackEx) {
	                System.out.println("[ERROR] Error en rollback: " + rollbackEx.getMessage());
	            }
	        }
	        throw new Exception("Error al insertar 30 POPs (insertados: " + insertados + "/30): " + e.getMessage());
	        
	    } finally {
	        // Cerrar conexión
	        try {
	            if (connection != null) connection.close();
	            System.out.println("[INFO] Conexión a BD cerrada\n");
	        } catch (SQLException closeEx) {
	            System.out.println("[ADVERTENCIA] Error al cerrar conexión: " + closeEx.getMessage());
	        }
	    }
	}

	/**
	 * Método auxiliar para insertar un POP usando una conexión existente (sin cerrarla)
	 * Versión modificada de sqlInsertPOPSinEv que recibe la conexión como parámetro
	 * @author Mario A. De León Rdz.
	 * @date 13/febrero/2026
	 * @param connection Conexión activa a la base de datos (no se cierra en este método).
	 * @param periodId Identificador del periodo promocional.
	 * @param tienda Identificador de la tienda (store_id).
	 * @param spaceGroup Grupo de espacio al que pertenece el POP (ej. Cabeceras).
	 * @param space Espacio específico asignado (ej. Cabecera 1).
	 * @param sku Código de identificación del producto (Stock Keeping Unit).
	 * @param popName Nombre descriptivo del POP a insertar.
	 * @param aruco Código ArUco asociado al POP.
	 * @return void
	 * @throws Exception Si ocurre un error durante la ejecución de los queries.
	 */
	private void sqlInsertPOPSinEvConConexion(
	    Connection connection,
	    String periodId,
	    String tienda,
	    String spaceGroup,
	    String space,
	    String sku,
	    String popName,
	    String aruco
	) throws Exception {

	    Statement statement = null;

	    try {
	        statement = connection.createStatement();

	        String queryInsertPop =
	                "INSERT INTO public.promo_labels ( " +
	                "period_id, store_id, space_group, space, pop, pack, sku, " +
	                "label_content, label_name, aruco, uploaded_at, weight, aruco_dictionary " +
	                ") VALUES ( " +
	                "'" + periodId + "', '" + tienda + "', '" + spaceGroup + "', '" + space + "', '" + popName + "', " +
	                "'Test Pack', '" + sku + "', 'Label content', 'Label name', " + aruco + ", now(), 0.50, 'Original ArUco' )";

	        String queryInsertStatus =
	                "INSERT INTO public.promo_label_statuses (period_id, store_id, sku, status) " +
	                "VALUES ('" + periodId + "', '" + tienda + "', '" + sku + "', 'EXECUTABLE')";

	        statement.executeUpdate(queryInsertPop);
	        statement.executeUpdate(queryInsertStatus);

	    } catch (SQLException e) {
	        throw new Exception("Error al insertar POP con SKU " + sku + ": " + e.getMessage());
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                // Ignorar error al cerrar statement
	            }
	        }
	    }
	}

	/**
	 * Método para validar que se insertaron correctamente los POPs en la base de datos
	 * @author Mario A. De León Rdz.
	 * @date 13/febrero/2026
	 * @param connection Conexión activa a la base de datos.
	 * @param tienda Identificador de la tienda donde se insertaron los POPs.
	 * @param esperados Cantidad de POPs que se esperan encontrar en la base de datos.
	 * @return boolean True si la cantidad de POPs encontrados coincide con la esperada, False en caso contrario.
	 * @throws SQLException Si ocurre un error durante la ejecución del query de validación.
	 */
	private boolean validarPOPsInsertados(Connection connection, String tienda, int esperados) throws SQLException {
	    Statement statement = null;
	    ResultSet rs = null;

	    try {
	        statement = connection.createStatement();

	        String query = "SELECT COUNT(*) as total FROM public.promo_labels " +
	                      "WHERE store_id = '" + tienda + "' " +
	                      "AND sku LIKE 'P13-TEST-%'";

	        rs = statement.executeQuery(query);

	        if (rs.next()) {
	            int total = rs.getInt("total");
	            System.out.println("[VALIDACION] POPs encontrados en BD: " + total + "/" + esperados);
	            return total == esperados;
	        }

	        return false;

	    } catch (SQLException e) {
	        throw new SQLException("Error al validar POPs insertados: " + e.getMessage());
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {
	                // Ignorar error al cerrar ResultSet
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                // Ignorar error al cerrar Statement
	            }
	        }
	    }
	};
	
	/**
	 * Método para ejecutar query para ELIMINAR POP insertado con metodo sqlInsertarPOPSinEv() en BDD PromOxxoQA 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2026
	 * @throws Exception 
	 */	  
	public synchronized void sqlDeletePOPSinEv(String dbUsername, String dbPassword, String tienda) throws Exception {
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

	        sqlSimpEtiq.sqlExecuteSinEv(deleteLabelValidations, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(deleteLabelStatuses, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(deleteLabels, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println("\nDELETE POP ejecutado correctamente");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	};
	
	/**
	 * Método para ejecutar query para ELIMINAR POP insertado con metodo sqlInsertarPOPSinEv() en BDD PromOxxoQA 
	 * @author Mario A. De León Rdz.
	 * @date 08/enero/2026
	 * @throws Exception 
	 */	  
	public synchronized void sqlDeletePOPSinEv(String dbUsername, String dbPassword, String tienda, String sku) throws Exception {
	    try {
	        String deleteLabelValidations =
	                "DELETE FROM public.label_validations " +
	                "WHERE store_id = '" + tienda + "' " +
	                "AND sku = '"+sku+"'";

	        String deleteLabelStatuses =
	                "DELETE FROM public.promo_label_statuses " +
	                "WHERE store_id = '" + tienda + "' " +
	                "AND sku = '"+sku+"'";

	        String deleteLabels =
	                "DELETE FROM public.promo_labels " +
	                "WHERE store_id = '" + tienda + "' " +
	                "AND sku = '"+sku+"'";

	        sqlSimpEtiq.sqlExecuteSinEv(deleteLabelValidations, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(deleteLabelStatuses, bdPromOxxoQA, dbUsername, dbPassword);
	        sqlSimpEtiq.sqlExecuteSinEv(deleteLabels, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println("\nDELETE POP ejecutado correctamente");
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	};
	
	/**
	 * Método para ejecutar query para ELIMINAR 30 POPs insertados con método sqlInsertTreintaPOPsCabecerasSinEv() en BDD PromOxxoQA
	 * VERSIÓN SÍNCRONA - Asegura que todas las eliminaciones se completen antes de retornar
	 * @author Mario A. De León Rdz.
	 * @date 09/febrero/2026
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param tienda Identificador de la tienda donde se eliminarán los POPs.
	 * @return void
	 * @throws Exception Si falla alguna de las eliminaciones en la base de datos.
	 */
	public synchronized void sqlDeleteTreintaPOPsSincronoSinEv(String dbUsername, String dbPassword, String tienda) throws Exception {
	    Connection connection = null;
	    Statement statement = null;
	    int totalDeletes = 0;
	    
	    try {
	        System.out.println("\n[INFO] Iniciando eliminación de 30 POPs...");
	        
	        // Crear conexión única para todas las operaciones
	        connection = DriverManager.getConnection(bdPromOxxoQA, dbUsername, dbPassword);
	        connection.setAutoCommit(false); // Desactivar auto-commit para control manual
	        statement = connection.createStatement();
	        
	        // Eliminación de 30 POPs de Cabeceras
	        for (int i = 1; i <= 30; i++) {
	            String popNumber = String.format("%02d", i); // Formato 01, 02, 03... 30
	            String sku = "P13-TEST-" + popNumber;

	            String deleteLabelValidations =
	                    "DELETE FROM public.label_validations " +
	                    "WHERE store_id = '" + tienda + "' " +
	                    "AND sku = '" + sku + "'";

	            String deleteLabelStatuses =
	                    "DELETE FROM public.promo_label_statuses " +
	                    "WHERE store_id = '" + tienda + "' " +
	                    "AND sku = '" + sku + "'";

	            String deleteLabels =
	                    "DELETE FROM public.promo_labels " +
	                    "WHERE store_id = '" + tienda + "' " +
	                    "AND sku = '" + sku + "'";

	            // Ejecutar deletes en orden
	            int rows1 = statement.executeUpdate(deleteLabelValidations);
	            int rows2 = statement.executeUpdate(deleteLabelStatuses);
	            int rows3 = statement.executeUpdate(deleteLabels);
	            
	            totalDeletes += (rows1 + rows2 + rows3);
	            
	            // Log de progreso cada 10 POPs
	            if (i % 10 == 0) {
	                System.out.println("[PROGRESO] " + i + "/30 POPs eliminados");
	            }
	        }
	        
	        // IMPORTANTE: Hacer commit para asegurar que los cambios se persistan
	        connection.commit();
	        
	        System.out.println("[OK] DELETE de 30 POPs ejecutado correctamente");
	        System.out.println("[INFO] Total de registros eliminados: " + totalDeletes);
	        
	    } catch (Exception e) {
	        // Rollback en caso de error
	        if (connection != null) {
	            try {
	                connection.rollback();
	                System.out.println("[ERROR] Rollback ejecutado debido a error");
	            } catch (SQLException rollbackEx) {
	                System.out.println("[ERROR] Error en rollback: " + rollbackEx.getMessage());
	            }
	        }
	        throw new Exception("Error al eliminar 30 POPs: " + e.getMessage());
	        
	    } finally {
	        // Cerrar recursos
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	            System.out.println("[INFO] Conexión a BD cerrada\n");
	        } catch (SQLException closeEx) {
	            System.out.println("[ADVERTENCIA] Error al cerrar conexión: " + closeEx.getMessage());
	        }
	    }
	};
	
	/**
	 * Método para ejecutar un SELECT en la tabla promo_label_statuses
	 * filtrando por SKU específico en la BDD PromOxxoQA.
	 * Agrega evidencia del resultado en el test.
	 *
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Resultado del query ejecutado
	 * @date 08/Enero/2026
	 * @throws Exception En caso de error durante la ejecución del query
	 */ 
	public SQLResult sqlSelectFolio(String dbUsername, String dbPassword) throws Exception {
	    try {
	        String query = "SELECT*FROM public.promo_label_statuses WHERE sku = 'P13-TEST-1'";
	        SQLResult result = sqlSimpEtiq.sqlExecuteSinEv(query, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println(query + "\nEjecutado correctamente. Resultado:");
	        sqlSimpEtiq.imprimirResult(result);
	        testCase.addQueryEvidenceCurrentStep(result);
	        return result;
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	};
	/**
	 * Método para ejecutar un SELECT en la tabla promo_label_statuses
	 * filtrando por SKU específico en la BDD PromOxxoQA,
	 * sin agregar evidencia al test.
	 *
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Resultado del query ejecutado
	 * @date 08/Enero/2026
	 * @throws Exception En caso de error durante la ejecución del query
	 */
	public SQLResult sqlSelectFolioSinEv(String dbUsername, String dbPassword) throws Exception {
	    try {
	        String query = "SELECT*FROM public.promo_label_statuses WHERE sku = 'P13-TEST-1'";
	        SQLResult result = sqlSimpEtiq.sqlExecuteSinEv(query, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println("\n"+query + "\nEjecutado correctamente. Resultado:");
	        sqlSimpEtiq.imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        throw new Exception("Error: " + e.getMessage());
	    }
	};
	
	/**
	 * Método para ejecutar un SELECT en la tabla promo_label_statuses
	 * filtrando por SKU y estado específico en la BDD PromOxxoQA.
	 * Agrega evidencia del resultado en el test.
	 *
	 * @author Mario A. De León Rdz.
	 * @param estado Estado esperado del registro a validar
	 * @return SQLResult Resultado del query ejecutado
	 * @date 08/Enero/2026
	 * @throws Exception En caso de error o si no se obtienen resultados
	 */
	public SQLResult sqlSelectFolioYValidarEstado(String dbUsername, String dbPassword, String estado) throws Exception {
	    try {
	        String query = "SELECT * FROM public.promo_label_statuses WHERE sku = 'P13-TEST-1' AND status = '" + estado + "'";
	        SQLResult result = sqlSimpEtiq.sqlExecuteSinEv(query, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println(query + "\nEjecutado correctamente. Resultado:");
	        sqlSimpEtiq.imprimirResult(result);

	        if (result == null || result.isEmpty()) {
	            throw new Exception(
	                "No se encontraron registros para el SKU 'P13-TEST-1' con estado '" + estado + "'"
	            );
	        }

	        testCase.addQueryEvidenceCurrentStep(result);
	        return result;
	    } catch (Exception e) {
	        throw new Exception("Error al validar el estado del folio en BDD: " + e.getMessage(), e);
	    }
	}
	
	/**
	 * Método para ejecutar un SELECT en la tabla promotion_periods
	 * obteniendo todos los períodos existentes en la BDD PromOxxoQA,
	 * sin agregar evidencia al test.
	 *
	 * @author Mario A. De León Rdz.
	 * @return SQLResult Resultado del query ejecutado
	 * @date 26/Febrero/2026
	 * @throws Exception En caso de error durante la ejecución del query
	 */
	public SQLResult sqlSelectPeriodosSinEv(String dbUsername, String dbPassword) throws Exception {
	    try {
	        String query = "SELECT id, code, start_date, end_date FROM PUBLIC.PROMO_PERIODS";
	        SQLResult result = sqlSimpEtiq.sqlExecuteSinEv(query, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println("\n" + query + "\nEjecutado correctamente. Resultado:");
	        sqlSimpEtiq.imprimirResult(result);
	        return result;
	    } catch (Exception e) {
	        throw new Exception("Error al obtener los períodos de promoción: " + e.getMessage());
	    }
	}
	/**
	 * Método para ejecutar un DELETE en la tabla promotion_periods
	 * eliminando todos los períodos existentes en la BDD PromOxxoQA,
	 * sin agregar evidencia al test.
	 *
	 * @author Mario A. De León Rdz.
	 * @param dbUsername Nombre de usuario de la base de datos
	 * @param dbPassword Contraseña de la base de datos
	 * @return void
	 * @date 26/Febrero/2026
	 * @throws Exception En caso de error durante la ejecución del query
	 */
	public void sqlDeletePeriodosSinEv(String dbUsername, String dbPassword) throws Exception {
	    try {
	        String query = "DELETE FROM PUBLIC.PROMO_PERIODS";
	        sqlSimpEtiq.sqlExecuteSinEv(query, bdPromOxxoQA, dbUsername, dbPassword);

	        System.out.println("\n" + query + "\nEjecutado correctamente.");
	    } catch (Exception e) {
	        throw new Exception("Error al eliminar los períodos de promoción: " + e.getMessage());
	    }
	}

	/**
	 * Método para ejecutar un INSERT en la tabla promotion_periods
	 * restaurando los períodos previamente obtenidos con sqlSelectPeriodosSinEv,
	 * sin agregar evidencia al test.
	 *
	 * @author Mario A. De León Rdz.
	 * @param dbUsername Nombre de usuario de la base de datos
	 * @param dbPassword Contraseña de la base de datos
	 * @param periodos SQLResult con los períodos a restaurar
	 * @return void
	 * @date 26/Febrero/2026
	 * @throws Exception En caso de error durante la ejecución del query
	 */
	public void sqlRestaurarPeriodosSinEv(String dbUsername, String dbPassword, SQLResult periodos) throws Exception {
	    try {
	        for (int i = 0; i < periodos.getRowCount(); i++) {
	            String id         = periodos.getData(i, "id");
	            String code       = periodos.getData(i, "code");
	            String start_date = periodos.getData(i, "start_date");
	            String end_date   = periodos.getData(i, "end_date");

	            String query = "INSERT INTO PUBLIC.PROMO_PERIODS (id, code, start_date, end_date) "
	                         + "VALUES ('" + id + "', '" + code + "', '" + start_date + "', '" + end_date + "') "
	                         + "ON CONFLICT (id) DO NOTHING";

	            sqlSimpEtiq.sqlExecuteSinEv(query, bdPromOxxoQA, dbUsername, dbPassword);
	            System.out.println("\n" + query + "\nEjecutado correctamente.");
	        }
	    } catch (Exception e) {
	        throw new Exception("Error al restaurar los períodos de promoción: " + e.getMessage());
	    }
	}
	/**
	 * Valida que la lista de grupos de espacios promocionales visibles en la UI
	 * correspondan con los registros en la BDD PromOxxoQA.
	 *
	 * @author Mario A. De León Rdz.
	 * @param dbUsername Nombre de usuario para la conexión a la base de datos.
	 * @param dbPassword Contraseña para la conexión a la base de datos.
	 * @param espaciosPromocionales Lista de textos obtenidos de la UI (incluye los 2 primeros a ignorar).
	 * @param crTienda Código CR de la tienda.
	 * @param crPlaza Código CR de la plaza.
	 * @throws Exception Si falla la consulta o algún grupo no fue encontrado en BD.
	 * @date 09/Marzo/2026
	 */
	public synchronized void validarListaGruposEspaciosPromocionalesConBD(String dbUsername, String dbPassword, List<String> espaciosPromocionales, String crTienda, String crPlaza) throws Exception {
	    List<String> gruposAValidar = espaciosPromocionales.subList(2, espaciosPromocionales.size());
	    boolean todosEncontrados = true;
	    StringBuilder evidencia = new StringBuilder();
	    evidencia.append("Validación de grupos promocionales en BDD | Plaza: ").append(crPlaza).append(" | Tienda: ").append(crTienda).append("\n\n");

	    try (Connection conn = DriverManager.getConnection(bdPromOxxoQA, dbUsername, dbPassword);
	         Statement stmt = conn.createStatement()) {

	        for (String grupo : gruposAValidar) {
	            String query =
	                    "SELECT COUNT(*) AS total FROM public.\"OUT_179_RDP_LISTA_PROD_MEP\" " +
	                    "WHERE \"REGION_CR\" = '" + crPlaza + "' " +
	                    "AND \"TIENDA_CR\" = '" + crTienda + "' " +
	                    "AND \"AGRUPADOR_MEP\" = '" + grupo + "'";

	            ResultSet rs = stmt.executeQuery(query);
	            rs.next();
	            int total = rs.getInt("total");
	            rs.close();

	            if (total > 0) {
	                evidencia.append("[OK] '").append(grupo).append("' → encontrado (").append(total).append(" registros)\n");
	            } else {
	                evidencia.append("[FAIL] '").append(grupo).append("' → NO encontrado en BD\n");
	                todosEncontrados = false;
	            }
	        }
	    } catch (Exception e) {
	        throw new Exception("Error al validar grupos promocionales contra BD: " + e.getMessage(), e);
	    }

	    testCase.addTextEvidenceCurrentStep(evidencia.toString());

	    if (!todosEncontrados) {
	        throw new Exception("Uno o más grupos de la UI no fueron encontrados en BD. Ver evidencia del paso.");
	    }
	}
}