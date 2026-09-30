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

/**Desc: Validar los tipos de filtrado por fecha mediante el filtro fecha
 * MTC_FT_131_Validar filtrado por fecha
 * Pre-requisitos: Contar con credenciales vÃ¡lidas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Febrero 2025
 */
public class ATC_FT_131_ValidarFiltradoFecha extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_131_ValidarFiltradoFecha_test(HashMap<String, String> data){
        testCase.setProject_Name("Portal Web: Cometa");
        /*
         * Variables
         ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");
        String rutaDinamica = data.get("rutaDinamica");

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
        addStep("Dar clic en el filtro fecha inicio y seleccionar una fecha");
        boolean resultStep7 = 	pc.validarInputRuta(rutaDinamica, false) &&
        						pc.inputFiltroLiquidacionRuta("FechaInicio", 4) &&
        						pc.inputFiltroLiquidacionRuta("FechaFin", 1);
        if(resultStep7){
		   testCase.addTextEvidenceCurrentStep("Se pudo ingresar ruta, fecha inicio y final");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se encontrar un elemento");
        }
        assertTrue(resultStep7, "No se pudo ingresar ruta, o fecha de inicio / fin");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Llenar el campo ruta y seleccionar una hora inicio y una hora fin");
        boolean resultStep8 = 	pc.inputFiltroLiquidacionRuta("HoraInicial", 1) &&
								pc.inputFiltroLiquidacionRuta("HoraFinal", 5);
		if(resultStep8){
		testCase.addTextEvidenceCurrentStep("Se visualiza los filtros completos con la informacion solicitada");
		} else {
		testCase.addTextEvidenceCurrentStep("No se visualiza los filtros completos con la informacion solicitada");
		}
		assertTrue(resultStep8, "No se visualiza los filtros completos con la informacion solicitada.");
        

        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Dar clic en el botÃ³n consultar");
        boolean resultStep9 = 	pc.VisibilidadElementoLiquidacionRuta("ConsultaButton", true)&&
								pc.VisibilidadElementoLiquidacionRuta("PantallaDetalle", false);
		if(resultStep9){
		testCase.addTextEvidenceCurrentStep("La pagina procede a poblarse de informacion, contemplando los filtros seleccionados");
		} else {
		testCase.addTextEvidenceCurrentStep("La pagina no se pobla de informacion");
		}
		assertTrue(resultStep9, "La pagina procede a poblarse de informacion, contemplando los filtros seleccionados");
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
        return "Validar los tipos de filtrado por fecha mediante el filtro fecha";
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