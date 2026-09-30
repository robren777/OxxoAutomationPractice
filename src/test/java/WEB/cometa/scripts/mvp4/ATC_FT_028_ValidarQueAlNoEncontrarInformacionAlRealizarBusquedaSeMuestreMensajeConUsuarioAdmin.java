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
* DESCRIPCIÓN: Validar  que al utilizar el usuario admin  y la búsqueda que realice el portal no encuentra información debe de mostrarse el siguiente mensaje
        		Esta devolución no se realizo por medio del proceso de verificación digital
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_028_Validar que al no encontrar informacion al realizar busqueda se muestre mensaje con usuario admin
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
* @date 19/08/2025
*/

public class ATC_FT_028_ValidarQueAlNoEncontrarInformacionAlRealizarBusquedaSeMuestreMensajeConUsuarioAdmin extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_028_ValidarQueAlNoEncontrarInformacionAlRealizarBusquedaSeMuestreMensajeConUsuarioAdmin_test(HashMap<String, String> data) throws Exception {
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
        addStep("Realizar una busqueda con la cual no se encuentre informacion, validar el formato con la liga UX");
        boolean resultStep5 = pc4.busquedaFiltroFechaCedis5(User,true);
        assertTrue(resultStep5, "No se pudo realizar la busqueda");  
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar selección de formato de verificación");
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
        return "1. Credenciales para el perfil Admin, validar el formato con la liga ux 2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "\"Validar  que al utilizar el usuario admin  y la búsqueda que realice el portal no encuentra información debe de mostrarse el siguiente mensaje\r\n"
        		+ " \"\"Esta devolución no se realizo por medio del proceso de verificación digital\"\"\"";
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