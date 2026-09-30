package WorkFlows.Planogramas.PlanogramasNexxo;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Jobs.JobUtils;
import WorkFlows.util.Planogramas.PomPlanogramas;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_003_ValCargaInicialLegos_PlanogramasNexxo extends BaseExecution{
	
	

	@Test(dataProvider = "data-provider")
	 
	 public void ATC_FT_003_ValCargaInicialLegos_PlanogramasNexxo_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PLANOGRAMAS NEXXO");
	

	/**
	 * Variables
	 * 
	 */
	String resultadoEjecucion;
	boolean ejecucionCorrectaJob;
	SQLResult queryResultBuscaArchivos;
	String BASE_PATH = System.getProperty("user.dir");
	String lego_id=data.get("item");
	String accion = data.get("accion");
	String ruta =(BASE_PATH+"\\src\\test\\java\\utils\\PlanogramasNexxo\\ArchivosFTP\\");
	String host = GlobalVariables.HOST_FTP_PLANOGRAMAS;
	String user = GlobalVariables.USER_FTP_PLANOGRAMAS;
	String pass = GlobalVariables.PASS_FTP_PLANOGRAMAS;
	String port = "22";


	/**
	 * QUERYS
	 */
	
	String QUERY_SIV3_XXVA_PLANOGRAMA_APROBADO = "SELECT ITEM, LEGOS_ID, ITEM_DESCRIPCION, LAST_UPDATE_DATE FROM XXVA_SIV3.XXVA_ENG_PLANOGRAMA_APROBADO where LEGOS_ID= "+data.get("lego_id")+" AND TRUNC(LAST_UPDATE_DATE)= TRUNC(SYSDATE)";
	String QUERY_SIV3_XXVA_SEM_LEGOS = "SELECT LEGOS_ID, PLAZA_ID, DESCRIPCION_LEGO, CREATE_DATE, LAST_UPDATE_DATE FROM XXVA_SIV3.xxva_sem_legos where LEGOS_ID = "+data.get("lego_id")+" AND ROWNUM <=5";
	String QUERY_SIV3_XXVA_SEM_MUEBLE_ID = "SELECT LEGOS_ID,SEMBRADO_MUEBLE_ID, MUEBLE_ID, CONJUNTO_ID FROM XXVA_SIV3.xxva_sem_sembrado_mueble where LEGOS_ID = "+data.get("lego_id")+" AND ROWNUM <=5";
	String QUERY_SIV3_XXVA_ENG_LEGOS_MV = "SELECT LEGOS_ID, PLAZA_ID, DESCRIPCION_LEGO, CREATE_DATE, LAST_UPDATE_DATE FROM XXVA_SIV3.xxva_eng_lego_mv  where LEGOS_ID = "+data.get("lego_id")+" AND ROWNUM <=5";
	String QUERY_SIV3_XXVA_Refresh = "{call dbms_mview.refresh('XXVA_SIV3.XXVA_RMS_ITEM_MASTER_FULL_MV')}";





	/**
	 * 
	 * Utilerías
	 */
	
	utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,GlobalVariables.DB_USER_XXVA_SIV3, 
			GlobalVariables.DB_PASSWORD_XXVA_SIV3);
	utils.sql.SQLUtil dbXXVA_SIV3_Admin = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_XXVA_SIV3,"XXVA_SIV3", 
			"xxvasiv3#QA");
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	JobUtils jobUtil = new JobUtils(u, testCase, data, "jobs.xlsx");

	System.out.println("Item ---> "+data.get("item"));
	addStep("Crear articulos en RMS16 con estatus aprobados y ejecutar el refresh a la tabla\r\n"
			+ "exec dbms_mview.refresh('XXVA_SIV3.XXVA_RMS_ITEM_MASTER_FULL_MV');");
	
				Statement stmt = dbXXVA_SIV3_Admin.getConn().createStatement();
			
			    boolean execute= stmt.execute(QUERY_SIV3_XXVA_Refresh);
			    
						    
						     if(execute == false) {
						
						      testCase.addTextEvidenceCurrentStep("PL/SQL Procedimiento completado exitosamente.");
						
						}else {
						
						  testCase.addTextEvidenceCurrentStep("PL/SQL Procedimiento no completado.");
						
						}
						
				assertFalse(execute,"No se ejecuto correctamente el procedimiento PL/SQL");
						
	addStep("Validar que los set de legos existen y estan sembrados, para ello deben existir en las siguientes tablas");
				
				testCase.addTextEvidenceCurrentStep("xxva_sem_legos");
			
				queryResultBuscaArchivos= executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXVA_SEM_LEGOS);
				testCase.addQueryEvidenceCurrentStep(queryResultBuscaArchivos);
				assertFalse(queryResultBuscaArchivos.isEmpty(), "No se encontraron legos");
			    System.out.println("1");
			
				testCase.addTextEvidenceCurrentStep("xxva_sem_sembrado_muebles");
			
				queryResultBuscaArchivos= executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXVA_SEM_MUEBLE_ID);
				testCase.addQueryEvidenceCurrentStep(queryResultBuscaArchivos);
				assertFalse(queryResultBuscaArchivos.isEmpty(), "No se encontraron legos");
			    System.out.println("2");
			
		  
		  addStep("El archivo a cargar debe tener las columnas correctas -Validar que dentro de la columna accion contenga el valor "+accion+" para indicar que se hará un cambio al registro.\r\n"
					+ "A continuación se describen las columnas que debe contener el archivo.\r\n"
					+ "\r\n"
					+ "\r\n• UPS (artículo)\r\n"
					+ "• Lego\r\n"
					+ "• Charola\r\n"
					+ "• Ubicación en la bandeja\r\n"
					+ "• # Frentes\r\n"
					+ "• #Fentes Verticales\r\n"
					+ "• #_Profundos\r\n"
					+ "• Tipo de Mercadeo\r\n"
					+ "• Capacity\r\n"
					+ "• Alto\r\n"
					+ "• Ancho\r\n"
					+ "• Profundo\r\n"
					+ "• X_Item\r\n"
					+ "• Y_Item\r\n"
					+ "• Tipo_Fixture\r\n"
					+ "• X_Fixture\r\n"
					+ "• Y_Fixture\r\n"
					+ "• Numero_segmento\r\n"
					+ "• Accion (A,B,C)\r\n"
					+ "Resultado esperado: La columna de Acción deberá contener la leyenda de Agregar para indicar que se agregará un nuevo registro, Modificar para indicar que se hará un cambio al registro o Eliminar para indicar que el registro se eliminará.\"");
			
			PomPlanogramas pom = new PomPlanogramas(u, testCase, data);

			assertTrue(pom.validateContenidoArchivo(ruta, "POG_20221230_162147_3.csv", "agregar"), "El contenido del archivo no es correcto");
			
			
			addStep("Validar lego existente con la combinación de la llave articulo + lego +charola+ubicación\r\n"
					+ "Resultado esperado: Se valida que el registro no existe");
			assertTrue(pom.validateExistenciaLegoAgregar(this, ruta, "POG_20221230_162147_3.csv"),"No se puede insertar el lego, el lego ya existe");

			addStep("Cargar el archivo .csv de  <ambas direcciones sin sembrar>  en la ruta /u02/usrplan/ini.");
			pom.sftpCarga(this, host, port, user, pass, ruta, "POG_20221230_162147_3.csv", "/u11/usrplan/.usrplan/csv");

			
			 addStep("Ejecutar Job starMonitor");
				
				resultadoEjecucion = jobUtil.ejecutar("starMonitor");
				ejecucionCorrectaJob = jobUtil.fueEjecutadoCorrectamente(resultadoEjecucion);
				System.out.println("Resultado -------> "+resultadoEjecucion);
				System.out.println(ejecucionCorrectaJob);

				assertTrue(ejecucionCorrectaJob, "El job starMonitor no se ejecuto correctamente");

			
			
			addStep("Validar en la tabla XXVA_ENG_PLANOGRAMA_TRA_MON que se muestra el nuevo campo del lego\r\n"
					+ "Resultado esperado: Se valida correctamente");
			assertTrue(pom.validateLegoCargado(this, "XXVA_ENG_PLANOGRAMA_tra_mon", accion), "No se encontro el lego cargado en la tabla XXVA_ENG_PLANOGRAMA_TRA_MON");
			
			
		////////////////////////////////////////////////////////////////////////////////
		addStep("'Validar que en la tabla XXVA_ENG_PLANOGRAMA_APROBADO 'Se muestra correctamente los legos con la variedad de los articulos"); 
					
			queryResultBuscaArchivos= executeQuery(dbXXVA_SIV3, QUERY_SIV3_XXVA_PLANOGRAMA_APROBADO);
			testCase.addQueryEvidenceCurrentStep(queryResultBuscaArchivos);
			
			assertFalse(queryResultBuscaArchivos.isEmpty(), "No se encontraron registros");
				
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Solicitar en hold el job StarMonitor y configurar el lego en el archivo csv";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar Carga Inicial de Legos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_ValCargaInicialLegos_PlanogramasNexxo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
