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

/**DESCRIPCION: Validar que al seleccionar mas de un valor en CEDIS, Plaza, Tienda o BOL el control muestre Multiples seleccionados y conserve la seleccion.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_089 - Validar seleccion multiple y texto Multiples seleccionados
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
public class ATC_FT_089_ValidarSeleccionMultipleTextoMultiplesSeleccionados extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_089_ValidarSeleccionMultipleTextoMultiplesSeleccionados_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";

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
		addStep("Seleccionar multiples valores en CEDIS");
		boolean resultStep8 = devEspecialesPage.seleccionarMultiplesValoresFiltroCedis(true);
		assertTrue(resultStep8, "El filtro CEDIS no mostro Multiples seleccionados despues de seleccionar mas de un valor");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep9 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep9, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por CEDIS");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Abrir de nuevo el control CEDIS");
		boolean resultStep10 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroCedis(true);
		assertTrue(resultStep10, "El filtro CEDIS no se desplego conservando la seleccion multiple");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar que las selecciones de CEDIS se mantienen");
		boolean resultStep11 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroCedis(true);
		assertTrue(resultStep11, "Las selecciones multiples de CEDIS no permanecen al reabrir el control");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Seleccionar multiples valores en Plaza");
		boolean resultStep12 = devEspecialesPage.seleccionarMultiplesValoresFiltroPlaza(true);
		assertTrue(resultStep12, "El filtro Plaza no mostro Multiples seleccionados despues de seleccionar mas de un valor");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep13 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep13, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por Plaza");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Abrir de nuevo el control Plaza");
		boolean resultStep14 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroPlaza(true);
		assertTrue(resultStep14, "El filtro Plaza no se desplego conservando la seleccion multiple");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Verificar que las selecciones de Plaza se mantienen");
		boolean resultStep15 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroPlaza(true);
		assertTrue(resultStep15, "Las selecciones multiples de Plaza no permanecen al reabrir el control");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Seleccionar multiples valores en Tienda");
		boolean resultStep16 = devEspecialesPage.seleccionarMultiplesValoresFiltroTienda(true);
		assertTrue(resultStep16, "El filtro Tienda no mostro Multiples seleccionados despues de seleccionar mas de un valor");

		/*
		 *                                             PASO 17
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep17 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep17, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por Tienda");

		/*
		 *                                             PASO 18
		 ***************************************************************************/
		addStep("Abrir de nuevo el control Tienda");
		boolean resultStep18 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroTienda(true);
		assertTrue(resultStep18, "El filtro Tienda no se desplego conservando la seleccion multiple");

		/*
		 *                                             PASO 19
		 ***************************************************************************/
		addStep("Verificar que las selecciones de Tienda se mantienen");
		boolean resultStep19 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroTienda(true);
		assertTrue(resultStep19, "Las selecciones multiples de Tienda no permanecen al reabrir el control");

		/*
		 *                                             PASO 20
		 ***************************************************************************/
		addStep("Seleccionar multiples valores en BOL");
		boolean resultStep20 = devEspecialesPage.seleccionarMultiplesValoresFiltroBol(true);
		assertTrue(resultStep20, "El filtro BOL no mostro Multiples seleccionados despues de seleccionar mas de un valor");

		/*
		 *                                             PASO 21
		 ***************************************************************************/
		addStep("Validar que el boton Consultar no se encuentre activo");
		boolean resultStep21 = devEspecialesPage.validarBotonConsultarDeshabilitadoSinRangoFechas(true);
		assertTrue(resultStep21, "El boton Consultar se habilito sin seleccionar Rango de Fechas despues de filtrar por BOL");

		/*
		 *                                             PASO 22
		 ***************************************************************************/
		addStep("Verificar que las selecciones de BOL se mantienen");
		boolean resultStep22 = devEspecialesPage.validarPersistenciaSeleccionMultipleFiltroBol(true);
		assertTrue(resultStep22, "Las selecciones multiples de BOL no permanecen al reabrir el control");

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
		return "Validar que al seleccionar mas de un valor en CEDIS, Plaza, Tienda o BOL el control muestre Multiples seleccionados y conserve la seleccion.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_089_ValidarSeleccionMultipleTextoMultiplesSeleccionados_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}