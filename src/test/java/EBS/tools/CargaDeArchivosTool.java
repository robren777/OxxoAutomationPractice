package EBS.tools;

import EBS.util.pageObjectModel.EBSCargaDeArchivo;
import EBS.util.pageObjectModel.EBSCrearCuentas;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CargaDeArchivosTool {
	/**
	 * Herramienta para cargar archivos en la ventana "Carga de Archivos"
	 *
	 * @author edwin.ramirez@neoris.com
	 * @version 1.0
	 * @see EBSCrearCuentas
	 * @since 23/6/2022
	 */
	SeleniumUtil u;
	TestCase testCase;
	EBSCargaDeArchivo cargaArchivos;

	public CargaDeArchivosTool(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		cargaArchivos = new EBSCargaDeArchivo(u, testCase);
	}

	/**
	 * Seleciona el archivo a ser cargado por medio de armar la ruta correcta hacia
	 * el archivo
	 *
	 * @param filePath El path despues de "user.name" donde se encuentra el archivo
	 */
	public void selectFile(String filePath) {
		String currentUser = System.getProperty("user.name"); // Obtener el user actual del equipo
		String actualFilePath = "C:\\Users\\" + currentUser + filePath; // Junta la ruta inicial, el usuario y la parte
		// final de la ruta del archivo
		cargaArchivos.chooseFileElement.sendKeys(actualFilePath); // Se envia la ruta
	}

	public String seleccionarArchivoSinSendkeys(String fileName) {
		String currentProject = System.getProperty("user.dir"); // Obtiene la ruta del Proyecto actual
		// Se junta la ruta del proyecto,la ubicacion de resources y el nombre del
		// archivo
		String srcFilePath = currentProject + "\\Config\\Input\\resources" + fileName;
		System.out.println(srcFilePath);
		return srcFilePath;

	}

	/**
	 * Método que sirve para seleccionar los archivos a cargar desde la carpeta en
	 * donde se encuentran guardados
	 *
	 * @param fileName
	 * @author edwin.ramirz@neoris.com
	 */
	public void selectFileCurrentProject(String fileName) {
		String currentProject = System.getProperty("user.dir"); // Obtiene la ruta del Proyecto actual
		// Se junta la ruta del proyecto,la ubicacion de resources y el nombre del
		// archivo
		String srcFilePath = currentProject + "\\Config\\Input\\resources" + fileName;
		System.out.println(srcFilePath);
		cargaArchivos.chooseFileElement.sendKeys(srcFilePath); // Se envia la ruta

	}

}
