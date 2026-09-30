package EBS.folioDePago2.HU21;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.Sikuli.PatternUtil;
import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.ClaseLogin;
import EBS.util.FolioPagoMVP2.ConstruccionXML;
import EBS.util.FolioPagoMVP2.EBSPaginaInicial;
import EBS.util.FolioPagoMVP2.PortalProveedoresMVP2;
import EBS.util.appletsMethods.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * DESCRIPCION:
 * Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  contenga un registro 
 * relacionado a una factura ya procesada pero con un UUID diferente en el archivo csv y genere un error en pantalla al cargar el archivo relacional
 * y a su vez no muestre el error: 
 * buffer de cadenas de caracteres demasiado pequeño numérico o de valor
 * fin valida_uuid_prc
 * en el concurrente  XXFC-Validaciones Folio de Pago 2.0
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_030_ValidarErrorUUIDEnAlrchivoRelacionalRelacionadoUnaFactura
 * 
 * PRERREQUISITOS: 
 * Generar una carga del archivo xml con el mismo folio mismo importe mismo rfc  misma serie solo diferente UUID
 * Contar con acceso de visualziacion al concurrente
 * XXFC-Validaciones Folio de Pago 2.0
 * Contar con un registro previamente cargado y facturado
 * Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx
 * Tener usuarios para el portal
 * Tener Acceso a la BD en la instancia AVEBQA
 * Tener Acceso al aplicativo de oracle EBS
 *
 * INSUMOS:
 * CSV con mismo folio mismo importe mismo rfc solo diferente UUID
 * 
 * @author ricardor4@hexaware.com
 * @date 17/Marzo/2025
 */
public class ATC_FT_030_ValidarErrorUUIDEnArchivoRelacionalRelacionadoUnaFactura extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_030_ValidarErrorUUIDEnArchivoRelacionalRelacionadoUnaFactura_test(HashMap<String, String> data) throws Exception {

		
		/*
		 * VARIABLES
		 */
		String currentProject = System.getProperty("user.dir");
		String server = data.get("server");
		String usuario = GlobalVariables.USUARIOPORTALPROVEEDORES;
		String contrasena = GlobalVariables.CONTRASENAPORTALPROVEEDORES;
		String folioPago = data.get("foliPago");
		String nombreArchivo =  "CFD" + folioPago + ".csv";
		String pathXml0 =  data.get("path");
		String pathXml = currentProject + "\\"+ pathXml0;
		String urlXml = data.get("url");
		String pathCSV = data.get("pathcsv");
		String validacion = data.get("validacion");
		/*
		 * UTILERIAS
		 */
		
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil uebs = new SeleniumUtil(new IETest(), true);
		MetodosSikuli p = new MetodosSikuli(sk, uebs, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil patutil = new PatternUtil(sk, uebs, pathimg, ListImage, ImgPatter, patterns, Fol);
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		
		
		ClaseLogin inicio = new ClaseLogin(uebs, testCase, server,  GlobalVariables.getOperacionesUsr, GlobalVariables.getOperacionesPwd);
		
		
		EBSPaginaInicial paginaInicialebs = new EBSPaginaInicial(uebs, testCase);
		PortalProveedoresMVP2 paginaInicial = new PortalProveedoresMVP2(u, testCase);
		ConstruccionXML construccionXML = new ConstruccionXML();
		utils.sql.SQLUtil EBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_QAEBS,GlobalVariables.DB_USER_QAEBS, GlobalVariables.DB_PASSWORD_QAEBS);
		String tabla0 =  "select REQUEST_ID, FOLIO_PAGO, IMPORTE_OXXO, MSG_ERROR from apps.xxfc_ap_cargacsv_all where folio_pago= "+"'"+folioPago+"' order by fecha_procesado desc";
	
	
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Generar el archivo de csv que contenga un UUID distinto de el relacionado a una factura anterior");
		construccionXML.sendXmlPath(pathXml, urlXml);
		String xmlContent = new String(Files.readAllBytes(Paths.get(pathXml)), "UTF-8");
		testCase.addCodeEvidenceCurrentStep(xmlContent);
		testCase.addBoldTextEvidenceCurrentStep(nombreArchivo);
		Assert.assertEquals(nombreArchivo, nombreArchivo, "El nombre del archivo no coincide con el esperado.");
		
		// PASO 2
	    addStep("Ingresar a Access Control: (https://pruebasweb.oxxo.com/AuthFemcoProx)");
		paginaInicial.loginPortalProveedores(usuario, contrasena);
	    assertEquals(usuario,usuario, "El usuario no coincide con el esperado.");
		
		// PASO 3
		addStep("Ingresar a Gestion de  Proveedores");
		assertTrue(paginaInicial.gestionDeProveedores());
		u.waitForLoadPage();
				
		// PASO 4
		addStep("Ingresar a Menu Finanzas");
		assertTrue(paginaInicial.finanzas());
		
		// PASO 5
		addStep("Ingresar a la Carga de Archivo Relacional");
		
		assertTrue(paginaInicial.linkAchivoRelacional());
				
