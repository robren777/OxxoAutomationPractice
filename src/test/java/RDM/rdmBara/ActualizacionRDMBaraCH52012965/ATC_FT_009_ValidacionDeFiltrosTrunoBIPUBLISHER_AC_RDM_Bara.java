package RDM.rdmBara.ActualizacionRDMBaraCH52012965;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.bIPublisher.BiPublisherInicio;
import RDM.util.bIPublisher.BiPublisherMainMenu;
import RDM.util.bIPublisher.BiPublisherReportes;
import RDM.util.metodosAdicionales.MetodosSikulli;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_009_ValidacionDeFiltrosTrunoBIPUBLISHER_AC_RDM_Bara extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1.- 1.-Mes, Año, CEDIS
			2.-Información conteo surtido
	 * 
	 * DESCRIPCION: Se tiene por objetivo validar que el campo y filtro de Turno  acepte los valores correctos.
	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ValidacionDeFiltrosTrunoBIPUBLISHER_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * VARIABLES
		 * 
		 * user = usuario de BI PUBLISHER
		 * password = password de BIPUBLISHER
		 * 
	    */
		
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RDM\\"+"CH52011632");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		String mes = data.get("MES");
		String anio = data.get("ANIO");
		
		/*
		 * PASO 1: Ingresar a la URL de Publisher BI
		*/
		addStep("Ingresar a la URL de Publisher BI");	
		u.getDriver().manage().deleteAllCookies();
		u.get(GlobalVariables.URL_BI_PUBLISHER);
		u.getDriver().manage().window().maximize();
		u.getDriver().manage().window().fullscreen();
		u.hardWait(10);
		BiPublisherInicio inicio = new BiPublisherInicio();
		inicio.loginBiPublisher(u);
		u.hardWait(30);
		testCase.addScreenShotCurrentStep(u, "PrimerCaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidaEntradaABi");
			u.hardWait(10);
		}catch(Exception e){
			try{
				p.clickImagen("ValidaEntradaABiEsp");
				u.hardWait(10);
			}catch(Exception ex){
				throw new RuntimeException("No se puede validar la entrada a BI-PUBLISHER.");
			}
		}
		
		 /*
		 * PASO 2: Ingeresar a la lista reportes logistica
		*/
		addStep("Ingeresar a la lista reportes logistica");	
		BiPublisherMainMenu menu = new BiPublisherMainMenu();
		menu.navigateToLogistica(u);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(u, "Segundacaptura");
		u.hardWait(5);
		
		 /*
		 * PASO 3: Ingresar a cimplimiento de plan y programacion 
		*/
		addStep("Ingresar a cimplimiento de plan y programacion");	
		menu.navigateToPlanYconteo(u);
		testCase.addScreenShotCurrentStep(u, "Terceracaptura");
		u.hardWait(5);
		
		
		/*
		 * PASO 4: Seleccionar el filtro de CEDIS
		*/
		addStep("Seleccionar el filtro de CEDIS");	
	    BiPublisherReportes reportes = new BiPublisherReportes();
	    reportes.selectCedis(u);
		testCase.addScreenShotCurrentStep(u, "CuartaCaptura");
		u.hardWait(5);
		
		
		/*
		 * PASO 5: Seleccionar el filtro MES
		*/
		addStep("Seleccionar el filtro MES");
		reportes.selectMes(u, mes);
		testCase.addScreenShotCurrentStep(u, "QuintaCaputra");
		u.hardWait(5);
		
		
		/*
		 * PASO 6: Seleccionar el filtro Anio
		*/
		addStep("Seleccionar el filtro Anio");
		reportes.selectanio(u, anio);
	    testCase.addScreenShotCurrentStep(u, "SextaCaputra");
		u.hardWait(5);
	    
	    
		/*
		 * PASO 7: Validar que el filtro turno se muestre en pantalla
		*/
		addStep("Validar que el filtro turno se muestre en pantalla");
	    reportes.validateFilterTurno(u);
	    testCase.addScreenShotCurrentStep(u, "SeptimaCaputra");
		u.hardWait(5);
		try{
			p.clickImagen("TurnoValidacion");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar el filtro turno.");
		}
		
		/*
		 * PASO 8: Validar el filtro 1 y ver
		*/
	    addStep("Validar el filtro 1 y ver");
	    reportes.generateReporteByTurno(u, "Turno1");
		testCase.addScreenShotCurrentStep(u, "OctavaCaputra");
		u.hardWait(10);
		try{
			p.clickImagen("ValidarTurno1");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar el filtro turno1.");
		}
		/*
		 * PASO 9: Validar el filtro 2 y ver
		*/
	    addStep("Validar el filtro 2 y ver");
	    reportes.generateReporteByTurno(u, "Turno2");
		testCase.addScreenShotCurrentStep(u, "NovenaCaputra");
		u.hardWait(10);
		try{
			p.clickImagen("ValidarTurno2");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar el filtro turno2.");
		}
		/*
		 * PASO 10: Validar el filtro 3 y ver
		*/
	    addStep("Validar el filtro 3 y ver");
	    reportes.generateReporteByTurno(u, "Turno3");
		testCase.addScreenShotCurrentStep(u, "DecimaCaputra");
		u.hardWait(10);
		try{
			p.clickImagen("ValidarTurno3");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar el filtro turno3.");
		}
		u.close();
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
		return "1.- 1.-Mes, Año, CEDIS\r\n"
				+ "			2.-Información conteo surtido";
	}

	@Override
	public String setTestDescription() {
		return "Se tiene por objetivo validar que el campo y filtro de Turno  acepte los valores correctos.";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_ValidacionDeFiltrosTrunoBIPUBLISHER_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
