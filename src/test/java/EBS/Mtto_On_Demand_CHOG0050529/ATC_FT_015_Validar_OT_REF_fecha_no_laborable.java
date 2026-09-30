package EBS.Mtto_On_Demand_CHOG0050529;

import static org.testng.Assert.assertTrue;

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

public class ATC_FT_015_Validar_OT_REF_fecha_no_laborable extends BaseExecution{
	@Test(dataProvider = "data-provider")
	/**
	 * DESCRIPCION: Validar que se genere la OT en categoria REF, que tenga mas de 30 días, y que sea fecha no laborable
	 * 
	 * PREREQUISITOS:
	 * - Insertar información DOMI, con organizaciones fuera del alcance"
	 * INSERT INTO xxfc_eam_otma_all (CR_TIENDA, ASSET_NUMBER, ID_SOLICITUD_IMA, FECHA_INI_PROG, FECHA_FIN_PROG) 
VALUES ('10VCZ5018M','REF - 10VCZ5018M',100017,TO_DATE('08/11/2024','DD/MM/RRRR'),TO_DATE('19/01/2025','DD/MM/RRRR'));
	 *
	 * INSUMOS:
	 * - IDSolicitudIMA 
	 *
	 * @author Michelle Jesús Vásquez Vivar
	 * @date 01/Marzo/2025
	 */
	public void ATC_FT_015_Validar_OT_REF_fecha_no_laborable_test(HashMap<String, String> data) throws Exception {
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
		
		// PASO 2 Validar Super Usurio
		addStep("Validar Super Usurio");
		mtoDema.validaAsignacion();		
		
		// PASO 3 Se ejecuta con el nombre del concurre XXFC - Proceso de generacion de OT en EAM (origen IMA)
		addStep ("Se ejecuta con el nombre del concurre XXFC - Proceso de generacion de OT en EAM (origen IMA)");
		mtoDema.ejecutarConcurrenteXXFCProcesoGeneracionOTEAM(concurrente);
		mtoDema.validarEjecConcurrenteEBS(concurrente);
		mtoDema.validarEstatusConcurrente(concurrente);				
		
		// Paso 4 Revisar OT que se generaron en el aplicativo
		addStep("Revisar OT que se generaron en el aplicativo");		
		mtoDema.cerraVenSolicitud();
		//mtoDema.veryOTMto(idIMA);
		assertTrue(mtoDema.veryOTMto(idIMA),"No se valido la OT generada por el Concurrente");
        
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
		+ "		3.- Insertar información DOMI, con organizaciones fuera del alcance";
	}	
	
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se genere la OT en categoria REF, que tenga mas de 30 días, y que sea fecha no laborable, la OT debe de generarse con la proxima fecha laborable pero sin pasar los 90 días ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "michelle.vasquez@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_015_Validar_OT_REF_fecha_no_laborable_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
