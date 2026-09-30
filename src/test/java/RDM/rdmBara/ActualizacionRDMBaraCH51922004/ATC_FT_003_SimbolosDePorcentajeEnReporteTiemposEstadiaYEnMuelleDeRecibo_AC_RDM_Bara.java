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

public class ATC_FT_003_SimbolosDePorcentajeEnReporteTiemposEstadiaYEnMuelleDeRecibo_AC_RDM_Bara extends BaseExecution{
	
	
	/**
	 * REREQUISITOS: 
	 *		1.Se requiere tener Citas creadas
	 * 
	 * DESCRIPCION: Se requiere se agregue el símbolo de % en el encabezado de tiempos Hot Replen y Tiempos Putaway.
	 * 
	 * @author franciscot2@hexaware.com
	 */
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_SimbolosDePorcentajeEnReporteTiemposEstadiaYEnMuelleDeRecibo_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * Citas creadas
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
		 * PASO 2: INGRESAR A RUTA:  Appointments/Reports/XXFC-Recibo Proveedor
		*/
	    
	    addStep("Ingresar a la ruta Appointments/Reports/XXFC-Recibo Proveedor");
	    p.clickImagen("Appoiments");
	    u.hardWait(10);
	    p.clickImagen("Reports");
	    u.hardWait(10);
	    p.clickImagen("RecivoProvedoor");
	    u.hardWait(15);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    //Se valida la pantalla de recivo proveedor.
	    try {
	    	p.clickImagen("ValidarPantallaReciboProveedor");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla Recibo proveedor");
	    }
	    
	    /*
		 * PASO 3: Dar clic en la lista de valores del filtro indicador y seleccionar la opción Seguimiento
		*/
	    addStep("Dar clic en la lista de valores del filtro indicador y seleccionar la opción Seguimiento");
	    p.doubleClickAndCopyTextBoxRight("FiltroIndicador", 10);
	    u.hardWait(10);
	    p.clickImagen("SeguimientoOpcion");
	    u.hardWait(10);
	    p.clickImagen("ButtonOk");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("TerceraCaptura");
	    //Validar la seleccion del filtro
	    try {
	    	p.clickImagen("ValidarFiltroSeguimiento");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No se valido que la opcion seguimiento exista.");
	    }
	    /*
		 * PASO 4: Seleccionar fecha de inicio y fin
		*/
	    addStep("Seleccionar fecha de inicio y fin");
	    p.doubleClickAndCopyTextBoxRight("FechaIniText", 10);
	    u.hardWait(10);
	    p.enviarTexto("08-MAY-2022");
		p.nTAB(1);
		u.hardWait(5);
	    u.hardWait(10);
	    p.doubleClickAndCopyTextBoxRight("FechaFin", 10);
	    u.hardWait(10);
	    p.clickImagen("OkButtonFecha");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("CuartaCaptura");
	    u.hardWait(10);
	    
	    /*
		 * PASO 5: Dar clic en al lista de valores del filtro ubicación y seleccionar la opción deseada
		*/
	    addStep("Dar clic en al lista de valores del filtro ubicación y seleccionar la opción deseada");
	    p.doubleClickAndCopyTextBoxRight("UbicacionF", 10);
	    u.hardWait(10);
	    //p.clickImagen("OkButtonUbicaciones");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("QuintaCaptura");
	    u.hardWait(10);
	    //Validando la ubicacion seleccionada
	    // try {
	    // 	p.clickImagen("ValidarLaSeleccionUbicacion");
	    // 	u.hardWait(10);
	    // }catch(Exception e) {
	    // 	throw new RuntimeException("No se valido que la ubicacion fue seleccionada");
	    // }
	    /*
		 * PASO 6: Posicionarse en la opción Estadía Muelles de Recibo 
		*/
	    addStep("Posicionarse en la opción Estadía Muelles de Recibo ");
	    p.clickImagen("EstadiaRecivo");
	    u.hardWait(2);
	    p.clickImagen("EstadiaRecivo");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SextaCaptura");
	    u.hardWait(10);
	    
	    /*
		 * PASO 7: Dar clic en el botón PRINT
		*/
	    addStep("Dar clic en el botón PRINT");
	    p.clickImagen("PrintB");
	    u.hardWait(15);
	   
	    testCase.addScreenShotCurrentStep("SeptimaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 8: Validar hayan sido agregados los símbolos de % en el encabezado de Tiempos Hot Reple y Tiempos Putaway
		*/
	    addStep("Validar hayan sido agregados los símbolos de % en el encabezado de Tiempos Hot Reple y Tiempos Putaway");
	    try {
	    p.clickImagen("EdgeIcon");
	    u.hardWait(10);
	    p.clickImagen("ventana");
	    u.hardWait(10);
	    p.clickImagen("popup");
	    u.hardWait(10);
	    p.clickImagen("pdfReporte");
	    u.hardWait(10);
	    p.clickImagen("EdgeIconConNoti");
	    u.hardWait(10);
	    p.clickImagen("VentanaOpen");
	    u.hardWait(10);
	    p.clickImagen("OpenBotonReporte");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("OctavaCaptura");
		u.hardWait(5);
		//validando el putaway ValoresDelPutAway
		p.clickImagen("ValoresDelPutAway");
		u.hardWait(10);
	    }catch(Exception e) {
	    	throw new RuntimeException("No fue posible abrir el reporte ");
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
		return "Se requiere tener Citas creadas";
	}

	@Override
	public String setTestDescription() {
		return "Se requiere se agregue el símbolo de % en el encabezado de tiempos Hot Replen y Tiempos Putaway, ";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_SimbolosDePorcentajeEnReporteTiemposEstadiaYEnMuelleDeRecibo_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
