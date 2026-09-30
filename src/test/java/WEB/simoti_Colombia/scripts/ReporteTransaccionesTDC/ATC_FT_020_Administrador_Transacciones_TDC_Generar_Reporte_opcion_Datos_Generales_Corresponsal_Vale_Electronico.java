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

public class ATC_FT_020_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Corresponsal_Vale_Electronico extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Corresponsal_Vale_Electronico_test(HashMap<String, String> data) throws SQLException {
		
//		addStep("test");
//		assertTrue(false);
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
		String tdcQueryPop = "SELECT \n"
				+ "                T.FOLIO\n"
				+ "                ,TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS')\n"
				+ "                ,T.PLAZA\n"
				+ "                ,T.TIENDA\n"
				+ "                ,T.CAJA\n"
				+ "                ,SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4) --Tarjeta\n"
				+ "                ,T.AFFILIATION \n"
				+ "                ,T.ISSUER --EMISOR\n"
				+ "                ,TO_CHAR(T.AMOUNT/100,'$999,999,999.99') AMOUNT --MONTO\n"
				+ "    ,T.SW_AUTH --Autorizacion\n"
				+ "                ,DECODE(T.REVERSED,1,'Si','No')  --Reversado\n"
				+ "                ,T.WM_CODE --Codigo estatus transaccion\n"
				+ "                ,TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) --FEcha de reverso\n"
				+ "                ,T.SW_REVERSE_CODE --Codigo de reverso\n"
				+ "                ,TO_CHAR( T.AUTH_DT, 'DD-MM-YYYY HH24:MI:SS' ) -- Fecha de autorizacion\n"
				+ "                ,T.SW_AUTH_CODE --Codigo de autorizacion\n"
				+ "                ,W.DESCRIPTION --ERROR\n"
				+ "                 ,DECODE( T.BANK, \n"
				+ "                                                'OTHER',NULL \n"
				+ "                                                ,'BBVA',NULL\n"
				+ "                                                ,T.ISSUER )--Casa Valera\n"
				+ "                ,T.OPERATOR\n"
				+ "FROM TPEUSER.TDC_TRANSACTION T\n"
				+ "INNER JOIN TPEUSER.TDC_WM_CODE W ON W.WM_CODE = T.WM_CODE \n"
				+ "WHERE \n"
				+ "                T.CREATION_DATE BETWEEN TO_DATE( '04/04/2016 12:00', 'DD/MM/YYYY HH24:MI:SS' ) AND TO_DATE( '04/04/2016 13:00', 'DD/MM/YYYY HH24:MI:SS' )\n"
				+ "                AND T.PLAZA = NVL( '10RYN', T.PLAZA ) --CR PLAZA\n"
				+ "                AND T.TIENDA = NVL( '50RLL', T.TIENDA ) --CR TIENDA\n"
				+ "                AND T.SW_AUTH = NVL( '0', T.SW_AUTH) --Numero de Autorizacion\n"
				+ "                AND T.CARD_TYPE = NVL( 'c' , T.CARD_TYPE )-- c -->Credito;d --> Debito; v --> Vale Electronico, en este caso el card_type debe cambiarse a un valor de \"c\"; ALL --> Se omite esta linea\n"
				+ "                AND T.PROM_TYPE = 'PAY' ----${PROM_TYPE} --Si tipo de transaccion es: Corresponsal--> AND T.PROM_TYPE = 'PAY'; Venta--> AND T.PROM_TYPE <> 'PAY'; Devolucion --> Omitir linea; \n"
				+ "                AND T.BANK NOT IN ( 'BBVA', 'OTHER' )----${BANK}-- Si Tipo de Tarjeta es: d -->  AND BANK IN ( 'BBVA', 'OTHER' ); v -->  AND T.BANK NOT IN ( 'BBVA', 'OTHER' ); ALL o c --> Se omite esta linea\n"
				+ "                --AND T.TRACK1 LIKE '%3799110000000001%'  ----${FILTER_CARD_NUMBER}-- Si el numero de tarjeta NO es ingresado esta linea se omite, en caso de que se ingrese un numero se sustituye por: AND T.TRACK1 LIKE '%tarjeta%'\n"
				+ "";
		
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
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\n"
				+ "Opcion: Datos Generales\n"
				+ "*Plaza: <Seleccione una Mega Plaza>\n"
				+ "*Distrito: < Seleccionar valor>\n"
				+ "*Tienda: <Seleccionar Todas>\n"
				+ "*Tipo de Transaccion: <C Corresponsal>\n"
				+ "*Tipo de Tarjeta: <C Credito> \n"
				+ "*Num. Tarjeta: <capture valor>\n"
				+ "*Num. Autorizacion: <capture valor>\n"
				+ "*Fecha Inicio: dd/mm/aaaa hh:mm\n"
				+ "*Fecha Fin: dd/mm/aaaa hh:mm\n"
				+ "Dar clic en [Consultar]");
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
		return "ATC_FT_020_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Corresponsal_Vale_Electronico";
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