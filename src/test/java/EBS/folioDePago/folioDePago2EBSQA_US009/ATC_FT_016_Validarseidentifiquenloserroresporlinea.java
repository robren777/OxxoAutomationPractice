package EBS.folioDePago.folioDePago2EBSQA_US009;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* PRERREQUISITOS: Archivo de carga relacional procesado por la interfaz nueva con errores y mensaje de error en JSON
*
* DESCRIPCION: Se validara que se identifiquen los errores por línea de tal modo que se regrese un archivo de salida para que puedan volver a cargar la información (Nota: se rechaza todo el archivo aunque solo sea una línea la incorrecta)
* @author irvin.martinez@grupotimex.onmicrosoft.com 
*/

public class ATC_FT_016_Validarseidentifiquenloserroresporlinea extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_016_Validarseidentifiquenloserroresporlinea_test(HashMap<String, String> data) throws Exception {
	
		  
			//Utileria// Se considera una utileria la BD //
			//****************** Llamar a la Base de Datos **********************/
				  
				 SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA); 
				  
				 // Variables
			//********************************************************************/
			//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
					testCase.setTest_Description(data.get("id") + data.get("description"));

					//testcase va a mandar los prerequisitos 
					testCase.setPrerequisites("Archivo de carga relacional procesado por la interfaz nueva con errores y mensaje de error en JSON");
					//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

					System.out.println(
							
"*************** PASOS 1 y 2 ******************************************");
					
					//add = añadir // step = paso // STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
					//añadir un paso // El paso consiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
					addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta en la tabla de error");
					String consulta_AVEBQA = "SELECT ORA_ERR_TAG$,RFC,FOLIO_PAGO,IMPORTE,IMPORTE_OXXO,MSG_ERROR,ID_ARCHIVO, ESTATUS FROM apps.XXFC_AP_CARGACSV_ERR WHERE ID_ARCHIVO = ("+data.get("IDARCHIVO")+") AND ROWNUM <6";
					System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
					
					boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa  = ExecuteQuery.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

					assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
					
					/******************************PASO 3 Y 4************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta");
					String consulta_1 = "SELECT ORA_ERR_TAG$,RFC,FOLIO_PAGO,IMPORTE,IMPORTE_OXXO,MSG_ERROR,ID_ARCHIVO, ESTATUS FROM apps.XXFC_AP_CARGACSV_ERR WHERE ID_ARCHIVO = ("+data.get("IDARCHIVO1")+") AND ROWNUM <6";
					System.out.println("Tu consulta es: " + consulta_1); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp = executeQuery(dbAvebqa, consulta_1); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa2  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa2  = ExecuteQueryp.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp);

					assertFalse(conexiondbAvebqa2, "La conexion no fue exitosa");
					
					
/******************************PASO 5 Y 6************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta");
					String consulta_2 = "SELECT ORA_ERR_TAG$,RFC,FOLIO_PAGO,IMPORTE,IMPORTE_OXXO,MSG_ERROR,ID_ARCHIVO, ESTATUS FROM apps.XXFC_AP_CARGACSV_ERR WHERE ID_ARCHIVO = ("+data.get("IDARCHIVO2")+") AND ROWNUM <6";
					System.out.println("Tu consulta es: " + consulta_2); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp1 = executeQuery(dbAvebqa, consulta_2); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa3  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa3  = ExecuteQueryp1.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp1);

					assertFalse(conexiondbAvebqa3, "La conexion no fue exitosa");
		
					
/******************************PASO 7 Y 8************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta");
					String consulta_3 = "SELECT ORA_ERR_TAG$,RFC,FOLIO_PAGO,IMPORTE,IMPORTE_OXXO,MSG_ERROR,ID_ARCHIVO, ESTATUS FROM apps.XXFC_AP_CARGACSV_ERR WHERE ID_ARCHIVO = ("+data.get("IDARCHIVO3")+") AND ROWNUM <6";
					System.out.println("Tu consulta es: " + consulta_3); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp2 = executeQuery(dbAvebqa, consulta_3); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa4 = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa4  = ExecuteQueryp2.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp2);

					assertFalse(conexiondbAvebqa4, "La conexion no fue exitosa");
		
/******************************PASO 9************************************/
					
					addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta");
					String consulta_4 = "select * from apps.XXFC_AP_CARGACSV_ERR WHERE ID_ARCHIVO = ("+data.get("IDARCHIVO4")+") AND ORA_ERR_TAG$ = 'PTZ-17'";
					System.out.println("Tu consulta es: " + consulta_4); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
					
					SQLResult ExecuteQueryp3 = executeQuery(dbAvebqa, consulta_4); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
									
					boolean conexiondbAvebqa5 = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
					conexiondbAvebqa5  = ExecuteQueryp3.isEmpty();

					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					testCase.addQueryEvidenceCurrentStep(ExecuteQueryp3);

					assertFalse(conexiondbAvebqa5, "La conexion no fue exitosa");
					

				}
			  


@Override
public void beforeTest() {
	// TODO Auto-generated method stub
	
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return "Archivo de carga relacional procesado por la interfaz nueva con errores y mensaje de error en JSON";
}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Se validara que se identifiquen los errores por línea de tal modo que se regrese un archivo de salida para que puedan volver a cargar la información (Nota: se rechaza todo el archivo aunque solo sea una línea la incorrecta)";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Irvin Martinez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_016_Validarseidentifiquenloserroresporlinea_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}
}
