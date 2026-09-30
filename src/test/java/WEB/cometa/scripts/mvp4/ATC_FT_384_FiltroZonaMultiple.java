package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar que permita seleccionar una o mas Zonas\r\n"
				+ "y me devuelva todos los registros ligados a la o a las zonas\r\n"
				+ "Se podrán seleccionar uno o más zonas.\r\n"
				+ " Si no se ha seleccionado uno o mas fuentes de abasto,\r\n"
				+ "se podrá seleccionar uno o más zonas, donde la búsqueda devolvera los registros\r\n"
				+ " que pertenezcan a las zonas seleccionadas
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_384_Validar que permita seleccionar una o mas Zonas y me devuelva todos los registros ligados a la o a las zonas, aplica para todos los usuarios "Cambios Físicos Pendientes" Directos
*  
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = directos-cf" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 23/01/2026
*/

public class ATC_FT_384_FiltroZonaMultiple extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_384_FiltroZonaMultiple_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");

		String User = data.get("user");
	    String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        
        addStep("Precondicion");
        boolean ResultPre = pc4.validaDatosFuncionCambiosFisicosPendientes(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la funcion get_cambios_fisicos_directos_pendientes_paginated");
        String Password = pc4.obtenerPasswordPorUsuario(User);
        assertTrue(Password != null, "No se encontro password configurada para el usuario: " + User);
        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1,"No se pudo ingresar a la URL");       
        //Se quita pantalla de seguridad ventana privada si existe
        pc4.PrivatePortal();     
        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc4.IniciarSesionNoToken(User, Password, true);
        assertTrue(resultStep2, "No se pudo iniciar sesion");        
        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep3, "No es visible el elemento de Cometa");
        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a DIRECTOS");
        boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep4, "No se encontro el elemento Directos");
        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Seleccionar Cambios fsicos");
        boolean resultStep4_1 = pc4.ClicBotonCF(true);
        assertTrue(resultStep4_1,"No se encontro el elemento CF");
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic en el botn, el sistema deber redirigir a la pantalla \"Detalle de Cambios Fsicos Pendientes\".");
        boolean resultStep6 = pc4.clickCambiosFisicosPendientesPantalla(true);
        assertTrue(resultStep6, "No se pudo ingresar a la pantalla");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Dar clic en el Boton Consultar");
        boolean resultStep16 = pc4.validarFiltroZonaAfectaFuncionPaginated(true,User);
        assertTrue(resultStep16, "No se pudo validar el filtro vs el grid");
		

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
		return "1. Credenciales para el perfil DIRECTOS con usuario\r\n"
				+ "[Admin, OXXO OS, OXXO Plaza, Administrativo Operativo,\r\n"
				+ "Comercial], validar desde el criterio   "
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que permita seleccionar una o mas Zonas\r\n"
				+ "y me devuelva todos los registros ligados a la o a las zonas\r\n"
				+ "Se podrán seleccionar uno o más zonas.\r\n"
				+ " Si no se ha seleccionado uno o mas fuentes de abasto,\r\n"
				+ "se podrá seleccionar uno o más zonas, donde la búsqueda devolvera los registros\r\n"
				+ " que pertenezcan a las zonas seleccionadas";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_384_FiltroZonaMultiple_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
