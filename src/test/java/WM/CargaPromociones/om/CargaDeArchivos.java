package WM.CargaPromociones.om;


import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import WM.om.MetodosExtra;
import WM.util.GlobalVariables;
import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class CargaDeArchivos {

	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	//****************************+*****LOCALIZADORES*********************************************
	
	
	By xmlChooseFile = By.xpath("//*[@id=\\\"pt1:contentAreaReg:0:tabrg2:2:ap1:if1\\\"]");	
	By xmlChooseFileBtn = By.xpath("//*[@id=\"formFiltroInventario\"]/div[1]/div/div/input");
	By xmlNumArchivo = By.xpath("//table/tbody/tr[1]/td[1]");
	By xmlTableTr = By.xpath("//table/tbody/tr");

	
	String pathFileLoaded = Constants.PATTERN_PATH + File.separator + "IEP" + File.separator + "Carga de inventario.xlsx";
	String pathFileDownloaded = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "%s";
	
	// variable usada para generar un nombre de evidencia nuevo
	private int numeroEvidencia = 0;
	
	// variable que utiliza sikuli
		SikuliUtil sk = new SikuliUtil();
		HashMap<String, Pattern> imgPattern;
		
		
		
	
	/**
	 * Constructor SeleniumUtil u,
	 */
		public CargaDeArchivos(SeleniumUtil u,TestCase testCase) {
			super();
			this.u = u;
			this.testCase = testCase;
			this.me = new MetodosExtra (u, testCase);
			imgPattern = getPatternsImg("RDM");
			
		}
		
		public CargaDeArchivos(SeleniumUtil u,TestCase testCase, String pattern) {
			super();
			this.u = u;
			this.testCase = testCase;
			this.me = new MetodosExtra (u, testCase);
			imgPattern = getPatternsImg(pattern);
			
		}
		

		/**
		 * Metodo para cargar archivo excel
		 * @return Retorna true si se cargo correctamente
		 * @throws FindFailed
		 * @author Maria Dueñas
		 */
		public Boolean cargarArchivo() throws FindFailed {
			//Boolean correct = me.selectFile("IEP", "FileTextBoxBlack", "OpenBlack", "Carga de inventario");
			//Constants.PATTERN_PATH + File.separator + "IEP" + File.separator + "Downloads"+  File.separator + 
			Boolean correct = false;
			try {
				//String filePath = System.getProperty("user.home") + File.separator + "Downloads" +  File.separator +  "Purchase Order - 8.1.23 12.58 PM.xlsx";
				//String filePath = Constants.PATTERN_PATH + File.separator + "RDM" + File.separator + "cargaDeArchivos"+  File.separator + "Purchase Order.png";
				String filePath =  "C:"  + File.separator + "Users"  + File.separator +"51653"  + File.separator + "Downloads"  + File.separator + "Purchase Order - 8.1.23 12.58 PM.ods";
				u.highLight(xmlChooseFile);
				testCase.addScreenShotCurrentStep(u, "cargarArchivo");
				u.sendKeys(xmlChooseFile, filePath);
				testCase.addScreenShotCurrentStep(u, "cargarArchivo");
				u.highLight(xmlChooseFile);
				correct = true;
			}catch(Exception e) {
				testCase.addScreenShotCurrentStep(u, "Error");
			}	
			
			u.hardWait(20);
			return correct;
			
			
		}


		
		
		/**
		 * Metodo para saber el numero del ultimo archivo excel en el sistema
		 * @return Retorna el numero de registro del archivo
		 * @author Maria Dueñas
		 */
		public String getLastFile() {
			String num = "";
			
			List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
			Integer row_count = tablaTr.size();
			
			if (row_count >= 1) {
				num = u.getText(xmlNumArchivo).trim();
			}
			
			return num;
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
			File file = new File(sk.PatternPath(path));
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
		
		
		public boolean cargarArchivo2()  { //no funciona correctamente
		u.hardWait(10);
			try {
				sk.screen.hover(imgPattern.get("DownloadsAndrea"));
				wait(1);
				screenshot();
				sk.screen.click();
			/*	
				sk.screen.hover(imgPattern.get("Purchase Order"));
				wait(1);
				screenshot();
				sk.screen.click();
				
				sk.screen.hover(imgPattern.get("Open"));
				wait(1);
				screenshot();
				sk.screen.click();
				*/
				
				sk.screen.wait(imgPattern.get("d2"), 30.0);
				sk.screen.hover(imgPattern.get("d2"));
				u.hardWait(5);
				screenshot();
				sk.screen.click();	
				
				wait(1);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return true;
		}
		
		/**
		 * Metodo para cargar archivo pasandole path en ventana de explorador de archivos
		 * @return Retorna true si se cargo correctamente
		 * @throws FindFailed
		 * @param ruta del archivo
		 * @author Tania Dena
		 */
		public Boolean cargarArchivoRms(String rutaArchivo) throws FindFailed {
			Boolean correct = false;
			try {
				
				Screen s = new Screen();
				
					Robot r = new Robot();
					s.click("C:\\Users\\51653\\git\\FrameworkNucleo\\Patterns\\RMS16\\cargaDeArchivos\\" + "FileName.png");
					r.delay(5000);
					System.out.println("Se encontro la imagen File name");
					s.type(rutaArchivo);
					r.delay(5000);
					System.out.println("Se escribio path del archivo");
					s.click("C:\\Users\\51653\\git\\FrameworkNucleo\\Patterns\\RMS16\\cargaDeArchivos\\" + "Open.PNG");
					r.delay(5000);
					System.out.println("Se cargo archivo");
					testCase.addScreenShotCurrentStep(u, "cargaArchivo");
				correct = true;
			}catch(Exception e) {
				testCase.addScreenShotCurrentStep(u, "Error");
			}	
			u.hardWait(20);
			return correct;
		}
		
		/**
		 * Metodo para cargar archivo pasandole path en ventana de explorador de archivos
		 * @return Retorna true si se cargo correctamente
		 * @throws FindFailed
		 * @param ruta del archivo
		 * @author 1000068915
		 */
		public Boolean cargarArchivoRms2(String file) throws FindFailed {
			Boolean correct = false;

			try {
				
					String ruta = GlobalVariables.User_Dir + "\\Patterns\\WM\\RMS16\\cargaDeArchivos\\" + file + ".ods";
					System.out.println(ruta);
					sk.screen.wait(imgPattern.get("FileNameLabel"), 60).right(10).click();
					sk.screen.type(ruta);
					u.hardWait(5);
					sk.screen.type(Key.ENTER);
					u.hardWait(3);
					testCase.addScreenShotCurrentStep(u, "cargaArchivo");
					
				correct = true;
			}catch(Exception e) {
				testCase.addScreenShotCurrentStep(u, "Error");
			}	
			return correct;
		}
		
		/**
		 * Metodo para cargar archivo pasandole path en ventana de explorador de archivos
		 * @throws FindFailed
		 * @param ruta del archivo
		 * @author 1000068915
		 */
		public void selectPromotion(String promotion) throws FindFailed {

	
					
				sk.screen.wait(imgPattern.get(promotion), 60).highlight(1, "red").right(10).click();
				u.hardWait(3);
					
				testCase.addScreenShotCurrentStep(u, "Upload File");
				System.out.println("Encontro imagen");
				
		}
		
		public void selectMassiveChangePromotion(String changePromotion) throws FindFailed {

			
			
			sk.screen.wait(imgPattern.get(changePromotion), 60).highlight(1, "red").right(10).click();
			u.hardWait(3);
				
			testCase.addScreenShotCurrentStep(u, "Massive Change Promotion");
			System.out.println("Encontro imagen");
			
	}
		
		/**
		 * Metodo para cargar archivo pasandole path en ventana de explorador de archivos
		 * @return Retorna true si se cargo correctamente
		 * @throws FindFailed
		 * @param ruta del archivo
		 * @author 1000068915
		 */
		public Boolean cargarArchivoRmsCustomerSegments(String file) throws FindFailed {
			Boolean correct = false;

			try {
				try {
					sk.screen.wait(imgPattern.get("ArchivoOrigen"), 15).highlight(1, "red").right(10).click();
					u.hardWait(3);
				}
				catch(Exception e){
					sk.screen.wait(imgPattern.get("sourceFile"), 15).highlight(1, "red").right(10).click();
					u.hardWait(3);
				}
					
					String ruta = GlobalVariables.User_Dir + "\\Patterns\\WM\\RMS16\\cargaDeArchivos\\" + file + ".ods";
					System.out.println(ruta);
					sk.screen.wait(imgPattern.get("FileNameLabel"), 60).right(10).click();
					sk.screen.type(ruta);
					u.hardWait(5);
					sk.screen.type(Key.ENTER);
					u.hardWait(3);
					testCase.addScreenShotCurrentStep(u, "cargaArchivo");


				correct = true;

			}catch(Exception e) {

				testCase.addScreenShotCurrentStep(u, "Error");

			}	

			return correct;

		}

		
		public Boolean cargarArchivoTransfer(String file) throws FindFailed {

			Boolean correct = false;

 

			try {

				sk.screen.wait(imgPattern.get("choosefile"), 60).highlight(6, "red").right(10).click();

				u.hardWait(3);

					String ruta = GlobalVariables.User_Dir + "\\Patterns\\WM\\RMS16\\cargaDeArchivos\\" + file + ".ods";
					System.out.println(ruta);
					sk.screen.wait(imgPattern.get("FileNameLabel"), 60).right(10).click();
					sk.screen.type(ruta);
					u.hardWait(5);
					sk.screen.type(Key.ENTER);
					u.hardWait(3);
					testCase.addScreenShotCurrentStep(u, "cargaArchivo");
        correct = true;

			}catch(Exception e) {

				testCase.addScreenShotCurrentStep(u, "Error");

			}	

			return correct;

		}
		
		
				

}

