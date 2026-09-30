package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCIÓN: Validar que los procedimientos de sincronizacion optimizada para Comprobantes de Compra, Cambios Fisicos y Devoluciones existan en PostgreSQL Cometa.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_118 - Validar disponibilidad de procedimientos de optimizacion (CC, CF, DV)
* 
*PREREQUISITOS:
*1. Acceso a PostgreSQL Cometa con permisos de lectura sobre pg_proc
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Procedimientos de optimizacion confirmados por desarrollo en CHG0150974 para CC, CF y DV.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_118_ValidarProcedimientosOptimizacionCCCFDV extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_118_ValidarProcedimientosOptimizacionCCCFDV_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "CEDIS";

        CometaBdValidationHelper bd = new CometaBdValidationHelper(testCase);

        addStep("Precondicion: documentar usuario de referencia para perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user + " | Url: " + url);
        System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ejecutar SQL: SELECT proname FROM pg_proc WHERE proname IN ('sincronizar_datos_comprobantes_compras','sincronizar_datos_comprobantes_compras_preparar','sincronizar_datos_comprobantes_compras_procesar_lote') ORDER BY proname;");
        Set<String> expectedCc = new LinkedHashSet<String>(Arrays.asList(
                "sincronizar_datos_comprobantes_compras",
                "sincronizar_datos_comprobantes_compras_preparar",
                "sincronizar_datos_comprobantes_compras_procesar_lote"));
        Set<String> actualCc = bd.obtenerProcedimientosOFunciones(
                "sincronizar_datos_comprobantes_compras",
                "sincronizar_datos_comprobantes_compras_preparar",
                "sincronizar_datos_comprobantes_compras_procesar_lote");
        assertEquals(actualCc, expectedCc, "La consulta no retorno exactamente los 3 procedimientos de optimizacion CC");
        testCase.addBoldTextEvidenceCurrentStep("La consulta retorna los 3 procedimientos de optimizacion de Comprobantes de compra (CC): sincronizar_datos_comprobantes_compras, sincronizar_datos_comprobantes_compras_preparar y sincronizar_datos_comprobantes_compras_procesar_lote.");

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Ejecutar SQL: SELECT proname FROM pg_proc WHERE proname IN ('sincronizar_datos_cambiosfisicos','sincronizar_datos_cambiosfisicos_preparar','sincronizar_datos_cambiosfisicos_procesar_lote') ORDER BY proname;");
        Set<String> expectedCf = new LinkedHashSet<String>(Arrays.asList(
                "sincronizar_datos_cambiosfisicos",
                "sincronizar_datos_cambiosfisicos_preparar",
                "sincronizar_datos_cambiosfisicos_procesar_lote"));
        Set<String> actualCf = bd.obtenerProcedimientosOFunciones(
                "sincronizar_datos_cambiosfisicos",
                "sincronizar_datos_cambiosfisicos_preparar",
                "sincronizar_datos_cambiosfisicos_procesar_lote");
        assertEquals(actualCf, expectedCf, "La consulta no retorno exactamente los 3 procedimientos de optimizacion CF");
        testCase.addBoldTextEvidenceCurrentStep("La consulta retorna los 3 procedimientos de optimizacion de Cambios fisicos (CF): sincronizar_datos_cambiosfisicos, sincronizar_datos_cambiosfisicos_preparar y sincronizar_datos_cambiosfisicos_procesar_lote.");

        /*
         *                                             PASO 3
         ***************************************************************************/
        addStep("Ejecutar SQL: SELECT proname FROM pg_proc WHERE proname IN ('sincronizar_datos_devoluciones','sincronizar_datos_devoluciones_preparar','sincronizar_datos_devoluciones_procesar_lote') ORDER BY proname;");
        Set<String> expectedDv = new LinkedHashSet<String>(Arrays.asList(
                "sincronizar_datos_devoluciones",
                "sincronizar_datos_devoluciones_preparar",
                "sincronizar_datos_devoluciones_procesar_lote"));
        Set<String> actualDv = bd.obtenerProcedimientosOFunciones(
                "sincronizar_datos_devoluciones",
                "sincronizar_datos_devoluciones_preparar",
                "sincronizar_datos_devoluciones_procesar_lote");
        assertEquals(actualDv, expectedDv, "La consulta no retorno exactamente los 3 procedimientos de optimizacion DV");
        testCase.addBoldTextEvidenceCurrentStep("La consulta retorna los 3 procedimientos de optimizacion de Devoluciones (DV): sincronizar_datos_devoluciones, sincronizar_datos_devoluciones_preparar y sincronizar_datos_devoluciones_procesar_lote.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Acceso a PostgreSQL Cometa con permisos de lectura sobre pg_proc      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que existan los 9 procedimientos de sincronizacion optimizada para CC, CF y DV confirmados por desarrollo en CHG0150974."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_118_ValidarProcedimientosOptimizacionCCCFDV_test"; }
    @Override public String setTestInstanceID() { return null; }
}