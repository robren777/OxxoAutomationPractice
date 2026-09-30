package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Desde una descarga masiva de la información filtrada en formato Excel, validar que los datos de los Precios y Val. 
 * Total sea igual al que se muestra en la vista preliminar del registro
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_465 Descarga Masiva Pantalla Devoluciones - Validar Precios Comparativa desde Archivo Descargado y Vista Preliminar
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO Plaza y OXXO OS] 
2. Liga de Portal Cometa
*
*INSUMOS: 
Existen datos en la tabla xxcm_dv

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_465_DescargaMasivaComparativaPreciosDevoluciones extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_465_DescargaMasivaComparativaPreciosDevoluciones_test (HashMap<String, String> data) throws Exception {
testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.ValidaBDExistanDatosFiltrosGridDirectosDev(true, user);
		validacion = pc.obtenerRegistroDevolucion(false);
        assertTrue(validacion,"No muestra información de manera correcta");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con las con credenciales 
		 */
		addStep("Ingresar a la liga del portal con las con credenciales"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(10);
		validacion = pc.accessControlScreenSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 3 - Ingresar al portal Cometa desde access control
		 */
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 4 - Ingresar a la pantalla "Devoluciones"
		 */
		addStep("Ingresar a la pantalla \"Devoluciones\"");
		pc.opcDirectosPortalCometa(false);
		validacion = pc.selectDevolucionesSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenDevoluciones();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 5 - Seleccionar cualquier filtro + rango de fechas
		 */
		addStep("Seleccionar cualquier filtro + rango de fechas");
		validacion = pc.SelecFecha(user,true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 6 - Seleccionar botón "Consultar"
		 */
		addStep("Seleccionar botón \"Consultar\"");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"El botón se encuentra deshabilitado"); 
		
		/*
		 * Paso 7 - Validar que se muestre una etiqueta con el texto: ""Descargar de manera masiva los comprobantes filtrados"".
		 */
		addStep("Validar que se muestre una etiqueta con el texto: \"\"Descargar de manera masiva los comprobantes filtrados\"\".");
		validacion = pc.etiquetaDescargaMasiva();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 8 - Validar que muestre un ícono de Excel 
		 */
		addStep("Validar que muestre un ícono de Excel ");
		validacion = pc.iconoExcelDescargaMasiva();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 9 - Seleccionar el ícono de Excel
		 */
		addStep("Seleccionar el ícono de Excel");
		validacion = pc.clicExcelDescargaMasiva();
		validacion = pc.imgEspereUnMomento();
		validacion = pc.CerrarPopUpCentroDescargayComputoDEV(false);
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 10 - Validar el nombre del archivo Excel 
		 */
		addStep("Validar el nombre del archivo Excel");
		validacion = pc.validarDescargaComprobanteMasivaExc(true);
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 12 - Abrir el archivo Excel recién descargado y validar el dato de la columna "Nombre de Tienda" 
		 */
		addStep("Abrir el archivo Excel recién descargado");
		validacion = pc.ValidarNombreTiendaExcelVsGrid(true);
		assertTrue(validacion,"No se muestra correctamente");
		boolean r12 = pc.abrirTicketDesdeGridYValidarVista(true);
        assertTrue(r12, "No se redirigió correctamente al ticket de Devoluciones");
		/*
		 * Paso 13 - Visualizar y comparar los datos de los precios en la columna "Precio" que se encuentran tanto en el documento Excel Masivo como en la vista preliminar del registro
		 */
		addStep("Visualizar y comparar los datos de los precios en la columna \"Precio\" que se encuentran tanto en el documento Excel Masivo como en la vista preliminar del registro");
		validacion = pc.validarPrecioYValTotalVistaPreliminarVsExcelMasivo(true);
		assertTrue(validacion,"No se muestra correctamente");



	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	@AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO Plaza y OXXO OS] \r\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Desde una descarga masiva de la información filtrada en formato Excel, validar que los datos de los Precios y Val. "
				+ "Total sea igual al que se muestra en la vista preliminar del registro";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_465_DescargaMasivaComparativaPreciosDevoluciones_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}