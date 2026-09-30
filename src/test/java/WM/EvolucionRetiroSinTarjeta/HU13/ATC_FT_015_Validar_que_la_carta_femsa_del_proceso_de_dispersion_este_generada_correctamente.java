package WM.EvolucionRetiroSinTarjeta.HU13;

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
import WEB.portal_Remesas.om.GenerarReporteMontos;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_015_Validar_que_la_carta_femsa_del_proceso_de_dispersion_este_generada_correctamente extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_Validar_que_la_carta_femsa_del_proceso_de_dispersion_este_generada_correctamente_test (HashMap<String, String> data)
			throws Exception{

				/**
				 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
				 * Desc:
				 * Validar que la carta femsa del proceso de dispersion este generada correctamente. Debera generar un archivo Excel donde debe contener los retiros + comisiones.
				 * @author Mónica Soto
				 * @date 10/04/2024
				 **/

				/*
		         * VARIABLES
		         */
				String ebsUrl = GlobalVariables.getAvanteServer;
		        String userName = GlobalVariables.get6008201User;
		        String password = GlobalVariables.get6008201Pswd;
		        
		        String mxoxxo = data.get("mxoxxo");
		        String cartasFemsa = data.get("cartasFemsa");
		        String cobroServicios= data.get("cobroServicios");				
		        String ingresosTerceros= data.get("ingresosTerceros");				
		        String xxfcReporteCartas= data.get("xxfcReporteCartas");	
		        String compania = data.get("compania");				
		        String plaza= data.get("plaza");				
		        String origenCompania= data.get("origenCompania");	
		        String cuentaCorriente= data.get("cuentaCorriente");	
		        String tipoSalida= data.get("tipoSalida");	
		        String fechaConsulta = data.get("fechaConsulta");
		        String fechaProcesoDispersion = data.get("fechaProcesoDispersion");
		
			    Screen screen = new Screen();
			   
				testCase.setTest_Description(data.get("id") + data.get("description"));
				
				/*
				 * Utilerias
				 *********************************************************************/
				//BD AVEBQA
				SQLUtil AVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
											GlobalVariables.DB_PASSWORD_AVEBQA);
		
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
				GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
				
				
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				addStep("Se ingresa a la página de EBS");
				inicio.login();
				
				System.out.println(
						"*****************************************PASO 1************************************************");
				
				addStep("Ingresar a la responsabilidad MXOXXO-GLXXXXX-CARTAS-FEMSA");
				
		        paginaInicial.clickOnResponsabilidad(mxoxxo);
				testCase.addScreenShotCurrentStep(u, "MXOXXO-GLXXXXX-CARTAS-FEMSA");
				
				System.out.println(
						"*****************************************PASO 2************************************************");
				
				addStep("Ingresar a la opcion: Captura Cartas FEMSA");
		        paginaInicial.clickOnResponsabilidad(cartasFemsa);
		        
				System.out.println(
						"*****************************************PASO 3************************************************");
				
				addStep("Ejecutar el aplicativo frmservlet.jnlp");
		        patternUtil.runApplet();
				testCase.addScreenShotCurrentStep("Applicativo frmservlet");
				
				System.out.println(
						"*****************************************PASO 4************************************************");
				
				addStep("Seleccionamos con doble click al botón Journal");
				patternUtil.clickPattern("mostrarNavegador", 5);
		        u.hardWait(2);
				testCase.addTextEvidenceCurrentStep("Se muestra el navegador");
				testCase.addScreenShotCurrentStep("Navegador");
				
				testCase.addTextEvidenceCurrentStep("Se selecciona doble click en el journal.");
				patternUtil.doubleClickPattern("journal", 5);
				
				testCase.addScreenShotCurrentStep("Journal");
				patternUtil.doubleClickPattern("journalSelect", 5);
					
				System.out.println(
						"*****************************************PASO 5, 6 y 7************************************************");
				
				addStep("Seleccionar los campos de busqueda, clic boton Encontrar");
		        u.hardWait(2);
				testCase.addScreenShotCurrentStep("Encontrar asientos");
		
				addStep("Escribir en el campo Origen: Cobro Servicios");
				p.clickAndType("origen",cobroServicios, true);
			       
			    ScreenImage file = screen.capture(600,310,100,30);
			    System.out.println("Saved screen as "+file);
			    
				p.soloIngresarCampo(fechaConsulta);
				
				addStep("Escribir en el campo Categoria: Ingresos Terceros");
				p.clickAndType("categoria",ingresosTerceros, false);	
				
				testCase.addScreenShotCurrentStep("Origen y Categoria");
		
				System.out.println(
						"*****************************************PASO 8************************************************");
				
				addStep("Dar click en Encontrar");
				patternUtil.clickPattern("encontrar3", 5);
				
				patternUtil.clickWhileExist("flechaArriba","estadoLoteVacio");
				
				testCase.addScreenShotCurrentStep("Asientos Encontrados");
		
				System.out.println(
						"*****************************************PASO 9************************************************");
				
				addStep("Se da un click en el botón lado izquierdo del Campo: Estado de Lote");				
				patternUtil.clickPattern("selectAzul", 10);
		        u.hardWait(2);
		
				System.out.println(
						"*****************************************PASO 10 y 11************************************************");
				
				addStep("Validar que la contabilizacion de la carta sea correcta");
				addStep("Validar que la poliza contable en GL sea correcta");
				testCase.addScreenShotCurrentStep("Asiento");
		
		        u.hardWait(2);
				patternUtil.clickPattern("cerrar2", 5);
		
				
				System.out.println(
						"*****************************************PASO 12************************************************");
				
				addStep("Seleccionar en el menu la opcion Ver(X) y Solicitudes");
				p.vers();
				testCase.addScreenShotCurrentStep("Solicitudes");
		
		
				System.out.println(
						"*****************************************PASO 13************************************************");
				
				addStep("Dar click en Ejecutar Nueva Solicitud");
				patternUtil.clickPattern("ejecutarNuevaSolicitud2", 5);
				testCase.addScreenShotCurrentStep("Nueva Solicitud");
		
				System.out.println(
						"*****************************************PASO 14 y 15************************************************");
		
				addStep("En el campo de Nombre buscar  -  (XXFC - Reporte de Conciliacion FEMCO Cartas)");
				p.clickAndType("nombreclick",xxfcReporteCartas, true);	
				
				addStep("Seleccionar Aceptar");
				testCase.addScreenShotCurrentStep("Nueva Solicitud");
				
				System.out.println(
						"*****************************************PASO 16************************************************");
				
				addStep("Seleccionar Ejecutar y muestra un mensaje de Decisión dar clic en No");
				
				// se agrega una compañia
				p.parametros7campos(compania,plaza,origenCompania,cuentaCorriente,tipoSalida,fechaConsulta);
				
				patternUtil.clickPattern("aceptarcerrar", 5);				
				patternUtil.clickPattern("ejecutar", 5);
				
				String idRequest = ExtractNumbersFromText();
				System.out.println(idRequest);
				
				testCase.addScreenShotCurrentStep("No");
				patternUtil.ejecutarOtraSolicitud("No");
				
				System.out.println(
						"*****************************************PASO 17************************************************");
				
				addStep("Seleccionar Encontrar");
				patternUtil.clickPattern("solicitudesEspecificas", 5);
				p.clickAndType("idSolicitud",idRequest, true);	
				
				patternUtil.clickPattern("encontrar", 15);
		
				testCase.addScreenShotCurrentStep("Id de la solicitud");
				
				System.out.println(
						"*****************************************PASO 18************************************************");
				
				addStep("Seleccionar Visualizar Output");
				
				patternUtil.clickWhileExist("refrescarDatos","ejecutandoVerde");
		
				p.visualizarOutput();
				testCase.addScreenShotCurrentStep("Output");
				
				System.out.println(
						"*****************************************PASO 19************************************************");
				
				addStep("Abrir el archivo excel descargado");
				rm.abrirExcelEdge("reporteConciliacion2","");	
				
				u.waitForLoadPage();
				
				System.out.println(
						"*****************************************PASO 20************************************************");
				
				addStep("Dar clic en la seccion Detalle");
				patternUtil.clickPattern("detalleExcel", 5);
		        u.hardWait(2);
								
				System.out.println(
						"*****************************************PASO 21************************************************");
				
				addStep("Validar la tabla Cartas");
		
				testCase.addScreenShotCurrentStep("Sección Detalle");
		        u.hardWait(5);
		
				patternUtil.closeWindow();
				
				System.out.println(
						"*****************************************PASO 22 y 23************************************************");
				
				String consulta_AVEBQA = "SELECT FORMA_PAGO, CR_PLAZA, CR_TIENDA, ACCOUNTING_DATE, IMPORTE FROM apps.xxfc_gl_rst_lineas WHERE accounting_date = TO_DATE ('"+fechaProcesoDispersion+"', 'DD/MM/YY') fetch first 10 rows only";
				System.out.println("Consulta: " + consulta_AVEBQA);
		
				SQLResult ExecuteQuery = executeQuery(AVEBQA, consulta_AVEBQA);
		
				boolean conexiondbAVEBQA  = true;
				conexiondbAVEBQA  = ExecuteQuery.isEmpty();
				
				String consulta2AVEBQA = "SELECT FORMA_PAGO,CR_PLAZA, ACCOUNTING_DATE, IMPORTE_PLAZA, POLIZA_OS, POLIZA_PLAZA  FROM apps.xxfc_gl_rst_ENCA WHERE accounting_date = TO_DATE ('"+fechaProcesoDispersion+"', 'DD/MM/YY') fetch first 10 rows only";
				System.out.println("Consulta: " + consulta2AVEBQA);
		
				SQLResult ExecuteQuery2 = executeQuery(AVEBQA, consulta2AVEBQA);
				
				boolean conexiondbAVEBQA2  = true;
				conexiondbAVEBQA2  = ExecuteQuery2.isEmpty();
				
				addStep("Ingresar a la Base de Datos AVEBQA");
				testCase.addTextEvidenceCurrentStep("Conexión: AVEBQA");
				testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
				
				addStep("Realizar las siguientes consultas");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		
				assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
				assertFalse(conexiondbAVEBQA2, "La conexion no fue exitosa");
		
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
	       screen.w = 70;
	       screen.h = 70;

	       screen.highlight(2);
	       
		   ScreenImage file = screen.capture(725,320,70,70);
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
	
		return "Validar que la carta femsa del proceso de dispersion este generada correctamente. Debera generar un archivo Excel donde debe contener los retiros + comisiones.";
	}
	
	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_015_Validar_que_la_carta_femsa_del_proceso_de_dispersion_este_generada_correctamente_test";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
	
	}
}
