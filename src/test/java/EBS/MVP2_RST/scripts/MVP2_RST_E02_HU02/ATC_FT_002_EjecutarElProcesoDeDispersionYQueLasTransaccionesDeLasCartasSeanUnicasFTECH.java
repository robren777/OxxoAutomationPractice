package EBS.MVP2_RST.scripts.MVP2_RST_E02_HU02;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.MVP2_RST.om.BaseComisionDif;
import EBS.MVP2_RST.om.mvp2ErstEBS;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

@AzureTestInfo(areaPath = "I23061-Evolución Retiros sin Tarjeta", 
iterationPath = "I23061-Evolución Retiros sin Tarjeta", 
tags = "ATC_FT_002;E02_HU02", 
userStoryId = 53721)

/**
 * DESCRIPCION: Si el proceso de dispersion se realiza con transacciones no usadas,la carta femsa y poliza contable deben generarse
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-002- Ejecutar el proceso de dispersion y que las transacciones de las cartas sean unicas (Ftech)
 *
 * PREREQUISITOS:
 * Tener acceso a la la responsabilidad en EBS y no haber usado transacciones fintech
 *
 * INSUMOS:
 * - NA
 *
 * suiteId=140620
 * @author Rafael Ramos
 * @date 12/03/2025
 */

public class ATC_FT_002_EjecutarElProcesoDeDispersionYQueLasTransaccionesDeLasCartasSeanUnicasFTECH extends BaseExecution{
	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_EjecutarElProcesoDeDispersionYQueLasTransaccionesDeLasCartasSeanUnicasFTECH_test(HashMap<String, String> data) throws Exception {

		// Variables globales
		String ebsUrl = GlobalVariables.Url_Ebs_Comision;
		String userName = GlobalVariables.User_EBS_C;
		String password = GlobalVariables.Password_EBS_C;
		// Ruta relativa (dentro de la estructura de tu proyecto):
		File file2 = new File("C:\\CENTRAL\\FrameworkEBS\\Patterns\\EBS");

		// data provider
		String superusuario = data.get("SuperUsuario");

		// utileria
		SeleniumUtil uSelenium = new SeleniumUtil(new InternetExplorerDriver());
		LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		SikuliUtil sk = new SikuliUtil();
		PatternUtil pattern = new PatternUtil(sk, uSelenium, null, null, ImgPatter, null, file2);
		mvp2ErstEBS rst = new mvp2ErstEBS(uSelenium, testCase);
		BaseComisionDif BaseComision = new BaseComisionDif(uSelenium, testCase, sk, ImgPatter);

		// Comienza codigo
	System.out.println("*****************************************PASO 1 y 2 Login************************************************");

	addStep("Se ingresa a la página de EBS");
	assertTrue(inicio.login(), "No se realiza el inicio de sesion");

	System.out.println("*****************************************PASO 3************************************************");

	addStep("Dar clic sobre la liga MXOXXO-GL17ADM-SERVICIOS: Informes: Solicitar: Standard y después dar clic dar clic sobre ejecutar");
	assertTrue(paginaInicial.clickOnResponsabilidad("MXOXXO-GL17ADM-SERVICIOS"), "No existe la responsabilidad");
	assertTrue(BaseComision.ClickOnCarpeta("Informes"), "No encuentra la carpeta informes");
	assertTrue(BaseComision.ClickOnCarpeta("Solicitar"), "No encuentra la carpeta Solicitar");
	assertTrue(BaseComision.ClickOnCarpeta("Standard"), "No encuentra la carpeta Standard");

	System.out.println(	"*****************************************PASO 4************************************************");

		addStep("Se le da clic en ver y despues en solicitudes");
		// Abrir applet abrir applet y marcar casilla
		rst.abrirAppletChrome();
		pattern.closeActiveWindow();
		assertTrue(BaseComision.CambioResponsabilidad(file2), "No se valida el cambio de responsabilidad");

		addStep("Se da clic super usuario");
		assertTrue(BaseComision.typeSuperUsuario(file2, superusuario), "No se da click a super usuario");

		addStep("Se busca al super usuario");
		assertTrue(BaseComision.buscarsuperusuario(file2), "No se ejecuta nueva solicitud");

		addStep("Se da click al boton aceptar");
		assertTrue(BaseComision.AbrirSuperUsuario(file2), "No se ejecuta nueva solicitud");

		addStep("Se da click al boton aceptar");
		assertTrue(BaseComision.AbrirSuperUsuario(file2), "No se ejecuta nueva solicitud");

		System.out.println("*****************************************PASO 5 ************************************************");
		addStep("Se busca el journal (asiento contable) de Fintech");
		assertTrue(BaseComision.botonEncontrar(file2), "No se encuentra el boton encontrar");

		addStep("Se da click al journal del total de Fintech para revisar el asiento contable"
				+ "y se ve la informacion de totales, comision e IVA");
		//assertTrue(BaseComision.totalhotpay(file2), "No se encuentra el total de hotpay");
		BaseComision.tab2(43);
		assertTrue(BaseComision.botonAsientoContable(file2), "No se encuentra el boton asiento contable");
		BaseComision.tab2(2);
		BaseComision.tab2(10);
		
		System.out.println("*****************************************PASO 6 ************************************************");
		// Conexión a la base de datos EBS
		SQLUtil dbEBS = new SQLUtil(GlobalVariables.DB_HOST_Ebs, GlobalVariables.DB_USER_Ebs, GlobalVariables.DB_PASSWORD_Ebs);
		// Consulta para verificar la existencia de los datos
		addStep("Verificar que los transacciones sumen el monto mostrado desde el asiento contable/journal"
				+ "La comison_socio viene vacia al ser socio No integrador");

		String consultaTransacciones = data.get("scriptTransacciones");
		SQLResult resultado = executeQuery(dbEBS, consultaTransacciones);

		// Verificar si la consulta retorna resultados
		boolean datosDisponibles = !resultado.isEmpty();

		// Agregar evidencia de los resultados de la consulta
		testCase.addTextEvidenceCurrentStep("Consulta ejecutada: " + consultaTransacciones);
		testCase.addQueryEvidenceCurrentStep(resultado);
		assertTrue(datosDisponibles, "No se encontraron datos"); // Asertar que los datos estén disponibles

		System.out.println("*****************************************PASO 7 ************************************************");
		// Consulta para verificar la existencia de los datos
		addStep("Verificar la comision de oxpay/conekta"
				+ "La comision es la suma del IVA y la comision en este caso es $14.66 (comision global)"
				+ "nota: solo se consideran 3 transacciones en el proceso de dispersion"
				+ "debido a que despues hubo otras transacciones pero no entraron en el proceso");

		String consultacomision = data.get("scriptComision");
		SQLResult resultadocomision = executeQuery(dbEBS, consultacomision);
		boolean datosDisponibles2 = !resultadocomision.isEmpty();

		// Agregar evidencia de los resultados de la consulta
		testCase.addTextEvidenceCurrentStep("Consulta ejecutada: " + consultacomision);
		testCase.addQueryEvidenceCurrentStep(resultadocomision);
		//Desglose como nota para que aparezca en la evidencia
		testCase.addTextEvidenceCurrentStep("En la BD de EBS se verifican las transacciones de ese journal (con su CR Plaza 10MON)\r\n"
						+ "Dando 4 transacciones:\r\n"
						+ "2 de $20\r\n"
						+ "2 de $10\r\n"
						+ "Sumando los $60 que es correcto.\r\n"
						+ "El IVA y comisión al No ser un socio integrador esta en nulo ($0) por el momento\r\n");	
		// Asertar que los datos estén disponibles
		assertTrue(datosDisponibles2, "No se encontraron datos");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_EjecutarElProcesoDeDispersionYQueLasTransaccionesDeLasCartasSeanUnicasFTECH_test";
	}

	@Override
	public String setTestDescription() {
		return "Si el proceso de dispersion se realiza con transacciones no usadas,"
				+ "la carta femsa y poliza contable deben generarse";
	}

	@Override
	public String setTestDesigner() {
		return " Rafael Ramos";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return " Tener acceso a la la responsabilidad en EBS" + "No haber usado transacciones fintech";
	}

	@Override
	public void beforeTest() {
	// TODO Auto-generated method stub
	}

}
