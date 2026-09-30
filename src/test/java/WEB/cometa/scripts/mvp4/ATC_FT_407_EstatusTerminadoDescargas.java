package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que al terminar de generar el archivo, se cambie el estatus a \"Terminado\""
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_407_Validar estatus terminado en centro de descargas
*
*PREREQUISITOS:
*1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  
*2. Liga de Portal Cometa 
*3. Contar con un registro de descarga generado (Se generan en modulo comprobantes de compra)
*
*INSUMOS: 
 *Contar con información disponible en tabla public.xxcm_historico_directos_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Noviembre 2025
*/
public class ATC_FT_407_EstatusTerminadoDescargas extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_407_EstatusTerminadoDescargas_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Pre requisto: Crear un registro con estatus Fallido
		 */
		addStep("Precondicion");
		boolean validacion = pc.validarYObtenerFecha(user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
        u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
        pc.logIntPortal(user, password);
		u.hardWait(8);
		pc.opcDirectosPortalCometa(false);
		pc.opcComprobantesCompra(false);
		u.hardWait(5);
		pc.fechasBd(false);
		pc.opcConsultar(false);
		pc.clicExcelResumen(false);
		u.hardWait(2);
		pc.btnEscape();
		u.hardWait(3);
		pc.logoutPC(false);
		u.hardWait(4);
		
		validacion = pc.actualizarEstatusCentroDescargasPorUsuario(user,"PENDING");
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_centro_descargas");
        
        /*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
		u.hardWait(5);
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
		 * Paso 4 - Se da clic en menú centro de descarga
		 */
		addStep("Se da clic en menú centro de descarga");
		validacion = pc.opcCentroDescargas(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.scCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(1,true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		pc.opcConsultar(false);
		
		/*
		 * Paso 6 - Validar que el registro se encuentre en estatus en proceso
		 */
		addStep("Validar que el registro se encuentre en estatus en proceso");
		validacion = pc.valEstatusEnProceso(true);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		
		/*
		 * Paso 7 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(1,true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		pc.opcConsultar(false);
		
		/*
		 * Paso 7 - Validar que cambie estatus de en proceso a terminado
		 */
		validacion = pc.actualizarEstatusCentroDescargasPorUsuario(user,"SUCCESS");
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_centro_descargas");
        pc.opcConsultar(false);
		addStep("Validar que cambie estatus de en proceso a terminado");
		validacion = pc.valEstatusTerminado(true);
		assertTrue(validacion,"No se pudo validar de manera correcta");
				

		

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
		return "1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa \n"
				+ "3. Contar con un registro de descarga generado (Se generan en modulo comprobantes de compra)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al terminar de generar el archivo, se cambie el estatus a \"Terminado\"";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_407_EstatusTerminadoDescargas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}