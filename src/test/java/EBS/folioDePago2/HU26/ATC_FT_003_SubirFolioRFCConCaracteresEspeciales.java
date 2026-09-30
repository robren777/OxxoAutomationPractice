package EBS.folioDePago2.HU26;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.PortalProveedoresMVP2;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * 
 * DESCRIPCION:
 * Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  contenga en el campo RFC un registro con caracteres especiales y este no se procese mostrando el mensaje de error
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_003_SubirFolioRFCConCaracteresEspeciales
 * 
 * PRERREQUISITOS: 
 * Tener acceso a https://portalespublicosqa.oxxo.com/AccessControl/pages/login_form.jsf
 * Tener usuarios para el portal
 * Tener acceso a la BD en la instancia AVEBQA
 * Tener acceso al aplicativo de oracle EBS
 * 
 * INSUMOS:
 * CSV con RFC con caracteres especiales
 * 
 * @author ricardor4@hexaware.com
 * @date 10/Marzo/2025
 */
public class ATC_FT_003_SubirFolioRFCConCaracteresEspeciales extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_SubirFolioRFCConCaracteresEspeciales_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
		String usuario = GlobalVariables.USUARIOPORTALPROVEEDORES;
		String contrasena = GlobalVariables.CONTRASENAPORTALPROVEEDORES;
		String nombreArchivo = data.get("nombreArchivo");
		String folioPago = data.get("folioPago");
		
		//TABLAS A VALIDAR 
		
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PortalProveedoresMVP2 paginaInicial = new PortalProveedoresMVP2(u, testCase);
		utils.sql.SQLUtil EBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_QAEBS,GlobalVariables.DB_USER_QAEBS, GlobalVariables.DB_PASSWORD_QAEBS);
		String tabla0 =  "select RFC,FOLIO_PAGO,IMPORTE,FECHA_FACTURA from apps.xxfc_ap_cargacsv_all where folio_pago= "+"'"+folioPago+"' order by fecha_procesado desc";
		String tabla1 = "select RFC,FOLIO_PAGO_OXXO,IMPORTE from XXFC.XXFC_CFD_RELACION_FACTURAS where folio_pago_oxxo = "+"'"+folioPago+"' order by creation_date desc";
		
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Generar el archivo de csv que contenga un rfc con caracteres especiales");
		testCase.addBoldTextEvidenceCurrentStep("Nombre del archivo csv creado: " +nombreArchivo);
	
		String fecha = data.get("fecha");
		String folio = data.get("folio");
		String serie = data.get("serie");
		String rfc = data.get("rfc");
		String importe = data.get("importe");
		String tipoDeComprobante = data.get("tipoDeComprobante");
		String uuid = data.get("uuid");
		String nombreProveedor = data.get("nombreProveedor");
		String importeOxxo = data.get("importeOxxo");
		Boolean flag = false;
		// Agregar los datos como evidencia
		testCase.addBoldTextEvidenceCurrentStep("Datos para el .csv:");
		testCase.addBoldTextEvidenceCurrentStep("///////////////////////////////////////");
		testCase.addBoldTextEvidenceCurrentStep("RFC: " + rfc);
		testCase.addBoldTextEvidenceCurrentStep("PROVEEDOR: " + nombreProveedor);
		testCase.addBoldTextEvidenceCurrentStep("SERIE: " + serie);
		testCase.addBoldTextEvidenceCurrentStep("FOLIO: " + folio);
		testCase.addBoldTextEvidenceCurrentStep("FOLIO_PAGO_OXXO: " + folioPago);
		testCase.addBoldTextEvidenceCurrentStep("IMPORTE: " + importe);
		testCase.addBoldTextEvidenceCurrentStep("IMPORTE_OXXO: " + importeOxxo);
		testCase.addBoldTextEvidenceCurrentStep("FECHA_FACTURA: " + fecha);
		testCase.addBoldTextEvidenceCurrentStep("VALC_UUID: " + uuid);
		testCase.addBoldTextEvidenceCurrentStep("TIPO_COMPROBANTE: " + tipoDeComprobante);
		testCase.addBoldTextEvidenceCurrentStep("UUIDRELACIONADO: " + "");
		assertEquals(nombreArchivo, nombreArchivo, "El nombre del archivo no coincide con el esperado.");
	
		// PASO 2
		addStep("Ingresar a Access Control: (https://pruebasweb.oxxo.com/AuthFemcoProx)");
		paginaInicial.loginPortalProveedores(usuario, contrasena);
		assertEquals(usuario,usuario, "El usuario no coincide con el esperado.");
		System.out.println(tabla1);
		// PASO 3
		addStep("Ingresar a Gestion de  Proveedores");
		Assert.assertTrue(paginaInicial.gestionDeProveedores());
		u.waitForLoadPage();
		// PASO 4
		addStep("Ingresar a Menu Finanzas");
		Assert.assertTrue(paginaInicial.finanzas());
		// PASO 5
		addStep("Ingresar a la Carga de Archivo Relacional");
		Assert.assertTrue(paginaInicial.linkAchivoRelacional());
		// PASO 6
		addStep("Adjuntar archivo relacional y presionar el boton  cargar");
		Assert.assertTrue(paginaInicial.subirArchivoRelacional2(nombreArchivo));
		u.hardWait(6);
		// PASO 7
		addStep("Visualizar que el archivo no se haya subido y no haya llegado a la interfaz FEMSA_EO17_MX con el query:"
				+ "select * from apps.xxfc_ap_cargacsv_all where folio_pago='Numero de factura' order by fecha_procesado desc");
		SQLResult consulta0 = EBS.executeQuery(tabla0);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta0);
		// PASO 8
		addStep("Verificar que el archivo no se haya procesado correctamente.");
		Assert.assertTrue(paginaInicial.validarErrorRfc());
		u.getDriver().quit();
		
		
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_SubirFolioRFCConCaracteresEspeciales_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  contenga en el campo RFC un registro con caracteres especiales y este no se procese mostrando el mensaje de error";
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
		return "Tener acceso a https://pruebasweb.oxxo.com/AuthFemcoProx\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener acceso al aplicativo de oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}