package RDM.rdmBara.ActualizacionRDMBaraCH52027739;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.bIPublisher.BiPublisherInicio;
import RDM.util.bIPublisher.BiPublisherMainMenu;
import RDM.util.metodosAdicionales.MetodosSikulli;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_ReportesBIPUBLISHER_AC_RDM_Bara extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1.- Acceso a BI Publisher
	 * 
	 * DESCRIPCION: Validar que los reportes “Informe de Cumplimiento de Plan y Programación " e "Informe de Errores Detectados en Almacén" se encuentren en la carpeta según corresponda. 
	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ReportesBIPUBLISHER_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
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
		
		
		/*
		 * PASO 1: Ingresar a la URL de Publisher BI
		*/
	    addStep("Ingresar a la URL de Publisher BI");	
		u.getDriver().manage().deleteAllCookies();
		u.get(GlobalVariables.URL_BI_PUBLISHER);
		u.getDriver().manage().window().maximize();
		u.getDriver().manage().window().fullscreen();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(u, "PrimeraCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 2: Hacer login en la pagina con usuario y password
		*/
	    addStep("Hacer login en la pagina con usuario y password");	
	    BiPublisherInicio inicio = new BiPublisherInicio();
		inicio.loginBiPublisher(u);
		u.hardWait(30);
		testCase.addScreenShotCurrentStep(u, "SegundaCaptura");
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
		 * PASO 3: Ingresar a la siguiente ruta: Inicio > Carpetas Compartidas > Logística  ".
		*/
	    addStep("ruta: Inicio --> Carpetas Compartidas --> Logística --> Inventarios --> Conteos Cíclicos");	
	    BiPublisherMainMenu menu = new BiPublisherMainMenu();
		menu.navigateToLogistica(u);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(u, "TerceraCaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidacionConteos");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede el conteo ciclico");
		}
		 /*
		 * PASO 4: Ingresar a Inicio --> Carpetas Compartidas --> Logística --> Inventarios --> Conteos Cíclicos
		*/
	    addStep("Ingresar a cumplimiento de plan y programacion e Informe de Errores Detectados en Almacén");	
		menu.navigateToConteosCiclicos(u);
		testCase.addScreenShotCurrentStep(u, "CuartaCaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidarEliminacion");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar errores detectados en almacen");
		}
		
		/*
		 * PASO 5: Ingresar a cumplimiento de plan y programacion e Informe de Errores Detectados en Almacén
		*/
	    addStep("Ingresar a cumplimiento de plan y programacion e Informe de Errores Detectados en Almacén");	
		menu.navigateToPlanYAlmacen(u);
		testCase.addScreenShotCurrentStep(u, "Quintacaptura");
		u.hardWait(10);
		try{
			p.clickImagen("ValidarEliminacion");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar errores detectados en almacen");
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
		return "Acceso a BI Publisher";
	}

	@Override
	public String setTestDescription() {
		return "Validar que los reportes “Informe de Cumplimiento de Plan y Programación \" e \"Informe de Errores Detectados en Almacén\" se encuentren en la carpeta según corresponda. ";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ReportesBIPUBLISHER_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
