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

public class ATC_FT_009_Plaza_Envios_de_Dinero_Transacciones_Retiro_Disposicion_IFE_Generar_Reporte_Diaria extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_Plaza_Envios_de_Dinero_Transacciones_Retiro_Disposicion_IFE_Generar_Reporte_Diaria_test(HashMap<String, String> data) throws IOException, SQLException {
		
		SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA_QAVIEW, GlobalVariables.DB_USER_FCWMREQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCWMREQA_QAVIEW);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String URL = data.get("Url");
		String fechaInicio = data.get("Fecha_Inicio");
		String fechaFin = data.get("Fecha_Fin");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		Transacciones_Envio_Retiro te = new Transacciones_Envio_Retiro (u, testCase);
		Boolean validacion;

		//fechaInicio = te.formatDate(fechaInicio);
		//fechaFin = te.formatDate(fechaFin);
		
		int cont = 1;
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
		

		// Paso 1
		addStep("Ingresar al portal de aplicaciones");
		u.get(URL);
		u.hardWait(3);	
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones1");
		pa.proceedToPortal();
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones2");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contraseña y da clic en el botón <Iniciar Sesión>.");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opcion de SIMOTI.");
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
		addStep("El usuario despliega el menú: Transacciones Electrónicas y selecciona el reporte: Envíos de Dinero - Transacciones");
		mp.clickTransaccionesElectronicas();
		u.hardWait(3);
		mp.clickTransacciones();
		validacion = te.validarPantallaE();
		assertTrue(validacion, "No se muestran correctamente los elementos de la pantalla");
		
		// Paso 5
		String step5 = "El usuario selecciona o captura valor en el filtro que se indique:\r\n"
				+ "-Tipo: <DISP Retiro/Disposición>\r\n"
				+ "-Cliente: <B Beneficiario> (al seleccionar la opcion DISP automáticamente se marcara la opción Beneficiario y se deshabilitará el filtro.\r\n"
				+ "-IFE: ";
				if(data.get("IFE") != "")
					step5 = step5 + "[Informar IFE] \r\n";
				else
					step5 = step5 + "<Dejar vacio>\r\n";
				step5 = step5 + "-Numero de transferencia: ";
				if(data.get("No_Transferencia") != "")
					step5 = step5 + "[Informar Transferencia] \r\n";
				else
					step5 = step5 + "<Dejar vacio>\r\n";
				step5 = step5 + "-Telefono o Celular: ";
				if(data.get("Telefono") != "")
					step5 = step5 + "[Informar Telefono] \r\n";
				else
					step5 = step5 + "<Dejar vacio>\r\n";
				step5 = step5 + "Seleccionar fecha del dia\r\n"
				+ "-Fecha Inicio: 06/01/2010.\r\n"
				+ "-Fecha Fin: 12/01/2010.\r\n"
				+ "Dar clic en [Consultar]\r\n";
				
		addStep(step5);
		
		validacion = te.DatosTipo(data.get("Tipo"));
		assertTrue(validacion, "No se encontro la opcion a seleccionar");
		u.hardWait(5);
		te.selectDate("Inicio", fechaInicio);
		te.selectDate("Fin", fechaFin);
		
		if(data.get("IFE") == "" && data.get("No_Transferencia")!= "") {
			te.typingInput(te.getTelefono(), data.get("Telefono"));
		}else if (data.get("Telefono") == "" && data.get("No_Transferencia") != "") {
			te.typingInput(te.getIFE(), data.get("IFE"));
		}
		  	
		 te.clickBoton(te.getConsultarBtn());
		 mp.waitLoading();

		 validacion = te.validarMensaje(data.get("MensajeError"));
		 assertTrue(validacion, "No se mostro el mensaje de error");
		
		//paso 6
		addStep("Informar un n�mero de transferencia.\r\n" + 
					"Pulsar el bot�n Consultar");
			te.typingInput(te.getNoTransferencia(), data.get("No_Transferencia"));
			te.clickBoton(te.getConsultarBtn());

		
		//paso 7
		addStep("Consultar la bd con los filtros seleccionados\r\n" + 
				"Tabla diaria\r\n" + 
				"SELECT TR.FOLIO, TO_CHAR(TR.CREATION_DATE,'YYYY/MM/DD HH24:MI:SS') AS FECHA, TR.PLAZA, TR.TIENDA, TR.CAJA, RET_REF_NO AS NO_TRASFERENCIA, DECODE(TR.MER_CODE, '01', 'IFE', '02', 'CEDULA', '03', 'PASAPORTE', '04', 'CARTILLA MILITAR', '05', 'CREDENCIAL CONSULAR', 'NA') AS TIPO_IDENTIFICACION, TR.PAN AS NO_IFE, TR.ADITIONAL_DATA AS TELEFONO, UPPER(TR.CA_NAME_LOC||' '||TR.CA_TERM_ID||' '||TR.CA_ACEP_ID) AS BENEFICIARIO, TO_CHAR(TR.AMOUNT/100,'$9999999999.99') AS MONTO\r\n" + 
				"    FROM TPE_FR_TRANSACTION TR\r\n" + 
				"    WHERE TR.APPLICATION = 'TSF'\r\n" + 
				"    AND TR.ENTITY = 'OXXO'\r\n" + 
				"    AND TR.MTI ='0200'\r\n" + 
				"    AND TR.OPERATION ='TRN03'\r\n" + 
				"    AND TR.PROC_CODE = NVL('DISP',TR.PROC_CODE)\r\n" + 
				"    --AND TR.PAN = NVL('1173129223068',TR.PAN)\r\n" + 
				"    AND TRUNC(TR.CREATION_DATE) >= TO_DATE('01/07/2015','DD/MM/YYYY')\r\n" + 
				"    AND TRUNC(TR.CREATION_DATE) <= TO_DATE( '08/07/2015' ,'DD/MM/YYYY')\r\n" + 
				"    AND TR.RET_REF_NO = NVL('OXXO2640098',TR.RET_REF_NO)\r\n" + 
				"    --AND TR.ADITIONAL_DATA = NVL('6643675007',TR.ADITIONAL_DATA)\r\n" + 
				"    AND TR.WM_CODE ='101'\r\n" + 
				"    ORDER BY TR.CREATION_DATE DESC" );
	
		
		String queryPop = "";
		if(data.get("IFE") != "" && data.get("No_Transferencia") != "" && data.get("Telefono") != "") {
			System.out.println("Todos");
			queryPop = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("Telefono"),data.get("IFE"),fechaInicio,fechaFin);
		}
		else if(data.get("IFE") != "" && data.get("No_Transferencia") != "") {
			System.out.println("IFE trans");
			queryPop = String.format(tdcQueryPop,data.get("No_Transferencia"),data.get("IFE"),fechaInicio,fechaFin);
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
		
		System.out.println(GlobalVariables.DB_HOST_FCWMREQA_QAVIEW); 
		
		SQLResult queryPopResult = executeQuery(dbFCWMREQA, queryPop);	
//		te.imprimeConsulta(queryPopResult);
			if(!queryPopResult.isEmpty()){
				assertTrue(te.validarConsulta(queryPopResult),"Los datos no coinciden con la BD.");
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