package RDM.rdmBara.ActualizacionRDMBaraCH51966374;

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

public class ATC_FT_002_CancelarCitaProgramadaEnReprogCita_AC_RDM_Bara extends BaseExecution{
	
	/**
	 * REREQUISITOS: 
	 *		Ordenes de Compra Recibo de Proveedores
	 * 		cita (dp)
	 * DESCRIPCION: Eliminar la cita solo con el botón CANCELAR
	 * 
	 * @author franciscot2@hexaware.com
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_CancelarCitaProgramadaEnReprogCita_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 * Contar con acceso al aplicativo RDM Bara
	    */
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String cita = data.get("Cita");
		
		
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
		 * PASO 1: Ingresar a RDM Bara QA
		*/
		addStep("Ingresar a RDM Bara QA");
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
	    //Validando el login
	    try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo validar el inicio de sesion en RDM ");
			throw new AssertionError("No se pudo validar el inicio de sesion en RDM ");
	    }
	    
	    /*
		 * PASO 2: Ingresar a la ruta: Appointments/Appointments/XXFC-Appointments Schedule
		*/
	    addStep("Ingresar a la ruta: Appointments/XXFC-Appointments Schedule");
	    p.clickImagen("Appoiments");
	    u.hardWait(10);
	    p.clickImagen("ApoimentSchedule");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    u.hardWait(5);
	    //validando la pantalla apoiments schedule
	    try {
	    	p.clickImagen("ValidarPantallaSchedule");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	System.out.println("No se pudo validar la pantalla de schedulle");
			throw new AssertionError("No se pudo validar la pantalla de schedulle ");
	    }
	    
	    /*
		 * PASO 3: Dar clic en el botón REPROG. CITA
		*/ 
	    addStep("Dar clic en el botón REPROG. CITA");
	    p.clickImagen("ReprogramarCitaBtn");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("TercerPasoCaptura");
	    //Validando la pantalla de reprogramar citas
	    try {
	    	p.clickImagen("ValidarPantallaReprogramar");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo valida la pantalla de reprogramar citas");
			throw new AssertionError("No se pudo valida la pantalla de reprogramar citas ");
	    }
	    
	    /*
		 * PASO 4: Introducir el numero de Cita a reprogramar/cancelar
		*/ 
	    addStep("Introducir el numero de Cita a reprogramar/cancelar");
	    p.doubleClickAndCopyTextBoxRight("CitaBusqueda", 10);
	    u.hardWait(10);
	    p.enviarTexto(cita);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("CuartoPasoCaptura");
	    
	    /*
		 * PASO 5: Presionar ENTER/TAB
		*/ 
	    addStep("Presionar ENTER/TAB");
	    p.nTAB(1);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("QuintoPasoCaptura");
	    //validando que la cita existe
	    try {
	    	p.clickImagen("ValidarCitaEncontrada");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo encontrar la cita a cancelar");
			throw new AssertionError("No se pudo encontrar la cita a cancelar");
	    }
	    
	    /*
		 * PASO 6: Presionar el botón CANCELAR CITA
		*/ 
	    addStep("Presionar el botón CANCELAR CITA");
	    testCase.addScreenShotCurrentStep("SextoPasoCaptura");
	    try {
	    	p.clickImagen("ValidandoMensajeDeConfirmacion");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se puede encontrar la pantalla de confirmacion de cancelacion");
			throw new AssertionError("No se puede encontrar la pantalla de confirmacion de cancelacion");
	    }
	    
	    /*
		 * PASO 7: El usuario presiona la opción YES
		*/ 
	    addStep("El usuario presiona la opción YES");
	    p.clickImagen("YesCancelarCitaBtn");
	    testCase.addScreenShotCurrentStep("SeptimoPasoCaptura");
	    u.hardWait(10);
	    try {
	    	p.clickImagen("ValidarCitaCancelada");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se puede encontrar la pantalla de cita eliminada");
			throw new AssertionError("No se puede encontrar la pantalla de cita eliminada");
	    }
	    u.close();
	    System.out.println("------------------------Caso de prueba pasado------------------------");
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
		return "Ordenes de Compra Recibo de Proveedores";
	}

	@Override
	public String setTestDescription() {
		return "Eliminar la cita solo con el botón CANCELAR";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CancelarCitaProgramadaEnReprogCita_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
