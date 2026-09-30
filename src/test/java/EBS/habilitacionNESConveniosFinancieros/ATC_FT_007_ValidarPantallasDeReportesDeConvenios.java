package EBS.habilitacionNESConveniosFinancieros;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSCrearConvenios;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_007_ValidarPantallasDeReportesDeConvenios extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ValidarPantallasDeReportesDeConvenios_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String reporte = data.get("reporte");
		String tipoNegociacion = data.get("tipoNegocio");
		String ebsPattern = data.get("ebsPattern");
		boolean defaultVal = false;

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
		EBSCrearConvenios convenioUnico = new EBSCrearConvenios(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		addStep("Ingresar al ambiente de EBS");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		// PASO 1
		addStep("Seleccionar la responsabilidad de " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 2
		addStep("Dar clic en el menu:" + responsabilidad + ">" + reporte);
		u.hardWait(5);
		// PASO 3
		addStep("Seleccionar los filtros correspondientes");
		paginaInicial.clickOnReporteDeConvenio();
		u.hardWait(5);
		// PASO 4
		addStep("Dar click en el boton IR");
		defaultVal = convenioUnico.GetConvenioResult();
		u.hardWait(10);
		convenioUnico.IngresarReporteConvenio(tipoNegociacion, userName);
		u.hardWait(10);
		System.out.println("--" + defaultVal + "--" + convenioUnico.GetFirstValueConvenio() );
		//assertEquals(defaultVal, convenioUnico.GetFirstValueConvenio());
		assertTrue(convenioUnico.GetFirstValueConvenio(), "No hay resultados");
		u.scroll(500);
		u.hardWait(5);
		u.screenShotFull("Tabla de resultados con los Convenios Existentes");
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1 Contar con la responsabilidad MXOXXO-ICXXXXX-CENTRAL.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Las pantallas de los reportes de convenios en los filtros se deben mostrar las opciones "
				+ "de segmentacion maestro, potencial, overlay"
				+ " 1 Contar con la responsabilidad MXOXXO-ICXXXXX-CENTRAL.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_ValidarPantallasDeReportesDeConvenios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}