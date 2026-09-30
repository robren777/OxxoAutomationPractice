package WEB.ruteoDinamico.cargaArchivoRestricionesTiendas;

import static org.testng.Assert.assertFalse;

import java.time.LocalDate;
import java.time.Month;
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
 * DESCRIPCION: Validar que al dar clic en Actions -> Filter, se muestre las opciones de filtro avanzado y se 
 * 				refleje en la tabla
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_021_AplicarFiltroOpcionFilterMenuActions extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_AplicarFiltroUsandoOpcionFilterMenuActions_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		Month mes = LocalDate.now().getMonth();
		String mesCorto = mes.toString().substring(0, 3);
		ArrayList<String> establecerFiltro = new ArrayList<String>();
		establecerFiltro.add("Column,Id Carga,greater than,5");
		establecerFiltro.add("Column,Fecha Carga,is not empty,N/A");
		establecerFiltro.add("Column,Fecha Ult. Envio a RDM,contains," + mesCorto);
		establecerFiltro.add("Column,Archivo Excel,starts with,CAT");
		establecerFiltro.add("Row,Row,N/A," + mesCorto);
		
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
			String opcionType = filtro[0];
			String opcionColumna = filtro[1];
			String opcionOperador = filtro[2];
			String valorInput = filtro[3];
			
			// PASO 3
			addStep("Clic en \"Actions\"");
			System.out.println("PASO " + i);
			
			rd.clicActions();
			i++;
			
			// PASO 4
			addStep("Clic en \"Filter\"");
			System.out.println("PASO " + i);
			
			rd.clicOpcionFilter();
			i++;
			
			// PASO 5
			addStep("Establecer configuraci�n de filtro:\r\n"
					+ "- Type: \r\n" + opcionType
					+ "- Column: \r\n" + opcionColumna
					+ "- Operator: \r\n" + opcionOperador
					+ "- Value: " + valorInput);
			System.out.println("PASO " + i);
						
			rd.establecerFiltroColumna(opcionType, opcionColumna, opcionOperador, valorInput);
			i++;
			
			// PASO 6
			addStep("Clic en \"Save\"");
			System.out.println("PASO " + i);
			
			rd.clicSave();
			rd.refrescarPagina();
			assertFalse(rd.validarVacioPrimeraCelda(), "La tabla est� vac�a");
			rd.clicEliminarFiltro();
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
		return "Validar que al dar clic en Actions -> Filter,"
				+ " se muestre las opciones de filtro avanzado"
				+ " y se refleje en la tabla";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_021_AplicarFiltroUsandoOpcionFilterMenuActions_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
