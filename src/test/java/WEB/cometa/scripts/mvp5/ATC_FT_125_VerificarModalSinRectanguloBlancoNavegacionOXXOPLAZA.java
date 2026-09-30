package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.comprobanteCompraPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Verifica que el modal Comprobante de compra no muestre rectangulo blanco durante la navegacion siguiente y anterior para el perfil OXXO_PLAZA.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_125 CA01 - Verificar que el modal no muestra rectangulo blanco durante navegacion siguiente y anterior - perfil OXXO_PLAZA
* 
*PREREQUISITOS:
*1. Ambiente QA disponible. Perfil OXXO_PLAZA con acceso al modulo Directos / Comprobantes de Compra, visibilidad limitada a plazas asignadas via key PCPLAZA y datos consultables en marzo 2026
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario OXXO_PLAZA 4444444 con acceso a Directos / Comprobantes de Compra
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_125_VerificarModalSinRectanguloBlancoNavegacionOXXOPLAZA extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_125_VerificarModalSinRectanguloBlancoNavegacionOXXOPLAZA_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("HU308149 - Correccion visual del modal Comprobante de compra");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		comprobanteCompraPage comprobantePage = new comprobanteCompraPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "OXXO_PLAZA";

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL del portal en el navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se pudo ingresar a la URL de Portal Cometa");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue(user != null && user.equals("4444444"), "El usuario del data provider no corresponde al perfil OXXO_PLAZA esperado");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		assertTrue(password != null && !password.trim().isEmpty(), "No se obtuvo password valido para el usuario: " + user);
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se pudo iniciar sesion con el usuario OXXO_PLAZA: " + user);

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep3, "No se visualizo o no se pudo seleccionar Portal COMETA");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Directos");
		boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
		assertTrue(resultStep4, "No se pudo ingresar al modulo Directos");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la tarjeta Comprobantes de Compra");
		boolean resultStep5 = pc4.ClicBotonComprobantesDeCompra(true);
		assertTrue(resultStep5, "No se pudo ingresar a Comprobantes de Compra");
		boolean resultStep5Pantalla = comprobantePage.validarPantallaComprobantesCompra(true);
		assertTrue(resultStep5Pantalla, "No se mostro la pantalla Comprobantes de Compra con filtros disponibles");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Aplicar filtros de Plaza y Fecha con informacion valida obtenida desde BD, usando el flujo probado de MVP4 para el perfil " + perfil);
		boolean resultStep6 = pc4.busquedaFiltroPlazaFechaDirectos(user, true);
		assertTrue(resultStep6, "No se pudo aplicar filtro de Plaza/Fecha ni ejecutar la consulta de Comprobantes de Compra con datos validos para el usuario: " + user);

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Validar que la consulta realizada por el flujo MVP4 deje registros disponibles en el grid");
		assertTrue(resultStep6, "El flujo reutilizado de MVP4 no dejo registros disponibles en el grid");
		testCase.addScreenShotCurrentStep(u, "Grid_Comprobantes_Compra_Con_Registros");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Confirmar que Comprobantes de Compra muestra resultados consultados para continuar con la vista preliminar");
		assertTrue(resultStep6, "No existen resultados consultados para abrir vista preliminar de Comprobante de compra");
		testCase.addScreenShotCurrentStep(u, "Resultados_Comprobantes_Compra_Para_Vista_Preliminar");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Dar clic en la primera fila del grid para abrir la vista preliminar del comprobante");
		boolean resultStep9 = pc4.seleccionarPrimerComprobanteCompra(true);
		assertTrue(resultStep9, "No se pudo seleccionar el primer comprobante de compra del grid");
		boolean resultStep9Modal = comprobantePage.validarModalComprobanteCompraAbierto(true);
		assertTrue(resultStep9Modal, "No se abrio correctamente el modal Comprobante de compra con iconos PDF y Excel");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Verificar que el primer comprobante del modal no muestra rectangulo blanco ni franja blanca");
		boolean resultStep10Contenido = comprobantePage.validarContenidoModalSinRectanguloBlanco(true);
		assertTrue(resultStep10Contenido, "El primer comprobante muestra un rectangulo blanco adicional");
		boolean resultStep10Footer = comprobantePage.validarFooterModalSinFranjaBlanca(true);
		assertTrue(resultStep10Footer, "El footer del primer comprobante presenta una franja blanca adicional");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Dar clic en el boton Siguiente para navegar al segundo comprobante y validar que no muestre artefactos blancos");
		boolean resultStep11 = comprobantePage.navegarSiguienteModalYValidarSinArtefactos(1, true);
		assertTrue(resultStep11, "El segundo comprobante muestra rectangulo blanco o franja blanca despues de navegar con Siguiente");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Dar clic en el boton Siguiente tres veces adicionales y validar que los comprobantes carguen sin artefactos blancos");
		boolean resultStep12 = comprobantePage.navegarSiguienteModalYValidarSinArtefactos(3, true);
		assertTrue(resultStep12, "Al navegar con Siguiente se detecto rectangulo blanco o franja blanca en algun comprobante");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Dar clic en el boton Anterior y validar que el comprobante al que se regresa no muestre artefactos blancos");
		boolean resultStep13 = comprobantePage.navegarAnteriorModalYValidarSinArtefactos(true);
		assertTrue(resultStep13, "Al navegar con Anterior se detecto rectangulo blanco o franja blanca");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Dar clic en el boton Ultimo registro si esta disponible y validar que el ultimo comprobante no muestre artefactos blancos");
		boolean resultStep14 = comprobantePage.navegarUltimoModalYValidarSinArtefactos(true);
		assertTrue(resultStep14, "El ultimo comprobante muestra rectangulo blanco o franja blanca");

		boolean resultCerrarModal = comprobantePage.cerrarModalComprobanteCompra(false);
		assertTrue(resultCerrarModal, "No se pudo cerrar el modal Comprobante de compra al finalizar la validacion");
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
		return "*1. Ambiente QA disponible. Perfil OXXO_PLAZA con acceso al modulo Directos / Comprobantes de Compra, visibilidad limitada a plazas asignadas via key PCPLAZA y datos consultables en marzo 2026      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica el MTC_FT_125 CA01: el modal Comprobante de compra no muestra rectangulo blanco durante la navegacion siguiente y anterior para el perfil OXXO_PLAZA, validando que la correccion visual se conserve al avanzar, retroceder y navegar al ultimo comprobante disponible.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_125_VerificarModalSinRectanguloBlancoNavegacionOXXOPLAZA_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}