package EBS.oxxoPay;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_007_ValidarConcurrenteProcesoDispersion extends BaseExecution{
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_ValidarConcurrenteProcesoDispersion_test(HashMap<String, String> data) throws Exception {
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
		
		String consulta1 = "SELECT * FROM XXFC.xxfc_mpc_gl_tpc where forma_pago = 'PYIO49' ";
		

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
		// PASO 2
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contraseña.");
		// PASO 4
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-GL17ADM-SERVICIOS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Informes -> Solicitar -> Standard");
		paginaInicial.clickOnCarpeta(informes);
		paginaInicial.clickOnCarpeta(solicitar);
		paginaInicial.clickRunStandardGLServicios();
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(8);
		// PASO 7
		addStep("Se captura el nombre del concurrente 'XXFC-Proceso de Dispersión de Retiros sin Tarjeta', parametros, Forma Pago = 'DEV_OXPAY', Fecha Inicio = '', Fecha Final = '' ");
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Se captura el nombre del concurrente 'XXFC-Proceso de Dispersión de Retiros sin Tarjeta', parametros, Forma Pago = 'DEV_OXPAY', Fecha Inicio = '', Fecha Final = ''. ");
		patternUtil.typeAndTab(campo);
		patternUtil.nTAB(5);
		patternUtil.nDOWN(14);
		patternUtil.keyAlt("a");
		u.hardWait(3);
		patternUtil.enterText(formaPago);
		patternUtil.nTAB(1);
		u.hardWait(1);
		patternUtil.enterText(fechaInicio);
		patternUtil.nTAB(1);
		u.hardWait(1);
		patternUtil.enterText(fechaFin);
		u.hardWait(3);
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		patternUtil.keyAlt("a");
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 9
		addStep("Ir a View --> Request ");
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		// PASO 10
		addStep("Seleccionar el ID del concurrente, clic boton Mostrar output");
		patternUtil.verificarEstado("ejecutandoVerde", 6000000);
		patternUtil.keyAlt("z"); // Visualizar el Registro
		//assertEquals(patternUtil.getTextDelNavegador("Concurrente", text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
		u.hardWait(3);
		// PASO 11
		addStep("Conectarse a la base de datos EBS 12.2.4");
		System.out.println(consulta1);
		// PASO 12
		addStep("Validar que existe retiro en la tabla, ejecutando el query: SELECT * FROM XXFC.xxfc_mpc_gl_tpc where forma_pago = 'PYIO49';");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "ACCOUNTING_DATE");
		boolean validar = atributo.equals(accounting);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		EBSAVANTE.close();
		
		

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ValidarConcurrenteProcesoDispersion_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que finalice correctamente el concurrente XXFC-Proceso de dispersion de retiro sin tarjeta. Tener movimientos de retiro en la tabla XXFC_MPC_GL_TPC. ";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
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