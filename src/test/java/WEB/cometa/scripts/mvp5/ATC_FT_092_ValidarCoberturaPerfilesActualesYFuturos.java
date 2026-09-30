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

/**DESCRIPCION: Validar que la pantalla Devoluciones Especiales conserve el comportamiento de filtros y consulta para los perfiles CEDIS y ADMIN como cobertura para perfiles actuales y futuros.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_092 - Validar cobertura para perfiles actuales y futuros
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y ADMIN
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios CEDIS y ADMIN activos con acceso a Devoluciones Especiales.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_092_ValidarCoberturaPerfilesActualesYFuturos extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_092_ValidarCoberturaPerfilesActualesYFuturos_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = resolverPerfil(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		assertTrue(!"NO_DEFINIDO".equals(perfil), "El usuario del data provider no corresponde a un perfil esperado para este caso: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Acceder a la URL de inicio de sesion del portal CEDIS");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se cargo correctamente la pantalla de inicio de sesion del portal CEDIS para el perfil " + perfil);
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2 Y PASO 3
		 ***************************************************************************/
		addStep("Capturar usuario y password validos, y hacer clic en Entrar");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se procesaron correctamente las credenciales validas del perfil " + perfil);

		/*
		 *                                             PASO 4 Y PASO 5
		 ***************************************************************************/
		addStep("Esperar la carga del portal y hacer clic en Portal COMETA");
		boolean resultStep4 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep4, "No se mostro o no fue posible seleccionar Portal COMETA para el perfil " + perfil);

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "No se mostro la opcion Recepcion dentro del modulo CEDIS para el perfil " + perfil);

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep7Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones con el perfil " + perfil);
		boolean resultStep7DevEspeciales = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep7DevEspeciales, "No se pudo abrir la pantalla Devoluciones Especiales con el perfil " + perfil);
		boolean resultStep7Filtros = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep7Filtros, "No se mostraron todos los filtros esperados de Devoluciones Especiales para el perfil " + perfil);
		boolean resultStep7BolNoDisponible = devEspecialesPage.validarFiltroBolDeshabilitadoSinTienda(true);
		assertTrue(resultStep7BolNoDisponible, "El filtro BOL no se mostro no disponible sin Tienda seleccionada para el perfil " + perfil);

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Validar cobertura de catalogos, filtros y flujo de consulta para el perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("La iteracion actual valida el comportamiento funcional reutilizable para el perfil " + perfil + ".");

		boolean resultStep8CedisCatalogo = devEspecialesPage.abrirFiltroCedisYValidarFormatoOpciones(true);
		assertTrue(resultStep8CedisCatalogo, "El catalogo CEDIS no mostro opciones validas para el perfil " + perfil);
		boolean resultStep8Cedis = devEspecialesPage.seleccionarPrimeraOpcionFiltroCedis(true);
		assertTrue(resultStep8Cedis, "No fue posible seleccionar un valor en el filtro CEDIS para el perfil " + perfil);
		boolean resultStep8CedisVisible = devEspecialesPage.validarValorVisibleFiltroCedis(true);
		assertTrue(resultStep8CedisVisible, "El filtro CEDIS no mostro el valor seleccionado correctamente para el perfil " + perfil);

		boolean resultStep8PlazaCatalogo = devEspecialesPage.abrirFiltroPlazaYValidarFormatoOpciones(true);
		assertTrue(resultStep8PlazaCatalogo, "El catalogo Plaza no mostro opciones validas para el perfil " + perfil);
		boolean resultStep8Plaza = devEspecialesPage.seleccionarPrimeraOpcionFiltroPlaza(true);
		assertTrue(resultStep8Plaza, "No fue posible seleccionar un valor en el filtro Plaza para el perfil " + perfil);
		boolean resultStep8PlazaVisible = devEspecialesPage.validarValorVisibleFiltroPlaza(true);
		assertTrue(resultStep8PlazaVisible, "El filtro Plaza no mostro el valor seleccionado correctamente para el perfil " + perfil);

		boolean resultStep8TiendaCatalogo = devEspecialesPage.abrirFiltroTiendaYValidarFormatoOpciones(true);
		assertTrue(resultStep8TiendaCatalogo, "El catalogo Tienda no mostro opciones validas para el perfil " + perfil);
		boolean resultStep8Tienda = devEspecialesPage.seleccionarPrimeraOpcionFiltroTienda(true);
		assertTrue(resultStep8Tienda, "No fue posible seleccionar un valor en el filtro Tienda para el perfil " + perfil);
		boolean resultStep8TiendaVisible = devEspecialesPage.validarValorVisibleFiltroTienda(true);
		assertTrue(resultStep8TiendaVisible, "El filtro Tienda no mostro el valor seleccionado correctamente para el perfil " + perfil);

		boolean resultStep8AbrirBol = devEspecialesPage.abrirFiltroBol(true);
		assertTrue(resultStep8AbrirBol, "No fue posible abrir o habilitar el filtro BOL para el perfil " + perfil);
		boolean resultStep8SugerenciasBol = devEspecialesPage.escribirTresDigitosValidosFiltroBolYValidarSugerencias(true);
		assertTrue(resultStep8SugerenciasBol, "El filtro BOL no mostro sugerencias para el perfil " + perfil);
		boolean resultStep8SeleccionBol = devEspecialesPage.seleccionarPrimeraSugerenciaFiltroBol(true);
		assertTrue(resultStep8SeleccionBol, "No fue posible seleccionar una sugerencia del filtro BOL para el perfil " + perfil);
		boolean resultStep8BolVisible = devEspecialesPage.validarValorVisibleFiltroBol(true);
		assertTrue(resultStep8BolVisible, "El filtro BOL no mostro el valor seleccionado correctamente para el perfil " + perfil);

		boolean resultStep8RangoFechas = devEspecialesPage.seleccionarMismoDiaRangoFechas(true);
		assertTrue(resultStep8RangoFechas, "No fue posible seleccionar un Rango de Fechas valido para el perfil " + perfil);
		boolean resultStep8Consultar = devEspecialesPage.validarBotonConsultarHabilitadoPorRangoFechas(true);
		assertTrue(resultStep8Consultar, "El boton Consultar no quedo habilitado al aplicar filtros y rango de fechas para el perfil " + perfil);

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
		return "*1. Credenciales para los perfiles CEDIS y ADMIN      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que Devoluciones Especiales conserve el comportamiento de filtros y consulta para los perfiles CEDIS y ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_092_ValidarCoberturaPerfilesActualesYFuturos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	private String resolverPerfil(String user) {
		if ("6666666".equals(user)) {
			return "CEDIS";
		}
		if ("5555555".equals(user)) {
			return "ADMIN";
		}
		return "NO_DEFINIDO";
	}
}