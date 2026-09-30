package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los registros mostrados coincidan con las caracterÃ­sticas especificadas en el filtro seleccionado.
* MTC-FT-57 -E6- Validar la funcionalidad de filtrado en Comprobantes de Compra â€“ Comercial.
* @author Jorge Hernandez
* @date Junio 2024
*/
public class ATC_FT_57_ValidarFuncionalidadFiltradoComprobantesCompra extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_57_ValidarFuncionalidadFiltradoComprobantesCompra_test(HashMap<String, String> data) throws Exception{
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
        addStep("Seleccionar una caracteristica especifica para el filtrado de los registros");
        boolean resultStep5 =  pc.seleccionarCaracteristicaFiltradoValidacionFecha(6); // parametrpara indicar al metodo cuantos meses retroceder en el rango de fechas
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se valida que se realice la seleccion y consulta");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la seleccion y consulta");
        }
        assertTrue(resultStep5, "No se pudo realizar la seleccion y consulta");

         /*
		 *                                             PASO 6
		 ******************************************************************************************/
        addStep("Seleccionar del filtro de calendario la fecha del dia siguiente");
        boolean resultStep6 = pc.limpiarFiltros();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se realizo el limpiado de filtros");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar el limpiado de filtros");
        }
        assertTrue(resultStep6, "No se pudo realizar el limpiado de filtros");
        u.hardWait(3);

        boolean resultStep6_1 =  pc.seleccionarfechaManana();
        if(resultStep6_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar fecha de manana");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar fecha de manana");
        }
        assertTrue(resultStep6_1, "No se pudo seleccionar fecha de manana");

        boolean resultStep6_2 = pc.consultarComprobantes();
        if(resultStep6_2){
            testCase.addTextEvidenceCurrentStep("Se pudo realizar la consulta de comprobantes");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la consulta de comprobantes");
        }
        assertTrue(resultStep6_2, "No se pudo realizar la consulta de comprobantes");

        boolean resultStep6_3 = pc.validarNoinfoCometaBusqueda();
        if(resultStep6_3){
            testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de No Informacion");
        } else {
            testCase.addTextEvidenceCurrentStep("No se muestra el mensaje de No Informacion");
        }
        assertTrue(resultStep6_3, "No se muestra el mensaje de No Informacion");

        u.quit();
        
    }

    @Override
    public void beforeTest() {

    }

    @Override
    public String setPrerequisites() {
        return "1. Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil COMERCIAL \n" +
                "de Directos.";
    }

    @Override
    public String setTestDescription() {
        return "Validar que los registros mostrados coincidan con las caracterÃ­sticas \n" +
                "especificadas en el filtro seleccionado.";
    }

    @Override
    public String setTestDesigner() {
        return "Jorge Hernandez Sanchez";
    }

    @Override
    public String setTestFullName() {
        return null;
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

}
