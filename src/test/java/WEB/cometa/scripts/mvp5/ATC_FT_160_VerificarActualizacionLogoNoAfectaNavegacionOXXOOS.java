package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que la actualizacion del logotipo Plataforma COMETA no afecte la navegacion entre modulos, menus y pantallas principales para el perfil OXXO_OS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_160 CA03 - Verificar que la actualizacion del logotipo no afecta la navegacion entre modulos y pantallas,interaccion de menus y comportamiento general de las pantallas. - perfil OXXO_OS
* 
*PREREQUISITOS:
*1. Logotipo oficial Plataforma COMETA implementado. Perfil OXXO_OS con acceso al modulo Directos, FAQS Directos y Centro de descarga
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario OXXO_OS 3333333 con acceso al modulo Directos
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_160_VerificarActualizacionLogoNoAfectaNavegacionOXXOOS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_160_VerificarActualizacionLogoNoAfectaNavegacionOXXOOS_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308186 - Identidad visual Plataforma COMETA");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "OXXO_OS";

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
		assertTrue(user != null && user.equals("3333333"), "El usuario del data provider no corresponde al perfil OXXO_OS esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario OXXO_OS: " + user);

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
		addStep("Seleccionar la tarjeta 'Directos' en la barra de navegacion superior");
		boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep4, "No se pudo ingresar al modulo Directos");
		boolean resultStep4Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep4Logo, "El logotipo Plataforma COMETA no se visualizo en Directos");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta 'Comprobantes de compra'");
		boolean resultStep5Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep5Inicio, "No se pudo regresar al menu principal antes de Comprobantes de compra");
		boolean resultStep5Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep5Directos, "No se pudo ingresar a Directos antes de Comprobantes de compra");
		boolean resultStep5 = pc4.ClicBotonComprobantesDeCompra(true);
		assertTrue(resultStep5, "No se pudo ingresar a Comprobantes de Compra");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep6 = pc4.busquedaFiltroPlazaFechaDirectos(user, true);
		assertTrue(resultStep6, "No se pudo aplicar filtro/consulta en Comprobantes de Compra");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("En el grid dar clic en alguna de los resultados de la busqueda");
		boolean resultStep7 = pc4.seleccionarPrimerComprobanteCompra(true);
		assertTrue(resultStep7, "No se pudo abrir la vista preliminar de Comprobante de Compra");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y regresar a la pantalla principal de 'Directos'");
		boolean resultStep8Cerrar = identidadVisualPage.cerrarVistaPreliminarGrid(true);
		assertTrue(resultStep8Cerrar, "No se pudo cerrar la vista preliminar de Comprobante de Compra");
		boolean resultStep8Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep8Inicio, "No se pudo regresar al inicio despues de Comprobantes de Compra");
		boolean resultStep8Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep8Directos, "No se pudo regresar a la pantalla principal de Directos");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Seleccionar la tarjeta 'Devoluciones'");
		boolean resultStep9Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep9Inicio, "No se pudo regresar al menu principal antes de Devoluciones");
		boolean resultStep9Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep9Directos, "No se pudo ingresar a Directos antes de Devoluciones");
		boolean resultStep9 = pc4.selectDevolucionesSE();
		assertTrue(resultStep9, "No se pudo ingresar a Devoluciones");
		boolean resultStep9Pantalla = identidadVisualPage.validarPantallaDevolucionesDirectos(true);
		assertTrue(resultStep9Pantalla, "No se mostro la pantalla Devoluciones");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep10 = identidadVisualPage.busquedaFiltroFechaDevolucionesDirectos(user, true);
		assertTrue(resultStep10, "No se pudo aplicar filtro/consulta en Devoluciones");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("En el grid dar clic en alguna de los resultados de la busqueda");
		boolean resultStep11 = identidadVisualPage.abrirPrimerRegistroGridDevoluciones(true);
		assertTrue(resultStep11, "No se pudo abrir la vista preliminar de Devoluciones");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y regresar a la pantalla principal de 'Directos'");
		boolean resultStep12Cerrar = identidadVisualPage.cerrarVistaPreliminarGrid(true);
		assertTrue(resultStep12Cerrar, "No se pudo cerrar la vista preliminar de Devoluciones");
		boolean resultStep12Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep12Inicio, "No se pudo regresar al inicio despues de Devoluciones");
		boolean resultStep12Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep12Directos, "No se pudo regresar a la pantalla principal de Directos despues de Devoluciones");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Seleccionar la tarjeta 'Cambios Fisicos'");
		boolean resultStep13Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep13Inicio, "No se pudo regresar al menu principal antes de Cambios Fisicos");
		boolean resultStep13Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep13Directos, "No se pudo ingresar a Directos antes de Cambios Fisicos");
		boolean resultStep13 = pc4.selectCambiosFisicosSE();
		assertTrue(resultStep13, "No se pudo ingresar a Cambios Fisicos");
		boolean resultStep13Pantalla = identidadVisualPage.validarPantallaCambiosFisicosDirectos(true);
		assertTrue(resultStep13Pantalla, "No se mostro la pantalla Cambios Fisicos");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep14 = identidadVisualPage.busquedaFiltroFechaCambiosFisicosDirectos(user, true);
		assertTrue(resultStep14, "No se pudo aplicar filtro/consulta en Cambios Fisicos");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y seleccionar la opcion 'Cambios fisicos pendientes actuales'");
		boolean resultStep15 = pc4.clickCambiosFisicosPendientesPantalla(true);
		assertTrue(resultStep15, "No se pudo ingresar a Cambios fisicos pendientes actuales");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Regresar al inicio de Portal COMETA y seleccionar la tarjeta 'FAGS Directos'");
		boolean resultStep16Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep16Inicio, "No se pudo regresar al menu principal antes de FAQS Directos");
		boolean resultStep16 = identidadVisualPage.clicFaqsDirectos(true);
		assertTrue(resultStep16, "No se pudo ingresar a FAQS Directos");

		/*
		 *                                             PASO 17
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta 'Centro de descargas'");
		boolean resultStep17Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep17Inicio, "No se pudo regresar al menu principal antes de Centro de descargas");
		boolean resultStep17 = pc4.ClicBotonCentroDescargaMenu(true);
		assertTrue(resultStep17, "No se pudo ingresar a Centro de descargas");
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
		return "*1. Logotipo oficial Plataforma COMETA implementado. Perfil OXXO_OS con acceso al modulo Directos, FAQS Directos y Centro de descarga      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_160 CA03: la actualizacion del logotipo Plataforma COMETA no afecta la navegacion entre modulos, menus, tarjetas, consultas y pantallas principales para el perfil OXXO_OS.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_160_VerificarActualizacionLogoNoAfectaNavegacionOXXOOS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}