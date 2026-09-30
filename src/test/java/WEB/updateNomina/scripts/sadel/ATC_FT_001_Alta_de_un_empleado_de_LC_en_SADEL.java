package WEB.updateNomina.scripts.sadel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.updateNomina.om.Empleados;
import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.RealizarMovimientos;
import WEB.updateNomina.om.SikuliSADEL;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Alta_de_un_empleado_de_LC_en_SADEL extends BaseExecution{
	private SeleniumUtil u;
	private Login login;
	private RealizarMovimientos realizarMov;
	private Empleados empleados;
    private UpdateNomina updNom;
    private SikuliSADEL sikuliSADEL;
    
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_Alta_de_un_empleado_de_LC_en_SADEL_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
		String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
        
        u = new SeleniumUtil(new IETest(), true);
		login = new Login(u, testCase);
		realizarMov = new RealizarMovimientos(u, testCase);
		empleados = new Empleados(u, testCase);
        updNom = new UpdateNomina(u, testCase);
		
        SikuliUtil sk = new SikuliUtil();
        String pathImg = sk.PatternPath("WEB\\"+"SADEL");
        Pattern pathimg = new Pattern();
        ArrayList<String> ListImage = new ArrayList<String>();
        HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        File Fol = new File(pathImg);
        File Folder = new File(pathImg);
        sikuliSADEL = new SikuliSADEL(u, testCase, sk, pathimg, ListImage, ImgPatter, patterns, Fol);
    	
    	addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contrase�a validos.");
    	login.IngresarAFcptlqa(user, ps);
    	
    	addStep("Seleccionar la opcion de SADEL NOMINA COMISIONISTA");
    	login.SeleccionarSADELFcptlqa();
    	
    	addStep("Seleccionar del menu de opciones el boton de Realizar Movimientos.");
    	realizarMov.SeleccionarBotonRealizarMovimiento();
    	
    	addStep("Seleccionar Alta en el filtro de Tipo de Movimiento y dar clic al boton Seleccionar");
    	realizarMov.SeleccionarMovimientoTipoAlta();
    	
    	addStep("Capturar datos personales del empleado. Todos los campos con asterisco");
    	realizarMov.CapturarDatosDelEmpleado("10MXL Hermosillo Sur", "54159538229", "27082024", "Cuba HMO", "248.93"); //26180323573 michelle anahi 24038117925 mariana garcia cadena
    	
    	addStep("Se muestra que el alta se encuentra en el apartado de \"Pendientes ante IMSS\"  con el estatus de \"Solicitud\".");
    	updNom.ValidarEnPendientesAnteIMSS();
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que la alta se refleje de forma correcta en SAP despues de solicitarla desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De Leon Rodriguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_Alta_de_un_empleado_de_LC_en_SADEL";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Los empleados de SADEL y SAP deben estar homologados, dar de alta a un empleado inactivo, tener una vacante vac�a en la tienda en la que se va a dar de alta al empleado";
    }

    @AfterMethod
    public void afterMethod() {
    	//u.quit();
    }
}
