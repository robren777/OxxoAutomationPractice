package WEB.portal_Remesas.ConfiguracionAtributos;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_081_Validar_EnvioDineroInter_enAtributos_compartidos extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_081_Validar_EnvioDineroInter_enAtributos_compartidos_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");

		String fechaInicio = data.get("Fecha_Inicio");
		String fechaFin = data.get("Fecha_Fin");
		String zonaOxxo = data.get("Zona_Oxxo");
		String cRPlaza = data.get("CR_Plaza");
		String cRTienda = data.get("CR_Tienda");
		String proveedor = data.get("Proveedor");
		String tipoReporte = data.get("Tipo_Reporte");
		String numRegistros = data.get("Numero_registros");
		String diaSemana = data.get("Dia_Semana");
		String mostrar = data.get("Mostrar");
		
		//									PASO 1
		addStep(" Ingresar al portal de aplicaciones <http://10.184.32.40:8000/AccessControl/pages/login_form.jsf> "); 
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		//SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
//		Alert alert = driver.switchTo().alert();
//		driver.switchTo().alert();
		
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		//pa.logginInventarioEspacios(user, password);	
		rm.clickAgregarImagen();
		
		//		                            PASO 2
		
		addStep(" Ingresar el usuario, password y dar clic en entrar ");
		pa.cicloLogin(user, password);

		//                                  PASO 3
		addStep(" Seleccionar el menu Portal Remesas ");	
		rm.clickSeleccionarPortalRemesas();

		//                                 PASO 4
		addStep(" Seleccionar modulo Configuracion/Envios y Retiro/Atributos compartidos ");
		rm.clickMenuConfiguracion();
		rm.clickOpAtributoCompartido();
		                      
		//                                 PASO 5
		addStep(" Validar que se muestra la opcion \"Env�o de dinero internacional\" ");	
		rm.clickEnvioDinero();

	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_081_Validar_opci�n_Env�o_dinero_internacional_dentro_deAtributos_compartidos_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_081_Validar_EnvioDineroInter_enAtributos_compartidos_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}


}
