package WEB.finiquitoTiendas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.finiquitoTiendas.om.Pantalla_AdministracionFiniquitos;
import WEB.finiquitoTiendas.om.pantalla_mensajesEnReportes;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validacion modificacion en pantalla Mensajes en Reportes.
* MTC_FT_007. Mensajes en Reportes
* @author Andrea Cano
* @date Diciembre 2022
*/
public class ATC_FT_007_MensajesEnReportes extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
  public void ATC_FT_007_MensajesEnReportes_test(HashMap<String, String> data) throws Exception {
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		
		
				// ------------------------------------PASO 1
				addStep("Ingresar al Portal de Aplicaciones	\n"
						+ "Resultado Esperado: Se muestra la pagina de inicio de sesi�n"); 																																								// excel
				u = new SeleniumUtil(new MEdgeTest(), true); // IETest para usar internet explorer

				PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
				Pantalla_AdministracionFiniquitos af = new Pantalla_AdministracionFiniquitos(u, testCase);
				pantalla_mensajesEnReportes pm = new pantalla_mensajesEnReportes(u,testCase);
				MetodosExtra me = new MetodosExtra(u,testCase);
				u.get(data.get("Url")); // toma el valor Url del data provider
				u.hardWait(3); // tiempo en segundos
				pa.proceedToPortal(); // es para lo de la pagina no segura
				
				// ------------------------------------PASO 2		
				addStep("Capturar un usuario y contrase�a validos	\n"
						+ "Resultado Esperado: Se muestra el Portal de aplicaciones con el listado de aplicaciones");
				pa.cicloLogin(user, password);
				
				// ------------------------------------PASO 3
				addStep("Seleccionar en el listado de aplicaciones Finiquito en Tienda	\n"
						+ "Resultado Esperado: Se muestra la pagina principal Administraci�n de Finiquitos");
				af.clickContainer();
				System.out.println("paso 3");
				
				// ------------------------------------PASO 4
				addStep("En el menu superior seleccionar Administraci�n y posteriormente se da clic al bot�n de Mensajes de Reportes.\n"
						+ "Resultado Esperado: Se muestra pantalla de Incentivos.");
				pm.clickAdminiYMensajesEnReportes();
				System.out.println("paso 4");
				
				// ------------------------------------PASO 5
				addStep("Dar clic en el bot�n de Modificar.	\n"
						+ "Resultado Esperado: Se habilitan los campos para su edici�n.");
				pm.clicBtnkModificar();
				System.out.println("paso 5");
				
				// ------------------------------------PASO 6
				addStep("Modificar alg�n campo y dar clic al bot�n Guardar	\n"
						+ "Resultado Esperado: Se muestra mensaje de La modificaci�n se realiz� exitosamente.");
				pm.modificarMensajeEnReportes();
				System.out.println("paso 6");
	
				boolean validacion = me.comprobarMensajeAlerta("La modificaci�n se realiz� exitosamente.");
				assertTrue(validacion, "No se muestra el mensaje: La modificaci�n se realiz� exitosamente.");
				
				
  }			

	
	
	
	
	
	
	
@Override
public void beforeTest() {
	// TODO Auto-generated method stub	
}

@Override
public String setTestDescription() {
	return "ATC_FT_007_MensajesEnReportes_test";
}

@Override
public String setTestDesigner() {
	return "AutomationQA";
}

@Override
public String setTestFullName() {
	return "ATC_FT_007_MensajesEnReportes_test";
}

@Override
public String setTestInstanceID() {
	return null;
}

@Override
public String setPrerequisites() {
	return null;
}


}
