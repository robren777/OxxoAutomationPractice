package RDM.cambiosMasivosPmo.hu04;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
 * PRERREQUISITOS: Contar con la liga de acceso a RDM Contar con credenciales
 * para RDM NOTA: No tener registros guardados
 *
 * Realiza una prueba que nos permita validar los distribuidores dados de alta
 * en la tabla RDM100.XXFC_PMO_TDISTRIB_SERVICIO
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_010_ValidarElTipoDeDistribuidorQueEsteDadoDeAlta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidarElTipoDeDistribuidorQueEsteDadoDeAlta_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */

		String distribucion = data.get("distribucion");
		String ruta = data.get("ruta");
		String plaza = data.get("plaza");
		String cilco = data.get("cilco");
		String produccion = data.get("produccion");
		String carga = data.get("carga");
		String verificacion = data.get("verificacion");
		String jit = data.get("jit");
		String envio = data.get("envio");
		String consulta1 = "select TIPO_DISTRIB,SERVICIO,FECHA_INSERT,STATUS from RDM100.XXFC_PMO_TDISTRIB_SERVICIO";
		String consulta2 = "SELECT * FROM RDM100.XXFC_PMO_CONFIG_RUTA_CICLO";
		/*
		 * UTILERIAS
		 */
		SikuliUtil sk = new SikuliUtil();
		SeleniumUtil u = new SeleniumUtil(new IETest());
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);
		utils.sql.SQLUtil RDM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRD71CU,
				GlobalVariables.DB_USER_FCRD71CU, GlobalVariables.DB_PASSWORD_FCRD71CU);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a la liga http://10.180.49.86:8071");
		System.out.println("PASO 1");
		// PASO 2
		addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas" + "Capturar:" + "- Usuario" + "- Contraseña"
				+ "Y dar clic al boton de Logon");
		System.out.println("PASO 2");
		c.logInCambiosMasivos();
		// PASO 3
		addStep("Dar clic al menu: Support Functions > Reports > PMO_Config_Ruta_Ciclo");
		System.out.println("PASO 3");
		c.clickPmoConfigRutaCiclo();
		// PASO 4
		addStep("Validar los distribuidores dados de alta en la tabla RDM100.XXFC_PMO_TDISTRIB_SERVICIO");
		System.out.println("PASO 4");
		try {
			System.out.println(consulta1);
			SQLResult c1 = RDM.executeQuery(consulta1);
			testCase.addQueryEvidenceCurrentStep(c1);
			u.hardWait(4);
			System.out.println(consulta2);
			SQLResult c2 = RDM.executeQuery(consulta2);
			testCase.addQueryEvidenceCurrentStep(c2);
		} catch (Exception e) {
			throw new AssertionError("Error al ejecutar consultas" + e);
		}
		// PASO 5
		addStep("Dar clic al boton de Agregar y Capturar los campos:" + "- Tipo de Distribución (P)"
				+ "- Tipo Ruta (LOCAL)" + "- Plaza (10GUD)" + "- Tiempo Cilco (2)" + "- Producción (01:00:00)"
				+ "- Verificación (02:00:00)" + "- Carga (03:00:00)" + "- Corte Jit (04:00:00)"
				+ "- Corte Envio (04:30:00)");
		c.agregarCambiosMasivos(distribucion, ruta, plaza, cilco, produccion, carga, verificacion, jit, envio);
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM" + "- Contar con credenciales para RDM"
				+ " NOTA: No tener registros guardados ";
	}

	@Override
	public String setTestDescription() {
		return "Realiza una prueba que nos permita validar los distribuidores dados de alta en la tabla RDM100.XXFC_PMO_TDISTRIB_SERVICIO";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_ValidarElTipoDeDistribuidorQueEsteDadoDeAlta_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}