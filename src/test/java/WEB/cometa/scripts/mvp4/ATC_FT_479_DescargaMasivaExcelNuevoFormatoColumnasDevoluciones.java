package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Dentro del documento masivo Excel, validar que se ya no se incluyan las columnas 
 * "Total de Artículos A Devolver", "Total de piezas a devolver", "Valor Total" y "Total Costo"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_479 Descarga Masiva Excel Pantalla de Devoluciones - Nuevo Formato de Columnas
*
*PREREQUISITOS:
1. Credenciales de todos los perfiles
2. Validar portal comenta
3. Validar pantalla "Devoluciones"
*
*INSUMOS: 
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_479_DescargaMasivaExcelNuevoFormatoColumnasDevoluciones extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_479_DescargaMasivaExcelNuevoFormatoColumnasDevoluciones_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
        boolean validacion = pc.ValidaBDExistanDatosFiltrosGridDirectosDevoluciones(true, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_dv");
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
		 * Paso 10 - Abrir el documento Excel recien descargado - Se visualiza el documento Excel y validar que 
		 * ya no existan las columnas "Total de Artículos A Devolver", "Total de piezas a devolver", "Valor Total" y "Total Costo", así como sus datos. 
		 */
		addStep("Abrir el documento Excel recien descargado - Se visualiza el documento Excel y validar que"
				+ "ya no existan las columnas \"Total de Artículos A Devolver\", \"Total de piezas a devolver\", \"Valor Total\" y \"Total Costo\", así como sus datos.");
		validacion = pc.validarColumnaNoExistentes();
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
		return "1. Credenciales de todos los perfiles\r\n"
				+ "2. Validar portal comenta\r\n"
				+ "3. Validar pantalla \"Devoluciones\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Dentro del documento masivo Excel, validar que se ya no se incluyan las columnas "
				+ "\"Total de Artículos A Devolver\", \"Total de piezas a devolver\", \"Valor Total\" y \"Total Costo\"";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_479_DescargaMasivaExcelNuevoFormatoColumnasDevoluciones_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}