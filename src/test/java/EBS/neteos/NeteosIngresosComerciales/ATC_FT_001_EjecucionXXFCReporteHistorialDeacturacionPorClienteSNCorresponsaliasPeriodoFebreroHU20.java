
package EBS.neteos.NeteosIngresosComerciales;

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
 * PRERREQUISITOS: 
 * Contar con acceso a EBS
 * Contar con acceso a la Responsabilidad de MXOXXO-ARXXXX-CONSULTAS_ADMON_COM_OS
 * Contar con el concurrente XXFC - Reporte de Facturacion SN y Corresponsalias
 * 
 *
 * DESCRIPCION: Validar que se realice correctamente la ejecucion del Concurrente 
 * XXFC - Reporte de Facturacion SN y Corresponsalias con Periodo de Febrero y envie información de dicha ejecucion
 * 	
 * @author ricardor4@hexaware.com
 */


public class ATC_FT_001_EjecucionXXFCReporteHistorialDeacturacionPorClienteSNCorresponsaliasPeriodoFebreroHU20 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_EjecucionXXFCReporteHistorialDeacturacionPorClienteSNCorresponsaliasPeriodoFebreroHU20_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");

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
		u.hardWait(4);
		// PASO 5
		addStep("Dar clic en Conexion Seleccionar la responsabilidad MXOXXO-ARXXXX-CONSULTAS_ADMON_COM_OS");

        try {
			
        	paginaInicial.clickOnResponsabilidad(responsabilidad);
			
			}
			catch (Exception e) {
				
				throw new AssertionError("Error al ingresar a la responsabilidad" + e);
			
			}
	
		u.hardWait(3);
		paginaInicial.clickTransacciones();
		u.hardWait(3);
		paginaInicial.clickRunTransactions();
		u.hardWait(20);
		p.runApplet2();
		u.hardWait(90);
		testCase.addScreenShotCurrentStep(u, "Dirigirse  al menU Ver--> Solicitudes ");
		p.vers();
		u.hardWait(21);
		testCase.addScreenShotCurrentStep(" ");
		p.clickEjecutarNuevaSolicitud();
		u.hardWait(18);
		
		// PASO 6
		addStep("Buscar y seleccionar el concurrente XXFC - Reporte de Facturacion SN y Corresponsalias");
		
		  try {
				
				p.enterText(concurrente);
				testCase.addScreenShotCurrentStep(" ");
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al ingresar nombre de concurrente" + e);
				
				}
	
		u.hardWait(16);
		
		// PASO 7
		addStep("Ingresar los parámetros:"
				+ "Fecha Inicio : 01-FEB-2024"
				+ "Fecha Fin: 29-FEB-2024"
				+ "Razon Social"
				+ "RFC: GCO121008MU7"
				+ "");
		
		
		
		  try {
				
				
				sk.screen.type(fecha1);
				u.hardWait(4);
				sk.screen.type(fecha2);
				testCase.addScreenShotCurrentStep(" ");
				u.hardWait(4);
				p.nTAB(1);
				u.hardWait(4);
				p.nTAB(1);
				p.nEnter(1);
				
				}
				catch (Exception e) {
					
					throw new AssertionError("Error al ingresar parametros" + e);
				
				}
		
		// PASO 8
	    addStep("Dar clic en Aceptar y Ejecutar");
	    u.hardWait(12);
	    p.clickEjecutar();
	    u.hardWait(12);
	    p.clickNo();
	    u.hardWait(30);
	    testCase.addScreenShotCurrentStep(" ");
	    p.clickEncontrar();
	    u.hardWait(30);
	    assertTrue(p.validarEstadoFinal());
	    u.hardWait(20);
	    p.clickVisualizarOutput();
	    testCase.addScreenShotCurrentStep(" ");
	    u.hardWait(30);
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_EjecucionXXFCReporteHistorialDeacturacionPorClienteSNCorresponsaliasPeriodoFebreroHU20_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice correctamente la ejecucion del Concurrente "
				+ "XXFC - Reporte de Facturacion SN y Corresponsalias con Periodo de Febrero y envie información de dicha ejecucion.";
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
		return "Contar con acceso a EBS"
				+ "Contar con acceso a la Responsabilidad de MXOXXO-ARXXXX-CONSULTAS_ADMON_COM_OS"
				+ "Contar con el concurrente XXFC - Reporte de Facturacion SN y Corresponsalias";
	}

	@Override
	public void beforeTest() {

	}
}
