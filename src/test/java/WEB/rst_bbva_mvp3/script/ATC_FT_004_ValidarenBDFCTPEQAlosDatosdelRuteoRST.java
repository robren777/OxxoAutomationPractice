package WEB.rst_bbva_mvp3.script;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCIÓN: Validar que se tengan las entidades correctas del ruteo esto para verificar la configuracion de las tiendas y su entidad 
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-004-Validar en BD  FCTPEQA.FEMCOM.NET los datos del ruteo RST
 * 
 * PREREQUISITOS: Contar con el acceso y las credenciales de la BD
 * 
 * INSUMOS: BD FCTPEQA y sus credenciales
 * @author Melba Solis
 * @date 03/04/2025
 */

public class ATC_FT_004_ValidarenBDFCTPEQAlosDatosdelRuteoRST extends BaseExecution {

//@AzureTestInfo(
//			areaPath = "Test-POCFrameworkOxxo\\test1",
//			iterationPath = "Test-POCFrameworkOxxo\\Iteration 1",
//			tags = "TestTag1;TestTag2",
//			userStoryId = 17086   //)

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarenBDFCTPEQAlosDatosdelRuteoRST_test(HashMap<String, String> data) throws Exception {

		 /** Llamar a la Base de Datos
		 * @param SQLUtil
		 * @author Melba Solís 
		 */
		
		//Utileria// Se considera una utileria la BD //
		SQLUtil dbFctpeqa = new SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW, GlobalVariables.DB_USER_FCTPEQA_QAVIEW, GlobalVariables.DB_PASSWORD_FCTPEQA_QAVIEW);
		  
		/** Comienza el script *****/
		System.out.println("*************** PASOS 1 y 2 **************************************");
			
		addStep("Ingresar a la BD FCTPEQA y realizar la siguiente consulta para validar que se tengan las entidades correctas del ruteo esto para verificar la configuracion de las tiendas y su entidad");
		String consulta_FCTPEQA = data.get("query"); 
		System.out.println("Tu consulta es: " + consulta_FCTPEQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO

		SQLResult ExecuteQuery = executeQuery(dbFctpeqa, consulta_FCTPEQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)

		boolean conexiondbFctpeqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
		conexiondbFctpeqa  = ExecuteQuery.isEmpty();

		testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

		assertFalse(conexiondbFctpeqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
		
		}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con acceso a la BD FCTPEQA \r\n" 
				+ "2. Contar con las credenciales de la BD FCTPEQA \r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarenBDFCTPEQAlosDatosdelRuteoRST_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se tengan las entidades correctas del ruteo esto para verificar la configuracion de las tiendas y su entidad ";
	}
}
