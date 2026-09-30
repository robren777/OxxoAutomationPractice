package EBS.neteos.Nacionales.gruposEjecucionCorresSerNacionales;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.SQL.QuerysOracleNacionales;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Actualizacion hora del proveedor del grupo de ejecucion Diario Corresponsalias.
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_007_Actualizacion hora del proveedor del grupo de ejecucion Diario Corresponsalias.
 * <p>
 * PREREQUISITOS:
 * 1. Contar con la responsabilidad "MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS".
 * 2. Se necesita acceso a las pantallas de grupos de Ejecucion.
 * 3. Haber creado un grupo de ejecucion con el tipo de servicio Corresponsalias.
 * 4. Haber asignado un proveedor al grupo de ejecucion con tipo de servicio Corresponsalias.
 * 5. Acceso al portal web EBS
 *
 * @author David Quiroz Rodriguez
 * @date 28/Marzo/2025
 */
public class ATC_FT_007_ActualizacionHoraProveedorGrupoDiarioCorresponsalias extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_007_ActualizacionHoraProveedorGrupoDiarioCorresponsalias_test(HashMap<String, String> data) throws Exception {
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
        String queryConsulta = QuerysOracleNacionales.queryConsultarNuevaHoraCorresponsaliasYServiciosNacionalesProveedor(grupoEjecucion, tipoServicio);
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
        testCase.addScreenShotCurrentStep("Se ingresan los nuevos valores que tendra servicio, descripcion y la hora");
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
        return "ATC_FT_007_ActualizacionHoraProveedorGrupoDiarioCorresponsalias_test";
    }

    @Override
    public String setTestDescription() {
        return "Actualizacion correcta de la hora del proveedor del Grupo de Ejecucion Diario Corresponsalias";
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
        return " 1. Contar con la responsabilidad \"MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS\".\n" +
                " 2. Se necesita acceso a las pantallas de grupos de Ejecucion.\n" +
                " 3. Haber creado un grupo de ejecucion con el tipo de servicio Corresponsalias.\n" +
                " 4. Haber asignado un proveedor al grupo de ejecucion con tipo de servicio Corresponsalias.\n" +
                " 5. Acceso al portal web EBS";
    }

    @Override
    public void beforeTest() {

    }
}