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
* DESCRIPCIÓN: Validar la columna \"Origen\" deberá incluirse en las descargas individuales de Excel y Si en el JSON del registro el campo Proceso_CF tiene valor false, la celda debe mostrar \"Devoluciones\", si el campo Proceso_CF tiene valor true, la celda debe mostrar \"Cambios Físicos\" aplica para todos los usuarios, del modulo \"Devoluciones \"DIRECTOS.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_317_Validar las descargas individuales de Excel incluya la columna "Origen" aplica para todos los usuarios, del modulo "Devoluciones" DIRECTOS
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
* @date 10/10/2025
*/

public class ATC_FT_317_IndividualExcelColumnaOrigenDevoluciones extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_317_IndividualExcelColumnaOrigenDevoluciones_test(HashMap<String, String> data) throws Exception {
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
        addStep("Validar el uso de los select en todos los filtros");
        boolean resultStep6 = pc4.SelectAllFiltros(true);
        assertTrue(resultStep6,"Elementos no en blanco en pantalla devoluciones directos");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar clic en el Boton Consultar y Validar que en el grid se muestre los registros filtrados");
        boolean resultStep9 = pc4.consultarComprobantes(true);
        assertTrue(resultStep9,"No se obtuvieron registros");
        /*
         *                                         PASO 8
         ******************************************************************************************/
        addStep("Validar que al hacer clic en una fila del grid se abre el ticket de Devoluciones");
        boolean r12 = pc4.abrirTicketDesdeGridYValidarVista(true); 
        assertTrue(r12, "No se redirigió correctamente al ticket de Devoluciones");
        /*
         *                                         PASO 9
         ******************************************************************************************/
        addStep("Validar que el ticket mostrado corresponda exactamente al seleccionado");
        boolean rA = pc4.validarTicketCoincideConSeleccion(true);
        assertTrue(rA, "El ticket abierto no corresponde al folio seleccionado en el grid");
        /*
         *                                         PASO 10
         ******************************************************************************************/
        addStep("Validar que la pantalla muestre correctamente los datos de la devolución");
        boolean rB = pc4.validarDatosDeDevolucion(true);
        assertTrue(rB, "Los datos de la vista detalles no coinciden con los del grid/filtros");
        /*
         *                                         PASO 11
         ******************************************************************************************/
        addStep("Dar clic en el Icono EXCEL");
        boolean rC = pc4.ClickBtnExcel(true);
        assertTrue(rC, "No se encontró botón cerrar");
        /*
         *                                         PASO 12
         ******************************************************************************************/
        addStep("Validar que se visualice una ventana emergente Descarga de archivo o mensaje modal con el texto: \"Espere un momento\"");
        boolean MSG = pc4.MensajeEspere(true);
        assertTrue(MSG, "No se pudo validar mensaje");
        /*
         *                                         PASO 13
         ******************************************************************************************/
        addStep("Validar que la ventana se mantenga visible hasta que el archivo esté listo para descarga, momento en el cual se cerrará automáticamente");
        boolean MSGD = pc4.MensajeEspereDesaparece(true);
        assertTrue(MSGD, "No se pudo validar mensaje");
        /*
         *                                         PASO 14
         ******************************************************************************************/
        addStep("Validar la descarga del archivo en formato Excel");
        boolean Desc = pc4.openLastFileDownloadFolder("Excel",true);
		assertTrue(Desc,"No se encontro el archivo");
		 /*
         *                                         PASO 15
         ******************************************************************************************/
        addStep("Validar en BD PostgreSQL, si en el JSON del registro el campo Proceso_CF tiene valor false, la celda debe mostrar \"Devoluciones\". Si el campo Proceso_CF tiene valor true, la celda debe mostrar \"Cambios Físicos\"."
        		+ "Validar que se incluya la columna \"Origen\" y muestre el tipo de registro [Devoluciones o Cambios fisicos] de acuerdo con el valor que se muestra en el JSON");
        boolean Posg = pc4.validarOrigenConExcel(true);
		assertTrue(Posg,"Se encontraron diferencias en campo origen");
		/*
         *                                         PASO 16
         ******************************************************************************************/
        addStep("Validar datos de los campos del formato EXCEL \"Fuente de Abasto\", \"Nombre del Proveedor\", \"Región\", \"Plaza\", \"CR Tienda\", \"Nombre de Tienda\", \"Fecha\", \"Hora\", \"FECHA ADMVA\" \"Folio\", \"Tiempo de devolución\", \"Origen\", \"CVE Artículo\", \"Descripción\", \"Precio\" \"Cantidad a devolver\", \"Val.Total\", \"Total de tasa fiscal\", \"Total de Articulos a devolver\", \"Total de piezas a devolver\", \"Valor Total\", \"Total Costo\""
        		+ "Validar que la columna deberá de colocarse después de la columna llamada \"Tiempo de devolución\" ");
        boolean Comp = pc4.validarEncabezadosExcel(true);
		assertTrue(Comp,"Se encontraron diferencias en los encabezados");
		
        
        
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
        return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] validar desde el criterio  "
        		+ "2.Liga de Portal Cometa "
        		+ "3. Acceso a BD PostgreSQL";
    }

    @Override
    public String setTestDescription() {
        return "Validar la columna \"Origen\" deberá incluirse en las descargas individuales de Excel y Si en el JSON del registro el campo Proceso_CF tiene valor false, la celda debe mostrar \"Devoluciones\", si el campo Proceso_CF tiene valor true, la celda debe mostrar \"Cambios Físicos\" aplica para todos los usuarios, del modulo \"Devoluciones \"DIRECTOS. ";
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