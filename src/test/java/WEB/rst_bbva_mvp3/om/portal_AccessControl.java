package WEB.rst_bbva_mvp3.om;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class portal_AccessControl {

	WebDriver driver;
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	String url = "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	String url2 = "https://10.182.92.88:8443/AccessControl/pages/login_form.jsf";
	// *[@id="i0116"] //input[@id='i0116'] //*[@id=\"i0116\"]
	// * Localizadores Inicio de sesion Inventarios Espacios
	// ******************************************************************************************/
	By campouser = By.xpath("//input[@id='email']");
	By campopassword = By.xpath("//input[@id='password']");
	By LogginButton = By.xpath("//button[@id='login']//*[name()='svg']//*[name()='path' and contains(@fill,'#fff')]");
	By BtnLogginInterno = By.xpath("");
	By campoingresarcorreo = By.xpath("//input[@id='i0116']");
	By BtnSiguiente = By.xpath("//input[@id='idSIButton9']");
	By campoingresarcontrasena = By.xpath("//input[@id='i0118']");
	By BtnIniciarsesion = By.xpath("//input[@id='idSIButton9']");
	By Liga = By.xpath("//*[@id=\"signInAnotherWay\"]");
	By Btnsolcodigo = By.xpath("//*[@id=\"idDiv_SAOTCS_Proofs\"]/div[5]/div/div/div[2]");
	By campocodigo = By.xpath("//*[@id=\"idTxtBx_SAOTCC_OTC\"]");
	By Btncomprobar = By.xpath("//*[@id=\"idSIButton9\"]");

	// * Localizadores Menu Principal Portal Aplicaciones
	// ******************************************************************************************/
	By BtnSistemadeMonitoreo = By.xpath("//a[normalize-space()='SISTEMA DE MONITOREO DE TIENDAS']");
	By BtnTransaccioneselectronicasoperacion = By.xpath("//a[normalize-space()='Transacciones Electrónicas Operacion']");
	By BtnRuteoTDC = By.xpath("//a[normalize-space()='- Ruteo TDC']");
	By BtnListSite = By.xpath("//div[@id='formRuteoTDC:cmbSite']//span[@class='ui-icon ui-icon-triangle-1-s ui-c']");
	By BtnListSiteMty = By.xpath("//li[@id='formRuteoTDC:cmbSite_0']");
	By BtnListSiteQro = By.xpath("//li[@id='formRuteoTDC:cmbSite_1']");
	By BtnOperacion = By.xpath("//div[@id='formRuteoTDC:cmbOperacion']//span[@class='ui-icon ui-icon-triangle-1-s ui-c']");
	By BtnOperacionCompras = By.xpath("//li[@id='formRuteoTDC:cmbOperacion_0']");
	By BtnOperacionComprasRCT = By.xpath("//li[@id='formRuteoTDC:cmbOperacion_1']");
	By BtnOperacionComprasRST = By.xpath("//li[@id='formRuteoTDC:cmbOperacion_2']");
	By BtnBanco = By.xpath("//label[@id='formRuteoTDC:cmbBanco_label']");
	By BtnBbva = By.xpath("//li[@id='formRuteoTDC:cmbBanco_1']");
	By BtnSwitch = By.xpath("//div[@id='formRuteoTDC:cmbSwitch']//span[@class='ui-icon ui-icon-triangle-1-s ui-c']");
	By BtnSwitchDefault = By.xpath("//li[@id='formRuteoTDC:cmbSwitch_1']");
	By BtnSwitchBbva = By.xpath("//li[@id='formRuteoTDC:cmbSwitch_2']");
	By BtnActualizar = By.xpath("//a[@id='formRuteoTDC:btnActualizar']");
	By BtnL8 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[8]/td[1]/div[1]/div[2]/span[1]");
	By plaza8 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[8]/td[3]");
	By BtnActualizarSwitch = By.xpath("//a[@id='formRuteoTDC:j_idt550']");
	By BtnCancelarSwitch = By.xpath("//a[@id='formRuteoTDC:j_idt551']");
	
	//Se utiliza cuando se encuentre con la Operacion COMPRAS
	By fila18 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[18]/td[1]/div[1]/div[2]/span[1]");
	By plaza18 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[18]/td[3]/label[1]");
	By switch18 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[18]/td[4]");
	By fecha18 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[18]/td[5]/label[1]");

	//Se utiliza cuando se encuentre con la Operacion RST
	By fila31 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[31]/td[1]/div[1]/div[2]/span[1]");
	By plaza31 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[31]/td[3]/label[1]");
	By switch31 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[31]/td[4]");
	By fecha31 = By.xpath("/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/div[3]/table[1]/tbody[1]/tr[31]/td[5]/label[1]");
	
	// Constructor
	// ******************************************************************************************/

	public portal_AccessControl(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/*
	 * METODOS
	 ******************************************************************************************
	 * Método para obtener url de Portal de Access Control
	 * @return url Regresa la url del portal
	 * @author María Duenas
	 */

	public String getUrl() {
		return url;
	}

	/**
	 * Método para intentar iniciar sesión
	 * @param dato  Usuario
	 * @param dato2 Contrasena
	 * @author Melba Solis
	 * @date 07/04/2025 	 */
	public boolean LoginPortal(String dato, String dato2) {
		Integer cont = 0; // Contador para controlar cuántos intentos se han hecho

		while (cont < 4) { // Se intentará el login hasta 4 veces
			cont += 1; // Incrementa el contador para hacer un seguimiento del número de intentos

			loggin(dato, dato2); // Llama al método loggin(dato, dato2) para intentar realizar el login con los
									// datos proporcionados
			try {
				u.getDriver().findElement(campouser); // Busca el campo de usuario usando el identificador 'campouser'
				u.hardWait(3); // Espera adicional para asegurarse de que la página haya cargado completamente
				testCase.addScreenShotCurrentStep("fin"); // Toma una captura de pantalla al finalizar el proceso de
															// login
				return true; // Si se encuentra el campo, el login fue exitoso
			} catch (Exception e) {
				// Si el campo no se encuentra, el login ha fallado, pero se sigue intentando
				if (cont < 4) { // Si no se ha alcanzado el máximo de intentos
					u.getDriver().navigate().to(getUrl()); // Navega de nuevo a la URL base
				}
			}
		}

		return false;
	}

	/**
	 * Método para ingresar las credenciales para iniciar sesión
	 * @param dato  Usuario
	 * @param dato2 Contrasena
	 * @author Melba Solis
	 * @date 07/04/2025	 */
	public void loggin(String dato, String dato2) {

		u.highLight(campouser); // marcar recuadro en rojo
		u.clear(campouser); // limpiar campo
		u.sendKeys(campouser, dato); // ingresar usuario

		u.highLight(campopassword); // marcar recuadro en rojo
		u.clear(campopassword); // limpiar campo
		u.sendKeys(campopassword, dato2); // ingresar contrasena

		u.highLight(LogginButton); // marcar recuadro en rojo
		testCase.addScreenShotCurrentStep(u, "inputs");
		u.click(LogginButton); // clic boton
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "fin");

	}

	/**
	 * Método para autenticarse e iniciar sesión correctamente
	 * @param dato  correo
	 * @param dato2 Contrasena
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean autenticar(String datocorreo, String dato2) {
		try {
			u.hardWait(5);
			// Verifica si el campo de correo está presente en la página
			if (u.getDriver().findElement(campoingresarcorreo) != null) {
				u.highLight(campoingresarcorreo); // Resalta el campo de correo
				u.clear(campoingresarcorreo); // Limpiamos el campo
				u.sendKeys(campoingresarcorreo, datocorreo); // Ingresamos el correo
				u.waitForLoadPage(); // Esperamos que la página cargue
				u.highLight(BtnSiguiente); // Resaltamos el botón "Siguiente"
				testCase.addScreenShotCurrentStep(u, "inputs");
				u.click(BtnSiguiente); // Hacemos clic en el botón Siguiente
				u.waitForLoadPage(); // Esperamos que la página cargue
			} else {
				System.out.println("El campo de correo no se encuentra en la página.");
				return false; // Retorna false si el campo de correo no está presente
			}

			// Verifica si el campo de contrasena está presente en la página
			if (u.getDriver().findElement(campoingresarcontrasena) != null) {
				u.highLight(campoingresarcontrasena); // Resalta el campo de contrasena
				u.clear(campoingresarcontrasena); // Limpiamos el campo
				u.sendKeys(campoingresarcontrasena, dato2); // Ingresamos la contrasena
				u.highLight(BtnIniciarsesion); // Resaltamos el botón "Iniciar sesión"
				testCase.addScreenShotCurrentStep(u, "inputs");
				u.click(BtnIniciarsesion); // Hacemos clic en el botón Iniciar sesión
				u.waitForLoadPage(); // Esperamos que la página cargue
			} else {
				System.out.println("El campo de contrasena no se encuentra en la página.");
				return false; // Retorna false si el campo de contrasena no está presente
			}

			// Validar y hacer clic en la Liga
			u.highLight(Liga); // Resalta la liga
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(Liga); // Hacemos clic en la liga
			u.waitForLoadPage(); // Esperamos que la página cargue

			// Validar y hacer clic en el botón para solicitar el código
			u.highLight(Btnsolcodigo); // Resalta el botón de solicitar código
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(Btnsolcodigo); // Hacemos clic en el botón
			u.hardWait(5); // Esperamos 5 segundos
			u.waitForLoadPage(); // Esperamos que la página cargue

			// Validar y hacer clic en el botón para comprobar el código
			u.highLight(Btncomprobar); // Resalta el botón de comprobar
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(Btncomprobar); // Hacemos clic en el botón
			u.waitForLoadPage(); // Esperamos que la página cargue

			return true; // Si todo salió bien, retornamos true

		} catch (Exception e) {
			System.out.println("Ocurrió un error durante el proceso de autenticación: " + e.getMessage());
			return false; // Si ocurre un error, retornamos false
		}
	}

	/**
	 * Método para ingresar al menu de SISTEMA DE MONITOREO
	 * @param BtnSistemadeMonitoreo resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SistemadeMonitoreo() {
		try {
			// Resaltar el botón "SistemadeMonitoreo" con un borde rojo
			u.highLight(BtnSistemadeMonitoreo);
			testCase.addScreenShotCurrentStep(u, "inputs");

			// Verificar si el botón está presente antes de hacer clic
			if (u.getDriver().findElement(BtnSistemadeMonitoreo) != null) {
				u.click(BtnSistemadeMonitoreo); // Hacer clic en el botón
				u.waitForLoadPage(); // Esperar que la página cargue
				return true; // Si todo salió bien, retornar true
			} else {
				System.out.println("El botón 'SistemadeMonitoreo' no se encuentra en la página.");
				return false; // Si el botón no está presente, retornar false
			}

		} catch (Exception e) {
			System.out.println("Ocurrió un error al interactuar con el botón 'SistemadeMonitoreo': " + e.getMessage());
			return false; // Si ocurre algún error, retornar false
		}
	}

	/**
	 * Método para desplegar el submenu de Transacciones Electronicas Operación e ingresar a Ruteo TDC
	 * @param BtnTransaccioneselectronicasoperacion resalta e interactua con el boton 
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean TransaccionesElectronicas() {

		try {
			u.hardWait(3);
			// Verifica si el botón de Transacciones Electrónicas Operación está presente
			if (u.getDriver().findElement(BtnTransaccioneselectronicasoperacion) != null) {
				u.highLight(BtnTransaccioneselectronicasoperacion); // Resalta el botón
				Actions actions = new Actions(u.getDriver()); // Crea una instancia de Actions
				actions.moveToElement(u.getDriver().findElement(BtnTransaccioneselectronicasoperacion)).perform(); // Mueve

				// Esperamos un poco para que el submenu se despliegue
				u.hardWait(2); // Espera de 2 segundos (ajustar si es necesario)

				// Verifica si el botón del submenu "Ruteo TDC" está presente
				if (u.getDriver().findElement(BtnRuteoTDC) != null) {
					u.highLight(BtnRuteoTDC); // Resalta el botón del submenu
					testCase.addScreenShotCurrentStep(u, "submenu");
					u.click(BtnRuteoTDC); // Hace clic en el botón del submenu
					u.waitForLoadPage(); // Esperamos que la página cargue
					return true; // Si todo salió bien, retornamos true
				} else {
					System.out.println("El botón 'Ruteo TDC' no se encuentra en el submenu.");
					return false; // Retorna false si el botón del submenu no está presente
				}
			} else {
				System.out.println("El botón 'Transacciones Electrónicas Operación' no se encuentra en la página.");
				return false; // Retorna false si el botón principal no está presente
			}
		} catch (Exception e) {
			System.out.println("Ocurrió un error al interactuar con el menú: " + e.getMessage());
			return false; // Si ocurre un error, retornamos false
		}
	}

	/**
	 * Método para desplegar el list de Site
	 * @param BtnListSite resalta e interactua con el boton con un clic 
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionSite() {
		try {
			u.highLight(BtnListSite); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnListSite); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Site la entidad de Queretaro
	 * @param BtnListSiteQro resalta e interactua con el boton con un clic 
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionSiteQRO() {

		try {
			u.highLight(BtnListSiteQro); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnListSiteQro); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Site la entidad de Monterrey
	 * @param BtnListSiteMty resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionSiteMTY() {

		try {
			u.highLight(BtnListSiteMty); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnListSiteMty); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para desplegar el list de Operación
	 * @param BtnOperacion resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionarOperacion() {

		try {
			u.highLight(BtnOperacion); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnOperacion); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Operación Compras
	 * @param BtnOperacionCompras resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionarOperacionCompras() {

		try {
			u.highLight(BtnOperacionCompras); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnOperacionCompras); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Operación RCT
	 * @param BtnOperacionComprasRCT resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionarOperacionComprasRCT() {

		try {
			u.highLight(BtnOperacionComprasRCT); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnOperacionComprasRCT); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Operación RST
	 * @param BtnOperacionComprasRST resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */ 
	public boolean SeleccionarOperacionComprasRST() {

		try {
			u.highLight(BtnOperacionComprasRST); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnOperacionComprasRST); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para desplegar el list de Banco
	 * @param BtnBanco resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionarBanco() {

		try {
			u.highLight(BtnBanco); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnBanco); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Banco BBVA
	 * @param BtnBbva resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */ 
	public boolean SeleccionarBancoBbva() {

		try {
			u.highLight(BtnBbva); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnBbva); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para desplegar el list de Switch
	 * @param BtnSwitch resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */
	public boolean SeleccionarelSwitch() {

		try {
			u.hardWait(3);
			u.highLight(BtnSwitch); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnSwitch); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Switch Default
	 * @param BtnSwitchDefault resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */ 
	public boolean SeleccionarelSwitchDefault() {

		try {
			u.highLight(BtnSwitchDefault); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnSwitchDefault); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para seleccionar del list de Switch BBVA
	 * @param BtnSwitchBbva resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */ 
	public boolean SeleccionarelSwitchBbva() {

		try {
			u.highLight(BtnSwitchBbva); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnSwitchBbva); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para dar clic en el boton actualizar y realizar los cambios realizados en switch (antes de confirmar el cambio)
	 * @param BtnActualizar resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025
	 */ 
	public boolean Actualizar() {

		try {
			u.highLight(BtnActualizar); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnActualizar); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para dar clic en el boton actualizar y confirmar los cambios realizados en switch y se ejecute el cambio
	 * @param BtnActualizarSwitch resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025 */
	public boolean ConfirmarSwitch() {

		try {
			u.highLight(BtnActualizarSwitch); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnActualizarSwitch); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para dar clic en el boton cancelar y no se realicen los cambios realizados en switch
	 * @param BtnCancelarSwitch resalta e interactua con el boton con un clic
	 * @author Melba Solis
	 * @date 07/04/2025 */
	public boolean CancelarSwitch() {

		try {
			u.highLight(BtnCancelarSwitch); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnCancelarSwitch); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para dar clic y seleccionar el checbox de la fila 8 con el site QUERETARO
	 * @param BtnQROL16 interactua con el boton checkbox de la fila 16 con un clic 
	 * @author Melba Solis
	 * @date 07/04/2025 */
	public boolean SeleccionarFilaL8() {

		try {
			u.highLight(BtnL8); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(BtnL8); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método para remarcar y extraer el dato de plaza de la fila 16 con el site QUERETARO
	 * @param BtnCancelarSwitch resalta e interactua con el campo/celda extrayendo el dato que contiene
	 * @author Melba Solis
	 * @date 07/04/2025 */
	public boolean SeleccionarPlaza8() {

	    try {
	        u.highLight(plaza8); // Marcar recuadro en rojo
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla

	        // Obtener el texto de la celda de plaza16
	        this.plazaText = u.getText(plaza8); // Guardar el texto de plaza16 en la variable de instancia

	        // Agregar el texto extraído a la evidencia (suponiendo que 'addTextEvidence' o similar esté disponible)
	        testCase.addTextEvidenceCurrentStep("La plaza es: " + this.plazaText); // Usar el método adecuado para agregar texto a la evidencia

	        // Si todo está bien, devolvemos true
	        return true;

	    } catch (Exception e) {
	        // Si ocurre algún error durante la ejecución, devolvemos false
	        e.printStackTrace();
	        return false;
	    }
	}
	
	private String plazaText;
	
	/** Método para remarcar y dar clic al checkbox de la fila 18 
	 * @param fila18 interactua con el boton checkbox de la fila 18 con un clic
	 * @author Melba Solis
	 * @date 07/04/2025 */
	public boolean SeleccionarFila18() {

		try {
			u.highLight(fila18); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(fila18); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}

		
	/** Método para remarcar y dar clic al checkbox de la fila 31 
	 * @param fila13 interactua con el boton checkbox de la fila 31 con un clic
	 * @author Melba Solis
	 * @date 07/04/2025 */
	public boolean SeleccionarFila31() {

		try {
			u.highLight(fila31); // marcar recuadro en rojo
			testCase.addScreenShotCurrentStep(u, "inputs");
			u.click(fila31); // clic a la Liga
			u.waitForLoadPage(); // esperar a que se cargue la página

			// Si la página se carga correctamente, podemos retornar true
			return true;
		} catch (Exception e) {
			// Si ocurre algún error durante la ejecución, devolvemos false
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Método para remarcar y comparar el dato de plaza de la fila 8 antes de la actualización y fila 18; en donde se recorre la fila 16 por los cambios realizados con el site QUERETARO
	 * @param plaza18 interactua con el campo/celda de la fila 18 extrayendo el dato que contiene 
	 * @param plazaText compara el dato extraido en metodo anterior con el plaza18Text
	 * @param plaza18Text compara el dato extraido con el plazaText
	 * @author Melba Solis 
	 * @date 07/04/2025 */
	public boolean compararPlaza() {
	    try {
	        // Resaltar la celda de Plaza 18
	        u.highLight(plaza18); // Marcar recuadro en rojo para Plaza 18
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla para Plaza 18

	        // Obtener el texto de la celda Plaza 18
	        String plaza18Text = u.getText(plaza18); // Extraer el texto de la celda plaza18

	        // Comparar los textos de Plaza 16 (plazaText) y Plaza 18 (plaza18Text)
	        if (this.plazaText.equals(plaza18Text)) {  // Comparar con el valor guardado de plaza16
	            // Si los textos son iguales
	            testCase.addTextEvidenceCurrentStep("Las plazas son iguales. Plaza inicial: " + this.plazaText + " y Plaza actualizada: " + plaza18Text);
	            return true; // Las plazas son iguales
	        } else {
	            // Si los textos son diferentes
	            testCase.addTextEvidenceCurrentStep("Las plazas son diferentes. Plaza inicial: " + this.plazaText + " y Plaza actualizada: " + plaza18Text);
	            return false; // Las plazas son diferentes
	        }

	    } catch (Exception e) {
	        // Si ocurre algún error durante la ejecución, devolver false
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Método para remarcar y extraer los datos de switch y fecha de la fila 18 con el site QUERETARO y confirmar el cambio a FEMCO_BBVA
	 * @param switch18 resalta e interactua con el campo/celda extrayendo el dato que contiene y compara con el dato esperado: FEMCO_BBVA
	 * @param fecha18 resalta e interactua con el campo/celda extrayendo el dato que contiene y compara con el dato de fecha actual 
	 * @author Melba Solis 
	 * @date 07/04/2025 */
	public boolean validarCambiobbva() {
	    try {
	        u.highLight(switch18); // Marcar recuadro en rojo para Switch 18
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla para Switch 18

	        // Obtener el texto de la celda switch18
	        String switch18Text = u.getText(switch18); // Extraer el texto de la celda switch18
	        
	        if (switch18Text.equals("FEMCO_BBVA")) { // Verificar si el texto de switch18 es igual a "BBVA"
	            testCase.addTextEvidenceCurrentStep("El valor de switch es correcto: " + switch18Text);
	        } else {
	            testCase.addTextEvidenceCurrentStep("Error: El valor de switch es incorrecto. Esperado 'FEMCO_BBVA', pero se encontró: " + switch18Text);
	            return false; // Fallar si no es "BBVA"
	        }

	        u.highLight(fecha18); // Marcar recuadro en rojo para Fecha 18
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla para Fecha 18

	        // Obtener el texto de la celda fecha18
	        String fecha18Text = u.getText(fecha18); // Extraer el texto de la celda fecha18

	        // Obtener la fecha actual en formato (puedes cambiar el formato si lo prefieres)
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        String fechaActual = sdf.format(new Date()); // Fecha actual en el formato dd/MM/yyyy

	        // Verificar si la fecha de la celda es igual a la fecha actual
	        if (fecha18Text.equals(fechaActual)) {
	            testCase.addTextEvidenceCurrentStep("La fecha de fecha es correcta: " + fecha18Text);
	        } else {
	            testCase.addTextEvidenceCurrentStep("Error: La fecha de fecha es incorrecta. Esperado '" + fechaActual + "', pero se encontró: " + fecha18Text);
	            return false; // Fallar si la fecha no es igual a la fecha actual
	        }

	        	return true;

	    } catch (Exception e) {
	        // Si ocurre algún error durante la ejecución, devolver false
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	/**
	 * Método para remarcar y comparar el dato de plaza de la fila 8 antes de la actualización y fila 31; en donde se recorre la fila 31 por los cambios realizados con el site QUERETARO
	 * @param plaza18 interactua con el campo/celda de la fila 31 extrayendo el dato que contiene 
	 * @param plazaText compara el dato extraido en metodo anterior con el plaza18Text
	 * @param plaza18Text compara el dato extraido con el plazaText
	 * @author Melba Solis 
	 * @date 07/04/2025 */
	public boolean compararPlaza31() {
	    try {
	        // Resaltar la celda de Plaza 31
	        u.highLight(plaza31); // Marcar recuadro en rojo para Plaza 31
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla para Plaza 31

	        // Obtener el texto de la celda Plaza 18
	        String plaza31Text = u.getText(plaza31); // Extraer el texto de la celda plaza18

	        // Comparar los textos de Plaza 16 (plazaText) y Plaza 18 (plaza18Text)
	        if (this.plazaText.equals(plaza31Text)) {  // Comparar con el valor guardado de plaza16
	            // Si los textos son iguales
	            testCase.addTextEvidenceCurrentStep("Las plazas son iguales. Plaza inicial: " + this.plazaText + " y Plaza actualizada: " + plaza31Text);
	            return true; // Las plazas son iguales
	        } else {
	            // Si los textos son diferentes
	            testCase.addTextEvidenceCurrentStep("Las plazas son diferentes. Plaza inicial: " + this.plazaText + " y Plaza actualizada: " + plaza31Text);
	            return false; // Las plazas son diferentes
	        }

	    } catch (Exception e) {
	        // Si ocurre algún error durante la ejecución, devolver false
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Método para remarcar y extraer los datos de switch y fecha de la fila 31 con el site QUERETARO y confirmar el cambio a FEMCO_BBVA
	 * @param switch31 resalta e interactua con el campo/celda extrayendo el dato que contiene y compara con el dato esperado: FEMCO_BBVA
	 * @param fecha31 resalta e interactua con el campo/celda extrayendo el dato que contiene y compara con el dato de fecha actual 
	 * @author Melba Solis 
	 * @date 07/04/2025 */
	public boolean validarCambiobbva31() {
	    try {
	        u.highLight(switch31); // Marcar recuadro en rojo para Switch 31
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla para Switch 31

	        // Obtener el texto de la celda switch18
	        String switch31Text = u.getText(switch31); // Extraer el texto de la celda switch31
	        
	        if (switch31Text.equals("FEMCO_BBVA")) { // Verificar si el texto de switch31 es igual a "BBVA"
	            testCase.addTextEvidenceCurrentStep("El valor de switch es correcto: " + switch31Text);
	        } else {
	            testCase.addTextEvidenceCurrentStep("Error: El valor de switch es incorrecto. Esperado 'FEMCO_BBVA', pero se encontró: " + switch31Text);
	            return false; // Fallar si no es "BBVA"
	        }

	        u.highLight(fecha31); // Marcar recuadro en rojo para Fecha 18
	        testCase.addScreenShotCurrentStep(u, "inputs"); // Agregar captura de pantalla para Fecha 18

	        // Obtener el texto de la celda fecha31
	        String fecha31Text = u.getText(fecha31); // Extraer el texto de la celda fecha18

	        // Obtener la fecha actual en formato (puedes cambiar el formato si lo prefieres)
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        String fechaActual = sdf.format(new Date()); // Fecha actual en el formato dd/MM/yyyy

	        // Verificar si la fecha de la celda es igual a la fecha actual
	        if (fecha31Text.equals(fechaActual)) {
	            testCase.addTextEvidenceCurrentStep("La fecha de fecha es correcta: " + fecha31Text);
	        } else {
	            testCase.addTextEvidenceCurrentStep("Error: La fecha de fecha es incorrecta. Esperado '" + fechaActual + "', pero se encontró: " + fecha31Text);
	            return false; // Fallar si la fecha no es igual a la fecha actual
	        }

	        	return true;

	    } catch (Exception e) {
	        // Si ocurre algún error durante la ejecución, devolver false
	        e.printStackTrace();
	        return false;
	    }
	}


}

