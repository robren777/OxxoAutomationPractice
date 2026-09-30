package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesPage;

/**
 * DESCRIPCIÓN: Validar que un usuario con perfil ADMIN pueda descargar el resumen en Excel del módulo Directos > Devoluciones, consultar el archivo en el Centro de descarga y verificar que la nomenclatura generada del archivo corresponda exactamente al rango de fechas filtrado, sin truncamiento.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_014 Descargar resumen Excel como ADMIN y verificar nomenclatura Resumen_DEVOLUCION CA01
 *
 * PREREQUISITOS:
 * 1. Credenciales para el perfil ADMIN
 * 2. Liga de Portal Cometa
 *
 * INSUMOS:
 * Contar con información disponible en tabla xxcm_historico_cedis_view.
 * URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date 06/07/2026
 */
public class ATC_FT_014_DescargarResumenExcelAdminNomenclaturaDevolucionCA01 extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_014_DescargarResumenExcelAdminNomenclaturaDevolucionCA01_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
 		String url = data.get("Url");
		u = new SeleniumUtil(new MEdgeTest(), true);
 		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
 		devolucionesPage devP = new devolucionesPage (u, testCase);
 		 boolean validacion =  devP.obtenerRegistroUsuario(user);
         assertTrue(validacion,"No muestra información de manera correcta");
         devP.actualizaVariables();
         String password = pc.obtenerPasswordPorUsuario(user);
         assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

        /*
         * Paso 1 - Ingresar a la liga del portal con las con credenciales
         */
        addStep("Ingresar a la liga del portal con las con credenciales");
        u.get(data.get("Url"));
        u.refresh();
        u.hardWait(1);
        validacion = pc.loginScreen();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 2 - Ingresar credenciales correspondientes al perfil ADMIN usuario para inicio de sesion
         */
        addStep("Ingresar credenciales correspondientes al perfil ADMIN usuario para inicio de sesion");
        validacion = pc.iniciarSesion(user, password);
        assertTrue(validacion, "No se puede iniciar sesión de manera correcta");
        u.hardWait(10);
        validacion = pc.accessControlScreenSE();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 3 - Ingresar al portal Cometa desde access control
         */
        addStep("Ingresar al portal Cometa desde access control");
        validacion = pc.ingPortalCometa();
        assertTrue(validacion, "No se pudo seleccionar la opción de manera correcta");
        u.hardWait(8);
        validacion = pc.portalCometaScDirectosComercialSE();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 4 - Ingresar a la pantalla "Devoluciones"
         */
        addStep("Ingresar a la pantalla \"Devoluciones\"");
        pc.opcDirectosPortalCometa(false);
        validacion = pc.selectDevolucionesSE();
        assertTrue(validacion, "No se pudo seleccionar la opción de manera correcta");
        validacion = pc.screenDevoluciones();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 5 - Seleccionar cualquier filtro + rango de fechas
         */
        addStep("Seleccionar fecha inicio y fecha fin en el filtro de fecha");
        validacion = devP.SelecFecha(true);
        assertTrue(validacion, "No se permite seleccionar correctamente las fechas de inicio y fin en el filtro de fecha");

        /*
         * Paso 6 - Seleccionar botón "Consultar"
         */
        addStep("Dar clic en el boton Consultar");
        validacion = pc.opcConsultar(true);
        assertTrue(validacion, "El botón se encuentra deshabilitado");

        /*
         * Paso 7 - Validacion de etiqueta, icono y click en el boton para descargar resumen excel".
         */
        addStep("Dar clic en el boton 'Descargar resumen de todos los comprobantes filtrados (Excel)'");
        validacion = pc.etiquetaDescargaResumen();
        assertTrue(validacion, "No se muestra correctamente");
        validacion = pc.iconoExcelResumen();
        assertTrue(validacion, "No se muestra correctamente");
        validacion = pc.clicExcelResumen(false);
        validacion = pc.imgEspereUnMomento();
        validacion = pc.CerrarPopUpCentroDescargayComputoDEV(false);
        assertTrue(validacion, "No se muestra correctamente");

        /*
         * Paso 8 - Validar en el Excel resumen que el dato de la columna Nombre de Tienda coincida con el grid
         */
        addStep("Validar en el Excel resumen que el dato de la columna Nombre de Tienda coincida con el grid");
        validacion = pc.ValidarNombreTiendaExcelVsGrid(true);
        assertTrue(validacion, "No se muestra correctamente");

        /*
         * Paso 9 - Validar en el Excel resumen que el dato de la columna Fecha coincida con el grid
         */
        addStep("Validar en el Excel resumen que el dato de la columna Fecha coincida con el grid");
        validacion = pc.ValidarFechasExcelVsGridResumen(true);
        assertTrue(validacion, "No se muestra correctamente");

        /*
         * Paso 10 - Ingresar a Centro de descarga
         */
        addStep("Se da clic en menú centro de descarga");
        boolean resultStep37 = pc.ClicBotonCentroDescarga(true);
        assertTrue(resultStep37, "No se pudo dar click en botón descarga");

        /*
         * PASO 11
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep10 = pc.FiltroFechaCentroDescarga(true);
        assertTrue(resultStep10, "No se pudo seleccionar fecha");

        /*
         * PASO 12
         ******************************************************************************************/
        addStep("Se valida que se registró la última descarga realizada");
        boolean resultStep11 = pc.validarUltimaDescargaYDescargar(true);
        assertTrue(resultStep11, "No se obtuvo la última descarga realizada");

        /*
         * PASO 13
         ******************************************************************************************/
        addStep("Se valida que se haya descargado de forma correcta y automatica el  archivo");
        boolean resultStep12 = pc.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep12, "No se obtuvo la última descarga realizada");

        /*
         * PASO 14
         ******************************************************************************************/
        addStep("Verificar el nombre completo del archivo descargado incluyendo su extension y validar que cada elemento del nombre cumple la regla de la HU");
        assertTrue(pc.validarDescargaComprobanteMasivaExc1(true), "El nombre del archivo descargado no cumple con la nomenclatura esperada");
    }

    @AfterMethod
    public void afterMethod() {
        u.quit();
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub

    }

    @Override
    public String setPrerequisites() {
        // TODO Auto-generated method stub
        return "*1. Credenciales para el perfil ADMIN      \r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar que un usuario con perfil ADMIN pueda descargar el resumen en Excel del módulo Directos > Devoluciones, consultar el archivo en el Centro de descarga y verificar que la nomenclatura generada del archivo corresponda exactamente al rango de fechas filtrado, sin truncamiento.";
    }

    @Override
    public String setTestDesigner() {
        // TODO Auto-generated method stub
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        // TODO Auto-generated method stub
        return "ATC_FT_014_DescargarResumenExcelAdminNomenclaturaDevolucionCA01_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
}