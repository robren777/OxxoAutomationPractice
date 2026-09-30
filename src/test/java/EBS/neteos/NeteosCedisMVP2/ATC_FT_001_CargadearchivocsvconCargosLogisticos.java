package EBS.neteos.NeteosCedisMVP2;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSCargaArchivos;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: tener Reporte XXFC - Reporte Cedis de Cargos Logisticos con información de convenios
 * tener layout con formato CSV con informacion de Reporte Cedis de Cargos Logisticos
 *
 * Cargar csv con informacion de cargos logisticos y validar tablas de prefactura XXFC_PREFACTURAS, XXFC_PREFACT_DISTRIBUCIONES
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_001_CargadearchivocsvconCargosLogisticos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CargadearchivocsvconCargosLogisticos_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String filepath = data.get("filepath");
		String proceso = data.get("proceso");
		By informes = By.xpath("//div[text()='Informes']");
		By otros = By.xpath("//div[normalize-space()='Otros']");
		
		String convenio = data.get("convenio");
		String periodo = data.get("periodo");
		
		//TABLAS A VALIDAR 
		
		String tabla1 = "SELECT PREFACTURA_ID,NUMERO_CONVENIO,PERIOD_NAME,PREFACTURA_FECHA FROM APPS.XXFC_PREFACTURAS WHERE NUMERO_CONVENIO ="+"'"+convenio+"'"+" AND PERIOD_NAME ="+"'"+periodo+"'";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSCargaArchivos archivosTool = new EBSCargaArchivos(u, testCase);
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
		
		
		// SEGUNDA VALIDACION EN TABLAS
		
		SQLResult obtengoId = AVANTE.executeQuery(tabla1);
		String Id = obtengoId.getData(0, "PREFACTURA_ID");
		String tabla2 = "SELECT PREFACT_DISTRIBUCION_ID,PREFACTURA_ID,ID_SUPERIOR,ID_TIENDA,IMPORTE,CREATION_DATE FROM APPS.XXFC_PREFACT_DISTRIBUCIONES WHERE PREFACTURA_ID ="+"'"+Id+"'";
		
		
		
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
		addStep("Ingresar a  la opcion: Ingresos Comerciales -> Carga Cargos Logisticos.");
		paginaInicial.clickOnCarpeta(carpeta);
		testCase.addScreenShotCurrentStep(" ");
		paginaInicial.cargarCargosLogisticos();
		// PASO 7
		addStep("En la opcion 'Archivo a Cargar' elegimos el archivo csv");
		archivosTool.selectFileCurrentProject(filepath);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 8
		addStep("Seleccionar el proceso XXFC - Ingresos Comerciales Creación Prefacturas Automatizadas");
		archivosTool.procesoElement.sendKeys(proceso);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 9
		addStep("Dar clic en el boton de Cargar");
		archivosTool.btnCargarElement.click();
		testCase.addScreenShotCurrentStep(" ");
		// PASO 10
		addStep("Dirigirse al menu principal de la responsabilidad y seleccionar: Informes -> Otros -> Cerrar la venta.");
		paginaInicial.home();
		u.waitForLoadPage();
		u.click(informes);
		u.click(otros);
		u.hardWait(20);
		p.runApplet();
		u.hardWait(80);
		testCase.addScreenShotCurrentStep(" ");
		p.keyAlt("c");
		u.hardWait(10);
		// PASO 11
		addStep("Seleccionar el menú: Ver(x) -> Solicitudes -> Encontrar.");
		p.vers();
		testCase.addScreenShotCurrentStep(" ");
		// PASO 12
		addStep("Dar clic en Visualizar Detalles");
		p.clickEncontrar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(7);
		p.clickVisualizarDetalles();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(7);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		// PASO 13
		addStep("Validar las tablas de prefactura.");
		
		 System.out.println(tabla1);
	      	SQLResult v4 = AVANTE.executeQuery(tabla1);
	        testCase.addQueryEvidenceCurrentStep(v4);
	        

			SQLResult validat = AVANTE.executeQuery(tabla2);
			boolean validaB = validat.isEmpty();
			if (!validaB) {
				testCase.addQueryEvidenceCurrentStep(validat);
			}
			assertFalse("Consulta invalida ", validaB);
			System.out.println(validaB);
			testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
			
			sk.screen.keyDown(Key.F4);
			sk.screen.keyUp(Key.F4);
			u.hardWait(6);
			sk.screen.keyDown(Key.F4);
			sk.screen.keyUp(Key.F4);
			u.hardWait(6);
			p.nEnter(1);
			u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_CargadearchivocsvconCargosLogisticos_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se carguen los registros de cargos logisticos a las tablas XXFC_PREFACTURAS, XXFC_PREFACT_DISTRIBUCIONES.";
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
		return "Contar con la informacion de los Cargos Logísticos obtenidos del Reporte XXFC - Reporte Cedis de Cargos Logisticos";
	}

	@Override
	public void beforeTest() {

	}
}
