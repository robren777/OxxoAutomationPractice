package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_334_Validar_Reportes_CNT_campos_Segmento_Concepto_consulta_Base_de_Datos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_334_Validar_Reportes_CNT_campos_Segmento_Concepto_consulta_Base_de_Datos_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		/*
		 * Variables
		 ******************************************************************************************/
	    String cantFilas = data.get("CantidadFilas");
		
		String query = "SELECT * FROM (SELECT  tt.cr_superior||tt.cr_tienda ID_TIENDA" + 
				" ,TC.PLAZO_CONTRATO PLAZO_CONTRATO" + 
				" ,TO_CHAR(TC.FECHA_INICIO_CONTRATO,'DD-MM-YYYY HH:MI:SS') FECHA_INICIO_CONTRATO "
				+ ",TO_CHAR(TC.FECHA_FIN_CONTRATO,'DD-MM-YYYY HH:MI:SS')  FECHA_FIN_CONTRATO" + 
				" ,TL.NUM_CUENTA_PREDIAL NUM_CUENTA_PREDIAL"
				+ ",TL.PEM_VTA_VINOS PEM_VTA_VINOS" + 
				" ,TF.ES_FRANJA_FRONTERIZA  ES_FRANJA_FRONT"
				+ ",TF.LID_SEGMENTO" + 
				" ,(select tt.cr_tienda from tiendas.t_tiendas tt where tt.id_tienda  = TF.retek_sister_store and rownum = 1) RETEK_SISTER_STORE" + 
				" ,(select tt1.cr_tienda from  tiendas.t_tiendas tt1 where tt1.retek_loc_id = tf.retek_like_store and rownum = 1) RETEK_LIKE_STORE" + 
				" ,tc.AREA_DEL_TERRENO AREA_DEL_TERRENO"
				+ ",tt.LAST_UPDATE_BY ACTUALIZADO_POR"
				+ ", TO_CHAR(tt.LAST_UPDATE_DATE,'DD-MM-YYYY HH:MI:SS') FECHA_ACTUAL" + 
				" ,FS.MAESTRO_SEGMENTO_DESC SEGMENTO_MAESTRO"
				+ ",FS.CONCEPTO_DIFERENCIADOR " + 
				" FROM tiendas.t_tiendas tt,  tiendas.t_formato_entorno tf, tiendas.t_operacion op, tiendas.T_CONDICIONES_PROPIEDAD TC" + 
				" ,(SELECT id_tienda,rtrim(xmlagg (xmlelement (e,'Num Cuenta Predial'||'='||nvl(convert(TL1.NUM_CUENTA_PREDIAL,'UTF8','US7ASCII') ,'0') ||',')).extract ('//text()'), ',')  NUM_CUENTA_PREDIAL" + 
				" ,rtrim (xmlagg (xmlelement (e,'Permiso Venta Vinos'||'='||nvl(TL1.PEM_VTA_VINOS,'N') ||',')).extract ('//text()'), ',')  PEM_VTA_VINOS " + 
				" from  tiendas.T_LEGALES TL1 group by id_tienda) TL," + 
				" tiendas.t_formato_segmento FS,  tiendas.T_SEGMENTO_TIENDA_V STV" + 
				" WHERE  tt.id_tienda = tf.id_tienda" + 
				" and tt.id_tienda = op.id_tienda" + 
				" and tt.id_tienda = tc.id_tienda(+)" + 
				" and tt.id_tienda = tl.id_tienda(+)" + 
				" and tt.id_tienda = FS.id_tienda(+)" + 
				" AND TT.ID_TIENDA = STV.ID_TIENDA(+)" + 
				" AND FS.ID_TIENDA IS NOT NULL)"+
				" WHERE ROWNUM < "+cantFilas;

		
		String query1 = "SELECT * FROM (SELECT  tt.cr_superior||tt.cr_tienda ID_TIENDA" + 
				" ,TC.PLAZO_CONTRATO PLAZO_CONTRATO" + 
				" ,TO_CHAR(TC.FECHA_INICIO_CONTRATO,'DD-MM-YYYY HH:MI:SS') FECHA_INICIO_CONTRATO" +
				" ,TO_CHAR(TC.FECHA_FIN_CONTRATO,'DD-MM-YYYY HH:MI:SS')  FECHA_FIN_CONTRATO" + 
				" FROM tiendas.t_tiendas tt,  tiendas.t_formato_entorno tf, tiendas.t_operacion op, tiendas.T_CONDICIONES_PROPIEDAD TC" + 
				" ,(SELECT id_tienda,rtrim(xmlagg (xmlelement (e,'Num Cuenta Predial'||'='||nvl(convert(TL1.NUM_CUENTA_PREDIAL,'UTF8','US7ASCII') ,'0') ||',')).extract ('//text()'), ',')  NUM_CUENTA_PREDIAL" + 
				" ,rtrim (xmlagg (xmlelement (e,'Permiso Venta Vinos'||'='||nvl(TL1.PEM_VTA_VINOS,'N') ||',')).extract ('//text()'), ',')  PEM_VTA_VINOS " + 
				" from  tiendas.T_LEGALES TL1 group by id_tienda) TL," + 
				" tiendas.t_formato_segmento FS,  tiendas.T_SEGMENTO_TIENDA_V STV" + 
				" WHERE  tt.id_tienda = tf.id_tienda" + 
				" and tt.id_tienda = op.id_tienda" + 
				" and tt.id_tienda = tc.id_tienda(+)" + 
				" and tt.id_tienda = tl.id_tienda(+)" + 
				" and tt.id_tienda = FS.id_tienda(+)" + 
				" AND TT.ID_TIENDA = STV.ID_TIENDA(+)" + 
				" AND FS.ID_TIENDA IS NOT NULL)" + 
				" WHERE ROWNUM < "+cantFilas;

		
		
		String query2 = "SELECT * FROM (SELECT TL.NUM_CUENTA_PREDIAL NUM_CUENTA_PREDIAL" +
				" ,TL.PEM_VTA_VINOS PEM_VTA_VINOS" + 
				" ,TF.ES_FRANJA_FRONTERIZA  ES_FRANJA_FRONT" +
				" ,TF.LID_SEGMENTO" + 
				" FROM tiendas.t_tiendas tt,  tiendas.t_formato_entorno tf, tiendas.t_operacion op, tiendas.T_CONDICIONES_PROPIEDAD TC" + 
				" ,(SELECT id_tienda,rtrim(xmlagg (xmlelement (e,'Num Cuenta Predial'||'='||nvl(convert(TL1.NUM_CUENTA_PREDIAL,'UTF8','US7ASCII') ,'0') ||',')).extract ('//text()'), ',')  NUM_CUENTA_PREDIAL" + 
				" ,rtrim (xmlagg (xmlelement (e,'Permiso Venta Vinos'||'='||nvl(TL1.PEM_VTA_VINOS,'N') ||',')).extract ('//text()'), ',')  PEM_VTA_VINOS " + 
				" from  tiendas.T_LEGALES TL1 group by id_tienda) TL," + 
				" tiendas.t_formato_segmento FS,  tiendas.T_SEGMENTO_TIENDA_V STV" + 
				" WHERE  tt.id_tienda = tf.id_tienda" + 
				" and tt.id_tienda = op.id_tienda" + 
				" and tt.id_tienda = tc.id_tienda(+)" + 
				" and tt.id_tienda = tl.id_tienda(+)" + 
				" and tt.id_tienda = FS.id_tienda(+)" + 
				" AND TT.ID_TIENDA = STV.ID_TIENDA(+)" + 
				" AND FS.ID_TIENDA IS NOT NULL)" + 
				" WHERE ROWNUM < "+cantFilas;

		
		String query3 = "SELECT * FROM (SELECT (select tt.cr_tienda from tiendas.t_tiendas tt where tt.id_tienda  = TF.retek_sister_store and rownum = 1) RETEK_SISTER_STORE" + 
				" ,(select tt1.cr_tienda from  tiendas.t_tiendas tt1 where tt1.retek_loc_id = tf.retek_like_store and rownum = 1) RETEK_LIKE_STORE" + 
				" ,tc.AREA_DEL_TERRENO AREA_DEL_TERRENO" +
				" ,tt.LAST_UPDATE_BY ACTUALIZADO_POR" + 
				" FROM tiendas.t_tiendas tt,  tiendas.t_formato_entorno tf, tiendas.t_operacion op, tiendas.T_CONDICIONES_PROPIEDAD TC" + 
				" ,(SELECT id_tienda,rtrim(xmlagg (xmlelement (e,'Num Cuenta Predial'||'='||nvl(convert(TL1.NUM_CUENTA_PREDIAL,'UTF8','US7ASCII') ,'0') ||',')).extract ('//text()'), ',')  NUM_CUENTA_PREDIAL" + 
				" ,rtrim (xmlagg (xmlelement (e,'Permiso Venta Vinos'||'='||nvl(TL1.PEM_VTA_VINOS,'N') ||',')).extract ('//text()'), ',')  PEM_VTA_VINOS " + 
				" from  tiendas.T_LEGALES TL1 group by id_tienda) TL," + 
				" tiendas.t_formato_segmento FS,  tiendas.T_SEGMENTO_TIENDA_V STV" + 
				" WHERE  tt.id_tienda = tf.id_tienda" + 
				" and tt.id_tienda = op.id_tienda" + 
				" and tt.id_tienda = tc.id_tienda(+)" + 
				" and tt.id_tienda = tl.id_tienda(+)" + 
				" and tt.id_tienda = FS.id_tienda(+)" + 
				" AND TT.ID_TIENDA = STV.ID_TIENDA(+)" + 
				" AND FS.ID_TIENDA IS NOT NULL)" + 
				" WHERE ROWNUM < "+cantFilas;
		
		
		String query4 = "SELECT * FROM (SELECT TO_CHAR(tt.LAST_UPDATE_DATE,'DD-MM-YYYY HH:MI:SS') FECHA_ACTUAL" + 
				" ,FS.MAESTRO_SEGMENTO_DESC SEGMENTO_MAESTRO" +
				" ,FS.CONCEPTO_DIFERENCIADOR" + 
				" FROM tiendas.t_tiendas tt,  tiendas.t_formato_entorno tf, tiendas.t_operacion op, tiendas.T_CONDICIONES_PROPIEDAD TC" + 
				" ,(SELECT id_tienda,rtrim(xmlagg (xmlelement (e,'Num Cuenta Predial'||'='||nvl(convert(TL1.NUM_CUENTA_PREDIAL,'UTF8','US7ASCII') ,'0') ||',')).extract ('//text()'), ',')  NUM_CUENTA_PREDIAL" + 
				" ,rtrim (xmlagg (xmlelement (e,'Permiso Venta Vinos'||'='||nvl(TL1.PEM_VTA_VINOS,'N') ||',')).extract ('//text()'), ',')  PEM_VTA_VINOS " + 
				" from  tiendas.T_LEGALES TL1 group by id_tienda) TL," + 
				" tiendas.t_formato_segmento FS,  tiendas.T_SEGMENTO_TIENDA_V STV" + 
				" WHERE  tt.id_tienda = tf.id_tienda" + 
				" and tt.id_tienda = op.id_tienda" + 
				" and tt.id_tienda = tc.id_tienda(+)" + 
				" and tt.id_tienda = tl.id_tienda(+)" + 
				" and tt.id_tienda = FS.id_tienda(+)" + 
				" AND TT.ID_TIENDA = STV.ID_TIENDA(+)" + 
				" AND FS.ID_TIENDA IS NOT NULL)" + 
				" WHERE ROWNUM < "+cantFilas;


		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Usuario estable la conexi�n a la BD de FICASQA."); 
			
		testCase.addBoldTextEvidenceCurrentStep("Se establece la conexi�n de manera exitosa con la BD");
		testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_FCDW2QA_PORTAL);
		testCase.addTextEvidenceCurrentStep("User: " + GlobalVariables.DB_USER_FCDW2QA_PORTAL);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + db.getConn()); 
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Usuario realiza la consulta con los campos de Segmento de Tienda y Concepto Diferenciador para que se muestren en el reporte, corriendo el siguiente querys:\r\n" + 
				"	SELECT  tt.cr_superior||tt.cr_tienda ID_TIENDA -->Identificador Unico.\r\n" + 
				",TC.PLAZO_CONTRATO PLAZO_CONTRATO\r\n" + 
				",TO_CHAR(TC.FECHA_INICIO_CONTRATO,'DD-MM-YYYY HH:MI:SS') FECHA_INICIO_CONTRATO ,TO_CHAR(TC.FECHA_FIN_CONTRATO,'DD-MM-YYYY HH:MI:SS')  FECHA_FIN_CONTRATO\r\n" + 
				",TL.NUM_CUENTA_PREDIAL NUM_CUENTA_PREDIAL,TL.PEM_VTA_VINOS PEM_VTA_VINOS\r\n" + 
				",TF.ES_FRANJA_FRONTERIZA  ES_FRANJA_FRONT, TF.LID_SEGMENTO\r\n" + 
				",(select tt.cr_tienda from tiendas.t_tiendas tt where tt.id_tienda  = TF.retek_sister_store and rownum = 1) RETEK_SISTER_STORE\r\n" + 
				",(select tt1.cr_tienda from  tiendas.t_tiendas tt1 where tt1.retek_loc_id = tf.retek_like_store and rownum = 1) RETEK_LIKE_STORE\r\n" + 
				",tc.AREA_DEL_TERRENO AREA_DEL_TERRENO,tt.LAST_UPDATE_BY ACTUALIZADO_POR, TO_CHAR(tt.LAST_UPDATE_DATE,'DD-MM-YYYY HH:MI:SS') FECHA_ACTUAL\r\n" + 
				",FS.MAESTRO_SEGMENTO_DESC SEGMENTO_MAESTRO,FS.CONCEPTO_DIFERENCIADOR\r\n" + 
				"FROM tiendas.t_tiendas tt,  tiendas.t_formato_entorno tf, tiendas.t_operacion op, tiendas.T_CONDICIONES_PROPIEDAD TC,\r\n" + 
				"(SELECT id_tienda,rtrim(xmlagg (xmlelement (e,'Num Cuenta Predial'||'='||nvl(convert(TL1.NUM_CUENTA_PREDIAL,'UTF8','US7ASCII') ,'0') ||',')).extract ('//text()'), ',')  NUM_CUENTA_PREDIAL\r\n" + 
				",rtrim (xmlagg (xmlelement (e,'Permiso Venta Vinos'||'='||nvl(TL1.PEM_VTA_VINOS,'N') ||',')).extract ('//text()'), ',')  PEM_VTA_VINOS\r\n" + 
				"from  tiendas.T_LEGALES TL1 group by id_tienda) TL,\r\n" + 
				"tiendas.t_formato_segmento FS,  tiendas.T_SEGMENTO_TIENDA_V STV\r\n" + 
				"WHERE  tt.id_tienda = tf.id_tienda\r\n" + 
				"and tt.id_tienda = op.id_tienda\r\n" + 
				"and tt.id_tienda = tc.id_tienda(+)\r\n" + 
				"and tt.id_tienda = tl.id_tienda(+)\r\n" + 
				"and tt.id_tienda = FS.id_tienda(+)\r\n" + 
				"AND TT.ID_TIENDA = STV.ID_TIENDA(+)\r\n" + 
				"AND FS.ID_TIENDA IS NOT NULL;");
		
		System.out.print(query1+"\n");
		
		SQLResult queryResult1 = executeQuery(db, query1);	
		SQLResult queryResult2 = executeQuery(db, query2);	
		SQLResult queryResult3 = executeQuery(db, query3);	
		SQLResult queryResult4 = executeQuery(db, query4);	
			
		boolean validacionBD = queryResult1.isEmpty();
		
		if(!validacionBD) {
			testCase.addBoldTextEvidenceCurrentStep(" Sistema regresa la consulta con la informaci�n correspondiente a la tienda consultado.");
		}
		
		testCase.addTextEvidenceCurrentStep(query);
		testCase.addQueryEvidenceCurrentStep(queryResult1, false);
		testCase.addQueryEvidenceCurrentStep(queryResult2, false);
		testCase.addQueryEvidenceCurrentStep(queryResult3, false);
		testCase.addQueryEvidenceCurrentStep(queryResult4, false);
					
		assertFalse(validacionBD, "No se mostraron resultados en la consulta");
			

		
		
		
    }

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "MTC_FT_333 Validar cambios en Reportes de CNT test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}
	
}