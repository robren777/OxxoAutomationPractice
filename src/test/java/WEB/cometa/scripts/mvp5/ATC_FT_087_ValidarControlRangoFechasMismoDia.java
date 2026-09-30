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

/**DESCRIPCIÓN: Validar que el filtro Rango de Fechas de Devoluciones Especiales use el calendario estandar, permita seleccionar fecha inicial y final, y acepte el mismo dia como rango valido.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_087 - Validar control de rango de fechas y seleccion de mismo dia
* 
*PREREQUISITOS:
*1. Credenciales para el perfil CEDIS
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuario CEDIS activo y acceso a Devoluciones Especiales.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_087_ValidarControlRangoFechasMismoDia extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_087_ValidarControlRangoFechasMismoDia_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";

		/*
		 * PRECONDICIÓN
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
		addStep("Abrir el control de calendario del filtro Rango de Fechas");
		boolean resultStep8 = devEspecialesPage.abrirControlCalendarioRangoFechas(true);
		assertTrue(resultStep8, "El calendario de Rango de Fechas no se desplego correctamente");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Validar la visualizacion del mes y anio");
		boolean resultStep9 = devEspecialesPage.validarVisualizacionMesAnioCalendario(true);
		assertTrue(resultStep9, "El encabezado del calendario no muestra correctamente mes y anio");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Validar fecha final disponible en calendario contra BD");
		boolean resultStep10 = devEspecialesPage.validarResaltadoDiaActualCalendario(true);
		assertTrue(resultStep10, "La fecha final disponible de BD no se muestra habilitada en el calendario");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Validar la navegacion entre meses");
		boolean resultStep11 = devEspecialesPage.validarNavegacionEntreMesesCalendario(true);
		assertTrue(resultStep11, "Las flechas de navegacion entre meses no se muestran correctamente");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Validar la distribucion de dias de la semana");
		boolean resultStep12 = devEspecialesPage.validarDistribucionDiasSemanaCalendario(true);
		assertTrue(resultStep12, "Los dias de la semana no se visualizan completos o alineados");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Validar el cierre del calendario");
		boolean resultStep13 = devEspecialesPage.validarCierreCalendarioAlSeleccionarFecha(true);
		assertTrue(resultStep13, "El calendario no se cerro correctamente al seleccionar una fecha");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Validar la persistencia de seleccion");
		boolean resultStep14 = devEspecialesPage.validarPersistenciaSeleccionCalendario(true);
		assertTrue(resultStep14, "La fecha seleccionada no permanece resaltada al reabrir el calendario");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Seleccionar una fecha inicial y una fecha final iguales");
		boolean resultStep15 = devEspecialesPage.seleccionarMismoDiaRangoFechas(true);
		assertTrue(resultStep15, "El sistema no acepto el mismo dia como rango valido");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Verificar que el boton Consultar se habilite con el rango valido");
		boolean resultStep16 = devEspecialesPage.validarBotonConsultarHabilitadoPorRangoFechas(true);
		assertTrue(resultStep16, "El boton Consultar no quedo habilitado con el rango de fechas valido");

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
		return "*1. Credenciales para el perfil CEDIS      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el filtro Rango de Fechas de Devoluciones Especiales use el calendario estandar, permita seleccionar fecha inicial y final, y acepte el mismo dia como rango valido.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_087_ValidarControlRangoFechasMismoDia_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}