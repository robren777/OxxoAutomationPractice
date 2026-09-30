package WorkFlows.Panp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Panp3.PomPanp;
import WorkFlows.util.Panp3.UtilsPanp;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_002_AltaDeComisionesDeMPEenElPANP_ValidacionDePantalla extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_AltaDeComisionesDeMPEenElPANP_ValidacionDePantalla(HashMap<String, String> data) {		
		
		/*
		 * variables
		 */
		String userUltimus = GlobalVariables.ULTIMUS_PANP3_USER_MERCANCIAS;
		String userUltimusAdmin = GlobalVariables.ULTIMUS_PANP3_USER_ADMIN;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		PomPanp pom = new PomPanp(u, userUltimus, testCase);
		UtilsPanp panp = new UtilsPanp();
		String RFC = panp.generaRfc();
		
		try {
			///////////////////////////////////////////////////////////
			addStep("Seleccionar el tipo de proveedor valido y Pulsar continuar");
			// Step 1.-  /////////////////////////////////////////////////////////
			addStep("Ingresar a la liga: http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx");
			pom.deleteCookies();
			u.get(data.get("urlUltimus"));
			testCase.addScreenShotCurrentStep(u,"Se muestra la pantalla de Ultimus");
			assertTrue(pom.validateAccessUltimus(), "No se pudo acceder a la pantalla de Ultimus");

			// Step 2.-/////////////////////////////////////////////////////////
			addStep("Se ingresa el Usuario : 6005151\r\n"
					+ "Contraseña: (Vacio)\r\n"
					+ "Dominio: comercio.fne\r\n"
					+ "\r\n"
					+ "Y seleccionar el boton de Conectarse.");
			pom.loginUltimus();
			
			addStep("Seleccionar PANP3");
			pom.accessPanp3();
			testCase.addScreenShotCurrentStep(u, "Se valida que se accede correctamente a la pantalla de Alta de Proveedor correspondiente al \"tipo\" seleccionado anteriormente.");
			assertTrue(pom.validateAccessAltaProvedor(), "No se pudo acceder a la pantalla de Alta de Provedor");

			///////////////////////////////////////////////////////////
			addStep("Informar en el Formulario de Alta los siguientes campos;\r\n"
					+ "-Tipo de Persona\r\n"
					+ "-Nacionalidad\r\n"
					+ "-RFC\r\n"
					+ "Pulsar Enviar");
			pom.accessAltaProvedor(RFC);
			testCase.addScreenShotCurrentStep(u, "Validar que se despliega el Formulario de llenado para Alta de proveedor");
			assertTrue(pom.validateLlenadoDeFormulario(), "Fallo el despliegue del formulario para Alta de proveedor");
			
			/////////////////////////////////////////////////////////
			addStep("Informar Correctamente todos los campos del Formulario y Pulsar Enviar");
			pom.clickEnviar();
			testCase.addScreenShotCurrentStep(u, "Se valida que se muestra un mensaje indicando que la informacion ha sido guardada exitosamente");
			assertTrue(pom.validateGuardado(), "La informacion no se guardo exitosamente");
			pom.clickContinuar();
			
			/////////////////////////////////////////////////////////
			addStep("Se accede a Ultimus con usuario Categoria de Servicio");
			pom.deleteCookies();
			u.get(data.get("urlUltimus"));
			pom = new PomPanp(u, userUltimusAdmin, testCase);
			pom.loginUltimus();
			testCase.addScreenShotCurrentStep(u, "Se accede correctamente a la Bandeja de Entrada del usuario de Categoria de Servicio.");
			assertTrue(pom.validateAccessInbox(), "Fallo el acceso a la Bandeja de Entrada");			
			
			/////////////////////////////////////////////////////////
			addStep("En la bandeja de entrada de Ultimus, seleccionar el registro correspondiente a la Alta de proveedor (RFC) \r\n"
					+ "Pulsar Abrir.");
			pom.selectInbox();
			pom.buscaRFC(RFC);
			pom.validateAccessAdminConvenios();
			pom.accessAdminConvenios();
			
			/////////////////////////////////////////////////////////
			addStep("Se llena la informacion correspondiente a los convenios y se autoriza la solicitud");
			pom.RegistraConvenio(RFC);
			pom.validateEnvioConvenio();
			testCase.addScreenShotCurrentStep(u, "Se muestra el mensaje que ha sido guardada la informacion exitosamente.");
			
			
			//u.close();
		}catch(Exception ex) {;
			testCase.addScreenShotCurrentStep(u, ex.getMessage());
			assertTrue(false, ex.getMessage());
		}
	}

	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Se tiene por objetivo validar la pantalla de comisiones en PANP";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_AltaDeComisionesDeMPEenElPANP_ValidacionDePantalla";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
