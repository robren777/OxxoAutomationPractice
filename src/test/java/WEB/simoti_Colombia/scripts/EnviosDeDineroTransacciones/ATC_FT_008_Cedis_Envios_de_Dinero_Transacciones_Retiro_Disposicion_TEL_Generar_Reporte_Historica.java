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
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_008_Cedis_Envios_de_Dinero_Transacciones_Retiro_Disposicion_TEL_Generar_Reporte_Historica extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_Cedis_Envios_de_Dinero_Transacciones_Retiro_Disposicion_TEL_Generar_Reporte_Historica_test(HashMap<String, String> data) throws IOException, SQLException {
		
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA_QAVIEW, GlobalVariables.DB_USER_FCWMREQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCWMREQA_QAVIEW);

		/*dbFCWMREQA
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String URL = data.get("Url");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		Transacciones_Envio_Retiro te = new Transacciones_Envio_Retiro (u, testCase);
		Boolean validacion;
		String fechaInicio = data.get("FechaInicio");
		String fechaFin = data.get("FechaFin");
		String registros = "Sin resultados";
		int cont1=1;
		int cont=1;
	//	fechaInicio = te.formatDate(fechaInicio);
		//fechaFin = te.formatDate(fechaFin);

		String tdcQueryPop = "SELECT \r\n"
				+ "TR.FOLIO, \r\n"
				+ "TO_CHAR(TR.CREATION_DATE,'YYYY/MM/DD HH24:MI:SS') AS FECHA, \r\n"
				+ "TR.PLAZA, \r\n"
				+ "TR.TIENDA, \r\n"
				+ "TR.CAJA, \r\n"
				+ "RET_REF_NO AS NO_TRANSFERENCIA, \r\n"
				+ "DECODE(TR.MER_CODE, '01', 'IFE', '02', 'CEDULA', '03', 'PASAPORTE', '04', 'CARTILLA MILITAR', '05', 'CREDENCIAL CONSULAR', 'NA') AS TIPO_IDENTIFICACION, \r\n"
				+ "TR.PAN AS NO_IFE, \r\n"
				+ "TR.ADITIONAL_DATA AS TELEFONO,\r\n"
				+ "UPPER(TR.CA_NAME_LOC||' '||TR.CA_TERM_ID||' '||TR.CA_ACEP_ID) AS BENEFICIARIO,\r\n"
				+ "TO_CHAR(TR.AMOUNT/100,'$9999999999.99') AS MONTO\r\n"
				+ "    FROM TPEREP.TPE_FR_TRANSACTION TR\r\n"
				+ "    WHERE TR.APPLICATION = 'TSF'\r\n";
				if(data.get("No_Transferencia") != "")
				{
					tdcQueryPop = tdcQueryPop + "	   AND RET_REF_NO = '%"+Integer.toString(cont)+"$s'";
					cont++;
				}
				if(data.get("Telefono") != "")
				{
					tdcQueryPop = tdcQueryPop + "	   AND TR.ADITIONAL_DATA = '%"+Integer.toString(cont)+"$s'";
					cont++;
				}
				if(data.get("IFE") != "")
				{
					tdcQueryPop = tdcQueryPop + "	   AND TR.PAN = '%"+Integer.toString(cont)+"$s'";
					cont++;
				}
				tdcQueryPop = tdcQueryPop + "    AND TR.ENTITY = 'OXXO'\r\n"
				+ "    AND TR.MTI ='0200'\r\n"
				+ "    AND TR.OPERATION ='TRN03'\r\n"
				+ "    AND TR.PROC_CODE = NVL('DISP',TR.PROC_CODE)\r\n"
				+ "    AND TR.WM_CODE ='101'\r\n"
				+ "	   AND  TR.CREATION_DATE BETWEEN TO_DATE('%"+Integer.toString(cont)+"$s 00:00:00 ','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('%"+Integer.toString(cont+1)+"$s 23:59:00 ', 'DD/MM/YYYY HH24:MI:SS') --Fecha inicio y Fecha Fin\r\n"
				+ "    ORDER BY TR.CREATION_DATE DESC";
		
				/**
				 * 
				 */
				String tdcQueryPop1 = "SELECT \r\n"
						+ "TR.FOLIO, \r\n"
						+ "TO_CHAR(TR.CREATION_DATE,'YYYY/MM/DD HH24:MI:SS') AS FECHA, \r\n"
						+ "TR.PLAZA, \r\n"
						+ "TR.TIENDA, \r\n"
						+ "TR.CAJA, \r\n"
						+ "RET_REF_NO AS NO_TRANSFERENCIA, \r\n"
						+ "DECODE(TR.MER_CODE, '01', 'IFE', '02', 'CEDULA', '03', 'PASAPORTE', '04', 'CARTILLA MILITAR', '05', 'CREDENCIAL CONSULAR', 'NA') AS TIPO_IDENTIFICACION, \r\n"
						+ "TR.PAN AS NO_IFE, \r\n"
						+ "TR.ADITIONAL_DATA AS TELEFONO,\r\n"
						+ "UPPER(TR.CA_NAME_LOC||' '||TR.CA_TERM_ID||' '||TR.CA_ACEP_ID) AS BENEFICIARIO,\r\n"
						+ "TO_CHAR(TR.AMOUNT/100,'$9999999999.99') AS MONTO\r\n"
						+ "    FROM TPEREP.TPE_FR_TRANSACTION_ED TR\r\n"
						+ "    WHERE TR.APPLICATION = 'TSF'\r\n";
						if(data.get("No_Transferencia") != "")
						{
							tdcQueryPop1 = tdcQueryPop1 + "	   AND RET_REF_NO = '%"+Integer.toString(cont1)+"$s'";
							cont++;
						}
						if(data.get("Telefono") != "")
						{
							tdcQueryPop1 = tdcQueryPop1 + "	   AND TR.ADITIONAL_DATA = '%"+Integer.toString(cont1)+"$s'";
							cont++;
						}
						if(data.get("IFE") != "")
						{
							tdcQueryPop1 = tdcQueryPop1 + "	   AND TR.PAN = '%"+Integer.toString(cont1)+"$s'";
							cont++;
						}
						tdcQueryPop1 = tdcQueryPop1 + "    AND TR.ENTITY = 'OXXO'\r\n"
						+ "    AND TR.MTI ='0200'\r\n"
						+ "    AND TR.OPERATION ='TRN03'\r\n"
						+ "    AND TR.PROC_CODE = NVL('DISP',TR.PROC_CODE)\r\n"
						+ "    AND TR.WM_CODE ='101'\r\n"
						+ "	   AND  TR.CREATION_DATE BETWEEN TO_DATE('%"+Integer.toString(cont1)+"$s 00:00:00 ','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('%"+Integer.toString(cont1+1)+"$s 23:59:00 ', 'DD/MM/YYYY HH24:MI:SS') --Fecha inicio y Fecha Fin\r\n"
						+ "    ORDER BY TR.CREATION_DATE DESC";
				/**
				 * 
				 */
				
					
				
		
		// Paso 1
		addStep("Ingresar al portal de aplicaciones");
		u.get(URL);
		u.hardWait(3);

		//Login pa = new Login(u, testCase);		
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
		String queryPop = "";
		if(data.get("IFE") != "" && data.get("No_Transferencia") != "" && data.get("Telefono") != "") {
			System.out.println("Todos");
			queryPop = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("Telefono"),data.get("IFE"),fechaInicio,fechaFin);
		}
		else if(data.get("IFE") != "" && data.get("No_Transferencia") != "") {
			System.out.println("IFE trans");
			queryPop = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("IFE"),fechaInicio,fechaFin);
		}
		else if(data.get("IFE") != "" && data.get("Telefono") != "") {
			System.out.println("IFE Fono");
			queryPop = String.format(tdcQueryPop,data.get("Telefono"),data.get("IFE"),fechaInicio,fechaFin);
		}
		else if(data.get("No_Transferencia") != "" && data.get("Telefono") != "")
		{
			System.out.println("Trans fono");
			queryPop = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("Telefono"),fechaInicio,fechaFin);
		}
		else
		{
			assertTrue(false,"Faltan datos para hacer la consulta.");
		}
		/**
		 * 
		 */
		String queryPop1 = "";
		
		if(data.get("IFE") != "" && data.get("No_Transferencia") != "" && data.get("Telefono") != "") 
			queryPop1 = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("Telefono"),data.get("IFE"),fechaInicio,fechaFin);
		else if(data.get("IFE") != "" && data.get("No_Transferencia") != "")
			queryPop1 = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("IFE"),fechaInicio,fechaFin);
		else if(data.get("IFE") != "" && data.get("Telefono") != "")
			queryPop1 = String.format(tdcQueryPop,data.get("Telefono"),data.get("IFE"),fechaInicio,fechaFin);
		else if(data.get("No_Transferencia") != "" && data.get("Telefono") != "")
			queryPop1 = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("Telefono"),fechaInicio,fechaFin);
		else
			assertTrue(false,"Faltan datos para hacer la consulta.");
		/**
		 * 
		 */
		
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		// Paso 3
	
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickSimotiColombia();
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Env�os de Dinero - Transacciones");
		mp.clickTransaccionesElectronicas();
		u.hardWait(3); 
		mp.clickTransacciones();
		validacion = te.validarPantallaE();
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
		//validacion = te.DatosEnvioDinero(data.get("Tipo"), data.get("Cliente"));
		te.DatosTipo(data.get("Tipo"));
		u.mouseOverAndClick(te.getTelefono());
		te.typingInput(te.getTelefono(), data.get("Telefono"));
		te.selectDate("Inicio",data.get("FechaInicio"));
		te.selectDate("Fin", data.get("FechaFin"));
		te.clickBoton(te.getConsultarBtn());
		mp.waitLoading();
		validacion = te.validarMensaje("Estimado usuario, debe capturar al menos 2 de los siguientes 3 datos: N�mero de transferencia, N�mero de IFE, N�mero de Tel�fono en la secci�n de filtros");
		assertTrue(validacion, "No se mostro el mensaje de error");
		
		//paso 6
		addStep("Ingresa No.de Trasnferencia y da click en boton consultar .");
		te.typingInput(te.getNoTransferencia(), data.get("No_Transferencia"));
		te.clickBoton(te.getConsultarBtn());
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "registros encontrados");
		
		
		//paso 7 
		System.out.println(GlobalVariables.DB_HOST_FCWMREQA_QAVIEW); 
		SQLResult queryPopResult = executeQuery(dbFCWMREQA, queryPop);	
		SQLResult queryPop1Result = executeQuery(dbFCWMREQA, queryPop1);	
		te.imprimeConsulta(queryPop1Result);
		te.imprimeConsulta(queryPopResult);
		testCase.addQueryEvidenceCurrentStep(queryPopResult);
		//testCase.addQueryEvidenceCurrentStep(queryPop1Result);
			if(!queryPopResult.isEmpty()){
				assertTrue(te.validarConsulta(queryPopResult),"Los datos no coinciden con la BD.");
			}
			else 
				assertTrue(false,"La consulta con la BD no funciono.");
			
			if(!queryPop1Result.isEmpty()){
				assertTrue(te.validarConsulta(queryPop1Result),"Los datos no coinciden con la BD.");
			}
			else
				assertTrue(false,"La consulta con la BD no funciono.");
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