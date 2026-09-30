package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que la dar click en el botón masivo PDF, se descargue en automatico si no supera los 1000 registros se mostrará un mensaje que diga \"La información se visualizara en el centro de descarga y en tu equipo de computo\", se deberá registrar en el modulo centro de descarga y se descargará en el equipo local"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_447_Validar boton masivo PDF en Cambios Fisicos
*
*PREREQUISITOS:
*1. Credenciales [Admin, Administrativo Operativo, Comercial] validar desde el criterio 
*2. Liga de Portal Cometa 
*
*INSUMOS: 
 *Contar con información disponible en tabla public.xxcm_historico_directos_cf_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Diciembre 2025
*/
public class ATC_FT_447_BotonMasivoPDFCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_447_BotonMasivoPDFCambiosFisicos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(10);
		validacion = pc.accessControlScreenSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 3 - Ingresar al portal Cometa desde access control
		 */
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 4 - Ingresar a Directos
		 */
		addStep("Ingresar a Directos");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		
		/*
		 * Paso 5 - Ingresar a "Cambios Fisicos" 
		 */
		addStep("Ingresar a \"Cambios Fisicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 6 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		validacion = pc.SelecFechaCFMenosMil(user, true);
        assertTrue(validacion, "No se pudo realizar la busqueda");
        
        /*
		 * Paso 7 - Dar clic en el Botón Consultar
		 */
		addStep("Dar clic en el Botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 8 - Desaparece mensaje y se muestra información en el grid
		 */
		addStep("Desaparece mensaje y se muestra información en el grid");
		validacion = pc.MensajeEspereDesaparece(true);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		
		/*
		 * Paso 9 - Validar que no se muestren más de 1000 registros en el paginado (0 of 0)
		 */
		addStep("Validar que no se muestren más de 1000 registros en el paginado (0 of 0)");
		validacion = pc.LabelOfandCountMinus1000CF(true);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		
		/*
		 * Paso 10 - Dar click en botón Descargar de manera masiva los comprobantes filtrados (PDF) 
		 */
		addStep("Dar click en botón Descargar de manera masiva los comprobantes filtrados (PDF) ");
		validacion = pc.clicPdfDescargaMasiva();
		assertTrue(validacion,"No se pudo seleccionar de manera correcta");
		validacion = pc.registrarFechaHora();
		assertTrue(validacion,"No se pudo realizar el registro de los datos de manera correcta");
		u.hardWait(1);
		
		/* * Paso 11 - Se da clic en la X del pop up 
		 */
		addStep("Se da clic en la X del pop up");
		validacion = pc.clickX();
		assertTrue(validacion,"No se pudo seleccionar de manera correcta");
		
		/*
		 * Paso 12 - Se da clic en menú centro de descarga
		 */
		addStep("Se da clic en menú centro de descarga");
		validacion = pc.opcCentroDescargas(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.scCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 13 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(1,true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		pc.opcConsultar(false);
		
		/*
		 * Paso 14 - Se valida que se registró la última descarga realizada 
		 */
		addStep("Se valida que se registró la última descarga realizada");
		validacion = pc.valRegCentroDeDescargas(false);
		assertTrue(validacion,"No se muestran los datos de manera correcta");
		
		/*
		 * Paso 15 - Se valida que se haya descargado de forma correcta y automatica el  archivo 
		 */
		addStep("Se valida que se haya descargado de forma correcta y automatica el  archivo");
		validacion = pc.validarDescargaCentroDescargas();
		assertTrue(validacion,"No se muestra la descarga de manera correcta");
		
		/*
		 * Finalizar el proceso
		 */
		pc.eliminarUltimoArchivoDescargado();
		u.hardWait(1);
		u.quit(); 

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
		return "1. Credenciales [Admin, Administrativo Operativo, Comercial] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la dar click en el botón masivo PDF, se descargue en automatico si no supera los 1000 registros se mostrará un mensaje que diga \"La información se visualizara en el centro de descarga y en tu equipo de computo\", se deberá registrar en el modulo centro de descarga y se descargará en el equipo local";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_447_BotonMasivoPDFCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}