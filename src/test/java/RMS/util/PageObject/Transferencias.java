package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.ScreenImage;

import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;


public class Transferencias {
	
//	private static final TestCase TestCase = null;
	static SQLUtil dbRms;
	static Robot r;
	static SeleniumUtil u;
	static SikuliUtil sk;
	static String User;
	static String Pwd;
	static ArrayList<String> ListImage;
	static ArrayList<Pattern> patterns;
	static HashMap<String, Pattern> ImgPatter;
	static File Fol;
	static Pattern pathimg;
	public static String cb ;
	static String status;
	static Boolean st;
	static HashMap<String, String> data;
	static ControlM j;
	static TestCase testCase;
	static JSONObject jobs;
	static String formatDate;
	static String df;
	int numeroEvidencia=0;
	
	
	@SuppressWarnings("static-access")
	public Transferencias (SikuliUtil sk,
			SeleniumUtil u,
			String cb,
			Robot r,
			Pattern pathimg,
			ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter,
			ArrayList<Pattern> patterns,
			File Fol,
			String status,
			SQLUtil	dbRms,
			Boolean st,
			HashMap<String, String> data, TestCase testcase) throws SQLException{
		this.u = u;
		this.sk = sk;
		this.cb = cb;
		this.r = r;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPatter = ImgPatter;
		this.patterns = patterns;
		this.Fol = Fol;
		this.status = status;
		this.dbRms = dbRms;
		this.st = st;
		this.data = data;
		this.testCase = testcase;
	}
	
	
/**
 * @author 72843
 * @param fol
 * @return ImgPatter 
 * @Comments
 * genera la data de las imagenes a utilizar en la automatizaci�n
 * 
 */
	private HashMap<String,Pattern> GetPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for(File s : files) {			
			patterns.add( new Pattern(s.getAbsolutePath()).similar((float).95));			
//			patterns.add( new Pattern(s.getAbsolutePath()).exact());			
		}
		for(Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			String[] afname = fname.split("-");			
			ImgPatter.put(afname[afname.length-1], p);			
		}	
		
