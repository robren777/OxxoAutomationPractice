package RDM.mermaYDev.NoAcreditacion;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Se realiza la verificacion de los ID de los 16 Motivos Originales de no acreditacion 
 * de devoluciones en base de datos
 * 
 * PREREQUISITOS: 
 * -Contar con acceso a base de datos RDM
 * 
 * INSUMOS:
 * N/A
 * 
 * @author Alejandro.Montalvo
 * @date 15/01/25
 */
public class ATC_FT_007_ID16MotivosOriginalesBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_ID16MotivosOriginalesBD_Test(HashMap<String, String> data) throws Exception {
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDM100, GlobalVariables.DB_USER_RDM_RDM100,
				GlobalVariables.DB_PASSWORD_RDM_RDM100);
		 SeleniumUtil u = new SeleniumUtil(new IETest());
	        String url = "http://example.com";
	        MermaYDev m = new MermaYDev(u, testCase, url);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
			
	    testCase.setProject_Name("I24046 Simplificación conciliación y motivos de acreditación");    
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Se ejecuta la validacion de los 16 ID de los 16 Motivos originales de no acreditacion");
		
	    
        // Validar existencia de tablas y agregar evidencia
        Map<Integer, SQLResult> resultados = m.validarId16MotivosOriginalesAcreditacion(dbRdm);
        for (Map.Entry<Integer, SQLResult> entry : resultados.entrySet()) {
            int id = entry.getKey();
            SQLResult rs = entry.getValue();
            testCase.addQueryEvidenceCurrentStep(rs);
            assertTrue(!rs.isEmpty(), "ID " + id + " no existe en la tabla rdm100.xxfc_unrc_reason_codes.");
        }
	    
	    
	    //Cerrar driver
		u.quit();
	}

	
	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "* -Tener acceso a Base de datos RDM";
	}

	@Override
	public String setTestDescription() {
		return "Se realiza la validacion de los id 16 motivos originales de no acreditacion" 
				+ " en Base de datos";
	}

	@Override
	public String setTestDesigner() {
		return "alejandro.montalvo@gtim.mx";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_ID16MotivosOriginalesBD_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}