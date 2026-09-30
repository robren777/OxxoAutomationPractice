package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que tanto en el excel y en el grid, el nombre de tienda ya no venga concatenado CR más nombre de tienda. Solamente debe venir el nombre de la tienda
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_482_Columna Nombre de Tienda sea unicamente el valor nombre de tienda sin concatenado de CR y nombre
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
public class ATC_FT_482_RegCoincCambiosFisicosPendientes extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_482_RegCoincCambiosFisicosPendientes_test (HashMap<String, String> data) throws Exception {
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
        addStep("Dar clic en el botn, el sistema deber redirigir a la pantalla \"Detalle de Cambios Fsicos Pendientes\".");
        boolean resultStep7 = pc4.clickCambiosFisicosPendientesPantalla(true);
        assertTrue(resultStep7, "No se pudo ingresar a la pantalla");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar que en el grid cada uno de los registros de la columna \"nombre de tienda\" tengan unicamente el dato de nombre de tienda (sin CR)");
        boolean resultStep8 = pc4.validarNombreTiendaGridCFDetalle(true);
        assertTrue(resultStep8, "No se pudo validar el nombre de tienda en grid");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Dar click en boton descargar resumen de la informacion filtrada");
        boolean resultStep9 = pc4.ClicBotonDescargarMasivoCFPend(true);
        assertTrue(resultStep9, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar que en el grid cada uno de los registros de la columna \"nombre de tienda\" tengan unicamente el dato de nombre de tienda (sin CR)");
        boolean resultStep10 = pc4.validarNombreTiendaExcelCFDetalle(true);
        assertTrue(resultStep10, "No se pudo validar el nombre de tienda en excel");


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
		return "Validar que tanto en el excel y en el grid, el nombre de tienda ya no venga concatenado CR más nombre de tienda. Solamente debe venir el nombre de la tienda\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_482_Columna Nombre de Tienda sea unicamente el valor nombre de tienda sin concatenado de CR y nombre\r\n"
				+ "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}