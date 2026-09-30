package WEB.LaborManagmentSystemMVP2.om;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LaborManagmentTabuladores {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;

	/**
	 * Localizadores
	 ******************************************************************************************/
	// Menu Tabuladores
	By tabuladoresMenu = By.xpath("//a[contains(@class, 'nav-link') and contains(text(), 'Tabuladores')]");
	By cargaTabuladores = By.xpath("//h3[text()='Tabulador de producción total']");
	By btnEditarTabuladores = By.xpath("//table[.//div[text()=' SURTIDO CAJAS ']]//button[contains(text(), 'Editar')]");
	By btnGuardarTabuladores = By
			.xpath("//table[.//div[text()=' SURTIDO CAJAS ']]//button[contains(text(), 'Guardar')]");
	By btnCancelarTabuladores = By
			.xpath("//table[.//div[text()=' SURTIDO CAJAS ']]//button[contains(text(), 'Cancelar')]");
	By loadingOverlay = By.cssSelector(".loading-overlay");

	// Selectores para el modal de confirmación (Ajustar si tus selectores son
	// diferentes)
	By labelRealizarCambios = By.xpath("//*[contains(text(), '¿Desea realizar los cambios?')]");
	By btnConfirmarSiCambios = By.xpath("//button[normalize-space(text())='SI']");
	By btnConfirmarNoCambios = By.xpath("//button[normalize-space(text())='NO']");

	By msgAlertaSoloNumerico = By.xpath(
			"//div[contains(@class, 'toast-body') and text()=' El campo Horas Productivas solo permite datos numéricos ']");
	By msgAlertaCamposVacios = By
			.xpath("//div[contains(@class, 'toast-body') and text()=' El campo Horas Productivas solo permite datos numéricos ']");
	
	By msgAlertaActualizado = By
			.xpath("//div[@role='alert']//div[@class='toast-body' and contains(text(), 'La información se actualizo de manera correcta')]");


	String horasProdXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' HORAS PROD ']]";
	String diasSemXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Días sem ']]";
	String paroOperativoXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Paro operativo ']]";
	String produccionXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Producción ']]";
	String horasTurnoXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Horas turno ']]";
	String horasComidaXpath = "//table[.//div[text()=' SURTIDO CAJAS ']]//tr[.//div[text()=' Horas comida ']]";

	/**
	 * Constructor
	 * 
	 ******************************************************************************************/
	public LaborManagmentTabuladores(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}

	/**
	     * Valida los valores mostrados en la cabecera de la tabla de Surtido Cajas (UI) contra
	     * los datos almacenados en la base de datos para la actividad 'SCAJAS'.
	     * El método compara seis campos clave (Horas Prod, Días Sem, Horas Turno, Hora Comida, Paro Operativo y Producción)
	     * para los tres turnos (T1, T2, T3) extraídos de la UI y la BD.
	     * Utiliza una tolerancia de 0.01 para la comparación de valores decimales (flotantes).
	     * @author Alejandro Montalvo
	     * @param URL La URL de conexión a la base de datos.
	     * @param USER El nombre de usuario para la conexión a la base de datos.
	     * @param PASSWORD La contraseña para la conexión a la base de datos.
	     * @return Retorna {@code true} si todos los datos de la UI coinciden con la BD dentro de la tolerancia de 0.01; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean validarTabuladoresPortalVsBD(String URL, String USER, String PASSWORD) {
	    String query = "SELECT horas_productivas, dias_semana, horas_turno, hora_comida, paro_operativo, produccion " +
	                   "FROM xxmai_inc.xxmai_cab_tab_prod WHERE clave_actividad = 'SCAJAS';";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        // Interacción con el portal
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
	        u.click(btnEditarTabuladores);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));

	        List<WebElement> hrProdUI = Arrays.asList(
	            u.getDriver().findElement(By.xpath(horasProdXpath + "/td[1]//input")),
	            u.getDriver().findElement(By.xpath(horasProdXpath + "/td[2]//input")),
	            u.getDriver().findElement(By.xpath(horasProdXpath + "/td[3]//input"))
	        );

	        List<WebElement> diasSemUI = Arrays.asList(
	            u.getDriver().findElement(By.xpath(diasSemXpath + "/td[1]//input")),
	            u.getDriver().findElement(By.xpath(diasSemXpath + "/td[2]//input")),
	            u.getDriver().findElement(By.xpath(diasSemXpath + "/td[3]//input"))
	        );

	        List<WebElement> hrTurnoUI = Arrays.asList(
	            u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[1]")),
	            u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[2]")),
	            u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[3]"))
	        );

	        List<WebElement> hrComidaUI = Arrays.asList(
	            u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[1]")),
	            u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[2]")),
	            u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[3]"))
	        );

	        List<WebElement> paroOperativoUI = Arrays.asList(
	            u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[1]//input")),
	            u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[2]//input")),
	            u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[3]//input"))
	        );

	        List<WebElement> produccionUI = Arrays.asList(
	            u.getDriver().findElement(By.xpath(produccionXpath + "/td[1]//input")),
	            u.getDriver().findElement(By.xpath(produccionXpath + "/td[2]//input")),
	            u.getDriver().findElement(By.xpath(produccionXpath + "/td[3]//input"))
	        );

	        int fila = 0;
	        while (rs.next() && fila < 3) {
	            String horasProdBD = rs.getString("horas_productivas").trim();
	            String diasSemBD = rs.getString("dias_semana").trim();
	            String horasTurnoBD = rs.getString("horas_turno").trim();
	            String horaComidaBD = rs.getString("hora_comida").trim();
	            String paroOperativoBD = rs.getString("paro_operativo").trim();
	            String produccionBD = rs.getString("produccion").trim();

	            String horasProdUIVal = hrProdUI.get(fila).getAttribute("value").trim();
	            String diasSemUIVal = diasSemUI.get(fila).getAttribute("value").trim();
	            String horasTurnoUIVal = hrTurnoUI.get(fila).getText().trim();
	            String horaComidaUIVal = hrComidaUI.get(fila).getText().trim();
	            String paroOperativoUIVal = paroOperativoUI.get(fila).getAttribute("value").trim();
	            String produccionUIVal = produccionUI.get(fila).getAttribute("value").trim();
	            
	            testCase.addTextEvidenceCurrentStep(String.format("Comparando fila %d:", fila + 1));
	            testCase.addTextEvidenceCurrentStep(String.format("BD → horas_productivas: %s | UI → %s", horasProdBD, horasProdUIVal));
	            testCase.addTextEvidenceCurrentStep(String.format("BD → dias_semana: %s | UI → %s", diasSemBD, diasSemUIVal));
	            testCase.addTextEvidenceCurrentStep(String.format("BD → horas_turno: %s | UI → %s", horasTurnoBD, horasTurnoUIVal));
	            testCase.addTextEvidenceCurrentStep(String.format("BD → hora_comida: %s | UI → %s", horaComidaBD, horaComidaUIVal));
	            testCase.addTextEvidenceCurrentStep(String.format("BD → paro_operativo: %s | UI → %s", paroOperativoBD, paroOperativoUIVal));
	            testCase.addTextEvidenceCurrentStep(String.format("BD → produccion: %s | UI → %s", produccionBD, produccionUIVal));

	            if (!horasProdBD.equals(horasProdUIVal) ||
	            	    !diasSemBD.equals(diasSemUIVal) ||
	            	    new BigDecimal(horasTurnoBD).compareTo(new BigDecimal(horasTurnoUIVal)) != 0 ||
	            	    new BigDecimal(horaComidaBD).compareTo(new BigDecimal(horaComidaUIVal)) != 0 ||
	            	    new BigDecimal(paroOperativoBD).compareTo(new BigDecimal(paroOperativoUIVal)) != 0 ||
	            	    new BigDecimal(produccionBD).compareTo(new BigDecimal(produccionUIVal)) != 0) {

	                System.err.println("Discrepancia en fila " + (fila + 1));
	                System.err.printf("BD: [%s, %s, %s, %s, %s, %s]%n", horasProdBD, diasSemBD, horasTurnoBD, horaComidaBD, paroOperativoBD, produccionBD);
	                System.err.printf("UI: [%s, %s, %s, %s, %s, %s]%n", horasProdUIVal, diasSemUIVal, horasTurnoUIVal, horaComidaUIVal, paroOperativoUIVal, produccionUIVal);
	                return false;
	            }

	            fila++;
	        }
	        u.click(btnCancelarTabuladores);
	        return fila == 3;
	        
	    } catch (SQLException e) {
	        System.err.println("Error al consultar tabuladores desde la BD: " + e.getMessage());
	        return false;
	    } catch (Exception e) {
	        System.err.println("Error al interactuar con el portal: " + e.getMessage());
	        return false;
	    }
	}

	
	/**
	     * Ejecuta una consulta SQL de tipo {@code SELECT} en la base de datos y retorna los resultados completos.
	     * Cada fila del {@code ResultSet} se mapea a un {@code Map<String, String>} donde la clave es el nombre de la columna (en minúsculas)
	     * y el valor es el contenido de la celda como String.
	     * El método asegura el cierre de los recursos de la conexión (Connection, Statement, ResultSet) en un bloque {@code finally}.
	     * @author Alejandro Montalvo
	     * @param URL La URL de conexión a la base de datos.
	     * @param USER El nombre de usuario para la conexión a la base de datos.
	     * @param PASS La contraseña para la conexión a la base de datos.
	     * @param query La consulta SQL a ejecutar.
	     * @return Retorna una {@code List} de {@code Map<String, String>} conteniendo todas las filas y columnas del resultado, o {@code null} si ocurre un error crítico.
	     * @date 29/Septiembre/2025
	     */
	public List<Map<String, String>> ejecutarConsultaBD(String URL, String USER, String PASS, String query) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		// Lista para almacenar los resultados: una entrada por fila (turno)
		List<Map<String, String>> resultados = new ArrayList<>();

		System.out.println("Ejecutando Query: " + query);

		try {
			// ... (1 y 2: Registro del Driver y Conexión) ...
			conn = DriverManager.getConnection(URL, USER, PASS);

			// ... (3: Crear el statement) ...
			stmt = conn.createStatement();

			// ... (4: Ejecutar la consulta) ...
			rs = stmt.executeQuery(query);

			// Obtener metadatos para saber los nombres de las columnas
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// 5. Procesar el resultado (todas las filas)
			while (rs.next()) {
				Map<String, String> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsmd.getColumnName(i).toLowerCase(); // Usar minúsculas para consistencia
					row.put(columnName, rs.getString(i));
				}
				resultados.add(row);
			}

			if (resultados.isEmpty()) {
				System.out.println("ADVERTENCIA: La consulta no retornó resultados.");
			}

		} catch (Exception e) {
			System.out.println("ERROR CRITICO en la conexión/ejecución de la BD: " + e.getMessage());
			e.printStackTrace();
			return null; // Retorna null en caso de fallo crítico
		} finally {
			// ... (6: Cerrar recursos) ...
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// Ignorar errores al cerrar recursos
			}
		}

		System.out.println("Filas retornadas de BD: " + resultados.size());
		return resultados;
	}
	
	public boolean actualizacionCabeceraSurtidoCajasDefault(String defaultHorasProd, String defaultDiasSem,
			String defaultParoOperativo, String defaultProduccion) {
		boolean resultado = false;

		Map<String, Object> elementos;

		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(300));
			clicTabuladores();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
			wait.until(ExpectedConditions.elementToBeClickable(btnEditarTabuladores));
			u.click(btnEditarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));
			testCase.addScreenShotCurrentStep(u, "Se dio clic en editar");

			elementos = inicializarElementosEditables();

			@SuppressWarnings("unchecked")
			List<WebElement> horasProdInputs = (List<WebElement>) elementos.get("horasProdInputs");
			@SuppressWarnings("unchecked")
			List<WebElement> diasSemInputs = (List<WebElement>) elementos.get("diasSemInputs");
			@SuppressWarnings("unchecked")
			List<WebElement> paroOperativoInputs = (List<WebElement>) elementos.get("paroOperativoInputs");
			@SuppressWarnings("unchecked")
			List<WebElement> produccionInputs = (List<WebElement>) elementos.get("produccionInputs");

			// Re-asignamos las listas para el llenado final
			horasProdInputs = (List<WebElement>) elementos.get("horasProdInputs");
			diasSemInputs = (List<WebElement>) elementos.get("diasSemInputs");
			paroOperativoInputs = (List<WebElement>) elementos.get("paroOperativoInputs");
			produccionInputs = (List<WebElement>) elementos.get("produccionInputs");

			// Se llenan los datos de los input editables
			modificarFilaInputs(horasProdInputs, defaultHorasProd);
			modificarFilaInputs(diasSemInputs, defaultDiasSem);
			modificarFilaInputs(paroOperativoInputs, defaultParoOperativo);
			modificarFilaInputs(produccionInputs, defaultProduccion);
			u.click(btnGuardarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnConfirmarSiCambios));
			u.click(btnConfirmarSiCambios);
			wait.until(ExpectedConditions.visibilityOfElementLocated(msgAlertaActualizado));
			testCase.addScreenShotCurrentStep(u, "Se modificaron los valores en cada campo editable");

			resultado = true;
		} catch (Exception e) {
			System.out.println("Error en la validación de cabecera de surtido cajas (Flujo de UI): " + e.getMessage());
			resultado = false;
		}
		return resultado;
	}

	
	/**
	     * Ejecuta un flujo completo de prueba para la actualización de la cabecera de la tabla "Surtido Cajas" (Tabuladores).
	     * Este método abarca múltiples validaciones de interfaz de usuario y funcionalidad de guardado, incluyendo:
	     * 1. Validación de campos de solo lectura (Horas Turno, Horas Comida) y cálculo dinámico de Horas Turno.
	     * 2. Prueba de "Cancelar" para verificar que los datos no se persisten.
	     * 3. Pruebas de validación de campos: intentar guardar con texto no numérico y campos vacíos.
	     * 4. Guardado final de nuevos valores y manejo de la alerta de confirmación.
	     * @author Alejandro Montalvo
	     * @param nuevoHorasProd El valor numérico a ingresar en los campos de "Horas de Producción".
	     * @param nuevoDiasSem El valor numérico a ingresar en los campos de "Días de la Semana".
	     * @param nuevoParoOperativo El valor numérico a ingresar en los campos de "Paro Operativo".
	     * @param nuevoProduccion El valor numérico a ingresar en los campos de "Producción".
	     * @return Retorna {@code true} si todas las interacciones y validaciones de la interfaz de usuario se completan sin errores; de lo contrario, retorna {@code false}.
	     * @date 29/Septiembre/2025
	     */
	public boolean validarActualizacionCabeceraSurtidoCajas2(String nuevoHorasProd, String nuevoDiasSem,
			String nuevoParoOperativo, String nuevoProduccion) {
		boolean resultado = false;

		Map<String, Object> elementos;

		try {
			
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(300));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
			//Por alguna razon se cambia al primer menu y toca volver a seleccionar tabuladores
			clicTabuladores();			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
			wait.until(ExpectedConditions.elementToBeClickable(btnEditarTabuladores));
			u.click(btnEditarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));
			testCase.addScreenShotCurrentStep(u, "Se dio clic en editar");

			elementos = inicializarElementosEditables();

			// Extracción de elementos necesarios para las validaciones
			WebElement inpHrProd = (WebElement) elementos.get("inpHrProd");
			WebElement inpHrProd2 = (WebElement) elementos.get("inpHrProd2");
			WebElement inpHrProd3 = (WebElement) elementos.get("inpHrProd3");
			WebElement inpDiasSem = (WebElement) elementos.get("inpDiasSem");
			WebElement inpDiasSem2 = (WebElement) elementos.get("inpDiasSem2");
			WebElement inpDiasSem3 = (WebElement) elementos.get("inpDiasSem3");

			WebElement lblHrTurno = (WebElement) elementos.get("lblHrTurno");
			WebElement lblHrTurno2 = (WebElement) elementos.get("lblHrTurno2");
			WebElement lblHrTurno3 = (WebElement) elementos.get("lblHrTurno3");
			WebElement lblHrComida = (WebElement) elementos.get("lblHrComida");
			WebElement lblHrComida2 = (WebElement) elementos.get("lblHrComida2");
			WebElement lblHrComida3 = (WebElement) elementos.get("lblHrComida3");

			@SuppressWarnings("unchecked")
			List<WebElement> horasProdInputs = (List<WebElement>) elementos.get("horasProdInputs");
			@SuppressWarnings("unchecked")
			List<WebElement> diasSemInputs = (List<WebElement>) elementos.get("diasSemInputs");
			@SuppressWarnings("unchecked")
			List<WebElement> paroOperativoInputs = (List<WebElement>) elementos.get("paroOperativoInputs");
			@SuppressWarnings("unchecked")
			List<WebElement> produccionInputs = (List<WebElement>) elementos.get("produccionInputs");

			validarLabelNoEditables(lblHrTurno, lblHrTurno2, lblHrTurno3);
			validarLabelNoEditables(lblHrComida, lblHrComida2, lblHrComida3);
			validarCalculoHorasTurno(inpHrProd, inpHrProd2, inpHrProd3, inpDiasSem, inpDiasSem2, inpDiasSem3,
					lblHrTurno, lblHrTurno2, lblHrTurno3);

			u.highLight(btnCancelarTabuladores);
			testCase.addScreenShotCurrentStep(u, "Se da clic en cancelar y se valida que no se guarden los datos");
			u.click(btnCancelarTabuladores);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));

			// Entrar en modo Edición (Segunda Vez)
			u.click(btnEditarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));
			testCase.addScreenShotCurrentStep(u, "Se muestran los mismos datos");

			elementos = inicializarElementosEditables();

			// Re-asignamos las listas para la prueba de datos numéricos/vacíos
			horasProdInputs = (List<WebElement>) elementos.get("horasProdInputs");
			diasSemInputs = (List<WebElement>) elementos.get("diasSemInputs");
			paroOperativoInputs = (List<WebElement>) elementos.get("paroOperativoInputs");
			produccionInputs = (List<WebElement>) elementos.get("produccionInputs");

			// Se realiza la prueba de ingresar datos numéricos y guardar
			String textoPrueba = "ABC";
			modificarFilaInputs(horasProdInputs, textoPrueba);
			modificarFilaInputs(diasSemInputs, textoPrueba);
			modificarFilaInputs(paroOperativoInputs, textoPrueba);
			modificarFilaInputs(produccionInputs, textoPrueba);
			u.click(btnGuardarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(msgAlertaSoloNumerico));
			System.out.println("NOTA : Aqui no imprime de manera general la alerta numerica sino toma el primer input de todos");

			elementos = inicializarElementosEditables();

			// Re-asignamos inputs individuales para limpiarTodosLosInputs
			inpHrProd = (WebElement) elementos.get("inpHrProd");
			// ... (Se re-asignan los otros 11 inputs individuales aquí si es necesario) ...

			limpiarTodosLosInputs((WebElement) elementos.get("inpHrProd"), (WebElement) elementos.get("inpHrProd2"),
					(WebElement) elementos.get("inpHrProd3"), (WebElement) elementos.get("inpDiasSem"),
					(WebElement) elementos.get("inpDiasSem2"), (WebElement) elementos.get("inpDiasSem3"),
					(WebElement) elementos.get("inpParoOperativo"), (WebElement) elementos.get("inpParoOperativo2"),
					(WebElement) elementos.get("inpParoOperativo3"), (WebElement) elementos.get("inpProduccion"),
					(WebElement) elementos.get("inpProduccion2"), (WebElement) elementos.get("inpProduccion3"));

			u.click(btnGuardarTabuladores);			
			wait.until(ExpectedConditions.visibilityOfElementLocated(msgAlertaCamposVacios));
			u.click(btnCancelarTabuladores);
			
			u.click(btnEditarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));
			elementos = inicializarElementosEditables(); // Mantener esta línea si tu método auxiliar hace otras cosas.
			// Reemplazar la llamada:
			validarCancelarCambios(nuevoHorasProd, nuevoDiasSem, nuevoParoOperativo, nuevoProduccion); 

			// Entrar en modo Edición (Tercera Vez) para el guardado final
			u.click(btnEditarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));

			elementos = inicializarElementosEditables();

			// Re-asignamos las listas para el llenado final
			horasProdInputs = (List<WebElement>) elementos.get("horasProdInputs");
			diasSemInputs = (List<WebElement>) elementos.get("diasSemInputs");
			paroOperativoInputs = (List<WebElement>) elementos.get("paroOperativoInputs");
			produccionInputs = (List<WebElement>) elementos.get("produccionInputs");

			// Se llenan los datos de los input editables
			modificarFilaInputs(horasProdInputs, nuevoHorasProd);
			modificarFilaInputs(diasSemInputs, nuevoDiasSem);
			modificarFilaInputs(paroOperativoInputs, nuevoParoOperativo);
			modificarFilaInputs(produccionInputs, nuevoProduccion);
			u.click(btnGuardarTabuladores);
			//Se comenta temporalmente debido a que por alguna razon no lee el elemento
			//wait.until(ExpectedConditions.visibilityOfElementLocated(labelRealizarCambios));
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnConfirmarNoCambios));
			u.click(btnConfirmarNoCambios);
			u.click(btnGuardarTabuladores);
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnConfirmarSiCambios));
			u.click(btnConfirmarSiCambios);
			wait.until(ExpectedConditions.visibilityOfElementLocated(msgAlertaActualizado));
			testCase.addScreenShotCurrentStep(u, "Se modificaron los valores en cada campo editable");

			resultado = true;
		} catch (Exception e) {
			System.out.println("Error en la validación de cabecera de surtido cajas (Flujo de UI): " + e.getMessage());
			resultado = false;
		}
		return resultado;
	}

	/**
	     * Inicializa y organiza todos los {@code WebElements} (inputs y etiquetas de solo lectura) 
	     * de la cabecera de la tabla de Surtido Cajas (Tabuladores).
	     * Los elementos se agrupan individualmente y en listas lógicas (por fila: Horas Prod, Días Sem, etc.) 
	     * para facilitar su acceso y reutilización en otros métodos de prueba, como validaciones y modificaciones masivas.
	     * @author Alejandro Montalvo
	     * @return Retorna un {@code Map<String, Object>} que contiene tanto los {@code WebElements} individuales 
	     * como las {@code List<WebElement>} agrupadas, indexados por claves descriptivas.
	     * @date 29/Septiembre/2025
	     */
	private Map<String, Object> inicializarElementosEditables() {
		Map<String, Object> elementos = new HashMap<>();

		// Campos Editables (Inputs)
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

		// Labels (Valores No Editables)
		WebElement lblHrTurno = u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[1]"));
		WebElement lblHrTurno2 = u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[2]"));
		WebElement lblHrTurno3 = u.getDriver().findElement(By.xpath(horasTurnoXpath + "/td[3]"));
		WebElement lblHrComida = u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[1]"));
		WebElement lblHrComida2 = u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[2]"));
		WebElement lblHrComida3 = u.getDriver().findElement(By.xpath(horasComidaXpath + "/td[3]"));

		// Listas
		final List<WebElement> horasProdInputs = Arrays.asList(inpHrProd, inpHrProd2, inpHrProd3);
		final List<WebElement> diasSemInputs = Arrays.asList(inpDiasSem, inpDiasSem2, inpDiasSem3);
		final List<WebElement> paroOperativoInputs = Arrays.asList(inpParoOperativo, inpParoOperativo2,
				inpParoOperativo3);
		final List<WebElement> produccionInputs = Arrays.asList(inpProduccion, inpProduccion2, inpProduccion3);
		List<List<WebElement>> todasLasListas = Arrays.asList(horasProdInputs, diasSemInputs, paroOperativoInputs,
				produccionInputs);

		// Agregamos todo al Map
		elementos.put("inpHrProd", inpHrProd);
		elementos.put("inpHrProd2", inpHrProd2);
		elementos.put("inpHrProd3", inpHrProd3);
		elementos.put("inpDiasSem", inpDiasSem);
		elementos.put("inpDiasSem2", inpDiasSem2);
		elementos.put("inpDiasSem3", inpDiasSem3);
		elementos.put("inpParoOperativo", inpParoOperativo);
		elementos.put("inpParoOperativo2", inpParoOperativo2);
		elementos.put("inpParoOperativo3", inpParoOperativo3);
		elementos.put("inpProduccion", inpProduccion);
		elementos.put("inpProduccion2", inpProduccion2);
		elementos.put("inpProduccion3", inpProduccion3);
		elementos.put("lblHrTurno", lblHrTurno);
		elementos.put("lblHrTurno2", lblHrTurno2);
		elementos.put("lblHrTurno3", lblHrTurno3);
		elementos.put("lblHrComida", lblHrComida);
		elementos.put("lblHrComida2", lblHrComida2);
		elementos.put("lblHrComida3", lblHrComida3);

		elementos.put("horasProdInputs", horasProdInputs);
		elementos.put("diasSemInputs", diasSemInputs);
		elementos.put("paroOperativoInputs", paroOperativoInputs);
		elementos.put("produccionInputs", produccionInputs);
		elementos.put("todasLasListas", todasLasListas);

		return elementos;
	}

	/**
	     * Valida el cálculo de "Horas Turno" en la interfaz de usuario, donde: 
	     * {@code Horas Turno = Horas Producción / Días Semanales}.
	     * El método itera sobre los tres turnos (T1, T2, T3), extrae los valores de los inputs y las etiquetas, 
	     * realiza la división y compara el resultado con el valor mostrado en pantalla, permitiendo una tolerancia de 0.01 
	     * para manejar la precisión del punto flotante.
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
	public void validarCalculoHorasTurno(WebElement inpHrProd, WebElement inpHrProd2, WebElement inpHrProd3,
			WebElement inpDiasSem, WebElement inpDiasSem2, WebElement inpDiasSem3, WebElement lblHrTurno,
			WebElement lblHrTurno2, WebElement lblHrTurno3) {

		// Arrays para iterar sobre las tres columnas (T1, T2, T3)
		WebElement[] horasProdElements = { inpHrProd, inpHrProd2, inpHrProd3 };
		WebElement[] diasSemElements = { inpDiasSem, inpDiasSem2, inpDiasSem3 };
		WebElement[] horasTurnoElements = { lblHrTurno, lblHrTurno2, lblHrTurno3 };
		String[] columnas = { "T1", "T2", "T3" };

		// Formato para redondear el resultado y la comparación, ya que se trata de
		// valores double
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
					System.err.println(
							"ERROR DE DATOS en Columna " + col + ": Días Semanales es cero. No se puede calcular.");
					Assert.fail("Validación fallida en " + col + ": Días Semanales es 0.");
					continue; // Pasa a la siguiente iteración
				}

				// 3. Realizar el cálculo
				double hrTurnoCalculado = hrProd / diasSem;

				// Redondear ambos valores a 2 decimales para la comparación (por precisión de
				// punto flotante)
				double calculadoRedondeado = Double.parseDouble(df.format(hrTurnoCalculado));
				double esperadoRedondeado = Double.parseDouble(df.format(hrTurnoEsperado));

				// 4. Validación y aserción
				String mensaje = String.format("%s: Calculado (%.2f / %.2f = %.2f) | Esperado: %.2f", col, hrProd,
						diasSem, calculadoRedondeado, esperadoRedondeado);

				Assert.assertEquals(calculadoRedondeado, esperadoRedondeado, 0.01, // 0.01 es la tolerancia (delta)
						"FAIL en Columna " + col + ": El cálculo no coincide. " + mensaje);

				System.out.println("PASS en Columna " + col + ": Cálculo verificado. " + mensaje);

			} catch (NumberFormatException e) {
				// Captura si alguno de los campos de input o label está vacío o contiene texto
				// no numérico
				String errorMsg = "FAIL: Error de formato en la Columna " + col
						+ ". Asegúrate de que los campos tienen valores numéricos. Detalle: " + e.getMessage();
				System.err.println(errorMsg);
				Assert.fail(errorMsg);
			}
		}
	}

	/**
	     * Valida que un conjunto de elementos (típicamente etiquetas de solo lectura o celdas de tabla) 
	     * estén visibles pero **no sean editables** por el usuario.
	     * La validación de no-editabilidad se realiza intentando invocar {@code sendKeys()} en el elemento 
	     * y esperando que se lance una excepción ({@code InvalidElementStateException} o similar), lo que 
	     * confirma que no acepta entrada de texto. Adicionalmente, verifica que el {@code tagName} sea 'td'.
	     * @author Alejandro Montalvo
	     * @param label1 El primer {@code WebElement} a verificar (Ej. Turno 1).
	     * @param label2 El segundo {@code WebElement} a verificar (Ej. Turno 2).
	     * @param label3 El tercer {@code WebElement} a verificar (Ej. Turno 3).
	     * @date 29/Septiembre/2025
	     */
	public void validarLabelNoEditables(WebElement label1, WebElement label2, WebElement label3) {

		// Lista de elementos a verificar
		WebElement[] elementosTurno = { label1, label2, label3 };
		String[] nombresElementos = { "T1", "T2", "T3" };

		System.out.println("Iniciando validación de campos...");

		for (int i = 0; i < elementosTurno.length; i++) {
			WebElement elemento = elementosTurno[i];
			String nombre = nombresElementos[i];

			// 1. Verificar que el elemento esté visible (siempre debería ser true)
			Assert.assertTrue(elemento.isDisplayed(), "FAIL: El campo de la fila" + nombre + "' NO está visible.");

			// 2. Verificar que el elemento NO esté habilitado para edición.
			// Para un elemento <td> (celda de tabla), isEnabled() generalmente es false o
			// levanta un error
			// si no es un elemento de formulario (como un input), pero es una buena
			// práctica.
			// Más importante aún: ¡Intentamos escribir!

			// 3. Verificación clave: Intentar enviar teclas. Si no es un input, fallará o
			// no surtirá efecto.
			try {
				// Intentar interactuar con sendKeys.
				// Esto es una prueba de fuego: si el elemento acepta entrada, es editable.
				elemento.sendKeys("TEST");

				// Si llegamos aquí, significa que sendKeys funcionó (es editable, lo cual es un
				// fallo para esta prueba)
				String mensajeFallo = "FAIL: El campo de la fila " + nombre + " PERMITE edición (aceptó sendKeys).";
				System.err.println(mensajeFallo);
				Assert.fail(mensajeFallo);

			} catch (Exception e) {
				// Si lanza una excepción (como InvalidElementStateException o una
				// NoSuchElementException
				// si el selector hubiera fallado), es probable que NO sea editable.

				// La mejor confirmación es si NO tiene la etiqueta <input>
				String tagName = elemento.getTagName();
				Assert.assertEquals(tagName, "td",
						"FAIL: El campo no es una celda <td> (es " + tagName + ") y podría ser editable.");

				System.out.println(
						"PASS: Fila" + nombre + " verificado. Es una etiqueta de solo lectura (<" + tagName + ">).");
			}
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
	public boolean clicTabuladores() {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.presenceOfElementLocated(tabuladoresMenu));
			u.highLight(tabuladoresMenu);
			u.click(tabuladoresMenu);
			wait.until(ExpectedConditions.presenceOfElementLocated(cargaTabuladores));
			testCase.addScreenShotCurrentStep(u, "Carga de menu Tabuladores exitosa");

			if (u.isDisplayed(cargaTabuladores)) {
				validacion = true;
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("No se pudo encontrar el menu tabuladores");
		}

		return validacion;
	}
	
	/**
	     * Valida la funcionalidad del botón "Cancelar" en el modo de edición de la cabecera de tabuladores.
	     * El método simula la edición de múltiples campos y luego cancela los cambios para verificar que los valores originales
	     * (o el estado pre-edición) se restauran correctamente en la UI y que los valores nuevos NO se guardaron.
	     *
	     * El flujo incluye:
	     * 1. Localizar y asignar nuevos valores a todos los inputs (Versión 1).
	     * 2. Hacer clic en "Cancelar" para salir del modo edición.
	     * 3. Re-entrar en modo edición y **re-localizar todos los WebElements** (Versión 2) para evitar errores de obsolescencia.
	     * 4. Aserción: Verificar que el valor actual en la UI (V2) NO es igual al valor que se intentó cancelar.
	     *
	     * @author Alejandro Montalvo
	     * @param nuevoHorasProd El valor que se intenta introducir en los campos de Horas Producción.
	     * @param nuevoDiasSem El valor que se intenta introducir en los campos de Días Semanales.
	     * @param nuevoParoOperativo El valor que se intenta introducir en los campos de Paro Operativo.
	     * @param nuevoProduccion El valor que se intenta introducir en los campos de Producción.
	     * @date 29/Septiembre/2025
	     */
	public void validarCancelarCambios(String nuevoHorasProd,
			String nuevoDiasSem, String nuevoParoOperativo, String nuevoProduccion) {
    
    // NOTA: La lista 'todasLasListas' se genera internamente ya que los WebElements deben ser frescos.
    
    // Declaración de variables de control
    WebElement inpHrProd_v1, inpHrProd2_v1, inpHrProd3_v1;
    WebElement inpDiasSem_v1, inpDiasSem2_v1, inpDiasSem3_v1;
    WebElement inpParoOperativo_v1, inpParoOperativo2_v1, inpParoOperativo3_v1;
    WebElement inpProduccion_v1, inpProduccion2_v1, inpProduccion3_v1;
    
    WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));

    System.out.println("--- 1. Entrando en modo edición y asignando valores ---");
    // Asumimos que la edición ya está activa al llamar a este método
    wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));

    // Bloque de Localización de Elementos (VERSION 1: Para asignar valores a CANCELAR)
    inpHrProd_v1 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[1]//input"));
    inpHrProd2_v1 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[2]//input"));
    inpHrProd3_v1 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[3]//input"));
    inpDiasSem_v1 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[1]//input"));
    inpDiasSem2_v1 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[2]//input"));
    inpDiasSem3_v1 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[3]//input"));
    inpParoOperativo_v1 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[1]//input"));
    inpParoOperativo2_v1 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[2]//input"));
    inpParoOperativo3_v1 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[3]//input"));
    inpProduccion_v1 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[1]//input"));
    inpProduccion2_v1 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[2]//input"));
    inpProduccion3_v1 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[3]//input"));
    
    // Reconstruimos las listas locales necesarias para la asignación
    final List<WebElement> horasProdInputs_v1 = Arrays.asList(inpHrProd_v1, inpHrProd2_v1, inpHrProd3_v1);
    final List<WebElement> diasSemInputs_v1 = Arrays.asList(inpDiasSem_v1, inpDiasSem2_v1, inpDiasSem3_v1);
    final List<WebElement> paroOperativoInputs_v1 = Arrays.asList(inpParoOperativo_v1, inpParoOperativo2_v1,
            inpParoOperativo3_v1);
    final List<WebElement> produccionInputs_v1 = Arrays.asList(inpProduccion_v1, inpProduccion2_v1, inpProduccion3_v1);

    // Llenar los inputs con los valores que NO deben guardarse
    modificarFilaInputs(horasProdInputs_v1, nuevoHorasProd);
    modificarFilaInputs(diasSemInputs_v1, nuevoDiasSem);
    modificarFilaInputs(paroOperativoInputs_v1, nuevoParoOperativo);
    modificarFilaInputs(produccionInputs_v1, nuevoProduccion);

    System.out.println("Valores asignados. Procediendo a Cancelar.");
    u.click(btnCancelarTabuladores); // <-- Esta acción invalida los elementos _v1

    // Espera a que el botón GUARDAR desaparezca, confirmando que salimos del modo edición
    wait.until(ExpectedConditions.invisibilityOfElementLocated(btnGuardarTabuladores));

    System.out.println("--- 3. Re-entrando en modo edición para validar ---");
    // Vuelve a entrar para poder acceder a los inputs y leer su atributo 'value'
    u.click(btnEditarTabuladores);
    wait.until(ExpectedConditions.visibilityOfElementLocated(btnGuardarTabuladores));

    // ====================================================================
    // SOLUCIÓN STALE ELEMENT: RE-LOCALIZAR ELEMENTOS PARA LA VALIDACIÓN (VERSIÓN 2)
    // ====================================================================
    // Re-inicializamos todos los elementos con nuevas referencias
    WebElement inpHrProd_v2 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[1]//input"));
    WebElement inpHrProd2_v2 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[2]//input"));
    WebElement inpHrProd3_v2 = u.getDriver().findElement(By.xpath(horasProdXpath + "/td[3]//input"));
    WebElement inpDiasSem_v2 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[1]//input"));
    WebElement inpDiasSem2_v2 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[2]//input"));
    WebElement inpDiasSem3_v2 = u.getDriver().findElement(By.xpath(diasSemXpath + "/td[3]//input"));
    WebElement inpParoOperativo_v2 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[1]//input"));
    WebElement inpParoOperativo2_v2 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[2]//input"));
    WebElement inpParoOperativo3_v2 = u.getDriver().findElement(By.xpath(paroOperativoXpath + "/td[3]//input"));
    WebElement inpProduccion_v2 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[1]//input"));
    WebElement inpProduccion2_v2 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[2]//input"));
    WebElement inpProduccion3_v2 = u.getDriver().findElement(By.xpath(produccionXpath + "/td[3]//input"));

    final List<WebElement> horasProdInputs_v2 = Arrays.asList(inpHrProd_v2, inpHrProd2_v2, inpHrProd3_v2);
    final List<WebElement> diasSemInputs_v2 = Arrays.asList(inpDiasSem_v2, inpDiasSem2_v2, inpDiasSem3_v2);
    final List<WebElement> paroOperativoInputs_v2 = Arrays.asList(inpParoOperativo_v2, inpParoOperativo2_v2,
            inpParoOperativo3_v2);
    final List<WebElement> produccionInputs_v2 = Arrays.asList(inpProduccion_v2, inpProduccion2_v2, inpProduccion3_v2);
    
    // Se crea la lista de listas con los elementos FRESCOS para la iteración.
    List<List<WebElement>> todasLasListas_v2 = Arrays.asList(horasProdInputs_v2, diasSemInputs_v2, paroOperativoInputs_v2, produccionInputs_v2);
    // ====================================================================

    String[] valoresNoDeseados = { nuevoHorasProd, nuevoDiasSem, nuevoParoOperativo, nuevoProduccion };
    String[] nombresCampos = { "Horas Prod", "Días Sem", "Paro Operativo", "Producción" };

    System.out.println("--- 4. Validando que los cambios fueron revertidos ---");

    // Usar la lista con los elementos FRESCOS (todasLasListas_v2)
    for (int i = 0; i < todasLasListas_v2.size(); i++) {
        List<WebElement> filaInputs = todasLasListas_v2.get(i);
        String valorNoDeseado = valoresNoDeseados[i];
        String nombreCampo = nombresCampos[i];

        for (int j = 0; j < filaInputs.size(); j++) {
            WebElement input = filaInputs.get(j);
            String valorActual = input.getAttribute("value");
            String columna = "T" + (j + 1);

            // ASERSCIÓN CLAVE: El valor actual NO DEBE ser el valor que intentamos guardar.
            Assert.assertNotEquals(valorActual.trim(), valorNoDeseado, String.format(
                    "FAIL: El botón Cancelar falló en %s (%s). El valor '%s' SE MANTUVO después de la cancelación.",
                    nombreCampo, columna, valorNoDeseado));

            System.out.println(String.format("PASS: %s (%s) valor revertido. Valor actual: '%s'", nombreCampo,
                    columna, valorActual));
        }
    }


		System.out.println("\nValidación de Cancelar cambios completada con éxito. Ningún cambio fue guardado.");

		// Clic en Cancelar para salir del modo edición después de la validación
		u.click(btnCancelarTabuladores);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnGuardarTabuladores));
	}

	/**
	     * Modifica el valor de todos los {@code WebElements} de entrada (inputs) contenidos en una lista.
	     * Este método es útil para aplicar el mismo valor a una fila completa de campos (por ejemplo, T1, T2, T3)
	     * de un mismo concepto (ej. Horas Producción).
	     * Utiliza el método auxiliar {@link #modificarInput(WebElement, String)} para realizar la acción en cada elemento.
	     *
	     * @author Alejandro Montalvo
	     * @param inputList Una lista de {@code WebElements} que representan los campos de entrada a modificar.
	     * @param value El valor de texto que se va a ingresar en cada uno de los inputs.
	     * @date 29/Septiembre/2025
	     */
	public void modificarFilaInputs(List<WebElement> inputList, String value) {
		for (WebElement element : inputList) {
			// Reutiliza tu método existente para la lógica de clear() y sendKeys()
			modificarInput(element, value);
		}
	}

	/**
	     * Limpia el contenido actual de un campo de entrada (input) y luego ingresa un nuevo valor.
	     * Este es el método atómico utilizado para establecer un valor en un {@code WebElement} de tipo input.
	     *
	     * @author Alejandro Montalvo
	     * @param element El {@code WebElement} (input) a modificar.
	     * @param value El valor de texto que se va a ingresar.
	     * @date 29/Septiembre/2025
	     */
	public void modificarInput(WebElement element, String value) {
		element.clear(); // Limpiar por si tiene valor
		element.sendKeys(value);
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
	public void limpiarTodosLosInputs(WebElement inpHrProd, WebElement inpHrProd2, WebElement inpHrProd3,
			WebElement inpDiasSem, WebElement inpDiasSem2, WebElement inpDiasSem3, WebElement inpParoOperativo,
			WebElement inpParoOperativo2, WebElement inpParoOperativo3, WebElement inpProduccion,
			WebElement inpProduccion2, WebElement inpProduccion3) {
		// 1. Agrupar todos los inputs en una lista para facilitar la iteración
		List<WebElement> todosLosInputs = Arrays.asList(inpHrProd, inpHrProd2, inpHrProd3, inpDiasSem, inpDiasSem2,
				inpDiasSem3, inpParoOperativo, inpParoOperativo2, inpParoOperativo3, inpProduccion, inpProduccion2,
				inpProduccion3);

		System.out.println("Iniciando limpieza de " + todosLosInputs.size() + " campos...");

		// 2. Iterar sobre la lista y aplicar .clear()
		for (WebElement input : todosLosInputs) {
			try {
				// El método .clear() es la forma más limpia de borrar el contenido de un campo
				// de texto.
				input.clear();
			} catch (Exception e) {
				// Manejo de errores por si el elemento no está visible, habilitado o si es de
				// solo lectura.
				System.err.println(
						"Advertencia: No se pudo limpiar el input. Posiblemente no está habilitado o visible.");
				// Puedes optar por usar sendKeys con las teclas CTRL+A y DELETE si .clear()
				// falla
				// para inputs muy particulares, pero .clear() es preferido:
				// input.sendKeys(Keys.CONTROL + "a");
				// input.sendKeys(Keys.DELETE);
			}
		}

		System.out.println("Limpieza de campos completada.");
	}
}