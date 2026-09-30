package Mobile.preinventariosinteligentes.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class Menus {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * Constructor de la clase Menus
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public Menus(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/*
	 * Localizadores
	 * */
	private By btnControl = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[2]");
	private By btnPreInvInt = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[5]");

	private By btnConteosInt = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_register_pre_inventory");

	private By btnConteosProp = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_own_counts");
	private By btnNuevaPlantilla = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cd_products_template");
	private By btnIniciarConteo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cd_init_own_flow");

	private By btnAsigRoles = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_pre_inventory_roles_assignment");
	private By btnRolesInt = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_roles_inteligent");
	private By btnPlantillaTda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_store_template");


	private By btnRolesPro = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_roles_own");

	private By btnReportes = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btn_pre_inventory_report");


	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param e Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForVisibility(WebElement e, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(e));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}

	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForVisibility(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}

	/**
	 * Espera a que un elemento se oculte dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForHidden(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento todavía es visible después del tiempo de espera: " + locator, ex);
		}
	}

	/**
	 * Metodo para tomar capturas de pantalla
	 * @author Marcos Morales
	 * @param screenShot Bandera true/false
	 */
	public void takeScreenshot(boolean screenShot) {
		if (screenShot) {
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/**
	 * Hacer click en el Botón Control
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnControl(boolean screenShot) {
		waitForVisibility(btnControl, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnControl);
	}

	/**
	 * Hacer click en el Botón Preinventarios Inteligentes
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnPreInvInt(boolean screenShot) {
		waitForVisibility(btnPreInvInt, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnPreInvInt);
	}

	/**
	 * Hacer click en el Botón Conteos Inteligentes
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnConteosInt(boolean screenShot) {
		waitForVisibility(btnConteosInt, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnConteosInt);
	}

	/**
	 * Hacer click en el Botón Conteos Propios
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnConteosProp(boolean screenShot) {
		waitForVisibility(btnConteosProp, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnConteosProp);
	}

	/**
	 * Hacer click en el Botón Nueva Plantilla
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnNuevaPlantilla(boolean screenShot) {
		waitForVisibility(btnNuevaPlantilla, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnNuevaPlantilla);
	}

	/**
	 * Hacer click en el Botón Iniciar Conteo
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnIniciarConteo(boolean screenShot) {
		waitForVisibility(btnIniciarConteo, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnIniciarConteo);
	}

	/**
	 * Hacer click en el Botón Asignación de Roles
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnAsigRoles(boolean screenShot) {
		waitForVisibility(btnAsigRoles, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnAsigRoles);
	}

	/**
	 * Hacer click en el Botón Reportes
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnReportes(boolean screenShot) {
		waitForVisibility(btnReportes, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnReportes);
	}

	/**
	 * Hacer click en el Botón Roles Inteligentes
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnRolesInt(boolean screenShot) {
		waitForVisibility(btnRolesInt, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnRolesInt);
	}

	/**
	 * Hacer click en el Botón Roles Propios
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnRolesPro(boolean screenShot) {
		waitForVisibility(btnRolesPro, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnRolesPro);
	}

	/**
	 * Hacer click en el Botón Plantilla de Tienda
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void clickBtnPlantillaTda(boolean screenShot) {
		waitForVisibility(btnPlantillaTda, Duration.ofSeconds(30));
		takeScreenshot(screenShot);
		au.click(btnPlantillaTda);
	}

	/**
	 * Simplifica el acceso en un solo paso para ingresar a Conteos Inteligentes
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void ingresarConteosInteligentes(boolean screenShot) {
		//Control
		clickBtnControl(screenShot);

		//Preinventarios Inteligentes
		clickBtnPreInvInt(screenShot);

		//Conteos Inteligentes
		clickBtnConteosInt(screenShot);
	}

	/**
	 * Simplifica el acceso en un solo paso para ingresar a Plantilla de Tienda
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void ingresarPlantillaTda(boolean screenShot) {
		//Control
		clickBtnControl(screenShot);

		//Preinventarios Inteligentes
		clickBtnPreInvInt(screenShot);

		//Asignacion de Roles
		clickBtnAsigRoles(screenShot);

		//Plantilla de Tienda
		clickBtnPlantillaTda(screenShot);
	}

	/**
	 * Simplifica el acceso en un solo paso para ingresar a Roles Inteligentes
	 * @author Lizbeth Hernandez
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void ingresarRolesInteligentes(boolean screenShot) {
		//Control
		clickBtnControl(screenShot);

		//Preinventarios Inteligentes
		clickBtnPreInvInt(screenShot);

		//Asignacion de Roles
		clickBtnAsigRoles(screenShot);

		//Roles Inteligentes
		clickBtnRolesInt(screenShot);
	}

	/**
	 * Simplifica el acceso en un solo paso para ingresar a Conteos Propios
	 * @author Marcos Morales
	 * @param screenShot true/false Define si toma o no captura de pantalla 
	 */
	public void ingresarConteosPropios(boolean screenShot) {
		//Control
		clickBtnControl(screenShot);

		//Preinventarios Inteligentes
		clickBtnPreInvInt(screenShot);

		//Conteos Propios
		clickBtnConteosProp(false);
	}
}
