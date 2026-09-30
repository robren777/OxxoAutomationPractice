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
* DESCRIPCIÓN: Validar que en la parte inferior de la pantalla del ticket esten visibles los íconos de navegación, \"Devoluciones\" DIRECTOS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_205_Validar que en la parte inferior de la pantalla del ticket esten visibles los íconos de navegación, en el modulo "Devoluciones" DIRECTOS
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
* @date 08/10/2025
*/

public class ATC_FT_205_TicketVisiblesIconosDeNavegacion extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_205_TicketVisiblesIconosDeNavegacion_test(HashMap<String, String> data) throws Exception {
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
        assertTrue(resultStep4_1,"No se encontro el elemento Devoluciones");
        /*
         *                                         PASO 10
         ******************************************************************************************/
        addStep("Validar que al hacer clic en una fila del grid se abre el ticket de Devoluciones");
        boolean r12 = pc4.abrirTicketDesdeGridYValidarVista(true); 
        assertTrue(r12, "No se redirigió correctamente al ticket de Devoluciones");
        /*
         *                                         PASO 11
         ******************************************************************************************/
        addStep("Validar que el ticket mostrado corresponda exactamente al seleccionado");
        boolean rA = pc4.validarTicketCoincideConSeleccion(true);
        assertTrue(rA, "El ticket abierto no corresponde al folio seleccionado en el grid");
        /*
         *                                         PASO 12
         ******************************************************************************************/
        addStep("Validar que la pantalla muestre correctamente los datos de la devolución");
        boolean rB = pc4.validarDatosDeDevolucion(true);
        assertTrue(rB, "Los datos de la vista detalles no coinciden con los del grid/filtros");
        /*
         *                                         PASO 13
         ******************************************************************************************/
        addStep("Validar que cuando pase el cursor sobre el ícono de paginación “|< \" muestre un tool tip \"Primera página\"");
        boolean resultStep8 = pc4.clickBotonPaginacionTicket("ultimo", false);
        assertTrue(resultStep8,"No se pudo hacer click en boton ultimo");
        boolean resultStep10 = pc4.validarTooltipBotonPaginacionTicket("primero", true);
        assertTrue(resultStep10,"No se pudo hacer click en boton primero");
        /*
         *                                         PASO 14
         ******************************************************************************************/
        addStep("Validar < , que al pasar el cursor por el icono debe de aparecer un tool tip que diga: \"Página anterior\"");
        boolean resultStep11 = pc4.validarTooltipBotonPaginacionTicket("anterior", true);
        assertTrue(resultStep11,"No se pudo hacer click en boton primero");
        /*
         *                                         PASO 15
         ******************************************************************************************/
        addStep("Validar > , que al pasar el cursor por el icono debe de aparecer un tool tip que diga: \"Página siguiente\"");
        boolean resultStep12 = pc4.clickBotonPaginacionTicket("primero", false);
        assertTrue(resultStep12,"No se pudo hacer click en boton ultimo");
        boolean resultStep13 = pc4.validarTooltipBotonPaginacionTicket("siguiente", true);
        assertTrue(resultStep13,"No se pudo hacer click en boton primero");
        /*
         *                                         PASO 16
         ******************************************************************************************/
        addStep("Validar >|, que al pasar el cursor por el icono debe de aparecer un tool tip que diga: \"Última página\"");
        boolean resultStep14 = pc4.validarTooltipBotonPaginacionTicket("ultimo", true);
        assertTrue(resultStep14,"No se pudo hacer click en boton primero");
        
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
        return "Validar que en la parte inferior de la pantalla del ticket esten visibles los íconos de navegación, \"Devoluciones\" DIRECTOS";
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