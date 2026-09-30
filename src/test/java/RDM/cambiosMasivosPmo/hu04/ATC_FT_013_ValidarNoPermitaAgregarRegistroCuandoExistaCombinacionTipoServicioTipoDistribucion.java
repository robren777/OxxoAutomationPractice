package RDM.cambiosMasivosPmo.hu04;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.CambiosMasivosQueries;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * PRERREQUISITOS: Contar con la liga de acceso a RDM Contar con credenciales
 * para RDM
 *
 * No permitir agregarlo cuando no exista la combinación de tipo de servicio y
 * tipo de distribucion Validar relación de TIPO_DISTRIB, SERVICIO SELECT
 * DISTINCT TIPO_DISTRIB, SERVICIO FROM RDM100.XXFC_PMO_TDISTRIB_SERVICIO
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_013_ValidarNoPermitaAgregarRegistroCuandoExistaCombinacionTipoServicioTipoDistribucion
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_ValidarNoPermitaAgregarRegistroCuandoExistaCombinacionTipoServicioTipoDistribucion_test(
			HashMap<String, String> data) throws Exception {

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

		/*
		 * UTILERIAS
		 */
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);
		SikuliUtil sk = new SikuliUtil();
		SeleniumUtil u = new SeleniumUtil(new IETest());
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);

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
		assertTrue(c.validarPantallaConfiguracionTiempoInterno(), "Pantalla validada");
		// PASO 4
		addStep("Validar relación de TIPO_DISTRIB, SERVICIO" + "SELECT DISTINCT TIPO_DISTRIB, SERVICIO"
				+ "  FROM RDM100.XXFC_PMO_TDISTRIB_SERVICIO");
		assertFalse(executeQuery(dbConecta, cq.queryShipDest()).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queyPmoTdistribServicio()).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryTpoPedido()).isEmpty());
		cq.detalleServicios(dbConecta);
		dbConecta.close();
		// PASO 5
		addStep("Dar clic al boton de Agregar y Capturar los campos:" + "- Tipo de Distribución (P)"
				+ "- Tipo Ruta (LOCAL)" + "- Plaza (10GUD)" + "- Tiempo Cilco (2)" + "- Producción (01:00:00)"
				+ "- Verificación (02:00:00)" + "- Carga (03:00:00)" + "- Corte Jit (04:00:00)"
				+ "- Corte Envio (04:30:00)");
		c.agregarCambiosMasivos(distribucion, ruta, plaza, cilco, produccion, carga, verificacion, jit, envio);
		c.nEnter(1);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		assertTrue(c.validarAlertaDistribucionNoExiste(), "Alerta validada");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM" + "- Contar con credenciales para RDM";
	}

	@Override
	public String setTestDescription() {
		return "No permitir agregarlo cuando no exista la combinación de tipo de servicio y tipo de distribucion"
				+ "Validar relación de TIPO_DISTRIB, SERVICIO" + "SELECT DISTINCT TIPO_DISTRIB, SERVICIO"
				+ "FROM RDM100.XXFC_PMO_TDISTRIB_SERVICIO";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_ValidarNoPermitaAgregarRegistroCuandoExistaCombinacionTipoServicioTipoDistribucion_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}