package WM.interfaces.eo17_mx;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Requisito mandatorios para el Nombre (Java): -No incluir acentos, espacios en
 * blanco ni caracteres especiales en el nombre -La longitud del nombre no debe
 * exceder de 100 caracteres -El nombre del caso de prueba debe ser igual que el
 * que se usa en Test, solo que debe ser precedido por la siglas o nombre del
 * proyecto al que pertenece. -Cuando se trata de la primera clase para un caso
 * de prueba no lleva ningun identificador de proyecto. [Tipo ejecucion]_[Tipo
 * Caso de prueba]_[Numero de Caso]_[Nombre caso de prueba]_[Proyecto] El tipo
 * de ejecucion siempre debe ser ATC para los casos de prueba automatizados. Ej:
 * ATC_FT_001_TestExample_Project (cuando es para un proyecto) ATC - Automated
 * Test Case FT - Functional Test 001 - Test case Nombre - TestExample Project -
 * Proyecto para el que se desarrollo el caso de prueba
 *
 * La clase siempre debe estar extendida de "BaseExecution", para usar los
 * metodos y herramientas del Framework de Oxxo
 */

public class ATC_FT_001_Validar_nuevo_nombre_de_la_interfaz_EO17_MX extends BaseExecution {

/**
 * Descripcion del test:
 * Validar el nombre de la interfaz EO17_MX. 
 * 
 * Pre-requisitos:
 * 1- Cargar Json que no cumpla con los parametros
 * 2- Que se haya generado un error en la carga json 
 *
 * Insumos:
 * 1- Tener acceso a la Base de Datos AVEBQA 
 */
	
/**
*  Con la sintaxix @Test del TestNG, se indica que el metodo que esta a
* continuacion es un Test.
* 
* Requisito mandatorios para el Nombre (Test): -No incluir acentos, espacios en blanco
* ni caracteres especiales en el nombre -La longitud del nombre no debe exceder de
* 100 caracteres -El nombre del caso de prueba debe ser unico. [Tipoejecucion]_[Tipo Caso de 
* prueba]_[Numero de Caso]_[Nombre caso de prueba]_Test El tipo de ejecucion siempre 
* debe ser ATC para los casos de prueba automatizados. Ej: ATC_FT_001_TestExample_Test ATC - Automated Test
* Case FT - Functional Test 001 - Test case Nombre - TestExample
* 
* Se manda como parametro el Data Provider: este es un archivo que esta alojado
* en Config/Input/DataProviders/TestExample/ATC_FT_001_TestExample.xlsx
*/
	
  @Test (dataProvider = "data-provider") // Data = Datos Provider = proveedor (el test empieza dandome datos del test validar nuevo nombre de la interfaz FEMSA_EO17_MX y me muestra una cadena de datos
  public void ATC_FT_001_Validar_nuevo_nombre_de_la_interfaz_EO17_MX_test(HashMap<String, String> data) 
		  throws Exception { 
		 
	 /** Abrir la pagina web donde se carga el JSON
	 * @param SQLUtil 
	 * @author Melba Solis
	 */
	//Utileria// Se considera una utileria la BD //
	//****************** Llamar a la Base de Datos **********************/
		  
	 SQLUtil DBFCWMLQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA3, GlobalVariables.DB_USER_FCWMLQA3, GlobalVariables.DB_PASSWORD_FCWMLQA3);
		  
	// Variables
	//********************************************************************/
	//testcase va a mandar una descripcion del dato id y obtener el campo descripcion //get = obtener //set= mandar
	testCase.setTest_Description(data.get("id") + data.get("description"));

	//testcase va a mandar los prerequisitos 
	testCase.setPrerequisites("La tabla WMLOG.WM_LOG_ERROR_TPE debe estar creada en la BD FCWMLQA Previamente se debe haber generado un error");
	//Para obtener el id y la descripcion necesito tener primero la BD (la tabla) si falla va a aparecer el mensaje que coloque y si no muestra nada, significa que esta bien y arrojara el id y la descripcion

	System.out.println(

	"*************** PASOS 1 y 2 ******************************************");
	//add = añadir // step = paso //STRING = CADENA (CADENA ALFANUMERICA) // owner = dueño // from = desde // where = donde // 
	//añadir un paso // El paso cosiste en que la cadena consultara la tabla AVEBQA y seleccionara al dueño nombre de la tabla desde todas las tablas 
	addStep("Ingresar a la BD de FCMLQA y realizar la siguiente consulta para validar el nombre de la interfaz EO17_MX");
	String consulta_FCWMLQA = data.get("consulta_error"); 
	System.out.println("Tu consulta es: " + consulta_FCWMLQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO

	SQLResult ExecuteQuery = executeQuery(DBFCWMLQA, consulta_FCWMLQA); // execute = ejecutar Query=consulta (DBFCMLQA, consulta_FCMLQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)

	boolean conexiondbFwmlqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
	conexiondbFwmlqa  = ExecuteQuery.isEmpty();

	testCase.addTextEvidenceCurrentStep("Conexion: FCWMLQA ");
	testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA);
	testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

	assertFalse(conexiondbFwmlqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
			
	String tipoDato = ExecuteQuery.getData(0, "TPE_TYPE");
	boolean validacionTipoDato = tipoDato.equals("EO17_MX");		
	assertTrue(validacionTipoDato, "No trae el dato correcto: EO17_MX");	

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
	return "Validar el nuevo nombre de interfaz EO17_MX, consultando la existencia de la tabla WMLOG.WM_LOG_ERROR_TPE en la BD FCWMLQA para que al haber provocado previamente un error, se pueda observar el valor que contenga el nombre de la interfaz ";
	}

	@Override
	public String setTestDesigner() {
	return "Melba Solís Castillo";
	}

	@Override
	public String setTestFullName() {
	return "ATC_FT_001_Validar_nuevo_nombre_de_la_interfaz_EO17_MX_test";
	}

	@Override
	public String setTestInstanceID() {
	return null;
	}

	@Override
	public String setPrerequisites() {
	return "La tabla WMLOG.WM_LOG_ERROR_TPE debe estar creada en la BD FCWMLQA \"Línea 1\\nLínea 2\" Previamente se debe haber generado un error";
	}

	@AfterMethod
	public void afterMethod() {

	}
}
