package IMA.ComisionDifSpinByOxxo_Julio2024.scripts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.ComisionDifSpinByOxxo_Julio2024.om.BD_IMA;
import modelo.BaseExecution;



/*
 *Se valida la informacion desde las tablas de BD que realizan el reporte de IMA
 *ATC_FT_001_IMA_Validacion_de_informacion_en_tabla_EBS
 *pre requisitos 1.-contar con acceso a las BD  
@author Alan Perez
@date 23/01/25
*/

public class ATC_FT_001_IMA_Validacion_de_informacion_en_tabla_EBS extends BaseExecution{

	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_IMA_Validacion_de_informacion_en_tabla_ebs_test (HashMap<String, String> data) throws Exception{
		
		
		//data provider
		String fecha = data.get("Fecha");
		
		//Nueva instancia de clase para obtener su metodo
		BD_IMA bd = new BD_IMA();
		
		
		//mandamos a llamar la conexion y ejecutamos el query correspondiente con la fecha solicitada DD/MM/YY
		
		
		assertTrue(bd.SelectTransactions(fecha),"No se realiza la consulta de manera correcta");
	
	}
	
	


@Override
public void beforeTest() {
	// TODO Auto-generated method stub

}

@Override
public String setTestDescription() {
	// TODO Auto-generated method stub
	return "Validar estructura de reporte Nivel de Servicio y Venta Perdida (Semanal)";
}

@Override
public String setTestDesigner() {
	// TODO Auto-generated method stub
	return "Alan Perez";
}

@Override
public String setTestFullName() {
	// TODO Auto-generated method stub
	return "ATC_FT_001_IMA_Validación_de_informacion_en_tabla_ebs_test";
}

@Override
public String setTestInstanceID() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String setPrerequisites() {
	// TODO Auto-generated method stub
	return null;
}

}
