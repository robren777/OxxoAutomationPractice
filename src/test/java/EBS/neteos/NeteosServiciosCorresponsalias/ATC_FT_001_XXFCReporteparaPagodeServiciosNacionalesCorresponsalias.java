package EBS.neteos.NeteosServiciosCorresponsalias;


import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
/**
 * PRERREQUISITOS: tener un servicio con los parametros GL,FACTURA_AR,FACTURA,POLIZA_OS,CARTA_FEMSA,ATRIBUTO_1,ATRIBUTO_4 
 *
 * DESCRIPCION: verificar que el concurrente genere el reporte
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_001_XXFCReporteparaPagodeServiciosNacionalesCorresponsalias extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_XXFCReporteparaPagodeServiciosNacionalesCorresponsalias_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
		String servicio = data.get("servicio");
		String estatus = data.get("estatus");
		String fecha = data.get("fecha");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);

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
		addStep("Ingresamos a la responsabilidad MXOXXO-GL17ADM-SERVICIOS-ADTIVO-OS");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickInformesSolicitarServicios();
		paginaInicial.clickStandard();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		p.runApplet();
		u.hardWait(60);
		testCase.addScreenShotCurrentStep(" ");
		// PASO 6
		addStep("Dirigirse a --> Solicitud Unica");
		testCase.addScreenShotCurrentStep(" ");
		p.nEnter(1);
		u.hardWait(5);
		// PASO 7
	    addStep("Ingresar el  concurrente XXFC Reporte para Pago de Servicios Nacionales Corresponsalias y seleccionarlo");
	    p.enterText(concurrente);
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(6);
	    
	    // PASO 8
	    addStep("Ingresar los parámetros:"
	    		+ "Servicio:"
	    		+ "Estatus: NO FACTURADO"
	    		+ "Fecha inicial:"
	    		+ "Fecha Final:"
	    		+ "Enviar correo a: NO_ENVIAR");
	    testCase.addScreenShotCurrentStep(" ");
	    p.parametrosCorresponsalias(servicio, estatus, fecha);
	    testCase.addScreenShotCurrentStep(" ");
		// PASO 9
	    addStep("Dar clic en Aceptar y Ejecutar");
	    u.hardWait(12);
	    p.clickEjecutar();
	    u.hardWait(12);
	    p.clickNo();
	    u.hardWait(30);
	    p.vers();
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep(" ");
	    p.clickEncontrar();
	    u.hardWait(30);
	    assertTrue(p.validarEstadoFinal());
	    u.hardWait(20);
	    p.clickVisualizarOutput();
	    u.hardWait(30);
		u.close();

		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_XXFCReporteparaPagodeServiciosNacionalesCorresponsalias_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar la generación del reporte Pago de servicios nacionales y corresponsalias";
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
		return "Contar con registros de cobros de comision a proveedores"
				+ "ITEM Configurado MPE";
	}

	@Override
	public void beforeTest() {

	}
}