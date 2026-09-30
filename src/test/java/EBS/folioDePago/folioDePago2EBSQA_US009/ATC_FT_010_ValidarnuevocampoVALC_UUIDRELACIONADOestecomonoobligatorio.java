package EBS.folioDePago.folioDePago2EBSQA_US009;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* PRERREQUISITOS: La interfaz FEMSAWS proceso correctamente la factura cargada por diverza XML y envió campos del XML cfdi recortado a la tabla XXFC_CFDAVALIDAR
*
* DESCRIPCION: Se validara que el nuevo campo VALC_UUIDRELACIONADO este marcado como no obligatorio y contenga la información correspondiente de las facturas cargadas
* @author irvin.martinez@grupotimex.onmicrosoft.com 
*/

public class ATC_FT_010_ValidarnuevocampoVALC_UUIDRELACIONADOestecomonoobligatorio extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_010_ValidarnuevocampoVALC_UUIDRELACIONADOestecomonoobligatorio_test(HashMap<String, String> data) throws Exception {
	
		  
			//Utileria// Se considera una utileria la BD //
			//****************** Llamar a la Base de Datos **********************/
				  
				 SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
				  
				 // Variables
			//********************************************************************/
			//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
					testCase.setTest_Description(data.get("id") + data.get("description"));

					//testcase va a mandar los prerequisitos 
					testCase.setPrerequisites("El Campo UUID RELACIONADO debe estar creada en la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA");
					//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

					System.out.println(
							
			"*************** PASOS 1 y 2 ******************************************");
					
					//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
					//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar el campo VALC_UUIDRELACIONADO de la tabla XXFC.XXFC_CFDAVALIDAR");
					String consulta_AVEBQA = "Select VALC_UUIDRELACIONADO,VALN_FOLIO,VALC_RFC,VALD_FECHARECEPCION,VALC_FOLIOPAGO,VALC_UUID from XXFC.XXFC_CFDAVALIDAR WHERE VALC_UUIDRELACIONADO IS NOT NULL AND VALC_FOLIOPAGO = ("+data.get("FOLIOPAGO")+")";
					System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa  = ExecuteQuery.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
				
					
				}
			  


@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "La interfaz FEMSAWS proceso correctamente la factura cargada por diverza XML y envió campos del XML cfdi recortado a la tabla XXFC_CFDAVALIDAR";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se validara que el nuevo campo VALC_UUIDRELACIONADO este marcado como no obligatorio y contenga la información correspondiente de las facturas cargadas	No se validará para el proceso el UUID Relacionado";

}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Irvin Martinez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_010_ValidarnuevocampoVALC_UUIDRELACIONADOestecomonoobligatorio_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}
}
