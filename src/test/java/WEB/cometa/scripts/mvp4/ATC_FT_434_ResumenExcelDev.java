package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar que la dar click en el botón resumen excel,  se descargue en automatico si no supera los 1000 registros se mostrará un mensaje que diga \"La información se visualizara en el centro de descarga y en tu equipo de computo\", se deberá registrar en el modulo centro de descarga y se descargará en el equipo local
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_434_Validar boton resumen excel en Devoluciones
*  
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = directos" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 06/01/2026
*/

public class ATC_FT_434_ResumenExcelDev extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_434_ResumenExcelDev_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");
        String User = data.get("user");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        addStep("Precondicion");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridDirectosDevoluciones(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_directos_devoluciones_view");
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
        addStep("Ingresar a Directos"); 
        boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
        assertTrue(resultStep4, "No se encontro el elemento Directos");
        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Ingresar a Devoluciones");
        boolean resultStep4_1 = pc4.ClicBotonDevoluciones(true);
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
        boolean resultStep19 = pc4.SelecFecha(User, true);
        assertTrue(resultStep19, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Dar clic en el Botón Consultar");
        boolean resultStep9 = pc4.consultarComprobantes(true);        
        assertTrue(resultStep9,"No se obtuvieron registros");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Validar que no se muestren más de 1000 registros en el paginado (0 of 0)");
		boolean resultStep5 = pc4.LabelOfandCountMinus1000Dev(true);
		assertTrue(resultStep5,"Se obtuvieron más de 1000 registros");
		 /*
         *                                             PASO 9
         ******************************************************************************************/
		addStep("Dar click en botón Descargar de manera masiva los comprobantes filtrados (Excel)");
		boolean rC = pc4.ClickBtnExcelDevolu(true);
        assertTrue(rC, "No se encontró botón Excel");     
        /*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Se da clic en menú centro de descarga");
		boolean resultStep37 = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep37,"No se pudo dar click en botón descarga");
		/*
         *                                             PASO 11
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
		boolean resultStep10 = pc4.FiltroFechaCentroDescarga(true);
		assertTrue(resultStep10,"No se pudo seleccionar fecha");
		/*
         *                                             PASO 12
         ******************************************************************************************/
        addStep("Se valida que se registró la última descarga realizada");
		boolean resultStep11 = pc4.validarUltimaDescargaRegistrada(true);
		assertTrue(resultStep11,"No se obtuvo la última descarga realizada");
		/*
         *                                             PASO 13
         ******************************************************************************************/
        addStep("Se valida que se haya descargado de forma correcta y automatica el  archivo");
		boolean resultStep12 = pc4.openLastFileDownloadFolder("Excel", true);
		assertTrue(resultStep12,"No se obtuvo la última descarga realizada");

		

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
		return "\"1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \r\n"
				+ "2.Liga de Portal Cometa\r\n"
				+ "3. Consulta de menos de 1000 registros (paso 9)\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la dar click en el botón resumen excel,  se descargue en automatico si no supera los 1000 registros se mostrará un mensaje que diga \"La información se visualizara en el centro de descarga y en tu equipo de computo\", se deberá registrar en el modulo centro de descarga y se descargará en el equipo local\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_432_Validar URL campo descarga en resgistro de devoluciones en centro de descargas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
