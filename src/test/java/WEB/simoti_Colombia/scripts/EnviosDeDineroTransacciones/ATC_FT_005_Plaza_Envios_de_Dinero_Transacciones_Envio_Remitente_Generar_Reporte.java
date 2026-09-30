package WEB.simoti_Colombia.scripts.EnviosDeDineroTransacciones;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.Transacciones_Envio_Retiro;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;


/**Desc: Validar que se realice la b�squeda al ingresar solo el n�mero de tel�fono / Validar que se realice la b�squeda al ingresar solo la IFE
 * MTC-FT-005 Plaza_Env�os de Dinero - Transacciones_Env�o-Remitente_IFE_Generar Reporte_Historica
 * MTC-FT-006 Plaza_Env�os de Dinero - Transacciones_Env�o-Remitente_TEL_Generar Reporte_Intermedia
 * Nota. Para consultar este reporte se tienen que tener al menos 2 datos (telefono,ife,transferencia), 
 * por lo que se para validar la consulta con telefono e ife, en ambos casos se ingresara el dato de transferencia 
 * @author 
 * @date Enero 2023*/
public class ATC_FT_005_Plaza_Envios_de_Dinero_Transacciones_Envio_Remitente_Generar_Reporte extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Plaza_Envios_de_Dinero_Transacciones_Envio_Remitente_Generar_Reporte_test(HashMap<String, String> data) throws IOException, SQLException {
			SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA_QAVIEW, GlobalVariables.DB_USER_FCWMREQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCWMREQA_QAVIEW);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String ife = data.get("IFE");
		String telefono = data.get("Telefono");
		String cliente = data.get("Cliente");
		String tipo = data.get("Tipo");
		String transferencia = data.get("No_Transferencia");
		String fechaInicio = data.get("FechaInicio");
		String fechaFin = data.get("FechaFin");	

		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		Transacciones_Envio_Retiro te = new Transacciones_Envio_Retiro (u, testCase);
		
		 if(!telefono.isEmpty()) {
	            testCase.setTest_Description("MTC-FT-006 Plaza_Env�os de Dinero - Transacciones_Env�o-Remitente_TEL_Generar Reporte_Intermedia\r\n"
	            		+ "Validar que se realice la b�squeda al ingresar el n�mero de tel�fono");            
	     }else if(!ife.isEmpty()) {
	        	testCase.setTest_Description("MTC-FT-005 Plaza_Env�os de Dinero - Transacciones_Env�o-Remitente_IFE_Generar Reporte_Historica\r\n"+
	        			"Validar que se realice la b�squeda al ingresar el IFE");
	     }
		
		// Paso 1
		addStep("Ingresar al portal SIMOTI");
		u.get(url);
		u.hardWait(3);
		pa.proceedToPortal();
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
	
	
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.logginInventarioEspacios(user, password);
		
		// Paso 3
	
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickSimotiColombia();
		boolean validarMenusCedis = mp.validarMenuUsuarioCedis();//tambien funciona para el usuario plaza	
		assertTrue(validarMenusCedis, "No se muestran los menus especificados");
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Env�os de Dinero - Transacciones");
		mp.clickTransaccionesElectronicas();
		u.hardWait(3);
		mp.clickTransacciones();
		boolean validacion = te.validarPantallaE();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		
		// Paso 5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\n"
				+ "-Tipo: <ENV Env�o>\n"
				+ "-Cliente: <Beneficiario>\n"
				+ "-IFE: <Ingresar IFE> \n"
				+ "-N�mero de transferencia: <Dejar vac�o> \n"
				+ "-Tel�fono o Celular: [Dejar vac�o] \n"
				+ "Seleccionar un una fecha menor a 3 meses atr�s\n"
				+ "-Fecha Inicio: 06/01/2010.\n"
				+ "-Fecha Fin: 12/01/2010.\n"
				+ "Dar clic en [Consultar]");
		
		validacion = te.DatosTipo(tipo);
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
		validacion = te.DatosCliente(cliente);
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
		te.typingInput(te.getNoTransferencia(), transferencia);
		if(ife.isEmpty() && !telefono.isEmpty()) {
			te.typingInput(te.getTelefono(), data.get("Telefono"));
		}
        if(!ife.isEmpty() && telefono.isEmpty()) {
        	te.typingInput(te.getIFE(), data.get("IFE"));
		}
		te.selectDate("Inicio", fechaInicio);
		te.selectDate("Fin", fechaFin);
		te.clickBoton(te.getConsultarBtn());
		mp.waitLoading();
		validacion = te.validarExistanResultados();		
		assertTrue(validacion, "No se muestran resultados");

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