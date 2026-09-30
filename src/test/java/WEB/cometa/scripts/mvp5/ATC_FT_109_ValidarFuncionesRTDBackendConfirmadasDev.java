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

/**DESCRIPCIÓN: Validar que las funciones RTD para backend confirmadas por desarrollo esten desplegadas en PostgreSQL Cometa.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_109 - Validar disponibilidad de funciones RTD para backend (confirmadas por dev)
* 
*PREREQUISITOS:
*1. Acceso a PostgreSQL Cometa con permisos de lectura sobre pg_proc
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Funciones get_xxcm_cedis_rtd_proyeccion_grid_filtrados y get_xxcm_cedis_rtd_proyeccion_grid_paginated.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_109_ValidarFuncionesRTDBackendConfirmadasDev extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_109_ValidarFuncionesRTDBackendConfirmadasDev_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

        String user = data.get("user");
        String url = data.get("Url");
        String perfil = "CEDIS";

        CometaBdValidationHelper bd = new CometaBdValidationHelper(testCase);

        addStep("Precondicion: documentar usuario de referencia para perfil " + perfil);
        testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user + " | Url: " + url);
        testCase.addBoldTextEvidenceCurrentStep("Pendiente confirmado por matriz: funcion de filtros de combos RTD y funcion reporte Excel RTD no entregadas en esta iteracion.");
        System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);

        /*
         *                                             PASO 1
         ***************************************************************************/
        addStep("Ejecutar SQL: SELECT proname FROM pg_proc WHERE proname IN ('get_xxcm_cedis_rtd_proyeccion_grid_filtrados','get_xxcm_cedis_rtd_proyeccion_grid_paginated') ORDER BY proname; -- PENDIENTE: get_xxcm_cedis_rtd_proyeccion_grid_filtros y funcion reporte Excel RTD no entregadas en esta iteracion.");
        Set<String> expected = new LinkedHashSet<String>(Arrays.asList(
                "get_xxcm_cedis_rtd_proyeccion_grid_filtrados",
                "get_xxcm_cedis_rtd_proyeccion_grid_paginated"));
        Set<String> actual = bd.obtenerProcedimientosOFunciones(
                "get_xxcm_cedis_rtd_proyeccion_grid_filtrados",
                "get_xxcm_cedis_rtd_proyeccion_grid_paginated");
        assertEquals(actual, expected, "La consulta no retorno exactamente las 2 funciones RTD confirmadas por desarrollo");
            testCase.addBoldTextEvidenceCurrentStep("La consulta retorna las 2 funciones RTD confirmadas por dev: get_xxcm_cedis_rtd_proyeccion_grid_filtrados y get_xxcm_cedis_rtd_proyeccion_grid_paginated. PENDIENTE: funcion de filtros de combos RTD (gap vs CA09) y funcion de reporte Excel RTD (gap vs CA06).");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Acceso a PostgreSQL Cometa con permisos de lectura sobre pg_proc      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que las funciones RTD confirmadas por desarrollo esten desplegadas; se documenta el gap de filtros de combos RTD y reporte Excel RTD no entregados."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_109_ValidarFuncionesRTDBackendConfirmadasDev_test"; }
    @Override public String setTestInstanceID() { return null; }
}