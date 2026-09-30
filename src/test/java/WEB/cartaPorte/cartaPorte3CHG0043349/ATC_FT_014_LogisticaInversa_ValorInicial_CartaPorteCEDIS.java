package WEB.cartaPorte.cartaPorte3CHG0043349;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.CartaPorteCedis;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_014_LogisticaInversa_ValorInicial_CartaPorteCEDIS extends BaseExecution{
	
		@Test(dataProvider = "data-provider")
		public void ATC_FT_014_LogisticaInversa_ValorInicial_CartaPorteCEDIS_test(HashMap<String, String> data) throws FindFailed, SQLException {
			String portalurl = data.get("server");
			String userName=data.get("user");
			String password = PasswordUtil.decryptPassword(data.get("password"));
			String campo=data.get("campo"); 
			String tipoCartaPorte = data.get("tipoCartaPorte");
			testCase.setTest_Description("");
			testCase.setTest_Description(campo);
			SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
			LoginCP login = new LoginCP(u, testCase, portalurl);
			MenuPortal portalmenu= new MenuPortal(u, testCase);
			PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
			CartaPorteCedis portalCedis= new CartaPorteCedis(u,testCase,portalCP);
			//PASO 1:
			addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
			u.get(login.getUrl());
			login.proceedToPortal();
			boolean cargarPagina=login.encontrarMensajeInicio();
			testCase.addScreenShotCurrentStep("CargarURL");
			assertTrue( "El sitio no fue cargado satisfactoriamente",cargarPagina);
			//PASO 2: 
			addStep("Capturar credenciales de acceso para iniciar sesión");
			login.cicloLogin2(userName, password);
			String mensajePortal=portalmenu.accesoMenuPortal(userName, password);
			assertTrue("No se encontró mensaje: Portal de Aplicaciones", mensajePortal.compareTo("Portal de Aplicaciones")==0);
			//PASO 3: 
			addStep("Seleccionar \"Portal Carta Porte\"");
			portalmenu.seleccionarOpcionCartaPorte();
			boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
			assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);
			
			//PASO 4: 
			addStep("Seleccionar Menu de Carta Porte");
			String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
			assertEquals(encontrarOpcionMenuCP, "Carta Porte CEDIS", "No se encontro la opcion: Carta Porte Cedis");
			
			//PASO 5:
			addStep("Seleccionar opcion \"Carta Porte Cedis\"");
			String seleccionarCPCedis=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
			assertEquals(seleccionarCPCedis, "Carta Porte Entrega", "No se encontro el texto: Carta Porte Cedis");
			u.scroll(500);
			//PASO 6:
			addStep("Obtener valor de campo: Logistica inversa");
			String campoActual="estatusLogisticaInversa";
			String obtenerValor=portalCedis.obtenerTexto(campoActual);
			getTestCase().addTextEvidenceCurrentStep("Estatus: seleccionado");
			assertEquals(obtenerValor, "Seleccionado", "El valor inicial de Logistica Inversa debe mostrarse seleccionable");
			
			//PASO 7:
			addStep("Editar valor de Logistica inversa");
			campoActual="logisticaInversa";
			boolean resultado=portalCedis.seleccionarElemento(campoActual, true, "");
			getTestCase().addTextEvidenceCurrentStep("El estatus se mantiene seleccionado");
			assertTrue( "No fue posible seleccionar el campo: "+campoActual, resultado);
			
			//PASO 6:
			addStep("Obtener valor de campo: Logistica inversa");
			campoActual="estatusLogisticaInversa";
			obtenerValor=portalCedis.obtenerTexto(campoActual);
			assertEquals(obtenerValor, "Seleccionado", "El valor inicial de Logistica Inversa debe msotrarse seleccionable");
			getTestCase().addTextEvidenceCurrentStep("El estatus se mantiene seleccionado");
			
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
		return "Logistica inversa; generar valor inicial en Carta Porte CEDIS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_LogisticaInversa_ValorInicial_CartaPorteCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
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
