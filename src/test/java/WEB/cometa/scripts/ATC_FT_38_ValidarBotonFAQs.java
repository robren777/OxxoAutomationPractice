package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que en cada pestaÃ±a aparezca un botÃ³n de preguntas mÃ¡s frecuentes sobre el funcionamiento del portal que nos lleve a su respectiva pantalla
* MTC-FT-45- E8- Validar botÃ³n de FAQs
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_38_ValidarBotonFAQs extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_38_ValidarBotonFAQs_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		String url = GlobalVariables.URL_PORTAL_APPS;
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Oxxo-Plaza Directos"); 
		u.get(data.get("Url"));
		u.refresh();
		u.hardWait(3);
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la pÃ¡gina de Comprobantes de Compra"); 
		pc.ClicBotonDirectos();
		pc.ClicBotonComprobantesDeCompra();
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Validar la visualizaciÃ³n de un botÃ³n de FAQ's "); 
		pc.ValidarBotonFAQsCEDIS();
		
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("SelecciÃ³n el botÃ³n de Reportes del menÃº home del MÃ³dulo"); 
		pc.ClicBotonInicio();
		pc.ClicBotonDirectos();
		
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("SelecciÃ³n el botÃ³n de Reportes del menÃº home del MÃ³dulo"); 
		pc.ValidarBotonFAQsCEDIS();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Dar click en el botÃ³n de \"editar\" de una de las preguntas de la pantalla"); 
		pc.clicEditarFAQ();
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Modificar la pregunta o respuesta y dar click en \"aceptar\""); 
		pc.clicGuardarFAQ();

		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Dar click en el botÃ³n de \"borrar\" de una de las preguntas de la pantalla"); 
		boolean validarEliminarFaq = pc.clicEliminarFAQ();;
		if(validarEliminarFaq)
			testCase.addTextEvidenceCurrentStep("El menu se muestra correctamente");
		else
			testCase.addTextEvidenceCurrentStep("El menu no se muestra correctamente");
		assertTrue(validarEliminarFaq, "El menu se muestra correctamente");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil Oxxo Plaza- Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en cada pestaÃ±a aparezca un botÃ³n de preguntas mÃ¡s frecuentes sobre el funcionamiento del portal que nos lleve a su respectiva pantalla";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_38_ValidarBotonFAQs_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
