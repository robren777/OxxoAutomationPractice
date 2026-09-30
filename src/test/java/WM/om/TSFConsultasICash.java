package WM.om;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import modelo.TestCase;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class TSFConsultasICash {
	TestCase testCase;
	SQLUtil db;
	
	public TSFConsultasICash(TestCase testCase, SQLUtil db){
		super();
		this.testCase = testCase;
		this.db = db;
	}
	

/*
* Concatena valores y ejecuta una consulta (QRY01, TRN01, etc.)
* @author Antonio Gonzalez
* @param baseUrl toma la url base del data provider
* @param path toma el path del data provider
* @param xml toma el xml (QRY01, TRN01, etc.) dependiendo de la consulta que se vaya a realizar
* @return regresa un xml con la respuesta de la consulta ejecutada
* @date 7/Abril/2025
*/

	public ArrayList<String> ejecutarConsulta(String baseUrl, String path, String xml) {
		Response getRespuesta;
		int statusCode = 0;
		String getBodyResponse = "";
		ArrayList<String> dataResponse = new ArrayList<String>();
		try {
			testCase.addBoldTextEvidenceCurrentStep("- request:\n");
			testCase.addCodeEvidenceCurrentStep("\n" + baseUrl + path + xml);
			RestAssured.baseURI = baseUrl;
			getRespuesta = RestAssured
			.given()
				.relaxedHTTPSValidation()
				.contentType(ContentType.JSON)
			.when()
				.get(path + xml); 
			
			statusCode = getRespuesta.getStatusCode();
			testCase.addBoldTextEvidenceCurrentStep("- statusCode:\n");
			testCase.addCodeEvidenceCurrentStep("\t" + statusCode);
			dataResponse.add(String.valueOf(statusCode));
			
			getBodyResponse = getRespuesta.asPrettyString();
			testCase.addBoldTextEvidenceCurrentStep("- bodyResponse:\n");
			testCase.addCodeEvidenceCurrentStep("\n" + getBodyResponse);
			dataResponse.add(getBodyResponse);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al realizar la consulta");
		}
		return dataResponse;
	}
	
	/*
	* Carga la respuesta y extrae valores especificos de un QRY01
	* @author Antonio Gonzalez
	* @param response carga los valores de la respuesta
	* @return regresa la respuesta y datos especificos que se extraen de la respuesta
	* @date 7/Abril/2025
	*/	
	
	public ArrayList<String> obtenerDataResponseQRY01(String response){
		ArrayList<String> dataResponse = new ArrayList<String>();
		try {
			XmlPath xmlPath = new XmlPath(response);
			dataResponse.add(xmlPath.getString("recTPEDoc.header.@adDate"));
			dataResponse.add(xmlPath.getString("recTPEDoc.header.@pvDate"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.@transferNo"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.@transferID"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.@originatingAmount"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.@amount"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.@agentCheckAmount"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.@originalSendFee"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.client.receiver.@nombre"));
			dataResponse.add(xmlPath.getString("recTPEDoc.response.transfer.client.receiver.@apellidoPaterno"));
			
			testCase.addBoldTextEvidenceCurrentStep("- valores de QRY01 necesarios para la consulta TRN02:\n");
			testCase.addCodeEvidenceCurrentStep("\n" + "adDate: " + dataResponse.get(0) + '\n'
					+ "pvDate: " + dataResponse.get(1) + '\n'
					+ "transferNo: " + dataResponse.get(2) + '\n'
			        + "transferID: " + dataResponse.get(3) + '\n'
			        + "dataResponse: " + dataResponse.get(4) + '\n'
			        + "amount: " + dataResponse.get(5) + '\n'
			        + "agentCheckAmount: " + dataResponse.get(6) + '\n'
			        + "originalSendFee: " + dataResponse.get(7) + '\n'
			        + "nombre: " + dataResponse.get(8) + '\n'
			        + "apellidoPaterno: " + dataResponse.get(9));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al conseguir los datos del QYR01");
		}
		return dataResponse;
	}
	
	/*
	* Carga la respuesta y extrae valores especificos de un TRN01
	* @author Antonio Gonzalez
	* @param response carga los valores de la respuesta
	* @return regresa la respuesta y datos especificos que se extraen de la respuesta
	* @date 7/Abril/2025
	*/	
	
	public ArrayList<String> obtenerDataResponseTRN01(String response){
		ArrayList<String> dataResponse = new ArrayList<String>();
		try {
			XmlPath xmlPath2 = new XmlPath(response);
			dataResponse.add(xmlPath2.getString("recTPEDoc.header.@folio"));
			dataResponse.add(xmlPath2.getString("recTPEDoc.header.@creationDate"));
			
			testCase.addBoldTextEvidenceCurrentStep("- valores de TRN01 necesarios para la consulta TRN02:\n");
			testCase.addCodeEvidenceCurrentStep("\n" + "folio: " + dataResponse.get(0) + '\n'
					+ "creationDate: " + dataResponse.get(1));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al conseguir los datos del TRN01");
		}
		return dataResponse;
	}
	
	/*
	* Trae los valores extraidos del request QRY01 y del TRN01 y los inserta al request del TRN02
	* @author Antonio Gonzalez
	* @param xml carga el xml asignado (TRN02)
	* @param dataQRY01 carga los valores extraidos de la respuesta QRY01 
	* @param dataTRN01 carga los valores extraidos de la respuesta TRN01
	* @return regresa el xml con las valores extraidos de dataQRY01 y dataTRN01 ya insertados en TRN02
	* @date 7/Abril/2025
	*/	
	
	public String obtenerBodyTRN02(String xml, ArrayList<String> dataQRY01, ArrayList<String> dataTRN01) {
		String bodyTRN02 = "";
		try {
			bodyTRN02 = String.format(xml, dataTRN01.get(0), dataQRY01.get(0), dataQRY01.get(1), dataTRN01.get(1), dataQRY01.get(2)
					, dataQRY01.get(3), dataQRY01.get(4), dataQRY01.get(5), dataQRY01.get(6), dataQRY01.get(7), dataQRY01.get(8)
					, dataQRY01.get(9));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al obtener el body TRN02");
		}
		return bodyTRN02;
	}
	
	/*
	* Trae los valores extraidos del request QRY01 y del TRN01 y los inserta al request del TRN03
	* @author Antonio Gonzalez
	* @param xml carga el xml asignado (TRN03)
	* @param dataQRY01 carga los valores extraidos de la respuesta QRY01 
	* @param dataTRN01 carga los valores extraidos de la respuesta TRN01
	* @return regresa el xml con las valores extraidos de dataQRY01 y dataTRN01 ya insertados en TRN03
	* @date 7/Abril/2025
	*/
	
	public String obtenerBodyTRN03(String xml, ArrayList<String> dataQRY01, ArrayList<String> dataTRN01) {
		String bodyTRN03 = "";
		try {
			bodyTRN03 = String.format(xml, dataTRN01.get(0), dataQRY01.get(0), dataQRY01.get(1), dataTRN01.get(1));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al obtener el body TRN03");
		}
		return bodyTRN03;
	}
	
	/*
	* Trae los valores extraidos del request QRY01 y del TRN01 y los inserta al request del TRN04
	* @author Antonio Gonzalez
	* @param xml carga el xml asignado (TRN04)
	* @param dataQRY01 carga los valores extraidos de la respuesta QRY01 
	* @param dataTRN01 carga los valores extraidos de la respuesta TRN01
	* @return regresa el xml con las valores extraidos de dataQRY01 y dataTRN01 ya insertados en TRN04
	* @date 7/Abril/2025
	*/
	
	public String obtenerBodyTRN04(String xml, ArrayList<String> dataQRY01, ArrayList<String> dataTRN01) {
		String bodyTRN04 = "";
		try {
			bodyTRN04 = String.format(xml, dataQRY01.get(0), dataQRY01.get(1), dataTRN01.get(0), dataTRN01.get(1));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al obtener el body TRN04");
		}
		return bodyTRN04;
	}
	
	/*
	* Realiza una consulta en la base de datos (solo para QRY01, TRN01, TRN02, TRN03)
	* @author Antonio Gonzalez
	* @param dbiCash conecta a la base de datos
	* @param response carga los valores de la respuesta
	* @param query se trae el query que se va a ejecutar en base de datos
	* @date 7/Abril/2025
	*/
	
	public Boolean validarInfoDB(SQLUtil dbiCash, ArrayList<String> response, String query) {
		String FOLIO = "";
		SQLResult result;
		Boolean estadoConsulta = false;
		try {
			byte[] xmlResponse = response.get(1).getBytes(StandardCharsets.UTF_8);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlResponse));
		    NodeList atributo = doc.getElementsByTagName("header");
		    FOLIO = atributo.item(0).getAttributes().getNamedItem("folio").getNodeValue();
		    query = String.format(query, FOLIO);

			result = dbiCash.executeQuery(query);
			boolean transaction = result.isEmpty();
			if (!transaction) {
				estadoConsulta = true;
				testCase.addQueryEvidenceCurrentStep(result);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al validar la informacion en base de datos");
		}
		return estadoConsulta;
	}
	
	/*
	* Realiza una consulta en la base de datos (TRN04)
	* @author Antonio Gonzalez
	* @param dbiCash conecta a la base de datos
	* @param response carga los valores de la respuesta
	* @param query se trae el query que se va a ejecutar en base de datos
	* @date 7/Abril/2025
	*/
	
	public Boolean validarInfoDBTRN04(SQLUtil dbiCash, ArrayList<String> response, String query) {
		String FOLIO = "";
		SQLResult result;
		Boolean estadoConsulta = false;
		try {
			byte[] xmlResponse = response.get(1).getBytes(StandardCharsets.UTF_8);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlResponse));
		    NodeList atributo = doc.getElementsByTagName("wmCode");
		    FOLIO = atributo.item(0).getAttributes().getNamedItem("folio").getNodeValue();
		    query = String.format(query, FOLIO);

			result = dbiCash.executeQuery(query);
			
			boolean transaction = result.isEmpty();
			if (!transaction) {
				estadoConsulta = true;
				testCase.addQueryEvidenceCurrentStep(result);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al validar la informacion en base de datos");
		}
		return estadoConsulta;
	}

}
