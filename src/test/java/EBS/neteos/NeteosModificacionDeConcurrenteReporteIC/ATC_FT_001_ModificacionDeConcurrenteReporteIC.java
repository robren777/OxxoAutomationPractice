package EBS.neteos.NeteosModificacionDeConcurrenteReporteIC;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * CHG0053976 - HU05 / MVP2
 * MTC-FT-001	Ejecución correcta del concurrente "Reporte IC".
 * 
 * PRERREQUISITOS: 
 *	1. Contar con la responsabilidad "MXOXXO-ICXXXXX-CENTRAL".
 *
 * Validar la correcta visualizacion de la información en la pantalla "Reporte IC"
 * @author ernestoh@hexaware.com
 */

public class ATC_FT_001_ModificacionDeConcurrenteReporteIC extends BaseExecution {
 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ModificacionDeConcurrenteReporteIC_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombreSolicitud = data.get("nombreSolicitud");
		String fechaFacturacionInicio= data.get("fechaFacturacionInicio");
		String fechaFacturacionFin= data.get("fechaFacturacionFin");
		
		String pathSystem = System.getProperty("user.home");
			
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
		System.out.println("ANTES LOGIN");
		inicio.login();		
		System.out.println("DESPUES LOGIN");	
		
		// PASO 5
		addStep("Ingresamos a la responsabilidad MXOXXO-ICXXXXX-CENTRAL");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		
		// PASO 6
		addStep("Seleccionar la opcion: Reporte IC");
		u.hardWait(4);
		paginaInicial.clickReporteIC();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(15);
			
		// PASO 7
		addStep("Ingresa parametros y da clic en ir");
		paginaInicial.ingresaParametrosReporteIC(fechaFacturacionInicio,fechaFacturacionFin);
		testCase.addScreenShotCurrentStep(" ");

		// PASO 8
		u.hardWait(10);
		addStep("Valida las nuevas columnas del reporte \"Serie Folio\", \"Fecha Timbrado\", \"Uuid\", \"Uuid Timbrado\", \"Estatus Timbrado\"");
		for(int i=0;i<59;i++) {
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			}
		u.hardWait(2);
		for(int i=0;i<160;i++) {
			sk.screen.type(Key.RIGHT);
			}
		assertEquals(true,paginaInicial.validaNuevasColumnasReporteIC());
		testCase.addScreenShotCurrentStep(" ");
		
		u.quit();
			
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ModificacionDeConcurrenteReporteIC_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar la correcta visualizacion de la información en la pantalla \"Reporte IC\"";
	}

	@Override
	public String setTestDesigner() {
		return "ernestoh@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con la responsabilidad \"MXOXXO-ICXXXXX-CENTRAL\".";
	}

	@Override
	public void beforeTest() {

	}
}