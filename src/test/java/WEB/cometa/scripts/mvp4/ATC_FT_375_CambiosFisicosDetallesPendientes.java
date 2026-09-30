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
* DESCRIPCIÓN: Validar pantalla \"Detalles de Cambios Físicos Pendientes\" \r\n"
				+ " se muestren correctamente , \r\n"
				+ "aplica para todos los usuarios DIRECTOS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_375_Validar pantalla "Detalles de Cambios Físicos Pendientes", aplica para todos los usuarios Directos
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
* @date 16/01/2026
*/

public class ATC_FT_375_CambiosFisicosDetallesPendientes extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_375_CambiosFisicosDetallesPendientes_test (HashMap<String, String> data) throws Exception {
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
        addStep("Seleccionar Cambios físicos");
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
        addStep("Dar clic en el botón, el sistema deberá redirigir a la pantalla \"Detalle de Cambios Físicos Pendientes\".");
        boolean resultStep7 = pc4.clickCambiosFisicosPendientesPantalla(true);
        assertTrue(resultStep7, "No se pudo ingresar a la pantalla");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar la información en el grid se visualice ordenada de forma descendente por fecha, es decir, de la más reciente a la más antigua.");
        boolean resultStep8 = pc4.AutoGridDirectosFechasCFDetalles(true);
        assertTrue(resultStep8, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Se visualiza un total de 20 registros al entrar a pantalla de CF Detalles");
        boolean resultStep9 = pc4.GridPaginacionMAXDetalles(true);
        assertTrue(resultStep9,"No se pudo obtener paginacion");
        /*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Abrir lista desplegable del filtro \"Registros por pagina\" con clic de raton");
        boolean resultStep10 = pc4.OpcionesPaginacionDispCF(true);
        assertTrue(resultStep10,"No se encontraron las opciones disponibles");
        /*
         *                                             PASO 11
         ******************************************************************************************/
        addStep("Validar cantidad de registros mostrados sea la misma a la seleccionada en el filtro \"Registros por pagina\"");
        boolean resultStep11 = pc4.SelecPaginacionCFDetalle(true);
        assertTrue(resultStep11,"No se pudieron seleccionar los registros");
        /*
         *                                             PASO 12
         ******************************************************************************************/
        addStep("Visualizar botones para cambiar de pagina de registros: \"|<    ,     <      ,     >      ,   >|\"");
        boolean resultStep12 = pc4.BotonesPaginacionCFdetalles(true);
        assertTrue(resultStep12,"No se visualizan los botones");
        /*
         *                                             PASO 13
         ******************************************************************************************/
        addStep("Dar clic en boton \">\" para avanzar a siguiente pagina");
        boolean resultStep13 = pc4.clickBotonPaginacionCFdet("siguiente", true);
        assertTrue(resultStep13,"No se pudo hacer click en boton siguiente");        
        /*
         *                                             PASO 14
         ******************************************************************************************/
        boolean resultStep14 = pc4.clickBotonPaginacionCFdet("anterior", false);
        assertTrue(resultStep14,"No se pudo hacer click en boton siguiente");
        /*
         *                                             PASO 15
         ******************************************************************************************/
        addStep("Se visualizan botones \"|<\" y \"<\" deshabilitados al estar en primera página y no poder retroceder mas");
        boolean resultStep15 = pc4.PaginacionPrimerosDeshabilitadosCFdet(true);
        assertTrue(resultStep15,"No se encuentran botones deshabilitados");
        /*
         *                                             PASO 16
         ******************************************************************************************/
        addStep("Seleccionar boton \">|\" para cambiar a ultima pagina de registros");
        boolean resultStep16 = pc4.clickBotonPaginacionCFdet("ultimo", true);
        assertTrue(resultStep16,"No se pudo hacer click en boton siguiente");
        /*
         *                                             PASO 17
         ******************************************************************************************/
        addStep("Validar se muestren los botones \">\" y \">|\" deshabilitados al estar en la ultima pagina");
        boolean resultStep17 = pc4.PaginacionUltimosDeshabilitadosCFdet(true);
        assertTrue(resultStep17,"No se encuentran botones deshabilitados");
        boolean resultStep175 = pc4.clickBotonPaginacionCFdet("primero", false);
        assertTrue(resultStep175,"No se pudo hacer click en boton siguiente");
        /*
         *                                             PASO 18
         ******************************************************************************************/
        addStep("Validar que se respete las caracteristicas de cada perfil de usuario. ");
        boolean resultStep18 = pc4.validaDatosFuncionCambiosFisicosPendientes(true, User);
        assertTrue(resultStep18,"No se obtuvieron datos en la funcion get_cambios_fisicos_directos_pendientes_paginated");
        /*
         *                                             PASO 19
         ******************************************************************************************/
        addStep("Validar botón \"Consultar\" en un inicio se encuentre habilitado ya que no se tienen filtros obligatorios");
        boolean resultStep19 = pc4.BtnConsultarCFDetEnabled(true);
        assertTrue(resultStep19,"El botón consultar se encuentra deshabilitado");
        /*
         *                                             PASO 20
         ******************************************************************************************/
        addStep("Validar que el sistema muestre el rango de elementos visibles y el total de resultados, utilizando el formato \"X – Y of Z\".of Z\".");
        boolean resultStep20 = pc4.LabelOfandCountCFdet(true);
        assertTrue(resultStep20,"No se visualiza la paginacion");
        /*
         *                                             PASO 21
         ******************************************************************************************/
        addStep("Se visualiza un total de 20 registros al entrar a pantalla de Devoluciones");
        boolean resultStep21 = pc4.GridPaginacionMAXDetalles(true);
        assertTrue(resultStep21,"No se pudo obtener paginacion");
        /*
         *                                             PASO 22
         ******************************************************************************************/
        boolean resultStep22 = pc4.clickBotonPaginacionCFdet("siguiente", false);
        assertTrue(resultStep22,"No se pudo hacer click en boton siguiente");   
        addStep("Validar que en la página 1 se visualice: \"1 – 20 de 230\" En la página 2: \"21 – 40 of 230\"  en Detalle de Cambios Físicos Pendientes");
        boolean resultStep23 = pc4.LabelOfandCountCFdet(true);
        assertTrue(resultStep23,"No se visualiza la paginacionn");
        /*
         *                                             PASO 23
         ******************************************************************************************/
        addStep("Validar al pasar el cursor sobre el ícono de paginación “|< \" muestre un tool tip \"Primera página\" ");
        boolean resultStep24 = pc4.validarTooltipBotonPaginacionTicketCFdet("primero", true);
        assertTrue(resultStep24,"No se pudo obtener tooltip de paginacion");
        /*
         *                                             PASO 23
         ******************************************************************************************/
        addStep("Validar al pasar el cursor sobre el ícono de paginación “< \" muestre un tool tip \"Página anterior\" ");
        boolean resultStep25 = pc4.validarTooltipBotonPaginacionTicketCFdet("anterior", true);
        assertTrue(resultStep25,"No se pudo obtener tooltip de paginacion");
        /*
         *                                             PASO 23
         ******************************************************************************************/
        addStep("Validar al pasar el cursor sobre el ícono de paginación “> \" muestre un tool tip \"Página Siguiente\" ");
        boolean resultStep26 = pc4.validarTooltipBotonPaginacionTicketCFdet("siguiente", true);
        assertTrue(resultStep26,"No se pudo obtener tooltip de paginacion");
        /*
         *                                             PASO 23
         ******************************************************************************************/
        addStep("Validar al pasar el cursor sobre el ícono de paginación “>| \" muestre un tool tip \"Última página\" ");
        boolean resultStep27 = pc4.validarTooltipBotonPaginacionTicketCFdet("ultimo", true);
        assertTrue(resultStep27,"No se pudo obtener tooltip de paginacion");
        
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
		return "1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \r\n"
				+ "2.Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar pantalla \"Detalles de Cambios Físicos Pendientes\" \r\n"
				+ " se muestren correctamente , \r\n"
				+ "aplica para todos los usuarios DIRECTOS";
	}

	@Override
	public String setTestDesigner() {
		return"José Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_375_CambiosFisicosDetallesPendientes_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
