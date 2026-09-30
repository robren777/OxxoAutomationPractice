package WEB.simoti_Colombia.scripts.EnviosDeDineroTransacciones;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.Transacciones_Envio_Retiro;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_011_Plaza_Envios_de_Dinero_Transacciones_Retiro_Disposicion_Vacio_Generar_Reporte extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Plaza_Envios_de_Dinero_Transacciones_Retiro_Disposicion_Vacio_Generar_Reporte_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String IFE = data.get("IFE");
		String fechaInicio = data.get("Fecha_Inicio");
		String fechaFin = data.get("Fecha_Fin");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		Transacciones_Envio_Retiro te = new Transacciones_Envio_Retiro (u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
	
		
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u.get(URL);
		pa.proceedToPortal();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickSimotiColombia();
		u.hardWait(1);
		boolean validacionMenu = mp.validarMenuUsuarioCedis();//este metodo tambien sirve para validar el menu del usuario Plaza
		assertTrue(validacionMenu, "No se muestran los menus: - Transmisi�n de Datos \r\n" + 
				"- Integridad de Datos\r\n" + 
				"- �rdenes de Compra\r\n" + 
				"- Folio de Pago\r\n" + 
				"- Transacciones Electr�nicas \r\n" + 
				"- Caja Inteligente");
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Env�os de Dinero - Transacciones");
		mp.clickTransaccionesElectronicas();
		u.hardWait(3);
		mp.clickTransacciones();
		boolean validacion = te.validarPantallaE();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		
		// Paso 5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\r\n" + 
				"-Tipo: <DISP Retiro/Disposici�n>\r\n" + 
				"-Cliente: <B Beneficiario> (al seleccionar la opcion DISP autom�ticamente se marcara la opci�n Beneficiario y se deshabilitar� el filtro.\r\n" + 
				"-IFE:<Dejar vac�o>\r\n" + 
				"-N�mero de transferencia: <Dejar vac�o>\r\n" + 
				"-Tel�fono o Celular: <Dejar vac�o>\r\n" + 
				"Seleccionar fecha del d�a\r\n" + 
				"-Fecha Inicio: 06/01/2010.\r\n" + 
				"-Fecha Fin: 12/01/2010.\r\n" + 
				"Dar clic en [Consultar]\r\n" + 
				"El sistema muestra el mensaje: Estimado usuario, debe capturar al menos 2 de los siguientes 3 datos: N�mero de transferencia, N�mero de IFE, N�mero de Tel�fono en la secci�n de filtros");
		
	    validacion = te.DatosTipo(data.get("Tipo"));
		assertTrue(validacion, "No se encontro la opcion a seleccionar");
		u.hardWait(5);
				
		te.selectDate("Inicio", fechaInicio);
		te.selectDate("Fin", fechaFin);	
		
		te.clickBoton(te.getConsultarBtn());
		mp.waitLoading();	
		
		validacion = te.validarMensaje(data.get("MensajeError"));
		assertTrue(validacion, "No se mostro el mensaje de error");
		
		// Paso 6
		addStep("Informar un n�mero de transferencia y un n�mero de tel�fono. Pulsar el bot�n Consultar");
		
		if(data.get("No_Transferencia") != ""){
			te.typingInput(te.getNoTransferencia(), data.get("No_Transferencia"));
		}else {
			Assert.fail("Numero de transferencia no debe estar vacio");
		}	
		u.hardWait(1);
		if(data.get("Telefono") != ""){	
			te.typingInput(te.getTelefono(), data.get("Telefono"));
		}else {
			Assert.fail("El campo telefono no debe estar vacio");
		}		
		
		te.clickBoton(te.getConsultarBtn());
		mp.waitLoading();	
		
		validacion = te.validarExistanResultados();
		assertTrue(validacion, "No se encontraron resultados en la consulta");
		
		
		
		
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