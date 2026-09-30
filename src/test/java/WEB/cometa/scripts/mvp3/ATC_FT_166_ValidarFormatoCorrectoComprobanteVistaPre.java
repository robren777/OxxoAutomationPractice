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

/**Desc: Validar el formato de Total General a la Venta, la inicial de cada palabra debe estar en mayuscula en la vista preliminar del comprobante de compra, que coincida con el que se tiene establecido en el HTML de directos
 * MTC_FT_07 Validar formato correcto en comprobante de compra directos vista preliminar
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de directos
 * @author Jorge Hernandez
 * @date Mayo 2025
 */
public class ATC_FT_166_ValidarFormatoCorrectoComprobanteVistaPre extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_166_ValidarFormatoCorrectoComprobanteVistaPre_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        String proveedor = data.get("proveedor");
        String txtCampo = data.get("nombreCampo");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga del portal");
        u.get(url);
        u.refresh();
        pc.esperarCarga(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();

        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar credenciales correspondientes al perfil de cometa para inicio de sesion");
        boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
        if(resultStep2){
            testCase.addTextEvidenceCurrentStep("El sistema inicia sesion correctamente y se muestra la pagina de access control");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
        }
        assertTrue(resultStep2, "No se pudo iniciar sesion");

        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar al portal cometa desde access control");
        boolean resultStep3 = pc.seleccionarPortalCometaQA();
        if(resultStep3){
            testCase.addTextEvidenceCurrentStep("El sistema muestra la pantalla principal del portal cometa");
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
            testCase.addTextEvidenceCurrentStep("El portal muestra la pantalla de directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Comprobantes de Compra");
        boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("El portal muestra la pantalla de comprobantes");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
        }
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Ingresar un proveedor, rango de fechas");
        boolean resultStep6 = pc.consultaProveedorComprobantesCompra(proveedor, 1);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("La pantalla muestra la informacion agregada");
        } else{
            testCase.addTextEvidenceCurrentStep("La pantalla no muestra la informacion");
        }
        assertTrue(resultStep6, "La pantalla no muestra la informacion");

        boolean resultStep6_1 = pc.consultarComprobantes();
        if(resultStep6_1){
            testCase.addTextEvidenceCurrentStep("La pantalla muestra los resultados de la consulta");
        } else{
            testCase.addTextEvidenceCurrentStep("La pantalla no muestra resultados de la consulta");
        }
        assertTrue(resultStep6_1, "La pantalla no muestra resultados de la consulta");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar clic en un comprobante de compra para abrir la vista preliminar");
        boolean resultStep7 = pc.seleccionarPrimerComprobanteCompra();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente la vista preliminar del comprobante de compra");
        } else{
            testCase.addTextEvidenceCurrentStep("El sistema no muestra la vista preliminar del comprobante de compra");
        }
        assertTrue(resultStep7, "El sistema no muestra la vista preliminar del comprobante de compra");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que el apartado de Total General a la Venta este en el formato correcto");
        boolean resultStep8 = pc.validarformatoTotalGeneralVenta(txtCampo);
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente el apartado con su formato correcto con el nombre Total General a la Venta");
        } else{
            testCase.addTextEvidenceCurrentStep("El sistema muestra el apartado con un formato incorrecto");
        }
        assertTrue(resultStep8, "El sistema muestra el apartado con un formato incorrecto");
  
        
        
        
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
        return "Contar con credenciales vÃ¡lidas para el perfil de directos";
    }

    @Override
    public String setTestDescription() {
        return "Validar el formato de Total General a la Venta, la inicial de cada palabra debe estar en mayuscula en la vista preliminar del comprobante de compra, que coincida con el que se tiene establecido en el HTML de directos";
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