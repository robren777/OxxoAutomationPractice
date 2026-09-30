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

/**Desc: Validar visibilidad de los filtros Ruta, Rango de Fecha Rango de hora, botones Recepcion de Transferencias y Devoluciones, Consultar y Limpiar Filtros
 * MTC_FT_124_Validar visibilidad botones y filtros en pantalla Liquidacion de Ruta
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Febrero 2025
 */
public class ATC_FT_124_ValidarVisibilidadBotonesFiltrosLiquidacionRuta extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_124_ValidarVisibilidadBotonesFiltrosLiquidacionRuta_test(HashMap<String, String> data){
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
        u.hardWait(4);

        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc.navegarCometaCedis();
 	   	if(resultStep4){
 		   testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Cedis");
 		} else {
 		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento Cedis");
 		}
 	   	assertTrue(resultStep4, "No se encontro el elemento Cedis");

        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a 'RecepciÃ³n de Transferencias y Devoluciones'");
        boolean resultStep5 = pc.cedisRecepciondTransferyDev();
        if(resultStep5){
		   testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Recepcion de Transferencias");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento Recepcion de Transferencias");
        }
        assertTrue(resultStep5, "No se encontro el elemento Recepcion de Transferencias");

        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic en el boton 'Liquidacion de Ruta' para ingresar a la pantalla 'Liquidacion de Ruta'");
        boolean resultStep6 = pc.CedisRecepcionLiquidacionRuta();
        if(resultStep6){
		   testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en LiquidaciÃ³n de Ruta");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento LiquidaciÃ³n de Ruta");
        }
        assertTrue(resultStep6, "No se encontro el elemento LiquidaciÃ³n de Ruta");

        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar visibilidad de los filtros Ruta, Rango de Fecha y Rango de hora");
        
        boolean resultStep7 = 	pc.VisibilidadElementoLiquidacionRuta("Ruta", false) &&
        						pc.VisibilidadElementoLiquidacionRuta("FechaInicio", true) &&
        						pc.VisibilidadElementoLiquidacionRuta("FechaFin", true) &&
        						pc.VisibilidadElementoLiquidacionRuta("HoraInicial", true) &&
        						pc.VisibilidadElementoLiquidacionRuta("HoraFinal", true) ;
        
        if(resultStep7){
		   testCase.addTextEvidenceCurrentStep("Los elementos de filtrado son visibles");
        } else {
		   testCase.addTextEvidenceCurrentStep("Los elementos de filtrado no son visibles");
        }
        assertTrue(resultStep7, "Los elementos de filtrado no se encontraron o no son visibles");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar visibilidad de botones Recepcion de Transferencia y Devoluciones, Consultar y Limpiar filtros");
        boolean resultStep8 = 	pc.VisibilidadElementoLiquidacionRuta("LimpiarFiltroButton", false) &&
								pc.VisibilidadElementoLiquidacionRuta("ConsultaButton", false);

		if(resultStep8){
		testCase.addTextEvidenceCurrentStep("Los elementos botones son visibles");
		} else {
		testCase.addTextEvidenceCurrentStep("Los elementos botonesno son visibles");
		}
		assertTrue(resultStep8, "Los elementos botones no se encontraron o no son visibles");
        
        
        u.getDriver().quit();
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
        return "Validar visibilidad de los filtros Ruta, Rango de Fecha Rango de hora, botones Recepcion de Transferencias y Devoluciones, Consultar y Limpiar Filtros";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales vÃ¡lidas para el perfil de CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "Isaac E. Garcia";
    }

    @Override
    public String setTestInstanceID() {
        return "";
    }

    @Override
    public void beforeTest() {

    }
}