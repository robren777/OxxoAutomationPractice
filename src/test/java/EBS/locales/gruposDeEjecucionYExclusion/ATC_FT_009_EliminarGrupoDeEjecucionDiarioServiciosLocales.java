package EBS.locales.gruposDeEjecucionYExclusion;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.SQL.QuerysOracleLocales;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Eliminar Grupo de Ejecución-DIARIO/SERVICIOS LOCALES
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_009_Eliminar Grupo de Ejecución-DIARIO/SERVICIOS LOCALES
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 * @Mantenimiento 08/04/2025
 */
public class ATC_FT_009_EliminarGrupoDeEjecucionDiarioServiciosLocales extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_009_EliminarGrupoDeEjecucionDiarioServiciosLocales_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String grupoEjecucion = data.get("grupoEjecucion");
        String tipoServicio = data.get("tipoServicio");
        /*Utilerias
         * */
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        metodosLocales MTLocales = new metodosLocales(u, testCase, ebsUrl, userName, password);
        metodosNacionales MTNacionales = new metodosNacionales(u, testCase);
        /*Utilerias SQL
         * */
        SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
                GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
        /*Variables SQL
         */
        String queryConsulta = QuerysOracleLocales.queryConsultaEliminacionGrupo(grupoEjecucion);
        /*Pasos para el caso de prueba
         * */
        //Paso 1
        addStep("Ingresar al ambiente de EBS Mexico.");
        MTLocales.LoginIEEBS();
        u.hardWait(15);
        //Paso 2
        addStep("Seleccionar la responsabilidad 'MXOXXO-AR10MON-TRANSACCIONES.'");
        MTLocales.theUserSelectsTheValueOfTheEBSPage(carpetaResponsabilidad);
        //Paso 3
        addStep("Seleccionar 'Frecuencia Facturacion SN / Corresponsalias.'");
        MTLocales.theUserSelectTheLinkFrecuenciaFacturacion();
        //Paso 4
        addStep("En la seccion Principal de búsqueda mediante la lista de valores de Grupo, seleccionar el grupo DIARIO");
        MTNacionales.buscarYseleccionarGrupoEjecucion(grupoEjecucion);
        //Paso 5
        addStep("En la seccion Principal de búsqueda mediante la lista de valores de Tipo Servicio, seleccionar el Tipo de Servicio CORRESPONSALIAS");
        MTNacionales.buscarYSeleccionarTipoServicioNacionales(tipoServicio);
        //Paso 6
        addStep("Presionar el boton 'Buscar'");
        MTNacionales.busquedaGrupo();
        //Paso 7
        addStep("Presionar el ícono 'Eliminar Grupo'");
        MTNacionales.borrarGrupoEjecucionCorresponsaliasYServiciosNacionales();
        MTNacionales.botonSiEliminarGrupoOItemsEjecucionCorresponsaliasYServiciosNacionales();
        MTNacionales.confirmacionDeEliminacionDeGrupo();
        //Paso 8
        addStep("Se crea registro en tabla de historico con codigo movimiento BAJA y descripcion movimiento");
        SQLResult validarQuery1 = dbAvebqa.executeQuery(queryConsulta);
        testCase.addQueryEvidenceCurrentStep(validarQuery1);
        //Paso 9
        addStep("Al buscar el Grupo de Ejecucion, no se muestra en la tabla");
        MTNacionales.busquedaGrupo();
        //Paso 10
        addStep("Cerrar el navegador");
        MTNacionales.cerrarSesionEBS();
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }
    @Override
    public String setTestFullName() {
        return "ATC_FT_009_EliminarGrupoDeEjecucionDiarioServiciosLocales_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Eliminar Grupo de Ejecución-DIARIO/SERVICIOS LOCALES";
    }

    @Override
    public String setTestDesigner() {
        return "davidq1@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return " * \t- Acceso al portal web EBS\n" +
                " *  - Tener responsabilidad AR a nivel plaza.\n";
    }

    @Override
    public void beforeTest() {

    }
}
