package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.Login;
import WEB.simoti_Colombia.om.TransaccionesTDC_SimotiColombia;
import WEB.simoti_Colombia.om.transaccionesElectronicas.transaccionesTDC.Transacciones_TDC;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_025_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Devolucion_Vale_Electronico extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_025_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Devolucion_Vale_Electronico_test(HashMap<String, String> data) throws SQLException {
		
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QAVIEW, GlobalVariables.DB_USER_FCTDCQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTDCQA_QAVIEW);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String tipoTransaccion = data.get("tipoTransaccion");
		String tipoTarjeta = data.get("tipoTarjeta");
		String numeroTarjeta = data.get("numeroTarjeta");
		String numeroAutorizacion = data.get("numeroAutorizacion");
		String f_inicio = data.get("FechaInicial");
		String f_fin = data.get("FechaFinal");
		String tdcQueryPop = "SELECT \r\n" + 
				"       T.FOLIO\r\n" + 
				"       ,TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') --Fecha\r\n" + 
				"       ,T.PLAZA\r\n" + 
				"       ,T.TIENDA\r\n" + 
				"       ,T.CAJA\r\n" + 
				"       ,SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4) --Tarjeta\r\n" + 
				"       ,T.AFFILIATION --Afiliacion\r\n" + 
				"       ,T.ISSUER --Emisor\r\n" + 
				"       ,TO_CHAR(T.AMOUNT/100,'$999,999,999.99') AMOUNT --Monto\r\n" + 
				"       ,T.SW_AUTH --Autorizacion\r\n" + 
				"       ,DECODE(T.REVERSED,1,'Si','No') --Reversado\r\n" + 
				"       ,T.WM_CODE --Codigo de estatus de transaccion\r\n" + 
				"       ,TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) --Fecha de reverso\r\n" + 
				"       ,SW_REVERSE_CODE --Codigo del reverso\r\n" + 
				"       ,SW_DEV_CODE --Codigo de la devolucion\r\n" + 
				"       ,W.DESCRIPTION --Status\r\n" + 
				"        ,DECODE( T.BANK, \r\n" + 
				"                                'OTHER',NULL \r\n" + 
				"                                ,'BBVA',NULL \r\n" + 
				"                                ,T.ISSUER ) --Casa Valera\r\n" + 
				"       ,NULL\r\n" + 
				"FROM TPEUSER.TDC_DEVOLUTION T\r\n" + 
				"INNER JOIN TPEUSER.TDC_WM_CODE W ON W.WM_CODE=T.WM_CODE\r\n" + 
				"WHERE \r\n" + 
				" T.CREATION_DATE BETWEEN TO_DATE('06/09/2016 00:00','DD/MM/YYYY HH24:MI:SS') AND TO_DATE('06/09/2016 23:59', 'DD/MM/YYYY HH24:MI:SS') --Fecha inicio y Fecha Fin\r\n" + 
				"  AND T.PLAZA = NVL( '10MON', T.PLAZA ) --CR_PLAZA\r\n" + 
				" --AND T.TIENDA = NVL( '50EDI', T.TIENDA ) --CR_TIENDA\r\n" + 
				" AND T.TRACK1 LIKE '%5566778899112247%'-- ${FILTER_CARD_NUMBER} -- Si el numero de tarjeta NO es ingresado esta linea se omite, en caso de que se ingrese un numero se sustituye por: AND T.TRACK1 LIKE '%tarjeta%'\r\n" + 
				" AND T.SW_AUTH = NVL( '079476', T.SW_AUTH ) --Numero de Autorizacion\r\n" + 
				"  AND T.CARD_TYPE = NVL( 'c', T.CARD_TYPE ) -- c -->Credito;d --> Debito; v --> Vale Electronico, en este caso el card_type debe cambiarse a un valor de \"c\"; ALL --> Se omite esta linea\r\n" + 
				"  --${BANK} -- Si Tipo de Tarjeta es: d -->  AND BANK IN ( 'BBVA', 'OTHER' ); v -->  AND T.BANK NOT IN ( 'BBVA', 'OTHER' ); ALL o c --> Se omite esta linea\r\n" + 
				"  ---AND BANK IN ( 'BBVA', 'OTHER' ) ---Debito\r\n" + 
				"  AND T.BANK NOT IN ( 'BBVA', 'OTHER' ) ---Vale Electronico \r\n" + 
				"";
		// Paso 1
		addStep("El usuario ingresa a la aplicacion Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(URL);
		Login pa = new Login(u, testCase);		
		Transacciones_TDC TTDC = new Transacciones_TDC(u, testCase);
		TransaccionesTDC_SimotiColombia tdc = new TransaccionesTDC_SimotiColombia(u,testCase);
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el boton Iniciar Sesion.");
		pa.logginPlatform(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opcion de SIMOTI.");
		pa.click_on_simoti();
		
		// Paso 4
		addStep("El usuario despliega el menu: Transacciones Electronicas y selecciona el reporte: Transacciones TDC");
		pa.transaccionesTDC();
		
		//Paso 5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:" + 
				"Opcion: Datos Generales" + 
				"*Plaza: <Seleccione una Mega Plaza>" + 
				"*Distrito: < Seleccionar valor>" + 
				"*Tienda: <Seleccionar Todasr>" + 
				"*Tipo de Transaccion: <D Devolucion>" + 
				"*Tipo de Tarjeta: <V Vale Electronico> " + 
				"*Num. Tarjeta: <capture valor>" + 
				"*Num. Autorizacion: <capture valor>" + 
				"*Fecha Inicio: dd/mm/aaaa hh:mm" + 
				"*Fecha Fin: dd/mm/aaaa hh:mm" + 
				"Dar clic en [Consultar]");
		TTDC.clickRadioDatosGenerales();
		TTDC.selecPlaza(plaza);
		TTDC.selecTienda(tienda);
		TTDC.selecTipoTarjeta(tipoTarjeta);
		TTDC.selecTipoTransaccion(tipoTransaccion);
		TTDC.ingresarNumeroTarjeta(numeroTarjeta);
		TTDC.ingresarNumeroAutorizacion(numeroAutorizacion);
		tdc.seleccionarFechas(f_inicio, f_fin);
		TTDC.clickBTNBuscar();
		
		assertTrue(tdc.validarHeadersTabla(), "Los headers no coinciden");

        if(tdc.validarMensaje("No se ha encontrado informacion con los filtros seleccionados."))

            assertTrue(true,"No existen datos en la consulta.");

        else

        {

            //Paso 6

            addStep("Validar en la tabla TDC_DEVOLUTION que los campos coincidan con los mostrados.");

            System.out.println(GlobalVariables.DB_HOST_FCTDCQA_QAVIEW);
            String queryPop = String.format(tdcQueryPop, f_inicio,f_fin,plaza.substring(0,plaza.indexOf(" ")),tienda.substring(0,tienda.indexOf(" ")));
            System.out.println(queryPop);
            SQLResult queryPopResult = executeQuery(dbFCIAS, queryPop);
            if(!queryPopResult.isEmpty()){
                assertTrue(tdc.validarConsulta(queryPopResult),"Los datos no coinciden con la BD.");
            }

            else

                assertTrue(false,"La consulta con la BD no funciono.");

           

            testCase.addQueryEvidenceCurrentStep(queryPopResult);  

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
		return "ATC_FT_025_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Devolucion_Vale_Electronico";
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