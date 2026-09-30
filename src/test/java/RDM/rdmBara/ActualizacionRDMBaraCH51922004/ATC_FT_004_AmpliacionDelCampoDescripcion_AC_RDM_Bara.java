package RDM.rdmBara.ActualizacionRDMBaraCH51922004;
import static org.testng.Assert.assertTrue;

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

public class ATC_FT_004_AmpliacionDelCampoDescripcion_AC_RDM_Bara extends BaseExecution{
	
	/**
	 * REREQUISITOS: 
	 *		1.Se requiere tener al menos una orden de compra la cual tenga una descripción de mínimo 60 
	 * 
	 * DESCRIPCION: Se  validar que el campo Descripción de la pantalla de detalle de cada OC, sea del tamaño adecuado, el cual nos permita visualizar los 60 caracteres
	 * 
	 * @author franciscot2@hexaware.com
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_AmpliacionDelCampoDescripcion_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 * Se requiere tener al menos una orden de compra la cual tenga una descripción de mínimo 60 
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
	  //Se valida la pantalla principal de RDM Bara.
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
		 * PASO 3: Dar clic en el icono Enter Query (lampara)
		*/
		addStep("Dar clic en el icono Enter Query (lampara)");
		p.clickImagen("CancelQuery");
		u.hardWait(10);
		p.clickImagen("linterna");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("TerceraCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 4: Dar clic en la lista de valores del filtro fecha.
		*/
		addStep("Dar clic en la lista de valores del filtro fecha");
		
		p.doubleClickAndCopyTextBoxRight("FechaCita", 10);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		u.hardWait(5);
		//Validar la pantalla auxiliar fecha cita
		try {
			p.clickImagen("ValidarFechaCita");
			u.hardWait(10);
		}catch(Exception e) {
			throw new RuntimeException("No se puede validar la pantalla auxiliar fecha cita");
		}
		/*
		 * PASO 5: Seleccionar una fecha en el calendario y dar clic en el boton de OK.
		*/
		addStep("Seleccionar una fecha en el calendario y dar clic en el boton de OK.");
	    p.clickImagen("ButtonokFc");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("QuintaCaptura");
	    u.hardWait(5);
	    //validando el regreso a appoiments schedule
	    try {
	    	p.clickImagen("ValidarPantallaSchedule");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla de appoiment schedule.");
	    }
	    /*
		 * PASO 6: Dar clic en la lista de valores tipo de cita.
		*/
	    addStep("Dar clic en la lista de valores tipo de cita.");
	    p.doubleClickAndCopyTextBoxRight("Tdcita",10);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SextaCaptura");
	    u.hardWait(5);
	    //Validando pantalla auxiliar tipo de cita
	    try {
	    	p.clickImagen("ValidarTipoDeCita");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla auxiliar tipo de cita.");
	    }
	    /*
		 * PASO 7: Seleccionar el tipo de cita deseado y dar clic en el boton OK.
		*/
	    addStep("Seleccionar el tipo de cita deseado y dar clic en el boton OK.");
	    p.clickImagen("OkBtTCita");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SeptimaCaptura");
	    u.hardWait(5);
	  //validando el regreso a appoiments schedule
	    try {
	    	p.clickImagen("ValidarPantallaSchedule");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla de appoiment schedule.");
	    }
	    /*
		 * PASO 8: Ingresar el Proveedor deseado y dar clic en el boton Execute Query.
		*/
	    addStep("Ingresar el Proveedor deseado y dar clic en el boton Execute Query.");
	    p.doubleClickAndCopyTextBoxRight("proveedor", 10);
	    u.hardWait(10);
	    //Validando la pantalla auxiliar de proveedor
	    try {
	    	p.clickImagen("ValidarPantallaAuxiliarProveedor");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla auxiliar de proveedor .");
	    }
	    p.clickImagen("OkBtnApSP");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("OctavaCaptura");
	    
	    /*
		 * PASO 9: Dar clic en el checkbox de la(s) orden(es) de compra a las que desea programar la cita.
		*/
	    addStep("Dar clic en el checkbox de la(s) orden(es) de compra a las que desea programar la cita.");
	    p.clickImagen("CheckboxCitas");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("NovenaCaptura");
	    /*
		 * PASO 10: Dar doble clic sobre la orden de compra señalada en el checkbox
		*/
		boolean flag=false;
	    addStep("Dar doble clic sobre la orden de compra señalada en el checkbox");
	    p.clickImagen("ordenCompra");
	    u.hardWait(2);
	    p.clickImagen("ordenCompra");
	    u.hardWait(2);
	    p.clickImagen("ordenCompra");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("DecimaCaptura");
	    u.hardWait(10);
		String desc = p.doubleClickAndCopyTextBoxLeft("ObjetoValidar", 10);
		if(desc.length()<60)
			flag=true; 
		assertTrue(flag, "La descripcion cumple con el requerimento de menos de 60 caracteres");
		//ObjetoValidar
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
		return "Se requiere tener al menos una orden de compra la cual tenga una descripción de mínimo 60";
	}

	@Override
	public String setTestDescription() {
		return "Se  validar que el campo Descripción de la pantalla de detalle de cada OC, sea del tamaño adecuado, el cual nos permita visualizar los 60 caracteres"; 
	}

	@Override
	public String setTestDesigner() {
		return "Franciscot2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_AmpliacionDelCampoDescripcion_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
