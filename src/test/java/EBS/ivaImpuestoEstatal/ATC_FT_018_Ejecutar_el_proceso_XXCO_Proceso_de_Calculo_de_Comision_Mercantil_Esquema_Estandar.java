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

public class ATC_FT_018_Ejecutar_el_proceso_XXCO_Proceso_de_Calculo_de_Comision_Mercantil_Esquema_Estandar extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_Ejecutar_el_proceso_XXCO_Proceso_de_Calculo_de_Comision_Mercantil_Esquema_Estandar_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String Proceso = data.get("StrProceso");
		String UnidadOperativa = data.get("StrUnidadOperativa");
		String FechaInicio = data.get("StrFechaInicio");
		String FechaFin = data.get("StrFechaFin");
		String FechaContable = data.get("StrFechaContable");
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
		//**Nota: Se requiere colocar los datos(Parametros) de prueba apropiados para validar el caso.

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
		u.hardWait(20);
		patternUtil.nTAB(1);
		patternUtil.nEnter(1);
		u.screenShotFull("Ingresar a la siguiente ruta:Solicitudes Ejecutar una Nueva Solicitud Solicitud Unica");
		
		// PASO 4
		addStep("Ejecutar proceso: XXCO-Proceso de Calculo de Comisión Mercantil");
		u.hardWait(5);
		patternUtil.typeAndTab(Proceso);
		u.hardWait(10);
		u.screenShotFull("Ejecutar proceso: XXCO-Proceso de Calculo de Comision Mercantil");
		
		// PASO 5
		addStep("Ingresa los valores de los parámetros de ejecución:Fecha Inicial,Fecha Final,Fecha Contable,Tienda-Comisionista");
		//patternUtil.typeAndTab(UnidadOperativa);
		patternUtil.keyAlt("A");	
		u.hardWait(3);
		patternUtil.typeAndTab(FechaInicio);
		u.hardWait(3);
		patternUtil.typeAndTab(FechaFin);
		u.hardWait(3);
		patternUtil.typeAndTab(FechaContable);
		patternUtil.keyAlt("A");
		u.hardWait(5);
		u.screenShotFull("Ingresa los valores de los parámetros de ejecución:Fecha Inicial,Fecha Final,Fecha Contable,Tienda-Comisionista");
		
		// PASO 6
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
		return "Validar que al ejecutar el proceso deberá calcular el IVA del impuesto estatal, " +
				"este IVA se deberá agregar sobre la línea ya existente IVA MERMA 16% este IVA corresponde al interior de la república, " +
				"en caso del IVA MERMA 8% este IVA será en frontera. El IVA del 16% será en el resto de la república y el IVA del 8% en frontera.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al ejecutar el proceso deberá calcular el IVA del impuesto estatal, " +
				"este IVA se deberá agregar sobre la línea ya existente 'IVA MERMA 16%' este IVA corresponde al interior de la república, " +
				"en caso del 'IVA MERMA 8%' este IVA será en frontera." +
				"El IVA del 16% será en el resto de la república y el IVA del 8% en frontera.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "misraimH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_018_Ejecutar_el_proceso_XXCO_Proceso_de_Calculo_de_Comision_Mercantil_Esquema_Estandar_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