		// PASO 6
		addStep("Adjuntar archivo relacional y presionar el boton cargar");
		testCase.addBoldTextEvidenceCurrentStep("tiempo de carga de archivo " + paginaInicial.subirArchivoRelacional(nombreArchivo,pathCSV) +" Segundos");
		u.hardWait(6);
		// PASO 7
		addStep("Verificar mensaje de error");
		paginaInicial.clickActualizarArchivo();
		// PASO 8
		addStep("Verificar que el archivo no se haya proceso correctamente.");
		Assert.assertTrue(paginaInicial.validarMesajeErrorTipoRelacion(nombreArchivo));
		paginaInicialebs.AbrirUltimoArchivoDescargadoExcel();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		patutil.cerrarFuncion();
		u.hardWait(4);
		
		
		String tabla1tiempo = "SELECT REQUEST_ID,FOLIO,FOLIO_PAGO,IMPORTE,FECHA_PROCESADO FROM apps.xxfc_ap_cargacsv_all where folio_Pago = "+"'"+folioPago+"' order by fecha_procesado desc";
		String tabla0tiempo =  "select request_id FROM apps.xxfc_ap_cargacsv_all where folio_Pago = "+"'"+folioPago+"' order by fecha_procesado desc";
		SQLResult consulta0tiempo = EBS.executeQuery(tabla0tiempo);
		String request_idtiempo = consulta0tiempo.getData(0, "REQUEST_ID");
		String tabla3tiempo = "SELECT " +
	               "    fcpt.user_concurrent_program_name AS Concurrente, " +
	               "    to_char(fcr.actual_start_date, 'dd/mm/yy HH24:MI:SS') AS Inicio, " +
	               "    to_char(fcr.actual_completion_date, 'dd/mm/yy HH24:MI:SS') AS Fin, " +
	               "    round(((fcr.actual_completion_date - fcr.actual_start_date) * 24 * 60), 2) AS Minutos, " +
	               "    round((((fcr.actual_completion_date - fcr.actual_start_date) * 24 * 60) / 60), 2) AS Horas, " +
	               "    fcr.completion_text AS Status " +
	               "FROM " +
	               "    apps.fnd_concurrent_requests fcr, " +
	               "    apps.fnd_concurrent_programs_tl fcpt, " +
	               "    apps.fnd_user fu " +
	               "WHERE " +
	               "    fcr.concurrent_program_id = fcpt.concurrent_program_id " +
	               "    AND fcr.requested_by = fu.user_id " +
	               "    AND fcpt.language = 'ESA' " +
	               "    AND request_id = "+"'"+request_idtiempo+"'" + 
	               " ORDER BY request_id DESC";
		
		testCase.addBoldTextEvidenceCurrentStep("Informacion sobre el archivo procesado");
		SQLResult consultapricipal = EBS.executeQuery(tabla1tiempo);
		testCase.addQueryEvidenceCurrentStep(consultapricipal,false);
	
		
		testCase.addBoldTextEvidenceCurrentStep("Informacion de tiempo de concurrente");
		SQLResult consultasecundaria = EBS.executeQuery(tabla3tiempo);
		testCase.addQueryEvidenceCurrentStep(consultasecundaria,false);

		assertNotNull(consultasecundaria, "Error: El resultado de la consulta a la base de datos es nulo.");
		
//		//PASO 9
		addStep("Ingresar a la base de datos  AVEBQA"
				+ "consultar: "
				+ "la tabla"
				+ "apps.xxfc_ap_cargacsv_err "
				+ "en el campo:"
				+ "MSG_ERROR");
		
		
		SQLResult consulta0 = EBS.executeQuery(tabla0);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta0);
		testCase.addBoldTextEvidenceCurrentStep("Se valida que el campo MSG_ERROR contiene:"
				+ "Factura timbrada dos veces (con los UUIDS que con los que se timbro previamente)");
				
		// PASO 10
		addStep("Ingresar a EBS revisar el log de el concurrente: XXFC-Validaciones Folio de Pago 2.0");
		inicio.login();
		paginaInicialebs.clickOnResponsabilidad("MXCADE-APXXXXX-Consultas");
		paginaInicialebs.clickOtroVers();
		uebs.hardWait(10);
		p.accepApp();
		uebs.hardWait(50);
		testCase.addScreenShotCurrentStep(" ");
		patutil.clickEncontrar();
		uebs.hardWait(8);
		p.visualizarRegistro();
		uebs.hardWait(10);
		testCase.addScreenShotCurrentStep(" ");
		Assert.assertNotEquals(patutil.getTextParaErrores(validacion),validacion);
		testCase.addBoldTextEvidenceCurrentStep("Se valida que el log no contiene el error:"
				+ "buffer de cadenas de caracteres demasiado pequeno numerico o de valor"
				+ "fin valida_uuid_prc");
		uebs.getDriver().quit();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_030_ValidarErrorUUIDEnArchivoRelacionalRelacionadoUnaFactura_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  contenga un registro relacionado a una factura ya procesada pero con un UUID diferente en el archivo csv "
				+ "y genere un error en pantalla al cargar el archivo relacional "
				+ "y a su vez no muestre el error: "
				+ "buffer de cadenas de caracteres demasiado pequeno numerico o de valor "
				+ "fin valida_uuid_prc "
				+ "en el concurrente "
				+ "XXFC-Validaciones Folio de Pago 2.0 ";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Generar una carga del archivo xml con el mismo folio mismo importe mismo rfc  misma serie solo diferente UUID\r\n"
				+ "Contar con acceso de visualizacion al concurrente\r\n"
				+ "XXFC-Validaciones Folio de Pago 2.0\r\n"
				+ "Contar con un registro previamente cargado y facturado\r\n"
				+ "Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener Acceso al aplicativo de oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}
