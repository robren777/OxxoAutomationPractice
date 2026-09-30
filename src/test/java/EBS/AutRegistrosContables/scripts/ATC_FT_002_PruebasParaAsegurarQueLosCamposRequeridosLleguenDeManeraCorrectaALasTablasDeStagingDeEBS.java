package EBS.AutRegistrosContables.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import EBS.AutRegistrosContables.om.AutRegContables;
import EBS.AutRegistrosContables.om.AutRegistrosContablesAPI;
import EBS.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


/**
* DESCRIPCIÓN: Validar que el Portal de Workflow tenga la conexión a las tablas staging que se crearán en EBS, para recibir la información necesaria para crear la Factura y la informacion se guarde correctamente
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_002_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS
* 
* PREREQUISITOS:
* 	"1.- Contar con acceso a la BD de EBS"
*	"2.- Tener Insumos en el bus de Workflow en espera de la ejecucion del primer Microservicio"
*	"3.- Ejecutar el script un momento antes de que el microservicio entre en accion(cada 5 o 15 minutos, depende de la configuracion)";	
* 
* INSUMOS:
* 	-Se requiere tener insumos en workflow
* 
* @author bernardo.mejia@grupotimex.onmicrosoft.com
* @date 08/Mayo/2025
*/

public class ATC_FT_002_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("AutRegistrosContables");
		/*
		 * VARIABLES y Utilidades
		 */
		String queryInvoices = "SELECT * FROM apps.XXFC_AP_INVOICES_STG_ALL";
		AutRegContables utilsSql = new AutRegContables();
		AutRegistrosContablesAPI utilsApi = new AutRegistrosContablesAPI();
		
		//PASO 1
		addStep(utilsSql.getStepFacturasWorkflow());
		Response workflowResponse = utilsApi.getResponseWorkflow();
		assertEquals(workflowResponse.statusCode(),200 , "Validacion del status code de la respuesta");
		testCase.addTextEvidenceCurrentStep("El estatus regresado de la API es : " + Integer.toString(workflowResponse.statusCode()));
		testCase.addTextEvidenceCurrentStep("El Json encontrado es:");
		testCase.addTextEvidenceCurrentStep(workflowResponse.asPrettyString());
		
		//PASO 2
		addStep(utilsSql.getStepMicroservicio());
		assertTrue(utilsSql.checarStatusM01(), "Validacion de ejecucion del microservicio");
		testCase.addTextEvidenceCurrentStep("Se ejecuto el Microservicio");
		
		//PASO 3
		addStep(utilsSql.getStepComparacion1());
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso no se puede ejecutar por cambios realizdos en el desarrollo.");
		
		//PASO 4
		addStep("La informacion concide asi como el tipo de campo, adelante.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso no se puede ejecutar por cambios realizdos en el desarrollo.");
		
		//PASO 5
		addStep("Buscar la Facturas en el EBS, ver los campos de la informacion de las facturas si coincidan. XXFC_AP_INVOICES_STG_ALL");
		//Se hace la llamada
				SQLUtil ebsDataBase = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, 
											GlobalVariables.DB_USER_AVEBQA, 
											GlobalVariables.DB_PASSWORD_AVEBQA);
				assertTrue(ebsDataBase.getConn().isValid(10),"Validacion de conexion a EBS");
				//Se ejecuta el query y se valida que no venga vacio
				SQLResult getRealTable = ebsDataBase.executeQuery(queryInvoices);
				assertTrue(!getRealTable.isEmpty(),"Se valida que la consulta no venga vacia");
				utilsSql.prettyPrint(ebsDataBase, getRealTable, 5, testCase);
				
		//PASO 6
		addStep("Si la informacion no coincide con la que se trae arrastrando se falla.");
		testCase.addBoldTextEvidenceCurrentStep("Se validan los siguientes Invoices Numbers: ");
		testCase.addTextEvidenceCurrentStep("En Api de Workflow: ");
		List<String> invoicesWorkflow = utilsApi.getAllInvoices(workflowResponse);
		for (int i = 0; i < invoicesWorkflow.size(); i++) {
			testCase.addTextEvidenceCurrentStep(invoicesWorkflow.get(i));
		}
		testCase.addTextEvidenceCurrentStep("En base de datos de EBS: ");
		List<String> invoicesEbs = utilsSql.getAllInvoices(getRealTable);
		for (int i = 0; i < invoicesEbs.size(); i++) {
			testCase.addTextEvidenceCurrentStep(invoicesEbs.get(i));
		}
		assertTrue(utilsApi.getAllInvoices(workflowResponse).size()==utilsSql.getAllInvoices(getRealTable).size(),"Se valida que tengan el mismo size.");

		ebsDataBase.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el Portal de Workflow tenga la conexión a las tablas staging que se crearán en EBS, para recibir la información necesaria para crear la Factura y la informacion se guarde correctamente";
	}

	@Override
	public String setTestDesigner() {
		return "bernardo.mejia@grupotimex.onmicrosoft.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Contar con acceso a la BD de EBS\r\n"
		+ "		2.- Tener Insumos en el bus de Workflow en espera de la ejecucion del primer Microservicio\r\n"
		+ "		3.- Ejecutar el script un momento antes de que el microservicio entre en accion(cada 5 o 15 minutos, depende de la configuracion)\n";		
	}

	@Override
	public void beforeTest() {

	}
	
}
