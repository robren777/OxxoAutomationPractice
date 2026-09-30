package RDM.rdmBara.ActualizacionRDMBaraCH52081087;

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

public class ATC_FT_006_RutaconASNabierto_AC_RDM_Bara extends BaseExecution{

     /**
	 * REREQUISITOS: 
	 *		1.- Iniciativa instalada en QA select * from xxfc_patches_installed where patch_nbr = '52081087';
            2.- Tener rutas creadas  
            asn (dp)
            ruta (dp)
	 * 
	 * DESCRIPCION: Validacion de ruta con ASN abierto, que no se pueda cerrar escribiendo en la LOV "CERRAR RUTA"
	 * 
	 * @author franciscot2@hexaware.com
	 */
    SeleniumUtil u = new SeleniumUtil(new IETest(),true);
	@Test(dataProvider = "data-provider")
    public void ATC_FT_006_RutaconASNabierto_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception{
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
		String asn = data.get("asn");
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH52081087");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);

        //PASO 1: Ingresar a RDM Bara QA
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

        //PASO 2: Ingresar a la siguiente ruta Receiving  ->  XXFC-Devoluciones -> XXFC-Acreditaciones
        addStep("Ingresar a la siguiente ruta Receiving  ->  XXFC-Devoluciones -> XXFC-Acreditaciones");
        p.clickImagen("Reciving");
        u.hardWait(10);
        p.clickImagen("Devoluciones");
        u.hardWait(10);
        p.clickImagen("Acreditaciones");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SegundaCaptura");
        u.hardWait(5);
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(15);
        //Validando la pantalla de Acreditaciones
        try{
            p.clickImagen("AcreditacionesValidaciones");
            u.hardWait(10);
        }catch(Exception e){
             throw new RuntimeException("No se puede validar la pantalla de Acreditaciones");
        }
        
        
        //PASO 3: Seleccionar un ASN  (ASN individual)
        addStep("Seleccionar un ASN  (ASN individual)");
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
        p.doubleClickAndCopyTextBoxRight("RutaCampo", 10);
        u.hardWait(10);
        p.clickImagen("OkBtnRuta");
        u.hardWait(10);
        String ruta = p.doubleClickAndCopyTextBoxLeft("RutaCopy", 10);
        p.clickImagen("SaveBtn");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SextaCaptura");
        p.nTAB(1);
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(10);
        

        //PASO 4: Salir de la pantalla de Acreditación
        addStep("Salir de la pantalla de Acreditación");
        p.clickImagen("SalirButon");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CapturaStepCuatro");
        u.hardWait(5);
        //Se valida la pantalla principal de RDM Bara.
	    try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }

        //STEP 5: Ingresar nuevamente a la siguiente ruta Receiving  ->  XXFC-Devoluciones -> XXFC-Acreditaciones
        addStep("Ingresar nuevamente a la siguiente ruta Receiving  ->  XXFC-Devoluciones -> XXFC-Acreditaciones");
        p.clickImagen("Reciving");
        u.hardWait(10);
        p.clickImagen("Devoluciones");
        u.hardWait(10);
        p.clickImagen("Acreditaciones");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SegundaCaptura");
        u.hardWait(5);
         

        //STEP 6: En el mensaje de ASN pendientes de acreditar seleccionamos la opción NO
        addStep("En el mensaje de ASN pendientes de acreditar seleccionamos la opción NO");
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("CapturaStepSeis");

        //STP 7: Presionar botón CIERRE RUTA y validar que la ruta no se encuentre en el listado de rutas a cerrar
        addStep("Presionar botón CIERRE RUTA y validar que la ruta no se encuentre en el listado de rutas a cerrar");
        p.clickImagen("CierreRuta");
        u.hardWait(10);
        p.doubleClickAndCopyTextBoxRight("RutaCampo", 10);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CapturaStepSiete");
        u.hardWait(10);

        //PASO 8: Validar que al escribir la ruta en la LOV "CERRAR RUTA" no permita cerrar la ruta
        addStep("Validar que al escribir la ruta en la LOV 'CERRAR RUTA' no permita cerrar la ruta");
        p.clickImagen("CancelButtonRuta");
        u.hardWait(10);
        p.enviarTexto(ruta);
        u.hardWait(10);
        p.clickImagen("SaveBtn");
        u.hardWait(10);
        p.clickImagen("OkMessageBtn");
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("CapturaStepocho");
        u.hardWait(10);
        p.clickImagen("ExitCancel");
        u.hardWait(10);

        //PASO 9: Regresar a Pantalla de Acreditación y seleccionar un grupo de  ASN’s  (ASN´s múltiple)
        addStep("Regresar a Pantalla de Acreditación y seleccionar un grupo de  ASNs  (ASNs múltiple)");
        p.clickImagen("AnsMulti");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CapturaStepocho");

        //PASO 10: Salir de la pantalla de Acreditación
        addStep("Salir de la pantalla de Acreditación");
        p.clickImagen("SalirButon");
        try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }

        //PASO 11:Ingresar nuevamente a la siguiente ruta Receiving  ->  XXFC-Devoluciones -> XXFC-Acreditaciones
        p.clickImagen("Reciving");
        u.hardWait(10);
        p.clickImagen("Devoluciones");
        u.hardWait(10);
        p.clickImagen("Acreditaciones");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CapturaStepOnce");
        u.hardWait(5);
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(15);
        //Validando la pantalla de Acreditaciones
        try{
            p.clickImagen("AcreditacionesValidaciones");
            u.hardWait(10);
        }catch(Exception e){
             throw new RuntimeException("No se puede validar la pantalla de Acreditaciones");
        }

        //PASO 12: En el mensaje de ASN pendientes de acreditar seleccionamos la opción NO
        addStep("En el mensaje de ASN pendientes de acreditar seleccionamos la opción NO");
        sk.screen.keyDown(Key.ENTER);
        u.hardWait(1);
        sk.screen.keyUp(Key.ENTER);
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("CapturaStepDoce");
        u.hardWait(5);

        //PASO 13: Presionar botón CIERRE RUTA y validar que la ruta no se encuentre en el listado de rutas a cerrar
        addStep("Presionar botón CIERRE RUTA y validar que la ruta no se encuentre en el listado de rutas a cerrar");
        p.clickImagen("CierreRuta");
        u.hardWait(10);
        p.doubleClickAndCopyTextBoxRight("RutaCampo", 10);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CapturaStepSiete");
        u.hardWait(10);

        //PASO 14: Validar que al escribir la ruta en la LOV "CERRAR RUTA" no permita cerrar la ruta
        addStep("Validar que al escribir la ruta en la LOV 'CERRAR RUTA' no permita cerrar la ruta");
        p.clickImagen("CancelButtonRuta");
        u.hardWait(10);
        p.enviarTexto(ruta);
        u.hardWait(10);
        p.clickImagen("SaveBtn");
        u.hardWait(10);
        p.clickImagen("OkMessageBtn");
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("CapturaStepocho");
        u.hardWait(10);

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
       return "1.- Iniciativa instalada en QA select * from xxfc_patches_installed where patch_nbr = '52081087'" +
       "2.- Tener rutas creadas l";
    }

    @Override
    public String setTestDescription() {
        return "Validacion de ruta con ASN abierto, que no se pueda cerrar escribiendo en la LOV CERRAR RUTA";
    }

    @Override
    public String setTestDesigner() {
       return "FranciscoT2@hexaware.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_006_RutaconASNabierto_AC_RDM_Bara_test";
    }

    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
