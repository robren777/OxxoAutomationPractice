package EBS.neteos.Nacionales.InformeIntegraciondeFacturaRemesas;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
 * DESCRIPCION: Clase de utileria para Creacion de grupo de ejecucion Diario Corresponsalias.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-001_Agregar configuración de nueva clave Remesador
 * 
 * PREREQUISITOS:
 * Agregar en pantallas de configuracion una nueva clave de Remesador y la cuenta contable con
 * auxiliar correspondiente para que el concurrente Reporte de Integración Factura Remesas
 * muestre la información del nuevo remesador.
 *
 * @author Ricardo Romero Palacios
 * @date 14/Julio/2025
 */
public class ATC_FT_001_AgregarConfiguracionNuevaClaveRemesador extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_AgregarConfiguracionNuevaClaveRemesador_test(HashMap<String, String> data) throws Exception {
        
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombrer = data.get("nombrer");
		String valor = data.get("valor");
		String descripcion = data.get("descripcion");
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
        addStep("En la seccion\r\n"
        		+ "Values, crear una nueva linea haciendo click en el espacio despues del ultimo registro");
        
        assertEquals(nombrer,nombrer);
        p.nTAB(70);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("");
       
        
        
        
        //Paso 4
        addStep("Agregar un nuevo remesador\r\n"
        		+"Value: Prefijo de Remesador\r\n"
        		+"Description: Cuenta contable con el segmento auxiliar separados por un punto");
        
        p.typeAndTab(valor);
        p.typeAndTab(descripcion);
        assertEquals(descripcion,descripcion);
        u.hardWait(4);
        testCase.addScreenShotCurrentStep("");
        u.hardWait(4);

        
        //Paso 5
        addStep("Presionar el icono Save");
        assertTrue(p.ClickDisket());	
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("");
        p.closeText();
        u.hardWait(10);
        
     
        p.menuResponsabilidades();
        u.hardWait(4);
        p.nDOWN(12);
        p.nEnter(1);
        u.hardWait(3);
        p.vers();
        u.hardWait(4);
        p.clickEjecutarNuevaSolicitud();
        u.hardWait(4);
        
        //Paso 6
        addStep("Se muestra el nuevo remesador en la lista de valores del concurrente");
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
        return "ATC_FT_001_AgregarConfiguracionNuevaClaveRemesador_test";
    }

    @Override
    public String setTestDescription() {
        return "Agregar en pantallas de configuracion una nueva clave de Remesador y la cuenta contable con\r\n"
        		+ "auxiliar correspondiente para que el concurrente Reporte de Integracion Factura Remesas\r\n"
        		+ "muestre la informacion del nuevo remesador.";
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
        return "Que exista la cuenta contable del remesador en GL";
    }

    @Override
    public void beforeTest() {

    }
}