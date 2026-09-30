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
* DESCRIPCIÓN: Validar que al dar click en consultar y despues click en botón limpiar filtro, el botón descarga se deshabilite
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC_FT_431_Validar que al dar Click al botón limpiar filtro despues de boton consultar, el botón descarga se deshabilite
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
* @date 22/12/2025
*/
public class ATC_FT_431_LimpiarFiltroDeshabilitaBotonDescarga extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_431_LimpiarFiltroDeshabilitaBotonDescarga_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Cometa");
        String User = data.get("user");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        addStep("Precondicion");
        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGrid(true, User);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_directos_view");
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
        addStep("Ingresar a Comprobantes de Compra ");
        boolean resultStep4_1 = pc4.ClicBotonComprobantesDeCompra(true);
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");
        /*
         *                                             PASO 6
         ******************************************************************************************/
        addStep("Dar clic en el Botón Consultar");
        boolean resultStep19 = pc4.busquedaFiltroPlazaFechaDirectos(User, true);
        assertTrue(resultStep19, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 7
         ******************************************************************************************/
        addStep("Validar que el botón descarga Habilitado");
        boolean resultStep5 = pc4.validarBtnDescargaResumenExcel_Visible(true);
        assertTrue(resultStep5, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 8
         ******************************************************************************************/
        addStep("Dar click en botón Limpiar Filtros");
        boolean resultStep6 = pc4.LimpiarFiltros(true);
        assertTrue(resultStep6, "No se pudo realizar la busqueda");
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Validar que el botón descarga se muestre deshabilitado");
        boolean resultStep7 = pc4.validarBtnDescargaResumenExcel_NoVisible(true);
        assertTrue(resultStep7, "No se pudo realizar la busqueda");
        
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
		return "1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \r\n"
				+ "2.Liga de Portal Cometa \r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al dar click en consultar y despues click en botón limpiar filtro, el botón descarga se deshabilite";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_431_LimpiarFiltroDeshabilitaBotonDescarga_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
