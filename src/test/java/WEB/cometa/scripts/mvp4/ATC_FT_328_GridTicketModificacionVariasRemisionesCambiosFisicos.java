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
* DESCRIPCIÓN: Validar en el grid la información del registro de ticket de MODIFICACIÓN  (puede tener n cantidad de remisiones), aplica para todos los usuarios, en el modulo Cambios Físicos
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_328_Validar en el grid la información del registro de ticket de MODIFICACIÓN  (puede tener n cantidad de remisiones), aplica para todos los usuarios, en el modulo Cambios Físicos 
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
* @date 22/10/2025
*/

public class ATC_FT_328_GridTicketModificacionVariasRemisionesCambiosFisicos extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_328_GridTicketModificacionVariasRemisionesCambiosFisicos_test(HashMap<String, String> data) throws Exception {
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
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar un \"Provedor\", \"Fuente de Abasto\", \"Zona\", \"Region\", \"Plaza\", \"Tienda\"");
        boolean resultStep6 = pc4.SelecPlazaRegion(true);
        assertTrue(resultStep6,"No se obtuvieron registros");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Realizar el filtro de \"Rango de Fecha\"");
        boolean resultStep7 = pc4.SelecFechaConFiltrosDropdownCambiosFisicos(User,true);
        assertTrue(resultStep7,"no se pudo seleccionar fecha");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Dar clic en el Boton Consultar y Validar que en el grid se muestre los registros filtrados");
        boolean resultStep9 = pc4.consultarComprobantes(true);
        assertTrue(resultStep9,"No se obtuvieron registros");
     
      
        
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
        return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] validar desde el criterio y liga UX  "
        		+ "2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar en el grid la información del registro de ticket de MODIFICACIÓN  (puede tener n cantidad de remisiones), aplica para todos los usuarios, en el modulo Cambios Físicos ";
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