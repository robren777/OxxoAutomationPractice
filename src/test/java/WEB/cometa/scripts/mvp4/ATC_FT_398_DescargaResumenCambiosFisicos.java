package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar la Descarga de un resumen de la información filtrada en formato Excel, del modulo cambios fisicos, aplica para todos los usuarios"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* ATC_FT_398_DescargaResumenCambiosFisicos
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
public class ATC_FT_398_DescargaResumenCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_398_DescargaResumenCambiosFisicos_test (HashMap<String, String> data) throws Exception {
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
        
        addStep("Consultar columnas requeridas en BD");
        boolean validacion = pc.consultarDatos9ColumnasBD(user);
        assertTrue(validacion, "No se obtuvieron las columnas requeridas");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/* * Paso 1  - Ingresar a la liga del portal
		*/
		addStep("Ingresar a la liga del portal");
		u.get(data.get("Url"));
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");

		/* * Paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		*/
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesion de manera correcta");

		/*
		* Paso 3 -  Ingresar al portal Cometa desde access control
		*/
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");

		/* * Paso 4  - Seleccionar el botón "Cambios físicos pendientes actuales"
		*/
		addStep("Seleccionar el botón Cambios físicos pendientes actuales");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"Se ingresa a cambios fisicos");

		/* * Paso 5 - Ingresar a la pantalla "Cambios Físicos"
		*/
		addStep("Ingresar a la pantalla Cambios Físicos");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se ingreso a la pantalla Cambion Físicos");
		
		/* * Paso 6 - Seleccionar el botón "Cambios físicos pendientes actuales"
		*/
		addStep("Seleccionar el botón Cambios físicos pendientes actuales");
		validacion = pc.btnCambiosFisicosPendientesActuales(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");

		/* Paso 7 - Seleccionar cualquier filtro + rango de fechas */
		addStep("Realizar búsqueda por SKU con coincidencias");
		validacion = pc.buscarSkuConCoincidencias(user,true);
		assertTrue(validacion, "No se pudieron aplicar los filtros de búsqueda");

		/* Paso 8 - Seleccionar botón "Consultar" */
		addStep("Seleccionar botón Consultar");
		validacion = pc.opcConsultarHabilitada();
		assertTrue(validacion,"El botón se encuentra deshabilitado");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion, "Fallo al hacer clic en Consultar");

		/* Paso 9 - Validar presencia de etiqueta 'Descargar resumen de la información filtrada' */
		addStep("Validar presencia de etiqueta 'Descargar resumen de la información filtrada'");
		validacion = pc.validarEtiquetaDescargaResumen(true);
		assertTrue(validacion, "La etiqueta de descarga no es visible o el texto cambió");

		/* Paso 11 - Validar que muestre un icono de Excel */
		addStep("Validar que muestre un icono de Excel ");
		validacion = pc.iconoExcelVisible();
		assertTrue(validacion, "El icono de Excel no está presente");

		/* Paso 12 - Seleccionar el icono de Excel */
        addStep("Seleccionar el icono de Excel y validar popup");
        pc.clicIconoExcel();
        validacion = pc.validarTextoPopupDescargaYCerrar(true);
        assertTrue(validacion, "Error al validar o cerrar el popup de descarga");

        /* Paso 13 - Validar descarga de archivo Excel - Validar estructura de títulos del Excel */
        addStep("Validar que los títulos del archivo Excel cumplan con el requerimiento de 9 columnas");
        u.hardWait(6); // Tiempo para asegurar descarga
        
        /* Paso 14 - Validar el archivo Excel (las columnas CR Tienda, Nombre de la tienda, Remisión, Fecha de programación, SKU, Descripción, Días transcurridos, Cantidad, Costo)
         * Validar formato de la columna Costo (La columna se muestra con sigo de pesos y dos decimales)*/
        validacion = pc.validarTitulosYFormatoCostoExcel(true);
        assertTrue(validacion, "La estructura de títulos del Excel no coincide con lo solicitado.");
        
        /* Paso Final - Limpieza */
        addStep("Eliminar archivo Excel validado");
        validacion = pc.eliminarReporteDescargado();
        assertTrue(validacion, "No se pudo eliminar el archivo de la carpeta de descargas.");
		
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
		return "Validar la Descarga de un resumen de la información filtrada en formato Excel, del modulo cambios fisicos, aplica para todos los usuarios";
	}

	@Override
	public String setTestDesigner() {
		return"Maximino Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_398_DescargaResumenCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}