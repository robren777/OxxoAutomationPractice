package RMS.util;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import config.Constants;
import utils.ExcelUtil;

public class JobsLoader {

	/**
	 * Recorre el archivo Jobs.xlsx agregando los jobs a un HashMap
	 * @return HashMap<String, JSONObject> con nombre e informacion de los jobs.
	 * @throws IOException
	 */
	public static HashMap<String, JSONObject> loadJobs(String fileName) throws IOException {
		String filename = fileName;
		ExcelUtil excelUtil = new ExcelUtil(Constants.DATA_PROVIDER_PATH + filename);
		
		HashMap<String, JSONObject> jobs = new HashMap<>();
		
		int i = 0;
		for	(String key : excelUtil.getRow(0, 0)) {
			jobs.put(key.toLowerCase(), new JSONObject(excelUtil.getRow(0, 1).get(i)));
			i++;
		}
				
		return jobs;

	}
	

}
