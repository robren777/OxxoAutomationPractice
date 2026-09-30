package WM.EvolucionRetiroSinTarjeta.HU13;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class caso011 extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Ejecucion_del_proceso_de_dispersion_de_HPAY_desde_el_portal_de_EBS_de_manera_manual_para_validar_que_siga_funcionando_correctamente_test (HashMap<String, String> data)
			throws Exception{
		
					/*
			         * VARIABLES
			         */
			        String ebsUrl = GlobalVariables.getAvanteServer;
			        String userName = GlobalVariables.get6008201User;
			        String password = GlobalVariables.get6008201Pswd;
			        
			        String gl17adm = data.get("gl17adm");
			        String carpetaInformes = data.get("carpetaInformes");
			        String carpetaSolicitar = data.get("carpetaSolicitar");
			        String carpetaStandard = data.get("carpetaStandard");
			        String procesoDispersion = data.get("procesoDispersion");
			        String nombreclick = data.get("nombreclick");
			        String fechaInicio = data.get("fechaInicio");
			        String fechaFin = data.get("fechaFin");
			        String tipoPago = data.get("tipoPago");
			        
					testCase.setTest_Description(data.get("id") + data.get("description"));
					testCase.setPrerequisites("Se debe tener acceso al aplicativo de Oracle con la responsabilidad MXOXXO-GLXXXXX-Super Usuario");
			
					/*
					 * Utilerias
					 *********************************************************************/
					System.setProperty("webdriver.ie.driver","Config\\Drivers\\IE\\Windows\\IEDriverServer32.exe");  
			
					SeleniumUtil u = new SeleniumUtil(new IETest(), true);
				    EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
				   	LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
			
				   	SikuliUtil sk = new SikuliUtil();
				   	String pathImg = sk.PatternPath("EBS\\"+"EBS");
				   	Pattern pathimg = new Pattern();
				   	ArrayList<String> ListImage = new ArrayList<String>();
				   	HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
				   	ArrayList<Pattern> patterns = new ArrayList<Pattern>();
				   	File Fol = new File(pathImg);
				   	
				   	MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
				   	PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
			
					System.out.println(
							"*****************************************PASO 1************************************************");
					addStep("Ingresar al portal http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp");
					inicio.login();
					
					System.out.println(
							"*****************************************PASO 2************************************************");
					
					addStep("Ingresar el usuario y contraseña del portal y dar en Conectarse");
					testCase.addScreenShotCurrentStep(u, "Login");
					
					System.out.println(
							"*****************************************PASO 3************************************************");
					
					addStep("Ingresar a la responsabilidad MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS");
					
			        paginaInicial.clickOnResponsabilidad(gl17adm);
					testCase.addScreenShotCurrentStep(u, "MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS");
					
					paginaInicial.clickOnResponsabilidad(carpetaInformes);
					paginaInicial.clickOnResponsabilidad(carpetaSolicitar);
					paginaInicial.clickOnResponsabilidad(carpetaStandard);
					
					System.out.println(
							"*****************************************PASO 4************************************************");
					
					addStep("Ejecutar el aplicativo frmservlet.jnlp");
					patternUtil.runApplet();
					testCase.addScreenShotCurrentStep("Applicativo frmservlet");

					System.out.println(
							"*****************************************PASO 5************************************************");
					
					addStep("En la pantalla de ejecutar una nueva solicitud, se elige Solicitud Unica");
					patternUtil.solicitudUnica();
					
					testCase.addScreenShotCurrentStep("Solicitud unica");
					
					System.out.println(
							"*****************************************PASO 6************************************************");
					
					addStep("En el campo de Nombre buscar  -  (XXFC-Proceso de Dispersión de Retiros sin Tarjeta)");
					p.clickAndType(nombreclick, procesoDispersion, true);
					
					System.out.println(
							"*****************************************PASO 7************************************************");
					
					addStep("Seleccionar el botón de los 3 puntitos de lado derecho del campo Nombre");
					testCase.addScreenShotCurrentStep("Proceso de dispersión");
					
					patternUtil.doubleClickPattern("procesoDispersion", 15);	
					
					System.out.println(
							"*****************************************PASO 8************************************************");
					
					addStep("Seleccionar los 3 puntitos de lado derecho de Forma de Pago");
					patternUtil.clickPattern("puntos", 15);
					testCase.addScreenShotCurrentStep("3puntos");
					
					patternUtil.doubleClickPattern(tipoPago, 15);	
					
					System.out.println(
							"*****************************************PASO 9************************************************");
					
					addStep("Captura los parámetros");
					
					p.clickAndType("fechaTransaccionInicio", fechaInicio, true);
					testCase.addScreenShotCurrentStep("Parámetros");
					
					System.out.println(
							"*****************************************PASO 10************************************************");
					
					addStep("Se aceptan los parametros");
					
					patternUtil.clickPattern("aceptarcerrar", 15);
					testCase.addScreenShotCurrentStep("Aceptar");

					System.out.println(
							"*****************************************PASO 11************************************************");
					
					addStep("Clic en el boton Ejecutar");
					patternUtil.clickPattern("ejecutar", 15);
					testCase.addScreenShotCurrentStep("Ejecutar");

					System.out.println(
							"*****************************************PASO 12************************************************");
					
					addStep("En la pantalla de decisión ¿Ejecutar Otra Solicitud? Se elige No");
					
					String idRequest = ExtractNumbersFromText();
					System.out.println(idRequest);
					
					patternUtil.clickPattern("no", 15);
					testCase.addScreenShotCurrentStep("No");

					System.out.println(
							"*****************************************PASO 13************************************************");
					
					addStep("Ir a la opcion Ver: Solicitudes: Todas las solicitudes, Aceptar");
					p.vers();
					testCase.addScreenShotCurrentStep("Solicitudes");
					
					patternUtil.clickPattern("solicitudesEspecificas", 5);
					p.clickAndType("idSolicitud",idRequest, true);						
					p.encontrar();

					System.out.println(
							"*****************************************PASO 14************************************************");
					
					addStep("Seleccionar el ID del concurrente, clic boton Mostrar Registro");
					
					Boolean validarFinalizado = patternUtil.validatePattern("ejecutandoVerde");

					do {
						patternUtil.clickPattern("refrescarDatos", 10);
						validarFinalizado = patternUtil.validatePattern("ejecutandoVerde");
						
					} while (validarFinalizado);
			
					testCase.addScreenShotCurrentStep("Id de la solicitud");

					patternUtil.clickPattern("visualizarregistro", 10);
					testCase.addTextEvidenceCurrentStep("Se valida que finalizado correctamente.");
					
					u.hardWait(5);
					testCase.addScreenShotCurrentStep("Registro");
					
					// ahora cierra la aplicacion
					patternUtil.clickOracle();
					p.cerrarApplet();
					// cierra los navegadores
					u.quit();
			}

	// Funcion para extraer los numeros de la imagen
	public String ExtractNumbersFromText() {
		   
	       // Captura la región de la pantalla donde se encuentra el texto
	       Screen screen = new Screen();
	       String numeros = "";
	       
	       screen.x = 725;
	       screen.y = 320;
	       screen.w = 90;
	       screen.h = 70;

	       screen.highlight(2);
	       
		   ScreenImage file = screen.capture(725,320,90,70);
		   System.out.println("Saved screen as "+file);
	       
	       // Busca la imagen en la pantalla
	       try {
	           Match match = screen.find(file);
	           
	           // Si se encuentra la imagen, captura el texto
	           if (match != null) {
	               String textoCompleto = match.text();
	               
	               // Utiliza una expresión regular para extraer los números del texto
	               numeros = textoCompleto.replaceAll("\\D+", "");
	               
	               // Imprime los números encontrados
	               System.out.println("Números encontrados: " + numeros);
	           } else {
	               System.out.println("Imagen no encontrada");
	           }
	       } catch (FindFailed e) {
	           e.printStackTrace();
	       }
	       return numeros;
	}
	

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Ejecucion del proceso de dispersion de HPAY desde el portal de EBS de manera manual para validar que siga funcionando correctamente";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_Ejecucion_del_proceso_de_dispersion_de_HPAY_desde_el_portal_de_EBS_de_manera_manual_para_validar_que_siga_funcionando_correctamente_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Se debe tener acceso al aplicativo de Oracle con la responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@AfterMethod
	public void afterMethod() {

	}
	
}
