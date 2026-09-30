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
Validar la recepcion y almacenamiento del mensaje ORDRSP que envia Grupo Modelo para tienda, Cedis y Punto de transbordo los cuales llegan al sistema de OXXO a travez de Terra en formato JSON esta informacion se almacena en las siguientes tablas ordrsp_headers y ordrsp_details, ordrsp.transaction. y validar el registro en el log de microservicios
NOMBRE CASOS DE PRUEBA MANUAL QUE ABARCA: MTC_FT_009_Recibir y almacenar informacion diaria de ORDRSP
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
@date   05/12/2025
*/
public class ATC_FT_009_RecibirYAlmacenarInformacionDiariaDeORDRSP extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_RecibirYAlmacenarInformacionDiariaDeORDRSP_test (HashMap<String, String> data) throws Exception {
	
		/*Utilerias ()
		*********************************************************************/
		String header = "Header: ";
		String numerodeorden = "purchase_order: Numero de Orden";
		String status = "po_status: ACEPTADO";
		String details = "Details: ";
		String position = "position_status: 001 La posición ha sido creada";
		String reason = "reason: NA";
		String codestring = "Code: ";
		String errorToken = "error al obtener token"; 
		String obtuvoToken = "Resultado Esperado: Se obtuvo el Token de forma exitosa: ";
		String obtuvoCodigo = "Resultado Obtenido: Se obtiene codigo ";
		String ordrspExito = "- Mensaje ORDRSP recibido con éxito";
		String ordrspNoExito = "- Mensaje ORDRSP no recibido";
		String pasoOmitido = "Paso Omitido por el doble factor de autenticación que usa openshift. Se valido con la Lider Automatizador durante la revision del SELECTOR y se concluyo en conjunto con la Lider de Calidad que la automatización del caso de pruba tiene mayor beneficio para el proyecto o futuros proyectos.";
		String logMicro = "Seleccionar el modo \"Administrator\" e ingresar en las siguientes opciones \"Workloads->Pods->Server QA->Pod ORDRSP\" y Seleccionar \"Logs\"";
		String admin = "Ingresar en las siguientes opciones para consultar el log del microservicio generado por las transacciones";
		String validaString = "ValidaResultBD: ";
		String noBd = "No se obtuvo resultado esperado en BD";
		
		OrdersInbound oi = new OrdersInbound(testCase);
		//Extracción de valores generales del DataProvider
		String getPathToken = data.get("getPathToken");
		String baseURLTokenTestdata = data.get("baseURLTokenTestdata");
		String clientId = data.get("clientId");
		String clientSecret = data.get("clientSecret");
		String apikey = data.get("APIKey");
		String baseURL_order_response = data.get("baseURL_order_response");
		String getPath_order_response = data.get("getPath_order_response");
		//Extracción de valores para Ordern de Tienda del DataProvider
		String s_messageNumber = data.get("s_messageNumber");
		String s_purchaseDate = data.get("s_purchaseDate");
		String s_messageFormat = data.get("s_messageFormat");
		String s_purchaseOrder = data.get("s_purchaseOrder");
		String s_receiptDeadline = data.get("s_receiptDeadline");
		String s_positionCount = data.get("s_positionCount");
		String s_requesterSupplier = data.get("s_requesterSupplier");
		String s_receiverStore = data.get("s_receiverStore");
		String s_poStatus = data.get("s_poStatus");
		String s_details = data.get("s_details");
		//Extracción de valores para Orden de Cedis del DataProvider
		String w_messageNumber = data.get("w_messageNumber");
		String w_purchaseDate = data.get("w_purchaseDate");
		String w_messageFormat = data.get("w_messageFormat");
		String w_purchaseOrder = data.get("w_purchaseOrder");
		String w_receiptDeadline = data.get("w_receiptDeadline");
		String w_positionCount = data.get("w_positionCount");
		String w_requesterSupplier = data.get("w_requesterSupplier");
		String w_receiverStore = data.get("w_receiverStore");
		String w_poStatus = data.get("w_poStatus");
		String w_details = data.get("w_details");
		//Extracción de valores para Ordern de Punto de Transbordo del DataProvider
		String pt_messageNumber = data.get("pt_messageNumber");
		String pt_purchaseDate = data.get("pt_purchaseDate");
		String pt_messageFormat = data.get("pt_messageFormat");
		String pt_purchaseOrder = data.get("pt_purchaseOrder");
		String pt_receiptDeadline = data.get("pt_receiptDeadline");
		String pt_positionCount = data.get("pt_positionCount");
		String pt_requesterSupplier = data.get("pt_requesterSupplier");
		String pt_receiverStore = data.get("pt_receiverStore");
		String pt_poStatus = data.get("pt_poStatus");
		String pt_details = data.get("pt_details");
		String pt_transshipmentCode = data.get("pt_transshipmentCode");
		String pt_transshipmentDescription = data.get("pt_transshipmentDescription");
		//Selects para consultar información del registro para Tienda en BD Postgresql
		String select_Header="Select * from ordrsp.ordrsp_header where purchase_order in ("+s_purchaseOrder+", "+w_purchaseOrder+", "+pt_purchaseOrder+")";
		String select_Details="Select * from ordrsp.ordrsp_detail where purchase_order in ("+s_purchaseOrder+", "+w_purchaseOrder+", "+pt_purchaseOrder+")";
		String select_Transaction="Select * from ordrsp.ordrsp_transaction where purchase_order in ("+s_purchaseOrder+", "+w_purchaseOrder+", "+pt_purchaseOrder+")";

