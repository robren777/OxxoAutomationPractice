package EBS.MigracionServiciosCashInOut.Scripts;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
* DESCRIPCIÓN: Realiza la ejecucion del proceso de dispersion.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-026- Proceso Dispersion de Retiro sin tarjeta
* 
* PREREQUISITOS:
* 	- Contar con registros procesados por la interfaz Wo01 en la 
* tabla xxfc_mpc_gl_tpc con forma de pago PYIO49
* 
* INSUMOS:
* 
* @author Michelle Ceja
* @date 25/Noviembre/2024
*/
public class ATC_FT_026_ValidarConcurrenteProcesoDispersion extends BaseExecution{
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_ValidarConcurrenteProcesoDispersion_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I24053 - Migración servicios Cash-in y Cash-out");

		
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String responsabilidad = data.get("responsabilidad");
		String informes = data.get("carpeta1");
		String solicitar = data.get("carpeta2");
		String campo = data.get("campo");
		String formaPago = data.get("formaPago");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");
		String text = data.get("text");
		String accounting = data.get("accounting");
		
		String consulta1 = "select * from (select ID_MPC_GL_TPC,FORMA_PAGO,CR_PLAZA,CR_TIENDA, FOLIO_TRANSFER, FOLIO_WM, ACCOUNTING_DATE from XXFC.xxfc_mpc_gl_tpc \r\n"
				+ "where forma_pago = 'PYIO49' ORDER BY ACCOUNTING_DATE desc)\r\n"
				+ "where rownum <=3";
		

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		
		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		testCase.addScreenShotCurrentStep("");

	
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();
		testCase.addScreenShotCurrentStep("");

		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-GL17ADM-SERVICIOS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep("");

		// PASO 6
		addStep("Seleccionar: Informes -> Solicitar -> Standard");
		paginaInicial.clickOnCarpeta(informes);
		paginaInicial.clickOnCarpeta(solicitar);
		paginaInicial.clickRunStandardGLServicios();
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("");
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		testCase.addScreenShotCurrentStep("");
		patternUtil.solicitudUnica();

		// PASO 8
		addStep("Se captura el nombre del concurrente 'XXFC-Proceso de Dispersión de Retiros sin Tarjeta', parametros, Forma Pago = 'DEV_OXPAY', Fecha Inicio = '', Fecha Final = ''. ");
		patternUtil.typeAndTab(campo);
		testCase.addScreenShotCurrentStep("");
		patternUtil.nTAB(5);
		patternUtil.nDOWN(22);
		testCase.addScreenShotCurrentStep("");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.enterText(formaPago);
		u.hardWait(6);
		patternUtil.nTAB(1);
		u.hardWait(1);
		patternUtil.enterText(fechaInicio);
		patternUtil.nTAB(1);
		u.hardWait(1);
		patternUtil.enterText(fechaFin);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		patternUtil.clickAceptarParametros();
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		u.hardWait(2);

		testCase.addScreenShotCurrentStep("");

		patternUtil.keyAlt("a");
		addStep("¿Ejecutar Otra Solicitud? No");
		testCase.addScreenShotCurrentStep("");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 9
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		testCase.addScreenShotCurrentStep("");

		// PASO 10
		addStep("Seleccionar el ID del concurrente, clic boton Mostrar output");
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		testCase.addScreenShotCurrentStep("");

		patternUtil.keyAlt("z"); // Visualizar el Registro
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		testCase.addScreenShotCurrentStep("");

		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");

		// PASO 11
		addStep("Conectarse a la base de datos EBS 12.2.4");
		System.out.println(consulta1);
		// PASO 12
		addStep("Validar que existen retiros en la tabla, ejecutando el query: SELECT ID_MPC_GL_TPC,FORMA_PAGO,CR_PLAZA,CR_TIENDA, FOLIO_TRANSFER, FOLIO_WM, ACCOUNTING_DATE FROM XXFC.xxfc_mpc_gl_tpc where forma_pago = 'PYIO49';");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		testCase.addQueryEvidenceCurrentStep(validat);
		assertFalse(validat.isEmpty(), "La consulta no tiene registros");		
		EBSAVANTE.close();
		

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ValidarConcurrenteProcesoDispersion_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que finalice correctamente el concurrente XXFC-Proceso de dispersion de retiro sin tarjeta";
	}

	@Override
	public String setTestDesigner() {
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener movimientos de retiro en la tabla XXFC_MPC_GL_TPC. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}