package EBS.neteos.NeteosIngresosComerciales;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
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
 * PRERREQUISITOS: tener acceso a EBS
 * 
 *
 * DESCRIPCION: Validar que en la pantalla de Creacion de convenios, al marcar la opción de Nota de Credito en Documento fiscal
 * unicamente se pueda marcar la Forma de pago: Folio de Pago.
 * 	
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_006_ValidarformadepagoclientesNotadeCredito extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarformadepagoclientesNotadeCredito_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		By labeltransferencia = By.xpath("//span[@id='rgFormaPago']//label//span[text()='Transferencia']");		
		By radionota = By.xpath("//span[@id='rgDocFiscal']//input[@id='M__Idg']");
		By labelfoliopago = By.xpath("//span[@id='rgFormaPago']//label//span[text()='Folio de Pago']");

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
		u.hardWait(10);
		// PASO 5
		addStep("Ingresar en la responsabilidad MXOXXO-ICXXXXX-CENTRAL");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 6
		addStep("Ingresar la opcion: Creacion Convenios -> clic boton Crear.");
		paginaInicial.clickCreacionConvenios2();
		testCase.addScreenShotCurrentStep(" ");
		paginaInicial.clickCreacionConvenios();
		testCase.addScreenShotCurrentStep(" ");
		u.waitForLoadPage();
		// PASO 7
		addStep("Seleccionar la opcion: Nota de Credito.");
		u.scroll();
		u.hardWait(3);
		u.click(radionota);
		testCase.addScreenShotCurrentStep(" ");
		u.highLight(labelfoliopago);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		if(u.highLight(labelfoliopago).isEnabled())
		{

			assertEquals(u.getText(labelfoliopago),"Folio de Pago");
        	testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
			
		}	
		
		else if (u.highLight(labeltransferencia).isEnabled())
			
		{
			testCase.addBoldTextEvidenceCurrentStep("Error No deberia de aparece el campo transferencia");
			assertTrue(false);
		}
		
		 u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidarformadepagoclientesNotadeCredito_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en la pantalla de Creacion de convenios, al marcar la opción de Nota de Credito en Documento fiscal"
				+ "unicamente se pueda marcar la Forma de pago: Folio de Pago.";
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
		return "1.- Acceso a la pantalla de Creacion de convenios.";
	}

	@Override
	public void beforeTest() {

	}
}
