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
* DESCRIPCIÓN: Validar que no se necesite usar filtro fecha para obtener la información de descargas de cada perfil
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_428_Validar que al ingresar a centro de descargas se visualice información sin usar filtro fecha
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
* @date 19/12/2025
*/

public class ATC_FT_428_FiltroFechaVisualizacionInfoDescargas extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_428_FiltroFechaVisualizacionInfoDescargas_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");
        String User = data.get("user");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        addStep("Precondicion");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGrid(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_directos_view");
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
        addStep("Ingresar a Directos");
        boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep4, "No se encontro el elemento Directos");
        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Se da clic en menú centro de descarga");
		boolean resultStep7 = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep7,"No se pudo dar click en botón descarga");
		/*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("No utilizar seleccionar rango de fechas");
		boolean resultStep9 = pc4.validarGridMatTableConInfo(true);
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
		return "1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \r\n"
				+ "2.Liga de Portal Cometa \r\n"
				+ "3. Contar con un registro de descarga generado (Se generan en modulo comprobantes de compra) ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que no se necesite usar filtro fecha para obtener la información de descargas de cada perfil";
	}

	@Override
	public String setTestDesigner() {
		return"José Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_428_FiltroFechaVisualizacionInfoDescargas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
