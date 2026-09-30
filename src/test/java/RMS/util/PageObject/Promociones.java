package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import com.ibm.icu.util.Calendar;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class Promociones {

	@SuppressWarnings("unused")
	private static final TestCase TestCase = null;
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
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static String df;

	@SuppressWarnings("static-access")
	public Promociones(SikuliUtil sk, SeleniumUtil u, String cb, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			Boolean st, HashMap<String, String> data) throws SQLException {
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
	}

	/**
	 * @param cb
	 * @author 72843
	 * @Comments regresa un String con el status de la promoci�n
	 * @throws query para consultar estatus de la promoci�n en la tabla PROMHEAD
	 * 
	 */
	private String ValidatedApproving() {
		String SQLQuery = "SELECT STATUS FROM PROMHEAD WHERE PROMOTION = " + cb;
//		String SQLQuery = "SELECT STATUS FROM PROMHEAD WHERE PROMOTION = 4555167";	
//		System.out.println(SQLQuery.toString());		
		SQLResult res = null;
		try {
			res = dbRms.executeQuery(SQLQuery);
			System.out.println(res.getData(0, 0).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res.getData(0, 0).toString();

	}

	/**
	 * @author 72843
	 * 
	 * @return status
	 * @Comments valida el estatus de la promoci�n en la base de datos de RMS
	 */
	public String Validar_DB() {

		do {
			String rest = ValidatedApproving();
			r.delay(5000);
			status = rest;
			System.out.println("estatus es: " + status);

		} while (status.equals("I"));

		return status;
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
//			patterns.add( new Pattern(s.getAbsolutePath()).exact());			
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

	private void OpenBrowser() {
		try {
//			System.out.println(data.get("rms_url"));
			u.get(data.get("rms_url"));
		} catch (Exception e) {
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
	 * @Comments realiza loguin en RMS
	 * @txt_Promosiones_simples_ItemList
	 */
	public void Login_En_RMS() {
		try {
			ImgPatter = GetPatternsImg(Fol);
			OpenBrowser();
			r.delay(15000);
			sk.getScreen().type(data.get("rms_user"));
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
			sk.getScreen().type(Key.ENTER);
			r.delay(15000);
			sk.getScreen().wait(ImgPatter.get("pricing"), 15).highlight(1, "red").click();
			sk.getScreen().wait(ImgPatter.get("promotions"), 15).doubleClick();
			sk.getScreen().capture().save("img.png");
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author 72843
	 * @Comments crea una nueva promoci�n en RMS
	 * @return
	 * @throws FindFailed
	 * @txt_Promosiones_simples_ItemList
	 */
	public String Crear_Promocion() throws FindFailed {
		try {

			sk.getScreen().wait(ImgPatter.get("action"), 15).click();
			sk.getScreen().wait(ImgPatter.get("neww"), 15).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok"), 15).click();
			sk.getScreen().wait(ImgPatter.get("box_promo").similar((float) .95), 15).click();
			r.delay(5000);
			sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
			r.delay(1000);
			sk.getScreen().keyDown(Key.CTRL);
			sk.getScreen().keyDown("c");
			sk.getScreen().keyUp("c");
			sk.getScreen().keyUp(Key.CTRL);
			cb = App.getClipboard();
			u.screenShotFull("full");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cb;
	}

	/**
	 * @author 72843
	 * @return datos de promoci�n simple
	 * @Comments Configure permission simple Item List
	 * @txt_Promosiones_simples_ItemList
	 */
	public void Configurar_Promocion_Simple_Item_List() {
		try {

			configurePROMHEADRMS();

			ConfigurepromstRMS();

			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_il"), 5).click();
			sk.getScreen().type(data.get("item") + Key.TAB);
			sk.getScreen().type(data.get("budgeted") + Key.TAB);
			sk.getScreen().wait(ImgPatter.get("slct_pct"), 5).click();
			sk.getScreen().wait(ImgPatter.get("itm_fp"), 5).click();
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("amount"));
			sk.getScreen().wait(ImgPatter.get("btn_ok1"), 5).click();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ingresa la informaci�n en la ventana de la PROMHEAD
	 */
	private void configurePROMHEADRMS() {
		try {
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("nombre_promo"));
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("description"));
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("event"));
			sk.getScreen().type(Key.TAB);
			// Si se reguqire una baja, ingresa la fecha actual como fecha inicio y fecha
			// fin de la promocion
			if (data.get("name").contains("baja")) {
				String f = fechas(0);
				sk.getScreen().type(f + Key.TAB);
				sk.getScreen().type(f + Key.TAB);

			}
			sk.getScreen().find(ImgPatter.get("box_promoType")).right(80).click();
			// valida si es necesario algun identificador de la promoci�n
			if (!data.get("promType").equals("")) {
				sk.getScreen().type(data.get("promType") + Key.TAB);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @author 72843
	 * @comments ingresa la fecha del mismo d�a en fecha inicio y fecha fin de la
	 *           promoci�n en RMS
	 */
	private String fechas(int dias) {
		try {
			LocalDateTime fecha = LocalDateTime.now();
			DateTimeFormatter dt = DateTimeFormatter.ofPattern("ddMMyy");
			formatDate = fecha.format(dt);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return formatDate;
	}
	
	/**
	 * @author 1000072843
	 * ingresa fecha en formato String
	 * @param d
	 * @return
	 */
	private String setDatestr(int d) {
		try {
			Date currentDate = new Date();
			Calendar c = Calendar.getInstance();

			c.setTime(currentDate);

			c.add(Calendar.DATE, d);

			Date currendateSet = c.getTime();

			df = dateFormat.format(currendateSet);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return df;
	}

	/**
	 * @Comments ingresa la infoemaci�n de las tiendas a considerar en la promoci�n
	 *           en la ventana promst
	 */
	private void ConfigurepromstRMS() {

		try {
			sk.getScreen().wait(ImgPatter.get("btn_store"), 5).click();
			sk.getScreen().wait(ImgPatter.get("slct_groupt"), 5).click();
			sk.getScreen().wait(ImgPatter.get("item_store"), 5).click();
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("ValueStore") + Key.TAB);
			sk.getScreen().wait(ImgPatter.get("btn_apply"), 5).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok"), 5).click();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * @Comments realiza la promoci�n simple Item Percetn off, amhound off, fixed
	 *           off
	 */
	public void configurePromSI() {
		try {
			configurePROMHEADRMS();

			ConfigurepromstRMS();

			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_si"), 5).click();
			sk.getScreen().type(data.get("item"));
			r.delay(2000);
			sk.getScreen().find(ImgPatter.get("lbl_chngtype")).below(20).click();
			sk.getScreen().wait(ImgPatter.get("itm_po_1"), 5).click();
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("changeamound") + Key.TAB);
			r.delay(2000);
			sk.getScreen().type(Key.UP + Key.UP + Key.UP + Key.UP + Key.TAB);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_ok"), 5).click();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @author 72843
	 * @Comments realiza el Submit de una promo en RMS
	 * @txt_Promosiones_simples_ItemList
	 */
	public void Submit_Promocion() {
		try {
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_sbmt"), 5).click();
			r.delay(3000);
			sk.getScreen().wait(ImgPatter.get("btn_No"), 5).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"), 5).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 5).click();
			sk.getScreen().wait(ImgPatter.get("btn_close"), 5).click();
			sk.getScreen().wait(ImgPatter.get("mn_action"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_close"), 5).click();
			r.delay(3000);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

	/**
	 * @author 72843
	 * @Comments realiza la aprobaci�n de una promocion en RMS
	 * @txt_Promosiones_simples_ItemList
	 */
	public void Aprovar_Promocion() {

		try {
			Login_En_RMS();
			r.delay(9000);
			sk.getScreen().find(ImgPatter.get("lbl_action")).right(60).click();
			sk.getScreen().wait(ImgPatter.get("itm_edit_promo"), 5).click();
			r.delay(1000);
			sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
			sk.getScreen().type("4555167" + Key.TAB);
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 5).click();
			r.delay(3000);

			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("itm_approve"), 5).click();

			do {

				st = sk.getScreen().has(ImgPatter.get("btn_mdl_yes"));
				System.out.println(st.toString());
				r.delay(59000);
				System.out.println("entro");
			} while (st.equals(false));

			sk.getScreen().wait(ImgPatter.get("btn_No"), 5).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"), 5).click();
			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"), 5).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 5).click();
			sk.getScreen().wait(ImgPatter.get("btn_close"), 5).click();
			sk.getScreen().wait(ImgPatter.get("mn_action"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_close"), 5).click();
//			CloseBrowser();
//			
//			r.delay(3000);
//			sk.getScreen().wait(ImgPatter.get("btn_No"),5).click();
//			r.delay(1000);
//			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"),5).click();

//			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"),5).click();
//			sk.getScreen().type("TODOS");
//			sk.getScreen().type(Key.TAB);
//			sk.getScreen().type("TODOS");
//			sk.getScreen().type(Key.TAB);
//			sk.getScreen().type("TODOS");
//			sk.getScreen().type(Key.TAB);
//			sk.getScreen().type(Key.ENTER); 
//			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes1"),5).click();
//			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes1"),5).click();
//			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"),5).click();
//			
//			sk.getScreen().find(ImgPatter.get("lbl_action")).right(60).click();
//			sk.getScreen().wait(ImgPatter.get("itm_edit_promo"),5).click();
//			r.delay(1000);
//			sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
//			sk.getScreen().type(cb + Key.TAB);
//			r.delay(2000);
//			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"),5).click();
//			r.delay(1000);
//			sk.getScreen().find(ImgPatter.get("lbl_status")).right(60).click();
//			r.delay(1000);
//			sk.getScreen().keyDown(Key.CTRL);
//			sk.getScreen().keyDown("c");
//			sk.getScreen().keyUp("c");
//			sk.getScreen().keyUp(Key.CTRL);
//			status = App.getClipboard();
//			
//			while (status.contains("Approve In Progress")) {
//				r.delay(59000);
//				sk.getScreen().wait(ImgPatter.get("btn_ok"),5).click();
//				sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"),5).click();
//				r.delay(1000);
//				sk.getScreen().find(ImgPatter.get("lbl_status")).right(60).click();
//				r.delay(1000);
//				sk.getScreen().keyDown(Key.CTRL);
//				sk.getScreen().keyDown("c");
//				sk.getScreen().keyUp("c");
//				sk.getScreen().keyUp(Key.CTRL);
//				status = App.getClipboard();				
//			}
//			r.delay(3000);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
//		return status;

	}

}