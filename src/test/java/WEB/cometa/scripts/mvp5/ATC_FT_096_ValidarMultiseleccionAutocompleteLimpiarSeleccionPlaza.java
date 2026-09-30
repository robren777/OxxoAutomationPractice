package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesEspecialesPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que el filtro Plaza permita autocomplete, seleccion multiple con checkbox y la accion Limpiar seleccion sin perder el comportamiento esperado.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_096 - Validar multi-seleccion, autocomplete y limpiar seleccion del filtro Plaza
* 
*PREREQUISITOS:
*1. Credenciales para el perfil ADMIN
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuario ADMIN activo y acceso a Devoluciones Especiales.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_096_ValidarMultiseleccionAutocompleteLimpiarSeleccionPlaza extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_096_ValidarMultiseleccionAutocompleteLimpiarSeleccionPlaza_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Acceder a la URL de inicio de sesion del portal CEDIS");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se cargo correctamente la pantalla de inicio de sesion del portal CEDIS");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2 Y PASO 3
		 ***************************************************************************/
		addStep("Capturar usuario y password validos, y hacer clic en Entrar");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se procesaron correctamente las credenciales validas del usuario ADMIN");

		/*
		 *                                             PASO 4 Y PASO 5
		 ***************************************************************************/
		addStep("Esperar la carga del portal y hacer clic en Portal COMETA");
		boolean resultStep4 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep4, "No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep7Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep7DevEspeciales = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep7DevEspeciales, "No se pudo abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Filtros = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep7Filtros, "No se mostraron todos los filtros esperados de Devoluciones Especiales");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Seleccionar el filtro Plaza y validar opciones disponibles");
		boolean resultStep8 = devEspecialesPage.abrirFiltroPlazaYValidarFormatoOpciones(true);
		assertTrue(resultStep8, "El filtro Plaza no mostro opciones disponibles correctamente");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Escribir un valor en el filtro Plaza y validar autocomplete");
		boolean resultStep9 = devEspecialesPage.escribirValorAutocompleteFiltroPlaza(true);
		assertTrue(resultStep9, "El filtro Plaza no mostro sugerencias de autocomplete");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Seleccionar varias opciones con checkbox en Plaza");
		boolean resultStep10 = devEspecialesPage.seleccionarMultiplesValoresFiltroPlazaManteniendoPanel(true);
		assertTrue(resultStep10, "El filtro Plaza no permitio seleccionar multiples valores o no mostro Multiples seleccionados");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar que el selector Plaza no se cierre automaticamente");
		boolean resultStep11 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroPlaza(true);
		assertTrue(resultStep11, "El control Plaza no conserva la seleccion multiple al reabrirse");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Usar la opcion Limpiar seleccion en Plaza");
		boolean resultStep12 = devEspecialesPage.limpiarSeleccionFiltroPlaza(true);
		assertTrue(resultStep12, "No fue posible limpiar la seleccion multiple del filtro Plaza");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Validar que el filtro Plaza vuelva a su estado inicial");
		boolean resultStep13 = devEspecialesPage.validarFiltroPlazaEstadoInicial(true);
		assertTrue(resultStep13, "El filtro Plaza no regreso a su estado inicial despues de limpiar seleccion");

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
		return "*1. Credenciales para el perfil ADMIN      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el filtro Plaza permita autocomplete, seleccion multiple con checkbox y la accion Limpiar seleccion sin perder el comportamiento esperado.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_096_ValidarMultiseleccionAutocompleteLimpiarSeleccionPlaza_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}