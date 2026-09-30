package WM.om;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import WM.util.GlobalVariables;
import exceptions.ReempRequestException;
import modelo.TestCase;
import utils.sql.SQLUtil;

public class TPE_FCP_FRQ {
	
	public String requestRTP = "http://%s/invoke/TPE.RTP.Pub/request?xmlIn=";

	HashMap<String, String> data;
	TestCase testCase;
	SQLUtil db;
	
	
	public TPE_FCP_FRQ(HashMap<String, String> data, TestCase testCase, SQLUtil  db) {
		
		super();
		this.data = data;
		this.testCase = testCase;
		this.db = db;
		
	}
	
	public String generacionFolioTransaccion() throws ReempRequestException, ClientProtocolException, IOException{
		
		String url = String.format(requestRTP, GlobalVariables.HOST_RTP);
		
		OxxoRequest or = new OxxoRequest(url);
		
		or.addHeader("application", data.get("application"));
		or.addHeader("entity", data.get("entity"));
		or.addHeader("operation", data.get("operation01"));
		or.addHeader("source", data.get("source"));
		or.addHeader("plaza", data.get("plaza"));
		or.addHeader("tienda", data.get("tienda"));
		or.addHeader("caja", data.get("caja"));

		String request = or.getRequestString();
		
		System.out.println(request);
		testCase.addTextEvidenceCurrentStep(request);
		
		String response = or.executeRequest();
		
		testCase.addTextEvidenceCurrentStep(response);
		
		return response;
		
		
		
	}
}