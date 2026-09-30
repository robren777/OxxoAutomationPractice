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
* DESCRIPCIÓN: Validar que se muestre icono eliminar, y al dar click, se elimine el registro con estatus terminado
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_443_Validar icono de eliminar registro estatus terminado en un registro de Cambios Fisicos.
*  
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_centro_descargas filtrando por la fecha más reciente con la columna "estatus = SUCCESS"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 13/01/2026
*/

public class ATC_FT_443_EliminarTermCamb extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_443_EliminarTermCamb_test (HashMap<String, String> data) throws Exception {
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
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Se da clic en menú centro de descarga");
		boolean resultStep7 = pc4.ClicBotonCentroDescargaMenu(true);
		assertTrue(resultStep7,"No se pudo dar click en botón descarga");
		/*
         *                                             PASO 11
         ******************************************************************************************/
        addStep("Se visualiza registro con estatus en proceso y se da clic en botón eliminar");
		boolean resultStep9 = pc4.eliminarUnaDescargaTerminada(true);
		assertTrue(resultStep9,"No se obtuvo la última descarga realizada");
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
		return "\"1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \r\n"
				+ "2.Liga de Portal Cometa \r\n"
				+ "3. Contar con un registro de descarga generado (Se generan en modulo Cambios Fisicos)\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se muestre icono eliminar, y al dar click, se elimine el registro con estatus terminado \r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_443_Validar icono de eliminar registro estatus terminado en un registro de Cambios Fisicos.\r\n"
				+ "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
