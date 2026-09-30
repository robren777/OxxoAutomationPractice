package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCIÓN: Validar que la familia tecnica RTD confirmada por desarrollo exista en PostgreSQL Cometa y cuente al menos con la tabla principal desnormalizada.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_104 - Validar estructura de xxcm_cedis_rtd_proyeccion_grid
* 
*PREREQUISITOS:
*1. Acceso a PostgreSQL Cometa con permisos de lectura
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Patron tecnico esperado xxcm_cedis_rtd% y tabla principal xxcm_cedis_rtd_proyeccion_grid.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_104_ValidarEstructuraTablaRTD extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_104_ValidarEstructuraTablaRTD_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ejecutar SQL: SELECT table_schema, table_name FROM information_schema.tables WHERE table_type='BASE TABLE' AND table_name LIKE 'xxcm_cedis_rtd%';");
        boolean resultStep1 = bd.validarFamiliaTablas("xxcm_cedis_rtd%", "xxcm_cedis_rtd_proyeccion_grid");
        assertTrue(resultStep1, "No se encontro la familia RTD esperada o falta la tabla principal xxcm_cedis_rtd_proyeccion_grid");
        testCase.addBoldTextEvidenceCurrentStep("La consulta retorna al menos la tabla xxcm_cedis_rtd_proyeccion_grid confirmada por dev. Tablas de detalle adicionales seran validadas segun objetos extras que el spike identifique.");

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Contar el total de objetos retornados por el patron xxcm_cedis_rtd%");
        boolean resultStep2 = bd.obtenerTablasPorPatron("xxcm_cedis_rtd%").size() >= 1;
        assertTrue(resultStep2, "El total de tablas con prefijo xxcm_cedis_rtd no es mayor o igual a 1");
        testCase.addBoldTextEvidenceCurrentStep("El total de tablas con prefijo xxcm_cedis_rtd es mayor o igual a 1. La tabla xxcm_cedis_rtd_proyeccion_grid esta confirmada; tablas de detalle adicionales seran registradas cuando dev las entregue.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Acceso a PostgreSQL Cometa con permisos de lectura      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que xxcm_cedis_rtd_proyeccion_grid exista dentro de la familia tecnica xxcm_cedis_rtd y confirme estructura principal disponible."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_104_ValidarEstructuraTablaRTD_test"; }
    @Override public String setTestInstanceID() { return null; }
}