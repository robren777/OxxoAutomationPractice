package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_InstructivoFiniquitoO;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validacion de no afectacion.
* MTC_FT_009. Instructivo Finiquito Optimo.
* @author Yair Liz�rraga
* @date Diciembre 2022
*/
		public class ATC_FT_009_Instructivo_Finiquito_Optimo extends BaseExecution { 
			SeleniumUtil u;
	
			@Test(dataProvider = "data-provider")
		public void ATC_FT_009_Instructivo_Finiquito_Optimo_test (HashMap<String, String> data) throws Exception {

		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = GlobalVariables.URL_PORTAL_APPS_SAR;
		String Mensaje = data.get("MensajeCFRO");
		Boolean validacion;
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pantalla_InstructivoFiniquitoO pif = new Pantalla_InstructivoFiniquitoO (u,testCase);

		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones "+url+" "); 
		
	    PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Capturar un usuario y contrase�a validos"); 
		
		pa.proceedToPortal(); 
		pa.cicloLogin(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar en el listado de aplicaciones Finiquito en Tienda"); 
		pif.clickContainer();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("En el menu superior seleccionar Administraci�n y posteriormente se da clic al boton de Instructivo de Finiquito �ptimo."); 
		pif.MostrarMensajeDeInstructivoFO();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Dar clic en el bot�n de Modificar."); 
		pif.clicBtnModificar();
		
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Modificar alg�n campo y dar clic al bot�n Guardar"); 
		pif.camposMensajeCFRO(Mensaje);
		pif.clicBtnGuardar();
		
		validacion = pif.validarAlerta();
		assertTrue(validacion, "La modificaci�n se realiz� exitosamente.");
		
		
		
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
				return null;
			}

			@Override
			public String setTestDesigner() {
				// TODO Auto-generated method stub
				return "AutomationQA";
			}

			@Override
			public String setTestFullName() {
				// TODO Auto-generated method stub
				return "ATC_FT_009_Instructivo_Finiquito_Optimo_test";
			}

			@Override
			public String setTestInstanceID() {
				// TODO Auto-generated method stub
				return null;
			}
}