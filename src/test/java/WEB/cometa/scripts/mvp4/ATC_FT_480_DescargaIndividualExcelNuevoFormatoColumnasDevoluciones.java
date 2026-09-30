package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Dentro del documento masivo Excel, validar que se ya no se incluyan las columnas 
 * "Total de Artículos A Devolver", "Total de piezas a devolver", "Valor Total" y "Total Costo"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_480 Descarga Individual Excel Pantalla de Devoluciones - Nuevo Formato de Columnas
*
*PREREQUISITOS:
1. Credenciales de todos los perfiles
2. Validar portal comenta
3. Validar pantalla "Devoluciones"
*
*INSUMOS: 
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_480_DescargaIndividualExcelNuevoFormatoColumnasDevoluciones extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_480_DescargaIndividualExcelNuevoFormatoColumnasDevoluciones_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		String User = data.get("user");
        String url = data.get("Url");
        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
        
        addStep("Precondicion");
        boolean validacion = pc4.ValidaBDExistanDatosFiltrosGridDirectosDevoluciones(true, User);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_dv");
        String Password = pc4.obtenerPasswordPorUsuario(User);
        assertTrue(Password != null, "No se encontro password configurada para el usuario: " + User);
       
        /*
         * Paso 1 - Ingresar a la liga del portal
         */
        addStep("Ingresar a la liga del portal");
        validacion = pc4.IngresarURL(url, true);
        assertTrue(validacion,"No se pudo ingresar a la URL");       
        //Se quita pantalla de seguridad ventana privada si existe
        pc4.PrivatePortal();     
        
        /*
         * Paso 2 - Ingresar credenciales Comercial para inicio de sesión
         */
        addStep("Ingresar credenciales Comercial para inicio de sesión");
        validacion = pc4.IniciarSesionNoToken(User, Password, true);
        assertTrue(validacion, "No se pudo iniciar sesion");        
        
        /*
         * Paso 3 - Ingresar al portal Cometa desde access control
         */
        addStep("Ingresar al portal Cometa desde access control");
        validacion = pc4.seleccionarPortalCometaQA(true);
        assertTrue(validacion, "No es visible el elemento de Cometa");
        
        /*
         * Paso 4 - Ingresar a la pantalla "Devoluciones"
         */
        addStep("Ingresar a la pantalla \"Devoluciones\"");
        validacion = pc4.clicBotonCometaDirectos(false);
        assertTrue(validacion, "No se encontro el elemento Directos");
        validacion = pc4.ClicBotonDevoluciones(true);
        assertTrue(validacion,"No se encontro el elemento Devoluciones");
        
        /*
		 * Paso 5 - Seleccionar cualquier filtro + rango de fechas
		 */
		addStep("Seleccionar cualquier filtro + rango de fechas");
		validacion = pc4.SelecFecha(User,true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 6 - Seleccionar botón "Consultar"
		 */
		addStep("Seleccionar botón \"Consultar\"");
		validacion = pc4.opcConsultar(true);
		assertTrue(validacion,"El botón se encuentra deshabilitado");
        
        /*
         * Paso 7 - Dar clic a un registro de Devoluciones que cuente con un documento para su vista preliminar
         */
        addStep("Dar clic a un registro de Devoluciones que cuente con un documento para su vista preliminar");
        validacion = pc4.abrirTicketDesdeGridYValidarVista(true); 
        assertTrue(validacion, "No se redirigió correctamente al ticket de Devoluciones");
        
		/*
		 * Paso 8 - Validar que muestre un ícono de Excel dentro de la vista preliminar
		 */
		addStep("Validar que muestre un ícono de Excel dentro de la vista preliminar");
		validacion = pc4.iconoExcelIndv();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 9 - Seleccionar el ícono de Excel
		 */
		addStep("Seleccionar el ícono de Excel");
		validacion = pc4.clicExcelDescargaIndv(true);
		assertTrue(validacion,"No se muestra correctamente");
		validacion = pc4.imgEspereUnMomento();
		assertTrue(validacion,"No se muestra correctamente");
		u.hardWait(2);
		
		/*
		 * Paso 10 - Abrir el documento Excel recien descargado - Se visualiza el documento Excel y validar que 
		 * ya no existan las columnas "Total de Artículos A Devolver", "Total de piezas a devolver", "Valor Total" y "Total Costo", así como sus datos. 
		 */
		addStep("Abrir el documento Excel recien descargado - Se visualiza el documento Excel y validar que"
				+ "ya no existan las columnas \"Total de Artículos A Devolver\", \"Total de piezas a devolver\", \"Valor Total\" y \"Total Costo\", así como sus datos.");
		validacion = pc4.validarColumnaNoExistentes();
		assertTrue(validacion,"No se muestra correctamente");



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
		return "1. Credenciales de todos los perfiles\r\n"
				+ "2. Validar portal comenta\r\n"
				+ "3. Validar pantalla \"Devoluciones\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Dentro del documento masivo Excel, validar que se ya no se incluyan las columnas \"Total de Artículos A Devolver\", "
				+ "\"Total de piezas a devolver\", \"Valor Total\" y \"Total Costo\"";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_480_DescargaIndividualExcelNuevoFormatoColumnasDevoluciones_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}