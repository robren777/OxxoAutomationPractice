package Mobile.Autoinventario;

import java.util.HashMap;

import org.testng.annotations.Test;

import Mobile.Autoinventario.om.comando;
import Mobile.Autoinventario.om.conexion;
import modelo.BaseExecution;

/**
 * MTC-FT-096-Validar informacion BD con nube 
 * @author Joel Rodriguez
 * Descripcion: Ingresar a BD del equipo y validar se  muestre informacion y coincida con la informacion de nube
 * 
 * Contar con DB pft satelites
 * Contar con preinvenatrios Inteligentes
 * Acceso a BD local
 * Credenciales de BD local
 * Acceso a BD nube
 * Credenciales de BD nube
 * Query Consulta de Semanas Preinventario
 * Excel de Contratos Preinventario Inteligente
 * Haber realizado el CP MTC-FT-042
 * 
 * Recomendaciones / Comentarios
 * Ejemplo:
 * 1.- Configurar Ip, Usuario y contraseña de equipo remoto PDV
 * 2.- Cambiar hora y fecha correspondiente a ejecucion de Cronjob para su informacion requerida
 */

public class ATC_FT_096_ValidarInformacionBDConNube  extends BaseExecution {
	@Test (dataProvider = "data-provider", enabled=true)
	public void ATC_FT_096_ValidarInformacionBDConNube_test(HashMap<String, String> data) throws Exception {
		//Variables de equipo remoto
		comando.ip = "10.200.10.111"; //IP equipo remoto 
		comando.user = "QA"; //User equipo remoto
		comando.password = "Comercio03"; //Pass equipo remoto
		comando.diaAcambiar= 1;  //Seleccionar 1 para fecha Lunes, 2 para Martes, 3 para Miercoles
		comando.hora =00; //Hora cambiar a equipo remoto, formato 24hrs
		comando.mins =59; //Mins cambiar a equipo remoto, recomendado 1 minuto antes de la hora en especifico para la ejecucion del cronjob
		conexion con = new conexion (testCase,this);
		
		/************************ Paso 1 - Conexion SQL ************************/
		try {	
			// Llamado al POM de conexion de SQL
			con.conn_XposStore();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.obtenerCr();
		con.Close();
		
		/************************ Paso 2 - Conexion SQL ************************/
		try {
			addStep("Ingresar a base de datos");	
			// Llamado al POM de conexion de SQL
			con.conn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/************************ Paso 3 ************************/
		// Realizar Consulta de preinventarios realizados
		addStep("Ejecutar query de semana");
		con.query_Semana();

		/************************ Paso 4 ************************/
		//Detener servicio
		comando cmd = new comando(comando.ip, comando.user, comando.password, testCase);
		addStep("Detener Servicio OxxoServiceApi");
		cmd.detenerServicio();

		/************************ Paso 5 ************************/
		//Cambiar fecha y hora
		addStep("Cambiar fecha y hora");
		cmd.cambiarDiaHora(comando.nuevaFechaHora, comando.diaAcambiar);

		/************************ Paso 6 ************************/
		//Limpiar informacion de tablas
		addStep("Limpiar informacion de tablas de preinventario");
		con.limpiarInfo();

		/************************ Paso 7 ************************/
		//Iniciar servicio OxxoServiceApi
		addStep("Se inicia servicio OxxoServiceApi");
		cmd.ejecutarServicio();

		/************************ Paso 7 ************************/
		//Validar informacion completada
		addStep("Se valida informacion de encabezado");
		con.tablaEncabezado();
		
		/************************ Paso 8 ************************/
		//Validar informacion completada
		addStep("Se valida informacion de detalle");
		con.tablaDetalle();
		
		/************************ Paso 9 ************************/
		//Validar informacion completada
		addStep("Se valida informacion de CronJob");
		con.tablaCronjob();

		/************************ Paso 9 ************************/
		addStep("Cerrar conexion");
		cmd.resetHoraFecha(comando.reseteaFyH);
		con.Close();
	
		/************************ Paso 10 ************************/
		try {
			addStep("Ingresar a base de datos power bi");	
			// Conexion PBI
			con.connPowerBi();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.query_PowerBi();
		con.valida_itemsSent();
		testCase.addTextEvidenceCurrentStep("Se valida informacion coincide entre base de datos local y base de datos en la nube");
		con.Close();
	}



	@Override
	public String setTestFullName() {
		return "MTC-FT-096-Validar informacion BD con nube";
	}

	@Override
	public String setTestDescription() {
		return "Ingresar a BD del equipo y validar se  muestre informacion y coincida con la informacion de nube";
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
		return "*Contar con DB pft satelites\r\n"
				+ "*Contar con preinvenatrios Inteligentes\r\n"
				+ "*Acceso a BD local\r\n"
				+ "*Credenciales de BD local\r\n"
				+ "*Acceso a BD nube\r\n"
				+ "*Credenciales de BD nube\r\n"
				+ "*Query Consulta de Semanas Preinventario\r\n"
				+ "*Excel de Contratos Preinventario Inteligente\r\n"
				+ "*Haber realizado el CP MTC-FT-042";
	}

	@Override
	public void beforeTest() {
	}
	

}