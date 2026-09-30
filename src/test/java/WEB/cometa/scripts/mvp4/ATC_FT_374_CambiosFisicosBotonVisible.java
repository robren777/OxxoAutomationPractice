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
* DESCRIPCIÓN: Validar que en la pantalla de \"Cambios fisicos\" \r\n"
				+ "exista un boton visible con el texto \"Cambios fsicos pendientes actuales\" \r\n"
				+ " para identificar rpidamente su funcion antes de interactuar,\r\n"

				+ " aplica para todos los usuarios DIRECTOS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_374_Validar pantalla de "Cambios físicos" exista un botón visible con el texto "Cambios físicos pendientes actuales", aplica para todos los usuarios Directos
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
* @date 15/01/2026
*/

public class ATC_FT_374_CambiosFisicosBotonVisible extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_374_CambiosFisicosBotonVisible_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");

        String User = data.get("user");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        
        addStep("Precondicion");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridDirectosCambiosfísicos(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_directos_cf_view");
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
        addStep("Validar la información en el grid se visualice ordenada de forma descendente por fecha, es decir, de la más reciente a la más antigua.");
        boolean resultStep7 = pc4.AutoGridDirectosFechasCF(true);
        assertTrue(resultStep7, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Al pasar el cursor sobre el botón, deberá desplegarse un tooltip con el texto: \"Cambios físicos de hoy\"");
        boolean resultStep8 = pc4.validarTooltipCambiosFisicos(true);
        assertTrue(resultStep8, "No se pudo obtener el tooltip");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar que el tooltip se muestre en un tiempo máximo de 0.5 segundos después de colocar el cursor sobre el botón y mantenerse visible mientras el cursor permanezca sobre él");
        boolean resultStep9 = pc4.validarTooltipCambiosFisicosTiempo(true);
        assertTrue(resultStep9, "No se pudo quitar el tooltip");
        /*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Dar clic en el botón, el sistema deberá redirigir a la pantalla \"Detalle de Cambios Físicos Pendientes\".");
        boolean resultStep10 = pc4.clickCambiosFisicosPendientesPantalla(true);
        assertTrue(resultStep10, "No se pudo ingresar a la pantalla");
        
		

	}

	@Override
	public void beforeTest() {
		
	}
    @AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil DIRECTOS, \r\n "
				+ "con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, \r\n"
				+ " Comercial] validar desde el criterio y el formato con la liga ux \r\n"
				+ "  2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Validar que en la pantalla de \"Cambios fisicos\" \r\n"
				+ "exista un boton visible con el texto \"Cambios fsicos pendientes actuales\" \r\n"
				+ " para identificar rpidamente su funcion antes de interactuar,\r\n"

				+ " aplica para todos los usuarios DIRECTOS";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
