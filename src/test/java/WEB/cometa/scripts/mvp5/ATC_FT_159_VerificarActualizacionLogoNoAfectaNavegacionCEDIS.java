package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesEspecialesPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que la actualizacion del logotipo Plataforma COMETA no afecte la navegacion entre modulos, menus y pantallas principales para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_159 CA03 - Verificar que la actualizacion del logotipo no afecta la navegacion entre modulos y pantallas,interaccion de menus y comportamiento general de las pantallas- perfil CEDIS
* 
*PREREQUISITOS:
*1. Logotipo oficial Plataforma COMETA implementado. Perfil CEDIS con acceso a CEDIS, FAQS CEDIS y Centro de descarga
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666 con acceso al modulo CEDIS
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_159_VerificarActualizacionLogoNoAfectaNavegacionCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_159_VerificarActualizacionLogoNoAfectaNavegacionCEDIS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308186 - Identidad visual Plataforma COMETA");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);
		devolucionesEspecialesPage devolucionesEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL del portal en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se pudo ingresar a la URL de Portal COMETA");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue(user != null && user.equals("6666666"), "El usuario del data provider no corresponde al perfil CEDIS esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario CEDIS: " + user);

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep3, "No se visualizo o no se pudo seleccionar Portal COMETA");
		boolean resultStep3Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep3Logo, "El logotipo Plataforma COMETA no se visualizo en la pantalla principal del portal");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Seleccionar la tarjeta 'CEDIS'");
		boolean resultStep4 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep4, "No se pudo ingresar al modulo CEDIS");
		boolean resultStep4Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep4Logo, "El logotipo Plataforma COMETA no se visualizo en CEDIS");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Seleccionar la tarjeta 'Recepcion y Devolucion'");
		boolean resultStep5Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep5Inicio, "No se pudo regresar al menu principal antes de Recepcion y Devolucion");
		boolean resultStep5Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep5Cedis, "No se pudo ingresar a CEDIS antes de Recepcion y Devolucion");
		boolean resultStep5 = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep5, "No se pudo ingresar a Recepcion y Devolucion");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep6 = identidadVisualPage.busquedaFiltroFechaRecepcionCedis(user, true);
		assertTrue(resultStep6, "No se pudo aplicar filtro/consulta en Recepcion CEDIS");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("En el grid dar clic el icono en alguna de las opciones FUE, Formato de Verificacion, Tira de Transferencia, Evidencias Fotograficas");
		boolean resultStep7 = identidadVisualPage.clicFuePrimerRegistroCedis(true);
		assertTrue(resultStep7, "No se pudo abrir la vista preliminar FUE desde el grid de Recepcion CEDIS");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y seleccionar 'Liquidacion de Ruta'");
		boolean resultStep8Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep8Inicio, "No se pudo regresar al menu principal antes de Liquidacion de Ruta");
		boolean resultStep8Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep8Cedis, "No se pudo ingresar a CEDIS antes de Liquidacion de Ruta");
		boolean resultStep8Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep8Recepcion, "No se pudo ingresar a Recepcion antes de Liquidacion de Ruta");
		boolean resultStep8 = pc4.opcLiquidacionRuta();
		assertTrue(resultStep8, "No se pudo seleccionar Liquidacion de Ruta");
		boolean resultStep8Pantalla = pc4.scLiquidacionRuta();
		assertTrue(resultStep8Pantalla, "No se mostro la pantalla Liquidacion de Ruta");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Regresar y seleccionar la opcion 'Devoluciones Especiales'");
		boolean resultStep9Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep9Inicio, "No se pudo regresar al menu principal antes de Devoluciones Especiales");
		boolean resultStep9Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep9Cedis, "No se pudo ingresar a CEDIS antes de Devoluciones Especiales");
		boolean resultStep9Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep9Recepcion, "No se pudo ingresar a Recepcion antes de Devoluciones Especiales");
		boolean resultStep9Liquidacion = pc4.opcLiquidacionRuta();
		assertTrue(resultStep9Liquidacion, "No se pudo seleccionar Liquidacion de Ruta antes de Devoluciones Especiales");
		boolean resultStep9 = devolucionesEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep9, "No se pudo seleccionar Devoluciones Especiales");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Regresar al incio de Portal Cometa");
		boolean resultStep10 = pc4.opcInicioExterno(true);
		assertTrue(resultStep10, "No se pudo regresar al inicio de Portal COMETA");
		boolean resultStep10Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep10Logo, "El logotipo Plataforma COMETA no se visualizo al regresar al inicio");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta ' FAQS CEDIS'");
		boolean resultStep11Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep11Inicio, "No se pudo regresar al menu principal antes de FAQS CEDIS");
		boolean resultStep11 = identidadVisualPage.clicFaqsCedis(true);
		assertTrue(resultStep11, "No se pudo ingresar a FAQS CEDIS");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta 'Centro de descargas'");
		boolean resultStep12Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep12Inicio, "No se pudo regresar al menu principal antes de Centro de descargas");
		boolean resultStep12 = pc4.ClicBotonCentroDescargaMenu(true);
		assertTrue(resultStep12, "No se pudo ingresar a Centro de descargas");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Logotipo oficial Plataforma COMETA implementado. Perfil CEDIS con acceso a CEDIS, FAQS CEDIS y Centro de descarga      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_159 CA03: la actualizacion del logotipo Plataforma COMETA no afecta la navegacion entre modulos, menus, tarjetas, consultas y pantallas principales para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_159_VerificarActualizacionLogoNoAfectaNavegacionCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}