package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesEspecialesPage;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que la actualizacion del logotipo Plataforma COMETA no afecte la navegacion entre modulos, menus y pantallas principales para el perfil ADMIN.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_158 CA03 - Verificar que la actualizacion del logotipo no afecta la navegacion entre modulos y pantallas,interaccion de menus y comportamiento general de las pantallas - perfil ADMIN
* 
*PREREQUISITOS:
*1. Logotipo oficial Plataforma COMETA implementado. Perfil ADMIN con acceso a CEDIS, Directos, FAQS y Centro de descarga
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario ADMIN 5555555 con acceso a CEDIS y Directos
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_158_VerificarActualizacionLogoNoAfectaNavegacionADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_158_VerificarActualizacionLogoNoAfectaNavegacionADMIN_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308186 - Identidad visual Plataforma COMETA");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadVisualPage = new CometaNuevaIdentidadVisualPage(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);
		devolucionesEspecialesPage devolucionesEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";

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
		assertTrue(user != null && user.equals("5555555"), "El usuario del data provider no corresponde al perfil ADMIN esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario ADMIN: " + user);

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
		addStep("Seleccionar la tarjeta 'Directos' en la barra de navegacion superior");
		boolean resultStep11 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep11, "No se pudo ingresar al modulo Directos");
		boolean resultStep11Logo = identidadVisualPage.validarLogoNuevaIdentidadVisualVisible(true);
		assertTrue(resultStep11Logo, "El logotipo Plataforma COMETA no se visualizo en Directos");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta 'Comprobantes de compra'");
		boolean resultStep12Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep12Inicio, "No se pudo regresar al menu principal antes de Comprobantes de compra");
		boolean resultStep12Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep12Directos, "No se pudo ingresar a Directos antes de Comprobantes de compra");
		boolean resultStep12 = pc4.ClicBotonComprobantesDeCompra(true);
		assertTrue(resultStep12, "No se pudo ingresar a Comprobantes de Compra");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep13 = pc4.busquedaFiltroPlazaFechaDirectos(user, true);
		assertTrue(resultStep13, "No se pudo aplicar filtro/consulta en Comprobantes de Compra");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("En el grid dar clic en alguna de los resultados de la busqueda");
		boolean resultStep14 = pc4.seleccionarPrimerComprobanteCompra(true);
		assertTrue(resultStep14, "No se pudo abrir la vista preliminar de Comprobante de Compra");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y regresar a la pantalla principal de 'Directos'");
		boolean resultStep15Cerrar = identidadVisualPage.cerrarVistaPreliminarGrid(true);
		assertTrue(resultStep15Cerrar, "No se pudo cerrar la vista preliminar de Comprobante de Compra");
		boolean resultStep15 = pc4.opcInicioExterno(true);
		assertTrue(resultStep15, "No se pudo regresar al inicio despues de Comprobantes de Compra");
		boolean resultStep15Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep15Directos, "No se pudo regresar a la pantalla principal de Directos");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Seleccionar la tarjeta 'Devoluciones'");
		boolean resultStep16Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep16Inicio, "No se pudo regresar al menu principal antes de Devoluciones");
		boolean resultStep16Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep16Directos, "No se pudo ingresar a Directos antes de Devoluciones");
		boolean resultStep16 = pc4.selectDevolucionesSE();
		assertTrue(resultStep16, "No se pudo ingresar a Devoluciones");
		boolean resultStep16Pantalla = identidadVisualPage.validarPantallaDevolucionesDirectos(true);
		assertTrue(resultStep16Pantalla, "No se mostro la pantalla Devoluciones");

		/*
		 *                                             PASO 17
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep17 = identidadVisualPage.busquedaFiltroFechaDevolucionesDirectos(user, true);
		assertTrue(resultStep17, "No se pudo aplicar filtro/consulta en Devoluciones");

		/*
		 *                                             PASO 18
		 ***************************************************************************/
		addStep("En el grid dar clic en alguna de los resultados de la busqueda");
		boolean resultStep18 = identidadVisualPage.abrirPrimerRegistroGridDevoluciones(true);
		assertTrue(resultStep18, "No se pudo abrir la vista preliminar de Devoluciones");

		/*
		 *                                             PASO 19
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y regresar a la pantalla principal de 'Directos'");
		boolean resultStep19Cerrar = identidadVisualPage.cerrarVistaPreliminarGrid(true);
		assertTrue(resultStep19Cerrar, "No se pudo cerrar la vista preliminar de Devoluciones");
		boolean resultStep19Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep19Inicio, "No se pudo regresar al inicio despues de Devoluciones");
		boolean resultStep19Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep19Directos, "No se pudo regresar a la pantalla principal de Directos despues de Devoluciones");

		/*
		 *                                             PASO 20
		 ***************************************************************************/
		addStep("Seleccionar la tarjeta 'Cambios Fisicos'");
		boolean resultStep20Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep20Inicio, "No se pudo regresar al menu principal antes de Cambios Fisicos");
		boolean resultStep20Directos = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep20Directos, "No se pudo ingresar a Directos antes de Cambios Fisicos");
		boolean resultStep20 = pc4.selectCambiosFisicosSE();
		assertTrue(resultStep20, "No se pudo ingresar a Cambios Fisicos");
		boolean resultStep20Pantalla = identidadVisualPage.validarPantallaCambiosFisicosDirectos(true);
		assertTrue(resultStep20Pantalla, "No se mostro la pantalla Cambios Fisicos");

		/*
		 *                                             PASO 21
		 ***************************************************************************/
		addStep("Agregar filtros en la busqueda y seleccionar el boton 'Consultar'");
		boolean resultStep21 = identidadVisualPage.busquedaFiltroFechaCambiosFisicosDirectos(user, true);
		assertTrue(resultStep21, "No se pudo aplicar filtro/consulta en Cambios Fisicos");

		/*
		 *                                             PASO 22
		 ***************************************************************************/
		addStep("Cerrar la vista preliminar y seleccionar la opcion 'Cambios fisicos pendientes actuales'");
		boolean resultStep22 = pc4.clickCambiosFisicosPendientesPantalla(true);
		assertTrue(resultStep22, "No se pudo ingresar a Cambios fisicos pendientes actuales");

		/*
		 *                                             PASO 23
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta ' FAQS CEDIS'");
		boolean resultStep23Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep23Inicio, "No se pudo regresar al menu principal antes de FAQS CEDIS");
		boolean resultStep23 = identidadVisualPage.clicFaqsCedis(true);
		assertTrue(resultStep23, "No se pudo ingresar a FAQS CEDIS");

		/*
		 *                                             PASO 24
		 ***************************************************************************/
		addStep("Regresar seleccionar la tarjeta 'FAGS Directos'");
		boolean resultStep24Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep24Inicio, "No se pudo regresar al menu principal antes de FAQS Directos");
		boolean resultStep24 = identidadVisualPage.clicFaqsDirectos(true);
		assertTrue(resultStep24, "No se pudo ingresar a FAQS Directos");

		/*
		 *                                             PASO 25
		 ***************************************************************************/
		addStep("Regresar y seleccionar la tarjeta 'Centro de descargas'");
		boolean resultStep25Inicio = pc4.opcInicioExterno(true);
		assertTrue(resultStep25Inicio, "No se pudo regresar al menu principal antes de Centro de descargas");
		boolean resultStep25 = pc4.ClicBotonCentroDescargaMenu(true);
		assertTrue(resultStep25, "No se pudo ingresar a Centro de descargas");
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
		return "*1. Logotipo oficial Plataforma COMETA implementado. Perfil ADMIN con acceso a CEDIS, Directos, FAQS y Centro de descarga      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_158 CA03: la actualizacion del logotipo Plataforma COMETA no afecta la navegacion entre modulos, menus, tarjetas, consultas y pantallas principales para el perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_158_VerificarActualizacionLogoNoAfectaNavegacionADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}