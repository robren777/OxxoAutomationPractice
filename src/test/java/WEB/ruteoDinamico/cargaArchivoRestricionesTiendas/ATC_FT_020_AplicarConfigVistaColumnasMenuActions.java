package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.RuteoDinamicoWebApex;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: Acceso al servidor de Apex (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que al dar clic en Actions -> Columns, se muestre las opciones de vista de la tabla para 
 * 				editar columnas desplegadas y se refleje en la tabla
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_020_AplicarConfigVistaColumnasMenuActions extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_AplicarConfigVistaColumnasMenuActions_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		ArrayList<String> establecerColumnas = new ArrayList<String>();
		establecerColumnas.add("Id Carga,colIdCarga,100");
		establecerColumnas.add("Fecha Carga,colFechaCarga,200");
		establecerColumnas.add("Fecha Ult. Envio a RDM,colFechaUltEnvio,200");
		establecerColumnas.add("Archivo Excel,colArchivoExcel,400");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		RuteoDinamicoWebApex rd = new RuteoDinamicoWebApex(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Hacer login en plataforma Apex");
		System.out.println("PASO 1");
		
		rd.loginApexCargaArchivo();
		
		// PASO 2
		addStep("Clic en \"XXFC_HISTORICO_CARGAS\"");
		System.out.println("PASO 2");
		
		rd.clicXxfcHistoricoCargas();
		rd.refrescarPagina();
		
		int i = 3;
		for (String datoColumna : establecerColumnas){
			String[] columna = datoColumna.split(",");
			String nombreColumna = columna[0];
			String opcionColumna = columna[1];
			String tamColumna = columna[2];
			
			// PASO 3
			addStep("Clic en \"Actions\"");
			System.out.println("PASO " + i);
			
			rd.clicActions();
			i++;
			
			// PASO 4
			addStep("Clic en \"Column\"");
			System.out.println("PASO " + i);
			
			rd.clicOpcionColumns();
			i++;
			
			// PASO 5
			addStep("Quitar columna " + nombreColumna + " de la vista de la tabla");
			System.out.println("PASO " + i);
			
			rd.refrescarPagina();
			rd.clicActions();
			rd.clicOpcionColumns();
			rd.establecerOpcionColumna(opcionColumna, nombreColumna, tamColumna);
			i++;
			
			// PASO 6
			addStep("Clic en \"Save\"");
			System.out.println("PASO " + i);
			
			rd.clicSave();
			rd.refrescarPagina();
			assertTrue(rd.validarNumColTabla(), "La columna " + nombreColumna + " no se elimino de la vista");
			i++;
		}
		
		
		// CERRAR APEX
		System.out.println("Cerrando Plataforma Apex");
		
		u.quit();
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
		return "Validar que al dar clic en Actions -> Columns, se "
				+ "muestre las opciones de vista de la tabla para editar"
				+ " columnas desplegadas y se refleje en la tabla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_AplicarConfigVistaColumnasMenuActions_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
