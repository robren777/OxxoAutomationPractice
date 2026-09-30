package EBS.neteos.ReporteyProcesoComprobantePagoDigital;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.neteos.NeteosCedisMVP2.om.pomNeteos;
import EBS.util.neteos.ModelosLoginNeteos.ModeloLoginEBS;
import EBS.util.neteos.PaginaInicialNeteos.EBSPaginaInicial;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PRERREQUISITOS: 
* 1. Contar con la responsabilidad "MXOXXO-AR17ADM-RECIBOS_ADMON_COM_OS".
* 2. Contar con registros de recibos vacíos. (Se solicita a soporte l3 que quiten los permisos de lectura y escritura al archivo pdf y xml para simular la prueba)
* 3. Antes de ejecutar revisar que el correo con el que probaremos este configurado para el cliente
 * @author priscilaestefaniam@hexaware.com
 */


public class ATC_FT_005_EjecucionXXFCProcesodeenvioporcorreodecomprobantedepagodigitalPesoarchivo0 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_005_EjecucionXXFCProcesodeenvioporcorreodecomprobantedepagodigitalPesoarchivo0_test (HashMap<String, String> data) throws Exception {
	
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
		String rfc = data.get("rfc");
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");		
		String Enviarcorreo = data.get("Enviarcorreo");
		String Reimprimir = data.get("Reimprimir");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		ModeloLoginEBS modelo = new ModeloLoginEBS(u, testCase, ebsUrl, userName, password);
		pomNeteos pom = new pomNeteos(u, testCase, data);
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
	
		// ******************************** PASO 1 ****************************************/
		addStep("Ingresar al ambiente de EBS.");
		modelo.navigate(ebsUrl);
		testCase.addScreenShotCurrentStep(" ");
		logger.info("EBS URL: " + ebsUrl);
		
		// ******************************** PASO 2 ****************************************/
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		modelo.setLanguage();
		testCase.addScreenShotCurrentStep(" ");
		
		// ******************************** PASO 3 ****************************************/
		addStep("Ingresar usuario y contrasena.");
		modelo.setCredentials();
		testCase.addScreenShotCurrentStep(" ");
		
		
		// ******************************** PASO 4 ****************************************/
		addStep("Dar clic en el boton de 'Conectese'.");
		modelo.clickLogIn();
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(3);
		
		// ******************************** PASO 5 ****************************************/
		addStep("Seleccionar la responsabilidad \"MXOXXO-AR17ADM-RECIBOS_ADMON_COM_OS\" -> Recibos.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(4);
		assertTrue(pom.clickRespRecibo2(),"Fallo al dar click en recibos");
		u.hardWait(30);
		p.runApplet3();
		
		// ******************************** PASO 6 ****************************************/
		addStep("Dirigirse al menu Ver (x): Solicitudes -> Ejecutar Nueva Solicitud.");		
		u.hardWait(90);
		p.vers();
		pom.screenshot();
		p.clickEjecutarNuevaSolicitud();
		assertTrue(pom.validarPantallaNeuvaSolicitud(),"No se encontro la pantalla de solicitud");
		
		/******************************** PASO 7 *****************************************/
		addStep("Buscar el proceso \"XXFC - Proceso de envío por correo de comprobante de pago digital - Peso archivo 0\".");
		p.enterText(concurrente);
		u.hardWait(12);
		p.escribirConcurrentepagodig();
		assertTrue(pom.validarPantallaParametros(),"No se encontro la pantalla para agregar parametros");
		
		/******************************** PASO 8 ****************************************/
		addStep("Ingresar en los parametros");
		u.hardWait(16);
		p.ingresarParametrosEnvcorreoComppagodigital(rfc, fecha1, fecha2, Enviarcorreo, Reimprimir );
		pom.screenshot();
		u.hardWait(16);
		
		/******************************** PASO 9 ****************************************/
		addStep("Dar clic en el boton de Ejecutar");
		p.clickEjecutar();
		u.hardWait(10);
		pom.screenshot();
		p.clickNo();
		assertTrue(pom.validarPantallaEncontrarSolicitudes(),"No se muestra la pantalla Encontrar Solicitudes");
		
		/******************************** PASO 10 ****************************************/
		addStep("Validar el concurrente finaliza.");
		u.hardWait(60);
		p.clickEncontrar();
		u.hardWait(10);
		assertTrue(pom.validarConcurrenteFinalizado(),"No se muestra el concurrente finalizado");
		
    	/******************************** PASO 11 ***************************************/
		addStep("Dar clic en Visualizar Registro");
		sk.screen.type(Key.DOWN);
		sk.screen.type(Key.DOWN);
		p.clickVisualizarRegistro();
		assertTrue(pom.validarRegistro(),"No se muestra el concurrente finalizado");
		p.scrolldown();
		pom.screenshot();
		p.closeActiveWindow();
		u.hardWait(10);
		p.closeWindow();
		u.hardWait(10);
		sk.screen.type(Key.ENTER);
		u.hardWait(11);
		u.quit();
		
	}
					
		@Override
		public String setTestFullName() {
			return "ATC_FT_005_EjecucionXXFCProcesodeenvioporcorreodecomprobantedepagodigitalPesoarchivo0_test";
		}
		
		@Override
		public String setTestDescription() {
			return "Ejecución correcta del reporte XXFC - Proceso de envío por correo de comprobante de pago digital, que se realice la validación de peso del archivo y al ser igual a 0 no envíe el correo.\r\n";
				
			
		}
		@Override
		public void beforeTest() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public String setPrerequisites() {
			return "1. Contar con la responsabilidad \"MXOXXO-AR17ADM-RECIBOS_ADMON_COM_OS\"\r\n"
					+ "2. Contar con registros de recibos vacíos.\r\n";
		}

		@Override
		public String setTestDesigner() {
			return "priscilaestefaniam@hexaware.com";
		}

		@Override
		public String setTestInstanceID() {
			// TODO Auto-generated method stub
			return null;
		}


	}

