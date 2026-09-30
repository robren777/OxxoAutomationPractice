package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Desde una descarga masiva de la información filtrada en formato Excel, validar que los datos esten correctos y 
 * en su formato Español de las siguientes columnas: 
1.- Fecha
2.- Fecha de Captura
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_466 Descarga Masiva Pantalla Cambios Físicos - Validar Formato Fecha En Español en Columnas "Fecha" y "Fecha de Captura" 
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial] 
2. Liga de Portal Cometa
*
*INSUMOS: 
Existen datos en la tabla xxcm_cf

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_466_FormatoFechaEspanolDescargaMasivaCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_466_FormatoFechaEspanolDescargaMasivaCambiosFisicos_test (HashMap<String, String> data) throws Exception {
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
		 * Paso 12 - Abrir el archivo Excel recién descargado y validar el dato de la columna "Nombre de Tienda" 
		 */
		addStep("Abrir el archivo Excel recién descargado");
		validacion = pc.ValidarNombreTiendaExcelVsGrid(true);
		assertTrue(validacion,"No se muestra correctamente");
		boolean r12 = pc.abrirTicketDesdeGridYValidarVista(true);
        assertTrue(r12, "No se redirigió correctamente al ticket de Devoluciones");
		
		/*
		 * Paso 11 - Validar los datos de las columnas "Fecha" y "FECHA ADMVA"
		 */
		addStep("Validar los datos de las columnas \"Fecha\" y \"FECHA ADMVA\"");
		validacion = pc.validarFechaYFechaAdmvaExcelMasivo(true);
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
		return "1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial] \r\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Desde una descarga masiva de la información filtrada en formato Excel, validar que los datos esten correctos y en su formato Español de "
				+ "las siguientes columnas: \r\n"
				+ "1.- Fecha\r\n"
				+ "2.- Fecha de Captura";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_466_FormatoFechaEspanolDescargaMasivaCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}