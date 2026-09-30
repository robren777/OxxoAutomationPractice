package RDM.rdmBara.ActualizacionRDMBaraCH52066349;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
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

public class ATC_FT_002_Relacion_ASN_Plaza_AC_RDM_Bara extends BaseExecution {

    /**
	 * REREQUISITOS: 
	 *		1.-Contar con Rutas creadas
	 *      asn (dp)
	 * DESCRIPCION: Se tiene por objetivo validar que solo se muestren ASNs relacionados con la PLAZA seleccionada (10MON) en la sección de ASN individual y ASNs múltiples

	 * 
	 * @author franciscot2@hexaware.com
	 */
    SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
    public void ATC_FT_002_Relacion_ASN_Plaza_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
        /*
		 * VARIABLES
		 * 	1.- Contar con Rutas creadas
	    */


        String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
        String asn = data.get("ASN");
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH52066349");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);

        //PASO 1: Ingresar a RDM Bara QA con un usuario privilegio = 3 o 9
        addStep("Ingresar a RDM Bara QA con un usuario privilegio = 3 o 9");
        u.get(login.getUrl());	
		u.hardWait(30);
	    p.enviarTexto(userName);
	    p.nTAB(1);
	    u.hardWait(10);
	    p.enviarTexto(password);
	    p.nTAB(1);
	    u.hardWait(10);
	    testCase.addScreenShotCurrentStep("PrimerCaptura");
	    p.clickImagen("Login");
	    u.hardWait(15);
	    //Validando el login
	    try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch(Exception e) {
	    	System.out.println("No se pudo validar el inicio de sesion en RDM ");
	    	return;
	    }

        //PASO 2: Ingresar a la siguiente ruta Receiving --> XXFC-Devoluciones --> XXFC-Acreditaciones
        addStep("Ingresar a la siguiente ruta Receiving --> XXFC-Devoluciones --> XXFC-Acreditaciones");
        p.clickImagen("Reciving");
        u.hardWait(10);
        p.clickImagen("Devoluciones");
        u.hardWait(10);
        p.clickImagen("Acreditaciones");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SegundaCaptura");
        u.hardWait(10);
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(10);
        //Validando la pantalla de Acreditaciones
        try{
            p.clickImagen("AcreditacionesValidaciones");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar la pantalla de Acreditaciones");
        }

        //PASO 3: Validar que solo se muestren ASNs relacionados con la PLAZA seleccionada en la sección de ASN individual
        addStep("Validar que solo se muestren ASNs relacionados con la PLAZA seleccionada en la sección de ASN individual");
        //12NEO52WVH003412
        p.clickImagen("AnsCampo");
        u.hardWait(10);
        p.enviarTexto(asn);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CuartaCaptura");
        u.hardWait(5);
        p.nTAB(1);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("QuintaCaptura");
        u.hardWait(5);
        p.doubleClickAndCopyTextBoxRight("RutaDato", 10);
        u.hardWait(10);
        p.clickImagen("OkBtnRuta");
        u.hardWait(10);
        p.clickImagen("SaveBtn");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SextaCaptura");
        u.hardWait(5);
        p.nTAB(1);
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        try{
            p.clickImagen("YesBtn");
            u.hardWait(10);
        }
        catch(Exception e){

        }
        try{
            p.clickImagen("ValidacionPlaza");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar los ASN");
        }
        testCase.addScreenShotCurrentStep("Captura");

        //STEP 4: Validar que solo se muestren ASNs relacionados con la PLAZA seleccionada (10MON) en la sección de ASNs múltiples
        addStep("Validar que solo se muestren ASNs relacionados con la PLAZA seleccionada (10MON) en la sección de ASNs múltiples");
        try{
            p.clickImagen("ValidacionPlaza");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar los ASN");
        }
        testCase.addScreenShotCurrentStep("Captura");
        u.close();
        //ValidacionPlaza
    }

    @AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}
    
    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String setPrerequisites() {
        return "Contar con Rutas creadas";
    }

    @Override
    public String setTestDescription() {
       return "Se tiene por objetivo validar que solo se muestren ASNs relacionados con la PLAZA seleccionada (10MON) en la sección de ASN individual y ASNs múltiples";
    }

    @Override
    public String setTestDesigner() {
        return "FranciscoT2@hexaware.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_002_Relacion_ASN_Plaza_AC_RDM_Bara_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
