package RMS.util.PageObject;


import java.awt.Robot;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.hotkey.Keys;
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

public class Items_Jorge {

	@SuppressWarnings("unused")
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
	public static String cb;
	static String status;
	static Boolean st;
	static HashMap<String, String> data;
	static ControlM j;
	static TestCase testCase;
	static JSONObject jobs;
	static String formatDate;
	static String df;
	int numeroEvidencia = 0;

	@SuppressWarnings("static-access")
	public Items_Jorge(SikuliUtil sk, SeleniumUtil u, String cb, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			Boolean st, HashMap<String, String> data, TestCase testcase) throws SQLException {
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
	 * @Comments genera la data de las imagenes a utilizar en la automatizaci�n
	 * 
	 */
	private HashMap<String, Pattern> GetPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
//				patterns.add( new Pattern(s.getAbsolutePath()).exact());			
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

	private void OpenBrowser(String URL) {
		try {
//				System.out.println(data.get("rms_url"));
			u.get(URL);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @author 1000072843
	 * @comments abre navegador con la url de RMS
	 * @return dato booleano
	 */
	public void Login_En_RMS() throws FindFailed {
		ImgPatter = GetPatternsImg(Fol);
		OpenBrowser(data.get("rms_url"));
		r.delay(25000);
		sk.getScreen().type(data.get("rms_user"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
		testCase.addScreenShotCurrentStep("ingresar credenciales");
		sk.getScreen().type(Key.ENTER);
		r.delay(15000);
//			sk.getScreen().capture().save("img.png");
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder items, elegir la opcion items y dar clic en el
	 *           botón open
	 */
	public void navItems() throws FindFailed {
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("lbl_items_1"), 15).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("lbl_items_2"), 15).doubleClick();
		screenshot();
	}

	public void EditItem() throws FindFailed {

		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("slct_action"), 15).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("slct_editItem"), 15).click();
		r.delay(1000);
		sk.getScreen().type(data.get("Item") + Key.TAB);
		r.delay(1000);
		screenshot();
		sk.getScreen().type(Key.ENTER);
	}

	/**
	 * @author 1000068915
	 * @comments Da click en la opción Supplier
	 */
	public void clicSuppliers() throws FindFailed {

		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("chck_suppliers"), 15).click();
		r.delay(3000);
		screenshot();

	}

	/**
	 * @author 1000068915
	 * @comments Da click en el botón Add
	 */
	public void appliSupplier() throws FindFailed {

		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("btn_add"), 15).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("itm_menu_supplier"), 15).click();
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("itm_opcion_supplier"), 15).doubleClick();
		r.delay(3000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("flete"));
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("btn_apply_suplier"), 15).click();
		r.delay(3000);
		screenshot();

	}

	/**
	 * @author 1000068915
	 * @comments Da click en Item-Supplier-Origin Country
	 */
	public void clicOriginCountry() throws FindFailed {

		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("itm_supplier_origin_country_red"), 15).click();
		r.delay(3000);
		screenshot();

	}

	/**
	 * @author 1000068915
	 * @comments Llenar la pantalla Origin Country
	 */
	public void selectOriginContry() throws FindFailed {

		r.delay(3000);
		sk.getScreen().type(data.get("OriginContry") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(Key.ENTER);
		r.delay(1000);
		sk.getScreen().type(Key.ENTER);
		r.delay(3000);
		//String btn_name = sk.getScreen().wait(ImgPatter.get("btn_create"), 15).getText();
		sk.getScreen().type(data.get("UnitCost"));
		r.delay(3000);
		sk.getScreen().find(ImgPatter.get("inner_Size")).below(15).click();
		r.delay(1000);
		sk.getScreen().type(data.get("innerSize") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("caseSize") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("Tier") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("Heigth") + Key.TAB);
		r.delay(1000);
		//System.out.println(btn_name);
		screenshot();
		sk.getScreen().wait(ImgPatter.get("btn_ok"), 15).click();
		r.delay(3000);

	}

	/**
	 * @author 1000068915
	 * @comments Dar clic en boton OK
	 */
	public void clic_btnOK() throws FindFailed {

		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_ok"), 15).click();
		r.delay(2000);
		screenshot();

	}
	
	/**
	 * @author 1000068915
	 * @comments Dar clic en boton OK en la pantalla ItemMaster
	 */
	public void clic_btnOK_itmaster() throws FindFailed {

		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_ok_itmaster"), 15).click();
		r.delay(2000);
		screenshot();

	}

	/**
	 * En la pantalla itemmaster selecciona VAT Maintenance y agregar nuevo impuesto
	 * @param store
	 */
	public void vatMaintenance(String store) {
		try {
			sk.getScreen().wait(ImgPatter.get("itemmaster_vatmaintenance")).doubleClick();
			r.delay(5000);
			sk.getScreen().wait(ImgPatter.get("vatitem_vatregion")).click();
			
			r.delay(2000);
			sk.getScreen().type(data.get("vatRegion"));
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("vatitem_addvat")).click();
			r.delay(2000);
			sk.getScreen().type(data.get("vatCode"));
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);
			
						while (true) {
							if (sk.getScreen().exists(ImgPatter.get("itemloc_grouptype_store")) == null) {
								sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_up")).click();
								
							} else {
								sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_store")).click();
								break;
							}
						}
			
			sk.getScreen().type(store); 
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(3000);
			sk.getScreen().type(Keys.ENTER);
			r.delay(7000);
			if (sk.getScreen().exists(ImgPatter.get("itemloc_reteknote")) != null) {
			} else {
				System.out.println("La creacion falló");
				return;
			}
			
			sk.getScreen().wait(ImgPatter.get("itemloc_reteknote_ok")).click();
			
		} catch (FindFailed e) {
			e.printStackTrace();
		}
		
	}
