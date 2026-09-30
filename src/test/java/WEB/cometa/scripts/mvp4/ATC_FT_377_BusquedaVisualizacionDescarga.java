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
* DESCRIPCIÓN: Validar que todas las búsquedas, visualizaciones y descargas \r\n"
				+ "deben de tomar en cuenta el identificador único RETEK_ID, \r\n"
				+ " aplica para todos los usuarios [Admin, OXXO OS, OXXO Plaza, \r\n"
				+ " Administrativo Operativo, Comercial] \"Cambios Físicos Pendientes\" . Directos
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_377_Validar que todas las búsquedas, visualizaciones y descargas debe de tomar en cuenta el identificador único, aplica para todos los usuarios "Cambios Físicos Pendientes" DIRECTOS
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
* @date 20/01/2026
*/

public class ATC_FT_377_BusquedaVisualizacionDescarga extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void TC_FT_377_BusquedaVisualizacionDescarga_test (HashMap<String, String> data) throws Exception {

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
        addStep("Se visualiza un total de 20 registros al entrar a pantalla de Devoluciones");
        boolean resultStep6 = pc4.GridPaginacionMAXFis(true);
        assertTrue(resultStep6,"No se pudo obtener paginacion");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar clic en el botn, el sistema deber redirigir a la pantalla \"Detalle de Cambios Fsicos Pendientes\".");
        boolean resultStep7 = pc4.clickCambiosFisicosPendientesPantalla(true);
        assertTrue(resultStep7, "No se pudo ingresar a la pantalla");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que si solo se filtra un solo dato se deber de mostrar en el filtro el dato");
        boolean resultStep16 = pc4.validarFiltroTiendaAfectaGrid(true);
        assertTrue(resultStep16, "No se pudo validar el filtro vs el grid");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar que las bsquedas de tienda esten ligados a nivel RETEK ID ");
        boolean resultStep8 = pc4.validarDropdownAllIncluyaIdRetekCFPendientes(true);
        assertTrue(resultStep8,"No se pudo obtener ID");


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
		return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza,\r\n"
				+ " Administrativo Operativo, Comercial], validar desde el criterio \r\n"
				+ " 2.Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que todas las búsquedas, visualizaciones y descargas \r\n"
				+ "deben de tomar en cuenta el identificador único RETEK_ID, \r\n"
				+ " aplica para todos los usuarios [Admin, OXXO OS, OXXO Plaza, \r\n"
				+ " Administrativo Operativo, Comercial] \"Cambios Físicos Pendientes\" . Directos";
	}

	@Override
	public String setTestDesigner() {
		return"Jos Pecina";
	}

	@Override
	public String setTestFullName() {
		return "TC_FT_377_BusquedaVisualizacionDescarga_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
