package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Valida el rango combinado de fecha y hora en el grid de Liquidación de Ruta con perfiles CEDIS y Admin.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_025 Validación del rango combinado de fecha y hora en el grid
* 
*PREREQUISITOS:
*1. Credenciales para los perfiles CEDIS y Admin, y escenario BD con ruta dinámica con uno o más registros en created_at y origen cedis
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con usuarios activos para los perfiles CEDIS y Admin y datos vigentes en xxcm_historico_correos para validar el rango combinado fecha + hora.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_074_ValidacionRangoCombinadoFechaHoraGrid extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_074_ValidacionRangoCombinadoFechaHoraGrid_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = user.equals("6666666") ? "CEDIS" : user.equals("5555555") ? "ADMIN" : "Perfil no identificado";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password, Ruta Dinámica válida y fecha con tiendas-transferencia asociadas en BD para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		boolean resultPreRuta = pc4.obtenerRutaPropuesta(user);
		assertTrue(resultPreRuta, "No se obtuvo una ruta dinámica base desde BD para el usuario: " + user);
		boolean resultPreFecha = pc4.obtenerFechaRegistroRutaPropuesta(user);
		assertTrue(resultPreFecha, "No se obtuvo fecha de registro válida desde BD para el usuario: " + user);

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
		addStep("Preparar el escenario obteniendo desde BD registros de xxcm_historico_correos por Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta + ", created_at mayor a " + PortalCometaMVP4.fechaFormateada + " y origen cedis, con perfil " + perfil);
		boolean resultStep5 = rutaPage.rutaPropuestaObtenerEscenarioCreatedAtDesdeBD(PortalCometaMVP4.rutaPropuesta, PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep5, "No se pudo preparar el escenario desde BD con created_at > " + PortalCometaMVP4.fechaFormateada + ", origen cedis y Ruta Dinámica " + PortalCometaMVP4.rutaPropuesta);
		String rutaDinamicaEscenario = rutaPage.rutaPropuestaGetRutaDinamicaEscenario();
		String horaMinBD = rutaPage.getHoraMinimaBD();
		String horaMedBD = rutaPage.getHoraMediaBD();
		String horaMaxBD = rutaPage.getHoraMaximaBD();
		testCase.addBoldTextEvidenceCurrentStep("Escenario listo desde created_at | Ruta efectiva: " + rutaDinamicaEscenario + " | Hora mínima BD: " + horaMinBD + " | Hora media BD: " + horaMedBD + " | Hora máxima BD: " + horaMaxBD);

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Capturar Fecha inicial y Fecha final obtenidas desde created_at BD, con rango de Horas del día completo (00:00 – 23:00), consultar por Ruta Dinámica (UNIGIS) " + rutaDinamicaEscenario + " y validar que el grid muestra todas las tiendas con información en el rango, con perfil " + perfil);
		boolean resultStep6Fechas = rutaPage.rutaPropuestaCapturaFechasObtenidasPorCreatedAt(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, true);
		assertTrue(resultStep6Fechas, "No se pudieron capturar Fecha inicial y Fecha final obtenidas desde created_at en rutaLiquidacionPage");
		boolean resultStep6Horas = rutaPage.capturaHorasInicialFinalLR("00:00", "23:00", true);
		assertTrue(resultStep6Horas, "No se pudieron capturar Hora inicial 00:00 y Hora final 23:00 para el rango del día completo");
		boolean resultStep6Selector = rutaPage.seleccionaRutaDinamicaEnSelectorFiltro(true);
		assertTrue(resultStep6Selector, "No se mostro Ruta Dinámica (UNIGIS) como valor elegido en el Selector de Filtro");
		boolean resultStep6Ruta = rutaPage.rutaPropuestaCapturaRutaDinamicaUnigis(rutaDinamicaEscenario, true);
		assertTrue(resultStep6Ruta, "No se pudo capturar la Ruta Dinámica " + rutaDinamicaEscenario + " en el filtro Ruta Dinámica");
		boolean resultStep6Tienda = rutaPage.validaFiltroTiendaActivo(true);
		assertTrue(resultStep6Tienda, "El filtro Tienda no se activo después de capturar la Ruta Dinámica " + rutaDinamicaEscenario);
		boolean resultStep6Consultar = rutaPage.validaBotonConsultarActivo(true);
		assertTrue(resultStep6Consultar, "El botón Consultar no se habilito después de capturar la Ruta Dinámica " + rutaDinamicaEscenario);
		testCase.addBoldTextEvidenceCurrentStep("[PASO 6] Filtros aplicados | Ruta: " + rutaDinamicaEscenario + " | Fechas obtenidas desde created_at BD | Rango de horas: 00:00 - 23:00 (día completo)");
		System.out.println("[PASO 6] Rango horas aplicado en portal: 00:00 - 23:00 (día completo)");
		boolean resultStep6DiaCompleto = rutaPage.rutaPropuestaValidaTotalTiendasGridContraBDPorRangoHoras(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, "00:00", "23:00", true);
		assertTrue(resultStep6DiaCompleto, "El total de tiendas del grid con rango del día completo no coincide con el total esperado en BD para la Ruta Dinámica " + rutaDinamicaEscenario);

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Modificar Hora final a un valor entre la hora media y la hora máxima obtenidas desde BD (Hora inicial: 00:00, Hora final: " + horaMedBD + ") y validar que el grid excluye la(s) tienda(s) con hora de entrega posterior a " + horaMedBD + ", con perfil " + perfil);
		boolean resultStep7Horas = rutaPage.capturaHorasInicialFinalLR("00:00", horaMedBD, true);
		assertTrue(resultStep7Horas, "No se pudieron capturar Hora inicial 00:00 y Hora final " + horaMedBD + " para excluir la tienda de hora tardía");
		testCase.addBoldTextEvidenceCurrentStep("[PASO 7] Filtros aplicados | Ruta: " + rutaDinamicaEscenario + " | Fecha: " + PortalCometaMVP4.fechaFormateada + " | Rango de horas: 00:00 - " + horaMedBD + " (hora final acotada a la mediana BD; deben excluirse tiendas con hora > " + horaMedBD + ")");
		System.out.println("[PASO 7] Rango horas aplicado en portal: 00:00 - " + horaMedBD + " (excluye tiendas con hora > " + horaMedBD + ")");
		boolean resultStep7Rango = rutaPage.rutaPropuestaValidaTotalTiendasGridContraBDPorRangoHoras(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, "00:00", horaMedBD, true);
		assertTrue(resultStep7Rango, "El total de tiendas del grid con Hora final " + horaMedBD + " no coincide con el total esperado en BD (debe excluir tienda(s) con hora > " + horaMedBD + ")");
		boolean resultStep7TiendasFuera = rutaPage.rutaPropuestaValidaTiendasFueraDeRangoNoVisiblesEnGrid(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, "00:00", horaMedBD, true);
		assertTrue(resultStep7TiendasFuera, "El grid muestra tienda(s) con hora de entrega fuera del rango 00:00 - " + horaMedBD);

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Modificar Hora inicial a " + horaMinBD + " y Hora final a " + horaMedBD + " conservando el resto de los filtros y validar que el grid muestra únicamente las tiendas cuya entrega se realizo entre " + horaMinBD + " y " + horaMedBD + " inclusive del día " + PortalCometaMVP4.fechaFormateada + ", con perfil " + perfil);
		boolean resultStep8Horas = rutaPage.capturaHorasInicialFinalLR(horaMinBD, horaMedBD, true);
		assertTrue(resultStep8Horas, "No se pudieron capturar Hora inicial " + horaMinBD + " y Hora final " + horaMedBD + " para el rango intermedio");
		testCase.addBoldTextEvidenceCurrentStep("[PASO 8] Filtros aplicados | Ruta: " + rutaDinamicaEscenario + " | Fecha: " + PortalCometaMVP4.fechaFormateada + " | Rango de horas: " + horaMinBD + " - " + horaMedBD + " (rango intermedio; solo tiendas dentro de este rango deben aparecer)");
		System.out.println("[PASO 8] Rango horas aplicado en portal: " + horaMinBD + " - " + horaMedBD + " (rango intermedio)");
		boolean resultStep8Rango = rutaPage.rutaPropuestaValidaTotalTiendasGridContraBDPorRangoHoras(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, horaMinBD, horaMedBD, true);
		assertTrue(resultStep8Rango, "El total de tiendas del grid con rango " + horaMinBD + " - " + horaMedBD + " no coincide con el total esperado en BD");
		boolean resultStep8TiendasDentro = rutaPage.rutaPropuestaValidaTiendasDentroDeRangoVisiblesEnGrid(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, horaMinBD, horaMedBD, true);
		assertTrue(resultStep8TiendasDentro, "El grid no muestra todas las tiendas cuya entrega esta dentro del rango " + horaMinBD + " - " + horaMedBD);

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Ejecutar en la base de datos COMETA la consulta SELECT COUNT(DISTINCT id_retek) FROM xxcm_historico_correos WHERE ruta_propuesta = '" + rutaDinamicaEscenario + "' AND created_at BETWEEN el rango de fecha/hora capturado AND origen = 'cedis', y validar que el resultado coincide con las tiendas mostradas en el grid del paso anterior, con perfil " + perfil);
		boolean resultStep9Dentro = rutaPage.rutaPropuestaValidaTiendasDentroDeRangoVisiblesEnGrid(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, horaMinBD, horaMedBD, true);
		assertTrue(resultStep9Dentro, "Las tiendas devueltas por BD dentro del BETWEEN " + horaMinBD + " - " + horaMedBD + " no coinciden con las tiendas visibles del grid del paso 8");
		boolean resultStep9Fuera = rutaPage.rutaPropuestaValidaTiendasFueraDeRangoNoVisiblesEnGrid(rutaDinamicaEscenario, PortalCometaMVP4.fechaFormateada, horaMinBD, horaMedBD, true);
		assertTrue(resultStep9Fuera, "El grid del paso 8 conserva tienda(s) cuya fecha/hora de entrega queda fuera del BETWEEN " + horaMinBD + " - " + horaMedBD);
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
		return "*1. Credenciales para los perfiles CEDIS y Admin, Ruta Dinámica válida con uno o más registros en xxcm_historico_correos.created_at y origen cedis disponible en BD      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Valida el MTC_FT_025: rango combinado de fecha y hora en el grid de Liquidación de Ruta con perfiles CEDIS y Admin. Antes de ejecutar el escenario obtiene desde BD los registros de xxcm_historico_correos filtrados por Ruta Dinámica, created_at mayor a la fecha base y origen cedis; con esos datos captura fechas y horas, aun cuando exista un solo registro, y valida que el total y las tiendas visibles del grid coincidan con BD para el rango combinado fecha + hora.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_074_ValidacionRangoCombinadoFechaHoraGrid_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
