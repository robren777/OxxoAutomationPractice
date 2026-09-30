package WM.apis.orders_inbound.despatch_advice;

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
		iterationPath = "I25086-Proceso End to End\\Sprints MVP2\\Sprint 10",
		tags = "",
		userStoryId = 217667

)

/**
DESCRIPCIÓN:
Validar la recepcion y almacenamiento del mensaje DESADV que envia Grupo Modelo el cual llega a WebMethods (API Gateway) a travez de TERRA en formato JSON, esta informacion se almacena en las siguientes tablas desadv.headers, desadv.detail y desadv.transaction de PostgreSQL, y genera registros en el log.
NOMBRE CASOS DE PRUEBA MANUAL QUE ABARCA: MTC_FT_009_Recibir y almacenar informacion diaria de DESADV
PREREQUISITOS:
1.- Metodo: POST
2.- URL Microservicio:
3.- Configurar Seccion "Authorization"
Seleccionar Bearer Token y agregar la variable {{accessTokenUX}}
4.- Configurar seccion "Headers"
Tracking-Id -> "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66"
Channel-Id -> "TERRA"
Country-Code -> " MX"
Language -> "SPA"
User-Agent -> "TerraApp/1.0"
x-Gateway-APIKey -> "{{apiKey}}"
Content-Type -> "application/json"
AcceptAccept -> "application/json"
INSUMOS:
1.- URL del endpoint para recibir mensajes de DESADV.
2.- Contar con acceso a la BD terra_oxxo_rspn (172.26.53.100).
3.- Contar con acceso a la consola de linux donde se genera el registro de logs del microservicio de Open Shift para mensajes DESADV.
@author	Sergio Robles Ramos
@date   22/12/2025
*/
public class ATC_FT_009_RecibirYAlmacenarInformacionDiariaDeDESADV extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_RecibirYAlmacenarInformacionDiariaDeDESADV_test (HashMap<String, String> data) throws Exception {
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
		String getPathToken = data.get("getPathToken");
		String baseURLTokenTestdata = data.get("baseURLTokenTestdata");
		String clientId = data.get("clientId");
		String clientSecret = data.get("clientSecret");
		String baseURL_despatch_advice = data.get("baseURL_despatch_advice");
		String getPath_despatch_advice = data.get("getPath_despatch_advice");
		String apikey = data.get("APIKey");
		//tienda
		String s_messageNumber = data.get("s_messageNumber");
		String s_purchaseOrder = data.get("s_purchaseOrder");
		String s_deliveryNumber = data.get("s_deliveryNumber");
		String s_messageFormat = data.get("s_messageFormat");
		String s_appointmentNumber = data.get("s_appointmentNumber");
		String s_region = data.get("s_region");
		String s_hub = data.get("s_hub");
		String s_routeNumber = data.get("s_routeNumber");
		String s_deliveryDate = data.get("s_deliveryDate");
		String s_uuid = data.get("s_uuid");
		String s_bill = data.get("s_bill");
		String s_details = data.get("s_details");
		//cedis
		String w_messageNumber = data.get("w_messageNumber");
		String w_purchaseOrder = data.get("w_purchaseOrder");
		String w_deliveryNumber = data.get("w_deliveryNumber");
		String w_messageFormat = data.get("w_messageFormat");
		String w_appointmentNumber = data.get("w_appointmentNumber");
		String w_region = data.get("w_region");
		String w_hub = data.get("w_hub");
		String w_routeNumber = data.get("w_routeNumber");
		String w_deliveryDate = data.get("w_deliveryDate");
		String w_uuid = data.get("w_uuid");
		String w_bill = data.get("w_bill");
		String w_details = data.get("w_details");
		//punto de transbordo
		String pt_messageNumber = data.get("pt_messageNumber");
		String pt_purchaseOrder = data.get("pt_purchaseOrder");
		String pt_deliveryNumber = data.get("pt_deliveryNumber");
		String pt_messageFormat = data.get("pt_messageFormat");
		String pt_appointmentNumber = data.get("pt_appointmentNumber");
		String pt_region = data.get("pt_region");
		String pt_hub = data.get("pt_hub");
		String pt_routeNumber = data.get("pt_routeNumber");
		String pt_deliveryDate = data.get("pt_deliveryDate");
		String pt_uuid = data.get("pt_uuid");
		String pt_bill = data.get("pt_bill");
		String pt_details = data.get("pt_details");
		//Selects para consultar información del registro para Tienda en BD Postgresql
		String select_Header="Select * from desadv.desadv_header where purchase_order in ("+s_purchaseOrder+", "+w_purchaseOrder+", "+pt_purchaseOrder+")";
		String select_Details="Select * from desadv.desadv_detail where purchase_order in ("+s_purchaseOrder+", "+w_purchaseOrder+", "+pt_purchaseOrder+")";
		String select_Transaction="Select * from desadv.desadv_transaction where purchase_order in ("+s_purchaseOrder+", "+w_purchaseOrder+", "+pt_purchaseOrder+")";

		PasswordUtil passwordUtil = new PasswordUtil();
		boolean validacionCodigoResp;
		boolean validaResultBD;
		//***************************Paso 1*****************************************************************************
				addStep("Configurar request en Postman para simular el envio del mensaje desadv por parte de Grupo Modelo para una Orden de compra de Tienda con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton Send");
					
