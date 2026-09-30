package RDM.rdmBara.ActualizacionRDMBaraCH51922004;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_FiltroUbicacion_AC_RDM_Bara extends BaseExecution{
	/**
	 * REREQUISITOS: 
	 *		1.Que existan citas creadas.
	 * 
	 * DESCRIPCION: Se validara que se agregue el campo ubicación el cual mostrara el CEDIS actual, el PT o PTs y TODOS
	 * 
	 * @author franciscot2@hexaware.com
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_FiltroUbicacion_AC_RDM_Bara_test (HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * Que existan citas creadas.
	    */
		
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH51922004");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);
		
		/*
		 * PASO 1: INGRESAR A RDM
		*/
		
		addStep("Ingresar a RDM");
		u.get(login.getUrl());	
		u.hardWait(30);
		addStep("Ingresar a usuario y password");
	    p.enviarTexto(userName);
	    p.nTAB(1);
	    u.hardWait(10);
	    p.enviarTexto(password);
	    p.nTAB(1);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("PrimerCaptura");
	    p.clickImagen("Login");
	    u.hardWait(15);
	    try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }
	    /*
		 * PASO 2: Ingresar a la ruta: Appointments/Reports/XXFC-Recibo Proveedor
		*/
	    
	    addStep("Ingresar a la ruta: Appointments/Reports/XXFC-Recibo Proveedor");
	    p.clickImagen("Appoiments");
	    u.hardWait(10);
	    p.clickImagen("Reports");
	    u.hardWait(10);
	    p.clickImagen("RecivoProvedoor");
	    u.hardWait(15);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    u.hardWait(5);
	    //Se valida la pantalla de recivo proveedor.
	    try {
	    	p.clickImagen("ValidarPantallaReciboProveedor");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla Recibo proveedor");
	    }
	    /*
		 * PASO 3: Validar que el campo Ubicación se encuentre en la pantalla
		*/
	    addStep("Validar que el campo Ubicación se encuentre en la pantalla");
	    try {
	    p.clickImagen("Ubicacion");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("TerceraCaptura");
	    u.hardWait(10);
	    
	    }
	    catch(Exception e) {
	    	throw new RuntimeException("No se puede validar el campo ubicacion. ");
	    }
	    u.close();
	}
	
	@AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "Que existan citas creadas.";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se agregue el campo ubicación el cual mostrara el CEDIS actual, el PT o PTs y TODOS";
	}

	@Override
	public String setTestDesigner() {
		return "Franciscot2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_FiltroUbicacion_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
