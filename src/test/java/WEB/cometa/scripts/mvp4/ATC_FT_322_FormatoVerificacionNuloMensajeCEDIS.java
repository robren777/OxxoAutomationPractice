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
* DESCRIPCIÓN: Validar que al intentar visualizar el formato de verificación no contiene los datos completos esto se mandara como nulo el sistema deberá mostrar al usuario el siguiente mensaje: \"\"No se encontró el formato de verificación en versión digital disponible para consulta\"\"  ( Ejemplo campo de caduco, dañado y faltante) \r\n"
        		+ "\r\n"
        		+ "\"\"Las verificaciones deberán contar con una recepción digital registrada por medio de HH para poder ser visualizadas dentro del Portal COMETA.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_322_Validar al intentar visualizar el formato de verificación no tiene los datos completos esto se mandara como nulo,  mostrara mensaje: "No se encontró el formato de verificación en versión digital disponible para consulta" con CEDIS
* 
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = cedis", "estatus = true" y "version_documento = 1"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 17/10/2025
*/

public class ATC_FT_322_FormatoVerificacionNuloMensajeCEDIS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_322_FormatoVerificacionNuloMensajeCEDIS_test(HashMap<String, String> data) throws Exception {
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
       addStep("Dar clic en el Boton \"Consultar\"");
       boolean resultStep5 = pc4.busquedaFiltroFechaCedis5(User,true);
       assertTrue(resultStep5, "No se pudo realizar la busqueda");  
       /*
        *                                             PASO 7
        ******************************************************************************************/
       addStep("Validar que muestre el mensaje: \"No se encontró el formato de verificación en versión digital disponible para consulta” (Ejemplo campo de caduco, dañado y faltante)");
       boolean resultStep19 = pc4.FormatoVerifGridCedis(true);
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
        return "1. Credenciales para el perfil CEDIS  validar desde el criterio  "
        		+ "2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "\"Validar que al intentar visualizar el formato de verificación no contiene los datos completos esto se mandara como nulo el sistema deberá mostrar al usuario el siguiente mensaje: \"\"No se encontró el formato de verificación en versión digital disponible para consulta\"\"  ( Ejemplo campo de caduco, dañado y faltante) \r\n"
        		+ "\r\n"
        		+ "\"\"Las verificaciones deberán contar con una recepción digital registrada por medio de HH para poder ser visualizadas dentro del Portal COMETA.\"\"\"";
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