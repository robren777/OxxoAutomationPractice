package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.CometaNuevaIdentidadVisualPage;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.cambiosFisicosPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar columnas nuevas (orden, valor y formato) en Excel INDIVIDUAL de Cambios
 * Fisicos vs vista preliminar para el perfil OXXO_OS (HU 308162).
 * Valida: CA01/CA01.1/CA01.2/CA01.4 (Observaciones Tienda), CA02/CA02.1/CA02.2/CA02.4 (Observaciones
 * Proveedor), CA03/CA03.1 (Perfil Autorizador='Proveedor'), CA04/CA04.1/CA04.2 (Codigo de activacion),
 * CA05/CA05.1/CA05.2 (ID Dispositivo) y CA06 (formato Arial).
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_262 - Verificar columnas nuevas (orden, valor y formato) en Excel INDIVIDUAL de
 *              Cambios Fisicos vs vista preliminar - perfil OXXO_OS
 *
 * PREREQUISITOS:
 * 1. HU 308162 desplegada en QA.
 * 2. Comprobantes de CF disponibles con Observaciones_Personal_tienda, Observaciones_Proveedor,
 *    Codigo_Activacion y numero_serie_celular capturados.
 * 3. Usuario OXXO_OS activo (3333333).
 * 4. Liga de Portal Cometa.
 *
 * @author Michelle Ceja
 * @date 2026-08-04
 */
