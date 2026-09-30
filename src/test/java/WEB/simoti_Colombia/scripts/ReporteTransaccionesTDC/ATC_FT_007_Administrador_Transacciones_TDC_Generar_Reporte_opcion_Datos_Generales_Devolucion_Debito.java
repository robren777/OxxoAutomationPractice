package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.Login;
import WEB.simoti_Colombia.om.TransaccionesTDC_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_007_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Devolucion_Debito extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Devolucion_Debito_test(HashMap<String, String> data) throws SQLException {
		
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QAVIEW, GlobalVariables.DB_USER_FCTDCQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTDCQA_QAVIEW);
		
		/*
		 * Variables
		 ******************************************************************************************/		
		String tdcQueryPop = "SELECT \r\n"
				+ "                T.FOLIO\r\n"
				+ "                ,TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') --Fecha\r\n"
				+ "                ,T.PLAZA\r\n"
				+ "                ,T.TIENDA\r\n"
				+ "                ,T.CAJA\r\n"
				+ "                ,SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4) --Tarjeta\r\n"
				+ "                ,T.AFFILIATION --Afiliacion\r\n"
				+ "                ,T.ISSUER --Emisor\r\n"
				+ "                ,TO_CHAR(T.AMOUNT/100,'$999,999,999.99') AMOUNT --Monto\r\n"
				+ "                ,T.SW_AUTH --Autorizacion\r\n"
				+ "                ,DECODE(T.REVERSED,1,'Si','No') --Reversado\r\n"
				+ "                ,T.WM_CODE --Codigo de estatus de transaccion\r\n"
				+ "                ,TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) --Fecha de reverso\r\n"
				+ "                ,SW_REVERSE_CODE --Codigo del reverso\r\n"
				+ "                ,SW_DEV_CODE --Codigo de la devolucion\r\n"
				+ "                ,W.DESCRIPTION --Status\r\n"
				+ "                 ,DECODE( T.BANK, \r\n"
				+ "                          'OTHER',NULL \r\n"
				+ "                         ,'BBVA',NULL \r\n"
				+ "                         ,T.ISSUER ) --Casa Valera\r\n"
				+ "                ,NULL\r\n"
				+ "FROM TPEUSER.TDC_DEVOLUTION T\r\n"
				+ "INNER JOIN TPEUSER.TDC_WM_CODE W ON W.WM_CODE=T.WM_CODE\r\n"
				+ "WHERE \r\n"
				+ " T.CREATION_DATE BETWEEN TO_DATE('%1$s','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('%2$s', 'DD/MM/YYYY HH24:MI:SS') --Fecha inicio y Fecha Fin\r\n"
				+ "  AND T.PLAZA = NVL( '%3$s', T.PLAZA ) --CR_PLAZA\r\n"
				+ "  AND T.TIENDA = NVL( '%4$s', T.TIENDA ) --CR_TIENDA\r\n";
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plaza = data.get("Plaza");
		String distrito = data.get("Distrito");
		String tienda = data.get("Tienda");
		String t_transaccion = data.get("Tipo_de_Transaccion");
		String t_tarjeta = data.get("Tipo_de_Tarjeta");
		String no_transaccion = data.get("No_Transaccion");
		String no_tarjeta = data.get("No_Tarjeta");
		
		String f_inicio = data.get("Fecha_Inicio");
		String f_fin = data.get("Fecha_Fin");
		
		// Paso 1
		addStep("El usuario ingresa a la aplicacion Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(URL);
		Login pa = new Login(u, testCase);	
		TransaccionesTDC_SimotiColombia tdc = new TransaccionesTDC_SimotiColombia(u,testCase);
		
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrasena y da clic en el boton Iniciar Sesion.");
		pa.logginPlatform(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opcion de SIMOTI.");
		pa.click_on_simoti();
		
		// Paso 4
		addStep("El usuario despliega el menu: Transacciones Electronicas y selecciona el reporte: Transacciones TDC");
		pa.transaccionesTDC();
		
		//Paso 5
		addStep("El usuario selecciona la opcion de busqueda por <Datos Generales>.");
		tdc.busquedaDatosGenerales(plaza,distrito,tienda,t_transaccion,t_tarjeta,no_transaccion,no_tarjeta);
		
		tdc.seleccionarFechas(f_inicio,f_fin);
		
		tdc.Consultar();
		assertTrue(tdc.validarHeadersTabla(), "Los headers no coinciden");
		//Paso 6
		addStep("Validar en la tabla TDC_DEVOLUTION que los campos coincidan con los mostrados.");
		
		System.out.println(GlobalVariables.DB_HOST_FCTDCQA_QAVIEW); 


		String queryPop = String.format(tdcQueryPop, f_inicio,f_fin,plaza.substring(0,plaza.indexOf(" ")),tienda.substring(0,tienda.indexOf(" ")));
		SQLResult queryPopResult = executeQuery(dbFCIAS, queryPop);	
		if(!queryPopResult.isEmpty()){
			assertTrue(tdc.validarConsulta(queryPopResult),"Los datos no coinciden con la BD.");
		}
		else
			assertTrue(false,"La consulta con la BD no funciono.");
		
		testCase.addQueryEvidenceCurrentStep(queryPopResult);	
		
		//left: 4.34783% multiplicar por hora en formato 24h
		//left: 1.69492% multiplicar por minuto
		
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
		return "ATC_FT_007_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Devolucion_Debito";
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