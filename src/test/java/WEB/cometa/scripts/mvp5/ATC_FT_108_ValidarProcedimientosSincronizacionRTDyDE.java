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

/**DESCRIPCIÓN: Validar que los cuatro procedimientos de sincronizacion RTD y DE confirmados por desarrollo existan en PostgreSQL Cometa.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_108 - Validar disponibilidad de procedimientos de sincronizacion RTD y DE
* 
*PREREQUISITOS:
*1. Acceso a PostgreSQL Cometa con permisos de lectura sobre pg_proc
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Procedimientos cedis_rtd_sincronizar_datos, cedis_rtd_sincronizar_proyeccion_grid, cedis_de_sincronizar_datos y cedis_de_sincronizar_proyeccion_grid.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_108_ValidarProcedimientosSincronizacionRTDyDE extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_108_ValidarProcedimientosSincronizacionRTDyDE_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ejecutar SQL: SELECT n.nspname, p.proname FROM pg_proc p JOIN pg_namespace n ON n.oid = p.pronamespace WHERE p.proname IN ('cedis_rtd_sincronizar_datos','cedis_rtd_sincronizar_proyeccion_grid','cedis_de_sincronizar_datos','cedis_de_sincronizar_proyeccion_grid') ORDER BY p.proname;");
        Set<String> expected = new LinkedHashSet<String>(Arrays.asList(
                "cedis_rtd_sincronizar_datos",
                "cedis_rtd_sincronizar_proyeccion_grid",
                "cedis_de_sincronizar_datos",
                "cedis_de_sincronizar_proyeccion_grid"));
        Set<String> actual = bd.obtenerProcedimientosOFunciones(
                "cedis_rtd_sincronizar_datos",
                "cedis_rtd_sincronizar_proyeccion_grid",
                "cedis_de_sincronizar_datos",
                "cedis_de_sincronizar_proyeccion_grid");
        assertEquals(actual, expected, "La consulta no retorno exactamente los 4 procedimientos confirmados por desarrollo");
            testCase.addBoldTextEvidenceCurrentStep("La consulta retorna exactamente los 4 procedimientos confirmados por dev: cedis_rtd_sincronizar_datos, cedis_rtd_sincronizar_proyeccion_grid, cedis_de_sincronizar_datos y cedis_de_sincronizar_proyeccion_grid.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Acceso a PostgreSQL Cometa con permisos de lectura sobre pg_proc      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que los cuatro procedimientos de sincronizacion RTD y DE confirmados por desarrollo existan en PostgreSQL Cometa."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_108_ValidarProcedimientosSincronizacionRTDyDE_test"; }
    @Override public String setTestInstanceID() { return null; }
}