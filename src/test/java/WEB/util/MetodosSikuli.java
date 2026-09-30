package WEB.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class MetodosSikuli {
	SikuliUtil sk;
	SeleniumUtil u;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPatter;
	ArrayList<Pattern> patterns;
	File Fol;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public MetodosSikuli(SikuliUtil sk, SeleniumUtil u, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol) {
		this.u = u;
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
		
		if(imagen.equals("printCP")||imagen.equals("saveCP")||imagen.equals("Folder")) {
			try {
				
				ImgPatter = getPatternsImg(Fol);
				sk.screen.click((ImgPatter.get(imagen)));
				
			}catch(Exception e) {
				ImgPatter = getPatternsImg(Fol);
				sk.screen.click((ImgPatter.get(imagen+"2")));
			}		
		}else{
			ImgPatter = getPatternsImg(Fol);
			sk.screen.click((ImgPatter.get(imagen)));
			
		}
	}
	

	/**
	 * Da click en la imagen guardada en la carpeta Pattern
	 * 
	 * @param imagen nombre de la imagen que se seleccionará
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void pressTAB(int n) {
		int count=1;
		do {
			sk.screen.type(Keys.TAB);
			count++;
		}while(count<=n);
	}
	
	
	
}
	