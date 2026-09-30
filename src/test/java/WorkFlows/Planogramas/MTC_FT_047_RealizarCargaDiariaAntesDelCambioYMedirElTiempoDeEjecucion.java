package WorkFlows.Planogramas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Planogramas.PomPlanogramas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_047_RealizarCargaDiariaAntesDelCambioYMedirElTiempoDeEjecucion extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Realizar carga Diaria antes del cambio y medir el tiempo de ejecucion
	 * @param data --> data provider que contiene la url del portal de ultimus1
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_02_047_RealizarCargaDiariaAntesDelCambioYMedirElTiempoDeEjecucion(HashMap<String, String> data) throws Exception {
		
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
		String fileName = data.get("nombreArchivo");
		String workingDirPath = data.get("workingDirPath");

		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		PomPlanogramas pom = new PomPlanogramas(u, testCase, data);		
		
		try {
			// Step 1.-  /////////////////////////////////////////////////////////
			addStep("Validar que el lego exista SELECT A.CHAROLA, A.COMPARTIDOS, A.ITEM,A.ITEM_DESCRIPCION ,A.UBICACION_BANDEJA, A.NUM_FRENTES, A.NUM_FRENTES_VERTICAL, A.NUM_PROFUNDOS,\r\n"
					+ "    A.TIPO_MERCADEO, A.TOTAL_UNIDADES, A.CAPACIDAD, A.ALTO, A.ANCHO, A.PROFUNDO, TO_CHAR(SYSDATE,'DD/MM/YYYY') AS FECHA\r\n"
					+ "FROM XXVA_ENG_PLANOGRAMA_APROBADO A, XXVA_SEM_LEGOS B\r\n"
					+ "WHERE A.LEGOS_ID = B.LEGOS_ID\r\n"
					+ "AND B.DESCRIPCION_LEGO = '10PGS_SIN_Refrescos-CF_(4)DI';\r\n"
					+ "Resultado Esperado: Se valida correctamente en tabla\r\n"
					+ "");
			assertTrue(pom.validateCargaDiaria(this), "No se encontraron registros");
			
			// Step 2.-  /////////////////////////////////////////////////////////
			addStep("Validarque el lego exista SELECT A.CHAROLA, A.COMPARTIDOS, A.ITEM,A.ITEM_DESCRIPCION ,A.UBICACION_BANDEJA, A.NUM_FRENTES, A.NUM_FRENTES_VERTICAL, A.NUM_PROFUNDOS,\r\n"
					+ "    A.TIPO_MERCADEO, A.TOTAL_UNIDADES, A.CAPACIDAD, A.ALTO, A.ANCHO, A.PROFUNDO, TO_CHAR(SYSDATE,'DD/MM/YYYY') AS FECHA\r\n"
					+ "FROM XXVA_ENG_PLANOGRAMA_APROBADO A, XXVA_SEM_LEGOS B\r\n"
					+ "WHERE A.LEGOS_ID = B.LEGOS_ID\r\n"
					+ "AND B.DESCRIPCION_LEGO = '10PGS_SIN_Refrescos-CF_(4)DI';\r\n"
					+ "Resultado Esperado: Se valida correctamente en tabla\r\n"
					+ "");
			assertTrue(pom.validateLegosSembarados(this), "No se encontraron registros");
			
			// Step 3.-  /////////////////////////////////////////////////////////
			addStep("Generar el archivos de carga	\r\n"
					+ "Resultado Esperado: Se validan archivos correctamente");
			assertTrue(pom.validateFileExist(filePath), "No se encuentran los archivos de carga");
			
			// Step 4.-  /////////////////////////////////////////////////////////
			// Step 5.-  /////////////////////////////////////////////////////////
			pom.sftpCargaMultiple(this, host, port, user, pass, filePath, workingDirPath);
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Ejecutar el siguiente job: Nombre: starMonitor.ksh Grupo: TQA_SIV_PLANOG_DIARIA\r\n"
					+ "Resultado Esperado: Se valida ejecucion del sistema correctamente.\r\n"
					+ "Validar cuanto tarda en ejecutarse el Job\"");
			assertTrue(pom.ejecutarJob(data.get("nombreJob")));
			
			// Step 7.-  /////////////////////////////////////////////////////////
			addStep("Validar en la tabla XXVA_ENG_PLANOGRAMA_TRA_MON el lego cargado\r\n"
					+ "resultado esperado: Se validan los 12,000 registros cargados en la tabla");
			assertTrue(pom.validateLegoCargado(this, "XXVA_ENG_PLANOGRAMA_tra_mon", ""), "No se encontro la totalidad de los registros");
				
			u.getDriver().quit();
			//u.close();
			
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
		return "Construido. Realizar carga Diaria antes del cambio y medir el tiempo de ejecucion";
	}	

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_02_047_RealizarCargaDiariaAntesDelCambioYMedirElTiempoDeEjecucion";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}