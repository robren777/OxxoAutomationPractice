package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EditarConstante {
	SeleniumUtil u;
	TestCase testCase;
	
	By botonCancelar=By.xpath("//button/span[text()=' Cancelar ']");
	By botonGuardar=By.xpath("//button[text()=' GUARDAR ']");
	
	By alerta=By.xpath("//div[@class='ng-tns-c38-23 toast-message ng-star-inserted']");
	
	public EditarConstante(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	/**
	 * Metodo para limpiar la caja de texto con el valor actual d ela variable y posteriormente capturar el nuevo valor
	
	 * @param valor---------------- valor a capturar en casilla
	
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String limpiaryCapturarValor(String valor) {
		By casillaEditarValor=null;
		String guardarValor=null;
		int contador=1;
		boolean continuar=false;
		do {
			casillaEditarValor=By.xpath("//html/body/app-root/div/div[@class='container']/app-constante-register/form/div[2]/div[2]/input");
			u.highLight(casillaEditarValor);
			guardarValor=u.getDriver().findElement(casillaEditarValor).getAttribute("value");
			u.highLight(casillaEditarValor);
			contador++;
			if(guardarValor.compareTo("")!=0) {
				continuar=true;
			}else {
				if(contador>5) {
					return "";
				}
			}
		}while(!continuar);
		u.getDriver().findElement(casillaEditarValor).clear();
		u.getDriver().findElement(casillaEditarValor).sendKeys(valor);
		u.highLight(casillaEditarValor);
		return guardarValor;
	}
	/**
	 * Metodo para limpiar la caja de texto con el valor actual d ela variable y posteriormente capturar el nuevo valor
	
	 * @param valor---------------- valor a capturar en casilla
	
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String limpiaryCapturarValorRenglon(String valor) {
		By casillaEditarValor=null;
		String guardarValor=null;
		int contador=1;
		boolean continuar=false;
		do {
			casillaEditarValor=By.xpath("//html/body/app-root/div/div[@class='container']/app-constante-register/form/div[2]/div[2]/input");
			u.highLight(casillaEditarValor);
			guardarValor=u.getDriver().findElement(casillaEditarValor).getAttribute("value");
			u.highLight(casillaEditarValor);
			contador++;
			if(guardarValor.compareTo("")!=0) {
				continuar=true;
			}else {
				if(contador>5) {
					seleccionarElemento("botonCancelar", false);
					return "ERROR";
				}
			}
		}while(!continuar);
		u.getDriver().findElement(casillaEditarValor).clear();
		u.getDriver().findElement(casillaEditarValor).sendKeys(valor);
		u.highLight(casillaEditarValor);
		return guardarValor;
	}
	/**
	 * Metodo para encontrar un elemento esperado en la pantalla actual	 
	 * 
	 * @param nombreCampo----------------campo que se espera localizar en pantalla
	
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean encontrarElemento(String nombreCampo) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		try {
			switch(nombreCampo) {
			case "botonCancelar":
				if(u.getDriver().findElement(botonCancelar).isDisplayed()) {
					return true;
				}
				return false;
			case "botonGuardar":
				if(u.getDriver().findElement(botonGuardar).isDisplayed()) {
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	/**
	 * Metodo para seleccionar un elemento web
	 * 
	 * @param nombreCampo----------------campo que se espera seleccionar
	 * @param captura--------------------true si se debe agregar esta acción al archivo de evidencia y false si no
	
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionarElemento(String nombreCampo, boolean captura) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(100));
		By elementoSeleccion=null;
		boolean seleccion=false;
		try {
			switch(nombreCampo) {
				case "botonCancelar":
					elementoSeleccion=botonCancelar;
					seleccion=true;
				break;
				case "botonGuardar":
					elementoSeleccion=botonGuardar;
					seleccion=true;
				break;
			}
			u.highLight(elementoSeleccion);
			if(captura) {
				testCase.addScreenShotCurrentStep("Seleccion"+nombreCampo);
			}
			
			u.unhighLight(elementoSeleccion);
			
				u.getDriver().findElement(elementoSeleccion).click();
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
			return seleccion;
		} catch (Exception e) {
			
			return false;
		}
	}
	/**
	 * Metodo para obtener el valor actual de la caja de texto
	 * 
	 * @param nombreConstante------------constante de la que se espera obtener su valor.
	 * @return regresa true si la captura fue exitosa
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean obtenerTexto(String nombreConstante) {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(120));
		wait.until(ExpectedConditions.visibilityOfElementLocated(botonCancelar));
		By nombreConstantePantalla=By.xpath("//html/body/app-root/div/div/app-constante-register/form/div[1]/div[2]/input");
		u.hardWait(3);
		String valorConstantePantalla=u.getDriver().findElement(nombreConstantePantalla).getAttribute("value");
		if(valorConstantePantalla.compareTo(nombreConstante)==0) {
			return true;
		}
		return false;
		
	}
	
	
}
