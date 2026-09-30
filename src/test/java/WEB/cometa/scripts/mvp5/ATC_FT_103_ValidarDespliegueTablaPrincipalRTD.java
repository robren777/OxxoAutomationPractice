package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.CometaBdValidationHelper;
import modelo.BaseExecution;

/**DESCRIPCIÓN: Validar que la tabla proyectada principal de Recepcion de Transferencias y Devoluciones quede desplegada en PostgreSQL Cometa y lista para poblarse.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_103 - Validar despliegue de tabla principal xxcm_cedis_rtd_proyeccion_grid
* 
*PREREQUISITOS:
*1. Acceso a PostgreSQL Cometa con permisos de lectura en information_schema y pg_catalog
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Tabla tecnica esperada public.xxcm_cedis_rtd_proyeccion_grid.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Mauricio Gurrola
* @date Julio 2026
*/
public class ATC_FT_103_ValidarDespliegueTablaPrincipalRTD extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_103_ValidarDespliegueTablaPrincipalRTD_test(HashMap<String, String> data) throws Exception {
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
        addStep("Conectarse al ambiente PostgreSQL Cometa correspondiente a la validacion");
        boolean resultStep1 = bd.validarConexionBD();
        assertTrue(resultStep1, "La conexion a la base PostgreSQL Cometa no se establecio correctamente");
        testCase.addBoldTextEvidenceCurrentStep("La conexion a la base se establece correctamente.");

        /*
         *                                             PASO 2
         ***************************************************************************/
        addStep("Ejecutar SQL: SELECT table_schema, table_name FROM information_schema.tables WHERE table_type='BASE TABLE' AND table_name='xxcm_cedis_rtd_proyeccion_grid';");
        boolean resultStep2 = bd.validarTablaBase("xxcm_cedis_rtd_proyeccion_grid");
        assertTrue(resultStep2, "No se encontro la tabla base xxcm_cedis_rtd_proyeccion_grid en information_schema.tables");
        testCase.addBoldTextEvidenceCurrentStep("La consulta retorna el objeto xxcm_cedis_rtd_proyeccion_grid y confirma que la estructura principal de RTD esta disponible para poblarse.");
    }

    @Override public void beforeTest() {}
    @Override public String setPrerequisites() { return "*1. Acceso a PostgreSQL Cometa con permisos de lectura en information_schema y pg_catalog      \r\n" + "*2.Liga de Portal Cometa"; }
    @Override public String setTestDescription() { return "Validar que la tabla proyectada principal xxcm_cedis_rtd_proyeccion_grid quede desplegada en PostgreSQL Cometa y lista para poblarse."; }
    @Override public String setTestDesigner() { return "Mauricio Gurrola"; }
    @Override public String setTestFullName() { return "ATC_FT_103_ValidarDespliegueTablaPrincipalRTD_test"; }
    @Override public String setTestInstanceID() { return null; }
}