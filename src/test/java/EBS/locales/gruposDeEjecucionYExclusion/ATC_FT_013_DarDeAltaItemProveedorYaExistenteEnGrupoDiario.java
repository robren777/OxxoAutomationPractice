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
 * DESCRIPCION: Clase de utileria para Dar de alta item proveedor ya existente en grupo DIARIO
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_013_Dar de alta item proveedor ya existente en grupo DIARIO
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Tener responsabilidad AR a nivel plaza.
 *
 * @author David Quiroz Rodriguez
 * @date 20/Febrero/2025
 * @Mantenimiento 08/04/2025
 */
public class ATC_FT_013_DarDeAltaItemProveedorYaExistenteEnGrupoDiario extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_013_DarDeAltaItemProveedorYaExistenteEnGrupoDiario_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String grupoEjecucion = data.get("grupoEjecucion");
        String tipoServicio = data.get("tipoServicio");
        String numeroProveedor = data.get("numeroProveedor");
        String hora = data.get("hora");
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
        addStep("Seleccionar el boton 'Crear'");
        MTNacionales.crearItem();
        //Paso 9
        addStep("Selecciona el Proveedor");
        MTNacionales.buscarYSeleccionarNumeroProveedor(numeroProveedor);
        //Paso 10
        addStep("Seleccionar la hora del Item");
        MTNacionales.ingresarHora(hora);
        testCase.addScreenShotCurrentStep("Se ingresan los parametros");
        //Paso 11
        addStep("Al presionar el boton 'Crear Item', se guarda la informacion");
        MTNacionales.crearItemServicio();
        //Paso 12
        addStep("Validar que se muestren los mensajes de error de asignacion de proveedor al grupo de ejecucion");
        MTLocales.mensajeErrorCrearItem(numeroProveedor);
        //Paso 13
        addStep("Cerrar el navegador");
        MTNacionales.cerrarSesionEBS();
        u.hardWait(3);
        if (u != null) {
            u.quit();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_013_DarDeAltaItemProveedorYaExistenteEnGrupoDiario_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se pueda Dar de alta item proveedor ya existente en grupo DIARIO";
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
