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


public class Ordenes {
	
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
	public Ordenes (SikuliUtil sk,
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
	 * @param navega al modulo de Ordering
	 * @throws FindFailed 
	 */

	public void navOrderingOrders() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("ordering"),15).click();
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("orders"),15).doubleClick();
		r.delay(5000);
		
	}
	
	public void actionNewOrder() throws FindFailed {
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("orders_action"), 30).right(50).click();
		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("New_Order"), 30).click();
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		screenshot();
		r.delay(2000);
		
		sk.getScreen().type(Key.ENTER);
		r.delay(1000);
	}
	
	public void fillNewOrder() throws FindFailed {
		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("orders_no"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().keyDown(Key.CTRL);
	    sk.getScreen().keyDown("c");
	    sk.getScreen().keyUp("c");
	    sk.getScreen().keyUp(Key.CTRL);
	    r.delay(3000);
	    cb = App.getClipboard();
	    
	    System.out.println(cb);
	    
		sk.getScreen().wait(ImgPatter.get("orders_supplier"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().type(data.get("supplier"));
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("orders_poType"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().type(data.get("po_type"));
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("orders_locationType"),20).right(20).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get(data.get("location_type")),20).right(20).click();
		r.delay(2000);
		
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("location"));
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("orders_btn_calculate_dates"),20).click();
		r.delay(2000);
		
		screenshot();
		r.delay(2000);
		
		sk.getScreen().wait(ImgPatter.get("orders_btn_items"),20).click();
		r.delay(1000);
	}
	
	public void fillNewOrderItem() throws FindFailed {
		r.delay(2000);
		
		String enterAs = data.get("enter_as");
		System.out.println(enterAs);
		
		if(enterAs.equals("Item")) {
			sk.getScreen().wait(ImgPatter.get("Ref_Item"),20).click();
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get(enterAs),20).click();
			r.delay(2000);
		}
		else {
			sk.getScreen().wait(ImgPatter.get(enterAs),20).right(20).click();
			r.delay(2000);
		}
		
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("item"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().type(data.get("origin_country"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().type(data.get("quantity"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().type(data.get("unit_of_purchase"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().type(data.get("supplier_pack_size"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		
		sk.getScreen().wait(ImgPatter.get("orders_btn_apply_item"),20).click();
		r.delay(2000);
		
		screenshot();
		r.delay(2000);
		
		
		sk.getScreen().wait(ImgPatter.get("orders_btn_ok"),20).click();
		r.delay(5000);
	}
	
	public void close_RMS() throws FindFailed {
		
		sk.getScreen().wait(ImgPatter.get("btn_close_jer"),15).highlight(1,"red").click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
	public void close_RMS_Menu() throws FindFailed {
		
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
	public void close_RMS_MaintenanceSupplier() throws FindFailed {
		
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
	public void close_RMS_CostChange() throws FindFailed {
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
	}
	
	public void close_RMS_lls() throws FindFailed {
		
		sk.getScreen().wait(ImgPatter.get("btn_close_lls"),15).highlight(1,"red").click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
	public void close_RMS_deals() throws FindFailed {
		
		sk.getScreen().wait(ImgPatter.get("btn_close_deals"),15).highlight(1,"red").click();
		r.delay(8000);
		sk.getScreen().wait(ImgPatter.get("btn_yes_deals"),15).highlight(1,"red").click();
		r.delay(8000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
	public void close_RMS_retail() throws FindFailed {
		
		sk.getScreen().wait(ImgPatter.get("btn_close_deals"),15).highlight(1,"red").click();
		r.delay(8000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
	public void close_RMS_RPCH() throws FindFailed {
		
		sk.getScreen().wait(ImgPatter.get("rpch_btn_OK"),15).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("rpch_btn_OK"),15).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_ok"),15).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
	}
	
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

	public void submit() throws FindFailed {
		// TODO Auto-generated method stub
		
		r.delay(2000);
//		sk.getScreen().type(data.get("OriginCountry")+Key.TAB);
		sk.getScreen().wait(ImgPatter.get("mn_options"),15).click();
		sk.getScreen().wait(ImgPatter.get("smn_submit"),15).click();
		screenshot();
		
	}
	
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
	
	public void approve() throws FindFailed {
		// TODO Auto-generated method stub
		
		r.delay(2000);
//		sk.getScreen().type(data.get("OriginCountry")+Key.TAB);
		sk.getScreen().wait(ImgPatter.get("mn_options"),15).click();
		sk.getScreen().wait(ImgPatter.get("itm_approve"),15).click();
		screenshot();
		
	}
	
	public void approveRetailPrice() throws FindFailed {
		// TODO Auto-generated method stub
		
		r.delay(2000);
//		sk.getScreen().type(data.get("OriginCountry")+Key.TAB);
		sk.getScreen().wait(ImgPatter.get("mn_options"),15).click();
		sk.getScreen().wait(ImgPatter.get("itm_approve"),15).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("rpch_btn_yes"),15).click();
		screenshot();
		
	}


	public void OpenControl() throws FindFailed {
		// TODO Auto-generated method stub SubSegNum
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("lbl_control_1"),15).doubleClick();
		r.delay(1000);
		screenshot();
		
	}


	public void Open_Setup() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("folder_setup_1"),15).doubleClick();
		r.delay(1000);
		screenshot();		
	}
	
	
	public void applyDealNewOrder() throws FindFailed {
		r.delay(3000);
		screenshot();
		
		sk.getScreen().wait(ImgPatter.get("newOrder_applyDeal"),20).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("applyDeals"),20).left(10).click();
		r.delay(1000);
		
		String enterAs = data.get("applyDeals");
		System.out.println(enterAs);
		
		if(enterAs.equals("Online")) {
			sk.getScreen().wait(ImgPatter.get("Offline"),20).click();
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get(enterAs),20).click();
			r.delay(2000);
		}
		else {
			sk.getScreen().wait(ImgPatter.get(enterAs),20).right(20).click();
			r.delay(2000);
		}
		screenshot();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("deal_btnOK"),20).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("deal_yes"),40).click();
		
	}
	
	public void clicBtnOk() throws FindFailed {
		
		screenshot();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_okO"),15).click();
		r.delay(2000);
	}
	
	public void approveOrder() throws FindFailed {
		
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_okO"),15).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("deal_yes"),40).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("deal_yes"),40).click();
	}
}
	
	
	