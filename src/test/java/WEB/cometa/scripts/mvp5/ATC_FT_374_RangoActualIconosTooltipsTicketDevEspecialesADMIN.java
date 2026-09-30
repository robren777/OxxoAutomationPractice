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

/**DESCRIPCION: Verificar en vista preliminar de Devoluciones Especiales que se muestre
 * el rango actual en la parte inferior, los iconos de navegacion y tooltips correctos para perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_374 Verificar rango actual en parte inferior iconos de navegacion visibles y tooltips correctos - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA.
 *2. Grid de Devoluciones Especiales con al menos un registro disponible.
 *3. Liga de Portal Cometa.
 *
 *INSUMOS:
 *Usuario ADMIN configurado en el data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_374_RangoActualIconosTooltipsTicketDevEspecialesADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_374_RangoActualIconosTooltipsTicketDevEspecialesADMIN_test(HashMap<String, String> data)
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
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, user, perfil, password);
		assertTrue(resultPrecondicion,
				"Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
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
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil ADMIN");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil ADMIN en el campo Contrasena");
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
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las opciones del perfil ADMIN");

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
		addStep("Seleccionar el rango de fechas en el campo Rango de fechas (maximo 7 dias) iniciando desde fecha con informacion disponible");
		boolean resultStep7 = devEspecialesPage.seleccionarRangoSieteDiasDesdeFechaInicialBdCedis(user, true);
		assertTrue(resultStep7,
				"Paso 7 fallido: no fue posible seleccionar un rango de fechas valido igual o menor a 7 dias");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en Consultar para mostrar resultados en grid");
		boolean resultStep8 = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: no se pudo ejecutar la consulta o el grid no quedo visible al terminar la carga");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en el icono de Devolucion Especial del primer registro para abrir la vista preliminar");
		boolean resultStep9 = devEspecialesPage.abrirVistaPreliminarPrimerRegistroDevolucionEspecial(true);
		assertTrue(resultStep9,
				"Paso 9 fallido: no se abre la vista preliminar del ticket Formato Devolucion Especial");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Validar que se muestre el rango actual en la parte inferior del ticket");
		boolean resultStep10 = devEspecialesPage.validarRangoActualPaginacionTicketVisible(true);
		assertTrue(resultStep10,
				"CA03 fallido: no se muestra el rango actual en la parte inferior del ticket con formato valido");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Validar que los iconos de navegacion del ticket esten visibles en la parte inferior");
		boolean resultStep11 = devEspecialesPage.validarIconosNavegacionTicketVisibles(true);
		assertTrue(resultStep11,
				"CA03 fallido: no se visualizan todos los iconos de navegacion del ticket en la parte inferior");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Validar tooltip del icono Pagina siguiente y navegar al siguiente registro con el icono >");
		boolean resultStep12 = devEspecialesPage.navegarPaginacionTicketConValidacionTooltip("siguiente", "Página siguiente", true);
		assertTrue(resultStep12,
				"CA03 fallido: el tooltip del icono Pagina siguiente no muestra el texto esperado o no se completo la navegacion");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Validar tooltip del icono Ultima pagina y navegar al ultimo registro con el icono >|");
		boolean resultStep13 = devEspecialesPage.navegarPaginacionTicketConValidacionTooltip("ultima", "Última página", true);
		assertTrue(resultStep13,
				"CA03 fallido: el tooltip del icono Ultima pagina no muestra el texto esperado o no se completo la navegacion");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Validar tooltip del icono Pagina anterior y navegar al registro previo con el icono <");
		boolean resultStep14 = devEspecialesPage.navegarPaginacionTicketConValidacionTooltip("anterior", "Página anterior", true);
		assertTrue(resultStep14,
				"CA03 fallido: el tooltip del icono Pagina anterior no muestra el texto esperado o no se completo la navegacion");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Validar tooltip del icono Primera pagina y navegar al primer registro con el icono |<");
		boolean resultStep15 = devEspecialesPage.navegarPaginacionTicketConValidacionTooltip("primera", "Primera página", true);
		assertTrue(resultStep15,
				"CA03 fallido: el tooltip del icono Primera pagina no muestra el texto esperado o no se completo la navegacion");
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
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA.\r\n"
				+ "*2. Grid de Devoluciones Especiales con al menos un registro disponible.\r\n"
				+ "*3. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Verificar en vista preliminar de Devoluciones Especiales que se muestre el rango actual en la parte inferior, los iconos de navegacion y tooltips correctos para perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_374_RangoActualIconosTooltipsTicketDevEspecialesADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
