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

public class ATC_FT_015_Enviar_la_informacion_a_ICM_Esquema_REDISENO extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_Enviar_la_informacion_a_ICM_Esquema_REDISENO_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String Proceso = data.get("StrProceso");
		String Proceso2 = data.get("StrProceso2");
		String Entidad = data.get("StrEntidad");
		String FechaInicio = data.get("StrFechaInicio");
		String FechaFin = data.get("StrFechaFin");
		String ebsPattern = data.get("ebsPattern");
		
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
		u.screenShotFull("Click en responsabilidad MXOXXO-COXXXXX-ADMINISTRADOR");

		// PASO 3
		addStep("Ingresar a la siguiente ruta:Solicitudes/Ejecutar una Nueva Solicitud/Solicitud Unica");
		paginaInicial.IngresarSolicitudUnica();
		patternUtil.runApplet();
		u.hardWait(30);
		patternUtil.nTAB(1);
		patternUtil.nEnter(1);
		u.screenShotFull("Ingresar a la siguiente ruta:Solicitudes Ejecutar una Nueva Solicitud Solicitud Unica");
		
		// PASO 4
		addStep("Buscar el siguiente proceso:  XXCO-Proceso de Carga entidades ICM");
		u.hardWait(15);
		patternUtil.typeAndTab(Proceso);
		u.hardWait(3);
		u.screenShotFull("Buscar el siguiente proceso:  XXCO Proceso de Carga entidades ICM");
		
		// PASO 5
		addStep("Capturar los parámetros:Entidad,Fecha Inicial,Fecha Final");
		patternUtil.typeAndTab(Entidad);
		patternUtil.typeAndTab(FechaInicio);
		patternUtil.typeAndTab(FechaFin);
		patternUtil.keyAlt("A");
		u.hardWait(3);
		u.screenShotFull("Capturar los parámetros:Entidad,Fecha Inicial,Fecha Final");
		
		// PASO 6
		addStep("Clic botón Ejecutar");
		patternUtil.keyAlt("j");
		u.hardWait(3);
		u.screenShotFull("Clic botón Ejecutar");
		patternUtil.keyAlt("S");
	
		
		// PASO 7
		addStep("Buscar el siguiente proceso:  XXCO-Proceso Extraccion Venta Diaria ICM");
		u.hardWait(3);
		patternUtil.typeAndTab(Proceso2);
		u.hardWait(3);
		u.screenShotFull("Buscar el siguiente proceso:  XXCO Proceso Extraccion Venta Diaria ICM");
		
		// PASO 8
		addStep("Capturar los parámetros:Fecha Inicial, Fecha Final");
		patternUtil.typeAndTab(FechaInicio);
		patternUtil.typeAndTab(FechaFin);
		patternUtil.keyAlt("A");
		u.hardWait(3);
		u.screenShotFull("Capturar los parámetros:Fecha Inicial, Fecha Final");
		
		// PASO 9
		addStep("Clic botón Ejecutar");
		patternUtil.keyAlt("j");
		u.hardWait(3);
		u.screenShotFull("Clic botón Ejecutar");
		patternUtil.keyAlt("S");
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Enviar la información a ICM mediante el concurrente: XXCO-Proceso de Carga entidades ICM para que devuelva el finiquito con la información enviada";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Enviar la información a ICM mediante el concurrente: XXCO-Proceso de Carga entidades ICM para que devuelva el finiquito con la información enviada";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "misraimH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_Enviar_la_informacion_a_ICM_Esquema_REDISENO_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

