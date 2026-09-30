package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Set;

import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCIÓN: Validar que RTD y DE cuenten con estructuras independientes para soportar el solapamiento controlado del origen cedis-devolucion-especial sin compartir la misma tabla operativa.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_107 - Validar independencia estructural entre RTD y DE
* 
*PREREQUISITOS:
*1. Acceso a PostgreSQL Cometa con permisos de lectura
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Familias tecnicas xxcm_cedis_rtd_proyeccion_grid% y xxcm_cedis_de_proyeccion_grid%.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_107_ValidarIndependenciaEstructuralRTDyDE extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_107_ValidarIndependenciaEstructuralRTDyDE_test(HashMap<String, String> data) throws Exception {
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
        addStep("Consultar por separado la existencia de las familias RTD y DE en information_schema.tables");
        Set<String> tablasRtd = bd.obtenerTablasPorPatron("xxcm_cedis_rtd_proyeccion_grid%");
        Set<String> tablasDe = bd.obtenerTablasPorPatron("xxcm_cedis_de_proyeccion_grid%");
        testCase.addBoldTextEvidenceCurrentStep("Objetos RTD: " + tablasRtd.toString());
        testCase.addBoldTextEvidenceCurrentStep("Objetos DE: " + tablasDe.toString());
        boolean resultStep1 = !tablasRtd.isEmpty() && !tablasDe.isEmpty();
        assertTrue(resultStep1, "No existen simultaneamente las familias RTD y DE en la base de datos");
        testCase.addBoldTextEvidenceCurrentStep("Ambas familias de tablas existen simultaneamente en la base de datos.");

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Comparar los nombres de objetos retornados para ambas familias");
        boolean resultStep2 = true;
        for (String tablaRtd : tablasRtd) {
            if (tablasDe.contains(tablaRtd)) {
                resultStep2 = false;
                testCase.addBoldTextEvidenceCurrentStep("Objeto compartido detectado: " + tablaRtd);
            }
        }
        assertTrue(resultStep2, "Los objetos de RTD y DE no se mantienen separados por nomenclatura");
        testCase.addBoldTextEvidenceCurrentStep("Los objetos de RTD y DE se mantienen separados por nomenclatura y no sustituyen la estructura del otro modulo.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Acceso a PostgreSQL Cometa con permisos de lectura      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que RTD y DE cuenten con estructuras independientes y no sustituyan la estructura del otro modulo."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_107_ValidarIndependenciaEstructuralRTDyDE_test"; }
    @Override public String setTestInstanceID() { return null; }
}