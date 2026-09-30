package WM.interfaces.femsa_ws;

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

public class ATC_FT_002_El_campo_UUID_RELACIONADO_se_traslade_a_la_tabla_XXFC_CFDAVALIDAR extends BaseExecution {
	
/**
* Descripcion del test:
 * Validar la existencia del campo UUID RELACIONADO en la tabla XXFC.XXFC_CFDAVALIDAR de la BD AVEBQA. 
 * 
 * Pre-requisitos:
 * 1- El campo debera estar creado en la tabla XXFC.XXFC_CFDAVALIDAR
 * 2- Acceder a la BD AVEBQA
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
	
  @Test(dataProvider = "data-provider") // Data = Datos Provider = proveedor (el test empieza dandome datos del test trasladar campos y me muestra una cadena de datos
  public void ATC_FT_002_El_campo_UUID_RELACIONADO_se_traslade_a_la_tabla_XXFC_CFDAVALIDAR_test(HashMap<String, String> data) 
	  throws Exception {
	  
	 /** Llamar a la Base de Datos
	 * @param SQLUtil
	 * @author Melba Solís
	 */
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
		addStep("Ingresar a la BD AVEBQA, realizar la siguiente consulta para validar la existencia del campo UUID RELACIONADO en la tabla XXFC.XXFC_CFDAVALIDAR");
		String consulta_AVEBQA = data.get("valor_consecutivo"); // "SELECT VALC_UUIDRELACIONADO FROM XXFC.XXFC_CFDAVALIDAR WHERE VALC_UUIDRELACIONADO = ??"
		System.out.println("Tu consulta es: " + consulta_AVEBQA); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		
		SQLResult ExecuteQuery = executeQuery(dbAvebqa, consulta_AVEBQA); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
		
		boolean conexiondbAvebqa  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
		conexiondbAvebqa  = ExecuteQuery.isEmpty();

		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);

		assertFalse(conexiondbAvebqa, "La conexion no fue exitosa"); // si es falso va a arrojar un error 
		
		/******************************PASO 3 Y 4************************************/
		
		addStep("Ingresar a las BD AVEBQA, realizar la siguiente consulta para validar que el campo UUID RRELACIONADO cuenta con sus propiedades originales de tipo VARCHAR2 y longitud 4,000 en la tabla XXFC_CFDAVALIDAR");
		String consulta_Propiedades = data.get("propiedades"); // CAMBIAR VALN_CONSECUTIVO CON VALC_UUIDRELACIONADO
		System.out.println("Tu consulta es: " + consulta_Propiedades); //IMPRIMIR LA CONSULTA DE LA BD + RESULTADO
		
		SQLResult ExecuteQueryp = executeQuery(dbAvebqa, consulta_Propiedades); // execute = ejecutar Query=consulta (dbAVEBQA, consulta_AVEBQA) ejecutar consulta de la BD que es igual al escript validar tabla CFDAVALIDAR (va a buscar la tabla)
						
		boolean conexiondbAvebqa2  = true; // variable que nos arroja si o no // si la conexion de datos se conecta es verdadero
		conexiondbAvebqa2  = ExecuteQueryp.isEmpty();

		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQueryp);

		assertFalse(conexiondbAvebqa2, "La conexion no fue exitosa");
		
		String tipoDato = ExecuteQueryp.getData(0, "data_type");
		boolean validacionTipoDato = tipoDato.equals("VARCHAR2");		
		assertTrue(validacionTipoDato, "No trae el dato correcto: VARCHAR2");	

		String longitudDato = ExecuteQueryp.getData(0, "data_length");
		boolean validacionLongitudDato = longitudDato.equals("4000");	
		assertTrue(validacionLongitudDato, "No trae el dato correcto: 4000");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Validar la existencia del campo UUID Relacionado en la tabla XXFC.XXFC_CFDAVALIDAR y comprobar las propiedades de tipo y longutud del campo";
	}

	@Override
	public String setTestDesigner() {
		return "Melba Solís Castillo";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_El_campo_UUID_RELACIONADO_se_traslade_a_la_tabla_XXFC_CFDAVALIDAR_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "El Campo UUID RELACIONADO debe estar creada en la tabla XXFC.XXFC_CFDAVALIDAR en la BD AVEBQA";
	}

	@AfterMethod
	public void afterMethod() {

	}
}

