package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que el usuario realice una consulta el portal debe de actualizar la tabla y mostrar unicamente lo que corresponde a la busqueda del usuario."
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* ATC_FT_396_ConsultaConResultados
*
*PREREQUISITOS:
*1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial
*2. Validar portal comenta
*3. Validar pantalla "Cambios Fisicos"
*
*INSUMOS: 
 *Contar con información disponible en tabla public.get_cambios_fisicos_directos_pendientes_paginated.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Maximino Ramirez
* @date Enero 2026
*/
public class ATC_FT_396_ConsultaConResultados extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_396_ConsultaConResultados_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		// --- LIMPIEZA DE VARIABLES ---
        pc.reiniciarVariablesVariablesBD();
        u.hardWait(2);
        
        addStep("Precondicion");
        boolean validacion = pc.consultarDatos9ColumnasBD(user);
        assertTrue(validacion, "No se obtuvieron las columnas requeridas");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		* Paso 1 -  Ingresar a la liga del portal
		*/
		addStep("Ingresar a la liga del portal");
		u.get(data.get("Url"));
		u.refresh();
		u.hardWait(1);
		boolean pantallaVisible = pc.loginScreen();
		assertTrue(pantallaVisible,"No se muestra la pantalla de manera correcta");

		/*
		* Paso 2 -  Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		*/
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se logro iniciar sesión de manera correcta");

		/*
		* Paso 3 -  Ingresar al portal Cometa desde access control
		*/
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");

		/*
		* Paso 4 -  Ingresar a DIRECTOS
		*/
		addStep("Ingresar a DIRECTOS");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"Se ingresa a cambios fisicos");

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
		 * Paso 6 - Seleccionar el botón "Cambios físicos pendientes actuales"
		 */
		addStep("Seleccionar el botón \"Cambios físicos pendientes actuales\"");
		validacion = pc.btnCambiosFisicosPendientesActuales(true);
		assertTrue(validacion, "No se pudo seleccionar la opción de manera correcta");

		/*
		 * Paso 7 - Realizar búsqueda por SKU con coincidencias
		 */
		addStep("Realizar búsqueda por SKU con coincidencias");
		validacion = pc.buscarSkuConCoincidencias(user,true);
		assertTrue(validacion, "No se pudieron aplicar los filtros de búsqueda");

		/*
		 * Paso 8 - Seleccionar botón "Consultar"
		 */
		 addStep("Seleccionar botón \"Consultar\"");
		 validacion = pc.opcConsultar(true);
		 assertTrue(validacion, "No se pudo realizar la consulta");

		/*
		 * Paso 9 - Validar visibilidad de columna Costo
		 */
		 addStep("Validar la columna de Costo en el Grid");
		 validacion = pc.columnasGridDevCFP("Costo", true);
		 assertTrue(validacion, "No se encontró la columna 'Costo' en el grid");

		 /*
		  * Paso 10 - Validar formato de columna Fecha de programación 
		  */
		 addStep("Validar formato de fecha en columna Fecha de Programación");
		 validacion = pc.validarFormatoFechaGrid(true);
		 assertTrue(validacion, "El formato de la fecha no es DD/MM/AAAA o no se encontraron datos.");
		 /*
		  * Paso 11 - Validar formato de moneda en columna Costo
		  */
		 addStep("Validar que la columna Costo contenga el símbolo de moneda ($)");
		 validacion = pc.validarFormatoMonedaCosto(true);
		 assertTrue(validacion, "El valor en la columna Costo no tiene el formato de moneda esperado ($)");


	}

	@AfterMethod
	public void afterMethod() {
		if (u != null && u.getDriver() != null) {
			u.getDriver().quit();
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial\n"
				+ "2. Validar portal comenta\n"
				+ "3. Validar pantalla \"Cambios Fisicos\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el usuario realice una consulta el portal debe de actualizar la tabla y mostrar unicamente lo que corresponde a la busqueda del usuario.";
	}

	@Override
	public String setTestDesigner() {
		return"Maximino Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_396_ConsultaConResultados_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}