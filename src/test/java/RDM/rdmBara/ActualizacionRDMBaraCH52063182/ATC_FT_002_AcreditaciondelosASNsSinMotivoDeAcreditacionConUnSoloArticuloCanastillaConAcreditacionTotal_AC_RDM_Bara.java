package RDM.rdmBara.ActualizacionRDMBaraCH52063182;

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

public class ATC_FT_002_AcreditaciondelosASNsSinMotivoDeAcreditacionConUnSoloArticuloCanastillaConAcreditacionTotal_AC_RDM_Bara extends BaseExecution{
    /**
	 * REREQUISITOS: 
	 *		 user = usuario de RDM QA
		 * password = password de RDM QA
         * asn (dp)
	 * 
	 * DESCRIPCION: Validar que al realizar una acreditación de los ASNs sin motivo de acreditación con un solo artículo (canastilla)  con acreditación total el sistema
	 * 
	 * @author franciscot2@hexaware.com
	 */



    SeleniumUtil u = new SeleniumUtil(new IETest(),true);
    @Test(dataProvider = "data-provider")
    public void ATC_FT_002_AcreditaciondelosASNsSinMotivoDeAcreditacionConUnSoloArticuloCanastillaConAcreditacionTotal_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
         /*
		 * VARIABLES
		 * 
		 * user = usuario de RDM QA
		 * password = password de RDM QA
		 * 
	    */

        String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String asn = data.get("ASN");
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH52063182");
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
		
		addStep("Ingresar a RDM Bara QA");
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
	    //Se valida la pantalla principal de RDM Bara.
	    try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }

        //PASO 2: Ingresar a la ruta:  Receiving -> XXFC – Devoluciones -> XXFC- Acreditación
        addStep("Ingresar a la ruta:  Receiving -> XXFC – > Devoluciones -> XXFC- Acreditación");
        p.clickImagen("Reciving");
        u.hardWait(10);
        p.clickImagen("Devoluciones");
        u.hardWait(10);
        p.clickImagen("Acreditaciones");
        u.hardWait(10);
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SegundaCaptura");
        u.hardWait(5);
        
        //Validando la pantalla de Acreditaciones
        try{
            p.clickImagen("AcreditacionesValidaciones");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar la pantalla de Acreditaciones");
        }

        //PASO 3: Ingresar el valor WH en el campo Location
        addStep("Ingresar el valor WH en el campo Location");
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("TerceraCaptura");
        u.hardWait(5);

        //PASO 4: Buscar y seleccionar un ASN en la lista de  valores del campo ASN
        addStep("Buscar y seleccionar un ASN en la lista de  valores del campo ASN");
        p.clickImagen("AnsCampo");
        u.hardWait(10);
        p.enviarTexto(asn);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CuartaCaptura");
        u.hardWait(5);

        //PASO 5: Dar un TAB o ENTER
        addStep("Dar un TAB o ENTER");
        p.nTAB(1);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("QuintaCaptura");
        u.hardWait(5);
        try{
            p.clickImagen("ValidarElCampoElegirRuta");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("Validar la pantalla de elegir ruta");
        }
        
        //PASO 6: Relacionar el ASN con un solo artículo (canastilla)  a la ruta 
        addStep("Relacionar el ASN con un solo artículo (canastilla) a la ruta");
        p.doubleClickAndCopyTextBoxRight("RutaDato", 10);
        u.hardWait(10);
        p.clickImagen("OkBtnRuta");
        u.hardWait(10);
        p.clickImagen("SaveBtn");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SextaCaptura");
        u.hardWait(5);
        //Validando la creacion de la ruta
        try{
            p.clickImagen("ValidacionCreacionRuta");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar la creacion de la ruta");
        }


        //PASO 7: Validar los siguientes puntos: -Que no se solicite contenedor al acreditar -Que no sea requerido el Motivo de No acreditación -Que  no genere contenedores de usuario
        addStep("Validar los siguientes puntos: -Que no se solicite contenedor al acreditar -Que no sea requerido el Motivo de No acreditación -Que  no genere contenedores de usuario");
        testCase.addScreenShotCurrentStep("SeptimaCaptura");
        u.hardWait(5);
        //Validando que no existan mas cuadros de dialogo
        try{
            p.clickImagen("ValidacionCreacionRuta");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar la creacion de la ruta");
        }
        u.close();
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
        return "Contar con ASN que contengan el mismo artículo en común";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al realizar una acreditación de los ASNs sin motivo de acreditación con un solo artículo (canastilla)  con acreditación total el sistema ";
    }

    @Override
    public String setTestDesigner() {
        return "FranciscoT2@hexaware.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_AcreditaciondelosASNsSinMotivoDeAcreditacionConUnSoloArticuloCanastillaConAcreditacionTotal_AC_RDM_Bara_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
