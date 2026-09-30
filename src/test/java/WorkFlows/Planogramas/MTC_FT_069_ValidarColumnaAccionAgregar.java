package WorkFlows.Planogramas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Planogramas.PomPlanogramas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_069_ValidarColumnaAccionAgregar extends BaseExecution {
	
	/**
	 * @author Jose Zarate
	 * Creacion de nuevo archivo para carga de planogramas Carga Diaria - Validar columna Accion = Agregar
	 * @param data - data provider que contiene el path donde se encuentra el archivo de carga, el path del servidor donde sera cargado el archivo, 
	 * el nombre del archivo de carga y el json con la informacion del job a ejecutar
	 * @throws Exception
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_069_ValidarColumnaAccionAgregar(HashMap<String, String> data) throws Exception {
		
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
		String accion = data.get("accion");

		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		PomPlanogramas pom = new PomPlanogramas(u, testCase, data);		
		
		try {
			// Step 1.-  /////////////////////////////////////////////////////////
			addStep("El archivo a cargar debe tener las columnas correctas -Validar que dentro de la columna accion contenga el valor "+accion+" para indicar que se hara un cambio al registro");
			testCase.addBoldTextEvidenceCurrentStep("A continuacion se describen las columnas que debe contener el archivo.");
			testCase.addBoldTextEvidenceCurrentStep("");
			testCase.addBoldTextEvidenceCurrentStep("	• UPS (articulo)");
			testCase.addBoldTextEvidenceCurrentStep("	• Lego");
			testCase.addBoldTextEvidenceCurrentStep("	• Charola");
			testCase.addBoldTextEvidenceCurrentStep("	• Ubicacion en la bandeja");
			testCase.addBoldTextEvidenceCurrentStep("	• # Frentes");
			testCase.addBoldTextEvidenceCurrentStep("	• #Fentes Verticales");
			testCase.addBoldTextEvidenceCurrentStep("	• Tipo de Mercadeo");
			testCase.addBoldTextEvidenceCurrentStep("	• Capacity");
			testCase.addBoldTextEvidenceCurrentStep("	• Alto\r\n");
			testCase.addBoldTextEvidenceCurrentStep("	• Ancho");
			testCase.addBoldTextEvidenceCurrentStep("	• Profundo");
			testCase.addBoldTextEvidenceCurrentStep("	• X_Item");
			testCase.addBoldTextEvidenceCurrentStep("	• Y_Itemn");
			testCase.addBoldTextEvidenceCurrentStep("	• Tipo_Fixture");
			testCase.addBoldTextEvidenceCurrentStep("	• X_Fixture");
			testCase.addBoldTextEvidenceCurrentStep("	• Y_Fixture"); 
			testCase.addBoldTextEvidenceCurrentStep("	• Numero_segmento");
			testCase.addBoldTextEvidenceCurrentStep("	• Accion (A,B,C)");
			testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: La columna de Accion debera contener la leyenda de Agregar para indicar que se agregara un nuevo registro, Modificar para indicar que se hara un cambio al registro o Eliminar para indicar que el registro se eliminara.\"");
			assertTrue(pom.validateContenidoArchivo(filePath, fileName, accion), "El contenido del archivo no es correcto");
			
			// Step 2.-  /////////////////////////////////////////////////////////
			addStep("Validar lego existente con la combinacion de la llave articulo + lego +charola+ubicacion");
			testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: Se valida que el registro no existe");
			assertTrue(pom.validateExistenciaLegoAgregar(this, filePath, fileName),"No se puede insertar el lego, el lego ya existe");
			
			// Step 3.-  /////////////////////////////////////////////////////////
			// Step 4.-  /////////////////////////////////////////////////////////
			//Se ejecutan de lado del Pom
			pom.sftpCarga(this, host, port, user, pass, filePath, fileName, workingDirPath);
			
			// Step 5.-  /////////////////////////////////////////////////////////	
			addStep("Ejecutar el siguiente job: Nombre: starMonitor.ksh Grupo: TQA_SIV_PLANOG_DIARIA");
			testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: El job se ejecuta correctamente");
			assertTrue(pom.ejecutarJob(data.get("nombreJob")), "No se ejecuto el job");
			
			// Step 6.-  /////////////////////////////////////////////////////////
			addStep("Validar en la tabla XXVA_ENG_PLANOGRAMA_TRA_MON que se muestra el nuevo campo del lego");
			testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: Se valida correctamente");
			assertTrue(pom.validateLegoCargado(this, "XXVA_ENG_PLANOGRAMA_tra_mon", accion), "No se encontro el lego cargado en la tabla XXVA_ENG_PLANOGRAMA_TRA_MON");
						
			// Step 7.-  /////////////////////////////////////////////////////////
			addStep("Validar que se muestran registros en la tabla de planograma_aprobado");
			testCase.addBoldTextEvidenceCurrentStep("Resultado esperado: Se valida correctamente");
			assertTrue(pom.validateLegoCargado(this, "XXVA_ENG_PLANOGRAMA_APROBADO", accion), "No se encontro el lego cargado en la tabla XXVA_ENG_PLANOGRAMA_APROBADO");
						
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
		return "Terminado. Creacion de nuevo archivo para carga de planogramas Carga Diaria - Validar columna Accion = Agregar";		
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_069_ValidarColumnaAccionAgregar";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}