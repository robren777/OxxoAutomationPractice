package WEB.updateNomina.scripts.sadel;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.updateNomina.om.Empleados;
import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_007_Edicion_de_empleado_de_LC_desde_SADEL_Domicilio_IT0006 extends BaseExecution{
	private SeleniumUtil u;
	private UpdateNomina updNom;
	private Login login;
	private Empleados empleados;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_007_Edicion_de_empleado_de_LC_desde_SADEL_Domicilio_IT0006_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
    	String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
		
		u = new SeleniumUtil(new IETest(), true);
		updNom = new UpdateNomina(u, testCase);
		login =  new Login(u, testCase);
		empleados = new Empleados(u, testCase);

		addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contrasena validos.");
    	login.IngresarAFcptlqa(user, ps);
		
    	addStep("Seleccionar la opcion de SADEL NOMINA COMISIONISTA");
    	login.SeleccionarSADELFcptlqa();
    	
    	addStep("Seleccionar del menu de opciones el boton de Empleados.");
    	empleados.SeleccionarBotonEmpleados();
    	
    	addStep("Llenar los filtros deseados y dar clic al boton Buscar");
    	empleados.BuscarEmpleadoPorNSS("02240461950"); //empleado usado el ciclo 1 57139407241
    	
    	addStep("Se selecciona al empleado deseado y se da clic en el boton Editar");
    	empleados.SeleccionarEmpleado();
    	
    	addStep("Se da clic en la pestana Datos Generales");
    	empleados.SeleccionarPestanaDatosGenerales();
    	
    	addStep("Se edita el campo Calle  y se da clic en el boton Guardar");
    	empleados.ModificarDatoDelEmpleadoEstudiosFechaInicio();
    	empleados.ModificarDatoDelEmpleadoDomicilio();
    	
    	addStep("Se valida nuevamente al empleado en SAP y se visualiza que se actualiz� el Domicilio de forma correcta");
    	testCase.addTextEvidenceCurrentStep("Las validaciones en SAP se realizaran de forma manual");
    	
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que la edicion del Domicilio se refleje de forma correcta en SAP despues de solicitarla desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De Leon Rodriguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_007_Edicion_de_empleado_de_LC_desde_SADEL_Domicilio_IT0006";
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
