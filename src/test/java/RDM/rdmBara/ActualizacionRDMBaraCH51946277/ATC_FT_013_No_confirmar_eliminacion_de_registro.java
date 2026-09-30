package RDM.rdmBara.ActualizacionRDMBaraCH51946277;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_013_No_confirmar_eliminacion_de_registro extends BaseExecution {
	SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	/**
	 * REREQUISITOS: 
	 *		1.Que existan registros en la vista XXFC_EO_ITEMS_V
	 * 
	 * DESCRIPCION: Validar funcionalidad de la elección 'No' al confirmar la eliminación de un registro en la pantalla 'XXFC Empaque Óptimo'
	 * 
	 * @author JesusM3@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_No_confirmar_eliminacion_de_registro_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES url = Link al aplicativo RDM user = usuario de acceso a RDM
		 * password = contraseña encriptada para acceder a RDM
		 */

		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));

		SikuliUtil sk = new SikuliUtil();
		

		String pathImg = sk.PatternPath("RDM\\"+"CH51946277");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);

		// PASO 1
		addStep("Ingresar a la URL de RDM Bara");
		u.get(login.getUrl());
		u.hardWait(30);
		p.enviarTexto(userName);
		p.nTAB(1);
		p.enviarTexto(password);
		p.nTAB(1);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("PrimerCaptura");
		p.clickImagen("Login");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SegundaCaptura");
		try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }

		// Paso 2. Ingresar a la ruta Support Functions / Item Setup / XXFC Item Master
		// Editor
		addStep("Ingresar a la ruta Support Functions / Item Setup / XXFC Item Master Editor");
		p.clickImagen("support_functions");
		u.hardWait(10);
		p.clickImagen("item_setup");
		u.hardWait(10);
		p.clickImagen("xxfc_item_master_editor");
		testCase.addScreenShotCurrentStep("TerceraCaptura");
		u.hardWait(20);
		try{
			p.clickImagen("ValidarPantallaItemMaster");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla principal item master editor.");
		}

		// Paso 3. Dar clic en el botón 'Optimal PACK'
		addStep("Dar clic en el botón 'Optimal PACK'");
		p.clickImagen("optimal_pack");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidarPantallaEmaqueOptimo");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla principal item master editor.");
		}

		// * Paso 4. Ingresar información a los filtros Artículo y Proveedor
		addStep("Ingresar información a los filtros Artículo y Proveedor ");
		p.clickImagen("execute");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Cuarta Captura");
		u.hardWait(5);

		// preparar registros a eliminar
		p.clickImagen("select_all");
		u.hardWait(10);
		p.clickImagen("ejecutar_demanda");
		u.hardWait(10);

		// Paso 5. Marcar mediante el checkbox el registro a eliminar que
		// tenga el mismo valor en los campos 'casepack' y 'casepack EO'
		addStep("Marcar mediante el checkbox el registro a eliminar que "
				+ "tenga el mismo valor en los campos 'casepack' y 'casepack EO' ");
		p.clickImagen("double_down");
		u.hardWait(10);
		p.clickImagen("blue_checkbox");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SextaCaptura");
		u.hardWait(10);

		// Paso 6. Dar clic en el botón 'Eliminar'
		addStep("Dar clic en el botón 'Eliminar' ");
		p.clickImagen("delete_record");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Septima Captura");
		u.hardWait(5);

		// Paso 7. Seleccionar la opción 'No'
		addStep("Seleccionar la opción 'No'");
		testCase.addScreenShotCurrentStep("Septima Captura");
		p.clickImagen("delete_no");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Octava Captura");
		//validando que regrese a la pantalla optimal pack
		try{
			p.clickImagen("ValidarPantallaItemMaster");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla principal item master editor.");
		}
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}

	@Override
	public String setPrerequisites() {
		return "Que existan registros en la vista XXFC_EO_ITEMS_V";
	}

	@Override
	public String setTestDescription() {
		return "Validar funcionalidad de la elección 'No' al confirmar la eliminación de un registro en la pantalla 'XXFC Empaque Óptimo' ";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_No_confirmar_eliminacion_de_registro_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
