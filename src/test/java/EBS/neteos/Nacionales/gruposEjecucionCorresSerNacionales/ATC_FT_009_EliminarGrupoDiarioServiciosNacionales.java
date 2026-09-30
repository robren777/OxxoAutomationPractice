package EBS.neteos.Nacionales.gruposEjecucionCorresSerNacionales;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.SQL.QuerysOracleNacionales;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Eliminar grupo de ejecucion Diario Servicios Nacionales.
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-009-Eliminar grupo de ejecucion Diario Servicios Nacionales.
 * <p>
 * PREREQUISITOS:
 * 1. Contar con la responsabilidad "MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS".
 * 2. Se necesita acceso a las pantallas de grupos de Ejecucion.
 * 3. Haber creado un grupo de ejecucion con tipo de servicio Servicios Nacionales.
 * 4. Acceso al portal web EBS
 *
 * @author David Quiroz Rodriguez
 * @date 02/Abril/2025
 */
public class ATC_FT_009_EliminarGrupoDiarioServiciosNacionales extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_009_EliminarGrupoDiarioServiciosNacionales_test (HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String grupoEjecucion = data.get("grupoEjecucion");
        String tipoServicio = data.get("tipoServicio");
        String servicioBD=data.get("servicioBD");
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
        String queryConsulta = QuerysOracleNacionales.queryConsultaEliminacionGrupoCorresponsaliasYSerNacionales(grupoEjecucion, servicioBD);
        /*Pasos para el caso de prueba
         * */
        //Paso 1
        addStep("Ingresar al ambiente de EBS México.");
        MTLocales.LoginIEEBS();
        u.hardWait(15);
        //Paso 2
        addStep("Seleccionar la responsabilidad 'MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS.'");
        MTNacionales.abrirCarpetaGL17ADM(carpetaResponsabilidad);
        //Paso 3
        addStep("Seleccionar 'Frecuencia Facturacion SN / Corresponsalias.'");
        MTNacionales.openFrecuencia_Facturacion();
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
        return "ATC_FT_009_EliminarGrupoDiarioServiciosNacionales_test";
    }

    @Override
    public String setTestDescription() {
        return "Eliminacion correcta del Grupo de Ejecucion Diario Servicios Nacionales";
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
        return " * 1. Contar con la responsabilidad \"MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS\".\n" +
                " * 2. Se necesita acceso a las pantallas de grupos de Ejecucion.\n" +
                " * 3. Haber creado un grupo de ejecucion con tipo de servicio Servicios Nacionales.\n" +
                " * 4. Acceso al portal web EBS";
    }

    @Override
    public void beforeTest() {

    }
}