		PasswordUtil passwordUtil = new PasswordUtil();
		boolean validacionCodigoResp;
		boolean validaResultBD;
		
		//***************************Paso 1*****************************************************************************
		addStep("Configurar request en Postman para simular el envio del mensaje ORDRSP por parte de Grupo Modelo para una Orden de compra de Tienda con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton Send");
			
		testCase.addBoldTextEvidenceCurrentStep(header);
		testCase.addBoldTextEvidenceCurrentStep("message_format: S");
		testCase.addBoldTextEvidenceCurrentStep(numerodeorden);
		testCase.addBoldTextEvidenceCurrentStep("receiver_store: Tienda Retek");
		testCase.addBoldTextEvidenceCurrentStep(status);
		testCase.addBoldTextEvidenceCurrentStep(details);
		testCase.addBoldTextEvidenceCurrentStep(position);
		testCase.addBoldTextEvidenceCurrentStep(reason);
		testCase.addBoldTextEvidenceCurrentStep("Body para OC de Tienda en estatus Aceptado:  ");
				
		String AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret)); 
	

		String code= oi.orderResponse (baseURL_order_response, getPath_order_response, s_messageNumber, s_purchaseDate, s_messageFormat, s_purchaseOrder, s_receiptDeadline, s_positionCount, s_requesterSupplier, s_receiverStore, s_poStatus, AccessToken, apikey, s_details,"","");
		System.out.println(codestring + code);
		
		//Validación del codigo de respuesta de la solicitud de token
		if (AccessToken.equals(errorToken)) {
			validacionCodigoResp=true;
			System.out.println(errorToken);
		}else {
			validacionCodigoResp=false;
			System.out.println(obtuvoToken + AccessToken);
		}
		assertFalse("No se pudo obtener el token: ", validacionCodigoResp);

		//Validación del codigo de respuesta de la recepcion del mensaje ORDRSP
		if (code.equals("X200000")) {
			validacionCodigoResp=false;
			testCase.addTextEvidenceCurrentStep(obtuvoCodigo + code + ordrspExito);
		}else {
			validacionCodigoResp=true;
			testCase.addTextEvidenceCurrentStep(obtuvoCodigo + code + ordrspNoExito);
		}
		assertFalse(obtuvoCodigo + code + ordrspNoExito, validacionCodigoResp);


		//***************************Paso 2*****************************************************************************	
	
		addStep(logMicro);
		testCase.addBoldTextEvidenceCurrentStep(admin);
		testCase.addTextEvidenceCurrentStep(pasoOmitido);
			
		//***************************Paso 3*****************************************************************************

		addStep("Ingresar en las siguientes opciones para consultar el log del microservicio generado por las transacciones");
	
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el modo \"Administrator\" e ingresar en las siguientes opciones \"Workloads->Pods->Server QA->Pod ORDRSP\" y Seleccionar \"Logs\"");
		testCase.addTextEvidenceCurrentStep(pasoOmitido);

		//***************************Paso 4*****************************************************************************

		addStep("Configurar request en Postman para simular el envio del mensaje ORDRSP por parte de Grupo Modelo para una Orden de compra de Cedis con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton \"Send\"");
					
		testCase.addBoldTextEvidenceCurrentStep("Campos a modificar para identificar la OC de Cedis en estatus Aceptado");
		testCase.addBoldTextEvidenceCurrentStep(header);
		testCase.addBoldTextEvidenceCurrentStep("message_format: W");
		testCase.addBoldTextEvidenceCurrentStep(numerodeorden);
		testCase.addBoldTextEvidenceCurrentStep("receiver_store: Cedis");
		testCase.addBoldTextEvidenceCurrentStep(status);
		testCase.addBoldTextEvidenceCurrentStep(details);
		testCase.addBoldTextEvidenceCurrentStep(position);
		testCase.addBoldTextEvidenceCurrentStep(reason);
		testCase.addBoldTextEvidenceCurrentStep("Body para OC de Cedis en estatus Aceptado: ");
						
		AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret)); 
			
		code= oi.orderResponse (baseURL_order_response, getPath_order_response, w_messageNumber, w_purchaseDate, w_messageFormat, w_purchaseOrder, w_receiptDeadline, w_positionCount, w_requesterSupplier, w_receiverStore, w_poStatus, AccessToken, apikey, w_details, "", "");
		System.out.println(codestring + code);
				
		//Validación del codigo de respuesta de la solicitud de token
		if (AccessToken.equals(errorToken)) {
			validacionCodigoResp=true;
					System.out.println(errorToken);
		}else {
			validacionCodigoResp=false;
			System.out.println(obtuvoToken + AccessToken);
				}
		assertFalse("No se pudo obtener el token. ", validacionCodigoResp);

		//Validación del codigo de respuesta de la recepcion del mensaje ORDRSP
		if (code.equals("X200000")) {
			validacionCodigoResp=false;
			testCase.addTextEvidenceCurrentStep(obtuvoCodigo + code + ordrspExito);
		 }else {
			validacionCodigoResp=true;
			testCase.addTextEvidenceCurrentStep(obtuvoCodigo + code + ordrspNoExito);
		 }
			assertFalse(obtuvoCodigo+ code + ordrspNoExito, validacionCodigoResp);


		//***************************Paso 5*****************************************************************************

		addStep(logMicro);
		
		testCase.addBoldTextEvidenceCurrentStep(admin);
		testCase.addTextEvidenceCurrentStep(pasoOmitido);


		//***************************Paso 6*****************************************************************************

		addStep("Configurar request en Postman para simular el envio del mensaje ORDRSP por parte de Grupo Modelo para una Orden de compra de Cedis con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton \"Send\"");
							
		testCase.addBoldTextEvidenceCurrentStep("Campos a modificar para identificar la OC de Punto de Transbordo en estatus Aceptado");
		testCase.addBoldTextEvidenceCurrentStep(header);
		testCase.addBoldTextEvidenceCurrentStep("message_format: W");
		testCase.addBoldTextEvidenceCurrentStep(numerodeorden);
		testCase.addBoldTextEvidenceCurrentStep("receiver_store: Cedis");
		testCase.addBoldTextEvidenceCurrentStep("transshipment_code: Código de punto de transbordo PT");
		testCase.addBoldTextEvidenceCurrentStep("transshipment_description Descripción de punto de transbordo PT");
		testCase.addBoldTextEvidenceCurrentStep(status);
		testCase.addBoldTextEvidenceCurrentStep(details);
		testCase.addBoldTextEvidenceCurrentStep(position);
		testCase.addBoldTextEvidenceCurrentStep(reason);
		testCase.addBoldTextEvidenceCurrentStep("Body para OC de Punto de transbordo en estatus Aceptado: ");
								
		AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret)); 
					
		code= oi.orderResponse (baseURL_order_response, getPath_order_response, pt_messageNumber, pt_purchaseDate, pt_messageFormat, pt_purchaseOrder, pt_receiptDeadline, pt_positionCount, pt_requesterSupplier, pt_receiverStore, pt_poStatus, AccessToken, apikey, pt_details, pt_transshipmentCode, pt_transshipmentDescription);
		System.out.println(codestring + code);
						
		//Validación del codigo de respuesta de la solicitud de token
		if (AccessToken.equals(errorToken)) {
			validacionCodigoResp=true;
			System.out.println(errorToken);
			}else {
			validacionCodigoResp=false;
			System.out.println(obtuvoToken + AccessToken);
						}
			assertFalse("No se pudo obtener el token", validacionCodigoResp);

			//Validación del codigo de respuesta de la recepcion del mensaje ORDRSP
			if (code.equals("X200000")) {
			validacionCodigoResp=false;
			testCase.addTextEvidenceCurrentStep(obtuvoCodigo + code + ordrspExito);
			}else {
			validacionCodigoResp=true;
			testCase.addTextEvidenceCurrentStep(obtuvoCodigo + code + ordrspNoExito);
			}
			assertFalse(obtuvoCodigo + code + ordrspNoExito, validacionCodigoResp);
			

		//***************************Paso 7*****************************************************************************
		
		addStep(logMicro);
				
		testCase.addBoldTextEvidenceCurrentStep(admin);
		testCase.addTextEvidenceCurrentStep(pasoOmitido);
				
		//***************************Paso 8*****************************************************************************

		addStep("Abrir el manejador de base de datos postgresql, Ingresar usuario, contraseña, y la conexión indicada en los datos de prueba, Dar clic a conectar ");
		
		testCase.addTextEvidenceCurrentStep("Conexión exitosa a la base de datos");	
		testCase.addTextEvidenceCurrentStep(oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN.substring(0, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN.lastIndexOf('&')));
		//***************************Paso 9*****************************************************************************
		addStep("Consutar la tabla ORDRSP.HEADER con el campo OrderId \r\n"
		+ select_Header);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje ORDRSP");
				
		validaResultBD = oi.queryBDPostgresql(select_Header, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println (validaString + validaResultBD);
		assertTrue (validaResultBD, noBd);
		
		//***************************Paso 10*****************************************************************************
		addStep("Consutar la tabla ORDRSP.DETAIL con el campo OrderId \r\n"
		+ select_Details);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje ORDRSP.");
		
		validaResultBD = oi.queryBDPostgresql(select_Details, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println (validaString + validaResultBD);
		assertTrue (validaResultBD,noBd);

		//***************************Paso 11*****************************************************************************
		addStep("Consutar la tabla ORDRSP.TRANSACTION con el campo OrderId \r\n"
		+ select_Transaction);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se inserta un registro de transaccion con los datos de fecha y hora, id de transacción, Status de transaccion, Detalle");
				
		validaResultBD = oi.queryBDPostgresql(select_Transaction, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println (validaString + validaResultBD);
		assertTrue (validaResultBD, noBd);

		
		}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return "Realizar la creacion del mensaje ORDRSP para una Orden de compra de Tienda, Cedis y Punto de trasbordo con estatus Aceptado que envia Grupo Modelo a OXXO a travez de Terra en formato JSON esta informacion se almacena en las siguientes tablas ordrsp.headers y ordrsp.detail, ordrsp.transaction. y validar el registro en el log de microservicios";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_RecibirYAlmacenarInformacionDiariaDeORDRSP";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}