package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc:  Validar que el botÃ³n de descarga nos de la opciÃ³n de descargar un excel con
 * los comprobantes, el excel debe contar con las siguientes columnas: FA, Nombre del
 * proveedor, RegiÃ³n, Plaza, CR Tienda, Fecha, Orden de Compra, RemisiÃ³n, Folio Goma,
 * Fecha ADMVA, Total General a la Venta, Cifra del proveedor, Movt Valorizados (nÃºmero de
 * articulos recibidos), Total Costo
 * MTC-FT-58-E6 - Validar descarga de Resumen de comprobantes de entrega â€“ Comercial
 * @author Jorge Hernandez
 * @date Junio 2024
*/
public class ATC_FT_58_ValidarDescargaResumenComprobantesEntrega extends BaseExecution{

    SeleniumUtil u;
    @Test(dataProvider = "data-provider")
    public void ATC_FT_58_ValidarDescargaResumenComprobantesEntrega_test(HashMap<String, String> data) throws Exception{
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
        addStep("Dar Click en boton 'Descargar'");
        boolean resultStep5 = pc.validarBotonDescargaComprobantesCompra();
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se valida el boton y se hace la descarga");
        } else {
            testCase.addTextEvidenceCurrentStep("No se econtro el boton y no se pudo realizar la descarga");
        }
        assertTrue(resultStep5, "No se econtro el boton y no se pudo realizar la descarga");

        /*
		 *                                             PASO 6
		 ******************************************************************************************/
        addStep("Validacion de descarga");
        boolean resultStep6 = pc.ExcelHeaderValidatorComprobanteEntrega();
        if(resultStep6){
            testCase.addTextEvidenceCurrentStep("Se validaron los headers de el archivo");
        } else {
            testCase.addTextEvidenceCurrentStep("Los headers no corresponden a los esperados");
        }
        assertTrue(resultStep6, "Los headers no corresponden a los esperados");

        boolean resultStep6_1 = pc.openLastFileDownloadFolder("Excel");
        if(resultStep6_1){
            testCase.addTextEvidenceCurrentStep("Se valido la descarga y se abrio el archivo");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
        }
        assertTrue(resultStep6_1, "No se encontro el archivo");

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
        return "Validar que el botÃ³n de descarga nos de la opciÃ³n de descargar un excel con \n" +
                "los comprobantes, el excel debe contar con las siguientes columnas: FA, Nombre del \n" +
                "proveedor, RegiÃ³n, Plaza, CR Tienda, Fecha, Orden de Compra, RemisiÃ³n, Folio Goma, \n" +
                "Fecha ADMVA, Total General a la Venta, Cifra del proveedor, Movt Valorizados (nÃºmero de \n" +
                "articulos recibidos), Total Costo.";
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
