package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesEspecialesPage;
import WEB.cometa.om.menuCedisPage;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar en la vista preliminar de Devoluciones Especiales la leyenda
 * Descarga Comprobante en Formato, el icono PDF y el modal Espere un momento con cierre automatico.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_369 Verificar leyenda Descarga Comprobante en Formato e icono PDF y verificar modal Espere un momento con cierre automatico - perfil CEDIS
 *
 *PREREQUISITOS:
 *1. Perfil CEDIS (usuario 6666666) disponible en ambiente QA.
 *2. Grid de Devoluciones Especiales con al menos un registro disponible.
 *3. Carpeta de descargas del equipo accesible.
 *
 *INSUMOS:
 *Usuario CEDIS configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_369_LeyendaPDFYModalEspereTicketDevEspecialesCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_369_LeyendaPDFYModalEspereTicketDevEspecialesCEDIS_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, user, perfil, password);
		assertTrue(resultPrecondicion,
				"Precondicion fallida: la URL, el usuario CEDIS o sus credenciales no estan configurados");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1,
				"Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil CEDIS");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4,
				"Paso 4 fallido: las credenciales no permiten iniciar sesion o no se redirige a la pantalla de seleccion de portales");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las opciones del perfil CEDIS");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en CEDIS > Recepcion y devolucion > Devoluciones especiales");
		boolean resultStep6Cedis = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6Cedis, "Paso 6 fallido: no fue posible ingresar al modulo CEDIS");
		boolean resultStep6Recepcion = menu.clicRecepcionYDevolucion();
		assertTrue(resultStep6Recepcion,
				"Paso 6 fallido: no fue posible abrir Recepcion y devolucion para continuar hacia Devoluciones Especiales");
		boolean resultStep6CargaRecepcion = recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep6CargaRecepcion,
				"Paso 6 fallido: la pantalla Recepcion y devolucion no quedo estable para seleccionar Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep6,
				"Paso 6 fallido: no se visualiza la pantalla Devoluciones Especiales con los filtros esperados");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Seleccionar el rango de fechas en el campo Rango de fechas (maximo 7 dias) iniciando desde fecha con informacion disponible y dar clic en Consultar");
		boolean resultStep7Fecha = devEspecialesPage.seleccionarRangoSieteDiasDesdeFechaInicialBdCedis(user, true);
		assertTrue(resultStep7Fecha,
				"Paso 7 fallido: no fue posible seleccionar un rango de fechas valido para habilitar Consultar");
		boolean resultStep7Consultar = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep7Consultar,
				"Paso 7 fallido: no se pudo ejecutar la consulta o el grid no quedo visible al terminar la carga");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en el icono de la columna Devolucion Especial del primer registro del grid");
		boolean resultStep8 = devEspecialesPage.abrirVistaPreliminarPrimerRegistroDevolucionEspecial(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: no se abre la vista preliminar del ticket Formato Devolucion Especial");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Localizar el area superior derecha del ticket en la vista preliminar");
		boolean resultStep9 = devEspecialesPage.validarAreaSuperiorDerechaTicketVisible(true);
		assertTrue(resultStep9,
				"Paso 9 fallido: el area superior derecha del ticket no es visible en la vista preliminar");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Comparar el texto del area superior derecha con la cadena exacta Descarga Comprobante en Formato:");
		boolean resultStep10 = devEspecialesPage
				.validarTextoExactoLeyendaDescargaComprobante("Descarga Comprobante en Formato:", true);
		assertTrue(resultStep10,
				"CA03 fallido: el texto mostrado no coincide exactamente con 'Descarga Comprobante en Formato:'");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar que inmediatamente despues de la leyenda se muestra el icono PDF");
		boolean resultStep11 = devEspecialesPage.validarIconoPdfTicketVisibleJuntoALeyenda(true);
		assertTrue(resultStep11,
				"CA03 fallido: el icono PDF no esta visible junto a la leyenda de descarga del ticket");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Dar clic en el icono PDF ubicado en la parte superior derecha del ticket");
		boolean resultStep12 = devEspecialesPage.accionarPdfTicketYValidarModalEspereVisible(true);
		assertTrue(resultStep12,
				"CA04 fallido: al accionar el icono PDF no aparece el modal 'Espere un momento'");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Observar el modal durante la descarga para validar que permanece visible y no puede cerrarse manualmente");
		boolean resultStep13 = devEspecialesPage.validarModalEspereVisibleSinCierreManual(true);
		assertTrue(resultStep13,
				"CA04 fallido: el modal no permanece visible durante el proceso o permite cierre manual");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Esperar a que finalice la descarga y validar cierre automatico del modal");
		boolean resultStep14Cierre = devEspecialesPage.esperarCierreAutomaticoModalEspereTicket(true);
		assertTrue(resultStep14Cierre,
				"CA04 fallido: el modal 'Espere un momento' no se cierra automaticamente al finalizar la descarga");
		boolean resultStep14Archivo = pc4.openLastFileDownloadFolder("PDF", true);
		assertTrue(resultStep14Archivo,
				"CA04 fallido: no se detecto archivo PDF descargado en la carpeta de descargas del equipo");
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
		return "*1. Perfil CEDIS (usuario 6666666) disponible en ambiente QA.\r\n"
				+ "*2. Grid de Devoluciones Especiales con al menos un registro disponible.\r\n"
				+ "*3. Carpeta de descargas del equipo accesible.";
	}

	@Override
	public String setTestDescription() {
		return "Verificar en la vista preliminar de Devoluciones Especiales la leyenda Descarga Comprobante en Formato, el icono PDF y el modal Espere un momento con cierre automatico para perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_369_LeyendaPDFYModalEspereTicketDevEspecialesCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
