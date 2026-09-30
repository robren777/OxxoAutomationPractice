package EBS.locales.gruposDeEjecucionYExclusion;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Clase de utileria para Dar de alta un grupo ya existente
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_012_Dar de alta un grupo ya existente
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 * @Mantenimiento 08/04/2025
 */
public class ATC_FT_012_DarDeAltaGrupoYaExistente extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_012_DarDeAltaGrupoYaExistente_test(HashMap<String, String> data) throws Exception {
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
        /*Variables SQL
         */
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
        addStep("Dar clic en el boton 'Crear Grupo'.");
        MTLocales.theUserSelectBotonCrearGrupo();
        MTLocales.theUserWaitsLeyendaCrearGrupo();
        //Paso 5
        addStep("En la pantalla 'Crear Grupo' capturar la siguiente informacion: Grupo Ejecucion: DIARIO (Captura libre) Tipo de Servicio: LOCAL (Seleccionar de la lista de Valores) Hora Ejecucion: Seleccionar hora de ejecucion.");
        MTLocales.theUserSentTextInGrupoEjecucion(grupoEjecucion);
        MTNacionales.buscarYseleccionarGrupoEjecucionParaCreacionDeGrupo(tipoServicio);
        testCase.addScreenShotCurrentStep("Se muestran los parametros ingresados");
        //Paso 6
        addStep("Dar clic en el boton 'Aplicar'.");
        MTNacionales.theUserClickBtnAplicar();
        //Paso 7
        addStep("Validar que se muestre el mensaje de error porque el grupo ya existe");
        MTLocales.mensajeErrorCreacionDeGrupo(grupoEjecucion);
        //Paso 8
        addStep("Cerrar el navegador");
        MTLocales.cerrarSesionEnBotonApagado();
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_012_DarDeAltaGrupoYaExistente_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Dar de alta un grupo ya existente";
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
