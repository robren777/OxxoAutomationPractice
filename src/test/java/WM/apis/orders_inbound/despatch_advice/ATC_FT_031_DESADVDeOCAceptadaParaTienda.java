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
Realizar la creacion del mensaje DESADV para una Orden de compra de Tienda con estatus Aceptado que envia Grupo Modelo a OXXO a travez de Terra en formato JSON esta informacion se almacena en las siguientes tablas desadv.headers, desadv.detail y desadv.transaction.
Validar el registro en el log de microservicios.
NOMBRE CASOS DE PRUEBA MANUAL QUE ABARCA: MTC_FT_031_DESADV de OC Aceptada para Tienda 
PREREQUISITOS:
1.- URL del endpoint para recibir mensajes de DESADV.
2.- Contar con acceso a la BD terra_oxxo_rspn (172.26.53.100).
3.- Contar con acceso a la consola de linux donde se genera el registro de logs del microservicio de Open Shift para mensajes DESADV.
4.- Contar con OC Aceptada para Tienda
INSUMOS:
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
@author	Sergio Robles Ramos
@date   22/12/2025
*/
public class ATC_FT_031_DESADVDeOCAceptadaParaTienda extends BaseExecution{
	public String purchaseOrder;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_031_DESADVDeOCAceptadaParaTienda_test (HashMap<String, String> data) throws Exception {
		/*Utilerias (Conexiones a BD)
		*********************************************************************/
		OrdersInbound oi = new OrdersInbound(testCase);
		String getPathToken = data.get("getPathToken");
		String baseURLTokenTestdata = data.get("baseURLTokenTestdata");
		String clientId = data.get("clientId");
		String clientSecret = data.get("clientSecret");
		String baseURL_despatch_advice = data.get("baseURL_despatch_advice");
		String getPath_despatch_advice = data.get("getPath_despatch_advice");
		String messageNumber = data.get("s_messageNumber");
		purchaseOrder = data.get("s_purchaseOrder");
		String deliveryNumber = data.get("s_deliveryNumber");
		String messageFormat = data.get("s_messageFormat");
		String appointmentNumber = data.get("s_appointmentNumber");
		String region = data.get("s_region");
		String hub = data.get("s_hub");
		String routeNumber = data.get("s_routeNumber");
		String deliveryDate = data.get("s_deliveryDate");
		String uuid = data.get("s_uuid");
		String bill = data.get("s_bill");
		String apikey = data.get("APIKey");
		String details = data.get("s_details");
		
		PasswordUtil passwordUtil = new PasswordUtil();
		boolean validacionCodigoResp;
		String select_Header="Select * from desadv.desadv_header where purchase_order = "+purchaseOrder+"";
		String select_Details="Select * from desadv.desadv_detail where purchase_order = "+purchaseOrder+"";
		String select_Transaction="Select * from desadv.desadv_transaction where purchase_order = "+purchaseOrder+"";
		boolean validaResultBD;
		//***************************Paso 1*****************************************************************************
				addStep("Configurar request en Postman para simular el envio del mensaje ORDRSP por parte de Grupo Modelo para una Orden de compra de Tienda con estatus Aceptado ingresando el mensaje Json en la seccion \"Body\" considerando los siguientes campos y dar clic en el boton Send");
					
				testCase.addBoldTextEvidenceCurrentStep("Campos a modificar para identificar la OC de Tienda en estatus Aceptado");
				testCase.addBoldTextEvidenceCurrentStep("Header:");
				testCase.addBoldTextEvidenceCurrentStep("message_format: S");
				testCase.addBoldTextEvidenceCurrentStep("purchase_order: Numero de Orden");
				testCase.addBoldTextEvidenceCurrentStep("receiver_store: Tienda Retek");
				testCase.addBoldTextEvidenceCurrentStep("po_status: ACEPTADO");
				testCase.addBoldTextEvidenceCurrentStep("Details: ");
				testCase.addBoldTextEvidenceCurrentStep("position_status: 001 La posición ha sido creada");
				testCase.addBoldTextEvidenceCurrentStep("reason: NA");
				testCase.addBoldTextEvidenceCurrentStep("Body para OC de Tienda en estatus Aceptado: ");
						
				String AccessToken = oi.getAccessToken(getPathToken, baseURLTokenTestdata, passwordUtil.decryptPass(clientId), passwordUtil.decryptPass(clientSecret)); 
				
				String code= oi.despatchAdvice(baseURL_despatch_advice, getPath_despatch_advice, messageNumber, purchaseOrder,  deliveryNumber,  messageFormat,  appointmentNumber, region, hub, routeNumber, deliveryDate, uuid, bill, AccessToken, apikey, details);
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
					testCase.addTextEvidenceCurrentStep("Resultado Obtenido: Se obtiene codigo " + code + "- Mensaje DESADV recibido con éxito");
				}else {
					validacionCodigoResp=true;
					testCase.addTextEvidenceCurrentStep("Resultado Obtenido: Se obtiene el codigo " + code + "- Mensaje DESADV no recibido");
				}
				assertFalse("Resultado Obtenido: Se obtiene el codigo " + code + "- Mensaje DESADV no recibido", validacionCodigoResp);
				
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
				addStep("Consutar la tabla DESADV.HEADER con el campo OrderId \r\n"
				+ select_Header);
				testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordene que se enviaron en el archivo JSON con mensaje ORDRSP");
						
				validaResultBD = oi.queryBDPostgresql(select_Header, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
				System.out.println ("ValidaResultBD: " + validaResultBD);
				assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
				
				//***************************Paso 6*****************************************************************************
				addStep("Consutar la tabla DESADV.DETAIL con el campo OrderId \r\n"
				+ select_Details);
				testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordene que se enviaron en el archivo JSON con mensaje ORDRSP");
				
				validaResultBD = oi.queryBDPostgresql(select_Details, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
				System.out.println ("ValidaResultBD: " + validaResultBD);
				assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");

				//***************************Paso 7*****************************************************************************
				addStep("Consutar la tabla DESADV.TRANSACTION con el campo OrderId \r\n"
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
		return "Realizar la creacion del mensaje DESADV para una Orden de compra de Tienda con estatus Aceptado que envia Grupo Modelo a OXXO a travez de Terra en formato JSON esta informacion se almacena en las siguientes tablas desadv.headers, desadv.detail y desadv.transaction.\r\n"
				+ "Validar el registro en el log de microservicios.";
	}

	@Override
	public String setTestDesigner() {
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_031_DESADVDeOCAceptadaParaTienda";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
