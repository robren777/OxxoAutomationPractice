package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Valida la persistencia del estado Excluido durante la consulta actual y su limpieza al modificar filtros, presionar Limpiar filtros o salir/regresar a la pantalla Liquidación de Ruta con perfiles CEDIS y Admin. Cubre la exclusión de una tienda-transferencia, la persistencia del estado sin ejecutar nueva consulta, el regreso a Incluido tras modificar filtros y volver a Consultar, el descarte del estado Excluido tras Limpiar filtros y el descarte del estado Excluido al salir y regresar a la pantalla.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_030 Persistencia del estado incluido y excluido durante la consulta actual y limpieza al salir
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin, y una Ruta Dinámica válida con al menos dos tiendas-transferencia en la fecha consultada disponible en BD
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin y una Ruta Dinámica en BD con dos o más tiendas-transferencia asociadas en la fecha de consulta.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_079_PersistenciaEstadoIncluidoExcluidoLimpiezaAlSalir extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_079_PersistenciaEstadoIncluidoExcluidoLimpiezaAlSalir_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = user.equals("6666666") ? "CEDIS" : user.equals("5555555") ? "Admin" : "Perfil no identificado";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password y escenario de Ruta Dinámica de origen CEDIS dentro de los cuatro días visibles, con entre dos y cincuenta tiendas y transferencias para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		boolean resultPreEscenario = rutaPage.obtenerEscenarioRutaDinamicaConMultiplesTiendasParaUsuario(user);
		assertTrue(resultPreEscenario, "No se obtuvo desde BD una Ruta Dinámica de origen CEDIS, con fecha visible, entre dos y cincuenta tiendas y transferencias para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Capturar usuario, contraseña y hacer clic en Inicio de sesión");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se pudo ingresar a la URL de Portal Cometa");
		pc4.PrivatePortal();
		boolean resultStep1Login = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep1Login, "No se pudo iniciar sesion con el usuario: " + user);

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Hacer clic en Portal COMETA");
		boolean resultStep2 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep2, "No se mostro la pantalla principal del portal COMETA");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep3 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep3, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y seleccionar Liquidacion de Ruta");
		boolean resultStep4Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep4Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep4Liquidacion = pc4.opcLiquidacionRuta();
		assertTrue(resultStep4Liquidacion, "No se pudo seleccionar la opcion Liquidacion de Ruta");
		boolean resultStep4Pantalla = pc4.scLiquidacionRuta();
		assertTrue(resultStep4Pantalla, "No se mostro correctamente la pantalla Liquidacion de Ruta");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Ejecutar una consulta valida por Ruta Dinámica (UNIGIS) " + PortalCometaMVP4.rutaPropuesta + " y la fecha " + PortalCometaMVP4.fechaFormateada + " y cambiar a Excluido del cálculo una tienda-transferencia, con perfil " + perfil);
		boolean resultStep5Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5Fechas, "No se pudieron capturar Fecha inicial y Fecha final desde BD correctamente");
		boolean resultStep5Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep5Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro");
		boolean resultStep5Ruta = rutaPage.capturaRutaDinamicaUnigis(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Ruta, "No se pudo capturar la Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta + " en el filtro Ruta Dinámica");
		boolean resultStep5Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep5Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta);
		boolean resultStep5Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep5Consultar, "El botón Consultar no se habilito después de capturar la Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta);
		boolean resultStep5Consulta = rutaPage.consultaRutaDinamicaYValidaEncabezadoSinPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep5Consulta, "El grid no mostro el encabezado esperado con la Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta + " sin personalizada");
		boolean resultStep5Grid = rutaPage.validaGridConAlMenosDosTiendasTransferencia(true);
		assertTrue(resultStep5Grid, "El grid no muestra al menos dos tiendas-transferencia después de la consulta por Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta);
		boolean resultStep5Incluido = rutaPage.validaEstadoInicialIncluidoCalculoTodasTiendasTransferencia(true);
		assertTrue(resultStep5Incluido, "No todas las tiendas-transferencia iniciaron en estado Incluido en el cálculo");
		boolean resultStep5Abre = rutaPage.abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(true);
		assertTrue(resultStep5Abre, "El selector Estado de la primera tienda-transferencia no muestra únicamente Incluido en el cálculo y Excluido del cálculo");
		boolean resultStep5Excluye = rutaPage.seleccionaExcluidoDelCalculoPrimeraTiendaTransferenciaYValidaModal(true);
		assertTrue(resultStep5Excluye, "No se pudo seleccionar Excluido del cálculo o el modal Espere un momento no se comporto como se esperaba");
		boolean resultStep5EstadoVisual = rutaPage.validaEstadoVisualExcluidaVsIncluida(true);
		assertTrue(resultStep5EstadoVisual, "La primera tienda-transferencia no quedo en estado Excluido con línea gris + signo menos + selector rojo");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Desplazarse dentro de la pantalla, abrir y cerrar selectores y verificar que el estado Excluido del cálculo se mantiene sin ejecutar una nueva consulta, con perfil " + perfil);
		boolean resultStep6 = rutaPage.interactuaSinConsultarYValidaEstadoExcluidoPersiste(true);
		assertTrue(resultStep6, "El estado Excluido del cálculo se perdio al interactuar con otros controles sin ejecutar Consultar");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Modificar el filtro Tienda agregando una tienda nueva y volver a Consultar; validar que todas las tiendas-transferencia devueltas vuelven al estado por defecto Incluido en el cálculo, con perfil " + perfil);
		boolean resultStep7Agrega = rutaPage.marcaTiendaAdicionalFueraRutaDinamica(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep7Agrega, "No se pudo agregar una tienda adicional al filtro Tienda");
		boolean resultStep7Consulta = rutaPage.consultaRutaDinamicaYValidaEncabezadoPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep7Consulta, "El grid no mostro el encabezado esperado con Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta + " personalizada después de agregar una tienda nueva");
		boolean resultStep7Incluidas = rutaPage.validaEstadoInicialIncluidoCalculoTodasTiendasTransferencia(true);
		assertTrue(resultStep7Incluidas, "No todas las tiendas-transferencia volvieron al estado por defecto Incluido en el cálculo después de la nueva consulta");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Volver a cambiar a Excluido del cálculo una tienda-transferencia, hacer clic en Limpiar filtros y validar que la nueva consulta muestra todas las tiendas-transferencia en Incluido en el cálculo, con perfil " + perfil);
		boolean resultStep8AbreSelector = rutaPage.abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(true);
		assertTrue(resultStep8AbreSelector, "El selector Estado de la primera tienda-transferencia no muestra únicamente Incluido en el cálculo y Excluido del cálculo");
		boolean resultStep8Excluye = rutaPage.seleccionaExcluidoDelCalculoPrimeraTiendaTransferenciaYValidaModal(true);
		assertTrue(resultStep8Excluye, "No se pudo excluir la primera tienda-transferencia antes de Limpiar filtros");
		boolean resultStep8Limpiar = pc4.clickLimpiarFiltros(true);
		assertTrue(resultStep8Limpiar, "No se pudo hacer clic en Limpiar filtros o el modal Espere un momento no desaparecio");
		boolean resultStep8EstadoInicial = rutaPage.validaEstadoInicialInactivoFiltrosYConsultar(true);
		assertTrue(resultStep8EstadoInicial, "Los filtros y el botón Consultar no quedaron en estado inicial inactivo después de Limpiar filtros");
		boolean resultStep8Fechas = rutaPage.capturaFechasInicialFinalDesdeBD(PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep8Fechas, "No se pudieron capturar Fecha inicial y Fecha final para la nueva consulta después de Limpiar filtros");
		boolean resultStep8SelectorRD = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep8SelectorRD, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro tras Limpiar filtros");
		boolean resultStep8Ruta = rutaPage.capturaRutaDinamicaUnigis(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep8Ruta, "No se pudo capturar la Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta + " tras Limpiar filtros");
		boolean resultStep8Consulta = rutaPage.consultaRutaDinamicaYValidaEncabezadoSinPersonalizada(PortalCometaMVP4.rutaPropuesta, true);
		assertTrue(resultStep8Consulta, "El grid no mostro el encabezado sin personalizada tras la consulta posterior a Limpiar filtros");
		boolean resultStep8Incluidas = rutaPage.validaEstadoInicialIncluidoCalculoTodasTiendasTransferencia(true);
		assertTrue(resultStep8Incluidas, "No todas las tiendas-transferencia iniciaron en estado Incluido en el cálculo después de Limpiar filtros y consultar nuevamente");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Salir de la pantalla Liquidación de Ruta haciendo clic en otro botón del módulo CEDIS y regresar nuevamente; validar que los filtros y el grid quedan en estado inicial descartando el estado excluido previo, con perfil " + perfil);
		boolean resultStep9Salir = rutaPage.clicBotonDevolucionesEspecialesLiquidacionRuta();
		assertTrue(resultStep9Salir, "No se pudo salir de Liquidación de Ruta hacia Devoluciones Especiales");
		boolean resultStep9PantallaDest = rutaPage.scDevolucionesEspecialesCedis(true);
		assertTrue(resultStep9PantallaDest, "No se mostro correctamente la pantalla Devoluciones Especiales de CEDIS");
		boolean resultStep9Regresar = rutaPage.clicBotonLiquidacionRutaDesdeDevolucionesEspeciales();
		assertTrue(resultStep9Regresar, "No se pudo regresar a Liquidación de Ruta desde Devoluciones Especiales");
		boolean resultStep9PantallaLR = pc4.scLiquidacionRuta();
		assertTrue(resultStep9PantallaLR, "No se mostro correctamente la pantalla Liquidación de Ruta al regresar");
		boolean resultStep9EstadoInicial = rutaPage.validaEstadoInicialInactivoFiltrosYConsultar(true);
		assertTrue(resultStep9EstadoInicial, "Los filtros y el botón Consultar no quedaron en estado inicial inactivo tras regresar a Liquidación de Ruta");
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
		return "*1. Credenciales para los perfiles CEDIS y Admin, y una Ruta Dinámica válida con al menos dos tiendas-transferencia en la fecha consultada disponible en BD      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida la persistencia del estado Excluido durante la consulta actual y su limpieza al modificar filtros, presionar Limpiar filtros o salir/regresar a la pantalla Liquidación de Ruta con perfiles CEDIS y Admin. Cubre la exclusión de una tienda-transferencia, la persistencia del estado sin ejecutar nueva consulta, el regreso a Incluido tras modificar filtros y volver a Consultar, el descarte del estado Excluido tras Limpiar filtros y el descarte del estado Excluido al salir y regresar a la pantalla.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_079_PersistenciaEstadoIncluidoExcluidoLimpiezaAlSalir_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
