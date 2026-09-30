package RDM.rdmBara.ActualizacionRDMBaraCH51922004;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
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

public class ATC_FT_016_AgendarCitaFija_AC_RDM_Bara extends BaseExecution{
	
	/**
	 * REREQUISITOS: 
	 *		Contar con acceso al aplicativo RDM Bara
	 * 
	 * DESCRIPCION: Se valida que el usuario pueda agregar una nueva cita fija 
	 * requiere ordenes de compra con items en la base de datos para realizar el script
	 * 
	 * @author franciscot2@hexaware.com
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_AgendarCitaFija_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 * Contar con acceso al aplicativo RDM Bara
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
	    //validando que se muestre la pantalla principal de RDM bara
	    try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }
	    /*
		 * PASO 2: Ingresar a la ruta: Appointments/Appointments/XXFC-Appointments Schedule
		*/
	    addStep("Ingresar a la ruta: Appointments/Appointments/XXFC-Appointments Schedule");
	    p.clickImagen("Appoiments");
	    u.hardWait(10);
	    p.clickImagen("ApoimentSchedule");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    u.hardWait(5);
	    //Validar ingreso a la pantalla de appoiments schedule
	    try {
	    	p.clickImagen("ValidarPantallaSchedule");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla de appoiment schedule.");
	    }
	    /*
		 * PASO 3: Dar clic en el botón "CITAS FIJAS"
		*/
	    addStep("Dar clic en el botón CITAS FIJAS");
	    p.clickImagen("CFBoton");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("TerceraCaptura");
	    u.hardWait(5);
	    try {
	    	p.clickImagen("ValidarPantallaCitasFijas");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se pudo validar la pantalla de citas fijas.");
	    }
	    /*
		 * PASO 4: se da clic en el botón "AGREGAR"
		*/
	    addStep("se da clic en el botón AGREGAR");
	    p.clickImagen("AggBt");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("CuartaCaptura");
	    u.hardWait(5);
		try {
			p.clickImagen("ValidarPantallaAuxiliarCitaFija");
			u.hardWait(10);
		}catch(Exception e) {
			throw new RuntimeException("No se pudo validar la pantalla auxiliar de crear cita fija.");
		}
	    /*
		 * PASO 5: Seleccionar o teclear un proveedor.
		*/
	    addStep("Seleccionar o teclear un proveedor.");
	    p.doubleClickAndCopyTextBoxRight("Proveed", 10);
	    u.hardWait(10);
	    p.clickImagen("OkBtnApSP");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("QuintaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 6: Dar clic en la lista de valores de Días y seleccionar el día deseado o teclear el día deseado.
		*/
	    addStep("Dar clic en la lista de valores de Días y seleccionar el día deseado o teclear el día deseado.");
	    p.doubleClickAndCopyTextBoxRight("Dias", 10);
	    u.hardWait(10);
	    p.clickImagen("OkBtnApSP");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SextaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 7: Dar clic en la lista de valores de Turno y seleccionar el Turno deseado o teclear el Turno deseado.
		*/
	    addStep("Dar clic en la lista de valores de Turno y seleccionar el Turno deseado o teclear el Turno deseado.");
	    p.doubleClickAndCopyTextBoxRight("Turno", 10);
	    u.hardWait(10);
	    p.clickImagen("OkBtnApSP");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SeptimaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 8: Dar clic en la lista de valores de Muelle y seleccionar el Muelle deseado o teclear el Muelle deseado.
		*/
	    addStep("Dar clic en la lista de valores de Muelle y seleccionar el Muelle deseado o teclear el Muelle deseado.");
	    p.doubleClickAndCopyTextBoxRight("Muelle", 10);
	    u.hardWait(10);
	    sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("OctavaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 9: Dar clic en la lista de valores de Fecha Inicio o teclear la fecha deseada.
		*/
	    addStep("Dar clic en la lista de valores de Fecha Inicio o teclear la fecha deseada.");
	    p.doubleClickAndCopyTextBoxRight("FechaI", 10);
	    u.hardWait(10);
	    p.clickImagen("BtnAC");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("NovenaCaptura");
	    u.hardWait(5);
	    
	    
	    /*
		 * PASO 10: Dar clic en la lista de valores de Hora de Inicio o teclear la hora deseada.
		*/
	    addStep("Dar clic en la lista de valores de Hora de Inicio o teclear la hora deseada.");
	    p.doubleClickAndCopyTextBoxRight("Horaini1", 10);
	    u.hardWait(10);
	    sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	    u.hardWait(10);
	    p.doubleClickAndCopyTextBoxRight("Horaini2", 10);
	    u.hardWait(10);
	    sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("DecimaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 11: Dar clic en la lista de valores de Fecha Fin o teclear la fecha deseada
		*/
	    addStep("Dar clic en la lista de valores de Fecha Fin o teclear la fecha deseada");
	    p.doubleClickAndCopyTextBoxRight("FechaF", 10);
	    u.hardWait(10);
	    p.clickImagen("BtnAC");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("OnceavaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 12: Dar clic en la lista de valores de Hora de Fin o teclear la hora deseada.
		*/
	    addStep("Dar clic en la lista de valores de Hora de Fin o teclear la hora deseada.");
	    p.doubleClickAndCopyTextBoxRight("Horafin1", 10);
	    u.hardWait(10);
	    sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	    u.hardWait(10);
	    p.doubleClickAndCopyTextBoxRight("Horafin2", 10);
	    u.hardWait(10);
	    sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("DoceavaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 13:Dar clic en el botón "GUARDAR"
		*/
	    addStep("Dar clic en el botón GUARDAR");
	    p.clickImagen("SaveBtn");
	    u.hardWait(10);
	    p.clickImagen("YesCita");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("Treceavacaptura");
	    u.hardWait(5);
	    try {
	    	p.clickImagen("ValidarMensajeCitaGuardada");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se pudo validar la pantalla de confirmacion del guardado de cita.");
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
		return "Contar con acceso al aplicativo RDM Bara";
	}

	@Override
	public String setTestDescription() {
		return " Se valida que el usuario pueda agregar una nueva cita fija ";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_016_AgendarCitaFija_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
