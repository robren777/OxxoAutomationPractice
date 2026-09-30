package WEB.util.PortalWebRuteoDinamico;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class SikuliMethodsPortalWebRuteoDinamico {

	SikuliUtil sk = new SikuliUtil();
	HashMap<String, Pattern> imgPattern;
	private SeleniumUtil u;
	private TestCase testCase;

	public SikuliMethodsPortalWebRuteoDinamico(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		imgPattern = getPatternsImg("PORTALES");
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
		File file = new File(sk.PatternPath("WEB\\"+path));
		File[] files = file.listFiles();

		ArrayList<Pattern> patterns = new ArrayList<Pattern>();

		for (File f : files) {
			if (f.isDirectory()) {
				map.putAll((getPatternsImg(path + "\\" + f.getName())));

			} else {
				patterns.add(new Pattern(f.getAbsolutePath()).similar((float) 0.95));
			}
		}

		for (Pattern p : patterns) {
			String fname = p.getFilename();
			fname = fname.replace(".png", "");
			fname = fname.replace("\\", "-");
			String[] afname = fname.split("-");

			map.put(afname[afname.length - 1], p);
		}

		return map;
	}
	
	/**
	 * Click en el icono de 'open file' de la ventada Downloads
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 * @throws FindFailed
	 */
	public Boolean clickOpenFileXLX() throws Exception {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			sk.screen.wait(imgPattern.get("Downloads")).highlight(1,"red");
			u.hardWait(2);
			sk.screen.wait(imgPattern.get("openFile")).highlight(1,"red");
			u.hardWait(2);
			sk.screen.wait(imgPattern.get("openFile").similar(0.70f), 5).click();
			u.hardWait(50);
			testCase.addScreenShotCurrentStep("");
			existencia = true;
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
			System.out.println("Fallo al mostrar Downloads");
		}
		return existencia;
	}
	
	/**
	 * Valida que se haya llenado o soleccionado datos en el campo dado 
	 * 
	 * @param String  nombre del campo llenado
	 * @return Boolean true si existe el elemento, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarCampoLlenado(String campo) {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			sk.screen.exists(imgPattern.get(campo).similar(0.70f), 5).highlight(5,"red");
			u.hardWait(2);
			existencia = true;
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al seleccionar cedis");
		}
		return existencia;
	}
	
}
