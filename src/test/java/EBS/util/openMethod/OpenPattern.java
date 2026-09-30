package EBS.util.openMethod;

import java.io.File;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;

public class OpenPattern implements Runnable {

	/*
	 * UTILERIAS
	 */

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

	public OpenPattern(SikuliUtil sk, PatternUtil patternUtil, HashMap<String, Pattern> ImgPattern, File Folder,
			String patternToSearch) {
		this.sk = sk;
		this.patternUtil = patternUtil;
		this.ImgPattern = ImgPattern;
		this.Folder = Folder;
		this.patternToSearch = patternToSearch;
		exit = false;
	}

	/**
	 * Busca un patron para poder visualizar el archivo que se descargo
	 * 
	 * @author JessicaR@hexaware.com
	 */
	@Override
	public void run() {
		ImgPattern = patternUtil.getPatternsImg(Folder);
		try {
			while (sk.screen.exists(ImgPattern.get(patternToSearch)) != null && !exit) {
				Thread.sleep(10000);

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
