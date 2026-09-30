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
* DESCRIPCIÓN: Validar el contenido del excel debe de ser como ejemplo adjuntado en la HU Ejemplo de descarga individual devoluciones en excel.xlsx
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_208_Validar en contenido del excel, en el modulo "Devoluciones" DIRECTOS
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
* @date 26/09/2025
*/

public class ATC_FT_208_ContenidoExcel extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_208_ContenidoExcel_test(HashMap<String, String> data) throws Exception {
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

        boolean resultStep1 = pc4.IngresarURL(url, false);
        assertTrue(resultStep1,"No se pudo ingresar a la URL");       
        //Se quita pantalla de seguridad ventana privada si existe
        pc4.PrivatePortal();     
        /*
         *                                             PASO 2
         ******************************************************************************************/

        boolean resultStep2 = pc4.IniciarSesionNoToken(User, Password, false);
        assertTrue(resultStep2, "No se pudo iniciar sesion");        
        /*
         *                                             PASO 3
         ******************************************************************************************/

        boolean resultStep3 = pc4.seleccionarPortalCometaQA(false);
        assertTrue(resultStep3, "No es visible el elemento de Cometa");
        /*
         *                                             PASO 4
         ******************************************************************************************/

        boolean resultStep4 = pc4.clicBotonCometaDirectos(false);
        assertTrue(resultStep4, "No se encontro el elemento Directos");
        /*
         *                                             PASO 5
         ******************************************************************************************/

        boolean resultStep4_1 = pc4.ClicBotonDevoluciones(false);
        assertTrue(resultStep4_1,"No se encontro el elemento Devoluciones");
        /*
         *                                             PASO 7
         ******************************************************************************************/

        boolean resultStep6 = pc4.SelecPlazaRegion(false);
        assertTrue(resultStep6,"No se obtuvieron registros");
        /*
         *                                             PASO 8
         ******************************************************************************************/

        boolean resultStep7 = pc4.SelecFechaConFiltrosDropdown(User,false);
        assertTrue(resultStep7,"No se obtuvieron registros");
        /*
         *                                             PASO 9
         ******************************************************************************************/

        boolean resultStep9 = pc4.consultarComprobantes(false);
        assertTrue(resultStep9,"No se obtuvieron registros");
        /*
         *                                         PASO 10
         ******************************************************************************************/

        boolean r12 = pc4.abrirTicketDesdeGridYValidarVista(false); 
        assertTrue(r12, "No se redirigió correctamente al ticket de Devoluciones");
        /*
         *                                         PASO 13
         ******************************************************************************************/

        boolean rC = pc4.ClickBtnExcel(false);
        assertTrue(rC, "No se encontró botón Excel");
        /*
         *                                         PASO 14
         ******************************************************************************************/

        boolean MSG = pc4.MensajeEspere(false);
        assertTrue(MSG, "No se encontró Mensaje Espere");
        /*
         *                                         PASO 15
         ******************************************************************************************/

        boolean MSGD = pc4.MensajeEspereDesaparece(false);
        assertTrue(MSGD, "No se pudo validar mensaje");
        /*
         *                                         PASO 16
         ******************************************************************************************/
        addStep("Contar con un Ticket descargado individual en formato EXCEL");
        boolean Desc = pc4.openLastFileDownloadFolder("Excel", true);
		assertTrue(Desc,"No se encontro el archivo");
	    /*
         *                                         PASO 17
         ******************************************************************************************/
        addStep("Validar datos de los campos del formato EXCEL \"Fuente de Abasto\", \"Nombre del Proveedor\", \"Región\", \"Plaza\", \"CR Tienda\", \"Nombre de Tienda\", \"Fecha\", \"Hora\", \"FECHA ADMVA\" \"Folio\", \"Tiempo de devolución\", \"Origen\", \"CVE Artículo\", \"Descripción\", \"Precio\" \"Cantidad a devolver\", \"Val.Total\", \"Total de tasa fiscal\", \"Total de Articulos a devolver\", \"Total de piezas a devolver\", \"Valor Total\", \"Total Costo\"");
        boolean Comp = pc4.validarEncabezadosExcel(true);
		assertTrue(Comp,"No se encontro el archivo");
        
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
        return "1. Tener un formato EXCEL individual descargado. "
        		+ "2.Validar desde el criterio y con el archivo adjunto en la HU como  Ejemplo de descarga individual devoluciones en excel.xlsx";
    }

    @Override
    public String setTestDescription() {
        return "Validar el contenido del excel debe de ser como ejemplo adjuntado en la HU Ejemplo de descarga individual devoluciones en excel.xlsx";
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