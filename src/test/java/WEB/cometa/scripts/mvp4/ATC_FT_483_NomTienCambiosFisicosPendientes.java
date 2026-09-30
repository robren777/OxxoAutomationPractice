package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: En el grid de cambios físicos pendientes, una remisión no debe tener 2 fechas diferentes y debe estar ordenado y agrupado por remision y fecha de forma descendente
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_483 Campo Remision fecha unica Cambios Fisicos Pendientes
*
*PREREQUISITOS:
"1. Credenciales de perfiles (Admin Operativo, Oxxo Plaza, Oxxo Os)
2. No utilizar VPN OXXO (Red libre de preferencia"
*
*INSUMOS: 
*Contar con datos en BD de cambios fisicos pendientes xxcm_cambios_fisicos_pendientes
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_483_NomTienCambiosFisicosPendientes extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_483_NomTienCambiosFisicosPendientes_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
        boolean validacion = pc.validaDatosFuncionCambiosFisicosPendientes(true, user);
        assertTrue(validacion,"No se obtuvieron datos en la funcion get_cambios_fisicos_directos_pendientes_paginated");
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
		 * Paso 5 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6 - Ingresar al boton cambios físicos pendientes
		 */
		addStep("Ingresar al boton cambios físicos pendientes");
		validacion = pc.btnCambiosFisicosPendientesActuales(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		
		/*
		 * Paso 7 - Validar que las fechas esten ordenadas de forma descendente
		 */
        addStep("Validar que las fechas esten ordenadas de forma descendente");
        validacion = pc.AutoGridDirectosFechasCFDetalles(true);
        assertTrue(validacion, "Las fechas del grid no estan ordenadas correctamente");
        
        /*
		 * Paso 8 - Validar que cada una de las remisiones existentes en el grid no tengan fechas distintas y que esten agrupadas por remisión
		 */
        addStep("Validar que cada una de las remisiones existentes en el grid no tengan fechas distintas y que esten agrupadas por remisión");
        validacion = pc.validarRemisionesAgrupadasYConUnaFecha(true);
        assertTrue(validacion, "No se muestran las remisiones de manera correcta");


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
		return "\"1. Credenciales de perfiles (Admin Operativo, Oxxo Plaza, Oxxo Os)\r\n"
				+ "2. No utilizar VPN OXXO (Red libre de preferencia\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "En el grid de cambios físicos pendientes, una remisión no debe tener 2 fechas diferentes y debe estar ordenado y agrupado por remision y fecha de forma descendente\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_483_NomTienCambiosFisicosPendientes_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}