package IMA.ComisionDifSpinByOxxo_Julio2024.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import IMA.util.GlobalVariables;





public class BD_IMA {
	
	
	
	
	public String Fecha;
	//Consultas
	public String SelectTransactionsEBS="select a.trans_id, a.plaza, a.tienda, a.folio_transaccion, a.servicio, a.consecutivo, a.fecha_transaccion,a.hora_transaccion, a.ref1, a.ref2, a.corte, a.caja, a.ticket, a.valor, a.comision, a.fecha_recepcion, a.estatus, a.fecha_transmision, a.lote, a.validated, a.org_id, a.oracle_cr, a.factura, a.factura_ar,a.gl, a.id_servicio, a.oracle_cia, a.xxfc_fecha_administrativa, a.poliza_os, a.carta_femsa, a.last_update_date,a.last_updated_by, a.atributo1, a.atributo2, a.atributo3, a.atributo4, a.atributo5, sysdate from xxfc.xxfc_pago_servicios a where a.fecha_transaccion = to_date("+Fecha+",'DD/MM/YYYY')and a.estatus = 'E' and TIENDA='50ENM' and a.GL = 'Y' and a.atributo5 is null";
	
	
	
	
	  /*
     * metodo generado para realizar la conexion a la BD de manera correcta
     * 
     * 
     * @author Alan Perez
     */
	
	 // Método para obtener la conexión a la base de datos Oracle
    public static Connection  obtenerConexion() {
       Connection conexion = null;
        
        try {
            // Establecer la conexión con la base de datos
		conexion = DriverManager.getConnection(GlobalVariables.DB_HOST_Comision_IMA , GlobalVariables.DB_USER_Comision_IMA , GlobalVariables.DB_PASSWORD_Comision_IMA);
         //   conexion = DriverManager.getConnection("jdbc:oracle:thin:@//oxfdbfinq1-vip.femcom.net:1532/AVEBQA", "appsview", "appsview");
            System.out.println("Conexión exitosa a la base de datos.");
            System.out.println(conexion.toString());
        } catch (SQLException e) {
            // Manejar errores de conexión
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        
       return conexion;
        
    }
    
    
    
 // Consulta SQL con la fecha formateada correctamente
    public static String SelectTransactionsEBS1 = 
        "select a.trans_id, a.plaza, a.tienda, a.folio_transaccion, a.servicio, a.consecutivo, a.fecha_transaccion,a.hora_transaccion, a.ref1, a.ref2, a.corte, a.caja, a.ticket, a.valor, a.comision, a.fecha_recepcion, a.estatus, a.fecha_transmision, a.lote, a.validated, a.org_id, a.oracle_cr, a.factura, a.factura_ar,a.gl, a.id_servicio, a.oracle_cia, a.xxfc_fecha_administrativa, a.poliza_os, a.carta_femsa, a.last_update_date,a.last_updated_by, a.atributo1, a.atributo2, a.atributo3, a.atributo4, a.atributo5, sysdate from xxfc.xxfc_pago_servicios a where a.fecha_transaccion = to_date('22/11/2024','DD/MM/YYYY')and a.estatus = 'E' and TIENDA='50ENM' and a.GL = 'Y' and a.atributo5 is null";

    
    /*
     * metodo generado para realizar un select con la fecha correspondiente
     * 
     * @param fecha
     * @author Alan Perez
     */
    
    
    // Método para ejecutar la consulta SQL con fecha
    public boolean SelectTransactions(String fecha) {
        if(fecha!=null) {
    	System.out.println("Fecha recibida: " + fecha);
        executeQuery( SelectTransactionsEBS);
        return true;
        }
        else {
        	return false;
        }
       
    }
    
 
    

    /*
     * metodo generado para realizar la ejecucion del query de manera correcta
     * 
     * @param query
     * @author Alan Perez
     */
    	
    	//Executamos el query 
    	public static ResultSet executeQuery(String query) {
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {
            	
           connection =  obtenerConexion();
                statement = connection.createStatement();
                try {
					resultSet = statement.executeQuery(SelectTransactionsEBS1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

               
               imprimirResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // No cerrar aquí para permitir el uso del resultSet fuera de este método
                // Se recomienda cerrar la conexión y el statement en el método donde se usa el ResultSet
           
            
            
            }

            return resultSet;
            
        }
    	
    	
    	 /*
         * metodo generado para obtener el resultado del query ejecutado
         * 
         * @param query
         * @author Alan Perez
         */
    	
    	// Método para imprimir los resultados de un ResultSet
        public static void imprimirResultSet(ResultSet resultSet) {
            try {
                // Obtener el número de columnas del ResultSet
                int numeroColumnas = resultSet.getMetaData().getColumnCount();

                // Imprimir los nombres de las columnas
                for (int i = 1; i <= numeroColumnas; i++) {
                    System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println(); // Salto de línea después de imprimir los nombres de las columnas

                // Recorrer las filas del ResultSet
                while (resultSet.next()) {
                    for (int i = 1; i <= numeroColumnas; i++) {
                        // Imprimir el valor de cada columna
                        System.out.print(resultSet.getString(i) + "\t");
                    }
                    System.out.println(); // Salto de línea después de cada fila
                }

            } catch (SQLException e) {
                System.out.println("Error al imprimir los resultados.");
                e.printStackTrace();
            }
        }
    	
}
