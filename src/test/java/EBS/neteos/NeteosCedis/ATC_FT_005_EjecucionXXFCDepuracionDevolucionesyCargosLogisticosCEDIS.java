package EBS.neteos.NeteosCedis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Key;
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
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX
 * tener ordenes con devoluciones y cargos logisticos
 *
 * DESCRIPCION: realiza una depuracion de devoluciones en la tabla XXFC.XXFC_XP_INVOICES_INTERFACE 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_005_EjecucionXXFCDepuracionDevolucionesyCargosLogisticosCEDIS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_EjecucionXXFCDepuracionDevolucionesyCargosLogisticosCEDIS_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		By carpetaOtros = By.xpath(
				"//div[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Otros']");
		String concurrente = data.get("concurrente");
		
		// BASES DE DATOS
		
		String tabla1 = "select INVOICE_ID,INVOICE_NUM,PO_NUMBER,STATUS,SOURCE from XXFC.XXFC_XP_INVOICES_INTERFACE";
		String tabla2 = "select INVOICE_ID,INVOICE_LINE_ID,AMOUNT,DESCRIPTION from XXFC.XXFC_XP_INVOICE_LINES_INTER";
		String tabla3 = "select INTERFACE_SALESCREDIT_ID,INTERFACE_LINE_ID,INTERFACE_LINE_CONTEXT,INTERFACE_LINE_ATTRIBUTE1 from XXFC.XXFC_XP_INTERFACE_SALESCREDITS";
		String tabla4 = "select INTERFACE_LINE_ID,INTERFACE_LINE_CONTEXT,INTERFACE_LINE_ATTRIBUTE1,INTERFACE_LINE_ATTRIBUTE2,INTERFACE_LINE_ATTRIBUTE5 from XXFC.XXFC_XP_INTERFACE_LINES";
		

		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		

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
		inicio.login();
		// PASO 5
		addStep("Ingresamos a la responsabilidad MXOXXXO-APXXXXX-Super Usuario");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Seleccionar Otros");
		u.scrollToElement(carpetaOtros);
		paginaInicial.clickOnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(u,"Seleccionar aplicativo");
		paginaInicial.clickRunConcurrent();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(6);
		p.runApplet();
		u.hardWait(50);
		testCase.addScreenShotCurrentStep(" ");
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(6);
		// PASO 6
		addStep("Ejecutamos el concurrente XXFC Depuracion Devoluciones y Cargos Logisticos CEDIS");
		testCase.addScreenShotCurrentStep(" ");
		p.enterText(concurrente);
		u.hardWait(3);
		// PASO 7
		addStep("Damos click en ejecutar.");
		p.clickEjecutar();
		testCase.addScreenShotCurrentStep(" ");
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		p.clickNo();
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		u.hardWait(3);
		// PASO 8
		addStep("Validar que los registros de devolucion correspondientes al periodo ejecutado hayan sido depurados en las tablas conforme al periodo de la lista de valores XXXXXXXXX declarada: XXFC_AP_INVOICES_INTERFACE, XXFC_AP_INVOICES_LINES_INTERFACE, XXFC_RA_INTERFACE_SALESCREDITS_ALL y XXFC_RA_INTERFACE_LINES_AL");
		
		// VALIDAR TABLAS
				
				System.out.println(tabla1);
				SQLResult validat1 = AVANTE.executeQuery(tabla1);
	
					testCase.addQueryEvidenceCurrentStep(validat1);
					testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de devolucion");
	
				
				//-------------------------------------------------------------------------------------------
	
				System.out.println(tabla2);
				SQLResult validat2 = AVANTE.executeQuery(tabla2);

					testCase.addQueryEvidenceCurrentStep(validat2);
					testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de devolucion");
				
				//-------------------------------------------------------------------------------------------
				
				System.out.println(tabla3);
				SQLResult validat3 = AVANTE.executeQuery(tabla3);
				
				
					testCase.addQueryEvidenceCurrentStep(validat3);
					testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de devolucion");

				
                //-------------------------------------------------------------------------------------------
				
				System.out.println(tabla4);
				SQLResult validat4 = AVANTE.executeQuery(tabla4);
				boolean valida4 = validat4.isEmpty();

					testCase.addQueryEvidenceCurrentStep(validat4);
					testCase.addTextEvidenceCurrentStep("Validacion Exitosa Se depuraron los registros de devolucion");
					sk.screen.keyDown(Key.F4);
					sk.screen.keyUp(Key.F4);
					sk.screen.keyDown(Key.F4);
					sk.screen.keyUp(Key.F4);
					u.hardWait(3);
					sk.screen.type(Key.ENTER);
		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_EjecucionXXFCDepuracionDevolucionesyCargosLogisticosCEDIS_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice la depuracion de registros de devolucion en las tablas stage de la interface de devoluciones";
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
		return "Validar que la lista de valores: cargos_conceptos se encuentre"
				+ "Deben existir registros de devolucion en las tablas XXFC_AP_INVOICES_INTERFACE, XXFC_AP_INVOICES_LINES_INTERFACE, XXFC_RA_INTERFACE_SALESCREDITS_ALL y XXFC_RA_INTERFACE_LINES_ALL";
	}

	@Override
	public void beforeTest() {

	}
}