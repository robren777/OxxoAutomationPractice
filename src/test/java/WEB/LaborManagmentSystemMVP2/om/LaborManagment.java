package WEB.LaborManagmentSystemMVP2.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.BOMInputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LaborManagment {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	
	
	/**
	 * Localizadores
	 ******************************************************************************************/
	By botonAvanzado = By.xpath("//button[@id='details-button' and text()[contains(., 'Avanzado')]]");
	By botonContinuar = By.xpath("//a[@id='proceed-link' and contains(text(), 'Continuar')]");
	By userLabel = By.xpath("//*[@id=\"email\"]");
	By passwordLabel = By.xpath("//*[@id=\"password\"]");
	By loginButton = By.xpath("//*[@id=\"login\"]");
	By loginSuccess = By.xpath("//a[contains(@class, 'card_portal') and contains(@href, 'portalIncentivos')]");
	By portalincentivos = By.xpath("//a[normalize-space()='Portal de incentivos']");
	
	By cargaArchivos = By.xpath("//h3[text()='Carga de archivos']");
	
	//Menu Tabuladores
	By tabuladoresMenu = By.xpath("//a[contains(@class, 'nav-link') and contains(text(), 'Tabuladores')]");
	By cargaTabuladores = By.xpath("//h3[text()='Tabulador de producción total']");
		

	
	// Selectores para botones de acción
	By btnEditar = By.xpath("//button[contains(text(), 'Editar')]");
	By btnGuardar = By.xpath("//button[contains(text(), 'Guardar')]");
 	By btnCancelar = By.xpath("//button[contains(text(), 'Cancelar')]");
    
    // Selectores para el modal de confirmación (Ajustar si tus selectores son diferentes)
    By labelRealizarCambios = By.xpath("//*[contains(text(), '¿Desea realizar los cambios?')]");
    By btnConfirmarSiCambios = By.xpath("//button[normalize-space(text())='Si']");
    By btnConfirmarNoCambios = By.xpath("//button[normalize-space(text())='No']");
    
   
    By msgAlertaSoloNumerico = By.xpath("//div[contains(@class, 'toast-body') and text()=' El campo Horas Productivas solo permite datos numéricos ']");    
    By msgAlertaCamposVacios = By.xpath("//div[contains(@class, 'toast-body') and text()=' Favor de completar todos los campos vacíos ']");
    
	String horasProdXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' HORAS PROD ']]";    		    
    String diasSemXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Días sem ']]";     
    String paroOperativoXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Paro operativo ']]";    
    String produccionXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Producción ']]";  
    String horasTurnoXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Horas turno ']]";
    String horasComidaXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Horas comida ']]";

 // ======================================================================
    // 1. CONSTANTES DE MAPEO (Nombres del CSV) y Nombres de Tablas BD
    // ======================================================================

    private static final String TABLA_ERRORES = "xxmai_inc.XXMAI_ERRORS";
    private static final String TABLA_INCIDENCIAS = "xxmai_inc.XXMAI_INCIDENTS";
    private static final String TABLA_PRODUCTIVIDAD = "xxmai_inc.XXMAI_PRODUCTION";

    // Nombres de las columnas de la BD ORDENADOS según la secuencia del CSV.
    // Usamos el nombre del CSV como valor para la clave en el Map de resultados.
    private static final String[] ENCABEZADOS_CSV_ERRORES = { 
        "CEDIS", "SEMANA", "AÑO", "ACT CLAVE", "FEC_REP", "AREA", "USUARIO", "NOMBRE", "TOTAL_AUDITA", "ERROR" 
    };
    // Sus equivalentes en BD para filtros (las CLAVES):
    private static final String[] CLAVES_ERRORES = {"id_employee", "date", "activity_code"}; 

    private static final String[] ENCABEZADOS_CSV_INCIDENCIAS = { 
        "CEDIS", "Semana", "Año", "Número", "Nombre", "Turno", "Total Asistencias", "Total Faltas", "Total INC", 
        "Total SASSO", "Total Baja", "Total PCG", "Total PSG", "Faltas", "Vacaciones", "Paro Individual", "Paro Grupal" 
    };
    private static final String[] CLAVES_INCIDENCIAS = {"id_employee", "week", "year"}; 

    private static final String[] ENCABEZADOS_CSV_PRODUCTIVIDAD = { 
        "CEDIS", "SEMANA", "AÑO", "ACT CLAVE", "FEC_REP", "USUARIO", "NOMBRE", "ACTIVIDAD", "HR07", "HR08", "HR09", 
        "HR10", "HR11", "HR12", "HR13", "HR14", "HR15", "HR16", "HR17", "HR18", "HR19", "HR20", "HR21", "HR22", 
        "HR23", "HR00", "HR01", "HR02", "HR03", "HR04", "HR05", "HR06", "TOTAL", "HRS"
    };
    private static final String[] CLAVES_PRODUCTIVIDAD = {"id_employee", "date", "activity_code"}; 


    
	/**
	 * Constructor
	 * 
	 ******************************************************************************************/
	public LaborManagment(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);

	}
    
    
    
	/**
	 * Metodos Generales
	 * 
	 ******************************************************************************************/
	
	/**
	 * Navega a una URL principal, ignorando cualquier error de WebDriver, y luego
	 * navega a una URL alternativa obligatoria, esperando la carga completa de la página.
	 * * Este método maximiza la ventana del navegador al inicio.
	 * * @param urlPrincipal La primera URL que se intentará cargar. Los fallos al cargar 
	 * esta URL (WebDriverException) serán capturados, mostrados 
	 * como advertencia y se continuará la ejecución.
	 * @param urlAlternativa La segunda URL que se cargará. La prueba/ejecución fallará
	 * si esta URL no se puede cargar correctamente.
	 */
	public void navegarURLsConManejoDeError(String urlPrincipal, String urlAlternativa) {
	    // 1. Maximiza la ventana del navegador.
	    u.getDriver().manage().window().maximize();

	    System.out.println("Intentando cargar la URL principal: " + urlPrincipal);

	    // 2. Intenta cargar la URL principal con manejo de excepción.
	    try {
	        u.get(urlPrincipal);
	        System.out.println("Carga de URL principal exitosa.");
	    } catch (org.openqa.selenium.WebDriverException e) {
	        // Captura e ignora la excepción de WebDriver.
	        System.err.println("¡Advertencia! Error al cargar la URL principal. Se ignora y se continúa: " + e.getMessage());
	    }

	    // 3. Carga la URL alternativa (sin manejo de excepción en este punto,
	    //    asumiendo que si falla, debe fallar la prueba/ejecución).
	    System.out.println("Cargando URL alternativa: " + urlAlternativa);
	    u.get(urlAlternativa);
	    
	    // 4. Espera a que la página cargue completamente.
	    u.waitForLoadPage();
	    WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
	    try {
	        WebElement botonAvanzado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("details-button")));
	        botonAvanzado.click();
	        
	        WebElement botonContinuar = wait.until(ExpectedConditions.elementToBeClickable(By.id("proceed-link")));
	        botonContinuar.click();
	    } catch (org.openqa.selenium.WebDriverException e) {
	        System.err.println("No aparecio la pantalla de conexion privada, se continua" + e.getMessage());	        
	    }
	    System.out.println("Navegación completa. Página cargada.");
	    }
	
	
	/**
	 * Realiza el proceso de login en el sistema Labor Management utilizando usuario
	 * y contraseña.
	 *
	 * @param dato  Nombre de usuario
	 * @param dato2 Contraseña
	 * @Autor Alejandro Montalvo
	 * @throws Exception
	 * @date 22/Septiembre/2025
	 */
	public boolean loginLaborManagment(String dato, String dato2) {
		boolean validacion = false;
		try {
			u.highLight(userLabel);
			u.clear(userLabel);
			u.sendKeys(userLabel, dato);

			u.highLight(passwordLabel);
			u.clear(passwordLabel);
			u.sendKeys(passwordLabel, dato2);

			u.highLight(loginButton);			
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(loginButton);
			u.waitWebElement(portalincentivos, 60, 4);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(50));
			wait.until(ExpectedConditions.presenceOfElementLocated(loginSuccess));


			if (u.isDisplayed(loginSuccess)) {
				validacion = true;
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("No se pudo realizar login");
		}

		return validacion;
	}		
	
	/**
	     * Verifica si un elemento web está presente en el DOM y es visible para el usuario.
	     * Maneja excepciones de forma segura, retornando {@code false} si el elemento no se encuentra o no es visible.
	     * @author Alejandro Montalvo
	     * @param element El {@code WebElement} a verificar.
	     * @return Retorna {@code true} si el elemento es distinto de {@code null} y es visible; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean isDisplayed(WebElement element) {
		    try {
		        return element != null && element.isDisplayed();
		    } catch (Exception e) {
		        return false;
		    }
		}

	/**
	     * Realiza un clic en el elemento web proporcionado, siempre y cuando sea distinto de {@code null} y sea visible.
	     * Captura y reporta cualquier excepción durante la operación de clic sin detener la ejecución de la prueba.
	     * @author Alejandro Montalvo
	     * @param element El {@code WebElement} donde se desea hacer clic.
	     * @date 29/Septiembre/2025
	     */
	public void click(WebElement element) {
		    try {
		        if (element != null && element.isDisplayed()) {
		            element.click();
		        }
		    } catch (Exception e) {
		        System.out.println("Error al hacer click en el elemento: " + e.getMessage());
		    }
		}

	/**
	     * Resalta visualmente un elemento web inyectando código JavaScript para aplicar un borde rojo.
	     * Útil para fines de depuración y para indicar visualmente qué elemento se está usando en el paso de la prueba.
	     * @author Alejandro Montalvo
	     * @param element El {@code WebElement} que se desea resaltar.
	     * @date 29/Septiembre/2025
	     */
	public void highLight(WebElement element) {
		    try {
		        JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		        js.executeScript("arguments[0].style.border='3px solid red'", element);
		    } catch (Exception e) {
		        System.out.println("Error al resaltar el elemento: " + e.getMessage());
		    }
		}
	

	



	/**
	     * Lee un archivo CSV de plantilla de errores, corrige problemas de codificación comunes ("AÃ‘O" a "AÑO") 
	     * y sobrescribe las columnas "SEMANA" y "FEC_REP" en todas las filas de datos con los valores proporcionados.
	     * Finalmente, escribe el contenido modificado en un nuevo archivo CSV compatible con Excel.
	     * @author Alejandro Montalvo
	     * @param rutaTemplate La ruta absoluta del archivo CSV de plantilla a leer.
	     * @param rutaDestino La ruta absoluta donde se guardará el archivo CSV modificado.
	     * @param semanaValor El valor de la semana que se insertará en la columna "SEMANA" para todas las filas de datos.
	     * @param fecRepValor El valor de la fecha de reporte que se insertará en la columna "FEC_REP" para todas las filas de datos.
	     * @return Retorna {@code true} si el archivo se preparó y se escribió correctamente; de lo contrario, retorna {@code false}.
	     * @date 23/Septiembre/2025
	     */
	public boolean prepararArchivoErrores(String rutaTemplate, String rutaDestino, String semanaValor, String fecRepValor) {
	    try (
	        BOMInputStream bomIn = new BOMInputStream(new FileInputStream(rutaTemplate));
	        InputStreamReader isr = new InputStreamReader(bomIn, StandardCharsets.UTF_8);
	        CSVReader reader = new CSVReader(isr);

	        // Escritura en UTF-8 con BOM para compatibilidad con Excel
	        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaDestino), StandardCharsets.UTF_8);
	        CSVWriter writer = new CSVWriter(osw)
	    ) {
	        // Escribe BOM manualmente
	        osw.write('\uFEFF');

	        List<String[]> allRows = reader.readAll();

	        // Corrección quirúrgica en celda C1 (índice 2)
	        if (allRows.size() > 0 && allRows.get(0).length > 2) {
	            String[] encabezado = allRows.get(0);
	            if (encabezado[2].equals("AÃ‘O")) {
	                encabezado[2] = "AÑO";
	            }
	        }

	        String[] encabezado = allRows.get(0);
	        int indexSemana = Arrays.asList(encabezado).indexOf("SEMANA");
	        int indexFecRep = Arrays.asList(encabezado).indexOf("FEC_REP");

	        for (int i = 0; i < allRows.size(); i++) {
	            String[] fila = allRows.get(i);
	            if (i == 0) {
	                writer.writeNext(fila); // encabezado corregido
	            } else {
	                    if (indexSemana != -1) fila[indexSemana] = semanaValor;
	                    if (indexFecRep != -1) fila[indexFecRep] = fecRepValor;
	                writer.writeNext(fila);
	            }
	        }

	        writer.flush(); // asegura que todo se escriba
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	     * Lee una plantilla de archivo CSV de incidencias, realiza correcciones de codificación ("AÃ‘O" a "AÑO") 
	     * y actualiza el valor de la columna "SEMANA" en todas las filas de datos con la semana proporcionada.
	     * El archivo modificado se guarda en la ruta de destino con codificación UTF-8 y BOM para asegurar la compatibilidad con Excel.
	     * @author Alejandro Montalvo
	     * @param rutaTemplate La ruta absoluta del archivo CSV de plantilla de incidencias a leer.
	     * @param rutaDestino La ruta absoluta donde se guardará el archivo CSV de incidencias modificado.
	     * @param semanaValor El valor de la semana que se insertará en la columna "SEMANA" para todas las filas de datos.
	     * @return Retorna {@code true} si el archivo se preparó y se escribió correctamente; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean prepararArchivoIncidencias(String rutaTemplate, String rutaDestino, String semanaValor) {
	    try (
	        BOMInputStream bomIn = new BOMInputStream(new FileInputStream(rutaTemplate));
	        InputStreamReader isr = new InputStreamReader(bomIn, StandardCharsets.UTF_8);
	        CSVReader reader = new CSVReader(isr);

	        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaDestino), StandardCharsets.UTF_8);
	        CSVWriter writer = new CSVWriter(osw)
	    ) {
	        osw.write('\uFEFF'); // BOM para Excel

	        List<String[]> allRows = reader.readAll();

	        // Corrección quirúrgica en celda C1
	        if (allRows.size() > 0 && allRows.get(0).length > 2) {
	            String[] encabezado = allRows.get(0);
	            if (encabezado[2].equals("AÃ‘O")) {
	                encabezado[2] = "AÑO";
	            }
	        }

	        String[] encabezado = allRows.get(0);
	        int indexSemana = Arrays.asList(encabezado).indexOf("SEMANA");

	        for (int i = 0; i < allRows.size(); i++) {
	            String[] fila = allRows.get(i);
	            if (i == 0) {
	                writer.writeNext(fila);
	            } else {
	            	if (indexSemana != -1) {
	                    fila[indexSemana] = semanaValor;
	                }
	                writer.writeNext(fila);
	            }
	        }

	        writer.flush();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	     * Lee un archivo CSV de plantilla de productividad, realiza correcciones de codificación necesarias ("AÃ‘O" a "AÑO")
	     * y sobrescribe las columnas "SEMANA" y "FEC_REP" en todas las filas de datos.
	     * El archivo modificado se guarda en la ruta de destino, asegurando la codificación UTF-8 con BOM para una correcta visualización en Excel.
	     * @author Alejandro Montalvo
	     * @param rutaTemplate La ruta absoluta del archivo CSV de plantilla de productividad a leer.
	     * @param rutaDestino La ruta absoluta donde se guardará el archivo CSV modificado.
	     * @param semanaValor El valor de la semana que se insertará en la columna "SEMANA" para todas las filas de datos.
	     * @param fecRepValor El valor de la fecha de reporte que se insertará en la columna "FEC_REP" para todas las filas de datos.
	     * @return Retorna {@code true} si el archivo se preparó y se escribió correctamente; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean prepararArchivoProductividad(String rutaTemplate, String rutaDestino, String semanaValor, String fecRepValor) {
	    try (
	        BOMInputStream bomIn = new BOMInputStream(new FileInputStream(rutaTemplate));
	        InputStreamReader isr = new InputStreamReader(bomIn, StandardCharsets.UTF_8);
	        CSVReader reader = new CSVReader(isr);

	        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(rutaDestino), StandardCharsets.UTF_8);
	        CSVWriter writer = new CSVWriter(osw)
	    ) {
	        osw.write('\uFEFF'); // BOM para Excel

	        List<String[]> allRows = reader.readAll();

	        // Corrección quirúrgica en celda C1
	        if (allRows.size() > 0 && allRows.get(0).length > 2) {
	            String[] encabezado = allRows.get(0);
	            if (encabezado[2].equals("AÃ‘O")) {
	                encabezado[2] = "AÑO";
	            }
	        }

	        String[] encabezado = allRows.get(0);
	        int indexSemana = Arrays.asList(encabezado).indexOf("SEMANA");
	        int indexFecRep = Arrays.asList(encabezado).indexOf("FEC_REP");

	        for (int i = 0; i < allRows.size(); i++) {
	            String[] fila = allRows.get(i);
	            if (i == 0) {
	                writer.writeNext(fila);
	            } else {
	                    if (indexSemana != -1) fila[indexSemana] = semanaValor;
	                    if (indexFecRep != -1) fila[indexFecRep] = fecRepValor;
	                writer.writeNext(fila);
	            }
	        }

	        writer.flush();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Realiza el clic en el enlace del portal de incentivos y valida que la carga
	 * del portal sea exitosa.
	 *
	 * @Autor Alejandro Montalvo
	 * @throws Exception
	 * @date 22/Septiembre/2025
	 */
	public boolean clicPortalIncentivos() {
		boolean validacion = false;
		try {
			u.highLight(loginSuccess);
			testCase.addScreenShotCurrentStep(u, "Seleccionar Opción Portal Incentivos");
			u.click(loginSuccess);

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(cargaArchivos));
			testCase.addScreenShotCurrentStep(u, "Carga de portal Incentivos Exitosa");

			if (u.isDisplayed(cargaArchivos)) {
				validacion = true;
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("No se pudo accesar al portal incentivos");
		}

		return validacion;
	}
      
	/**
	     * Ejecuta una consulta {@code SELECT} simple en la base de datos y retorna el valor de la primera columna 
	     * de la primera fila del resultado.
	     * El método gestiona la conexión, la ejecución del statement y el cierre seguro de todos los recursos ({@code ResultSet}, {@code Statement}, {@code Connection}).
	     * @author Alejandro Montalvo
	     * @param URL La URL de conexión a la base de datos.
	     * @param USER El nombre de usuario para la conexión a la base de datos.
	     * @param PASS La contraseña para la conexión a la base de datos.
	     * @param query La consulta SQL a ejecutar (debe ser una consulta SELECT que retorne datos).
	     * @return Retorna el valor de la primera columna de la primera fila como un {@code String}, o {@code null} si la consulta no retorna resultados o si ocurre un error crítico.
	     * @date 29/Septiembre/2025
	     */
    public String ejecutarConsultaBD(String URL, String USER, String PASS, String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String resultado = null;
        
        System.out.println("Ejecutando Query: " + query);

        try {
            // 1. Registrar el Driver (puede ser opcional dependiendo de la versión de Java)
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Ejemplo para MySQL
            
            // 2. Abrir la conexión
            conn = DriverManager.getConnection(URL, USER, PASS);

            // 3. Crear el statement
            stmt = conn.createStatement();
            
            // 4. Ejecutar la consulta
            rs = stmt.executeQuery(query);

            // 5. Procesar el resultado
            if (rs.next()) {
                // Obtiene el valor de la primera columna (índice 1)
                resultado = rs.getString(1); 
            } else {
                System.out.println("ADVERTENCIA: La consulta no retornó resultados.");
            }

        } catch (Exception e) {
            System.out.println("ERROR CRITICO en la conexión/ejecución de la BD: " + e.getMessage());
            e.printStackTrace(); // Imprime la traza completa del error de BD
            resultado = null; // Asegura que se retorne null en caso de fallo.
        } finally {
            // 6. Cerrar recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                // Ignorar errores al cerrar recursos
            }
        }
        
        System.out.println("Valor retornado de BD: " + resultado);
        testCase.addTextEvidenceCurrentStep("Valor retornado de BD: " + resultado);
        return resultado;
    }
    
    /**
     * Ejecuta una consulta SELECT compleja en la base de datos y retorna TODAS las filas y TODAS las columnas
     * del resultado como una lista de mapas.
     * Cada Map<String, String> representa una fila, donde la clave es el nombre de la columna y el valor es el dato.
     * Este método es ideal para validar conjuntos de datos completos (como la comparación con un CSV).
     * * @author Alejandro Montalvo
     * @param URL La URL de conexión a la base de datos.
     * @param USER El nombre de usuario para la conexión a la base de datos.
     * @param PASS La contraseña para la conexión a la base de datos.
     * @param query La consulta SQL a ejecutar.
     * @return Retorna una {@code List<Map<String, String>>} con todos los resultados. Retorna una lista vacía si la consulta no
     * tiene filas, o {@code null} si ocurre un error crítico de conexión/ejecución.
     * @date 29/Septiembre/2025
     */
    public List<Map<String, String>> ejecutarConsultaBD_List(String URL, String USER, String PASS, String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, String>> resultados = new ArrayList<>();
        
        System.out.println("Ejecutando Query (List): " + query);

        try {
            // 1. Abrir la conexión
            conn = DriverManager.getConnection(URL, USER, PASS);

            // 2. Crear el statement
            stmt = conn.createStatement();
            
            // 3. Ejecutar la consulta
            rs = stmt.executeQuery(query);

            // Obtener metadatos para saber los nombres de las columnas
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // 4. Procesar todas las filas del resultado
            while (rs.next()) {
                Map<String, String> fila = new HashMap<>();
                
                // Iterar sobre todas las columnas de la fila actual
                for (int i = 1; i <= columnCount; i++) {
                    // Usamos el nombre de la columna en MINÚSCULAS para coincidir con las claves del CSV
                    String columnName = rsmd.getColumnLabel(i).toLowerCase();
                    // Obtener el valor como String (más seguro para comparación CSV)
                    String value = rs.getString(i);
                    
                    // Manejar valores nulos de la BD
                    fila.put(columnName, value != null ? value.trim() : ""); 
                }
                resultados.add(fila);
            }
            
            if (resultados.isEmpty()) {
                System.out.println("ADVERTENCIA: La consulta no retornó resultados.");
            } else {
                System.out.println("Resultados de BD obtenidos: " + resultados.size() + " filas.");
            }

        } catch (Exception e) {
            System.err.println("ERROR CRITICO en la conexión/ejecución de la BD (List): " + e.getMessage());
            e.printStackTrace();
            return null; // Retorna null en caso de error crítico de BD
        } finally {
            // 5. Cerrar recursos en el bloque finally
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                // Ignorar errores al cerrar recursos
            }
        }
        
        return resultados;
    }
    
    /**
         * Ejecuta el flujo completo de validación y actualización de la cabecera de la tabla "Surtido Cajas",
         * incluyendo:
         * 1. Clic en "Editar" y validación de campos no editables (etiquetas).
         * 2. Validación de la funcionalidad de "Cancelar" cambios.
         * 3. Validación de errores de campos: intenta guardar con valores no numéricos y campos vacíos.
         * 4. Ingreso de los nuevos valores proporcionados y confirmación de guardado de los cambios.
         * 5. Limpieza de todos los campos de entrada al finalizar.
         * @author Alejandro Montalvo
         * @param nuevoHorasProd El nuevo valor numérico a ingresar en los campos de "Horas de Producción".
         * @param nuevoDiasSem El nuevo valor numérico a ingresar en los campos de "Días de la Semana".
         * @param nuevoParoOperativo El nuevo valor numérico a ingresar en los campos de "Paro Operativo".
         * @param nuevoProduccion El nuevo valor numérico a ingresar en los campos de "Producción".
         * @return Retorna {@code true} si todas las validaciones y el flujo de guardado se completan sin errores; de lo contrario, retorna {@code false}.
         * @date 29/Septiembre/2025
         */
    public boolean validarActualizacionCabeceraSurtidoCajas2(
            String nuevoHorasProd, 
            String nuevoDiasSem, 
            String nuevoParoOperativo, 
            String nuevoProduccion
        ) {
    	boolean resultado = false;
        WebElement inpHrProd = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[1]//input"));            
        WebElement inpHrProd2 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[2]//input"));            
        WebElement inpHrProd3 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[3]//input"));
        WebElement inpDiasSem = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[1]//input"));
        WebElement inpDiasSem2 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[2]//input"));
        WebElement inpDiasSem3 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[3]//input"));
        WebElement inpParoOperativo = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[1]//input"));
        WebElement inpParoOperativo2 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[2]//input"));
        WebElement inpParoOperativo3 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[3]//input"));
        WebElement inpProduccion = u.getDriver().findElement(By.xpath(produccionXpath + "/td[1]//input"));
        WebElement inpProduccion2 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[2]//input"));
        WebElement inpProduccion3 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[3]//input"));
        WebElement lblHrTurno = u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[1]"));
        WebElement lblHrTurno2 = u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[2]"));
        WebElement lblHrTurno3 = u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[3]"));
        WebElement lblHrComida = u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[1]"));
        WebElement lblHrComida2 = u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[2]"));
        WebElement lblHrComida3 = u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[3]"));
		final List<WebElement> horasProdInputs = Arrays.asList(inpHrProd, inpHrProd2, inpHrProd3);
		final List<WebElement> diasSemInputs = Arrays.asList(inpDiasSem, inpDiasSem2, inpDiasSem3);
		final List<WebElement> paroOperativoInputs = Arrays.asList(inpParoOperativo, inpParoOperativo2,inpParoOperativo3);
		final List<WebElement> produccionInputs = Arrays.asList(inpProduccion, inpProduccion2, inpProduccion3);
	    List<List<WebElement>> todasLasListas = Arrays.asList(
	    	    horasProdInputs, diasSemInputs, paroOperativoInputs, produccionInputs
	    	);
    	try {
    		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15)); 
            // PASO 1: Clic en 'Editar'
            u.click(btnEditar);
            wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardar));
            testCase.addScreenShotCurrentStep(u, "Se dio clic en editar");
            
            validarLabelNoEditables(lblHrTurno,lblHrTurno2,lblHrTurno3);
			validarLabelNoEditables(lblHrComida,lblHrComida2,lblHrComida3);
			validarCalculoHorasTurno(inpHrProd, inpHrProd2, inpHrProd3, inpDiasSem, inpDiasSem2, inpDiasSem3, lblHrTurno, lblHrTurno2, lblHrTurno3);			
						
			u.click(btnCancelar);
			
			//Se realiza la prueba de ingresar datos numericos y guardar			
			String textoPrueba = "ABC";
			modificarFilaInputs(horasProdInputs, textoPrueba);
            modificarFilaInputs(diasSemInputs, textoPrueba);
            modificarFilaInputs(paroOperativoInputs, textoPrueba);
            modificarFilaInputs(produccionInputs, textoPrueba);
            u.click(btnGuardar);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(msgAlertaSoloNumerico, "El campo solo permite datos numéricos"));
            //Se realiza la prueba de ingresar datos vacios y guardar
            String camposVacios = " ";
			modificarFilaInputs(horasProdInputs, camposVacios);
            modificarFilaInputs(diasSemInputs, camposVacios);
            modificarFilaInputs(paroOperativoInputs, camposVacios);
            modificarFilaInputs(produccionInputs, camposVacios);
            u.click(btnGuardar);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(msgAlertaCamposVacios, "Hay campos vacios en la tabla"));
            
			
            		    		    

            validarCancelarCambios(todasLasListas,nuevoHorasProd,nuevoDiasSem,nuevoParoOperativo,nuevoProduccion);
            
            
            
            //Se llenan los datos de los input editables
            modificarFilaInputs(horasProdInputs, nuevoHorasProd);
            modificarFilaInputs(diasSemInputs, nuevoDiasSem);
            modificarFilaInputs(paroOperativoInputs, nuevoParoOperativo);
            modificarFilaInputs(produccionInputs, nuevoProduccion);
            u.click(btnGuardar);            
            wait.until(ExpectedConditions.visibilityOfElementLocated(labelRealizarCambios));
            u.click(btnConfirmarNoCambios);
            u.click(btnGuardar);
            u.click(btnConfirmarSiCambios);
            
			limpiarTodosLosInputs(inpHrProd, inpHrProd2, inpHrProd3, inpDiasSem, inpDiasSem2, inpDiasSem3,inpParoOperativo, inpParoOperativo2, inpParoOperativo3, inpProduccion, inpProduccion2,inpProduccion3);
            
            testCase.addScreenShotCurrentStep(u, "Se modificaron los valores en cada campo editable");
                        
            
            resultado = true;
    	}
    	catch (Exception e) {
            System.out.println("Error en la validación de cabecera de surtido cajas (Flujo de UI): " + e.getMessage());
            resultado = false;
        }
        return resultado;
    }
    
    
    /**
         * Limpia el contenido de un elemento de entrada de texto (input, textarea) y luego escribe un nuevo valor.
         * @author Alejandro Montalvo
         * @param element El {@code WebElement} de tipo input o textarea a modificar.
         * @param value El {@code String} que se desea ingresar en el campo de texto.
         * @date 29/Septiembre/2025
         */
    public void modificarInput(WebElement element,String value) {
        element.clear(); // Limpiar por si tiene valor
        element.sendKeys(value);
    }
    
    /**
         * Aplica el mismo valor a una lista de elementos de entrada de texto, modificando cada uno secuencialmente.
         * Reutiliza {@code modificarInput(WebElement element, String value)} para la operación de limpieza y escritura.
         * @author Alejandro Montalvo
         * @param inputList Una {@code List<WebElement>} que contiene los campos de entrada a modificar.
         * @param value El {@code String} que se desea ingresar en cada elemento de la lista.
         * @date 29/Septiembre/2025
         */
    public void modificarFilaInputs(List<WebElement> inputList, String value) {
        for (WebElement element : inputList) {
            // Reutiliza tu método existente para la lógica de clear() y sendKeys()
            modificarInput(element, value);
        }
    }
    
    /**
         * Valida que un conjunto de elementos (etiquetas o celdas de tabla) estén visibles pero no sean editables.
         * La validación de no-editabilidad se realiza intentando invocar {@code sendKeys()} en el elemento, 
         * y esperando una excepción, y adicionalmente verifica que el {@code tagName} sea 'td' (celda de tabla).
         * @author Alejandro Montalvo
         * @param label1 El primer {@code WebElement} a verificar (Ej. Turno 1).
         * @param label2 El segundo {@code WebElement} a verificar (Ej. Turno 2).
         * @param label3 El tercer {@code WebElement} a verificar (Ej. Turno 3).
         * @date 29/Septiembre/2025
         */
    public void validarLabelNoEditables(WebElement label1, WebElement label2,WebElement label3 ) {
        
        // Lista de elementos a verificar
        WebElement[] elementosTurno = {label1, label2, label3};
        String[] nombresElementos = {"T1", "T2", "T3"};

        System.out.println("Iniciando validación de campos...");
        
        for (int i = 0; i < elementosTurno.length; i++) {
            WebElement elemento = elementosTurno[i];
            String nombre = nombresElementos[i];
            
            // 1. Verificar que el elemento esté visible (siempre debería ser true)
            Assert.assertTrue(elemento.isDisplayed(), "FAIL: El campo de la fila" + nombre + "' NO está visible.");
            
            // 2. Verificar que el elemento NO esté habilitado para edición.
            // Para un elemento <td> (celda de tabla), isEnabled() generalmente es false o levanta un error 
            // si no es un elemento de formulario (como un input), pero es una buena práctica.
            // Más importante aún: ¡Intentamos escribir!
            
            // 3. Verificación clave: Intentar enviar teclas. Si no es un input, fallará o no surtirá efecto.
            try {
                // Intentar interactuar con sendKeys. 
                // Esto es una prueba de fuego: si el elemento acepta entrada, es editable.
                elemento.sendKeys("TEST"); 
                
                // Si llegamos aquí, significa que sendKeys funcionó (es editable, lo cual es un fallo para esta prueba)
                String mensajeFallo = "FAIL: El campo de la fila " + nombre + " PERMITE edición (aceptó sendKeys).";
                System.err.println(mensajeFallo);
                Assert.fail(mensajeFallo); 
                
            } catch (Exception e) {
                // Si lanza una excepción (como InvalidElementStateException o una NoSuchElementException 
                // si el selector hubiera fallado), es probable que NO sea editable.
                
                // La mejor confirmación es si NO tiene la etiqueta <input>
                String tagName = elemento.getTagName();
                Assert.assertEquals(tagName, "td", "FAIL: El campo no es una celda <td> (es " + tagName + ") y podría ser editable.");
                
                System.out.println("PASS: Fila" + nombre + " verificado. Es una etiqueta de solo lectura (<" + tagName + ">).");
            }
        }
    }
   
    /**
         * Valida el cálculo de "Horas Turno" en la interfaz de usuario, donde: 
         * {@code Horas Turno = Horas Producción / Días Semanales}.
         * El método itera sobre los tres turnos (T1, T2, T3), extrae los valores de los inputs y las etiquetas, 
         * realiza la división y compara el resultado con el valor mostrado en pantalla, permitiendo una tolerancia de 0.01.
         * @author Alejandro Montalvo
         * @param inpHrProd Input de Horas Producción para el Turno 1.
         * @param inpHrProd2 Input de Horas Producción para el Turno 2.
         * @param inpHrProd3 Input de Horas Producción para el Turno 3.
         * @param inpDiasSem Input de Días Semanales para el Turno 1.
         * @param inpDiasSem2 Input de Días Semanales para el Turno 2.
         * @param inpDiasSem3 Input de Días Semanales para el Turno 3.
         * @param lblHrTurno Label/Display de Horas Turno para el Turno 1 (valor esperado).
         * @param lblHrTurno2 Label/Display de Horas Turno para el Turno 2 (valor esperado).
         * @param lblHrTurno3 Label/Display de Horas Turno para el Turno 3 (valor esperado).
         * @date 29/Septiembre/2025
         */
    public void validarCalculoHorasTurno(
            WebElement inpHrProd, WebElement inpHrProd2, WebElement inpHrProd3,
            WebElement inpDiasSem, WebElement inpDiasSem2, WebElement inpDiasSem3,
            WebElement lblHrTurno, WebElement lblHrTurno2, WebElement lblHrTurno3) {
            
            // Arrays para iterar sobre las tres columnas (T1, T2, T3)
            WebElement[] horasProdElements = {inpHrProd, inpHrProd2, inpHrProd3};
            WebElement[] diasSemElements = {inpDiasSem, inpDiasSem2, inpDiasSem3};
            WebElement[] horasTurnoElements = {lblHrTurno, lblHrTurno2, lblHrTurno3};
            String[] columnas = {"T1", "T2", "T3"};

            // Formato para redondear el resultado y la comparación, ya que se trata de valores double
            // Usaremos 2 decimales, basándonos en los datos del HTML (7.83, 7.60, 8.33)
            DecimalFormat df = new DecimalFormat("#.##"); 

            System.out.println("Iniciando validación del cálculo: (Horas Prod / Días Sem) == Horas Turno");

            for (int i = 0; i < horasProdElements.length; i++) {
                String col = columnas[i];
                
                // 1. Extracción de valores de los WebElements
                
                // Horas Prod (Inputs -> getAttribute("value"))
                String hrProdStr = horasProdElements[i].getAttribute("value");
                // Días Sem (Inputs -> getAttribute("value"))
                String diasSemStr = diasSemElements[i].getAttribute("value");
                // Horas Turno (Texto -> getText())
                String hrTurnoEsperadoStr = horasTurnoElements[i].getText().trim(); 

                try {
                    // 2. Conversión de String a Double
                    double hrProd = Double.parseDouble(hrProdStr);
                    double diasSem = Double.parseDouble(diasSemStr);
                    double hrTurnoEsperado = Double.parseDouble(hrTurnoEsperadoStr);

                    // Prevenir división por cero
                    if (diasSem == 0) {
                        System.err.println("ERROR DE DATOS en Columna " + col + ": Días Semanales es cero. No se puede calcular.");
                        Assert.fail("Validación fallida en " + col + ": Días Semanales es 0.");
                        continue; // Pasa a la siguiente iteración
                    }

                    // 3. Realizar el cálculo
                    double hrTurnoCalculado = hrProd / diasSem;
                    
                    // Redondear ambos valores a 2 decimales para la comparación (por precisión de punto flotante)
                    double calculadoRedondeado = Double.parseDouble(df.format(hrTurnoCalculado));
                    double esperadoRedondeado = Double.parseDouble(df.format(hrTurnoEsperado));
                    
                    // 4. Validación y aserción
                    String mensaje = String.format(
                        "%s: Calculado (%.2f / %.2f = %.2f) | Esperado: %.2f",
                        col, hrProd, diasSem, calculadoRedondeado, esperadoRedondeado);

                    Assert.assertEquals(calculadoRedondeado, esperadoRedondeado, 0.01, // 0.01 es la tolerancia (delta)
                        "FAIL en Columna " + col + ": El cálculo no coincide. " + mensaje);
                    
                    System.out.println("PASS en Columna " + col + ": Cálculo verificado. " + mensaje);

                } catch (NumberFormatException e) {
                    // Captura si alguno de los campos de input o label está vacío o contiene texto no numérico
                    String errorMsg = "FAIL: Error de formato en la Columna " + col + ". Asegúrate de que los campos tienen valores numéricos. Detalle: " + e.getMessage();
                    System.err.println(errorMsg);
                    Assert.fail(errorMsg);
                }
            }
        }

    /**
         * Limpia el contenido de todos los campos de entrada de texto (inputs) proporcionados como argumentos.
         * Agrupa todos los elementos en una lista interna y utiliza el método {@code .clear()} de Selenium para borrar los valores.
         * @author Alejandro Montalvo
         * @param inpHrProd Input de Horas Producción (T1).
         * @param inpHrProd2 Input de Horas Producción (T2).
         * @param inpHrProd3 Input de Horas Producción (T3).
         * @param inpDiasSem Input de Días Semanales (T1).
         * @param inpDiasSem2 Input de Días Semanales (T2).
         * @param inpDiasSem3 Input de Días Semanales (T3).
         * @param inpParoOperativo Input de Paro Operativo (T1).
         * @param inpParoOperativo2 Input de Paro Operativo (T2).
         * @param inpParoOperativo3 Input de Paro Operativo (T3).
         * @param inpProduccion Input de Producción (T1).
         * @param inpProduccion2 Input de Producción (T2).
         * @param inpProduccion3 Input de Producción (T3).
         * @date 29/Septiembre/2025
         */
    public void limpiarTodosLosInputs(
            WebElement inpHrProd, WebElement inpHrProd2, WebElement inpHrProd3,
            WebElement inpDiasSem, WebElement inpDiasSem2, WebElement inpDiasSem3,
            WebElement inpParoOperativo, WebElement inpParoOperativo2, WebElement inpParoOperativo3,
            WebElement inpProduccion, WebElement inpProduccion2, WebElement inpProduccion3) 
        {
            // 1. Agrupar todos los inputs en una lista para facilitar la iteración
            List<WebElement> todosLosInputs = Arrays.asList(
                inpHrProd, inpHrProd2, inpHrProd3,
                inpDiasSem, inpDiasSem2, inpDiasSem3,
                inpParoOperativo, inpParoOperativo2, inpParoOperativo3,
                inpProduccion, inpProduccion2, inpProduccion3
            );

            System.out.println("Iniciando limpieza de " + todosLosInputs.size() + " campos...");

            // 2. Iterar sobre la lista y aplicar .clear()
            for (WebElement input : todosLosInputs) {
                try {
                    // El método .clear() es la forma más limpia de borrar el contenido de un campo de texto.
                    input.clear();
                } catch (Exception e) {
                    // Manejo de errores por si el elemento no está visible, habilitado o si es de solo lectura.
                    System.err.println("Advertencia: No se pudo limpiar el input. Posiblemente no está habilitado o visible.");
                    // Puedes optar por usar sendKeys con las teclas CTRL+A y DELETE si .clear() falla 
                    // para inputs muy particulares, pero .clear() es preferido:
                    // input.sendKeys(Keys.CONTROL + "a");
                    // input.sendKeys(Keys.DELETE);
                }
            }
            
            System.out.println("Limpieza de campos completada.");
        }
    
    /**
         * Realiza un test de regresión para validar la funcionalidad del botón "Cancelar" en el modo de edición.
         * El flujo consiste en:
         * 1. Entrar en modo edición y asignar nuevos valores a todos los campos.
         * 2. Hacer clic en "Cancelar" y esperar la salida del modo edición.
         * 3. Re-entrar en modo edición y verificar que el valor actual de cada input NO es el valor recién ingresado.
         * @author Alejandro Montalvo
         * @param todasLasListas Una lista de listas de {@code WebElements} que representan las filas de inputs editables (Horas Prod, Días Sem, etc.).
         * @param nuevoHorasProd El valor que se intentará guardar en Horas Producción.
         * @param nuevoDiasSem El valor que se intentará guardar en Días Semanales.
         * @param nuevoParoOperativo El valor que se intentará guardar en Paro Operativo.
         * @param nuevoProduccion El valor que se intentará guardar en Producción.
         * @date 29/Septiembre/2025
         */
	public void validarCancelarCambios(List<List<WebElement>> todasLasListas, String nuevoHorasProd, String nuevoDiasSem, String nuevoParoOperativo, String nuevoProduccion) {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
			System.out.println("--- 1. Entrando en modo edición y asignando valores ---");
			// Vuelve a entrar a edición (por si acaso el estado inicial es de solo lectura)
			u.click(btnEditar);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardar));

			// **Llenar los inputs con los valores que NO deben guardarse**
			modificarFilaInputs(todasLasListas.get(0), nuevoHorasProd); // Horas Prod
			modificarFilaInputs(todasLasListas.get(1), nuevoDiasSem); // Días Sem
			modificarFilaInputs(todasLasListas.get(2), nuevoParoOperativo); // Paro Operativo
			modificarFilaInputs(todasLasListas.get(3), nuevoProduccion); // Producción

			System.out.println("Valores asignados. Procediendo a Cancelar.");
			u.click(btnCancelar);

			// Espera a que el botón GUARDAR desaparezca, confirmando que salimos del modo
			// edición
			wait.until(ExpectedConditions.invisibilityOfElementLocated(btnGuardar));

			System.out.println("--- 3. Re-entrando en modo edición para validar ---");
			// Vuelve a entrar para poder acceder a los inputs y leer su atributo 'value'
			u.click(btnEditar);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardar));

			// Define los valores que NO deberían estar en los inputs después de la
			// cancelación
			String[] valoresNoDeseados = { nuevoHorasProd, nuevoDiasSem, nuevoParoOperativo, nuevoProduccion };
			String[] nombresCampos = { "Horas Prod", "Días Sem", "Paro Operativo", "Producción" };

			for (int i = 0; i < todasLasListas.size(); i++) {
				List<WebElement> filaInputs = todasLasListas.get(i);
				String valorNoDeseado = valoresNoDeseados[i];
				String nombreCampo = nombresCampos[i];

				// Iterar sobre T1, T2 y T3 de cada fila
				for (int j = 0; j < filaInputs.size(); j++) {
					WebElement input = filaInputs.get(j);
					String valorActual = input.getAttribute("value");
					String columna = "T" + (j + 1);


					Assert.assertNotEquals(valorActual, valorNoDeseado,
							String.format("FAIL en %s (%s): El valor '%s' SE GUARDÓ después de CANCELAR.", nombreCampo,
									columna, valorNoDeseado));

					System.out.println(
							String.format("PASS: %s (%s) valor revertido correctamente (Actual: '%s' | No es '%s').",
									nombreCampo, columna, valorActual, valorNoDeseado));
				}
			}

			System.out.println("\nValidación de la función Cancelar completada con éxito. Ningún cambio fue guardado.");
		}
	
	/**
	     * Elimina los archivos CSV generados por los tests de carga (Errores, Incidencias y Productividad) 
	     * del directorio local del proyecto.
	     * El método construye las rutas de archivo dinámicamente utilizando la fecha actual y la ruta base del proyecto.
	     * @author Alejandro Montalvo
	     * @return Retorna {@code true} si todos los archivos que existían fueron eliminados exitosamente; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean limpiarArchivosGenerados() {
	    // 1. Definición de variables base (Tomadas de tu ejemplo)
	    LocalDate hoy = LocalDate.now();
	    String fechaFormateada = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    String rutaBase = System.getProperty("user.dir");
	    String subDirectorio = "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/";
	    
	    // 2. Construcción de las rutas completas
	    List<Path> rutasAEliminar = new ArrayList<>();
	    
	    // Ruta Destino 1: Errores
	    String nombreArchivo1 = "Errores_" + fechaFormateada + ".csv";
	    rutasAEliminar.add(Paths.get(rutaBase + subDirectorio + nombreArchivo1));
	    
	    // Ruta Destino 2: Incidencias
	    String nombreArchivo2 = "Incidencias_" + fechaFormateada + ".csv";
	    rutasAEliminar.add(Paths.get(rutaBase + subDirectorio + nombreArchivo2));
	    
	    // Ruta Destino 3: Productividad
	    String nombreArchivo3 = "Productividad_" + fechaFormateada + ".csv";
	    rutasAEliminar.add(Paths.get(rutaBase + subDirectorio + nombreArchivo3));

	    boolean todosEliminados = true;
	    
	    // 3. Eliminación de archivos
	    System.out.println("--- Limpiando archivos CSV generados ---");
	    
	    for (Path ruta : rutasAEliminar) {
	        File archivo = ruta.toFile();
	        if (archivo.exists()) {
	            try {
	                // Usamos Files.deleteIfExists para una eliminación segura
	                boolean eliminado = Files.deleteIfExists(ruta);
	                if (eliminado) {
	                    System.out.println("ÉXITO: Archivo eliminado: " + archivo.getName());
	                } else {
	                    System.err.println("FALLO: No se pudo eliminar el archivo: " + archivo.getName());
	                    todosEliminados = false;
	                }
	            } catch (Exception e) {
	                System.err.println("ERROR: Excepción al intentar eliminar " + archivo.getName() + ": " + e.getMessage());
	                todosEliminados = false;
	            }
	        } else {
	            // No es un fallo crítico si el archivo no existe (ej. el test falló antes de crearlo)
	            System.out.println("INFO: Archivo no encontrado (omitiendo): " + archivo.getName());
	        }
	    }
	    
	    return todosEliminados;
	}
	
	/**
     * Método de control principal. Orquesta la validación de los tres tipos de archivos CSV
     * contra sus respectivas tablas en la Base de Datos.
     * @param URL La URL de conexión a la base de datos.
     * @param USER El nombre de usuario para la conexión a la base de datos.
     * @param PASSWORD La contraseña para la conexión a la base de datos.
     * @return Retorna true si todos los archivos y BD coinciden, false en caso de fallo.
     * @author Alejandro Montalvo
	 * @date 29/Septiembre/2025     
     */
    public boolean validarDatosCSVsVsBD(String URL, String USER, String PASSWORD) {
        boolean resultadoFinal = true;
        
        // Configuración de Fechas y Rutas
        LocalDate hoy = LocalDate.now();
        String fechaBD = hoy.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fechaArchivo = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String rutaBase = System.getProperty("user.dir");
        
        String rutaError = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Errores_" + fechaArchivo + ".csv";
        String rutaIncidencia = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Incidencias_" + fechaArchivo + ".csv";
        String rutaProductividad = rutaBase + "/src/test/java/LaborManagmentSystemMVP2/archivoscsv/Productividad_" + fechaArchivo + ".csv";
        
        // V A L I D A C I Ó N   E R R O R E S
        if (!compararDatos(rutaError, ENCABEZADOS_CSV_ERRORES, CLAVES_ERRORES, TABLA_ERRORES, fechaBD, URL, USER, PASSWORD)) {
            resultadoFinal = false;
        }

        // V A L I D A C I Ó N   I N C I D E N C I A S
        if (!compararDatos(rutaIncidencia, ENCABEZADOS_CSV_INCIDENCIAS, CLAVES_INCIDENCIAS, TABLA_INCIDENCIAS, fechaBD, URL, USER, PASSWORD)) {
            resultadoFinal = false;
        }

        // V A L I D A C I Ó N   P R O D U C T I V I D A D
        if (!compararDatos(rutaProductividad, ENCABEZADOS_CSV_PRODUCTIVIDAD, CLAVES_PRODUCTIVIDAD, TABLA_PRODUCTIVIDAD, fechaBD, URL, USER, PASSWORD)) {
            resultadoFinal = false;
        }

        System.out.println(resultadoFinal ? 
            "\n--- VALIDACIÓN FINAL GLOBAL: TODOS LOS ARCHIVOS CSV COINCIDEN CON LA BD. ---" :
            "\n--- VALIDACIÓN FINAL GLOBAL: FALLO EN UNO O MAS ARCHIVOS. ---");
            
        return resultadoFinal;
    }

    /**
     * Método auxiliar que implementa la lógica de Validación por Existencia (la más robusta).
     * @param encabezadosCSV Los nombres de las columnas del CSV (Ej: "CEDIS").
     * @param clavesBD Los nombres de las columnas de la BD que forman la clave primaria (Ej: "id_employee", "date").
     * @param nombreTabla El nombre completo de la tabla BD (Ej: "xxmai_inc.XXMAI_ERRORS").
     * @param fechaBD La fecha del día de hoy en formato 'yyyy-MM-dd' para el filtro principal de la BD.
     * @author Alejandro Montalvo
	 * @date 29/Septiembre/2025         
     */
    private boolean compararDatos(String rutaCSV, String[] encabezadosCSV, String[] clavesBD, String nombreTabla, String fechaBD, String URL, String USER, String PASSWORD) {
        String tipoArchivo = nombreTabla.substring(nombreTabla.lastIndexOf('.') + 1); 
        System.out.println("\n--- INICIANDO VALIDACIÓN: " + tipoArchivo + " (SOLO CONTEO GLOBAL) ---");
        
        // 1. Lectura del CSV para obtener el conteo de filas.
        List<Map<String, String>> datosCSV = leerCSV(rutaCSV, encabezadosCSV);
        
        if (datosCSV == null) {
            System.err.println("FAIL en " + tipoArchivo + ": Error de lectura del CSV.");
            return false;
        }
        
        int countCSV = datosCSV.size();

        // 2. Obtener conteo total en la BD para el día de hoy.
        String queryCount = String.format(
            "SELECT COUNT(*) FROM %s WHERE DATE(creation_date) = '%s'", 
            nombreTabla, fechaBD
        );
        
        System.out.println("Ejecutando Query: " + queryCount);
        
        // Asumiendo que ejecutarConsultaBD devuelve una cadena (el conteo)
        String countBDStr = ejecutarConsultaBD(URL, USER, PASSWORD, queryCount);
        int countBD = Integer.parseInt(countBDStr != null ? countBDStr.trim() : "0");
        
        // 3. Comparación y Asersión.
        if (countCSV != countBD) {
            String mensajeError = String.format("FAIL en %s: El número de registros NO coincide. CSV: %d, BD: %d", 
                                                tipoArchivo, countCSV, countBD);
            System.err.println(mensajeError);
            // Utiliza Assert.fail para detener la prueba si usas TestNG
            Assert.fail(mensajeError); 
            return false;
        }
        
        System.out.println(String.format("PASS en %s: Conteo global coincide: %d registros.", 
                                         tipoArchivo, countCSV));
        return true;
    }
    
    /**
     * Lógica principal para calcular el número de semana para cualquier fecha.
     * (Semana 1 = Primer Viernes del año. Semana va de Viernes a Jueves).
     *
     * @param fecha La fecha a evaluar.
     * @return El número de semana (entero).
     * @author Alejandro Montalvo
	 * @date 29/Septiembre/2025             
     */
    public static int calcularNumeroSemana(LocalDate fecha) {
        
        // 1. Obtener el 1 de enero del año de la fecha dada
        LocalDate primeroDeEnero = LocalDate.of(fecha.getYear(), 1, 1);

        // 2. Encontrar el PRIMER VIERNES del año (Inicio de la Semana 1).
        // Si el 1 de enero ya es viernes, usa esa fecha (nextOrSame).
        LocalDate primerViernes = primeroDeEnero.with(
            TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)
        );

        // 3. Determinar la fecha de inicio (Viernes) de la semana a la que pertenece 'fecha'.
        // Esto encuentra el viernes anterior o el mismo día si 'fecha' es viernes.
        LocalDate inicioDeSemanaActual = fecha.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)
        );

        // 4. Calcular la diferencia en días entre el inicio de la Semana 1 y el inicio de la semana actual.
        long diasTranscurridos = ChronoUnit.DAYS.between(primerViernes, inicioDeSemanaActual);
        
        // 5. Calcular el número de semana: 
        // (díasTranscurridos / 7) para obtener semanas completas, y luego sumar 1 (para contar la Semana 1).
        int numeroSemana = (int) (diasTranscurridos / 7) + 1;

        return numeroSemana;
    }
    
    /**
     * Devuelve el número de la semana actual basado en la regla:
     * El conteo de semanas inicia con el primer viernes del año (Semana 1) y
     * la semana corre de viernes a jueves.
     * * @return El número de semana actual (entero).
     * @author Alejandro Montalvo
	 * @date 29/Septiembre/2025       
     */
    public static String obtenerSemanaActualEspecial() {
        // Llama a la función que calcula el entero
        int numeroSemana = calcularNumeroSemana(LocalDate.now());

        // Convierte el entero (int) a una cadena de texto (String)
        String semanaString = String.valueOf(numeroSemana);
        
        return semanaString;
    }   
    
    
    /**
     * Lee un archivo CSV, omite la fila de encabezados del archivo y mapea cada fila de datos 
     * a un Map<String, String>, utilizando los nombres de columna de la BD (encabezadosCSV) 
     * como claves.
     * * @param rutaArchivo La ruta completa del archivo CSV a leer.
     * @param encabezadosBD Los nombres de las columnas de la BD (en minúsculas/sin espacios) que 
     * serán utilizados como claves en el Map para la comparación.
     * @return Retorna una lista de Mapas, donde cada Map representa una fila de datos, 
     * o {@code null} si ocurre un error de lectura o si el archivo no existe.
     */
    public List<Map<String, String>> leerCSV(String rutaArchivo, String[] encabezadosBD) {
        List<Map<String, String>> datos = new ArrayList<>();
        
        // Verificación básica de existencia de archivo
        if (!new java.io.File(rutaArchivo).exists()) {
            System.err.println("ADVERTENCIA: Archivo CSV no encontrado en la ruta: " + rutaArchivo);
            return datos; // Retorna lista vacía si el archivo no existe
        }

        System.out.println("Iniciando lectura de archivo CSV: " + rutaArchivo);

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            
            // 1. Omitir la fila de encabezados del archivo (los títulos en español)
            // Ya que la matriz 'encabezadosBD' proporciona las claves correctas para el mapeo.
            reader.readNext(); 
            
            String[] nextLine;
            int rowCount = 0;

            // 2. Leer cada línea de datos
            while ((nextLine = reader.readNext()) != null) {
                rowCount++;
                // Validar que el número de columnas leídas coincida con los encabezados BD
                if (nextLine.length != encabezadosBD.length) {
                    System.err.println(String.format(
                        "ERROR en la Fila %d: El número de columnas no coincide. Esperado: %d, Encontrado: %d. Fila omitida.", 
                        rowCount, encabezadosBD.length, nextLine.length));
                    continue; // Saltar esta fila y continuar con la siguiente
                }
                
                Map<String, String> fila = new HashMap<>();
                
                // 3. Mapear los datos de la fila (nextLine) a las claves de la BD (encabezadosBD)
                for (int i = 0; i < encabezadosBD.length; i++) {
                    // Coloca el valor leído (nextLine[i]) en el Map, usando el nombre de la columna BD (encabezadosBD[i]) como clave.
                    // Usa .trim() para limpiar espacios alrededor del valor leído del CSV
                    fila.put(encabezadosBD[i].toLowerCase().trim(), nextLine[i].trim());
                }
                
                datos.add(fila);
            }
            
            System.out.println(String.format("Lectura CSV completada. %d filas de datos procesadas.", datos.size()));
            testCase.addTextEvidenceCurrentStep(String.format("Lectura CSV completada. %d filas de datos procesadas.", datos.size()));
            
        } catch (IOException e) {
            System.err.println("ERROR DE IO al leer el archivo CSV: " + e.getMessage());
            return null;
        } catch (CsvValidationException e) {
            System.err.println("ERROR DE FORMATO CSV: " + e.getMessage());
            return null;
        }
        
        return datos;
    }
    
    
	
}
    
