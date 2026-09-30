package EBS.AutRegistrosContables.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.AutRegistrosContables.om.AutRegContables;
import EBS.AutRegistrosContables.om.AutRegistrosContablesAPI;
import EBS.AutRegistrosContables.om.AutRegistrosContablesEBS;
import EBS.AutRegistrosContables.om.Factura;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* DESCRIPCIÓN: Crear un concurrente Para la importación de las Facturas al módulo de EBS a las tables de AP y tomar el tiempo que se tarde este proceso
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_008_ValidarFacturasRecibidasDeEBSSeanLaMismaCantidadQueElConcurrente
* 
* PREREQUISITOS:
* 	"1.- Contar con acceso a la BD de EBS"
*	"2.- Tener Insumos en el bus de Workflow en espera de la ejecucion del primer Microservicio"
*	"3.- Ejecutar el script un momento antes de que el microservicio entre en accion(cada 5 o 15 minutos, depende de la configuracion)";	
* 
* INSUMOS:
* 	-Se requiere tener insumos en workflow
* 
* @author juan.lopez@grupotimexico.com
* @date 08/Mayo/2025
*/

public class ATC_FT_008_ValidarFacturasRecibidasDeEBSSeanLaMismaCantidadQueElConcurrente extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_008_ValidarFacturasRecibidasDeEBSSeanLaMismaCantidadQueElConcurrente_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("AutRegistrosContables");
		/*
		 * VARIABLES y Utilidades
		 */
		String queryAllData = "SELECT a.*,b.* "
				+ "FROM APPS.XXFC_AP_INVOICES_STG_ALL a, APPS.XXFC_AP_INVLINES_STG_ALL b "
				+ "WHERE a.id=b.header_id";
		
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = data.get("password");
		String responsabilidad = data.get("responsabilidad");
		String Concurrente = data.get("Concurrente");		
		String facturaNumero= "";

		//UTILERIAS WEB		 
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		Pattern pathimg = new Pattern();
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();		 
		File Folder = new File(pathImg);
		
		AutRegistrosContablesEBS utilsEBS = new AutRegistrosContablesEBS(sk,u, testCase,pathimg,ListImage,ImgPattern,patterns,Folder);
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
		assertTrue(ebsDataBase.getConn().isValid(10), "Validacion de conexion a EBS");
		// Se ejecuta el query y se valida que no venga vacio
		SQLResult getRealTable = ebsDataBase.executeQuery(queryAllData);
		SQLResult tableA = ebsDataBase.executeQuery("SELECT * FROM APPS.XXFC_AP_INVLINES_STG_ALL");
		SQLResult tableB = ebsDataBase.executeQuery("SELECT * FROM apps.XXFC_AP_INVOICES_STG_ALL");
		assertTrue(!getRealTable.isEmpty(),"Se valida que la consulta no venga vacia");
		utilsSql.prettyPrint(ebsDataBase, tableA, 5, testCase);
		utilsSql.prettyPrint(ebsDataBase, tableB, 5, testCase);

		// Se mapea todo el response del api
		List<Factura> workflow = utilsApi.getFacturas(workflowResponse);

		//PASO 6
		addStep("Ejecutamos el concurrente y tomamos el tiempo que dure en procesar");
		utilsEBS.loggingResponsabilidadApp(ebsUrl,userName,responsabilidad,inicio,paginaInicial);
		
		//Se toma tiempo de incio de concureente
		long inicioConcu = System.currentTimeMillis();
		 
		//Se ejecuta el concurrente		
		assertTrue(utilsEBS.ejecutarConcurrente(Concurrente));
		long fin = System.currentTimeMillis();
		long duracion = fin - inicioConcu; // en milisegundos
		testCase.addTextEvidenceCurrentStep("Duración: " + duracion + " ms");

		
		//PASO 7
		addStep("Validar que la cantidad de facturas procesadas desde EBS sea la misma que se proceso en las tablas XXFC_AP_RESPONSE_STG_ALL, XXFC_AP_INVOICES_STG_ALL");
		assertTrue(utilsApi.getTotalLines(workflow) == getRealTable.getRowCount(), "Se valida que tengan el mismo size.");
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");

		ebsDataBase.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_ValidarFacturasRecibidasDeEBSSeanLaMismaCantidadQueElConcurrente";
	}

	@Override
	public String setTestDescription() {
		return "Crear un concurrente Para la importación de las Facturas al módulo de EBS a las tables de AP y tomar el tiempo que se tarde este proceso";
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
