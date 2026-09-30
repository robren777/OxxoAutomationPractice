package RDM.rdmBara.ActualizacionRDMBaraCH52012965;

import static org.testng.Assert.assertTrue;

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

public class ATC_FT_010_ValidacionDeFiltrosTrunoBIPUBLISHERBD_AC_RDM_Bara extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1.- 1.-Mes, Año, CEDIS
			2.-Información conteo surtido
	 * 
	 * DESCRIPCION: Se tiene por objetivo validar que el Turno de cada registro mostrado en el reporte este correcto.
	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidacionDeFiltrosTrunoBIPUBLISHERBD_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
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
		String trunc1 = data.get("TruncDate1");
		String trunc2 = data.get("TruncDate2");
		String turno = data.get("Turno");
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
		 * PASO 8: Validar la informacion en la base de datos
		*/
	    addStep("Validar la informacion en la base de datos");
		boolean flag1=false;
	    String querty1 = "select surt.location_id, pick.item_id, item.description, surt.plan_cc_date, surt.real_cc_date, surt.turno\r\n"
	    		+ "from xxfc_cc_plan_surtido_d  surt \r\n"
	    		+ "join pick_from_loc pick on pick.location_id = surt.location_id\r\n"
	    		+ "join  item_master item on item.item_id = pick.item_id\r\n"
	    		+ "where trunc(surt.PLAN_CC_DATE) >=  to_date('"+ trunc1 +"','DD/MM/YYYY') \r\n"
	    		+ "and  trunc(surt.PLAN_CC_DATE) < = to_date('"+ trunc2 +"','DD/MM/YYYY') AND ROWNUM <= 50\r\n";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		if(!resultadoContenedor1.isEmpty())
	    	flag1=true;
	    assertTrue(flag1, "El registro existe en la BD");
		u.hardWait(5);
	    
	    /*
		 * PASO 9: Seleccionar un turno y ver
		*/
	    addStep("Seleccionar un turno y ver");
	    reportes.generateReporteByTurno(u, turno);
		testCase.addScreenShotCurrentStep(u, "OctavaCaputra");
		u.hardWait(10);
		u.close();
	    try{
			p.clickImagen("ValidarTurno1");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar el filtro turno1.");
		}
		 /*
		 * PASO 10: Validar la informacion en la base de datos
		*/
		boolean flag=false;
	    addStep("Validar la informacion en la base de datos");
	    String querty2 = "select surt.location_id, pick.item_id, item.description, surt.plan_cc_date, surt.real_cc_date, surt.turno\r\n"
	    		+ "from xxfc_cc_plan_surtido_d  surt \r\n"
	    		+ "join pick_from_loc pick on pick.location_id = surt.location_id\r\n"
	    		+ "join  item_master item on item.item_id = pick.item_id\r\n"
	    		+ "where trunc(surt.PLAN_CC_DATE) >=  to_date('"+ trunc1 +"','DD/MM/YYYY') \r\n"
	    		+ "and  trunc(surt.PLAN_CC_DATE) < = to_date('"+ trunc2 +"','DD/MM/YYYY') and TURNO LIKE '"+ turno+"' AND ROWNUM <= 50";
	    utils.sql.SQLResult resultadoContenedor2= dbBARAqa.executeQuery(querty2);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor2);
	    if(!resultadoContenedor2.isEmpty())
	    	flag=true;
	    assertTrue(flag, "El registro existe en la BD");
		u.hardWait(5);
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
		return "1.-Mes, Año, CEDIS\r\n"
				+ "2.-Información conteo surtido";
	}

	@Override
	public String setTestDescription() {
		return "Se tiene por objetivo validar que el Turno de cada registro mostrado en el reporte este correcto.";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_ValidacionDeFiltrosTrunoBIPUBLISHERBD_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
