package WM.EvolucionRetiroSinTarjeta.migraciones.om.tablas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.TestCase;
import EBS.om.MetodosExtra;
import utils.selenium.SeleniumUtil;

//No funciona si las tablas no tienen id's
//Tambi�n se puede implementar regla mediante el constructor cuando hay reglas de transofaci�n en el id con el objeto customEqualsId
//Considerar que se validan menos columnas de todas las de la tabla
//Considerar que cualquiera de las tablas pudiera tener m�s o menos datos
//TODO validar cuando el id de origen no se encuentra en el destino (se tiene que implementar dentro del m�todo de CuestomEquals)
//TODO validar cuando el id de destino no se encuentra en el origen (se puede hacer manual despu�s de implementar CustomEquals)
public class TableCompare {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;


	private CustomEquals<String> customEqualsId;

	public TableCompare() {
		this(null);
	}
	
	public TableCompare(CustomEquals<String> customEqualsId) {
		this.customEqualsId = customEqualsId;
	}

	public String[] getRowById(String valorIdOrigen, Tabla tabla) {
			
		String nombreColumnaIdDestino = tabla.getId();
		
		for (String[] filaDestinoActual : tabla.getFilas()) {
			String valorIdDestino = filaDestinoActual[tabla.getColumnas().indexOf(nombreColumnaIdDestino)];
			if (
					(customEqualsId==null && valorIdOrigen.equals(valorIdDestino))
					||
					(customEqualsId!=null && customEqualsId.equals( valorIdOrigen,valorIdDestino))
			) {
				return filaDestinoActual;
			}
		}

		return null; //Se retorna null, si el id de origen no est� en el destino
	}
	
	public List<String[]> compareTables(Tabla tablaOrigen, Tabla tablaDestino){
		return compareTables(tablaOrigen, tablaDestino, null);
	}
	
	public List<String[]> compareTables(Tabla tablaOrigen, Tabla tablaDestino, CustomEquals<Map<String, String>> customEqualsMap){
		List<String[]> filasErroneas = new ArrayList<>();
		
		String nombreColumnaIdOrigen = tablaOrigen.getId();
		
		for (String[] filaOrigenActual : tablaOrigen.getFilas()) {
			String valorIdOrigen = filaOrigenActual[tablaOrigen.getColumnas().indexOf(nombreColumnaIdOrigen)];
			String[] filaDestinoActual = getRowById(valorIdOrigen, tablaDestino);
			if (filaDestinoActual==null) {
				filasErroneas.add(filaOrigenActual);
			} else {
				//Convertir la informaci�n a Map para su uso m�s f�cil
				Map<String, String> dataFilaOrigen = rowTableToMap(tablaOrigen, filaOrigenActual);
				Map<String, String> dataFilaDestino = rowTableToMap(tablaDestino, filaDestinoActual);
				
				//Se elimina comparar el id porque previamente ya se hizo la tarea de buscar cu�l es la fila correspondiente mediante los id's
				dataFilaOrigen.remove(tablaOrigen.getId());
				dataFilaDestino.remove(tablaDestino.getId());
				
				if (
						(customEqualsMap==null && !dataFilaOrigen.equals(dataFilaDestino))
						||
						(customEqualsMap!=null && !customEqualsMap.equals(dataFilaOrigen, dataFilaDestino))
						
				) {
				//	testCase.addTextEvidenceCurrentStep("Filas que no coinciden ");
					filasErroneas.add(filaDestinoActual);
					
				}
				//else {	testCase.addTextEvidenceCurrentStep("Filas coinciden ");}
			}
			
		}

		return filasErroneas;
	}
	
	private Map<String, String> rowTableToMap(Tabla tabla, String[] row){
		Map<String, String> data = new HashMap<>();
		List<String> columnas = tabla.getColumnas();
		
		for (int i = 0; i < columnas.size(); i++) {
			data.put(columnas.get(i), row[i]);
		}
		
		return data;
	}

