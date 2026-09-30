package WEB.updateNomina.scripts.sadel;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.updateNomina.om.Empleados;
import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_012_Edicion_de_empleado_de_LC_desde_SADEL_Datos_personales_genero_indefinido_IT0002 extends BaseExecution{
	private SeleniumUtil u;
	private UpdateNomina updNom;
	private Login login;
	private Empleados empleados;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_012_Edicion_de_empleado_de_LC_desde_SADEL_Datos_personales_genero_indefinido_IT0002_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
    	String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		updNom = new UpdateNomina(u, testCase);
		login =  new Login(u, testCase);
		empleados = new Empleados(u, testCase);

		addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contrase�a validos.");
    	
    	login.IngresarAFcptlqa(user, ps);
    	
    	addStep("Seleccionar la opcion de SADEL NOMINA COMISIONISTA");
    	login.SeleccionarSADELFcptlqa();
    	
    	addStep("Seleccionar del menu de opciones el boton de Empleados.");
    	empleados.SeleccionarBotonEmpleados();
    	
    	addStep("Llenar los filtros deseados y dar clic al boton Buscar");
    	
    	empleados.BuscarEmpleadoPorNSS("02230055689");
    	
    	addStep("Se selecciona al empleado deseado y se da clic en el boton Editar");
    	empleados.SeleccionarEmpleado();
    	
    	addStep("Se edita el CURP  y se da clic en el boton Guardar");
    	empleados.ModificarDatoDelEmpleadoCURP();
    	
    	addStep("Se valida nuevamente al empleado en SAP y se visualiza que se actualiz� el CURP de forma correcta");
    	testCase.addTextEvidenceCurrentStep("Las validaciones en SAP se realizaran de forma manual");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que la edicion del CURP se refleje de forma correcta en SAP despues de solicitarla desde SADEL una vez con el chg aplicado";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De Leon Rodriguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_012_Edicion_de_empleado_de_LC_desde_SADEL_Datos_personales_genero_indefinido_IT0002";
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
