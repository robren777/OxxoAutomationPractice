package WEB.I26002_GestionBines.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.I26002_GestionBines.om.GestionBines;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
* <b>DESCRIPCIÓN:</b> Validar que al crear BIN hijo a 8 posiciones no herede configuraciones 
* 	de BIN padre. 
* <br>
* <b>NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:</b>
* <ul>
*     <li>MTC_FT_031_Crear un BIN hijo a 8 posiciones en Portal APEX</li>
* </ul>
* <b>PREREQUISITOS:</b> 
* <ul>
* 	<li> Acceso a la funcionalidad BINES en Apex.</li>
* 	<li>Informacion para crear BIN hijo.</li>
* </ul>
*
* <b>INSUMOS:</b>
* <ul>
* 	<li> BINES nuevos.</li>
* </ul>	
*
* @author	Oliver Martinez
* @date 	23/06/2026
*/
public class ATC_FT_031_CrearBINhijoa8posicionesEnPortalApexBINES extends BaseExecution {
	
	private SeleniumUtil u;
	private GestionBines gb;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_031_CrearBINhijoa8posicionesEnPortalApexBINES_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I26002_GestionBines");
		u = new SeleniumUtil(new ChromeTest(), true);
		gb = new GestionBines(u, testCase);
		
		String maxBin = Integer.toString(gb.getMaxBinPerBank(data.get("BINTYPE")));
		System.out.println("Max Bin: " + maxBin);
		
		addStep("Loguearse en la aplicación.");
		gb.loginApex(data.get("URL") ,data.get("USER"), data.get("PASSWORD"));
		
		addStep("Se muestran las secciones: Archivo, Cargar Archivo y listado de Archivos "
				+ "Cargados e Información.");
		assertTrue(gb.validateInicioPage(), "No se realizo Login de forma exitosa.");
		
		addStep("Ingresar a la página BINes.");
		gb.selectBinesOption();
		
		addStep("Se muestran las secciones: Registro de BINes, Cancelar, Aplicar Cambios, Histórico de Versiones.");
		assertTrue(gb.validateBinesPage(), "Pagina BINes no se encuentra en pantalla.");
		
		addStep("Dar clic en el botón Crear Nuevo.");
		gb.selectCrearNuevo();
		
		addStep("Se muestran las secciones: Registro de Bancos, Cancelar, Aceptar.");
		gb.validateBinPageForm();
		
		addStep("Ingresar informacion en los campos BIN, Status, Bandera, Banco, Tipo, "
				+ "Bank Desc, Nombre del Producto, Codigo Banco, Aplica pts Bancomer, "
				+ "aplica CashBack, Aplica para Dev, Aplica Deposito, Integrador");
		gb.selectOptionCreateBin("status");
		gb.selectOptionCreateBin("bandera");
		gb.selectOptionCreateBin("bank");
		gb.selectOptionCreateBin("type");
		gb.selectOptionCreateBin("bankcode");
		gb.selectOptionCreateBin("pts bancomer");
		gb.selectOptionCreateBin("devolucion");
		gb.selectOptionCreateBin("deposito");
		gb.selectOptionCreateBin("integrador");
		gb.typeInCreateBinFields(maxBin, data.get("DESCRIPTION"), data.get("PRODUCT"));
		
		addStep("Seleccionar \"Aplicar cambios\".");
		gb.aceptarCreateBin();
		
		addStep("Consultar en base de datos FCDASPRF QA en la tabla TDC_BIN "
				+ "se encuentre informacion del nuevo BIN.");
		assertTrue(gb.getBinCreated(maxBin), "No se encuentran registros de BIN en la tabla TDC_BIN.");
		
	}
	
	@AfterMethod
	public void cerrarNavegador() {
	    if (u != null) {
	        try {
	            gb.logout();
	        } catch (Exception e) {
	            System.out.println("Error al cerrar sesion: \r\n" + e);
	        } finally {
	            try {
	                u.quit();
	            } catch (Exception e) {
	                System.out.println("Error al cerrar el navegador: \r\n" + e);
	            }
	        }
	    }
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
