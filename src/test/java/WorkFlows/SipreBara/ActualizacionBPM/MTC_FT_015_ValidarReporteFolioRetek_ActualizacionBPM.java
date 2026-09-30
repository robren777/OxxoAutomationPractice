package WorkFlows.SipreBara.ActualizacionBPM;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userAdmin;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_015_ValidarReporteFolioRetek_ActualizacionBPM extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_015_ValidarReporteFolioRetek_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");

	/**
	 * Variables
	 * 
	 */
	String userUltimus = data.get("userUltimus");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	String BASE_PATH = System.getProperty("user.home");
	int rowCount;

	
	/**
	 * 
	 * Utiler�as
	 */
	
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url"));
	pomUltimus pom = new pomUltimus(u);
	pom_metodosSipreBara_userAdmin pom_userAdmin = new pom_metodosSipreBara_userAdmin(u, testCase);
	
	utils.sql.SQLUtil dbSipreBara = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREBARA,
			GlobalVariables.DB_USER_SIPREBARA, GlobalVariables.DB_PASSWORD_SIPREBARA);

	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesi�n en Ultimus");
		pom.loginUltimus(userUltimus, "");
		testCase.addScreenShotCurrentStep(u, "");
		
	/*-----------------------------------------------------------------------------------------*/
		addStep("Selecciona Iniciar");
	
		pom.seleccionarVista("INICIAR");
		testCase.addScreenShotCurrentStep(u, "");
	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia sesi�n en SIPRE_BARA");
	
		pom.openSipreBara(userSipre, psSipre);
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(u, "");
		
	/*-----------------------------------------------------------------------------------------*/
		addStep("Generar Reporte");
		
		pom_userAdmin.generaReporte("folioRetek");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "La carpeta de descargas debe ser por default";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la Creacion, Contenido y Estructura del Reporte Folio Retek";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_015_ValidarReporteFolioRetek_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
