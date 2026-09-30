package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionElements;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Script: ATC_FT_006_RegresionConsultaFiltrosUsuarioCEDIS
 * Proyecto: I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5
 * Autor: Mauricio Gurrola
 * Fecha: 2026-06-23
 * Descripción: Regresión de consulta y filtros (Usuario CEDIS).
 *              Valida que los filtros de Liquidación de Ruta estén visibles y editables,
 *              que la consulta funcione sin errores y que al limpiar filtros y volver a
 *              consultar no reaparezca la etiqueta Tapas en el primer renglón.
 */
public class ATC_FT_006_RegresionConsultaFiltrosUsuarioCEDIS extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_006_RegresionConsultaFiltrosUsuarioCEDIS_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        PortalCometa pc = new PortalCometa(u, testCase);
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
        addStep("Verificar que estén visibles y editables los filtros: Ruta Dinámica (UNIGIS), Fecha inicial, Hora inicial, Fecha final, Hora final");
        boolean resultStep9 = pc4.llenadoFiltrosLR();
        assertTrue(resultStep9, "No se pudo realizar el llenado de fechas y horas correctamente");

        /*
         *                                             PASO 10
         ***************************************************************************/
        addStep("Hacer clic en el botón Consultar");
        boolean resultStep10 = pc4.consultarLR(true);
        assertTrue(resultStep10, "La consulta no arrojó resultados o se presentó un error");

        /*
         *                                             PASO 11
         ***************************************************************************/
        addStep("Limpiar filtros, volver a consultar y verificar que Tapas no reaparece en el primer renglón");
        boolean resultStep11a = pc4.opcLimpiarFiltrosLiqRuta(true);
        assertTrue(resultStep11a, "No se pudo ejecutar la limpieza de filtros");
        boolean resultStep11b = pc4.llenadoFiltrosLR();
        assertTrue(resultStep11b, "No se pudo recargar los filtros de fechas y horas tras la limpieza");
        boolean resultStep11c = pc4.ingRutaDinamica();
        assertTrue(resultStep11c, "No se pudo reingresar la Ruta Dinámica (UNIGIS) tras la limpieza");
        boolean resultStep11d = pc4.consultarLR(true);
        assertTrue(resultStep11d, "La segunda consulta no arrojó resultados o se presentó un error");
        assertFalse(rutaPage.validaExisteEtiqueta(rutaElements.containerDetalle, etiqueta),
                "Se encontró la etiqueta '" + etiqueta + "' en el primer renglón tras limpiar y volver a consultar");

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
        return "Regresión de consulta y filtros (Usuario CEDIS).\r\n"
        		+ " *              Valida que los filtros de Liquidación de Ruta estén visibles y editables,\r\n"
        		+ " *              que la consulta funcione sin errores y que al limpiar filtros y volver a\r\n"
        		+ " *              consultar no reaparezca la etiqueta Tapas en el primer renglón.";
    }

    @Override
    public String setTestDesigner() {
        return "Mauricio Gurrola";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_006_RegresionConsultaFiltrosUsuarioCEDIS_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
