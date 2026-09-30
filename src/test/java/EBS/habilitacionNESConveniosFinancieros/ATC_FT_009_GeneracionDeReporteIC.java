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

public class ATC_FT_009_GeneracionDeReporteIC extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_GeneracionDeReporteIC_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = "MXOXXO-ICXXXXX-CENTRAL";//data.get("responsabilidad");
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
		EBSCrearConvenios cc = new EBSCrearConvenios(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al ambiente de EBS");
		
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la responsabilidad " + responsabilidad);
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		u.hardWait(3);

		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("En el apartado  MXOXXO-ICXXXXX-CENTRAL seleccionar \"Reporte IC\"");
		u.hardWait(2);
		paginaInicial.clickOnReporteDeConvenioIC();
		u.hardWait(5);
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Seleccionar los filtros correspondientes para la generación del reporte");
		cc.filtrosReporteIC();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Dar clic en el botón \"Ir\"");
		cc.clickBtnIr();
		assertTrue(cc.GetFirstValueConvenio(), "No hay resultados");
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Dar clic en exportar Excel");
		cc.clickExportar();
		Boolean validacion = cc.abrirExcel();
		assertTrue(validacion, "No se puede abrir el archivo excel");
		
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'"
				+ "2 Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' CP002"
				+ "3 Haber realizado el CP005 Aprobacion de Pagos";

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "" + "Validar que se graben los cambios de Aprobacion de Pagos" + "Para el Contrato Nuevo posterior a la"
				+ "Ejecucion de Reclasificacion " + "1 Tener asignada la responsabilidad 'MXOXXO-PNXXXXX-Super Usuario'"
				+ "2 Previamente haber ejecutado el proceso 'XXFC PN Reclasificacion de Contratos y Direcciones NEXXO' CP002"
				+ "3 Haber realizado el CP005 Aprobacion de Pagos";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JoseO@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_GeneracionDeReporteIC";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}