package WorkFlows.util.Cuanti;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class UtilsMecanicas {
	
	/*
	 * Variables para conexión a la base de datos
	 */
	String dbUser = GlobalVariables.DB_USERNAME_CUANTI;
	String dbPass = GlobalVariables.DB_PASS_CUANTI;
	String dbHost = GlobalVariables.DB_HOST_CUANTI;
	
	private String query;
	private String folio;
	
	/**
	 * @author José Zárate
	 * Busca un folio del tipo de mecanica necesaria para ña prueba
	 * @param bExecution --> Clase heredada para utilizar los metodos del framework
	 * @param tipoFolio  --> Tipo de folio que se encesita para la prueba
	 * @return --> El folio del tipo seleccionado
	 * @throws SQLException
	 */
	public String getFolio(BaseExecution bExecution, String tipoFolio) throws SQLException {
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		switch(tipoFolio) {
			case "simple-Descuento":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1002 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "simple-Precio":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1001 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "multiple-Unidad":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1003 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "multiple-Precio":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1004 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "promopack - Suma art = precio":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1005 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "promopack-art+precio=art2":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1006 and xxpro_promocion.promocion_estatus_id = 1";
				break;			
			case "promopack-art gratis":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1007 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "promopack-art = descuento art2":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1008 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "comunicación e imagen - Sin Precio":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1009 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "comunicación e imagen - Precio Regular":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1010 and xxpro_promocion.promocion_estatus_id = 1";
				break;
			case "promopack servicios - Servicio mas precio":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1011 and xxpro_promocion.promocion_estatus_id = 1";
				break;
		}
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			folio = ExecuteQuery.getFirstData();
			System.out.println("El folio de tipo de promocion "+tipoFolio+"es: "+folio);
			db.close();
		}else {
			System.out.println("No se encontro");
			db.close();
		}
		
		return folio;
	}
	
	/**
	 * @author José Zárate
	 * Obtiene el folio del tipo de mecánica especificado con estatus "Borrador"
	 * @param bExecution --> Clase heredada para utilizar los metodos del framework
	 * @param tipoFolio	 --> Tipo de folio que se necesita para la prueba
	 * @return --> El folio del tipo seleccionado
	 * @throws SQLException
	 */
	public String getFolioAdmin(BaseExecution bExecution, String tipoFolio) throws SQLException{
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		switch(tipoFolio) {
			case "simple-Descuento":
				query = "select max(promocion_id) from xxpro_promocion where tipo_mecanica_id = 1 and promocion_estatus_id = 1";
			break;
			case "multiple-Unidad":
				//query = "select max(promocion_id) from xxpro_promocion where tipo_mecanica_id = 2 and promocion_estatus_id = 1";
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1003 and xxpro_promocion.promocion_estatus_id = 1 and xxpro_promocion_mecanica.promocion_id between 170396 and 358625";
			break;
			case "multiple-Precio":
				query = "select max(xxpro_promocion_mecanica.promocion_id) from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1004 and xxpro_promocion.promocion_estatus_id = 1";
			break;
			case "promopack - Suma art = precio":
				query = "select max(promocion_id) from xxpro_promocion where tipo_mecanica_id = 3 and promocion_estatus_id = 1 and promocion_id between 168927 and 359245";
			break;
			case "comunicación e imagen - Sin Precio":
				query = "select max(promocion_id) from xxpro_promocion where tipo_mecanica_id = 4 and promocion_estatus_id = 1 and promocion_id between 167627 and 359243";
			break;			
			case "promopack servicios - Servicio mas precio":
				query = "select max(promocion_id) from xxpro_promocion where tipo_mecanica_id = 5 and promocion_estatus_id = 1";
			break;
		}
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			folio = ExecuteQuery.getFirstData();
			System.out.println("El folio de tipo de promocion "+tipoFolio+"es: "+folio);
			db.close();
		}else {
			System.out.println("No se encontro");
			db.close();
		}
		
		return folio;
	}
	
	/**
	 * @author José Zárate
	 * Obtiene 3 folios del tipo de mecánica especificado con estatus "Borrador"
	 * @param bExecution --> Clase heredada para utilizar los metodos del framework
	 * @param tipoFolio	 --> Tipo de folio que se necesita para la prueba
	 * @return --> El folio del tipo seleccionado
	 * @throws SQLException
	 */
	public String getFolios(BaseExecution bExecution, String tipoFolio) throws SQLException{
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		List<String[]> folios;
		int count = 1;
		
		switch(tipoFolio) {
			case "simple-Descuento":
				query = "select * from (select promocion_id from xxpro_promocion where tipo_mecanica_id = 1 and promocion_estatus_id = 1 order by promocion_id desc) where rownum <= 3";
			break;
			case "multiple-Unidad":
				//query = "select * from (select promocion_id from xxpro_promocion where tipo_mecanica_id = 2 and promocion_estatus_id = 1 order by promocion_id desc) where rownum <= 3";
				query = "select * from (select xxpro_promocion_mecanica.promocion_id from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1003 and xxpro_promocion.promocion_estatus_id = 1 and xxpro_promocion_mecanica.promocion_id between 170396 and 358625 order by promocion_id desc) where rownum <= 3";
			break;
			case "multiple-Precio":
				query = "select * from (select xxpro_promocion_mecanica.promocion_id from xxpro_promocion_mecanica join xxpro_promocion on xxpro_promocion_mecanica.promocion_id = xxpro_promocion.promocion_id and xxpro_promocion_mecanica.metodo_id = 1004 and xxpro_promocion.promocion_estatus_id = 1 order by promocion_id desc) where rownum <= 3";
			break;
			case "promopack - Suma art = precio":
				query = "select * from (select promocion_id from xxpro_promocion where tipo_mecanica_id = 3 and promocion_estatus_id = 1 and promocion_id between 168927 and 359245 order by promocion_id desc) where rownum <= 3";
			break;
			case "comunicación e imagen - Sin Precio":
				query = "select * from (select promocion_id from xxpro_promocion where tipo_mecanica_id = 4 and promocion_estatus_id = 1 and promocion_id between 167627 and 359243 order by promocion_id desc) where rownum <= 3";
			break;			
			case "promopack servicios - Servicio mas precio":
				query = "select * from (select promocion_id from xxpro_promocion where tipo_mecanica_id = 5 and promocion_estatus_id = 1 order by promocion_id desc) where rownum <= 3";
			break;
		}
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			folios = ExecuteQuery.getAllData();
			
			for (String[] strings : folios) {
			    System.out.println(Arrays.toString(strings));
			    if(count != 3) {
			    	folio += Arrays.toString(strings)+",";
			    }else {
			    	folio += Arrays.toString(strings);
			    }
			    count++;
			}
			db.close();
		}else {
			System.out.println("No se encontro");
			db.close();
		}
		
		return folio;
	}
	
	/**
	 * @author José Zárate
	 * Verifica la exitencia de un motivo de oferta especificado por su descripción
	 * @param bExecution   --> Clase heredada para utilizar los metodos del framework
	 * @param nombreMotivo --> Descripcion del motivo de oferta a buscar en la base de datos
	 * @return --> True si se encomtro el motivo de oferta buscado, False en caso contrario
	 * @throws SQLException
	 */
	public Boolean existNombreMotivo(BaseExecution bExecution, String nombreMotivo) throws SQLException {
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "select motivo_oferta_dsc from xxpro_motivo_oferta Where motivo_oferta_dsc = '"+nombreMotivo+"'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			db.close();
			return true;
		}else {
			db.close();
			System.out.println("No se encontro el motivo de oferta");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Elimina un motivo de oferta especificado por su descripción 
	 * @param bExecution   --> Clase heredada para la ejecucion del metodos del framework  
	 * @param nombreMotivo --> Nombre del motivo de oferta para su búsqueda 
	 * @throws SQLException	
	 */
	public void deleteNombreMotivo(BaseExecution bExecution, String nombreMotivo) {
		try {
			SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
			
			query = "delete from xxpro_motivo_oferta Where motivo_oferta_dsc = '"+nombreMotivo+"'";
			SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
			ExecuteQuery.notify();
			System.out.println("Se borró el motivo de oferta");
			db.close();
		}catch(SQLException ex) {
			System.out.print("Error en el delete del motivo de oferta: "+ex);
		}
	}
	
	/**
	 * @author José Zárate
	 * Obtiene la descripción de un motivo de oferta existente
	 * @param bExecution --> Clase heredada para la ejecucion del metodos del framework  
	 * @return --> Descripción del tipo de motivo a capturar
	 * @throws SQLException
	 */
	public String getNombreMotivo(BaseExecution bExecution) throws SQLException {
		String nombre = null;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "select motivo_oferta_dsc from xxpro_motivo_oferta where motivo_oferta_id = (select max(motivo_oferta_id) from xxpro_motivo_oferta)";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			nombre = ExecuteQuery.getFirstData();
		}else {
			System.out.println("No se encontro motivo de oferta");
		}
		
		db.close();
		return nombre;
	}
	
	/**
	 * @author José Zárate
	 * Obtiene el id del ultimo componente registrado en la base de datos
	 * @param bExecution --> Clase heredada para la ejecucion del metodos del framework 
	 * @return --> Id del componente a eliminar
	 * @throws SQLException
	 */
	public String getIdComponente(BaseExecution bExecution) throws SQLException {
		String id = null;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "select max(i_id) from xxpro_componentes_cv";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			id = ExecuteQuery.getFirstData();
		}else {
			System.out.println("No se encontro el componente");
		}
		
		db.close();
		return id;
	}
	
	/**
	 * @author José Zárate
	 * Obtiene la descripcion del id del componenete
	 * @param bExecution --> Clase heredada para la ejecucion del metodos del framework 
	 * @param id		 --> Id del componente
	 * @return --> Descripción del componente
	 * @throws SQLException
	 */
	public String getDscComponente(BaseExecution bExecution, String id) throws SQLException {
		String componente = null;
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "select componente_dsc from xxpro_componentes_cv where i_id = '"+id+"'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			componente = ExecuteQuery.getFirstData();
		}else {
			System.out.println("No se encontro el componente");
		}
		
		db.close();
		return componente;
		
	}
	
	/**
	 * @author José Zárate
	 * Valida que el componente eliminado ya no aparezca en ninguna de las tablas, XXPRO_COMPONENTES_CV, XXPRO_PAQUETES_CV y XXPRO_PAQUETE_COMPONENTES_CV
	 * @param bExecution --> Clase heredada para la ejecucion del metodos del framework 
	 * @param id		 --> ID del componente a buscar
	 * @return --> True en caso de que se haya borrado de forma correcta, False en caso contrario
	 * @throws SQLException
	 */
	public Boolean buscarComponenteEliminado(BaseExecution bExecution, String id) throws SQLException {
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "SELECT xxpro_componentes_cv.i_id\r\n"
				+ "FROM xxpro_componentes_cv\r\n"
				+ "JOIN xxpro_paquetes_cv ON xxpro_paquetes_cv.i_id = xxpro_componentes_cv.i_id \r\n"
				+ "JOIN xxpro_paquete_componentes_cv ON xxpro_componentes_cv.i_id = xxpro_paquete_componentes_cv.i_id\r\n"
				+ "WHERE xxpro_componentes_cv.i_id = '"+id+"'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			db.close();
			return false;
			
		}else {
			System.out.println("No se encontro motivo de oferta");
			db.close();
			return true;
		}
		
	}
	

	/**
	 * @author José Zárate
	 * Genera un número aleatorio para la seleccion de valores los dropdown
	 * @param num  --> valor máximo que puede tener el numero random 
	 * @return int --> número random generado
	 */
	public int randomIndex(int num) {
		return (int)(Math.random()*num+1);
	}
}
