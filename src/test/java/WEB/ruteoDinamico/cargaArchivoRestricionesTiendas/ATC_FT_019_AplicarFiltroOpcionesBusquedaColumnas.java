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
 * DESCRIPCION: Validar filtrado de cargas hist�ricas por medio de las opciones de columna
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_019_AplicarFiltroOpcionesBusquedaColumnas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_AplicarFiltroOpcionesBusquedaColumnas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		Month mes = LocalDate.now().getMonth();
		String mesCorto = mes.toString().substring(0, 3);
		String datoSensitivo = data.get("datoFiltroSensitivo");
		ArrayList<String> filtracionColumnas = new ArrayList<String>();
		filtracionColumnas.add("All Text Columns,opcionAllTextColumns," + data.get("datoFiltro"));
		filtracionColumnas.add("Fecha Carga,opcionFechaCarga," + mesCorto);
		filtracionColumnas.add("Fecha Ult. Envio a RDM,opcionFechaUltEnvio," + mesCorto);
		filtracionColumnas.add("Archivo Excel,opcionArchivoExcel," + data.get("datoFiltro"));
		filtracionColumnas.add("Case Sensitive,opcionCaseSensitive," + data.get("datoFiltro"));
		
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
		for (String filtroCol : filtracionColumnas){
			String[] datoFiltro = filtroCol.split(",");
			String nombreFiltro = datoFiltro[0];
			String filtro = datoFiltro[1];
			String datoBusqueda = datoFiltro[2];
			
			// PASO 3
			addStep("Clic en el icono en forma de lupa del lado izquierdo del campo Search");
			System.out.println("PASO " + i);
			
			rd.clicFiltroColumnas();
			i++;
			
			// PASO 4
			addStep("Establecer tipo de filtro " + nombreFiltro);
			System.out.println("PASO " + i);
			
			rd.clicOpcionFiltro(filtro, nombreFiltro);
			i++;
			
			// PASO 5
			addStep("Establecer alg�n dato en el campo Search");
			System.out.println("PASO " + i);
						
			rd.establecerDatoBusqueda(datoBusqueda);
			i++;
			
			// PASO 6
			addStep("Clic en el bot�n \"Go\"");
			System.out.println("PASO " + i);
			
			rd.clicGo();
			assertFalse(rd.validarVacioPrimeraCelda(), "La tabla est� vac�a");
			rd.clicEliminarFiltro();
			if (nombreFiltro.contentEquals("Case Sensitive")) {
				rd.establecerDatoBusqueda(datoSensitivo);
				rd.clicGo();
				assertFalse(rd.validarVacioPrimeraCelda(), "La tabla est� vac�a");
			} 
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
		return "Validar filtrado de cargas hist�ricas por medio de las opciones de columna";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_019_AplicarFiltroOpcionesBusquedaColumnas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
