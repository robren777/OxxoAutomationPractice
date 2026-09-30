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

public class ATC_FT_008_NoCrearUnRegistroConCamposObligatorios_AC_RDM_Bara extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1.Acceso a RDM Bara
			Vigencia inicial (Fecha)
			Casepack EO
	 * 
	 * DESCRIPCION: Validar no crear un nuevo registro al omitir la captura de los datos marcados como obligatorios: 
	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_NoCrearUnRegistroConCamposObligatorios_AC_RDM_Bara_test (HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * 
		 Validar no crear un nuevo registro al omitir la captura de los datos marcados como obligatorios: 

		Vigencia Ini 
		Casepack EO 
	    */
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH51987957");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);
		
		/*
		 * PASO 1: INGRESAR A RDM
		*/
		
		addStep("Ingresar a RDM");
		u.get(login.getUrl());	
		u.hardWait(30);
		addStep("Ingresar a usuario y password");
	    p.enviarTexto(userName);
	    p.nTAB(1);
	    u.hardWait(10);
	    p.enviarTexto(password);
	    p.nTAB(1);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("PrimerCaptura");
	    p.clickImagen("Login");
	    u.hardWait(15);
		try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }
	    /*
		 * PASO 2: INGRESAR A LA RUTA DE HOJA DE AUDITORIA DE ALMACENAJE 
		*/
	    
	    addStep("Ingresar a la ruta seleccionada");
	    p.clickImagen("supportF");
	    u.hardWait(15);
	    p.clickImagen("ItemS");
	    u.hardWait(15);
	    p.clickImagen("itemME");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    u.hardWait(15);
		try{
			p.clickImagen("ValidarPantallaItemMaster");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla principal item master editor.");
		}
	    /*
		 * PASO 3: Dar click en el boton OPTIMAL PACK
		*/
		
		addStep("Dar click en el boton OPTIMAL PACK");
		p.clickImagen("optimalP");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("TerceraCaptura");
		u.hardWait(15);
		try{
			p.clickImagen("ValidarPantallaEmaqueOptimo");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla empaque optimo.");
		}
		/*
		 * PASO 4: Dar click en el boton crear
		*/
		addStep("Dar click en el boton crear");
		p.clickImagen("btn_CreateRecord");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidarPantallaAuxiliarCrear");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla auxiliar de crear.");
		}
		/*
		 * PASO 5: Seleccionar un articulo para crear
		*/
		addStep("Seleccionar un articulo para crear");
		p.doubleClickAndCopyTextBoxRight("btn_SelectArt", 5);
		//p.clickImagen("btn_SelectArt");
		u.hardWait(15);
		p.clickImagen("btn_okArt");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("QuintaCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 6: Omitir la captura de campoos
		*/
		addStep("Omitir la captura de campos");
		testCase.addScreenShotCurrentStep("SextaCaptura");
		u.hardWait(5);
		
		
		/*
		 * PASO 7: Dar click en guardar
		*/
		p.clickImagen("saveBtn");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SeptimaCaptura");
		u.hardWait(10);
		try{
			p.clickImagen("ValidarLaQueLosCampos");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar el error.");
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
		return "Acceso a RDM Bara";
	}

	@Override
	public String setTestDescription() {
		return "Validar no crear un nuevo registro al omitir la captura de los datos marcados como obligatorios: \r\n"
				+ "\r\n"
				+ "Vigencia Ini \r\n"
				+ "Casepack EO ";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_NoCrearUnRegistroConCamposObligatorios_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
