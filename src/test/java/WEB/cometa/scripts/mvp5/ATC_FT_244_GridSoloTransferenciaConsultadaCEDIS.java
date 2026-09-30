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

/**DESCRIPCION: Verifica que el grid muestre unicamente registros de la transferencia consultada con perfil CEDIS.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_244 CA09 - Verificar que el grid muestra unicamente registros de la transferencia consultada - perfil CEDIS
* 
*PREREQUISITOS:
*1. Ambiente QA disponible, perfil CEDIS y al menos dos transferencias distintas asociadas al PCSUPPLIERID del usuario.
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Usuario CEDIS configurado en el data provider; Transferencias A y B obtenidas dinamicamente.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_244_GridSoloTransferenciaConsultadaCEDIS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_244_GridSoloTransferenciaConsultadaCEDIS_test(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/* PRECONDICION *************************************************************/
		addStep("Precondicion: validar URL, usuario CEDIS y credenciales configuradas");
		boolean resultPrecondition = accessControl.validarPrecondicionPerfil(
				url, user, "6666666", perfil, password);
		assertTrue(resultPrecondition,
				"Precondicion fallida: la URL, el usuario CEDIS o sus credenciales no estan configurados");
		testCase.addTextEvidenceCurrentStep(
				"Perfil en prueba: CEDIS | Usuario esperado: 6666666 | Existen al menos dos transferencias distintas en QA.");
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

		/* PASO 1 ******************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "Paso 1 fallido: no se muestra el inicio de sesion de Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/* PASO 2 ******************************************************************/
		addStep("Escribir '6666666' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor 6666666");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor '6666666'.");

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
		assertTrue(resultStep5, "Paso 5 fallido: Portal COMETA no muestra las opciones del perfil CEDIS");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal con las opciones CEDIS y FAQS CEDIS.");

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
		addStep("Obtener mediante filtros historicos un rango dinamico con datos y documentar Transferencia A y Transferencia B distintas");
		boolean resultStep8 = recepcion.descubreDosTransferenciasConDatos(user, perfil);
		assertTrue(resultStep8, "Paso 8 fallido: no se documentaron dos transferencias distintas");
		testCase.addTextEvidenceCurrentStep(
				"El grid muestra multiples registros y quedan documentadas Transferencia A y Transferencia B con numeros distintos.");

		/* PASO 9 ******************************************************************/
		addStep("Dar clic en Limpiar filtros de filtros historicos para regresar al estado inicial");
		boolean resultStep9 = recepcion.limpiaFiltrosHistoricos();
		assertTrue(resultStep9, "Paso 9 fallido: los filtros historicos no regresan al estado inicial");
		testCase.addTextEvidenceCurrentStep(
				"Los campos historicos quedan vacios y el filtro rapido Transferencia vuelve a estar habilitado.");

		/* PASO 10 *****************************************************************/
		addStep("Capturar Transferencia A en el filtro Transferencia y dar clic en Consultar");
		boolean resultStep10 = recepcion.capturaTransferenciaA()
				&& recepcion.consultaTransferenciaConResultados();
		assertTrue(resultStep10, "Paso 10 fallido: Transferencia A no fue capturada o consultada con resultados");
		testCase.addTextEvidenceCurrentStep(
				"El campo conserva Transferencia A y el grid muestra resultados de la transferencia consultada.");

		/* PASO 11 *****************************************************************/
		addStep("Verificar que TODOS los registros del grid contienen el numero de Transferencia A");
		boolean resultStep11 = recepcion.validaGridSoloTransferenciaA();
		assertTrue(resultStep11, "Paso 11 fallido: el grid mezcla registros ajenos a Transferencia A");
		testCase.addTextEvidenceCurrentStep(
				"El cien por ciento de todos los registros procesados pertenece a Transferencia A, sin mezclar Transferencia B ni otra transferencia.");

		/* PASO 12 *****************************************************************/
		addStep("Documentar la cantidad N_A de registros mostrados para Transferencia A");
		boolean resultStep12 = recepcion.documentaConteoTransferenciaA();
		assertTrue(resultStep12, "Paso 12 fallido: no se documento una cantidad positiva para Transferencia A");
		testCase.addTextEvidenceCurrentStep(
				"La cantidad N_A queda documentada con un numero positivo de registros de Transferencia A.");

		/* PASO 13 *****************************************************************/
		addStep("Limpiar filtros rapidos, consultar Transferencia B y validar el orden descendente por fecha");
		boolean resultStep13 = recepcion.consultaTransferenciaBYValidaOrden();
		assertTrue(resultStep13, "Paso 13 fallido: Transferencia B no muestra resultados en orden descendente");
		testCase.addTextEvidenceCurrentStep(
				"El grid muestra resultados de Transferencia B de la fecha mas reciente a la mas antigua.");

		/* PASO 14 *****************************************************************/
		addStep("Verificar que TODOS los registros del grid contienen Transferencia B y documentar N_B respecto de N_A");
		boolean resultStep14 = recepcion.validaGridSoloTransferenciaB();
		assertTrue(resultStep14, "Paso 14 fallido: el grid mezcla transferencias o no muestra Transferencia B");
		testCase.addTextEvidenceCurrentStep(
				"El cien por ciento de todos los registros procesados pertenece a Transferencia B, sin mezclar Transferencia A; N_B y N_A quedan documentados aunque sus cantidades sean iguales o distintas.");
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
		return "*1. Ambiente QA disponible, perfil CEDIS y dos transferencias distintas asociadas al PCSUPPLIERID del usuario.      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verifica que el grid muestre unicamente registros de las Transferencias A y B consultadas con perfil CEDIS.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_244_GridSoloTransferenciaConsultadaCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}