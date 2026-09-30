package EBS.wfDeOC_Entregable2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import modelo.BaseExecution;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_XXFC_PO_WFOC_SincronizacionOracleSap_PuestoVacante extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_XXFC_PO_WFOC_SincronizacionOracleSap_PuestoVacante_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombre = data.get("nombre");
		String nombre2 = data.get("nombre2");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a EBS AVEBQA");
		logger.info("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		inicio.login();

		// PASO 2
		addStep("Ingresar a la Responsabilidad MXOXXO-POXXXXX-Super Usuario.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 3
		addStep("Ingresar a la pantalla de Ejecucion de concurrentes: ");
		paginaInicial.runSolicitudesElement.click();
		u.hardWait(7);
		p.clickNuevaSolicitud();
		p.ingresarNombreAcentos(nombre, nombre2);
		p.ejecutar();
		p.enter();
		p.otraSolicitud();
		p.encontrar();

		// PASO 4
		addStep("Validar que la ejecucion del concurrente finalice de manera Normal.");
		p.estadoNormal();

		// PASO 5
		addStep("Validar el que en el Output se muestre la informacion de las actualizaciones que se realizaron con la ejecucion del proceso. ");
		p.visualizaRegistro();
		u.hardWait(3);
		p.cerrarNavegador();

		// PASO 6
		addStep("Validar a nivel BD que se muestren las actualizaciones correspondientes.");
		logger.info("Sin queries 13/07/22");
		p.cerrarApplet();
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Acceso a las responsabilidades:MXOXXO-POXXXXX-Super Usuario." + "Acceso a BD de instancia AVEBQA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecutar proceso de Sincronizacion XXFC - PO WFOC Sincronizacion Oracle Sap para que actualice el puesto con el empleado asignado."
				+ "Prerequisitos: Acceso a las responsabilidades:MXOXXO-POXXXXX-Super Usuario."
				+ "Acceso a BD de instancia AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_XXFC_PO_WFOC_SincronizacionOracleSap_PuestoVacante_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
