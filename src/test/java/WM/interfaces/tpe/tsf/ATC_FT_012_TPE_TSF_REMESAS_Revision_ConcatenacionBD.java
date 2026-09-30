package WM.interfaces.tpe.tsf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_012_TPE_TSF_REMESAS_Revision_ConcatenacionBD extends BaseExecution {
	public Document xmlDocument;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_TPE_TSF_REMESAS_Revision_ConcatenacionBD_test(HashMap<String, String> data) throws Exception {

		/**
		 * Proyecto: Limite Dispersion Remesas
		 * CP: MTC-FT-035-Revision Concatenacion BD Western Union
		 *     MTC-FT-036-Revision Concatenacion BD Money Gram
		 *     MTC-FT-037-Revision Concatenacion BD TransNetwork
		 *     
		 */
		
		/*
		 * Utilerias
		 *********************************************************************/
	
		SQLUtil dbFCWMREDQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREDQA, GlobalVariables.DB_USER_FCWMREDQA,GlobalVariables.DB_PASSWORD_FCWMREDQA);		
		SQLUtil dbFCTPEQA2 = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		/*
		 * Variables
		 *********************************************************************/
		
	
		String consulta_TPE_FR_TRANS = "select*from (select FOLIO,ENTITY,CREATION_DATE,WM_DESC,PAN \r\n"
				+ "from TPEREP.TPE_FR_TRANSACTION \r\n"
				+ "WHERE ENTITY IN ('"+data.get("entity")+"')\r\n"
				+ "AND CREATION_DATE > =TO_DATE('"+ data.get("date")+"' ,'dd/mm/yyyy')\r\n"
				+ "AND (WM_DESC LIKE 'Transacci%')\r\n"
				+ "AND PAN IS NOT NULL \r\n"
				+ "order by creation_date desc)where rownum =1";
		
		
		String consulta_TPE_FR_TRANSXML = "select*from (select ORIG_DATA_ELEMS \r\n"
				+ "from TPEREP.TPE_FR_TRANSACTION \r\n"
				+ "WHERE ENTITY IN ('"+data.get("entity")+"')\r\n"
				+ "AND CREATION_DATE > =TO_DATE('"+ data.get("date")+"' ,'dd/mm/yyyy')\r\n"
				+ "AND (WM_DESC LIKE 'Transacci%')\r\n"
				+ "AND PAN IS NOT NULL \r\n"
				+ "order by creation_date desc)where rownum =1";
		
		String tramas = "select folio_tpe, trama_request \r\n"
				+ "from TPEUSER.TPE_TSF_TRAMAS\r\n"
				+ "where folio_tpe= '%s'";
		
		
		
		testCase.setTest_Description(data.get("id") + data.get("descripcion"));
		testCase.setProject_Name("Limite Dispersion Remesas");
		testCase.setPrerequisites("prerequisitos");
	
			
		
		// Paso 1 ****************************************************
		addStep("Realizar conexion a la BD FCWMREDQA");
		
		
		boolean conexiondbFCWMREDQA = true;

		testCase.addTextEvidenceCurrentStep("Conexion: dbFCWMREDQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREDQA);

		assertTrue(conexiondbFCWMREDQA, "La conexion no fue exitosa");
	
		
		// Paso 2 ****************************************************
		
		addStep("Realizar consulta modificando la fecha del retiro de Remesa");
	
		System.out.println(GlobalVariables.DB_HOST_FCWMREDQA);
		System.out.println("Consulta 1: \r\n"+consulta_TPE_FR_TRANS + "\r\n");
		System.out.println("Consulta 2: \r\n"+consulta_TPE_FR_TRANSXML+ "\r\n");
		
		SQLResult consulta_TPE_FR_TRANS_r = executeQuery(dbFCWMREDQA, consulta_TPE_FR_TRANS);
		SQLResult consulta_TPE_FR_TRANSXML_r = executeQuery(dbFCWMREDQA, consulta_TPE_FR_TRANSXML);
		
		String XML_result = null;
		String PAN = null;
		String FOLIO = null;
		boolean validaTPE_FR_TRANS = consulta_TPE_FR_TRANS_r.isEmpty();
		
		

		if (!validaTPE_FR_TRANS) {
			
			PAN = consulta_TPE_FR_TRANS_r.getData(0, "PAN");
			System.out.println("PAN: "+PAN + "\r\n");
			XML_result = consulta_TPE_FR_TRANSXML_r.getData(0, "ORIG_DATA_ELEMS");
			System.out.println("Campo ORIG_DATA_ELEMS: \r\n"+XML_result +"\r\n");
			FOLIO = consulta_TPE_FR_TRANS_r.getData(0, "FOLIO");
			System.out.println("FOLIO: \r\n"+FOLIO +"\r\n");
			
			
		}
		
		testCase.addQueryEvidenceCurrentStep(consulta_TPE_FR_TRANS_r);
		testCase.addQueryEvidenceCurrentStep(consulta_TPE_FR_TRANSXML_r);
		
		System.out.println(validaTPE_FR_TRANS);
		
		
		assertFalse(validaTPE_FR_TRANS, "No se encontraron registros en la consulta");
		
		// Paso 3 ****************************************************
		
		addStep("Se obtiene el XML");
		
		
		testCase.addCodeEvidenceCurrentStep(XML_result);
		
		assertTrue(XML_result!=null && !XML_result.isEmpty(), "El campo ORIG_DATA_ELEMS se encuentra vacio");
		
	    //Paso 4 ******************************************************
		
		addStep("Revisar el campo \"calleNum=\" en XML ");
		
		//Obtiene el campo entre los dos delimitadores
		String calleNum = getDataByDelimiters(XML_result, "calleNum=\"", "\"");
		
		testCase.addTextEvidenceCurrentStep("calleNum= " + calleNum);
		
		System.out.println("calleNum: " + calleNum);
		
		//Valida que el dato calleNum  contenga un número al final separado por un espacio, y antes de ese número exista algún texto
		String[] calleNumSplit = calleNum.split(" ");
		
		boolean valCantidadTexto = calleNumSplit.length > 1;
		
		assertTrue( valCantidadTexto,"El campo CalleNum no contiene calle y número");
		
		String num = calleNumSplit[calleNumSplit.length-1];
		System.out.println("num: " + num);
		boolean valNumero =  isNumeric(num);
		
		assertTrue( valNumero,"El numero del campo calleNum contiene letras o simbolos");
		
		//**************************************************************************************************
		
		addStep("Relizar consulta en TPE_TSF_TRAMAS, en el campo REQUEST revisar que el numero de remesador corresponda al campo "
				+ "PAN de la consulta retiro de remesas (paso 3)");
		String FormatTramas = String.format(tramas, FOLIO);
		
		SQLResult Tramas_r = executeQuery(dbFCTPEQA2,FormatTramas);
		
		boolean validaTramas = Tramas_r.isEmpty();
		String Trama_request= null;
		
		testCase.addQueryEvidenceCurrentStep(Tramas_r);
		
		if(!validaTramas) {
			
			Trama_request = Tramas_r.getData(0, "TRAMA_REQUEST");
			System.out.println(Trama_request);
		}
		
		
		assertFalse(validaTramas, "No se encontraron registros en la tabla TPE_TSF_TRAMAS");
		
	//******************************************************************************************************************
		addStep("Validar campo TRAMA_REQUEST con numero de remesador");
		
		testCase.addCodeEvidenceCurrentStep("TRAMA_REQUEST: \r\n"+Trama_request);
		
		String startDelimiter = null;
		String endDelimiter = null;
		
		if (data.get("entity").equals("WU")) {
			startDelimiter = "<id_number>";
			endDelimiter = "</id_number>";
		} else if (data.get("entity").equals("MGI")) {
			startDelimiter = "<firstIDnum>";
			endDelimiter = "</firstIDnum>";
		} else if (data.get("entity").equals("TNC")) {
			startDelimiter = "<tns:idNumber>";
			endDelimiter = "</tns:idNumber>";
		}
		
		String idTrn = getDataByDelimiters(Trama_request, startDelimiter, endDelimiter);
		System.out.println("idTrn: " + idTrn);
		
		//Si el id no se encontró con la primer etiqueta, se busca con la segunda
		if (idTrn == null) {
			System.out.println("El elemento " + startDelimiter + " no fue encontrado en el xml, se busca por el elemento receiverPhotoIDnumber");
			startDelimiter = "<ac:receiverPhotoIdNumber>";
			endDelimiter = "</ac:receiverPhotoIdNumber>";
		}
		
		idTrn = getDataByDelimiters(Trama_request, startDelimiter, endDelimiter);
		System.out.println("idTrn: " + idTrn);
		
		testCase.addTextEvidenceCurrentStep("Id remesa: "+ idTrn);
		testCase.addTextEvidenceCurrentStep("PAN: "+ PAN);
		
		assertTrue(Trama_request!=null && !Trama_request.isEmpty(), "El campo TRAMA_RESQUET se encuentra vacio");
		
		assertEquals(PAN, idTrn, "El numero de remesa no coincide con el campo PAN");
		
		//*********************************************************************************************************
		
		
	/*	String [] arr = XML_result.split("calleNum=\"");
		String [] arr2 = arr[1].split("\"");
		String datoCalle = arr2[0];
		System.out.print("Dato calleNum: "+ datoCalle);*/
		
		// Obtener variables de la respuesta
		
	/*	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(XML_result))); 
		Element formato = document.getDocumentElement();
		
		testCase.addTextEvidenceCurrentStep(XML_result);

	
		// Paso 4 ****************************************************
		addStep("Obtener el dato callNum");
		
		String calleNum = getValueTag("calleNum",formato);
		testCase.addTextEvidenceCurrentStep("calleNum: "+calleNum);
		System.out.println("calleNum: " + calleNum);
		
		String nombre = getValueTag("nombre",formato);
		System.out.println("nombre: " + nombre);
	
		// Paso 5 ****************************************************
		
		addStep("Se compara el campo 'calleNum' del XML ");
		
		/**
		 * Al revisar el XML en el campo "calleNum" se debera
		 *  de observar que los datos de Calle y 
		 * Numero Exterior se deberán de visualizar en el mismo campo, ejemplo: en pantalla se agrega
		 * Calle: Colon, Numero: 123, en pantalla se deberá de visualizar, CalleNum: Colon 123
		 */
		
	/*	boolean validateRequest = calleNum.equals(data.get("calleNum"));
		if(validateRequest) {
			testCase.addTextEvidenceCurrentStep("El dato CalleNum del XMLConincide");
		}
		
		testCase.addTextEvidenceCurrentStep(calleNum);
		testCase.addTextEvidenceCurrentStep(XML_result);
		
		System.out.println("calleNum: " + calleNum);
		System.out.println(validateRequest);
		assertTrue(validateRequest, "el calleNum no es correcto" );
		
		// Paso 6 ****************************************************
		
		addStep("Revisar la columna PAN, obtener el dato que se refleja, debe de coincidir con el numero de identificación ingresado en el cobro de remesa");
		
		System.out.println("PAN: "+ PAN);
		boolean ValidPAN = PAN.equals(data.get("PAN"));
		
		if(ValidPAN) {
			
			testCase.addBoldTextEvidenceCurrentStep("Se valido que el dato PAN es igual a: "+data.get("PAN"));
		}
		
		testCase.addQueryEvidenceCurrentStep(consulta_TPE_FR_TRANS_r);
		System.out.println(ValidPAN);
		assertTrue(ValidPAN, "el Dato PAN no es correcto" );
	}
	
	private String getValueTag(String tagName, Element element) { 
		  String res = "";
		  NodeList list = element.getElementsByTagName(tagName);
		    if (list != null && list.getLength() > 0) {
		    NodeList subList = list.item(0).getChildNodes();
		        if (subList != null && subList.getLength() > 0) { 
		        res = subList.item(0).getNodeValue();
		        System.out.println(res);
		        }
		    } 
		    if(!res.equals("")){
		       res = "Etiqueta no encontrada";
		       } 
		    return res;*/
		}
	
	public static String getDataByDelimiters(String s, String startDelimiter,  String endDelimiter) {
		try {
			s = s.substring(s.indexOf(startDelimiter) + startDelimiter.length());
			
			//System.out.println(s);
			
			s = s.substring(0, s.indexOf(endDelimiter));
			
			return s;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo automatizacion";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
