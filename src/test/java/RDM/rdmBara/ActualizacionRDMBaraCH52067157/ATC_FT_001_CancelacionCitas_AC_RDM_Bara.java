package RDM.rdmBara.ActualizacionRDMBaraCH52067157;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_CancelacionCitas_AC_RDM_Bara extends BaseExecution{
	/**
	 * REREQUISITOS: 
	 *		1.-Contar con la creación de dos citas a lo largo del día.
	 * 		provedoor (dp)
	 * 		cita (dp)
	 * DESCRIPCION: Validar que al realizar la tercera cita, entre dos citas ya existentes, el sistema permita la asignación de la cita, 
	 * cuando el tiempo de descarga real sea menor o igual al tiempo disponible en el muelle.

	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CancelacionCitas_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 * 	1.- Contar con la creación de dos citas a lo largo del día.
	    */
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String proveedor = data.get("Proveedor");
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH52067157");
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
			throw new AssertionError("No se pudo validar el inicio de sesion en RDM");
	    }
	    
	    /*
		 * PASO 2: Creación de cita en la pantalla XXFC-Appointment Schedule
			Ingresar a la siguiente ruta Appointment/ XXFC-Appointment Schedule 
			y seleccionar el proveedor 66772

		*/
	    addStep(" Creación de cita en la pantalla XXFC-Appointment Schedule Ingresar a la siguiente ruta Appointment/ XXFC-Appointment Schedule \r\n"
	    		+ "y seleccionar el proveedor 66772 ");
	    p.clickImagen("Appoiments");
	    u.hardWait(10);
	    p.clickImagen("ApoimentSchedule");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    u.hardWait(5);
	    p.doubleClickAndCopyTextBoxRight("ProveedorTxt", 10);
	    u.hardWait(10);
	    p.enviarTexto(proveedor);
	    u.hardWait(10);
	    p.nTAB(10);
	    p.nTAB(10);
	    //validando la pantalla de XXFC-Appointment Schedule
	    try {
	    	p.clickImagen("ValidarPantallaSchedule");	    
	    	u.hardWait(10);
	    }
	    catch(Exception e) {
	    	System.out.println("No se puede validar la pantalla de Appointment Schedule");
	    	throw new AssertionError("No se puede validar la pantalla de Appointment Schedule");
	    }

		/*
		 * PASO 3: Selección de artículo (s) en pantalla de Detalle
		*/
	    addStep("Selección de artículo (s) en pantalla de Detalle");
	    p.clickImagen("OrdenCompraChbx");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("TercerPasoCaptura");
	    u.hardWait(5);
	    /*
		 * PASO 4: Ingresar la información para la generación de la cita
					Ingresar Muelle MR01 y ejecutar
		*/
	    addStep("Ingresar la información para la generación de la cita\r\n"
	    		+ "Ingresar Muelle MR01 y ejecutar\r\n"
	    		+ "");
	    p.doubleClickAndCopyTextBoxRight("MuelleSelect", 10);
	    u.hardWait(10);
	    //p.clickImagen("MR01Muelle");
	    //u.hardWait(10);
	    sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	    u.hardWait(10);
	    p.clickImagen("EjecutarBtn");
	    testCase.addScreenShotCurrentStep("CuartoPasoCaptura");
	    u.hardWait(5);
	    //Validando la carga de horarios.
	    try {
	    	p.clickImagen("ClickHora");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo validar el horario disponible.");
	    	throw new AssertionError("No se pudo validar el horario disponible.");
	    }
	    /*
		 * PASO 5: Dar doble clic dentro de Hora IN para abrir pantalla de programación de cita
		*/
	    addStep("Dar doble clic dentro de Hora IN para abrir pantalla de programación de cita");
	    p.clickImagen("ClickHora");
	    p.clickImagen("ClickHora");
	    u.hardWait(5);
	    p.clickImagen("ClickHora");
	    p.clickImagen("ClickHora");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("QuintoPasoCaptura");
	    u.hardWait(10);
	    try {
	    	p.clickImagen("ValidarProgCita");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se puede validar la programacion de las citas");
			throw new AssertionError("No se puede validar la programacion de las citas");
	    }
	    
	    
	    /*
		 * PASO 6: Para generar la cita validar dentro del botón Yes
		*/
	    addStep("Para generar la cita validar dentro del botón Yes");
	    p.clickImagen("SaveBtn");
	    u.hardWait(10);
	    p.clickImagen("Yesbtn");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SextoPasoCaptura");
	    u.hardWait(5);
	    String citaN = p.doubleClickAndCopyTextBoxRight("CitaNum", 10);
	    p.clickImagen("Exitbtn");
	    //Validando el regreso a la pantalla de XXFC-Appointment Schedule
	    try {
	    	p.clickImagen("ValidarPantallaSchedule");	    
	    	u.hardWait(10);
	    }
	    catch(Exception e) {
	    	System.out.println("No se puede validar la pantalla de Appointment Schedule");
	    	throw new AssertionError("No se puede validar la pantalla de Appointment Schedule");
	    }
	    
	    /*
		 * PASO 7: Ingresar a la siguiente ruta Appointment/ XXFC-Appointment Schedule y presionar botón REPROG. CITA 
		*/
	    addStep("Ingresar a la siguiente ruta Appointment/ XXFC-Appointment Schedule y presionar botón REPROG. CITA ");
	    p.clickImagen("ReprogCitaBtn");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SeptimoPasoCaptura");
	    u.hardWait(5);
	    //Validando la pantalla de reprog cita
	    try {
	    	p.clickImagen("ValidarReprogCita");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo validar la pantalla de REPROG CITA");
	    	throw new AssertionError("No se pudo validar la pantalla de REPROG CITA");
	    }
	    /*
		 * PASO 8: Seleccionar de la lista de valores la cita a eliminar
		*/
	    
	    addStep("Seleccionar de la lista de valores la cita a eliminar");
	    p.doubleClickAndCopyTextBoxRight("Cita", 10);
	    u.hardWait(10);
	    p.enviarTexto(citaN);
	    u.hardWait(10);
	    p.nTAB(1);
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("OctavaPasoCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 9: Presionar botón CANCELAR CITA
		*/
	    addStep("Presionar botón CANCELAR CITA");
	    p.clickImagen("CancelarCitabtn");
	    u.hardWait(10);
	    p.clickImagen("YesCancelbtn");
	    testCase.addScreenShotCurrentStep("NovenaPasoCaptura");
	    
	    //validando el regreso a la pantalla de REPROG CITA
	    try {
	    	p.clickImagen("ValidarReprogCita");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo validar la pantalla de REPROG CITA");
	    	throw new AssertionError("No se pudo validar la pantalla de REPROG CITA");
	    }
	    u.hardWait(5);
	    u.close();
	    /*
		 * PASO 10: Validar en tablas de control que la cita se haya eliminado de las tablas de RDM 
		*/
	    
	    addStep("Validar en tablas de control que la cita se haya eliminado de las tablas de RDM y RMS");
	    String querty1 = "select COUNT(*) from appointment where APPT_NBR= '" + citaN + "'";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);	    
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
	    //validando que la cita no exista mas en la base de datos RDM
	    boolean flag = false; 
	    if(resultadoContenedor1.isEmpty())
	    	flag=true;
	    assertTrue(flag, "La base de datos no tiene datos referentes");
	    
	    
	    
	}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	@AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}

	@Override
	public String setPrerequisites() {
		return "Contar con la creación de dos citas a lo largo del día.";
	}
	@Override
	public String setTestDescription() {
		return "Validar que al realizar la tercera cita, entre dos citas ya existentes, el sistema permita la asignación de la cita, cuando el tiempo de descarga real sea menor o igual al tiempo disponible en el muelle.";
	}
	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}
	@Override
	public String setTestFullName() {
		return "ATC_001_CancelacionCitas_AC_RDM_Bara_test";
	}
	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
