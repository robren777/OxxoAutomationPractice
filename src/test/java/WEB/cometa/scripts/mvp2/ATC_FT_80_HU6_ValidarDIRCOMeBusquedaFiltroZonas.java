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
/**Desc: Validar que se puede realizar bÃºsquedas solo por el atributo de zonas
 * @autor Yair Lizarraga
 * @date Octubre 2024
 */
public class ATC_FT_80_HU6_ValidarDIRCOMeBusquedaFiltroZonas extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_80_HU6_ValidarDIRCOMeBusquedaFiltroZonas_test(HashMap<String, String> data){
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
         *                                           PASO 1
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
        addStep("Realizar busqueda de zona");
        boolean resultStep5 = pc.busquedaFiltroZona(6); //se manda la el rango de meses a partir de el mes actual para atras.
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se realiza la busqueda por zona");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la busqueda por zona");
        }
        assertTrue(resultStep5, "No se pudo realizar la busqueda por zona");

    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se puede realizar bÃºsquedas solo por el atributo de zona";
    }

    @Override
    public String setTestDesigner() {
        return "Yair Lizarraga";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales validas para el inicio de sesion del perfil DIRECTOS - COMERCIAL";
    }

    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}