package RDM.rdmBara.ActualizacionRDMBaraCH51996732;

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

public class ATC_FT_001_SeguimientodeAuditoriasdeAlmacenaje_Validaciondecampos_AC_RDM_Bara extends BaseExecution{
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
	 *      4. Mes actual
	 *      5. Anio actual
	 *      6. Zona
	 *      7. Pasillo de almacenaje
	 *      8. Numero de auditorias al mes
	 * 
	 * DESCRIPCION: El objetivo de la prueba es realizar validaciones en la BD: RDM BARA QA en las cuales se valida el correcto funcionamiento de los campos en las pantallas
	 * de seguimiento de auditorias de almacenaje 
	 * 
	 * @author franciscot2@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_SeguimientodeAuditoriasdeAlmacenaje_Validaciondecampos_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
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
		String zona1 = data.get("Zona");
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
		 * PASO 3: VALIDAR QUE SE MUESTRE EN NOMBRE CORRECTO DE LA PANTALLA    
		*/
	    addStep("Validar el nombre de la pantalla");
	    p.clickImagen("SeguimientoDeADA");
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("TerceraCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 4: VALIDAR QUE EL CAMPO MES SEA SELECCIONABLE Y LIMPIAR 
		*/
	    
	    addStep("Validar el campo mes seleccionable y limpiar el mes");
	    p.doubleClickAndCopyTextBoxRight("Mes", 5);
	    u.hardWait(10);
	    p.clickImagen("ClearScreen");
	    testCase.addScreenShotCurrentStep("CuartaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 5: VALIDAR QUE EL CAMPO ANIO SEA SELECCIONABLE Y LIMPIAR
		*/
	    
	    addStep("Validar el campo anio seleccionable y limpiar el mes");
	    p.doubleClickAndCopyTextBoxRight("Anio", 10);
	    u.hardWait(10);
	    p.clickImagen("ClearScreen");
	    testCase.addScreenShotCurrentStep("QuintaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 6: VALIDAR QUE EL CAMPO MES SEA SELECCIONABLE Y LIMPIAR  
		*/
	    
	    addStep("Validar el campo mes seleccionable y limpiar el mes");
	    p.doubleClickAndCopyTextBoxRight("Mes", 10);
	    u.hardWait(10);
	    p.clickImagen("ClearScreen");
	    testCase.addScreenShotCurrentStep("SextaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 7: VALIDAR QUE LA ZONA NO SEA EDITABLE
		*/
	    
	    addStep("Validar el campo zona no sea editable");
	    p.clickImagen("EjecutarQuery");
	    u.hardWait(10);
	    p.doubleClickAndCopyTextBoxRight("Zona", 20);
	    u.hardWait(10);
	    p.enviarTexto(zona1);
	    testCase.addScreenShotCurrentStep("SeptimaCaptura");
	    u.hardWait(10);
	    
	    /*
		 * PASO 8: VALIDAR QUE LA DESCRIPCION ZONA NO SEA EDITABLE  
		*/
	    
	    addStep("Validar el campo descripcion zona no sea editable");
	    p.clickImagen("DescZona");
	    u.hardWait(10);
	    p.enviarTexto(zona1);
	    testCase.addScreenShotCurrentStep("OctavaCaptura");
	    u.hardWait(10);
	    
	    
	    /*
		 * PASO 9: VALIDAR QUE EL CAMPO PASILLOS DE ALMACENAJE NO SEA EDITABLE
		*/
	    
	    addStep("Validar el campo pasillos de almacenaje no sea editable");
	    p.clickImagen("Almacenaje");
	    u.hardWait(10);
	    p.enviarTexto(zona1);
	    testCase.addScreenShotCurrentStep("NovenaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 10: VALIDAR QUE LOS CHECKBOX FUNCIONAN
		*/
	    
	    addStep("Validar que las checkbox sean editables");
	    p.clickImagen("CheckBox");
	    testCase.addScreenShotCurrentStep("DecimaCaptura");
	    u.hardWait(10);
	    
	    /*
		 * PASO 11: VALIDAR EL CAMPO AUDITORIAS POR MES  
		*/
	    
	    addStep("Validar columna de auditorias por mes");
	    p.clickImagen("AuditoriasMes");
	    testCase.addScreenShotCurrentStep("OnceavaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 12: VALIDAR EL BOTON AUDITAR
		*/
	    
	    addStep("Validar el boton auditar");
	    p.clickImagen("Auditar");
	    u.hardWait(10);
	    p.clickImagen("Close");
	    testCase.addScreenShotCurrentStep("DoceavaCaptura");
	    u.hardWait(10);
	    /*
		 * PASO 13: VALIDACION CON QUERYS
		*/
	    addStep("Validacion de datos por medio de querys");
	    String querty1 = "Select DISTINCT l.ZONE, z.DESCRIPTION, SUBSTR(l.LOCATION_ID,1,2) as \"Pasillos de Almacenaje\" from LOCATION l JOIN ZONE z ON (l.ZONE = z.ZONE)";
	    utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
	    utils.sql.SQLResult resultadoContenedor1= dbBARAqa.executeQuery(querty1);	    
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
	    
	    String pasillo = data.get("pasillos");
	    String zona = data.get("zona");
	    
	    LocalDateTime fechaActual = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	    String fechaActualFormateada = fechaActual.format(formatter);
	    
	    
	    String querty2 = "Select count(*) as Auditorias from LOCATION where CYCLE_COUNT_STATUS = 'OK' and SUBSTR(LOCATION_ID,1,2) = '"+ pasillo +"' and ZONE = '" + zona + "' and trunc(CYCLE_COUNT_DATE) = to_date('"+fechaActualFormateada+"','DD/MM/YYYY')";
	    utils.sql.SQLResult resultadoContenedor2= dbBARAqa.executeQuery(querty2);
	    testCase.addQueryEvidenceCurrentStep(resultadoContenedor2);
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
		return "1. Acceso a la base de datos Bara: \r\n"
				+ "	      2. Acceso a RDM-BARA\r\n"
				+ "	       3. Claves de accesso de RDM-BARA\r\n"
				+ "	       4. Mes actual\r\n"
				+ "	       5. Anio actual\r\n"
				+ "	      6. Zona\r\n"
				+ "	      7. Pasillo de almacenaje\r\n"
				+ "	      8. Numero de auditorias al mes ";
	}

	@Override
	public String setTestDescription() {
		return "El objetivo de la prueba es realizar validaciones en la BD: RDM BARA QA en las cuales se valida el correcto funcionamiento de los campos en las pantallas\r\n"
				+ "	de seguimiento de auditorias de almacenaje";
	}

	@Override
	public String setTestDesigner() {
		return "FranciscoT2@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_SeguimientodeAuditoriasdeAlmacenaje_Validaciondecampos_AC_RDM_Bara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