		return ImgPatter;
	}
	
	public void OpenBrowser() {
		try {
			System.out.println("Driver: " + u.getDriver());
			System.out.println(data.get("rms_url"));
			u.get(data.get("rms_url"));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * @author 1000068915
	 * @comments Cierra el navegador
	 */
	public void CloseBrowser() {
		try {
			u.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	/**
	 * @author 72843
	 * @throws InterruptedException 
	 * @Comments
	 * realiza loguin en RMS
	 * 
	 */	
	public void loginRMS() throws FindFailed, InterruptedException {
		ImgPatter = GetPatternsImg(Fol);
		OpenBrowser();
		r.delay(15000);	
		sk.getScreen().type(data.get("rms_user"));
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
		screenshot();
		sk.getScreen().type(Key.ENTER);
		r.delay(15000);
		sk.getScreen().wait(ImgPatter.get("menu"),40);
		sk.getScreen().wait(ImgPatter.get("btn_max"),15).highlight(1,"red").click();
	}
	
	/**
	 * @author 1000072843
	 * @comments navega al modulo de Ordering
	 */
	 
	public void navInventoryTransfer() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("inventory"),15).click();
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("transfer"),15).doubleClick();
		r.delay(5000);
		
	}
	
	/**
	 * @author 1000068915
	 * @comments Seleccionar el menú Invetory, elegir la opción Transfer y dar click en Open
	 */
	public void actionNewTransfer() throws FindFailed {
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("transfer_action"), 30).right(50).click();
		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("transfer_order_new"), 30).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("transfer_type"), 30).right(50).click();
		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get(data.get("transfer_type")), 30).click();
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		screenshot();
		r.delay(2000);
		
		sk.getScreen().type(Key.ENTER);
		r.delay(1000);
	}
	
	/**
	 * @author 1000068915
	 * @comments Llena el formulario de transfer  dentro del proceso de EDIT
	 * @throws FindFailed
	 */
	public void actionEditTransfer() throws FindFailed {
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("transfer_action"), 30).right(50).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("transfer_order_edit"), 30).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("transfer_type"), 30).right(50).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get(data.get("transfer_type")), 30).click();
		r.delay(1000);
		
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().type(cb);
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		screenshot();
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_btn_ok"), 30).click();
		r.delay(1000);
	}
	
	/**
	 * @author 1000068915
	 * @comments Llena la panntalla de creacion de New Transfer
	 */
	public void fillNewTransfer() throws FindFailed {
		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("transfer_no"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().keyDown(Key.CTRL);
	    sk.getScreen().keyDown("c");
	    sk.getScreen().keyUp("c");
	    sk.getScreen().keyUp(Key.CTRL);
	    r.delay(3000);
	    cb = App.getClipboard();
	    
	    System.out.println(cb);
	    
		sk.getScreen().wait(ImgPatter.get("transfer_freightType"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().mouseMove(ImgPatter.get("space"));
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get(data.get("freight_type")),20).click();
		r.delay(1000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_locationType_from"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get(data.get("location_typeFrom")),20).click();
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("location_from"));
		r.delay(1000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_locationType_to"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get(data.get("location_typeTo")),20).click();
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("location_to"));
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		
		screenshot();
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_btn_items"),20).click();
		r.delay(1000);
	}
	
	/**
	 * @author 1000068915
	 * @comments Llena la panntalla de ITEM dentro del proceso de creacion de New Transfer
	 */
	public void fillNewTransferItem() throws FindFailed {
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_itemType"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().mouseMove(ImgPatter.get("space"));
		r.delay(1000);
		//sk.getScreen().wait(ImgPatter.get(data.get("item_type")),20).click();
		sk.getScreen().type(data.get("itemType"));
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(3000);
		sk.getScreen().type(Key.TAB);
		r.delay(3000);
		
		sk.getScreen().type(data.get("item"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_qtyTrans"),20).right(20).click();
		r.delay(1000);;
		sk.getScreen().type(data.get("qty_transfer"));
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("transfer_btn_apply_item"),20).click();
		r.delay(2000);
		
		screenshot();
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("orders_btn_ok"),20).click();
		r.delay(7000);

	}
	
	/**
	 * @author 1000068915
	 * @comments da clic en un boton con el label OK que esta seleccionado
	 */
	public void clickBtnOkU() throws FindFailed {
		
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_ok_u"),15).click();
		r.delay(1000);
		
	}
	
	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 2 veces
	 */
	public void close_RMS_2() throws FindFailed {
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
	}
	
	/**
	 * @author 1000068915
	 * @comments Toma una screenshot de una pantalla actual
	 */
	public void screenshot() {
        Region window = App.focusedWindow(); // obtener la ventana con el focus
        ScreenImage image = sk.getScreen().capture(window); // toma la captura
       String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
        String filename = "evidence" + numeroEvidencia;
        image.save(savePath, filename);
       String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";
       testCase.addScreenShotCurrentStep(imgPath, true);
        numeroEvidencia++;

   }

	/**
	 * @author 1000068915
	 * @comments Va a la barra de tareas y selecciona la opcion SUBMIT dentro del menu OPTION
	 */
	public void submit() throws FindFailed {
		// TODO Auto-generated method stub
		
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_options"),15).click();
		sk.getScreen().wait(ImgPatter.get("smn_submit2"),15).click();
		screenshot();
		
	}
	
	/**
	 * @author 1000068915
	 * @comments Va a la barra de tareas y selecciona la opcion SUBMIT dentro del menu OPTION
	 * Cierra la pantalla extra que se genera
	 */
	public void submitPrice() throws FindFailed {
		// TODO Auto-generated method stub
		
		r.delay(2000);
//		sk.getScreen().type(data.get("OriginCountry")+Key.TAB);
		sk.getScreen().wait(ImgPatter.get("mn_options"),15).click();
		sk.getScreen().wait(ImgPatter.get("smn_submit"),15).click();
		screenshot();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("rpch_btn_yes"),15).click();
		
	}
	
	/**
	 * @author 1000068915
	 * @comments Va a la barra de tareas y selecciona la opcion Approve  dentro del menu OPTION
	 */
	public void approve() throws FindFailed {
		// TODO Auto-generated method stub
		
		r.delay(2000);
//		sk.getScreen().type(data.get("OriginCountry")+Key.TAB);
		sk.getScreen().wait(ImgPatter.get("mn_options"),15).click();
		sk.getScreen().wait(ImgPatter.get("itm_approve"),15).click();
		screenshot();
		
	}
	
	/**
	 * @author 1000068915
	 * @throws InterruptedException 
	 * @Comments
	 * realiza loguin en RDM
	 * 
	 */	
	public void loginRDM() throws FindFailed, InterruptedException {
		ImgPatter = GetPatternsImg(Fol);
		OpenBrowser(data.get("rdm_url"));
		r.delay(15000);	
		sk.getScreen().type(data.get("rdm_user"));
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rdm_ps")) + Key.TAB + Key.TAB);
		screenshot();
		sk.getScreen().type(Key.ENTER);
		r.delay(15000);
		sk.getScreen().wait(ImgPatter.get("menu"),40);
		sk.getScreen().wait(ImgPatter.get("btn_max"),15).highlight(1,"red").click();
	}
	
	public void OpenBrowser(String url) {
		try {
			System.out.println("Driver: " + u.getDriver());
			System.out.println(url);
			u.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}



}
	
	
	