package WEB.LaborManagmentSystemMVP2.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.eclipse.persistence.queries.SQLResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LaborManagmentPlantilla {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;

	/**
	 * Localizadores
	 ******************************************************************************************/
	By userLabel = By.xpath("//*[@id=\"email\"]");
	By passwordLabel = By.xpath("//*[@id=\"password\"]");
	By loginButton = By.xpath("//*[@id=\"login\"]");
	By loginSuccess = By.xpath("//a[contains(@class, 'card_portal') and contains(@href, 'portalIncentivos')]");
	By portalincentivos = By.xpath("//a[normalize-space()='Portal de incentivos']");
	By pantallacargarArchivo = By.xpath("//*[contains(normalize-space(text()), 'Carga de archivos')]");
	By menuplantilla = By.xpath("//a[contains(normalize-space(.), 'Plantilla')]");
	By pantallaplantilla = By.xpath("//h3[normalize-space()='Plantilla']");
	By botoneseditarelimar = By.xpath("//tbody/tr[2]/td[1]");
	By botonnuevousuario = By.xpath("//button[normalize-space()='Nuevo usuario']");
	By botonguardarnuevousuario = By.xpath("//button[normalize-space()='Guardar']");
	By mensajeemergente = By.xpath("//div[@class='toast-body']");
	By cerrarmesajeemergente = By.xpath("//button[@aria-label='Close']");
	By botonsieliminar = By.xpath("//button[normalize-space()='SI']");
	By botonnoeliminar = By.xpath("//button[normalize-space()='NO']");
	By campocedis = By.xpath("//select[@aria-label='Default select example']");
	By camponombre = By.xpath("//input[@placeholder='Nombre']");
	By campousuario = By.xpath("//input[@placeholder='Usuario']");
	By campoantiguedad = By.xpath("//input[@type='date']");
	By campoSemana = By.xpath("//input[@placeholder='Semana']");
	By seleccionarturno = By.xpath("//select[@formcontrolname='shift']");
	By seleccionartipocolaborador = By.xpath("//select[@formcontrolname='collaborator']");
	By seleccionartipoincentivo = By.xpath("//select[@formcontrolname='incentive']");
	By seleccionarposicion = By.xpath("//select[@formcontrolname='rol']");
	By campoposicionclave = By.xpath("(//input[@type='text'])[last()]");
	By loadingOverlay = By.cssSelector(".loading-overlay");

	/**
	 * Constructor
	 * 
	 ******************************************************************************************/
	public LaborManagmentPlantilla(SeleniumUtil u, TestCase testCase) {
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
	 * Método que permite ingresar a la pantalla Plantilla dentro del portal.
	 * 
	 * Realiza la espera del elemento de menú "Plantilla", da clic sobre él, y
	 * valida que la pantalla de Plantilla se muestre correctamente.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si la pantalla Plantilla se visualiza correctamente
	 *         false si no se visualiza la pantalla Plantilla.
	 * @date 22/Septiembre/2025
	 */
	public boolean opcionmenuplantilla() {
		boolean validacion = false;
		try {
			u.waitWebElement(menuplantilla, 25, 4);

			u.highLight(menuplantilla);
			u.click(menuplantilla);

			// Esperar a que desaparezca el overlay de carga
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));

	        // Por alguna razón se cambia al primer menú y toca volver a seleccionar tabuladores
	        u.click(menuplantilla);

	        // Volvemos a esperar a que desaparezca el overlay por seguridad
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));

			u.waitWebElement(pantallaplantilla, 25, 4);
			testCase.addScreenShotCurrentStep(u, "Se ingresa a la pantalla de Plantilla de manera correcta.");

			u.unhighLight(menuplantilla);
			if (u.isDisplayed(pantallaplantilla)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("No se mostro la pantalla Plantilla. Error: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método que valida la correcta carga de la pantalla **Plantilla**.
	 *
	 * Espera que los botones de editar/eliminar estén disponibles para tomar la
	 * evidencia.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si los botones de editar/eliminar se encuentran visibles en
	 *         pantalla
	 *         false no se mostro la pantalla.
	 * @date 22/Septiembre/2025
	 */
	public boolean cargapantallaplantilla() {
		boolean validacion = false;
		try {
			u.waitWebElement(botoneseditarelimar, 25, 4);
			testCase.addScreenShotCurrentStep(u, "La pantalla de Plantilla se muestra de manera correcta.");

			if (u.isDisplayed(botoneseditarelimar)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("No se mostro la pantalla de Plantilla. Error: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método que permite agregar un nuevo usuario en la pantalla Plantilla.
	 *
	 * Realiza clic en el botón de "Nuevo usuario", espera brevemente la aparición
	 * de la ventana emergente, captura evidencia y valida que el botón "Guardar"
	 * esté disponible para confirmar que la ventana se cargó correctamente.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si la ventana de nuevo usuario se muestra correctamente (botón
	 *         Guardar visible)
	 *         false si no cargo la ventana usuario
	 * @date 23/Septiembre/2025
	 */
	public boolean seleccionarnuevousuario() {
		boolean validacion = false;
		try {
			u.unhighLight(menuplantilla);
			u.click(botonnuevousuario);
			// Se agrega hard wait para esperar que se muestre la ventana emergente en el
			// screen
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Se abre una ventana emergente para agregar el usuario de manera correcta");

			if (u.isDisplayed(botonguardarnuevousuario)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("No cargo la ventana de nuevo usuario");
		}
		return validacion;
	}

	/**
	 * Método para validar que se visualicen los campos y que se cumplan sus
	 * restricciones en la ventana de alta de usuario.
	 *
	 * * Notas: - Si alguno de los campos no se encuentra o no cumple la condición
	 * esperada, se genera un Assert.fail y la ejecución del caso de prueba se
	 * detiene.
	 *
	 * @author Carlos MartínezOrtega
	 * @return true si todos los campos cumplen sus condiciones (visibles,
	 *         editables/no editables). 
	 *         false si ocurre alguno de los siguientes casos: 
	 *         - No se encontraron los campos esperados
	 *         - El campo semana y posición son editables 
	 *         - Uno o mas campos de los que deberian estar activos no lo estan 
	 *         - Error validando los campos de la ventana
	 * @date 23/Septiembre/2025
	 */
	public boolean validarCamposAlta() {
		boolean validacion = false;
		try {
			testCase.addScreenShotCurrentStep(u, "Se valida la existencia de los campos y restricciones de edición");

			// Lista de campos esperados
			By[] camposEsperados = { campocedis, campousuario, campoantiguedad, campoSemana, camponombre,
					seleccionarturno, seleccionartipocolaborador, seleccionartipoincentivo, seleccionarposicion,
					campoposicionclave };

			// Validar existencia de todos los campos
			int encontrados = 0;
			for (By campo : camposEsperados) {
				if (u.isDisplayed(campo)) {
					encontrados++;
				}
			}

			if (encontrados != camposEsperados.length) {
				Assert.fail("No se encontraron todos los campos. Esperados: " + camposEsperados.length
						+ ", encontrados: " + encontrados);
			}

			// Validar que "Semana" y "Posición clave" sean no editables
			WebElement semana = u.getDriver().findElement(camposEsperados[3]);
			WebElement posicionClave = u.getDriver().findElement(camposEsperados[9]);

			boolean semanaNoEditable = semana.getAttribute("readonly") != null
					|| semana.getAttribute("disabled") != null;
			boolean posicionNoEditable = posicionClave.getAttribute("readonly") != null
					|| posicionClave.getAttribute("disabled") != null;

			if (!semanaNoEditable || !posicionNoEditable) {
				Assert.fail("Los campos Semana y/o Posición Clave son editables cuando no deberían serlo.");
			}

			// Validar que los otros 8 campos estén habilitados (activos)
			WebElement cedis = u.getDriver().findElement(campocedis);
			WebElement usuario = u.getDriver().findElement(campousuario);
			WebElement antiguedad = u.getDriver().findElement(campoantiguedad);
			WebElement nombre = u.getDriver().findElement(camponombre);
			WebElement turno = u.getDriver().findElement(seleccionarturno);
			WebElement tipoColaborador = u.getDriver().findElement(seleccionartipocolaborador);
			WebElement tipoIncentivo = u.getDriver().findElement(seleccionartipoincentivo);
			WebElement posicion = u.getDriver().findElement(seleccionarposicion);

			if (!cedis.isEnabled() || !usuario.isEnabled() || !antiguedad.isEnabled() || !nombre.isEnabled()
					|| !turno.isEnabled() || !tipoColaborador.isEnabled() || !tipoIncentivo.isEnabled()
					|| !posicion.isEnabled()) {
				Assert.fail("Uno o más campos que deberían estar activos se encuentran deshabilitados.");
			}
			validacion = true;
			testCase.addTextEvidenceCurrentStep("Los campos especificados se muestran de manera correcta");

		} catch (Exception e) {
			Assert.fail("Error validando los campos en la ventana: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para validar que el sistema muestre el mensaje de error al intentar guardar
	 * un nuevo usuario con campos vacíos en la ventana de alta.
	 * 
	 * - Da clic en el botón "Guardar nuevo usuario".
	 * - Espera que aparezca el mensaje emergente de validación.
	 * - Verifica que el mensaje mostrado coincida exactamente con el esperado:
	 *   "Favor de completar todos los campos vacíos".
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si el mensaje emergente aparece y coincide con el texto esperado.  
	 *         false si ocurre alguno de los siguientes casos:
	 *         - El mensaje no aparece.  
	 *         - El mensaje mostrado es diferente al esperado.  
	 *         - Si ocurre un error al validar la aparición o el contenido del mensaje.
	 * @date 23/Septiembre/2025
	 */
	public boolean Validarmensajecompletarloscampos() {
		boolean validacion = false;
		String mensajeEsperado = "Favor de completar todos los campos vacíos";
		try {
			u.click(botonguardarnuevousuario);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u, "El mensaje aparece de manera correcta");

			if (u.isDisplayed(mensajeemergente)) {
				// Obtener el texto real del mensaje
				String mensajeReal = u.getText(mensajeemergente).trim();

				if (mensajeEsperado.equals(mensajeReal)) {
					validacion = true;
				} else {
					Assert.fail(
							"El mensaje esperado era: '" + mensajeEsperado + "' pero apareció: '" + mensajeReal + "'");
				}
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("No se mostro el mensaje esperado");
		}
		return validacion;
	}

	/**
	 * Método para validar que el sistema muestre un mensaje de error
	 * al intentar dar de alta un usuario con un nombre no alfabético
	 *
	 * @author Carlos Martínez Ortega
	 * @param CEDIS           Nombre del CEDIS a seleccionar en el alta.
	 * @param Usuario         Número de usuario a ingresar.
	 * @param Antiguedad      Valor de antigüedad que se ingresará.
	 * @param Nombreerroneo   Valor inválido para el nombre (numérico o especial).
	 * @param Turno           Turno a seleccionar.
	 * @param Tipocolaborador Tipo de colaborador.
	 * @param Tipoincentivo   Tipo de incentivo.
	 * @param Posicion        Posición a seleccionar.
	 * @return true si el mensaje emergente aparece y coincide con el texto esperado.  
	 *         false si ocurre alguno de los siguientes casos:
	 *         - El mensaje no aparec
	 *         - Es diferente al esperado
	 *         - Si el sistema permite registrar datos inválidos en el campo Nombre.
	 * @date 24/Septiembre/2025
	 */
	public boolean validarsolocaracteresalfanumericos(String CEDIS, String Usuario, String Antiguedad,
			String Nombreerroneo, String Turno, String Tipocolaborador, String Tipoincentivo, String Posicion) {
		By seleccionarcedis = By.xpath("//option[contains(text(),'" + CEDIS + "')]");
		By seleccionarturno = By.xpath("//option[contains(text(),'" + Turno + "')]");
		By seleccionartipocolaborador = By.xpath("//option[contains(text(),'" + Tipocolaborador + "')]");
		By seleccionartipoincentivo = By.xpath("//option[contains(text(),'" + Tipoincentivo + "')]");
		By seleccionarposicion = By.xpath("//option[contains(text(),'" + Posicion + "')]");
		String mensajeEsperado = "El campo nombre solo permite letras";
		boolean validacion = false;
		try {
			u.click(By.xpath("//button[@aria-label='Close']"));
			u.click(seleccionarcedis);
			u.click(seleccionarturno);
			u.click(seleccionartipocolaborador);
			u.click(seleccionartipoincentivo);
			u.click(seleccionarposicion);
			u.sendKeys(camponombre, Nombreerroneo);
			u.sendKeys(campousuario, Usuario);
			u.sendKeys(campoantiguedad, Antiguedad);
			u.click(botonguardarnuevousuario);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u, "El mensaje aparece de manera correcta");

			if (u.isDisplayed(mensajeemergente)) {
				// Obtener el texto real del mensaje
				String mensajeReal = u.getText(mensajeemergente).trim();

				if (mensajeEsperado.equals(mensajeReal)) {
					validacion = true;
				} else {
					Assert.fail(
							"El mensaje esperado era: '" + mensajeEsperado + "' pero apareció: '" + mensajeReal + "'");
				}
			}
		} catch (Exception e) {
			Assert.fail("Permitio agregar datos alfabeticos");
		}
		return validacion;
	}

	/**
	 * Método para validar que el sistema muestre un mensaje de error
	 * al intentar dar de alta un usuario con un valor no numérico en el campo "Usuario".
	 *
	 * @author Carlos Martínez Ortega
	 * @param Nombre         Valor válido para el campo Nombre.
	 * @param Usuarioerroneo Valor no numérico para el campo Usuario
	 * @return true si el mensaje emergente aparece y coincide con el texto esperado.  
	 *         false si ocurre alguno de los siguientes casos:
	 *         - El mensaje no aparece
	 *         - Es distinto al esperado
	 *         - Si el sistema permite avanzar
	 *         datos no numéricos en el campo Usuario.
	 * @date 24/Septiembre/2025
	 */
	public boolean validarsolocaracteresnumericos(String Nombre, String Usuarioerroneo) {
		String mensajeEsperado = "El campo usuario solo permite datos numéricos";
		boolean validacion = false;
		try {
			u.click(cerrarmesajeemergente);
			u.clear(camponombre);
			u.sendKeys(camponombre, Nombre);
			u.clear(campousuario);
			u.sendKeys(campousuario, Usuarioerroneo);
			u.click(botonguardarnuevousuario);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u, "El mensaje aparece de manera correcta");

			if (u.isDisplayed(mensajeemergente)) {
				// Obtener el texto real del mensaje
				String mensajeReal = u.getText(mensajeemergente).trim();

				if (mensajeEsperado.equals(mensajeReal)) {
					validacion = true;
				} else {
					Assert.fail(
							"El mensaje esperado era: '" + mensajeEsperado + "' pero apareció: '" + mensajeReal + "'");
				}
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("Permitio agregar datos alfabeticos");
		}
		return validacion;
	}

	/**
	 * Método para validar el cálculo automático del campo "Semana"
	 * en función de la fecha de antigüedad ingresada.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si el valor mostrado en el campo Semana coincide con el cálculo esperado.
	 *         false si ocurre alguno de los siguientes casos: 
	 *         - La fecha de antigüedad está vacía o es inválida.
	 *         - La fecha de antigüedad es futura.
	 *         - El valor en "Semana" no coincide con el cálculo esperado.
	 *         - Ocurre un error durante la validación.
	 * @date 27/Septiembre/2025
	 */
	public boolean validarcalculosemana() {
		boolean validacion = false;
		try {

			// Leer la fecha que ya está cargada en Antiguedad (valor del input)
			String fechaAntiguedadStr = u.getDriver().findElement(campoantiguedad).getAttribute("value");

			if (fechaAntiguedadStr == null || fechaAntiguedadStr.trim().isEmpty()) {
				Assert.fail("No se obtuvo la fecha de antigüedad del input.");
				return false;
			}

			// Parsear la fecha con formato dd/MM/yyyy
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fechaSeleccionada = LocalDate.parse(fechaAntiguedadStr, formatter);

			// Calcular diferencia en días y semanas
			LocalDate hoy = LocalDate.now();
			long dias = ChronoUnit.DAYS.between(fechaSeleccionada, hoy);
			if (dias < 0) {
				Assert.fail("La fecha de antigüedad es futura: " + fechaAntiguedadStr);
				return false;
			}

			long semanasEsperadas = dias / 7; // truncamiento hacia abajo

			// Leer el valor que muestra la app en el campo Semana
			String valorSemana = u.getDriver().findElement(campoSemana).getAttribute("value").trim();

			// Comparar con el valor esperado
			if (valorSemana.equals(String.valueOf(semanasEsperadas))) {
				testCase.addScreenShotCurrentStep(u, "El campo Semana muestra el valor correcto: " + valorSemana);
				validacion = true;
			} else {
				Assert.fail("Valor incorrecto en Semana. Esperado: " + semanasEsperadas + ", Obtenido: " + valorSemana);
			}

		} catch (Exception e) {
			Assert.fail("Error validando el campo Semana: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para validar que no se permita registrar un usuario con una fecha de antigüedad
	 * mayor a un año atrás o superior al día actual.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Usuario valor que se utilizará para llenar el campo "Usuario".
	 * @param Antiguedaderronea fecha inválida para el campo "Antigüedad", usada para forzar el error.
	 * @return true si el mensaje emergente coincide exactamente con el esperado.
	 *         false si ocurre alguno de los siguientes casos:
	 *         - No aparece el mensaje esperado.
	 *         - El mensaje es diferente al esperado.
	 *         - Se permite avanzar con una fecha inválida.
	 * @date 24/Septiembre/2025
	 */
	public boolean validarfechamayoraunano(String Usuario, String Antiguedaderronea) {
		boolean validacion = false;
		String mensajeEsperado = "La fecha de ingreso no puede ser mayor al día actual y menor a un año atrás";
		try {
			u.click(cerrarmesajeemergente);
			u.clear(campousuario);
			u.sendKeys(campousuario, Usuario);
			u.clear(campoantiguedad);
			u.sendKeys(campoantiguedad, Antiguedaderronea);
			u.click(botonguardarnuevousuario);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u, "El mensaje aparece de manera correcta");

			if (u.isDisplayed(mensajeemergente)) {
				// Obtener el texto real del mensaje
				String mensajeReal = u.getText(mensajeemergente).trim();

				if (mensajeEsperado.equals(mensajeReal)) {
					validacion = true;
				} else {
					Assert.fail(
							"El mensaje esperado era: '" + mensajeEsperado + "' pero apareció: '" + mensajeReal + "'");
				}
			}
		} catch (Exception e) {
			Assert.fail("Permitio agregar un usuario con fecha mayor a 1 año");
		}
		return validacion;
	}

	/**
	 * Método para validar el alta correcta de un nuevo usuario en la pantalla de Plantilla.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Antiguedad fecha de ingreso que se usará para completar el campo obligatorio.
	 * @return true si el mensaje de confirmación aparece y coincide exactamente con el esperado.
	 *         false si ocurre alguno de los siguientes casos:
	 *         - No aparece el mensaje emergente.
	 *         - El mensaje es diferente al esperado.
	 *         - Ocurre un error durante la validación..
	 * @date 24/Septiembre/2025
	 */
	public boolean validaragregarunusuario(String Antiguedad) {
		boolean validacion = false;
		String mensajeEsperado = "El usuario se agregó de manera correcta";
		try {
			u.click(cerrarmesajeemergente);
			u.clear(campoantiguedad);
			u.sendKeys(campoantiguedad, Antiguedad);
			testCase.addScreenShotCurrentStep(u, "Se llenan los campos con la información correcta");
			u.click(botonguardarnuevousuario);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u, "El mensaje aparece de manera correcta");

			if (u.isDisplayed(mensajeemergente)) {
				// Obtener el texto real del mensaje
				String mensajeReal = u.getText(mensajeemergente).trim();

				if (mensajeEsperado.equals(mensajeReal)) {
					validacion = true;
				} else {
					Assert.fail(
							"El mensaje esperado era: '" + mensajeEsperado + "' pero apareció: '" + mensajeReal + "'");
				}
			}
		} catch (Exception e) {
			Assert.fail("Permitio agregar datos alfabeticos");
		}
		return validacion;
	}

	/**
	 * Método para seleccionar la opción de edición de un usuario dentro de la tabla en la pantalla de Plantilla.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Nombre nombre del usuario que se desea buscar en la tabla para editar.
	 * @return true si el usuario se encuentra en la tabla y se abre correctamente la ventana de edición.
	 *         false si ocurre alguno de los siguientes casos:
	 *         - No se encuentre el usuario especificado en la tabla.
	 *         - No aparezca la ventana emergente de edición.
	 *         - Ocurra un error durante la interacción con los elementos.
	 * @date 27/Septiembre/2025
	 */
	public boolean seleccionareditarusuario(String Nombre) {
		boolean validacion = false;
		try {
			u.unhighLight(menuplantilla);
			By botonEditar = By.xpath("//table/tbody/tr[td[2][contains(text(),'" + Nombre + "')]]/td[10]//button[1]");
			u.click(botonEditar);
			// Se agrega hard wait para esperar que se muestre la ventana emergente en la pantalla
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "La pantalla de Plantilla se muestra de manera correcta.");

			if (u.isDisplayed(botonguardarnuevousuario)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("No se encontro el usuario");
		}
		return validacion;
	}

	/**
	 * Método para validar la visualización y restricciones de los campos en la ventana de edición de usuario.
	 *
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si todos los campos cumplen las condiciones establecidas:
	 *         - Visibles en pantalla
	 *         - Editables/no editables según corresponda
	 *         false si ocurre alguno de los siguientes casos:
	 *         - No se encuentran todos los campos esperados
	 *         - Uno o más campos que deberían ser no editables sí permiten edición
	 *         - Uno o más campos que deberían estar habilitados aparecen deshabilitados
	 *         - Error al validar los campos de la ventana
	 * @date 23/Septiembre/2025
	 */
	public boolean validarCamposEditar() {
		boolean validacion = false;
		try {
			testCase.addScreenShotCurrentStep(u,
					"Se valida la existencia de los campos y restricciones de edición en la ventana Editar");

			// Lista de campos esperados
			By[] camposEsperados = { campocedis, campousuario, campoantiguedad, campoSemana, camponombre,
					seleccionarturno, seleccionartipocolaborador, seleccionartipoincentivo, seleccionarposicion,
					campoposicionclave };

			// Validar existencia de todos los campos
			int encontrados = 0;
			for (By campo : camposEsperados) {
				if (u.isDisplayed(campo)) {
					encontrados++;
				}
			}

			if (encontrados != camposEsperados.length) {
				Assert.fail("No se encontraron todos los campos. Esperados: " + camposEsperados.length
						+ ", encontrados: " + encontrados);
			}

			// --- Validar que estos 6 campos sean NO editables ---
			WebElement cedis = u.getDriver().findElement(campocedis);
			WebElement usuario = u.getDriver().findElement(campousuario);
			WebElement antiguedad = u.getDriver().findElement(campoantiguedad);
			WebElement semana = u.getDriver().findElement(campoSemana);
			WebElement nombre = u.getDriver().findElement(camponombre);
			WebElement posicionClave = u.getDriver().findElement(campoposicionclave);

			boolean cedisNoEditable = cedis.getAttribute("readonly") != null || cedis.getAttribute("disabled") != null;
			boolean usuarioNoEditable = usuario.getAttribute("readonly") != null
					|| usuario.getAttribute("disabled") != null;
			boolean antiguedadNoEditable = antiguedad.getAttribute("readonly") != null
					|| antiguedad.getAttribute("disabled") != null;
			boolean semanaNoEditable = semana.getAttribute("readonly") != null
					|| semana.getAttribute("disabled") != null;
			boolean nombreNoEditable = nombre.getAttribute("readonly") != null
					|| nombre.getAttribute("disabled") != null;
			boolean posicionNoEditable = posicionClave.getAttribute("readonly") != null
					|| posicionClave.getAttribute("disabled") != null;

			if (!cedisNoEditable || !usuarioNoEditable || !antiguedadNoEditable || !semanaNoEditable
					|| !nombreNoEditable || !posicionNoEditable) {
				Assert.fail("Uno o más campos que deberían ser no editables se pueden editar.");
			}

			// --- Validar que estos 4 campos estén habilitados (activos) ---
			WebElement turno = u.getDriver().findElement(seleccionarturno);
			WebElement tipoColaborador = u.getDriver().findElement(seleccionartipocolaborador);
			WebElement tipoIncentivo = u.getDriver().findElement(seleccionartipoincentivo);
			WebElement posicion = u.getDriver().findElement(seleccionarposicion);

			if (!turno.isEnabled() || !tipoColaborador.isEnabled() || !tipoIncentivo.isEnabled()
					|| !posicion.isEnabled()) {
				Assert.fail("Uno o más campos que deberían estar activos se encuentran deshabilitados.");
			}
			validacion = true;
			testCase.addTextEvidenceCurrentStep("Los campos y sus reglas se muestran de manera correcta");

		} catch (Exception e) {
			Assert.fail("Error validando los campos en la ventana Editar: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para editar un usuario existente en la pantalla de Plantilla y validar que la actualización sea exitosa.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Turno           valor del turno que se editara al usuario.
	 * @param Tipocolaborador valor del tipo de colaborador que se editara 
	 * @param Tipoincentivo   valor del tipo de incentivo que se editara (ejemplo: "Fijo").
	 * @param Posicion        valor de la posición que se editara al usuario.
	 * @return true si el mensaje de confirmación aparece con el texto esperado,
	 *         false si el mensaje difiere o si ocurre un error durante la edición.
	 * @date 25/Septiembre/2025
	 */
	public boolean validareditarunusuario(String Turno, String Tipocolaborador, String Tipoincentivo, String Posicion) {
		// Localizadores dinámicos de los selects
		String mensajeEsperado = "La información del usuario se actualizo de manera correcta";
		boolean validacion = false;
		try {
			u.sendKeys(seleccionarturno, Turno);
			u.sendKeys(seleccionartipocolaborador, Tipocolaborador);
			u.sendKeys(seleccionartipoincentivo, Tipoincentivo);
			u.sendKeys(seleccionarposicion, Posicion);
			testCase.addScreenShotCurrentStep(u,
					"Los campos se editaron de manera correcta");
			u.click(botonguardarnuevousuario);

			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u,
					"La información del usuario se actualizó y apareció el mensaje correctamente");

			if (u.isDisplayed(mensajeemergente)) {
				String mensajeReal = u.getText(mensajeemergente).trim();

				if (mensajeEsperado.equals(mensajeReal)) {
					validacion = true;
				} else {
					Assert.fail(
							"El mensaje esperado era: '" + mensajeEsperado + "' pero apareció: '" + mensajeReal + "'");
				}
			}
		} catch (Exception e) {
			Assert.fail("El usuario no se editó y no apareció el mensaje: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para seleccionar la opción de eliminar un usuario desde la pantalla de Plantilla.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Nombre nombre del usuario que se desea eliminar (se utiliza para identificar la fila en la tabla).
	 * @return true si la ventana de confirmación de eliminación se muestra correctamente con el botón
	 *         false si la ventana no aparece o si ocurre algún error al interactuar con los elementos.
	 * @date 25/Septiembre/2025
	 */
	public boolean seleccionareliminarusuario(String Nombre) {
		boolean validacion = false;
		try {
			u.unhighLight(menuplantilla);
			By botonEliminar = By.xpath("//table/tbody/tr[td[2][contains(text(),'" + Nombre + "')]]/td[10]//button[2]");
			u.highLight(botonEliminar);
			u.click(botonEliminar);
			// Se agrega el hardwait ya que con el waitelement no da tiempo a mostrar la ventana emergente
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "La ventana se muestra de manera correcta");

			if (u.isDisplayed(botonsieliminar)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("No se muestra la ventana de eliminar");
		}
		return validacion;
	}

	/**
	 * Método para validar la opción "No" en la ventana de confirmación al intentar eliminar un usuario.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Nombre nombre del usuario que se intenta eliminar
	 * @return true si el registro del usuario sigue visible en la tabla después de cancelar la acción.
	 *         false si el usuario no aparece  o si ocurre algún error en la validación.
	 * @date 25/Septiembre/2025
	 */
	public boolean seleccionarnoeneliminar(String Nombre) {
		boolean validacion = false;
		try {
			u.click(botonnoeliminar);
			// Se agrega el hardwait para darle tiempo a que desaparezca la ventana emergente
			u.hardWait(1);
			By usuarioEnTabla = By.xpath("//table/tbody/tr/td[2][contains(text(),'" + Nombre + "')]");

			if (u.isDisplayed(usuarioEnTabla)) {
				testCase.addScreenShotCurrentStep(u,
						"El registro no se elimina y la ventna se cierra de manera correcta");
				validacion = true;
			} else {
				Assert.fail("El usuario '" + Nombre + "' no se encontró en la tabla, parece que fue eliminado.");
			}

		} catch (Exception e) {
			Assert.fail("Error validando que el usuario siga en la tabla tras dar clic en 'No': " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para confirmar la eliminación de un usuario en la pantalla Plantilla.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Nombre nombre del usuario que se desea eliminar, utilizado para localizar 
	 *        dinámicamente el botón de eliminación en la tabla.
	 * @return true si se muestra el mensaje emergente de confirmación y el registro es eliminado de la tabla.  
	 *         false si el mensaje no aparece o el registro no se elimina correctamente.  
	 * @date 26/Septiembre/2025
	 */
	public boolean seleccionarsieliminar(String Nombre) {
		boolean validacion = false;
		try {
			By botonEliminar = By.xpath("//table/tbody/tr[td[2][contains(text(),'" + Nombre + "')]]/td[10]//button[2]");
			u.click(botonEliminar);
			// Se agrega el hardwait ya que con el waitelement no da tiempo a mostrar la
			// ventana emergente
			u.hardWait(1);
			u.click(botonsieliminar);
			// Este hardwait es para dar tiempo a que el registro eliminado desaparezca de
			// pantalla
			u.hardWait(1);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u,
					"El mensaje aparece de manera correcta y el usuario ya no se muestra en la pantalla ");

			if (u.isDisplayed(mensajeemergente)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("El mensaje no aparecio y no se borro el registro");
		}
		return validacion;
	}
	
	/**
	 * Método para preparar los datos de prueba necesarios para validar la inserción de un usuario (Alta) 
	 * en el portal y su correspondiente registro en la base de datos.
	 *
	 * @author Carlos Martínez Ortega
	 * @param user usuario del portal para el login.
	 * @param password contraseña del portal.
	 * @param URL cadena de conexión a la base de datos.
	 * @param USER usuario de la base de datos.
	 * @param PASSWORD contraseña de la base de datos.
	 * @param nombreUsuario nombre del usuario a consultar en el portal; si es null o vacío, se tomará el primer registro disponible.
	 * @param la objeto de la clase LaborManagment utilizado para la navegación.
	 * @param lap objeto de la clase LaborManagmentPlantilla utilizado para obtener valores del portal.
	 * @param u instancia de SeleniumUtil para interacción con la UI.
	 * @return Map<String, Object> que contiene:
	 *         - "valoresPortal": Map con los valores obtenidos del portal.
	 *         - "resultadoAlta": instancia de SQLResultAlta con los registros obtenidos de la BD.
	 * @date 25/Septiembre/2025
	 */
	public static Map<String, Object> prepararDatosPruebaAlta(String user, String password, String URL, String USER,
			String PASSWORD, String nombreUsuario, LaborManagment la, LaborManagmentPlantilla lap, SeleniumUtil u) {


		// Login y navegación
		lap.ingresarPantallaPlantillaPorDatos(user, password, la, user, password);

		// Obtener valores del portal (si no hay nombre, usar primer registro)
		Map<String, String> valoresPortal;
		if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
			valoresPortal = lap.obtenerPrimerRegistroPortal(); // primer registro dinámico
		} else {
			valoresPortal = lap.obtenerValoresPortal(nombreUsuario);
		}

		// Armar query solo con nombre
		String query = "SELECT * FROM xxmai_inc.xxmai_plantilla " + "WHERE nombre = '" + valoresPortal.get("nombre")
				+ "'";

		// Ejecutar el query en la BD
		SQLResultAlta resultadoAlta = LaborManagmentPlantilla.executeQueryAlta(query, URL, USER, PASSWORD);

		Map<String, Object> datos = new HashMap<>();
		datos.put("valoresPortal", valoresPortal);
		datos.put("resultadoAlta", resultadoAlta);

		return datos;
	}

	/**
	 * Método para ejecutar un query SQL en la tabla XXMAI_PLANTILLA y obtener
	 * los datos de un usuario dado de alta.
	 *
	 * @author Carlos Martínez Ortega
	 * @param query     Sentencia SQL que se ejecutará para obtener la información.
	 * @param URL       Dirección de conexión a la base de datos.
	 * @param USER      Usuario con permisos para acceder a la base de datos.
	 * @param PASSWORD  Contraseña del usuario de la base de datos.
	 * @return Un objeto SQLResultAlta que contiene la lista de resultados (filas) obtenidas
	 *         de la consulta. Cada fila incluye 13 campos (idPlant, idWarehouse, noEmpleado,
	 *         nombre, turno, fechaIngreso, semanas, clasificacion, tpo_incentivo, actividad,
	 *         claveActividad, createdBy, creationDate).
	 * @date 26/Septiembre/2025
	 */
	public static SQLResultAlta executeQueryAlta(String query, String URL, String USER, String PASSWORD) {
		List<String[]> resultados = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(query)) {

			// Guardar los resultados del query ejecutado
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String idPlant = rs.getString("id_plant");
					String idWarehouse = rs.getString("id_warehouse");
					String noEmpleado = rs.getString("no_empleado");
					String nombre = rs.getString("nombre");
					String turno = rs.getString("turno");
					String fechaIngreso = normalizarFecha(rs.getString("fecha_ingreso"));
					String semanas = rs.getString("semanas");
					String clasificacion = normalizarTipoColaborador(rs.getString("clasificacion"));
					String tpo_incentivo = normalizarTipoIncentivo(rs.getString("tpo_incentivo"));
					String actividad = rs.getString("actividad");
					String claveActividad = rs.getString("clave_actividad");
					String createdBy = rs.getString("created_by");
					String creationDate = normalizarFecha(rs.getString("creation_date"));

					resultados
							.add(new String[] { idPlant, idWarehouse, noEmpleado, nombre, turno, fechaIngreso, semanas,
									clasificacion, tpo_incentivo, actividad, claveActividad, createdBy, creationDate });
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}

		return new SQLResultAlta(resultados);
	}

	/**
	 * Clase que representa el resultado de una consulta SQL ejecutada sobre la
	 * tabla XXMAI_PLANTILLA, usada en pruebas de alta de usuarios.
	 *
	 * @author Carlos Martínez Martínez
	 * @date 26/Septiembre/2025
	 */
	public static class SQLResultAlta {
		private final List<String[]> resultados;

		public SQLResultAlta(List<String[]> resultados) {
			this.resultados = resultados;
		}

		public boolean isEmpty() {
			return resultados == null || resultados.isEmpty();
		}

		public List<String[]> getResultados() {
			return resultados;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (String[] fila : resultados) {
				sb.append("ID_PLANT: ").append(fila[0]).append(" | ID_WAREHOUSE: ").append(fila[1])
						.append(" | NO_EMPLEADO: ").append(fila[2]).append(" | NOMBRE: ").append(fila[3])
						.append(" | TURNO: ").append(fila[4]).append(" | FECHA_INGRESO: ").append(fila[5])
						.append(" | SEMANAS: ").append(fila[6]).append(" | CLASIFICACION: ").append(fila[7])
						.append(" | TPO_INCENTIVO: ").append(fila[8]).append(" | ACTIVIDAD: ").append(fila[9])
						.append(" | CLAVE_ACTIVIDAD: ").append(fila[10]).append(" | CREATED_BY: ").append(fila[11])
						.append(" | CREATION_DATE: ").append(fila[12]).append("\n");
			}
			return sb.toString();
		}
	}

	/**
	 * Método para obtener los valores de un usuario en la tabla del portal de incentivos.
	 *
	 * @param nombreUsuario Nombre del usuario a buscar en la tabla. Si es null o vacío, 
	 *                      se toma el primer registro.
	 * @return Map con los valores obtenidos del portal (clave = nombre lógico, valor = texto en pantalla).
	 * @author Carlos Martínez Ortega
	 * @date 26/Septiembre/2025
	 */
	public Map<String, String> obtenerValoresPortal(String nombreUsuario) {
		Map<String, String> valores = new HashMap<>();
		String filaBase;

		if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
			// Buscar por nombre
			filaBase = "//table/tbody/tr[td[2][contains(text(),'" + nombreUsuario + "')]]";
		} else {
			// Si no se pasa nombre, tomar la primera fila
			filaBase = "//table/tbody/tr[1]";
		}

		valores.put("usuario", u.getText(By.xpath(filaBase + "/td[1]")));
		valores.put("nombre", u.getText(By.xpath(filaBase + "/td[2]")));
		valores.put("turno", u.getText(By.xpath(filaBase + "/td[3]")));
		valores.put("antiguedad", normalizarFecha(u.getText(By.xpath(filaBase + "/td[4]"))));
		valores.put("semana", u.getText(By.xpath(filaBase + "/td[5]")));
		valores.put("colaborador", u.getText(By.xpath(filaBase + "/td[6]")));
		valores.put("incentivo", u.getText(By.xpath(filaBase + "/td[7]")));
		valores.put("posicion", u.getText(By.xpath(filaBase + "/td[8]")));
		valores.put("posicionClave", u.getText(By.xpath(filaBase + "/td[9]")));

		return valores;
	}

	/**
	 * Método para preparar los datos de prueba utilizados en la validación de edición de usuarios.
	 *
	 * @param user       Usuario de acceso al portal.
	 * @param password   Contraseña de acceso al portal.
	 * @param URL        URL de conexión a la base de datos.
	 * @param USER       Usuario de la base de datos.
	 * @param PASSWORD   Contraseña de la base de datos.
	 * @param nombreUsuario Nombre del usuario a buscar en el portal. Si es null o vacío,
	 *                      se toma el primer registro de la tabla.
	 * @param la         Objeto de la clase LaborManagment para operaciones en el portal.
	 * @param lap        Objeto de la clase LaborManagmentPlantilla para interacciones con la pantalla Plantilla.
	 * @param u          Objeto SeleniumUtil para manejo del navegador y acciones sobre elementos web.
	 * @return Map con dos llaves principales:
	 *         - "valoresPortal": Map<String, String> con los valores obtenidos desde el portal.
	 *         - "resultadoEditar": Objeto SQLResultEditar con los resultados obtenidos desde la BD.
	 *
	 * @author Carlos Martínez Ortega
	 * @date 25/Septiembre/2025
	 */
	public static Map<String, Object> prepararDatosPruebaEditar(String user, String password, String URL, String USER,
			String PASSWORD, String nombreUsuario, LaborManagment la, LaborManagmentPlantilla lap, SeleniumUtil u) {

		// Login y navegación
		lap.ingresarPantallaPlantillaPorDatos(user, password, la, user, password);

		// Obtener valores del portal (si no hay nombre, usar primer registro)
		Map<String, String> valoresPortal;
		if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
			valoresPortal = lap.obtenerPrimerRegistroPortal();
		} else {
			valoresPortal = lap.obtenerValoresPortal(nombreUsuario);
		}

		// Armar query solo con nombre
		String query = "SELECT * FROM xxmai_inc.xxmai_plantilla " + "WHERE nombre = '" + valoresPortal.get("nombre")
				+ "'";

		// Ejecutar el query en la BD
		SQLResultEditar resultadoEditar = LaborManagmentPlantilla.executeQueryEditar(query, URL, USER, PASSWORD);

		// Empaquetar ambos resultados
		Map<String, Object> datos = new HashMap<>();
		datos.put("valoresPortal", valoresPortal);
		datos.put("resultadoEditar", resultadoEditar);

		return datos;
	}

	/**
	 * Método para ejecutar un query SQL en la base de datos XXMAI y obtener
	 * la información relacionada con usuarios para pruebas de edición.
	 *
	 * @param query     Query SQL a ejecutar en la base de datos.
	 * @param URL       URL de conexión a la base de datos.
	 * @param USER      Usuario de la base de datos.
	 * @param PASSWORD  Contraseña de la base de datos.
	 * @return SQLResultEditar objeto que contiene una lista de filas (String[]) con los valores extraídos.
	 *         Cada fila incluye 15 columnas en el mismo orden que se definen en el código.
	 *         En caso de error de conexión o ejecución, se devuelve un objeto vacío.
	 *
	 * @author Carlos Martínez Ortega
	 * @date 25/Septiembre/2025
	 */
	public static SQLResultEditar executeQueryEditar(String query, String URL, String USER, String PASSWORD) {

		List<String[]> resultados = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement ps = conn.prepareStatement(query)) {

			//Extraer los resultados del query ejecutado 
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String idPlant = rs.getString("id_plant");
					String idWarehouse = rs.getString("id_warehouse");
					String noEmpleado = rs.getString("no_empleado");
					String Nombre = rs.getString("nombre");
					String Turno = rs.getString("turno");
					String fechaIngreso = normalizarFecha(rs.getString("fecha_ingreso"));
					String Semanas = rs.getString("semanas");
					String Clasificacion = normalizarTipoColaborador(rs.getString("clasificacion"));
					String tpo_incentivo = normalizarTipoIncentivo(rs.getString("tpo_incentivo"));
					String Actividad = rs.getString("actividad");
					String Clave_actividad = rs.getString("clave_actividad");
					String createdBy = rs.getString("created_by");
					String creationDate = rs.getString("creation_date");
					String modifiedBy = rs.getString("modified_by");
					String modifiedDate = rs.getString("modified_date");

					resultados.add(new String[] { idPlant, // 0
							idWarehouse, // 1
							noEmpleado, // 2
							Nombre, // 3
							Turno, // 4
							fechaIngreso, // 5
							Semanas, // 6
							Clasificacion, // 7
							tpo_incentivo, // 8
							Actividad, // 9
							Clave_actividad, // 10
							createdBy, // 11
							creationDate, // 12
							modifiedBy, // 13
							modifiedDate // 14
					});
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}

		return new SQLResultEditar(resultados);
	}

	/**
	 * Clase contenedora de resultados de consultas SQL para pruebas de edición de usuarios.
	 *
	 * @author Carlos Martínez oRTEGA
	 * @date 25/Septiembre/2025
	 */
	public static class SQLResultEditar {
		private final List<String[]> resultados;

		public SQLResultEditar(List<String[]> resultados) {
			this.resultados = resultados;
		}

		public boolean isEmpty() {
			return resultados == null || resultados.isEmpty();
		}

		public List<String[]> getResultados() {
			return resultados;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (String[] fila : resultados) {
				sb.append("ID_PLANT: ").append(fila[0]).append(" | ID_WAREHOUSE: ").append(fila[1])
						.append(" | NO_EMPLEADO: ").append(fila[2]).append(" | NOMBRE: ").append(fila[3])
						.append(" | TURNO: ").append(fila[4]).append(" | FECHA_INGRESO: ").append(fila[5])
						.append(" | SEMANAS: ").append(fila[6]).append(" | CLASIFICACION: ").append(fila[7])
						.append(" | TPO_INCENTIVO: ").append(fila[8]).append(" | ACTIVIDAD: ").append(fila[9])
						.append(" | CLAVE_ACTIVIDAD: ").append(fila[10]).append(" | CREATED_BY: ").append(fila[11])
						.append(" | CREATION_DATE: ").append(fila[12]).append(" | MODIFIED_BY: ").append(fila[13])
						.append(" | MODIFIED_DATE: ").append(fila[14]).append("\n");
			}
			return sb.toString();
		}
	}

	/**
	 * Ejecuta un query SQL para validar la eliminación de registros en la tabla `xxmai_plantilla`.
	 *
	 * @param query Instrucción SQL a ejecutar
	 * @param URL   URL de conexión a la base de datos.
	 * @param USER  Usuario con permisos de acceso a la BD.
	 * @param PASSWORD Contraseña asociada al usuario de la BD.
	 * 
	 * @return Un objeto {@link SQLResultEliminar} que contiene una lista con los nombres
	 *         obtenidos de la consulta. Si la lista está vacía, significa que el registro
	 *         fue eliminado correctamente.
	 *
	 * @throws RuntimeException en caso de error de conexión o ejecución de la consulta.
	 *
	 * @author Carlos Martínez Ortega
	 * @date 25/Septiembre/2025
	 */
	public static SQLResultEliminar executeQuery(String query, String URL, String USER, String PASSWORD) {
		List<String> resultados = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				resultados.add(rs.getString("nombre"));
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}

		return new SQLResultEliminar(resultados);
	}

	/**
	 * Clase contenedora para los resultados de una consulta de eliminación en la BD.
	 *
	 * Métodos principales:
	 * - {@link #isEmpty()} → Indica si no se obtuvieron resultados.
	 * - {@link #getResultados()} → Devuelve la lista de nombres encontrados.
	 * - {@link #toString()} → Convierte los resultados en un String separado por comas.
	 *
	 * @author Carlos Martínez Ortega
	 * @date 25/Septiembre/2025
	 */
	public static class SQLResultEliminar {
		private final List<String> resultados;

		public SQLResultEliminar(List<String> resultados) {
			this.resultados = resultados;
		}

		public boolean isEmpty() {
			return resultados == null || resultados.isEmpty();
		}

		public List<String> getResultados() {
			return resultados;
		}

		@Override
		public String toString() {
			return String.join(", ", resultados);
		}
	}

	/**
	 * Método para normalizar fechas provenientes del portal o de la base de datos.
	 *
	 * @author Carlos Martínez
	 * @param fecha Cadena de texto que representa la fecha (con o sin hora).
	 * @return Cadena con la fecha normalizada en el formato dd-MM-yyyy,
	 *         o la fecha original si no se pudo transformar.
	 * @date 26/Septiembre/2025
	 */
	private static String normalizarFecha(String fecha) {
		try {
			if (fecha == null || fecha.trim().isEmpty()) {
				return "";
			}

			// Si viene con hora (ej: 2025-09-03 17:17:43.783414), solo tomara los valores
			// yyyy-MM-dd
			if (fecha.contains(" ")) {
				fecha = fecha.split(" ")[0];
			}

			// Detecta formato con "/" (portal) dd/MM/yyyy
			if (fecha.contains("/")) {
				LocalDate f = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				return f.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			}
			// Detecta formato con "-" (BD) → yyyy-MM-dd
			else if (fecha.contains("-")) {
				LocalDate f = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				return f.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			}
		} catch (Exception e) {
			System.err.println("Error al normalizar fecha: " + fecha);
		}
		return fecha; // fallback sin modificar
	}

	/**
	 * Método para normalizar el valor del campo "Tipo de Colaborador".
	 *
	 * @author Carlos Martínez Ortega
	 * @param valor Cadena que representa el tipo de colaborador (ej. "C", "E", "Capacitación", "Experto").
	 * @return Cadena normalizada con el valor completo ("Capacitación" o "Experto"),
	 *         o el valor original si no coincide con ninguno de los casos conocidos.
	 * @date 27/Septiembre/2025
	 */
	private static String normalizarTipoColaborador(String valor) {
		if (valor == null)
			return "";
		switch (valor.trim().toUpperCase()) {
		case "C":
			return "Capacitación";
		case "E":
			return "Experto";
		case "CAPACITACIÓN":
			return "Capacitación";
		case "EXPERTO":
			return "Experto";
		default:
			return valor.trim();
		}
	}

	/**
	 * Método para normalizar el valor del campo "Tipo de Incentivo".
	 *
	 * @author Carlos Martínez Ortega
	 * @param valor Cadena que representa el tipo de incentivo (ej. "F", "V", "Fijo", "Variable").
	 * @return Cadena normalizada con el valor completo ("Fijo" o "Variable"),
	 *         o el valor original si no coincide con ninguno de los casos conocidos.
	 * @date 27/Septiembre/2025
	 */
	private static String normalizarTipoIncentivo(String valor) {
		if (valor == null)
			return "";
		switch (valor.trim().toUpperCase()) {
		case "F":
			return "Fijo";
		case "V":
			return "Variable";
		case "FIJO":
			return "Fijo";
		case "VARIABLE":
			return "Variable";
		default:
			return valor.trim();
		}
	}

	/**
	 * Método para obtener los valores del primer registro de la tabla en el portal.
	 * 
	 * @author Carlos Martínez Ortega
	 * @return Un Map<String, String> con los valores obtenidos del primer registro
	 *         de la tabla en el portal. Cada clave representa el nombre del campo
	 *         (ej. "usuario", "nombre", "turno", "antiguedad", etc.).
	 * @date 25/Septiembre/2025
	 */
	public Map<String, String> obtenerPrimerRegistroPortal() {
		Map<String, String> valores = new HashMap<>();
		String filaBase = "//table/tbody/tr[1]"; //

		valores.put("usuario", u.getText(By.xpath(filaBase + "/td[1]")));
		valores.put("nombre", u.getText(By.xpath(filaBase + "/td[2]")));
		valores.put("turno", u.getText(By.xpath(filaBase + "/td[3]")));
		String fechaPortal = u.getText(By.xpath(filaBase + "/td[4]"));
		valores.put("antiguedad", LaborManagmentPlantilla.normalizarFecha(fechaPortal));
		valores.put("semana", u.getText(By.xpath(filaBase + "/td[5]")));
		valores.put("colaborador", u.getText(By.xpath(filaBase + "/td[6]")));
		valores.put("incentivo", u.getText(By.xpath(filaBase + "/td[7]")));
		valores.put("posicion", u.getText(By.xpath(filaBase + "/td[8]")));
		valores.put("posicionClave", u.getText(By.xpath(filaBase + "/td[9]")));

		return valores;
	}
	
	/**
	 * Método para ingresar al Portal de Incentivos y acceder a la pantalla Plantilla.
	 * 
	 * Nota: Se trataron de reutilizar las ya creadas pero existian conflictos entre ellas y llegaba a la pantalla plantilla
	 * se generan estos pasos para poder llegar a la pantalla y obtener los campos del primer registro de la tabla. 
	 * 
	 * @author Carlos Martínez Ortega
	 * @param user  Usuario de acceso al portal.
	 * @param password Contraseña de acceso al portal.
	 * @param la Objeto de la clase LaborManagment, utilizado como referencia del flujo.
	 * @param user2 Segundo parámetro de usuario
	 * @param password2 Segundo parámetro de contraseña
	 * @return true si el flujo de login y acceso a la pantalla Plantilla se ejecuta correctamente,
	 *         con botones de editar/eliminar visibles. 
	 *         false no cargo la pantalla plantillaS
	 * @date 25/Septiembre/2025
	 */
	public boolean ingresarPantallaPlantillaPorDatos(String user, String password, LaborManagment la, String user2,
			String password2) {
		boolean validacion = false;
		try {
			u.highLight(userLabel);
			u.clear(userLabel);
			u.sendKeys(userLabel, user2);

			u.highLight(passwordLabel);
			u.clear(passwordLabel);
			u.sendKeys(passwordLabel, password2);

			u.highLight(loginButton);
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(loginButton);

			System.out.println("Login enviado, esperando botón Portal Incentivos...");
			u.waitWebElement(portalincentivos, 60, 4);
			u.highLight(portalincentivos);
			testCase.addScreenShotCurrentStep(u, "Seleccionar opción Portal incentivos");
			System.out.println("Botón Portal Incentivos encontrado, dando click...");
			u.click(portalincentivos);

			System.out.println("Esperando pantalla de carga...");
			u.waitWebElement(pantallacargarArchivo, 60, 4);
			
			testCase.addScreenShotCurrentStep(u, "Pantalla Cargar Archivo");
			System.out.println("Portal Incentivos cargado correctamente");

			System.out.println("Esperando que aparezca el menú Plantilla...");
			u.waitWebElement(menuplantilla, 25, 4); // Esperamos antes de hacer click

			System.out.println("Dando clic en menú Plantilla...");
			u.highLight(menuplantilla);
			u.click(menuplantilla);

			System.out.println("Esperando botones de editar/eliminar...");
			u.waitWebElement(botoneseditarelimar, 25, 4);
			testCase.addScreenShotCurrentStep(u, "Pantalla Plantilla");

			if (u.isDisplayed(botoneseditarelimar)) {
				validacion = true;
				System.out.println("Botones de editar/eliminar visibles.");
			}
		} catch (Exception e) {
			Assert.fail("No cargo la pantalla de Plantilla. Error: " + e.getMessage());
		}
		return validacion;
	}

}