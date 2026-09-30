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

/**Desc: Validar que la informacion de la columna debe ser unicamente el nombre de la tienda, sin incluir el CR
 * MTC_FT_001 Validar formato de nombre tienda en comprobante de compra directos grid
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de directos
 * @author Jorge Hernandez
 * @date Mayo 2025
 */
public class ATC_FT_162_ValidarFormatoNombreTiendaComprobanteGrid extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_162_ValidarFormatoNombreTiendaComprobanteGrid_test(HashMap<String, String> data) throws Exception {
        testCase.setProject_Name("Portal Cometa");

        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        String proveedor = data.get("proveedor");

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
        boolean resultStep6 = pc.consultaProveedorComprobantesCompra(proveedor, 0);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("La pantalla muestra la informacion agregada");
        } else{
            testCase.addTextEvidenceCurrentStep("La pantalla no muestra la informacion");
        }
        assertTrue(resultStep6, "La pantalla no muestra la informacion");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar clic en el boton consultar");
        boolean resultStep7 = pc.consultarComprobantes();
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("La pantalla muestra los resultados de la consulta");
        } else{
            testCase.addTextEvidenceCurrentStep("La pantalla no muestra resultados de la consulta");
        }
        assertTrue(resultStep7, "La pantalla no muestra resultados de la consulta");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que el nombre en el grid esta en el formato correcto");
        boolean resultStep8 = pc.validarNombreTiendaNoCRGrid();
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("El sistema muestra correctamente el nombre sin el CR");
        } else{
            testCase.addTextEvidenceCurrentStep("El sistema muestra el nombre con el CR");
        }
        assertTrue(resultStep8, "El sistema muestra el nombre con el CR");

        
    }
    @Override
    public void beforeTest() {}

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales validas para el perfil de Directos";
    }

    @Override
    public String setTestDescription() {
        return "Validar que la informacion de la columna debe ser unicamente el nombre de la tienda, sin incluir el CR";
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