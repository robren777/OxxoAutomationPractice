package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.accessControlPage;
import WEB.cometa.om.devolucionesEspecialesPage;
import WEB.cometa.om.menuCedisPage;
import WEB.cometa.om.recepcionTransferenciasPage;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Verificar nombre y contenido del ZIP, integridad y nomenclatura de los PDF,
 * y consistencia de formato de fecha en Devoluciones Especiales para perfil ADMIN.
 *
 *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_380 Verificar nombre y contenido del ZIP, integridad y nomenclatura de los PDF y formato de fecha consistente - perfil ADMIN
 *
 *PREREQUISITOS:
 *1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA. Grid con registros tras ejecutar consulta en Devoluciones Especiales.
 *2. Carpeta de descargas del equipo accesible y herramienta de descompresion disponible.
 *3.Liga de Portal Cometa
 *
 *INSUMOS:
 *Usuario ADMIN configurado en data provider.
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date Agosto 2026
 */
public class ATC_FT_380_NombreContenidoZipIntegridadNomenclaturaADMIN extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_380_NombreContenidoZipIntegridadNomenclaturaADMIN_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		accessControlPage accessControl = new accessControlPage(u, testCase, pc4);
		menuCedisPage menu = new menuCedisPage(u, testCase);
		recepcionTransferenciasPage recepcion = new recepcionTransferenciasPage(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "ADMIN";
		String password = pc4.obtenerPasswordPorUsuario(user);

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		boolean resultPrecondicion = accessControl.validarPrecondicionPerfil(url, user, user, perfil, password);
		assertTrue(resultPrecondicion,
				"Precondicion fallida: la URL, el usuario ADMIN o sus credenciales no estan configurados");
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Ingresar la URL " + url + " en el campo de direcciones del navegador");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1,
				"Paso 1 fallido: no se muestra la pantalla de inicio de sesion del portal Access Control");
		testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla de inicio de sesion del portal Access Control.");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Escribir '" + user + "' en el campo Usuario");
		boolean resultStep2 = accessControl.capturarYValidarUsuario(user);
		assertTrue(resultStep2, "Paso 2 fallido: el campo Usuario no muestra el valor esperado del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep("El campo Usuario muestra el valor capturado desde data provider: " + user + ".");

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Escribir la contrasena del perfil ADMIN en el campo Contrasena");
		boolean resultStep3 = accessControl.capturarYValidarPassword(password);
		assertTrue(resultStep3, "Paso 3 fallido: la contrasena no queda capturada y enmascarada en el campo");
		testCase.addTextEvidenceCurrentStep("El campo Contrasena muestra los caracteres enmascarados.");

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Dar clic en el boton Iniciar sesion");
		boolean resultStep4 = accessControl.iniciarSesionYValidarAcceso();
		assertTrue(resultStep4,
				"Paso 4 fallido: las credenciales no permiten iniciar sesion o no se redirige a la seleccion de portales");
		testCase.addTextEvidenceCurrentStep(
				"El sistema valida las credenciales, inicia sesion y redirige a la pantalla de seleccion de portales donde se visualizan las opciones General y Portal COMETA.");

		/*
		 *                                             PASO 5
		 ***************************************************************************/
		addStep("Dar clic en la opcion 'Portal COMETA'");
		boolean resultStep5 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep5,
				"Paso 5 fallido: no se visualiza la pantalla principal del portal Cometa con las opciones del perfil ADMIN");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla principal del portal Cometa con las opciones disponibles para el perfil.");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Dar clic en la opcion Recepcion y devolucion> Devoluciones especiales");
		boolean resultStep6Cedis = pc4.clicBotonCometaCedis(false);
		assertTrue(resultStep6Cedis, "Paso 6 fallido: no fue posible ingresar al modulo CEDIS");
		boolean resultStep6Recepcion = menu.clicRecepcionYDevolucion();
		assertTrue(resultStep6Recepcion,
				"Paso 6 fallido: no fue posible abrir Recepcion y devolucion para continuar hacia Devoluciones Especiales");
		boolean resultStep6CargaRecepcion = recepcion.validaCargaInicialFiltrosRapidosEHistoricos(7);
		assertTrue(resultStep6CargaRecepcion,
				"Paso 6 fallido: la pantalla Recepcion y devolucion no quedo estable para seleccionar Devoluciones Especiales");
		boolean resultStep6 = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep6,
				"Paso 6 fallido: no se visualiza la pantalla Devoluciones Especiales con los filtros esperados");
		testCase.addTextEvidenceCurrentStep(
				"Se visualiza la pantalla Devoluciones Especiales con los filtros de busqueda CEDIS, Plaza, Tienda, Bol y Rango de fechas.");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Seleccionar el rango de fechas en el campo Rango de fechas (maximo 7 dias) iniciando desde fecha con informacion disponible");
		boolean resultStep7 = devEspecialesPage.seleccionarRangoSieteDiasDesdeFechaInicialBdCedis(user, true);
		assertTrue(resultStep7,
				"Paso 7 fallido: no fue posible seleccionar un rango de fechas valido igual o menor a 7 dias");
		testCase.addTextEvidenceCurrentStep("El campo muestra el rango seleccionado y el boton Consultar se habilita.");

		/*
		 *                                             PASO 8
		 ***************************************************************************/
		addStep("Dar clic en el boton Consultar");
		boolean resultStep8 = devEspecialesPage.ejecutarConsultaDevolucionesEspeciales(true);
		assertTrue(resultStep8,
				"Paso 8 fallido: no se pudo ejecutar la consulta o el grid no quedo visible al terminar la carga");
		testCase.addTextEvidenceCurrentStep(
				"Se muestra la alerta Espere un momento y al finalizar se visualiza el grid con columnas correctas.");

		/*
		 *                                             PASO 9
		 ***************************************************************************/
		addStep("Anotar la cantidad total de registros mostrados en el grid de la consulta ejecutada (valor X)");
		boolean resultStep9 = devEspecialesPage.obtenerTotalRegistrosGridDevEspeciales(true);
		assertTrue(resultStep9,
				"Paso 9 fallido: no fue posible obtener la cantidad total de registros del grid para documentar el valor X");
		testCase.addTextEvidenceCurrentStep("El valor X queda documentado para su comparacion en pasos posteriores.");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Dar clic en el icono de PDF de la leyenda 'Descarga MASIVA de comprobantes filtrados'");
		boolean resultStep10Modal = devEspecialesPage.accionarPdfMasivoYValidarModalEspere(false);
		assertTrue(resultStep10Modal,
				"Paso 10 fallido: al accionar el icono PDF no se muestra el modal Espere un momento");
		boolean resultStep10Cierre = devEspecialesPage.esperarCierreModalEspereUnMomento(true);
		assertTrue(resultStep10Cierre,
				"Paso 10 fallido: el modal Espere un momento no cierra automaticamente despues de iniciar la descarga");
		testCase.addTextEvidenceCurrentStep(
				"El sistema muestra el modal Espere un momento y posteriormente lo cierra iniciando la descarga automatica del ZIP.");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar el nombre del archivo ZIP descargado en la carpeta de descargas del equipo");
		boolean resultStep11 = devEspecialesPage.validarDescargaZipDevEspeciales(false);
		assertTrue(resultStep11,
				"CA02 fallido: el nombre del ZIP descargado no cumple con la nomenclatura esperada para la descarga masiva");
		boolean resultStep11EvidenciaZip = devEspecialesPage.evidenciarZipDescargadoEnExplorador(true);
		assertTrue(resultStep11EvidenciaZip,
				"Paso 11 fallido: no fue posible evidenciar en Explorador el ZIP descargado en Descargas");
		devEspecialesPage.restaurarFocoPortalWeb();
		testCase.addTextEvidenceCurrentStep(
				"Se valida la nomenclatura del ZIP descargado con su prefijo y formato de fechas conforme a la regla de descarga masiva y se evidencia en Explorador de archivos.");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Descomprimir el archivo ZIP descargado en una carpeta local");
		boolean resultStep12 = pc4.extraerZipSimple(true);
		assertTrue(resultStep12,
				"Paso 12 fallido: no fue posible descomprimir el ZIP descargado o no se encontraron archivos extraidos");
		boolean resultStep12GridVisible = devEspecialesPage.validarGridCargaInicialDevolucionesEspeciales(true);
		assertTrue(resultStep12GridVisible,
				"Paso 12 fallido: no se lograron visualizar registros del grid tras hacer scroll para evidencia");
		testCase.addTextEvidenceCurrentStep(
				"El archivo ZIP se descomprime correctamente y se visualizan los documentos PDF contenidos. Se realiza scroll para evidenciar registros visibles en el grid.");

		/*
		 *                                             PASO 13
		 ***************************************************************************/
		addStep("Contar la cantidad de archivos PDF contenidos dentro del ZIP descomprimido");
		int totalRegistrosGrid = PortalCometaMVP4.totalRegistrosP;
		boolean resultStep13 = devEspecialesPage.validarCantidadPdfExtraidosVsTotalGrid(totalRegistrosGrid, true);
		assertTrue(resultStep13,
				"CA04 fallido: la cantidad de PDF dentro del ZIP no coincide con el total de registros del grid (valor X)");
		boolean resultStep13EvidenciaExtraidos = devEspecialesPage.evidenciarPdfExtraidoEnExplorador(true);
		assertTrue(resultStep13EvidenciaExtraidos,
				"Paso 13 fallido: no fue posible evidenciar en Explorador los PDF de la carpeta descomprimida");
		devEspecialesPage.restaurarFocoPortalWeb();
		testCase.addTextEvidenceCurrentStep(
				"La cantidad de PDF dentro del ZIP coincide con el total de registros mostrados en el grid.");

		/*
		 *                                             PASO 14
		 ***************************************************************************/
		addStep("Abrir uno de los archivos PDF contenidos en el ZIP y localizar los campos 'Bol' y 'Fecha' del comprobante");
		boolean resultStep14 = devEspecialesPage.validarCamposBolYFechaEnPrimerPdfExtraido(false);
		assertTrue(resultStep14,
				"Paso 14 fallido: no fue posible abrir un PDF extraido o no se localizaron los campos Bol y Fecha en su contenido");
		testCase.addTextEvidenceCurrentStep(
				"El PDF extraido se procesa correctamente y contiene los campos Bol y Fecha del comprobante.");

		/*
		 *                                             PASO 15
		 ***************************************************************************/
		addStep("Comparar el nombre del archivo PDF abierto contra el formato Devoluciones_Especiales_BOL_Fecha_Consecutivo.pdf");
		boolean resultStep15 = devEspecialesPage.validarNomenclaturaPdfDevEspecialesExtraidos(false);
		assertTrue(resultStep15,
				"CA03 fallido: la nomenclatura de los PDF extraidos no cumple con la estructura Devoluciones_Especiales_BOL_Fecha[_Consecutivo].pdf");
		testCase.addTextEvidenceCurrentStep(
				"La nomenclatura de los PDF extraidos cumple el formato esperado con BOL, fecha y consecutivo cuando aplica.");

		/*
		 *                                             PASO 16
		 ***************************************************************************/
		addStep("Verificar que no existen dos archivos PDF con el mismo nombre dentro del ZIP descomprimido");
		boolean resultStep16Nombres = devEspecialesPage.validarNomenclaturaPdfDevEspecialesExtraidos(false);
		devEspecialesPage.evidenciarPdfExtraidoEnExplorador(false);
		boolean resultStep16 = resultStep16Nombres;
		assertTrue(resultStep16,
				"CA04 fallido: existen nombres de archivos PDF duplicados o consecutivos no validos en el ZIP extraido");
		testCase.addTextEvidenceCurrentStep(
				"Se valida que todos los PDF tengan nombres unicos dentro del ZIP extraido y se evidencia el archivo en el Explorador.");

		/*
		 *                                             PASO 17
		 ***************************************************************************/
		addStep("Comparar el formato de fecha utilizado en el nombre del ZIP (dd_MM_yyyy) contra el formato de fecha utilizado en el nombre de los archivos PDF");
		boolean resultStep17Zip = devEspecialesPage.validarDescargaZipDevEspeciales(false);
		boolean resultStep17Pdf = devEspecialesPage.validarNomenclaturaPdfDevEspecialesExtraidos(false);
		boolean resultStep17 = resultStep17Zip && resultStep17Pdf;
		assertTrue(resultStep17,
				"CA09 fallido: el formato de fecha de los nombres PDF no se mantiene consistente con la regla esperada dd_MM_yyyy");
		testCase.addTextEvidenceCurrentStep(
				"El formato de fecha del ZIP y de los PDF se valida de forma consistente con la regla dd_MM_yyyy.");

		/*
		 *                                             PASO 18
		 ****************************************************************************************************/
		addStep("Validar que el CR tienda y Folio BOL del comprobante PDF existan en BD para perfil ADMIN");
		boolean resultStep18 =  devEspecialesPage.validarContenidoPdfContraBdDevEspecialesAdmin(false);
		assertTrue(resultStep18,
		        "CA05 fallido: el CR tienda y Folio BOL del PDF no fueron encontrados en BD para perfil ADMIN");
		boolean resultStep18AbrirPdf = devEspecialesPage.abrirPdfExtraidoMasRecienteEnPrimerPlano(true);
		assertTrue(resultStep18AbrirPdf,
				"Paso 18 fallido: no fue posible abrir el PDF extraido en el paso final para evidencia");
		testCase.addTextEvidenceCurrentStep(
		        "Se valida que el CR tienda y Folio BOL del comprobante PDF existan en BD para el perfil ADMIN y se abre el PDF en primer plano para evidencia final.");
	}
@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "*1. Perfil ADMIN (usuario 5555555) disponible en ambiente QA. Grid con registros tras ejecutar consulta en Devoluciones Especiales.\r\n"
				+ "*2. Carpeta de descargas del equipo accesible y herramienta de descompresion disponible.\r\n"
				+ "*3.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Verificar nombre y contenido del ZIP, integridad y nomenclatura de los PDF y consistencia de formato de fecha en Devoluciones Especiales para perfil ADMIN.";
	}

	@Override
	public String setTestDesigner() {
		return "Mauricio Gurrola";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_380_NombreContenidoZipIntegridadNomenclaturaADMIN_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}

