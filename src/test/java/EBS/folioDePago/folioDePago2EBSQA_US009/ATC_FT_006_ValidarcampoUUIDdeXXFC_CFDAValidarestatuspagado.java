package EBS.folioDePago.folioDePago2EBSQA_US009;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* PRERREQUISITOS: El Campo UUID RELACIONADO debe estar creada en la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA
*
* DESCRIPCION: Se validara que el UUID su estatus no esté como pagado, en caso de que esté como pagado, entonces se deberá marcar como error.
* @author irvin.martinez@grupotimex.onmicrosoft.com 
*/

public class ATC_FT_006_ValidarcampoUUIDdeXXFC_CFDAValidarestatuspagado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_ValidarcampoUUIDdeXXFC_CFDAValidarestatuspagado_test(HashMap<String, String> data) throws Exception {
	
		  
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
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar la existencia de la tabla XXFC.XXFC_CFDAVALIDAR");
					String consulta_AVEBQA = "Select VALN_FOLIO,VALD_FECHARECEPCION,VALC_UUID,VALC_RFC,VALC_SERIE,VALN_PRECIONETO from XXFC.XXFC_CFDAVALIDAR WHERE VALN_FOLIO = ("+data.get("FOLIO")+") AND VALC_RFC = ("+data.get("RFC")+")";
					System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa  = ExecuteQuery.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
					
					
					/******************************PASO 3 Y 4************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta para validar la existencia de la columna UUID RRELACIONADO y sus propiedades de tipo y longitud en la tabla XXFC_CFDAVALIDAR");
					String consulta_Propiedades = "select invoice_id, invoice_num, vendor_id, invoice_date, payment_status_flag, amount_paid from apps.ap_invoices_all where invoice_num= ("+data.get("invoicenum")+")";
					System.out.println("Tu consulta es: " + consulta_Propiedades); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp1 = executeQuery(dbAvebqa, consulta_Propiedades); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa2  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa2  = ExecuteQueryp1.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp1);

					assertFalse(conexiondbAvebqa2, "La conexion no fue exitosa");
					
/******************************PASO 5 Y 6************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta para validar la existencia de la columna UUID RRELACIONADO y sus propiedades de tipo y longitud en la tabla XXFC_CFDAVALIDAR");
					String consulta_Propiedades1 = "select RFC,FOLIO,FOLIO_PAGO,IMPORTE,IMPORTE_OXXO,UUID,MSG_ERROR,ID_ARCHIVO from XXFC.XXFC_AP_CARGACSV_ALL WHERE FOLIO_PAGO = ("+data.get("invoice_num")+")";
					System.out.println("Tu consulta es: " + consulta_Propiedades1); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp2 = executeQuery(dbAvebqa, consulta_Propiedades1); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa3  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa3  = ExecuteQueryp2.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp2);

					assertFalse(conexiondbAvebqa3, "La conexion no fue exitosa");
					
/******************************PASO 7 ************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta para validar la existencia de la columna UUID RRELACIONADO y sus propiedades de tipo y longitud en la tabla XXFC_CFDAVALIDAR");
					String consulta_Propiedades2 = "select ORA_ERR_TAG$,RFC,FOLIO_PAGO,IMPORTE,IMPORTE_OXXO,MSG_ERROR,ID_ARCHIVO from apps.XXFC_AP_CARGACSV_ERR WHERE ORA_ERR_TAG$ = 'PTZ-05' AND FOLIO_PAGO = ("+data.get("invoicenum")+")";
					System.out.println("Tu consulta es: " + consulta_Propiedades2); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp3 = executeQuery(dbAvebqa, consulta_Propiedades2); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa4  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa4  = ExecuteQueryp3.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp3);

					assertFalse(conexiondbAvebqa4, "La conexion no fue exitosa");
					
				}
			  

@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "El Campo UUID RELACIONADO debe estar creada en la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se validara que el UUID su estatus no esté como pagado, en caso de que esté como pagado, entonces se deberá marcar como error";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Irvin Martinez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_006_ValidarcampoUUIDdeXXFC_CFDAValidarestatuspagado_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}
}
