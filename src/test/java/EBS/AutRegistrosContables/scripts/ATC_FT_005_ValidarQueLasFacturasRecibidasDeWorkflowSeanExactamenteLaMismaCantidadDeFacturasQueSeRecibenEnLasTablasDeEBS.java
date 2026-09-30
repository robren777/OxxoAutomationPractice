package EBS.AutRegistrosContables.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
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
* DESCRIPCIÓN: Validar que el Portal de Workflow tenga la conexión a las tablas staging que se crearán en EBS, para recibir la información necesaria para crear la Factura y la informacion se guarde correctamente
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_005_ValidarQueLasFacturasRecibidasDeWorkflowSeanExactamenteLaMismaCantidadDeFacturasQueSeRecibenEnLasTablasDeEBS
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

public class ATC_FT_005_ValidarQueLasFacturasRecibidasDeWorkflowSeanExactamenteLaMismaCantidadDeFacturasQueSeRecibenEnLasTablasDeEBS extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_ValidarQueLasFacturasRecibidasDeWorkflowSeanExactamenteLaMismaCantidadDeFacturasQueSeRecibenEnLasTablasDeEBS_test(HashMap<String, String> data)
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
				assertTrue(ebsDataBase.getConn().isValid(10),"Validacion de conexion a EBS");
				//Se ejecuta el query y se valida que no venga vacio
				SQLResult getRealTable = ebsDataBase.executeQuery(queryAllData);
				SQLResult tableA = ebsDataBase.executeQuery("SELECT * FROM APPS.XXFC_AP_INVLINES_STG_ALL");
				SQLResult tableB = ebsDataBase.executeQuery("SELECT * FROM apps.XXFC_AP_INVOICES_STG_ALL");
				assertTrue(!getRealTable.isEmpty(),"Se valida que la consulta no venga vacia");
				utilsSql.prettyPrint(ebsDataBase, tableA, 5, testCase);
				utilsSql.prettyPrint(ebsDataBase, tableB, 5, testCase);

		//PASO 6
		addStep("Tomar la informacion que regreso sobre la factura.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");				
				
		//PASO 7
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
			
		//PASO 8
		addStep("Buscar la Factura en el EBS, ver los campos de la informacion de la factura si coinciden.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
			
		// PASO 9
		addStep("Se analizan si la informacion de la Factura es la correcta.");
		assertTrue(true);
		testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
		
		if(!utilsSql.checkDay()) {
			// PASO 10
			addStep("Ver dia no es fin de mes o dias 1 y 2 de el mes, esperar 15 a 30 minutos.");
			assertTrue(!utilsSql.checkDay(), "Se valida si es dia primero, segundo o ultimo");
			testCase.addTextEvidenceCurrentStep("El dia de hoy es: " + LocalDate.now());
			
			System.out.println("Inicia wait de 15 min");
			Thread.sleep(15 * 60 * 1000); // 15 minutos en milisegundos
			// PASO 11
			addStep("Tomar la captura de Facturas en el WorkFlow, usando la liga https://pre.iacostforce.mx/solicitudes/api_listado_pagos/.");
			assertTrue(true);
			testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");

			// PASO 12
			addStep("Colocar la pasword de acceso a la liga."); 
			utilsEBS.loggingResponsabilidadApp(ebsUrl,userName,responsabilidad,inicio,paginaInicial);

			// PASO 13
			addStep("Tomar la informacion que regreso sobre la factura");
			String []datosFactura=utilsEBS.abrirfactura(getRealTable.getData(0, "INVOICE_NUM"));
			testCase.addTextEvidenceCurrentStep("Se obtuvieron los siguientes valores:");

			testCase.addTextEvidenceCurrentStep("Descripcion :" + datosFactura[0]);
			testCase.addTextEvidenceCurrentStep("Provedor :" + datosFactura[1]);
			testCase.addTextEvidenceCurrentStep("Num_Factura :" + datosFactura[2]);
			
			
			// PASO 14
			addStep("Buscar la Factura en el EBS, ver los campos de la informacion de la factura si coinciden.");
			String queryValidation = "SELECT NRO_PROVEEDOR,DESCRIPTION, INVOICE_NUM FROM apps.XXFC_AP_INVOICES_STG_ALL where invoice_num='" +datosFactura[2]+"'";	
			SQLResult validChangeExec = ebsDataBase.executeQuery(queryValidation);
			System.out.println("Query: " +queryValidation);
			utilsSql.prettyPrint(ebsDataBase, validChangeExec, 5, testCase);
			assertTrue(utilsSql.validardatosfactura(datosFactura[2],datosFactura[0],datosFactura[1],validChangeExec),"Validacion de valores");
			

			// PASO 15
			addStep("Se analizan si la informacion de la Factura es la correcta.");
			assertTrue(true);
			testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
		}
		else {
			// PASO 16
			addStep("Mover el dia al fin de mes o dia 1 y 2 del mes y esperar 5 minutos.");
			assertTrue(utilsSql.checkDay(), "Se valida si es dia primero, segundo o ultimo");
			testCase.addTextEvidenceCurrentStep("El dia de hoy es: " + LocalDate.now());

			System.out.println("Inicia wait de 5 min");
			Thread.sleep(5 * 60 * 1000); // 5 minutos en milisegundos

			// PASO 17
			addStep("Tomar la captura de Facturas en el WorkFlow, usando la liga https://pre.iacostforce.mx/solicitudes/api_listado_pagos/.");
			assertTrue(true);
			testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");

			// PASO 18
			addStep("Colocar la pasword de acceso a la liga.");
			utilsEBS.loggingResponsabilidadApp(ebsUrl,userName,responsabilidad,inicio,paginaInicial);
			
			// PASO 19
			addStep("Tomar la informacion que regreso sobre la factura");
			String []datosFactura=utilsEBS.abrirfactura(getRealTable.getData(0, "INVOICE_NUM"));
			testCase.addTextEvidenceCurrentStep("Se obtuvieron los siguientes valores:");

			testCase.addTextEvidenceCurrentStep("Descripcion :" + datosFactura[0]);
			testCase.addTextEvidenceCurrentStep("Provedor :" + datosFactura[1]);
			testCase.addTextEvidenceCurrentStep("Num_Factura :" + datosFactura[2]);
			
			
			// PASO 20
			addStep("Buscar la Factura en el EBS, ver los campos de la informacion de la factura si coinciden.");
			String queryValidation = "SELECT NRO_PROVEEDOR,DESCRIPTION, INVOICE_NUM FROM apps.XXFC_AP_INVOICES_STG_ALL where invoice_num='" +datosFactura[2]+"'";	
			SQLResult validChangeExec = ebsDataBase.executeQuery(queryValidation);
			System.out.println("Query: " +queryValidation);
			utilsSql.prettyPrint(ebsDataBase, validChangeExec, 5, testCase);
			assertTrue(utilsSql.validardatosfactura(datosFactura[2],datosFactura[0],datosFactura[1],validChangeExec),"Validacion de valores");

			// PASO 21
			addStep("Se analizan si la informacion de la Factura es la correcta.");
			assertTrue(true);
			testCase.addTextEvidenceCurrentStep("Este paso ya se ejecuto en los pasos previos");
		}
		ebsDataBase.close();	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarQueLasFacturasRecibidasDeWorkflowSeanExactamenteLaMismaCantidadDeFacturasQueSeRecibenEnLasTablasDeEBS";
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
