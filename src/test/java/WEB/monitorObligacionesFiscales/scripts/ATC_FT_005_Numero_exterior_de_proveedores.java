package WEB.monitorObligacionesFiscales.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.monitorObligacionesFiscales.om.Contabilidad;
import WEB.monitorObligacionesFiscales.om.MenuPrincipal_MonitorObligaciones;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_Numero_exterior_de_proveedores extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Numero_exterior_de_proveedores_test(HashMap<String, String> data) {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String URL = data.get("Url");
		String Lider = data.get("Lider");
		String RazonSocial = data.get("Razon_Social");
		String RFC = data.get("RFC");
		String EntidadFederativa = data.get("Estado");
		String Delegacion = data.get("Delegacion");
		String Colonia = data.get("Colonia");
		String Codigo_Postal = data.get("Codigo_Postal");
		String Otra_Colonia = data.get("Otra_Colonia");
		String Calle = data.get("Calle");
		String No_Exterior = data.get("No_Exterior");
		String No_Interior = data.get("No_Interior");
		String Entre_Calle = data.get("Entre_Calle");
		String Y_Calle = data.get("Y");
		String msg = "Estimado usuario, si realizas movimientos en ingresos, deducciones, n�mina o finiquito, es necesario volver a realizar el c�lculo de impuestos.";
		
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_MonitorObligaciones mo = new MenuPrincipal_MonitorObligaciones(u,testCase);
		Contabilidad moC =  new Contabilidad(u,testCase);
				
		// Paso 1
		addStep("Ingresar al portal de aplicaciones");

		u.get(URL);
		u.hardWait(3);
		pa.proceedToPortalIEsecureSite();

		// Paso 2
		addStep("El usuario ingresa usuario y contraseña y da clic en el botón <Iniciar Sesión>.");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opción de Monitor de Obligaciones Fiscales.");
		pa.clickPortalMonitorObligacionesFiscales(1);
		//Paso 4
		mo.clickContabilidad();
		moC.clickContabilidadPrincipal();
		moC.seleccionarContabilidadLiderActivo(Lider);
		assertTrue(moC.MensajeAdvertenciaContabilidad(msg),"El mensaje de advertencia no coincide");
		assertTrue(moC.selectSubmenuContabilidad("Proveedores"),"La opcion buscada no existe.");
		moC.selectProveedoresTabla(1);
		moC.modificarDatosProveedor(RazonSocial, RFC, EntidadFederativa, Delegacion, Colonia, Codigo_Postal, Otra_Colonia, No_Exterior, No_Interior, Calle, Entre_Calle, Y_Calle);
		moC.ContabilidadPrincipalActualizar();
		//Paso 5
		

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
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}

