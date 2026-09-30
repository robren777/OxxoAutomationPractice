package EBS.folioDePago.folioDePago2EBSQA_US007;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import org.testng.annotations.Test;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* PRERREQUISITOS: El Campo UUID RELACIONADO debe estar creado en la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA
*
* DESCRIPCION: Se validara que el campo VALC_UUIDRELACIONADO cumpla con los valores en la tabla de EBS (XXFC_CFDAValidar)
* @author irvin.martinez@grupotimex.onmicrosoft.com 
*/

public class ATC_FT_002_ValidarcampoVALC_UUIDRELACIONADOsecreocorrectotablaEBSXXFC_CFDAValidar extends BaseExecution {
	
	  @Test(dataProvider = "data-provider")
	  public void ATC_FT_002_ValidarcampoVALC_UUIDRELACIONADOsecreocorrectotablaEBSXXFC_CFDAValidar_test(HashMap<String, String> data) 
		  throws Exception {
		  
		  
			//Utileria// Se considera una utileria la BD //
			//****************** Llamar a la Base de Datos **********************/
				  
				 SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
				  
				 // Variables
			//********************************************************************/
			//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
					testCase.setTest_Description(data.get("id") + data.get("description"));

					//testcase va a mandar los prerequisitos 
					testCase.setPrerequisites("El Campo UUID RELACIONADO debe estar creado en la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA");
					//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

					System.out.println(
							
			"*************** PASOS 1 y 2 ******************************************");
					
					//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
					//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar la existencia de la tabla XXFC.XXFC_CFDAVALIDAR");
					String consulta_DISTINCT = "Select distinct VALC_UUIDRELACIONADO from XXFC.XXFC_CFDAValidar WHERE VALN_FOLIO = ("+data.get("FOLIO")+") and valc_rfc = ("+data.get("RFC")+")";
					System.out.println("Tu consulta es: " + consulta_DISTINCT); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_DISTINCT); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa  = ExecuteQuery.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
					
					
					/******************************PASO 3 Y 4************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta para validar la existencia de la columna VALC_UUIDRELACIONADO en la tabla XXFC_CFDAVALIDAR");
					String consulta_1 = "select VALC_UUIDRELACIONADO from XXFC.XXFC_CFDAValidar WHERE VALC_UUIDRELACIONADO IS NULL AND VALN_FOLIO = ("+data.get("FOLIO")+")";
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
	return "El campo VALC_UUIDRELACIONADO se creo correctamente";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se validara que el campo VALC_UUIDRELACIONADO cumpla con los valores en la tabla de EBS (XXFC_CFDAValidar)";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Irvin Martinez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_002_ValidarcampoVALC_UUIDRELACIONADOsecreocorrectotablaEBSXXFC_CFDAValidar_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}
}

