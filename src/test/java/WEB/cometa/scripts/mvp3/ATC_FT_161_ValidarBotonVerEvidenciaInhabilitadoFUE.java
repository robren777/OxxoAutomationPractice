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

/**Desc: Validar que al abrir vista preeliminar de un FUE sin devolucion de Canastillas este muestre el boton de Ver Evidencia inhabilitado
 * MTC_FT_161_Validar boton Ver Evidencia inhabilitado en FUE sin devolucion de Canastillas
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Jorge Hernandez
 * @date Marzo 2025
 */
public class ATC_FT_161_ValidarBotonVerEvidenciaInhabilitadoFUE extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_161_ValidarBotonVerEvidenciaInhabilitadoFUE_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

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
        pc.esperarCarga(3);
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
        addStep("Realizar una consulta con los filtros");
        boolean resultStep7 = pc.consultaRecepcionTiendaFechas();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("");
        } else {
            testCase.addTextEvidenceCurrentStep("");
        }
        assertTrue(resultStep7, "");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Seleccionar un FUE que no tenga canstillas devueltas");
        boolean resultStep8 = pc.seleccionarFueSinCanastillasDevueltas();
        if(!resultStep8){
            testCase.addTextEvidenceCurrentStep("Canastillas devueltas mayores a 0");
        } else {
            testCase.addTextEvidenceCurrentStep("Canastillas devueltas 0");
        }
        assertTrue(!resultStep8, "Canastillas devueltas 0");


        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que el boton de Ver evidencia este inhabilitado");
        boolean resultStep9 = pc.validarBotonVerEvidenciaInhabilitado();
        if(resultStep9){
            testCase.addTextEvidenceCurrentStep("La pagina muestra el boton de Ver evidencia inhabilitado");
        } else {
            testCase.addTextEvidenceCurrentStep("La pagina muestra el boton habilitado");
        }
        assertTrue(resultStep9, "La pagina muestra el boton habilitado");

    }
    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al abrir vista preeliminar de un FUE sin devolucion de Canastillas este muestre el boton de Ver Evidencia inhabilitado";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez";
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}