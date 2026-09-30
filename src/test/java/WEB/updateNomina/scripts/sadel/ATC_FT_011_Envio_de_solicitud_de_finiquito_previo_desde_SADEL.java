package WEB.updateNomina.scripts.sadel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.Nomina;
import WEB.updateNomina.om.SikuliSADEL;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_011_Envio_de_solicitud_de_finiquito_previo_desde_SADEL extends BaseExecution{
	private SeleniumUtil u;
	private UpdateNomina updNom;
	private Login login;
	private Nomina nomina;
	private SikuliSADEL sikuliSADEL;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_011_Envio_de_solicitud_de_finiquito_previo_desde_SADEL_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
    	String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
		
    	u = new SeleniumUtil(new IETest(), true);
		updNom = new UpdateNomina(u, testCase);
		login =  new Login(u, testCase);
		nomina = new Nomina(u, testCase);
		
		SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("WEB\\"+"SADEL");
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);
        File Folder = new File(pathImg);
        sikuliSADEL = new SikuliSADEL(u, testCase, sk, pathimg, ListImage, ImgPatter, patterns, Fol);

		addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contraseña válidos.");
    	login.IngresarAFcptlqa(user, ps);
    	
    	addStep("Seleccionar la opcion de SADEL NOMINA COMISIONISTA");
    	login.SeleccionarSADELFcptlqa();
    	
    	addStep("Seleccionar la opcion de Nomina.");
    	nomina.SeleccionarBotonNomina();
   
    	addStep("Dentro de la pestana de Solicitud de Finiquitos, llenar los filtros de Plaza, tienda y fechas.");
    	nomina.SeleccionarPestanaFiniquitos();
    	nomina.SeleccionarPlazaYTiendaFiniquitos(); 
    	
    	addStep("Dar clic en el boton Solicitar");
    	nomina.SolicitarFiniquitos();
    	
    	addStep("Seleccionar del menu de opciones el boton de Nomina.");
    	addStep("Seleccionar la pestana de Recibos"); //Este metodo cumple con los 2 pasos
    	nomina.SeleccionarPestanaRecibos();
    	
    	addStep("Seleccionar una Tienda y Periodo 0-0.");
    	nomina.SeleccionarTiendaYPeriodoRecibos();
    	
    	addStep("Dar clic en el boton de Finiquito Previo del empleado al que se lo solicitamos");
    	addStep("Dar clic en el boton aceptar");
    	addStep("Dar clic en el boton OK");
    	nomina.SeleccionarFiniquitoPrevioSolicitado("Hector Manuel");
    	
    	addStep("Se valida que se haya creado la solicitud del finiquito de forma exitosa en SAP");
    	testCase.addTextEvidenceCurrentStep("Las validaciones en SAP se realizaran de forma manual");
    	
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que la baja se refleje de forma correcta en SAP despues de solicitarla desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De León Rodríguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_010_Edicion_de_empleado_de_LC_desde_SADEL_Jornadas_IT0007";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Previamente se tiene que generar una baja para que se tenga un finiquito pendiente por generar en la misma tienda";
    }
    
    @AfterMethod
    public void afterMethod() {
    	//u.quit();
    }
}	
