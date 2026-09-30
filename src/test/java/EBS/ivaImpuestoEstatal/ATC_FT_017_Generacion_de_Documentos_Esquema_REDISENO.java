package EBS.ivaImpuestoEstatal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_017_Generacion_de_Documentos_Esquema_REDISENO extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_Generacion_de_Documentos_Esquema_REDISENO_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String Proceso = data.get("StrProceso");
		String ebsPattern = data.get("ebsPattern");
		String Plaza = data.get("strPlaza");
		String Distrito = data.get("strDistrito");
		String Tienda = data.get("strTienda");
		String RFC = data.get("strRFC");
		String FechaInicial = data.get("strFechaInicial");
		
		
		
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
	
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		GenericMethods action = new GenericMethods(u);
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a la siguiente liga: http://fcrebsqa.femcom.net:8011/");
		inicio.login();

		// PASO 2
		addStep("Ingresar a EBS con la responsabilidad MXOXXO-COXXXXX-ADMINISTRADOR");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.screenShotFull("Ingresar a EBS con la responsabilidad MXOXXO COXXXXX ADMINISTRADOR");
		

		// PASO 3
		addStep("Ingresar a la siguiente ruta: Nuevo sistema ICM Generacion de Documentos y Reversa");
		paginaInicial.IngresarMenuGeneracionDocumentosReversa();
		u.screenShotFull("Ingresar a la siguiente ruta: Nuevo sistema ICM Generacion de Documentos y Reversa");
		
		// PASO 4
		addStep("Capturar parámetros de busqueda:Plaza, Distrito, Tienda, R.F.C, Nombre Comisionista, Fecha,Tipo de Documento");
		
	
		// PASO 5
		addStep("Clic en el botón IR");
		paginaInicial.capturarParametrosGeneracionDocumentosReversa(Plaza, Distrito, Tienda, RFC, FechaInicial);
		u.screenShotFull("Clic en el botón IR");
		
				
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Validar que en la pantalla muestre el comisionista y el periodo con el mismo monto a pagar que envío ICM";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en la pantalla muestre el comisionista y el periodo con el mismo monto a pagar que envío ICM";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "misraimH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_Generacion_de_Documentos_Esquema_REDISENO_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

