package WorkFlows.estandarizacionHomologacionImpuestos.homologacionVariedoxxo_CHG0038430;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import WorkFlows.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Acceder al reporte descargado Reporte de Variedoxxo Catálogo y
* validar el orden de las columnas.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* - MTC-FT-01-Validar el orden de columnas del Reporte de Variedoxxo Catálogo
* 
* PREREQUISITOS:
* - Acceso a los reportes previamente descargados
* 
* INSUMOS:
* - Reporte Variedoxxo (.xml) de Proceso 1
* 
* @author Angel Del Rio
* @date 17/Septiembre/2024
*/

public class ATC_FT_001_ValidarColumnasReporteCatalogo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarColumnasReporteCatalogo_test(HashMap<String, String> data) throws Exception {

		// Ruta del reporte XML
		final String BASE_PATH = System.getProperty("user.dir");
		String fileName = data.get("filename");
		String path = BASE_PATH + "\\Config\\Input\\Files\\HomologacionEstandarizacionImpuestos\\" + fileName;

		// Variables for each column
		String fechaValidacion = "";
		String fechaCiclo = "";
		String codigoBarras = "";
		String plazaCedis = "";
		String region = "";
		String supergrupo = "";
		String categoria = "";
		String subcategoria = "";
		String descripcion = "";
		String storeOrdMult = "";
		String proveedorFaPadre = "";
		String proveedorFaHija = "";
		String nombreProveedor = "";
		String presentacion = "";
		String costoSinIva = "";
		String vatRegionJurisdiccion = "";
		String impuestoArticuloIVAIEPS = "";
		String tipoImp = "";
		String iva = "";
		String ieps = "";
		String ivaIeps = "";
		String impEst = "";
		String ivaImpEst = "";
		String vatRate = "";
		String factDesgrav = "";
		String precioSugSinImp = "";
		String tipoDistribucion = "";
		String nombreCedis = "";
		String tipoDescuentoDistribucion = "";
		String estatus = "";
		String estacionalidad = "";

		// ---------- PASO 1
		addStep("Ingresar al Reporte de Variedoxxo Catálogo generado");

