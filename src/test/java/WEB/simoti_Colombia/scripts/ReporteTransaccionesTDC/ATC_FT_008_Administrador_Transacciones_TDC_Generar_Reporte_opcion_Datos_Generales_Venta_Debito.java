package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.TransaccionesTDC_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_008_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Venta_Debito extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Venta_Debito_test(HashMap<String, String> data)  {
		
		//SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QAVIEW, GlobalVariables.DB_USER_FCTDCQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTDCQA_QAVIEW);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String URL = data.get("Url");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		TransaccionesTDC_SimotiColombia tdc = new TransaccionesTDC_SimotiColombia (u, testCase);
		Boolean validacion;
		String plaza = data.get("Plaza");
		String tienda = data.get("Tienda");
		String No_tarjeta = data.get("No_Tarjeta");
		String No_autorizacion = data.get("No_Autorizacion");
		String f_inicio = data.get("Fecha_Inicio");
		String f_fin = data.get("Fecha_Fin");
		
		
		// Paso 1
		addStep("Ingresar a la aplicaciÃ³n Access Control V3");
		u.get(URL);
		u.hardWait(3);	
		testCase.addScreenShotCurrentStep(u,"Imagen de login");
		pa.proceedToPortal();
		
		// Paso 2
		addStep("El usuario ingresa usuario y contraseÃ±a y da clic en el botÃ³n â€œIniciar SesiÃ³nâ€�.");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opciÃ³n de SIMOTI.");
		pa.clickSimotiColombia();
		
		// Paso 4
		addStep("El usuario despliega el menÃº: Transacciones ElectrÃ³nicas y selecciona el reporte: Transacciones TDC");
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(3);
		mp.clickTransaccionesTDC();
		u.hardWait(3);
		validacion = tdc.validarPantallaTDC();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		// Paso5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\r\n"
				+ "Opcion: Datos Generales\r\n"
				+ "*Plaza: <Seleccione una Mega Plaza>\r\n"
				+ "*Distrito: < Seleccionar valor>\r\n"
				+ "*Tienda: <Seleccionar valor>\r\n"
				+ "*Tipo de TransacciÃ³n: <V Venta>\r\n"
				+ "*Tipo de Tarjeta: <D DÃ©bito> \r\n"
				+ "*Num. Tarjeta: <capture valor>\r\n"
				+ "*Num. AutorizaciÃ³n: <capture valor>\r\n"
				+ "*Fecha Inicio: dd/mm/aaaa hh:mm\r\n"
				+ "*Fecha Fin: dd/mm/aaaa hh:mm\r\n"
				+ "Dar clic en [Consultar]");
		validacion = tdc.Busquedapor(data.get("Tipo"));
		assertTrue(validacion, "No se encontrÃ³ la opcion a seleccionar");
		u.hardWait(3);
		validacion = tdc.validarPantallaTDC_Transacccion();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		validacion = tdc.DatosPlaza(data.get("Plaza"));
		assertTrue(validacion, "No se encontrÃ³ la opcion a seleccionar");
		//validacion = tdc.tienda(data.get("Tienda"));
		//assertTrue(validacion, "No se encontrÃ³ la opcion a seleccionar");
		validacion = tdc.Transaccion(data.get("Transaccion"));
		assertTrue(validacion, "No se encontrÃ³ la opcion a seleccionar");
		validacion = tdc.TipoTarjeta(data.get("Tipo Tarjeta"));
		assertTrue(validacion, "No se encontrÃ³ la opcion a seleccionar");
		tdc.Numerotajeta_NumeroAutorizacion(No_tarjeta, No_autorizacion);
		tdc.seleccionarFechas(f_inicio,f_fin);
		tdc.clickConsultaBTN();
		assertTrue(tdc.validarHeadersTabla(), "Los headers no coinciden");
		
		//Paso 6
		addStep("Validar en la tabla TDC_DEVOLUTION que los campos coincidan con los mostrados.");
				
		
		
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
		u.getDriver().quit();
	}

}
