package WorkFlows.SipreBara;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pomCargaArchivos;
import WorkFlows.util.SipreBara.pom_inicioSesionUltimusPega;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_045_CargaInputMonitoreoItems extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_045_CargaInputMonitoreoItems_Test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Workflows Library: Sipre Bara");

		/**
		 * Variables
		 * 
		 */
		String userPega = data.get("userUltimus");
		String userSipre = GlobalVariables.SIPRE_BARA_USER;
		String psSipre = GlobalVariables.SIPRE_BARA_PS;

		/**
		 * 
		 * Utiler�as
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		u.get(data.get("url_ultimus"));

		utils.sql.SQLUtil dbSipreBara = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREBARA,
				GlobalVariables.DB_USER_SIPREBARA, GlobalVariables.DB_PASSWORD_SIPREBARA);

		pom_inicioSesionUltimusPega inicioSesion = new pom_inicioSesionUltimusPega(u, testCase, userPega);
		pomCargaArchivos cargaArchivos = new pomCargaArchivos(u, testCase);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Administrador");
		inicioSesion.inicioSesion("ULTIMUS", userSipre, psSipre);
		u.hardWait(2);
		/*-----------------------------------------------------------------------------------------*/
		addStep("Posicionar el cursor en la opcion 'Configuracion' y "
				+ "despues dar clic en la opcion  'Carga de archivos'");

		cargaArchivos.selectCargaArchivo();

		/*-----------------------------------------------------------------------------------------*/
		addStep("Seleccionar la opcion MONITOREO DE ITEMS");
		cargaArchivos.selecTipoArchivo("4.- MONITOREO DE ITEMS");

		/*-----------------------------------------------------------------------------------------*/
		addStep("Subir archivo");
		String path = GlobalVariables.FILES_WF + "Files\\SipreBara\\04-Monitoreo de Items.xlsx";
		cargaArchivos.subirArchivo(path);

		u.hardWait(10);

		/*-----------------------------------------------------------------------------------------*/
		addStep("Se valida que la información subida en el input se encuentre en la consulta:\r\n"
				+ "SELECT * FROM INPUT_MONITORITEMS");
		String fechaActual = cargaArchivos.getDate();

		testCase.addTextEvidenceCurrentStep("Fecha de hoy: " + fechaActual);

		String QueryCargaArchivo = "SELECT PLAZA_CR, TIPO_MONITOREO,DESCRIPCION_ITEM, CREATED_DATE "
				+ "FROM INPUT_MONITORITEMS WHERE trunc(UPDATED_DATE) = TO_DATE('" + fechaActual
				+ "', 'DD/MM/YY') and rownum <= 5";

		SQLResult queryResultCargaArchivo = executeQuery(dbSipreBara, QueryCargaArchivo);
		testCase.addQueryEvidenceCurrentStep(queryResultCargaArchivo);

		System.out.println("resultado consulta --->" + queryResultCargaArchivo.isEmpty());

		assertFalse(queryResultCargaArchivo.isEmpty(), "La consulta no muestra resultados de carga del d�a de hoy");

	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que se pueda realizar una Carga exitosa del INPUT de \"MONITOREO DE ITEMS\" desde el usuario Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_045_CargaInputMonitoreoItems_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
