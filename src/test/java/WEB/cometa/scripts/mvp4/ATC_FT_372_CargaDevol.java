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
* DESCRIPCIÓN: Validar la carga inicial en pantalla de \"Devoluciones\"  sin hacer ningún filtro aplica para todos los usuarios DIRECTOS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_372_ Validar la carga inicial en pantalla de "Devoluciones" aplica para todos los usuarios Directos
*  
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = directos-devolucion" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 13/11/2025
*/

public class ATC_FT_372_CargaDevol extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_372_CargaDevol_test(HashMap<String, String> data) throws Exception {
    	testCase.setProject_Name("Portal Cometa");

        String User = data.get("User");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        
        addStep("Precondicion");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridDirectosDevoluciones(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_directos_devoluciones_view");
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
        addStep("Seleccionar Devoluciones");
        boolean resultStep4_1 = pc4.ClicBotonDevoluciones(true);
        assertTrue(resultStep4_1,"No se encontro el elemento Devolucion");
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Se visualiza un total de 20 registros al entrar a pantalla de Devoluciones");
        boolean resultStep6 = pc4.GridPaginacionMAXDev(true);
        assertTrue(resultStep6,"No se pudo obtener paginacion");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar la información en el grid se visualice ordenada de forma descendente por fecha, es decir, de la más reciente a la más antigua.");
        boolean resultStep19 = pc4.AutoGridDirectosFechas(true);
        assertTrue(resultStep19, "No se pudo realizar la busqueda");
     
      
        
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
        return "1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] validar desde el criterio y el formato con la liga ux  "
        		+ "2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar la carga inicial en pantalla de \"Devoluciones\"  sin hacer ningún filtro aplica para todos los usuarios DIRECTOS";
    }

    @Override
    public String setTestDesigner() {
        return "JosePecina-GTIM";
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