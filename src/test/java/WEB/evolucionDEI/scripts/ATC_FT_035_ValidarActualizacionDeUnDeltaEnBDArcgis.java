package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertFalse;
import java.util.HashMap;
import org.testng.annotations.Test;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Validar que al realizar una petición POST actualizando un delta
 * en el campos ESTATUS_GRAL se refleje la actualización en la BD Arcgis
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-035 Validar
 * actualización de un Delta en BD Arcgis
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 07/10/2025
 */

public class ATC_FT_035_ValidarActualizacionDeUnDeltaEnBDArcgis extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "ATririga hacia ArcGis",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_035_ValidarActualizacionDeUnDeltaEnBDArcgis_test(HashMap<String, String> data) throws Exception {

		/**
		 * Llamar a la Base de Datos
		 * @param SQLUtil
		 * @author Melba Solís
		 */

		addStep("Se ingresa a la BD de ARCGIS y se realizar la siguiente consulta para validar la existencia de la tabla XXGISOWNER.EXP_PUNTOS_POTENCIALES_V2 y la actualización del Estatus General");

		//Utileria// Se considera una utileria la BD //
		SQLUtil dbFcgisqn = new SQLUtil(GlobalVariables.DB_HOST_FCGISQN, GlobalVariables.DB_USER_FCGISQN, GlobalVariables.DB_PASSWORD_FCGISQN);
		
		/****************** Llamar a la Base de Datos **********************/
		System.out.println("*************** PASOS 1 y 2 *****************");
				
		String consulta_FCGISQN = data.get("ConsultaBDArcgis");
		System.out.println("Tu consulta es: " + consulta_FCGISQN); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO

		SQLResult ExecuteQuery = executeQuery(dbFcgisqn, consulta_FCGISQN); // execute = ejecutar Query=consulta /ejecutar consulta de la BD = al escript validar tabla (va a buscar la tabla)

		boolean conexiondbFcgisqn  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
		conexiondbFcgisqn  = ExecuteQuery.isEmpty();

		testCase.addTextEvidenceCurrentStep("Conexion: ARCGIS ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCGISQN);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

		assertFalse(conexiondbFcgisqn, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con la herramienta postman \r\n"
				+ "2. Contar con las credenciales correspondientes a la colección \r\n"
				+ "3. Que se haya actualizado el ESTATUS_GRAL de un registro \r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_035_ValidarActualizacionDeUnDeltaEnBDArcgis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Validar que al realizar una petición POST actualizando un delta en el campos ESTATUS_GRAL se refleje la actualización en la BD Arcgis";
	}
}
