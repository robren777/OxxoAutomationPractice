package Mobile.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.App;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.ScreenImage;
import config.Constants;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;
public class BaseRDM {

	// variable usada para generar un nombre de evidencia nuevo
	private int numeroEvidencia = 0;

	// variable que utiliza sikuli
	SikuliUtil sk = new SikuliUtil();
	HashMap<String, Pattern> imgPattern;

	// variable de inicio de sesion
	private SeleniumUtil u;
	private TestCase testCase;
	private String url;
	//private String userName = GlobalVariables.usuarioRicardo;
	//private String password = GlobalVariables.contrasenaRicardo;

	public BaseRDM(SeleniumUtil u, TestCase testCase, String url) {
		this.u = u;
		this.testCase = testCase;
		this.url = url;
		imgPattern = getPatternsImg("RDM");
	}

	/**
	 * Crea un HashMap con key = nombre de la imagen y value = obj Pattern(path,
	 * similarity) para utilizar con Sikuli
	 * 
	 * @param dir raiz en donde se van a buscar las imagenes
	 * @return HashMap<String, Pattern>: con el nombre y el obj Pattern de la imagen
	 * @author guerrero.vargas
	 */
	public HashMap<String, Pattern> getPatternsImg(String path) {
		HashMap<String, Pattern> map = new HashMap<>();
		File file = new File(sk.PatternPath("Mobile\\"+path));
		File[] files = file.listFiles();

		ArrayList<Pattern> patterns = new ArrayList<Pattern>();

		// agrega el obj Pattern al ArrayList patterns
		for (File f : files) {
			if (f.isDirectory()) {
				// si el file es una carpeta entra a buscar imgs
				map.putAll((getPatternsImg(path + "\\" + f.getName())));

			} else {
				patterns.add(new Pattern(f.getAbsolutePath()).similar((float) 0.95));
			}
		}

		// recorre el ArrayList patters para obtener el path individual de las img
		for (Pattern p : patterns) {
			String fname = p.getFilename();
			fname = fname.replace(".png", ""); // elimina la extension
			fname = fname.replace("\\", "-"); //
			String[] afname = fname.split("-");

			map.put(afname[afname.length - 1], p);
		}

		return map;
	}

	/**
	 * Inicia sesion en RDM
	 * 
	 * @author ricardo.palacios
	 */
	public void logIn() {
		u.get(url);
		u.waitForLoadPage();

		testCase.addScreenShotCurrentStep(u, "Login");
		wait(3);
		maximizar();

		// ingresa datos de acceso
		//sk.screen.type(userName);
		sk.screen.type(Key.TAB);
		//sk.screen.type(password);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		screenshot();
		sk.screen.type(Key.ENTER);
		wait(5);
		maximizar();
		screenshot();

		wait(2);
	}

	/**
	 * Toma capturas de pantalla y las agrega al testCase
	 * 
	 * @author guerrero.vargas
	 */
	public void screenshot() {
		Region window = App.focusedWindow(); // obtener la ventana con el focus
		ScreenImage image = sk.screen.capture(window); // toma la captura

		String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
		String filename = "evidence" + numeroEvidencia;
		image.save(savePath, filename);

		String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";

		testCase.addScreenShotCurrentStep(imgPath, true);
		numeroEvidencia++;

	}

	/**
	 * Maximiza la ventana del navegador
	 * 
	 * @author guerrero.vargas
	 */
	void maximizar() {
		sk.screen.keyDown(Keys.WIN);
		sk.screen.keyDown(Keys.UP);
		sk.screen.keyUp(Keys.UP);
		sk.screen.keyUp(Keys.WIN);
	}

	/**
	 * Minimiza la ventana del navegador
	 * 
	 * @author guerrero.vargas
	 */
	public void minimizar() {
		WebDriver driver = this.u.getDriver();
		Point point = driver.manage().window().getPosition();
		Dimension d = driver.manage().window().getSize();
		driver.manage().window().setSize(new Dimension(0, 0));
		driver.manage().window().setPosition(new Point((d.getHeight() - point.getX()), (d.getWidth() - point.getY())));
	}

	/**
	 * Escribe el texto en una caja de texto que ya tenga el cursor activo y
	 * presiona la tecla TAB
	 *
	 * @param text
	 * @author ricardo.palacios
	 */
	public void typeAndTab(String text) {
		sk.screen.type(text);
		sk.screen.type(Key.TAB);
	}
	
