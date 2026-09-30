package EBS.folioDePago2.HU21;


import static org.junit.jupiter.api.Assertions.assertNotNull;

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
 * DESCRIPCION:
 * Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  en el campo UUID"
 * contenga un registro exceda 32,767 caracteres en el campo UUID Relacionado y nos lanze el error en pantalla"
 * tamaño maximo del campo UUID Relacionado excede la cantidad de  caracteres permitidos 
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_011_GenerarArchivoDondeExceda32767CaracteresRelacionadoUUID
 * 
 * PRERREQUISITOS: 
 * Tener previamente un Folio de Pago para que sea posible el procesamiento
 * Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx
 * Tener usuarios para el portal
 * Tener Acceso a la BD en la instancia AVEBQA
 * Tener Acceso al aplicativo de Oracle EBS
 *
 * INSUMOS:
 * Archivo csv con mas de 32767 caracteres
 * 
 * @author ricardor4@hexaware.com
 * @date 10/Marzo/2025
 */
public class ATC_FT_011_GenerarArchivoDondeExceda32767CaracteresRelacionadoUUID extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_011_GenerarArchivoDondeExceda32767CaracteresRelacionadoUUID_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
		String usuario = GlobalVariables.USUARIOPORTALPROVEEDORES;
		String contrasena = GlobalVariables.CONTRASENAPORTALPROVEEDORES;
		String nombreArchivo = data.get("nombreArchivo");
		String folioPago = data.get("folioPago");
		String pathCSV = data.get("pathCSV");
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
		addStep("Ingresar a Access Control: (https://pruebasweb.oxxo.com/AuthFemcoProx)");
		paginaInicial.loginPortalProveedores(usuario, contrasena);
		Assert.assertEquals(usuario,usuario, "El usuario no coincide con el esperado.");
		u.waitForLoadPage();
		// PASO 2
		addStep("Ingresar a Gestion de  Proveedores");
		Assert.assertTrue(paginaInicial.gestionDeProveedores());
		u.waitForLoadPage();
		// PASO 3
		addStep("Ingresar a Menu Finanzas");
		Assert.assertTrue(paginaInicial.finanzas());
		// PASO 4
		addStep("Ingresar a la Carga de Archivo Relacional");
		Assert.assertTrue(paginaInicial.linkAchivoRelacional());
		// PASO 5
		addStep("Adjuntar archivo relacional y presionar el boton  cargar");
		Assert.assertTrue(paginaInicial.subirArchivoRelacional2(nombreArchivo));
		u.hardWait(6);
		// PASO 6
		addStep("Visualizar que el archivo no se haya subido y no haya llegado a la interfaz FEMSA_EO17_MX con el query:"
						+ "select * from apps.xxfc_ap_cargacsv_all where folio_pago='Numero de factura' order by fecha_procesado desc");
		SQLResult consulta0 = EBS.executeQuery(tabla0);
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consulta0);
		assertNotNull(consulta0, "Error: El resultado de la consulta a la base de datos es nulo.");
		// PASO 7
		addStep("Verificar que el archivo no se haya procesado correctamente");
		Assert.assertTrue(paginaInicial.mensajeUUIDExcedente());
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.getDriver().quit();
		
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_GenerarArchivoDondeExceda32767CaracteresRelacionadoUUID_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar una carga de archivo relacional en el portal de proveedores, donde el documento .csv  en el campo UUID"
				+ " contenga un registro exceda 32,767 caracteres en el campo UUID Relacionado y nos lanze el error en pantalla"
				+ " tamano maximo del campo UUID Relacionado excede la cantidad de  caracteres permitidos ";
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
		return "Tener previamente un Folio de Pago para que sea posible el procesamiento\r\n"
				+ "Contar con un archivo CSV que contenga mas de 32767 caracteres\r\n"
				+ "Tener Acceso a https://pruebasweb.oxxo.com/AuthFemcoProx\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener Acceso al aplicativo de Oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}