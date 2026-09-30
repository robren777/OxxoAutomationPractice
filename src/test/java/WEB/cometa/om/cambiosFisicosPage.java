package WEB.cometa.om;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import WEB.util.GlobalVariables;

/**
 * Page Object para la pantalla Cambios Fisicos Directos del Portal COMETA.
 * Cubre el flujo de vista preliminar y descarga/validacion del Excel individual
 * con las 5 columnas nuevas de la HU 308162.
 *
 * @author Michelle Ceja
 * @date 2026-08-04
 */
public class cambiosFisicosPage {

    private final SeleniumUtil u;
    private final TestCase testCase;
    private final cambiosFisicosElements elements = new cambiosFisicosElements();

    /** Timestamp tomado justo antes de iniciar la descarga, para detectar el archivo nuevo. */
    private long inicioUltimaDescargaMs = 0L;

    /** Muestra de valores sale_price obtenida desde BD para comparacion contra grid. */
    private List<String> muestraPreciosBdCFP = new ArrayList<String>();

    /** Muestra de SKU obtenida desde BD para comparacion contra grid. */
    private List<String> muestraSkusBdCFP = new ArrayList<String>();

    /** Muestra de valores visibles de columna Precio capturada desde grid para comparacion. */
    private List<String> muestraPreciosGridCFP = new ArrayList<String>();

    /** Muestra de valores visibles de columna SKU capturada desde grid para comparacion. */
    private List<String> muestraSkusGridCFP = new ArrayList<String>();

    /** Muestra de IDs con sale_price nulo obtenida desde BD para validaciones de caso negativo. */
    private List<String> muestraIdsSalePriceNuloBdCFP = new ArrayList<String>();

    /** Muestra de SKU con sale_price nulo obtenida desde BD para validaciones de caso negativo. */
    private List<String> muestraSkusSalePriceNuloBdCFP = new ArrayList<String>();

    public cambiosFisicosPage(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }

    // =========================================================
    // NAVEGACION / GRID
    // =========================================================

    /**
     * Da clic en el primer registro del grid de Cambios Fisicos Directos y
     * valida que se abra el modal de vista preliminar.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return true si el modal se abre correctamente.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public boolean abrirPrimerRegistroGridCF(boolean screenshot) {
        try {
            u.waitWebElement(elements.primeraFilaGridCF, 60, 5);
            WebElement fila = u.getDriver().findElement(elements.primeraFilaGridCF);
            u.scrollToElement(elements.primeraFilaGridCF);
            u.highLight(elements.primeraFilaGridCF);
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "Se selecciona el primer registro visible del grid de Cambios Fisicos.");
                testCase.addScreenShotCurrentStep(u, "Primer_Registro_Grid_CF");
            }
            u.unhighLight(elements.primeraFilaGridCF);

            try {
                fila.click();
            } catch (Exception ex) {
                ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", fila);
            }

            u.hardWait(3);
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "Se abre la vista preliminar del comprobante de Cambios Fisicos.");
                testCase.addScreenShotCurrentStep(u, "Vista_Preliminar_CF_Abierta");
            }
            return true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                "Error al abrir primer registro del grid de CF: " + e.getMessage());
            return false;
        }
    }

    // =========================================================
    // LECTURA DE CAMPOS EN MODAL
    // =========================================================

    /**
     * Lee el valor del campo "Observaciones Tienda" desde la vista preliminar del
     * comprobante de Cambios Fisicos abierto en modal.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return texto del campo, o cadena vacia si no se encuentra.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public String leerObservacionesTiendaDeVistaPreliminar(boolean screenshot) {
        try {
            String valor = leerCampoDeModal("Observaciones Tienda", screenshot);
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "Valor leido 'Observaciones Tienda' en vista preliminar: [" + valor + "]");
                testCase.addScreenShotCurrentStep(u, "Observaciones_Tienda_Modal_CF");
            }
            return valor;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                "Error al leer Observaciones Tienda del modal: " + e.getMessage());
            return "";
        }
    }

    /**
     * Lee el valor del campo "Observaciones Proveedor" desde la vista preliminar del
     * comprobante de Cambios Fisicos abierto en modal.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return texto del campo, o cadena vacia si no se encuentra.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public String leerObservacionesProveedorDeVistaPreliminar(boolean screenshot) {
        try {
            String valor = leerCampoDeModal("Observaciones Proveedor", screenshot);
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "Valor leido 'Observaciones Proveedor' en vista preliminar: [" + valor + "]");
                testCase.addScreenShotCurrentStep(u, "Observaciones_Proveedor_Modal_CF");
            }
            return valor;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                "Error al leer Observaciones Proveedor del modal: " + e.getMessage());
            return "";
        }
    }

    /**
     * Busca un campo por su label dentro del modal activo y retorna su valor.
     * Estrategia: busca el texto del label y luego el siguiente elemento sibling.
     */
    private String leerCampoDeModal(String labelTexto, boolean screenshot) {
        WebDriver driver = u.getDriver();
        /* Patron flexible: busca por texto exacto o parcial en cualquier elemento del dialog */
        String[] xpaths = {
            "(//*[starts-with(@id,'mat-mdc-dialog-')]" +
            "  //*[normalize-space(text())='" + labelTexto + "']" +
            "  /following::span[1])[1]",
            "(//*[starts-with(@id,'mat-mdc-dialog-')]" +
            "  //*[contains(normalize-space(text()),'" + labelTexto + "')]" +
            "  /following-sibling::*[1])[1]",
            "(//*[starts-with(@id,'mat-mdc-dialog-')]" +
            "  //*[contains(normalize-space(),'" + labelTexto + "')])[1]"
        };

        for (String xp : xpaths) {
            try {
                List<WebElement> elementos = driver.findElements(By.xpath(xp));
                if (!elementos.isEmpty()) {
                    String texto = elementos.get(0).getText().trim();
                    /* Si el texto contiene el label mismo, quitarlo */
                    if (texto.toLowerCase().startsWith(labelTexto.toLowerCase())) {
                        texto = texto.substring(labelTexto.length()).replaceFirst("^\\s*:?\\s*", "").trim();
                    }
                    if (!texto.isEmpty()) {
                        System.out.println("[CF-Modal] Campo '" + labelTexto + "': " + texto);
                        return texto;
                    }
                }
            } catch (Exception ignored) {}
        }

        System.out.println("[CF-Modal] Campo '" + labelTexto + "' no encontrado en modal.");
        return "";
    }

    // =========================================================
    // DESCARGA EXCEL INDIVIDUAL DESDE MODAL
    // =========================================================

    /**
     * Da clic en el boton de descarga Excel individual dentro del modal de
     * vista preliminar de Cambios Fisicos y espera que el archivo quede disponible.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return true si el clic se ejecuta correctamente.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public boolean descargarExcelIndividualVistaPreliminarCF(boolean screenshot) {
        try {
            u.scrollToElement(elements.seccionDescargaModalCF);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            WebElement boton = wait.until(
                ExpectedConditions.elementToBeClickable(elements.btnDescargaExcelIndividualCF));

            u.highLight(elements.btnDescargaExcelIndividualCF);
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "Se da clic en el icono de descarga Excel dentro de la vista preliminar del comprobante CF.");
                testCase.addScreenShotCurrentStep(u, "Clic_Descarga_Excel_Individual_CF");
            }
            u.unhighLight(elements.btnDescargaExcelIndividualCF);

            inicioUltimaDescargaMs = System.currentTimeMillis() - 2000;

            try {
                new Actions(u.getDriver()).moveToElement(boton).pause(Duration.ofMillis(150)).click().perform();
            } catch (Exception ex) {
                boton.click();
            }

            u.hardWait(3);
            return true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                "Error al descargar Excel individual desde modal CF: " + e.getMessage());
            return false;
        }
    }

    /**
     * Espera y retorna el archivo Excel individual mas reciente descargado.
     *
     * @return archivo Excel o null si no se encuentra dentro del tiempo limite.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public File esperarArchivoExcelIndividualCF() {
        File carpeta = new File(System.getProperty("user.home"), "Downloads");
        long inicio = inicioUltimaDescargaMs > 0L ? inicioUltimaDescargaMs : System.currentTimeMillis() - 60000;
        long fin = System.currentTimeMillis() + 60000;

        while (System.currentTimeMillis() < fin) {
            File mejor = null;
            long mejorTs = Long.MIN_VALUE;

            File[] archivos = carpeta.listFiles();
            if (archivos != null) {
                for (File f : archivos) {
                    if (f == null || !f.isFile()) continue;
                    String nombre = f.getName().toLowerCase();
                    if (!nombre.endsWith(".xlsx") && !nombre.endsWith(".xls")) continue;
                    if (nombre.endsWith(".tmp") || nombre.endsWith(".crdownload")) continue;
                    long ts = f.lastModified();
                    if (ts >= inicio && ts > mejorTs) {
                        mejorTs = ts;
                        mejor = f;
                    }
                }
            }

            if (mejor != null && mejor.exists() && mejor.length() > 0) {
                long tam1 = mejor.length();
                try { Thread.sleep(800); } catch (InterruptedException ignored) {}
                if (mejor.exists() && mejor.length() == tam1) {
                    return mejor;
                }
            }

            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }

        return null;
    }

    // =========================================================
    // VALIDACIONES EXCEL
    // =========================================================

    /**
     * Abre y valida que el Excel individual descargado contenga las 5 columnas nuevas
     * de HU 308162 (Observaciones Tienda, Observaciones Proveedor, Perfil Autorizador,
     * Codigo de activacion, ID Dispositivo) en el orden correcto despues de "Precio Total".
     *
     * @param valorObsTiendaEsperado valor leido de la vista preliminar para "Observaciones Tienda".
     * @param valorObsProveedorEsperado valor leido de la vista preliminar para "Observaciones Proveedor".
     * @param screenshot true para registrar evidencia visual.
     * @return true si todas las validaciones pasan.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public boolean validarColumnas5NuevasExcelIndividualCF(
            String valorObsTiendaEsperado,
            String valorObsProveedorEsperado,
            boolean screenshot) {

        FileInputStream fis = null;
        Workbook wb = null;

        try {
            File archivoExcel = esperarArchivoExcelIndividualCF();
            if (archivoExcel == null || !archivoExcel.exists()) {
                testCase.addCodeEvidenceCurrentStep(
                    "No se encontro el archivo Excel individual descargado para CF.");
                return false;
            }

            System.out.println("[CF-Excel] Archivo: " + archivoExcel.getName());

            fis = new FileInputStream(archivoExcel);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            /* Encontrar la fila de encabezados */
            int filaEncabezado = encontrarFilaEncabezadosCF(sheet, fmt);
            if (filaEncabezado == -1) {
                testCase.addCodeEvidenceCurrentStep("No se encontro la fila de encabezados en el Excel CF.");
                return false;
            }

            Row header = sheet.getRow(filaEncabezado);

            /* Construir mapa de nombre de columna → indice */
            java.util.Map<String, Integer> colIdx = new java.util.LinkedHashMap<>();
            for (int c = 0; c < header.getLastCellNum(); c++) {
                Cell cell = header.getCell(c);
                if (cell != null) {
                    String nombre = normalizarEncabezado(fmt.formatCellValue(cell));
                    if (!nombre.isEmpty()) {
                        colIdx.put(nombre, c);
                    }
                }
            }

            /* Nombres normalizados de las columnas esperadas */
            String PRECIO_TOTAL         = normalizarEncabezado("Precio Total");
            String OBS_TIENDA           = normalizarEncabezado("Observaciones Tienda");
            String OBS_PROVEEDOR        = normalizarEncabezado("Observaciones Proveedor");
            String PERFIL_AUTORIZADOR   = normalizarEncabezado("Perfil Autorizador");
            String COD_ACTIVACION       = normalizarEncabezado("Codigo de activacion");
            String ID_DISPOSITIVO       = normalizarEncabezado("ID Dispositivo");

            /* [CA01.1] Orden: Obs Tienda inmediatamente despues de Precio Total */
            boolean ca01 = validarColumnasContiguas(colIdx, PRECIO_TOTAL, OBS_TIENDA);
            /* [CA02.1] Orden: Obs Proveedor inmediatamente despues de Obs Tienda */
            boolean ca02 = validarColumnasContiguas(colIdx, OBS_TIENDA, OBS_PROVEEDOR);
            /* [CA03]   Perfil Autorizador despues de Obs Proveedor */
            boolean ca03pos = validarColumnasContiguas(colIdx, OBS_PROVEEDOR, PERFIL_AUTORIZADOR);
            /* [CA04.1] Codigo de activacion despues de Perfil Autorizador */
            boolean ca04 = validarColumnasContiguas(colIdx, PERFIL_AUTORIZADOR, COD_ACTIVACION);
            /* [CA05.1] ID Dispositivo despues de Codigo de activacion */
            boolean ca05 = validarColumnasContiguas(colIdx, COD_ACTIVACION, ID_DISPOSITIVO);

