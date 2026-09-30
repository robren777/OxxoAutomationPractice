package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que la información mostrada al usuario deberá de corresponder fielmente al rango horario seleccionado, sin importar desde qué parte del país se conecta / encuentra, en la pantalla de Liquidación de Ruta del modulo CEDIS, con usuario Admin"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_362_Validar información mostrada al usuario deberá corresponder fielmente al rango horario seleccionado, sin importar desde qué parte del país se conecta / encuentra, con usuario Admin
*
*PREREQUISITOS:
*1. Credenciales para el perfil CEDIS con usuario [Admin] validar desde el criterio 
*2. Liga de Portal Cometa 
*3. Contar con acceso de configuración Zona Horaria 
*
*INSUMOS: 
 *Contar con información disponible en tabla xcm_historico_correos.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Noviembre 2025
*/
public class ATC_FT_362_ValidarInformacionIndependienteDeHorarioUsuarioAdmin extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_362_ValidarInformacionIndependienteDeHorarioUsuarioAdmin_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRutaPropuesta(user);
		pc.obtenerFechaRegistroRutaPropuesta(user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil CEDIS usuario Admin para inicio de sesion 
		 */
		addStep("Ingresar credenciales correspondientes al perfil CEDIS usuario Admin para inicio de sesion ");
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
		 * Paso 5 - Ingresar a Recepción de Transferencias y Devoluciones
		 */
		addStep("Ingresar a Recepción de Transferencias y Devoluciones");
		pc.selecListaCedis();
		validacion = pc.opcRTyDevoluciones();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.RTyDevolucionesScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 6 - Dar clic en el botón de Liquidación de Ruta
		 */
		addStep("Dar clic en el botón de Liquidación de Ruta");
		validacion = pc.opcLiquidacionRuta();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.scLiquidacionRuta();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 7 - Ingresar Ruta Dinámica (UNIGIS)
		 */
		addStep("Ingresar Ruta Dinámica (UNIGIS)");
		validacion = pc.ingRutaDinamica(true);
		assertTrue(validacion,"No se pudo ingresar de manera correcta");
		
		/*
		 * Paso 8 - Realizar filtros y seleccionar  \"Fecha inicial\", \"Hora Inicial\", \"Fecha final\", \"Hora final\"
		 */
		addStep("Realizar filtros y seleccionar  \"Fecha inicial\", \"Hora Inicial\", \"Fecha final\", \"Hora final\"");
		validacion = pc.llenadoFiltrosLiquidacionRuta();
		assertTrue(validacion,"No se pudo realizar el llenado de manera correcta.");
		
		/*
		 * Paso 9 - Click en el botón consultar
		 */
		addStep("Click en el botón Consultar");
		validacion = pc.opcConsultarLiqRuta(true);
		assertTrue(validacion,"No se permite seleccionar el botón correctamente");
		
		/*
		 * Paso 10 - Validar información mostrada corresponda fielmente al rango horario seleccionado.
		 */
		u.hardWait(3);
		addStep("Validar información mostrada corresponda fielmente al rango horario seleccionado");
		validacion = pc.valInformacionLiqRutaAdmin();
		assertTrue(validacion,"No se pudo validar la información de manera correcta");
		
		/*
		 * Paso 11 - Configurar en tu equipo con una hora menos al horario actual.
		 */
		addStep("Configurar en tu equipo con una hora menos al horario actual.");
		validacion = pc.atrasarHora();
		assertTrue(validacion,"No se pudo realizar de manera correcta.");
		
		/*
		 * Paso 12 - Seleccionar con los mismo \"Fecha inicial\", \"Hora Inicial\", \"Fecha final\", \"Hora final\" seleccionados anteriormente
		 */
		addStep("Seleccionar con los mismo \"Fecha inicial\", \"Hora Inicial\", \"Fecha final\", \"Hora final\" seleccionados anteriormente");
		pc.scrollTop();
		pc.opcLimpiarFiltrosLiqRuta(false);
		u.hardWait(1);
		pc.ingRutaDinamica(false);
		validacion = pc.llenadoFiltrosLiquidacionRuta();
		assertTrue(validacion,"No se pudo realizar el llenado de manera correcta.");
		
		/*
		 * Paso 13 - Click en el botón consultar
		 */
		addStep("Click en el botón Consultar");
		validacion = pc.opcConsultarLiqRuta(true);
		assertTrue(validacion,"No se permite seleccionar el botón correctamente");
		
		/*
		 * Paso 14 - Validar información mostrada corresponda fielmente al rango horario seleccionado.
		 */
		u.hardWait(3);
		addStep("Validar información mostrada corresponda fielmente al rango horario seleccionado");
		validacion = pc.valInformacionLiqRutaAdmin();
		assertTrue(validacion,"No se pudo validar la información de manera correcta");
		
		/*
		 * Finalizar el proceso
		 */
		pc.restaurarHoraSistemaConElevacion();

		
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
		return "1. Credenciales para el perfil CEDIS con usuario [Admin] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa \n"
				+ "3. Contar con acceso de configuración Zona Horaria ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la información mostrada al usuario deberá de corresponder fielmente al rango horario seleccionado, sin importar desde qué parte del país se conecta / encuentra, en la pantalla de Liquidación de Ruta del modulo CEDIS, con usuario Admin";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_362_ValidarInformacionIndependienteDeHorarioUsuarioAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}