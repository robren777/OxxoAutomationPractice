package WEB.cometa.scripts.mvp2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que se puede realizar bÃºsquedas por autocomplete solo por el atributo de provedor y seleccionando varios provedores al mismo tiempo
 * MTC-FT-147-HU10- Validar Que al seleccionar un proveedor por autocomplete, permite seguir seleccionando mÃ¡s proveedores
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS
 * @author Jorge Hernandez
 * @date Octubre 2024
 */
public class ATC_FT_147_HU10_ValidarSelecionProveedoPostSeleccionAutocomplete extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_147_HU10_ValidarSelecionProveedoPostSeleccionAutocomplete_test(HashMap<String, String> data){
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
            testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
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
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep3, "No es visible el elemento de Cometa");

        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a Comprobantes de compra y Resumen");
        boolean resultStep4 = pc.clicBotonCometaDirectos();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
        }
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");

        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Seleccionar Proveedor mediante el autocomplete");
        boolean resultStep5 = pc.seleccionAutocompleteProveedor();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se valida seleccion por autocomplete");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo validar la seleccion por autocomplete");
        }
        assertTrue(resultStep5, "No se pudo validar la seleccion por autocomplete");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Seleccionar otro Proveedor");
        boolean resultStep6 = pc.seleccionProveedorOp2();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se valdia la seleccion de proveedor");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo validar la seleccion de proveedor");
        }
        assertTrue(resultStep6, "No se pudo validar la seleccion de proveedor");


        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("realizar busqueda");
        boolean resultStep7 = pc.seleccionRangoFechas(6);
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Se realiza la busqueda");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la busqueda");
        }
        assertTrue(resultStep7, "No se pudo realizar la busqueda");

        boolean resultStep7_1 = pc.consultarComprobantes();
        if(resultStep7_1){
            testCase.addTextEvidenceCurrentStep("Se realiza la busqueda");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la busqueda");
        }
        assertTrue(resultStep7_1, "No se pudo realizar la busqueda");

        u.getDriver().quit();
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se puede realizar bÃºsquedas por autocomplete solo por el atributo de provedor y seleccionando varios provedores al mismo tiempo";
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
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS";
    }

    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}