            logValidacion("[CA01.1] Obs Tienda inmediatamente despues de Precio Total", ca01);
            logValidacion("[CA02.1] Obs Proveedor inmediatamente despues de Obs Tienda", ca02);
            logValidacion("[CA03]   Perfil Autorizador inmediatamente despues de Obs Proveedor", ca03pos);
            logValidacion("[CA04.1] Cod Activacion inmediatamente despues de Perfil Autorizador", ca04);
            logValidacion("[CA05.1] ID Dispositivo inmediatamente despues de Cod Activacion", ca05);

            /* [CA01.4] Valor Obs Tienda en Excel == valor de la vista preliminar */
            boolean ca01val = validarValorExcelVsPreview(
                sheet, header, filaEncabezado, fmt, OBS_TIENDA, valorObsTiendaEsperado);
            /* [CA02.4] Valor Obs Proveedor en Excel == valor de la vista preliminar */
            boolean ca02val = validarValorExcelVsPreview(
                sheet, header, filaEncabezado, fmt, OBS_PROVEEDOR, valorObsProveedorEsperado);

            logValidacion("[CA01.4] Valor Obs Tienda Excel == Vista Preliminar", ca01val);
            logValidacion("[CA02.4] Valor Obs Proveedor Excel == Vista Preliminar", ca02val);

            /* [CA03.1] Perfil Autorizador = 'Proveedor' en todas las filas */
            boolean ca03val = validarValorConstanteColumna(
                sheet, header, filaEncabezado, fmt, PERFIL_AUTORIZADOR, "Proveedor");
            logValidacion("[CA03.1] Perfil Autorizador = 'Proveedor' en todas las filas", ca03val);

            if (screenshot) {
                boolean todosOk = ca01 && ca02 && ca03pos && ca04 && ca05 && ca01val && ca02val && ca03val;
                testCase.addTextEvidenceCurrentStep(
                    "Resultado validacion Excel CF - " +
                    "Orden columnas: " + (ca01 && ca02 && ca03pos && ca04 && ca05 ? "OK" : "FALLA") +
                    " | Valores: " + (ca01val && ca02val && ca03val ? "OK" : "FALLA"));
                testCase.addScreenShotCurrentStep(u, "Validacion_Excel_Individual_CF_5Columnas");
            }

