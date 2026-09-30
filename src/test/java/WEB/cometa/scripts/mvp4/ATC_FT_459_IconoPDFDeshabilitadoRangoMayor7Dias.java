package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que en la pantalla de Comprobantes de Compra, al seleccionar un rango mayor de 7 días en su respectivo filtro de busqueda, el ícono se muestre de forma deshabilitada y no se pueda descargar los comprobantes en archivo PDF"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_459_Ícono de PDF deshabilitado al seleccionar rango mayor de 7 días en Comprobante de Compra
*
*PREREQUISITOS:
*1. Credenciales con usuario   [Admin, Administrativo Operativo, Comercial] validar desde el criterio  
*2. Liga de Portal Cometa 
*
*INSUMOS: 
 *Contar con información disponible en tabla public.xxcm_historico_directos_cf_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Diciembre 2025
*/
public class ATC_FT_459_IconoPDFDeshabilitadoRangoMayor7Dias extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_459_IconoPDFDeshabilitadoRangoMayor7Dias_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.validarYObtenerFecha(user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_historico_directos_view");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
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
		 * Paso 4 - Ingresar a Directos
		 */
		addStep("Ingresar a Directos");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		
		/*
		 * Paso 5 - Ingresar a Comprobantes de Compra
		 */
		addStep("Ingresar a Comprobantes de Compra");
		validacion = pc.opcComprobantesCompra(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		validacion = pc.comprobantesCompraCampos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6 - Seleccionar un rango de fecha mayor de 7 días 
		 */
		addStep("Seleccionar un rango de fecha mayor de 7 días ");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(9,true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		pc.opcConsultar(false);
		
		/*
		 * Paso 7 - Se muestra el ícono de PDF para descargar de manera deshabilitada y no permita hacer la descarga.
		 */
		addStep("Se muestra el ícono de PDF para descargar de manera deshabilitada y no permita hacer la descarga.");
		validacion = pc.valBtnDescMasivaPDFDeshabilitado();
		assertTrue(validacion,"No se muestra el botón de manera correcta"); 
		
		/*
		 * Paso 8 - Pasar por el cursor el ícono de PDF deshabilitado.
		 * Se visualiza un tooltip que dice: "La descarga en PDF está limitada a un rango máximo de 7 días para garantizar un tiempo óptimo de generación"
		 */
		addStep("Pasar por el cursor el ícono de PDF deshabilitado."
				+ "Se visualiza un tooltip que dice: \"La descarga en PDF está limitada a un rango máximo de 7 días para garantizar un tiempo óptimo de generación\"");
		validacion = pc.valMensajePDFDeshabilitado();
		assertTrue(validacion,"No se muestra el botón de manera correcta");
		

		

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
		return "1. Credenciales con usuario   [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \n"
				+ "2. Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en la pantalla de Comprobantes de Compra, al seleccionar un rango mayor de 7 días en su respectivo filtro de busqueda, el ícono se muestre de forma deshabilitada y no se pueda descargar los comprobantes en archivo PDF";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_459_IconoPDFDeshabilitadoRangoMayor7Dias_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}