		Document document = null;
		try {
			// Initialize DocumentBuilderFactory and DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Parse the XML file
			document = builder.parse(new File(path));
			document.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		testCase.addTextEvidenceCurrentStep("Archivo validado: \n" + path);

		// ---------- PASO 2
		addStep("Validar que sus columnas sigan el siguiente orden:\r\n" + "FECHA DE VALIDACIÓN\r\n"
				+ "FECHA DEL CICLO\r\n" + "CÓDIGO DE BARRAS\r\n" + "PLAZA/CEDIS\r\n" + "REGION\r\n" + "SUPERGRUPO\r\n"
				+ "CATEGORÍA\r\n" + "SUBCATEGORÍA\r\n" + "DESCRIPCIÓN\r\n" + "STORE ORD MULT (SOM)\r\n"
				+ "PROVEEDOR (FA PADRE)\r\n" + "PROVEEDOR (FA HIJA)\r\n" + "NOMBRE DE PROVEEDOR\r\n"
				+ "PRESENTACIÓN\r\n" + "COSTO SIN IVA\r\n" + "VAT REGION/JURISDICCIÓN\r\n"
				+ "Impuesto Articulo (IVA + IEPS)\r\n" + "Tipo Imp.\r\n" + "IVA\r\n" + "IEPS\r\n" + "IVA IEPS\r\n"
				+ "IMP EST\r\n" + "IVA IMP EST\r\n" + "VAT RATE\r\n" + "FACT DESGRAV\r\n" + "PRECIO SUG SIN IMP \r\n"
				+ "...\r\n" + "TIPO DE DISTRIBUCIÓN\r\n" + "NOMBRE CEDIS\r\n" + "TIPO DESCUENTO DISTRIBUCIÓN\r\n"
				+ "ESTATUS\r\n" + "ESTACIONALIDAD");

		try {
			// Get all rows
			NodeList rowList = document.getElementsByTagName("Row");

			// Columna 6: Encabezados
			Node rowNode = rowList.item(6);
			if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
				Element rowElement = (Element) rowNode;

				// Get all cells in this row
				NodeList cellList = rowElement.getElementsByTagName("Cell");

				for (int j = 0; j < cellList.getLength(); j++) {
					Node cellNode = cellList.item(j);
					if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
						Element cellElement = (Element) cellNode;
						// Extract data from cell
						NodeList dataList = cellElement.getElementsByTagName("Data");
						if (dataList.getLength() > 0) {
							Element dataElement = (Element) dataList.item(0);
							String cellValue = dataElement.getTextContent();

							// Assign cell values to variables based on column index
							switch (j) {
							case 0:
								fechaValidacion = cellValue;
								break;
							case 1:
								fechaCiclo = cellValue;
								break;
							case 2:
								codigoBarras = cellValue;
								break;
							case 3:
								plazaCedis = cellValue;
								break;
							case 4:
								region = cellValue;
								break;
							case 5:
								supergrupo = cellValue;
								break;
							case 6:
								categoria = cellValue;
								break;
							case 7:
								subcategoria = cellValue;
								break;
							case 8:
								descripcion = cellValue;
								break;
							case 9:
								storeOrdMult = cellValue;
								break;
							case 10:
								proveedorFaPadre = cellValue;
								break;
							case 11:
								proveedorFaHija = cellValue;
								break;
							case 12:
								nombreProveedor = cellValue;
								break;
							case 13:
								presentacion = cellValue;
								break;
							case 14:
								costoSinIva = cellValue;
								break;
							case 15:
								vatRegionJurisdiccion = cellValue;
								break;
							case 16:
								impuestoArticuloIVAIEPS = cellValue;
								break;
							case 17:
								tipoImp = cellValue;
								break;
							case 18:
								iva = cellValue;
								break;
							case 19:
								ieps = cellValue;
								break;
							case 20:
								ivaIeps = cellValue;
								break;
							case 21:
								impEst = cellValue;
								break;
							case 22:
								ivaImpEst = cellValue;
								break;
							case 23:
								vatRate = cellValue;
								break;
							case 24:
								factDesgrav = cellValue;
								break;
							case 25:
								precioSugSinImp = cellValue;
								break;
							case 74:
								tipoDistribucion = cellValue;
								break;
							case 75:
								nombreCedis = cellValue;
								break;
							case 76:
								tipoDescuentoDistribucion = cellValue;
								break;
							case 77:
								estatus = cellValue;
								break;
							case 78:
								estacionalidad = cellValue;
								break;
							}
						}
					}
				}

				// Print out all variables (or perform validation checks as needed)
				System.out.println("Fecha de Validación: " + fechaValidacion);
				System.out.println("Fecha del Ciclo: " + fechaCiclo);
				System.out.println("Código de Barras: " + codigoBarras);
				System.out.println("Plaza/CEDIS: " + plazaCedis);
				System.out.println("Región: " + region);
				System.out.println("Supergrupo: " + supergrupo);
				System.out.println("Categoría: " + categoria);
				System.out.println("Subcategoría: " + subcategoria);
				System.out.println("Descripción: " + descripcion);
				System.out.println("Store Ord Mult (SOM): " + storeOrdMult);
				System.out.println("Proveedor (FA Padre): " + proveedorFaPadre);
				System.out.println("Proveedor (FA Hija): " + proveedorFaHija);
				System.out.println("Nombre de Proveedor: " + nombreProveedor);
				System.out.println("Presentación: " + presentacion);
				System.out.println("Costo Sin IVA: " + costoSinIva);
				System.out.println("VAT Region/Jurisdicción: " + vatRegionJurisdiccion);
				System.out.println("Impuesto Articulo (IVA + IEPS): " + impuestoArticuloIVAIEPS);
				System.out.println("Tipo Imp: " + tipoImp);
				System.out.println("IVA: " + iva);
				System.out.println("IEPS: " + ieps);
				System.out.println("IVA IEPS: " + ivaIeps);
				System.out.println("IMP EST: " + impEst);
				System.out.println("IVA IMP EST: " + ivaImpEst);
				System.out.println("VAT Rate: " + vatRate);
				System.out.println("Fact Desgrav: " + factDesgrav);
				System.out.println("Precio SUG Sin IMP: " + precioSugSinImp);

				System.out.println("Tipo de Distribución: " + tipoDistribucion);
				System.out.println("Nombre CEDIS: " + nombreCedis);
				System.out.println("Tipo Descuento Distribución: " + tipoDescuentoDistribucion);
				System.out.println("Estatus: " + estatus);
				System.out.println("Estacionalidad: " + estacionalidad);

				String columnas = fechaValidacion + ", " + fechaCiclo + ", " + codigoBarras + ", " + plazaCedis + ", "
						+ region + ", " + supergrupo + ", " + categoria + ", " + subcategoria + ", " + descripcion
						+ ", " + storeOrdMult + ", " + proveedorFaPadre + ", " + proveedorFaHija + ", "
						+ nombreProveedor + ", " + presentacion + ", " + costoSinIva + ", " + vatRegionJurisdiccion
						+ ", " + impuestoArticuloIVAIEPS + ", " + tipoImp + ", " + iva + ", " + ieps + ", " + ivaIeps
						+ ", " + impEst + ", " + ivaImpEst + ", " + vatRate + ", " + factDesgrav + ", "
						+ precioSugSinImp + ", " + tipoDistribucion + ", " + nombreCedis + ", "
						+ tipoDescuentoDistribucion + ", " + estatus + ", " + estacionalidad;
				testCase.addTextEvidenceCurrentStep("Columnas del reporte: \n" + columnas);

				assertEquals(fechaValidacion.trim(), "FECHA DE VALIDACIÓN");
				assertEquals(fechaCiclo.trim(), "FECHA DEL CICLO");
				assertEquals(codigoBarras.trim(), "CÓDIGO DE BARRAS");
				assertEquals(plazaCedis.trim(), "PLAZA/CEDIS");
				assertEquals(region.trim(), "REGION");
				assertEquals(supergrupo.trim(), "SUPERGRUPO");
				assertEquals(categoria.trim(), "CATEGORÍA");
				assertEquals(subcategoria.trim(), "SUBCATEGORÍA");
				assertEquals(descripcion.trim(), "DESCRIPCIÓN");
				assertEquals(storeOrdMult.trim(), "STORE ORD MULT (SOM)");
				assertEquals(proveedorFaPadre.trim(), "PROVEEDOR (FA PADRE)");
				assertEquals(proveedorFaHija.trim(), "PROVEEDOR (FA HIJA)");
				assertEquals(nombreProveedor.trim(), "NOMBRE DE PROVEEDOR");
				assertEquals(presentacion.trim(), "PRESENTACIÓN");
				assertEquals(costoSinIva.trim(), "COSTO SIN IVA");
				assertEquals(vatRegionJurisdiccion.trim(), "VAT REGION/JURISDICCIÓN");
				assertEquals(impuestoArticuloIVAIEPS.trim(), "Impuesto Articulo (IVA + IEPS)");
				assertEquals(tipoImp.trim(), "Tipo Imp.");
				assertEquals(iva.trim(), "IVA");
				assertEquals(ieps.trim(), "IEPS");
				assertEquals(ivaIeps.trim(), "IVA IEPS");
				assertEquals(impEst.trim(), "IMP EST");
				assertEquals(ivaImpEst.trim(), "IVA IMP EST");
				assertEquals(vatRate.trim(), "VAT RATE");
				assertEquals(factDesgrav.trim(), "FACT DESGRAV");
				assertEquals(precioSugSinImp.trim(), "PRECIO SUG SIN IMP");

				assertEquals(tipoDistribucion.trim(), "TIPO DE DISTRIBUCIÓN");
				assertEquals(nombreCedis.trim(), "NOMBRE CEDIS");
				assertEquals(tipoDescuentoDistribucion.trim(), "TIPO DESCUENTO DISTRIBUCIÓN");
				assertEquals(estatus.trim(), "ESTATUS");
				assertEquals(estacionalidad.trim(), "ESTACIONALIDAD");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso a los reportes previamente descargados";
	}

	@Override
	public String setTestDescription() {
		return "Acceder al reporte descargado Reporte de Variedoxxo Catálogo y validar el orden de las columnas.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarColumnasReporteCatalogo_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

}