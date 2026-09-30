package WM.EvolucionRetiroSinTarjeta.HU014;

import static org.testng.Assert.assertFalse;

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
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_Validar_Depuracion_de_la_Base_de_Datos_EBS_con_configuracion_de_Meaning_25Meses extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_Validar_Depuracion_de_la_Base_de_Datos_EBS_con_configuracion_de_Meaning_25Meses_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
				 * Desc:
				 * Validar Depuracion de la Base de Datos EBS con configuracion de Meaning 25(Meses)
				 * @author Mónica Soto
				 * @date 29/02/2024
				 **/
		
				/*
		        * VARIABLES
		        */
				String ebsUrl = GlobalVariables.getAvanteServer;
		        String userName = GlobalVariables.get6008201User;
		        String password = GlobalVariables.get6008201Pswd;
		        
		        // Responsabilidades para Consultas GL
		        String desarrolloAplicaciones = data.get("desarrolloAplicaciones");
		        String carpetaAplicacion = data.get("carpetaAplicacion");
		        String carpetaConsultas = data.get("carpetaConsultas");
		        String careptaConsultasGl = data.get("careptaConsultasGl");

		        // Responsabilidades para servicios de Oxxo
		        String mxoxxoServicios = data.get("mxoxxoServicios");
		        String carpetaInformes = data.get("carpetaInformes");
		        String carpetaSolicitar = data.get("carpetaSolicitar");
		        String careptaStandard = data.get("careptaStandard");
		        
				// Captura la región de la pantalla para editar
			    Screen screen = new Screen();
		
				testCase.setTest_Description(data.get("id") + data.get("description"));
				testCase.setPrerequisites("Que exista la tabla FND_LOOKUP_VALUES_VL en la bd AVEBQA");
		
				/*
				 * Utilerias
				 *********************************************************************/
		
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
		       
			   	//BD AVEBQA
				SQLUtil AVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
											GlobalVariables.DB_PASSWORD_AVEBQA);
		
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				addStep("Se ingresa a la página de EBS");
				inicio.login();

				addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");	
				testCase.addScreenShotCurrentStep(u, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 3************************************************");
				
		        paginaInicial.clickOnResponsabilidad(desarrolloAplicaciones);
		        paginaInicial.clickOnResponsabilidad(carpetaAplicacion);
		        paginaInicial.clickOnResponsabilidad(carpetaConsultas);
		        
				addStep("Ingresar a la responsabilidad de Application Developer y luego a las carpetas /Application/Lookups/ y despues abrir GL Lookups para que se descargue el aplicativo frmservlet");
				testCase.addScreenShotCurrentStep(u, "Carpeta Consultas GL");
				paginaInicial.clickOnResponsabilidad(careptaConsultasGl);
				
				System.out.println(
						"*****************************************PASO 4************************************************");
				
				addStep("Ejecutar el aplicativo frmservlet desde la carpeta de Descargas");
		        patternUtil.runApplet();
				testCase.addScreenShotCurrentStep("Applicativo frmservlet");
								
				System.out.println(
						"*****************************************PASO 5************************************************");
				
				addStep("En la pestaña de arriba debajo File esta el boton de Find, debemos de darle click para que se nos muestre la ventana de Busqueda de Lookup Types con su buscador");

		        p.lamparaBusqueda();
				testCase.addScreenShotCurrentStep("Ventana de búsqueda Lookups");
				
				p.buscarNombreSolicitud("XXFC_GL_RST_CONFIG");
				
				System.out.println(
						"*****************************************PASO 6 y 7************************************************");
				
				addStep("En el campo de Find debemos buscar las configuraciones de XXFC_GL_RST_CONFIG y darle click en el boton Find");
			
				for (int i = 0; i <= 13; i++) {
					patternUtil.doubleClickPattern("ocDownArrow", 10);
				}
				
				addStep("En la ventana XXFC_GL_RST_CONFIG, debemos buscar el campo Code = 'RESP_MESES'  y en el MEANING ='30'");
				testCase.addScreenShotCurrentStep("Ventana XXFC_GL_RST_CONFIG");
				
				testCase.addTextEvidenceCurrentStep("El dato : "+patternUtil.textCopy());
				p.enter();
				testCase.addTextEvidenceCurrentStep("Tiene el valor de: "+patternUtil.textCopy());
				
				System.out.println(
						"*****************************************PASO 8************************************************");
				
				addStep("Actualizar el campo MEANING a 25 y guardar los cambios");
				
				// Captura la región de la pantalla para editar
				ScreenImage file = screen.capture(120,278,120,20);
				System.out.println("Saved screen as "+file);
				   
				p.soloIngresarCampo("25");
				patternUtil.clickPattern("traducciones", 5);

				p.nTAB(5);
				
				p.soloIngresarCampo("25");
				patternUtil.clickPattern("aceptararchivo", 5);

				patternUtil.clickPattern("guardar", 5);
				testCase.addScreenShotCurrentStep("Valor de Meaning cambiado");
				
				// ahora cierra la aplicacion
				p.cerrarApplet();
				
				
				System.out.println(
						"*****************************************PASO 9 y 10************************************************");
				
				String consulta_AVEBQA = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE,-25)";
				System.out.println("Consulta: " + consulta_AVEBQA);
		
				SQLResult ExecuteQuery = executeQuery(AVEBQA, consulta_AVEBQA);
		
				boolean conexiondbAVEBQA  = true;
				conexiondbAVEBQA  = ExecuteQuery.isEmpty();
		
				addStep("Se realiza la conexión a la BD de AVEBQA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Contabilizar el total de registros a depurar en la tabla XXFC_MPC_GL_TPC despues de 25 meses");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		
				assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
				
				System.out.println(
						"*****************************************PASO 11 y 12************************************************");
				addStep("Se ingresa a la página de EBS");
				inicio.login();
		
				addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");	
				testCase.addScreenShotCurrentStep(u, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 13************************************************");
				
				paginaInicial.clickOnResponsabilidad(mxoxxoServicios);
				paginaInicial.clickOnResponsabilidad(carpetaInformes);
				paginaInicial.clickOnResponsabilidad(carpetaSolicitar);
		       
				addStep("Ingresar a la responsabilidad de MXOXXO-GL17ADM-SERVICIOS y luego a las carpetas /Informes/Request/ y despues abrir Standard para que se descargue el aplicativo frmservlet");
				testCase.addScreenShotCurrentStep(u, "Standard");
				paginaInicial.clickOnResponsabilidad(careptaStandard);
				
				System.out.println(
						"*****************************************PASO 14************************************************");
				
				addStep("Ejecutar el aplicativo frmservlet desde la carpeta de Descargas");

				patternUtil.runApplet();
				testCase.addScreenShotCurrentStep("Applicativo frmservlet");
				
				System.out.println(
						"*****************************************PASO 15************************************************");
				
				addStep("Seleccionar Single Request y Dar click en el boton de OK y nos debe abrir la ventana Submit Request");
				patternUtil.solicitudUnica();
				
				testCase.addScreenShotCurrentStep("Submit request");
				
				System.out.println(
						"*****************************************PASO 16************************************************");
		
				addStep("En la ventana de Submit Request en el campo de Name dar click en el boton ... para que se muestre la ventana Reports");
				p.clickPuntosNombre();
				testCase.addScreenShotCurrentStep("Reports");
				
				System.out.println(
						"*****************************************PASO 17************************************************");
				
				addStep("En el campo de Find debemos buscar el concurrente de XXFC-Proceso de Respaldo y Depuracion Tablas de Retiro Sin Tarjeta "
						+ "y darle click en el boton Find y selecionarlo del listado dando en click en el boton OK");
				p.clickAndType("buscarXXFC","Proceso de Respaldo", false);
				patternUtil.clickPattern("buscarnavegador", 5);
				
				testCase.addScreenShotCurrentStep("XXFC-Proceso de Respaldo y Depuracion Tablas de Retiro Sin Tarjeta");
				
				System.out.println(
						"*****************************************PASO 18************************************************");
				
				addStep("En la ventana Submit Request ya con los datos del Concurrente XXFC-Proceso de Respaldo y Depuracion Tablas de Retiro Sin Tarjeta, "
						+ "le damos click en Summit y nos muestra una ventana de Caution en la cual damos click en OK");
				
				patternUtil.clickPattern("ejecutar", 5);
				testCase.addScreenShotCurrentStep("Ejecutar");
				
				p.aceptarPrecaucion();
				
				System.out.println(
						"*****************************************PASO 19************************************************");
		
				addStep("En la ventana Decision Elegimos el boton No");	
				
				String idRequest = ExtractNumbersFromText();
				System.out.println(idRequest);
				
				testCase.addScreenShotCurrentStep("No");
				patternUtil.ejecutarOtraSolicitud("No");
				
				System.out.println(
						"*****************************************PASO 20************************************************");
		
				addStep("Desde la ventana de Navigator - MXOXXO-GL17ADM-SERVICIOS vamos a View y se despliega su menu y de ahi elegimos Requests");			
				p.vers();
				
				testCase.addScreenShotCurrentStep("Solicitudes");
				
				System.out.println(
						"*****************************************PASO 21************************************************");

				addStep("En la Ventana Find Request, elegimos Specific Requests,en el campo de Request ID ingresamos el Id obtenido del paso 19 y damos click en el boton Find");
				patternUtil.clickPattern("solicitudesEspecificas", 5);
				
				u.hardWait(5);
				p.clickAndType("idSolicitud",idRequest, true);
				
				testCase.addScreenShotCurrentStep("Id de la solicitud");
				
				p.encontrar();
				
				System.out.println(
						"*****************************************PASO 22************************************************");
				
				addStep("Validamos el estatus en el que termino el Request en los Campos Phase= Completed, Status=Normal");
				
				Boolean validarFinalizado = patternUtil.validatePattern("ejecutandoVerde");

				do {
					patternUtil.clickPattern("refrescarDatos", 10);
					validarFinalizado = patternUtil.validatePattern("ejecutandoVerde");
					
				} while (validarFinalizado);				
				
				testCase.addScreenShotCurrentStep("Fase y Estado");

				System.out.println(
						"*****************************************PASO 23 y 24************************************************");

				addStep("Validamos el log del request al dar click en el boton View Log (K)");
				addStep("Validamos el registro Correcto de la ejecucion en el log");

				p.visualizarRegistro();
				testCase.addScreenShotCurrentStep("Log generado");
				
				System.out.println("***********************Se sale de oracle*************************");
				
				// ahora cierra la aplicacion
				patternUtil.clickOracle();
				p.cerrarApplet();
				
				System.out.println(
						"*****************************************PASO 25 y 26************************************************");
				
				String consulta_AVEBQA2 = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC WHERE CREATION_DATE <= ADD_MONTHS(SYSDATE,-25)";
				System.out.println("Consulta: " + consulta_AVEBQA2);
		
				SQLResult ExecuteQuery2 = executeQuery(AVEBQA, consulta_AVEBQA2);
		
				boolean conexiondbAVEBQA2  = true;
				conexiondbAVEBQA2  = ExecuteQuery2.isEmpty();
		
				addStep("Se realiza la conexión a la BD de AVEBQA de manera exitosa.");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Contabilizar el total de registros a depurar en la tabla XXFC_MPC_GL_TPC despues de 25 meses");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		
				assertFalse(conexiondbAVEBQA2, "La conexion no fue exitosa");
				
				
				System.out.println(
						"*****************************************PASO 27 y 28************************************************");
				addStep("Se ingresa a la página de EBS");
				inicio.login();
				
				addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");	
				testCase.addScreenShotCurrentStep(u, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 29************************************************");
		        
				addStep("Ingresar a la responsabilidad de Application Developer y luego a las carpetas /Application/Lookups/ y despues abrir GL Lookups para que se descargue el aplicativo frmservlet");
				testCase.addScreenShotCurrentStep(u, "Carpeta Consultas GL");
				paginaInicial.clickOnResponsabilidad(careptaConsultasGl);
				
				System.out.println(
						"*****************************************PASO 30************************************************");
				
				addStep("Ejecutar el aplicativo frmservlet desde la carpeta de Descargas");
		        patternUtil.runApplet();
				testCase.addScreenShotCurrentStep("Applicativo frmservlet");
								
				System.out.println(
						"*****************************************PASO 31************************************************");
				
				addStep("En la pestaña de arriba debajo File esta el boton de Find, debemos de darle click para que se nos muestre la ventana de Busqueda de Lookup Types con su buscador");

		        p.lamparaBusqueda();
				testCase.addScreenShotCurrentStep("Ventana de búsqueda Lookups");
				
				p.buscarNombreSolicitud("XXFC_GL_RST_CONFIG");
				
				
				System.out.println(
						"*****************************************PASO 32 y 33************************************************");
				
				addStep("En el campo de Find debemos buscar las configuraciones de XXFC_GL_RST_CONFIG y darle click en el boton Find");
			
				for (int i = 0; i <= 13; i++) {
					patternUtil.doubleClickPattern("ocDownArrow", 10);
				}
				
				addStep("En la ventana XXFC_GL_RST_CONFIG, debemos buscar el campo Code = 'RESP_MESES'  y en el MEANING ='25'");
				testCase.addScreenShotCurrentStep("Ventana XXFC_GL_RST_CONFIG");
				
				testCase.addTextEvidenceCurrentStep("El dato : "+patternUtil.textCopy());
				p.enter();
				testCase.addTextEvidenceCurrentStep("Tiene el valor de: "+patternUtil.textCopy());
				
				
				System.out.println(
						"*****************************************PASO 34************************************************");
	
				addStep("Actualizar el campo MEANING a 30 y guardar los cambios");

				file = screen.capture(120,278,120,20);
				System.out.println("Saved screen as "+file);
				   
				p.soloIngresarCampo("30");
				patternUtil.clickPattern("traducciones", 5);

				p.nTAB(5);
				
				p.soloIngresarCampo("30");
				patternUtil.clickPattern("aceptararchivo", 5);
				
				patternUtil.clickPattern("guardar", 5);
				testCase.addScreenShotCurrentStep("Valor de Meaning cambiado");
				
				// ahora cierra la aplicacion
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
	
		return "Validar Depuracion de la Base de Datos EBS con configuracion de Meaning 25(Meses)";
	}
	
	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_007_Validar_Depuracion_de_la_Base_de_Datos_EBS_con_configuracion_de_Meaning_25Meses_test";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override
	public String setPrerequisites() {
		return "La tabla XXFC_MPC_GL_TPC debe estar creada en la BD AVEBQA\r\n";
	}
	
	@AfterMethod
	public void afterMethod() {
	
	}
}
