package WorkFlows.SipreBara.ActualizacionBPM;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userCategoria;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;


public class MTC_FT_024_AsignarReglaPrecioSINFILTROS_ActualizacionBPM extends BaseExecution{

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_024_AsignarReglaPrecioSINFILTROS_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");
	
	
	/**
	 * Variables
	 * 
	 */
	String userUltimus = data.get("userUltimus");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	
	/**
	 * 
	 * Utiler�as
	 */
	
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url"));
	pomUltimus pom = new pomUltimus(u);
	pom_metodosSipreBara_userCategoria pomSipreBaraCategoria = new pom_metodosSipreBara_userCategoria(u, testCase);	
	utils.sql.SQLUtil dbSipreBara = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREBARA,
			GlobalVariables.DB_USER_SIPREBARA, GlobalVariables.DB_PASSWORD_SIPREBARA);


	/*-----------------------------------------------------------------------------------------*/
	addStep("Inicia Sesi�n en Ultimus");
		pom.loginUltimus(userUltimus, "");
		testCase.addScreenShotCurrentStep(u, "");
		
	/*-----------------------------------------------------------------------------------------*/
	addStep("Selecciona Iniciar");
	
		pom.seleccionarVista("INICIAR");
		testCase.addScreenShotCurrentStep(u, "");
	
	/*-----------------------------------------------------------------------------------------*/
	addStep("Inicia sesi�n en SIPRE_BARA");
	
		pom.openSipreBara(userSipre, psSipre);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "");
	    u.waitForLoadPage();
    
    /*-----------------------------------------------------------------------------------------*/
	addStep("Click en la opci�n 'Asignaci�n de reglas de precio en el apartado"
			+ "de ''Reglas de precio");
	    u.hardWait(10);
	    String urla=u.getDriver().getCurrentUrl();
	    String[] Fecha = urla.split("sesid=");
	    u.hardWait(2);
	    pomSipreBaraCategoria.asignaReglaPrecio();
	    
	    
    /*-----------------------------------------------------------------------------------------*/
	addStep("Dar click en el bot�n Asignar reglas y bot�n Buscar");
	    pomSipreBaraCategoria.btnAsignaReglaBuscar();;
	    System.out.println("------> "+urla);
	    System.out.println("------> "+Fecha[1]);
	    String articulo = Fecha[1].toString();

    /*-----------------------------------------------------------------------------------------*/
  	addStep("Comparar tabla interfaz con el resultado de la consulta");
  	
	  	testCase.addTextEvidenceCurrentStep("Tabla de la interfaz");
		testCase.addScreenShotCurrentStep(u, "");
	
	    
	    
	    String tablaInterfaz = "SELECT DISTINCT item.item, item.ITEM_DESC DESCRIPCION, item.division_name SUPER_GRUPO, \r\n"
	    		+ "item.group_name CAEGORIA, item.dept_name SUB_CATEGORIA, item.class_name SEGMENTO,\r\n"
	    		+ "item.subclass_name SUBSEGMENTO, (select nvl(sum(no_tiendas),0) from XXWF_SIPRE_ITEM_ZONA_COST_INV \r\n"
	    		+ "where item=item.item) TOTAL_TIENDAS FROM xxwf_sipre_item_estruct_cat_mv item, XXWF_SIPRE_ITEM_LORP_V ilorp,\r\n"
	    		+ "xxwf_sipre_item_zona_cost_inv inv, ( SELECT RMS.DIVISION, RMS.DIVISION_NAME, RMS.GROUP_NO, RMS.GROUP_NAME \r\n"
	    		+ "FROM ( SELECT REGEXP_SUBSTR ( REGEXP_SUBSTR ( (SELECT CATEGORIA_DESC FROM XXWF_SIPRE_SESSION_CONTROL\r\n"
	    		+ "WHERE SESSIONKEY = '"+articulo+"'), '[^,]+', 1, LEVEL), '[^-]+', 1, 1) AS \"DIVISION\", \r\n"
	    		+ "REGEXP_SUBSTR ( REGEXP_SUBSTR ( (SELECT CATEGORIA_DESC FROM XXWF_SIPRE_SESSION_CONTROL \r\n"
	    		+ "WHERE SESSIONKEY = '"+articulo+"'), '[^,]+', 1, LEVEL), '[^-]+', 1, 2) AS \"GRUPO\" \r\n"
	    		+ "FROM DUAL CONNECT BY LEVEL <= LENGTH ( REGEXP_REPLACE ( (SELECT CATEGORIA_DESC FROM XXWF_SIPRE_SESSION_CONTROL\r\n"
	    		+ "WHERE SESSIONKEY = 'F5F10CF27BB44215B20A2F65DCF7AB1DEFE24425992085D30EA539E89E4D4A30'), '[^,]+')) + 1) DIV_GPO LEFT JOIN XXVA_RMS_ESTRUCTURA_CAT_V RMS\r\n"
	    		+ "ON ( DIV_GPO.DIVISION = RMS.DIVISION AND DIV_GPO.GRUPO = RMS.GROUP_NO) \r\n"
	    		+ "GROUP BY RMS.DIVISION, RMS.DIVISION_NAME, RMS.GROUP_NO, RMS.GROUP_NAME HAVING RMS.GROUP_NO > 0\r\n"
	    		+ "ORDER BY 2 ASC) SESSION_ID WHERE item.item = ilorp.item AND inv.item = ilorp.item \r\n"
	    		+ "AND ilorp.clave_preciogrupo IS NULL AND ilorp.division_rtk IS NOT NULL AND ilorp.group_rtk \r\n"
	    		+ "IS NOT NULL AND ilorp.paqdereglas = 'BARA'";
	    
	    
		SQLResult queryResultTabla = executeQuery(dbSipreBara, tablaInterfaz);
	  	
		testCase.addTextEvidenceCurrentStep("Tabla de la consulta");
		testCase.addQueryEvidenceCurrentStep(queryResultTabla);
		
		int rowsTablaInterfaz = pomSipreBaraCategoria.numeroElementosTabla();
		int rowsConsulta = queryResultTabla.getRowCount();
		assertEquals(rowsConsulta, rowsTablaInterfaz, "La tabla de  interfaz y la tabla de la db No tiene los mismos elementos");

	    /*-----------------------------------------------------------------------------------------*/
	  	addStep("Seleccionar un elemento de la tabla y dar click en Agregar Regla ");
	  	pomSipreBaraCategoria.seleccionaArticulo();
	  	
	  	u.getDriver().quit();
    
    





	}
	
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "La carpeta de descargas debe ser por default";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Asignar regla de precio a varios art�culos SIN FILTROS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_024_AsignarReglaPrecioSINFILTROS_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
