package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Validar que un usuario con perfil ADMIN pueda consultar devoluciones con un rango exacto de 7 dias,
 * solicitar la descarga masiva en PDF desde el portal COMETA y verificar que el archivo ZIP generado en Centro de descarga
 * y en la carpeta de descargas del equipo cumpla con la nomenclatura esperada Concentrado_DEVOLUCION_FECHAINICIO-FECHAFIN.zip.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_024 Descargar ZIP PDF masivo con rango exacto de 7 dias como ADMIN y verificar nomenclatura CA03
 *
 * PREREQUISITOS:
 * 1. Credenciales para el perfil ADMIN
 * 2. Liga de Portal Cometa
 *
 * INSUMOS:
 * Contar con informacion disponible en tabla xxcm_historico_directos_devoluciones_view.
 * URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 * Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Mauricio Gurrola
 * @date 07/07/2026
 */
public class ATC_FT_024_DescargarZipPdfMasivoRango7DiasAdminNomenclatura extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_024_DescargarZipPdfMasivoRango7DiasAdminNomenclatura_test(HashMap<String, String> data) throws Exception {
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
         * Paso 2 - Ingresar credenciales correspondientes al perfil ADMIN para inicio de sesion
         ******************************************************************************************/
        addStep("Ingresar credenciales correspondientes al perfil ADMIN usuario para inicio de sesion");
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
        validacion = devP.SelecFechaRango7Dias(true);
        assertTrue(validacion, "No se permite seleccionar correctamente las fechas de inicio y fin en el filtro de fecha");

        /*
         * Paso 6 - Dar clic en el boton Consultar
         ******************************************************************************************/
        addStep("Dar clic en el boton Consultar");
        validacion = pc.opcConsultar(true);
        assertTrue(validacion, "El boton Consultar se encuentra deshabilitado o no se obtuvieron registros");

        /*
         * Paso 7 - Validar que se muestre la etiqueta de descarga masiva
         ******************************************************************************************/
        addStep("Validar que se muestre una etiqueta con el texto: \"Descargar de manera masiva los comprobantes filtrados\"");
        validacion = pc.etiquetaDescargaMasiva();
        assertTrue(validacion, "No se muestra correctamente la etiqueta de descarga masiva");

        /*
         * Paso 8 - Validar que el icono o boton de descarga PDF masiva aparece habilitado y es clickeable
         ******************************************************************************************/
        addStep("Validar que el icono o boton de descarga PDF masiva aparece habilitado y es clickeable");
        validacion = pc.iconoPdfDescargaMasiva();
        assertTrue(validacion, "No se muestra correctamente el icono de PDF masivo");

        /*
         * Paso 9 - Dar clic en el boton Descargar de manera masiva los comprobantes filtrados PDF
         ******************************************************************************************/
        addStep("Dar clic en el boton Descargar de manera masiva los comprobantes filtrados (PDF)");
        validacion = pc.clicPdfDescargaMasiva();
        assertTrue(validacion, "No se pudo seleccionar el icono de PDF masivo");
        validacion = pc.imgEspereUnMomento();
        assertTrue(validacion, "No se muestra correctamente el mensaje Espere un momento");

        /*
         * Paso 10 - Cerrar pop up informativo de descarga
         ******************************************************************************************/
        addStep("Dar clic en el boton X del pop up informativo");
        validacion = pc.cerrarPopupInformativoDescarga();
        assertTrue(validacion, "El pop up informativo no se cerro correctamente");

        /*
         * Paso 11 - Dar clic en menu Centro de descarga
         ******************************************************************************************/
        addStep("Se da clic en menu centro de descarga");
        boolean resultStep11 = pc.ClicBotonCentroDescarga(true);
        assertTrue(resultStep11, "No se pudo dar click en boton descarga");

        /*
         * Paso 12 - Seleccionar rango de fechas en Centro de descarga
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep12 = pc.FiltroFechaCentroDescarga(true);
        assertTrue(resultStep12, "No se pudo seleccionar fecha");

        /*
         * Paso 13 - Validar que se registro la ultima descarga realizada
         ******************************************************************************************/
        addStep("Se valida que se registro la ultima descarga realizada");
        boolean resultStep13 = pc.validarUltimaDescargaRegistrada(true);
        assertTrue(resultStep13, "No se obtuvo la ultima descarga realizada");

        /*
         * Paso 14 - Validar nomenclatura del archivo ZIP descargado
         ******************************************************************************************/
        addStep("Verificar el nombre completo del archivo ZIP incluyendo su extension y validar que cada elemento del nombre cumple la regla de la HU");
        validacion = pc.validarDescargaConcentradoZipPdfDevoluciones(true);
        assertTrue(validacion, "El nombre del archivo ZIP no cumple con la nomenclatura esperada Concentrado_DEVOLUCION_FECHAINICIO-FECHAFIN.zip");
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
        return "Validar que un usuario con perfil ADMIN pueda consultar devoluciones con un rango exacto de 7 dias, solicitar la descarga masiva en PDF desde el portal COMETA y verificar que el archivo ZIP generado en Centro de descarga y en la carpeta de descargas del equipo cumpla con la nomenclatura esperada Concentrado_DEVOLUCION_FECHAINICIO-FECHAFIN.zip.";
    }

    @Override
    public String setTestDesigner() {
        // TODO Auto-generated method stub
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        // TODO Auto-generated method stub
        return "ATC_FT_024_DescargarZipPdfMasivoRango7DiasAdminNomenclatura_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
}