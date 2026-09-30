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
* DESCRIPCIÓN: Visualizar los datos del campo plaza del CNT actual ejemplo Ejemplo: 32ZJO 10CAN Chichen Itza
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_021_Validar el campo que remplaza el CR Plaza actual del CNT CEDIS
* 
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = cedis" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 14/08/2025
*/

public class ATC_FT_021_ValidarElCampoQueRemplazaElCRPlazaActualDelCNTCEDIS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_021_ValidarElCampoQueRemplazaElCRPlazaActualDelCNTCEDIS_test(HashMap<String, String> data) throws Exception {
     	 testCase.setProject_Name("Portal Cometa");

         String User = data.get("User");
         String url = data.get("Url");
         u = new SeleniumUtil(new ChromeTest(), true);
         PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
         addStep("Precondicion");
         boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridCedis(true, User);
         assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
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
         addStep("Ingresar a CEDIS");
         boolean resultStep4 = pc4.clicBotonCometaCedis(true);
         assertTrue(resultStep4, "No se encontro el elemento Cedis");
         /*
          *                                             PASO 5
          ******************************************************************************************/
         addStep("Seleccionar Recepción de Transferencias y Devoluciones");
         boolean resultStep4_1 = pc4.ClicBotonRecepcionCedis(true);
         assertTrue(resultStep4_1,"No se encontro el elemento Recepcion");
         /*
          *                                             PASO 6
          ******************************************************************************************/
         addStep("Posiscionarse en el campo plazas y Visualizar los datos del campo plaza del CNT actual ejemplo Ejemplo: 32ZJO 10CAN Chichen Itza");
         boolean resultStep25 = pc4.busquedaFiltroPlaza(true);
         assertTrue(resultStep25, "No se pudo realizar la busqueda");
 
      

        
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
        return "\"1. Credenciales para el perfil CEDIS,  Validar en el portal cometa\r\n"
        		+ "2. Información de la tabla dim. tienda (Proviene del equipo de IMA) \"";
    }

    @Override
    public String setTestDescription() {
        return "Visualizar los datos del campo plaza del CNT actual ejemplo Ejemplo: 32ZJO 10CAN Chichen Itza";
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