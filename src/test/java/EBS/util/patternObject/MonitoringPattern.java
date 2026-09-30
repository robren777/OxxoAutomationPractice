package EBS.util.patternObject;

import java.io.File;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import utils.SikuliUtil;

public class MonitoringPattern implements Runnable {

	SikuliUtil sk;
	PatternUtil patternUtil;
	HashMap<String, Pattern> ImgPattern;
	File Folder;
	String patternToSearch;

	/**
	 * To stop the thread
	 */
	private boolean exit;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public MonitoringPattern(SikuliUtil sk, PatternUtil patternUtil, HashMap<String, Pattern> ImgPattern, File Folder,
			String patternToSearch) {
		this.sk = sk;
		this.patternUtil = patternUtil;
		this.ImgPattern = ImgPattern;
		this.Folder = Folder;
		this.patternToSearch = patternToSearch;
		exit = false;
	}

	/**
	 * Busca el un patron y presiona el boton 'Refrescar los datos'
	 * 
	 * @author JesusH@hexaware.com
	 */
	@Override
	public void run() {
		ImgPattern = patternUtil.getPatternsImg(Folder);
		try {
			while (sk.screen.exists(ImgPattern.get(patternToSearch)) != null && !exit) {
				Thread.sleep(10000);
				patternUtil.keyAlt("f");

			}
		} catch (InterruptedException e) {
			logger.debug("Error " + e);
			e.printStackTrace();
		}

	}

	/**
	 * Se utiliza para forzar la finalizacion del hilo
	 */
	public void stopping() {
		exit = true;
		logger.warn("Forzando finalizacion... Thread");
		System.out.println("Forzando finalizacion... Thread");
	}

}
