package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: En la descarga de excel de cambios fisicos pendientes, deben coincidir los registros mostrados a los del grid de cambios fisicos pendientes
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_481_Registros Excel VS Registros Grid deben coincidir
*
*PREREQUISITOS:
"1. Credenciales de perfiles (Admin Operativo, Oxxo Plaza, Oxxo Os)
2. No utilizar VPN OXXO (Red libre de preferencia"
*
*INSUMOS: 
*Contar con datos en BD de cambios fisicos pendientes xxcm_cambios_fisicos_pendientes

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_481_RemFechaCambiosFisicosPendientes extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_481_RemFechaCambiosFisicosPendientes_test (HashMap<String, String> data) throws Exception {
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
        addStep("Validar la informacion en el grid se visualice ordenada de forma descendente por fecha, es decir, de la mas reciente a la mas antigua.");
        boolean resultStep7 = pc4.AutoGridDirectosFechasCFDetalles(true);
        assertTrue(resultStep7, "Las fechas del grid no estan ordenadas correctamente");

        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Dar clic en el boton de descarga de Excel.");
        boolean resultStep8 = pc4.ClicBotonDescargarMasivoCFPend(true);
        assertTrue(resultStep8, "No se pudo descargar el Excel");

        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar que la informacion del Excel descargado coincida exactamente con la informacion mostrada en el grid.");
        boolean resultStep9 = pc4.validarExcelVsGridCFDetalles(true);
        assertTrue(resultStep9, "Los datos del Excel no coinciden con el grid");



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
		return "\"1. Credenciales de perfiles (Admin Operativo, Oxxo Plaza, Oxxo Os)\r\n"
				+ "2. No utilizar VPN OXXO (Red libre de preferencia\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "En la descarga de excel de cambios fisicos pendientes, deben coincidir los registros mostrados a los del grid de cambios fisicos pendientes\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_481_Registros Excel VS Registros Grid deben coincidir\r\n"
				+ "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}