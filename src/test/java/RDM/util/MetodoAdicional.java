package RDM.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.ibm.icu.util.Calendar;

import utils.sql.SQLResult;

public class MetodoAdicional {
	
	/**
	 * Metodo para validar los campos: ESTATUS y CONFIRM_DATE_TS de la tabla: FEM_PICK_POR_CONFIRM
	 * antes de la ejecución del JOB
	
	 * @param query---------------- Resultado de la ejecución de la consulta a la Base de Datos
	 * 
	 * @return regresa true si los campos ESTATUS y CONFIRM_DATE_TS son nulos
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean validarEstatus_Date(SQLResult query) {
		if(query.getData(0, "ESTATUS")==null && query.getData(0, "CONFIRM_DATE_TS")==null) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Metodo para validar el campo: ESTATUS de la tabla: FEM_PICK_POR_CONFIRM
	
	 * @param query---------------- Resultado de la ejecución de la consulta a la Base de Datos
	 * 
	 * @return regresa true si el valor del campo ESTATUS es SUCCESS
	 * 
	 * @author nellyoliviab@hexaware.com
	 */	
	public boolean validarEstatus_DespuesDelJOB(SQLResult query) throws ParseException {
		if(query.getData(0, "ESTATUS")!=null) {
			if(query.getData(0, "ESTATUS").compareToIgnoreCase("SUCCESS")==0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo para validar el campo CONFIRM_DATE_TS de la tabla: FEM_PICK_POR_CONFIRM
	
	 * @param query---------------- Resultado de la ejecución de la consulta a la Base de Datos
	 * 
	 * @return regresa true si se encuentra valor
	 * 
	 * @author nellyoliviab@hexaware.com
	 */	
	public boolean validarFecha_DespuesDelJOB(SQLResult query) throws ParseException {
		if(query.getData(0, "CONFIRM_DATE_TS")!=null) {
			
			return true;
		}
		return false;
	}
	/**
	 * Metodo para validar el campo CONTAINER_STATUS 
	
	 * @param query---------------- Resultado de la ejecución de la consulta a la Base de Datos
	 * 
	 * @return regresa true si el valor del estatus es P
	 * 
	 * @author nellyoliviab@hexaware.com
	 */	
	
	public boolean validarEstatusContainer(SQLResult query) {
		if(query.getData(0, "CONTAINER_STATUS")!=null) {
			if(query.getData(0, "CONTAINER_STATUS").compareTo("P")==0) {
				return true;
			}
		}
		return false;
	}
}
