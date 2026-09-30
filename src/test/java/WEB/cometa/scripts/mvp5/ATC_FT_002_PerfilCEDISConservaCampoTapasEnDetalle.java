package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.rutaLiquidacionElements;
import WEB.cometa.om.rutaLiquidacionPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;



/**DESCRIPCIÓN: Verifica que el perfil CEDIS mantenga visible el campo “Tapas” en los renglones de detalle por tienda-transferencia y que esté presente de forma consistente.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_002 Perfil CEDIS conserva el campo "Tapas" en detalle
* 
*PREREQUISITOS:
*1. Credenciales para el perfil CEDIS     
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con información disponible en tabla xxcm_historico_cedis_view.
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Junio 2026
*/
public class ATC_FT_002_PerfilCEDISConservaCampoTapasEnDetalle extends BaseExecution{
	
	   SeleniumUtil u;
		@Test(dataProvider = "data-provider")
		public void ATC_FT_002_PerfilCEDISConservaCampoTapasEnDetalle_test (HashMap<String, String> data) throws Exception {
			testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");
			
			

	       
	        u = new SeleniumUtil(new ChromeTest(), true);
	        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
	        rutaLiquidacionElements rutaElements = new rutaLiquidacionElements ();
	        rutaLiquidacionPage rutaPage = new rutaLiquidacionPage (u, testCase);
	        
	        String User = data.get("User");
	        String url = data.get("Url");
	        String etiqueta = data.get("etiqueta");
	        
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
	    
	        
	        addStep("Ingresar a la liga");
	        boolean resultStep1 = pc4.IngresarURL(url, true);
	        assertTrue(resultStep1,"No se pudo ingresar a la URL");
	        
	        //Se quita pantalla de seguridad ventana privada si existe
	        pc4.PrivatePortal();     

	        
	        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
	        boolean resultStep2 = pc4.IniciarSesionNoToken(User, Password, true);
	        assertTrue(resultStep2, "No se pudo iniciar sesion");        
	     
	        
	        addStep("Ingresar a Portal Cometa");
	        boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
	        assertTrue(resultStep3, "No es visible el elemento de Cometa");
	        
	        
	        addStep("Ingresar a CEDIS");
	        boolean resultStep4 = pc4.clicBotonCometaCedis(true);
	        assertTrue(resultStep4, "No se encontro el elemento Cedis");
	      
	        
	        addStep("Seleccionar Recepción de Transferencias y Devoluciones");   
	        boolean resultStep4_1 = pc4.ClicBotonRecepcionCedis(true);
	        assertTrue(resultStep4_1,"No se encontro el elemento Recepcion");
	       
	        
			addStep("Dar clic en el botón de Liquidación de Ruta");
			boolean resultStep6 = pc4.opcLiquidacionRuta();
			assertTrue(resultStep6,"No se pudo seleccionar la opción de manera correcta");
			boolean resultStep7 = pc4.scLiquidacionRuta();
			assertTrue(resultStep7,"No se muestra la pantalla de manera correcta");
		
			
			addStep("Ingresar Ruta Dinámica (UNIGIS)");
			boolean resultStep5 = pc4.ingRutaDinamica();
			assertTrue(resultStep5,"No se pudo ingresar de manera correcta");
			
			
			addStep("Realizar filtros y seleccionar  \"Fecha inicial\", \"Hora Inicial\", \"Fecha final\", \"Hora final\"");
			boolean resultStep8 = pc4.llenadoFiltrosLR();
			assertTrue(resultStep8,"No se pudo realizar el llenado de manera correcta.");
			
			
	        addStep("Dar clic en el Boton Consultar");
	        boolean resultStep9 = pc4.consultarLR(true);
	        assertTrue(resultStep9,"No se obtuvieron registros");
		
	        
	        addStep("Revisar los renglones inferiores correspondientes al detalle por tienda-transferencia.");
	        assertTrue(rutaPage.validaExisteEtiqueta(rutaElements.containerTienda, etiqueta),"No se encontró la etiqueta Tapas");
	        
	        addStep("Comparar al menos dos renglones de detalle entre si y confirmar que la columna o campo Tapas sigue presente en ambos.");
	        assertTrue(rutaPage.validaExisteEtiqueta(rutaElements.containerTienda, etiqueta),"Se encontró la etiqueta Tapas");
	        assertFalse(rutaPage.validaExisteEtiqueta(rutaElements.containerDetalle, etiqueta),"Se encontró la etiqueta Tapas");
	        u.close();
	      
		}


	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil CEDIS     \r\n"
		+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verifica que el perfil CEDIS mantenga visible el campo “Tapas” en los renglones de detalle por tienda-transferencia y que esté presente de forma consistente.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_PerfilCEDISConservaCampoTapasEnDetalle_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
