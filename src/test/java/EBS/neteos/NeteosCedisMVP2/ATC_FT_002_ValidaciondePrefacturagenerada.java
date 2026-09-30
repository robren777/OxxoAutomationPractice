package EBS.neteos.NeteosCedisMVP2;

import static org.junit.Assert.assertEquals;

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
 * PRERREQUISITOS: Contar con archivo csv.
 * tener layout con formato CSV 
 *
 * Validar que se carguen los registros de cargos logisticos a las tablas XXFC_PREFACTURAS, XXFC_PREFACT_DISTRIBUCIONES
 * NOTA: DEBIDO A UN MEJOR MANEJO EN VALIDACIONES SE ACTUALIZARON LOS ULTIMOS PASOS DE EL SCRIPT
 * @author ricardor4@hexaware.com
 */

public class ATC_FT_002_ValidaciondePrefacturagenerada extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidaciondePrefacturagenerada_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		By ingresosComerciales = By.xpath("//div[normalize-space()='MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS']/parent::a/following-sibling::ul//div[normalize-space()='Ingresos Comerciales']");
		By facturacion = By.xpath("//div[normalize-space()='Facturación']");
		String convenio = data.get("convenio");
		String periodo = data.get("periodo");
		
		//TABLAS A VALIDAR 
		
		String tabla1 = "SELECT ESTADO,PREFACTURA_ID,NUMERO_CONVENIO,PERIOD_NAME,PREFACTURA_FECHA FROM APPS.XXFC_PREFACTURAS WHERE NUMERO_CONVENIO ="+"'"+convenio+"'"+" AND PERIOD_NAME ="+"'"+periodo+"'";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
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
		addStep("Ingresamos a la responsabilidad MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 6
		addStep("Seleccionar la opcion: Ingresos Comerciales -> Facturacion.");
		u.hardWait(4);
		u.click(ingresosComerciales);
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(" ");
		u.click(facturacion);
		u.hardWait(30);
		p.runApplet();
		u.hardWait(40);
		// PASO 7
		addStep("Consultar Prefactura generada.");
		sk.screen.type(Key.F11);
		u.hardWait(3);
		sk.screen.type("f");
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		sk.screen.type(convenio);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		testCase.addScreenShotCurrentStep(" ");
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		u.hardWait(6);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		u.hardWait(6);
		p.nEnter(1);
		// PASO 8
		addStep("Validar prefactura aparece en pantalla Reporte IC "
				+ "Nota: en este caso se valida el estatus F en la tabla de prefacturas");
		
		
		//Validacion en tablas
		
		
		// SEGUNDA VALIDACION EN TABLAS
		
				SQLResult obtengoId = AVANTE.executeQuery(tabla1);
				String estado = obtengoId.getData(0, "ESTADO");
				
		
		addStep("Validar las tablas de prefactura.");
		    System.out.println(tabla1);
	      	SQLResult v4 = AVANTE.executeQuery(tabla1);
	        testCase.addQueryEvidenceCurrentStep(v4);
	        assertEquals(estado,"F");
			testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
			

		u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidaciondePrefacturagenerada_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se genere la prefactura, factura AR y su difitalizacion con base a la informacion que se tiene capturada del MTC-FT-001";
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
		return "Haber cargado el archivo .csv con la información de las prefacturas.";
	}

	@Override
	public void beforeTest() {

	}
}