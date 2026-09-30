package RDM.rdmBara.ActualizacionRDMBaraCH51996732;

import static org.testng.Assert.assertTrue;

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

public class ATC_FT_007_SeguimientoAuditoriasDeAlmacenaje_ValidacionBotonDelete_AC_RDM_Bara extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(),true);

	/**
	 * REREQUISITOS: 
	 *		1. Filtros seleccionados 
	 *		2. Los registros filtrados
	 *		3. Boton delete

	 	   user = usuario de acceso a rdm bara
		 * password = contraseña encriptada para acceder 
		 * registroContenedor = contenedor a validar
		 * facility = valor de facility
		 * distro = valor de distro
		 * job = información del job
	 * 
	 * DESCRIPCION: Validar la correcta funcionalidad del boton delete dentro de la pantalla de seguimiento de auditorias de almacenaje
	 * 
	 * @author franciscot2@hexaware.com
	 */
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_SeguimientoAuditoriasDeAlmacenaje_ValidacionBotonDelete_AC_RDM_Bara_test (HashMap<String, String> data) throws Exception {
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
		
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH51996732");
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
	    /*
		 * PASO 2: INGRESAR A LA RUTA DE HOJA DE AUDITORIA DE ALMACENAJE 
		*/
	    
	    addStep("Ingresar a la ruta seleccionada");
	    p.clickImagen("inventoryManagment");
	    u.hardWait(15);
	    p.clickImagen("ModuloConteoCiclico");
	    u.hardWait(15);
	    testCase.addScreenShotCurrentStep("SegundaCaptura");
	    u.hardWait(5);
	    p.clickImagen("ProgConteoAlmacen");
	    u.hardWait(15);
	    /*
		 * PASO 3: Seleccionar mes, anio y presionar boton ejecutar  
		*/
	    addStep("Ejecutar un query");
	    p.clickImagen("EjecutarQuery");
	    testCase.addScreenShotCurrentStep("TerceraCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 4: Seleccionar un pasillo de las ultimas zonas 
		*/
	    addStep("Seleccionar un pasillo de las ultimas zonas y auditar");
	    p.clickImagen("ScDown");
	    u.hardWait(10);
	    p.clickImagen("UltimoPasillo");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("CuartaCaptura");
	    p.clickImagen("Auditar");
	    u.hardWait(10);
	    /*
		 * PASO 5: Seleccionar filtros
		*/
	    addStep("Seleccionar filtros");
	    testCase.addScreenShotCurrentStep("QuintaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 6: Insertar 0 en un campo  
		*/
	    addStep("Insertar 0 en un campo de existencia de cajas");
	    p.doubleClickAndCopyTextBoxRight("location", 20);
	    u.hardWait(10);
	    String id = p.doubleClickAndCopyTextBoxRight("location", 20);
	    u.hardWait(10);
	    p.clickImagen("ExistCaja");
	    u.hardWait(10);
	    p.enviarTexto("0");
	    u.hardWait(5);
	    p.nTAB(1);
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("SextaCaptura");
	    u.hardWait(5);
	    /*
		 * PASO 7: Seleccionar el error de no esta en la tarima
		*/
	    addStep("Seleccionar el error de no esta en la tarima");
	    p.clickImagen("errorMenu");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("SeptimaCaptura");
	    p.clickImagen("ErrorOk");
	    u.hardWait(10);
	    /*
		 * PASO 8: Seleccionar el dato y el boton adjust
		*/
	    addStep("Seleccionar el dato y el boton adjust");
	    p.clickImagen("TextBoxRegis");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("octavaCaptura");
	    u.hardWait(5);
	    p.clickImagen("AdjustBt");
	    u.hardWait(10);
	    /*
		 * PASO 9: Seleccionar el valor inventario fisico y dar ok
		*/
	    addStep("Seleccionar el valor inventario fisico y dar ok");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("NovenaCaptura");
	    u.hardWait(5);
	    /*
		 * PASO 10: dar ok
		*/
	    addStep("dar ok");
	    p.clickImagen("OkBtnRc");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("DecimaCaptura");
	    u.hardWait(5);
	    /*
		 * PASO 11: Validacion en la base de datos como eliminado
		*/
	    addStep("Validacion en la base de datos como eliminado");
	    String querty1 = "select COUNT(*) from rdm100.CONTAINER where container_id = '" + id + "'";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);	    
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
	    
	    String querty2 = "select COUNT(*) from rdm100.CONTAINER_ITEM where container_id = '" +id +"' " ;
	    utils.sql.SQLResult resultadoContenedor2= dbBARAqa.executeQuery(querty2);	    
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor2);
	    /*
		 * PASO 12: Salir de la pantalla auditoria
		*/
	    addStep("Salir de la pantalla auditoria");
	    u.hardWait(10);
	    //p.clickImagen("OkAlertBtn");
	    u.hardWait(10);
	    p.clickImagen("SalidaBtn");
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("OnceavaCaptura");
	    u.hardWait(5);
	    /*
		 * PASO 13: Seleccionar el mismo pasillo
		*/
	    addStep("Seleccionar el mismo pasillo");
	    p.clickImagen("ScDown");
	    u.hardWait(10);
	    p.clickImagen("UltimoPasillo");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("DoceavaCaptura");
	    p.clickImagen("Auditar");
	    u.hardWait(10);
	    /*
		 * PASO 14 : Buscar el mismo valor anteriormente seleccionado
		*/
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("Treceava");
	    u.hardWait(5);
	    p.doubleClickAndCopyTextBoxRight("location", 20);
	    u.hardWait(10);
	    String id2 = p.doubleClickAndCopyTextBoxRight("location", 20);
	    boolean flag = false; 
	    if(id2!=id)
	    	flag=true;
	    assertTrue(flag, "El campo eliminado anteriormente no existe");
	    u.hardWait(5);
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
		return "1. Filtros seleccionados \r\n"
				+ "	 		2. Los registros filtrados\r\n"
				+ "	 		3. Boton delete";
	}

	@Override
	public String setTestDescription() {
		return "Validar la correcta funcionalidad del boton delete dentro de la pantalla de seguimiento de auditorias de almacenaje";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_SeguimientoAuditoriasDeAlmacenaje_ValidacionBotonDelete_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
