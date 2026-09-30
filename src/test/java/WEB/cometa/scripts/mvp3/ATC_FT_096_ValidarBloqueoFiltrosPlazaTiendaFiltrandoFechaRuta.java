package WEB.cometa.scripts.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar el bloqueo de filtros Plaza y tienda la usar el filtro Ruta
 * MTC_FT_096_Validar bloqueo plaza y tienda filtrado fecha y ruta
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Jorge Hernandez
 * @date Febrero 2025
 */
public class ATC_FT_096_ValidarBloqueoFiltrosPlazaTiendaFiltrandoFechaRuta extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_096_ValidarBloqueoFiltrosPlazaTiendaFiltrandoFechaRuta_test(HashMap<String, String> data){
        testCase.setProject_Name("Portal Web: Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        u.get(url);
        u.refresh();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();

        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
        if(resultStep2){
            testCase.addTextEvidenceCurrentStep("Se inicia sesion correctamente y se visualiza la pagina de access control");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
        }
        assertTrue(resultStep2, "No se pudo iniciar sesion");

        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc.seleccionarPortalCometaQA();
        if(resultStep3){
            testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla principal Cometa");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep3, "No es visible el elemento de Cometa");

        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se visualizan las opciones de Cedis");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Cedis");
        }
        assertTrue(resultStep4, "No se encontro el elemento Cedis");


        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a 'RecepciÃ³n de Transferencias y Devoluciones'");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de Recepcion");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion");
        }
        assertTrue(resultStep5, "No se encontro el elemento Recepcion");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep6 = pc.seleccionRangoFechas(3);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se visualizan las fechas seleccionadas");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar un rango de fechas");
        }
        assertTrue(resultStep6, "No se pudo seleccionar un rango de fechas");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar filtro Ruta en Filtros de busqueda Historica");
        boolean resultStep7 = pc.seleccionRutaBusquedaHistorica();
        boolean resultStep7_1 = pc.seleccionOpcionDinamica();
        if(resultStep7 && resultStep7_1){
            testCase.addTextEvidenceCurrentStep("Se visualizan las opciones de el filtro Ruta: Dinamica y PMO");
        } else {
            testCase.addTextEvidenceCurrentStep("No se visualizan las opciones de el filtro");
        }
        assertTrue(resultStep7, "No se visualizan las opciones de el filtro");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar bloque de filtros Plaza y Tienda");
        boolean resultStep8 = pc.validarPlazaTiendaDesactivados();
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("Se visualiza bloqueo de filtros Plaza y Tienda por uso de filtro Ruta");
        } else {
            testCase.addTextEvidenceCurrentStep("Los filtros no estan desactivados");
        }
        assertTrue(resultStep8, "Los filtros no estan desactivados");
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar el bloqueo de filtros Plaza y tienda la usar el filtro Ruta";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}