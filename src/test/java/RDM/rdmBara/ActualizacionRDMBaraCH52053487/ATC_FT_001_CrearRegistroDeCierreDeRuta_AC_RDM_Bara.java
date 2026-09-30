package RDM.rdmBara.ActualizacionRDMBaraCH52053487;
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
public class ATC_FT_001_CrearRegistroDeCierreDeRuta_AC_RDM_Bara extends BaseExecution{
    /**
	 * REREQUISITOS: 
	 *		user = usuario de RDM QA
		 *   password = password de RDM QA
         * plaza (dp)
         * unidad (dp)
         * tipo devolucion (dp)
	 * 
	 * DESCRIPCION: Validar que al ingresar a la pantalla XXFC Módulo de Devoluciones el sistema permita la creación de registro de entrada de los camiones que entregan una devolución
	 * 
     * 
	 * @author franciscot2@hexaware.com
	 */

     SeleniumUtil u = new SeleniumUtil(new IETest(),true);
     @Test(dataProvider = "data-provider")
     public void ATC_FT_001_CrearRegistroDeCierreDeRuta_AC_RDM_Bara_test(HashMap<String, String> data) throws Exception {
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
		String plaza =  data.get("plaza");
        String unidad = data.get("idunidad");
        String dev = data.get("dev");
		
		SikuliUtil sk = new SikuliUtil();
		
		
		String pathImg = sk.PatternPath("RDM\\"+"CH52053487");
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

        //Paso 2: Ingresar a la ruta:  Receiving -> XXFC – Devoluciones -> XXFC-Seguimiento Operativo
        addStep("Ingresar a la ruta:  Receiving -> XXFC – Devoluciones -> XXFC-Seguimiento Operativo");
        p.clickImagen("Reciving");
        u.hardWait(10);
        p.clickImagen("Devoluciones");
        u.hardWait(10);
        p.clickImagen("SOperativo");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("SegundaCaptura");
        u.hardWait(5);
        //Validando la pantalla de seguimiento operativo
        try{
            p.clickImagen("SOperativoValidacion");
            u.hardWait(10);
        }
        catch(Exception e){
            throw new RuntimeException("No se puede validar la pantalla de seguimiento operativo");
        }

        //Paso 3: Dar clic en el botón Reg. Cierre Ruta
        addStep("Dar clic en el botón Reg. Cierre Ruta");
        p.clickImagen("BtnRegCierrRuta");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("TerceraCaptura");
        u.hardWait(5);
        //Validando la pantalla auxiliar cierre de ruta
        try{
            p.clickImagen("RCierreRutaValidacion");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar la pantalla auxiliar Reg. Cierre Ruta");
        }

        //Paso 4: Ingresar los parámetros requeridos: Location, Plaza, Ruta, Id Chofer, ID Unidad, Tipo de DEV
        addStep("Ingresar los parámetros requeridos: Location, Plaza, Ruta, Id Chofer, ID Unidad, Tipo de DEV");
        p.clickImagen("PlazaCampo");
        u.hardWait(10);
        p.enviarTexto(plaza);
        u.hardWait(10);
        p.doubleClickAndCopyTextBoxRight("RutaCampo", 5);
        u.hardWait(10);
        p.clickImagen("OkBtnRuta"); 
        u.hardWait(5);
        p.doubleClickAndCopyTextBoxRight("IdChoferCampo", 10);
        u.hardWait(10);
        p.clickImagen("OkBtnRuta");
        u.hardWait(10);
        p.nTAB(1);
        u.hardWait(10);
        p.enviarTexto(unidad);
        u.hardWait(10);
        p.nTAB(1);
        u.hardWait(10);
        p.enviarTexto(dev);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("CuartaCaptura");
        u.hardWait(5);

        //Paso 5: Dar clic en Save 
        addStep("Dar clic en Save ");
        p.clickImagen("SaveButton");
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("QuintaCaptura");
        u.hardWait(5);
        //Validar la creacion de la cita
        try{
            p.clickImagen("ValidarCreacionDeCita");
            u.hardWait(10);
        }catch(Exception e){
            throw new RuntimeException("No se puede validar la creacion de la cita");
        }
        u.close();

        //PASO 6: Validar dentro de la tabla Cierre de Ruta la creación de la ruta.  select * from XXFC_RDEV_CIERRE_RUTA where ruta = 'ruta' 

        addStep("Validar dentro de la tabla Cierre de Ruta la creación de la ruta. select * from XXFC_RDEV_CIERRE_RUTA where ruta = 'ruta'");

		String querty1 = "SELECT appt_nbr, appt_duration, entarimado, last_update_date FROM xxfc_appointment WHERE TRUNC(last_update_date) = TO_DATE(TO_CHAR(sysdate, 'DD-MON-YY'), 'DD-MON-YY')";

		utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
		utils.sql.SQLResult resultadoContenedor1 = dbBARAqa.executeQuery(querty1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);

		boolean flag = !resultadoContenedor1.isEmpty();

		if(flag){

        }else{
            throw new RuntimeException("No se puede validar el cierre de la ruta");
        }
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
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String setTestDescription() {
        return "Validar que al ingresar a la pantalla XXFC Módulo de Devoluciones el sistema permita la creación de registro de entrada de los camiones que entregan una devolución";
    }
    @Override
    public String setTestDesigner() {
        return "FranciscoT2@hexaware.com";
    }
    @Override
    public String setTestFullName() {
        return "ATC_FT_001_CrearRegistroDeCierreDeRuta_AC_RDM_Bara_test";
    }
    @Override
    public String setTestInstanceID() {
        // TODO Auto-generated method stub
        return null;
    }


}
