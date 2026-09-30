package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCIÓN: Validar que las tablas proyectadas RTD y DE tengan estructura definida y no solo el nombre del objeto desplegado.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_113 - Validar existencia de columnas minimas en xxcm_cedis_rtd_proyeccion_grid y xxcm_cedis_de_proyeccion_grid
* 
*PREREQUISITOS:
*1. Existen las tablas xxcm_cedis_rtd_proyeccion_grid y xxcm_cedis_de_proyeccion_grid
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Consulta de conteo de columnas en information_schema.columns.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_113_ValidarColumnasMinimasTablasRTDyDE extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_113_ValidarColumnasMinimasTablasRTDyDE_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ejecutar SQL: SELECT table_name, COUNT(*) AS total_columnas FROM information_schema.columns WHERE table_name IN ('xxcm_cedis_rtd_proyeccion_grid','xxcm_cedis_de_proyeccion_grid') GROUP BY table_name ORDER BY table_name;");
        Map<String, Integer> columnas = bd.obtenerTotalColumnasPorTabla(
                "xxcm_cedis_rtd_proyeccion_grid",
                "xxcm_cedis_de_proyeccion_grid");
        boolean resultStep1 = columnas.containsKey("xxcm_cedis_rtd_proyeccion_grid")
                && columnas.containsKey("xxcm_cedis_de_proyeccion_grid")
                && columnas.get("xxcm_cedis_rtd_proyeccion_grid") > 0
                && columnas.get("xxcm_cedis_de_proyeccion_grid") > 0;
        assertTrue(resultStep1, "La consulta no retorno ambas tablas con total de columnas mayor a 0");
            testCase.addBoldTextEvidenceCurrentStep("La consulta retorna ambas tablas y en cada una el total de columnas es mayor a 0.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Existen las tablas xxcm_cedis_rtd_proyeccion_grid y xxcm_cedis_de_proyeccion_grid      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que las tablas proyectadas RTD y DE tengan estructura definida y total de columnas mayor a 0."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_113_ValidarColumnasMinimasTablasRTDyDE_test"; }
    @Override public String setTestInstanceID() { return null; }
}