package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Desde una descarga masiva de la información filtrada en formato Excel, 
 * validar que los datos sea igual al que se muestra en la vista preliminar del registro de las siguientes columnas: 
1.- Costo Total
2.- Precio Total 
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_476_Validar Descarga Masiva Excel - Comparativa "Costo Total" y "Precio" Vista Preliminar y Descarga
*
*PREREQUISITOS:
1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial
2. Acceso a Portal Comenta
3. Acceso a la pantalla "Cambios Fisicos"
*
*INSUMOS: 
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_476_DescargaMasivaExcelComparativaCostoTotalPrecio extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_476_DescargaMasivaExcelComparativaCostoTotalPrecio_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRegistroCorreoCFTipo2(true, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con las con credenciales 
		 */
		addStep("Ingresar a la liga del portal con las con credenciales"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(12);
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
		 * Paso 4 - Ingresar a DIRECTOS
		 */
		addStep("Ingresar a DIRECTOS");
		validacion = pc.opcDirectosPortalCometa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.directosPortalCometaSc();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6 - Seleccionar cualquier filtro + rango de fechas
		 */
		addStep("Seleccionar cualquier filtro + rango de fechas");
		validacion = pc.datosRegistroTipoDos();
		validacion = pc.fechasBd(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 7 - Seleccionar botón "Consultar"
		 */
		addStep("Seleccionar botón \"Consultar\"");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"El botón se encuentra deshabilitado");
		
		/*
		 * Paso 8 - Dar clic en el primer registro que se muestra en el grid al aplicar 
		 * el rango de fechas y que ese registro contenga una cantidad de piezas realizadas (que no esté en cero)
		 */
		addStep("Dar clic en el primer registro que se muestra en el grid al aplicar "
				+ "el rango de fechas y que ese registro contenga una cantidad de piezas realizadas (que no esté en cero)");
		validacion = pc.selectRegTipoDosCambiosFisicos(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		
		/*
		 * Paso 9 - Dar clic en el ícono de X para cerrar la vista preliminar
		 */
		addStep("Dar clic en el ícono de X para cerrar la vista preliminar");
		validacion = pc.obtenerTotalesDesdeTexto();
		assertTrue(validacion,"No se pudo obtener correctamente");
		validacion = pc.iconoXFormVer();
		assertTrue(validacion,"No se muestra correctamente");

		/*
		 * Paso 10 - Seleccionar el ícono de Excel
		 */
		u.hardWait(2);
		addStep("Seleccionar el ícono de Excel");
		validacion = pc.clicExcelDescargaMasiva();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 11 - Abrir el documento Excel recien descargado y validar formato del archivo 
		 */
		addStep("Validar el archivo Excel");
		validacion = pc.ejecutarValidacionCompletaExcelCF();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 12 - Validar las columnas del excel 
		 */
		addStep("Validar las columnas del excel ");
		validacion = pc.validarArchivoCompletoEncabezadosExcelCF();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 13 - Visualizar y comparar los datos de los precios en las columnas "Costo Total" y "Precio Total" 
		 * que se encuentran tanto en el documento Excel como en la vista preliminar del registro 
		 */
		addStep("Visualizar y comparar los datos de los precios en las columnas \"Costo Total\" y \"Precio Total\" que "
				+ "se encuentran tanto en el documento Excel como en la vista preliminar del registro");
		validacion = pc.compararTotalesConExcel();
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
		return "1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial\r\n"
				+ "2. Acceso a Portal Comenta\r\n"
				+ "3. Acceso a la pantalla \"Cambios Fisicos\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Desde una descarga masiva de la información filtrada en formato Excel, "
				+ "validar que los datos sea igual al que se muestra en la vista preliminar del registro de las siguientes columnas: \r\n"
				+ "1.- Costo Total\r\n"
				+ "2.- Precio Total";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_476_DescargaMasivaExcelComparativaCostoTotalPrecio_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}