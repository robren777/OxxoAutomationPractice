package WEB.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class SikuliMethodsUtil {

	SikuliUtil sk;
	SeleniumUtil u;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<Pattern> patterns;
	File Folder;
	TestCase testCase;
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public SikuliMethodsUtil(SikuliUtil sk, SeleniumUtil u, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Fol, TestCase testCase) {
		this.u = u;
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPattern;
		this.patterns = patterns;
		this.Folder = Fol;
		this.testCase = testCase;
	}
	
	
	/**
	 * Genera una data de paths de las imagenes.
	 *
	 * @param folder Carpeta en la que se encuentras las imagenes
	 * @return ImgPattern El pattern
	 */
	public HashMap<String, Pattern> getPatternsImg(File folder) {
		File[] files = Folder.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPattern.put(afname[afname.length - 1], p);
		}

		return ImgPattern;
	}
	
	/**
	 * Metodo que sirve para hacer click en el icono de 'open file' de la ventada Downloads
	 * 
	 * @author JoseO@hexaware.com
	 * @throws FindFailed
	 */
	public void clickOpenFileXls() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("Downloads")).highlight();
		//testCase.addScreenShotCurrentStep("DownloadsXls", true);
		u.hardWait(15);
		sk.screen.wait(ImgPattern.get("Downloads")).highlightOff();
		sk.screen.wait(ImgPattern.get("openFile"), 20).click();
		
		
	}
	
	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 *
	 * @author JoseO@hexaware.com
	 */
	public void WindowMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
	}
	
	/**
	 * Cierra la ventana actual activa usando las teclas ALT + F4
	 * 
	 * @author JoseO@hexaware.com
	 */
	public void CloseCurrentWindow() {
		
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
	}
	
}
