package Mobile.Autoinventario;

import java.util.HashMap;

import org.testng.annotations.Test;

import Mobile.Autoinventario.om.comando;
import Mobile.Autoinventario.om.conexion;
import modelo.BaseExecution;

/**
 * MTC-FT-093-Validar se muestre informacion en el horario 4pm del Miercoles
 * @author Joel Rodriguez
 * Descripcion: Ingresar a BD del equipo y validar se  muestre informacion en el horario  4 pm.
 * 
 * Pre condiciones para ejecutar Script automatizado:
 * 1.- Contar con DB pft satelites de preinventarios Inteligentes
 * 2.- Tener acceso a Sql Server
 * 3.- Script Consulta Informacion
 * 4.- Script ejecutable para validar semanas de los preinventarios (Fecha por semana/año)
 * 
 * Recomendaciones / Comentarios
 * Ejemplo:
 * 1.- Configurar Ip, Usuario y contraseña de equipo remoto PDV
 * 2.- Cambiar hora y fecha correspondiente a ejecucion de Cronjob para su informacion requerida
 */

public class ATC_FT_093_ValidarSeMuestreInformacionEnElHorario4pmDelMiercoles extends BaseExecution {
	@Test (dataProvider = "data-provider", enabled=true)
	public void ATC_FT_093_ValidarSeMuestreInformacionEnElHorario4pmDelMiercoles_test(HashMap<String, String> data) throws Exception {
		//Variables de equipo remoto
		comando.ip = "10.200.10.111"; //IP equipo remoto 
		comando.user = "QA"; //User equipo remoto
		comando.password = "Comercio03"; //Pass equipo remoto
		comando.diaAcambiar= 3;  //Seleccionar 1 para fecha Lunes, 2 para Martes, 3 para Miercoles
		comando.hora =15; //Hora cambiar a equipo remoto, formato 24hrs
		comando.mins =59; //Mins cambiar a equipo remoto, recomendado 1 minuto antes de la hora en especifico para la ejecucion del cronjob
		conexion con = new conexion (testCase,this);

		/************************ Paso 1 - Conexion SQL ************************/
		try {
			addStep("Ingresar a base de datos");	
			// Llamado al POM de conexion de SQL
			con.conn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/************************ Paso 2 ************************/
		// Realizar Consulta de preinventarios realizados
		addStep("Ejecutar query de semana");
		con.query_Semana();

		/************************ Paso 3 ************************/
		//Detener servicio
		comando cmd = new comando(comando.ip, comando.user, comando.password, testCase);
		addStep("Detener Servicio OxxoServiceApi");
		cmd.detenerServicio();

		/************************ Paso 4 ************************/
		//Cambiar fecha y hora
		addStep("Cambiar fecha y hora");
		cmd.cambiarDiaHora(comando.nuevaFechaHora, comando.diaAcambiar);

		/************************ Paso 5 ************************/
		//Limpiar informacion de tablas
		addStep("Limpiar informacion de tablas de preinventario");
		con.limpiarInfo();

		/************************ Paso 6 ************************/
		//Iniciar servicio OxxoServiceApi
		addStep("Se inicia servicio OxxoServiceApi");
		cmd.ejecutarServicio();

		/************************ Paso 7 ************************/
		//Validar informacion completada
		addStep("Se valida informacion de encabezado");
		//conexion.tipo=1;
		con.tablaEncabezado();
		
		/************************ Paso 8 ************************/
		//Validar informacion completada
		addStep("Se valida informacion de detalle");
		con.tablaDetalle();
		
		/************************ Paso 9 ************************/
		//Validar informacion completada
		addStep("Se valida informacion de CronJob");
		con.tablaCronjob();

		/************************ Paso 10 ************************/
		addStep("Cerrar conexion");
		cmd.resetHoraFecha(comando.reseteaFyH);
		con.Close();;

	}



	@Override
	public String setTestFullName() {
		return "MTC-FT-093-Validar se muestre informacion en el horario 4pm del Miercoles";
	}

	@Override
	public String setTestDescription() {
		return "Ingresar a BD del equipo y validar se  muestre informacion en el horario  4 pm.";
	}

	@Override
	public String setTestDesigner() {
		return "Joel Rodriguez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "* 1.- Contar con DB pft satelites de preinventarios Inteligentes\r\n"
				+ " * 2.- Tener acceso a Sql Server\r\n"
				+ " * 3.- Script Consulta Informacion\r\n"
				+ " * 4.- Script ejecutable para validar semanas de los preinventarios (Fecha por semana/año)";
	}

	@Override
	public void beforeTest() {
	}
	

}