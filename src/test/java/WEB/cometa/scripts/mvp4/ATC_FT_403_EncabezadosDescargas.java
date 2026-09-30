package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que los registros en el modulo de notifiaciones cuenten con los datos correctos"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* ATC_FT_403_EncabezadosDescargas
*
*PREREQUISITOS:
*1. Credenciales [Admin, Administrativo Operativo, Comercial] validar desde el criterio 
*2. Liga de Portal Cometa 
*
*INSUMOS: 
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Noviembre 2025
*/
public class ATC_FT_403_EncabezadosDescargas extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_403_EncabezadosDescargas_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
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
		u.hardWait(3);
		
		/*
		 * Paso 4 - Se da clic en menú centro de descarga
		 */
		addStep("Se da clic en menú centro de descarga");
		validacion = pc.opcCentroDescargas(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.scCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Validar que los encabezados sean los siguientes:
				Fecha de descarga
				Hora de descarga
				Módulo origen
				Nombre de documento
				Filtros aplicados en la búsqueda
				Estatus
				Descarga
				Eliminar
		 */
		addStep("Validar que los encabezados sean los siguientes:\r\n"
				+ "Fecha de descarga\r\n"
				+ "Hora de descarga\r\n"
				+ "Módulo origen\r\n"
				+ "Nombre de documento\r\n"
				+ "Filtros aplicados en la búsqueda\r\n"
				+ "Estatus\r\n"
				+ "Descarga\r\n"
				+ "Eliminar");
		validacion = pc.encabezadosCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		

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
		return "1. Credenciales [Admin, Administrativo Operativo, Comercial] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que los registros en el modulo de notifiaciones cuenten con los datos correctos";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_403_EncabezadosDescargas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}