package WorkFlows.Siv.NexxoSiv;

import static org.testng.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Siv.pomSIV;
import WorkFlows.util.Siv.utilSiv_BuscarArticulo;
import WorkFlows.util.Siv.utilSiv_Conjuntos;
import WorkFlows.util.Siv.utilSiv_InboxPendientes;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_420_Categoria_Modificacion_CBExistente_DecrementoLegos_NexxoSiv extends BaseExecution {
	private SeleniumUtil u;
	private pomSIV pom;
	private pomUltimus ultimus;
	private utilSiv_InboxPendientes inbox;
	private utilSiv_BuscarArticulo buscar;
	private utilSiv_Conjuntos conjunto;
	private String windowUltimus = "";

	@Test(dataProvider = "data-provider")

	public void ATC_FT_420_Categoria_Modificacion_CBExistente_DecrementoLegos_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("Nexxo Siv");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioCategoria = data.get("usuarioCategoria");

		// - CB - //
		String cb = data.get("cb");

		// - Conjuntos -//
		String cambioCobertura = data.get("cambioCobertura");

		/// INICIALIZACIÓN DE POMS ///
		u = new SeleniumUtil(new FirefoxTest(), true);
		windowUltimus = u.getDriver().getWindowHandle();
		pom = new pomSIV(u, testCase);
		ultimus = new pomUltimus(u);
		inbox = new utilSiv_InboxPendientes(u, testCase);
		buscar = new utilSiv_BuscarArticulo(u, testCase);
		conjunto = new utilSiv_Conjuntos(u, testCase);

		/// LOGIN ULTIMUS ///
		addStep("Iniciar sesión con usuario: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCategoria);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		u.hardWait(2);
		pom.waitLoadingModal();
		u.hardWait(2);
		pom.waitLoadingModal();
		testCase.addScreenShotCurrentStep(u, "Portal Siv");

		/// AGREGAR ITEM ///
		addStep("Agregar ítem: buscar por código de barras");
		inbox.agregarItem();
		u.hardWait(2);

		// cb = "";

		/// BUSCAR EL CB ///
		buscar.enterCB(cb);
		buscar.busquedaPredeterminada();
		if (buscar.seEncontraronArticulos()) {
			addStep("Seleccionar artículo y Modificar");
			buscar.seleccionarEncontrados(cb);
		} else {
			throw new AssertionError("No se encontro el artículo: " + cb);
		}

		/// MODIFICAR ///
		buscar.Modificar();
		if (pom.msgModal().contains("exitosamente")) {
			pom.optionMsgModal("OK");
		} else {
			throw new AssertionError("No se agregaron los artículos");
		}

		/// IR AL MÓDULO DE CONJUNTOS ///
		addStep("Ir a Conjuntos");
		u.hardWait(4);
		inbox.seleccionarItemPendiente(cb);
		inbox.conjuntos();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Modulo Conjuntos");

		if (pom.msgModal().contains("No se han configurado reglas de precio para Bara"))
			pom.optionMsgModal("OK");

		addStep(cambioCobertura + " Cobertura");
		conjunto.buscar();
		testCase.addTextEvidenceCurrentStep("Cobertura Objetivo");
		conjunto.coberturaObjetivo();
		conjunto.seleccionarObjetivos();

		if (cambioCobertura.equalsIgnoreCase("Incrementar")) {
			conjunto.incrementar();
			u.hardWait(3);
			conjunto.guardarConfiguracion();
		} else if (cambioCobertura.equalsIgnoreCase("Reducir")) {
			conjunto.reducir();
			u.hardWait(3);
			conjunto.guardarConfigMod("Merma");
		}

		u.hardWait(1);
		conjunto.salir();
		u.hardWait(2);

		/// ENVIAR SELECCION ///
		addStep("Envíar Selección");
		u.hardWait(4);
		inbox.seleccionarItemPendiente(cb);
		u.hardWait(2);
		inbox.enviarSeleccion();
		u.hardWait(1);

		if (pom.msgModal().contains("El ciclo seleccionado para los siguientes"))
			pom.optionMsgModal("ACEPTAR");

		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Enviada");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowUltimus);

		/// CERRAR SESION EN ULTIMUS ///
		addStep("Cerrar sesión en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// REVISAR ESTATUS ///

		addStep("Validar en la Base de Datos el estatus del Código de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,
					GlobalVariables.DB_USER2_XXVA_SIV3, GlobalVariables.DB_PASSWORD2_XXVA_SIV3);
			String query_ItemStatus = "SELECT item_rtk, item_transaccion_id, step_label, incidenteultimus_id FROM XXVA_SIV3.xxva_item_incident_ultimus WHERE item_RTK = '"
					+ cb + "' AND ROWNUM <= 1 ORDER BY item_transaccion_id DESC";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			assertNotNull(queryResult.isEmpty(), "No se encontraro el codigo de Barras");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

		/// FIN DE FLUJO ///

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ingresar los datos en el data-provider correspondiente";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "ATC_FT_420_Categoria_Modificacion_CBExistente_DecrementoLegos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_420_Categoria_Modificacion_CBExistente_DecrementoLegos_NexxoSiv";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}
