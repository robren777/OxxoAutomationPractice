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

/**DESCRIPCION: Validar que al seleccionar un solo valor en los filtros CEDIS, Plaza, Tienda o BOL el sistema aplique el valor visible correctamente sin alterar la etiqueta del control.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_088 - Validar seleccion simple en filtros CEDIS, Plaza, Tienda y BOL
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
public class ATC_FT_088_ValidarSeleccionSimpleFiltrosCEDISPlazaTiendaBOL extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_088_ValidarSeleccionSimpleFiltrosCEDISPlazaTiendaBOL_test(HashMap<String, String> data) throws Exception {
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
		assertTrue(resultStep2, "No se procesaron correctamente las credenciales validas del usuario CEDIS");

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
		addStep("Seleccionar un solo valor en el filtro CEDIS");
		boolean resultStep8 = devEspecialesPage.seleccionarPrimeraOpcionFiltroCedis(true);
		assertTrue(resultStep8, "No fue posible seleccionar un valor en el filtro CEDIS");
		boolean resultStep8Valor = devEspecialesPage.validarValorVisibleFiltroCedis(true);
		assertTrue(resultStep8Valor, "El filtro CEDIS no mostro el valor seleccionado correctamente");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep9 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep9, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por CEDIS");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Seleccionar un solo valor en el filtro Plaza");
		boolean resultStep10 = devEspecialesPage.seleccionarPrimeraOpcionFiltroPlaza(true);
		assertTrue(resultStep10, "No fue posible seleccionar un valor en el filtro Plaza");
		boolean resultStep10Valor = devEspecialesPage.validarValorVisibleFiltroPlaza(true);
		assertTrue(resultStep10Valor, "El filtro Plaza no mostro el valor seleccionado correctamente");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep11 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep11, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por Plaza");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Seleccionar un solo valor en el filtro Tienda");
		boolean resultStep12 = devEspecialesPage.seleccionarPrimeraOpcionFiltroTienda(true);
		assertTrue(resultStep12, "No fue posible seleccionar un valor en el filtro Tienda");
		boolean resultStep12Valor = devEspecialesPage.validarValorVisibleFiltroTienda(true);
		assertTrue(resultStep12Valor, "El filtro Tienda no mostro el valor seleccionado correctamente");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep13 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep13, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por Tienda");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Seleccionar un solo valor en el filtro BOL");
		boolean resultStep14Abrir = devEspecialesPage.abrirFiltroBol(true);
		assertTrue(resultStep14Abrir, "No fue posible abrir o habilitar el filtro BOL");
		boolean resultStep14Sugerencias = devEspecialesPage.escribirTresDigitosValidosFiltroBolYValidarSugerencias(true);
		assertTrue(resultStep14Sugerencias, "El filtro BOL no mostro sugerencias para seleccionar un valor");
		boolean resultStep14Seleccion = devEspecialesPage.seleccionarPrimeraSugerenciaFiltroBol(true);
		assertTrue(resultStep14Seleccion, "No fue posible seleccionar una sugerencia del filtro BOL");
		boolean resultStep14Valor = devEspecialesPage.validarValorVisibleFiltroBol(true);
		assertTrue(resultStep14Valor, "El filtro BOL no mostro el valor seleccionado correctamente");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep15 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep15, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por BOL");

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
		return "Validar que al seleccionar un solo valor en CEDIS, Plaza, Tienda o BOL el sistema aplique el valor visible correctamente y muestre la informacion correspondiente.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_088_ValidarSeleccionSimpleFiltrosCEDISPlazaTiendaBOL_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}