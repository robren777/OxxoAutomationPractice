package WEB.inventarioEspacios.scripts.CargaMasivaEspacios;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.BandejaPedidos;
import WEB.inventarioEspacios.om.CargaMasivaInventariosIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_CargaMasivaEspacios_Equipos_Espacios_ErrorArchivo extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_CargaMasivaEspacios_Equipos_Espacios_ErrorArchivo_test(HashMap<String, String> data) throws Exception {
	
		/*
		 * Utiler�as
		 *********************************************************************/
		
		SQLUtil dbFCIASQAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL, GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		Boolean validacion;
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		CargaMasivaInventariosIEP cmi = new CargaMasivaInventariosIEP(u, testCase);
		MetodosExtra me = new MetodosExtra(u, testCase);
		BandejaPedidos bp = new BandejaPedidos(u, testCase);
		String query;
		
		String db = "";
		String negocio_id = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
				negocio_id = "1";
			break;
			
			default: 
				db = "XXINE";
				negocio_id = "2";
			break;
		
		}
		
		
		testCase.setTest_Description("ATC_FT_002_CargaMasivaEspacios_Equipos_Espacios_ErrorArchivo - "+ perfil +" " + negocio);

		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario y contrase�a correcta");
		u.get(pa.getUrl());
		u.hardWait(3);
		
		pa.proceedToPortal();
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep(" Se accede correctamente al portal de aplicaciones.");
		
		query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		//String nameUser = data.get("nombreUsuario");
		
		validacion = pa.accesoCorrectoPortal(nameUser);
		assertTrue(validacion, "No se accede correctamente al portal de aplicaciones");
		
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Seleccionar Inventario de Espacios V2");
		pa.clickPortalInventarioEspacios();
		u.waitForLoadPage();
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Se ingresa al Inventario de espacios correctamente.");
		validacion = mp.accesoCorrectoIEP();
		assertTrue(validacion, "No se ingresa al Inventario de espacios correctamente.");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que se visualicen los MENUS");
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
		
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
			
		}
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Los menus se muestran correctamente.");
		
		
		testCase.addTextEvidenceCurrentStep("Se muestra correctamente los menus");
		assertTrue(true, "No se muestran correctamente los men�s");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo");
		validacion = mp.verificarEncabezado(negocio);

		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("El sistema muestra el Negocio, periodo y estatus del periodo.");
		
		if (validacion) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del negocio y periodo");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del negocio y/o periodo no se muestran");
		}
		
		assertTrue(validacion, "No Muestra la informaci�n en el encabezado");
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Seleccionar menu Espacios --> Carga masiva de Inventario");
		
		switch(perfil.toUpperCase()) {
		case "ADMINISTRADOR": 
			if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
				mp.clickEspacios();
			}
			
		break;
		
		case "PLAZA": mp.clickEspaciosPO();
		break;
		
		}
		
		mp.clickCargaMasivaInventarios();
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Se muestra la pantalla con los filtros: " + 
				"Carga de archivo " + 
				"Sistema " + 
				"Tipo" + 
				"y el boton : \"\"Subir Archivo\"\"\"");
		
		validacion = cmi.pantallaFiltros();
		assertTrue(validacion, "No se muestran correctamente los filtros");
		
		String numNextToLastFile = cmi.getLastFile();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Seleccionar archivo CSV desde tu maquina ");
		
		//cmi.clickChooseFileBtn();
		validacion = cmi.cargarArchivo();
		assertTrue(validacion, "No se pudo cargar correctamente el archivo");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		
		addStep("Escoger el sistema y dar click en el boton de \"Subir Archivo\"");
		
		cmi.clickSubirArchivoBtn();
		
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		
		addStep("El archivo comienza a cargarse y en pantalla se muestra lo siguiente: " + 
				"Archivo: Ruta y nombre del documento " + 
				"Fecha Inicio: Fecha de inicio de carga " + 
				"Fecha Fin: Fecha fin de carga " + 
				"Conteo: Porcentaje de carga (Avance al momento de la consulta) " + 
				"Estatus: Estatus de carga (Validando, ejecutando, cancelado o terminado) " + 
				"Log: Informaci�n de la carga y log de errores");
		
		me.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		String numLastFile = cmi.getLastFile();
		if(numLastFile != numNextToLastFile) {
			validacion = cmi.validarDatosExcelCargado();
			assertTrue(validacion, "La informaci�n no se muestra correctamente");
		}else {
			assertTrue(false, "No se cre� un registro nuevo para el archivo cargado");
		}
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		
		addStep("El sistema termina las validaciones y muestra log de errores para descargar.");
		
		validacion = cmi.esperarCarga();
		assertTrue(validacion , "No ha terminado de subirse el archivo");
		
		if(cmi.existenErrores()) {
			validacion = cmi.validarLog();
			assertTrue(validacion, "No se genero el archivo Log");
		}
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		
		addStep("Validar que los errores en el log sean correctos.");
		
		cmi.descargarLog();
		
		Sheet firstSheetFL = cmi.createFicheroLoaded2();
        
		
        String fd = cmi.getNombreArchivo();
        
        File fichero2 = cmi.createFicheroDownloaded(fd);
        
        ArrayList<String> errores = new ArrayList<String>();
        String error = "";
		
        for(int i = 1; i <= firstSheetFL.getLastRowNum(); i++) {
        	
        	Boolean correct = false;
        	
        	Row rowFL = firstSheetFL.getRow(i);
        	
        	String plaza = bp.datoCelda(firstSheetFL, i, 1);
        	query = "SELECT * FROM " + db + ".XXINE_CAT_PLAZAS "
        			+ "WHERE nombre_plaza = '" + plaza + "' and plaza_inactiva is null";
        	ExecuteQuery = executeQuery(dbFCIASQAS, query);
    		Boolean StatusQuery = ExecuteQuery.isEmpty();
    		
    		if (!StatusQuery) {
    			
    			if (ExecuteQuery.getData(0, "NOMBRE_PLAZA").equals(plaza)) {
    				
    				String espacio = bp.datoCelda(firstSheetFL, i, 6);
    				query = "SELECT * FROM " + db + ".XXINE_CAT_ESPACIOS "
    						+ "WHERE nombre_espacio = '" + espacio + "' "
    						+ "and negocio_id = '" + negocio_id + "' and sistema = 'Espacios'";
    				ExecuteQuery = executeQuery(dbFCIASQAS, query);
    				StatusQuery = ExecuteQuery.isEmpty();
    				
    				if (StatusQuery) {
    					query = "SELECT * FROM " + db + ".XXINE_CAT_ESPACIOS "
        						+ "WHERE nombre_espacio = '" + espacio + "' "
        						+ "and negocio_id = '" + negocio_id + "' and sistema = 'Equipos'";
        				ExecuteQuery = executeQuery(dbFCIASQAS, query);
        				StatusQuery = ExecuteQuery.isEmpty();
    				}
    				
    				if (!StatusQuery) {
    					
    					String cantidad = bp.datoCelda(firstSheetFL, i, 7);
    					if (cantidad != null && cantidad.matches("[0-9]+")) {
    						Integer cant = Integer.parseInt(cantidad);
    						if (cant <= 100) {
    							correct = true;
    						}else {
    							error = "Error en la cantidad";
    						}
    					}else {
    						error = "Error en la cantidad";
    					}
    					
    							
    				}else{
    					//error = "No se encontr� la tienda con CR " + bp.datoCelda(firstSheetFL, i, 3);
    					error = "Espacio no existe; ";
    				}
    				
    			} else {
    				//error = "No se encontr� la plaza con Nombre " + plaza;
    				error = "Plaza no existe; ";
    			}
    			
    		}else {
    			//error = "No se encontr� la plaza con Nombre " + plaza;
				error = "Plaza no existe; Tienda no existe; Espacio no existe; ";
    		}
    		
    		if (!correct) {
    			
    			validacion = cmi.validarErrores(rowFL, fd);
    			
    			if (!validacion) {
    				 String row = "";
	       			 for(int j = 0; j <= 7; j++) {
	       				 row = row + bp.datoCelda(firstSheetFL, i, j) + " ";
	       			 }
	       			 
	       			 row += " " + error;
	       			 //System.out.println(row);
	       			 errores.add(row);
    			}
    			
    		}
        	
        }
        
        validacion = cmi.validarErrorReglas(fd);
        
        fichero2.delete();
        
        
        if (errores != null && !errores.isEmpty() || validacion) {
        	for (String e : errores) {
                testCase.addTextEvidenceCurrentStep(e);
            }
        	testCase.addTextEvidenceCurrentStep("El archivo Log de errores es correcto");
        }else {
        	assertTrue(false, "El archivo Log de errores no es correcto");
        }
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_CargaMasivaEspacios_Equipos_Espacios_ErrorArchivo_test";
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
		u.getDriver().quit();
	}
}