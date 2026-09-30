package EBS.cfeEnLinea.om;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import EBS.util.GlobalVariables;
import modelo.TestCase;
import utils.sql.SQLResult;

public class SqlExecute {

	
	private TestCase testcase;

	public SqlExecute(TestCase testcase){
		this.testcase =  testcase;
	}

	
	
	/**
	 * Metodo para ejecutar un query en la BD correspondiente
	 *@param query instruccion sql a ejecutar 
	 *@param dbUrl url de la Base de datos
	 *@param dbUser usuario de la Base de datos 
	 *@param dbPassword contraseña de la Base de datos
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
  	 * @date 03/10/2025
  	 * @return result resultado de query
	 */
	
	public SQLResult sqlExecute(String query, String dbUrl, String dbUser, String dbPassword) {

		SQLResult result = null;
		
		
	     // Inicializamos el objeto donde guardaremos los datos

	    try  (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	         Statement stmt = connection.createStatement()) {
	    	 result = new SQLResult( connection, query);
	        boolean hasResultSet = stmt.execute(query);

	        if (hasResultSet) {
	            ResultSet rs = stmt.getResultSet();
	            loadResultSet(rs); // cargamos datos
	        } else {
	            int updateCount = stmt.getUpdateCount();
	            setUpdateCount(updateCount);
	        }
	        
	

	    } catch (SQLException e) {
	        e.printStackTrace();
	        setError(e.getMessage()); // guardamos el error dentro del objeto
	    }

	    return result;
	}


    
	/**
	 * Metodo para convertir el resultado SQL a String para validaciones extra
	 *@param res resultado obtenido por el query ejecutado
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
  	 * @date 03/10/2025
  	 * @return stringbuilder en tipo string es texto el cual va armando mediante un resultado de SQL 
	 */
	
	// Convierte SQLResult a String legible
	public static String sqlResultToString(SQLResult res) {
	    if (res == null) return "SQLResult vacío";

	    StringBuilder sb = new StringBuilder();

	    // Columnas
	    List<String> columnas = res.getColumnNames();
	    sb.append(String.join(" | ", columnas)).append("\n");

	    // Filas
	    for (int i = 0; i < res.getRowCount(); i++) {
	        List<String> fila = res.getRow(i); // asumimos que getRow devuelve List<String>
	        sb.append(String.join(" | ", fila)).append("\n");
	    }

	    return sb.toString();
	}
    
    private List<List<String>> data = new ArrayList<>();  //  lista inicializada
    private int updateCount = -1;
    private String error = null;
    
    
    /**
	 * Metodo para cargar result ser y recorrer datos
	 *@param rs result set regresa lo armado por la respuesta de sql, recorre cada fila 
	 * @author Alan Perez
  	 * @date 03/10/2025
	 */
    ///metodos para SQL 
    // Cargar un ResultSet en memoria
    public void loadResultSet(ResultSet rs) throws SQLException {
        data.clear();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
    }

    
    /**
	 * Metodo para obtener conteo
	 *@param int para asignar el conteo lo actualiza y lo setea en una variable
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 *  
	 */
    // Guardar resultado de UPDATE/INSERT/DELETE
    public void setUpdateCount(int count) {
        this.updateCount = count;
    }

    /**
	 * Metodo para obtener y actualizar el contador
	 *
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return actualizacion de contador
	 */
    public int getUpdateCount() {
        return updateCount;
    }

    /**
	 * Metodo para setear el error como mensaje
	 *@param msg mensaje de error guardado
	 * @author Alan Perez
  	 * @date 03/10/2025 
	 */
    // Manejo de errores
    public void setError(String msg) {
        this.error = msg;
    }

    /**
	 * Metodo para validar si existe error
	 *
	 * @author Alan Perez 
  	 * @date 03/10/2025
  	 * @return error si es diferente a null valida que contenga error
	 */
    public boolean hasError() {
        return error != null;
    }

    /**
	 * Metodo para obtener el error 
	 *
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 *  @return error obtiene el error de tipo string
	 */
    public String getError() {
        return error;
    }

    
    /**
	 * Metodo para validar si es vacio se utiliza para validaciones de data
	 * @author Alan Perez 
  	 * @date 03/10/2025
  	 * @return data valida si esta vacia o no 
	 */
    // Métodos de acceso
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
	 * Metodo para obtener el contador de filas
	 *
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return tamaño de data para obtener el contador de filas
     	 
