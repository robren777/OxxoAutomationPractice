package WM.apis.orders_inbound.order_response;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.OrdersInbound;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;



@AzureTestInfo(

		areaPath = "I25086-Proceso End to End\\MVP2 Proceso End to End",
		iterationPath = "I25086-Proceso End to End\\Sprints MVP2\\Sprint 9",
		tags = "",
		userStoryId = 217683

)

/**
DESCRIPCIÓN:
Realizar la creacion del mensaje ORDRSP para una Orden de compra de Cedis con estatus Aceptado que envia Grupo Modelo a OXXO a travez de Terra en formato JSON esta informacion se almacena en las siguientes tablas ordrsp.headers y ordrsp.detail, ordrsp.transaction. y validar el registro en el log de microservicios
NOMBRE CASOS DE PRUEBA MANUAL QUE ABARCA: MTC_FT_036_ORDRSP Aceptado de OC para Cedis
PREREQUISITOS:
1.- Metodo: POST
2.- URL Microservicio:
3.- Configurar Seccion "Authorization"
Seleccionar Bearer Token y agregar la variable {{accessTokenUX}}
4.- Configurar seccion "Headers"
Tracking-Id -> "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66"
Channel-Id -> "TERRA"
Country-Code -> " MX"
Language  -> "SPA" 
User-Agent -> "TerraApp/1.0"
x-Gateway-APIKey -> "{{apiKey}}"
Content-Type -> "application/json"
AcceptAccept -> "application/json"
INSUMOS:
1.- URL del endpoint para recibir mensajes de ORDRSP.
2.- Contar con acceso a la BD terra_oxxo_respn (172.26.52.140).
3.- Contar con acceso a la consola de linux donde se genera el registro de logs del microservicio de Open Shift para mensajes ORDRSP.
@author	Sergio Robles Ramos
@date   01/12/2025
*/

