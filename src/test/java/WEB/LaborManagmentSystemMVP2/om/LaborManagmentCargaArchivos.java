package WEB.LaborManagmentSystemMVP2.om;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LaborManagmentCargaArchivos {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	
	
	/**
	 * Localizadores
	 ******************************************************************************************/
	//Login
	By loginSuccess = By.xpath("//a[contains(@class, 'card_portal') and contains(@href, 'portalIncentivos')]");
	
	
	// Menu Carga de Archivos
	By cargaArchivosMenu = By.xpath("//a[contains(@class, 'nav-link') and contains(text(), 'Carga de archivos')]");
	By eliminarErrores = By.xpath("//td[contains(text(),'Errores_')]/following-sibling::td//button");
	By eliminarIncidencias = By.xpath("//td[contains(text(),'Incidencias_')]/following-sibling::td//button");
	By eliminarProductividad = By.xpath("//td[contains(text(),'Productividad_')]/following-sibling::td//button");
	By mensajeConfirmacion = By.xpath("//div[contains(@class,'modal-body')]//label[contains(text(),'¿Desea realmente eliminar el archivo?')]");
	By botonNoEliminar = By.xpath("//div[contains(@class,'modal-body')]//button[contains(@class,'btn-cancel') and text()=' NO ']");
	By botonSiEliminar = By.xpath("//div[contains(@class,'modal-body')]//button[contains(@class,'btn-save') and text()='SI']");
	By botonCargarArchivo = By.xpath("//button[contains(@class, 'btn-outline-dark') and contains(text(), 'Cargar')]");
	By noHayInformacionCargada = By.xpath("//label[contains(text(), 'No hay información para mostrar')]");
	By tablaCSV = By.xpath("//table[contains(@class, 'mat-mdc-table') and contains(@class, 'cdk-table')]");
	By cargaArchivos = By.xpath("//h3[text()='Carga de archivos']");
	By btnProcesar = By.xpath("//button[text()=' Procesar ']");
	By procesadoExito = By.xpath("//strong[text()='Archivos procesados con exito']");
	
	//Cargar CSV
	By toastExito = By.xpath("//div[@class='toast-body' and contains(text(),'El archivo se cargó de manera correcta')]");
	By labelSinDatos = By.xpath("//label[contains(text(),'No hay información para mostrar')]");
	By filasTabla = By.xpath("//table[@mat-table]//tbody/tr");
	By inputOculto = By.xpath("//button[normalize-space(text())='Cargar archivo']/following-sibling::input[@type='file' and @accept='.csv']");

	/**
	 * Constructor
	 * 
	 ******************************************************************************************/
	public LaborManagmentCargaArchivos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}
	

	
	


	/**
	     * Realiza un clic en el menú "Carga de Archivos" y valida que la vista o sección se haya cargado correctamente.
	     * @author Alejandro Montalvo
	     * @return Retorna {true} si la sección "Carga de Archivos" es visible después del clic; de lo contrario, retorna {@code false} y falla la prueba.
	     * @date 29/Septiembre/2025
	     */
	public boolean clicCargaArchivos() {
		boolean validacion = false;
		try {
			u.highLight(cargaArchivosMenu);
			u.click(cargaArchivosMenu);

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(cargaArchivos));
			testCase.addScreenShotCurrentStep(u, "Carga de menu carga archivos exitosa");

			if (u.isDisplayed(cargaArchivos)) {
				validacion = true;
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("No se pudo encontrar el menu carga archivos");
		}

		return validacion;
	}
	
	/**
	 * Valida si existen elementos en la columna "Nombre" dentro de una tabla HTML y
	 * elimina los archivos específicos si están presentes: Errores, Incidencias y
	 * Productividad.
	 *
	 * @Autor Alejandro Montalvo
	 * @throws Exception
	 * @date 22/Septiembre/2025
	 */
	public boolean existenElementosColumnaNombre(boolean confirmarEliminacion) {
	    boolean existen = false;
	    try {
	    	//Se utiliza hardwait ya que hay que esperar algunos segundos a que muestre la tabla 
	    	//Ya que al principio muestra "No hay informacion cargada"
	    	u.hardWait(10);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			List<WebElement> mensajeVacio = u.getDriver().findElements(noHayInformacionCargada);
			if (!mensajeVacio.isEmpty() && mensajeVacio.get(0).isDisplayed()) {
			    System.out.println("La tabla está vacía. No hay información para mostrar.");
			    testCase.addScreenShotCurrentStep(u, "No hay elementos a eliminar");
			    return true;
			}

			wait.until(ExpectedConditions.presenceOfElementLocated(tablaCSV));
	        // Verifica si hay elementos en la columna 'Nombre'
	        List<WebElement> elementosNombre = u.getDriver().findElements(By.xpath("//td[contains(@class, 'cdk-column-name')]"));

	        if (elementosNombre != null && !elementosNombre.isEmpty()) {
	            existen = true;
	            
	            eliminarSiVisible(eliminarErrores, confirmarEliminacion);
	            eliminarSiVisible(eliminarIncidencias, confirmarEliminacion);
	            eliminarSiVisible(eliminarProductividad, confirmarEliminacion);
	            //Espera 5 segundos para la siguiente accion
	            u.hardWait(5);
	        }

	    } catch (Exception e) {
	        System.out.println("Error al validar o eliminar elementos de la columna 'Nombre': " + e.getMessage());
	    }

	    return existen;
	}
	
	/**
	     * Verifica si los tres archivos CSV requeridos (Errores, Incidencias y Productividad) 
	     * se encuentran listados en la interfaz de usuario.
	     *      * Recorre la columna de nombres y busca la presencia de los tres tipos de archivos.
	     * Si los tres son encontrados, hace clic en el botón "Procesar".
	     * @author Alejandro Montalvo
	     * @return Retorna {@code true} si los tres archivos (Errores, Incidencias y Productividad) están presentes; 
	     * de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean existenArchivosCSVEsperados() {
	    try {
	    	
	        List<WebElement> elementosNombre = u.getDriver().findElements(By.xpath("//td[contains(@class, 'cdk-column-name')]"));
	        if (elementosNombre == null || elementosNombre.isEmpty()) {
	            System.out.println("No se encontraron elementos en la columna 'Nombre'.");
	            return false;
	        }

	        // Flags para cada tipo de archivo
	        boolean erroresEncontrado = false;
	        boolean incidenciasEncontrado = false;
	        boolean productividadEncontrado = false;

	        for (WebElement elemento : elementosNombre) {
	            String nombreArchivo = elemento.getText().trim().toLowerCase();

	            if (nombreArchivo.contains("errores") && nombreArchivo.endsWith(".csv")) {
	                erroresEncontrado = true;
	            } else if (nombreArchivo.contains("incidencias") && nombreArchivo.endsWith(".csv")) {
	                incidenciasEncontrado = true;
	            } else if (nombreArchivo.contains("productividad") && nombreArchivo.endsWith(".csv")) {
	                productividadEncontrado = true;
	            }

	            // Si ya se encontraron los tres, no hace falta seguir
	            if (erroresEncontrado && incidenciasEncontrado && productividadEncontrado) {
	                break;
	            }
	        }

	        boolean todosPresentes = erroresEncontrado && incidenciasEncontrado && productividadEncontrado;
	        System.out.println("Archivos encontrados - Errores: " + erroresEncontrado +
	                           ", Incidencias: " + incidenciasEncontrado +
	                           ", Productividad: " + productividadEncontrado);
	        testCase.addScreenShotCurrentStep(u, "Archivos csv encontrados");
	        return todosPresentes;

	    } catch (Exception e) {
	        System.out.println("Error al validar los archivos CSV esperados: " + e.getMessage());
	        return false;
	    }
	}
	
	
	/**
	     * Verifica si los tres archivos CSV requeridos (Errores, Incidencias y Productividad) 
	     * se encuentran listados en la interfaz de usuario.
	     *      * Recorre la columna de nombres y busca la presencia de los tres tipos de archivos.
	     * Si los tres son encontrados, hace clic en el botón "Procesar".
	     * @author Alejandro Montalvo
	     * @return Retorna {@code true} si los tres archivos (Errores, Incidencias y Productividad) están presentes; 
	     * de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean existenArchivosCSVEsperadosyProcesa() {
	    try {
	    	WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(120));
	        List<WebElement> elementosNombre = u.getDriver().findElements(By.xpath("//td[contains(@class, 'cdk-column-name')]"));
	        if (elementosNombre == null || elementosNombre.isEmpty()) {
	            System.out.println("No se encontraron elementos en la columna 'Nombre'.");
	            return false;
	        }

	        // Flags para cada tipo de archivo
	        boolean erroresEncontrado = false;
	        boolean incidenciasEncontrado = false;
	        boolean productividadEncontrado = false;

	        for (WebElement elemento : elementosNombre) {
	            String nombreArchivo = elemento.getText().trim().toLowerCase();

	            if (nombreArchivo.contains("errores") && nombreArchivo.endsWith(".csv")) {
	                erroresEncontrado = true;
	            } else if (nombreArchivo.contains("incidencias") && nombreArchivo.endsWith(".csv")) {
	                incidenciasEncontrado = true;
	            } else if (nombreArchivo.contains("productividad") && nombreArchivo.endsWith(".csv")) {
	                productividadEncontrado = true;
	            }

	            // Si ya se encontraron los tres, no hace falta seguir
	            if (erroresEncontrado && incidenciasEncontrado && productividadEncontrado) {
	                break;
	            }
	        }

	        boolean todosPresentes = erroresEncontrado && incidenciasEncontrado && productividadEncontrado;
	        System.out.println("Archivos encontrados - Errores: " + erroresEncontrado +
	                           ", Incidencias: " + incidenciasEncontrado +
	                           ", Productividad: " + productividadEncontrado);
	        //Se procesan los archivos una vez esten encontrados
	        u.click(btnProcesar);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(procesadoExito));
            System.out.println("Archivos cargados correctamente");
	        return todosPresentes;

	    } catch (Exception e) {
	        System.out.println("Error al validar los archivos CSV esperados: " + e.getMessage());
	        return false;
	    }
	}
	
	/**
     * Intenta hacer clic en el botón de eliminar y gestiona la ventana de confirmación (pop-up).
     * El proceso solo se ejecuta si el botón de eliminar es visible. Si la eliminación es confirmada,
     * espera un tiempo fijo para que el elemento desaparezca de la tabla.
     * * @author Alejandro Montalvo
     * @param eliminarErrores El localizador (By) del botón de eliminar (ej. el ícono de bote de basura).
     * @param confirmarEliminacion {@code true} para hacer clic en el botón "Sí" de la confirmación; {@code false} para "No".
     * @date 29/Septiembre/2025
     */
	public void eliminarSiVisible(By eliminarErrores, boolean confirmarEliminacion) {
	    try {
	        if (u.isDisplayed(eliminarErrores)) {
	            u.click(eliminarErrores);

	            if (waitForElementVisible(mensajeConfirmacion, 5)) {
	                u.getDriver().findElement(confirmarEliminacion ? botonSiEliminar : botonNoEliminar).click();
	                testCase.addScreenShotCurrentStep(u, "Se elimino o no correctamente");
	                //Espera 5 segundos a que se elimine correctamente de la tabla
	                u.hardWait(5);	            
	            } else {
	                System.out.println("No se mostró el mensaje de confirmación.");
	            }
	        } else {
	            System.out.println("El botón de eliminar no está visible.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error al intentar eliminar: " + e.getMessage());
	    }
	}
	
	
	/**
	     * Espera de forma explícita hasta que el elemento identificado por el localizador sea visible en la página.
	     * Utiliza {@code WebDriverWait} y {@code ExpectedConditions} para manejar la sincronización del AJAX y la carga dinámica.
	     * @author Alejandro Montalvo
	     * @param locator El {@code By} (localizador) del elemento a esperar.
	     * @param timeoutSeconds El tiempo máximo de espera en segundos.
	     * @return Retorna {@code true} si el elemento es visible dentro del tiempo de espera; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean waitForElementVisible(By locator, int timeoutSeconds) {
		    try {
		        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSeconds));
		        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		        return true;
		    } catch (TimeoutException e) {
		        return false;
		    }
		}
	
	
	/**
     * Itera sobre un arreglo de rutas de archivos, cargando cada archivo CSV de forma secuencial 
     * en un input oculto de la página web.
     * * @author Alejandro Montalvo
     * @param rutasCSV Un arreglo de {@code String} que contiene las rutas absolutas de los archivos CSV a cargar.
     * @return Retorna {@code true} si todos los archivos fueron cargados exitosamente, se visualizó el mensaje de éxito 
     * y las filas se reflejan en la tabla; de lo contrario, retorna {@code false}.
     * @date 29/Septiembre/2025
     */	
	public boolean cargarArchivosCSVUnoPorUno(String[] rutasCSV) {
	    try {

	    	WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
	        for (int i = 0; i < rutasCSV.length; i++) {
	            String rutaArchivo = rutasCSV[i];
	            File archivo = new File(rutaArchivo);

	            if (!archivo.exists()) {
	                System.out.println("Archivo no encontrado: " + rutaArchivo);
	                return false;
	            }
	            
	            WebElement inputFile = wait.until(ExpectedConditions.presenceOfElementLocated(inputOculto));

	            ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].removeAttribute('hidden')", inputFile);
	            inputFile.sendKeys(rutaArchivo);

	            System.out.println("Archivo enviado al input: " + rutaArchivo);
	            //System.out.println("Valor del input: " + inputFile.getAttribute("value"));

	            wait.until(ExpectedConditions.visibilityOfElementLocated(toastExito));
	            System.out.println("Archivo cargado correctamente: " + rutaArchivo);
                testCase.addScreenShotCurrentStep(u, "Se cargo el archivo correctamente");
	            

	            final int expectedRows = i + 1;
	            wait.until(driver1 -> driver1.findElements(filasTabla).size() >= expectedRows);
	        }

	        int filas = u.getDriver().findElements(filasTabla).size();
	        if (filas == rutasCSV.length) {
	            System.out.println("Todos los archivos están visibles en la tabla.");
	            return true;
	        } else {
	            System.out.println("Se esperaban " + rutasCSV.length + " archivos, pero hay: " + filas);
	            return false;
	        }

	    } catch (Exception e) {
	        System.out.println("Error durante la carga de archivos: " + e.getMessage());
	        return false;
	    }
	}

	/**
	     * Valida la presencia de los tres archivos de actualización esperados (Errores, Incidencias y Productividad) 
	     * en la tabla de base de datos {@code xxmai_inc.xxmai_upd_files}.
	     * Los nombres de los archivos se construyen dinámicamente utilizando el prefijo y la fecha formateada proporcionada.
	     * @author Alejandro Montalvo
	     * @param URL La URL de conexión a la base de datos.
	     * @param USER El nombre de usuario para la conexión a la base de datos.
	     * @param PASSWORD La contraseña para la conexión a la base de datos.
	     * @param fechaFormateada La porción de la fecha con el formato esperado para el nombre de los archivos (ej. AAAA-MM-DD).
	     * @return Retorna {@code true} si los tres archivos esperados están presentes en la base de datos; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean validarArchivosActualizacion(String URL, String USER, String PASSWORD, String fechaFormateada) {

	    Set<String> archivosEsperados = new HashSet<>(Arrays.asList(
	            "Errores_" + fechaFormateada + ".csv",
	            "Incidencias_" + fechaFormateada + ".csv",
	            "Productividad_" + fechaFormateada + ".csv"
	        ));

	    Set<String> archivosEncontrados = new HashSet<>();

	    String query = "SELECT file_name FROM xxmai_inc.xxmai_upd_files;";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	          Statement stmt = conn.createStatement();
	          ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            String fileName = rs.getString("file_name");
	            if (archivosEsperados.contains(fileName)) {
	                archivosEncontrados.add(fileName);
	            }
	        }

	        // ---------- INICIO DE LA SECCIÓN AGREGADA PARA IMPRESIÓN ----------
	        boolean resultadoFinal = archivosEncontrados.containsAll(archivosEsperados);

	        System.out.println("--- RESULTADO DE VALIDACIÓN ---");
	        System.out.println("Archivos Esperados: " + archivosEsperados);
	        System.out.println("Archivos Encontrados en DB: " + archivosEncontrados);
	        System.out.println("Validación: " + (resultadoFinal ? "ÉXITO" : "FALLO - Faltan archivos"));
	        testCase.addTextEvidenceCurrentStep("Archivos Esperados: " + archivosEsperados);
	        testCase.addTextEvidenceCurrentStep("Archivos Encontrados en DB: " + archivosEncontrados);
	        System.out.println("---------------------------------");
	        
	        return resultadoFinal;
	        // ---------- FIN DE LA SECCIÓN AGREGADA PARA IMPRESIÓN ----------

	    } catch (SQLException e) {
	        System.err.println("Error al consultar archivos de actualización: " + e.getMessage());
	        return false;
	    }
	}
		

}