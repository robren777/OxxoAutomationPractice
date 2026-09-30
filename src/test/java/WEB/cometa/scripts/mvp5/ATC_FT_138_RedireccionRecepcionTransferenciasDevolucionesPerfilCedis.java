package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.menuCedisPage;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verifica que Recepcion y Devolucion redirija a la pantalla Recepcion de Transferencias y Devoluciones para el perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_138 CA03 - Verificar que clic en Recepcion y Devolucion redirige a la pantalla Recepcion de Transferencias y Devoluciones - perfil CEDIS
* 
*PREREQUISITOS:
*1. Script de reorganizacion ejecutado y perfil CEDIS (usuario 6666666) disponible en QA.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS 6666666.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_138_RedireccionRecepcionTransferenciasDevolucionesPerfilCedis extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_138_RedireccionRecepcionTransferenciasDevolucionesPerfilCedis_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menuCedis = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: confirmar que el script fue ejecutado y validar el perfil CEDIS con credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(url, user, "6666666", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: el perfil CEDIS, el usuario 6666666 o sus credenciales no estan disponibles");
		testCase.addTextEvidenceCurrentStep(
				"Prerrequisito declarado: script ejecutado. Perfil CEDIS (usuario 6666666) y credenciales validados en QA.");

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1,
				"Paso 1 fallido: el sistema no muestra la pantalla de inicio de sesion del portal Access Control");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASOS 2, 3 Y 4
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario, escribir la contrasena del perfil y dar clic en el boton Iniciar sesion");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2,
				"Los pasos 2, 3 y 4 fallaron: el sistema no valido las credenciales ni redirigio a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep(
				"El campo Usuario recibe '" + user + "', la contrasena se captura enmascarada y el sistema inicia sesion.");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con CEDIS y FAQS CEDIS");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones: CEDIS y FAQS CEDIS.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'CEDIS' del menu principal de navegacion");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6,
				"Paso 6 fallido: el submenu CEDIS no muestra la opcion 'Recepcion y Devolucion'");
		testCase.addTextEvidenceCurrentStep(
				"El sistema despliega el submenu del modulo CEDIS mostrando la opcion 'Recepcion y Devolucion'.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'Recepcion y Devolucion' del submenu de CEDIS");
		boolean resultStep7 = menuCedis.clicRecepcionYDevolucion();
		assertTrue(resultStep7, "Paso 7 fallido: el sistema no procesa la navegacion ni carga la pantalla destino");
		testCase.addTextEvidenceCurrentStep("El sistema procesa la navegacion y carga la pantalla destino.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Verificar que la URL del navegador corresponde a la ruta de la pantalla Recepcion de Transferencias y Devoluciones");
		boolean resultStep8 = recepcion.validaRutaRecepcion();
		assertTrue(resultStep8,
				"Paso 8 fallido: la URL no corresponde a Recepcion de Transferencias y Devoluciones o muestra un error 404");
		testCase.addTextEvidenceCurrentStep(
				"La URL del navegador cambia a la ruta correcta de la pantalla Recepcion de Transferencias y Devoluciones. No se muestra error 404 ni redireccion incorrecta.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Verificar que el titulo principal de la pantalla cargada es 'Recepcion de Transferencias y Devoluciones'");
		boolean resultStep9 = recepcion.validaTituloPantalla();
		assertTrue(resultStep9,
				"Paso 9 fallido: la pantalla no muestra el titulo Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep(
				"La pantalla muestra el titulo 'Recepcion de Transferencias y Devoluciones' en el encabezado. Este es el mismo titulo que tenia la pantalla antes del cambio de nombre en el menu.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar que la pantalla cargada muestra los botones 'Liquidacion de ruta' y 'Devoluciones Especiales' en la parte superior");
		boolean resultStep10 = recepcion.validaBotonesSuperiores();
		assertTrue(resultStep10,
				"Paso 10 fallido: Liquidacion de ruta o Devoluciones Especiales no esta visible y funcional");
		testCase.addTextEvidenceCurrentStep(
				"Los botones 'Liquidacion de ruta' y 'Devoluciones Especiales' son visibles y funcionales en la parte superior de la pantalla.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar que la seccion 'Filtros de Busqueda Historica' esta visible con los campos: Plaza, Tienda, Selector de Ruta, Ruta y Rango de fechas");
		boolean resultStep11 = recepcion.validaFiltrosHistoricos();
		assertTrue(resultStep11,
				"Paso 11 fallido: faltan campos requeridos en la seccion Filtros de Busqueda Historica");
		testCase.addTextEvidenceCurrentStep(
				"La seccion 'Filtros de Busqueda Historica' es visible con todos sus campos de filtro: Plaza, Tienda, Selector de Ruta, Ruta (Ejemplo: OBR284) y Rango de fechas.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Verificar que la seccion 'Filtros de Busqueda Rapida' esta visible con el campo 'Escanea o teclea la canastilla'");
		boolean resultStep12 = recepcion.validaFiltrosRapidos();
		assertTrue(resultStep12,
				"Paso 12 fallido: Filtros de Busqueda Rapida o el campo de canastilla no estan visibles");
		testCase.addTextEvidenceCurrentStep(
				"La seccion 'Filtros de Busqueda Rapida' es visible con el campo de captura 'Escanea o teclea la canastilla'.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Verificar que la seccion 'Descarga MASIVA de comprobantes filtrados' esta visible con los iconos FUE, Verificacion y Tira");
		boolean resultStep13Precondicion = recepcion.preparaCanastillaValidaConDatos(user)
				&& recepcion.consultaRapidaConResultados();
		assertTrue(resultStep13Precondicion,
				"Paso 13 fallido: no fue posible preparar la canastilla valida ni ejecutar la consulta rapida previa a la validacion de Descarga MASIVA");
		testCase.addTextEvidenceCurrentStep(
				"Precondicion tecnica del Paso 13: se captura una canastilla valida y se ejecuta Consultar en filtros rapidos para habilitar la seccion de Descarga MASIVA.");
		boolean resultStep13 = recepcion.validaDescargasMasivas();
		assertTrue(resultStep13,
				"Paso 13 fallido: la descarga masiva no muestra los controles FUE, Verificacion y Tira");
		testCase.addTextEvidenceCurrentStep(
				"La linea 'Descarga MASIVA de comprobantes filtrados:' es visible con sus tres iconos de descarga PDF: FUE (rojo), Verificacion (rojo) y Tira (rojo).");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Verificar que el grid de la pantalla (tabla ENTREGA) muestra las columnas: Fecha, CR Tienda, Nombre de Tienda, Transferencia, # Canastillas Devueltas, Bols en entrega, FUE, Formato de Verificacion, Evidencias Fotograficas, Tira de transferencia");
		boolean resultStep14 = recepcion.validaColumnasGridEntrega();
		assertTrue(resultStep14, "Paso 14 fallido: el grid ENTREGA no muestra todas las columnas esperadas");
		testCase.addTextEvidenceCurrentStep(
				"El grid de la seccion ENTREGA muestra todas las columnas esperadas. La pantalla esta completamente funcional tal como estaba antes del cambio de nombre en el menu.");
	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*1. Script de reorganizacion ejecutado y perfil CEDIS (usuario 6666666) disponible en QA.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que Recepcion y Devolucion redirija a Recepcion de Transferencias y Devoluciones para el perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_138_RedireccionRecepcionTransferenciasDevolucionesPerfilCedis_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}