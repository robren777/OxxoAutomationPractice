package RDM.rdmBara.ActualizacionRDMBaraCH51996086;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class ATC_FT_012_PantallaLineaDeSurtidoModificacionAdjust_AC_RDM_Bara extends BaseExecution {
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	/**
	 * REREQUISITOS: 
	 *		1. Acceso a la base de datos Bara: 
	 *			Connection Name: BARA QA
	 *			Hostname: fcrdm05c.femcom.net
	 *			Port: 1535
	 *			SID: FCRDM59Q
	 * 		2. Acceso a RDM-BARA
	 *      3. Claves de accesso de RDM-BARA
	 * 
	 * DESCRIPCION: El objetivo de la prueba es realizar validaciones en la BD: RDM BARA QA en las cuales se valida el correcto funcionamiento de el adjust dentro de la pantalla
	 * de Hoja de trrabajo
	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_PantallaLineaDeSurtidoModificacionAdjust_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * 
		 * user = usuario de acceso a rdm bara
		 * password = contraseña encriptada para acceder 
		 * registroContenedor = contenedor a validar
		 * facility = valor de facility
		 * distro = valor de distro
		 * job = información del job
	    */
		
		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String existencia = data.get("existencia");
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH51996086");
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
		
		addStep("1: Ingresar a RDM");
		u.get(login.getUrl());	
		testCase.addScreenShotCurrentStep(u, "PrimerCaptura");
		u.hardWait(30);
		addStep("Ingresar a usuario y password");
	    p.enviarTexto(userName);
	    p.nTAB(1);
	    u.hardWait(10);
	    p.enviarTexto(password);
	    p.nTAB(1);
	    u.hardWait(10);
	    p.clickImagen("Login");
	    u.hardWait(15);
	    
	    /*
		 * PASO 2: INGRESAR A LA RUTA DE HOJA DE TRABAJO
		*/
	    
	    addStep("2: Ingresar a la ruta seleccionada");
	    p.clickImagen("inventoryManagment");
	    u.hardWait(15);
	    p.clickImagen("ModuloConteoCiclico");
	    u.hardWait(15);
	    p.clickImagen("HojaDeTrabajo");
	    u.hardWait(15);
	    
	    /*
		 * PASO 3: EJECUTAR EL QUERY CON LOS FILTROS PREDETERMINADOS
		*/
	    
	    addStep("3 Seleccionar ejecutar query");
	    p.clickImagen("PalomitaRoja");
	    u.hardWait(5);
	    String location1 = p.doubleClickAndCopyTextBoxRight("Checkbox", 25);
	    u.hardWait(10);
	    
	    /*
		 * PASO 4: SELECCIONAR TODOS LOS REGISTROS 
		*/
	    
	    addStep("4 Pulsar el boton Select all");
	    u.hardWait(5);
	    
	    /*
		 * PASO 5: AGREGAR UN VALOR A UNA CANTIDAD EN UN ARTICULO
		*/
	    
	    
	    addStep("5 Capturar numerico en existencias");
	    p.clickImagen("Existencia");
	    u.hardWait(5);
	    p.enviarTexto(existencia);
	    u.hardWait(5);
	    String articulo = p.doubleClickAndCopyTextBoxRight("Checkbox", 1000);
	    u.hardWait(5);
	    String location = p.doubleClickAndCopyTextBoxRight("Checkbox", 1000);
	    u.hardWait(10);
	    
	    /*
		 * PASO 6: PRESIONAR EL BOTON TAB
		*/
	    
	    
	    addStep("6 presionar el boton tab");
	    p.nTAB(1);
	    u.hardWait(10);
	    
	    /*
	  		 * PASO 7: SELECCIONAR UN PRODUCTO
	  	*/
	    
	    addStep("7 Seleccionar el checkbox");
	    p.clickImagen("Checkbox");
	    u.hardWait(5);
	    
	    /*
  		 * PASO 8: PRESIONAR EL BOTON PARA AJUSTAR EL INVENTARIO
  		*/
	    
	    addStep("8 presionar el boton Adjust");
	    p.clickImagen("Adjust");
	    u.hardWait(5);
	    u.hardWait(10);
	    p.clickImagen("OkButton");
	    
	    /*
  		 * PASO 8: VALIDAR CAMBIOS EN LA BASE DE DATOS
  		*/
	    
	    addStep("9 Validar los steps");
	    String querty1 = "Select UNIT_QTY from INV_ADJUSTMENT_TO_UPLOAD where ITEM_ID = '" + articulo + "'";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);
	   
	    String querty2 = "Select CYCLE_COUNT_STATUS from LOCATION where LOCATION_ID =  '"+location + "'";
	    utils.sql.SQLResult resultadoContenedor2= dbBARAqa.executeQuery(querty2);
	    
	    if(!resultadoContenedor2.getFirstData().equals("OK"))
	    	throw new RuntimeException("Fallido");
	    
	    String querty3 = "Select CYCLE_COUNT_DATE from LOCATION where LOCATION_ID =  '"+location + "'";
	    utils.sql.SQLResult resultadoContenedor3= dbBARAqa.executeQuery(querty3);
	    
	    LocalDateTime fechaActual = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String fechaActualFormateada = fechaActual.format(formatter);
	    
	    if (!resultadoContenedor3.getFirstData().substring(0, 9).equals(fechaActualFormateada.substring(0, 9))) 
	    	throw new RuntimeException("Fallido");
	    
	    String querty4 = "Select UNIT_QTY from PICK_FROM_LOC where ITEM_ID = '" + articulo + "'";
	    utils.sql.SQLResult resultadoContenedor4= dbBARAqa.executeQuery(querty4);
	    
	    if(resultadoContenedor4.getFirstData() == resultadoContenedor3.getFirstData())
	    	throw new RuntimeException("Fallido");
	    
	    String querty5 = "Select ITEM_ID, TO_LOCATION_ID, USER_PICK, UNITS_PROCESSED, ACTIVITY_CODE from xxfc_labor_productivity where ITEM_ID = '" + articulo+ "'";
	    utils.sql.SQLResult resultadoContenedor5= dbBARAqa.executeQuery(querty5);

	    
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
		return "1 Acceso a la base de datos Bara Connection Name: BARA QA Hostname: fcrdm05c.femcom.net Port: 1535 SID: FCRDM59Q 2. Acceso a RDM-BARA 3. Claves de accesso de RDM-BARA";
	}

	@Override
	public String setTestDescription() {
		return "El objetivo de la prueba es realizar validaciones en la BD: RDM BARA QA en las cuales se valida el correcto funcionamiento de el adjust dentro de la pantalla de Hoja de trrabajo";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_PantallaLineaDeSurtidoModificacionAdjust_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
