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
* DESCRIPCIÓN: Validar que al tener un registro con estatus terminado, genere la URL para poder realizar la descarga en el campo \"Descarga\" y al dar click se realice la descarga correctamente
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_442_Validar URL campo descarga en resgistro de Cambios Fisicos en centro de descargas
*  
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = directos-cf" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 12/01/2026
*/

public class ATC_FT_442_UrlDescargaCamb extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_442_UrlDescargaCamb_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");
        String User = data.get("user");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        addStep("Precondicion");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridDirectosCambiosfísicos(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_directos_cf_view");
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
        
        //Precondicion      
        boolean resultStep4 = pc4.clicBotonCometaDirectos(false);
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        boolean resultStep4_1 = pc4.ClicBotonCF(false);
        assertTrue(resultStep4_1,"No se encontro el elemento CF");

        boolean resultStep19 = pc4.SelecFechaCF(User, false);
        assertTrue(resultStep19, "No se pudo realizar la busqueda");

        boolean resultStep9 = pc4.consultarComprobantes(false);
        assertTrue(resultStep9,"No se obtuvieron registros");

		boolean resultStep5 = pc4.LabelOfandCountMinus1000CF(true);
		assertTrue(resultStep5,"Se obtuvieron más de 1000 registros");

		boolean rC = pc4.ClickBtnExcelDevolu(true);
        assertTrue(rC, "No se encontró botón Excel");

        // fin de precondicion --continua script
        
        
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Se da clic en menú centro de descarga");
		boolean resultStep37 = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep37,"No se pudo dar click en botón descarga");
		/*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
		boolean resultStep10 = pc4.FiltroFechaCentroDescarga(true);
		assertTrue(resultStep10,"No se pudo seleccionar fecha");

		/*
         *                                             PASO 11
         ******************************************************************************************/
        addStep("Se valida que se registró la última descarga realizada");
		boolean resultStep11 = pc4.validarUltimaDescargaYDescargar(true);
		assertTrue(resultStep11,"No se obtuvo la última descarga realizada");
		
		/*
         *                                             PASO 12
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
				+ "2.Liga de Portal Cometa \r\n"
				+ "3. Contar con un registro de descarga generado (Se generan en modulo Cambios Fisicos)\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al tener un registro con estatus terminado, genere la URL para poder realizar la descarga en el campo \"Descarga\" y al dar click se realice la descarga correctamente\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "MTC_FT_442_Validar URL campo descarga en resgistro de Cambios Fisicos en centro de descargas\r\n"
				+ "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
