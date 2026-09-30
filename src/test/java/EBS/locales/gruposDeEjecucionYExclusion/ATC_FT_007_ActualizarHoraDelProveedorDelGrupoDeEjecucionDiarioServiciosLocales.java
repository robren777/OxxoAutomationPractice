package EBS.locales.gruposDeEjecucionYExclusion;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.SQL.QuerysOracleLocales;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Actualizar Hora del proveedor del Grupo de Eejcución-DIARIO/SERVICIOS LOCALES
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_007_Actualizar Hora del proveedor del Grupo de Eejcución-DIARIO/SERVICIOS LOCALES
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 * @Mantenimiento 07/04/2025
 */
public class ATC_FT_007_ActualizarHoraDelProveedorDelGrupoDeEjecucionDiarioServiciosLocales extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_007_ActualizarHoraDelProveedorDelGrupoDeEjecucionDiarioServiciosLocales_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String grupoEjecucion = data.get("grupoEjecucion");
        String tipoServicio = data.get("tipoServicio");
        String nuevaHora = data.get("nuevaHora");
        ArrayList<String> descripcionSeparada = new ArrayList<>();
        //Variables de leyenda convertida
        String seperador = ",";
        String nuevacadena = nuevaHora.replace(" ", ",");
        System.out.println(nuevacadena);
        String[] arr = nuevacadena.split(java.util.regex.Pattern.quote(seperador));
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
        String queryConsulta=QuerysOracleLocales.queryConsultaHoraNueva(grupoEjecucion);
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
        addStep("En la seccion Principal de búsqueda mediante la lista de valores de Tipo Servicio, seleccionar el Tipo de Servicio LOCAL");
        MTNacionales.buscarYSeleccionarTipoServicioNacionales(tipoServicio);
        //Paso 6
        addStep("Presionar el boton 'Buscar'");
        MTNacionales.busquedaGrupo();
        //Paso 7
        addStep("En la tabla de grupo, seleccionar el icono 'Ver Items' ");
        MTNacionales.busquedaDeItems();
        //Paso 8
        addStep("Dar click en el boton 'Actualizar Item' del proveedor");
        MTNacionales.actualizarItem();
        //Paso 9
        addStep("Selecciona la nueva hora de la lista de valores");
        MTNacionales.modificarHora(nuevaHora);
        //Paso 10
        addStep("Al presionar el boton 'Aplicar', se guarda la informacion");
        MTNacionales.theUserClickBtnAplicar();
        testCase.addScreenShotCurrentStep("Se ingresan los parametros");
        //Paso 11
        addStep("Se crea registro en tabla de historico con codigo movimiento");
        SQLResult validarQuery1 = dbAvebqa.executeQuery(queryConsulta);
        testCase.addQueryEvidenceCurrentStep(validarQuery1);
        //Paso 12
        addStep("Presionar el boton 'Ok', para abrir a la página principal");
        MTNacionales.botonOkActualizacionItem();
        //Paso 13
        addStep("Al realizar la búsqueda del grupo, se muestra la tabla con el registro actualizado");
        MTNacionales.buscarYseleccionarGrupoEjecucion(grupoEjecucion);
        MTNacionales.buscarYSeleccionarTipoServicioNacionales(tipoServicio);
        MTNacionales.busquedaGrupo();
        u.hardWait(2);
        MTNacionales.busquedaDeItems();
        String texto = CommonMethodsSikuli.getAllTextPage();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se selecciona el texto de la pagina");
        System.out.println(texto);
        u.hardWait(2);
        for (String ele : arr) {
            descripcionSeparada.add(ele);
            System.out.println(ele);
        }
        for (String ele : descripcionSeparada) {
            if (texto.contains(ele)) {
                testCase.addScreenShotCurrentStep("El elemento perteneciente a la nueva hora " + ele + " Si se muestra el grupo de ejecucion");
                testCase.addTextEvidenceCurrentStep("El elemento perteneciente a la nueva hora " + ele + " Si se muestra el grupo de ejecucion");
                System.out.println("Si se muestra en el item la nueva hora");
            } else {
                testCase.addScreenShotCurrentStep("El elemento perteneciente al grupo " + ele + " No se muestra el grupo de ejecucion");
                System.out.println("No se encuentra el mensaje");
            }
        }
        testCase.addTextEvidenceCurrentStep("Se muestra item con la nueva hora");
        //Paso 14
        addStep("Cerrar el navegador");
        MTNacionales.cerrarSesionEBS();
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_007_ActualizarHoraDelProveedorDelGrupoDeEjecucionDiarioServiciosLocales_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Actualizar Hora del proveedor del Grupo de Eejcución-DIARIO/SERVICIOS LOCALES";
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