	 */
    public int getRowCount() {
        return data.size();
    }
	
    /**
	 * Metodo para obtener el contador de columnas
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return data is empty o valor de data, valida la cantidad de columnas
	 */

    public int getColumnCount() {
        return data.isEmpty() ? 0 : data.get(0).size();
    }

    public String getValueAt(int row, int col) {
        if (row < 0 || row >= getRowCount()) return null;
        if (col < 0 || col >= getColumnCount()) return null;
        return data.get(row).get(col);
    }

    // Opcional: acceder por nombre de columna
    public String getValueAt(int row, String columnName, ResultSetMetaData meta) throws SQLException {
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            if (meta.getColumnName(i).equalsIgnoreCase(columnName)) {
                return data.get(row).get(i - 1);
            }
        }
        return null;
    }

    String QueryCBanco = "SELECT ID,DOC_NAME,STATUS,CREATED_DATE, SERVICE_ID, MONTO\r\n"
    		+ "FROM (\r\n"
    		+ "    SELECT ID,DOC_NAME,STATUS,CREATED_DATE, SERVICE_ID, MONTO \r\n"
    		+ "    FROM apps.wm_ol9_outbound_docs\r\n"
    		+ "    WHERE service_id IN ('101960422')\r\n"
    		+ "      AND DOC_NAME LIKE 'CBANCO.FBB2025%'\r\n"
    		+ "    ORDER BY created_date DESC\r\n"
    		+ ")\r\n"
    		+ "WHERE ROWNUM = 1";
 
    
    /**
	 * Metodo para validar el archivo CBANCO en BD
	 *
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos en la BD EBS QA false si no encuentra información

	 */
    // Validar archivo CBANCO CFE
    public boolean validateArchivoCBanco() {
    	SQLResult res = null; 
        try {
             res = sqlExecute(QueryCBanco,
                    GlobalVariables.DB_HOST_CFE_EBS,
                    GlobalVariables.DB_USER_CFE_EBS,
                    GlobalVariables.DB_PASSWORD_CFE_EBS);

            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
          
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    String QueryInbound ="SELECT ID,\r\n"
    		+ "       PE_ID,\r\n"
    		+ "       STATUS,\r\n"
    		+ "       DOC_TYPE,\r\n"
    		+ "       PV_DOC_NAME,\r\n"
    		+ "       RECEIVED_DATE\r\n"
    		+ "FROM (\r\n"
    		+ "    SELECT ID,\r\n"
    		+ "           PE_ID,\r\n"
    		+ "           STATUS,\r\n"
    		+ "           DOC_TYPE,\r\n"
    		+ "           PV_DOC_NAME,\r\n"
    		+ "           RECEIVED_DATE\r\n"
    		+ "    FROM posuser.pos_inbound_docs\r\n"
    		+ "    WHERE STATUS = 'I'\r\n"
    		+ "      AND pv_Doc_name LIKE 'SYB10UMI50BSX25%'\r\n"
    		+ "    ORDER BY received_date DESC\r\n"
    		+ ")\r\n"
    		+ "WHERE ROWNUM = 1\r\n"
    		+ "";
    	 ;

    public static String lastRetrievedId;

    
    /**
	 * Metodo para validar tabla POS_INBOUND_DOCS
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
 
	 */
    // Validar Tabla POS_INBOUND_DOCS
    public boolean validateTablaInboundDocs() {
    	SQLResult res = null; 
        try {
             res = sqlExecute(QueryInbound,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            if (res != null && !res.isEmpty() && res.getRowCount() > 0 && res.getColumnCount() > 0) {
                lastRetrievedId = res.getData(0,0);
                System.out.println("ID extraído: " + lastRetrievedId);

                if (testcase != null) {
                    System.out.println("No es nulo testcase");
                  
                    
                } else {
                    System.out.println("testcase es nulo o no existe");
					testcase.addQueryEvidenceCurrentStep(res);
                }

				testcase.addQueryEvidenceCurrentStep(res);
                return true;
            } else {
                System.out.println("No se encontraron registros en la consulta o no hay columnas.");
               testcase.addQueryEvidenceCurrentStep(res);
				return false;
            }

        } catch (Exception e) {
        	testcase.addQueryEvidenceCurrentStep(res);
           return false;
        }
    }


    String QueryInboundHEF = "SELECT * FROM (select ID, PE_ID, STATUS, PV_DOC_NAME from posuser.pos_inbound_docs\r\n"
    		+ "            where  1=1\r\n"
    		+ "            AND PV_DOC_NAME like 'HEF10UMI50BSX2509%' order by received_date desc)WHERE ROWNUM = 1";

    public static String lastRetrievedIdHEF;

    /**
	 * Metodo para validar tabla Inbound docs HEF
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
	 */
    public boolean validateTablaInboundDocsHEF() {
    	SQLResult res = null; 
        try {
             res = sqlExecute(QueryInboundHEF,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            
            
            
            if (!res.isEmpty() && res.getRowCount() > 0) {
                lastRetrievedIdHEF = res.getData(0, 0);
                System.out.println("ID extraído: " + lastRetrievedIdHEF);
                return true;
            }

            System.out.println("No se encontraron registros en la consulta.");
			testcase.addQueryEvidenceCurrentStep(res);
            return false;

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    /**
	 * Metodo para validar tabla POS_SYB
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
	 */
    // Validar Tabla POS_SYB
    public boolean validateTablaPosSYB() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocs();

            if (lastRetrievedId == null || lastRetrievedId.isEmpty()) {
                System.out.println("ERROR: lastRetrievedId está vacío o null.");
                return false;
            }

            String QueryPOS_SYB = "SELECT * FROM POSUSER.POS_SYB WHERE PID_ID = '" + lastRetrievedId + "'";
           res = sqlExecute(QueryPOS_SYB,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            if (res != null && !res.isEmpty() && res.getRowCount() > 0 && res.getColumnCount() > 0) {
                lastRetrievedId = res.getData(0,0);
                System.out.println("ID extraído: " + lastRetrievedId);

                if (testcase != null) {
                    System.out.println("No es nulo testcase");
                  
                    
                } else {
                    System.out.println("testcase es nulo o no existe");
                }

                return true;
            } else {
                System.out.println("No se encontraron registros en la consulta o no hay columnas.");
				testcase.addQueryEvidenceCurrentStep(res);
               return false;
            }

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
           return false;
        }
    }

    /**
	 * Metodo para validar tabla SYB DETL en BD WM6QA
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
	 */
    public boolean validateTablaPosSYBDetl() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocs();

            if (lastRetrievedId == null || lastRetrievedId.isEmpty()) {
                System.out.println("ERROR: lastRetrievedId está vacío o null.");
                return false;
            }

            String QueryPOS_SYBD = "SELECT REF1,PID_ID,CANCELADO,VALOR,SERVICIO FROM POSUSER.POS_SYB_DETL WHERE PID_ID = '" + lastRetrievedId + "'";
             res = sqlExecute(QueryPOS_SYBD,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOS_SYBD);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            if (res != null && !res.isEmpty() && res.getRowCount() > 0 && res.getColumnCount() > 0) {
                lastRetrievedId = res.getData(0,0);
                System.out.println("ID extraído: " + lastRetrievedId);

                if (testcase != null) {
                    System.out.println("No es nulo testcase");
                  
                    
                } else {
                    System.out.println("testcase es nulo o no existe");
                }

                return true;
            } else {
                System.out.println("No se encontraron registros en la consulta o no hay columnas.");
				testcase.addQueryEvidenceCurrentStep(res);
               return false;
            }

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
           return false;
        }
    }

    
    /**
	 * Metodo para validar tabla HEF DETL en BD WM6QA
	 *
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
     
	 */
    public boolean validateTablaPosHEFDetl() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocsHEF();

            if (lastRetrievedIdHEF == null || lastRetrievedIdHEF.isEmpty()) {
                System.out.println("ERROR: lastRetrievedIdHEF está vacío o null.");
                return false;
            }

            String QueryPOSD_HEF = "SELECT PID_ID,PV_DATE,PREFIJO,VALOR FROM POSUSER.POS_HEF_DETL\r\n"
            		+ " WHERE PID_ID = '" + lastRetrievedIdHEF + "'";
           res = sqlExecute(QueryPOSD_HEF,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOSD_HEF);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            if (res != null && !res.isEmpty() && res.getRowCount() > 0 && res.getColumnCount() > 0) {
                lastRetrievedId = res.getData(0,0);
                System.out.println("ID extraído: " + lastRetrievedId);

                if (testcase != null) {
                    System.out.println("No es nulo testcase");
                  
                    
                } else {
                    System.out.println("testcase es nulo o no existe");
                }

                return true;
            } else {
                System.out.println("No se encontraron registros en la consulta o no hay columnas.");
				testcase.addQueryEvidenceCurrentStep(res);
               return false;
            }

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
           return false;
        }
    }

    /**
	 * Metodo para validar archivo HEF en BD WM6QA
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información 
	 */
    public boolean validateTablaPosHEF() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocsHEF();

            if (lastRetrievedIdHEF == null || lastRetrievedIdHEF.isEmpty()) {
                System.out.println("ERROR: lastRetrievedIdHEF está vacío o null.");
                return false;
            }

            String QueryPOS_HEF = "SELECT * FROM POSUSER.POS_HEF WHERE PID_ID = '" + lastRetrievedIdHEF + "'";
             res = sqlExecute(QueryPOS_HEF,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOS_HEF);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            if (res != null && !res.isEmpty() && res.getRowCount() > 0 && res.getColumnCount() > 0) {
                lastRetrievedId = res.getData(0,0);
                System.out.println("ID extraído: " + lastRetrievedId);

                if (testcase != null) {
                    System.out.println("No es nulo testcase");
                  
                    
                } else {
                    System.out.println("testcase es nulo o no existe");
                }

                return true;
            } else {
                System.out.println("No se encontraron registros en la consulta o no hay columnas.");
				testcase.addQueryEvidenceCurrentStep(res);
               return false;
            }

        } catch (Exception e) {
            
			testcase.addQueryEvidenceCurrentStep(res);
           return false;
        }
    }

    // 🔹 Nube ------------------------------------------------------------------------------------------------------

    String QueryCBancoNube = "SELECT * FROM apps.wm_ol9_outbound_docs WHERE service_id \r\n"
            + "IN ('101960422') AND DOC_NAME LIKE 'CBANCO.FBB' || TO_CHAR(SYSDATE - 4, 'YYYYMMDD')\r\n"
            + "ORDER BY created_date DESC";

    
    
    
    /**
	 * Metodo para validar archivo CBANCO nube en BD
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos return false si no encuentra información
   
	 */
    public boolean validateArchivoCBancoNube() {
    	SQLResult res = null; 
        try {
             res = sqlExecute(QueryCBancoNube,
                    GlobalVariables.DB_HOST_CFE_EBS,
                    GlobalVariables.DB_USER_CFE_EBS,
                    GlobalVariables.DB_PASSWORD_CFE_EBS);
            testcase.addQueryEvidenceCurrentStep(res);
            System.out.println("Resultado completo:\n" + res);

            return !res.isEmpty();

        } catch (Exception e) {
          
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    String QueryInboundNube = "SELECT ID,\r\n"
    		+ "       PE_ID,\r\n"
    		+ "       STATUS,\r\n"
    		+ "       DOC_TYPE,\r\n"
    		+ "       PV_DOC_NAME,\r\n"
    		+ "       RECEIVED_DATE\r\n"
    		+ "FROM (\r\n"
    		+ "    SELECT ID,\r\n"
    		+ "           PE_ID,\r\n"
    		+ "           STATUS,\r\n"
    		+ "           DOC_TYPE,\r\n"
    		+ "           PV_DOC_NAME,\r\n"
    		+ "           RECEIVED_DATE\r\n"
    		+ "    FROM posuser.pos_inbound_docs\r\n"
    		+ "    WHERE STATUS = 'I'\r\n"
    		+ "      AND pv_Doc_name LIKE 'SYB10UMI50ASA25%'\r\n"
    		+ "    ORDER BY received_date DESC\r\n"
    		+ ")\r\n"
    		+ "WHERE ROWNUM = 1\r\n"
    		+ ""
    		;

    public static String lastRetrievedIdNube;

    
    /**
	 * Metodo para validar tabla inbound docs con tienda nube
	 * @author Alan Perez
	 * @throws
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
   
	 */
    
    public boolean validateTablaInboundDocsNube() {
    	SQLResult res = null; 
        try {
             res = sqlExecute(QueryInboundNube,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            
            testcase.addQueryEvidenceCurrentStep(res);
            if (!res.isEmpty() && res.getRowCount() > 0) {
                lastRetrievedIdNube = res.getData(0, 0);
                System.out.println("ID extraído: " + lastRetrievedIdNube);
                testcase.addQueryEvidenceCurrentStep(res);
                return true;
            }

            System.out.println("No se encontraron registros en la consulta.");
			testcase.addQueryEvidenceCurrentStep(res);
            return false;

        } catch (Exception e) {
            
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    String QueryInboundHEFNube = "SELECT * FROM (select ID, PE_ID, STATUS, PV_DOC_NAME from posuser.pos_inbound_docs where  1=1 AND PV_DOC_NAME like 'HEF10UMI50ASA25%' order by received_date desc)WHERE ROWNUM = 1";

    public static String lastRetrievedIdHEFNube;

    /**
	 * Metodo para validar tabla inbound docs con el archivo HEF en la tienda nube
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
      
	 */
    
    public boolean validateTablaInboundDocsHEFNube() {
    	SQLResult res = null; 
        try {
             res = sqlExecute(QueryInboundHEFNube,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

          
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            if (!res.isEmpty() && res.getRowCount() > 0) {
                lastRetrievedIdHEFNube = res.getData(0, 0);
                System.out.println("ID extraído: " + lastRetrievedIdHEFNube);
                return true;
            }

            System.out.println("No se encontraron registros en la consulta.");
			testcase.addQueryEvidenceCurrentStep(res);
            return false;

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    
    
    
    /**
	 * Metodo para validar tabla POS SYB Nube
	 * @author Alan Perez
	 * @throws 
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
  
	 */
    public boolean validateTablaPosSYBNube() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocsNube();

            if (lastRetrievedIdNube == null || lastRetrievedIdNube.isEmpty()) {
                System.out.println("ERROR: lastRetrievedIdNube está vacío o null.");
                return false;
            }

            String QueryPOS_SYBNube = "SELECT * FROM POSUSER.POS_SYB WHERE PID_ID = '" + lastRetrievedIdNube + "'";
             res = sqlExecute(QueryPOS_SYBNube,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOS_SYBNube);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    
    
    
    /**
	 * Metodo para validar tabla POS SYB DETL para la tienda Nube
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
  
	 */
    public boolean validateTablaPosSYBDetlNube() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocsNube();

            if (lastRetrievedIdNube == null || lastRetrievedIdNube.isEmpty()) {
                System.out.println("ERROR: lastRetrievedIdNube está vacío o null.");
                return false;
            }

            String QueryPOS_SYBDNube = "SELECT REF1,PID_ID,CANCELADO,VALOR,SERVICIO FROM POSUSER.POS_SYB_DETL WHERE PID_ID = '" + lastRetrievedIdNube + "'";
            res = sqlExecute(QueryPOS_SYBDNube,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOS_SYBDNube);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    
    /**
	 * Metodo para validar tabla POS HEF DETL para tienda nube
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
    
	 */
    public boolean validateTablaPosHEFDetlNube() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocsHEFNube();

            if (lastRetrievedIdHEFNube == null || lastRetrievedIdHEFNube.isEmpty()) {
                System.out.println("ERROR: lastRetrievedIdHEFNube está vacío o null.");
                return false;
            }

            String QueryPOS_HEFDNube = "SELECT PID_ID, PV_DATE, PREFIJO, VALOR " +
                    "FROM POSUSER.POS_HEF_DETL " +
                    "WHERE PID_ID = '" + lastRetrievedIdHEFNube + "'";
             res = sqlExecute(QueryPOS_HEFDNube,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOS_HEFDNube);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
            
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    
    /**
	 * Metodo para validar tabla POS HEF DETL para tienda nube
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
   
	 */
    public boolean validateTablaPosHEFNube() {
    	SQLResult res = null; 
        try {
            validateTablaInboundDocsHEFNube();

            if (lastRetrievedIdHEFNube == null || lastRetrievedIdHEFNube.isEmpty()) {
                System.out.println("ERROR: lastRetrievedIdHEFNube está vacío o null.");
                return false;
            }

            String QueryPOS_HEFNube = "SELECT * FROM POSUSER.POS_HEF WHERE PID_ID = '" + lastRetrievedIdHEFNube + "'";
             res = sqlExecute(QueryPOS_HEFNube,
                    GlobalVariables.DB_HOST_FCWMQA_CFE,
                    GlobalVariables.DB_USER_FCWMQA_CFE,
                    GlobalVariables.DB_PASSWORD_FCWMQA_CFE);

            System.out.println("Query generado: " + QueryPOS_HEFNube);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
           
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    
    /**
	 * Metodo para validar tabla servicios pre en BD
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si encuentra datos false si no encuentra información
      
	 */
    public boolean validateTablaServiciosPRE() {
    	
    	SQLResult res = null; 
        try {
            String QueryServiciosPRE = 
            		"SELECT TIENDA,\r\n"
            		+ "       FOLIO_TRANSACCION,\r\n"
            		+ "       REF1,\r\n"
            		+ "       VALOR\r\n"
            		+ "FROM (\r\n"
            		+ "    SELECT TIENDA,\r\n"
            		+ "           FOLIO_TRANSACCION,\r\n"
            		+ "           REF1,\r\n"
            		+ "           VALOR\r\n"
            		+ "    FROM APPS.XXFC_PAGO_SERVICIOS_PRE\r\n"
            		+ "    WHERE TIENDA = '50BSX'\r\n"
            		+ "      AND SERVICIO = '101960422'\r\n"
            		+ "    ORDER BY FECHA_TRANSACCION DESC\r\n"
            		+ ")\r\n"
            		+ "WHERE ROWNUM <= 10";

            res = sqlExecute(QueryServiciosPRE,
                    GlobalVariables.DB_HOST_CFE_EBS,
                    GlobalVariables.DB_USER_CFE_EBS,
                    GlobalVariables.DB_PASSWORD_CFE_EBS);

            System.out.println("Query generado: " + QueryServiciosPRE);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
           
        	 testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }
    
    
    /**
   	 * Metodo para validar tabla servicios pre en BD con estatus E
   	 * @author Alan Perez
     * @date 03/10/2025
     * @return true si encuentra datos @return false si no encuentra información	
   	 */

    public boolean validateTablaServiciosPREStatusE() {
    	SQLResult res = null; 
        try {
            String QueryServiciosPRE = "SELECT estatus, tienda, fecha_transaccion FROM apps.xxfc_pago_servicios_pre WHERE servicio = '101960422'\r\n"
            		+ "                    AND estatus ='E'\r\n"
            		+ "                    AND tienda = '50BSX'\r\n"
            		+ "                    ORDER BY fecha_transaccion DESC";

             res = sqlExecute(QueryServiciosPRE,
                    GlobalVariables.DB_HOST_CFE_EBS,
                    GlobalVariables.DB_USER_CFE_EBS,
                    GlobalVariables.DB_PASSWORD_CFE_EBS);

            System.out.println("Query generado: " + QueryServiciosPRE);
            System.out.println("Resultado completo:\n" + sqlResultToString(res));
            testcase.addQueryEvidenceCurrentStep(res);
            return true;

        } catch (Exception e) {
            
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }

    
    
    /**
   	 * Metodo para validar archivo CBANCO EN BD
   	 * @author Alan Perez
     * @date 03/10/2025
     * @return true si encuentra datos false si no encuentra información
   	 */
    public boolean validateTablaArchivoCbanco() {
    	SQLResult res = null; 
        try {
            String QueryArchivosCbanco = "SELECT * FROM apps.wm_ol9_outbound_docs WHERE service_id \r\n"
                    + "IN ('101960422') AND DOC_NAME LIKE 'CBANCO.FBB' || TO_CHAR(SYSDATE - 4, 'YYYYMMDD')\r\n"
                    + "ORDER BY created_date DESC";

             res = sqlExecute(QueryArchivosCbanco,
                    GlobalVariables.DB_HOST_CFE_EBS,
                    GlobalVariables.DB_USER_CFE_EBS,
                    GlobalVariables.DB_PASSWORD_CFE_EBS);

            System.out.println("Query generado: " + QueryArchivosCbanco);
            System.out.println("Resultado completo:\n" + res);

            return !res.isEmpty();

        } catch (Exception e) {
            e.printStackTrace();
			testcase.addQueryEvidenceCurrentStep(res);
            return false;
        }
    }
				
}

