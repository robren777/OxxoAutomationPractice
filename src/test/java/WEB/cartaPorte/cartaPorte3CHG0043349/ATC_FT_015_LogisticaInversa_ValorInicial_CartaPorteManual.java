package WEB.cartaPorte.cartaPorte3CHG0043349;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionUnidades;
import WEB.cartaPorte.objectModelPortalCP.CartaPorteManual;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.cartaPorte.objectModelPortalCP.UnidadesDetalleEquipo;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_015_LogisticaInversa_ValorInicial_CartaPorteManual extends BaseExecution{
	// Flujo Carta Porte Manual
		@Test(dataProvider = "data-provider")
		/*
		 * El objetivo es generar una carta porte Carta Porte CEDIS Carta Porte Manual
		 * Carta Porte OC
		 * 
		 * REREQUISITOS PARA LA EJECUCION:
		 * 
		 *Validar que el valor de logistica Inversa este predeterminado, no editable y se imprima en la documentacion
		 * 
		 */
		public void ATC_FT_015_LogisticaInversa_ValorInicial_CartaPorteManual_test(HashMap<String, String> data)
				throws FindFailed {
			/*
			 * VARIABLES
			 * 
			 * portalUrl = Url para acceder al portal WEB carta porte userName = usuario de
			 * acceso password = contraseña encriptada para acceder a la aplicación
			 * tipoCartaPorte = cedis, manual u oc origen = CEDIS o PT origen destino =
			 * CEDIS o PT destino check = Opcion de sección de checkbox: // cedis / plaza/
			 * terceros tipoArticulo = especial o normal nombreChofer= nombre del chofer,
			 * relacionado con el CEDIS o PT origen capturado numeroEconomico =
			 * numeroEconomico de transporte relacionado con el nombre del Chofer correo =
			 * correo adicional para enviar los documentos campo= descripcion que se muestra
			 * en la de los testcase pesoBruto = vacio para enviar y modificar el valor en
			 * los casos que sea 0, o vacio para solo para encontrar el elemento.
			 * registroIstmo= SI para seleccionar la casilla No para no seleccionar
			 * poloOrigen = Origen que pertenezca a la zona Istmo poloDestino = Destino que
			 * pertenezca a la zona Istmo logisticaInversa= Si para seleccionar y no para no
			 * seleccionar pac= pac con el que se realizará la prueba registro= Número de
			 * registros a agregar
			 */
			String portalurl = data.get("server");
			String userName = data.get("user");
			String password = PasswordUtil.decryptPassword(data.get("password"));
			String campo = data.get("campo");
			String tipoCartaPorte = data.get("tipoCartaPorte");
			testCase.setTest_Description("");
			testCase.setTest_Description(campo);
			SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
			LoginCP login = new LoginCP(u, testCase, portalurl);
			MenuPortal portalmenu = new MenuPortal(u, testCase);
			PortalCartaPorte portalCP = new PortalCartaPorte(u, testCase);
			AdministracionUnidades admonUnidades = new AdministracionUnidades(u, testCase);
			UnidadesDetalleEquipo unidadesDetalle = new UnidadesDetalleEquipo(u, testCase);
			CartaPorteManual manual = new CartaPorteManual(u, testCase, portalCP);
			String mensajeSeleccion = "No fue posible seleccionar elemento: ";
			String mensajeEncontrar = "No fue posible encontrar elemento: ";
			String mensajeCaptura = "No fue posible capturar datos ";
			SikuliUtil sk = new SikuliUtil();
			String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
			Pattern pathimg = new Pattern();
			ArrayList<String> ListImage = new ArrayList<String>();
			HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
			ArrayList<Pattern> patterns = new ArrayList<Pattern>();
			File Fol = new File(pathImg);
			MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
			// Preparar datos
			

			// PASO 1:
			addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
			u.get(login.getUrl());
			login.proceedToPortal();
			boolean cargarPagina = login.encontrarMensajeInicio();
			testCase.addScreenShotCurrentStep("CargarURL");
			assertTrue("El sitio no fue cargado satisfactoriamente", cargarPagina);
			// PASO 2:
			addStep("Capturar credenciales de acceso para iniciar sesion");
			login.cicloLogin2(userName, password);
			String mensajePortal = portalmenu.accesoMenuPortal(userName, password);
			assertTrue("No se encontro mensaje: Portal de Aplicaciones",
					mensajePortal.compareTo("Portal de Aplicaciones") == 0);

			// PASO 3:
			addStep("Seleccionar: Portal Carta Porte");
			portalmenu.seleccionarOpcionCartaPorte();
			boolean encontrarMenuCP = portalCP.encontrarMenuOpcionesCartaPorte();
			assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);

			// PASO 4:
			addStep("Seleccionar Menu de Carta Porte");
			String encontrarOpcionMenuCP = portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
			assertEquals(encontrarOpcionMenuCP, "Carta Porte Manual", "No se encontro la opcion: Carta Porte Manual");

			// PASO 5:
			addStep("Seleccionar opcion: Carta Porte manual");
			String seleccionarCPManual = portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
			assertEquals(seleccionarCPManual, "Carta Porte Manual", "No se encontro el texto: Carta Porte Manual");// PASO
																													// 29
			u.scroll(500);
			//PASO 6:
			addStep("Obtener valor de campo: Logistica inversa");
			String campoActual="estatusLogisticaInversa";
			String obtenerValor=manual.obtenerTexto(campoActual);
			assertEquals(obtenerValor, "Seleccionado", "El valor inicial de Logistica Inversa debe mostrarse seleccionable");
			
			//PASO 7:
			addStep("Editar valor de Logistica inversa");
			campoActual="logisticaInversa";
			boolean resultado=manual.seleccionarElemento(campoActual,true);
			//esultado=portalCedis.seleccionarElemento(campoActual, true, "");
//NEW		
			assertTrue( "No fue posible seleccionar el campo: "+campoActual, resultado);
			
			//PASO 6:
			addStep("Obtener valor de campo: Logistica inversa");
			campoActual="estatusLogisticaInversa";
			obtenerValor=manual.obtenerTexto(campoActual);
			assertEquals(obtenerValor, "Seleccionado", "El valor inicial de Logistica Inversa debe msotrarse seleccionable");
			
			
			
			u.close();
		}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Generar una carta porte Carta Porte CEDIS Carta Porte Manual";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_LogisticaInversa_ValorInicial_CartaPorteManual_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void obtenerTiempoDeEjecucion(ITestResult result) {
		long ms = result.getEndMillis() - result.getStartMillis();
		int ss = (int) (ms / 1000) % 60;
		int mm = (int) (ms / (1000 * 60) % 60);
		int hh = (int) ((ms / (1000 * 60 * 60)) % 24);
		String tiempoDeEjecucion = String.format("%02d:%02d:%02d", hh, mm, ss);

		getTestCase().addTextEvidenceCurrentStep("Tiempo de ejecución:  " + tiempoDeEjecucion);
		System.out.println("Tiempo de ejecución:  " + tiempoDeEjecucion);
	}
}
