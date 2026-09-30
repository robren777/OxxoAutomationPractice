package EBS.neteos.NeteosCedisMVP2.om;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pomNeteos {
	
	private int numeroEvidencia = 0;
	
	private TestCase testCase;
	private HashMap<String, String> data;
	private SeleniumUtil u;
	
	protected SikuliUtil sk = new SikuliUtil();
	protected HashMap<String, Pattern> ImgPattern;
	
	private By recibos = By.xpath("//div[text()='Receipts']");
	private By recibos2 = By.xpath("//div[text()='Recibos']");
	private By otros = By.xpath("//div[text()='Otros']");
	
	
	public pomNeteos(SeleniumUtil u, TestCase testCase, HashMap<String, String> data) {
		this.testCase = testCase;
		this.data = data;
		this.u = u;
		ImgPattern = getPatternsImg("EBS-NETEOS");
	}
	
	/**
	 * Click en la carpeta Recibos de la responsabilidad MXOXXO-AR17ADM-Consultas_ADMON_COM_OS
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean clickRespRecibo() {
		try {
			u.waitWebElement(recibos, 30, 2);
			u.highLight(recibos);
			testCase.addScreenShotCurrentStep(u, "Click en recibo");
			u.click(recibos);
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	
		
	/**
	 * Click en la carpeta Recibos de la responsabilidad MXOXXO-AR17ADM-RECIBOS_ADMON_COM_OS
	 * @author 1000043031 - Priscila Medellin
	 */	
		public Boolean clickRespRecibo2() {
			try {
				u.waitWebElement(recibos2, 30, 2);
				u.highLight(recibos2);
				testCase.addScreenShotCurrentStep(u, "Click en recibo");
				u.click(recibos2);
				return true;
			}catch(Exception ex) {
				System.out.println(" -> " + ex);
	            return false;
			}
			
	}
	
	/**
	 * Click en la carpeta Otros
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean clickOtros() {
		try {
			u.waitWebElement(otros, 30, 2);
			u.highLight(otros);
			testCase.addScreenShotCurrentStep(u, "Click en Otros");
			u.click(otros);
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
		
	}
	/**
	 * Maximiza la pantalla del navegador
	 * @author 1000072516 - Jose Zarate
	 */
		public Boolean maximiza_() {
		try {
			sk.screen.wait(ImgPattern.get("maximiza"), 90);
			sk.screen.click(ImgPattern.get("maximiza"));
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            throw new AssertionError("Error al maximizar la ventana");
		}
		
	}
	
	/**
	 * Toma captura de pantalla, para cuando se usa la libreria de Sikuli
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean screenshot() {
		try {
			u.hardWait(1);
			String filename = "evidence" + numeroEvidencia;
			testCase.addScreenShotCurrentStep(filename);
			u.hardWait(1);
			numeroEvidencia++;
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            throw new AssertionError("Fallo al realizar el screenshot");
		}
	
	}
		
	/**
	 * Crea un hashMap de clave valor con las imagenes a utilizar con la libreria de sikuli
	 * @param folder --> Nombre del folder dentro de la carpeta Pattern
	 * @return --> objeto que contiene las imagenes a buscar
	 * @author 1000072516 - Jose Zarate
	 */
	public HashMap<String, Pattern> getPatternsImg(String folder) {
		try {
			HashMap<String, Pattern> map = new HashMap<>();
			File file = new File(sk.PatternPath("EBS"+folder));
			File[] files = file.listFiles();

			ArrayList<Pattern> patterns = new ArrayList<Pattern>();

			// agrega el obj Pattern al ArrayList patterns
			for (File f : files) {
				if (f.isDirectory()) {
					// si el file es una carpeta entra a buscar imgs
					map.putAll((getPatternsImg("EBS"+folder + "\\" + f.getName())));

				} else {
					patterns.add(new Pattern(f.getAbsolutePath()).similar((float) 0.95));
				}
			}

			// recorre el ArrayList patters para obtener el path individual de las img
			for (Pattern p : patterns) {
				String fname = p.getFilename();
				fname = fname.replace(".png", ""); // elimina la extension
				fname = fname.replace("\\", "-"); 
				String[] afname = fname.split("-");

				map.put(afname[afname.length - 1], p);
			}

			return map;
			
		}catch(Exception ex){
			System.out.println(" -> " + ex);
            throw new AssertionError("Fallo al generar el hashMAp con las imagenes");
		}
	}
	/**
	 * Valida la pantalla de Ejecutar Solicitud (en donde se agrega el concurrente)
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean validarPantallaNeuvaSolicitud() {
		try {
			sk.screen.exists(ImgPattern.get("nuevaSolicitud"), 20);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
	}
	
	/**
	 * Valida la pantalla emergente de Parametros
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean validarPantallaParametros() {
		try {
			sk.screen.exists(ImgPattern.get("parametros"), 20);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	
	/**
	 * Valida que se muestre la pantalla Encontrar Solicitudes
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean validarPantallaEncontrarSolicitudes() {
		try {
			sk.screen.exists(ImgPattern.get("encontrarSolicitudes"), 20);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	/**
	 * Valida que la ejecución del concurrente haya finalizado
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean validarConcurrenteFinalizado() {
		try {
			sk.screen.exists(ImgPattern.get("concurrenteFinalizado"), 30);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	/**
	 * Valida que se muestre el log
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean validarRegistro() {
		try {
			sk.screen.exists(ImgPattern.get("registro"), 20);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	
	public Boolean clickApp() {
		try {
			sk.screen.exists(ImgPattern.get("oracle"), 90);
			screenshot();
			sk.screen.click(ImgPattern.get("oracle"));
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            throw new AssertionError("Error al dar click en el icono de la App");
		}
		
	}
	
	/**
	 * Valida el botón de Visualizar Output
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean clickVisualizaOutput() {
		try {
			sk.screen.exists(ImgPattern.get("btnVisualizaOutput"), 20);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
	}
	
	/**
	 * Valida si existe el patron a buscar
	 *
	 * @param target Imagen a buscar
	 * @return True o False utilizado para hacer un assert
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public Boolean validatePattern(String target) throws FindFailed {
		if (sk.screen.exists(ImgPattern.get(target)) != null) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * Valida que se descargue el archivo
	 * @author 1000072516 - Jose Zarate
	 */
	String nombreArchivo;
	public Boolean validaDescargaDeArchivo() {
		try {
			u.hardWait(10);
			String descargasDir = System.getProperty("user.home")+"/Downloads";
			
			String nombreBase = "XXFC___Reporte_Remisiones_AR_S_";
			String fechaActual = new SimpleDateFormat("ddMMyy").format(new Date());
			nombreArchivo = nombreBase+fechaActual+".xls";
			
			Path rutaArchivo = Paths.get(descargasDir, nombreArchivo);
			if(Files.exists(rutaArchivo)) {
				testCase.addBoldTextEvidenceCurrentStep("El archivo se descargo correctamente");
				return true;
			}else {
				testCase.addBoldTextEvidenceCurrentStep("El archivo No se descargo correctamente");
				return false;
			}
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	/**
	 * Valida que se descargue el archivo del reporte de relacion de recibos
	 * @author 1000072516 - Jose Zarate
	 */
	String nombreArchivoRelacionderecibos;
	public Boolean validaDescargaDeArchivoRelacionderecibos() {
		try {
			u.hardWait(60);
			String descargasDir2 = System.getProperty("user.home")+"/Downloads";
			
			String nombreBase = "XXFC_–_Reporte_de_relación__";
			String fechaActual = new SimpleDateFormat("ddMMyy").format(new Date());
			nombreArchivoRelacionderecibos = nombreBase+fechaActual+".xls";
			
			Path rutaArchivo2 = Paths.get(descargasDir2, nombreArchivoRelacionderecibos);
			if(Files.exists(rutaArchivo2)) {
				testCase.addBoldTextEvidenceCurrentStep("El archivo se descargo correctamente");
				return true;
			}else {
				testCase.addBoldTextEvidenceCurrentStep("El archivo No se descargo correctamente");
				return false;
			}
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	
	
	/**
	 * Valida pantalla de Solicitud Única
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean validaOpcionSolicitudUnica() {
		try {
			sk.screen.exists(ImgPattern.get("solicitudUnica"), 90);
			screenshot();
			return true;
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	/**
	 * Valida pantalla de aceptar solicitud Unica
	 * @author 1000072516 - Jose Zarate
	 */
	public Boolean aceptarSolicitud() {
		try {
			
			sk.screen.exists(ImgPattern.get("aceptarSolicitdUnica"), 90);
			sk.screen.click(ImgPattern.get("aceptarSolicitdUnica"));
			return true;
			
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}
	
	/**
	 * Valida que se descargue el archivo
	 * @author 1000072516 - Jose Zarate
	 */
	String nombreArchivo2;
	public Boolean validaDescargaDeArchivo2() {
		try {
			u.hardWait(10);
			String descargasDir = System.getProperty("user.home")+"/Downloads";
			
			String nombreBase = "XXFC_–_Reporte_de_relación__";
			String fechaActual = new SimpleDateFormat("ddMMyy").format(new Date());
			nombreArchivo2 = nombreBase+fechaActual+".xls";
			
			Path rutaArchivo = Paths.get(descargasDir, nombreArchivo2);
			if(Files.exists(rutaArchivo)) {
				testCase.addBoldTextEvidenceCurrentStep("El archivo se descargo correctamente");
				return true;
			}else {
				testCase.addBoldTextEvidenceCurrentStep("El archivo No se descargo correctamente");
				return false;
			}
		}catch(Exception ex) {
			System.out.println(" -> " + ex);
            return false;
		}
	}

}
