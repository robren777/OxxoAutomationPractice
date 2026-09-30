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


public class ATC_FT_009_Edicion_de_empleado_de_LC_desde_SADEL_RFC_IT0185 extends BaseExecution{
	private SeleniumUtil u;
	private UpdateNomina updNom;
	private Login login;
	private Empleados empleados;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_009_Edicion_de_empleado_de_LC_desde_SADEL_RFC_IT0185_test(HashMap<String, String> data) throws Exception {
    	String user = data.get("Usuario");
    	String ps = PasswordUtil.decryptPassword(data.get("Contrasenia"));
		
    	u = new SeleniumUtil(new MEdgeTest(), true);
		updNom = new UpdateNomina(u, testCase);
		login =  new Login(u, testCase);
		empleados = new Empleados(u, testCase);

		addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login y accesar con usuario y contraseña válidos.");
    	login.IngresarAFcptlqa(user, ps);
    	
    	addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
    	login.SeleccionarSADELFcptlqa();
    	
    	addStep("Seleccionar del menú de opciones el botón de Empleados.");
    	empleados.SeleccionarBotonEmpleados();
    	
    	addStep("Llenar los filtros deseados y dar clic al botón Buscar");
    	empleados.BuscarEmpleadoPorNSS("57139407241");
    	
    	addStep("Se selecciona al empleado deseado y se da clic en el botón Editar");
    	empleados.SeleccionarEmpleado();
    	
    	addStep("Se da clic en la pestana Datos Generales");
    	empleados.SeleccionarPestanaDatosGenerales();
    	
    	addStep("Se edita el campo Calle  y se da clic en el boton Guardar");
    	empleados.ModificarDatoDelEmpleadoEstudiosFechaInicio();
    	empleados.ModificarDatoDelEmpleadoRFC();
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Validar que la edicion de RFC se refleje de forma correcta en SAP despues de solicitarla desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De León Rodríguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_009_Edicion_de_empleado_de_LC_desde_SADEL_RFC_IT0185";
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
