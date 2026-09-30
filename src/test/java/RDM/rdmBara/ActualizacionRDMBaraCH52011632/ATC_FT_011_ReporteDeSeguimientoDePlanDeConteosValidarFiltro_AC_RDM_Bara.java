package RDM.rdmBara.ActualizacionRDMBaraCH52011632;

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

public class ATC_FT_011_ReporteDeSeguimientoDePlanDeConteosValidarFiltro_AC_RDM_Bara extends BaseExecution {
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	
	/**
	 * REREQUISITOS: 
	 *		1.- Contar con usuario con acceso a BI Publisher
			2.- Contar con información de localizaciones de Surtido  auditadas en mes y año actual.
	 * 
	 * DESCRIPCION: Se tiene por objetivo validar  el filtro de Año en el reporte de seguimiento plan de conteos.
	 * 
	 * @author franciscot2@hexaware.com
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_ReporteDeSeguimientoDePlanDeConteosValidarFiltro_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
		
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
		 * PASO 3: Ingresar a cumplimiento de plan y programacion 
		*/
	    addStep("Ingresar a cumplimiento de plan y programacion");	
		menu.navigateToPlanYconteo(u);
		testCase.addScreenShotCurrentStep(u, "Terceracaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidarEntradaAreportes");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantall de cumplimiento del plan y programacion.");
		}
		
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
	    
		/*
		 * PASO 8: Presionar el control VIEW
		*/
	    addStep("Presionar el control VIEW");
	    reportes.generateReporte(u);
	    testCase.addScreenShotCurrentStep(u, "OctavaCaputra");
		
		
		/*
		 * PASO 9: Validar el anio y mes seleccionado
		*/
	    addStep("Validar el anio y mes seleccionado");
	    try{
			p.clickImagen("Report");
			u.hardWait(10);
		}
		catch(Exception e){
			throw new RuntimeException("No se puede validar el reporte.");
		}
	    testCase.addScreenShotCurrentStep(u, "NovenaCaputra");
		u.hardWait(5);
		
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
		return "	 *		1.- Contar con usuario con acceso a BI Publisher\r\n"
				+ "			2.- Contar con información de localizaciones de Surtido  auditadas en mes y año actual.";
	}

	@Override
	public String setTestDescription() {
		return "Se tiene por objetivo validar  el filtro de Año en el reporte de seguimiento plan de conteos.";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_ReporteDeSeguimientoDePlanDeConteosValidarFiltro_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}