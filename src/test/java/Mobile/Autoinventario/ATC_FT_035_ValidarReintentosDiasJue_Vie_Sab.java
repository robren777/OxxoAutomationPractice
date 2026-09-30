package Mobile.Autoinventario;

import java.util.HashMap;

import org.testng.annotations.Test;

import Mobile.Autoinventario.om.conexion;
import modelo.BaseExecution;


public class ATC_FT_035_ValidarReintentosDiasJue_Vie_Sab extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)

	public void ATC_FT_035_ValidarReintentosDiasJue_Vie_Sab_test(HashMap<String, String> data) throws Exception {
		/************************ Paso 1 - Conexion SQL ************************/
		addStep("Ingresar a base de datos");	
		// Llamado al POM de conexion de SQL
		conexion con = new conexion (testCase,this);
		con.conn();

		/************************ Paso 2 - Revision de informacion de semanas ************************/
		// Realizar Consulta de preinventarios realizados
		con.query_Semana();

		/************************ Paso 3 - Validar informacion no existente ************************/
		// Realizar Consulta para validar no existan resultados en dias mencionados
		con.query_infoSemana();

		//Cerrar conexion
		con.Close();
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-035-Validar reintentos los dias jueves viernes sabado";
	}

	@Override
	public String setTestDescription() {
		return "Ingresar a BD del equipo y validar no se muestre informacion de reintentos los dias jueves, viernes, sabado";
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
		return "1.- Contar con DB pft satelites de preinventarios Inteligentes\r\n"
				+ "2.- Tener acceso a Sql Server\r\n"
				+ "3.- Script Consulta Informacion\r\n"
				+ "4.-  Consulta para revisar las semanas de preinventarios";
	}

	@Override
	public void beforeTest() {
	}

}