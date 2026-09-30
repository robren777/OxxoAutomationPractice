package EBS.pagoQrBazMVP3.US01;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.om.CorresponsaliasBAZ_EBS;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import azure.annotation.AzureTestInfo;
//import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

@AzureTestInfo(areaPath = "I23013 - Pago con QR en OXXO", iterationPath = "I23013 - Pago con QR en OXXO", tags = "ATC_FC_001;QR_BAZ", userStoryId = 73503)
public class ATC_FT_008_GeneracionDeCartaFemsaPagoQrBazManual extends BaseExecution {

	/**
	 * DESCRIPCIÓN: Validar que se generen las cartas femsa en EBS para pagos con QR
	 * BAZ NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-008-Generación de
	 * Carta FEMSA Pago QR BAZ (manual) PREREQUISITOS: 1.- Haber realizado ventas
	 * con QR BAZ 2.-Haber ejecutado fin dia 3.-Tener usuario y contraseña de la
	 * aplicación Oracle EBS 4.- Tener responsabilidad MXOXXO-GLXXXXX-Super Usaurio
	 * INSUMOS: Acceso a página EBS
	 *
	 * @author Antonio Martinez
	 * @date 26/04/2025
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_GeneracionDeCartaFemsaPagoQrBazManual_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("PAGO QR BAZ MVP3");

		/* Utils **************************************************************/

		SeleniumUtil uSelenium = new SeleniumUtil(new IETest(), true);
		CorresponsaliasBAZ_EBS cb = new CorresponsaliasBAZ_EBS(uSelenium, testCase);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);

		// BD AVEBQA
		SQLUtil AVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * VARIABLES
		 */

		String ebsUrl = GlobalVariables.getAvanteServer;
		String userName = GlobalVariables.get6008201User;
		String password = GlobalVariables.get6008201Pswd;
		String reporteConciliacion = data.get("reporteConciliacion");
		String compania = data.get("compania");
		String plaza = data.get("plaza");
		String origen = data.get("origen");
		String cuentaCorriente = data.get("cuentaCorriente");
		String tipoReporte = data.get("tipoReporte");
		String tipoSalida = data.get("tipoSalida");
		String periodo = data.get("periodo");

		LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);

		System.out.println(
				"*****************************************PASO 1************************************************");

		addStep("Se ingresa a la página de EBS");
		inicio.login();

		System.out.println(
				"*****************************************PASO 2************************************************");
		addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");
		cb.validatePatternAndClick("paginaInicial");

		System.out.println(
				"*****************************************PASO 3************************************************");

		addStep("Dar clic sobre la liga 'MXOXXO-GLXXXXX-Super Usuario'");
		paginaInicial.carpetaMXOXXOGLXXXXXSUPERUSUARIO();

		System.out.println(
				"*****************************************PASO 4************************************************");

		addStep("Ejecutar el aplicativo frmservlet");
		cb.runApplet();
		
		System.out.println(
				"*****************************************PASO 5************************************************");

		addStep("Se mostrará una ventana indicando el tipo de solicitud elegir Solicitud única, y dar clic en Aceptar");
		cb.validatePatternAndClick("aceptarSoli2");

		System.out.println(
				"*****************************************PASO 6************************************************");

		addStep("Se ingresa el nombre del concurrente XXFC - Reporte de Conciliacion FEMCO Cartas");
		cb.validatePatternAndClick("puntosbusqueda");
		cb.validatePatternAndWrite("buscarPorcentaje", reporteConciliacion);
		cb.clickEnter();

		System.out.println(
				"*****************************************PASO 7************************************************");

		addStep("Ingresar los parámetros:");
		cb.ingresarParametrosXXFCReporteConciliacionFEMCOCartas(compania, plaza, origen, cuentaCorriente, tipoReporte,
				tipoSalida, periodo);

		cb.validatePatternAndClick("aceptararchivo");

		System.out.println(
				"*****************************************PASO 8************************************************");

		addStep("Clic botón Ejecutar");
		cb.validatePatternAndClick("ejecutar");

		String idRequest = cb.ExtractNumbersFromText();
		System.out.println(idRequest);
		cb.validatePatternAndClick("no");

		System.out.println(
				"*****************************************PASO 9************************************************");
		addStep("Se ingresa a la pantalla de Solicitudes");
		cb.entrarSolicitudes();

		cb.validatePatternAndClick("solicitudesEspecificas");
		cb.validatePatternAndWrite("idSolicitud", idRequest);

		cb.validatePatternAndClick("encontrar");

		System.out.println(
				"*****************************************PASO 10************************************************");

		addStep("Validar que se ejecutan correctamente en automático los concurrentes:");
		cb.validatePattern("faseFinalizadoEstadoNormal2");

		System.out.println(
				"*****************************************PASO 11************************************************");

		addStep("Dar clic en Visualizar Output");
		cb.validatePatternAndClick("visualizaroutput");

		System.out.println(
				"*****************************************PASO 12************************************************");

		addStep("Dar clic en el archivo Excel");
		cb.validatePatternAndClick("xxfc_reporte_conciliacion_archivo");
		cb.checkTwoPatternNoEvidenceClick("maximizarExcelBlanco", "maximizarExcelOscuro");
		cb.checkTwoPatternNoEvidenceClick("detalleExcel_2", "detalleExcel_oscuro");

		cb.cerrarExcel();
		// ahora cierra la aplicacion
		cb.cerrarApplet();
		// cierra los navegadores
		uSelenium.quit();
		// Script Code End

		System.out.println(
				"*****************************************PASO 13************************************************");
		addStep("Verificar que se muestre la siguiente información: ");
		cb.ValidarContenidoExcel();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Haber realizado ventas con QR BAZ\r\n" + "2.-Haber ejecutado fin dia\r\n"
				+ "3.-Tener usuario y contraseña de la aplicación Oracle EBS\r\n"
				+ "4.- Tener responsabilidad MXOXXO-GLXXXXX-Super Usaurio\r\n";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Antonio Martinez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_008_GeneracionDeCartaFemsaPagoQrBazManual";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se ejecute correctamente el concurrente XXFC - Reporte de Conciliacion FEMCO Cartas";
	}
}
