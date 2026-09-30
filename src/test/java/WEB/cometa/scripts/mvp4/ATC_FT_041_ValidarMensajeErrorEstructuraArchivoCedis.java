package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que si se detecta un archivo distinto en el formato de acreditacion el sistema deberá mostrar al usuario el siguiente mensaje: \"No se encontró el formato de verificación en versión digital disponible para consulta\" "
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_041_Validar que al utilizar usuario CEDIS e intentar visualizar el formato de verificacion y se detecta un archivo con estructura distinta a la establecida se muestra mensaje
* 
* **PREREQUISITOS:
*1. Credenciales para el perfil CEDIS
*2.Liga de Portal Cometa
*
*INSUMOS: 
 *Contar con información disponible en tabla xxcm_historico_cedis_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_041_ValidarMensajeErrorEstructuraArchivoCedis extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_041_ValidarMensajeErrorEstructuraArchivoCedis_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator

		addStep("Precondicion");
		boolean ResultPre = pc.ValidaBDExistanDatosFiltrosGridCedis(true, user);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con usuario Cedis
		 */
		addStep("Ingresar a la liga del portal con usuario CEDIS"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(10);
		validacion = pc.accessControlScreenSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 3 - Ingresar al portal Cometa desde access control
		 */
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScCedisSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 4 - Ingresar a CEDIS
		 */
		addStep("Ingresar a CEDIS");
		validacion = pc.opcCedisPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.cedisPortalCometaScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Posicionarse al icono CEDIS se despliega una lista o hacer clic sobre el menú cedis
		 */
		addStep("Posicionarse al icono CEDIS se despliega una lista o hacer clic sobre el menú cedis");
		validacion = pc.selecListaCedis();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(2);
		validacion = pc.opcListaCedis();
		assertTrue(validacion,"No se muestra la lista de manera correcta");
		
		/*
		 * Paso 6 - Seleccionar Recepción de Transferencias y Devoluciones
		 */
		addStep("Seleccionar Recepción de Transferencias y Devoluciones");
		validacion = pc.opcRTyDevoluciones();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.RTyDevolucionesScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 7 - Realizar una busqueda que tenga información
		 */
		addStep("Realizar una busqueda que tenga información");
		validacion = pc.busquedaFiltroPlazaFechaCedisSinVerif(user,true);
        assertTrue(validacion, "No se regresaron datos del filtro");
		
		/*
		 * Paso 8 - Dar click en formato de verificacion
		 */
		addStep("dar click en formato de verificacion");
		validacion = pc.scFormatoVerificacion();
		validacion = pc.ckFirstFormVerificacion();
		assertTrue(validacion,"No se pudo seleccionar de manera correta");
		
		/*
		 * Paso 9 - Se muestra mensaje "No se encontró el formato de verificación en versión digital disponible para consulta"
		 */
		addStep("Se muestra mensaje \"No se encontró el formato de verificación en versión digital disponible para consulta\"");
		validacion = pc.msgDevNoSeRealizoPorVD();
		assertTrue(validacion,"No se muestra mensaje de manera correcta");


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	@AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil CEDIS"
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que si se detecta un archivo distinto en el formato de acreditacion el sistema deberá mostrar al usuario el siguiente mensaje: \"No se encontró el formato de verificación en versión digital disponible para consulta\" \r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_041_ValidarMensajeErrorEstructuraArchivoCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}