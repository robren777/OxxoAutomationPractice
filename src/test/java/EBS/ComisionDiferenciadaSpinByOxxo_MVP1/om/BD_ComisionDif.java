package EBS.ComisionDiferenciadaSpinByOxxo_MVP1.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;

import EBS.util.GlobalVariables;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class BD_ComisionDif {

	
	
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private WebDriver driver;
	private SikuliUtil sk;
	private HashMap<String, Pattern> ImgPattern;
	
	
	 // Obtener la fecha actual
    Date fechaActual = new Date();
    
    // Formatear la fecha actual en el formato adecuado para Oracle SQL (YYYY/MM/DD)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    String fechaFormateada = sdf.format(fechaActual);
    
    // Crear la consulta SQL con la fecha formateada
    String queryEstatusE = "FROM   xxfc.xxfc_pago_servicios\r\n"
            + "WHERE  \r\n"
            + "org_id = '00099'--pCia\r\n"
            + "--AND    plaza = '10MXL'--vItemRsva10.site\r\n"
            + "AND servicio ='102023553'\r\n"
            + "AND   lote IS NOT NULL\r\n"
            + "AND   atributo4 IS NULL\r\n"
            + "AND    (fecha_transmision >= TO_DATE('" + fechaFormateada + "', 'YYYY/MM/DD') AND\r\n"
            + "       fecha_transmision <= TO_DATE('" + fechaFormateada + "', 'YYYY/MM/DD')\r\n"
            + "      )\r\n"
            + "AND   poliza_OS IS NULL\r\n"
            + "AND    estatus = 'E'";
	String ValidarItemExistenteNull ="SELECT * FROM XXFC_MAPEOS_VARIOS WHERE TIPO_MAPEO LIKE '%CORR%' AND SALIDA1 = '102023553' AND SALIDA4 =' null'";

    
    String itemExistente="SELECT * FROM XXFC_MAPEOS_VARIOS WHERE TIPO_MAPEO LIKE '%CORR%' AND SALIDA1 = '102023553'";
    
    String queryItems = "SELECT * FROM XXFC_MAPEOS_VARIOS WHERE TIPO_MAPEO LIKE '%CORR%' AND SALIDA1  IN ( '115810169', '102023553')";
    
    String ValidarItemNuevoNull="SELECT * FROM XXFC_MAPEOS_VARIOS WHERE TIPO_MAPEO LIKE '%CORR%' AND SALIDA1 = '115810169' AND SALIDA4='null'";
    
    String itemVacioNuevo = "SELECT* FROM XXFC_MAPEOS_VARIOS WHERE TIPO_MAPEO LIKE'%CORR%' AND SALIDA1='115810169' AND SALIDA4=''";
    
    String itemNuevo = "SELECT * FROM XXFC_MAPEOS_VARIOS WHERE TIPO_MAPEO LIKE '%CORR%' AND SALIDA1 = '115810169'";

	public void Bd_Comisionif(SeleniumUtil u, TestCase testCase, SikuliUtil sk,HashMap<String, Pattern> ImgPattern) {
		
		this.u = u;
		this.sk = sk;
		this.driver = u.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		this.testCase = testCase;
		this.ImgPattern = ImgPattern;
	}
	
	//Script Alan
		//Se genera conexion a BD ORACLE
		public static Connection getConnection() throws SQLException {
			
			System.out.println("Se ejecuta la conexión");
			System.out.println(DriverManager.getConnection(GlobalVariables.DB_HOST_Comision_EBS , GlobalVariables.DB_USER_Comision_EBS , GlobalVariables.DB_PASSWORD_Comision_EBS));
	        return DriverManager.getConnection(GlobalVariables.DB_HOST_Comision_EBS , GlobalVariables.DB_USER_Comision_EBS , GlobalVariables.DB_PASSWORD_Comision_EBS);
	   
	
		
		}
	
		//Executamos el query 
		public static ResultSet executeQuery(String query) {
	        Connection connection = null;
	        Statement statement = null;
	        ResultSet resultSet = null;

	        try {
	            connection = getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // No cerrar aquí para permitir el uso del resultSet fuera de este método
	            // Se recomienda cerrar la conexión y el statement en el método donde se usa el ResultSet
	        }

	        return resultSet;
	    }
		
		
		
		
		
		//Metodo para validar un texto en cierta columna de BD
		public static boolean containsText(ResultSet resultSet, String columnName, String searchText) {
	        boolean found = false;

	        try {
	            while (resultSet.next()) {
	                String columnValue = resultSet.getString(columnName);
	                if (columnValue != null && columnValue.contains(searchText)) {
	                    found = true;
	                    break; // Salir del bucle si se encuentra el texto
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Cerrar el ResultSet en el mismo método o contexto donde se usa
	            try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return found;
	    }
		
		//Se valida la tabla de xxfc_pago_servicios
		public void ValidarEstatusE(String ColumnName, String searchText) {
			
			
			String Estatus="";
			
			//Ejecutar consulta para validar el valor en null 
			 try (ResultSet resultSet = executeQuery(queryEstatusE)) {
				 System.out.println(resultSet);
			        // Verificar si hay al menos una fila en el ResultSet
			        if (resultSet.next()) {
			            // Obtener el valor de la columna RECONCILIED  	
			           Estatus = resultSet.getNString(ColumnName);

			            // Imprimir el valor obtenido
			            System.out.println("El valor de Estatus es: " + Estatus);
			            

			            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
			            while (resultSet.next()) {
			                // Puedes acceder a más datos si es necesario
			                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
			                Estatus = resultSet.getNString("ESTATUS");
			                
			                System.out.println("Valor de Estatus en otra fila: " + Estatus);
			                
			                if(Estatus==searchText) {
			                	System.out.println("El valor de reconcilied es: "+ Estatus +"El valor del texto buscado es:"+searchText);
			                }
			                else {
			                	System.out.println(Estatus);
			                }
			            }
			        } else {
			        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
			        	System.out.println("El valor de la columna es: " + Estatus);
			            System.out.println("No se encontraron resultados.");
			        }
			    } catch (SQLException e) {
			        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
			    }
			 
			
			
			
		}
		
		//Se validan los items de item existente e item nuevo 

		public void Validaritems() {
			// TODO Auto-generated method stub
	String Salida4="";
	String Salida1="";	
			//Ejecutar consulta para validar el valor en null 
			 try (ResultSet resultSet = executeQuery(queryItems)) {
				 System.out.println(resultSet);
			        // Verificar si hay al menos una fila en el ResultSet
			        if (resultSet.next()) {
			            // Obtener el valor de la columna RECONCILIED  	
			           Salida4 = resultSet.getNString("SALIDA4");
                       Salida1 = resultSet.getNString("SALIDA1");
			            // Imprimir el valor obtenido
			            System.out.println("El valor de salida4 es: " +  Salida4);
			            

			            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
			            while (resultSet.next()) {
			                // Puedes acceder a más datos si es necesario
			                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
			            	 Salida4 = resultSet.getNString("SALIDA4");
			                 Salida1 = resultSet.getNString("SALIDA1");      
			                System.out.println("Valor de salida4 en otra fila: " + Salida4 );
			                
			                if(Salida4=="") {
			                	System.out.println("El valor de salida4 es: "+ Salida4 +"El valor del texto buscado es:"+"");
			                	System.out.println("El valor de la salida1 es: "+ Salida1);
			                }
			                else {
			                	System.out.println(Salida4);
			                }
			            }
			        } else {
			        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
			        	System.out.println("El valor de la columna es: " +Salida4);
			            System.out.println("No se encontraron resultados.");
			        }
			    } catch (SQLException e) {
			        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
			    }
			 
		}
		
public void ValidaritemNuevo() {
	//Se valida la tabla de xxfc_pago_servicios
			
				String Estado="";
				String Salida1="";
				String Salida4="";
				//Ejecutar consulta para validar el valor en null 
				 try (ResultSet resultSet = executeQuery(itemNuevo)) {
					 System.out.println(resultSet);
				        // Verificar si hay al menos una fila en el ResultSet
				        if (resultSet.next()) {
				            // Obtener el valor de la columna RECONCILIED  	
				           Estado = resultSet.getNString("ESTADO");
                           Salida1 = resultSet.getNString("SALIDA1");
                           Salida4 = resultSet.getNString("SALIDA4");
				            // Imprimir el valor obtenido
				            System.out.println("El valor de Estatus es: " + Estado);
				            

				            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
				            while (resultSet.next()) {
				                // Puedes acceder a más datos si es necesario
				                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
				                Estado = resultSet.getNString("ESTADO");
				                Salida1= resultSet.getNString("SALIDA1");
				                Salida4 = resultSet.getNString("SALIDA4");
				                System.out.println("Valor de Estatus en otra fila: " + Estado);
				                System.out.println("Valor de Salida1 en otra fila: " + Salida1);
				                System.out.println("Valor de Salida4 en otra fila: " + Salida4);
				            }
				        } else {
				        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
				        	System.out.println("El valor de la columna es: " + Salida4);
				            System.out.println("No se encontraron resultados.");
				        }
				    } catch (SQLException e) {
				        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
				    }
				 
				
				
}


public void ValidarNullItemExistente() {
	//Se valida la tabla de xxfc_pago_servicios
	
	String Estado="";
	String Salida1="";
	String Salida4="";
	//Ejecutar consulta para validar el valor en null 
	 try (ResultSet resultSet = executeQuery(ValidarItemExistenteNull )) {
		 System.out.println(resultSet);
	        // Verificar si hay al menos una fila en el ResultSet
	        if (resultSet.next()) {
	            // Obtener el valor de la columna RECONCILIED  	
	           Estado = resultSet.getNString("ESTADO");
               Salida1 = resultSet.getNString("SALIDA1");
               Salida4 = resultSet.getNString("SALIDA4");
	            // Imprimir el valor obtenido
	            System.out.println("El valor de Estatus es: " + Estado);
	            
              if(Salida4!=null) {
            	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
		        	System.out.println("El valor de la columna es: " + Salida4);
		            System.out.println("No se encontraron resultados.");
	           
	            }
              }
	        else {
	        	 // Si necesitas continuar procesando más filas, puedes hacerlo aquí
	            while (resultSet.next()) {
	                // Puedes acceder a más datos si es necesario
	                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
	                Estado = resultSet.getNString("ESTADO");
	                Salida1= resultSet.getNString("SALIDA1");
	                Salida4 = resultSet.getNString("SALIDA4");
	                System.out.println("Valor de Estatus en otra fila: " + Estado);
	                System.out.println("Valor de Salida1 en otra fila: " + Salida1);
	                System.out.println("Valor de Salida4 en otra fila: " + Salida4);
	        	
	        }
	        }
	 }
	     catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	    }
}

public void ValidarNullItemNuevo() {
	//Se valida la tabla de xxfc_pago_servicios
	
		String Estado="";
		String Salida1="";
		String Salida4="";
		//Ejecutar consulta para validar el valor en null 
		 try (ResultSet resultSet = executeQuery(ValidarItemNuevoNull)) {
			 System.out.println(resultSet);
		        // Verificar si hay al menos una fila en el ResultSet
		        if (resultSet.next()) {
		            // Obtener el valor de la columna RECONCILIED  	
		           Estado = resultSet.getNString("ESTADO");
	               Salida1 = resultSet.getNString("SALIDA1");
	               Salida4 = resultSet.getNString("SALIDA4");
		            // Imprimir el valor obtenido
		            System.out.println("El valor de Estatus es: " + Estado);
		            
                  if(Salida4!=null) {
                	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
  		        	System.out.println("El valor de la columna es: " + Salida4);
  		            System.out.println("No se encontraron resultados.");
		           
		            }
                  }
		        else {
		        	 // Si necesitas continuar procesando más filas, puedes hacerlo aquí
		            while (resultSet.next()) {
		                // Puedes acceder a más datos si es necesario
		                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
		                Estado = resultSet.getNString("ESTADO");
		                Salida1= resultSet.getNString("SALIDA1");
		                Salida4 = resultSet.getNString("SALIDA4");
		                System.out.println("Valor de Estatus en otra fila: " + Estado);
		                System.out.println("Valor de Salida1 en otra fila: " + Salida1);
		                System.out.println("Valor de Salida4 en otra fila: " + Salida4);
		        	
		        }
		        }
		 }
		     catch (SQLException e) {
		        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		    }
	
}




public void ValidaritemExistente() {
	// TODO Auto-generated method stub
	//Se valida la tabla de xxfc_pago_servicios
	
	String Estado="";
	String Salida1="";
	String Salida4="";
	//Ejecutar consulta para validar el valor en null 
	 try (ResultSet resultSet = executeQuery(itemExistente)) {
		 System.out.println(resultSet);
	        // Verificar si hay al menos una fila en el ResultSet
	        if (resultSet.next()) {
	            // Obtener el valor de la columna RECONCILIED  	
	           Estado = resultSet.getNString("ESTADO");
               Salida1 = resultSet.getNString("SALIDA1");
               Salida4 = resultSet.getNString("SALIDA4");
	            // Imprimir el valor obtenido
	            System.out.println("El valor de Estatus es: " + Estado);
	            

	            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
	            while (resultSet.next()) {
	                // Puedes acceder a más datos si es necesario
	                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
	                Estado = resultSet.getNString("ESTADO");
	                Salida1= resultSet.getNString("SALIDA1");
	                Salida4 = resultSet.getNString("SALIDA4");
	                System.out.println("Valor de Estatus en otra fila: " + Estado);
	                System.out.println("Valor de Salida1 en otra fila: " + Salida1);
	                System.out.println("Valor de Salida4 en otra fila: " + Salida4);
	            }
	        } else {
	        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
	        	System.out.println("El valor de la columna es: " + Salida4);
	            System.out.println("No se encontraron resultados.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	    }
}

public void ValidarVacioitemNuevo() {
	// TODO Auto-generated method stub
	
		//Se valida la tabla de xxfc_pago_servicios
		
		String Estado="";
		String Salida1="";
		String Salida4="";
		//Ejecutar consulta para validar el valor en null 
		 try (ResultSet resultSet = executeQuery(itemVacioNuevo)) {
			 System.out.println(resultSet);
		        // Verificar si hay al menos una fila en el ResultSet
		        if (resultSet.next()) {
		            // Obtener el valor de la columna RECONCILIED  	
		           Estado = resultSet.getNString("ESTADO");
	               Salida1 = resultSet.getNString("SALIDA1");
	               Salida4 = resultSet.getNString("SALIDA4");
		            // Imprimir el valor obtenido
		            System.out.println("El valor de Estatus es: " + Estado);
		            

		            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
		            while (resultSet.next()) {
		                // Puedes acceder a más datos si es necesario
		                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
		                Estado = resultSet.getNString("ESTADO");
		                Salida1= resultSet.getNString("SALIDA1");
		                Salida4 = resultSet.getNString("SALIDA4");
		                System.out.println("Valor de Estatus en otra fila: " + Estado);
		                System.out.println("Valor de Salida1 en otra fila: " + Salida1);
		                System.out.println("Valor de Salida4 en otra fila: " + Salida4);
		            }
		        } else {
		        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
		        	System.out.println("El valor de la columna es: " + Salida4);
		            System.out.println("No se encontraron resultados.");
		        }
		    } catch (SQLException e) {
		        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		    }
	}

public void ValoresNoValidosItemNuevo() {
	// TODO Auto-generated method stub
	//Se valida la tabla de xxfc_pago_servicios
	
		String Estado="";
		String Salida1="";
		String Salida4="";
		//Ejecutar consulta para validar el valor en null 
		 try (ResultSet resultSet = executeQuery(itemNuevo)) {
			 System.out.println(resultSet);
		        // Verificar si hay al menos una fila en el ResultSet
		        if (resultSet.next()) {
		            // Obtener el valor de la columna RECONCILIED  	
		           Estado = resultSet.getNString("ESTADO");
	               Salida1 = resultSet.getNString("SALIDA1");
	               Salida4 = resultSet.getNString("SALIDA4");
		            // Imprimir el valor obtenido
		            System.out.println("El valor de Estatus es: " + Estado);
		         // Validación de Salida4
		            if (Salida4 != null && !Salida4.trim().isEmpty() && !Salida4.equals("Y") && !Salida4.equals("N")) {
		                System.out.println("Salida4 tiene un valor válido: " + Salida4);
		            } else {
		                System.out.println("Salida4 tiene un valor inválido o está vacío.");
		            }

		            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
		            while (resultSet.next()) {
		                // Puedes acceder a más datos si es necesario
		                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
		                Estado = resultSet.getNString("ESTADO");
		                Salida1= resultSet.getNString("SALIDA1");
		                Salida4 = resultSet.getNString("SALIDA4");
		                System.out.println("Valor de Estatus en otra fila: " + Estado);
		                System.out.println("Valor de Salida1 en otra fila: " + Salida1);
		                System.out.println("Valor de Salida4 en otra fila: " + Salida4);
		            }
		        } else {
		        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
		        	System.out.println("El valor de la columna es: " + Salida4);
		            System.out.println("No se encontraron resultados.");
		        }
		    } catch (SQLException e) {
		        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		    }
}




public void ValidarMAPEOS(String ColumnName, String searchText) {
	
	
	String Valor="";
	
	//Ejecutar consulta para validar el valor en null 
	 try (ResultSet resultSet = executeQuery(queryItems)) {
		 System.out.println(resultSet);
	        // Verificar si hay al menos una fila en el ResultSet
	        if (resultSet.next()) {
	            // Obtener el valor de la columna RECONCILIED  	
	           Valor = resultSet.getNString(ColumnName);

	            // Imprimir el valor obtenido
	            System.out.println("El valor de Estatus es: " + Valor);
	            

	            // Si necesitas continuar procesando más filas, puedes hacerlo aquí
	            while (resultSet.next()) {
	                // Puedes acceder a más datos si es necesario
	                // Por ejemplo, imprimir el valor de RECONCILIED de las siguientes filas
	                Valor = resultSet.getNString(ColumnName);
	                
	                System.out.println("Valor de Estatus en otra fila: " + Valor);
	                
	                if(Valor==searchText) {
	                	System.out.println("El valor de reconcilied es: "+ Valor +"El valor del texto buscado es:"+searchText);
	                }
	                else {
	                	System.out.println(Valor);
	                }
	            }
	        } else {
	        	//Si imprime 0 significa que no se modifico el valor y no encontro resultados
	        	System.out.println("El valor de la columna es: " + Valor);
	            System.out.println("No se encontraron resultados.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	    }
	 
	
	
	
}

}
