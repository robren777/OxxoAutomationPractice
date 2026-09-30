package EBS.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class GenericMethods {

	SeleniumUtil u;
	TestCase testCase;
	EBSPaginaInicial ebs;

	public GenericMethods(SeleniumUtil u) {
		super();
		this.u = u;
		ebs = new EBSPaginaInicial(u, testCase);

	}

	/**
	 * Generic Click method
	 * 
	 * @param driver             Selenium webDriver
	 * @param elementToBeClicked Elemento al que se le dara clic
	 */
	public void genericClick(WebDriver driver, WebElement elementToBeClicked) {

		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", elementToBeClicked,
					"color: red; border: 4px solid red;");
			u.hardWait(1);
			elementToBeClicked.click();
		} catch (Exception e) {
			System.out.println("Elemento no encontrado " + e);
		}
	}

	/**
	 * Verifica si un archivo existe en la ruta indicada
	 * 
	 * @param filePath El path absoluto a donde se encuentre el archivo
	 * @return True si el archivo existe, False en caso contrario
	 * @author edwin.ramirez
	 */
	public boolean verifyFileExists(String filePath) {
		System.out.println("Se buscara el archivo en la ruta: " + filePath);
		boolean fileExists = false;
		File f = new File(filePath);
		if (f.exists() && !f.isDirectory()) {
			System.out.println("The file exists in the path: " + filePath);
			fileExists = true;
		} else {
			System.out.println("File not found in the path: " + filePath);
		}
		return fileExists;
	}

	/**
	 * Metodo hecho para formatear la ruta usada en casos NexxoCHO52088158
	 * C:\\Users\\ + miUsuario + \\Downloads\\ + nombreDelArchivo + fechaEnFormato
	 * mmddaa +.xls
	 * 
	 * @param fileName El nombre de archivo excel a buscar sin el formato de fecha
	 * @return fixedPath La ruta exacta del archivo
	 * @author edwin.ramirez
	 */
	public String formatPathNexxoCHO52088158(String fileName) {
		String fixedPath = "";
		String currentUser = System.getProperty("user.name"); // Obtener el user actual del equipo

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		String formatedDate = formatter.format(date);
		formatedDate = formatedDate.replace("/", "");

		fixedPath = "C:\\Users\\" + currentUser + "\\Downloads\\" + fileName + formatedDate + ".xls";
		return fixedPath;
	}

}
