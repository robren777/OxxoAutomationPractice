package IMA.Paths_ExtraccionDirecta_InfoComercial_PenalizacionProveedores_03RevisionDatosDestino;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_03 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_03_Test(HashMap<String, String> data) throws Exception {
		
		utils.sql.SQLUtil rms_mex = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		String consultaTabla = "Select * from BIODSMTTO.TIENDA where %s in TIENDAS";
		String consultaCR = "Select * from BIODSMTTO.T_TIENDAS where %s IN TIENDAS";
		String id_tienda = "Select * from BIODSMTTO.T_OPERACION  where ID_TIENDA IN (<<<ID -TIENDAS>>>)";
		String nombre_tiendas = "Select * from BIODSMTTO.MTL_EAM_LOCATIONS  where location_codes LIKES %s";
		
		// Paso 1
		addStep("Conectarse a la Base de Datos: BIMTTO BI");
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		// Paso 2
		addStep("Consultar  la tabla  BIODSMTTO.TIENDA");
		consultaTabla = String.format(consultaTabla, data.get("tienda_key"));
		System.out.println(consultaTabla);		
		SQLResult connectionResult = executeQuery(dbBi, consultaTabla);
		
		boolean connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 3
		addStep("consultar la tabla BIODSMTTO.T_TIENDAS mediante la siguiente consulta:\n"
				+ "Select * from BIODSMTTO.T_TIENDAS  where CR_TIENDA IN (<<<TIENDAS>>>)");
		consultaCR = String.format(consultaCR, data.get("cr_tienda"));
		System.out.println(consultaCR);		
		connectionResult = executeQuery(dbBi, consultaCR);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 4
		addStep("consultar la tabla BIODSMTTO.T_OPERACION mediante la siguiente consulta:\n"
				+ "Select * from BIODSMTTO.T_OPERACION  where ID_TIENDA IN (<<<ID -TIENDAS>>>);");
		id_tienda = String.format(id_tienda, data.get("id_tienda"));
		System.out.println(id_tienda);		
		connectionResult = executeQuery(dbBi, id_tienda);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 5
		addStep("consultar la tabla BIODSMTTO.MTL_EAM_LOCATIONS mediante la siguiente consulta:\n"
				+ "Select * from BIODSMTTO.MTL_EAM_LOCATIONS  where location_codes LIKES (<<<'%NOMBRE_TIENDAS%'>>>)");
		nombre_tiendas = String.format(nombre_tiendas, data.get("nombre_tiendas"));
		System.out.println(nombre_tiendas);		
		connectionResult = executeQuery(dbBi, nombre_tiendas);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");	
		
		// Paso 6
		addStep("Consultar  la tabla  BIODSMTTO.TIENDA");
		consultaTabla = String.format(consultaTabla, data.get("tienda_key"));
		System.out.println(consultaTabla);		
		connectionResult = executeQuery(dbBi, consultaTabla);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		// Paso 7
		// El paso 7 es una solicitud por correo electronico por tanto queda decartada
		
		// Paso 8
		// El paso 8 es una revisi�n de la solicitud por correo electronico, por tanto queda descartada
		
		// Paso 9
		addStep("Consultar  la tabla  BIODSMTTO.TIENDA");
		consultaTabla = String.format(consultaTabla, data.get("tienda_key"));
		System.out.println(consultaTabla);		
		connectionResult = executeQuery(dbBi, consultaTabla);
		
		connection = true;
		if(connectionResult.isEmpty())
			connection = false;
		assertTrue(connection, "La tabla no contiene informaci�n.");
		
		
		
	}
	
  
	@Override
	public String setTestFullName() {
		return "ATC_FT_03";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que una vez ejecutados los procesos, la informaci�n del CP 1 se vea reflejada en las tablas ODS correspondientes";
	}

	@Override
	public String setTestDesigner() {
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}
}
