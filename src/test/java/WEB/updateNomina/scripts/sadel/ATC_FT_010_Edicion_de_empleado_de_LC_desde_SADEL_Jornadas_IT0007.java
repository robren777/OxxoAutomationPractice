package WEB.updateNomina.scripts.sadel;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.Nomina;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_010_Edicion_de_empleado_de_LC_desde_SADEL_Jornadas_IT0007 extends BaseExecution{
	private SeleniumUtil u;
	private UpdateNomina updNom;
	private Login login;
	private Nomina nomina;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_010_Edicion_de_empleado_de_LC_desde_SADEL_Jornadas_IT0007_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
    	String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
		
    	u = new SeleniumUtil(new MEdgeTest(), true);
		updNom = new UpdateNomina(u, testCase);
		login =  new Login(u, testCase);
		nomina = new Nomina(u, testCase);

		addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contrasena validos.");
		login.IngresarAFcptlqa(user, ps);
    	
    	addStep("Seleccionar la opcion de SADEL NOMINA COMISIONISTA");
    	login.SeleccionarSADELFcptlqa();
    	
    	addStep("Seleccionar la opcion de Nomina.");
    	nomina.SeleccionarBotonNomina();
    	
    	addStep("Seleccionar la pestana de Jornadas");
    	nomina.SeleccionarPestanaJornadas();
    	
    	addStep("Se llenan los campos Plaza y Tienda");
    	nomina.SeleccionarPlazaYTiendaJornadas();
    	
    	addStep("Se selecciona Empleado y Edicion en el campo Accion");
    	nomina.SeleccionarEmpleado();
    	
    	addStep("Se editan los datos de la nueva jornada en SADEL y da clic en el boton Guardar");
    	nomina.EditarJornada();
    	
    	addStep("Se selecciona Enviar en el campo Acci�n");
    	addStep("Se da clic al bot�n Enviar");
    	nomina.EnviarJornadas();
    	
    	addStep("Se muestra en SAP que lleg� de forma correcta el env�o de jornada realizado desde SADEL");
    	testCase.addTextEvidenceCurrentStep("Las validaciones en SAP se realizaran de forma manual");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que la edicion de la jornada se refleje de forma correcta en SAP despues de solicitarla desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De Leon Rodriguez";
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
        return "Los empleados de SADEL y SAP deben estar homologados";
    }
    
    @AfterMethod
    public void afterMethod() {
    	//u.quit();
    }
}	