				testCase.addBoldTextEvidenceCurrentStep("Campos a modificar para identificar la OC de Tienda en estatus Aceptado");
				testCase.addBoldTextEvidenceCurrentStep(header);
				testCase.addBoldTextEvidenceCurrentStep("message_format: S");
				testCase.addBoldTextEvidenceCurrentStep(numerodeorden);
				testCase.addBoldTextEvidenceCurrentStep("receiver_store: Tienda Retek");
				testCase.addBoldTextEvidenceCurrentStep(status);
				testCase.addBoldTextEvidenceCurrentStep(details);
				testCase.addBoldTextEvidenceCurrentStep(position);
				testCase.addBoldTextEvidenceCurrentStep(reason);
				testCase.addBoldTextEvidenceCurrentStep("Body para OC de Tienda en estatus Aceptado: ");
						
				String AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret)); 
				
				String code= oi.despatchAdvice (baseURL_despatch_advice, getPath_despatch_advice, s_messageNumber, s_purchaseOrder,  s_deliveryNumber,  s_messageFormat,  s_appointmentNumber, s_region, s_hub, s_routeNumber, s_deliveryDate, s_uuid, s_bill, AccessToken, apikey, s_details);
				System.out.println("code: " + code);
				
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

				
				//***************************Paso 2*****************************************************************************
					addStep(logMicro);
					testCase.addBoldTextEvidenceCurrentStep(admin);
					testCase.addTextEvidenceCurrentStep(pasoOmitido);
				//***************************Paso 3*****************************************************************************
				addStep("Ingresar en las siguientes opciones para consultar el log del microservicio generado por las transacciones");
			
				testCase.addBoldTextEvidenceCurrentStep("Seleccionar el modo \"Administrator\" e ingresar en las siguientes opciones \"Workloads->Pods->Server QA->Pod desadv\" y Seleccionar \"Logs\"");
				testCase.addTextEvidenceCurrentStep("Paso Omitido por el doble factor de autenticación que usa openshift. Se valido con la Lider Automatizador durante la revision del SELECTOR y se concluyo en conjunto con la Lider de Calidad que la automatización del caso de pruba tiene mayor beneficio para el proyecto o futuros proyectos.");
					
				//***************************Paso 2*****************************************************************************
				addStep("Configurar request en Postman para simular el envio del mensaje desadv por parte de Grupo Modelo para una Orden de compra de cedis con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton Send");
					
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
				
				code= oi.despatchAdvice (baseURL_despatch_advice, getPath_despatch_advice, w_messageNumber, w_purchaseOrder,  w_deliveryNumber,  w_messageFormat,  w_appointmentNumber, w_region, w_hub, w_routeNumber, w_deliveryDate, w_uuid, w_bill, AccessToken, apikey, w_details);
				System.out.println("code: " + code);
				
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

				//***************************Paso 2*****************************************************************************
					addStep(logMicro);
					testCase.addBoldTextEvidenceCurrentStep(admin);
					testCase.addTextEvidenceCurrentStep(pasoOmitido);	
				//***************************Paso 3*****************************************************************************
				addStep("Ingresar en las siguientes opciones para consultar el log del microservicio generado por las transacciones");
			
				testCase.addBoldTextEvidenceCurrentStep("Seleccionar el modo \"Administrator\" e ingresar en las siguientes opciones \"Workloads->Pods->Server QA->Pod desadv\" y Seleccionar \"Logs\"");
				testCase.addTextEvidenceCurrentStep("Paso Omitido por el doble factor de autenticación que usa openshift. Se valido con la Lider Automatizador durante la revision del SELECTOR y se concluyo en conjunto con la Lider de Calidad que la automatización del caso de pruba tiene mayor beneficio para el proyecto o futuros proyectos.");

				//***************************Paso 2*****************************************************************************
				addStep("Configurar request en Postman para simular el envio del mensaje desadv por parte de Grupo Modelo para una Orden de compra de cedis con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton Send");
					
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
				
				code= oi.despatchAdvice (baseURL_despatch_advice, getPath_despatch_advice, pt_messageNumber, pt_purchaseOrder,  pt_deliveryNumber,  pt_messageFormat,  pt_appointmentNumber, pt_region, pt_hub, pt_routeNumber, pt_deliveryDate, pt_uuid, pt_bill, AccessToken, apikey, pt_details);
				System.out.println("code: " + code);
				
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
			
				testCase.addBoldTextEvidenceCurrentStep("Seleccionar el modo \"Administrator\" e ingresar en las siguientes opciones \"Workloads->Pods->Server QA->Pod desadv\" y Seleccionar \"Logs\"");
				testCase.addTextEvidenceCurrentStep("Paso Omitido por el doble factor de autenticación que usa openshift. Se valido con la Lider Automatizador durante la revision del SELECTOR y se concluyo en conjunto con la Lider de Calidad que la automatización del caso de pruba tiene mayor beneficio para el proyecto o futuros proyectos.");
					
				//***************************Paso 4*****************************************************************************

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
		return "Validar la recepcion y almacenamiento del mensaje DESADV que envia Grupo Modelo el cual llega a WebMethods (API Gateway) a travez de TERRA en formato JSON, esta informacion se almacena en las siguientes tablas desadv.headers, desadv.detail y desadv.transaction de PostgreSQL, y genera registros en el log.";
	}

	@Override
	public String setTestDesigner() {
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_RecibirYAlmacenarInformacionDiariaDeDESADV";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
