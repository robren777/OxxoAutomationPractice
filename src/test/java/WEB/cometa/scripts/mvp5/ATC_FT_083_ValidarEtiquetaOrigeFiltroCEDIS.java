package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesEspecialesElements;
import WEB.cometa.om.devolucionesEspecialesPage;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


/**
 * DESCRIPCION: Validar que el filtro CEDIS muestre opciones concatenadas desde xxcm_cedis usando los campos id_supplier y supplier_name, y que el valor visible en pantalla coincida con la concatenación validada en DB.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - 
PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = directos" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja 
 * @date 08/07/2026
 */
public class ATC_FT_083_ValidarEtiquetaOrigeFiltroCEDIS extends BaseExecution{
	
	
	private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_083_ValidarEtiquetaOrigeFiltroCEDIS_test(HashMap<String, String> data) throws Exception {
     
    	testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String user = data.get("user");
		String url = data.get("Url");
		String perfil = "CEDIS";

		/*
		 * PRECONDICIÓN
		 ***************************************************************************/
		addStep("Precondicion: obtener password configurado para el usuario " + user + " con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
		String password = pc4.obtenerPasswordPorUsuario(user);
		assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Acceder a la URL de inicio de sesion del portal CEDIS");
		boolean resultStep1 = pc4.IngresarURL(url, true);
		assertTrue(resultStep1, "No se cargo correctamente la pantalla de inicio de sesion del portal CEDIS");
		pc4.PrivatePortal();

		/*
		 *                                             PASO 2 Y PASO 3
		 ***************************************************************************/
		addStep("Capturar usuario y password validos, y hacer clic en Entrar");
		boolean resultStep2 = pc4.IniciarSesionNoToken(user, password, true);
		assertTrue(resultStep2, "No se procesaron correctamente las credenciales validas del usuario CEDIS");

		/*
		 *                                             PASO 4 Y PASO 5
		 ***************************************************************************/
		addStep("Esperar la carga del portal y hacer clic en Portal COMETA");
		boolean resultStep4 = pc4.seleccionarPortalCometaQA(true);
		assertTrue(resultStep4, "No se mostro o no fue posible seleccionar Portal COMETA desde Access Control");

		/*
		 *                                             PASO 6
		 ***************************************************************************/
		addStep("Hacer clic en el modulo CEDIS");
		boolean resultStep6 = pc4.clicBotonCometaCedis(true);
		assertTrue(resultStep6, "No se mostro la opcion Recepcion dentro del modulo CEDIS");

		/*
		 *                                             PASO 7
		 ***************************************************************************/
		addStep("Ingresar a Recepcion y abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Recepcion = pc4.ClicBotonRecepcionCedis(true);
		assertTrue(resultStep7Recepcion, "No se pudo ingresar a Recepcion de Transferencias y Devoluciones");
		boolean resultStep7DevEspeciales = devEspecialesPage.ingresarDevolucionesEspeciales(true);
		assertTrue(resultStep7DevEspeciales, "No se pudo abrir la pantalla Devoluciones Especiales");
		boolean resultStep7Filtros = devEspecialesPage.validarFiltrosDevolucionesEspecialesVisibles(true);
		assertTrue(resultStep7Filtros, "No se mostraron todos los filtros esperados de Devoluciones Especiales");

		/*
		 *                                             PASO 8 Y PASO 9
		 ***************************************************************************/
		addStep("Abrir el filtro CEDIS y revisar que las opciones tengan formato id_supplier supplier_name");
		boolean resultStep8 = devEspecialesPage.abrirFiltroCedisYValidarFormatoOpciones(true);
		assertTrue(resultStep8, "El filtro CEDIS no desplego opciones con el formato esperado id_supplier supplier_name");

		/*
		 *                                             PASO 10
		 ***************************************************************************/
		addStep("Seleccionar una opcion de CEDIS");
		boolean resultStep10 = devEspecialesPage.seleccionarPrimeraOpcionFiltroCedis(true);
		assertTrue(resultStep10, "No fue posible seleccionar una opcion del filtro CEDIS");

		/*
		 *                                             PASO 11
		 ***************************************************************************/
		addStep("Verificar el valor visible en el control CEDIS y la etiqueta del filtro");
		boolean resultStep11 = devEspecialesPage.validarValorVisibleFiltroCedis(true);
		assertTrue(resultStep11, "El filtro CEDIS no mostro el valor seleccionado o no respeto la etiqueta CEDIS");

		/*
		 *                                             PASO 12
		 ***************************************************************************/
		addStep("Validar en DB la concatenacion xxcm_cedis.id_supplier + xxcm_cedis.supplier_name");
		boolean resultStep12 = devEspecialesPage.validarCedisSeleccionadoContraBD(true);
		assertTrue(resultStep12, "La concatenacion de xxcm_cedis no coincide con el valor mostrado en el filtro CEDIS");

     	
    	 
    }

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	  @AfterMethod
	    public void afterMethod(){
	    	u.getDriver().quit();
	    }


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Validar que existan datos en la BD\r\n"
				+ "* 	- No utilizar VPN OXXO (Red libre de preferencia)\r\n"
				+ "* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el filtro CEDIS muestre opciones concatenadas desde xxcm_cedis usando los campos id_supplier y supplier_name, y que el valor visible en pantalla coincida con la concatenación validada en DB.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_083_ValidarEtiquetaOrigeFiltroCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
