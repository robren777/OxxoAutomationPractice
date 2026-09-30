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

/**DESCRIPCIÓN: Valida el recálculo automático del resumen superior al excluir una tienda-transferencia en Liquidación de Ruta con perfiles CEDIS y Admin. Cubre la ejecución de la consulta por Ruta Dinámica (UNIGIS) con al menos dos tiendas-transferencia, el registro del snapshot del resumen y de cada tienda, la exclusión de la primera tienda-transferencia con validación del modal homologado, la validación del recálculo automático (resumen del grid = suma manual de tiendas Incluidas) y la trazabilidad contra BD de los id_retek Incluidos.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_028 Recálculo automático del resumen superior al excluir una tienda-transferencia
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
public class ATC_FT_077_RecalculoAutomaticoResumenSuperiorExcluirTiendaTransferencia extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_077_RecalculoAutomaticoResumenSuperiorExcluirTiendaTransferencia_test(HashMap<String, String> data) throws Exception {
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
		addStep("Ejecutar una consulta valida por Ruta Dinámica (UNIGIS) " + PortalCometaMVP4.rutaPropuesta + " y la fecha " + PortalCometaMVP4.fechaFormateada + " y registrar los valores del primer renglón de resumen con todas las tiendas-transferencia en estado Incluido en el cálculo, con perfil " + perfil);
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
		boolean resultStep5Snapshot = rutaPage.registraResumenPrimerRenglonYTiendasTransferencia("antes_exclusion", true);
		assertTrue(resultStep5Snapshot, "No se pudo registrar el snapshot antes_exclusion del primer renglón de resumen y de cada tienda-transferencia");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Cambiar a Excluido del cálculo el selector de una tienda-transferencia con valores conocidos, esperar a que el modal Espere un momento desaparezca y validar que el sistema recalcule automáticamente el resumen del primer renglón, con perfil " + perfil);
		boolean resultStep6Abre = rutaPage.abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(true);
		assertTrue(resultStep6Abre, "El selector Estado de la primera tienda-transferencia no muestra únicamente Incluido en el cálculo y Excluido del cálculo");
		boolean resultStep6Excluye = rutaPage.seleccionaExcluidoDelCalculoPrimeraTiendaTransferenciaYValidaModal(true);
		assertTrue(resultStep6Excluye, "No se pudo seleccionar Excluido del cálculo o el modal Espere un momento no se comporto como se esperaba");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Comparar los nuevos valores del primer renglón contra la suma manual de las tiendas-transferencia que permanecen en estado Incluido en el cálculo, con perfil " + perfil);
		boolean resultStep7 = rutaPage.validaRecalculoResumenSumaManualTiendasIncluidas(true);
		assertTrue(resultStep7, "El resumen recalculado del primer renglón no coincide con la suma manual de las tiendas-transferencia Incluidas");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Ejecutar la consulta agregada en BD COMETA sobre las tiendas-transferencia Incluidas y validar trazabilidad del recálculo del frontend, con perfil " + perfil);
		boolean resultStep8 = rutaPage.validaSumaResumenContraBDTiendasIncluidas(user, PortalCometaMVP4.rutaPropuesta, PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep8, "La trazabilidad BD (id_retek de tiendas Incluidas y totales del resumen tras exclusión) no es consistente con lo mostrado por el grid");
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
		return "Valida el recálculo automático del resumen superior al excluir una tienda-transferencia en Liquidación de Ruta con perfiles CEDIS y Admin, cubriendo la ejecución de la consulta con al menos dos tiendas-transferencia, el registro del snapshot antes de excluir, la exclusión de una tienda-transferencia con modal homologado, la validación de que el resumen recalculado coincide con la suma manual de las tiendas Incluidas y la trazabilidad contra BD de los id_retek Incluidos.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_077_RecalculoAutomaticoResumenSuperiorExcluirTiendaTransferencia_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
