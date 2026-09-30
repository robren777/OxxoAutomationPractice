package EBS.neteos.NeteosServiciosCorresponsalias;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: Tener en existencia ITEMS en la tabla de Portal de Servicios con status 0 y tipo CORRES 
 *
 * DESCRIPCION: El reporte debe mostrar todos los items de servicio que se encuentran en la tabla de Portal de Servicios con status 0
				y tipo CORRES vs la informacion de la vista de item de servicios de RMS
				
				Nota no ejecutar el mismo dia que el script:ATC_FT_005_XXFComisionesRetekvsPortalconparametro
				
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_004_XXFComisionesRetekvsPortalsinparametro extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_XXFComisionesRetekvsPortalsinparametro_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
		String NumeroDeItems = "SELECT count(*) contador FROM   xxfc_retek_item_servicios_v xis , ( SELECT ms.ms_valcom, ms.ms_servid FROM   xxfc.xxpso_m_service ms  WHERE  ms.status = 0 AND  ms.ms_type = 'CORRES' AND unique_id  IN ( SELECT MAX(unique_id) FROM  xxfc.xxpso_m_service WHERE  ms_type = 'CORRES' AND status = 0  GROUP BY ms_servid  )   ) xps   WHERE 1=1  AND   xis.item = xps.ms_servid   ORDER BY xis.item";
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresamos a la responsabilidad MXOXXO-GL17ADM-SERVICIOS-ADTIVO-OS");
		 try {
				
		    	paginaInicial.clickOnResponsabilidad(responsabilidad);
						
						}
						catch (Exception e) {
							
							throw new AssertionError("Error al invocar responsabilidad" + e);
						}
		u.hardWait(3);
		paginaInicial.clickInformesSolicitarServicios();
		paginaInicial.clickStandard();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 6
		addStep("Dirigirse a --> Solicitud Unica");
		testCase.addScreenShotCurrentStep(" ");
		
		 try {
				
				p.nEnter(1);
				u.hardWait(5);
		  
						}
						catch (Exception e) {
							
							throw new AssertionError("Error al invocar metodo" + e);
						
						}
		
		// PASO 7
	    addStep("Ingresar el  concurrente "
	    		+ " y seleccionarlo");
	    
	    try {
			
	    	p.enterText(concurrente);
	  
					}
					catch (Exception e) {
						
						throw new AssertionError("Error al invocar metodo" + e);
					
					}
	    
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(6);
	    testCase.addScreenShotCurrentStep(" ");
		// PASO 8
	    addStep("Dar clic en Aceptar y Ejecutar");
	    p.nEnter(2);
	    u.hardWait(4);
	    p.nEnter(2);
	    u.hardWait(12);
	    p.clickEjecutar();
	    u.hardWait(12);
	    p.clickNo();
	    u.hardWait(30);
	    p.vers();
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep(" ");
	    p.clickEncontrar();
	    u.hardWait(30);
	    assertTrue(p.validarEstadoFinal());
	    u.hardWait(20);
	    p.clickVisualizarOutput();
        u.hardWait(30);
		int excel = paginaInicial.ObtenerNumeroRenglones();
	    SQLResult executeQuery = executeQuery(AVANTE,NumeroDeItems);
		String consulta = "";
	    consulta= executeQuery.getData(0,0);
	    u.hardWait(5);
	    // PASO 8
	    addStep("Se valida el reporte debe muestra todos los items de servicio");
	    System.out.println("Consulta:  "+consulta+"    Excel:  "+excel);
	    
	    try {
			
	        assertEquals(consulta,""+excel);
	        u.hardWait(10);
	        testCase.addTextEvidenceCurrentStep("Se valida que los items coinciden con el servicio");
	        testCase.addCodeEvidenceCurrentStep("items en base de datos: "+consulta+"  items en reporte: "+excel);
		     
			}
			  catch (Exception e) {
							
			throw new AssertionError("Error al validar los items del servicio no coinciden " + e);
			 }
	    
		u.close();

		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_XXFComisionesRetekvsPortalsinparametro_test";
	}

	@Override
	public String setTestDescription() {
		return "El reporte debe mostrar todos los items de servicio que se encuentran en la tabla de Portal de Servicios con status 0"
				+ " y tipo CORRES vs la informacion de la vista de item de servicios de RMS.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener en existencia ITEMS en la tabla de Portal de Servicios con status 0 y tipo CORRES";
	}

	@Override
	public void beforeTest() {

	}
}
