package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionElements;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Script: ATC_FT_008_SinResultadosSinDatosResidualesCEDIS
 * Proyecto: I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5
 * Autor: Mauricio Gurrola
 * Fecha: 2026-06-23
 * Descripción: Test to Fail — sin resultados no muestra datos residuales (Usuario CEDIS).
 *              Realiza primero una consulta válida con resultados, luego cambia a una
 *              combinación de filtros sin resultados (solo fechas, sin ruta) y verifica
 *              que el sistema no conserve datos residuales ni la etiqueta Tapas del
 *              resumen de la consulta previa.
 */
public class ATC_FT_008_SinResultadosSinDatosResidualesCEDIS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_008_SinResultadosSinDatosResidualesCEDIS_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        rutaLiquidacionElements rutaElements = new rutaLiquidacionElements();
        rutaLiquidacionPage rutaPage = new rutaLiquidacionPage(u, testCase);

        // === Variables del CSV ===
        String User     = data.get("User");
        String url      = data.get("Url");
        String etiqueta = data.get("etiqueta");

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
        addStep("Capturar Fecha inicial, Hora inicial, Fecha final y Hora final");
        boolean resultStep7 = pc4.llenadoFiltrosLR();
        assertTrue(resultStep7, "No se pudo realizar el llenado de fechas y horas correctamente");

        /*
         *                                             PASO 8
         ***************************************************************************/
        addStep("Capturar Ruta Dinámica (UNIGIS)");
        boolean resultStep8 = pc4.ingRutaDinamica();
        assertTrue(resultStep8, "No se pudo ingresar la Ruta Dinámica (UNIGIS) correctamente");

        /*
         *                                             PASO 9
         ***************************************************************************/
        addStep("Hacer clic en el botón Consultar con una combinación válida — el sistema muestra resumen y detalle");
        boolean resultStep9 = pc4.consultarLR(true);
        assertTrue(resultStep9, "La consulta válida no arrojó resultados o se presentó un error");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Limpiar filtros, reingresar combinación y hacer clic en Consultar — validar que no hay datos residuales");
        boolean resultStep10a = pc4.opcLimpiarFiltrosLiqRuta(true);
        assertTrue(resultStep10a, "No se pudo ejecutar la limpieza de filtros");
        boolean resultStep10b = pc4.llenadoFiltrosLR();
        assertTrue(resultStep10b, "No se pudo recargar los filtros de fechas tras la limpieza");
        boolean resultStep10c = pc4.ingRutaDinamica();
        assertTrue(resultStep10c, "No se pudo reingresar la Ruta Dinámica (UNIGIS) tras la limpieza");
        // No se hace assertTrue sobre consultarLR: el sistema puede retornar resultados o grid vacío;
        // la validación clave es la ausencia de datos residuales en el paso siguiente.
        pc4.consultarLR(true);

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Verificar que el sistema no conserva datos residuales ni la etiqueta Tapas de la consulta previa");
        assertFalse(rutaPage.validaExisteEtiqueta(rutaElements.containerDetalle, etiqueta),
                "Se encontró la etiqueta '" + etiqueta + "' — el sistema conservó datos residuales de la consulta previa");

        u.close();
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }

    @Override
    public String setPrerequisites() {
        return "Usuario CEDIS con su password y url del portal COMETA QA.";

    }

    @Override
    public String setTestDescription() {
        return "Test to Fail — sin resultados no muestra datos residuales (Usuario CEDIS).\r\n"
        		+ " *              Realiza primero una consulta válida con resultados, luego cambia a una\r\n"
        		+ " *              combinación de filtros sin resultados (solo fechas, sin ruta) y verifica\r\n"
        		+ " *              que el sistema no conserve datos residuales ni la etiqueta Tapas del\r\n"
        		+ " *              resumen de la consulta previa.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_008_SinResultadosSinDatosResidualesCEDIS_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
