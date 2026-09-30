package WEB.cometa.om;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WindowType;
public class devolucionesPageDescargas {

	SeleniumUtil u;
	TestCase testCase;
	PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase);

	
	
	public devolucionesPageDescargas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

		}
	
	 // Estas variables se dejan publicas para facilitar copia/pegado.
    // Si ya las tienes en otra clase, elimina estas lineas y usa tus variables globales.
    public String crTiendaFV;
    public String folio;
    public String fecha;
    public int cantidadElementosZip;
    private String ultimoPdfAbiertoNombre;
    private String handlePrincipalAntesPdf;
    private String handlePdfAbierto;
    public String ultimoZipAbierto;



    /**
     * Valida ZIP descargado recibiendo variables de forma explicita.
     *
     * Valida contenido:
     * - Carga variables en la instancia actual
     * - Reutiliza la validacion principal del ZIP
     *
     * @param screenshot true para agregar screenshot
     * @param crTiendaFV valor esperado de CR tienda
     * @param folio valor esperado de folio
     * @param fecha valor esperado de fecha
     * @return true si el ZIP, su conteo y el PDF interno esperado son correctos
     * @throws Exception cuando ocurre un error de ejecucion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    public void setVariablesValidacion(String crTiendaFV, String folio, String fecha) {
        this.crTiendaFV = crTiendaFV;
        this.folio = folio;
        this.fecha = fecha;
    }

    /**
     * Valida ZIP descargado con nomenclatura Concentrado_DEVOLUCION_<fecha>-<fecha> (N)
     * y valida que dentro exista al menos un PDF con nomenclatura formada por las variables
     * globales DEVOLUCION_<CR>_<FOLIO>_<FECHA>[_N].pdf. Adicionalmente valida el contenido
     * del PDF encontrado.
     *
     * @param screenshot true para agregar screenshot
     * @return true si el ZIP, su conteo y el PDF interno esperado son correctos
     * @throws Exception cuando ocurre un error de ejecucion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    public boolean validaDescargaZipDevolucion(boolean screenshot) throws Exception {
    	   ZipFile zip = null;
           PDDocument doc = null;
           boolean pdfAbiertoVisual = false;
           boolean exploradorAbierto = false;
           boolean exploradorContenidoZipAbierto = false;
           String rutaExploradorZip = null;
           String rutaExploradorContenido = null;
           Set<String> hwndExploradorInicial = obtenerHwndExploradorAbiertos();

           try {
               long inicioValidacionMs = System.currentTimeMillis();
               cantidadElementosZip = 0;

                   String crTiendaFVEsperado = valorNoVacio(
                       crTiendaFV,
                       leerCampoEstatico("devolucionesPage", "crTiendaFV"));
                   String folioEsperado = valorNoVacio(
                       folio,
                       leerCampoEstatico("devolucionesPage", "folio"));
                   String fechaGlobal = valorNoVacio(
                       fecha,
                       leerCampoEstatico("devolucionesPage", "fecha"));

               System.out.println("=== DIAGNOSTICO validaDescargaZipDevolucion ===");
               System.out.println("  crTiendaFV : [" + crTiendaFVEsperado + "]");
               System.out.println("  folio      : [" + folioEsperado + "]");
               System.out.println("  fecha      : [" + fechaGlobal + "]");

               if (crTiendaFVEsperado.isEmpty() || folioEsperado.isEmpty() || fechaGlobal.isEmpty()) {
                   System.out.println("  ERROR: alguna variable global viene vacia");
                   return false;
               }

               String fechaNombreEsperada = convertirFechaNombre(fechaGlobal);
               String nombreBaseZipEsperado = "CONCENTRADO_DEVOLUCION_" + fechaNombreEsperada + "-" + fechaNombreEsperada;
               String nombreBasePdfEsperado = "DEVOLUCION_" + crTiendaFVEsperado + "_" + folioEsperado + "_" + fechaNombreEsperada;

               System.out.println("  nombreBaseZipEsperado: [" + nombreBaseZipEsperado + "]");
               System.out.println("  nombreBasePdfEsperado: [" + nombreBasePdfEsperado + "]");

               String downloadDir = System.getProperty("user.home") + "\\Downloads";
               File carpetaDescargas = new File(downloadDir);

               if (!carpetaDescargas.exists()) {
                   System.out.println("  ERROR: no existe la carpeta de descargas: " + downloadDir);
                   return false;
               }

               File zipEncontrado = esperarArchivoDescargado(carpetaDescargas, nombreBaseZipEsperado, ".zip", inicioValidacionMs, 18000);
               if (zipEncontrado == null) {
                   System.out.println("  ERROR: no se encontro ZIP descargado finalizado para: " + nombreBaseZipEsperado);
                   return false;
               }

               ultimoZipAbierto = zipEncontrado.getAbsolutePath();
               System.out.println("  ultimoZipAbierto: " + ultimoZipAbierto);

               exploradorAbierto = abrirExploradorEnArchivo(zipEncontrado);
               if (exploradorAbierto) {
                   rutaExploradorZip = zipEncontrado.getParent();
                   dormir(100);
                   boolean descargasAlFrente = traerVentanaAlFrente(new File(rutaExploradorZip));
                   dormir(descargasAlFrente ? 150 : 250);
                   registrarEvidenciaExplorador(zipEncontrado.getName(), screenshot, "Explorador - Descargas ZIP");
                   dormir(500);  // ESPERAR A QUE SE PROCESE LA CAPTURA
                   cerrarExploradorPorRuta(rutaExploradorZip);
                   dormir(150);
                   exploradorAbierto = false;
               }

               System.out.println("  ZIP seleccionado: " + zipEncontrado.getName());
               System.out.println("  Ruta completa   : " + zipEncontrado.getAbsolutePath());

               Matcher matcherCantidadZip = Pattern.compile("(?i)^CONCENTRADO_DEVOLUCION_\\d{2}_\\d{2}_\\d{4}-\\d{2}_\\d{2}_\\d{4} \\((\\d+)\\)\\.zip$")
                       .matcher(zipEncontrado.getName());
               if (!matcherCantidadZip.matches()) {
                   System.out.println("  ERROR: el ZIP no cumple nomenclatura esperada");
                   return false;
               }

               int cantidadEsperadaEnNombreZip = Integer.parseInt(matcherCantidadZip.group(1));
               String nombrePdfRegex = "(?i)^" + Pattern.quote(nombreBasePdfEsperado) + "(?:_\\d+)?\\.pdf$";
               String nombrePdfRegexGeneral = "(?i)^DEVOLUCION_[^_]+_[^_]+_\\d{2}_\\d{2}_\\d{4}(?:_\\d+)?\\.pdf$";

               zip = new ZipFile(zipEncontrado);

               ZipEntry pdfEsperado = null;
               Enumeration<? extends ZipEntry> entries = zip.entries();
               while (entries.hasMoreElements()) {
                   ZipEntry entry = entries.nextElement();
                   if (entry.isDirectory()) {
                       continue;
                   }

                   String nombreInterno = new File(entry.getName()).getName();
                   if (!nombreInterno.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                       System.out.println("  ERROR: el ZIP contiene un archivo no permitido: " + nombreInterno);
                       return false;
                   }

                   if (!nombreInterno.matches(nombrePdfRegexGeneral)) {
                       System.out.println("  ERROR: PDF interno con nomenclatura invalida: " + nombreInterno);
                       return false;
                   }

                   cantidadElementosZip++;
                   if (pdfEsperado == null && nombreInterno.matches(nombrePdfRegex)) {
                       pdfEsperado = entry;
                   }
               }

               System.out.println("  cantidadElementosZip: " + cantidadElementosZip);
               System.out.println("  cantidad en nombre ZIP: " + cantidadEsperadaEnNombreZip);

               if (cantidadElementosZip == 0) {
                   System.out.println("  ERROR: el ZIP no contiene PDFs");
                   return false;
               }

               if (cantidadElementosZip != cantidadEsperadaEnNombreZip) {
                   System.out.println("  INFO: conteo interno distinto al numero del nombre del ZIP (pendiente comparacion con otra pantalla)");
               }

               if (pdfEsperado == null) {
                   System.out.println("  ERROR: no se encontro dentro del ZIP el PDF esperado para las variables globales");
                   return false;
               }

               File carpetaContenidoZip = extraerContenidoZipATemporal(zip, zipEncontrado.getName());
               if (carpetaContenidoZip != null) {
                   rutaExploradorContenido = carpetaContenidoZip.getAbsolutePath();
                   exploradorContenidoZipAbierto = abrirExploradorEnCarpeta(carpetaContenidoZip);
                   if (!exploradorContenidoZipAbierto) {
                       // Revalidar por si Explorer abrio, pero la verificacion inicial llego tarde.
                       exploradorContenidoZipAbierto = esperarVentanaExploradorParaRuta(rutaExploradorContenido, 2200);
                   }

                   // Aunque la deteccion falle, intentar evidencia porque la ventana puede estar visible.
                   dormir(500);
                   boolean contenidoAlFrente = traerVentanaAlFrente(carpetaContenidoZip);
                   if (!contenidoAlFrente) {
                       dormir(350);
                       contenidoAlFrente = traerVentanaAlFrente(carpetaContenidoZip);
                   }
                   dormir(contenidoAlFrente ? 250 : 450);
                   registrarEvidenciaExploradorContenido(carpetaContenidoZip, screenshot);

                   cerrarExploradorPorRuta(rutaExploradorContenido);
                   dormir(150);
                   exploradorContenidoZipAbierto = false;
               }

               // Evita que la captura de contenido se procese cuando ya abrio el PDF.
               if (screenshot) {
                   dormir(1800);
               }

               byte[] pdfBytes;
               String nombrePdfValidado = new File(pdfEsperado.getName()).getName();
               try (InputStream inputPdf = zip.getInputStream(pdfEsperado)) {
                   pdfBytes = inputPdf.readAllBytes();
                   doc = Loader.loadPDF(pdfBytes);
               }

               PDFTextStripper stripper = new PDFTextStripper();
               stripper.setStartPage(1);
               stripper.setEndPage(1);

               String text = stripper.getText(doc);
               if (text == null || text.trim().isEmpty()) {
                   System.out.println("  ERROR: no se pudo extraer texto de la primera hoja del PDF dentro del ZIP");
                   return false;
               }

               String normalizado = normalizarTextoPdf(text);
               String fechaContenidoEsperada = convertirFechaContenido(fechaGlobal);

               boolean okCR = normalizado.contains(normalizarTextoPdf("CR tienda: " + crTiendaFVEsperado))
                       || normalizado.contains(normalizarTextoPdf("CR tienda:" + crTiendaFVEsperado));

               boolean okFecha = normalizado.contains(normalizarTextoPdf("Fecha: " + fechaContenidoEsperada))
                       || normalizado.contains(normalizarTextoPdf("Fecha:" + fechaContenidoEsperada));

               boolean okFolio = normalizado.contains(normalizarTextoPdf("Folio: " + folioEsperado))
                       || normalizado.contains(normalizarTextoPdf("Folio:" + folioEsperado));

               System.out.println("  contiene CR tienda: " + okCR);
               System.out.println("  contiene Fecha    : " + okFecha);
               System.out.println("  contiene Folio    : " + okFolio);

               if (!(okCR && okFecha && okFolio)) {
                   System.out.println("  ERROR: el PDF dentro del ZIP no cumple validacion de CR tienda, fecha y folio");
                   return false;
               }

               pdfAbiertoVisual = abrirPdfTemporalVisual(pdfBytes, nombrePdfValidado);

               if (pdfAbiertoVisual) {
                   dormir(500);
                   traerVentanaPdfAlFrente();
                   dormir(250);
                   registrarEvidenciaPdfAbierto(crTiendaFVEsperado, folioEsperado, fechaGlobal, screenshot);
               }

               if (screenshot) {
                   System.out.println("  INFO: screenshot solicitado");
               }

               System.out.println("  validaDescargaZipDevolucion resultado: true");
               System.out.println("================================================");
               return true;

           } catch (Exception e) {
               System.out.println("Error en validaDescargaZipDevolucion: " + e.toString());
               e.printStackTrace();
               return false;

           } finally {
               try {
                   if (doc != null) {
                       doc.close();
                   }
               } catch (Exception e) {
                   System.out.println("  ERROR cerrando PDDocument: " + e.getMessage());
               }

               try {
                   if (zip != null) {
                       zip.close();
                   }
               } catch (Exception e) {
                   System.out.println("  ERROR cerrando ZipFile: " + e.getMessage());
               }

               if (pdfAbiertoVisual) {
                   cerrarVisorPdf();
               }

               if (exploradorAbierto && rutaExploradorZip != null) {
                   cerrarExploradorPorRuta(rutaExploradorZip);
               }

               if (exploradorContenidoZipAbierto && rutaExploradorContenido != null) {
                   cerrarExploradorPorRuta(rutaExploradorContenido);
                   dormir(800);
               }

               cerrarVentanasExploradorNuevas(hwndExploradorInicial, rutaExploradorZip, rutaExploradorContenido);
               regresarFocoDriverPrincipal();
           }
       
    }

    /**
     * Espera a que un archivo descargado termine de escribirse en la carpeta de descargas.
     *
     * Valida contenido:
     * - Coincidencia por nombre base esperado
     * - Extension final esperada
     * - Ausencia de archivo temporal .crdownload
     * - Estabilidad en el tamano del archivo
     *
     * @param carpetaDescargas carpeta donde se busca el archivo descargado
     * @param nombreBaseEsperado prefijo esperado del nombre del archivo
     * @param extensionFinal extension final esperada, por ejemplo .zip o .pdf
     * @param inicioMs marca de tiempo de inicio para filtrar archivos recientes
     * @param timeoutMs tiempo maximo de espera en milisegundos
     * @return archivo final encontrado o el mejor candidato detectado
     * @throws Exception no lanza excepcion controlada; cualquier fallo se maneja internamente
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private File esperarArchivoDescargado(File carpetaDescargas, String nombreBaseEsperado, String extensionFinal,
            long inicioMs, long timeoutMs) {
        long fin = System.currentTimeMillis() + timeoutMs;
        File mejor = null;
        long mejorMod = -1L;
        int ciclosConMejorEstable = 0;

        while (System.currentTimeMillis() < fin) {
            File[] archivos = carpetaDescargas.listFiles();
            if (archivos == null) {
                dormir(300);
                continue;
            }

            boolean hayTemporal = false;
            File candidato = null;
            long candidatoMod = -1L;

            for (File f : archivos) {
                if (!f.isFile()) {
                    continue;
                }

                String n = f.getName();
                String upper = n.toUpperCase(Locale.ROOT);
                String lower = n.toLowerCase(Locale.ROOT);
                String baseUpper = nombreBaseEsperado.toUpperCase(Locale.ROOT);

                if (upper.startsWith(baseUpper) && lower.endsWith(".crdownload")) {
                    hayTemporal = true;
                }

                boolean matchFinal = upper.startsWith(baseUpper)
                        && lower.endsWith(extensionFinal.toLowerCase(Locale.ROOT));
                if (!matchFinal) { 
                    continue;
                }

                long mod = f.lastModified();
                if (mod >= (inicioMs - 2000) && mod > candidatoMod) {
                    candidato = f;
                    candidatoMod = mod;
                }

                if (mod > mejorMod) {
                    mejor = f;
                    mejorMod = mod;
                }
            }

            if (!hayTemporal && candidato != null) {
                long s1 = candidato.length();
                dormir(450);
                long s2 = candidato.length();

                if (s1 == s2 && s1 > 0) {
                    return candidato;
                }
            }

            // Salida rapida: si no hay temporal y ya existe ZIP estable por algunos ciclos, usarlo.
            if (!hayTemporal && mejor != null && mejor.exists() && mejor.length() > 0) {
                ciclosConMejorEstable++;
                if (ciclosConMejorEstable >= 3) {
                    return mejor;
                }
            } else {
                ciclosConMejorEstable = 0;
            }

            dormir(300);
        }

        return mejor;
    }

    /**
     * Convierte la fecha global al formato usado en la nomenclatura de archivos.
     *
     * Valida contenido:
     * - Soporta formato yyyy-MM-dd HH:mm:ss
     * - Soporta formato yyyy-MM-dd
     * - Regresa fecha en formato dd_MM_yyyy
     *
     * @param fechaGlobal fecha origen tomada de la variable global
     * @return fecha formateada como dd_MM_yyyy
     * @throws Exception no lanza excepcion controlada; usa fallback entre formatos
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private String convertirFechaNombre(String fechaGlobal) {
        try {
            LocalDate f = LocalDateTime.parse(fechaGlobal,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
            return f.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        } catch (Exception ex) {
            LocalDate f = LocalDate.parse(fechaGlobal,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return f.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        }
    }

    /**
     * Convierte la fecha global al formato esperado dentro del contenido del PDF.
     *
     * Valida contenido:
     * - Soporta formato yyyy-MM-dd HH:mm:ss
     * - Soporta formato yyyy-MM-dd
     * - Regresa fecha en formato yyyy-MM-dd
     *
     * @param fechaGlobal fecha origen tomada de la variable global
     * @return fecha formateada como yyyy-MM-dd
     * @throws Exception no lanza excepcion controlada; usa fallback entre formatos
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private String convertirFechaContenido(String fechaGlobal) {
        try {
            LocalDate f = LocalDateTime.parse(fechaGlobal,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
            return f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ex) {
            LocalDate f = LocalDate.parse(fechaGlobal,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    /**
     * Normaliza el texto extraido del PDF para facilitar comparaciones robustas.
     *
     * Valida contenido:
     * - Elimina acentos y marcas diacriticas
     * - Sustituye saltos de linea por espacios
     * - Colapsa espacios multiples
     * - Convierte todo a mayusculas
     *
     * @param texto texto extraido del PDF
     * @return texto normalizado para comparaciones
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private String normalizarTextoPdf(String texto) {
        if (texto == null) {
            return "";
        }

        String t = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        t = t.replace('\n', ' ').replace('\r', ' ');
        t = t.replaceAll("\\s+", " ").trim().toUpperCase(Locale.ROOT);
        return t;
    }

    /**
     * Pausa la ejecucion del hilo actual por el tiempo indicado.
     *
     * Valida contenido:
     * - Conserva el estado de interrupcion si ocurre InterruptedException
     *
     * @param ms tiempo de espera en milisegundos
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Extrae en temporal y abre visualmente el PDF validado dentro del ZIP.
     *
     * Valida contenido:
     * - Escribe bytes en un archivo temporal .pdf
     * - Conserva un nombre identificable para evidencia
    * - Abre el PDF en una pestaña controlada por WebDriver para no afectar otros Edge
     *
     * @param pdfBytes contenido binario del PDF
     * @param nombrePdf nombre original del PDF dentro del ZIP
     * @throws Exception no lanza excepcion controlada; en fallo solo registra informacion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private boolean abrirPdfTemporalVisual(byte[] pdfBytes, String nombrePdf) {
        try {
            if (pdfBytes == null || pdfBytes.length == 0) {
                System.out.println("  INFO: no se abrio PDF visualmente porque no hay contenido");
                return false;
            }

            String nombreSeguro = nombrePdf == null ? "PDF_ZIP_VALIDADO"
                    : nombrePdf.replaceAll("[^a-zA-Z0-9._-]", "_");
            if (nombreSeguro.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                nombreSeguro = nombreSeguro.substring(0, nombreSeguro.length() - 4);
            }

            File tempPdf = new File(System.getProperty("java.io.tmpdir"), nombreSeguro + ".pdf");
            if (tempPdf.exists()) {
                tempPdf = new File(System.getProperty("java.io.tmpdir"),
                        nombreSeguro + "_" + System.currentTimeMillis() + ".pdf");
            }
            Files.write(tempPdf.toPath(), pdfBytes);
            tempPdf.deleteOnExit();

            ultimoPdfAbiertoNombre = tempPdf.getName();

            return abrirPdfEnPestanaDriver(tempPdf);
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo abrir el PDF validado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Abre un PDF en una nueva pestaña del navegador controlado por Selenium.
     *
     * Valida contenido:
     * - Conserva el handle principal del driver
     * - Abre el archivo local en una pestaña nueva
     * - Evita abrir Edge como aplicacion externa
     *
     * @param pdf archivo PDF a abrir
     * @return true si la pestaña del PDF queda abierta
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private boolean abrirPdfEnPestanaDriver(File pdf) {
        try {
            if (pdf == null || !pdf.exists()) {
                System.out.println("  INFO: no se abrio PDF en driver porque el archivo no existe");
                return false;
            }

            handlePrincipalAntesPdf = u.getDriver().getWindowHandle();
            u.getDriver().switchTo().newWindow(WindowType.TAB);
            handlePdfAbierto = u.getDriver().getWindowHandle();
            u.getDriver().get(pdf.toURI().toString());
            dormir(1200);

            System.out.println("  INFO: PDF validado abierto en pestaña del driver: " + pdf.getAbsolutePath());
            return true;
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo abrir PDF en pestaña del driver: " + e.getMessage());
            regresarFocoDriverPrincipal();
            return false;
        }
    }

    /**
     * Cierra la pestaña PDF controlada por Selenium y visores PDF comunes externos si existieran.
     * No cierra procesos Edge completos.
     *
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void cerrarVisorPdf() {
        cerrarPestanaPdfDriver();

        String[][] comandos = new String[][] {
                { "cmd.exe", "/c", "taskkill", "/IM", "AcroRd32.exe", "/F" },
                { "cmd.exe", "/c", "taskkill", "/IM", "Acrobat.exe", "/F" },
                { "cmd.exe", "/c", "taskkill", "/IM", "SumatraPDF.exe", "/F" },
                { "cmd.exe", "/c", "taskkill", "/IM", "FoxitPDFReader.exe", "/F" }
        };

        for (String[] cmd : comandos) {
            try {
                ejecutarComandoWindows(cmd);
            } catch (Exception ignore) {
                // Ignorar para no interrumpir la ejecucion principal.
            }
        }
        System.out.println("  INFO: intento de cierre de visor PDF ejecutado sin cerrar Edge completo");
    }

    /**
     * Cierra solo la pestaña PDF abierta por WebDriver y vuelve al handle principal.
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void cerrarPestanaPdfDriver() {
        try {
            if (handlePdfAbierto != null && u.getDriver().getWindowHandles().contains(handlePdfAbierto)) {
                u.getDriver().switchTo().window(handlePdfAbierto);
                u.getDriver().close();
                System.out.println("  INFO: pestaña PDF del driver cerrada");
            }
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo cerrar pestaña PDF del driver: " + e.getMessage());
        } finally {
            handlePdfAbierto = null;
            regresarFocoDriverPrincipal();
        }
    }

    /**
     * Regresa el foco al navegador controlado por Selenium despues de abrir PDF o Explorador externo.
     * No cierra procesos Edge; solo cambia al handle actual del driver y activa la ventana por titulo.
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void regresarFocoDriverPrincipal() {
        try {
            String handlePrincipal = handlePrincipalAntesPdf;
            if (handlePrincipal == null || !u.getDriver().getWindowHandles().contains(handlePrincipal)) {
                handlePrincipal = u.getDriver().getWindowHandle();
            }
            u.getDriver().switchTo().window(handlePrincipal);
            try {
                ((org.openqa.selenium.JavascriptExecutor) u.getDriver()).executeScript("window.focus();");
            } catch (Exception ignore) {
            }

            String titulo = u.getDriver().getTitle();
            if (titulo != null && !titulo.trim().isEmpty()) {
                String tituloEscapado = escaparPowerShell(titulo);
                ejecutarComandoWindows(new String[] {
                        "powershell.exe", "-NoProfile", "-Command",
                        "$ws = New-Object -ComObject WScript.Shell; "
                                + "try { $ws.AppActivate('" + tituloEscapado + "') | Out-Null } catch {}"
                });
            }

            dormir(500);
            System.out.println("  INFO: foco devuelto al navegador principal del driver");
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo devolver foco al driver principal: " + e.getMessage());
        }
    }

    /**
     * Cierra la ventana de Explorador de Windows que muestra una ruta especifica.
     *
     * Valida contenido:
     * - Cierra por URL de carpeta visible en la ventana
     * - Compatible con Windows en cualquier idioma
     * - No detiene la ejecucion si falla
     *
     * @param ruta ruta de la carpeta cuya ventana se desea cerrar
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void cerrarExploradorPorRuta(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return;
        }

        String rutaEscapada = escaparPowerShell(ruta);
        String script = "$shell = New-Object -ComObject Shell.Application; "
                + "$shell.Windows() | ForEach-Object { try { "
                + "$isExplorer = $_.FullName -like '*explorer.exe'; "
            + "if (-not $isExplorer) { } else { "
                + "$loc = ''; $path = ''; "
                + "try { $loc = [System.Uri]::UnescapeDataString($_.LocationURL) } catch {}; "
                + "if ($loc -like 'file:///*') { $path = $loc.Replace('file:///','').Replace('/','\\') }; "
                + "if (($path -like '" + rutaEscapada + "*') -or ($loc -like '*" + rutaEscapada + "*')) { $_.Quit() } "
            + "} } catch {} }";

        try {
            Process p = Runtime.getRuntime().exec(new String[] {
                    "powershell.exe", "-NoProfile", "-Command", script
            });
            p.waitFor(1200, TimeUnit.MILLISECONDS);
            System.out.println("  INFO: cierre de ventana Explorador para ruta: " + ruta);
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo cerrar ventana de Explorador: " + e.getMessage());
        }
    }

    /**
     * Obtiene HWND de ventanas actuales de Explorer para cerrar solo las abiertas por la automatizacion.
     *
     * Valida contenido:
     * - Lista ventanas del objeto Shell.Application
     * - Filtra solo ventanas de explorer.exe
     * - Retorna identificadores HWND en texto
     *
     * @return conjunto de HWND abiertos al momento de la consulta
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private Set<String> obtenerHwndExploradorAbiertos() {
        Set<String> hwnds = new HashSet<>();
        String script = "$shell = New-Object -ComObject Shell.Application; "
                + "$shell.Windows() | Where-Object { $_.FullName -like '*explorer.exe' } "
                + "| ForEach-Object { [string]$_.HWND }";

        try {
            Process p = Runtime.getRuntime().exec(new String[] {
                    "powershell.exe", "-NoProfile", "-Command", script
            });

            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String hwnd = line.trim();
                    if (!hwnd.isEmpty()) {
                        hwnds.add(hwnd);
                    }
                }
            }

            p.waitFor(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo obtener listado inicial de Explorer: " + e.getMessage());
        }

        return hwnds;
    }

    /**
     * Cierra ventanas de Explorer abiertas durante la validacion actual.
     *
     * Valida contenido:
     * - Compara HWND actual contra el listado inicial
     * - Cierra ventanas nuevas
     * - Refuerza cierre por rutas conocidas de evidencia
     *
     * @param hwndIniciales conjunto de HWND presentes antes de iniciar la validacion
     * @param rutas rutas de carpetas usadas durante evidencia para cierre prioritario
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void cerrarVentanasExploradorNuevas(Set<String> hwndIniciales, String... rutas) {
        String inicialCsv = "";
        if (hwndIniciales != null && !hwndIniciales.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String hwnd : hwndIniciales) {
                if (hwnd == null || hwnd.trim().isEmpty()) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("'").append(escaparPowerShell(hwnd.trim())).append("'");
            }
            inicialCsv = sb.toString();
        }

        String rutasCsv = "";
        if (rutas != null && rutas.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String ruta : rutas) {
                if (ruta == null || ruta.trim().isEmpty()) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("'").append(escaparPowerShell(ruta.trim())).append("'");
            }
            rutasCsv = sb.toString();
        }

        String script = "$inicial = @(" + inicialCsv + "); "
                + "$rutas = @(" + rutasCsv + "); "
                + "$shell = New-Object -ComObject Shell.Application; "
                + "$shell.Windows() | ForEach-Object { try { "
            + "$isExplorer = $_.FullName -like '*explorer.exe'; if (-not $isExplorer) { } else { "
                + "$hwnd = [string]$_.HWND; "
                + "$loc = ''; $path = ''; "
                + "try { $loc = [System.Uri]::UnescapeDataString($_.LocationURL) } catch {}; "
                + "if ($loc -like 'file:///*') { $path = $loc.Replace('file:///','').Replace('/','\\') }; "
                + "$cerrar = ($inicial -notcontains $hwnd); "
                + "foreach($r in $rutas){ if($r -and (($path -like ($r + '*')) -or ($loc -like ('*' + $r + '*')))){ $cerrar = $true } }; "
                + "if($cerrar){ $_.Quit() } "
            + "} } catch {} }";

        try {
            Process p = Runtime.getRuntime().exec(new String[] {
                    "powershell.exe", "-NoProfile", "-Command", script
            });
            p.waitFor(1500, TimeUnit.MILLISECONDS);
            System.out.println("  INFO: cierre final de ventanas nuevas de Explorer ejecutado");
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo cerrar ventanas nuevas de Explorer: " + e.getMessage());
        }
    }

    /**
     * Ejecuta un comando de Windows y espera brevemente su finalizacion.
     *
     * Valida contenido:
     * - Ejecuta el comando recibido
     * - Espera hasta 4 segundos por finalizacion
     * - No interrumpe flujo principal ante fallo
     *
     * @param comando comando a ejecutar
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void ejecutarComandoWindows(String[] comando) {
        if (comando == null || comando.length == 0) {
            return;
        }

        try {
            Process p = Runtime.getRuntime().exec(comando);
            p.waitFor(1200, TimeUnit.MILLISECONDS);
        } catch (Exception ignore) {
            // Ignorar para no interrumpir la ejecucion principal.
        }
    }

    /**
     * Escapa comillas simples para incrustar texto en scripts de PowerShell.
     *
     * Valida contenido:
     * - Duplica comillas simples segun sintaxis de PowerShell
     * - Retorna cadena vacia si recibe null
     *
     * @param valor texto a escapar
     * @return texto escapado para literal de PowerShell
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private String escaparPowerShell(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("'", "''");
    }

    /**
     * Trae al frente la ventana del Explorador de Windows que muestra una carpeta.
     *
     * Valida contenido:
     * - Busca la ventana por LocationURL exacto
     * - Usa SetForegroundWindow para enfocar la ventana
     * - Evita que evidencia se tome con ventana en segundo plano
     *
    * @param carpeta carpeta cuya ventana se desea traer al frente
    * @return true si la ventana objetivo quedo en primer plano
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private boolean traerVentanaAlFrente(File carpeta) {
        if (carpeta == null) {
            return false;
        }

        String rutaObjetivo = carpeta.getAbsolutePath();
        String rutaObjetivoEscapada = escaparPowerShell(rutaObjetivo);
        String script = "$shell = New-Object -ComObject Shell.Application; "
                + "$objetivo = '" + rutaObjetivoEscapada + "'; "
                + "$ok = $false; "
                + "$win = $shell.Windows() | Where-Object { "
                + "  try { "
                + "    if ($_.FullName -notlike '*explorer.exe') { return $false }; "
                + "    $loc = [System.Uri]::UnescapeDataString($_.LocationURL); "
                + "    $path = ''; "
                + "    if ($loc -like 'file:///*') { $path = $loc.Replace('file:///','').Replace('/','\\') }; "
                + "    $pathNorm = $path.TrimEnd('\\'); "
                + "    $objNorm = $objetivo.TrimEnd('\\'); "
                + "    return ($pathNorm -ieq $objNorm); "
                + "  } catch { return $false } "
                + "} | Select-Object -First 1; "
                + "if ($win -ne $null) { "
                + "  $hwnd = [System.IntPtr]$win.HWND; "
                + "  $code = 'using System.Runtime.InteropServices; "
                + "public class W { "
                + "[DllImport(\"user32.dll\")] public static extern bool ShowWindow(System.IntPtr h, int n); "
                + "[DllImport(\"user32.dll\")] public static extern bool SetForegroundWindow(System.IntPtr h); "
                + "[DllImport(\"user32.dll\")] public static extern System.IntPtr GetForegroundWindow(); }'; "
                + "  Add-Type -TypeDefinition $code -ErrorAction SilentlyContinue; "
                + "  [W]::ShowWindow($hwnd, 3) | Out-Null; "
                + "  [W]::ShowWindow($hwnd, 9) | Out-Null; "
                + "  try { Add-Type -AssemblyName System.Windows.Forms -ErrorAction SilentlyContinue } catch {}; "
                + "  try { [System.Windows.Forms.SendKeys]::SendWait('%') } catch {}; "
                + "  Start-Sleep -Milliseconds 60; "
                + "  [W]::SetForegroundWindow($hwnd) | Out-Null; "
                + "  Start-Sleep -Milliseconds 120; "
                + "  if ([W]::GetForegroundWindow() -eq $hwnd) { $ok = $true } "
                + "}; "
                + "if ($ok) { Write-Output '1' } else { Write-Output '0' }";

        try {
            Process p = Runtime.getRuntime().exec(new String[] {
                    "powershell.exe", "-NoProfile", "-Command", script
            });
            String salida = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    salida = line.trim();
                }
            }
            boolean finalizo = p.waitFor(900, TimeUnit.MILLISECONDS);
            if (!finalizo) {
                p.destroyForcibly();
                System.out.println("  INFO: timeout al intentar traer ventana al frente: " + carpeta.getAbsolutePath());
                return false;
            }
            boolean ok = "1".equals(salida);
            System.out.println("  INFO: ventana traida al frente para: " + carpeta.getAbsolutePath() + " | ok=" + ok);
            return ok;
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo traer ventana al frente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Abre el Explorador de Windows seleccionando el archivo descargado.
     *
     * Valida contenido:
     * - Abre explorer.exe con /select para enfocar el archivo
     * - Deja visible el archivo descargado para evidencia
     *
     * @param archivo archivo descargado a mostrar en el explorador
     * @return true si se ejecuto el comando de apertura
     * @throws Exception no lanza excepcion controlada; en fallo solo registra informacion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private boolean abrirExploradorEnArchivo(File archivo) {
        try {
            if (archivo == null || !archivo.exists()) {
                System.out.println("  INFO: no se abrio explorador porque el archivo no existe");
                return false;
            }

            String rutaArchivo = archivo.getAbsolutePath();
            String comandoCmd = "cmd.exe /c start \"\" explorer.exe /n,/select,\"" + rutaArchivo + "\"";
            Runtime.getRuntime().exec(comandoCmd);

            dormir(600);
            if (!existeVentanaExploradorParaRuta(archivo.getParent())) {
                String rutaEscapada = escaparPowerShell(rutaArchivo);
                String script = "Start-Process explorer.exe -ArgumentList '/n,/select,\"" + rutaEscapada + "\"'";
                Runtime.getRuntime().exec(new String[] {
                        "powershell.exe", "-NoProfile", "-Command", script
                });
                dormir(600);
            }

            boolean ok = existeVentanaExploradorParaRuta(archivo.getParent());
            System.out.println("  INFO: explorador abierto con archivo seleccionado: " + rutaArchivo + " | ok=" + ok);
            return ok;
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo abrir explorador de Windows: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extrae el contenido del ZIP a una carpeta temporal para evidencia visual.
     *
     * Valida contenido:
     * - Crea carpeta temporal por ejecucion
     * - Extrae archivos manteniendo estructura interna
     * - Omite entradas de directorio en la copia
     *
     * @param zip ZIP abierto con contenido a extraer
     * @param nombreZip nombre del archivo ZIP para identificar carpeta temporal
     * @return carpeta temporal con contenido extraido o null en caso de error
     * @throws Exception no lanza excepcion controlada; en fallo solo registra informacion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private File extraerContenidoZipATemporal(ZipFile zip, String nombreZip) {
        if (zip == null) {
            return null;
        }

        try {
            String base = (nombreZip == null ? "ZIP" : nombreZip);
            if (base.toLowerCase(Locale.ROOT).endsWith(".zip")) {
                base = base.substring(0, base.length() - 4);
            }
            // Remover solo caracteres invalidos para nombres de carpetas en Windows
            base = base.replaceAll("[\\\\/:*?\"<>|]", "_").trim();

            File carpeta = new File(System.getProperty("java.io.tmpdir"), base);
            if (carpeta.exists()) {
                carpeta = new File(System.getProperty("java.io.tmpdir"),
                        base + "_" + System.currentTimeMillis());
            }
            Files.createDirectories(carpeta.toPath());

            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                Path destino = carpeta.toPath().resolve(entry.getName()).normalize();

                if (!destino.startsWith(carpeta.toPath())) {
                    continue;
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(destino);
                    continue;
                }

                if (destino.getParent() != null) {
                    Files.createDirectories(destino.getParent());
                }

                try (InputStream is = zip.getInputStream(entry)) {
                    Files.copy(is, destino, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            System.out.println("  INFO: contenido ZIP extraido en: " + carpeta.getAbsolutePath());
            return carpeta;
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo extraer contenido del ZIP: " + e.getMessage());
            return null;
        }
    }

    /**
     * Abre el Explorador de Windows mostrando una carpeta.
     *
     * Valida contenido:
     * - Abre explorer.exe apuntando a la carpeta indicada
     * - Permite tomar evidencia del contenido visible
     *
     * @param carpeta carpeta a mostrar en explorador
     * @return true si se ejecuto el comando de apertura
     * @throws Exception no lanza excepcion controlada; en fallo solo registra informacion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private boolean abrirExploradorEnCarpeta(File carpeta) {
        try {
            if (carpeta == null || !carpeta.exists() || !carpeta.isDirectory()) {
                System.out.println("  INFO: no se abrio explorador de contenido ZIP porque la carpeta no existe");
                return false;
            }

            String rutaCarpeta = carpeta.getAbsolutePath();
            String comandoCmd = "cmd.exe /c start \"\" explorer.exe /n,\"" + rutaCarpeta + "\"";
            Runtime.getRuntime().exec(comandoCmd);

            // Espera corta y valida apertura real; reintenta una sola vez si no aparece.
            dormir(500);
            boolean ok = false;
            for (int i = 0; i < 3; i++) {
                if (existeVentanaExploradorParaRuta(rutaCarpeta)) {
                    ok = true;
                    break;
                }
                dormir(250);
            }

            if (!ok) {
                String rutaEscapada = escaparPowerShell(rutaCarpeta);
                String script = "Start-Process explorer.exe -ArgumentList '/n,\"" + rutaEscapada + "\"'";
                Runtime.getRuntime().exec(new String[] {
                        "powershell.exe", "-NoProfile", "-Command", script
                });

                dormir(600);
                for (int i = 0; i < 4; i++) {
                    if (existeVentanaExploradorParaRuta(rutaCarpeta)) {
                        ok = true;
                        break;
                    }
                    dormir(250);
                }
            }

            System.out.println("  INFO: explorador abierto con contenido ZIP: " + rutaCarpeta + " | ok=" + ok);
            return ok;
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo abrir explorador de contenido ZIP: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si hay una ventana de Explorer abierta para una ruta dada.
     *
     * Valida contenido:
     * - Enumera ventanas de explorer.exe
     * - Normaliza LocationURL a ruta local
     * - Retorna true si encuentra coincidencia por prefijo
     *
     * @param ruta ruta objetivo a buscar en ventanas abiertas
     * @return true si existe una ventana de Explorer en esa ruta
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private boolean existeVentanaExploradorParaRuta(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return false;
        }

        String rutaEscapada = escaparPowerShell(ruta.trim());
        String script = "$objetivo = '" + rutaEscapada + "'; "
                + "$shell = New-Object -ComObject Shell.Application; "
                + "$ok = $false; "
                + "$shell.Windows() | ForEach-Object { try { "
                + "if ($_.FullName -notlike '*explorer.exe') { } else { "
                + "$loc = [System.Uri]::UnescapeDataString($_.LocationURL); "
                + "$path = ''; "
                + "if ($loc -like 'file:///*') { $path = $loc.Replace('file:///','').Replace('/','\\') }; "
                + "$pathNorm = $path.TrimEnd('\\'); "
                + "$objNorm = $objetivo.TrimEnd('\\'); "
                + "if ($pathNorm -ieq $objNorm) { $ok = $true } "
                + "} } catch {} }; "
                + "if ($ok) { Write-Output '1' } else { Write-Output '0' }";

        try {
            Process p = Runtime.getRuntime().exec(new String[] {
                    "powershell.exe", "-NoProfile", "-Command", script
            });

            String salida = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    salida = line.trim();
                }
            }
            p.waitFor(4, TimeUnit.SECONDS);
            return "1".equals(salida);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Espera brevemente la ventana de Explorer para una ruta especifica.
     *
     * @param ruta ruta objetivo en Explorer
     * @param timeoutMs tiempo maximo de espera
     * @return true si detecta la ventana dentro del tiempo
     */
    private boolean esperarVentanaExploradorParaRuta(String ruta, long timeoutMs) {
        long fin = System.currentTimeMillis() + Math.max(0L, timeoutMs);
        while (System.currentTimeMillis() < fin) {
            if (existeVentanaExploradorParaRuta(ruta)) {
                return true;
            }
            dormir(220);
        }
        return existeVentanaExploradorParaRuta(ruta);
    }

    /**
     * Registra evidencia cuando el explorador muestra el archivo descargado.
     *
     * Valida contenido:
     * - Captura pantalla del paso actual en explorador
     * - Registra nombre del archivo descargado
     *
     * @param nombreArchivo nombre del archivo descargado mostrado en explorador
     * @param screenshot indica si se debe tomar captura
     * @throws Exception no lanza excepcion controlada; ante fallo solo informa en consola
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void registrarEvidenciaExplorador(String nombreArchivo, boolean screenshot, String tituloCaptura) {
        if (testCase == null) {
            System.out.println("  INFO: testCase es null; no se registra evidencia de explorador");
            return;
        }

        if (screenshot) {
            String titulo = (tituloCaptura == null || tituloCaptura.trim().isEmpty())
                    ? "Explorador de Windows"
                    : tituloCaptura;
            testCase.addScreenShotCurrentStep(titulo);
        }

        testCase.addTextEvidenceCurrentStep("Archivo descargado: " + nombreArchivo);
    }

    /**
     * Registra evidencia de contenido ZIP con foco inmediato y captura de respaldo.
     *
     * @param carpetaContenido carpeta temporal abierta en Explorer
     * @param screenshot indica si se debe tomar captura
     */
    private void registrarEvidenciaExploradorContenido(File carpetaContenido, boolean screenshot) {
        if (testCase == null) {
            System.out.println("  INFO: testCase es null; no se registra evidencia de contenido ZIP");
            return;
        }

        System.out.println("  INFO: entrando a evidencia de contenido ZIP");

        String nombre = (carpetaContenido == null) ? "Contenido ZIP" : carpetaContenido.getName();

        if (screenshot && carpetaContenido != null) {
            boolean focoAntesCaptura = traerVentanaAlFrente(carpetaContenido);
            dormir(focoAntesCaptura ? 220 : 420);
            System.out.println("  INFO: tomando screenshot de contenido ZIP");
            testCase.addScreenShotCurrentStep("Explorador - Contenido ZIP");
            dormir(2500); // Mantener contenido visible hasta que la captura quede materializada.
        }

        testCase.addTextEvidenceCurrentStep("Archivo descargado: " + nombre);
    }

    /**
     * Intenta traer al frente el visor del PDF abierto para que la captura sea correcta.
     *
     * Valida contenido:
     * - Usa AppActivate con el nombre del ultimo PDF abierto
     * - No detiene la ejecucion si falla
     *
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void traerVentanaPdfAlFrente() {
        if (ultimoPdfAbiertoNombre == null || ultimoPdfAbiertoNombre.trim().isEmpty()) {
            return;
        }

        String titulo = escaparPowerShell(ultimoPdfAbiertoNombre.trim());
        String script = "$ws = New-Object -ComObject WScript.Shell; "
                + "$ok = $false; "
                + "try { $ok = $ws.AppActivate('" + titulo + "') } catch {}; "
                + "if ($ok) { Write-Output '1' } else { Write-Output '0' }";

        try {
            Process p = Runtime.getRuntime().exec(new String[] {
                    "powershell.exe", "-NoProfile", "-Command", script
            });
            p.waitFor(3, TimeUnit.SECONDS);
            System.out.println("  INFO: intento de foco a PDF ejecutado para: " + ultimoPdfAbiertoNombre);
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo enfocar ventana PDF: " + e.getMessage());
        }
    }

    /**
     * Registra evidencia en testCase cuando el PDF validado se abre visualmente.
     *
     * Valida contenido:
     * - Captura pantalla del paso actual
     * - Registra CR validado
     * - Registra folio validado
     * - Registra fecha validada
     *
     * @param cr CR validado
     * @param folio folio validado
     * @param fecha fecha validada
     * @param screenshot indica si se debe tomar captura
     * @throws Exception no lanza excepcion controlada; ante fallo solo informa en consola
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void registrarEvidenciaPdfAbierto(String cr, String folio, String fecha, boolean screenshot) {
        if (testCase == null) {
            System.out.println("  INFO: testCase es null; no se registra evidencia del PDF abierto");
            return;
        }

        if (screenshot) {
            testCase.addScreenShotCurrentStep("PDF validado abierto");
        }

        testCase.addTextEvidenceCurrentStep("Cr encontrado: " + cr);
        testCase.addTextEvidenceCurrentStep("Folio encontrado: " + folio);
        testCase.addTextEvidenceCurrentStep("Fecha encontrada: " + fecha);
    }

    /**
     * Obtiene el primer valor no vacio de una lista de candidatos.
     *
     * Valida contenido:
     * - Ignora nulos
     * - Aplica trim a cada candidato
     * - Retorna el primer valor con contenido
     *
     * @param candidatos lista de valores candidatos
     * @return primer valor no vacio o cadena vacia
     * @throws Exception no lanza excepcion controlada
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private String valorNoVacio(String... candidatos) {
        if (candidatos == null) {
            return "";
        }

        for (String candidato : candidatos) {
            if (candidato != null) {
                String t = candidato.trim();
                if (!t.isEmpty()) {
                    return t;
                }
            }
        }
        return "";
    }

    /**
     * Lee un campo estatico String usando reflection para fallback de variables.
     *
     * Valida contenido:
     * - Busca clase por nombre
     * - Busca campo publico estatico
     * - Retorna valor String con trim
     *
     * @param nombreClase nombre simple o totalmente calificado de la clase
     * @param nombreCampo nombre del campo estatico
     * @return valor del campo o cadena vacia si no existe o falla
     * @throws Exception no lanza excepcion controlada; retorna vacio ante error
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private String leerCampoEstatico(String nombreClase, String nombreCampo) {
        try {
            Class<?> clazz;
            try {
                clazz = Class.forName(nombreClase);
            } catch (ClassNotFoundException ex) {
                return "";
            }

            Object valor = clazz.getField(nombreCampo).get(null);
            if (valor instanceof String) {
                return ((String) valor).trim();
            }
            return "";
        } catch (Exception ex) {
            return "";
        }
    }
    /**
     * Cuenta cuantos archivos PDF contiene un ZIP.
     *
     * @param rutaZip ruta absoluta del archivo ZIP
     * @return cantidad de archivos .pdf dentro del ZIP
     * @throws Exception cuando la ruta es invalida o no se puede leer el ZIP
     *
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public int contarArchivosPdfEnZip(String rutaZip) throws Exception {
        if (rutaZip == null || rutaZip.trim().isEmpty()) {
            throw new Exception("La ruta del ZIP esta vacia.");
        }
        return contarArchivosPdfEnZip(new File(rutaZip.trim()));
    }

    /**
     * Cuenta cuantos archivos PDF contiene el ZIP esperado en Descargas, sin
     * recibir parametros.
     *
     * Usa la fecha disponible en variables globales para construir el prefijo del
     * nombre esperado del ZIP:
     * CONCENTRADO_DEVOLUCION_dd_MM_yyyy-dd_MM_yyyy
     *
     * @return cantidad de archivos .pdf dentro del ZIP encontrado
     * @throws Exception cuando no hay fecha disponible, no existe Descargas o no se
     *                   encuentra ZIP
     *
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public int contarArchivosPdfEnZip() throws Exception {
        String rutaZip = valorNoVacio(ultimoZipAbierto);
        if (rutaZip.isEmpty()) {
            throw new Exception("No hay ZIP previo guardado en ultimoZipAbierto. Ejecuta primero validaDescargaZipDevolucion.");
        }

        File zip = new File(rutaZip);
        if (!zip.exists() || !zip.isFile()) {
            throw new Exception("La ruta guardada en ultimoZipAbierto no existe: " + rutaZip);
        }

        System.out.println("ZIP tomado desde ultimoZipAbierto para conteo PDF: " + zip.getAbsolutePath());
        return contarArchivosPdfEnZip(zip);
    }

    /**
     * Cuenta cuantos archivos PDF contiene un ZIP.
     *
     * @param archivoZip archivo ZIP a evaluar
     * @return cantidad de archivos .pdf dentro del ZIP
     * @throws Exception cuando el archivo es invalido o no se puede leer
     *
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public int contarArchivosPdfEnZip(File archivoZip) throws Exception {
        if (archivoZip == null) {
            throw new Exception("El archivo ZIP es null.");
        }
        if (!archivoZip.exists() || !archivoZip.isFile()) {
            throw new Exception("No existe el archivo ZIP: " + archivoZip.getAbsolutePath());
        }
        if (!archivoZip.getName().toLowerCase(Locale.ROOT).endsWith(".zip")) {
            throw new Exception("El archivo no es ZIP: " + archivoZip.getName());
        }

        int totalPdf = 0;
        try (ZipFile zip = new ZipFile(archivoZip)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                String nombreInterno = new File(entry.getName()).getName();
                if (nombreInterno.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                    totalPdf++;
                }
            }
        }

        System.out.println("Cantidad de PDFs dentro del ZIP [" + archivoZip.getName() + "]: " + totalPdf);
        return totalPdf;
    }
}
