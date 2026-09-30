package EBS.devolucionesOxxoPay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_007_EjecucionConcurrenteXXFCProcesoDispersionRetiroSinTarjeta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_EjecucionConcurrenteXXFCProcesoDispersionRetiroSinTarjeta_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String formaPagoQuery = "SELECT forma_pago, cr_plaza, cr_tienda FROM XXFC.xxfc_mpc_gl_tpc where forma_pago = 'PYIO49'";
		String nombre = data.get("nombre");
		String nombre2 = data.get("nombre2");
		String formapago = data.get("formapago");
		String fechainicio = data.get("inicio");
		String fechafin = data.get("fin");
		/*
		 * UTILERIAS
		 */
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
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
		addStep("Conectarse a la base de datos EBS 12.2.4");

		// PASO 2
		addStep("Ejecutar el query");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		System.out.println(formaPagoQuery);
		SQLResult ValidChangeExec = dbAvebqa.executeQuery(formaPagoQuery);
		String forma_pago = "";

		boolean ValidChangeRes = ValidChangeExec.isEmpty();
		if (!ValidChangeRes) {
			forma_pago = ValidChangeExec.getData(0, "forma_pago");
			System.out.println("Forma de pago: " + forma_pago);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		} else if (ValidChangeRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta " + formaPagoQuery);
			testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		}

		// PASO 3
		addStep("Entrar  a la responsabilidad de MXOXXO-GL17ADM-SERVICIOS --> Informes --> Request --> Standard");
		inicio.login();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaInformesElement.click();
		paginaInicial.carpetaSolicitarElement.click();
		paginaInicial.runStandardElement.click();
		u.hardWait(5);
		p.runApplet();
		u.hardWait(10);

		// PASO 4
		addStep("Se captura el nombre del concurrente");
		p.inicio();
		p.ingresarNombreAcentos(nombre, nombre2);
		p.parametros(formapago, fechainicio, fechafin);
		p.ejecutar();
		p.otraSolicitud();

		// PASO 5
		addStep("Ir a View --> Request ");
		p.vers();
		p.encontrar();

		// PASO 6
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener movimientos de retiro en la tabla XXFC_MPC_GL_TPC";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que finalice correctamente el concurrente XXFC-Proceso de dispersion de retiro sin tarjeta."
				+ "Prerrequisitos: Tener movimientos de retiro en la tabla XXFC_MPC_GL_TPC";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_EjecucionConcurrenteXXFCProcesoDispersionRetiroSinTarjeta_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
