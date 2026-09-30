package EBS.folioDePago.folioDePago2EBSQA_US009;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* PRERREQUISITOS: Acceso a BD de EBS Acceso a sistema EBS Archivo SFTP cargado en el servidor
*
* DESCRIPCION: Se validara que para la tabla XXFC_CFD_RELACION_FACTURAS los campos que ya existían se mantengan sin afectación y traigan la información correcta de los registros
* @author irvin.martinez@grupotimex.onmicrosoft.com 
*/

public class ATC_FT_013_ValidarcamposexistentesXXFC_CFD_RELACION_FACTURASsemantengansinafectacion extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_013_ValidarcamposexistentesXXFC_CFD_RELACION_FACTURASsemantengansinafectacion_test(HashMap<String, String> data) throws Exception {
	
		  
			//Utileria// Se considera una utileria la BD //
			//****************** Llamar a la Base de Datos **********************/
				  
				 SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
				  
				 // Variables
			//********************************************************************/
			//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
					testCase.setTest_Description(data.get("id") + data.get("description"));

					//testcase va a mandar los prerequisitos 
					testCase.setPrerequisites("Se validara que para la tabla XXFC_CFD_RELACION_FACTURAS los campos que ya existían se mantengan sin afectación y traigan la información correcta de los registros");
					//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

					System.out.println(
							
			"*************** PASOS 1 y 2 ******************************************");
					
					//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
					//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar la tabla XXFC_CFD_RELACION_FACTURAS");
					String consulta_AVEBQA = "SELECT RFC,PROVEEDOR,IMPORTE,IMPORTE_OXXO,FOLIO_PAGO_OXXO,FOLIO FROM XXFC.XXFC_CFD_RELACION_FACTURAS WHERE FOLIO IN ("+data.get("FOLIO")+") AND RFC IN ("+data.get("RFC")+") ";
					System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa  = ExecuteQuery.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
					
					/******************************PASO 3 Y 4************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta para validar la tabla XXFC_CFD_RELACION_FACTURAS");
					String consulta_1 = "SELECT REQUEST_ID,LAST_UPDATE_DATE,LAST_UPDATED_BY,LAST_UPDATE_LOGIN,REQUEST_ID FROM XXFC.XXFC_CFD_RELACION_FACTURAS WHERE FOLIO IN ("+data.get("FOLIO")+") AND RFC IN ("+data.get("RFC")+") ";
					System.out.println("Tu consulta es: " + consulta_1); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp = executeQuery(dbAvebqa, consulta_1); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa2  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa2  = ExecuteQueryp.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp);

					assertFalse(conexiondbAvebqa2, "La conexion no fue exitosa");
					
					
					
				}
			  


@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "Acceso a BD de EBS Acceso a sistema EBS Archivo SFTP cargado en el servidor";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se validara que para la tabla XXFC_CFD_RELACION_FACTURAS los campos que ya existían se mantengan sin afectación y traigan la información correcta de los registros";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Irvin Martinez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_013_ValidarcamposexistentesXXFC_CFD_RELACION_FACTURASsemantengansinafectacion_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}
}
