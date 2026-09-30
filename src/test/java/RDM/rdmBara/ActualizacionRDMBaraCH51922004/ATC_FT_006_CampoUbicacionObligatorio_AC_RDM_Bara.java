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

public class ATC_FT_006_CampoUbicacionObligatorio_AC_RDM_Bara extends BaseExecution{
	/**
	 * REREQUISITOS: 
	 *		1.Que existan citas creadas.
	 * 
	 * DESCRIPCION: Se validara que el campo Ubicación sea requerido y que por default muestre el valor "CEDIS"
	 * 
	 * @author franciscot2@hexaware.com
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_CampoUbicacionObligatorio_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
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
		 * PASO 3: Dar clic en la lista de valores del filtro indicador
		*/
	    addStep("Dar clic en la lista de valores del filtro indicador");
	    p.doubleClickAndCopyTextBoxRight("Indicador", 10);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("TerceraCaptura");
	    u.hardWait(10);
	    //Validando el filtro indicador
	    try {
	    	p.clickImagen("ValidarFind");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla Auxiliar de indicador");
	    }
	    
	    /*
		 * PASO 4: Seleccionar la opción Seguimiento.
		*/
	    addStep("Seleccionar la opción Seguimiento.");
	    p.clickImagen("SeguimientoOpcion");
	    u.hardWait(10);
	    p.clickImagen("okUbi");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("CuartaCaptura");
	    u.hardWait(5);
	    //Se valida el regreso a la pantalla Recivo proveeodr.
	    try {
	    	p.clickImagen("ValidarPantallaReciboProveedor");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla Recibo proveedor");
	    }
	    /*
		 * PASO 5: Seleccionar fecha inicio y fecha fin.
		*/
	    addStep("Seleccionar fecha de inicio y fin");
	    p.doubleClickAndCopyTextBoxRight("FechaIni", 10);
	    u.hardWait(10);
	    p.clickImagen("OkButtonFecha");
	    u.hardWait(10);
	    p.doubleClickAndCopyTextBoxRight("FechaFin", 10);
	    u.hardWait(10);
	    p.clickImagen("OkButtonFecha");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("QuintaCaptura");
	    u.hardWait(5);
	    //Se valida el regreso a la pantalla Recivo proveeodr.
	    try {
	    	p.clickImagen("ValidarPantallaReciboProveedor");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla Recibo proveedor");
	    }
	    /*
		 * PASO 6: Validar que el campo Ubicación muestre el valor CEDIS por Default
		*/
	    addStep("Validar que el campo Ubicación muestre el valor CEDIS por Default");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("SextaCaptura");
	    u.hardWait(5);	
		try{
			p.clickImagen("ValidarUbicacion");
			u.hardWait(10);
		}catch(Exception e){
			throw new AssertionError("No se pudo validar la ubicacion por defecto. ");
		}

		//ValidarUbicacion
	    
	    /*
		 * PASO 7: Dar clic en al lista de valores del filtro ubicación.
		*/
	    addStep("Dar clic en al lista de valores del filtro ubicación.");
	    p.clickImagen("UbicacionF");
	    p.clickImagen("UbicacionF");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SeptimaCaptura");
	    u.hardWait(5);
	    
	    /*
		 * PASO 8: Dejar el campo ubicación sin valor y dar clic al botón Print
		*/
	    addStep("Dejar el campo ubicación sin valor y dar clic al botón Print");
	    p.enviarTexto(" ");
	    u.hardWait(10);
	    p.clickImagen("PrintB");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("OctavaCaptura");
	    u.hardWait(5);
	    //validando el mensaje de ubicacion requerida
	    try {
	    	p.clickImagen("ValidarUbicaionRequierda");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("El mensaje de ubicacion requerida no se encuentra");
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
		return "Se validara que el campo Ubicación sea requerido y que por default muestre el valor \"CEDIS\"";
	}

	@Override
	public String setTestDesigner() {
		return "Franciscot2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_CampoUbicacionObligatorio_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
