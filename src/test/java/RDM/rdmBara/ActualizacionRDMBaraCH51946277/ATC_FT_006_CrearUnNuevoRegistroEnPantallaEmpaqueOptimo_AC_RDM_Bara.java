package RDM.rdmBara.ActualizacionRDMBaraCH51946277;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_CrearUnNuevoRegistroEnPantallaEmpaqueOptimo_AC_RDM_Bara extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1.Acceso a RDM Bara
	 		registro de oc fija en la base de datos.
			 casepack eo nuevo (dp)
	 * 
	 * DESCRIPCION: Crear un nuevo registro dentro de la pantalla de 'XXFC Empaque Óptimo' mediante pantalla auxiliar. 
	 * 
	 * @author franciscot2@hexaware.com
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_CrearUnNuevoRegistroEnPantallaEmpaqueOptimo_AC_RDM_Bara_test (HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * casepack eo nuevo (dp)
		 Validar que al seleccionar uno de los registros de la pantalla de resultados éste se seleccione en color azul. 
	    */
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String EO =data.get("caseEO");
		
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
		addStep("Seleccionar un articulo para cre");
		p.ClickAndCopyTextBoxRight("btn_SelectArt", 5);
		u.hardWait(15);
		p.clickImagen("btn_okArt");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("QuintaCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 6: Capturar el campo 'Casepack EO'
		*/
		addStep("Capturar el campo Casepack EO");
		p.ClickAndCopyTextBoxRight("txb_Eoc", 20);
		u.hardWait(10);
		p.enviarTexto(EO);
		testCase.addScreenShotCurrentStep("SextaCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 7: Seleccionar el campo vigencia inical
		*/
		addStep("Seleccionar el campo vigencia inical");
		p.doubleClickAndCopyTextBoxRight("selectVigencia", 5);
		u.hardWait(10);
//		p.clickImagen("slctFecha");
//		u.hardWait(10);
//		p.clickImagen("btnOkFecha");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SeptimaCaptura");
		u.hardWait(5);
		
		
		/*
		 * PASO 8: Seleccionar el campo vigencia final
		*/
		addStep("Seleccionar el campo vigencia final");
		p.doubleClickAndCopyTextBoxRight("selectVigenciaF", 5);
		u.hardWait(10);
//		p.clickImagen("slctFechaF");
//		u.hardWait(10);
		//p.clickImagen("btnOkFecha");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("OctavaCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 9: Seleccionar boton save
		*/
		addStep("Seleccionar boton save");
		p.clickImagen("saveBtn");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("NovenaCaptura");
		u.hardWait(5);
		
		/*
		 * PASO 10: Confirmar el guardado
		*/
		addStep("Confirmar el guardado");
		p.clickImagen("btnYes");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("DecimaCaptura");
		u.hardWait(5);
		u.close();
		
		/*
		 * PASO 11: Confirmar la vista RDM100.XXFC_EO_ITEMS_V
		*/
		addStep("Consultar la vista RDM100.XXFC_EO_ITEMS_V");	
	    String querty1 = "  Select ITEM_ID,DESCRIPTION,STD_CASEPACK from RDM100.XXFC_EO_ITEMS_V";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
	    boolean flag = false; 
	    if(!resultadoContenedor1.isEmpty())
	    	flag=true;
	    assertTrue(flag, "El registro existe en la BD");
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
		return "Crear un nuevo registro dentro de la pantalla de 'XXFC Empaque Óptimo' mediante pantalla auxiliar.  ";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_CrearUnNuevoRegistroEnPantallaEmpaqueOptimo_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
