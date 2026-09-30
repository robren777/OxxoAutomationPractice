package WorkFlows.Planogramas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Planogramas.PomPlanogramas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_041_GenerarCargaDe4ArchivosCon3MilLegosCadaUno extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Generar carga de 4 archivos con 3 mil legos Cada uno
	 * @param data --> data provider que contiene el path donde se encuentra el archivo de carga, el path del servidor donde sera cargado el archivo, 
	 * el nombre del archivo de carga y el json con la informacion del job a ejecutar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_041_GenerarCargaDe4ArchivosCon3MilLegosCadaUno(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Framework WorkFlows --> PLANOGRAMAS");
		
		/*
		 * Variables
		 */
		String host = GlobalVariables.HOST_FTP_PLANOGRAMAS;
		String user = GlobalVariables.USER_FTP_PLANOGRAMAS;
		String pass = GlobalVariables.PASS_FTP_PLANOGRAMAS;
		String port = "22";
		
		String BASE_PATH = System.getProperty("user.dir");
		String filePath = BASE_PATH+data.get("path");
		//String fileName = data.get("nombreArchivo");
		String workingDirPath = data.get("workingDirPath");

		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		PomPlanogramas pom = new PomPlanogramas(u, testCase, data);		
		
		try {
			// Step 1.-  /////////////////////////////////////////////////////////
			addStep("Generar los 4 archivos de carga con 3,000 legos cada uno\r\n"
					+ "resultado esperado: Se valida correctamente");
			assertTrue(pom.validateFileExist(filePath), "No se encuentran los archivos de carga");
			
			// Step 2.-  /////////////////////////////////////////////////////////
			// Step 3.-  /////////////////////////////////////////////////////////
			//Se ejecutan de lado del Pom
			pom.sftpCargaMultiple(this, host, port, user, pass, filePath, workingDirPath);
			
			// Step 4.-  /////////////////////////////////////////////////////////
			/*addStep("Ejecutar el siguiente job: Nombre: starMonitor.ksh Grupo: TQA_SIV_PLANOG_DIARIA\"\r\n"
					+ "resultado esperado:El job se ejecuta correctamente");
			assertTrue(pom.ejecutarJob(data.get("nombreJob")), "No se ejecuto el job");
			
			// Step 5.-  /////////////////////////////////////////////////////////
			addStep("Validar en la tabla XXVA_ENG_PLANOGRAMA_TRA_MON el lego cargado\r\n"
					+ "resultado esperado: Se validan los 12,000 registros cargados en la tabla");
			assertTrue(pom.validateCargaMultiple(this, "XXVA_ENG_PLANOGRAMA_tra_mon"), "No se encontro la totalidad de los registros");
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Validar en la tabla XXVA_ENG_PLANOGRAMA_APROBADOS el lego cargado\r\n"
					+ "resultado esperado: Se validan los 12,000 registros cargados en la tabla");
			assertTrue(pom.validateCargaMultiple(this, "XXVA_ENG_PLANOGRAMA_APROBADO"), "No se encontro la totalidad de los registros");*/
				
			u.getDriver().quit();
			
		}catch(Exception ex) {
			testCase.addScreenShotCurrentStep(u, ex.getMessage()); 
			assertTrue(false, ex.getMessage());			
		}		
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Generar carga de 4 archivos con 3 mil legos Cada uno";
	}	

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_01_041_GenerarCargaDe4ArchivosCon3MilLegosCadaUno";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
