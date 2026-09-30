package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que en base de datos postgresql, tabla de cambios físicos pendientes, se muestre 
 * la columna quantity_items , y en el grid, la columna Cantidad sea igual a quantity_items 
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_485_Campo cantidad debe coincidir en grid y BD
*
*PREREQUISITOS:
1. Credenciales de perfil ADMIN Operativo
2. Validar portal comenta
3. Validar pantalla "Cambios Físicos Pendientes"
*
*INSUMOS: 
Tener registros en la pantalla de cambios fisicos pendientes, la tabla xxcm_cambios_fisicos_pendientes

* @author Jose Pecina
* @date Marzo 2026
*/
public class ATC_FT_485_CampoCantidadCoincideGridBD extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_485_CampoCantidadCoincideGridBD_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");

		String User = data.get("user");
	    String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        
        addStep("Precondicion");
        boolean ResultPre = pc4.validaDatosFuncionCambiosFisicosPendientes(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la funcion get_cambios_fisicos_directos_pendientes_paginated");
        String Password = pc4.obtenerPasswordPorUsuario(User);
        assertTrue(Password != null, "No se encontro password configurada para el usuario: " + User);
        /*
         *                                             PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        boolean resultStep1 = pc4.IngresarURL(url, true);
        assertTrue(resultStep1,"No se pudo ingresar a la URL");       
        //Se quita pantalla de seguridad ventana privada si existe
        pc4.PrivatePortal();     
        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc4.IniciarSesionNoToken(User, Password, true);
        assertTrue(resultStep2, "No se pudo iniciar sesion");        
        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
        assertTrue(resultStep3, "No es visible el elemento de Cometa");
        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a DIRECTOS");
        boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep4, "No se encontro el elemento Directos");
        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Seleccionar Cambios fsicos");
        boolean resultStep4_1 = pc4.ClicBotonCF(true);
        assertTrue(resultStep4_1,"No se encontro el elemento CF");
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic en el boton, el sistema debera redirigir a la pantalla \"Detalle de Cambios Fisicos Pendientes\".");
        boolean resultStep6 = pc4.clickCambiosFisicosPendientesPantalla(true);
        assertTrue(resultStep6, "No se pudo ingresar a la pantalla");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar que cada una de las cantidades en el grid coincidan con el campo quantity_items de la base de datos PostgreSQL.");
        boolean resultStep8 = pc4.validarCantidadGridVsBDCFPendientes(true, User);
        assertTrue(resultStep8, "No coinciden las cantidades del grid contra quantity_items de la BD");


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	@AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales de perfil ADMIN Operativo\r\n"
				+ "2. Validar portal comenta\r\n"
				+ "3. Validar pantalla \"Cambios Físicos Pendientes\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en base de datos postgresql, tabla de cambios físicos pendientes, se muestre la columna quantity_items "
				+ ", y en el grid, la columna Cantidad sea igual a quantity_items ";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_485_CampoCantidadCoincideGridBD_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}