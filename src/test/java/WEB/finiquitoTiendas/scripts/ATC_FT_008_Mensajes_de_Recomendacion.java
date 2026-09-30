package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_AdministracionMRecomendacion;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validacion de no afectacion.
* MTC_FT_008. Mensajes de Recomendacion.
* @author Yair Liz�rraga
* @date Diciembre 2022
*/
		public class ATC_FT_008_Mensajes_de_Recomendacion extends BaseExecution { 
			SeleniumUtil u;
	
			@Test(dataProvider = "data-provider")
		public void ATC_FT_008_Mensajes_de_Recomendacion_test (HashMap<String, String> data) throws Exception {

		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = GlobalVariables.URL_PORTAL_APPS_SAR;
		String Concepto = data.get("Concepto");
		String Estatus = data.get("Estatus");
		String Mensaje = data.get("Mensaje");
		Boolean validacion;
		u = new SeleniumUtil(new MEdgeTest(), true);
		Pantalla_AdministracionMRecomendacion par = new Pantalla_AdministracionMRecomendacion (u,testCase);

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
		par.clickContainer();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("En el menu superior seleccionar Administraci�n y posteriormente se da clic al boton de Mensajes de Recomendaci�n."); 
		par.MostrarMensajeDeRecomendacion();
			
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Llenar los campos de Concepto, Estatus, Mensaje y dar clic al bot�n Agregar."); 
		validacion = par.capturaCamposMensajeRecomendacion(Concepto, Estatus, Mensaje);
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
				return"AutomationQA";
			}

			@Override
			public String setTestFullName() {
				return "ATC_FT_008_Mensajes_de_Recomendacion_test";
			}

			@Override
			public String setTestInstanceID() {
				// TODO Auto-generated method stub
				return null;
			}
}