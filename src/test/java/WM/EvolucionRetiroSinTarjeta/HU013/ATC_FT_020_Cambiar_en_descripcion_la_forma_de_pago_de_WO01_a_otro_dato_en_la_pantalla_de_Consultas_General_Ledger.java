package WM.EvolucionRetiroSinTarjeta.HU013;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_020_Cambiar_en_descripcion_la_forma_de_pago_de_WO01_a_otro_dato_en_la_pantalla_de_Consultas_General_Ledger extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_020_Cambiar_en_descripcion_la_forma_de_pago_de_WO01_a_otro_dato_en_la_pantalla_de_Consultas_General_Ledger_test(HashMap<String, String> data)
			throws Exception {
		/**
		 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
		 * Desc: Validar que no se pueda ejecutar el concurrente XXFC_Proceso_de_dispersion_RST FI
		 * @author Mario Garza
		 * @date 09/02/2024
		 **/
		
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server"); 
		String userName = data.get("userName");
		//PasswordUtil.generateEncryptPass();
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String informes = data.get("carpeta1");
		String solicitar = data.get("carpeta2"); 
		String tipo = data.get("tipo");
		String fechaInicio = data.get("fechaInicio"); 
		String fechaFin = data.get("fechaFin"); 
		String errorFormaPago = data.get("errorFormaPago"); 
		String finalizado = data.get("finalizado");
		

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
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

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: Proceso de dispersion de RST
		 * Llamada de Metodos, clases y objetos
		 */
		
        // PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 2
		addStep("Ingresar usuario y contraseña.");
		inicio.login();
		addStep("Dar clic en el boton de 'Conectese'.");
		// PASO 3
		addStep("Ingresar a la responsabilidad '17ADM-SSERVICIOS_ADTIVO_OS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Responsabilidad");
		
		addStep("Seleccionar: Infomres : Solicitar -> Standard");
		paginaInicial.clickOnCarpeta(informes);
		paginaInicial.clickOnCarpeta(solicitar);
		paginaInicial.clickStandard17admServicios();
		testCase.addScreenShotCurrentStep(u, "Infomres : Solicitar -> Standard");
		u.hardWait(8);
		//PASO 4
		addStep("Descargar el ejecutable java frmServlet y se ejecuta");
		patternUtil.frmServlet();
		testCase.addScreenShotCurrentStep(u, "frmServlet");
		u.hardWait(5);
		patternUtil.runApplet();
		//PASO 5
		patternUtil.solicitudUnica();
		u.hardWait(5);
		addStep("Selecciona los 3 puntitos en el Nombre para buscar el concurrente ");
		patternUtil.buscarNombreConcurrente();
		testCase.addScreenShotCurrentStep(u, "Busca el concurrente");
		// PASO 6
		addStep("Escribe el concurrente: XXFC-Proceso de Dispersion de retiros sin tarjeta ");
		patternUtil.typeAndBuscar(tipo);
		testCase.addScreenShotCurrentStep(u, "Tipo de concurrente");
		addStep("Selecciona lo forma de pago");
		patternUtil.seleccionaTAB();
		// PASO 9 y 10
		addStep("Selecciona la fecha incio - La fecha se modifica en el archivo .xls");
		patternUtil.escribeFechaInicio(fechaInicio);
		testCase.addScreenShotCurrentStep(u, "Fecha inicio");
		addStep("Selecciona la fecha fin - La fecha se modifica en el archivo .xls");
		patternUtil.escribeFechaFin(fechaFin);
		testCase.addScreenShotCurrentStep(u, "Fecha fin");
		// PASO 11
		addStep("Se valida el error por no ingresar la forma de pago");
		patternUtil.validatePattern("errorFormaPago");
		testCase.addScreenShotCurrentStep(u, "errorFormaPago");
		
		
		String pathYourSystem = System.getProperty("user.dir") + "\\";
 	     String imagePath = (pathYourSystem + "Patterns\\EBS\\errorFormaPago.png");
	       // Captura la región de la pantalla donde se encuentra el texto
 	    addStep("Se valida el error por no ingresar la forma de pago");
 	    testCase.addScreenShotCurrentStep(u, "imagen");
	       Screen screen = new Screen();
	       // Busca la imagen en la pantalla
	       try {
	    	   u.hardWait(5);
	           Match match = screen.find(imagePath);
	           
	           // Si se encuentra la imagen, captura el texto
	           if (match != null) {
	               String textoCompleto = match.text();
	               // Utiliza una expresión regular para extraer los números del texto
	               //String numeros = textoCompleto.replaceAll("\\D+", "");
	               // Imprime los números encontrados
	               System.out.println("Campo requerido: " + textoCompleto);
	           }else {  
	        	   System.out.print("Imagen no encontrada");
	           }

			
	       } catch (FindFailed e) {
	           e.printStackTrace();
	       }
	    
        // PASO 14 ------Pendiente de realizar 
		//patternUtil.clickAceptarParametros();
		patternUtil.closeWindow();
		patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_Cambiar_en_descripcion_la_forma_de_pago_de_WO01_a_otro_dato_en_la_pantalla_de_Consultas_General_Ledger";
	}

	@Override
	public String setTestDescription() {
		return "020- Cambiar en descripcion la forma de pago de WO01 a otro dato en la pantalla de Consultas General Ledger existan los medios de pago segun configuracion R_PYIO24 - RST_Hotpay, R_PYIO26 - R_RST_Ftech, R_PYIO49 - DEV_OXPAY	";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener la responsabilidad 'MXOXXO-GLXXXXX-Super Usuario'. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}
