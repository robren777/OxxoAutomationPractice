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

/**Desc: Validar que al descargarse el comprobante de compra individual venga con el formato de nombre correcto:
 Individual
 OC_NÃºmero de orden de compra ( esta adentro de la orden de compra)_Fecha que esta adentro del comprobante de compra en espaÃ±ol y en el orden que se pone en el ejemplo
 Ejemplo: OC_1244280_24-07-2024
 Masivo: Pendiente
 * MTC-FT-39-E1- Validar el formato del nombre del formato Excel masivo
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS
 * @author Jorge Hernandez
 * @date Octubre 2024
 */
public class ATC_FT_39_HU8_ValidarFormatoNombreArchivoExcelMasivo extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_39_HU8_ValidarFormatoNombreArchivoExcelMasivo_test(HashMap<String,String> data){
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
        addStep("Realizar una busqueda usando los filtros");
        boolean resultStep5 = pc.seleccionarCaracteristicaFiltrado(6);
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se pudo realizar la busqueda usando los filtros");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la busqueda");
        }
        assertTrue(resultStep5, "No se pudo realizar la busqueda");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Descargar el excel masivo de los comprobantes filtrados");
        boolean resultStep6 = pc.descargaComprobantesEntregaMasivaExcel(); //Validar metodo
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se valido el boton y se realiza la descarga");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la descarga");
        }
        assertTrue(resultStep6,"No se pudo realilzar la descarga");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar descarga");
        boolean resultStep7 = pc.validarDescargaResumen();
        if (resultStep7){
            testCase.addTextEvidenceCurrentStep("Se valida la descarga");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
        }
        assertTrue(resultStep7,"No se encontro el archivo");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar el nombre de el archivo");
        boolean resultStep8 = pc.validacionFormatoNombreMasivo();
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("Se valida que el formato de el nombre de el archivo es el correcto");
        } else {
            testCase.addTextEvidenceCurrentStep("El nombre no tiene el formato establecido");
        }
        assertTrue(resultStep8,"El nombre no tiene el formato establecido");

        boolean resultStep8_1 = pc.openLastFileDownloadFolder("Excel");
        if(resultStep8_1){
            testCase.addTextEvidenceCurrentStep("Se muestra el archivo excel");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo abrir el archivo excel");
        }
        assertTrue(resultStep8_1,"No se pudo abrir el archivo excel");

    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setPrerequisites() {
        return "1.Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al descargarse el comprobante de compra individual venga con el formato de nombre correcto:\n" +
                "Individual\n" +
                "OC_NÃºmero de orden de compra ( esta adentro de la orden de compra)_Fecha que esta adentro del comprobante de compra en espaÃ±ol y en el orden que se pone en el ejemplo \n" +
                "Ejemplo: OC_1244280_24-07-2024\n" +
                "Masivo: Pendiente";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez Sanchez";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
        u.getDriver().quit();
    }
}
