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
* <b>DESCRIPCIÓN:</b> Validar que permita realizar la edicion de un BIN y este se guarde correctamente. 
* <br>
* <b>NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:</b>
* <ul>
*     <li>MTC_FT_029_Editar un BIN en portal APEX</li>
* </ul>
* <b>PREREQUISITOS:</b> 
* <ul>
* 	<li> El usuario debe tener permisos de escritura en la tabla xxfc_users_bines.
* 	<li> Contar con datos insertados en las tabla tdc_bandera, tdc_card_type y tdc_bank</li>
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
public class ATC_FT_029_EditarBINenPortalAPEX extends BaseExecution{
	
	private SeleniumUtil u;
	private GestionBines gb;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_029_EditarBINenPortalAPEX_test(HashMap <String, String> data) throws Exception {
		testCase.setProject_Name("I26002_GestionBines");
		u = new SeleniumUtil(new ChromeTest(), true);
		gb = new GestionBines(u, testCase);
		
		String bin = gb.getBin();
		System.out.println("BIN G: " + bin);
		
		addStep("Loguearse en la aplicación.");
		gb.loginApex(data.get("URL") ,data.get("USER"), data.get("PASSWORD"));
		
		addStep("Se muestran las secciones: Archivo, Cargar Archivo y listado de Archivos "
				+ "Cargados e Información.");
		assertTrue(gb.validateInicioPage(), "No se realizo Login de forma exitosa.");
		
		addStep("Ingresar a la página BINes.");
		gb.selectBinesOption();
		
		addStep("Se muestran las secciones: Registro de BINes, Cancelar, Aplicar Cambios, Histórico de Versiones.");
		assertTrue(gb.validateBinesPage(), "Pagina BINes no se encuentra en pantalla.");
		
		addStep("Seleccionar un BIN y dar clic en la columna Editar.");
		gb.searchBinAndEdit(bin);
		
		addStep("Se muestra grid de datos con el reporte con el histórico de versiones (cambios realizados).");
		assertTrue(gb.validateBinPageForm(), "Pagina editar Bin no se encuentra en pantalla.");
		
		addStep("Realizar modificacion de informacion del BIN y seleccionar Aplicar cambios");
		gb.selectOptionEditBin("status");
		gb.selectOptionEditBin("bank");
		gb.selectOptionEditBin("bankcode");
		gb.selectOptionEditBin("pts bancomer");
		gb.selectOptionEditBin("devolucion");
		gb.selectOptionEditBin("deposito");
		gb.selectOptionEditBin("integrador");
		
		gb.selectAplicarCambios();
		
		addStep("Debera permitir realizar guardado exitoso.");
		assertTrue(gb.validateSuccesMessage(), "Mensaje de guardado exitoso no se encontro.");
		
		addStep("Validar que el BIN se muestre actualizado correctamente en la tabla TDC_BIN_CHANGES.");
		assertTrue(gb.getBinCreated(bin), "No se encuentran registros de BIN en la tabla TDC_BIN_CHANGES.");

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
		return "- Usuario\r\n"
				+ "- Contraseña\r\n"
				+ "- Datos de nuevo BIN";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que permita realizar el registro de un nuevo BIN"
				+ " padre a 6 posiciones y este se guarde correctamente.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Oliver Martinez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_029_EditarBINenPortalAPEX";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
