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
 * DESCRIPCION: Validar que un usuario con perfil COMERCIAL pueda consultar devoluciones, solicitar la descarga masiva en Excel desde el portal COMETA y verificar en el Centro de descarga y en la carpeta de descargas del equipo que la nomenclatura del archivo generado corresponda exactamente a Concentrado_DEVOLUCION_29_02_2026-02_03_2026 con la extension definida por la solucion.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_018 Descargar concentrado Excel masivo como COMERCIAL y verificar nomenclatura Concentrado_DEVOLUCION CA02
 *
 * PREREQUISITOS:
 * 1. Credenciales para el perfil COMERCIAL
 * 2. Liga de Portal Cometa
 *
 * INSUMOS:
 * Contar con informacion disponible en tabla xxcm_historico_cedis_view.
 * URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date 06/07/2026
 */
public class ATC_FT_018_DescargarConcentradoExcelMasivoCOMERCIALVerificarNomenclaturaDevolucion extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_018_DescargarConcentradoExcelMasivoCOMERCIALVerificarNomenclaturaDevolucion_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

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
         * Paso 1 - Ingresar a la liga del portal con las credenciales
         ******************************************************************************************/
        addStep("Ingresar a la liga del portal con las credenciales");
        u.get(url);
        u.refresh();
        u.hardWait(1);
        validacion = pc.loginScreen();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 2 - Ingresar credenciales correspondientes al perfil COMERCIAL para inicio de sesion
         ******************************************************************************************/
        addStep("Ingresar credenciales correspondientes al perfil COMERCIAL usuario para inicio de sesion");
        validacion = pc.iniciarSesion(user, password);
        assertTrue(validacion, "No se puede iniciar sesion de manera correcta");
        u.hardWait(10);
        validacion = pc.accessControlScreenSE();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 3 - Ingresar al portal Cometa desde access control
         ******************************************************************************************/
        addStep("Ingresar al portal Cometa desde access control");
        validacion = pc.ingPortalCometa();
        assertTrue(validacion, "No se pudo seleccionar la opcion de manera correcta");
        u.hardWait(8);
        validacion = pc.portalCometaScDirectosComercialSE();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 4 - Ingresar a la pantalla "Devoluciones"
         ******************************************************************************************/
        addStep("Ingresar a la pantalla \"Devoluciones\"");
        pc.opcDirectosPortalCometa(false);
        validacion = pc.selectDevolucionesSE();
        assertTrue(validacion, "No se pudo seleccionar la opcion de manera correcta");
        validacion = pc.screenDevoluciones();
        assertTrue(validacion, "No se muestra la pantalla de manera correcta");

        /*
         * Paso 5 - Seleccionar fecha inicio y fecha fin en el filtro de fecha
         ******************************************************************************************/
        addStep("Seleccionar fecha inicio y fecha fin en el filtro de fecha");
        validacion = devP.SelecFecha(true);
        assertTrue(validacion, "No se permite seleccionar correctamente las fechas de inicio y fin en el filtro de fecha");

        /*
         * Paso 6 - Dar clic en el boton Consultar
         ******************************************************************************************/
        addStep("Dar clic en el boton Consultar");
        validacion = pc.consultarComprobantes(true);
        assertTrue(validacion, "No se obtuvieron registros");

        /*
         * Paso 7 - Validar que no se muestren mas de 1000 registros en el paginado
         ******************************************************************************************/
        addStep("Validar que no se muestren mas de 1000 registros en el paginado");
        validacion = pc.LabelOfandCountMinus1000Dev(true);
        assertTrue(validacion, "Se obtuvieron mas de 1000 registros");

        /*
         * Paso 8 - Dar clic en el boton Descargar de manera masiva los comprobantes filtrados Excel
         ******************************************************************************************/
        addStep("Dar click en boton Descargar de manera masiva los comprobantes filtrados (Excel)");
        boolean resultStep36 = pc.ClicBotonDescargarMasivoDevolu(true);
        assertTrue(resultStep36, "No se encontro el elemento para realizar la descarga");

        /*
         * Paso 9 - Dar clic en menu Centro de descarga
         ******************************************************************************************/
        addStep("Se da clic en menu centro de descarga");
        boolean resultStep37 = pc.ClicBotonCentroDescarga(true);
        assertTrue(resultStep37, "No se pudo dar click en boton descarga");

        /*
         * Paso 10 - Seleccionar rango de fechas en Centro de descarga
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep10 = pc.FiltroFechaCentroDescarga(true);
        assertTrue(resultStep10, "No se pudo seleccionar fecha");

        /*
         * Paso 11 - Validar que se registro la ultima descarga realizada
         ******************************************************************************************/
        addStep("Se valida que se registro la ultima descarga realizada");
        boolean resultStep11 = pc.validarUltimaDescargaRegistrada(true);
        assertTrue(resultStep11, "No se obtuvo la ultima descarga realizada");

        /*
         * Paso 12 - Validar que se haya descargado de forma correcta y automatica el archivo
         ******************************************************************************************/
        addStep("Se valida que se haya descargado de forma correcta y automatica el archivo");
        boolean resultStep12 = pc.openLastFileDownloadFolder("Excel", true);
        assertTrue(resultStep12, "No se obtuvo la ultima descarga realizada");

        /*
         * Paso 13 - Validar nomenclatura del archivo concentrado descargado
         ******************************************************************************************/
        addStep("Verificar el nombre completo del archivo descargado incluyendo su extension y validar que cada elemento del nombre cumple la regla de la HU");
        assertTrue(pc.validarDescargaConcetradoExcel(true), "El nombre del archivo descargado no cumple con la nomenclatura esperada");
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
        return "*1. Credenciales para el perfil COMERCIAL      \r\n"
                + "*2.Liga de Portal Cometa";
    }

    @Override
    public String setTestDescription() {
        return "Validar que un usuario con perfil COMERCIAL pueda consultar devoluciones, solicitar la descarga masiva en Excel desde el portal COMETA y verificar en el Centro de descarga y en la carpeta de descargas del equipo que la nomenclatura del archivo generado corresponda exactamente a Concentrado_DEVOLUCION_29_02_2026-02_03_2026 con la extension definida por la solucion.";
    }

    @Override
    public String setTestDesigner() {
        // TODO Auto-generated method stub
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        // TODO Auto-generated method stub
        return "ATC_FT_018_DescargarConcentradoExcelMasivoCOMERCIALVerificarNomenclaturaDevolucion_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
}