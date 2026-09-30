package EBS.Mtto_On_Demand_CHOG0050529;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Mtto_On_Demand_CHOG0050529.OM.Mtto_On_Demand;
import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_022_Guarda_datos_5years extends BaseExecution{
	@Test(dataProvider = "data-provider")
	
	/**
	 * DESCRIPCION: Se borra todo lo que es mas antiguo a 1825 dias que seria 5 años
	 * 
	 * PREREQUISITOS:
	 * - Requiere modificar le fecha que ya haya trancurrido 5 años a una solicitud con estatus S (soicitud ya procesada)
	 * - Identificar el ID a modificar
	 *
	 * INSUMOS:
	 * - IDSolicitudIMA 
	 *
	 * @author Michelle Jesús Vásquez Vivar
	 * @date 01/Marzo/2025
	*/
	public void ATC_FT_022_Guarda_datos_5years_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Mtto_On_Demand");
		/*
		 * VARIABLES
		 */
		int shortWait = 2000;
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String campo = data.get("campo");
		String ruta = data.get("archivo");
		String organizacion = data.get("organizacion");
		String concurrente = data.get("concurrente");
		String query = data.get("query");
		String userDB = data.get("userDB");
		String PassDB = PasswordUtil.decryptPassword(data.get("PassDB"));
		String idIMA = data.get("idIMA");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);		
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		Mtto_On_Demand mtoDema = new Mtto_On_Demand(u, testCase);				
		
		// PASO 1  1. Acceso a EAM
		addStep("1.	Acceso a EAM");				
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();			
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaWorkOrdersElement.click();		
		paginaInicial.workOrdersSubElement.click();
		u.hardWait(1);
		mtoDema.runApplet();
		u.hardWait(3);
		mtoDema.selecOrganizacion(organizacion);
		u.hardWait(5);
		mtoDema.cerrarVenOrga();	
		
		// PASO 2 Se ejecuta con el nombre del concurre XXFC - Proceso de generación de OT en EAM (origen IMA)
		addStep("2. Se ejecuta con el nombre del concurre XXFC - Proceso de generacion de OT en EAM (origen IMA)");		
		mtoDema.ejecutarConcurrenteXXFCProcesoGeneracionOTEAM(concurrente);
		mtoDema.validarEjecConcurrenteEBS(concurrente);
		mtoDema.validarEstatusConcurrente(concurrente);	
		
		// PASO 3 Error, no esta configurado el tipo de cr. Error, no esta configurado la organización
		addStep ("3. Validar informacion en la BD");
		assertFalse(mtoDema.obSoliIMA(idIMA),"No valido estatus OT");		

	}	

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Contar con acceso a la BD de EBS\r\n"
		+ "		2.- Acceso a EAM\r\n"
		+ "		3.- Modificar informacion en la BD, en la columna CREATION_DATE con fecha que ya hayan pasado 5 años";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se borra todo lo que es mas antiguo a 1825 dias que seria 5 años";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "michelle.vasquez@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATCF_T_022_Guarda_datos_5years_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
