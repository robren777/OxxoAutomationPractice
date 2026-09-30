package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Script: ATC_FT_009_RangoInvalidoNoPermiteConsultarADMIN
 * Proyecto: I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5
 * Autor: Mauricio Gurrola
 * Fecha: 2026-06-23
 * Descripción: Test to Fail — rango inválido no permite consultar (Usuario ADMIN).
 *              Valida que al capturar una Fecha inicial mayor que la Fecha final
 *              (hoy como inicial, ayer como final + horarios + ruta dinámica),
 *              el botón Consultar SÍ se habilita pero al hacer clic el sistema
 *              no muestra resultados y borra automáticamente la fecha final.
 */
public class ATC_FT_009_RangoInvalidoNoPermiteConsultarADMIN extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_009_RangoInvalidoNoPermiteConsultarADMIN_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

        // === Variables del CSV ===
        String User = data.get("User");
        String url  = data.get("Url");

        /*
         * PRECONDICIÓN
         ***************************************************************************/
        addStep("Precondicion");
        boolean ResultPrec = pc4.validarYActualizarHistoricoCorreosCedis(true, User);
        assertTrue(ResultPrec, "Se actualizó correctamente la ruta propuesta");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridCedis(true, User);
        assertTrue(ResultPre, "No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
        boolean validacion = pc4.obtenerRutaPropuesta(User);
        pc4.obtenerFechaRegistroRutaPropuesta(User);
        assertTrue(validacion, "No se obtuvieron datos en la tabla");
        String Password = pc4.obtenerPasswordPorUsuario(User);
        assertTrue(Password != null, "No se encontró password configurada para el usuario: " + User);

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ingresar a la URL https://pruebasweb.oxxo.com/AuthFemcoProx/Login");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1, "No se pudo ingresar a la URL");

        // Se quita pantalla de seguridad ventana privada si existe
        pc4.PrivatePortal();

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Capturar usuario y contraseña y hacer clic en Inicio de sesión");
        boolean resultStep2 = pc4.IniciarSesionNoToken(User, Password, true);
        assertTrue(resultStep2, "No se pudo iniciar sesión");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Hacer clic en Portal COMETA");
        boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep3, "No es visible el elemento de COMETA");

        /*
         *                                             PASO 4
         ***************************************************************************/
        addStep("Ingresar al módulo CEDIS");
        boolean resultStep4 = pc4.clicBotonCometaCedis(true);
        assertTrue(resultStep4, "No se encontró el elemento CEDIS");

        /*
         *                                             PASO 5
         ***************************************************************************/
        addStep("Ingresar al módulo Recepción de Transferencias y Devoluciones");
        boolean resultStep5 = pc4.ClicBotonRecepcionCedis(true);
        assertTrue(resultStep5, "No se encontró el elemento Recepción");

        /*
         *                                             PASO 6
         ***************************************************************************/
        addStep("Seleccionar la opción Liquidación de Ruta");
        boolean resultStep6 = pc4.opcLiquidacionRuta();
        assertTrue(resultStep6, "No se pudo seleccionar la opción Liquidación de Ruta");
        boolean resultStep6b = pc4.scLiquidacionRuta();
        assertTrue(resultStep6b, "No se muestra la pantalla de Liquidación de Ruta correctamente");

        /*
         *                                             PASO 7
         ***************************************************************************/
        addStep("Capturar Fecha inicial mayor que Fecha final como rango inválido (hoy > ayer) con horarios y Ruta Dinámica");
        // fechaFormateada y horaFormateada son public static en PortalCometaMVP4,
        // se cargan en la precondición con obtenerFechaRegistroRutaPropuesta(User).
        boolean resultStep7a = rutaPage.llenadoFiltrosLRRangoInvalido(
                pc4, PortalCometaMVP4.fechaFormateada, PortalCometaMVP4.horaFormateada, true);
        assertTrue(resultStep7a, "No se pudo capturar el rango inválido de fechas/horas en los filtros");
        boolean resultStep7b = pc4.ingRutaDinamica();
        assertTrue(resultStep7b, "No se pudo ingresar la Ruta Dinámica (UNIGIS)");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Hacer clic en Consultar con rango inválido — el sistema no muestra resultados y borra la fecha final");
        // Se usa opcConsultarLiqRuta (solo da clic) en lugar de consultarLR,
        // porque consultarLR espera el detalle de resultados que nunca aparece con rango inválido.
        boolean resultStep8 = pc4.opcConsultarLiqRuta(true);
        assertTrue(resultStep8, "No se pudo hacer clic en el botón Consultar");
        // El sistema necesita un momento para procesar el rango inválido,
        // borrar la fecha final y deshabilitar el botón Consultar nuevamente.
        u.hardWait(4);

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Validar que el botón Consultar queda deshabilitado tras la consulta con rango inválido");
        // opcConsultarDeshabilitada() de pc4 busca clase 'btn-consultar' que NO existe en el botón de LR.
        // Se usa validaBtnConsultarLRDeshabilitado() que tiene el selector correcto para esta pantalla.
        boolean resultStep9 = rutaPage.validaBtnConsultarLRDeshabilitado(pc4, true);
        assertTrue(resultStep9, "El botón Consultar debería estar deshabilitado pero aún aparece habilitado");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Validar que la Fecha Final fue borrada automáticamente por el sistema (campo vacío)");
        boolean resultStep10 = rutaPage.validaFechaFinalVacia(true);
        assertTrue(resultStep10, "La Fecha Final debería estar vacía pero aún muestra una fecha");

        u.close();
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }

    @Override
    public String setPrerequisites() {
    	 return "Usuario ADMIN con su password y url del portal COMETA QA";
    }

    @Override
    public String setTestDescription() {
        return "Test to Fail — rango inválido no permite consultar (Usuario ADMIN).\r\n"
        		+ " *              Valida que al capturar una Fecha inicial mayor que la Fecha final\r\n"
        		+ " *              (hoy como inicial, ayer como final + horarios + ruta dinámica),\r\n"
        		+ " *              el botón Consultar SÍ se habilita pero al hacer clic el sistema\r\n"
        		+ " *              no muestra resultados y borra automáticamente la fecha final.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_009_RangoInvalidoNoPermiteConsultarADMIN_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
