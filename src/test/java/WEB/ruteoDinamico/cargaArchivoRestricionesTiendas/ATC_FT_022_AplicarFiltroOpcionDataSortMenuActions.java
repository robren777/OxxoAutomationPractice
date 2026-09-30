package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

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
 * DESCRIPCION: Validar que al dar clic en Actions -> Data -> Sort, se despliegue las opciones de filtrado por 
 * 				ordenaci�n y se refleje en la tabla
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_022_AplicarFiltroOpcionDataSortMenuActions extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_AplicarFiltroOpcionDataSortMenuActions_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		ArrayList<String> establecerFiltro = new ArrayList<String>();
		establecerFiltro.add("Id Carga,Ascending,Last");
		establecerFiltro.add("Fecha Carga,Descending,Last");
		establecerFiltro.add("Fecha Ult. Envio a RDM,Ascending,First");
		
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
		
		int i = 3;
		for (String datoFiltro : establecerFiltro){
			String[] filtro = datoFiltro.split(",");
			String opcionColumna = filtro[0];
			String opcionDirection = filtro[1];
			String opcionNull = filtro[2];
			
			// PASO 3
			addStep("Clic en \"Actions\"");
			System.out.println("PASO " + i);
			
			rd.clicActions();
			i++;
			
			// PASO 4
			addStep("Clic en \"Data\"");
			System.out.println("PASO " + i);
			
			rd.clicOpcionData();
			i++;
			
			// PASO 5
			addStep("Clic en \"Sort\"");
			System.out.println("PASO " + i);
			
			rd.clicOpcionSort();
			i++;
			
			// PASO 6
			addStep("Establecer configuraci�n de filtro:\r\n"
					+ "- Columna: \r\n" + opcionColumna
					+ "- Direction:\r\n" + opcionDirection
					+ "- Null: " + opcionNull);
			System.out.println("PASO " + i);
						
			rd.establecerFiltroSort(opcionColumna, opcionDirection, opcionNull);
			i++;
			
			// PASO 7
			addStep("Clic en \"Save\"");
			System.out.println("PASO " + i);
			
			rd.clicSave();
			rd.refrescarPagina();
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
		return "Validar que al dar clic en Actions -> Data -> Sort,"
				+ " se despliegue las opciones de filtrado por"
				+ " ordenaci�n y se refleje en la tabla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_022_AplicarFiltroOpcionDataSortMenuActions_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
