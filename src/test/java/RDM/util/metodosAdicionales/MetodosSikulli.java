package RDM.util.metodosAdicionales;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//import org.openqa.selenium.interactions.DoubleClickAction;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class MetodosSikulli {
	SikuliUtil sk;
	SeleniumUtil u;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPatter;
	ArrayList<Pattern> patterns;
	File Fol;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);
	
	
	public MetodosSikulli(SikuliUtil sk,  Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol) {
		
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPatter = ImgPatter;
		this.patterns = patterns;
		this.Fol = Fol;
	}
	
	/**
	 * Metodo donde se obtiene la ruta de las imagenes
	 * 
	 * @param fol
	 * @return ImgPatter
	 * @author ricardo.palacios
	 */
	public HashMap<String, Pattern> getPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPatter.put(afname[afname.length - 1], p);
		}

		return ImgPatter;
	}
	
	/**
	 * Da click en la imagen guardada en la carpeta Pattern
	 * 
	 * @param imagen nombre de la imagen que se seleccionará
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void clickImagen(String imagen) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.click((ImgPatter.get(imagen)));
	}
	
	/**
	 * Emula presionar la tecla TAB un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 */
	public void nTAB(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.TAB);
			x = x + 1;
		}
	}
	
	/**
	 * Emula presionar la tecla down arrow un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla down arrow
	 * 
	 * * @author JesusM3@hexaware.com
	 */
	public void nDown(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.DOWN);
			x = x + 1;
		}
	}
	
	/**
	 * Emula presionar la tecla up arrow un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla up arrow
	 * 
	 * @author JesusM3@hexaware.com
	 */
	public void nUp(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.UP);
			x = x + 1;
		}
	}
	
	
	public void enviarTexto(String texto) {
		sk.screen.type(texto);
	}
	
	public void capturarCredenciales() {
		
	}
	
	public void clickImagenprueba(String imagen) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.click((ImgPatter.get(imagen)));
		sk.screen.type("u", Key.CTRL);
	}
	
	/**
	 * Metodo que hace clic a la derecha de una imagen y copia el texto
	 *
	 * @param image
	 * @param number
	 * @return
	 */
	public String doubleClickAndCopyTextBoxRight(String image, int number)
			throws FindFailed, IOException, UnsupportedFlavorException {
		try{
		String textBox = null;
		ImgPatter = getPatternsImg(Fol);
		sk.screen.find(ImgPatter.get(image)).right(number).doubleClick();
		//u.hardWait(5);
		textBox = copyTxt();
		System.out.println(textBox);
		return textBox;
		}catch(Exception e){
			return "";
		}
	}
	public String ClickAndCopyTextBoxRight(String image, int number)
			throws FindFailed, IOException, UnsupportedFlavorException {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.find(ImgPatter.get(image)).right(number).click();
		//u.hardWait(5);
		return "";
	}
	
	public String copyTxt() throws IOException, UnsupportedFlavorException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String text = String.valueOf(clipboard.getData(DataFlavor.stringFlavor));
			System.out.println("Texto copiado: " + text);
			return text;
		}catch(Exception e) {
			return "No text in the buffer";
		}
		
	}


	///
	public String doubleClickAndCopyTextBoxLeft(String image, int number)
			throws FindFailed, IOException, UnsupportedFlavorException {
		try{
		String textBox = null;
		ImgPatter = getPatternsImg(Fol);
		sk.screen.find(ImgPatter.get(image)).left(number).doubleClick();
		//u.hardWait(5);
		textBox = copyTxt();
		System.out.println(textBox);
		return textBox;
		}catch(Exception e){
			return "";
		}
	}
	
}
