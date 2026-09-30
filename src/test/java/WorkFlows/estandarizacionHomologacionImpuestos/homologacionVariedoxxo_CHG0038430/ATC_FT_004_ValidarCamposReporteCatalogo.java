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
import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Acceder al reporte descargado Reporte de Variedoxxo Catálogo y
* validar el contenido de la información
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* - MTC-FT-04-Validar los nuevos campos del Reporte de Variedoxxo Catálogo
* 
* PREREQUISITOS:
* - Acceso a los reportes previamente descargados
* - Acceso a la base de datos FCPVESIT
* 
* INSUMOS:
* - Reporte Variedoxxo (.xml) de Proceso 1
* 
* @author Angel Del Rio
* @date 20/Septiembre/2024
*/

public class ATC_FT_004_ValidarCamposReporteCatalogo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarCamposReporteCatalogo_test(HashMap<String, String> data) throws Exception {

		// Ruta del reporte XML
		final String BASE_PATH = System.getProperty("user.dir");
		String fileName = data.get("filename");
		String path = BASE_PATH + "\\Config\\Input\\Files\\HomologacionEstandarizacionImpuestos\\" + fileName;
		String fechainicial = data.get("fechainicial");
		String fechafinal = data.get("fechafinal");
		String selectFCPVESIT = MetodoGeneral.queryDBProceso1(null, fechainicial, fechafinal, null, null, null, null, null, null, null, null);
		
		// Variables para cada columna del reporte
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
		String impuestoArticuloIvaIeps = "";
		String tipoImp = "";
		String iva = "";
		String ieps = "";
		String ivaIeps = "";
		String impEst = "";
		String ivaImpEst = "";
		String vatRate = "";
		String factDesgrav = "";
		String precioSugSinImp = "";
		String precioSugConIva = "";
		String tipoDistribucion = "";
		String nombreCedis = "";
		String tipoDescuentoDistribucion = "";
		String estatus = "";
		String estacionalidad = "";
		
		Double ivaIepsCalculado;
		Double vatRateCalculado;
		Double factDesgravCalculado;
		Double precioSugSinImpCalculado;
		