	/**
	 * Escribe el texto en una caja de texto que ya tenga el cursor activo
	 *
	 * @param text
	 * @author ricardo.palacios
	 */
	public void type(String text) {
		sk.screen.type(text);
	}
	
	/**
	 * Metodo para darle un tiempo de espera al applet
	 * 
	 * @param segundos
	 * @author ricardo.palacios
	 */
	public void wait(int segundos) {
		u.hardWait(segundos);
	}

	/**
	 * Metodo para extraer un dato de un archivo excel
	 * 
	 * @param nombreArchivo
	 * @param numeroDeHoja
	 * @param numeroDeRenglon
	 * @param numeroDeColumna
	 * @return contenidoCelda
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author ricardo.palacios
	 */
	public String extraerDatoExcel(String nombreArchivo, int numeroDeHoja, int numeroDeColumna, int numeroDeRenglon)
			throws EncryptedDocumentException, IOException {

		String currentUser = System.getProperty("user.name"); // Obtener el user actual del equipo
		String rutaDelArchivo = "C:\\Users\\" + currentUser + "\\Downloads\\" + nombreArchivo;
		System.out.println("la ruta del archivo es : " + rutaDelArchivo);
		ExcelUtil hoja = new ExcelUtil(rutaDelArchivo);
		hoja.open();
		String contenidoCelda = hoja.getCell(numeroDeHoja, numeroDeRenglon, numeroDeColumna);
		return contenidoCelda;

	}
	
	/**
	 * Emula presionar la tecla 'Alt' + alguna tecla que necesitemos (solo una)
	 * 
	 * @param x
	 * @author ricardo.palacios
	 */
	public void keyAltMasTecla(String x) {
		sk.screen.keyDown(Key.ALT);
		sk.screen.type(x);
		sk.screen.keyUp(Key.ALT);
	}
	
	/**
	 * Metodo que da click en el navegador en base a una imagen, selecciona toda el
	 * texto y busca un String dentro del texto.
	 * 
	 * @param nameImgToClick
	 * @param strToFind
	 * @return String con el texto
	 * @throws FindFailed
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 * @throws HeadlessException
	 * @author ricardo.palacios
	 */
	public String getTextDelNavegador(String ImagenConTextoSeleccionada, String TextoaBuscar)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.wait(imgPattern.get(ImagenConTextoSeleccionada), 15).click(); // Encuentra la imagen a dar click en el navegador

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		String find = TextoaBuscar; // El String a buscar dentro de lo que copiamos
		String findResult = ""; // Lo que regresaermos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			findResult = "string not found";
			System.out.println("string not found");
		}

		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		return findResult; // Si lo encuentra lo regresa

	}
	
	/**
	 * Copia un valor mostrado en la pantalla del aplicativo.
	 * 
	 * @return String con el valor copiado.
	 * @author guerrero.vargas
	 */
	String copyPaste() {
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		
		return App.getClipboard();
	}
	
	/**
	 * Desactiva el NUM LOCK para podre realizar ciertas combinaciones
	 * de teclas
	 * 
	 * @author guerrero.vargas
	 */
	void numLockOff() {
		if (Key.isLockOn(Key.C_NUM_LOCK)) {
			sk.screen.type(Keys.NUM_LOCK);
		}
	}
	
	/**
	 * Metodo que optiene el url
	 * 
	 * @return
	 * @author ricardo.palacios
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Emula presionar la tecla TAB un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 * @author ricardo.palacios
	 */
	public void nTAB(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.TAB);
			x = x + 1;
		}
	}
	
	/**
	 * Emula presionar la combinacion shift + tab un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione shift + tab
	 * @author guerrero.vargas
	 */
	public void nShiftTab(int n) {
		int x = 0;
		
		while (x <= n) {
			sk.screen.type(Keys.TAB, KeyModifier.SHIFT);
			x++;
		}
	}
	
	/**
	 * Emula presionar la tecla Enter un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 * @author ricardo.palacios
	 */
	public void nEnter(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.ENTER);
			x = x + 1;
		}
	}
	
	/**
	 * Presiona la tecla Backspace n veces
	 * @param n Numero de veces que quieres que se presione la tecla backspace
	 */
	public void nBackspace(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.BACKSPACE);
			x = x + 1;
		}
	}
	
    /**
     * Metodo que optiene el nombre de usuario
     * 
     * @author ricardo.palacios
     */
	//public String getUserName() {
	//	return userName;
	//}
	
	/**
	 * Devuelve la instancia del WebDriver
	 * @return
	 * @author guerrero.vargas
	 */
	public WebDriver getDriver() {
		return u.getDriver();
	}

}
