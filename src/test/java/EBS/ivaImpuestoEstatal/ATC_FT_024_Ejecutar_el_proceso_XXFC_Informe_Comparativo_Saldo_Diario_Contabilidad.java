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

public class ATC_FT_024_Ejecutar_el_proceso_XXFC_Informe_Comparativo_Saldo_Diario_Contabilidad extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_024_Ejecutar_el_proceso_XXFC_Informe_Comparativo_Saldo_Diario_Contabilidad_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String Proceso = data.get("StrProceso");
		String TipoReporte = data.get("StrTipoReporte");
		String FechaCorte = data.get("StrFechaCorte");
		
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
		addStep("Ingresar a EBS con la responsabilidad MXOXXO-GLXXXXX-Super Usuario");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.screenShotFull("Click en responsabilidad MXOXXOGLXXXXX-Super Usuario");

		// PASO 3
		addStep("Ingresar a la siguiente ruta: Ver solicitudes Ejecutar Solicitud");
		paginaInicial.EjecutarSolicitud();
		patternUtil.runApplet();
		patternUtil.clickPattern("nuevasolicitud", 60);
		u.screenShotFull("Ingresar a la siguiente ruta: Ver solicitudes Ejecutar Solicitud");
		
		// PASO 4
		addStep(" Ejecutar proceso: XXFC Informe Comparativo Saldo Diario  Contabilidad");
		patternUtil.typeAndTab(Proceso);
		u.hardWait(5);
		patternUtil.keyAlt("A");
		u.hardWait(5);
		u.screenShotFull("Ejecutar proceso: XXFC Informe Comparativo Saldo Diario  Contabilidad");
		
		// PASO 5
		addStep("Ingresa los valores de los parámetros de ejecución:Tipo Reporte, Fecha Corte");
		sk.screen.type(TipoReporte);
		u.hardWait(3);
		sk.screen.type(FechaCorte);
		u.hardWait(3);
		patternUtil.keyAlt("A");
		u.screenShotFull("Ingresa los valores de los parámetros de ejecución:Plaza,Tienda");
		
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
		return "Validar que este proceso mantenga su funcionalidad no sea afectada.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que este proceso mantenga su funcionalidad no sea afectada.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "misraimH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_024_Ejecutar_el_proceso_XXFC_Informe_Comparativo_Saldo_Diario_Contabilidad_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

