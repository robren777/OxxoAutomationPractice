package WEB.simoti_Colombia.scripts.EnviosDeDineroTransacciones;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
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

public class ATC_FT_010_Cedis_Envios_de_Dinero_Transacciones_Retiro_Disposicion_IFE_TEL_Exportar_Reporte extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_Cedis_Envios_de_Dinero_Transacciones_Retiro_Disposicion_IFE_TEL_Exportar_Reporte_test(HashMap<String, String> data) throws IOException {
		
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
	    		"-IFE: [Informar IFE] \r\n" + 
	    		"-N�mero de transferencia:<Dejar vac�o>\r\n" + 
	    		"-Tel�fono o Celular: <Informar n�mero de tel�fono> \r\n" + 
	    		"Seleccionar un una fecha menor a 3 meses atr�s\r\n" + 
	    		"-Fecha Inicio: 06/01/2010.\r\n" + 
	    		"-Fecha Fin: 12/01/2010.\r\n" + 
	    		"Dar clic en [Consultar]");
	    
	    validacion = te.DatosTipo(data.get("Tipo"));
		assertTrue(validacion, "No se encontro la opcion a seleccionar");
		u.hardWait(5);
				
		te.selectDate("Inicio", fechaInicio);
		u.hardWait(1);
		te.selectDate("Fin", fechaFin);	
		u.hardWait(1);
		if(IFE != "") {
			te.typingInput(te.getIFE(), IFE);
		}else {
			Assert.fail("El campo IFE no debe estar vacio");
		}
		u.hardWait(1);
		if(data.get("No_Transferencia") == ""){
			te.typingInput(te.getNoTransferencia(), data.get("No_Transferencia"));
		}else {
			Assert.fail("Numero de transferencia debe estar vacio");
		}	
		u.hardWait(1);
		if(data.get("Telefono") != ""){
		
			te.typingInput(te.getTelefono(), data.get("Telefono"));
		}else {
			Assert.fail("El campo telefono no debe estar vacio");
		}		
		te.clickBoton(te.getConsultarBtn());
		mp.waitLoading();	
				
				
		// Paso 6
		addStep("El usuario da clic en la opci�n: [Exportar]");
		te.clickBoton(te.getExportarBtn());	
		mp.waitLoading();
		u.hardWait(2);
				
		// Paso 7
		addStep("El usuario da clic en Open");		
		String filename = "Reporte _Envio_Dinero_Transacciones_"+me.obtenerFechaActual("ddMMyyyy");
		System.out.print(filename);
		validacion = me.abrirArchivo(filename, "Edge");
		assertTrue(validacion, "No se encontro el archivo");
		
	
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