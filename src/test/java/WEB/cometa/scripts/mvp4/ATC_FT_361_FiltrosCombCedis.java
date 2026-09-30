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
* DESCRIPCIÓN: Validar que los filtros deberian de funcionar de forma individual y también en la combinación hora inicio + hora fin, en la pantalla de Liquidación de Ruta del modulo CEDIS, con usuario CEDIS
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_361_Validar los filtros funcionen de forma individual y también en la combinación hora inicio + hora fin, con usuario CEDIS
*  
* PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = cedis" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
* @author Jose Pecina
* @date 06/11/2025
*/

public class ATC_FT_361_FiltrosCombCedis extends BaseExecution {

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_361_FiltrosCombCedis_test(HashMap<String, String> data) throws Exception {
    	testCase.setProject_Name("Portal Cometa");

        String User = data.get("User");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        addStep("Precondicion");
        boolean ResultPrec = pc4.validarYActualizarHistoricoCorreosCedis(true, User);
        assertTrue(ResultPrec,"Se actulizo correctamente ruta prouesta");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridCedis(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
        boolean validacion = pc4.obtenerRutaPropuesta(User);
		pc4.obtenerFechaRegistroRutaPropuesta(User);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
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
        addStep("Ingresar a CEDIS");
        boolean resultStep4 = pc4.clicBotonCometaCedis(true);
        assertTrue(resultStep4, "No se encontro el elemento Cedis");
        /*
         *                                             PASO 5
         ******************************************************************************************/
        addStep("Seleccionar Recepción de Transferencias y Devoluciones");   
        boolean resultStep4_1 = pc4.ClicBotonRecepcionCedis(true);
        assertTrue(resultStep4_1,"No se encontro el elemento Recepcion");
        /*
         *                                             PASO 6
         ******************************************************************************************/
		addStep("Dar clic en el botón de Liquidación de Ruta");
		boolean resultStep6 = pc4.opcLiquidacionRuta();
		assertTrue(resultStep6,"No se pudo seleccionar la opción de manera correcta");
		boolean resultStep7 = pc4.scLiquidacionRuta();
		assertTrue(resultStep7,"No se muestra la pantalla de manera correcta");		
		/*
         *                                             PASO 7
         ******************************************************************************************/
		addStep("Ingresar Ruta Dinámica (UNIGIS)");
		boolean resultStep5 = pc4.ingRutaDinamica();
		assertTrue(resultStep5,"No se pudo ingresar de manera correcta");		
		/*
         *                                             PASO 8
         ******************************************************************************************/
		addStep("Realizar filtro \"Fecha Inicial\",\"Fecha Final\" (individual) y también en la combinación hora inicio + hora fin");
		boolean resultStep8 = pc4.llenadoFiltrosLRScreenIndiv(true);
		assertTrue(resultStep8,"No se pudo realizar el llenado de manera correcta.");
		/*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Dar clic en el Boton Consultar");
        boolean resultStep9 = pc4.consultarLR(true);
        assertTrue(resultStep9,"No se obtuvieron registros");
		/*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Validar que se visualice únicamente los registros que correspondan a ese rango seleccionado");
        boolean resultStep10 = pc4.valInformacionLiqRutaCedis(User);
        assertTrue(resultStep10,"No se obtuvieron registros");
      
        
    }
    @Override
    public void beforeTest() {

    }

    @AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }

    @Override
    public String setPrerequisites() {
        return "1. Credenciales para el perfil CEDIS con usuario  [CEDIS] validar desde el criterio  "
        		+ "2.Liga de Portal Cometa ";
    }

    @Override
    public String setTestDescription() {
        return "Validar que los filtros deberian de funcionar de forma individual y también en la combinación hora inicio + hora fin, en la pantalla de Liquidación de Ruta del modulo CEDIS, con usuario CEDIS";
    }

    @Override
    public String setTestDesigner() {
        return "JosePecina-GTIM";
    }

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}