package RDM.mermaYDev.NoAcreditacion;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Se realiza la validacion de los 14 Nuevos Motivos de no acreditacion 
 * de devoluciones en pantalla XXFC ASN NSC Receiving (Proceso Original)
 * 
 * PREREQUISITOS: 
 * -Contar con acceso a base de datos RDM
 * -Contar con acceso a aplicativo RDM
 * -Usar Microsoft Edge en modo Internet Explorer
 * -Tener habilitado los 14 Nuevos motivos para su validacion
 * 
 * INSUMOS:
 * -Tener generada previamente una cita (Appoinment Schedule) > Ligar ASN a BOL (NSC Appoinment Detail)
 * 
 * @author Alejandro.Montalvo
 * @date 15/01/25
 */
public class ATC_FT_001_14NuevosMotivosAsnNscReceiving extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_14NuevosMotivosAsnNscReceiving_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * VARIABLES
		 */
		String apptNbr = data.get("apptNbr");
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDM100, GlobalVariables.DB_USER_RDM_RDM100,
				GlobalVariables.DB_PASSWORD_RDM_RDM100);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		testCase.setProject_Name("I24046 Simplificación conciliación y motivos de acreditación");
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("En el menu principal de RDM ingresar a la opcion \"Receiving\" "
				+ "Ingresar a la opcion XXFC ASN / NSC Receiving ");
		System.out.println("PASO 2");
		
		m.clickMenuReceivingXxfcAsnNscReceiving();
		
		// PASO 3
		addStep("Ingresar el apptNBR y dar clic en palomita para desplegar las devoluciones "
				+ "a no acreditar");
		System.out.println("PASO 3");
		
		m.ingresarApptNbrCargaDevoluciones(apptNbr);	
						
		// PASO 4
		addStep("Verificar los 14 nuevos motivos de no acreditacion existentes");
        System.out.println("PASO 4");                
        
        boolean result = m.validar14NuevosMotivosNoAcreditacion();
        assertTrue(result, "No todos los motivos de no acreditacion fueron encontrados.");                    
        
        // Finalizar el script
		u.quit();
	}


	@Override
	public String setTestDescription() {
		return "Se realiza la validacion de los 14 nuevos motivos de no acreditacion"
				+ "en la pantalla XXFC ASN / NSC Receiving";
	}

	@Override
	public String setTestDesigner() {
		return "alejandro.montalvo@gtim.mx";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_14NuevosMotivosAsnNscReceiving_Test";
	}


	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String setPrerequisites() {
		return " * -Contar con acceso a base de datos RDM\r\n"
	+ " * -Contar con acceso a aplicativo RDM\r\n"
	+ " * -Usar Microsoft Edge en modo Internet Explorer\r\n"
	+ " * -Tener habilitado los 14 Nuevos motivos para su validacion\r\n"
	+ " * -Tener generada previamente una cita (Appoinment Schedule) > Ligar ASN a BOL (NSC Appoinment Detail)";
	
	}


	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


}