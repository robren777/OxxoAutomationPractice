package RDM.rdmBara.ActualizacionRDMBaraCH51996086;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_Pantalla_para_conteos_en_linea_de_surtido_Hoja_de_Trabajo_Modificacion_Select_All
		extends BaseExecution {
			SeleniumUtil u = new SeleniumUtil(new IETest(),true);

	/**
	 * REREQUISITOS: 1. Tener acceso a la aplicación RDM 2. Mantener la resolución
	 * de pantalla en 100%
	 * 
	 * DESCRIPCION: El objetivo de la prueba es verificar el funcionamiento del
	 * botón Select All
	 * 
	 * @author JesusM3@hexaware.com
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Pantalla_para_conteos_en_linea_de_surtido_Hoja_de_Trabajo_Modificacion_Select_All_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES url = Link al aplicativo RDM user = usuario de acceso a RDM
		 * password = contraseña encriptada para acceder a RDM
		 */

		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));

		SikuliUtil sk = new SikuliUtil();
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);

		String pathImg = sk.PatternPath("RDM\\"+"CH51996086");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);

		// PASO 1
		addStep("Ingresar a la URL de RDM Bara:\r\n"
				+ "http://fcrdm05c.femcom.net:8059/. Ingresar username y password y presionar el botón de login");
		u.get(login.getUrl());
		u.hardWait(30);
		p.enviarTexto(userName);
		p.nTAB(1);
		p.enviarTexto(password);
		p.nTAB(1);
		u.hardWait(10);
		p.clickImagen("Login");
		testCase.addScreenShotCurrentStep("PrimerCaptura");
		u.hardWait(10);
		//Validando la entrada a RDM.
		try{
			p.clickImagen("ValidandoLogin");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la entrada a RDM.");
		}

		// Paso 2. Navegar hacia la hoja de trabajo
		addStep("Ingresar a la siguiente ruta: Inventory Management > XXFC - Modulo Conteo Cíclico > XXFC-Hoja de Trabajo     ");
		p.clickImagen("inventory_management");
		u.hardWait(10);
		p.clickImagen("xxfc_modulo_conteo_ciclico");
		u.hardWait(10);
		p.clickImagen("xxfc_hoja_de_trabajo");
		testCase.addScreenShotCurrentStep("SegundaCaptura");
		u.hardWait(30);
		try{
			p.clickImagen("ValidarHojaDeTrabajo");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar pantalla hoja de trabajo.");
		}

		// Paso 3. Aplica valores en los filtros para sortear la información en pantalla
		// y presiona el botón Ejecutar
		addStep("Aplica valores en los filtros para sortear la información en pantalla y presiona el botón Ejecutar");
		p.clickImagen("execute");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("TerceraCaptura");

		// Paso 4. Presionar el botón "Select All"
		addStep("Presionar el botón Select All");
		p.clickImagen("select_all");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		try{
			p.clickImagen("ValidandoElSelectAll");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la seleccion de items.");
		}

		// Paso 5. Presionar el botón "Select All" mientras todos los checkbox de los
		// registros están seleccionados
		addStep("Presionar el botón Select All mientras todos los checkbox de los registros están seleccionados");
		p.clickImagen("select_all");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("QuintaCaptura");
		try{
			p.clickImagen("ValidandoDesElSelectAll");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la desseleccion de items.");
		}
		

		// Paso 6. Presionar el botón "Select All"
		addStep("Presionar el botón Select All");
		p.clickImagen("select_all");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SextaCaptura");
		try{
			p.clickImagen("ValidandoElSelectAll");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la seleccion de items (paso 6).");
		}

		// Paso 7. Mientras todos los checkbox están seleccionados manualmente remueve
		// la selección de uno o mas checkbox, después presiona el botón "Select All"
		addStep("Mientras todos los checkbox están seleccionados manualmente remueve la selección de uno o mas checkbox, después presiona el botón Select All");
		p.clickImagen("checked_checkbox2");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SeptimaCaptura");
		p.clickImagen("select_all");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("OctavaCaptura");
		try{
			p.clickImagen("ValidarCheckboxBlanco");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la deseleccion unitaria.");
		}
		
		u.close();

	}

	@Override
	public void beforeTest() {
	}

	@AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Se tiene por objetivo el validar el funcionamiento del botón Select All";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Pantalla_para_conteos_en_linea_de_surtido_Hoja_de_Trabajo_Modificacion_Select_All_test";
	}

	@Override
	public String setTestInstanceID() {

		return null;
	}

}