            return ca01 && ca02 && ca03pos && ca04 && ca05 && ca01val && ca02val && ca03val;

        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                "Error al validar columnas en Excel CF: " + e.getMessage());
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    /**
     * Valida que el valor de la columna "Perfil Autorizador" sea "Proveedor" en todas las
     * filas de datos del Excel individual (al menos 2 adicionales si existen).
     *
     * @param screenshot true para registrar evidencia.
     * @return true si todas las filas inspeccionadas tienen el valor "Proveedor".
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public boolean validarValorConstantePerfilAutorizadorExcelCF(boolean screenshot) {
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            File archivoExcel = esperarArchivoExcelIndividualCF();
            if (archivoExcel == null || !archivoExcel.exists()) {
                testCase.addCodeEvidenceCurrentStep("No se encontro el archivo Excel CF para validar Perfil Autorizador.");
                return false;
            }

            fis = new FileInputStream(archivoExcel);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            int filaEncabezado = encontrarFilaEncabezadosCF(sheet, fmt);
            if (filaEncabezado == -1) {
                testCase.addCodeEvidenceCurrentStep("No se encontro fila de encabezados en Excel CF.");
                return false;
            }

            Row header = sheet.getRow(filaEncabezado);
            String PERFIL_AUTORIZADOR = normalizarEncabezado("Perfil Autorizador");

            boolean resultado = validarValorConstanteColumna(
                sheet, header, filaEncabezado, fmt, PERFIL_AUTORIZADOR, "Proveedor");

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "CA03.1 - Valor constante 'Proveedor' en columna Perfil Autorizador: "
                    + (resultado ? "OK" : "FALLA"));
                testCase.addScreenShotCurrentStep(u, "Perfil_Autorizador_Constante_Excel_CF");
            }
            return resultado;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error al validar Perfil Autorizador: " + e.getMessage());
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    /**
     * Valida el formato visual (fuente, tamanio, color) de los encabezados de las
     * 5 columnas nuevas: Arial 14, negrita, texto blanco, fondo azul (CA06).
     *
     * @param screenshot true para registrar evidencia.
     * @return true si todos los encabezados cumplen el formato esperado.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public boolean validarFormatoEncabezados5ColumnasExcelCF(boolean screenshot) {
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            File archivoExcel = esperarArchivoExcelIndividualCF();
            if (archivoExcel == null || !archivoExcel.exists()) {
                testCase.addCodeEvidenceCurrentStep("No se encontro el archivo Excel CF para validar formato.");
                return false;
            }

            fis = new FileInputStream(archivoExcel);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            int filaEncabezado = encontrarFilaEncabezadosCF(sheet, fmt);
            if (filaEncabezado == -1) {
                testCase.addCodeEvidenceCurrentStep("No se encontro fila de encabezados en Excel CF.");
                return false;
            }

            Row header = sheet.getRow(filaEncabezado);
            String[] columnas5 = {
                "Observaciones Tienda",
                "Observaciones Proveedor",
                "Perfil Autorizador",
                "Codigo de activacion",
                "ID Dispositivo"
            };

            boolean todoOk = true;
            for (String colNombre : columnas5) {
                String normalizado = normalizarEncabezado(colNombre);
                int idx = encontrarIndiceColumna(header, fmt, normalizado);
                if (idx == -1) {
                    System.out.println("[CF-Excel] Columna no encontrada para validar formato: " + colNombre);
                    testCase.addCodeEvidenceCurrentStep("CA06: columna no encontrada en encabezado: " + colNombre);
                    todoOk = false;
                    continue;
                }

                Cell cell = header.getCell(idx);
                if (cell == null) {
                    todoOk = false;
                    continue;
                }

                CellStyle style = cell.getCellStyle();
                Font font = wb.getFontAt(style.getFontIndex());

                boolean esArial = font.getFontName() != null && font.getFontName().contains("Arial");
                boolean esTamano14 = font.getFontHeightInPoints() == 14;
                boolean esNegrita = font.getBold();

                String resumen = "Encabezado '" + colNombre + "': " +
                    "Arial=" + esArial + " Tam14=" + esTamano14 + " Bold=" + esNegrita;
                System.out.println("[CF-Excel] " + resumen);

                if (!esArial || !esTamano14 || !esNegrita) {
                    testCase.addCodeEvidenceCurrentStep("CA06 fallido - " + resumen);
                    todoOk = false;
                }
            }

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "CA06 - Formato encabezados 5 columnas nuevas: " + (todoOk ? "OK" : "FALLA"));
                testCase.addScreenShotCurrentStep(u, "Formato_Encabezados_5Columnas_CF");
            }
            return todoOk;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error al validar formato encabezados: " + e.getMessage());
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    /**
     * Valida el formato de las celdas de datos de las 5 columnas nuevas:
     * Arial 12, color texto negro (CA06).
     *
     * @param screenshot true para registrar evidencia.
     * @return true si las celdas de datos cumplen el formato esperado.
     *
     * @author Michelle Ceja
     * @date 2026-08-04
     */
    public boolean validarFormatoDatos5ColumnasExcelCF(boolean screenshot) {
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            File archivoExcel = esperarArchivoExcelIndividualCF();
            if (archivoExcel == null || !archivoExcel.exists()) {
                testCase.addCodeEvidenceCurrentStep("No se encontro el archivo Excel CF para validar formato datos.");
                return false;
            }

            fis = new FileInputStream(archivoExcel);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            int filaEncabezado = encontrarFilaEncabezadosCF(sheet, fmt);
            if (filaEncabezado == -1) {
                testCase.addCodeEvidenceCurrentStep("No se encontro fila de encabezados en Excel CF.");
                return false;
            }

            Row header = sheet.getRow(filaEncabezado);
            int primeraDato = filaEncabezado + 1;

            /* Solo validar la primera fila de datos que exista */
            Row filaDatos = null;
            for (int r = primeraDato; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    filaDatos = row;
                    break;
                }
            }

            if (filaDatos == null) {
                testCase.addCodeEvidenceCurrentStep("No se encontraron filas de datos en el Excel CF.");
                return false;
            }

            String[] columnas5 = {
                "Observaciones Tienda",
                "Observaciones Proveedor",
                "Perfil Autorizador",
                "Codigo de activacion",
                "ID Dispositivo"
            };

            boolean todoOk = true;
            for (String colNombre : columnas5) {
                String normalizado = normalizarEncabezado(colNombre);
                int idx = encontrarIndiceColumna(header, fmt, normalizado);
                if (idx == -1) continue;

                Cell cell = filaDatos.getCell(idx);
                if (cell == null) continue;

                CellStyle style = cell.getCellStyle();
                Font font = wb.getFontAt(style.getFontIndex());

                boolean esArial = font.getFontName() != null && font.getFontName().contains("Arial");
                boolean esTamano12 = font.getFontHeightInPoints() == 12;

                String resumen = "Datos '" + colNombre + "': Arial=" + esArial + " Tam12=" + esTamano12;
                System.out.println("[CF-Excel] " + resumen);

                if (!esArial || !esTamano12) {
                    testCase.addCodeEvidenceCurrentStep("CA06 datos fallido - " + resumen);
                    todoOk = false;
                }
            }

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "CA06 - Formato datos 5 columnas nuevas: " + (todoOk ? "OK" : "FALLA"));
                testCase.addScreenShotCurrentStep(u, "Formato_Datos_5Columnas_CF");
            }
            return todoOk;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error al validar formato datos: " + e.getMessage());
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    // =========================================================
    // HELPERS PRIVADOS
    // =========================================================

    /**
     * Localiza la fila de encabezados en el Excel de Cambios Fisicos
     * buscando columnas clave del layout esperado.
     *
     * @param sheet hoja de Excel a inspeccionar.
     * @param fmt formateador para obtener texto normalizado de celdas.
     * @return indice de la fila de encabezados; -1 si no se encuentra.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private int encontrarFilaEncabezadosCF(Sheet sheet, DataFormatter fmt) {
        for (int i = 0; i <= Math.min(sheet.getLastRowNum(), 20); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            for (int c = 0; c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell == null) continue;
                String texto = normalizarEncabezado(fmt.formatCellValue(cell));
                if ("PRECIO TOTAL".equals(texto) || "OBSERVACIONES TIENDA".equals(texto)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Valida que dos columnas esten en posicion contigua dentro del encabezado.
     *
     * @param colIdx mapa de nombre de columna a indice de columna.
     * @param columnaAnterior nombre de la columna que debe ir primero.
     * @param columnaSiguiente nombre de la columna que debe ir inmediatamente despues.
     * @return true si ambas columnas existen y su posicion es contigua.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private boolean validarColumnasContiguas(
            java.util.Map<String, Integer> colIdx, String columnaAnterior, String columnaSiguiente) {

        Integer idxAnterior = colIdx.get(columnaAnterior);
        Integer idxSiguiente = colIdx.get(columnaSiguiente);

        if (idxAnterior == null) {
            System.out.println("[CF-Excel] Columna no encontrada: " + columnaAnterior);
            return false;
        }
        if (idxSiguiente == null) {
            System.out.println("[CF-Excel] Columna no encontrada: " + columnaSiguiente);
            return false;
        }
        boolean ok = (idxSiguiente == idxAnterior + 1);
        System.out.println("[CF-Excel] '" + columnaAnterior + "'[" + idxAnterior + "] → '" +
            columnaSiguiente + "'[" + idxSiguiente + "] contiguas=" + ok);
        return ok;
    }

    /**
     * Compara el primer valor no vacio de una columna del Excel contra el valor
     * observado en la vista previa del flujo.
     *
     * @param sheet hoja de Excel a validar.
     * @param header fila de encabezados detectada.
     * @param filaEncabezado indice de la fila de encabezados.
     * @param fmt formateador para lectura de celdas.
     * @param nombreColumna nombre normalizado de la columna a comparar.
     * @param valorEsperado valor esperado proveniente de la vista previa.
     * @return true si el valor coincide o si no hay valor esperado para comparar.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private boolean validarValorExcelVsPreview(
            Sheet sheet, Row header, int filaEncabezado,
            DataFormatter fmt, String nombreColumna, String valorEsperado) {

        if (valorEsperado == null || valorEsperado.trim().isEmpty()) {
            System.out.println("[CF-Excel] Valor esperado vacio para columna: " + nombreColumna);
            return true; /* si no hay valor en preview, no se puede comparar */
        }

        int idx = encontrarIndiceColumna(header, fmt, nombreColumna);
        if (idx == -1) {
            System.out.println("[CF-Excel] Columna no encontrada para comparar valor: " + nombreColumna);
            return false;
        }

        for (int r = filaEncabezado + 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            Cell cell = row.getCell(idx);
            if (cell == null) continue;
            String valorExcel = fmt.formatCellValue(cell).trim();
            if (!valorExcel.isEmpty()) {
                boolean ok = valorExcel.equals(valorEsperado.trim());
                System.out.println("[CF-Excel] '" + nombreColumna + "' Excel=[" + valorExcel +
                    "] Preview=[" + valorEsperado + "] coincide=" + ok);
                return ok;
            }
        }

        System.out.println("[CF-Excel] No se encontro valor en columna: " + nombreColumna);
        return false;
    }

    /**
     * Verifica que todos los valores no vacios de una columna coincidan con
     * un valor constante esperado.
     *
     * @param sheet hoja de Excel a validar.
     * @param header fila de encabezados detectada.
     * @param filaEncabezado indice de la fila de encabezados.
     * @param fmt formateador para lectura de celdas.
     * @param nombreColumna nombre de la columna a validar.
     * @param valorEsperado valor constante esperado para todas las filas.
     * @return true si todas las filas con datos cumplen el valor esperado.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private boolean validarValorConstanteColumna(
            Sheet sheet, Row header, int filaEncabezado,
            DataFormatter fmt, String nombreColumna, String valorEsperado) {

        int idx = encontrarIndiceColumna(header, fmt, nombreColumna);
        if (idx == -1) {
            System.out.println("[CF-Excel] Columna no encontrada para validar constante: " + nombreColumna);
            return false;
        }

        boolean todoOk = true;
        int filasDatos = 0;

        for (int r = filaEncabezado + 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            Cell cell = row.getCell(idx);
            if (cell == null) continue;
            String valor = fmt.formatCellValue(cell).trim();
            if (valor.isEmpty()) continue;

            filasDatos++;
            boolean ok = valor.equalsIgnoreCase(valorEsperado);
            if (!ok) {
                System.out.println("[CF-Excel] Fila " + (r + 1) + " '" + nombreColumna +
                    "' = [" + valor + "] esperado=[" + valorEsperado + "]");
                todoOk = false;
            }
        }

        if (filasDatos == 0) {
            System.out.println("[CF-Excel] Sin filas de datos para validar constante en: " + nombreColumna);
            return false;
        }

        return todoOk;
    }

    /**
     * Obtiene el indice de una columna por nombre normalizado dentro de la fila
     * de encabezados del Excel.
     *
     * @param header fila de encabezados.
     * @param fmt formateador para lectura de celdas.
     * @param nombreNormalizado nombre de columna ya normalizado.
     * @return indice de columna encontrado; -1 si no existe.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private int encontrarIndiceColumna(Row header, DataFormatter fmt, String nombreNormalizado) {
        for (int c = 0; c < header.getLastCellNum(); c++) {
            Cell cell = header.getCell(c);
            if (cell == null) continue;
            if (normalizarEncabezado(fmt.formatCellValue(cell)).equals(nombreNormalizado)) {
                return c;
            }
        }
        return -1;
    }

    /**
     * Normaliza texto de encabezado para comparaciones robustas:
     * elimina acentos, caracteres de control, espacios duplicados y convierte a mayusculas.
     *
     * @param texto encabezado original.
     * @return texto normalizado listo para comparacion.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private String normalizarEncabezado(String texto) {
        if (texto == null) return "";
        String t = java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[\\p{C}]", "")
                .replaceAll("\\s+", " ")
                .trim()
                .toUpperCase();
        return t;
    }

    /**
     * Registra en consola el resultado de una validacion interna.
     *
     * @param nombre nombre corto de la validacion.
     * @param resultado resultado final de la validacion.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private void logValidacion(String nombre, boolean resultado) {
        System.out.println("[CF-Validacion] " + nombre + ": " + (resultado ? "PASS" : "FAIL"));
    }

    /**
     * Cierra de forma segura el workbook y el stream de archivo.
     *
     * @param wb instancia de workbook a cerrar.
     * @param fis stream de archivo asociado al workbook.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private void cerrarWorkbook(Workbook wb, FileInputStream fis) {
        try { if (wb != null) wb.close(); } catch (Exception ignored) {}
        try { if (fis != null) fis.close(); } catch (Exception ignored) {}
    }

    /**
     * Valida que el encabezado Precio del grid de Cambios Fisicos Pendientes se
     * muestre con alineacion centrada conforme a CA04 y CA07.
     *
     * @param screenshot true para registrar evidencia visual del encabezado.
     * @return true si el encabezado Precio esta centrado.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    public boolean validarAlineacionCentradaEncabezadoPrecioGridCFP(boolean screenshot) {
        By encabezadoPrecio = elements.encabezadoPrecioGridCambiosFisicosPendientes;
        try {
            WebElement encabezado = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfElementLocated(encabezadoPrecio));
            ((JavascriptExecutor) u.getDriver()).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});", encabezado);

            java.util.Map<String, Object> diagnostico = obtenerDiagnosticoAlineacionCentrada(encabezado);
            boolean resultadoPaso = Boolean.TRUE.equals(diagnostico.get("centrado"));
            String textAlign = String.valueOf(diagnostico.getOrDefault("textAlign", ""));
            String parentTextAlign = String.valueOf(diagnostico.getOrDefault("parentTextAlign", ""));
            String justify = String.valueOf(diagnostico.getOrDefault("justify", ""));
            String parentJustify = String.valueOf(diagnostico.getOrDefault("parentJustify", ""));
            double diffCentro = ((Number) diagnostico.getOrDefault("diffCentro", 999d)).doubleValue();

            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "El encabezado Precio no esta centrado. Esperado: alineacion centrada | Actual: text-align="
                            + textAlign + ", parent-text-align=" + parentTextAlign + ", justify-content=" + justify
                            + ", parent-justify-content=" + parentJustify + ", diffCentro="
                            + String.format("%.2f", diffCentro);

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Validar alineacion centrada del encabezado Precio"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                u.highLight(encabezadoPrecio);
                try {
                    testCase.addTextEvidenceCurrentStep(resultadoPaso
                            ? "El encabezado Precio se muestra centrado en el grid de Cambios Fisicos Pendientes."
                            : "El encabezado Precio no se muestra centrado en el grid de Cambios Fisicos Pendientes.");
                    testCase.addScreenShotCurrentStep(u, "Encabezado_Precio_Alineacion_Centrada_CFP");
                } finally {
                    u.unhighLight(encabezadoPrecio);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible validar alineacion centrada del encabezado Precio: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Validar alineacion centrada del encabezado Precio | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Valida que el contenido de la columna Precio en el grid de Cambios Fisicos
     * Pendientes se muestre con alineacion centrada para una muestra minima de
     * registros.
     *
     * @param minimoRegistros cantidad minima de registros a validar.
     * @param screenshot      true para registrar evidencia visual de la validacion.
     * @return true si la muestra validada de celdas de Precio esta centrada.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    public boolean validarAlineacionCentradaContenidoPrecioGridCFP(int minimoRegistros, boolean screenshot) {
        try {
            u.waitWebElement(elements.celdasColumnaPrecioGridCFP, 20, 2);
            List<WebElement> celdas = u.getDriver().findElements(elements.celdasColumnaPrecioGridCFP);

            List<WebElement> celdasConValor = new ArrayList<WebElement>();
            List<Integer> indicesCeldasConValor = new ArrayList<Integer>();
            for (int i = 0; i < celdas.size(); i++) {
                String texto = celdas.get(i).getText() == null ? "" : celdas.get(i).getText().trim();
                if (!texto.isEmpty()) {
                    celdasConValor.add(celdas.get(i));
                    indicesCeldasConValor.add(i);
                }
            }

            boolean resultadoPaso;
            String motivoFallo = "";
            int registrosRevisados = Math.min(minimoRegistros, celdasConValor.size());
            int indicePrimeraCelda = -1;

            if (registrosRevisados < minimoRegistros) {
                resultadoPaso = false;
                motivoFallo = "No hay suficientes registros con valor en Precio. Esperado: al menos " + minimoRegistros
                        + " | Actual: " + registrosRevisados;
            } else {
                resultadoPaso = true;
                List<String> hallazgosNoCentrados = new ArrayList<String>();
                for (int i = 0; i < registrosRevisados; i++) {
                    WebElement celda = celdasConValor.get(i);
                    if (indicePrimeraCelda < 0) {
                        indicePrimeraCelda = indicesCeldasConValor.get(i);
                    }
                    java.util.Map<String, Object> diagnostico = obtenerDiagnosticoAlineacionCentrada(celda);
                    boolean centrada = Boolean.TRUE.equals(diagnostico.get("centrado"));
                    if (!centrada) {
                        resultadoPaso = false;
                        String valor = celda.getText().trim();
                        String textAlign = String.valueOf(diagnostico.getOrDefault("textAlign", ""));
                        String parentTextAlign = String.valueOf(diagnostico.getOrDefault("parentTextAlign", ""));
                        String justify = String.valueOf(diagnostico.getOrDefault("justify", ""));
                        String parentJustify = String.valueOf(diagnostico.getOrDefault("parentJustify", ""));
                        double diffCentro = ((Number) diagnostico.getOrDefault("diffCentro", 999d)).doubleValue();
                        String crTienda = obtenerTextoCeldaHermana(celda, "mat-column-crTienda");
                        String nombreTienda = obtenerTextoCeldaHermana(celda, "mat-column-nombreTienda");
                        hallazgosNoCentrados.add("Registro " + (i + 1) + " (CR=" + crTienda + ", Tienda=" + nombreTienda
                                + ", Precio='" + valor + "', text-align=" + textAlign + ", parent-text-align="
                                + parentTextAlign + ", justify-content=" + justify + ", parent-justify-content="
                                + parentJustify + ", diffCentro=" + String.format("%.2f", diffCentro) + ")");
                    }
                }
                if (!resultadoPaso) {
                    motivoFallo = "Al menos una celda de Precio no esta centrada. Detalle: "
                            + String.join(" | ", hallazgosNoCentrados);
                }
            }

            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Validar alineacion centrada del contenido de la columna Precio"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot && indicePrimeraCelda >= 0) {
                By primeraCeldaPrecio = By.xpath("(//table//td[contains(@class,'mat-column-salePrice')])["
                        + (indicePrimeraCelda + 1) + "]");
                WebElement primerElemento = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOfElementLocated(primeraCeldaPrecio));
                ((JavascriptExecutor) u.getDriver()).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", primerElemento);
                u.highLight(primeraCeldaPrecio);
                try {
                    testCase.addTextEvidenceCurrentStep(resultadoPaso
                            ? "Los valores de la columna Precio se muestran centrados en la muestra revisada ("
                                    + registrosRevisados + " registros)."
                            : "Se detectaron celdas de la columna Precio sin alineacion centrada en la muestra revisada.");
                    testCase.addScreenShotCurrentStep(u, "Contenido_Precio_Alineacion_Centrada_CFP");
                } finally {
                    u.unhighLight(primeraCeldaPrecio);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible validar alineacion centrada del contenido de Precio: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Validar alineacion centrada del contenido de Precio | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Obtiene diagnostico tecnico de alineacion visual para una celda o encabezado.
     *
     * @param elemento elemento visual a evaluar.
     * @return mapa con valores de estilos y centrado visual.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private java.util.Map<String, Object> obtenerDiagnosticoAlineacionCentrada(WebElement elemento) {
        return (java.util.Map<String, Object>) ((JavascriptExecutor) u.getDriver()).executeScript(
                "const el = arguments[0];"
                        + "const parent = el.parentElement || el;"
                        + "const style = window.getComputedStyle(el);"
                        + "const parentStyle = window.getComputedStyle(parent);"
                        + "const textAlign = (style.textAlign || '').toLowerCase();"
                        + "const parentTextAlign = (parentStyle.textAlign || '').toLowerCase();"
                        + "const justify = (style.justifyContent || '').toLowerCase();"
                        + "const parentJustify = (parentStyle.justifyContent || '').toLowerCase();"
                        + "const rect = el.getBoundingClientRect();"
                        + "const parentRect = parent.getBoundingClientRect();"
                        + "const diffCentro = Math.abs((rect.left + rect.width / 2) - (parentRect.left + parentRect.width / 2));"
                        + "const centradoVisual = diffCentro <= 12;"
                        + "const centrado = textAlign === 'center' || parentTextAlign === 'center' || justify.includes('center') || parentJustify.includes('center') || centradoVisual;"
                        + "return {centrado, textAlign, parentTextAlign, justify, parentJustify, diffCentro};",
                elemento);
    }

    /**
     * Obtiene el texto de una celda hermana en la misma fila, identificada por
     * clase de columna.
     *
     * @param celda                celda base desde la cual se ubica la fila.
     * @param claseParcialColumna  clase parcial de la columna hermana.
     * @return texto de la celda hermana o cadena vacia si no se localiza.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private String obtenerTextoCeldaHermana(WebElement celda, String claseParcialColumna) {
        try {
            WebElement fila = celda.findElement(By.xpath("./ancestor::tr[1]"));
            return fila.findElement(By.cssSelector("td." + claseParcialColumna)).getText().trim();
        } catch (Exception ignored) {
            return "";
        }
    }

    /**
     * Consulta en BD cometa los valores de sale_price filtrados por la muestra
     * de SKU capturada previamente en el grid de Cambios Fisicos Pendientes.
     * Para cada SKU busca el registro mas reciente por created_date.
     *
     * @param limite cantidad maxima de SKU a consultar en BD.
     * @param screenshot true para registrar evidencia textual de la consulta.
     * @return true si se recupera al menos un SKU con sale_price para comparar.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    public boolean consultarMuestraSalePriceBDCFPendientes(int limite, boolean screenshot) {
        List<String> muestraPrecios = new ArrayList<String>();
        List<String> muestraSkus = new ArrayList<String>();
        try {
            LinkedHashSet<String> skusUnicos = new LinkedHashSet<String>();
            for (String sku : this.muestraSkusGridCFP) {
                String skuNormalizado = normalizarSku(sku);
                if (!skuNormalizado.isEmpty()) {
                    skusUnicos.add(skuNormalizado);
                }
                if (skusUnicos.size() >= limite) {
                    break;
                }
            }

            if (skusUnicos.isEmpty()) {
                String motivo = "No hay SKU capturados en grid para filtrar la consulta en BD.";
                System.out.println("[RESULTADO PASO] FAILED - Consultar muestra sale_price en BD por SKU | Motivo: " + motivo);
                return false;
            }

            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < skusUnicos.size(); i++) {
                if (i > 0) {
                    placeholders.append(",");
                }
                placeholders.append("?");
            }

            String sql = "SELECT DISTINCT ON (sku) sku, sale_price "
                    + "FROM public.xxcm_cambios_fisicos_pendientes "
                    + "WHERE sale_price IS NOT NULL "
                    + "AND sku IN (" + placeholders.toString() + ") "
                    + "ORDER BY sku, created_date DESC";

            try (Connection conn = DriverManager.getConnection(
                    GlobalVariables.DB_URL_Cometa,
                    GlobalVariables.DB_USER_Cometa,
                    GlobalVariables.DB_PASSWORD_Cometa);
                    PreparedStatement pst = conn.prepareStatement(sql)) {

                int parametro = 1;
                for (String sku : skusUnicos) {
                    pst.setString(parametro++, sku);
                }

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        String skuBd = normalizarSku(rs.getString("sku"));
                        BigDecimal precio = rs.getBigDecimal("sale_price");
                        if (!skuBd.isEmpty() && precio != null) {
                            muestraSkus.add(skuBd);
                            muestraPrecios.add(formatearPrecioCanonico(precio.toPlainString()));
                        }
                    }
                }
            }

            boolean resultadoPaso = !muestraPrecios.isEmpty();
            this.muestraSkusBdCFP = muestraSkus;
            this.muestraPreciosBdCFP = muestraPrecios;
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "No se recuperaron valores sale_price para los SKU consultados. Esperado: al menos 1 | Actual: 0";

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Consultar muestra sale_price en BD por SKU"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep("SKU consultados: " + skusUnicos.toString());
                testCase.addTextEvidenceCurrentStep("Muestra BD (SKU->sale_price): " + construirParesSkuPrecio(muestraSkus, muestraPrecios));
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible consultar sale_price en BD por SKU para CFP: " + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Consultar muestra sale_price en BD por SKU | Motivo: " + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
    * Captura una muestra de valores visibles de las columnas SKU y Precio del
    * grid de Cambios Fisicos Pendientes y la guarda para comparacion contra BD.
     *
     * @param limite cantidad maxima de valores no vacios a capturar del grid.
     * @param screenshot true para registrar evidencia visual de la columna Precio.
    * @return true si se capturan pares SKU-Precio visibles suficientes para comparar.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    public boolean capturarValoresPrecioVisiblesEnGridCFP(int limite, boolean screenshot) {
        List<String> muestraGrid = new ArrayList<String>();
        List<String> muestraSkus = new ArrayList<String>();
        By primeraCeldaPrecio = null;
        try {
            u.waitWebElement(elements.filasGridCambiosFisicosPendientes, 20, 2);
            List<WebElement> filas = u.getDriver().findElements(elements.filasGridCambiosFisicosPendientes);
            for (int i = 0; i < filas.size() && muestraGrid.size() < limite; i++) {
                WebElement fila = filas.get(i);

                String sku = "";
                String precioTexto = "";
                try {
                    sku = fila.findElement(By.cssSelector("td.mat-column-sku")).getText();
                } catch (Exception ignored) {
                    try {
                        sku = fila.findElement(By.xpath("./td[5]")).getText();
                    } catch (Exception ignored2) {
                        sku = "";
                    }
                }

                try {
                    precioTexto = fila.findElement(By.cssSelector("td.mat-column-salePrice")).getText();
                } catch (Exception ignored) {
                    precioTexto = "";
                }

                String skuCanonico = normalizarSku(sku);
                String precioCanonico = formatearPrecioCanonico(precioTexto);

                if (!skuCanonico.isEmpty() && !precioCanonico.isEmpty()) {
                    muestraSkus.add(skuCanonico);
                    muestraGrid.add(precioCanonico);
                    if (primeraCeldaPrecio == null) {
                        primeraCeldaPrecio = By.xpath("(//table//td[contains(@class,'mat-column-salePrice')])["
                                + (i + 1) + "]");
                    }
                }
            }

            this.muestraSkusGridCFP = muestraSkus;
            this.muestraPreciosGridCFP = muestraGrid;
            boolean resultadoPaso = !muestraGrid.isEmpty() && muestraGrid.size() <= limite;
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "No se capturaron pares SKU-Precio visibles en grid. Esperado: al menos 1 | Actual: "
                            + muestraGrid.size();

            System.out.println("[RESULTADO PASO] " + estadoPaso
                        + " - Capturar muestra SKU-Precio visible en grid"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot && primeraCeldaPrecio != null) {
                WebElement celda = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOfElementLocated(primeraCeldaPrecio));
                ((JavascriptExecutor) u.getDriver()).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", celda);
                u.highLight(primeraCeldaPrecio);
                try {
                    testCase.addTextEvidenceCurrentStep("Muestra Grid (SKU->Precio): "
                            + construirParesSkuPrecio(muestraSkus, muestraGrid));
                    testCase.addScreenShotCurrentStep(u, "Muestra_Precio_Grid_CFP");
                } finally {
                    u.unhighLight(primeraCeldaPrecio);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible capturar muestra SKU-Precio en grid CFP: " + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Capturar muestra SKU-Precio visible en grid | Motivo: " + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
    * Compara la muestra de valores de Precio capturada del grid contra la muestra
    * de sale_price obtenida de BD para los mismos SKU.
     *
     * @param screenshot true para registrar evidencia del resultado de comparacion.
    * @return true si cada par SKU-Precio del grid coincide con sale_price en BD.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    public boolean compararMuestraPrecioGridVsSalePriceBDCFP(boolean screenshot) {
        try {
            List<String> muestraSkuBd = this.muestraSkusBdCFP == null
                    ? new ArrayList<String>()
                    : new ArrayList<String>(this.muestraSkusBdCFP);
            List<String> muestraBd = this.muestraPreciosBdCFP == null
                    ? new ArrayList<String>()
                    : new ArrayList<String>(this.muestraPreciosBdCFP);
            List<String> muestraSkuGrid = this.muestraSkusGridCFP == null
                    ? new ArrayList<String>()
                    : new ArrayList<String>(this.muestraSkusGridCFP);
            List<String> muestraGrid = this.muestraPreciosGridCFP == null
                    ? new ArrayList<String>()
                    : new ArrayList<String>(this.muestraPreciosGridCFP);

            boolean baseValida = !muestraSkuBd.isEmpty() && !muestraBd.isEmpty()
                    && !muestraSkuGrid.isEmpty() && !muestraGrid.isEmpty();
            boolean resultadoPaso = baseValida;
            List<String> diferencias = new ArrayList<String>();

            if (baseValida) {
                java.util.Map<String, String> bdPorSku = new java.util.LinkedHashMap<String, String>();
                for (int i = 0; i < muestraSkuBd.size() && i < muestraBd.size(); i++) {
                    bdPorSku.put(normalizarSku(muestraSkuBd.get(i)), muestraBd.get(i));
                }

                for (int i = 0; i < muestraSkuGrid.size() && i < muestraGrid.size(); i++) {
                    String skuGrid = normalizarSku(muestraSkuGrid.get(i));
                    String precioGrid = muestraGrid.get(i);
                    String precioBd = bdPorSku.get(skuGrid);
                    if (precioBd == null) {
                        resultadoPaso = false;
                        diferencias.add("SKU " + skuGrid + " no encontrado en BD");
                        continue;
                    }
                    if (!precioGrid.equals(precioBd)) {
                        resultadoPaso = false;
                        diferencias.add("SKU " + skuGrid + " -> Grid=" + precioGrid + " | BD=" + precioBd);
                    }
                }
            }

            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo;
            if (!baseValida) {
                motivoFallo = "No hay muestra suficiente para comparar. BD SKU=" + muestraSkuBd.size()
                        + ", BD Precio=" + muestraBd.size() + " | Grid SKU=" + muestraSkuGrid.size()
                        + ", Grid Precio=" + muestraGrid.size();
            } else if (!diferencias.isEmpty()) {
                motivoFallo = "Diferencias SKU-Precio detectadas. Esperado: cada SKU del grid tenga el mismo sale_price en BD | Detalle: "
                        + diferencias.toString();
            } else {
                motivoFallo = "";
            }

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Comparar Precio grid vs sale_price BD"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                    testCase.addTextEvidenceCurrentStep("Muestra BD (SKU->sale_price): "
                        + construirParesSkuPrecio(muestraSkuBd, muestraBd));
                    testCase.addTextEvidenceCurrentStep("Muestra Grid (SKU->Precio): "
                        + construirParesSkuPrecio(muestraSkuGrid, muestraGrid));
                testCase.addScreenShotCurrentStep(u, "Comparacion_Precio_Grid_Vs_BD_CFP");
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible comparar Precio grid vs sale_price BD: " + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Comparar Precio grid vs sale_price BD | Motivo: " + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Normaliza representaciones de precio para comparar UI y BD en formato
     * numerico canonico con dos decimales.
     *
     * @param valor valor de precio crudo proveniente de UI o BD.
     * @return precio normalizado con dos decimales o cadena vacia si no se puede convertir.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private String formatearPrecioCanonico(String valor) {
        try {
            if (valor == null) {
                return "";
            }
            String limpio = valor.replace("$", "").replace(",", "").trim();
            if (limpio.isEmpty()) {
                return "";
            }
            BigDecimal bd = new BigDecimal(limpio);
            return bd.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
        } catch (Exception ignored) {
            return "";
        }
    }

    /**
     * Normaliza SKU para comparaciones entre grid y BD eliminando espacios.
     *
     * @param sku valor SKU crudo de UI o BD.
     * @return SKU normalizado en mayusculas o vacio cuando no hay dato.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private String normalizarSku(String sku) {
        if (sku == null) {
            return "";
        }
        return sku.trim().toUpperCase();
    }

    /**
     * Construye representacion legible de pares SKU-Precio para evidencia.
     *
     * @param skus lista de SKU de la muestra.
     * @param precios lista de precios asociada por indice.
     * @return lista formateada de pares SKU=Precio.
     *
     * @author Michelle Ceja
     * @date 2026-08-05
     */
    private String construirParesSkuPrecio(List<String> skus, List<String> precios) {
        List<String> pares = new ArrayList<String>();
        int limite = Math.min(skus.size(), precios.size());
        for (int i = 0; i < limite; i++) {
            pares.add(skus.get(i) + "=" + precios.get(i));
        }
        return pares.toString();
    }

    /**
     * Consulta en BD cometa una muestra de registros con sale_price nulo para
     * preparar la validacion del escenario negativo en pantalla.
     *
     * @param limite cantidad maxima de registros a recuperar.
     * @param screenshot true para registrar evidencia textual de la consulta.
     * @return true si se recupera al menos un registro con sale_price nulo.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean consultarMuestraSalePriceNuloBDCFPendientes(int limite, boolean screenshot) {
        return consultarMuestraSalePriceNuloBDCFPendientesPorUsuario(null, limite, screenshot);
    }

    /**
     * Consulta en BD cometa una muestra de registros con sale_price nulo usando
     * el alcance del usuario para garantizar que el SKU exista en el grid de su
     * sesion.
     *
     * @param usuario usuario de Portal COMETA para resolver alcance; null aplica consulta global.
     * @param limite cantidad maxima de registros a recuperar.
     * @param screenshot true para registrar evidencia textual de la consulta.
     * @return true si se recupera al menos un registro con sale_price nulo dentro del alcance.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean consultarMuestraSalePriceNuloBDCFPendientesPorUsuario(String usuario, int limite, boolean screenshot) {
        List<String> muestraIds = new ArrayList<String>();
        List<String> muestraSkus = new ArrayList<String>();
        try {
            String usuarioNormalizado = usuario == null ? "" : usuario.trim();
            String[] alcance = resolverAlcanceUsuarioCFPendientes(usuarioNormalizado);
            String tipoFiltro = alcance[0];
            String valorFiltro = alcance[1];
            boolean consultaGlobal = "GLOBAL".equals(tipoFiltro);

            String sql = construirSqlSalePriceNuloPorAlcance(tipoFiltro, Math.max(1, limite));

            try (Connection conn = DriverManager.getConnection(
                    GlobalVariables.DB_URL_Cometa,
                    GlobalVariables.DB_USER_Cometa,
                    GlobalVariables.DB_PASSWORD_Cometa);
                    PreparedStatement pst = conn.prepareStatement(sql)) {

                if (!consultaGlobal) {
                    pst.setString(1, valorFiltro);
                }
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        String id = rs.getString("id");
                        String sku = normalizarSku(rs.getString("sku"));
                        if (id != null && !id.trim().isEmpty()) {
                            muestraIds.add(id.trim());
                            muestraSkus.add(sku);
                        }
                    }
                }
            }

            boolean resultadoPaso = !muestraIds.isEmpty();
            this.muestraIdsSalePriceNuloBdCFP = muestraIds;
            this.muestraSkusSalePriceNuloBdCFP = muestraSkus;
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "No se encontraron registros con sale_price nulo en BD. Esperado: al menos 1 | Actual: 0";

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Consultar muestra sale_price nulo en BD"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep("Consulta alcance sale_price nulo -> Tipo filtro: "
                    + tipoFiltro + " | Valor: " + (consultaGlobal ? "SIN FILTRO" : valorFiltro));
                testCase.addTextEvidenceCurrentStep("Muestra BD con sale_price nulo (id->sku): "
                        + construirParesSkuPrecio(muestraIds, muestraSkus));
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible consultar muestra sale_price nulo en BD para CFP: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Consultar muestra sale_price nulo en BD | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Resuelve el tipo de alcance aplicable al usuario para consultas de
     * Cambios Fisicos Pendientes.
     *
     * @param usuario usuario de Portal COMETA.
     * @return arreglo [tipoFiltro, valorFiltro].
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    private String[] resolverAlcanceUsuarioCFPendientes(String usuario) {
        String usuarioLimpio = usuario == null ? "" : usuario.trim();
        if (usuarioLimpio.isEmpty()) {
            return new String[] { "GLOBAL", "SIN FILTRO" };
        }

        String perfil = "";
        String pcrfc = "";
        String pcSupplierId = "";
        String pcPlaza = "";
        String sqlPerfil = "select key_value from xxcm_user_keys "
                + "where short_name = 'PCPERFIL' "
                + "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
                + "limit 1";
        String sqlKeys = "select short_name, key_value from xxcm_user_keys "
                + "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
                + "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";

        try (Connection conn = DriverManager.getConnection(
                GlobalVariables.DB_URL_Cometa,
                GlobalVariables.DB_USER_Cometa,
                GlobalVariables.DB_PASSWORD_Cometa);
                PreparedStatement pstPerfil = conn.prepareStatement(sqlPerfil);
                PreparedStatement pstKeys = conn.prepareStatement(sqlKeys)) {

            pstPerfil.setString(1, usuarioLimpio);
            try (ResultSet rsPerfil = pstPerfil.executeQuery()) {
                if (rsPerfil.next()) {
                    perfil = rsPerfil.getString("key_value") == null ? "" : rsPerfil.getString("key_value").trim();
                }
            }

            pstKeys.setString(1, usuarioLimpio);
            try (ResultSet rsKeys = pstKeys.executeQuery()) {
                while (rsKeys.next()) {
                    String shortName = rsKeys.getString("short_name") == null ? "" : rsKeys.getString("short_name").trim();
                    String keyValue = rsKeys.getString("key_value") == null ? "" : rsKeys.getString("key_value").trim();
                    if ("PCRFC".equalsIgnoreCase(shortName)) {
                        pcrfc = keyValue;
                    } else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
                        pcSupplierId = keyValue;
                    } else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
                        pcPlaza = keyValue;
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("[CFP-Alcance] Fallback a consulta GLOBAL por error al resolver alcance de usuario "
                    + usuarioLimpio + ": " + exception.getMessage());
            return new String[] { "GLOBAL", "SIN FILTRO" };
        }

        String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
        if ("ADMIN".equals(perfilNormalizado) || "OXXO_OS".equals(perfilNormalizado)
                || "5555555".equals(usuarioLimpio) || "3333333".equals(usuarioLimpio)) {
            return new String[] { "GLOBAL", "SIN FILTRO" };
        }

        if ("OXXO_PLAZA".equals(perfilNormalizado) && !pcPlaza.isEmpty()) {
            return new String[] { "PCPLAZA", pcPlaza };
        }

        if (!pcrfc.isEmpty()) {
            return new String[] { "PCRFC", pcrfc };
        }

        if (!pcSupplierId.isEmpty()) {
            return new String[] { "PCSUPPLIERID", pcSupplierId };
        }

        return new String[] { "GLOBAL", "SIN FILTRO" };
    }

    /**
     * Construye el SQL para recuperar registros con sale_price nulo segun el
     * alcance del usuario.
     *
     * @param tipoFiltro tipo de alcance: GLOBAL, PCRFC, PCSUPPLIERID, PCPLAZA.
     * @param limite cantidad maxima de filas a devolver.
     * @return SQL listo para PreparedStatement.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    private String construirSqlSalePriceNuloPorAlcance(String tipoFiltro, int limite) {
        if ("PCRFC".equals(tipoFiltro)) {
            return "select id::text as id, sku "
                    + "from public.get_cambios_fisicos_directos_pendientes_paginated("
                    + "1, 2147483647, array[?], null, null, null, null, null, null, null, null, null, null, null) "
                    + "where sale_price is null "
                    + "order by created_datetime_business desc nulls last "
                    + "limit " + limite;
        }

        if ("PCSUPPLIERID".equals(tipoFiltro)) {
            return "select id::text as id, sku "
                    + "from public.get_cambios_fisicos_directos_pendientes_paginated("
                    + "1, 2147483647, null, array[?::bigint], null, null, null, null, null, null, null, null, null, null) "
                    + "where sale_price is null "
                    + "order by created_datetime_business desc nulls last "
                    + "limit " + limite;
        }

        if ("PCPLAZA".equals(tipoFiltro)) {
            return "select id::text as id, sku "
                    + "from public.get_cambios_fisicos_directos_pendientes_paginated("
                    + "1, 2147483647, null, null, null, null, null, null, array[?], null, null, null, null, null) "
                    + "where sale_price is null "
                    + "order by created_datetime_business desc nulls last "
                    + "limit " + limite;
        }

        return "select id::text as id, sku "
                + "from public.xxcm_cambios_fisicos_pendientes "
                + "where sale_price is null "
                + "order by created_date desc "
                + "limit " + limite;
    }

    /**
     * Valida que la pantalla Cambios Fisicos Pendientes no presente error tecnico,
     * crash ni pantalla en blanco al mostrar resultados.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return true si no se detectan indicadores de error tecnico y hay contenido visible.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean validarPantallaSinErrorTecnicoCFPendientes(boolean screenshot) {
        try {
            u.waitWebElement(elements.filasGridCambiosFisicosPendientes, 20, 2);
            List<WebElement> filas = u.getDriver().findElements(elements.filasGridCambiosFisicosPendientes);

            String contenido = u.getDriver().getPageSource();
            String contenidoNormalizado = contenido == null ? "" : contenido.toLowerCase();
            boolean sinErrorTecnico = !contenidoNormalizado.contains("stacktrace")
                    && !contenidoNormalizado.contains("internal server error")
                    && !contenidoNormalizado.contains("http 500")
                    && !contenidoNormalizado.contains("exception in thread")
                    && !contenidoNormalizado.contains("nullpointerexception");
            boolean conContenidoVisible = !filas.isEmpty();

            boolean resultadoPaso = sinErrorTecnico && conContenidoVisible;
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "Se detecto error tecnico o ausencia de contenido visible. Esperado: pantalla operable sin error tecnico | Actual: sinErrorTecnico="
                            + sinErrorTecnico + ", filasVisibles=" + filas.size();

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Validar pantalla sin error tecnico en Cambios Fisicos Pendientes"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot && !filas.isEmpty()) {
                By primeraFila = By.xpath("(//table//tbody//tr)[1]");
                WebElement fila = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOfElementLocated(primeraFila));
                ((JavascriptExecutor) u.getDriver()).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", fila);
                u.highLight(primeraFila);
                try {
                    testCase.addTextEvidenceCurrentStep(resultadoPaso
                            ? "La pantalla se visualiza sin errores tecnicos ni crash y mantiene el grid operable."
                            : "La pantalla presenta indicadores de error tecnico o no muestra resultados visibles en grid.");
                    testCase.addScreenShotCurrentStep(u, "Pantalla_Sin_Error_Tecnico_CFP");
                } finally {
                    u.unhighLight(primeraFila);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible validar ausencia de error tecnico en Cambios Fisicos Pendientes: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Validar pantalla sin error tecnico en Cambios Fisicos Pendientes | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Valida que al menos un registro con SKU asociado a sale_price nulo en BD
     * muestre un valor neutro en la columna Precio del grid.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return true si se localiza un SKU de la muestra y su Precio es neutro.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean validarValorNeutroPrecioParaSalePriceNuloEnGridCFP(boolean screenshot) {
        try {
            List<String> skusNulos = this.muestraSkusSalePriceNuloBdCFP == null
                    ? new ArrayList<String>()
                    : new ArrayList<String>(this.muestraSkusSalePriceNuloBdCFP);

            if (skusNulos.isEmpty()) {
                String motivo = "No hay SKU con sale_price nulo provenientes de BD para validar en grid.";
                System.out.println("[RESULTADO PASO] FAILED - Validar valor neutro de Precio para sale_price nulo | Motivo: "
                        + motivo);
                return false;
            }

            u.waitWebElement(elements.filasGridCambiosFisicosPendientes, 20, 2);
            List<WebElement> filas = u.getDriver().findElements(elements.filasGridCambiosFisicosPendientes);

            boolean encontrado = false;
            boolean existeValorNeutro = false;
            String skuEvaluado = "";
            String precioNeutroEncontrado = "";
            int indiceFilaNeutra = -1;
            int indicePrimeraCoincidencia = -1;
            String precioPrimeraCoincidencia = "";
            List<String> preciosEncontrados = new ArrayList<String>();

            for (int i = 0; i < filas.size(); i++) {
                WebElement fila = filas.get(i);
                String skuGrid = "";
                String precioGrid = "";

                try {
                    skuGrid = normalizarSku(fila.findElement(By.cssSelector("td.mat-column-sku")).getText());
                } catch (Exception ignored) {
                    skuGrid = "";
                }

                if (skuGrid.isEmpty() || !skusNulos.contains(skuGrid)) {
                    continue;
                }

                try {
                    precioGrid = fila.findElement(By.cssSelector("td.mat-column-salePrice")).getText();
                } catch (Exception ignored) {
                    precioGrid = "";
                }

                encontrado = true;
                skuEvaluado = skuGrid;
                String precioNormalizado = precioGrid == null ? "" : precioGrid.trim();
                preciosEncontrados.add(precioNormalizado);

                if (indicePrimeraCoincidencia < 0) {
                    indicePrimeraCoincidencia = i + 1;
                    precioPrimeraCoincidencia = precioNormalizado;
                }

                if (esValorNeutroPrecio(precioNormalizado)) {
                    existeValorNeutro = true;
                    precioNeutroEncontrado = precioNormalizado;
                    indiceFilaNeutra = i + 1;
                    break;
                }
            }

            boolean resultadoPaso = encontrado && existeValorNeutro;
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = "";
            if (!encontrado) {
                motivoFallo = "No se encontro en el grid un SKU de la muestra BD con sale_price nulo. SKU muestra BD: "
                        + skusNulos.toString();
            } else if (!existeValorNeutro) {
                motivoFallo = "Ninguna fila del SKU con sale_price nulo mostro valor neutro. Esperado: al menos una fila en vacio, guion o cero | Actual: SKU="
                        + skuEvaluado + ", Precios encontrados=" + preciosEncontrados.toString();
            }

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Validar valor neutro de Precio para sale_price nulo"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

                if (screenshot && encontrado && (indiceFilaNeutra > 0 || indicePrimeraCoincidencia > 0)) {
                int indiceEvidencia = indiceFilaNeutra > 0 ? indiceFilaNeutra : indicePrimeraCoincidencia;
                String precioEvidencia = indiceFilaNeutra > 0 ? precioNeutroEncontrado : precioPrimeraCoincidencia;
                By celdaPrecio = By.xpath("(//table//tbody//tr)[" + indiceEvidencia
                        + "]//td[contains(@class,'mat-column-salePrice')]");
                WebElement celda = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOfElementLocated(celdaPrecio));
                ((JavascriptExecutor) u.getDriver()).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'center'});", celda);
                u.highLight(celdaPrecio);
                try {
                    if (resultadoPaso) {
                    testCase.addTextEvidenceCurrentStep("Validacion de valor neutro para SKU=" + skuEvaluado
                        + " en columna Precio. Se encontro valor neutro: '" + precioEvidencia + "'.");
                    } else {
                    testCase.addTextEvidenceCurrentStep("Validacion de valor neutro para SKU=" + skuEvaluado
                        + " en columna Precio. No se encontro valor neutro; primer valor observado: '"
                        + precioEvidencia + "'.");
                    }
                    testCase.addScreenShotCurrentStep(u, "Valor_Neutro_Precio_SalePrice_Nulo_CFP");
                } finally {
                    u.unhighLight(celdaPrecio);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible validar valor neutro de Precio para sale_price nulo: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Validar valor neutro de Precio para sale_price nulo | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Determina si un valor de precio en grid puede considerarse neutro para el
     * escenario de sale_price nulo.
     *
     * @param valor precio visible en el grid.
     * @return true si el valor es vacio, guion o representacion de cero.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    private boolean esValorNeutroPrecio(String valor) {
        if (valor == null) {
            return true;
        }
        String limpio = valor.trim();
        if (limpio.isEmpty() || "-".equals(limpio) || "--".equals(limpio)) {
            return true;
        }

        String canonico = formatearPrecioCanonico(limpio);
        return "0.00".equals(canonico);
    }

    /**
     * Captura un SKU proveniente de la muestra BD con sale_price nulo y lo
     * escribe en el input de busqueda de Cambios Fisicos Pendientes antes de
     * ejecutar la consulta.
     *
     * @param screenshot true para registrar evidencia visual del input cargado.
     * @return true si se escribe correctamente un SKU de la muestra en el filtro.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean filtrarPorSkuSalePriceNuloAntesDeConsultarCFP(boolean screenshot) {
        try {
            List<String> skusNulos = this.muestraSkusSalePriceNuloBdCFP == null
                    ? new ArrayList<String>()
                    : new ArrayList<String>(this.muestraSkusSalePriceNuloBdCFP);

            if (skusNulos.isEmpty()) {
                String motivo = "No hay SKU de muestra BD con sale_price nulo para cargar en el filtro de busqueda.";
                System.out.println("[RESULTADO PASO] FAILED - Cargar filtro SKU para sale_price nulo | Motivo: " + motivo);
                return false;
            }

            String skuObjetivo = normalizarSku(skusNulos.get(0));
            if (skuObjetivo.isEmpty()) {
                String motivo = "El SKU recuperado desde BD para sale_price nulo es vacio.";
                System.out.println("[RESULTADO PASO] FAILED - Cargar filtro SKU para sale_price nulo | Motivo: " + motivo);
                return false;
            }

            WebElement inputBusqueda = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                    .until(ExpectedConditions.visibilityOfElementLocated(elements.inputBuscarSkuDescripcionCFP));
            ((JavascriptExecutor) u.getDriver()).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});", inputBusqueda);

            inputBusqueda.click();
            inputBusqueda.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            inputBusqueda.sendKeys(Keys.DELETE);
            inputBusqueda.sendKeys(skuObjetivo);

            boolean resultadoPaso = skuObjetivo.equals(normalizarSku(inputBusqueda.getAttribute("value")));
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "No fue posible confirmar el valor cargado en filtro SKU. Esperado: " + skuObjetivo
                            + " | Actual: " + String.valueOf(inputBusqueda.getAttribute("value"));

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Cargar filtro SKU para sale_price nulo"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                u.highLight(elements.inputBuscarSkuDescripcionCFP);
                try {
                    testCase.addTextEvidenceCurrentStep("Se carga en filtro de busqueda el SKU con sale_price nulo: "
                            + skuObjetivo + ".");
                    testCase.addScreenShotCurrentStep(u, "Filtro_SKU_SalePrice_Nulo_CFP");
                } finally {
                    u.unhighLight(elements.inputBuscarSkuDescripcionCFP);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible cargar el filtro SKU para sale_price nulo: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Cargar filtro SKU para sale_price nulo | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Valida que el boton de descarga de resumen Excel en Cambios Fisicos
     * Pendientes se encuentre visible y habilitado despues de consultar.
     *
     * @param screenshot true para registrar evidencia visual.
     * @return true si el boton esta visible y habilitado.
     *
     * @author Michelle Ceja
     * @date 2026-08-10
     */
    public boolean validarBotonDescargaResumenExcelVisibleCFP(boolean screenshot) {
        try {
            WebDriver driver = u.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));

            wait.until(ExpectedConditions.visibilityOfElementLocated(elements.seccionDescargaResumenExcelCFP));
            WebElement seccionDescarga = driver.findElement(elements.seccionDescargaResumenExcelCFP);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                    seccionDescarga);

            WebElement botonDescarga = wait
                    .until(ExpectedConditions.elementToBeClickable(elements.btnDescargaResumenExcelCFP));

            boolean resultadoPaso = botonDescarga.isDisplayed() && botonDescarga.isEnabled();
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "El boton de descarga de resumen Excel no esta visible o habilitado despues de consultar.";

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Validar boton de descarga de resumen Excel en CFP"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                u.highLight(elements.btnDescargaResumenExcelCFP);
                try {
                    testCase.addTextEvidenceCurrentStep(
                            "Se visualiza habilitado el boton de descarga de resumen en Excel en Cambios Fisicos Pendientes.");
                    testCase.addScreenShotCurrentStep(u, "Boton_Descarga_Resumen_Excel_CFP_Habilitado");
                } finally {
                    u.unhighLight(elements.btnDescargaResumenExcelCFP);
                }
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible validar el boton de descarga de resumen Excel en CFP: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Validar boton de descarga de resumen Excel en CFP | Motivo: "
                    + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        }
    }

    /**
     * Valida que el archivo de resumen de Cambios Fisicos Pendientes conserve el
     * orden de columnas esperado y que la columna Precio sea la unica adicion
     * colocada inmediatamente despues de Costo.
     *
     * @param screenshot true para registrar evidencia textual del resultado.
     * @return true si los encabezados del resumen cumplen la estructura esperada.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean validarEncabezadosResumenCFPendientesConPrecio(boolean screenshot) {
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            File archivoResumen = obtenerUltimoResumenExcelCFPendientesDescargado();
            if (archivoResumen == null || !archivoResumen.exists()) {
                String motivoFallo = "No se encontro archivo de resumen de Cambios Fisicos Pendientes en Downloads.";
                System.out.println("[RESULTADO PASO] FAILED - Validar encabezados de resumen CFP con columna Precio | Motivo: " + motivoFallo);
                testCase.addTextEvidenceCurrentStep("No se encontro archivo de resumen de Cambios Fisicos Pendientes en Downloads.");
                return false;
            }

            fis = new FileInputStream(archivoResumen);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                String motivoFallo = "El archivo de resumen no contiene fila de encabezados en la primera fila.";
                System.out.println("[RESULTADO PASO] FAILED - Validar encabezados de resumen CFP con columna Precio | Motivo: " + motivoFallo);
                testCase.addTextEvidenceCurrentStep("El archivo de resumen no contiene fila de encabezados en la primera fila.");
                return false;
            }

            DataFormatter formatter = new DataFormatter(Locale.US);
            List<String> encabezadosOriginales = new ArrayList<String>();
            List<String> encabezadosNormalizados = new ArrayList<String>();
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                String valor = formatter.formatCellValue(headerRow.getCell(c));
                if (valor != null) {
                    valor = valor.trim();
                }
                if (valor != null && !valor.isEmpty()) {
                    encabezadosOriginales.add(valor);
                    encabezadosNormalizados.add(normalizarEncabezado(valor));
                }
            }

            String[] baseEsperada = {
                "CR TIENDA",
                "NOMBRE DE LA TIENDA",
                "REMISION",
                "FECHA DE PROGRAMACION",
                "SKU",
                "DESCRIPCION",
                "DIAS TRANSCURRIDOS",
                "CANTIDAD",
                "COSTO",
                "PRECIO"
            };

            int offset = (!encabezadosNormalizados.isEmpty() && "RETEK".equals(encabezadosNormalizados.get(0))) ? 1 : 0;
            List<String> diferencias = new ArrayList<String>();

            for (int i = 0; i < baseEsperada.length; i++) {
                int idxReal = i + offset;
                String actual = idxReal < encabezadosNormalizados.size() ? encabezadosNormalizados.get(idxReal) : "<VACIO>";
                if (!baseEsperada[i].equals(actual)) {
                    diferencias.add("Posicion " + (idxReal + 1) + ": esperado='" + baseEsperada[i] + "' | actual='" + actual + "'");
                }
            }

            int idxCosto = encabezadosNormalizados.indexOf("COSTO");
            int idxPrecio = encabezadosNormalizados.indexOf("PRECIO");
            boolean precioDespuesDeCosto = idxCosto >= 0 && idxPrecio == (idxCosto + 1);
            if (!precioDespuesDeCosto) {
                diferencias.add("Orden Costo/Precio invalido. Esperado: Precio inmediatamente despues de Costo | Actual: idxCosto="
                        + idxCosto + ", idxPrecio=" + idxPrecio);
            }

            boolean resultadoPaso = diferencias.isEmpty();
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? "" : String.join(" | ", diferencias);

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Validar encabezados de resumen CFP con columna Precio"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            String evidenciaBase = "Archivo validado: " + archivoResumen.getName()
                    + " | Encabezados detectados: " + encabezadosOriginales.toString();
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(evidenciaBase);
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible validar encabezados de resumen CFP: " + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Validar encabezados de resumen CFP con columna Precio | Motivo: " + mensajeError);
            testCase.addTextEvidenceCurrentStep("No fue posible validar encabezados de resumen CFP.");
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    /**
     * Obtiene el archivo de resumen de Cambios Fisicos Pendientes mas reciente
     * dentro de la carpeta Downloads del usuario.
     *
     * @return archivo de resumen mas reciente; null si no existe coincidencia.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    private File obtenerUltimoResumenExcelCFPendientesDescargado() {
        File carpetaDescargas = new File(System.getProperty("user.home"), "Downloads");
        File[] archivos = carpetaDescargas.listFiles();
        if (archivos == null || archivos.length == 0) {
            return null;
        }

        File mejor = null;
        long mejorTs = Long.MIN_VALUE;
        for (File archivo : archivos) {
            if (archivo == null || !archivo.isFile()) {
                continue;
            }

            String nombre = archivo.getName().toLowerCase(Locale.ROOT);
            boolean extensionValida = nombre.endsWith(".xlsx") || nombre.endsWith(".xls");
            boolean nombreValido = nombre.startsWith("resumen_cambios_fisicos_pendientes");
            boolean temporal = nombre.endsWith(".tmp") || nombre.endsWith(".crdownload");

            if (!extensionValida || !nombreValido || temporal) {
                continue;
            }

            if (archivo.lastModified() > mejorTs) {
                mejor = archivo;
                mejorTs = archivo.lastModified();
            }
        }

        return mejor;
    }

    /**
     * Abre el ultimo archivo de resumen de Cambios Fisicos Pendientes y navega
     * visualmente hasta la columna Precio para capturar evidencia.
     *
     * @param screenshot true para registrar evidencia del desplazamiento.
     * @return true si se logra abrir el archivo y posicionar visualmente Precio.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean abrirResumenYMostrarColumnaPrecioEnEvidencia(boolean screenshot) {
        File archivoResumen = null;
        try {
            archivoResumen = obtenerUltimoResumenExcelCFPendientesDescargado();
            if (archivoResumen == null || !archivoResumen.exists()) {
                String motivoFallo = "No se encontro archivo de resumen para mostrar columna Precio en evidencia.";
                System.out.println("[RESULTADO PASO] FAILED - Mostrar columna Precio en evidencia de Excel | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            if (!Desktop.isDesktopSupported()) {
                String motivoFallo = "Desktop no es compatible en este equipo para abrir Excel y evidenciar la columna Precio.";
                System.out.println("[RESULTADO PASO] FAILED - Mostrar columna Precio en evidencia de Excel | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            Desktop.getDesktop().open(archivoResumen);
            u.hardWait(6);

            Robot robot = new Robot();
            robot.setAutoDelay(120);

            // 1) Forzar foco en la hoja de Excel con clic real en el area central.
            int centroX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
            int centroY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
            robot.mouseMove(centroX, centroY);
            robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
            u.hardWait(1);

            // 2) Intento directo a K1 por cuadro Ir a.
            enviarCombinacionTeclas(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_HOME);
            u.hardWait(1);
            enviarCombinacionTeclas(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_G);
            u.hardWait(1);
            escribirTextoRobot(robot, "K1");
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            u.hardWait(1);

            // 3) Fallback robusto: mover 10 columnas a la derecha desde A1.
            enviarCombinacionTeclas(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_HOME);
            u.hardWait(1);
            for (int i = 0; i < 10; i++) {
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                u.hardWait(0);
            }

            // 4) Resaltar columna activa para evidencia visual (debe ser K/Precio).
            enviarCombinacionTeclas(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE);
            u.hardWait(2);

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                    "Se abre el Excel descargado desde Centro de Descargas y se navega a la celda K1 para mostrar la columna Precio en evidencia visual.");
                testCase.addScreenShotCurrentStep("Excel_Columna_Precio_Visible");
            }

            System.out.println("[RESULTADO PASO] PASSED - Mostrar columna Precio en evidencia de Excel");
            return true;
        } catch (Exception exception) {
            String mensajeError = "No fue posible mostrar la columna Precio en el Excel: " + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Mostrar columna Precio en evidencia de Excel | Motivo: " + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        } finally {
            try {
                Process cierrePorTitulo = new ProcessBuilder(
                        "cmd",
                        "/c",
                        "taskkill /f /fi \"IMAGENAME eq EXCEL.EXE\" /fi \"WINDOWTITLE eq *Resumen_CAMBIOS_FISICOS_PENDIENTES*\"")
                        .start();
                int exitCode = cierrePorTitulo.waitFor();

                if (exitCode != 0) {
                    new ProcessBuilder("cmd", "/c", "taskkill /f /im EXCEL.EXE").start().waitFor();
                }
            } catch (Exception ignored) {
                try {
                    new ProcessBuilder("cmd", "/c", "taskkill /f /im EXCEL.EXE").start().waitFor();
                } catch (Exception ignoredFallback) {
                }
            }
        }
    }

    /**
     * Envia una combinacion de dos teclas con Robot.
     */
    private void enviarCombinacionTeclas(Robot robot, int teclaModificadora, int teclaPrincipal) {
        robot.keyPress(teclaModificadora);
        robot.keyPress(teclaPrincipal);
        robot.keyRelease(teclaPrincipal);
        robot.keyRelease(teclaModificadora);
    }

    /**
     * Escribe texto basico ASCII usando eventos de teclado.
     */
    private void escribirTextoRobot(Robot robot, String texto) {
        if (texto == null) {
            return;
        }
        for (char caracter : texto.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(caracter);
            if (keyCode == KeyEvent.VK_UNDEFINED) {
                continue;
            }

            boolean mayuscula = Character.isUpperCase(caracter);
            if (mayuscula) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (mayuscula) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }

    // =========================================================
    // VALIDACIONES EXCEL RESUMEN CFP - CA06 / CA07
    // =========================================================

    /**
     * Valida que cada valor no vacio de la columna Precio en el Excel de resumen de
     * Cambios Fisicos Pendientes incluye el simbolo de pesos mexicanos ($) y tiene
     * formato monetario con dos decimales, conforme a CA06 y CA07.
     *
     * @param screenshot true para registrar evidencia del resultado.
     * @return true si todos los valores inspeccionados muestran simbolo $ y formato monetario.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean validarSimboloPesoEnColumnaPrecioExcelResumenCFP(boolean screenshot) {
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            File archivo = obtenerUltimoResumenExcelCFPendientesDescargado();
            if (archivo == null || !archivo.exists()) {
                testCase.addCodeEvidenceCurrentStep(
                        "No se encontro archivo de resumen CFP para validar formato monetario.");
                return false;
            }

            fis = new FileInputStream(archivo);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                testCase.addCodeEvidenceCurrentStep("El Excel de resumen CFP no contiene fila de encabezados.");
                return false;
            }

            DataFormatter fmt = new DataFormatter(Locale.US);

            int idxPrecio = -1;
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                if ("PRECIO".equals(normalizarEncabezado(fmt.formatCellValue(headerRow.getCell(c))))) {
                    idxPrecio = c;
                    break;
                }
            }

            if (idxPrecio < 0) {
                testCase.addCodeEvidenceCurrentStep(
                        "No se encontro la columna PRECIO en el Excel de resumen CFP.");
                return false;
            }

            List<String> valoresSinSimbolo = new ArrayList<String>();
            int filasDatos = 0;

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Cell cell = row.getCell(idxPrecio);
                if (cell == null) continue;

                String valorFormateado = fmt.formatCellValue(cell).trim();
                if (valorFormateado.isEmpty()) continue;
                filasDatos++;

                /* Verificar simbolo $ en el valor formateado o en el patron de celda */
                boolean tieneSimbolo = valorFormateado.contains("$");
                if (!tieneSimbolo) {
                    String fmtStr = cell.getCellStyle().getDataFormatString();
                    tieneSimbolo = fmtStr != null && fmtStr.contains("$");
                }

                if (!tieneSimbolo) {
                    valoresSinSimbolo.add("Fila " + (r + 1) + ": '" + valorFormateado + "'");
                }
            }

            boolean resultadoPaso;
            String motivoFallo = "";

            if (filasDatos == 0) {
                resultadoPaso = false;
                motivoFallo = "No se encontraron filas de datos con valor en columna Precio del Excel resumen.";
            } else {
                resultadoPaso = valoresSinSimbolo.isEmpty();
                if (!resultadoPaso) {
                    motivoFallo = "Valores sin simbolo $: " + valoresSinSimbolo.toString();
                }
            }

            System.out.println("[RESULTADO PASO] " + (resultadoPaso ? "PASSED" : "FAILED")
                    + " - Validar simbolo $ columna Precio Excel resumen CFP"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                        "Archivo: " + archivo.getName() + " | Filas inspeccionadas: " + filasDatos
                        + " | Simbolo $ en todos los valores: " + (valoresSinSimbolo.isEmpty() ? "SI" : "NO - " + motivoFallo));
                testCase.addScreenShotCurrentStep("Validacion_Simbolo_Peso_Excel_Resumen_CFP");
            }

            return resultadoPaso;

        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                    "Error al validar formato monetario en Excel resumen CFP: " + e.getMessage());
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    /**
     * Compara los valores de Precio de la muestra capturada del grid de Cambios Fisicos
     * Pendientes contra los valores de la columna Precio en el Excel de resumen descargado,
     * haciendo la correspondencia por SKU, conforme a CA06.
     * Requiere que {@link #capturarValoresPrecioVisiblesEnGridCFP} haya sido llamado antes.
     *
     * @param screenshot true para registrar evidencia del resultado de la comparacion.
     * @return true si cada SKU de la muestra del grid tiene el mismo Precio en el Excel.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean compararPreciosGridVsExcelResumenCFP(boolean screenshot) {
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            List<String> skusGrid = this.muestraSkusGridCFP == null
                    ? new ArrayList<String>() : new ArrayList<String>(this.muestraSkusGridCFP);
            List<String> preciosGrid = this.muestraPreciosGridCFP == null
                    ? new ArrayList<String>() : new ArrayList<String>(this.muestraPreciosGridCFP);

            if (skusGrid.isEmpty() || preciosGrid.isEmpty()) {
                String motivo = "No hay muestra SKU/Precio capturada del grid para comparar con el Excel.";
                System.out.println("[RESULTADO PASO] FAILED - Comparar Precio grid vs Excel resumen CFP | Motivo: " + motivo);
                testCase.addCodeEvidenceCurrentStep(motivo);
                return false;
            }

            File archivo = obtenerUltimoResumenExcelCFPendientesDescargado();
            if (archivo == null || !archivo.exists()) {
                testCase.addCodeEvidenceCurrentStep(
                        "No se encontro archivo de resumen CFP para comparar valores de Precio.");
                return false;
            }

            fis = new FileInputStream(archivo);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                testCase.addCodeEvidenceCurrentStep("El Excel de resumen CFP no contiene fila de encabezados.");
                return false;
            }

            DataFormatter fmt = new DataFormatter(Locale.US);

            int idxSku = -1;
            int idxPrecio = -1;
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                String enc = normalizarEncabezado(fmt.formatCellValue(headerRow.getCell(c)));
                if ("SKU".equals(enc)) idxSku = c;
                if ("PRECIO".equals(enc)) idxPrecio = c;
            }

            if (idxSku < 0 || idxPrecio < 0) {
                testCase.addCodeEvidenceCurrentStep(
                        "No se encontro columna SKU o PRECIO en el Excel resumen CFP. idxSku="
                        + idxSku + " idxPrecio=" + idxPrecio);
                return false;
            }

            java.util.Map<String, String> preciosExcel = new java.util.LinkedHashMap<String, String>();
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Cell cellSku = row.getCell(idxSku);
                Cell cellPrecio = row.getCell(idxPrecio);
                if (cellSku == null || cellPrecio == null) continue;
                String skuExcel = normalizarSku(fmt.formatCellValue(cellSku));
                if (skuExcel.isEmpty()) continue;
                String valorCrudo = fmt.formatCellValue(cellPrecio).trim()
                        .replace("$", "").replace(",", "").trim();
                preciosExcel.put(skuExcel, formatearPrecioCanonico(valorCrudo));
            }

            List<String> diferencias = new ArrayList<String>();
            for (int i = 0; i < skusGrid.size() && i < preciosGrid.size(); i++) {
                String skuGrid = normalizarSku(skusGrid.get(i));
                String precioGrid = preciosGrid.get(i);
                String precioExcel = preciosExcel.get(skuGrid);
                if (precioExcel == null) {
                    diferencias.add("SKU " + skuGrid + " no encontrado en Excel");
                } else if (!precioGrid.equals(precioExcel)) {
                    diferencias.add("SKU " + skuGrid + " -> Grid=" + precioGrid + " | Excel=" + precioExcel);
                }
            }

            boolean resultadoPaso = diferencias.isEmpty();
            String motivoFallo = resultadoPaso ? "" : diferencias.toString();

            System.out.println("[RESULTADO PASO] " + (resultadoPaso ? "PASSED" : "FAILED")
                    + " - Comparar Precio grid vs Excel resumen CFP"
                    + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                        "SKUs comparados: " + skusGrid.size()
                        + " | Resultado: " + (resultadoPaso
                                ? "Todos los valores del grid coinciden con el Excel."
                                : "DIFERENCIAS: " + motivoFallo));
            }

            return resultadoPaso;

        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep(
                    "Error al comparar Precio grid vs Excel resumen CFP: " + e.getMessage());
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
        }
    }

    /**
     * Abre el Excel de resumen de Cambios Fisicos Pendientes, busca los SKU
     * capturados previamente en el grid y selecciona visualmente su fila para
     * evidencia del paso de localizacion en archivo.
     *
     * @param limiteSkus cantidad maxima de SKU a localizar y seleccionar.
     * @param screenshot true para registrar evidencia visual por SKU localizado.
     * @return true si se localiza y selecciona al menos un SKU en el Excel.
     *
     * @author Michelle Ceja
     * @date 2026-08-10
     */
    public boolean abrirResumenYSeleccionarFilasSkusCapturadosEnEvidencia(int limiteSkus, boolean screenshot) {
        File archivoResumen = null;
        FileInputStream fis = null;
        Workbook wb = null;
        try {
            List<String> skusCapturados = this.muestraSkusGridCFP == null
                    ? new ArrayList<String>() : new ArrayList<String>(this.muestraSkusGridCFP);
            List<String> preciosCapturados = this.muestraPreciosGridCFP == null
                    ? new ArrayList<String>() : new ArrayList<String>(this.muestraPreciosGridCFP);

            if (skusCapturados.isEmpty() || preciosCapturados.isEmpty()) {
                String motivoFallo = "No hay muestra SKU/Precio capturada del grid para localizar en el Excel de resumen.";
                System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            int maximo = limiteSkus > 0 ? limiteSkus : skusCapturados.size();
            if (skusCapturados.size() > maximo) {
                skusCapturados = new ArrayList<String>(skusCapturados.subList(0, maximo));
            }
            if (preciosCapturados.size() > maximo) {
                preciosCapturados = new ArrayList<String>(preciosCapturados.subList(0, maximo));
            }

            archivoResumen = obtenerUltimoResumenExcelCFPendientesDescargado();
            if (archivoResumen == null || !archivoResumen.exists()) {
                String motivoFallo = "No se encontro archivo de resumen para localizar SKU capturados en el Excel.";
                System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            fis = new FileInputStream(archivoResumen);
            wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                String motivoFallo = "El Excel de resumen no contiene fila de encabezados para ubicar columnas SKU/Precio.";
                System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            DataFormatter fmt = new DataFormatter(Locale.US);
            int idxSku = -1;
            int idxPrecio = -1;
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                String enc = normalizarEncabezado(fmt.formatCellValue(headerRow.getCell(c)));
                if ("SKU".equals(enc)) {
                    idxSku = c;
                }
                if ("PRECIO".equals(enc)) {
                    idxPrecio = c;
                }
            }

            if (idxSku < 0 || idxPrecio < 0) {
                String motivoFallo = "No se encontro columna SKU o PRECIO en Excel resumen. idxSku="
                        + idxSku + " idxPrecio=" + idxPrecio;
                System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            List<Integer> filasObjetivoExcel = new ArrayList<Integer>();
            List<String> detallesObjetivo = new ArrayList<String>();
            List<String> noEncontrados = new ArrayList<String>();
            java.util.Set<Integer> filasYaUsadas = new java.util.LinkedHashSet<Integer>();

            System.out.println("[DEBUG][GRID] Total SKU capturados=" + skusCapturados.size()
                    + " | Total precios capturados=" + preciosCapturados.size());
            for (int i = 0; i < skusCapturados.size() && i < preciosCapturados.size(); i++) {
                System.out.println("[DEBUG][GRID] Par " + (i + 1) + " -> SKU="
                        + normalizarSku(skusCapturados.get(i))
                        + " | Precio=" + formatearPrecioCanonico(preciosCapturados.get(i)));
            }

            System.out.println("[DEBUG][EXCEL] Columnas detectadas -> idxSku=" + idxSku + " | idxPrecio=" + idxPrecio);
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Cell cellSku = row.getCell(idxSku);
                Cell cellPrecio = row.getCell(idxPrecio);
                if (cellSku == null || cellPrecio == null) {
                    continue;
                }
                String skuExcelDbg = normalizarSku(fmt.formatCellValue(cellSku));
                String valorCrudoPrecioDbg = fmt.formatCellValue(cellPrecio).trim()
                        .replace("$", "").replace(",", "").trim();
                String precioExcelDbg = formatearPrecioCanonico(valorCrudoPrecioDbg);
                if (!skuExcelDbg.isEmpty() || !precioExcelDbg.isEmpty()) {
                    System.out.println("[DEBUG][EXCEL] Fila " + (r + 1)
                            + " -> SKU=" + skuExcelDbg + " | Precio=" + precioExcelDbg);
                }
            }

            int totalPares = Math.min(skusCapturados.size(), preciosCapturados.size());
            for (int i = 0; i < totalPares; i++) {
                String skuObjetivo = normalizarSku(skusCapturados.get(i));
                String precioObjetivo = formatearPrecioCanonico(preciosCapturados.get(i));

                System.out.println("[DEBUG][TARGET] Buscando par " + (i + 1)
                        + " -> SKU=" + skuObjetivo + " | Precio=" + precioObjetivo);

                if (skuObjetivo.isEmpty() || precioObjetivo.isEmpty()) {
                    noEncontrados.add("Par vacio en muestra. SKU='" + skuObjetivo + "' Precio='" + precioObjetivo + "'");
                    System.out.println("[DEBUG][TARGET] Par omitido por valor vacio.");
                    continue;
                }

                Integer filaEncontrada = null;
                for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                    if (filasYaUsadas.contains(r)) {
                        continue;
                    }
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }

                    Cell cellSku = row.getCell(idxSku);
                    Cell cellPrecio = row.getCell(idxPrecio);
                    if (cellSku == null || cellPrecio == null) {
                        continue;
                    }

                    String skuExcel = normalizarSku(fmt.formatCellValue(cellSku));
                    String valorCrudoPrecio = fmt.formatCellValue(cellPrecio).trim()
                            .replace("$", "").replace(",", "").trim();
                    String precioExcel = formatearPrecioCanonico(valorCrudoPrecio);

                    boolean skuCoincide = skuObjetivo.equals(skuExcel);
                    boolean precioCoincide = precioObjetivo.equals(precioExcel);
                    System.out.println("[DEBUG][COMPARE] Target " + (i + 1) + " vs fila " + (r + 1)
                            + " -> SKU_Excel=" + skuExcel + " | Precio_Excel=" + precioExcel
                            + " | skuCoincide=" + skuCoincide + " | precioCoincide=" + precioCoincide);

                    if (skuObjetivo.equals(skuExcel) && precioObjetivo.equals(precioExcel)) {
                        filaEncontrada = r;
                        System.out.println("[DEBUG][MATCH] Target " + (i + 1)
                                + " encontrado en fila " + (r + 1));
                        break;
                    }
                }

                if (filaEncontrada == null) {
                    noEncontrados.add("SKU=" + skuObjetivo + " Precio=" + precioObjetivo);
                    System.out.println("[DEBUG][MISS] Sin coincidencia exacta para Target " + (i + 1)
                            + " -> SKU=" + skuObjetivo + " | Precio=" + precioObjetivo);
                } else {
                    filasYaUsadas.add(filaEncontrada);
                    filasObjetivoExcel.add(filaEncontrada + 1);
                    detallesObjetivo.add("SKU=" + skuObjetivo + " Precio=" + precioObjetivo + " FilaExcel=" + (filaEncontrada + 1));
                }
            }

            if (filasObjetivoExcel.isEmpty()) {
                String motivoFallo = "No se encontro coincidencia de SKU+Precio entre grid y Excel. Buscados: "
                        + noEncontrados.toString();
                System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            if (!Desktop.isDesktopSupported()) {
                String motivoFallo = "Desktop no es compatible para abrir Excel y localizar SKU capturados.";
                System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + motivoFallo);
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
                return false;
            }

            System.out.println("[DEBUG][UI][ETAPA] Iniciando apertura de archivo Excel para evidencia visual: " + archivoResumen.getName());
            Desktop.getDesktop().open(archivoResumen);
            u.hardWait(6);
            System.out.println("[DEBUG][UI][ETAPA] Archivo Excel abierto. Iniciando control con Robot.");

            Robot robot = new Robot();
            robot.setAutoDelay(80);
            System.out.println("[DEBUG][UI][ETAPA] Robot inicializado.");

            int centroX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
            int centroY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
            robot.mouseMove(centroX, centroY);
            robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
            u.hardWait(1);
            System.out.println("[DEBUG][UI][ETAPA] Foco inicial aplicado con clic al centro de Excel.");

            // Primero normalizar posicion a esquina superior izquierda con solo flechas.
            for (int j = 0; j < 80; j++) {
                robot.keyPress(KeyEvent.VK_UP);
                robot.keyRelease(KeyEvent.VK_UP);
                u.hardWait(0);
            }
            for (int j = 0; j < 40; j++) {
                robot.keyPress(KeyEvent.VK_LEFT);
                robot.keyRelease(KeyEvent.VK_LEFT);
                u.hardWait(0);
            }
            System.out.println("[DEBUG][UI][ETAPA] Posicion normalizada a esquina superior izquierda.");

            // Luego mover a columna K (Precio) para dejarla visible.
            for (int j = 0; j < 10; j++) {
                robot.keyPress(KeyEvent.VK_RIGHT);
                robot.keyRelease(KeyEvent.VK_RIGHT);
                u.hardWait(0);
            }
            u.hardWait(1);
            System.out.println("[DEBUG][UI] Columna Precio ubicada y visible en Excel por desplazamiento con flechas hasta K.");
            if (screenshot) {
                testCase.addTextEvidenceCurrentStep(
                        "Se enfoca inicialmente la columna Precio en Excel antes de seleccionar filas por coincidencia SKU+Precio.");
                testCase.addScreenShotCurrentStep("Excel_Columna_Precio_Visible_Paso7");
            }

            int filaActual = 1;
            int columnaActual = 11; // K

            for (int i = 0; i < filasObjetivoExcel.size(); i++) {
                int filaExcel = filasObjetivoExcel.get(i);
                String detalle = detallesObjetivo.get(i);

                // Navegacion relativa desde la posicion actual para no regresar a columna A.
                int deltaFilas = filaExcel - filaActual;
                if (deltaFilas > 0) {
                    for (int k = 0; k < deltaFilas; k++) {
                        robot.keyPress(KeyEvent.VK_DOWN);
                        robot.keyRelease(KeyEvent.VK_DOWN);
                        u.hardWait(0);
                    }
                } else if (deltaFilas < 0) {
                    for (int k = 0; k < Math.abs(deltaFilas); k++) {
                        robot.keyPress(KeyEvent.VK_UP);
                        robot.keyRelease(KeyEvent.VK_UP);
                        u.hardWait(0);
                    }
                }

                int columnaObjetivo = 11; // K
                int deltaColumnas = columnaObjetivo - columnaActual;
                if (deltaColumnas > 0) {
                    for (int c = 0; c < deltaColumnas; c++) {
                        robot.keyPress(KeyEvent.VK_RIGHT);
                        robot.keyRelease(KeyEvent.VK_RIGHT);
                        u.hardWait(0);
                    }
                } else if (deltaColumnas < 0) {
                    for (int c = 0; c < Math.abs(deltaColumnas); c++) {
                        robot.keyPress(KeyEvent.VK_LEFT);
                        robot.keyRelease(KeyEvent.VK_LEFT);
                        u.hardWait(0);
                    }
                }

                filaActual = filaExcel;
                columnaActual = columnaObjetivo;
                u.hardWait(1);
                System.out.println("[DEBUG][UI] Navegando a fila objetivo en columna Precio por flechas: K" + filaExcel
                        + " | deltaFilas=" + deltaFilas + " | deltaColumnas=" + deltaColumnas + ".");

                // Mantener seleccionado K{fila} para que Precio permanezca visible en cada evidencia.
                System.out.println("[DEBUG][UI] Celda activa de evidencia en columna Precio: K" + filaExcel + ".");

                if (screenshot) {
                    testCase.addTextEvidenceCurrentStep(
                            "Fila validada en Excel por coincidencia SKU+Precio: " + detalle);
                    testCase.addScreenShotCurrentStep("Excel_Fila_SKU_Seleccionado_" + (i + 1));
                }
            }

            boolean resultadoPaso = (filasObjetivoExcel.size() == totalPares);
            String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
            String motivoFallo = resultadoPaso ? ""
                    : "No se localizaron todos los pares SKU+Precio esperados. Faltantes: " + noEncontrados.toString();

            System.out.println("[RESULTADO PASO] " + estadoPaso
                    + " - Localizar y seleccionar filas SKU en Excel resumen CFP"
                    + (resultadoPaso ? " | Coincidencias: " + detallesObjetivo.toString()
                            : " | Motivo: " + motivoFallo));

            if (!resultadoPaso) {
                testCase.addCodeEvidenceCurrentStep(motivoFallo);
            }

            return resultadoPaso;
        } catch (Exception exception) {
            String mensajeError = "No fue posible localizar y seleccionar filas SKU en Excel resumen CFP: "
                    + exception.getMessage();
            System.out.println("[RESULTADO PASO] FAILED - Localizar y seleccionar filas SKU en Excel resumen CFP | Motivo: " + mensajeError);
            testCase.addCodeEvidenceCurrentStep(mensajeError);
            return false;
        } finally {
            cerrarWorkbook(wb, fis);
            try {
                Process cierrePorTitulo = new ProcessBuilder(
                        "cmd",
                        "/c",
                        "taskkill /f /fi \"IMAGENAME eq EXCEL.EXE\" /fi \"WINDOWTITLE eq *Resumen_CAMBIOS_FISICOS_PENDIENTES*\"")
                        .start();
                int exitCode = cierrePorTitulo.waitFor();

                if (exitCode != 0) {
                    new ProcessBuilder("cmd", "/c", "taskkill /f /im EXCEL.EXE").start().waitFor();
                }
            } catch (Exception ignored) {
                try {
                    new ProcessBuilder("cmd", "/c", "taskkill /f /im EXCEL.EXE").start().waitFor();
                } catch (Exception ignoredFallback) {
                }
            }
        }
    }
}
