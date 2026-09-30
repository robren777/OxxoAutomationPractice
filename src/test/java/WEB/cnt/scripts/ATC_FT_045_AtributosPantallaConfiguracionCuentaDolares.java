package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
public class ATC_FT_045_AtributosPantallaConfiguracionCuentaDolares extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_045_AtributosPantallaConfiguracionCuentaDolares_Test(
			HashMap<String, String> data) throws Exception {
		
		/*
		 * Utilerias
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String Plaza = data.get("Plaza");
		String TiendaE = data.get("TiendaE");
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String cheque = data.get("cheque");
		String dolares = data.get("dolares");
		String efectivo = data.get("efectivo");
		String TDD1 = data.get("TDD1");
		String TDC1 = data.get("TDC1");
		String Referencia = "";
		String t1 = "t1";
		String t2 = "t2";
		String t3 = "t3";
		String t4 = "t4";
		String t5 = "t5";
		String vales = data.get("vales"); 

		String queryT = //"SELECT lid_banco_personal from tiendas.t_operacion" + "where id_tienda" + 
				//"in(SELECT id_tienda from tiendas.t_tiendas"  + "where cr_tienda" + "in '507QG' and CR_SUPERIOR IN '10MON')";
	     "SELECT lid_banco_personal from tiendas.t_operacion where id_tienda " 
		+ "in (SELECT id_tienda from tiendas.t_tiendas where cr_tienda in '%s' and CR_SUPERIOR IN '%s')";
		String query2 = "SELECT * FROM TIENDAS.T_BANCOS where banco_corresponsal = '%s'"; //query para el paso 4
		
		String queryT_Banco = "SELECT cuenta FROM Tiendas.T_BANCO_CTA_DLLS where cr_plaza = '" + Plaza + "'";
		//String queryT_Banco = "SELECT BANCO_CTA_DLLS FROM TIENDAS.T_OPERACION where id_tienda" + 
			//	" IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '"+ TiendaE +"'"+  " AND CR_SUPERIOR IN '" + Plaza +"')"; //query para el paso 5
		
		/*String queryV = "SELECT DIGITO_VERIFICADOR FROM TIENDAS.T_OPERACION where id_tienda" + 
				"in(SELECT ID_TIENDA FROM TIENDAS.t_tiendas" + "WHERE CR_TIENDA IN '500QN '" + 
				"AND CR_SUPERIOR IN '10AGC')"; */
		
		String queryVerificador = "SELECT DIGITO_VERIFICADOR FROM TIENDAS.T_OPERACION where id_tienda " + 
				"IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaE + "'" +  " AND CR_SUPERIOR IN '" + Plaza +"')"; //query paso 6
				
		String queryReference = "SELECT REFERENCIA FROM TIENDAS.T_OPERACION where id_tienda " +  
				"IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaE + "'" + " AND CR_SUPERIOR IN '" + Plaza +"')"; //query paso 7
		
		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		//u.get(data.get("Url")); // toma el valor Url del data provider
		u.get("http://10.184.32.40:8000/tiendas/html/login.html");
		u.hardWait(3); // tiempo en segundos

		//pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

		
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(PlazaE);

		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		
		//------------------------------------PASO 2
		Permisos_y_Contratos po = new Permisos_y_Contratos(u, testCase);
		addStep("Dar clic en el link Permisos y Contratos"); 
		po.PermCon();
		
		//Paso 3
		//addStep("Dar click en en link CONFIGURACION CUENTA DOLARES.");
		//po.pantallaConfigCuentaDolares();
		//System.out.println(ref);
        
		//Paso 3 y 4 
		//Permisos_y_Contratos corresponsal = new Permisos_y_Contratos(u, testCase);
		addStep("Dar click en el link configuraci�n cuenta d�lares e ir al campo banco corresponsal");
		//System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		po.pantallaConfigCuentaDolares();
		
		String bancoCorresponsal = po.CampoCorr();
		String banco_corresponsal = String.format(queryT, Tienda.substring(0, 5), PlazaE.substring(0, 5));
		//System.out.print(banco_corresponsal);
		SQLResult queryResult = executeQuery(dbFCIAS, banco_corresponsal);
		testCase.addQueryEvidenceCurrentStep(queryResult);
		boolean empty = queryResult.isEmpty();
		if (empty) {
			
			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
		
		} else {
			if(bancoCorresponsal != queryResult.getData(0, "lid_banco_personal") ) {
				String queryR = String.format(query2, queryResult.getData(0, "lid_banco_personal"));
				//System.out.println(queryR);
				queryResult = executeQuery(dbFCIAS, query2);
				//testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
				testCase.addTextEvidenceCurrentStep("coinciden los bancos corresponsales en BD y Tienda");
			}
		}					
		assertFalse(empty, "son iguales los bancos corresponsales en bd y tienda");
		testCase.addTextEvidenceCurrentStep("no coinciden los bancos corresponsales en bd y tienda");
		
		
		//Paso 5
		addStep("Ir al campo cuenta/convenio");
		//System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		String cuentas = po.CampoCuentaConvenio();
		String cuenta_corresponsal = String.format(queryT_Banco, Tienda.substring(0, 5), PlazaE.substring(0, 5));
		System.out.print(cuenta_corresponsal);
		SQLResult queryResult1 = executeQuery(dbFCIAS, cuenta_corresponsal);
		//testCase.addQueryEvidenceCurrentStep(queryResult1);
		boolean empty1 = queryResult1.isEmpty();
		if (empty1) {
			
			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
		
		} else {
			if(cuentas == queryResult1.getData(0, "CUENTA")) {
				String queryC = String.format(queryT_Banco, queryResult1.getData(0, "CUENTA"));
				System.out.println(queryC);
				queryResult1 = executeQuery(dbFCIAS, queryC);
				testCase.addQueryEvidenceCurrentStep(queryResult1); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
				testCase.addTextEvidenceCurrentStep("coinciden las cuentas usd en BD y Tienda");
			}
		}
		assertFalse(empty, "No son iguales las cuentas usd en bd y tienda");
		testCase.addQueryEvidenceCurrentStep(queryResult1);
		testCase.addTextEvidenceCurrentStep("no coinciden las cuentas usd en bd y tienda");
		/*boolean igual3 = true;
		if (cuenta.equals(cuenta) == true) {
			igual3 = true;
			testCase.addTextEvidenceCurrentStep("Coinciden en BD y en Portal CNT ");
		} else {
			igual3 = false;
			testCase.addTextEvidenceCurrentStep("NO Coinciden en BD y en Portal CNT ");
		}
		assertTrue(igual3, "No son iguales las cuentas usd en bd y tienda");*/
		//testCase.addTextEvidenceCurrentStep("no coinciden la cuenta usd en bd y tienda");
		//assertFalse(empty, "No son iguales los convenios en bd y tienda");
		
		
		//Paso 6
		addStep("ir al campo digito verificador");
		//System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		//po.CampoCorr();
		String digito = po.ExtraerCodVerificadorSinCuentaDolares();
		//System.out.println("digito  :" + digito);
		//int number = Integer.parseInt(digito);
		//String cuenta_ver = po.CampoCorr();
		String cuenta_veri = String.format(queryVerificador, Tienda.substring(0, 5), PlazaE.substring(0, 5));
		SQLResult queryResult2 = executeQuery(dbFCIAS, cuenta_veri);
		testCase.addQueryEvidenceCurrentStep(queryResult2);
		boolean empty2 = queryResult2.isEmpty();
		if (empty2) {
			
			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
		
		} else {
			if(digito == queryResult2.getData(0, "DIGITO_VERIFICADOR")) {
				String queryV = String.format(queryVerificador, queryResult2.getData(0, "DIGITO_VERIFICADOR"));
				System.out.println(queryV);
				queryResult = executeQuery(dbFCIAS, queryV);
				testCase.addQueryEvidenceCurrentStep(queryResult2); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
				testCase.addTextEvidenceCurrentStep("coinciden los codigos verificadores en BD y tienda");
			}
		//assertTrue(digito , "coinciden los codigos verificadores en bd y tienda");
		}		
		assertFalse(empty, "No son iguales los codigos verificadores en bd y tienda");
		testCase.addTextEvidenceCurrentStep("coinciden los codigos verificadores en bd y tienda");
		/*boolean igual2 = true;
		if (queryResult2.equals(digito) == true) {
			igual2 = true;
			testCase.addTextEvidenceCurrentStep("Coinciden en BD y en Portal CNT ");
		} else {
			igual2 = false;
			testCase.addTextEvidenceCurrentStep("NO Coinciden en BD y en Portal CNT ");
		}
		assertTrue(igual2, "No son iguales las referencias en bd y tienda");*/
		//assertFalse(empty, "no son iguales los codigos verificadores en bd y tienda");
 
		//Paso 7
		addStep("ir al campo referencia");
		//po.CampoCorr();
		String ref = po.ExtraerReferenciaBancariaSinCuentaDolares();
		System.out.println(ref);

		SQLResult ExecuteQueryplanogramacion4 = executeQuery(dbFCIAS, queryReference);
		System.out.print("consulta" + queryReference);
		boolean StatusQueryplanogramacion4 = ExecuteQueryplanogramacion4.isEmpty();

		if (!StatusQueryplanogramacion4) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion4);
			Referencia = ExecuteQueryplanogramacion4.getData(0, "Referencia");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		System.out.print("Referencia" + Referencia);
		boolean igual = true;
		if (Referencia.equals(ref) == true) {
			igual = true;
			testCase.addTextEvidenceCurrentStep("Coinciden en BD y en Portal CNT ");
		} else {
			igual = false;
			testCase.addTextEvidenceCurrentStep("NO Coinciden en BD y en Portal CNT ");
		}
		assertTrue(igual, "No son iguales las referencias en bd y tienda");
		/*String digito = po.ExtraerCodVerificador();
		System.out.println("digito  :" + digito);
		int number = Integer.parseInt(digito);
		int digitoExcel = po.Formula_digito_Verificador(t1, t2, t3, t4, t5);
		boolean si = true;
		if (digitoExcel == number) {
			si = true;
			testCase.addTextEvidenceCurrentStep("Digito verificador segun formula de excel y del portal son iguales");

		} else {
			si = false;
			testCase.addTextEvidenceCurrentStep(
					"Digito verificador segun formula de excel y del portal NO son iguales");
		}
		assertTrue(si, "el digito verificador segun formula excel no es igual a la del portal");*/
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
		// TODO "ATC_FT_045_AtributosPantallaConfiguracionCuentaDolares_test"
		return "ATC_FT_045_AtributosPantallaConfiguracionCuentaDolares_test";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Automation QA";
	}

	@Override
	public String setTestFullName() {
		// TODO "ATC_FT_045_AtributosPantallaConfiguraci�nCuentaD�lares_Test"
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
