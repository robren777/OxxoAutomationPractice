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

/**DESCRIPCION: Verifica que no sea posible usar filtros rapidos e historicos de forma simultanea con perfil ADMIN.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_246- Verificar que no es posible usar filtros rapidos e historicos de forma simultanea - perfil ADMIN
* 
*PREREQUISITOS:
*1. Ambiente QA disponible, perfil ADMIN y transferencias proyectadas consultables con alcance global.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario ADMIN configurado en el data provider y transferencia obtenida dinamicamente.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_246_BloqueoUsoSimultaneoFiltrosADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_246_BloqueoUsoSimultaneoFiltrosADMIN_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/* PRECONDICION *************************************************************/
		addStep("Precondicion: validar URL, usuario ADMIN y credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(
				url, user, "5555555", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
		testCase.addTextEvidenceCurrentStep(
				"Perfil en prueba: ADMIN | Usuario esperado: 5555555 | Datos obtenidos dinamicamente desde BD.");
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

		/* PASO 1 ******************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: no se muestra el inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/* PASO 2 ******************************************************************/
		addStep("Escribir '5555555' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor 5555555");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor '5555555'.");

		/* PASO 3 ******************************************************************/
		addStep("Escribir la contrasena del perfil en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada");
		testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");

		/* PASO 4 ******************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4, "Paso 4 fallido: las credenciales no permiten iniciar sesion");
		testCase.addTextEvidenceCurrentStep("El sistema valida las credenciales y muestra las opciones General y Portal COMETA.");

		/* PASO 5 ******************************************************************/
		addStep("Dar clic en la opcion Portal COMETA");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5, "Paso 5 fallido: Portal COMETA no muestra las opciones del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal con las opciones CEDIS, Directos, FAQS Directos y FAQS CEDIS.");

		/* PASO 6 ******************************************************************/
		addStep("Dar clic en la tarjeta CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "Paso 6 fallido: no se muestra la opcion Recepcion y Devoluciones");
		testCase.addTextEvidenceCurrentStep("Se visualiza el modulo CEDIS con la opcion Recepcion y Devoluciones.");

		/* PASO 7 ******************************************************************/
		addStep("Dar clic en la tarjeta Recepcion y Devoluciones");
		boolean resultStep7 = menu.clicRecepcionYDevolucion()
				&& recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep7, "Paso 7 fallido: no carga Recepcion de Transferencias y Devoluciones");
		testCase.addTextEvidenceCurrentStep("La pantalla muestra filtros rapidos, filtros historicos y el grid.");

		/* PASO 8 ******************************************************************/
		addStep("Escribir una transferencia valida y dar clic en Consultar de filtros rapidos");
		boolean resultStep8 = recepcion.descubreTransferenciaProyectadaGlobal()
				&& recepcion.capturaTransferenciaA()
				&& recepcion.consultaTransferenciaConResultados()
				&& recepcion.validaFiltrosHistoricosDeshabilitados()
				&& recepcion.validaBotonesHistoricosDeshabilitados();
		assertTrue(resultStep8, "Paso 8 fallido: la transferencia valida no fue capturada o consultada");
		testCase.addTextEvidenceCurrentStep(
				"El grid muestra resultados de la transferencia consultada y los filtros historicos quedan bloqueados.");

		/* PASO 9 ******************************************************************/
		addStep("Intentar capturar una fecha mientras la consulta por Transferencia permanece activa");
		boolean resultStep9 = recepcion.intentaCapturarFechaHistoricaBloqueada();
		assertTrue(resultStep9, "Paso 9 fallido: el filtro historico acepta uso simultaneo con filtros rapidos");
		testCase.addTextEvidenceCurrentStep(
				"La fecha historica no permite una consulta mientras los filtros rapidos estan activos.");

		/* PASO 10 *****************************************************************/
		addStep("Intentar dar clic en Consultar de filtros historicos mientras la consulta rapida esta activa");
		boolean resultStep10 = recepcion.intentaConsultaHistoricaBloqueadaYValidaTooltip();
		assertTrue(resultStep10, "Paso 10 fallido: no se muestra el tooltip de bloqueo historico o cambia el grid");
		testCase.addTextEvidenceCurrentStep(
				"Se muestra 'Para usar los filtros historicos, limpia primero los filtros rapidos.' y no se ejecuta la consulta historica.");

		/* PASO 11 *****************************************************************/
		addStep("Dar clic en Limpiar filtros de la seccion de filtros rapidos");
		boolean resultStep11 = recepcion.limpiaFiltrosRapidos()
				&& recepcion.validaEstadoInicialConsultarHistorico();
		assertTrue(resultStep11, "Paso 11 fallido: los filtros rapidos no regresan al estado inicial");
		testCase.addTextEvidenceCurrentStep(
				"Los filtros rapidos quedan vacios y los controles historicos vuelven a estar disponibles.");

		/* PASO 12 *****************************************************************/
		addStep("Capturar un rango historico reciente con datos y dar clic en Consultar");
		boolean resultStep12 = recepcion.capturaRangoHistoricoConDatos(user)
				&& recepcion.consultaHistoricaConResultados()
				&& recepcion.validaFiltrosRapidosDeshabilitados()
				&& recepcion.validaBotonesRapidosDeshabilitados();
		assertTrue(resultStep12, "Paso 12 fallido: la consulta historica no muestra resultados");
		testCase.addTextEvidenceCurrentStep(
				"El grid muestra resultados del rango historico y los filtros rapidos quedan bloqueados.");

		/* PASO 13 *****************************************************************/
		addStep("Intentar dar clic en Consultar de filtros rapidos mientras la consulta historica esta activa");
		boolean resultStep13 = recepcion.intentaConsultaRapidaBloqueadaYValidaTooltip();
		assertTrue(resultStep13, "Paso 13 fallido: no se muestra el tooltip de bloqueo rapido o cambia el grid");
		testCase.addTextEvidenceCurrentStep(
				"Se muestra 'Para usar los filtros rapidos, limpia primero los filtros historicos.' y no se ejecuta la consulta rapida.");
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
		return "*1. Ambiente QA disponible, perfil ADMIN y transferencias proyectadas consultables con alcance global.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que filtros rapidos e historicos no puedan usarse simultaneamente y muestren sus tooltips de bloqueo con perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_246_BloqueoUsoSimultaneoFiltrosADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}