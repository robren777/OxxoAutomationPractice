/**
 * Clase para automatizar el modulo de RF
 * 
 * @author guerrero.vargas
 */

package RDM.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.App;
import org.sikuli.script.Key;
import org.sikuli.script.Region;
import org.sikuli.script.ScreenImage;
import org.sikuli.script.Pattern;

import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;

public class RadioFrecuencia {
	
	private int numeroEvidencia = 10000;
	
	Robot r;
	TestCase testCase;
	Process process;
	
	SikuliUtil sk = new SikuliUtil();
	HashMap<String, Pattern> imgPattern;
	
	public RadioFrecuencia(TestCase testCase) throws AWTException {
		r = new Robot();
		this.testCase = testCase;
		imgPattern = getPatternsImg("RDM\\RF");
		
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
		File file = new File(sk.PatternPath("RDM\\"+path));
		File[] files = file.listFiles();

		ArrayList<Pattern> patterns = new ArrayList<Pattern>();

		// agrega el obj Pattern al ArrayList patterns
		for (File f : files) {
			patterns.add(new Pattern(f.getAbsolutePath()).similar((float) 0.95));
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
	 * Inicia sesion en TeraTerm y en la radio frecuencia
	 * 
	 * @param host de TeraTerm
	 * @param puerto de TeraTerm
	 * @param user de TeraTerm
	 * @param pass TeraTerm
	 * @param rfUser usuario de RF
	 * @param rfPass password de RF
	 * 
	 * @author guerrero.vargas
	 */
	public void abrir(String host, String puerto, String user, String pass, 
			String rfUser, String rfPass) {
		Runtime runtime = Runtime.getRuntime();
		
		try {
			// config iniciales del emulador, K=Function.CNF es para que sirvan las teclas F1, F2, etc
			process = runtime.exec("C:\\Program Files (x86)\\teraterm\\TTERMPRO " + host + ":" + puerto + " "
					+ "/K=FUNCTION.CNF");
			
			r.delay(3000);
			
			testCase.addScreenShotCurrentStep("Enter RF");
			
			if (sk.screen.exists(imgPattern.get("checkAddHost")) != null) {
				sk.screen.wait(imgPattern.get("checkAddHost")).click();
				r.delay(1000);
				screenshot();
				sk.screen.type(Keys.ENTER);
				sk.screen.type(user);
				sk.screen.type(Keys.TAB);
				sk.screen.type(pass);	
				screenshot();
				sk.screen.type(Keys.ENTER);	
			}
			
			r.delay(1000);
			
			r.keyPress(KeyEvent.VK_2);
			screenshot();
			r.keyPress(KeyEvent.VK_ENTER);
			screenshot();
			r.delay(1000);
			r.keyPress(KeyEvent.VK_ENTER);
			screenshot();
					
			write(rfUser);				
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
				
			// necesita caps lock para que acepte la contrase�a
			if (!Key.isLockOn(Key.C_CAPS_LOCK)) {
				sk.screen.type(Key.CAPS_LOCK);
			}

			write(rfPass);
			sk.screen.type(Key.CAPS_LOCK);
			r.delay(500);
			screenshot();				
			r.keyPress(KeyEvent.VK_ENTER);	
			r.delay(1000);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Selecciona la opcion receiving > receiving
	 * 
	 * @author guerrero.vargas
	 */
	public void seleccionarReceivingReceiving() {		
		screenshot();
		
		downArrowKey(3);
		screenshot();
		r.keyPress(KeyEvent.VK_ENTER);
		
		r.delay(1000);
		
		downArrowKey(3);
		screenshot();
		r.keyPress(KeyEvent.VK_ENTER);	
	}
	
	/**
	 * Selecciona la opcion invent mgmt > manual replen
	 * 
	 * @author guerrero.vargas
	 */
	public void seleccionarInventMgmtManualReplen() {		
		r.delay(1000);
		screenshot();
		r.keyPress(KeyEvent.VK_ENTER);
		
		r.delay(1000);
		
		downArrowKey(8);
		screenshot();
		r.keyPress(KeyEvent.VK_ENTER);
		
	}
		
	/**
	 * Selecciona la opcion xxfc-receiving > stay in cedis
	 * 
	 * @author guerrero.vargas
	 */
	public void seleccionarXxfcReceivingStay() {		
		screenshot();
		
		downArrowKey(8);
		screenshot();
		r.keyPress(KeyEvent.VK_ENTER);
		
		r.delay(1000);
		
		downArrowKey(1);
		screenshot();
		r.keyPress(KeyEvent.VK_ENTER);	
	}
	
	/**
	 * en el menu receiving captura la informacion de la cita que se va a recibir
	 * 
	 * @param cita Id de la cita
	 * @param door Id de la puerta
	 * @param trailerId Id del trailer
	 * @param sealNumber Id de quien va a recibir la cita
	 * @return true si se captura correctamente, de lo contrario false
	 * @author guerrero.vargas
	 */
	public boolean capturarCita(String cita, String door, String trailerId, String sealNumber) {
		screenshot();
		
		write(cita);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(500);
		
		// si sale el error "Labels not printed" regresa null
		//(hay que imprimir las etiquetas en el aplicativo)
		if (sk.screen.exists(imgPattern.get("labelsNotPrinted")) != null) {
			return false;
		}
		
		write(door);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(500);
		
		write(trailerId);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(500);
		
		write(sealNumber);
		r.keyPress(KeyEvent.VK_ENTER);
		r.delay(500);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_F4);	
		
		r.delay(1000);
		
		// si la puerta colocada es invalida regresa false
		if (sk.screen.exists(imgPattern.get("invalidDoor")) != null) {
			System.out.println(door + " invalida");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Captura un numero de cita en la radio frecuencia
	 * 
	 * @param cita numero de cita
	 * @return true si existe la cita, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean capturarCitaStay(String cita) {
		screenshot();
		
		write(cita);
		r.keyPress(KeyEvent.VK_F4);
		
		if (sk.screen.exists(imgPattern.get("noExisteCita")) != null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Ingresa el numero de contenedor que se va a recibir
	 * 
	 * @param contenedor Id del contenedor
	 * @param cantidad Cantidad que se va a recibir
	 * @param item Id del item que se va a recibir
	 * @return true si se recibe correctamente, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean ingresarContenedor(String contenedor, String cantidad, String item) {
		screenshot();
		
		write(contenedor);
		screenshot();
		r.delay(1000);
		r.keyPress(KeyEvent.VK_TAB);
		
		r.delay(1000);
		
		//write(cantidad);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_ENTER);
		
		if (sk.screen.exists(imgPattern.get("prScanUnusedLabel")) != null) {
			r.delay(1000);
			screenshot();
			
			write(contenedor);
			screenshot();
			r.keyPress(KeyEvent.VK_F4);
		}
		
		//write(item);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_F4);
		r.delay(1000);
		
		screenshot();
		r.keyPress(KeyEvent.VK_F4);
		
		r.delay(2000);
		screenshot();
		
		r.delay(2000);
		
		return sk.screen.exists(imgPattern.get("prReceiving")) != null;
		
	}
	
	/**
	 * Ingresa el numero de contenedor que se va a recibir y se activa la opcion BEST BEFORE DATE
	 * 	  
	 * @param contenedor Id del contenedor
	 * @param cantidad Cantidad que se va a recibir
	 * @param item Id del item que se va a recibir
	 * @return true si se recibe correctamente, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean ingresarContenedorBest(String contenedor, String bestBeforeDate) {
		screenshot();
		
		write(contenedor);
		screenshot();
		r.delay(1000);
		r.keyPress(KeyEvent.VK_TAB);
		
		r.delay(1000);
		
		//write(cantidad);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_ENTER);
		
		r.delay(1000);
		
		if (sk.screen.exists(imgPattern.get("containerIdFocus")) != null) {
			return true;
		}
		
		if (sk.screen.exists(imgPattern.get("bestBeforeDateCantidad")) != null) {
			write(bestBeforeDate);
			r.delay(1000);
			screenshot();
			
			r.keyPress(KeyEvent.VK_F4);
			r.delay(1000);
			screenshot();
			
			write(contenedor);
			r.delay(1000);
			screenshot();
			r.keyPress(KeyEvent.VK_ENTER);
			
			r.keyPress(KeyEvent.VK_ENTER);
			
			return true;
		}
		
		if (sk.screen.exists(imgPattern.get("prScanUnusedLabel")) != null) {
			r.delay(1000);
			screenshot();
			
			write(contenedor);
			screenshot();
			r.keyPress(KeyEvent.VK_F4);
		}
		
		//write(item);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_F4);
		r.delay(1000);
		
		screenshot();
		r.keyPress(KeyEvent.VK_F4);
		
		r.delay(2000);
		screenshot();
		
		r.delay(2000);
		
		if (sk.screen.exists(imgPattern.get("bestBeforeDate")) == null) {
			write(bestBeforeDate);
		}
		
		return sk.screen.exists(imgPattern.get("prReceiving")) != null;
		
	}
	
	/**
	 *
	 */
	public void lock() {
		r.keyPress(KeyEvent.VK_F4);
		
		// TODO
	}
	
	/**
	 * 
	 * @param contenedor
	 */
	public void ingresarContenedorReintegro(String contenedor) {
		screenshot();
		
		write(contenedor);
		screenshot();
		
		r.keyPress(KeyEvent.VK_ENTER);
		/*
		r.delay(1000);
		r.keyPress(KeyEvent.VK_TAB);
		
		r.delay(1000);
		
		//write(cantidad);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_ENTER);
		
		if (sk.screen.exists(imgPattern.get("prScanUnusedLabel")) != null) {
			r.delay(1000);
			screenshot();
			
			write(contenedor);
			screenshot();
			r.keyPress(KeyEvent.VK_F4);
		}
		
		//write(item);
		
		screenshot();
		
		r.keyPress(KeyEvent.VK_F4);
		r.delay(1000);
		
		screenshot();
		r.keyPress(KeyEvent.VK_F4);
		
		r.delay(2000);
		screenshot();
		
		return sk.screen.exists(imgPattern.get("prReceiving")) != null;*/
		
	}
	
	/**
	 * Cierra el proceso de la radio frecuencia
	 * 
	 * @author guerrero.vargas
	 */
	public void closeProcess() {
		process.destroy();
	}
	
	/**
	 * Simula un ALT-F4 para cerrar la RF
	 * 
	 * @author guerrero.vargas
	 */
	public void closeAltF4() {
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_F4);
		r.keyRelease(KeyEvent.VK_ALT);
		screenshot();
		r.delay(1000);
		
		r.keyPress(KeyEvent.VK_LEFT);
		r.delay(1000);
		r.keyPress(KeyEvent.VK_ENTER);
	}
	
	/**
	 * Presiona n veces la tecla abajo
	 * 
	 * @param n Numero de veces que se va a presionar la tecla
	 * @author guerrero.vargas
	 */
	private void downArrowKey(int n) {
		for (int i = 0; i < n; i++) {
			r.keyPress(KeyEvent.VK_DOWN);
			r.delay(200);
		}
	}
	
	/**
	 * teclea palabras letra por letra para poder usar la clase java.awt.Robot
	 * 
	 * @param word palabra que se quiere teclear
	 * @author guerrero.vargas
	 * 
	 */
	private void write(String word) {
		for (int i = 0; i < word.length(); i++) {
			r.keyPress(word.charAt(i));
			r.delay(200);
		}
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
	 * Toma capturas de pantalla y las agrega al testCase
	 * 
	 * @author guerrero.vargas
	 */
	void screenshot() {
		Region window = App.focusedWindow(); // obtener la ventana con el focus
		ScreenImage image = sk.screen.capture(window); // toma la captura

		String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
		String filename = "evidence" + numeroEvidencia;
		image.save(savePath, filename);

		String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";

		testCase.addScreenShotCurrentStep(imgPath, true);
		numeroEvidencia++;

	}
	
}
