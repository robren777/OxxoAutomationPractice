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

/**Desc: Validar que la pantalla de liquidacion de ruta muestre la leyenda No se encontro informacion con los filtros proporcionados despues de una busqueda exitosa
 * MTC_FT_156_Validar leyenda consulta sin resultados despues de una consulta exitosa
 * Pre-requisitos: Contar con credenciales validas para el perfil de CEDIS
 * @author Isaac E. Garcia
 * @date Marzo 2025
 */
public class ATC_FT_156_ValidarLeyendaConsultaSinResultadosDespuesConsultaExitosa extends BaseExecution {
    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_156_ValidarLeyendaConsultaSinResultadosDespuesConsultaExitosa_test(HashMap<String, String> data){
        testCase.setProject_Name("Portal Web: Cometa");

        /*
        *
        *
        * Variables
        ******************************************************************************************/
       String user = data.get("user");
       String password = PasswordUtil.decryptPassword(data.get("password"));
       String url = data.get("Url");
       String rutaDinamica = data.get("rutaDinamica");
       String rutaDinamicaAlterna = data.get("rutaDinamicaAlterna");

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
       addStep("Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion");
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
       addStep("Ingresar al portal Cometa desde access control");
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
       addStep("Ingresar a \"Recepcion de Transferencias y Devoluciones\"");
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
       addStep("Dar clic en el boton \"Liquidacion de Ruta\" para ingresar a la pantalla "
       		+ "\n \"Liquidacion de Ruta\"");
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
       addStep("Seleccionar Ruta");
       boolean resultStep7 = pc.validarInputRuta(rutaDinamica, false);
       if(resultStep7){
		   testCase.addTextEvidenceCurrentStep("Se seleccionÃ³ una ruta");
        } else {
		   testCase.addTextEvidenceCurrentStep("No se encontro el elemento Ruta");
        }
        assertTrue(resultStep7, "No se encontro el elemento Ruta");
       /*
        *                                             PASO 8
        ******************************************************************************************/
       addStep("Seleccionar fecha inicio y hora en el que no haya informacion");
       boolean resultStep8 = 	pc.inputFiltroLiquidacionRuta("FechaInicio", 4) &&
								pc.inputFiltroLiquidacionRuta("HoraInicial", 1);
		if(resultStep8){
		testCase.addTextEvidenceCurrentStep("Se pudo seleccionar Fecha inicio y Hora inicial");
		} else {
		testCase.addTextEvidenceCurrentStep("No se encontro el elemento Fecha inicio y Hora inicial");
		}
		assertTrue(resultStep8, "No se encontro el elemento Fecha inicio y Hora inicial");
		
       /*
        *                                             PASO 9
        ******************************************************************************************/
       addStep("Seleccionar fecha fin y hora en el que no haya informacion");
       boolean resultStep9 = 	pc.inputFiltroLiquidacionRuta("FechaFin", 1) &&
				pc.inputFiltroLiquidacionRuta("HoraFinal", 23);
		if(resultStep9){
		testCase.addTextEvidenceCurrentStep("Se pudo seleccionar Fecha fin y Hora final");
		} else {
		testCase.addTextEvidenceCurrentStep("No se encontro el elemento Fecha fin y Hora final");
		}
		assertTrue(resultStep9, "No se encontro el elemento Fecha fin y Hora final");

       /*
        *                                             PASO 10
        ******************************************************************************************/
       addStep("Dar clic en Consultar");
       boolean resultStep10 = 	pc.VisibilidadElementoLiquidacionRuta("ConsultaButton", true) &&
								pc.VisibilidadElementoLiquidacionRuta("PantallaDetalle", false);
		if(resultStep10){
		testCase.addTextEvidenceCurrentStep("Se muestr informaciÃ³n al dar clic al botÃ³n");
		} else {
		testCase.addTextEvidenceCurrentStep("No se encontro el elemento Consulta button");
		}
		assertTrue(resultStep10, "No se encontro el elemento Consulta button");

        /*
         *                                             PASO 11
         ******************************************************************************************/
        addStep("Seleccionar Ruta");
        boolean resultStep11 = 	pc.VisibilidadElementoLiquidacionRuta("LimpiarFiltroButton", true) &&
        						pc.validarInputRuta(rutaDinamicaAlterna, false);
        if(resultStep11){
 		   testCase.addTextEvidenceCurrentStep("Limpiar la informaciÃ³n y filtros");
         } else {
 		   testCase.addTextEvidenceCurrentStep("No se encontro Limpiar filtro");
         }
         assertTrue(resultStep11, "No se encontro Limpiar filtro");
        /*
         *                                             PASO 12
         ******************************************************************************************/
        addStep("Seleccionar fecha inicio y hora en el que no haya informacion");
        boolean resultStep12 = 	pc.inputFiltroLiquidacionRuta("FechaInicio", 4) &&
								pc.inputFiltroLiquidacionRuta("HoraInicial", 1);
		if(resultStep12){
		testCase.addTextEvidenceCurrentStep("Se pudo seleccionar Fecha Inicio y Hora Inicial");
		} else {
		testCase.addTextEvidenceCurrentStep("No se encontro Fecha inicio y Hora inicial");
		}
		assertTrue(resultStep12, "No se encontro Fecha inicio y Hora inicial");

        /*
         *                                             PASO 13
         ******************************************************************************************/
        addStep("Seleccionar fecha fin y hora en el que no haya informacion");
        boolean resultStep13 = 	pc.inputFiltroLiquidacionRuta("FechaFin", 1) &&
								pc.inputFiltroLiquidacionRuta("HoraFinal", 23);
		if(resultStep13){
		testCase.addTextEvidenceCurrentStep("Se pudo seleccionar Fecha fin y Hora final");
		} else {
		testCase.addTextEvidenceCurrentStep("No se encontro el elemento Fecha fin y Hora final");
		}
		assertTrue(resultStep13, "No se encontro el elemento Fecha fin y Hora final");

        /*
         *                                             PASO 14
         ******************************************************************************************/
        addStep("Dar clic en Consultar");
        boolean resultStep14 = 	pc.VisibilidadElementoLiquidacionRuta("ConsultaButton", true) &&
								pc.VisibilidadElementoLiquidacionRuta("ConsultaSinDatos", false);
		if(resultStep14){
		testCase.addTextEvidenceCurrentStep("La consulta no devuelve informaciÃ³n");
		} else {
		testCase.addTextEvidenceCurrentStep("No se encontro Consulta button");
		}
		assertTrue(resultStep14, "No se encontro el elemento LiquidaciÃ³n de Ruta");
        
        
        
        
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
        return "Validar que la pantalla de liquidacion de ruta muestre la leyenda No se encontro informacion con los filtros proporcionados despues de una busqueda exitosa";
    }

    @Override
    public String setPrerequisites() {
        return "Contar con credenciales validas para el perfil de CEDIS";
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