package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que tanto en la vista preliminar como en la descarga PDF individual se visualicen las evidencias
(URL) en la parte de Dañados y que al dar clic en dichas evidencias se muestren las imágenes
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*MTC_FT_470_Evidencias URL de productos Dañados en Formato de Verificación  - Individual
*
*PREREQUISITOS:
1. Credenciales para el perfil Admin, validar el formato con la liga ux
2. Liga de Portal Cometa
*
*INSUMOS: 
Existen datos en la tabla xxcm_historico_cedis_view

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_470_EvidenciasURLProductosDanadosFormatoVerificacionIndividual extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_470_EvidenciasURLProductosDanadosFormatoVerificacionIndividual_test (HashMap<String, String> data) throws Exception {
		 testCase.setProject_Name("Portal Cometa");

         String User = data.get("user");
         String url = data.get("Url");
         u = new SeleniumUtil(new ChromeTest(), true);
         PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
         addStep("Precondicion");
         boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridCedis(true, User);
         assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
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
         addStep("Posiscionarse en el campo plaza");
         boolean resultStep26 = pc4.busquedaFiltroPlazaFechaCedisVerif(User,true);
         assertTrue(resultStep26, "No se regresaron datos del filtro");
         /*
          *                                             PASO 7
          ******************************************************************************************/
         addStep("Dar clic a un registro de Recepcion donde se contenga toda la informacion en su formato de verificacion");
         boolean resultStep7 = pc4.FormatoVerifGridCedis(true);
         assertTrue(resultStep7, "No se pudo validar el botón formato de verificacion"); 
         /*
          *                                             PASO 8
          ******************************************************************************************/
         addStep("Dar clic en el icono de PDF que se encuentra dentro de la vista preliminar");
         boolean resultStep8 = pc4.ClicBotonDescargarIndividualPDFCedisVerif(true);
         assertTrue(resultStep8, "No se pudo dar click en boton PDF"); 
         /*
          *                                             PASO 9
          ******************************************************************************************/
         addStep("Abrir el documento PDF recién descargado");
         boolean resultStep9 = pc4.validarUrlsEvidenciaPDFvsPreview(true);
         assertTrue(resultStep9, "No se pudieron validar URLS"); 


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
		return "1. Credenciales para el perfil Admin, validar el formato con la liga ux               "
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que tanto en la vista preliminar como en la descarga PDF individual se visualicen las evidencias (URL) "
				+ "en la parte de Dañados y que al dar clic en dichas evidencias se muestren las imágenes";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_470_EvidenciasURLProductosDanadosFormatoVerificacionIndividual_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}