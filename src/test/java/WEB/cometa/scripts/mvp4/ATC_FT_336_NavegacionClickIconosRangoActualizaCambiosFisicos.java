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
* DESCRIPCIÓN: Validar que cuando el usuario hace clic en los iconos de navegacion los rangos se deben de actualizar automaticamente, \"Cambios Físicos\" Directos
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_336_ Validar que cuando el usuario realice clic en los iconos de navegacion los rangos se deben de actualizar automaticamente, "Cambios Físicos" Directos
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
* @date 03/11/2025
*/

public class ATC_FT_336_NavegacionClickIconosRangoActualizaCambiosFisicos extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_336_NavegacionClickIconosRangoActualizaCambiosFisicos_test(HashMap<String, String> data) throws Exception {
    	testCase.setProject_Name("Portal Cometa");

        String User = data.get("User");
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
        addStep("Realizar el filtro en el campo \"Provedor\", \"Fuente de Abasto\", \"Zona\", \"Region\", \"Plaza\", \"Tienda\"");
        boolean resultStep5 = pc4.FiltrosDatosDirectosDevol("Plaza", true);
        assertTrue(resultStep5,"No se obtuvo ningún filtro Plaza");
        /*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Se visualiza un total de 20 registros al entrar a pantalla de Devoluciones");
        boolean resultStep10 = pc4.GridPaginacionMAXFis(true);
        assertTrue(resultStep10,"No se pudo obtener paginacion");
        /*
         *                                             PASO 11 
         ******************************************************************************************/
        addStep("Validar la información en el grid se visualice ordenada de forma descendente por fecha, es decir, de la más reciente a la más antigua.");
        boolean resultStep19 = pc4.AutoGridDirectosFechasCF(true);
        assertTrue(resultStep19, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 12
         ******************************************************************************************/
        addStep("Visualizar botones para cambiar de pagina de registros: \"|<    ,     <      ,     >      ,   >|\"");
        boolean resultStep11 = pc4.BotonesPaginacionCF(true);
        assertTrue(resultStep11,"No se visualizan los botones");
        /*
         *                                             PASO 13
         ******************************************************************************************/
        addStep("Validar que en la página 1 se visualice: \"1 – 20 of 230\" \"Cambios Físicos\" DIRECTOS");
        boolean resultStep12 = pc4.LabelOfandCountCF(true);
        assertTrue(resultStep12,"No se visualiza la paginacion");
        /*
         *                                             PASO 14
         ******************************************************************************************/
        addStep("Dar clic en boton \">\" para avanzar a siguiente pagina");
        boolean resultStep8 = pc4.clickBotonPaginacionCF("siguiente", true);
        assertTrue(resultStep8,"No se pudo hacer click en boton siguiente");
        /*
         *                                             PASO 15
         ******************************************************************************************/
        addStep("Validar que al hacer clic en los iconos de navegacion los rangos se actualicen automaticamente");
        boolean resultStep13 = pc4.LabelOfandCountCF(true);
        assertTrue(resultStep13,"No se visualiza la paginacion");
     
      
        
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
        return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial], validar desde el criterio   "
        		+ "2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar que cuando el usuario hace clic en los iconos de navegacion los rangos se deben de actualizar automaticamente, \"Cambios Físicos\" Directos";
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