		// ---------- PASO 1
		addStep("Ingresar a la base de datos FCPVESIT");
		utils.sql.SQLUtil dbFCPVESIT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPVESIT,
				GlobalVariables.DB_USER_FCPVESIT, GlobalVariables.DB_PASSWORD_FCPVESIT);
		testCase.addTextEvidenceCurrentStep("Se ingresó a la BD FCPVESIT correctamente");

		// ---------- PASO 2
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

		// ---------- PASO 3
		addStep("Validar que la información del reporte generado coincida con la información mostrada en la BD FCPVESIT");
		
		try {
			NodeList rowList = document.getElementsByTagName("Row");

			utils.sql.SQLResult resultadoSQL = dbFCPVESIT.executeQuery(selectFCPVESIT);
			
			testCase.addCodeEvidenceCurrentStep(selectFCPVESIT);

			String fechaValidacionBD;
			String fechaCicloBD;
			String codigoBarrasBD;
			String plazaCedisBD;
			String regionBD;
			String supergrupoBD;
			String categoriaBD;
			String subcategoriaBD;
			String descripcionBD;
			String storeOrdMultBD;
			String proveedorFaPadreBD;
			String proveedorFaHijaBD;
			String nombreProveedorBD;
			String presentacionBD;
			String costoSinIvaBD;
			String vatRegionJurisdiccionBD;
			String tipoImpBD;
			String impuestoArticuloIvaIepsBD;
			String ivaBD;
			String iepsBD;
			String ivaIepsBD;
			String impEstBD;
			String ivaImpEstBD;
			String vatRateBD;
			String factDesgravBD;
			String precioSugSinImpBD;
			String tipoDistribucionBD;
			String nombreCedisBD;
			String tipoDescuentoDistribucionBD;
			String estatusBD;
			String estacionalidadBD;

			int j = 7; // Inicializacion para tomar los datos de el reporte excel, en 7, ya que en esa row inician los datos
			for (int i = 0; i < resultadoSQL.getRowCount(); i++) {
				fechaValidacionBD = resultadoSQL.getData(i, "FECHA_APROBACION");
				fechaCicloBD = resultadoSQL.getData(i, "FECHA_CICLO");
				codigoBarrasBD = resultadoSQL.getData(i, "CODIGO_BARRAS");
				plazaCedisBD = resultadoSQL.getData(i, "PLAZA_CEDIS");
				regionBD = resultadoSQL.getData(i, "REGION");
				supergrupoBD = resultadoSQL.getData(i, "SUPERGRUPO_DESC");
				categoriaBD = resultadoSQL.getData(i, "CATEGORIA_DESC");
				subcategoriaBD = resultadoSQL.getData(i, "SUBCATEGORIA_DESC");
				descripcionBD = resultadoSQL.getData(i, "DESCRIPCION");
				storeOrdMultBD = resultadoSQL.getData(i, "STORE_ORD_MULT_DESC");
				proveedorFaPadreBD = resultadoSQL.getData(i, "SUPPLIER_PARENT");
				proveedorFaHijaBD = resultadoSQL.getData(i, "PROVEEDOR_FA_ID");
				nombreProveedorBD = resultadoSQL.getData(i, "PROVEEDOR_FA_DESC");
				presentacionBD = resultadoSQL.getData(i, "PRESENTACION_DESC");
				costoSinIvaBD = resultadoSQL.getData(i, "COSTO_SIN_IVA");
				vatRegionJurisdiccionBD = resultadoSQL.getData(i, "VAT_REGION");
				impuestoArticuloIvaIepsBD = resultadoSQL.getData(i, "VAT_CODE");
				tipoImpBD = resultadoSQL.getData(i, "VAT_TYPE");
				ivaBD = resultadoSQL.getData(i, "IVA");
				iepsBD = resultadoSQL.getData(i, "IEPS");
				ivaIepsBD = resultadoSQL.getData(i, "IVA_IEPS");
				impEstBD = resultadoSQL.getData(i, "IMPUESTO_ESTATAL");
				ivaImpEstBD = resultadoSQL.getData(i, "IVA_IMPUESTO_ESTATAL");	
				vatRateBD = resultadoSQL.getData(i, "VAT_RATE");
				factDesgravBD = resultadoSQL.getData(i, "FACT_DESGRAV");
				precioSugSinImpBD = resultadoSQL.getData(i, "PRECIO_SUG_SIN_IMP");

				tipoDistribucionBD = resultadoSQL.getData(i, "TIPO_DE_DISTRIBUCION");
				nombreCedisBD = resultadoSQL.getData(i, "NAME_CEDIS");
				tipoDescuentoDistribucionBD = resultadoSQL.getData(i, "TIPO_DSC_DISTRIBUCION");
				estatusBD = resultadoSQL.getData(i, "STATUS");
				estacionalidadBD = resultadoSQL.getData(i, "ESTACIONALIDAD");

				Node rowNode = rowList.item(j + i);
				if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
					Element rowElement = (Element) rowNode;
					NodeList cellList = rowElement.getElementsByTagName("Cell");
					for (int k = 0; k < cellList.getLength(); k++) {
						Node cellNode = cellList.item(k);
						if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
							Element cellElement = (Element) cellNode;
							// Extract data from cell
							NodeList dataList = cellElement.getElementsByTagName("Data");
							if (dataList.getLength() > 0) {
								Element dataElement = (Element) dataList.item(0);
								String cellValue = dataElement.getTextContent();
								if (cellValue.isEmpty()) {
									cellValue = null;
								}

								// Assign cell values to variables based on column index
								switch (k) {
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
									impuestoArticuloIvaIeps = cellValue;
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
								case 27: 
									precioSugConIva = cellValue;
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
					assertEquals(fechaValidacion, fechaValidacionBD, "No coinciden los valores entre la BD " + fechaValidacionBD + " y el reporte " + fechaValidacion);
					assertEquals(fechaCiclo, fechaCicloBD, "No coinciden los valores entre la BD " + fechaCicloBD + " y el reporte " + fechaCiclo);
					assertEquals(codigoBarras, codigoBarrasBD, "No coinciden los valores entre la BD " + codigoBarrasBD + " y el reporte " + codigoBarras); 
					assertEquals(plazaCedis, plazaCedisBD, "No coinciden los valores entre la BD " + plazaCedisBD + " y el reporte " + plazaCedis);
					assertEquals(region, regionBD, "No coinciden los valores entre la BD " + regionBD + " y el reporte " + region);
					assertEquals(supergrupo, supergrupoBD, "No coinciden los valores entre la BD " + supergrupoBD + " y el reporte " + supergrupo);
					assertEquals(categoria, categoriaBD, "No coinciden los valores entre la BD " + categoriaBD + " y el reporte " + categoria);
					assertEquals(subcategoria, subcategoriaBD, "No coinciden los valores entre la BD " + subcategoriaBD + " y el reporte " + subcategoria);
					assertEquals(descripcion, descripcionBD, "No coinciden los valores entre la BD " + descripcionBD + " y el reporte " + descripcion);
					assertEquals(storeOrdMult, storeOrdMultBD, "No coinciden los valores entre la BD " + storeOrdMultBD + " y el reporte " + storeOrdMult);
					assertEquals(proveedorFaPadre, proveedorFaPadreBD, "No coinciden los valores entre la BD " + proveedorFaPadreBD + " y el reporte " + proveedorFaPadre);
					assertEquals(proveedorFaHija, proveedorFaHijaBD, "No coinciden los valores entre la BD " + proveedorFaHijaBD + " y el reporte " + proveedorFaHija);
					assertEquals(nombreProveedor, nombreProveedorBD, "No coinciden los valores entre la BD " + nombreProveedorBD + " y el reporte " + nombreProveedor);
					assertEquals(presentacion, presentacionBD, "No coinciden los valores entre la BD " + presentacionBD + " y el reporte " + presentacion);
					assertEquals(MetodoGeneral.toDouble(costoSinIva), MetodoGeneral.toDouble(costoSinIvaBD), "No coinciden los valores entre la BD " + costoSinIvaBD + " y el reporte " + costoSinIva);
					assertEquals(vatRegionJurisdiccion, vatRegionJurisdiccionBD, "No coinciden los valores entre la BD " + vatRegionJurisdiccionBD + " y el reporte " + vatRegionJurisdiccion);
					assertEquals(impuestoArticuloIvaIeps, impuestoArticuloIvaIepsBD, "No coinciden los valores entre la BD " + impuestoArticuloIvaIepsBD + " y el reporte " + impuestoArticuloIvaIeps);
					assertEquals(tipoImp, tipoImpBD, "No coinciden los valores entre la BD " + tipoImpBD + " y el reporte " + tipoImp);
					assertEquals(MetodoGeneral.toDouble(iva), MetodoGeneral.toDouble(ivaBD), "No coinciden los valores entre la BD " + ivaBD + " y el reporte " + iva);
					assertEquals(MetodoGeneral.toDouble(ieps), MetodoGeneral.toDouble(iepsBD), "No coinciden los valores entre la BD " + iepsBD + " y el reporte " + ieps);
					assertEquals(MetodoGeneral.toDouble(ivaIeps), MetodoGeneral.toDouble(ivaIepsBD), "No coinciden los valores entre la BD " + ivaIepsBD + " y el reporte " + ivaIeps);
					assertEquals(MetodoGeneral.toDouble(impEst), MetodoGeneral.toDouble(impEstBD), "No coinciden los valores entre la BD " + impEstBD + " y el reporte " + impEst);
					assertEquals(MetodoGeneral.toDouble(ivaImpEst), MetodoGeneral.toDouble(ivaImpEstBD), "No coinciden los valores entre la BD " + ivaImpEstBD + " y el reporte " + ivaImpEst);
					assertEquals(MetodoGeneral.toDouble(vatRate), MetodoGeneral.toDouble(vatRateBD), "No coinciden los valores entre la BD " + vatRateBD + " y el reporte " + vatRate);
					assertEquals(MetodoGeneral.toDouble(factDesgrav), MetodoGeneral.toDouble(factDesgravBD), "No coinciden los valores entre la BD " + factDesgravBD + " y el reporte " + factDesgrav);
					assertEquals(MetodoGeneral.toDouble(precioSugSinImp), MetodoGeneral.toDouble(precioSugSinImpBD), "No coinciden los valores entre la BD " + precioSugSinImpBD + " y el reporte " + precioSugSinImp);

					assertEquals(tipoDistribucion, tipoDistribucionBD, "No coinciden los valores entre la BD " + tipoDistribucionBD + " y el reporte " + tipoDistribucion);
					assertEquals(nombreCedis, nombreCedisBD, "No coinciden los valores entre la BD " + nombreCedisBD + " y el reporte " + nombreCedis);
					assertEquals(tipoDescuentoDistribucion, tipoDescuentoDistribucionBD, "No coinciden los valores entre la BD " + tipoDescuentoDistribucionBD + " y el reporte " + tipoDescuentoDistribucion);
					assertEquals(estatus, estatusBD, "No coinciden los valores entre la BD " + estatusBD + " y el reporte " + estatus);
					assertEquals(estacionalidad, estacionalidadBD, "No coinciden los valores entre la BD " + estacionalidadBD + " y el reporte " + estacionalidad);
					
					// Cálculo de las columnas de impuestos
					
					ivaIepsCalculado = MetodoGeneral.toDouble(iva) * MetodoGeneral.toDouble(ieps);
					vatRateCalculado = MetodoGeneral.toDouble(impEst) + MetodoGeneral.toDouble(ivaImpEst)
							+ MetodoGeneral.toDouble(iva) + MetodoGeneral.toDouble(ieps) + MetodoGeneral.toDouble(ivaIeps);
					
					if (MetodoGeneral.toDouble(vatRate) == 0) {
						factDesgravCalculado = 0.0;
					} else {
						factDesgravCalculado = MetodoGeneral.toDouble(vatRate) + 1;
					}
					
					if (factDesgrav == null) {
						precioSugSinImpCalculado = 0.0;
					} else if (factDesgrav.equals("0")) {
						precioSugSinImpCalculado = MetodoGeneral.toDouble(precioSugConIva);
					} else {
						precioSugSinImpCalculado = MetodoGeneral.toDouble(precioSugConIva)
								/ MetodoGeneral.toDouble(factDesgrav);
					}
					
					assertEquals(MetodoGeneral.toDouble(ivaIeps), ivaIepsCalculado);
					assertEquals(MetodoGeneral.toDouble(vatRate), vatRateCalculado);
					assertEquals(MetodoGeneral.toDouble(factDesgrav), factDesgravCalculado);
					assertEquals(MetodoGeneral.toDouble(precioSugSinImp), precioSugSinImpCalculado, 0.01);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		testCase.addTextEvidenceCurrentStep("Se validó que todos los registros coinciden con la BD FCPVESIT");
		dbFCPVESIT.close();
		
		// ---------- PASO 4
		addStep("Validar que los nuevos campos cumplan las siguientes reglas:\r\n"
				+ "\r\n"
				+ "IVA IEPS: Se obtiene multiplicando el IVA por el IEPS\r\n"
				+ "VAT RATE: Se obtiene sumando los valores de impuesto estatal,  IVA estatal, IVA, IEPS y IVA IEPS\r\n"
				+ "FACT DESGRAV: Se obtiene sumándole un 1 al VatRate\r\n"
				+ "PRECIO SUG SIN IMP: Se obtiene dividiendo el precio sugerido con impuesto del artículo entre el factor de Desgrave");
		
		testCase.addTextEvidenceCurrentStep("Se validó que todos los registros cumplen con las reglas especificadas");
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "1. Acceso a los reportes previamente descargados 2. Acceso a la base de datos FCPVESIT";
	}

	@Override
	public String setTestDescription() {
		return "Acceder al reporte descargado Reporte de Variedoxxo Catálogo y validar el contenido de la información";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarCamposReporteCatalogo_test";
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
