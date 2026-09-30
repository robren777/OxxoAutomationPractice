package Mobile.cedis.mvp3.handheld.sprint2;

import java.util.HashMap;

import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;

public class DebugVane extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void DebugVane_test(HashMap<String, String> data) throws Exception { 
    	
    	
    	D01_Conexiones_BD rdmmty = new D01_Conexiones_BD(testCase, null, this);
    	
    	addStep("Se realiza la conexión");
    	System.out.print("Se intenta realizar conexión a BD de RDM MTY. \n");
    	rdmmty.conexionrdmmty();
//    	addStep("Se realiza la consulta a la BD: \n");
//    	rdmmty.consultaPAVZONEG();
    	addStep("Se realiza la consulta a la BD: \n");
    	rdmmty.consultaPAVZONEe();
    	
    	
    }
    
    
    
    
	
    
	
	
	
    




    /**
     * @autor Vanessa Torres
     * @date 
     */

    @Override
    public String setTestFullName() {
        return "Debug Vane";
    }

    @Override
    public String setTestDescription() {
        return "Solo es para realizar pruebas debug";
    }

    @Override
    public String setTestDesigner() {
        return "Vanessa Torres";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "NA";
    }

    @Override
    public void beforeTest() {
    }
}