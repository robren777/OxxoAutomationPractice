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
* DESCRIPCIÓN: Validar si al intentar visualizar el formato de verificación anterior a la fecha 1 Junio 2025  el sistema deberá mostrar al usuario el siguiente mensaje: \"\"No se encontró el formato de verificación en versión digital disponible para consulta\"\"   con CEDIS\r\n"
        		+ "\"\"Las verificaciones deberán contar con una recepción digital registrada por medio de HH para poder ser visualizadas dentro del Portal COMETA.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_324_Validar al intentar visualizar el formato de verificación anterior (Formato viejo) a la fecha 1 Jun 2025, se deberá mostrar  mensaje: "No se encontró el formato de verificación en versión digital disponible para consulta" con CEDIS
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
* @date 20/10/2025
*/

public class ATC_FT_324_FormatoVerificacionViejoFecha2025MensajeCEDIS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_324_FormatoVerificacionViejoFecha2025MensajeCEDIS_test(HashMap<String, String> data) throws Exception {
    	testCase.setProject_Name("Portal Cometa");

        String User = data.get("User");
        String url = data.get("Url");
        String fecha = ("2025-01-10");
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
        addStep("Realizar cualquier filtro de búsqueda [Plaza, Tienda, Selector, Ruta]");
        boolean resultStep5 = pc4.busquedaFiltroPlazaFechaCedisdsinCons(fecha,User,true);
        assertTrue(resultStep5, "No se pudo realizar la busqueda"); 
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar el \"Rango de Fechas\" y Dar clic en el Boton \"Consultar\"");
        boolean resultStep6 = pc4.busquedaFiltroFechaExacta(fecha,true);
        assertTrue(resultStep6, "No se encontaron resultados en esa fecha seleccionada"); 
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que al internar visualizar el formato de verificación anterior (Formato viejo) a la fecha 1 Jun 2025, deberá mostrar mensaje: \"No se encontró el formato de verificación en versión digital disponible para consulta\"");
        boolean resultStep7 = pc4.FormatoVerifGridCedis(true);
        assertTrue(resultStep7, "No se pudo validar el botón formato de verificacion");  
     
      
        
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
        return "\"Validar si al intentar visualizar el formato de verificación anterior a la fecha 1 Junio 2025  el sistema deberá mostrar al usuario el siguiente mensaje: \"\"No se encontró el formato de verificación en versión digital disponible para consulta\"\"   con CEDIS\r\n"
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