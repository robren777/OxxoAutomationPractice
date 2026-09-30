package EBS.neteos.NeteosCedisMVP2;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: 
 * El Reporte Excel debe de contener los siguientes datos:"
				+ "- Numero de transaccion"
				+ "- Motivo del error"
				+ "- Numero de convenio"
				+ "- PARAMETROS:"
				+ "- Fecha Ini (tomanda de la fecha de la digitalizacion)"
				+ "- Fecha Fin. 
 *
 * Haber cargado el archivo .csv con la información de las prefacturas de el caso: ATC_FT_001_Cargaprefacturas
 * NOTA: DEBIDO A UN MEJOR MANEJO EN VALIDACIONES SE ACTUALIZARON LOS ULTIMOS PASOS DE EL SCRIPT
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_003_ValidarreporteXXFCICReporteConveniosDigitalizados extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarreporteXXFCICReporteConveniosDigitalizados_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		By ingresosComerciales = By.xpath("//div[normalize-space()='MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS']/parent::a/following-sibling::ul//div[normalize-space()='Ingresos Comerciales']");
		By facturacion = By.xpath("//div[normalize-space()='Facturación']");
		String convenio = data.get("convenio");
		String concurrente = data.get("concurrente");
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");
		String f1 = data.get("f1");
		String f2 = data.get("f2");
		
		//TABLAS A VALIDAR 
		
		String tabla1 = "SELECT rct.trx_number,xp.numero_convenio,xcfd.wm_error,TO_CHAR(xcfd.fecha,'DD/MM/YYYY HH24:MI:SS') fecha "
				+ "FROM   apps.ra_customer_trx_all        rct"
				+ "      ,apps.xxfc_prefacturas           xp"
				+ "      ,apps.xxfc_cfd_factura_digital   xcfd "
				+ "WHERE  rct.interface_header_attribute1 = TO_CHAR (xp.prefactura_id)"
				+ " AND    rct.interface_header_attribute2 = TO_CHAR (xp.prefactura_id)"
				+ " AND    rct.customer_trx_id = xcfd.customer_trx_id(+)"
				+ " AND    TRUNC(xcfd.fecha) >= TO_dATE(" +"'"+f1+"','DD/MM/RRRR')"
				+ " AND    TRUNC(xcfd.fecha) <= TO_dATE(" +"'"+f2+"','DD/MM/RRRR')"
				+ " AND    xp.NUMERO_CONVENIO = "+"'"+convenio+"'";
		
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresamos a la responsabilidad MXOXXO-AR17ADM-ADMON-COMERCIAL_ADMON_COM_OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		u.click(ingresosComerciales);
		u.hardWait(4);
		testCase.addScreenShotCurrentStep(" ");
		u.click(facturacion);
		u.hardWait(30);
		p.runApplet();
		u.hardWait(50);
		// PASO 6
		addStep("Seleccionar el menú: Ver(x) -> Solicitudes -> Encontrar.");
		p.vers();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(20);
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(20);
		// PASO 7
		addStep("Seleccionar el proceso XXFC - IC Reporte Convenios Digitalizados");
		p.enterText(concurrente);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 8
		addStep("Ingresar en los parametros");
		u.hardWait(8);
		sk.screen.type(fecha1);
		u.hardWait(8);
		sk.screen.type(fecha2);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(8);
		p.nEnter(1);
		u.hardWait(8);
		// PASO 9
		addStep("Dar clic en Aceptar y Ejecutar");
		p.clickEjecutar();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep(" ");
		p.clickNo();
		// PASO 10
		addStep("Validar el concurrente XXFC - IC Reporte Convenios Digitalizados finaliza en error");
		p.clickEncontrar();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(20);
		// PASO 11
		addStep("Dar clic en Visualizar Output");
		p.clickVisualizarOutput();
		u.hardWait(7);
		testCase.addScreenShotCurrentStep(" ");
		
		//Validacion en tablas
		// PASO 12
		addStep("Validar que se muestre informacion en tabla");
		System.out.println(tabla1);

		SQLResult validat = AVANTE.executeQuery(tabla1);

		boolean validaB = validat.isEmpty();

		if (!validaB) {

			testCase.addQueryEvidenceCurrentStep(validat);
		}
		assertFalse("No se encuentran errores ", validaB);
		System.out.println(validaB);
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa se encontro el error esperado");
		 u.close();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarreporteXXFCICReporteConveniosDigitalizados_test";
	}

	@Override
	public String setTestDescription() {
		return "Haber cargado el archivo .csv con la información de las prefacturas de el caso : ATC_FT_001_Cargaprefacturas";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "El Reporte Excel debe de contener los siguientes datos:"
				+ "- Numero de transaccion"
				+ "- Motivo del error"
				+ "- Numero de convenio"
				+ "- PARAMETROS:"
				+ "- Fecha Ini (tomanda de la fecha de la digitalizacion)"
				+ "- Fecha Fin.";
	}

	@Override
	public void beforeTest() {

	}
}
