/**
 * Representa la funcionalidad compartida de los procedimientos en RMS, como:
 * Login, seleccionar funcion en un menu, maximizar, minimizar y cerrar ventana, etc.
 */

package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.ScreenImage;

import RMS.util.GlobalVariables;
import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class BaseRMS {

	// counter para generar un nombre nuevo al output de screenshoot
	private static int evidenceNumber = 1;

	SQLUtil dbRms;
	Robot r;
	SeleniumUtil u;
	SikuliUtil sk;
	String UserChile;
	String PwdChile;
	String User;
	String Pwd;
	ArrayList<String> ListImage;
	ArrayList<Pattern> patterns;
	HashMap<String, Pattern> ImgPatter;
	File Fol;
	Pattern pathimg;
	String urlChile;
	String url;
	String status;
	HashMap<String, String> data;
	TestCase testCase;
	
	String UserCol;
	String PwdCol;
	
	

	public BaseRMS(SikuliUtil sk, SeleniumUtil u, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			HashMap<String, String> data, TestCase testCase) {

		this.u = u;
		this.sk = new SikuliUtil();
		this.data = data;
		this.UserCol = GlobalVariables.RMS_UserCol;
		this.PwdCol = GlobalVariables.RMS_PssCol;
		this.UserChile = GlobalVariables.RMS_User_CHILE;
		this.PwdChile = GlobalVariables.RMS_Pss_CHILE;
		this.urlChile = GlobalVariables.RMS_URL_CHILE;	
		this.User = data.get("rms_user");
		this.Pwd = PasswordUtil.decryptPassword(data.get("rms_ps"));
		this.url = data.get("rms_url");	
		this.r = r;		
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPatter = ImgPatter;
		this.patterns = patterns;
		this.Fol = Fol;
		// this.status = status;
		this.dbRms = dbRms;
		this.testCase = testCase;
	}

	// genera una data de paths de las imagenes.
	protected HashMap<String, Pattern> GetPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));

		}

		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			String[] afname = fname.split("-");
			ImgPatter.put(afname[afname.length - 1], p);
		}

		return ImgPatter;
	}

	/**
	 * Metodo para hacer login al aplicativo RMS.
	 */
	public void loginRMS() {
		ImgPatter = GetPatternsImg(Fol);

		u.get(url);
		testCase.addScreenShotCurrentStep(this.u, "Entrando a RMS");


		try {
			// sk.getScreen().wait(ImgPatter.get("rms_home"));
			// r.delay(2000);

			this.maximizar();
			System.out.println("Esperar 10");
			r.delay(12000);
			System.out.println("Espero 10");

			sk.getScreen().type(User);
			r.delay(3000);
			sk.getScreen().type(Key.TAB);
			r.delay(5000);
			sk.getScreen().type(Pwd + Key.TAB + Key.TAB);

			r.delay(3000);
			this.screenshot();

			sk.getScreen().type(Key.ENTER);
			r.delay(2000);

			this.maximizar();
			r.delay(30000);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Selecciona las opciones en el menu de la pantalla Rtkstrt (pantalla que se
	 * muestra despues de hacer login).
	 * 
	 * @param folder carpeta de la funcion a la que se va a acceder
	 * @param action funcion a la que se va a acceder
	 */
	void seleccionarMenuRtkstrt(String folder, String action) {
		try {
			sk.getScreen().wait(ImgPatter.get(folder)).doubleClick();
			r.delay(8000);
			sk.getScreen().wait(ImgPatter.get(action)).doubleClick();
			r.delay(8000);
			screenshot();
			sk.getScreen().doubleClick();
			sk.getScreen().wait(ImgPatter.get("action_label"), 30);

		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Hace click en el desplegable "action". Esta accion es compartida por todas
	 * las subclases.
	 */
	void action() {
		try {
			sk.getScreen().wait(ImgPatter.get("action")).click();
			r.delay(1000);

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Cierra el aplicativo
	 */
	void cerrarVentana() {
		try {
			sk.getScreen().wait(ImgPatter.get("mn_action")).click();
			sk.getScreen().wait(ImgPatter.get("smn_close")).click();

		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Maximiza la ventana del navegador.
	 */
	void maximizar() {
		sk.getScreen().keyDown(Keys.WIN);
		sk.getScreen().keyDown(Keys.UP);
		sk.getScreen().keyUp(Keys.UP);
		sk.getScreen().keyUp(Keys.WIN);
	}

	/**
	 * Minimiza la ventana del navegador.
	 */
	public void minimizar() {
		WebDriver driver = this.u.getDriver();
		Point point = driver.manage().window().getPosition();
		Dimension d = driver.manage().window().getSize();

		driver.manage().window().setSize(new Dimension(0, 0));
		driver.manage().window().setPosition(new Point((d.getHeight() - point.getX()), (d.getWidth() - point.getY())));
	}

	/**
	 * Copia un valor (folio, fecha, id) mostrado en la pantalla del aplicativo.
	 * 
	 * @return String con el valor copiado.
	 */
	String copyPaste() {
		sk.getScreen().keyDown(Key.CTRL);
		sk.getScreen().keyDown("c");
		sk.getScreen().keyUp("c");
		sk.getScreen().keyUp(Key.CTRL);

		return App.getClipboard();
	}

	/**
	 * Toma una captura de pantalla y la agrega al reporte de evidencias.
	 */
	void screenshot() {
		Region window = App.focusedWindow(); // obtener la ventana con el focus
		ScreenImage image = sk.getScreen().capture(window); // toma la captura

		String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
		String filename = "evidence" + BaseRMS.evidenceNumber;
		image.save(savePath, filename);

		String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";

		testCase.addScreenShotCurrentStep(imgPath, true);
		evidenceNumber++;

	}

}