package WEB.updateNomina.scripts.sadel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.RealizarMovimientos;
import WEB.updateNomina.om.SikuliSADEL;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_004_Realizar_un_cambio_de_puesto_con_cambio_de_sueldo_de_un_empleado_de_lider
 extends BaseExecution{
	private SeleniumUtil u;
	private UpdateNomina updNom;
	private Login login;
	private RealizarMovimientos realizarMov;
	private SikuliSADEL sikuliSADEL;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_004_Realizar_un_cambio_de_puesto_con_cambio_de_sueldo_de_un_empleado_de_lider_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
    	String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
		//HOLA
		
		u = new SeleniumUtil(new IETest(), true);
		updNom = new UpdateNomina(u, testCase);
		login = new Login(u, testCase);
		realizarMov = new RealizarMovimientos(u, testCase);
		
		//Utileria Sikuli
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
    	
    	addStep("Seleccionar del menu de opciones el boton de Realizar Movimientos.");
    	realizarMov.SeleccionarBotonRealizarMovimiento();
    	
    	addStep("Seleccionar en el campo Tipo de movimiento la opcion Cambio de Puesto, " +
    	        "en el campo Subtipo de movimiento la opcion Cambio de Sueldo " +
    	        "y se da click al boton Seleccionar");
    	realizarMov.SeleccionarMovimientoTipoCambioDePuesto();
    	realizarMov.SeleccionarMovimientoSubtipoCambioDeSueldo();
    	
    	addStep("Llenar los filtros deseados y dar clic al boton Buscar");
    	addStep("Seleccionar un empleado con el radio button y dar clic al boton aceptar.");
    	sikuliSADEL.FiltroVentanaEmpleado(Folder, "57200542769"); //Este metodo cumple con los 2 pasos de arriba
    	
    	addStep("Llenar el resto de campos y dar clic al boton Guardar");
    	realizarMov.CapturarPuestoYSueldoNuevoDelEmpleado("255", "29082024");
    
    	addStep("Se autoriza el movimiento desde SADEL para ser enviado a SAP");
    	realizarMov.autorizarCambioDeSueldoDelEmpleado("Pueblo Dorado HMO");
    	
    	addStep("Se valida que se muestra en pendientes ante IMSS");
    	updNom.ValidarEnPendientesAnteIMSS();
    	
    	addStep("Se valida que en SAP se hayan actualizado de forma correcta los IT�s 0, 1, 7 y 8, "
    			+ "asi como en la estructura visualizandose en su nueva posicion");
    	testCase.addTextEvidenceCurrentStep("Las validaciones en SAP se realizaran de forma manual");
    	
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que el cambio de puesto con cambio de sueldo se refleje de forma correcta en SAP despues de solicitarla desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De León Rodríguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_004_Realizar_un_cambio_de_puesto_con_cambio_de_sueldo_de_un_empleado_de_lider";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Los empleados de SADEL y SAP deben estar homologados";
    }
    
    @AfterMethod
    public void afterMethod() {
    	//u.quit();
    }
}	
