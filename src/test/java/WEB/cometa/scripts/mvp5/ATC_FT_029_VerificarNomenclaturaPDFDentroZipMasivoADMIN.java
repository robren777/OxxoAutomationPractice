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
 * DESCRIPCION: Validar que un usuario con perfil ADMIN pueda consultar devoluciones, descargar el ZIP masivo de PDF
 * desde el portal COMETA, descomprimirlo y verificar que cada PDF contenido cumpla con la nomenclatura esperada
 * DEVOLUCION_[CR]_[FOLIO]_DD_MM_AAAA, con _CONSECUTIVO solo cuando el nombre base se duplica,
 * sin nombres identicos y sin incluir descripcion de tienda en el campo CR.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_029 Verificar nomenclatura de cada PDF dentro del ZIP masivo como ADMIN CA04
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
public class ATC_FT_029_VerificarNomenclaturaPDFDentroZipMasivoADMIN extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_029_VerificarNomenclaturaPDFDentroZipMasivoADMIN_test(HashMap<String, String> data) throws Exception {
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
        validacion = devP.SelecFecha(true);
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
         * Paso 8 - Dar clic en el boton Descargar de manera masiva los comprobantes filtrados PDF
         ******************************************************************************************/
        addStep("Dar clic en el boton Descargar de manera masiva los comprobantes filtrados (PDF)");
        validacion = pc.clicPdfDescargaMasiva();
        assertTrue(validacion, "No se pudo seleccionar el icono de PDF masivo");
        validacion = pc.imgEspereUnMomento();
        assertTrue(validacion, "No se muestra correctamente el mensaje Espere un momento");

        /*
         * Paso 9 - Cerrar pop up informativo de descarga
         ******************************************************************************************/
        addStep("Dar clic en el boton X del pop up");
        validacion = pc.cerrarPopupInformativoDescarga();
        assertTrue(validacion, "El pop up informativo no se cerro correctamente");

        /*
         * Paso 10 - Dar clic en menu Centro de descarga
         ******************************************************************************************/
        addStep("Dar clic en Centro de Descarga y seleccionar el rango de fechas de la ejecucion");
        boolean resultStep10 = pc.ClicBotonCentroDescarga(true);
        assertTrue(resultStep10, "No se pudo dar click en boton descarga");

        /*
         * Paso 11 - Seleccionar rango de fechas en Centro de descarga
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep11 = pc.FiltroFechaCentroDescarga(true);
        assertTrue(resultStep11, "No se pudo seleccionar fecha");

        /*
         * Paso 12 - Validar que se registro la ultima descarga realizada
         ******************************************************************************************/
        addStep("Se valida que se registro la ultima descarga realizada");
        boolean resultStep12 = pc.validarUltimaDescargaRegistrada(true);
        assertTrue(resultStep12, "No se obtuvo la ultima descarga realizada");

        /*
         * Paso 13 - Descargar y validar que exista el archivo ZIP en el equipo
         ******************************************************************************************/
        addStep("Descargar el archivo ZIP al equipo de computo desde el Centro de Descarga");
        validacion = pc.validarDescargaConcentradoZipPdfDevoluciones(true);
        assertTrue(validacion, "El archivo ZIP descargado no cumple con la nomenclatura esperada Concentrado_DEVOLUCION_FECHAINICIO-FECHAFIN.zip");

        /*
         * Paso 14 - Descomprimir el archivo ZIP
         ******************************************************************************************/
        addStep("Descomprimir el archivo ZIP utilizando la herramienta del sistema operativo");
        validacion = pc.extraerZipSimple(false);
        assertTrue(validacion, "No se pudo descomprimir el archivo ZIP descargado");

        /*
         * Paso 15 - Validar nomenclatura de cada PDF contenido dentro del ZIP
         ******************************************************************************************/
        addStep("Listar todos los archivos PDF extraidos y validar su nombre completo");
        validacion = pc.validarNomenclaturaPdfDevExtraidos(true);
        assertTrue(validacion, "El nombre del PDF extraido no cumple con la estructura DEVOLUCION_[CR]_[FOLIO]_DD_MM_AAAA, con _CONSECUTIVO solo cuando el nombre base se duplica");

        /*
         * Paso 16 - Validar que el campo CR no contiene descripcion de tienda
         ******************************************************************************************/
        addStep("Verificar que el campo CR en el nombre del PDF contiene unicamente la clave de Cr_Tienda sin nombre de tienda");
        assertTrue(validacion, "El campo CR del nombre del PDF contiene descripcion de tienda o no cumple la regla esperada");

        /*
         * Paso 17 - Validar formato de fecha en el nombre del PDF
         ******************************************************************************************/
        addStep("Verificar que el campo FECHA en el nombre del PDF no contiene hora ni separadores distintos a guion bajo");
        assertTrue(validacion, "El campo FECHA del nombre del PDF no cumple con el formato DD_MM_AAAA esperado");

        /*
         * Paso 18 - Validar unicidad de nombres PDF dentro del ZIP
         ******************************************************************************************/
        addStep("Listar todos los nombres de PDF dentro del ZIP y verificar que no existen nombres identicos");
        assertTrue(validacion, "Existen nombres identicos duplicados o algun consecutivo presente no es numerico en los PDF extraidos");
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
        return "Validar que un usuario con perfil ADMIN pueda consultar devoluciones, descargar el ZIP masivo de PDF desde el portal COMETA, descomprimirlo y verificar que cada PDF contenido cumpla con la nomenclatura esperada DEVOLUCION_[CR]_[FOLIO]_DD_MM_AAAA, con _CONSECUTIVO solo cuando el nombre base se duplica, sin nombres identicos y sin incluir descripcion de tienda en el campo CR.";
    }

    @Override
    public String setTestDesigner() {
        // TODO Auto-generated method stub
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        // TODO Auto-generated method stub
        return "ATC_FT_029_VerificarNomenclaturaPDFDentroZipMasivoADMINCA04_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
}