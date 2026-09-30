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

public class ATC_FT_018_No_confirmar_a_modificacion_de_registro extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	/**
	 * REREQUISITOS: 
	 *		1.Que existan registros en la vista XXFC_EO_ITEMS_V

	 	user = usuario de acceso a rdm bara password = contraseña encriptada para
		 * acceder registroContenedor = contenedor a validar facility = valor de
		 * facility distro = valor de distro job = información del job
	 * 
	 * DESCRIPCION: Validar funcionalidad de la elección 'No' al confirmar la modificación de un registro en la pantalla 'XXFC Empaque Óptimo'
	 * 
	 * @author JesusM3@hexaware.com
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_No_confirmar_a_modificacion_de_registro_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 * 
		 * user = usuario de acceso a rdm bara password = contraseña encriptada para
		 * acceder registroContenedor = contenedor a validar facility = valor de
		 * facility distro = valor de distro job = información del job
		 * vigencia ini (dp)
		 * casepack eo (dp)
		 */

		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String EO =data.get("caseEO");
		String vigenciaFin = data.get("vigencia");

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

		// Paso 4. Ingresar información a los filtros Artículo y Proveedor
		addStep("Ingresar información a los filtros Artículo y Proveedor ");
		p.clickImagen("execute");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Quinta Captura");
		u.hardWait(5);

		// Paso 5. Dar doble clic sobre el registro de la pantalla de resultados que se
		// desea modificar
		addStep("Dar doble clic sobre el registro de la pantalla de resultados que se desea modificar");
		p.doubleClickAndCopyTextBoxRight("modificar_reference", 100);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Sexta Captura");
		u.hardWait(5);

		/*
		 * Paso 6. "Capturar la nueva información para los campos: -Casepack EO
		 * -Vigencia Ini -Vigencia Fin "
		 */
		addStep("Capturar la nueva información para los campos: -Casepack EO\r\n"
				+ "		 * -Vigencia Ini -Vigencia Fin");
		p.enviarTexto(EO);
		u.hardWait(5);
		p.nTAB(1);
		p.nTAB(1);
		p.enviarTexto(vigenciaFin);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Septima Captura");
		u.hardWait(5);

		// Paso 7. Dar clic en el botón 'Guardar'
		addStep("Dar clic en el botón 'Guardar'");
		p.clickImagen("save");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Octava Captura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidandoLaPantallaDeCreacion");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla de creacion.");
		}

		// Paso 8.Seleccionar la opción 'No'
		addStep("Seleccionar la opción 'No'");
		p.clickImagen("save_no");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Novena Captura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidacionVueltaACreate");
			u.hardWait(10);
		}catch(Exception e){
			throw new AssertionError("No se pudo validar que el boton no, regrese a la pantalla auxiliar");
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
		return "Validar funcionalidad de la elección 'No' al confirmar la modificación de un registro en la pantalla 'XXFC Empaque Óptimo' ";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_018_No_confirmar_a_modificacion_de_registro_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