public class ATC_FT_262_VerificarColNuevasExcelIndividualCFvsVistaPreliminarOXXOOS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_262_VerificarColNuevasExcelIndividualCFvsVistaPreliminarOXXOOS_test(
			HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		CometaNuevaIdentidadVisualPage identidadPage = new CometaNuevaIdentidadVisualPage(u, testCase);
		cambiosFisicosPage cfPage = new cambiosFisicosPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "OXXO_OS";

		/* PRECONDICION *************************************************************/
		addStep("Precondicion: obtener credenciales del perfil OXXO_OS y validar usuario configurado");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		assertTrue("3333333".equals(user),
				"El data provider no corresponde al perfil OXXO_OS (usuario esperado: 3333333)");
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null && !password.trim().isEmpty(),
				"No se encontro password configurada para el usuario: " + user);

		/* PASO 1-4 (Login) *********************************************************/
		addStep("Ingresar la URL del portal COMETA e iniciar sesion con perfil OXXO_OS");
		assertTrue(pc4.IngresarURL(url, true),
				"CA01 fallido - Paso 1: No se muestra la pantalla de inicio de sesion del portal COMETA");
		testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de inicio de sesion del portal COMETA.");
		pc4.PrivatePortal();
		assertTrue(pc4.IniciarSesionNoToken(user, password, true),
				"CA01 fallido - Paso 2-4: El sistema no autentica correctamente al perfil OXXO_OS");
		testCase.addTextEvidenceCurrentStep("El sistema autentica correctamente y redirige a Access Control.");

		/* PASO 5 *******************************************************************/
		addStep("Dar clic en la opcion Portal COMETA dentro de la pantalla de seleccion de portales");
		assertTrue(pc4.seleccionarPortalCometaQAConRefresh(true),
				"CA01 fallido - Paso 5: No fue posible seleccionar Portal COMETA en Access Control");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones disponibles.");

		/* PASO 6 *******************************************************************/
		addStep("Dar clic en la tarjeta Directos en la pantalla principal del portal Cometa");
		assertTrue(pc4.clicBotonCometaDirectos(true),
				"CA01 fallido - Paso 6: No se despliega el menu del modulo Directos");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla del modulo Directos con sus opciones disponibles.");

		/* PASO 7 *******************************************************************/
		addStep("Dar clic en la tarjeta Cambios Fisicos dentro del modulo Directos");
		assertTrue(pc4.ClicBotonCF(true),
				"CA01 fallido - Paso 7: No se visualiza la pantalla Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Cambios Fisicos con la seccion Filtros de Busqueda disponible.");

		/* PASO 8-9 *****************************************************************/
		addStep("Seleccionar un rango de fecha valido y dar clic en el boton Consultar");
		assertTrue(identidadPage.busquedaFiltroFechaCambiosFisicosDirectos(user, true),
				"CA01 fallido - Paso 8-9: No se obtuvo resultado de la consulta en Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep(
				"El grid de resultados muestra los comprobantes de cambios fisicos del rango filtrado.");

		/* PASO 10 ******************************************************************/
		addStep("En el grid de resultados, localizar el primer registro visible y registrar su Folio como FOLIO_REF");
		assertTrue(pc4.ValidaBDExistanDatosFiltrosGridCambiosFisicos(true, user),
				"CA01 fallido - Paso 10: No se visualizan registros en el grid de Cambios Fisicos para el perfil " + user);
		testCase.addTextEvidenceCurrentStep(
				"El primer registro del grid queda identificado con su Folio (FOLIO_REF) visible.");

		/* PASO 11 ******************************************************************/
		addStep("Dar clic en el registro para abrir su vista preliminar");
		assertTrue(cfPage.abrirPrimerRegistroGridCF(true),
				"CA01 fallido - Paso 11: No se abrio la vista preliminar del comprobante de Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep(
				"Se abre la vista preliminar del comprobante de cambios fisicos del registro seleccionado.");

		/* PASO 12 ******************************************************************/
		addStep("En la vista preliminar, localizar y registrar el valor mostrado en el campo 'Observaciones Tienda' (VALOR_OBS_TIENDA)");
		String valorObsTienda = cfPage.leerObservacionesTiendaDeVistaPreliminar(true);
		testCase.addTextEvidenceCurrentStep(
				"La vista preliminar muestra el valor VALOR_OBS_TIENDA en el campo 'Observaciones Tienda' (CA01.4). " +
				"Valor capturado: [" + valorObsTienda + "]");

		/* PASO 13 ******************************************************************/
		addStep("En la vista preliminar, localizar y registrar el valor mostrado en el campo 'Observaciones Proveedor' (VALOR_OBS_PROVEEDOR)");
		String valorObsProveedor = cfPage.leerObservacionesProveedorDeVistaPreliminar(true);
		testCase.addTextEvidenceCurrentStep(
				"La vista preliminar muestra el valor VALOR_OBS_PROVEEDOR en el campo 'Observaciones Proveedor' (CA02.4). " +
				"Valor capturado: [" + valorObsProveedor + "]");

		/* PASO 14 ******************************************************************/
		addStep("Dar clic en el icono de descarga Excel dentro de la vista preliminar del comprobante FOLIO_REF");
		assertTrue(cfPage.descargarExcelIndividualVistaPreliminarCF(true),
				"CA01 fallido - Paso 14: No fue posible dar clic en el icono de descarga Excel de la vista preliminar");
		testCase.addTextEvidenceCurrentStep(
				"Se muestra el modal de descarga y al finalizar el archivo Excel individual queda disponible.");

		/* PASO 15 ******************************************************************/
		addStep("Abrir el archivo Excel individual descargado correspondiente a FOLIO_REF");
		java.io.File archivoExcel = cfPage.esperarArchivoExcelIndividualCF();
		assertTrue(archivoExcel != null && archivoExcel.exists(),
				"CA01 fallido - Paso 15: No se encontro el archivo Excel individual descargado para el FOLIO_REF");
		testCase.addTextEvidenceCurrentStep(
				"El archivo Excel abre correctamente y muestra el detalle del comprobante: " +
				(archivoExcel != null ? archivoExcel.getName() : ""));

		/* PASOS 16-21: Validar orden, posicion y valores de las 5 columnas nuevas */
		addStep("Verificar columnas nuevas: orden (pasos 16-21), valores y CA03 Perfil Autorizador='Proveedor'");
		boolean resultadoColumnas = cfPage.validarColumnas5NuevasExcelIndividualCF(
				valorObsTienda, valorObsProveedor, true);
		assertTrue(resultadoColumnas,
				"CA01/CA02/CA03/CA04/CA05 fallido - Las 5 columnas nuevas no cumplen el orden o valor esperado en el Excel individual. " +
				"Observaciones Tienda despues de Precio Total, Observaciones Proveedor despues de Observaciones Tienda, " +
				"Perfil Autorizador con valor 'Proveedor', Codigo de activacion e ID Dispositivo en el orden correcto.");
		testCase.addTextEvidenceCurrentStep(
				"Las 5 columnas nuevas (Observaciones Tienda, Observaciones Proveedor, Perfil Autorizador, " +
				"Codigo de activacion, ID Dispositivo) se encuentran en la posicion correcta con los valores esperados.");

		/* PASO 19: Verificar valor constante Perfil Autorizador en 2+ filas adicionales */
		addStep("Verificar el valor de la columna 'Perfil Autorizador' en al menos 2 filas adicionales del mismo Excel");
		boolean resultadoPerfilConstante = cfPage.validarValorConstantePerfilAutorizadorExcelCF(true);
		assertTrue(resultadoPerfilConstante,
				"CA03.1 fallido - El valor de la columna 'Perfil Autorizador' no es 'Proveedor' en todas las filas del Excel individual.");
		testCase.addTextEvidenceCurrentStep(
				"El valor de la columna 'Perfil Autorizador' es 'Proveedor' en todas las filas verificadas (CA03.1).");

		/* PASO 22: Verificar formato de encabezados (CA06) */
		addStep("Verificar formato de encabezados de las 5 columnas nuevas: Arial 14, negrita, texto blanco, fondo azul");
		boolean resultadoFormatoEncabezados = cfPage.validarFormatoEncabezados5ColumnasExcelCF(true);
		assertTrue(resultadoFormatoEncabezados,
				"CA06 fallido - Paso 22: El formato de los encabezados de las 5 columnas nuevas no es Arial 14 negrita " +
				"con texto blanco y fondo azul en el Excel individual.");
		testCase.addTextEvidenceCurrentStep(
				"CA06 validado: los encabezados de las 5 columnas nuevas tienen fuente Arial tamano 14, " +
				"negrita activada, color de texto blanco y fondo azul.");

		/* PASO 23: Verificar formato de datos (CA06) */
		addStep("Verificar formato de datos de las 5 columnas nuevas: Arial 12, color de texto negro");
		boolean resultadoFormatoDatos = cfPage.validarFormatoDatos5ColumnasExcelCF(true);
		assertTrue(resultadoFormatoDatos,
				"CA06 fallido - Paso 23: El formato de las celdas de datos de las 5 columnas nuevas no es Arial 12 " +
				"con texto negro en el Excel individual.");
		testCase.addTextEvidenceCurrentStep(
				"CA06 validado: las celdas de datos de las 5 columnas nuevas tienen fuente Arial tamano 12 " +
				"y color de texto negro.");
	}

	@AfterMethod
	public void afterMethod() {
		
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return "*1. HU 308162 desplegada en QA.      \r\n"
				+ "*2. Comprobantes de CF disponibles con campos Observaciones_Personal_tienda, "
				+ "Observaciones_Proveedor, Codigo_Activacion y numero_serie_celular capturados.      \r\n"
				+ "*3. Usuario OXXO_OS activo en QA (3333333).      \r\n"
				+ "*4. Liga de Portal Cometa.";
	}

	@Override
	public String setTestDescription() {
		return "Verificar columnas nuevas (orden, valor y formato) en Excel INDIVIDUAL de Cambios Fisicos "
				+ "vs vista preliminar para el perfil OXXO_OS (HU 308162).";
	}

	@Override
	public String setTestDesigner() {
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_262_VerificarColNuevasExcelIndividualCFvsVistaPreliminarOXXOOS_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
