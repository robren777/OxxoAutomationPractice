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

/**Desc: Validar dentro de la pantalla de Comprobantes de compra la funcionalidad del filtro de Rango de fechas al seleccionar una fecha inicial y una fecha final.
 * MTC_FT_086_Validar la funcionalidad del filtro Rango de fechas
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS
 * @author Jorge Hernandez
 * @date Febrero 2025
 */
public class ATC_FT_086_ValidarFuncionalidadFiltroRangoFechas extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_086_ValidarFuncionalidadFiltroRangoFechas_test(HashMap<String, String> data){
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
         * ******************************************************************************************/
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
        addStep("Dar click en filtro Rango de fechas");
        boolean resultStep6 = pc.seleccionFiltroRangoFechas();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se visualiza el calendario en el filtro Rango de fechas");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar el filtro Rango de Fechas");
        }
        assertTrue(resultStep6, "No se pudo seleccionar el filtro Rango de Fechas");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar click en una fecha inicial");
        boolean resultStep7 = pc.seleccionFechaInicialRangoFechas(5);
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("Seleccionar una fecha inicial para el rango de fechas");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar fecha inicial");
        }
        assertTrue(resultStep7, "No se pudo seleccionar fecha inicial");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Dar click en una fecha final");
        boolean resultStep8 = pc.seleccionFechaFinalRangoFechas(3);
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("Seleccionar una fecha final para el rango de fechas");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar fecha final");
        }
        assertTrue(resultStep8, "No se pudo seleccionar fecha final");

        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar formato de fechas");
        boolean resultStep9 = pc.validacionFormatoFechas("d/MM/yyyy");
        if(resultStep9){
            testCase.addTextEvidenceCurrentStep("Se corrobora que el formato de las fechas es dd/mm/aaaa");
        } else {
            testCase.addTextEvidenceCurrentStep("Los formatos de las fechas no corresponde al indicado");
        }
        assertTrue(resultStep9, "Los formatos de las fechas no corresponde al indicado");

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
        return "Validar dentro de la pantalla de Comprobantes de compra la funcionalidad del filtro de Rango de fechas al seleccionar una fecha inicial y una fecha final.";
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
