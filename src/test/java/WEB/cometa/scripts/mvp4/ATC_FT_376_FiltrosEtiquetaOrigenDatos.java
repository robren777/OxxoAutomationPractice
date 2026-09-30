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
* DESCRIPCIÓN: Validar que todas las búsquedas, visualizaciones y descargas deben de tomar en cuenta el identificador único RETEK_ID, aplica para todos los usuarios [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] \"Cambios Físicos Pendientes\" . Directos
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_376_Validar Etiqueta y Origen de Datos para Filtros en "Detalles de Cambios Físicos Pendientes" aplica para todos los usuarios Directos
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

public class ATC_FT_376_FiltrosEtiquetaOrigenDatos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_376_FiltrosEtiquetaOrigenDatos_test (HashMap<String, String> data) throws Exception {
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
        addStep("Validar el filtro \"Proveedor\"");
        boolean resultStep8 = pc4.validarFiltroProveedor(true, User);
        assertTrue(resultStep8, "No se pudo validar el filtro proveedor");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar filtro \"Fuente de Abasto\"");
        boolean resultStep9 = pc4.validarFiltroFuenteAbasto(true, User);
        assertTrue(resultStep9, "No se pudo validar el filtro Fuente de Abasto");
        /*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Validar filtro \"Zona\" Proviene de la tabla CNT,  Ejemplo: Peninsular");
        boolean resultStep10 = pc4.validarFiltroZona(true, User);
        assertTrue(resultStep10, "No se pudo validar el filtro zona");
        /*
         *                                             PASO 11
         ******************************************************************************************/
        addStep("Validar filtro \"Regin\" Proviene de la tabla CNT, Ejemplo: Yucatan");
        boolean resultStep11 = pc4.validarFiltroRegion(true, User);
        assertTrue(resultStep11, "No se pudo validar el filtro Region");
        /*
         *                                             PASO 12
         ******************************************************************************************/
        addStep("Validar filtro \"Plaza\"sera un combinacin de dos datos. El primero proviene de la tabla dim.tienda en el campo \"CR_Plaza\" + de la tabla CNT el campo \"Distrito\". Ejemplo: 32ZJO 10CAN Chichen Itza");
        boolean resultStep12 = pc4.validarFiltroPlaza(true, User);
        assertTrue(resultStep12, "No se pudo validar el filtro Plaza");
        /*
         *                                             PASO 13
         ******************************************************************************************/
        addStep("Validar filtro \"Tienda\" sera un combinacin de dos datos. El primero proviene de la tabla CNT en el campo CR Tienda + de la tabla CNT el campo Nombre Tienda. Ejemplo: 506LL Orin");
        boolean resultStep13 = pc4.validarFiltroTienda(true, User);
        assertTrue(resultStep13, "No se pudo validar el filtro Tienda");
        /*
         *                                             PASO 14
         ******************************************************************************************/
        addStep("Validar el uso de la opcin multiselect el filtro debe poblarse con \"Mltiples Seleccionados\"  ( Aplica para todos los filtros)");
        boolean resultStep14 = pc4.multiselectEnTodosLosFiltros(true);
        assertTrue(resultStep14, "No se pudo validar el multiselect en los filtros");
        boolean resultStep15 = pc4.clickLimpiarFiltros(false);
        assertTrue(resultStep15, "No se pudo dar click en limpiar filtros");
        /*
         *                                             PASO 14
         ******************************************************************************************/
        addStep("Validar que si solo se filtra un solo dato se deber de mostrar en el filtro el dato");
        boolean resultStep16 = pc4.validarFiltroTiendaAfectaGrid(true);
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
		return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial], validar desde el criterio   2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que todas las búsquedas, visualizaciones y descargas deben de tomar en cuenta el identificador único RETEK_ID, aplica para todos los usuarios [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] \"Cambios Físicos Pendientes\" . Directos";
	}

	@Override
	public String setTestDesigner() {
		return"José Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_376_FiltrosEtiquetaOrigenDatos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
