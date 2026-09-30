package WorkFlows.ReporteDeAbastoCEDIS;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.ReporteDeAbastoCEDIS.PomReporteDeAbastoCEDIS;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_004_ValidacionDeSOM_performance extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidacionDeSOM_performance(HashMap<String, String> data) throws Exception {
		
		String userUltimus = GlobalVariables.ULTIMUS_USER_CEDIS;		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);			
		PomReporteDeAbastoCEDIS pom = new PomReporteDeAbastoCEDIS(u, userUltimus, testCase);
		
		try {
			// Step -1 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Ingresar a la base de datos de FCPVEQA.FEMCOM.NET, y en la tabla XXVA_ENG_COMPRAS_CEDIS contrar con registros habilitados (500mil) para ser procesados por el Job");
			u.get(data.get("url"));
			assertTrue(pom.buscarComprasCEDIS(this), "No se cuentran los registros");
			
			// Step -2 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Se modifica la fecha de la ejecuci�n del Job en XXVA_ENG_GLOBALS de FCPVEQA.FEMCOM.NET, En el campo Value, en el registro NAME= TODAY_REPABASTO");
			assertTrue(pom.validateFechaEjecucionJob(this), "No se realiz� el cambio de fecha de ejecuci�n del job");
				
			// Step -3 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Ir a la siguiente ruta My Views -> Views -> Iniciar");
			testCase.addBoldTextEvidenceCurrentStep("Y seleccionar el proceso Reporte de Abasto CEDIS");
			pom.selectIniciar();
			testCase.addScreenShotCurrentStep(u,"Se muestran las aplicaciones disponibles para el usuario");
			assertTrue(pom.validateAppsUltimus(), "No se muestran las aplicaciones disponibles para el usuario");
			
			// Step -4 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Seleccionar la opci�n \"Reporte de Abastecimiento\"");
			pom.accessReporteDeAbasto();		
			assertTrue(pom.validateAccessReporteDeAbastos(), "No se pued� acceder a la pagina de Reporte De Abasto Cedis");
			
			// Step -5 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Informar los siguientes campos y pulsar el bot�n  [Buscar]:");
			testCase.addBoldTextEvidenceCurrentStep(" ");
			testCase.addBoldTextEvidenceCurrentStep("• Lista desplegable \"Cedis\" (Capturar CEDIS surtido)");
			testCase.addBoldTextEvidenceCurrentStep("• Lista desplegable \"S�per Grupo\" (Dato Valido)");
			testCase.addBoldTextEvidenceCurrentStep("• Lista desplegable \"Rango\" (Dato Valido) ");
			testCase.addBoldTextEvidenceCurrentStep("• Caja de Texto \"C�digo\" (Dato Valido) ");
			pom.informarCampos();
			assertTrue(pom.validateGrid(), "No se encontraron articulos para la busqueda");			
			
			// Step -6 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Seleccionar el o los registros del Grid de la pantalla y modificar el valor del campo Store Order Multiple seleccionando uno de los dos valores (Inner, Case)");
			testCase.addBoldTextEvidenceCurrentStep("Presionar el bot�n [Guardar SOM]");
			pom.selectArticulo();
			
			// Step -7 ///////////////////////////////////////////////////////////////////////////////////////
			testCase.addBoldTextEvidenceCurrentStep("Solicitar la ejecuci�n del JOB XXVA_REP_ABA_CEDIS_AUT");
			assertTrue(pom.ejecutarJob(data.get("job_XXVA_REP_ABA_CEDIS_AUT")));
			
			// Step -8 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Establcer conexi�n a la BD de WF para validar que se haya creado el registro en la tabla stagin xxva_zz_femsa_repl_som_changes.");
			testCase.addBoldTextEvidenceCurrentStep("SELECT * FROM XXVA_ZZ_FEMSA_REPL_SOM_CHANGES WHERE ITEM = I_Item AND LOCATION = I_Loc AND SOM_NEW = I_Som;");
			assertTrue(pom.buscarCambio(this), "No se registr� el cambio en la base de datos");
			
			// Step -9 ///////////////////////////////////////////////////////////////////////////////////////
			addStep("Establecer conexi�n a la BD de RMS16 para consultar la tabla staging STG_REPL_SOM y validar que se haya insertado el registro de la actualizaci�n del SOM.");
			testCase.addBoldTextEvidenceCurrentStep("SELECT * FROM RMS_INT.STG_REPL_SOM WHERE ITEM = I_Item AND LOC = I_Loc AND SOM = I_Som AND MSG_TYPE = 'REPLSOMCRE' AND ORIGIN_SYSTEM = 'ReporteAbasto' AND PROCESS_IND  = 'N';");
			assertTrue(pom.buscarNuevoRegistro(this),"No existe el nuevo registro en la tabla");
			
			// Step -10 //////////////////////////////////////////////////////////////////////////////////////
			addStep("Solicitar la ejecuci�n del JOB NB_REPLE_SOM");
			assertTrue(pom.ejecutarJob(data.get("job_NB_REPLE_SOM")));
			
			// Step -11 //////////////////////////////////////////////////////////////////////////////////////
			addStep("Consultar la tabla NB_REPL_SOM para validar que se haya creado el registro de la actualizaci�n del SOM.");
			testCase.addBoldTextEvidenceCurrentStep("SELECT * FROM RMS_EXT.NB_REPL_SOM WHERE ITEM = I_Item AND LOC =I_Loc AND SOM = I_Som AND STATUS = 'N';");
			assertTrue(pom.consultarStatus(this),"No se pudo verificar el status");
			
			// Step -12 //////////////////////////////////////////////////////////////////////////////////////
			addStep("Solicitar la ejecuci�n del JOB NB_BATCH_SOM");
			assertTrue(pom.ejecutarJob(data.get("job_NB_BATCH_SOM")));
			
			// Step -13 //////////////////////////////////////////////////////////////////////////////////////
			addStep("Consultar la tabla ITEM_LOC y validar que se haya cambiado el valor del campo STORE_ORD_MULT para la cobertura del articulo.");
			testCase.addBoldTextEvidenceCurrentStep("SELECT * FROM RMS.ITEM_LOC WHERE ITEM = I_Item AND LOC = I_Loc AND STORE_ORD_MULT = I_Som;");
			assertTrue(pom.consultaCobertura(this), "No se pudo verificar la cobertura");
			
			u.close();
						
		}catch(Exception ex) {
			testCase.addScreenShotCurrentStep(u, ex.getMessage());
			assertTrue(false, ex.getMessage());
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "* Contar con 200 mil registros en la tabla XXVA_ENG_COMPRAS_CEDIS\r\n"+
				"* Contral con Job XXVA_REP_ABA_CEDIS_UAT";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Validar Performance del Job XXVA_REP_ABA_CEDIS_AUTcon una volumetria de 500mil registros"; 
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jos� Antonio Z�rate �vila";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_Validaci�nDeSOM_performance";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
