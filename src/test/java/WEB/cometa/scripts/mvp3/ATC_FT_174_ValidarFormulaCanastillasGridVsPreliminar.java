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

/**Desc: Validar que la formula de canastillas devueltas en el grid de Recepcion de Transferencias 
 * y Devoluciones coincida con la mostrada en la vista preliminar FUE en CEDIS.
 * MTC_FT_021 Validar formula de canastillas devueltas correcta en el grid vs vista preliminar CEDIS
 * Pre-requisitos: Contar con credenciales validas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Mayo 2025
 */
public class ATC_FT_174_ValidarFormulaCanastillasGridVsPreliminar extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_174_ValidarFormulaCanastillasGridVsPreliminar_test(HashMap<String, String> data) throws Exception {
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
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("El portal muestra las opciones de CEDIS");
        } else {
            testCase.addTextEvidenceCurrentStep("");
        }
        assertTrue(resultStep4, "");


        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Recepcion");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("El portal muestra la pantalla de recepcion");
        } else {
            testCase.addTextEvidenceCurrentStep("");
        }
        assertTrue(resultStep5, "");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Ingresar un rango de fechas y dar clic en el boton consultar");
        boolean resultStep6 = pc.busquedaBusquedaHistorica(4);
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("La pantalla muestra los resultados de la consulta");
        } else {
            testCase.addTextEvidenceCurrentStep("No es visible el elemento boton de consulta");
        }
        assertTrue(resultStep6, "No es visible el elemento boton de consulta");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar el numero de canastillas devueltas de un registro en el grid");
        int canastillaDevuelta = pc.contarCanastillasDevueltas();
        System.out.println("########--> "+canastillaDevuelta);
        boolean resultStep7 = canastillaDevuelta>0;
        if(resultStep7){
            testCase.addTextEvidenceCurrentStep("El sistema muestra el numero de canastillas devueltas en el grid");
        } else {
            testCase.addTextEvidenceCurrentStep("El registro no cumple con tener canastillas devueltas");
        }
        assertTrue(resultStep7, "El registro no cumple con tener canastillas devueltas");
        

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Dar clic en el boton de vista preliminar de FUE");
        boolean resultStep8 = pc.seleccionarRegistro(canastillaDevuelta);
        if(resultStep8){
            testCase.addTextEvidenceCurrentStep("El sistema muestra la vista preliminar del registro");
        } else {
            testCase.addTextEvidenceCurrentStep("El sistema no muestr la vista preliminar del registro");
        }
        assertTrue(resultStep8, "El sistema no muestr la vista preliminar del registro");
        
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Verificar que el calculo de canastillas devuletas que se muestra en el grid es correcto "
        		+ "siguiendo la formula de devolucion de mercancia  mÃ¡s las de devolucion de canastilla "
        		+ "que se encuentran en la vista preliminar.");
        boolean resultStep9 = pc.calcularCanastillasDevueltas(canastillaDevuelta);
        if(resultStep9){
            testCase.addTextEvidenceCurrentStep("El valor mostrado es correcto, encajando con la formula de la suma "
            		+ "de deloucion de mercancia y devolucion de canastilla ");
        } else {
            testCase.addTextEvidenceCurrentStep("El valor mostrado no es correcto o no encaja con la formula de a suma"
            		+ "de devolucion de mercancia y devolucion de canastilla");
        }
        assertTrue(resultStep9, "El valor mostrado no es correcto o no encaja con la formula de a suma\"\r\n"
        		+ "            		+ \"de devolucion de mercancia y devolucion de canastilla");
        
        
        u.getDriver().quit();
  
        
        
        
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
        return "Contar con credenciales validas para el perfil de CEDIS";
    }

    @Override
    public String setTestDescription() {
        return "Validar que solo se muestre el nombre de la tienda en la columna Nombre de Tienda en grid recepcion de transferencias";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac E. Garcia";
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