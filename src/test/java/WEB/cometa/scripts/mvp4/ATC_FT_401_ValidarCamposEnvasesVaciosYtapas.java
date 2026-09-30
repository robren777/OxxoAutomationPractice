package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar los campos Envases vacíos y Tapas en la pantalla Liquidación de Ruta. Para poder consultar esta información tanto a nivel Ruta (sumatoria) como a nivel Tienda-Transferencia (detalle)"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_401_Validar campos Envases vacíos y Tapas
*
*PREREQUISITOS:
*1. Credenciales para el perfil CEDIS con usuario [CEDIS y Admin] validar desde el criterio 
*2. Liga de Portal Cometa 
*
*INSUMOS: 
 *Contar con información disponible en tabla xxcm_historico_correos.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Maximino Ramirez
* @date Enero 2026
*/
public class ATC_FT_401_ValidarCamposEnvasesVaciosYtapas extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_401_ValidarCamposEnvasesVaciosYtapas_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRutaPropuesta(user);
		assertTrue(validacion,"No se obtuvieron datos en la tabla");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/* * Paso 1  - Ingresar a la liga del portal
		*/
		addStep(" Ingresar a la liga del portal");
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");

		/* * Paso 2 - Ingresar credenciales correspondientes al perfil CEDIS usuario CEDIS para inicio de sesion 
		*/
		addStep("Ingresar credenciales correspondientes al perfil CEDIS usuario CEDIS para inicio de sesion ");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se logro iniciar sesión de manera correcta");

		/* * Paso 3 - Ingresar al portal Cometa desde access control
		*/
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);

		/* * Paso 4 - Ingresar a CEDIS
		*/
		addStep("Ingresar a CEDIS");
		validacion = pc.selecListaCedis();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");


		/* * Paso 5 - Ingresar a Recepción de Transferencias y Devoluciones
		*/
		addStep("Ingresar a Recepción de Transferencias y Devoluciones");
		validacion = pc.opcListaCedis();
		assertTrue(validacion,"No se selecciono correctamente la opcion de la lista");
		validacion = pc. opcRTyDevoluciones();
		assertTrue(validacion,"No se selecciono correctamente Recepcion de Transferencias y Devoluciones");
		

		/* * Paso 6 - Dar clic en el botón de Liquidación de Ruta
		*/
		addStep("Dar clic en el botón de Liquidación de Ruta");
		boolean resultStep6 = pc.opcLiquidacionRuta();
		assertTrue(resultStep6,"No se pudo seleccionar la opción de manera correcta");
		boolean resultStep7 = pc.scLiquidacionRuta();
		assertTrue(resultStep7,"No se muestra la pantalla de manera correcta");


		/* * Paso 7 - Ingresar Ruta Dinómica (UNIGIS)
		*/
		addStep(" Ingresar Ruta Dinómica (UNIGIS)");
		validacion = pc.ingRutaDinamica(true);
		assertTrue(validacion,"no se ingreso la Ruta Dinamica ");

		/* * Paso 8 - Realizar filtros y seleccionar  "Fecha inicial", "Hora Inicial", "Fecha final", "Hora final"
		*/
		addStep("Realizar filtros y seleccionar  \"Fecha inicial\", \"Hora Inicial\", \"Fecha final\", \"Hora final\"");
		validacion = pc.llenadoFiltrosLiquidacionRuta2();
		assertTrue(validacion,"No se llenan los filtros correctamente");


		/* * Paso 9 - Dar clic en el Boton Consultar
		*/
		addStep("Dar clic en el Boton Consultar");
		validacion = pc.opcConsultarLiqRuta(true);
		assertTrue(validacion," No se da clic al botón consultar");
		
		/* * Paso 10 - Validar los nuevos campos en el Primer renglón "datos consolidados/sumados"
		*/
		addStep(" Validar los nuevos campos en el Primer renglón \"datos consolidados/sumados\"");
		validacion = pc.validarCamposConsolidados(true);
		assertTrue(validacion,"No se encuentran los datos consolidados / sumados");

		/* * Paso 11 - Validar los campos Renglones siguientes "detalle por tienda o transferencia"
		*/
		addStep("Validar los campos Renglones siguientes \"detalle por tienda o transferencia\"");
		validacion = pc.validarDetalleEnvasesYTapasBajo(true);
		assertTrue(validacion,"No se encuentran los datos de abajo");


		/* * Paso 12 - Realizar otra consulta 
		*/
		addStep("Realizar otra consulta ");
		validacion = pc.llenadoFiltrosLiquidacionRuta2();
		assertTrue(validacion,"No se llenan los filtros correctamente");
		validacion = pc.opcConsultarLiqRuta(true);
		assertTrue(validacion,"");

	}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	
	@AfterMethod
	public void afterMethod() {
		if (u != null && u.getDriver() != null) {
			u.getDriver().quit();
		}
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil CEDIS con usuario [CEDIS y Admin] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar los campos Envases vacíos y Tapas en la pantalla Liquidación de Ruta. Para poder consultar esta información tanto a nivel Ruta (sumatoria) como a nivel Tienda-Transferencia (detalle)";
	}

	@Override
	public String setTestDesigner() {
		return "Maximino Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_401_ValidarCamposEnvasesVaciosYtapas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}