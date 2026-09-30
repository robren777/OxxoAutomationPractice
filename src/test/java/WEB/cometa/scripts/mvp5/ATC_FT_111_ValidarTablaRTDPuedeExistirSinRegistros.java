package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCIÓN: Validar que la tabla xxcm_cedis_rtd_proyeccion_grid pueda consultarse tecnicamente aunque no tenga datos cargados.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_111 - Validar que xxcm_cedis_rtd_proyeccion_grid puede existir sin registros
* 
*PREREQUISITOS:
*1. La tabla xxcm_cedis_rtd_proyeccion_grid existe en PostgreSQL Cometa
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Tabla tecnica public.xxcm_cedis_rtd_proyeccion_grid.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_111_ValidarTablaRTDPuedeExistirSinRegistros extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_111_ValidarTablaRTDPuedeExistirSinRegistros_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ejecutar SQL: SELECT COUNT(*) AS total_registros FROM xxcm_cedis_rtd_proyeccion_grid;");
        boolean resultStep1 = bd.validarConteoTablaSinError("xxcm_cedis_rtd_proyeccion_grid");
        assertTrue(resultStep1, "La consulta COUNT sobre xxcm_cedis_rtd_proyeccion_grid fallo tecnicamente");
        testCase.addBoldTextEvidenceCurrentStep("La consulta se ejecuta sin error tecnico aunque el total de registros sea 0.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. La tabla xxcm_cedis_rtd_proyeccion_grid existe en PostgreSQL Cometa      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Confirmar que xxcm_cedis_rtd_proyeccion_grid puede validarse a nivel infraestructura aunque el total de registros sea 0."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_111_ValidarTablaRTDPuedeExistirSinRegistros_test"; }
    @Override public String setTestInstanceID() { return null; }
}