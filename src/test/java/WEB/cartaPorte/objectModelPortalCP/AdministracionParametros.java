package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdministracionParametros {
	SeleniumUtil u;
	TestCase testCase;

	By paginadoSiguiente = By.xpath("//div[@class='mat-paginator-range-actions']/button[3]/span[1]");
	By botonAlerta = By.xpath("//div[@class='alert-footer mt-3 mb-3']/button[@class='btn warn']");
	By alerta = By.xpath("//div[@id='toast-container']/div");

	public AdministracionParametros(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Metodo para buscar la variable esperada en la pantalla de Parametros
	 * generales, se envia el numero de renglon y la pagina para ir comparando segun
	 * el nombre de la Variable y poder seleccionar el elemento de edicion
	 * 
	 * @param nombreVariable-----------nombre de la Variable que se espera encontrar
	 * @param base---------------------Base   Execution actual, para poder agregar
	 *                                        pasos a la evidencia en caso de
	 *                                        encontrar la variable buscada
	 * @param captura------------------True   si se requiere añadir al archivo de
	 *                                        evidencia la accion o false para no
	 * @param renglon------------------número de renglón en que va la busqueda.
	 * @param pagina-------------------numero de pagina a seleccionar
	 * 
	 * @return String se regresa una concatenacion del renglon y la pagina para
	 *         saber desde donde continuar la siguiente busqueda en caso de no ser
	 *         iguales las variables comparadas
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String encontrarRenglonYEditarVariableRenglon(String nombreVariable, BaseExecution base, boolean captura,
			String renglon, String pagina) {
		boolean continuar = false;
		int contador = Integer.parseInt(renglon);
		int pag = Integer.parseInt(pagina);
		int c = 1;
		if (pag > 1) {
			do {
				u.getDriver().findElement(paginadoSiguiente).click();
				c++;
			} while (pag > c);
		}
		do {
			By variableTabla = By.xpath("//table/tbody/tr[" + contador + "]/td[1]");
			u.highLight(variableTabla);
			String nombreActualVariable = u.getDriver().findElement(variableTabla).getText();
			if (nombreActualVariable.compareTo(nombreVariable) == 0) {
				testCase.addScreenShotCurrentStep("VariableEncontrada");
				if (captura) {
					base.addStep("Seleccionar Editar");
				}
				u.unhighLight(variableTabla);
				renglon = String.valueOf(contador);
				By registroEditar = By.xpath("//table/tbody/tr[" + contador + "]/td[4]/button");
				u.highLight(registroEditar);
				if (captura) {
					testCase.addScreenShotCurrentStep("SeleccionVEditar");
				}
				u.unhighLight(registroEditar);
				u.hardWait(2);
				do {
					u.getDriver().findElement(registroEditar).click();
					continuar = false;
					try {
						u.hardWait(2);
						if (u.getDriver().findElement(registroEditar).isDisplayed()) {
							continuar = false;
						}
					} catch (Exception e) {
						continuar = true;
					}
				} while (!continuar);
				// u.hardWait(5);
				continuar = true;
			} else {
				u.unhighLight(variableTabla);
				contador++;
				if (contador > 10) {
					u.getDriver().findElement(paginadoSiguiente).click();
					c++;
					contador = 1;
					u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				}
			}
			if (contador == 5) {
				buscarYQuitarAlerta();
			}

		} while (!continuar);
		System.out.print(" Esta en el renglon: " + renglon + " Y en la pagina: " + c);
		return renglon + "-" + String.valueOf(c);
	}

	/**
	 * Metodo para seleccionar el elemento del edicion del registro que coincide con
	 * la variable buscada, realiza esta acción hasta que se ingresa a la pantalla
	 * de edición
	 * 
	 * @param nombreVariable-----------nombre de la Variable que se espera encontrar
	 * @param base---------------------Base   Execution actual, para poder agregar
	 *                                        pasos a la evidencia en caso de
	 *                                        encontrar la variable buscada
	 * @param captura------------------True   si se requiere añadir al archivo de
	 *                                        evidencia la accion o false para no
	 * 
	 * @return se regresa un true si la acción fue exitosa y false si no
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarYEditarVariable(String nombreVariable, BaseExecution base, boolean captura) {
		boolean continuar = false;
		int contador = 1;
		do {
			By variableTabla = By.xpath("//table/tbody/tr[" + contador + "]/td[1]");
			u.highLight(variableTabla);
			String nombreActualVariable = u.getDriver().findElement(variableTabla).getText();
			if (nombreActualVariable.compareTo(nombreVariable) == 0) {
				testCase.addScreenShotCurrentStep("VariableEncontrada");
				if (captura) {
					base.addStep("Seleccionar Editar");
				}
				u.unhighLight(variableTabla);
				By registroEditar = By.xpath("//table/tbody/tr[" + contador + "]/td[4]/button");
				u.highLight(registroEditar);
				if (captura) {
					testCase.addScreenShotCurrentStep("SeleccionVEditar");
				}
				u.unhighLight(registroEditar);
				u.hardWait(2);
				do {
					u.getDriver().findElement(registroEditar).click();
					continuar = false;
					try {
						u.hardWait(2);
						if (u.getDriver().findElement(registroEditar).isDisplayed()) {
							continuar = false;
						}
					} catch (Exception e) {
						continuar = true;
					}
				} while (!continuar);
				// u.hardWait(5);
				continuar = true;
			} else {
				u.unhighLight(variableTabla);
				contador++;
				if (contador > 10) {
					u.getDriver().findElement(paginadoSiguiente).click();
					contador = 1;
					u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				}
			}
			if (contador == 5) {
				buscarYQuitarAlerta();
			}
		} while (!continuar);
		return continuar;
	}

	/**
	 * Metodo para continuar el test en caso de que se muestre la pantalla de alerta
	 * sobre los datos cargados de la base de datos
	 * 
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void buscarYQuitarAlerta() {
		boolean continuar = false;
		int intentos = 1;
		do {
			try {
				if (u.getDriver().findElement(botonAlerta).isDisplayed()) {
					u.getDriver().findElement(botonAlerta).click();
				}
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
				continuar = false;
			} catch (org.openqa.selenium.NoSuchElementException NOs) {
				continuar = false;
			}
			intentos++;
			if (intentos > 3) {
				continuar = true;
			}
		} while (!continuar);
	}
	/**
	 * Metodo para obtener el contenido de texto de un elemento web
	 * 
	 * @param nombreCampo-----------Campo del que se espera obtener el texto
	 * @param captura---------------True si se requiere añadir al archivo de
	 *                                        evidencia la accion o false para no
	 * 
	 * @return String que contiene el texto del elemento web enviado
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerTexto(String nombreCampo, boolean captura) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
		By elemento = null;
		try {
			switch (nombreCampo) {
			case "AlertaGuardado":
				wait.until(ExpectedConditions.visibilityOfElementLocated(alerta));
				elemento = alerta;
				break;
			}
			if (captura) {
				testCase.addScreenShotCurrentStep("AlertaVariable");
			}
			return u.getDriver().findElement(elemento).getText();
		} catch (Exception e) {

			return "";
		}
	}
	/**
	 * Metodo para designar los datos según el servicio de timbrado a probar (Konesh o Diverza)
	 * 
	 * @param nombreVariable--------El nombre d ela variable actual, para poder designarle un valor segun sea el caso
	 * @param pac-------------------Se espera que sea K para Konesh y D para Diverza
	 * 
	 * @return String con el valor que se le debe asignar a la variable enviada
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerValorVariable(String nombreVariable, String pac) {
		String valor = "";
		String nombrePAC = "";
		String RFCPAC = "";
		String CPPAC = "";
		if (pac.compareTo("K") == 0) {
			nombrePAC = "ESCUELA KEMPER URGATE";
			RFCPAC = "EKU9003173C9";
			CPPAC = "26015";

		} else {
			if (pac.compareTo("D") == 0) {
				nombrePAC = "CADENA COMERCIAL OXXO";
				RFCPAC = "CCO8605231N4";
				CPPAC = "64480";

			} else {
				valor = "ERROR";
			}
		}
		if (nombreVariable.contains("RFC") || nombreVariable.contains("Rfc")) {
			valor = RFCPAC;
		} else {
			if (nombreVariable.contains("Nombre")) {
				valor = nombrePAC;
			} else {
				if (nombreVariable.contains("CP") || nombreVariable.contains("Domicilio")) {
					valor = CPPAC;
				} else {
					valor = "ERROR";
				}
			}
		}

		return valor;
	}
}
