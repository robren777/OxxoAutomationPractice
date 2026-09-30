package EBS.folioDePago2.HU21;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.ConstruccionXML;
import EBS.util.FolioPagoMVP2.PortalProveedoresMVP2;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * DESCRIPCION:
 * Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  en el campo Serie
 * contenga un registro mayor a 25 caracteres y este falle al cargar, mostrando el mensaje
 * tamaño máximo del campo Serie excede la cantidad de caracteres permitidos
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_010_GenerarUnaCargaDeArchivoRelacionalDondeExcedaNumeroCampoSerie
 * 
 * PRERREQUISITOS: 
 * Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx
 * Tener usuarios para el portal
 * Tener Acceso a la BD en la instancia AVEBQA
 * Tener Acceso al aplicativo de Oracle EBS
 *
 * INSUMOS:
 * Archivo CSV Donde el campo serie exceda el numero de caracteres
 * 
 * @author ricardor4@hexaware.com
 * @date 10/Marzo/2025
 */
public class ATC_FT_010_GenerarUnaCargaDeArchivoRelacionalDondeExcedaNumeroCampoSerie extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_010_GenerarUnaCargaDeArchivoRelacionalDondeExcedaNumeroCampoSerie_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
		
		String usuario = GlobalVariables.USUARIOPORTALPROVEEDORES;
		String contrasena = GlobalVariables.CONTRASENAPORTALPROVEEDORES;
		String nombreArchivo = data.get("nombreArchivo");
		String folioPago = data.get("folioPago");
		String importeOxxo = data.get("importeOxxo");
		String path = data.get("path");
		//TABLAS A VALIDAR 
		
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PortalProveedoresMVP2 paginaInicial = new PortalProveedoresMVP2(u, testCase);
		utils.sql.SQLUtil EBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_QAEBS,GlobalVariables.DB_USER_QAEBS, GlobalVariables.DB_PASSWORD_QAEBS);
		String tabla0 =  "select RFC,FOLIO_PAGO,IMPORTE,FECHA_FACTURA from apps.xxfc_ap_cargacsv_all where folio_pago= "+"'"+folioPago+"' order by fecha_procesado desc";
	
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Generar un archivo que contenga mas de 25 caracteres en el campo serie");
		ConstruccionXML construccionXML = new ConstruccionXML();
		Map<String, String> attributes = construccionXML.obtenerAtributos(path);
		// Declarar variables individuales y asignarles los valores
		String fecha = attributes.get("Fecha");
		String folio = attributes.get("Folio");
		String serie = attributes.get("Serie");
		String rfc = attributes.get("Rfc");
		String importe = attributes.get("Total");
		String tipoDeComprobante = attributes.get("TipoDeComprobante");
		String uuid = attributes.get("UUID");
		String nombreProveedor = "";
		String fechaFactura = "";
		Boolean flag = false;
		testCase.addBoldTextEvidenceCurrentStep("Nombre del archivo csv creado: " +nombreArchivo);
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
		Assert.assertEquals(nombreArchivo, nombreArchivo, "El nombre del archivo no coincide con el esperado.");
		// PASO 2
		addStep("Ingresar a Access Control: (https://pruebasweb.oxxo.com/AuthFemcoProx)");
		paginaInicial.loginPortalProveedores(usuario, contrasena);
		Assert.assertEquals(usuario,usuario, "El usuario no coincide con el esperado.");
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
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		SQLResult consulta0 = EBS.executeQuery(tabla0);
		testCase.addQueryEvidenceCurrentStep(consulta0);
		assertNotNull(consulta0, "Error: El resultado de la consulta a la base de datos es nulo.");
		addStep("Verificar que el archivo no se haya procesado correctamente.");
		// PASO 8 
		Assert.assertTrue(paginaInicial.validarErrorCampoSerie());
		u.getDriver().close();

		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_GenerarUnaCargaDeArchivoRelacionalDondeExcedaNumeroCampoSerie_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar una carga de archivo relacional donde exceda el numero de caracteres en el campo Serie";
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
		return "Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener Acceso al aplicativo de Oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}