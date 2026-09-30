package EBS.neteos.Nacionales.InformeIntegraciondeFacturaRemesas;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.FolioPagoMVP2.ClaseLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Inactivar Remesador mediante el checkbox Enabled en la pantalla de configuracion de Remesadores
 * y Cuenta Contable para que no sea considerado en el concurrente
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-002_Inactivar Remesador en configuración
 * 
 * PREREQUISITOS:
 * Que la relacion de Cuenta Contable y Remesador exista en el juego de valores
 *
 * @author Ricardo Romero Palacios
 * @date 14/Julio/2025
 */
public class ATC_FT_002_InactivarRemesadorEnConfiguracion extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_InactivarRemesadorEnConfiguracion_test(HashMap<String, String> data) throws Exception {
        
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombrer = data.get("nombrer");
		String concurrente = data.get("concurrente");
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);

		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
    	
    	
    	
        //Paso 1
        addStep(" En responsabilidad\r\n"
        		+ "  MXOXXO-GL17ADM-SERVICIOS Seleccionar Setup, Flexfields, Validation, Values");

        logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		u.hardWait(6);
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        u.hardWait(3);
        u.scroll();
        assertTrue(paginaInicial.configuracionRemesas());	
        u.hardWait(4);
        p.accepApp();
        u.hardWait(8);
        p.closeText();
        u.hardWait(5);
        p.nDOWN(6);
        u.hardWait(3);
        p.nEnter(1);
        u.hardWait(3);
        p.nEnter(1);
        u.hardWait(3);
        p.nEnter(1);
        u.hardWait(3);
        p.nDOWN(2);
        u.hardWait(3);
        p.nEnter(1);
        u.hardWait(3);
        p.nDOWN(1);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("");
        u.hardWait(3);
        p.nEnter(1);
        u.hardWait(5);
        
        
        //Paso 2
        addStep("Buscar el juego de\r\n"
        		+ "valores XXFC_GL_CONCIL_CUENTA_REMESAS y presionar el boton Find");
        
        p.typeAndTab(nombrer);
        assertEquals(nombrer,nombrer);
        p.nTAB(3);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("");
        p.nEnter(1);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("");
        
        //Paso 3
        addStep("Con ayuda del check box Enabled, desmarcar la casilla para inactivar un remesador");
        assertEquals(nombrer,nombrer);
        p.nTAB(24);
        testCase.addScreenShotCurrentStep("");
        u.hardWait(3);
        sk.screen.type(Key.SPACE);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("");
        
        //Paso 4
        addStep("Presionar el icono Save");
        assertTrue(p.ClickDisket());	
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("");
        p.closeText();
        u.hardWait(5);
        p.nEnter(1);
        u.hardWait(5);
     
        p.menuResponsabilidades();
        u.hardWait(4);
        p.nDOWN(13);
        p.nEnter(1);
        u.hardWait(3);
        p.vers();
        u.hardWait(4);
        p.clickEjecutarNuevaSolicitud();
        u.hardWait(4);
        
        //Paso 5
        addStep("El Remesador ya no se muestra en la lista de valores del concurrente");
        
        p.typeAndTab(concurrente);
        assertEquals(concurrente,concurrente);
        u.hardWait(3);
        p.ClickBotonTresPuntos();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("");
        testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
        u.getDriver().quit();
        
 
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_InactivarRemesadorEnConfiguracion_test";
    }

    @Override
    public String setTestDescription() {
        return "Inactivar Remesador mediante el checkbox Enabled en la pantalla de configuracion de Remesadores\r\n"
        		+ "y Cuenta Contable para que no sea considerado en el concurrente\r\n";
    }

    @Override
    public String setTestDesigner() {
        return "RicardoR4@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Que la relacion de Cuenta Contable y Remesador exista en el juego de valores";
    }

    @Override
    public void beforeTest() {

    }
}