package WorkFlows.SipreBara.ActualizacionBPM;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pom_Login_PEGA;
import WorkFlows.util.SipreBara.pom_metodosSipreBara_userAdmin;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class MTC_FT_007_ApruebaReglasDePrecio_ActualizaBPM extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_007_ApruebaReglasDePreci_ActualizaBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");
	
	
	/**
	 * Variables
	 * 
	 */
	String userPega = data.get("userPega");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	
	/**
	 * 
	 * Utiler�as
	 */
	
	
	u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url"));
	pomUltimus pom = new pomUltimus(u);
	pom_metodosSipreBara_userAdmin pomSipre = new pom_metodosSipreBara_userAdmin(u, testCase);
	pom_Login_PEGA pomPega = new pom_Login_PEGA(u,testCase, userPega );

	utils.sql.SQLUtil dbSipreBara = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREBARA,
			GlobalVariables.DB_USER_SIPREBARA, GlobalVariables.DB_PASSWORD_SIPREBARA);

	/**
	 * QUERYS
	 */
	String tabla = "SELECT  DISTINCT PR.item ARTICULO, ITEM_DESC DESCRIPCION,\r\n"
			+ "paqregla AS paqdereglas, preciogrupo, division_name SUPER_GRUPO,\r\n"
			+ "group_name CATEGORIA, dept_name SUBCATEGORIA, class_name SEGMENTO, \r\n"
			+ "subclass_name SUB_SEGMENTO,(select nvl(sum(A.no_tiendas),0) from XXWF_SIPRE_ITEM_ZONA_COST_INV A \r\n"
			+ "where A.item=PR.item) TOTAL_TIENDA FROM XXWF_SIPRE_LOG_ARTS_SIN_RP PR \r\n"
			+ "WHERE estatus = 'NUEVO' AND regla = 'COMPETENCIA' ";
	
	String elementosTabla ="SELECT count (DISTINCT PR.item) as ARTICULO"
			+"FROM XXWF_SIPRE_LOG_ARTS_SIN_RP PR \r\n"
			+ "WHERE estatus = 'NUEVO' AND regla = 'COMPETENCIA'";
	
	/*-----------------------------------------------------------------------------------------*/
	addStep("Inicia Sesi�n en PEGA");
	pomPega.iniciaSesion();
	
	pomPega.cambiarVentana(1);
    
	testCase.addScreenShotCurrentStep("");

	
		/*-----------------------------------------------------------------------------------------*/
	addStep("Seleccionar 'Validar reglas de precio' en el apartado de Configuraci�n y dar click en buscar");
	u.hardWait(3);
	pomSipre.selectReglasPrecio();
	

	/*-----------------------------------------------------------------------------------------*/
	addStep("Validar los registros mostrados en la tabla 'Validaci�n de Reglas de Precio 'Competencia' ");
	u.hardWait(5);
	u.waitForLoadPage();
	SQLResult queryResultTabla = executeQuery(dbSipreBara, tabla);
	System.out.println("Query Tabla ---->"+tabla);


	int elementosQueryResult = queryResultTabla.getRowCount();
	int elementosInterfaz =pomSipre.numeroElementosTabla();
	System.out.println("Query Elementos ---->"+elementosQueryResult);
	System.out.println("Interfaz Elementos ---->"+elementosInterfaz);

	
	testCase.addQueryEvidenceCurrentStep(queryResultTabla);
	testCase.addBoldTextEvidenceCurrentStep("Cantidad de elementos en la db: "+elementosQueryResult+"\n Cantidad de elementos en interfaz: "+elementosInterfaz);
	testCase.addScreenShotCurrentStep(u, "");

	assertEquals(elementosQueryResult, elementosInterfaz, "La tabla de  interfaz y la tabla de la db No tiene los mismos elementos");

	/*-----------------------------------------------------------------------------------------*/
	addStep("Validar que los botones aprobar y descartar realicen su funci�n solo cuando se tenga un "
			+ "art�culo seleccionado");
	pomSipre.botonesDescartarAprobar();
	int elementosInterfazBtnProbarGuardar = pomSipre.numeroElementosTabla();
	
	testCase.addBoldTextEvidenceCurrentStep("Cantidad de elementos interfaz antes de dar click en botones"
			+ ": "+elementosInterfaz);
	testCase.addBoldTextEvidenceCurrentStep("Cantidad de elementos despu�s: "+elementosInterfazBtnProbarGuardar);
	//Si los botones funcionaron sin seleccionar un art�culo la cantidad de elementos en la tabla cambiar�
	assertEquals(elementosInterfaz,elementosInterfazBtnProbarGuardar, "Los botones funcionaron y afectaron a los elementos de la tabla");

	
	/*-----------------------------------------------------------------------------------------*/
	addStep("Seleccionar elemento de la tabla y descartarlo");
	
	String articulo = pomSipre.selectArticulo();
	
	String consultaArticulo ="SELECT  DISTINCT PR.item ARTICULO, ITEM_DESC DESCRIPCION,\r\n"
			+ "paqregla AS paqdereglas, preciogrupo, division_name SUPER_GRUPO,\r\n"
			+ "group_name CATEGORIA, dept_name SUBCATEGORIA, class_name SEGMENTO, \r\n"
			+ "subclass_name SUB_SEGMENTO,(select nvl(sum(A.no_tiendas),0) from XXWF_SIPRE_ITEM_ZONA_COST_INV A \r\n"
			+ "where A.item=PR.item) TOTAL_TIENDA FROM XXWF_SIPRE_LOG_ARTS_SIN_RP PR \r\n"
			+ "WHERE estatus = 'NUEVO' AND regla = 'COMPETENCIA' and PR.item  = '"+articulo+"'";
	
	testCase.addTextEvidenceCurrentStep("Consulta art�culo antes de descartarlo");
	SQLResult queryResultBuscaArti= executeQuery(dbSipreBara, consultaArticulo);
	testCase.addQueryEvidenceCurrentStep(queryResultBuscaArti);

	testCase.addBoldTextEvidenceCurrentStep("Click en bot�n descartar");
	pomSipre.descartarArticulo();
	
	u.hardWait(3);
	testCase.addTextEvidenceCurrentStep("Consulta art�culo despu�s de descartarlo");
	 queryResultBuscaArti= executeQuery(dbSipreBara, consultaArticulo);
	testCase.addQueryEvidenceCurrentStep(queryResultBuscaArti);

	assertTrue(queryResultBuscaArti.isEmpty(), "El art�culo no se Descart�");
	
	u.getDriver().quit();
		
	
	
	}
	
	

	@Override
	public void beforeTest() {
		// TODO Auto-generated mehod stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Aprobar Reglas de precio para art�culos 1 x 1";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_007_ApruebaReglasDePreci_ActualizaBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
    public void afterMethod() {
        u.getDriver().quit();
    }
}