//		/**
//		 * @author 1000072843
//		 * @comments ingresa las credenciales de acceso para RMS y da c lic en el botom logon
//		 * @return st
//		 */
//		public Boolean ingresar_credenciales() {
//			
//			ImgPatter = GetPatternsImg(Fol);
//			r.delay(15000);	
//			sk.getScreen().type(data.get("rms_user"));
//			r.delay(1000);
//			sk.getScreen().type(Key.TAB);
//			r.delay(1000);
//			sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
//			sk.getScreen().type(Key.ENTER);
//			r.delay(15000);			
//			st = sk.getScreen().has(ImgPatter.get("img_menu_inicio"));
//			if(st.equals(true)) {
//			testCase.addScreenShotCurrentStep("ingresar credenciales");
//			}else {
//			u.screenShotFull("scr");
//			}
//			
//			
//			return st;
//		}
//		
//		public Boolean selecionar_folder_items() {
//			
//			try {
//				sk.getScreen().wait(ImgPatter.get("mn_item"),15).click();
//				sk.getScreen().wait(ImgPatter.get("smn_item"),15).doubleClick();
//				r.delay(15000);			
//				st = sk.getScreen().has(ImgPatter.get("img_Items"));
//				if(st.equals(true)) {
//				u.screenShotFull("scr");
//				}else {
//				u.screenShotFull("scr");
//				}
//			}
//				
//				catch (FindFailed e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			
//			return st;
//		}
//		
//		public Boolean selecionar_new_items() {
//			try {
//				sk.getScreen().wait(ImgPatter.get("action"),15).click();
//				sk.getScreen().wait(ImgPatter.get("slct_new_item"),15).click();
//				sk.getScreen().type(data.get("Subcategoria"+ Key.TAB));
//				sk.getScreen().type(data.get("Segmento"+ Key.TAB));
//				sk.getScreen().type(data.get("Subsegmento"+ Key.TAB));
//				sk.getScreen().wait(ImgPatter.get("slct_pack_Item"),15).click();
//				sk.getScreen().wait(ImgPatter.get("btn_ok"),15).click();
//				st = sk.getScreen().has(ImgPatter.get("img_new_item"));
//				if(st.equals(true)) {
//				u.screenShotFull("scr");
//				}else {
//				u.screenShotFull("scr");
//				}
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			
//			return st;
//		}
//		
//		public Boolean capturar_campos_item() {
//			try {
//				
//				sk.getScreen().wait(ImgPatter.get("slct_Item_type"),15).click();
//				sk.getScreen().wait(ImgPatter.get("op_PLU"),15).click();
//				sk.getScreen().type(Key.TAB + Key.TAB);
//				sk.getScreen().type(data.get("item_nombre")+ Key.TAB);		
//				sk.getScreen().type(data.get("item_desc")+ Key.TAB);		
//				sk.getScreen().type(data.get("item_sort")+ Key.TAB);		
//				sk.getScreen().type(data.get("item_type")+ Key.TAB);		
//				sk.getScreen().type(data.get("tran_level")+ Key.TAB);		
//				sk.getScreen().type(data.get("merchandise")+ Key.TAB);		
//				sk.getScreen().type(data.get("forecastable")+ Key.TAB);		
//				sk.getScreen().type(data.get("catch_weihgt")+ Key.TAB);		
//				sk.getScreen().type(data.get("constant_dimensions")+ Key.TAB);		
//				sk.getScreen().type(data.get("retail_zone")+ Key.TAB);		
//				sk.getScreen().type(data.get("cost_zone")+ Key.TAB);		
//				sk.getScreen().type(data.get("Selling Unit Retail")+ Key.TAB);		
//				sk.getScreen().type(data.get("Suggested Retail")+ Key.TAB);		
//				sk.getScreen().type(data.get("Store Order Multiple")+ Key.TAB);		
//				sk.getScreen().type(data.get("Standard UOM")+ Key.TAB);		
//				sk.getScreen().wait(ImgPatter.get("btn_groc_att"),15).click();
//				st = sk.getScreen().has(ImgPatter.get("img_item_af"));
//				if(st.equals(true)) {
//				u.screenShotFull("scr");
//				}else {
//				u.screenShotFull("scr");
//				}
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//
//			return st;
//		}
//		
//		public Boolean capturar_campos_package() {
//			try {
//				sk.getScreen().type(data.get("package RMS.Size")+ Key.TAB);
//				sk.getScreen().type(data.get("package RMS.UOM")+ Key.TAB);
//				sk.getScreen().wait(ImgPatter.get("btn_groc_att"),15).click();
//				st = sk.getScreen().has(ImgPatter.get("img_item_pkg"));
//				if(st.equals(true)) {
//				u.screenShotFull("scr");
//				}else {
//				u.screenShotFull("scr");
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			return st;
//		}
//		
//		public Boolean click_btn_supplier() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			return st;
//		}
//		
//		public Boolean click_btn_add() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean seleccionar_pais_origen_proveedor() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean llenar_seccion_dimensions() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean clic_btn_item_supplier_origin_country() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean desplegar_clic_btn_ok() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean clic_btn_location() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean select_campos_item_location() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean clic_submit() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean clic_approve() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean valida_tbl_item_bd() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean valida_tbl_POS_MODS() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean ejecuta_jobs_PP_POSDNLD_ITM() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean valida_tbl_POS_ITM_PRM_HEAD() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean valida_tbl_POS_MODS_STG() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean ejecuta_job_runITM() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean valida_tbl_POS_ITM_PRM_HEAD_doc() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
//		public Boolean valida_tbl_POS_OUTBOUND_DOCS() {
//			try {
//				
//			} catch (Exception e) {
//				// TODO: handle exception 
//			}
//			return st;
//		}
//		
	/**
	 * @author 1000068915
	 * @comments Toma un screenshot de la pantalla actual
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
	 * @comments Cierra la pantalla de RMS
	 */
	public void close_RMS() throws FindFailed {
		sk.getScreen().wait(ImgPatter.get("btn_close_itm"),15).highlight(1,"red").click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("mn_action"),5).highlight(1,"red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"),5).highlight(1,"red").click();
		
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
}
