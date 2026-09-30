package WEB.simoti;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti.om.Simoti;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_Plaza_Envios_de_Dinero_Transacciones_Envio_Beneficiario extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_Plaza_Envios_de_Dinero_Transacciones_Envio_Beneficiario_Test(HashMap<String, String> data)
			throws Exception {
		String cp = data.get("CP");
		testCase.setTest_Description(cp);
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String tipo = data.get("Tipo");
		String ife = data.get("IFE");
		String nTransf1 = data.get("NTransferencia");
		String nTransf2 = data.get("NTransferencia2");
		String tel = data.get("Telefono");
		String mes1 = data.get("MES1");
		String ano1 = data.get("A�O1");
		String dia1 = data.get("DIA1");
		String mes2 = data.get("MES2");
		String ano2 = data.get("A�O2");
		String dia2 = data.get("DIA2");
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
//		String negocio = data.get("Negocio");
//		String perfil = data.get("Perfil");
//		String plaza = data.get("Plaza");
//		String direcc = data.get("Nombre_Dir");
//		String cp = data.get("C�digo_postal");
//		String calle = data.get("Calle");
//		String numero = data.get("N�mero");
//		String entreCalle = data.get("Entre_Calle");
//		String yEntreCalle = data.get("y_entre_Calle");
//		String nomContacto = data.get("Nombre_Contacto");
//		String telContacto = data.get("Tel�fono_Contacto");
//		String comentario = data.get("Comentario");

		String query ="";

		// ------------------------------------PASO 1
		addStep("Ingresar al portal de aplicaciones e Ingresar el usuario, password y dar clic en entrar"); // viene //
																											// excel
		u = new SeleniumUtil(new MEdgeTest(), true); // IETest para usar internet explorer

		PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		pa.proceedToPortal(); // es para lo de la pagina no segura
		pa.cicloLogin(user, password);

		addStep("Seleccionar el menu Portal SIMOTI.");
		pa.clickPortalSIMOTI();
		Simoti po = new Simoti(u, testCase);
		po.ValidaR_TD_ID_OC_FP_TE();

		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Env�os de Dinero - Transacciones");
		po.Clic_trans_electronicas_EnvDinTrans();

		addStep("El usuario selecciona o captura valor en el filtro que se indique:\r\n" + "-Tipo: <ENV Env�o>\r\n"
				+ "-Cliente: \r\n" + "-IFE: \r\n" + "-N�mero de transferencia: \r\n" + "-Tel�fono o Celular:\r\n"
				+ "Seleccionar un una fecha menor a 3 meses atr�s\r\n" + "-Fecha Inicio:fecha inicio.\r\n"
				+ "-Fecha Fin: fecha fin\r\n" + "Dar clic en [Consultar]\r\n" + "");
		po.TablaEDBeneficiario(tipo, ife, nTransf1, tel, mes1, ano1, dia1, mes2, ano2, dia2);

		if (nTransf1.isEmpty() == true) {
			addStep("Informar el n�mero de transferencia. Pulsar Consultar");
			po.TablaTransf(nTransf2);
			boolean existe = po.DatosTabla();
			assertFalse(existe, "No hay datos en la tabla");
		}

		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\r\n"
				+ "1.-No. (Tipo de dato: Number)\r\n" + "2.-Folio (Tipo de dato: Number)\r\n"
				+ "3.-Fecha (Tipo de dato: Date dd/mm/aaaa hh:mm:ss a 24 hrs)\r\n"
				+ "4.-Plaza (Tipo de dato: String)\r\n" + "5.-Tienda (Tipo de dato: String)\r\n"
				+ "6.-Caja (Tipo de dato: Number)\r\n" + "7.-No. Trasferencia (Tipo de dato: Number)\r\n"
				+ "8.-No. IFE (Tipo de dato: Number)\r\n" + "9.-Tel�fono (Tipo de dato: String)\r\n"
				+ "10.-Beneficiario (Tipo de dato: String)\r\n" + "11.-Remitente (Tipo de dato: String)\r\n"
				+ "12.-Monto (Tipo de dato: Number)\r\n" + "13.-Comisi�n (Tipo de dato: Number)\r\n"
				+ "14.-IVA (Tipo de dato: Number)\r\n" + "15.-Total (Tipo de dato: Number)\r\n"
				+ "16.-Fecha de Cobro Date (Tipo de dato: dd/mm/aaaa hh:mm:ss a 24 hrs)\r\n"
				+ "17.-Plaza de Cobro (Tipo de dato: String)\r\n" + "18.-Tienda de Cobro (Tipo de dato: String)\r\n"
				+ "19.- No. Identificaci�n Beneficiario (Tipo de dato: Number)\r\n"
				+ "**El n�mero telef�nico deber� visualizarse completo  \r\n"
				+ "Ejemplo: Si el n�mero es 010448111707361,\r\n" + "");

		if (tipo.equals("B Beneficiario")) {

			addStep("Consultar la bd con los filtros seleccionados\r\n" + "Tabla historica\r\n" + " \r\n"
					+ "SELECT TR.FOLIO, TO_CHAR(TR.CREATION_DATE,'YYYY/MM/DD HH24:MI:SS') AS FECHA, TR.PLAZA, TR.TIENDA, TR.CAJA,RET_REF_NO AS NO_TRASFERENCIA, TR.PAN AS NO_IFE, TR.ADITIONAL_DATA AS TELEFONO, UPPER(TR.CA_NAME_LOC||' '||TR.CA_TERM_ID||' '||TR.CA_ACEP_ID) AS BENEFICIARIO, \r\n"
					+ "           UPPER(C.NOMBRE||' '||C.APATERNO||' '|| C.AMATERNO) AS REMITENTE, TO_CHAR(TR.AMOUNT/100,'$9999999999.99') AS MONTO, TO_CHAR(TR.ATT3/100 ,'$9999999999.99') AS COMISION, TO_CHAR((TR.ATT2/100) ,'$9999999999.99') AS IVA, TO_CHAR(TR.ATT1/100 ,'$9999999999.99') AS TOTAL\r\n"
					+ "    FROM TPEREP.TPE_FR_TRANSACTION TR\r\n"
					+ "    INNER JOIN TPEREP.TPE_TSF_CLIENTES C ON (TR.PAN = C.NOIDENTIFICACION_R)\r\n"
					+ "    WHERE C.NOIDENTIFICACION_R = TR.PAN \r\n" + "    AND \r\n" + "    TR.APPLICATION = 'TSF'\r\n"
					+ "    AND TR.ENTITY = 'OXXO'\r\n" + "    and TR.MTI ='0200'\r\n"
					+ "    AND TR.OPERATION ='TRN03'\r\n" + "    AND TR.PROC_CODE = NVL('ENV',TR.PROC_CODE) --ENV\r\n"
					+ "    --AND TR.PAN = NVL('1173129223068',TR.PAN) -- IFE\r\n"
					+ "    AND TR.CREATION_DATE >= TO_DATE('15/02/2015','DD/MM/YYYY') \r\n"
					+ "   AND TR.CREATION_DATE <= TO_DATE('22/02/2015','DD/MM/YYYY') \r\n"
					+ "   --AND TR.RET_REF_NO = NVL('OXXO5339338',TR.RET_REF_NO) --NUMERO DE TRANSFERENCIA\r\n"
					+ "   aND TR.ADITIONAL_DATA = NVL('4611184929',TR.ADITIONAL_DATA) -- TELEFONO\r\n"
					+ "    AND TR.WM_CODE ='101'\r\n" + "    ORDER BY TR.CREATION_DATE DESC\r\n" + "");
		
			SQLResult ExecuteQueryplanogramacion3= executeQuery(dbFCIAS, query);
			System.out.print("consulta"+ query);
			boolean StatusQueryplanogramacion3 = ExecuteQueryplanogramacion3.isEmpty();

			if (!StatusQueryplanogramacion3) {
				testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
				testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion3);
//				CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
			} else {
				testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
			}
		
		
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return null;
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Plaza_Envios_de_Dinero_Transacciones_Envio_Beneficiario_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}
