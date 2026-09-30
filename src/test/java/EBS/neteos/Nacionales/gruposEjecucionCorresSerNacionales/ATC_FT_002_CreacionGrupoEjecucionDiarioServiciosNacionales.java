package EBS.neteos.Nacionales.gruposEjecucionCorresSerNacionales;

import java.util.HashMap;
import java.util.Random;

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
 * DESCRIPCION: Clase de utileria para Creacion de grupo de ejecucion Diario Servicios Nacionales.
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_002_Creacion de grupo de ejecucion Diario Servicios Nacionales.
 * <p>
 * PREREQUISITOS:
 * 1. Contar con la responsabilidad "MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS".
 * 2. Se necesita acceso a las pantallas de grupos de Ejecucion. Nota: La hora del Grupo es un valor informativo
 * 3. Acceso al portal web EBS
 *
 * @author David Quiroz Rodriguez
 * @date 27/Marzo/2025
 */

public class ATC_FT_002_CreacionGrupoEjecucionDiarioServiciosNacionales extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_CreacionGrupoEjecucionDiarioServiciosNacionales_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String grupoEjecucion = data.get("grupoEjecucion");
        String tipoServicio = data.get("tipoServicio");
        String servicioBD = data.get("servicioBD");
        Random aleatorio = new Random(System.currentTimeMillis()); //Se declara para obtener un numero random
        int num = aleatorio.nextInt(10000); // en un rango del 1 al 1000
        String nuevoNombreGrupoEjecucion = grupoEjecucion + num; // concatenamos el grupoEjecuion + el numero random
        System.out.println(nuevoNombreGrupoEjecucion); // imprime para ver el resultado de la concatenacion
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
        String queryCreacionDeGruposEjecucionNacionales1 = QuerysOracleNacionales.queryConsultaGrupoEjecucionCorresponsaliasyNacionales1(nuevoNombreGrupoEjecucion, servicioBD);
        String queryCreacionDeGruposEjecucionNacionales2 = QuerysOracleNacionales.queryConsultaGrupoEjecucionCorresponsaliasyNacionales2(nuevoNombreGrupoEjecucion, servicioBD);

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
        addStep("Dar clic en el boton 'Crear Grupo'.");
        MTLocales.theUserSelectBotonCrearGrupo();
        MTLocales.theUserWaitsLeyendaCrearGrupo();
        //Paso 5
        addStep("En la pantalla 'Crear Grupo' capturar la siguiente informacion: Grupo Ejecucion: DIARIO (Captura libre) Tipo de Servicio: SERVICIOS NACIONALES (Seleccionar de la lista de Valores) Hora Ejecucion: Seleccionar hora de ejecucion.");
        MTLocales.theUserSentTextInGrupoEjecucion(nuevoNombreGrupoEjecucion);
        MTNacionales.buscarYseleccionarGrupoEjecucionParaCreacionDeGrupo(tipoServicio);
        testCase.addScreenShotCurrentStep("Se muestran los parametros ingresados");
        //Paso 6
        addStep("Dar clic en el boton 'Aplicar'.");
        MTNacionales.theUserClickBtnAplicar();
        //Paso 7
        addStep("Se crea registro en tabla de historico XXFC_AP_GPOS_SNC_HIST_ALL con codigo movimiento ALTA y descripcion movimiento");
        SQLResult validarQuery = dbAvebqa.executeQuery(queryCreacionDeGruposEjecucionNacionales1);
        SQLResult validarQuery2 = dbAvebqa.executeQuery(queryCreacionDeGruposEjecucionNacionales2);
        testCase.addQueryEvidenceCurrentStep(validarQuery);
        testCase.addQueryEvidenceCurrentStep(validarQuery2);
        //Paso 8
        addStep("Volver a la pantalla 'Grupos'");
        u.hardWait(2);
        MTNacionales.botonConsultarGruposCreacionGrupos();
        u.hardWait(3);
        //Paso 9
        addStep("Colocar la siguiente informacion: Grupo: Nombre del grupo que se creo Tipo Servicio: CORRESPONSALIAS Y dar clic en el boton 'Buscar'.");
        MTNacionales.buscarYseleccionarGrupoEjecucion(nuevoNombreGrupoEjecucion);
        MTNacionales.buscarYSeleccionarTipoServicioNacionales(tipoServicio);
        MTNacionales.busquedaGrupo();
        u.hardWait(10);
        //Paso 10
        addStep("Validar que se muestra el grupo creado.");
        String texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
        if (texto.contains(nuevoNombreGrupoEjecucion)) {
            testCase.addScreenShotCurrentStep("Se muestra el nombre del grupo creado");
            testCase.addTextEvidenceCurrentStep("El nombre del grupo creado " + nuevoNombreGrupoEjecucion + " Si se encuentra en el apartado de grupos de ejecucion de EBS");
        } else {
            testCase.addTextEvidenceCurrentStep("El nombre del grupo que creaste " + nuevoNombreGrupoEjecucion + " No se encuentra en el apartado de grupos de ejecucion de EBS");
            System.out.println("Se procede a cerrar script ya que no existe el grupo");
            System.exit(0);
        }
        testCase.addScreenShotCurrentStep("Se muestra la tabla donde se aloja el grupo de ejecucion con sus caracteriticas");
        //Paso 11
        addStep("Cerrar el navegador");
        MTNacionales.cerrarSesionEBS();
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_CreacionGrupoEjecucionDiarioServiciosNacionales_test";
    }

    @Override
    public String setTestDescription() {
        return "Creacion correcta del Grupo de Ejecucion Diario Servicios Nacionales";
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
                " * 2. Se necesita acceso a las pantallas de grupos de Ejecucion. Nota: La hora del Grupo es un valor informativo\n" +
                " * 3. Acceso al portal web EBS";
    }

    @Override
    public void beforeTest() {

    }
}