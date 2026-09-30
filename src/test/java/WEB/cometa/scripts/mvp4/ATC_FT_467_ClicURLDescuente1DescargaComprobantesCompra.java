package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que al dar click en descargar a un documento correspondiente de Comprobantes de Compra, 
 * se descuente la descarga finalizada del botón notificación
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_467 Validar que al dar click en la URL se descuente 1 descarga en descargas finalizadas en comprobantes de compra
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial y CEDIS] 
2. Liga de Portal Cometa
3. Contar con un documento de origen Comprobantes de Compra pendiente de descarga en el centro de descargas
*
*INSUMOS: 
Existen datos en la tabla xxcm_cc

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_467_ClicURLDescuente1DescargaComprobantesCompra extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_467_ClicURLDescuente1DescargaComprobantesCompra_test (HashMap<String, String> data) throws Exception {
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
        
        //Precondicion      
        boolean resultStep4 = pc4.clicBotonCometaDirectos(false);
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        boolean resultStep4_1 = pc4.ClicBotonComprobantesDeCompra(false);
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");

        boolean resultStep19 = pc4.busquedaFiltroPlazaFechaDirectos(User, false);
        assertTrue(resultStep19, "No se pudo realizar la busqueda");

		boolean resultStep5 = pc4.LabelOfandCountMinus1000(false);
		assertTrue(resultStep5,"Se obtuvieron más de 1000 registros");

		boolean resultStep36 = pc4.ClicBotonDescargarMasivoPDF(false);
		assertTrue(resultStep36,"No se encontro el elemento para realizar la descarga");
        // fin de precondicion --continua script
        
        
        /*
         *                                             PASO 9
         ******************************************************************************************/
        addStep("Se da clic en menú centro de descarga");
		boolean resultStep9 = pc4.ClicBotonCentroDescarga(true);
		assertTrue(resultStep9,"No se pudo dar click en botón descarga");
		/*
         *                                             PASO 10
         ******************************************************************************************/
        addStep("Seleccionar rango de fechas");
		boolean resultStep10 = pc4.FiltroFechaCentroDescarga(true);
		assertTrue(resultStep10,"No se pudo seleccionar fecha");
		/*
         *                                             PASO 11
         ******************************************************************************************/
		
		boolean resultStep11 = pc4.validarCampanitaDescargasFinalizadas(false);
		assertTrue(resultStep11,"No se pudo obtener el número de descargas");
        addStep("Dar click en el botón URL para descargar el documento pendiente");
		boolean resultStep11_5 = pc4.clickDescargarUltimoDocumentoGenerado(true);
		assertTrue(resultStep11_5,"No se pudo dar click en descargar de ningún registro");		
		/*
         *                                             PASO 12
         ******************************************************************************************/
        addStep("Se valida que el número de la campana haya cambiado al darle descargar");
        boolean resultStep12 = pc4.validarCampanitaDescargasFinalizadasDiferente(true);
		assertTrue(resultStep12,"No se encontro el archivo");


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
		return "1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial y CEDIS] \r\n"
				+ "2. Liga de Portal Cometa\r\n"
				+ "3. Contar con un documento de origen Comprobantes de Compra pendiente de descarga en el centro de descargas";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al dar click en descargar a un documento correspondiente de Comprobantes de Compra, se descuente la descarga finalizada del botón notificación";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_467_ClicURLDescuente1DescargaComprobantesCompra_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}