public class ATC_FT_036_ORDRSPAceptadaDeOCParaCedis  extends BaseExecution{
	public String purchaseOrder;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_036_ORDRSPAceptadaDeOCParaCedis_test (HashMap<String, String> data) throws Exception {
		/*Utilerias (Conexiones a BD)
		*********************************************************************/
		OrdersInbound oi = new OrdersInbound(testCase);
		String getPathToken = data.get("getPathToken");
		String baseURLTokenTestdata = data.get("baseURLTokenTestdata");
		String clientId = data.get("clientId");
		String clientSecret = data.get("clientSecret");
		String baseURL_order_response = data.get("baseURL_order_response");
		String getPath_order_response = data.get("getPath_order_response");
		String messageNumber = data.get("w_messageNumber");
		String purchaseDate = data.get("w_purchaseDate");
		String messageFormat = data.get("w_messageFormat");
		purchaseOrder = data.get("w_purchaseOrder");
		String receiptDeadline = data.get("w_receiptDeadline");
		String positionCount = data.get("w_positionCount");
		String requesterSupplier = data.get("w_requesterSupplier");
		String receiverStore = data.get("w_receiverStore");
		String poStatus = data.get("w_poStatus");
		String apikey = data.get("APIKey");
		String details = data.get("w_details");
		String transshipmentCode = data.get("w_transshipmentCode");
		String transshipmentDescription = data.get("w_transshipmentDescription");
		PasswordUtil passwordUtil = new PasswordUtil();
		boolean validacionCodigoResp;
		String select_Header="Select * from ordrsp.ordrsp_header where purchase_order = "+purchaseOrder+"";
		String select_Details="Select * from ordrsp.ordrsp_detail where purchase_order = "+purchaseOrder+"";
		String select_Transaction="Select * from ordrsp.ordrsp_transaction where purchase_order = "+purchaseOrder+"";
		boolean validaResultBD;
		//***************************Paso 1*****************************************************************************
		addStep("Configurar request en Postman para simular el envio del mensaje ORDRSP por parte de Grupo Modelo para una Orden de compra de Cedis con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton Send");
				
		testCase.addBoldTextEvidenceCurrentStep("Campos a modificar para identificar la OC de Cedis en estatus Aceptado");
		testCase.addBoldTextEvidenceCurrentStep("Header:");
		testCase.addBoldTextEvidenceCurrentStep("message_format: W");
		testCase.addBoldTextEvidenceCurrentStep("purchase_order: Numero de Orden");
		testCase.addBoldTextEvidenceCurrentStep("receiver_store: Cedis");
		testCase.addBoldTextEvidenceCurrentStep("po_status: Aceptado");
		testCase.addBoldTextEvidenceCurrentStep("Details: ");
		testCase.addBoldTextEvidenceCurrentStep("position_status: 001 La posición ha sido creada");
		testCase.addBoldTextEvidenceCurrentStep("reason: N/A");
		testCase.addBoldTextEvidenceCurrentStep("Body para OC de Tienda en estatus Aceptado: ");
				
		String AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret)); 
		
		String code= oi.orderResponse(baseURL_order_response, getPath_order_response, messageNumber, purchaseDate, messageFormat, purchaseOrder, receiptDeadline, positionCount, requesterSupplier, receiverStore, poStatus, AccessToken, apikey, details,transshipmentCode,transshipmentDescription);
		System.out.println("code: " + code);
		
		//Validación del codigo de respuesta de la solicitud de token
		if (AccessToken.equals("error al obtener token")) {
			validacionCodigoResp=true;
			System.out.println("Error al obtener token");
		}else {
			validacionCodigoResp=false;
			System.out.println("Resultado Esperado: Se obtuvo el Token de forma exitosa: " + AccessToken);
		}
		assertFalse("No se pudo obtener el token.", validacionCodigoResp);

		//Validación del codigo de respuesta de la recepcion del mensaje ORDRSP
		if (code.equals("X200000")) {
			validacionCodigoResp=false;
			testCase.addTextEvidenceCurrentStep("Resultado Obtenido: Se obtiene codigo " + code + "- Mensaje ORDRSP recibido con exito");
		}else {
			validacionCodigoResp=true;
			testCase.addTextEvidenceCurrentStep("Resultado Obtenido: Se obtiene el codigo " + code + "- Mensaje ORDRSP no recibido");
		}
		assertFalse("Resultado Obtenido: Se obtiene el codigo " + code + "- Mensaje ORDRSP no recibido", validacionCodigoResp);
		
		
		//***************************Paso 2*****************************************************************************
		addStep("Iniciar sesion en OpenShift para consultar el log de microservicios");
		
				testCase.addBoldTextEvidenceCurrentStep("Ingresar en un navegador la URL de la consola Openshift");
				testCase.addBoldTextEvidenceCurrentStep("Dar clic en AAD");
				testCase.addBoldTextEvidenceCurrentStep("Seleccionar Administrator");
		testCase.addTextEvidenceCurrentStep("Paso Omitido por el doble factor de autenticación que usa openshift. Se valido con la Lider Automatizador durante la revision del SELECTOR y se concluyo en conjunto con la Lider de Calidad que la automatización del caso de pruba tiene mayor beneficio para el proyecto o futuros proyectos.");
				
		//***************************Paso 3*****************************************************************************
		addStep("Ingresar en las siguientes opciones para consultar el log del microservicio generado por las transacciones");
	
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el modo \"Administrator\" e ingresar en las siguientes opciones \"Workloads->Pods->Server QA->Pod ORDRSP\" y Seleccionar \"Logs\"");
		testCase.addTextEvidenceCurrentStep("Paso Omitido por el doble factor de autenticación que usa openshift. Se valido con la Lider Automatizador durante la revision del SELECTOR y se concluyo en conjunto con la Lider de Calidad que la automatización del caso de pruba tiene mayor beneficio para el proyecto o futuros proyectos.");
				
		//***************************Paso 4*****************************************************************************
		addStep("Abrir el manejador de base de datos postgresql, Ingresar usuario, contraseña, y la conexión indicada en los datos de prueba, Dar clic a conectar ");
		
		testCase.addTextEvidenceCurrentStep("Conexión exitosa a la base de datos");	
		testCase.addTextEvidenceCurrentStep(oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN.substring(0, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN.lastIndexOf('&')));
		//***************************Paso 5*****************************************************************************
		addStep("Consutar la tabla ORDRSP.HEADER con el campo OrderId \r\n"
		+ select_Header);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordened que se enviaron en el archivo JSON con mensaje ORDRSP");
				
		validaResultBD = oi.queryBDPostgresql(select_Header, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		
		//***************************Paso 6*****************************************************************************
		addStep("Consutar la tabla ORDRSP.DETAIL con el campo OrderId \r\n"
		+ select_Details);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje ORDRSP");
		
		validaResultBD = oi.queryBDPostgresql(select_Details, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");

		//***************************Paso 7*****************************************************************************
		addStep("Consutar la tabla ORDRSP.TRANSACTION con el campo OrderId \r\n"
		+ select_Transaction);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se inserta un registro de transaccion con los datos de fecha y hora, id de transacción, Status de transaccion, Detalle");
				
		validaResultBD = oi.queryBDPostgresql(select_Transaction, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		
		}

	
	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Metodo: POST\r\n"
				+ "2.- URL Microservicio:\r\n"
				+ "3.- Configurar Seccion \"Authorization\"\r\n"
				+ "Seleccionar Bearer Token y agregar la variable {{accessTokenUX}}\r\n"
				+ "4.- Configurar seccion \"Headers\"\r\n"
				+ "Tracking-Id -> \"4bd9e9c2-4989-4f21-8398-ad9d19ef0f66\"\r\n"
				+ "Channel-Id -> \"TERRA\"\r\n"
				+ "Country-Code -> \" MX\"\r\n"
				+ "Language  -> \"SPA\"\r\n"
				+ "User-Agent -> \"TerraApp/1.0\"\r\n"
				+ "x-Gateway-APIKey -> \"{{apiKey}}\"\r\n"
				+ "Content-Type -> \"application/json\"\r\n"
				+ "AcceptAccept -> \"application/json\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		return "Realizar la creacion del mensaje ORDRSP para una Orden de compra de Cedis con estatus Aceptado que envia Grupo Modelo a OXXO a travez de Terra en formato JSON esta informacion se almacena en las siguientes tablas ordrsp.headers y ordrsp.detail, ordrsp.transaction. y validar el registro en el log de microservicios";
	}

	@Override
	public String setTestDesigner() {
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_036_ORDRSPAceptadaDeOCParaCedis";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}