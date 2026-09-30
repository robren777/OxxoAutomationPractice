package WorkFlows.SipreBara.ActualizacionBPM;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pomCargaArchivos;
import WorkFlows.util.SipreBara.pom_Login_PEGA;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_001_AccesoUsuariosPortalPEGA_ActualizacionBPM extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_001_AccesoUsuariosPortalPEGA_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");
	

	/**
	 * Variables
	 * 
	 */
	String userPega = data.get("userPega");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	String BASE_PATH = System.getProperty("user.dir");

	
	/**
	 * 
	 * Utiler�as
	 */
	
	
	 u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url"));
	pomCargaArchivos cargaArchivos = new pomCargaArchivos(u, testCase);
	pom_Login_PEGA pomPega = new pom_Login_PEGA(u,testCase, userPega );
	
	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesi�n en PEGA con usuario ADMINISTRADOR");
		pomPega.iniciaSesion();
		
		
		System.out.println("URL --> "+u.getDriver().getCurrentUrl());
		
		//Cambiar url
		
		/*String url =u.getDriver().getCurrentUrl();
		
		String[] restoUrl = url.split("http://fcportalqa.femcom.net:7019");
	    System.out.println("------> "+restoUrl[0]);
	    System.out.println("------> "+restoUrl[1]);
	    

		String ip = "http://10.184.32.129:7019"+restoUrl[1].toString();
		System.out.println("URL_Nueva --> "+ip);
		
		u.get(ip);*/
		pomPega.cambiarVentana(1);
		u.hardWait(3);
		pomPega.errorSesion();
		testCase.addScreenShotCurrentStep("");
		
		u.hardWait(4);
	    
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
		return "Validar el log in correcto desde la plataforma PEGA al aplicativo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_AccesoUsuariosPortalPEGA_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@AfterMethod
    public void afterMethod() {
        u.getDriver().quit();
    }

}
