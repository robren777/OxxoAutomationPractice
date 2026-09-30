package EBS.folioDePago.folioDePago2EBSQA_US009;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* PRERREQUISITOS: Los campos existentes deben mantenerse sin afectacion de la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA
*
* DESCRIPCION: Se validara que para la tabla XXFC_CFDAVALIDAR los campos que ya existían se mantengan sin afectación y se inserte la información exitosamente de la factura cargada
* @author irvin.martinez@grupotimex.onmicrosoft.com 
*/

public class ATC_FT_001_ValidarcamposexistentesXXFC_CFDAVALIDAR extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_ValidarcamposexistentesXXFC_CFDAVALIDAR_test(HashMap<String, String> data) throws Exception {
	
		  
			//Utileria// Se considera una utileria la BD //
			//****************** Llamar a la Base de Datos **********************/
				  
				 SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
				  
				 // Variables
			//********************************************************************/
			//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
					testCase.setTest_Description(data.get("id") + data.get("description"));

					//testcase va a mandar los prerequisitos 
					testCase.setPrerequisites("Los campos existentes deben mantenerse sin afectacion de la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA");
					//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

					System.out.println(
							
			"*************** PASOS 1 y 2 ******************************************");
					
					//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
					//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar los campos existentes de la tabla XXFC.XXFC_CFDAVALIDAR");
					String consulta_AVEBQA = "SELECT VALN_FOLIO,VALC_FOLIOPAGO,VALC_SERIE,VALD_FECHACFD,VALD_FECHARECEPCION,VALC_RFC FROM XXFC.XXFC_CFDAVALIDAR \n"
							+ "WHERE VALC_FOLIOPAGO = ("+data.get("Foliopago")+") and valc_rfc = ("+data.get("RFC")+") ";
					System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa  = ExecuteQuery.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
					
					/******************************PASO 3 Y 4************************************/
					
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar los campos existentes de la tabla XXFC.XXFC_CFDAVALIDAR");
					String consulta_AVEBQA1 = "SELECT VALN_COMPANIA, VALN_USUARIO,VALN_CONSECUTIVO,VALN_IMPORTE,VALN_PRECIONETO FROM XXFC.XXFC_CFDAVALIDAR \n"
							+ "WHERE VALC_FOLIOPAGO = ("+data.get("Foliopago")+") and valc_rfc = ("+data.get("RFC")+") ";
					System.out.println("Tu consulta es: " + consulta_AVEBQA1); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery1 = executeQuery(dbAvebqa, consulta_AVEBQA1); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa1  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa1  = ExecuteQuery1.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa1, "La conexion no fue exitosa"); // si es falso va a arrojar un error 

					
/******************************PASO 5 Y 6************************************/
					
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar los campos existentes de la tabla XXFC.XXFC_CFDAVALIDAR");
					String consulta_AVEBQA2 = "SELECT VALN_ESTATUS,VALN_TIPOREGISTRO,VALN_TIPOVALIDACION, VALN_TIPOLOCALIZACION,VALN_COMPANIA FROM XXFC.XXFC_CFDAVALIDAR \n"
							+ "WHERE VALC_FOLIOPAGO = ("+data.get("Foliopago")+") and valc_rfc = ("+data.get("RFC")+") ";
					System.out.println("Tu consulta es: " + consulta_AVEBQA2); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery2 = executeQuery(dbAvebqa, consulta_AVEBQA2); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa2  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa2  = ExecuteQuery2.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);

					assertFalse(conexiondbAvebqa2, "La conexion no fue exitosa"); // si es falso va a arrojar un error 

					
				}
			  

	@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "Los campos existentes deben mantenerse sin afectacion de la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se validara que para la tabla XXFC_CFDAVALIDAR los campos que ya existían se mantengan sin afectación y se inserte la información exitosamente de la factura cargada";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Irvin Martinez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_001_ValidarcamposexistentesXXFC_CFDAVALIDAR_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}
}
