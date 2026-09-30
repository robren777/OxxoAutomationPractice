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

/**Desc: Validar dentro de la pantalla de Comprobantes de compra la funcionabilidad de la multiseleccion en el filtro Proveedor.
 * MTC_FT_074 Validar que al agregar multiples opciones en el filtro Proveedor este no se cierre automaticamente
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS
 * @author Jorge Hernandez
 * @date Febrero 2025
 */
public class ATC_FT_074_ValidarMultiSeleccionFiltroProveedorNoCierreAutomaticamente extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_074_ValidarMultiSeleccionFiltroProveedorNoCierreAutomaticamente_test(HashMap<String, String> data){
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
        addStep("Ingresar a Directos");
        boolean resultStep4 = pc.clicBotonCometaDirectos();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se visualizan las opciones de Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento Directos");


        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Comprobantes de compra");
        boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se visualiza la pantalla de Comprobantes de compra");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
        }
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar click en filtro Proveedor");
        boolean resultStep6 = pc.seleccionProveedor();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se visualizan las opciones del multiselect listados de Proveedor");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el filtro Proveedor");
        }
        assertTrue(resultStep6, "No se encontro el filtro Proveedor");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar click a una(s) opcion(es) de la lista");
        boolean resultStep7 = pc.seleccionMultiProveedor();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Se permite la seleccion de opciones sin cerrar la ventana despues de la seleccion");
        } else {
            testCase.addTextEvidenceCurrentStep("La lista de opciones se cierra al seleccionar una opcion");
        }
        assertTrue(resultStep7, "La lista de opciones se cierra al seleccionar una opcion");
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @AfterMethod
    public void afterMethod(){
//        u.getDriver().quit();
    }

    @Override
    public String setTestDescription() {
        return "Validar dentro de la pantalla de Comprobantes de compra la funcionabilidad de la multiseleccion en el filtro Proveedor.";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS";
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
