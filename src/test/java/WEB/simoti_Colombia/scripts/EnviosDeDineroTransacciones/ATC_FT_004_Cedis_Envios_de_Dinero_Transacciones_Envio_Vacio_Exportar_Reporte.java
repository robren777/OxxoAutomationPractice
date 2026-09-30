package WEB.simoti_Colombia.scripts.EnviosDeDineroTransacciones;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.Transacciones_Envio_Retiro;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


/**Desc: Validar que al no ingresar un n�mero de transferencia el sistema no permita hacer la b�squeda.
 * Validar que el sistema muestre un mensaje de error
 * MTC-FT-004 Cedis_Env�os de Dinero - Transacciones_Env�o-Beneficiario_Vac�o_Exportar Reporte
 * MTC-FT-007 Cedis_Env�os de Dinero - Transacciones_Env�o-Remitente_Vac�o_Exportar Reporte
 * Nota. Tiene que estar vacio alguno de los siguientes 2 campos en el dataprovider: Telefono o IFE
 * @author 
 * @date Enero 2023*/
public class ATC_FT_004_Cedis_Envios_de_Dinero_Transacciones_Envio_Vacio_Exportar_Reporte extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Cedis_Envios_de_Dinero_Transacciones_Envio_Vacio_Exportar_Reporte_test(HashMap<String, String> data) {
		
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
		String mensajeError = "Estimado usuario, debe capturar al menos 2 de los siguientes 3 datos: N�mero de transferencia, N�mero de IFE, N�mero de Tel�fono en la secci�n de filtros";

        if(cliente.equals("B Beneficiario")) {
            testCase.setTest_Description("MTC-FT-007 Cedis_Envios de Dinero - Transacciones_Envio-Beneficiario_Vacio_Exportar Reporte\r\n"
            		+ "Validar que al no ingresar un n�mero de transferencia el sistema no permita hacer la b�squeda.\r\n"
            		+ "Validar que el sistema muestre un mensaje de error");            
        }else if(cliente.equals("R Remitente")) {
        	testCase.setTest_Description("MTC-FT-004 Cedis_Envios de Dinero - Transacciones_Envio-Remitente_Vacio_Exportar Reporte\r\n"+
        			"Validar que al no ingresar IFE, N�mero de transferencia ni T�lefono el sistema muestre el mensaje \"Debe de introducir alguno de los siguientes datos: \r\n" + 
        			" - IFE\r\n" + 
        			" - N�mero de Transferencia\r\n" + 
        			" - Tel�fono\r\n" + 
        			"Validar que se realice la b�squeda al ingresar solo el N�mero de transferencia.");
        }
        
        
        
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(url);
		
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		Transacciones_Envio_Retiro te = new Transacciones_Envio_Retiro (u, testCase);
		
		pa.proceedToPortal();
		testCase.addScreenShotCurrentStep(u,"Imagen de login");
		
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
		addStep("El usuario selecciona valor en los campos que se indique\r\n" + 
				"-Tipo: <Env�o>\r\n" + 
				"-Cliente: <B Beneficiario>\r\n" + 
				"-IFE: <Dejar vac�o> \r\n" + 
				"-N�mero de transferencia: <Dejar vac�o> \r\n" + 
				"-Tel�fono o Celular:<Dejar vac�o> \r\n" + 
				"-Fecha Inicio: 10/01/2010.\r\n" + 
				"-Fecha Fin: 160/01/2010.\r\n" + 
				"Dar clic en [Consultar]. El sistema muestra el mensaje \"Debe de introducir el n�mero de transferencia\"");
		
		validacion = te.DatosTipo(tipo);
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
		validacion = te.DatosCliente(cliente);
		assertTrue(validacion, "No se encontr� la opcion a seleccionar");
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
		validacion = te.validarMensaje(mensajeError);
		assertTrue(validacion, "No se muestran el mensaje de error");
				
		// Paso 6
		addStep("Informar el n�mero de transferencia. Pulsar Consultar");
		u.mouseOverAndClick(te.getNoTransferencia());
		te.typingInput(te.getNoTransferencia(), transferencia);
		te.clickBoton(te.getConsultarBtn());
		mp.waitLoading();
		validacion = te.validarExistanResultados();		
		assertTrue(validacion, "No se muestran resultados");
		
		// Paso 7
		addStep("El usuario da clic en la opci�n: [Exportar]");
		te.clickExportarBtn();
		
		// Paso 8
		addStep("El usuario da clic en Open. ");
		
		
		if(cliente.equals("B Beneficiario")) {
			
		// Paso 9
		addStep("El usuario valida que el excel muestre las columnas y la informaci�n de manera correcta.\r\n" + 
				"-En el encabezado se muestra:\r\n" + 
				"SIMOTI\r\n" + 
				"Env�o de Dinero\r\n" + 
				"Reporte de Transferencias\r\n" + 
				"- Secci�n de filtros:\r\n" + 
				"Se deben de mostrar los mismos filtros del reporte, con los datos con los que se realiz� la consulta.\r\n" + 
				"-Se deben mostrar las columnas:\r\n" + 
				" \r\n" + 
				"1. No.\r\n" + 
				"2. Folio\r\n" + 
				"3. Fecha\r\n" + 
				"4. Plaza\r\n" + 
				"5. Tienda\r\n" + 
				"6. Caja\r\n" + 
				"7. No. Trasferencia\r\n" + 
				"8. No. IFE\r\n" + 
				"9. Tel�fono\r\n" + 
				"10. Beneficiario\r\n" + 
				"11. Remitente\r\n" + 
				"12. Monto\r\n" + 
				"13. Comisi�n \r\n" + 
				"14. IVA \r\n" + 
				"15. Total\r\n" + 
				"16. Fecha de Cobro\r\n" + 
				"17. Plaza de Cobro\r\n" + 
				"18. Tienda de Cobro\r\n" + 
				"19. No. Identificaci�n Beneficiario");
		
		// Paso 10
		addStep("Validar que los registros del aplicativo sean iguales a los de los registros del Excel ");
		
		
		
		
		}
		
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