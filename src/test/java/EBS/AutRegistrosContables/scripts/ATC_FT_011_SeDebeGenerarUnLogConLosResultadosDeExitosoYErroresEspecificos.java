package EBS.AutRegistrosContables.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
import EBS.AutRegistrosContables.om.RegistroCR;
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
* DESCRIPCIÓN: Se debe generar un log con los resultados de exitoso y errores especificos
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_011_SeDebeGenerarUnLogConLosResultadosDeExitosoYErroresEspecificos
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

public class ATC_FT_011_SeDebeGenerarUnLogConLosResultadosDeExitosoYErroresEspecificos extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_011_SeDebeGenerarUnLogConLosResultadosDeExitosoYErroresEspecificos_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("AutRegistrosContables");
		/*
		 * VARIABLES y Utilidades
		 */
		String queryAllData = "SELECT a.*,b.* "
				+ "FROM APPS.XXFC_AP_INVOICES_STG_ALL a, APPS.XXFC_AP_INVLINES_STG_ALL b "
				+ "WHERE a.id=b.header_id";
		String queryErrors = "SELECT * "
				+ "FROM apps.XXFC_AP_RESPONSE_STG_ALL";
		String queryErrorsAgain = "SELECT * "
				+ "FROM apps.XXFC_AP_RESPONSE_STG_ALL WHERE ESTATUS = \"SUCCES\"";
		
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
				assertTrue(ebsDataBase.getConn().isValid(10),"Validacion de conexion a EBS");
				//Se ejecuta el query y se valida que no venga vacio
				SQLResult getRealTable = ebsDataBase.executeQuery(queryAllData);
				SQLResult tableA = ebsDataBase.executeQuery("SELECT * FROM APPS.XXFC_AP_INVLINES_STG_ALL");
				SQLResult tableB = ebsDataBase.executeQuery("SELECT * FROM apps.XXFC_AP_INVOICES_STG_ALL");
				assertTrue(!getRealTable.isEmpty(),"Se valida que la consulta no venga vacia");
				utilsSql.prettyPrint(ebsDataBase, tableA, 5, testCase);
				utilsSql.prettyPrint(ebsDataBase, tableB, 5, testCase);
				
		//PASO 6
		addStep("Si la informacion no coincide con la que se trae arrastrando se falla.");
		testCase.addBoldTextEvidenceCurrentStep("Se validan los siguientes Valores: ");
		testCase.addTextEvidenceCurrentStep("En Api de Workflow: ");
		//Se mapea todo el response del api
		List<Factura> workflow = utilsApi.getFacturas(workflowResponse);
		List<RegistroCR> registros;
		 for (Factura factura : workflow) {
		        if (factura.getRegistros_cr() != null) {
		        	registros = factura.getRegistros_cr();
		        	for (RegistroCR registroCR : registros) {
		        		testCase.addTextEvidenceCurrentStep(factura.getNumero_oc());
		        		testCase.addTextEvidenceCurrentStep(registroCR.getCuenta_distribucion());
		        		testCase.addTextEvidenceCurrentStep(registroCR.getDescripcion_servicio());
		        		testCase.addTextEvidenceCurrentStep(registroCR.getImporte());
					}
		        }
		    }
		testCase.addTextEvidenceCurrentStep("En base de datos de EBS: ");
		List<String[]> linesEbs = utilsSql.getAllLinesAndInvoices(getRealTable);
		for (int i = 0; i < linesEbs.size(); i++) {
			testCase.addTextEvidenceCurrentStep(linesEbs.get(i)[0]);
			testCase.addTextEvidenceCurrentStep(linesEbs.get(i)[1]);
			testCase.addTextEvidenceCurrentStep(linesEbs.get(i)[2]);
			testCase.addTextEvidenceCurrentStep(linesEbs.get(i)[3]);
		}
		assertTrue(utilsApi.getTotalLines(workflow)==linesEbs.size(),"Se valida que tengan el mismo size.");
		
		// PASO 7
		addStep("Colocae la pasword de acceso a la liga.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
		
		//PASO 8
		addStep("Tomar la informacion que regreso sobre la factura.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
				
		//PASO 9
		addStep("Buscar la Factura en el EBS, ver los campos de la informacion de la factura si coinciden.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
			
		//PASO 10
		addStep("Se analizan la tabla de errores en el EBS, para serciorarnos que exista alguna factura con error en la tabla correspondiente.");
		SQLResult getErros = ebsDataBase.executeQuery(queryErrors);
		assertTrue(!getErros.isEmpty(),"Se valida que la consulta Errors no venga vacia");
		utilsSql.prettyPrint(ebsDataBase, getErros, 5, testCase);
		List<String> errorsInvoices = utilsSql.getInvoicesWithErrors(getErros);
		//Se extraen los invoices_num para comparar
		List<String> invoicesNum = utilsSql.getInvoicesToCompare(getRealTable);
		
		if(!errorsInvoices.isEmpty()) {
			testCase.addTextEvidenceCurrentStep("Los invoices con errores encontrados son: ");			
			for (String invoice : errorsInvoices) {
				testCase.addTextEvidenceCurrentStep(invoice);	
				//Se valida que los invoice que comparamos no se encuentren en los errores
				assertFalse(invoicesNum.contains(invoice));
			}
		}
		else {
			testCase.addTextEvidenceCurrentStep("No se encontraron errores.");
		}
		
			
		// PASO 11
		addStep("Se consulta en el EBS para ver que los que no tuvieron errores se depuraron al instante.");
		SQLResult getErrosAgain = ebsDataBase.executeQuery(queryErrorsAgain);
		assertTrue(getErrosAgain.isEmpty(),"Se valida que la consulta Errors venga vacia");
		utilsSql.prettyPrint(ebsDataBase, getErros, 5, testCase);

		// PASO 12
		addStep("Se consulta en el EBS para ver que las que si tuvieron errores se no fueron depuradas, dejando antesedente hasta ser corregidas.");
		SQLResult getErrosFinal = ebsDataBase.executeQuery(queryErrors);
		assertTrue(!getErrosFinal.isEmpty(),"Se valida que la consulta Errors final no venga vacia");
		utilsSql.prettyPrint(ebsDataBase, getErros, 5, testCase);
		List<String> errorsInvoicesFinal = utilsSql.getInvoicesWithErrors(getErrosFinal);

		if (!errorsInvoicesFinal.isEmpty()) {
			testCase.addTextEvidenceCurrentStep("Los invoices con errores encontrados nuevamente son: ");
			for (String invoice : errorsInvoicesFinal) {
				testCase.addTextEvidenceCurrentStep(invoice);
			}
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontraron errores.");
		}
		assertTrue(errorsInvoicesFinal.size()==errorsInvoices.size(),"Se comparan los tamanos de la tabla de errores.");
		
		//PASO 13
		addStep("Tomar la captura de la Factura en el WorkFlow, usando la liga https://pre.iacostforce.mx/solicitudes/api_listado_pagos/..");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("La API se volvio obsoleta.");

		// PASO 14
		addStep("Tomar la informacion que regreso sobre la factura.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya no aplica");

		// PASO 15
		addStep("Buscar la Factura en el EBS, ver los campos de la informacion de la factura si coinciden.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya no aplica");

		// PASO 16
		addStep("Se analizan la tabla de errores en el EBS, para serciorarnos que no se genero ni un error relacionado con esta factura.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");

		// PASO 17
		addStep("Se analizan si la informacion de la Factura es la correcta.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");

		// PASO 18
		addStep("Se consulta en el EBS para ver que los que no tuvieron errores se depuraron al instante.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");

		// PASO 19
		addStep("Abrir el archivo Log en la ruta especificada, y buscar las dos facturas la con error y la sin error.");
		utilsEBS.loggingResponsabilidadApp(ebsUrl,userName,responsabilidad,inicio,paginaInicial);
		boolean statusConcurrente = utilsEBS.ejecutarConcurrente(Concurrente); 
		boolean statusLogConcurrente = utilsEBS.verLogConcurrente();
		assertTrue(statusConcurrente && statusLogConcurrente,"Validacion de que se ejecutaron correctamente el paso del concurrente y su log");
		
		ebsDataBase.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_SeDebeGenerarUnLogConLosResultadosDeExitosoYErroresEspecificos";
	}

	@Override
	public String setTestDescription() {
		return "Se debe generar un log con los resultados de exitoso y errores especificos";
	}

	@Override
	public String setTestDesigner() {
		return "juan.lopez@gruptimexico.com";
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
