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

public class ATC_FT_013_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Venta_Credito extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Venta_Credito_test(HashMap<String, String> data) throws SQLException {
		
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
		String tdcQueryPop = "SELECT\n"
				+ "T.FOLIO ,TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') ,T.PLAZA ,T.TIENDA ,T.CAJA\n"
				+ ",SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4)\n"
				+ ",T.AFFILIATION ,T.ISSUER ${TRACK1_FORMAT} ,T.SW_AUTH ,DECODE(T.REVERSED,1,'Si','No')\n"
				+ ",T.WM_CODE ,TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) ,T.SW_REVERSE_CODE\n"
				+ ",TO_CHAR( T.AUTH_DT, 'DD-MM-YYYY HH24:MI:SS' ) ,T.SW_AUTH_CODE ,W.DESCRIPTION\n"
				+ ",DECODE( T.BANK, 'OTHER',NULL ,'BBVA',NULL ,T.ISSUER )\n"
				+ ",T.OPERATOR,T.AMOUNT\n"
				+ "FROM TDC_TRANSACTION T\n"
				+ "INNER JOIN TDC_WM_CODE W ON W.WM_CODE = T.WM_CODE\n"
				+ "WHERE\n"
				+ "T.CREATION_DATE BETWEEN TO_DATE( ?, 'DD/MM/YYYY HH24:MI:SS' ) AND TO_DATE( ?, 'DD/MM/YYYY HH24:MI:SS' )\n"
				+ "AND T.PLAZA = NVL( ?, T.PLAZA ) --- CR_PLAZA\n"
				+ "AND T.TIENDA = NVL( ?, T.TIENDA ) -- CR_TIENDA\n"
				+ "AND T.TRACK1 = ? --- NUMERO TARJETA\n"
				+ "AND T.SW_AUTH = NVL( ?, T.SW_AUTH ) --- CODIGO AUTORIZACION \n"
				+ "AND T.CARD_TYPE = NVL( ?, T.CARD_TYPE ) -- C Corresponsal,D Devolucion, V Venta solo el ID y si el usuario selecciona TODOS no se manda nada como condicion en este campo ";
		
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
				+ "*Plaza: <Seleccione una Plaza>\n"
				+ "*Distrito: < Seleccionar valor>\n"
				+ "*Tienda: <Seleccionar Todas>\n"
				+ "*Tipo de Transaccion: <V Venta>\n"
				+ "*Tipo de Tarjeta: <C Credito>\n"
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
		return "ATC_FT_013_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Datos_Generales_Venta_Credito_test";
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