	public static void main(String[] args) {

		//Llenado de datos
		
		// Tabla1
		// ID
		String id = "ID";

		// Columnas
		ArrayList<String> columnas = new ArrayList<>();
		columnas.add("ID");
		columnas.add("Columna1");
		columnas.add("Columna2");

		// Filas
		List<String[]> filas = new ArrayList<>();

		String[] fila1 = new String[3];
		fila1[0] = "1";
		fila1[1] = "a1";
		fila1[2] = "b1";

		String[] fila2 = new String[3];
		fila2[0] = "2";
		fila2[1] = "a2";
		fila2[2] = "b2";

		filas.add(fila1);
		filas.add(fila2);

		Tabla tabla1 = new Tabla(id, columnas, filas);

		// ID
		String idTabla2 = "NUEVO_ID";

		// Columnas
		ArrayList<String> columnasTabla2 = new ArrayList<>();
		columnasTabla2.add("Columna1");
		columnasTabla2.add("NUEVO_ID");
		columnasTabla2.add("Columna2");

		// Filas
		List<String[]> filasTabla2 = new ArrayList<>();

		String[] fila1Tabla2 = new String[3];
		fila1Tabla2[0] = "desc1";
		fila1Tabla2[1] = "1";
		fila1Tabla2[2] = "b1";

		String[] fila2Tabla2 = new String[3];
		fila2Tabla2[0] = "a2";
		fila2Tabla2[1] = "2";
		fila2Tabla2[2] = "b2";

		filasTabla2.add(fila1Tabla2);
		filasTabla2.add(fila2Tabla2);

		Tabla tabla2 = new Tabla(idTabla2, columnasTabla2, filasTabla2);


		/////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////
		//Empieza comprobaci�n
		
		
		TableCompare tc = new TableCompare();
		
		//List<String[]> filasErroneas = tc.compareTables(tabla1, tabla2, null);
		
		CustomEquals<Map<String, String>> customEquals = new CustomEquals<Map<String,String>>() {
			
			@Override
			public boolean equals(Map<String, String> val1, Map<String, String> val2) {
				
				String valorCol1 = val1.get("Columna1");
				String valorCol2 = val2.get("Columna1");
				
				if (valorCol1.equals("a1")) {
					valorCol1 = "desc1";
				}
				
				System.out.println("valorCol1 " + valorCol1);
				System.out.println("valorCol2 " + valorCol2);
				
				//columna2
				String valorCol3 = val1.get("Columna2");
				String valorCol4 = val2.get("Columna2");
				
				System.out.println("valorCol3 " + valorCol3);
				System.out.println("valorCol4 " + valorCol4);
				
				if (valorCol1.equals(valorCol2) && valorCol3.equals(valorCol4)) {
					return true;
				}
				
				return false;
			}
		};
		
		List<String[]> filasErroneas = tc.compareTables(tabla1, tabla2, customEquals);
		
		List<String[]> filasDestino = tabla2.getFilas();
		boolean laEncotnro = false;
		for (String[] filaActual : filasDestino) {
			String[] filaBuscar = {"a3","3","b4"};
			if (filaActual.equals(filaBuscar)) {
				laEncotnro = true;
			}
		}
		if(laEncotnro==false) {
			//Poner en la evidencia que no encontr� la fila
		}
		
		
		
		
		
		
		
		filasErroneas.forEach(a -> System.out.println(Arrays.toString(a)));
		
//		List<String[]> filasErroneas = tc.compareTables(tabla1, tabla2, (m1, m2) -> {
//			System.out.println("0" + m1.get("Columna1"));
//			System.out.println(m2.get("Columna1"));
//			
//			if (m2.get("Columna1").equals("0" + m1.get("Columna1"))) {
//				return true;
//			}
//			
//			return false;
//		});
		
//		filasErroneas.forEach(a -> System.out.println(Arrays.toString(a)));
		
	}

	/**
	 * Constructor
	 ******************************************************************************************/

	public TableCompare(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);

	}

}
