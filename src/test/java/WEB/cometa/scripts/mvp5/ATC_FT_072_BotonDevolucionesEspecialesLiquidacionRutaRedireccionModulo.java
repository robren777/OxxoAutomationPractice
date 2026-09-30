package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Valida el botón Devoluciones Especiales en Liquidación de Ruta y la redirección al módulo correspondiente con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_072 Botón Devoluciones Especiales en Liquidación de Ruta y redireccion al módulo correspondiente
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_072_BotonDevolucionesEspecialesLiquidacionRutaRedireccionModulo extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_072_BotonDevolucionesEspecialesLiquidacionRutaRedireccionModulo_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = user.equals("6666666") ? "CEDIS" : user.equals("5555555") ? "Admin" : "Perfil no identificado";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Capturar usuario, contraseña y hacer clic en Inicio de sesión");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se pudo ingresar a la URL de Portal Cometa");
		pc4.PrivatePortal();
		boolean resultStep1Login = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep1Login, "No se pudo iniciar sesion con el usuario: " + user);

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Hacer clic en Portal COMETA");
		boolean resultStep2 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep2, "No se mostro la pantalla principal del portal COMETA");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep3 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep3, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y seleccionar Liquidacion de Ruta");
		boolean resultStep4Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep4Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep4Liquidacion = pc4.opcLiquidacionRuta();
		assertTrue(resultStep4Liquidacion, "No se pudo seleccionar la opcion Liquidacion de Ruta");
		boolean resultStep4Pantalla = pc4.scLiquidacionRuta();
		assertTrue(resultStep4Pantalla, "No se mostro correctamente la pantalla Liquidacion de Ruta");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Identificar visualmente los botones Recepcion de Transferencias y Devoluciones y Devoluciones Especiales en Liquidacion de Ruta con perfil " + perfil);
		boolean resultStep5 = rutaPage.validaBotonDevolucionesEspecialesLiquidacionRuta(true);
		assertTrue(resultStep5, "No se mostraron los botones Recepcion de Transferencias y Devoluciones y Devoluciones Especiales alineados en Liquidacion de Ruta");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en el boton Devoluciones Especiales y validar redireccion al modulo correspondiente con perfil " + perfil);
		boolean resultStep6Clic = rutaPage.clicBotonDevolucionesEspecialesLiquidacionRuta();
		assertTrue(resultStep6Clic, "No se pudo hacer clic en el boton Devoluciones Especiales");
		boolean resultStep6Pantalla = rutaPage.scDevolucionesEspecialesCedis(true);
		assertTrue(resultStep6Pantalla, "No se mostro la pantalla Devoluciones Especiales de CEDIS despues de la redireccion");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Verificar el boton Liquidacion de Ruta en Devoluciones Especiales y hacer clic con perfil " + perfil);
		boolean resultStep7BotonLiquidacion = rutaPage.validaBotonLiquidacionRutaDevolucionesEspeciales(true);
		assertTrue(resultStep7BotonLiquidacion, "No se mostro el boton Liquidacion de Ruta dentro de Devoluciones Especiales");
		boolean resultStep7Clic = rutaPage.clicBotonLiquidacionRutaDesdeDevolucionesEspeciales();
		assertTrue(resultStep7Clic, "No se pudo hacer clic en el boton Liquidacion de Ruta desde Devoluciones Especiales");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Validar evidencia de regreso a la pantalla Liquidacion de Ruta con perfil " + perfil);
		boolean resultStep8PantallaLiquidacion = pc4.scLiquidacionRuta();
		assertTrue(resultStep8PantallaLiquidacion, "No se mostro correctamente la pantalla Liquidacion de Ruta al regresar desde Devoluciones Especiales");

		
	}

	@AfterMethod
	public void afterMethod() {
		u.quit();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Credenciales para los perfiles CEDIS y Admin      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida el botón Devoluciones Especiales en Liquidación de Ruta y la redirección al módulo correspondiente con perfiles CEDIS y Admin.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_072_BotonDevolucionesEspecialesLiquidacionRutaRedireccionModulo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}