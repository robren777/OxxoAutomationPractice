package WEB.AccessControl.om;

import org.openqa.selenium.By;
import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class PortalLoginPage {

	/**
	 * Se crean todos los metodos que se pueden utilizar para el inicio de sesion y
	 * que se visualicen los modulos, tambien se tiene el metodo para cambiar la
	 * contraseña, estos metodos son desarrollados por una herramienta(IA)
	 * 
	 * @author Emmanuel Gomez
	 * @return Metodos con clases propias
	 * @date 02/Diciembre/2025
	 */

	private final SeleniumUtil u;
	private final TestCase testCase;

	private final By loginForm = By.id("email");
	private final By cambiarContrasenaBtn = By.xpath("//a[contains(text(),'Cambiar contraseña')]");
	private final By usuarioInput2 = By.xpath("//input[@formcontrolname='username']");

	private final By contrasenaInput1 = By.xpath("//input[@formcontrolname='currentPassword']");
	private final By nuevaContrasenaInput = By.xpath("//input[@formcontrolname='newPassword']");
	private final By confirmacionContra = By.xpath("//input[@formcontrolname='passwordConfirmation']");
	private final By btnGuardarCC = By.xpath("//button[contains(normalize-space(), 'Aceptar')]");
	private final By CerrarModal = By.xpath("//button[@aria-label='Close']");

	private final By btnIngresar = By.id("login");
	private final By usuarioInput = By.id("email");
	private final By contrasenaInput = By.id("password");

	public PortalLoginPage(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Se crea el metodo para verificar que habra la pagina del navegador y le toma una screenshot	 
	 *
	 * 
	 * @author Emmanuel Gomez
	 * @date 02/Diciembre/2025
	 */
	public void abrirPagina(String url) throws Exception {
		u.get(url);
		testCase.addScreenShotCurrentStep(u, "pagina_ingresada");
	}

	/**
	 * Se crea el metodo para verificar que el login haya cargado, asignandole un
	 * tiempo determinado
	 * 
	 * @author Emmanuel Gomez
	 * @return regresa un true o false
	 * @date 02/Diciembre/2025
	 */
	public boolean verificarLoginCargado() {
		try {
			u.waitWebElement(loginForm, 50, 30); // Espera a que el formulario aparezca
			testCase.addScreenShotCurrentStep(u, "login_cargado");
			return true; // Login cargado correctamente
		} catch (Exception e) {
			return false; // No se encontró el formulario
		}
	}

	/**
	 * Se crea el metodo para cambiar la contraseña y volver a iniciar la sesion con
	 * la nueva contraseña
	 * 
	 * @author Emmanuel Gomez
	 * @param Se pasan los valores nuevaContrasena, usuario, contrasenaActual,
	 *           confirmacion
	 * @date 12/Diciembre/2025
	 */

	public void cambiarContrasena(String nuevaContrasena, String usuario, String contrasenaActual, String confirmacion)
			throws Exception {
		u.click(cambiarContrasenaBtn);
		u.sendKeys(usuarioInput2, usuario);
		u.sendKeys(contrasenaInput1, contrasenaActual);
		u.sendKeys(nuevaContrasenaInput, nuevaContrasena);
		u.sendKeys(confirmacionContra, confirmacion);
		u.click(btnGuardarCC);
		u.click(CerrarModal);
		;
		testCase.addScreenShotCurrentStep(u, "cambiar_contrasena");
	}

	/**
	 * Se crea el metodo para ingresar el usuario y la contraseña para poder iniciar
	 * sesion en el Acess Control
	 * 
	 * @author Emmanuel Gomez
	 * @param Se pasan los valores usuario y contraseña
	 * @return regresa un true o false
	 * @date 12/Diciembre/2025
	 */

	public boolean ingresarCredenciales(String usuario, String password) {
		try {
			u.sendKeys(usuarioInput, usuario);
			u.sendKeys(contrasenaInput, password);
			u.click(btnIngresar);

			u.waitWebElement(usuarioInput, 50, 30);
			testCase.addScreenShotCurrentStep(u, "login_exitoso");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}