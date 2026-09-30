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
* DESCRIPCIÓN: Validar que al hacer clic en el ícono de Excel, se visualice una ventana emergente o mensaje modal con el texto: \"\"Espere un momento\"\".
        		Esta ventana se mantendrá visible hasta que el archivo esté listo para descarga, momento en el cual se cerrará automáticamente
        		el sistema debe generar y descargar automáticamente un archivo en formato en excel
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_207_Validar que al hacer clic en el ícono de Excel, se visualice una ventana emergente o mensaje modal con el texto: "Espere un momento",  en el modulo"Devoluciones" DIRECTOS
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
* @date 25/09/2025
*/

public class ATC_FT_207_MensajeModalConElTextoEspereUnMomento extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_207_MensajeModalConElTextoEspereUnMomento_test(HashMap<String, String> data) throws Exception {
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
        boolean resultStep7 = pc4.SelecFechaConFiltrosDropdown(User,true);
        assertTrue(resultStep7,"No se obtuvieron registros");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Dar clic en el Boton Consultar y Validar que en el grid se muestre los registros filtrados");
        boolean resultStep9 = pc4.consultarComprobantes(true);
        assertTrue(resultStep9,"No se obtuvieron registros");
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
        addStep("Dar clic en el Icono EXCEL");
        boolean rC = pc4.ClickBtnExcel(true);
        assertTrue(rC, "No se encontró botón cerrar");
        /*
         *                                         PASO 14
         ******************************************************************************************/
        addStep("Validar que se visualice una ventana emergente Descarga de archivo o mensaje modal con el texto: \"Espere un momento\"");
        boolean MSG = pc4.MensajeEspere(true);
        assertTrue(MSG, "No se encontró botón cerrar");
        /*
         *                                         PASO 15
         ******************************************************************************************/
        addStep("Validar que la ventana se mantenga visible hasta que el archivo esté listo para descarga, momento en el cual se cerrará automáticamente");
        boolean MSGD = pc4.MensajeEspereDesaparece(true);
        assertTrue(MSGD, "No se pudo validar mensaje");
        /*
         *                                         PASO 16
         ******************************************************************************************/
        addStep("Validar la descarga del archivo en formato Excel");
        boolean Desc = pc4.openLastFileDownloadFolder("Excel",true);
		assertTrue(Desc,"No se encontro el archivo");
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
        return "\"Validar que al hacer clic en el ícono de Excel, se visualice una ventana emergente o mensaje modal con el texto: \"\"Espere un momento\"\".\r\n"
        		+ "Esta ventana se mantendrá visible hasta que el archivo esté listo para descarga, momento en el cual se cerrará automáticamente\r\n"
        		+ "\r\n"
        		+ "el sistema debe generar y descargar automáticamente un archivo en formato en excel\"";
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