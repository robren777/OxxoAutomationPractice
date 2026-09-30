package EBS.util.FolioPagoMVP2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.sikuli.script.FindFailed;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ConstruccionXML {

	/**
	 * Metodo para enviar un xml por la interfaz FEMSA_WS
	 * 
	 * @param path donde está el archivo xml, url de la interfaz
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public boolean sendXml(String xmlContent, String url) {
		boolean isSuccess = false;
		int maxAttempts = 5; // Intentar enviar hasta 5 veces
		int attempt = 0;

		while (attempt < maxAttempts) {
			attempt++;
			try {
				// Crear un cliente HTTP que no verifica el certificado SSL
				SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
				CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(builder.build())
						.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

				// Enviar el contenido XML a la interfaz
				HttpPost httpPost = new HttpPost(url);
				StringEntity requestEntity = new StringEntity(xmlContent, ContentType.APPLICATION_XML);
				httpPost.setEntity(requestEntity);

				try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
					System.out.println("Interfaz: " + url);
					System.out.println("xmlContent" + xmlContent);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						isSuccess = true;
						System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
						break; // Salir del bucle si la solicitud fue exitosa
					} else if (statusCode == 500) {
						System.out.println("Error 500 al enviar la solicitud. Reintentando...");
						Thread.sleep(5000);
						// No romper el bucle, intentar de nuevo
					} else if (statusCode == 401) {
						System.out.println("Error 401 al enviar la solicitud. Reintentando...");
						Thread.sleep(5000);
						// No romper el bucle, intentar de nuevo
					} else {
						System.out.println("Error al enviar la solicitud. Código de estado: " + statusCode);
						break; // Salir del bucle si es otro error
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				break; // Salir del bucle si hay una excepción
			}
		}
		return isSuccess;
	}

	/**
	 * Método para enviar un archivo XML desde su ruta a una URL específica.
	 *
	 * @param xmlFilePath la ruta del archivo XML que se desea enviar
	 * @param url la URL a la que se enviará el contenido del archivo XML
	 * @throws IOException si ocurre un error al leer el archivo XML
	 * @throws Exception si ocurre algún otro tipo de excepción durante el proceso
	 * @author JesusM3@hexaware.com
	 */

	public boolean sendXmlPath(String xmlFilePath, String url) {
		boolean isSuccess = false;
		int maxAttempts = 5; // Intentar enviar hasta 5 veces
		int attempt = 0;

		while (attempt < maxAttempts) {
			attempt++;
			try {
				// Leer el contenido del archivo XML desde el path proporcionado
				String xmlContent = new String(Files.readAllBytes(Paths.get(xmlFilePath)));

				// Crear un cliente HTTP que no verifica el certificado SSL
				SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, (chain, authType) -> true); // Aceptar todos los certificados
				CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(builder.build())
						.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

				// Enviar el contenido XML a la interfaz
				HttpPost httpPost = new HttpPost(url);
				StringEntity requestEntity = new StringEntity(xmlContent, ContentType.APPLICATION_XML);
				httpPost.setEntity(requestEntity);

				try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
					System.out.println("Interfaz: " + url);
					System.out.println("xmlContent: " + xmlContent);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						isSuccess = true;
						System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
						break; // Salir del bucle si la solicitud fue exitosa
					} else if (statusCode == 500) {
						System.out.println("Error 500 al enviar la solicitud. Reintentando...");
						Thread.sleep(5000);
						// No romper el bucle, intentar de nuevo
					} else if (statusCode == 401) {
						System.out.println("Error 401 al enviar la solicitud. Reintentando...");
						Thread.sleep(5000);
						// No romper el bucle, intentar de nuevo
					} else {
						System.out.println("Error al enviar la solicitud. Código de estado: " + statusCode);
						break; // Salir del bucle si es otro error
					}
				}
			} catch (IOException e) {
				System.out.println("Error al leer el archivo XML: " + e.getMessage());
				break; // Salir del bucle si hay un error al leer el archivo
			} catch (Exception e) {
				e.printStackTrace();
				break; // Salir del bucle si hay una excepción
			}
		}
		return isSuccess;
	}

	/**
	 * Metodo para extraer el uuid del archivo xml
	 * 
	 * @param El xml al que se le hará la extracción
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractTimbreFiscalDigitalUUID(String xmlContent) {
		String uuid = null;
		try {
			// Convertir el contenido del XML a un documento DOM
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

			// Obtener el nodo TimbreFiscalDigital
			NodeList nodeList = document.getElementsByTagName("TimbreFiscalDigital");
			if (nodeList.getLength() > 0) {
				Element timbreFiscalDigital = (Element) nodeList.item(0);
				uuid = timbreFiscalDigital.getAttribute("UUID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuid;
	}

	/**
	 * Metodo para extraer el uuid de un XML desde su locación
	 * 
	 * @param path donde está el archivo xml
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractUUIDFromFile(String filePath) {
		String uuid = null;
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo TimbreFiscalDigital
				NodeList timbreNodes = cdataDocument.getElementsByTagName("TimbreFiscalDigital");
				if (timbreNodes.getLength() > 0) {
					Element timbreFiscalDigital = (Element) timbreNodes.item(0);
					uuid = timbreFiscalDigital.getAttribute("UUID");
					System.out.println("uuid dentro del método por path: " + uuid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuid;
	}

	/**
	 * Método para extraer el UUID de un XML proporcionado como cadena.
	 *
	 * @param xmlContent contenido del archivo XML
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractUUIDFromContent(String xmlContent) {
		String uuid = null;
		try {
			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Analizar el contenido del XML proporcionado
			Document document = builder.parse(new InputSource(new StringReader(xmlContent)));

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo TimbreFiscalDigital
				NodeList timbreNodes = cdataDocument.getElementsByTagName("TimbreFiscalDigital");
				if (timbreNodes.getLength() > 0) {
					Element timbreFiscalDigital = (Element) timbreNodes.item(0);
					uuid = timbreFiscalDigital.getAttribute("UUID");
					System.out.println("uuid dentro del método por xml: " + uuid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuid;
	}

	/**
	 * Método para modificar la fecha extraída de un UUID proporcionado como cadena.
	 *
	 * @param uuid UUID del que se extraerá la fecha
	 * @throws Exception
	 * @author JesusM3@hexaware.com
	 */
	public boolean modificarFecha(String uuid) throws Exception {
		// Guardar el UUID en una variable String
		String uuidString = uuid;

		// Extraer los últimos 12 caracteres del UUID
		String lastTwelveChars = uuidString.substring(uuidString.length() - 12);

		// Extraer los últimos 8 dígitos de la nueva cadena y guardarlos como fecha
		String fecha = lastTwelveChars.substring(lastTwelveChars.length() - 8);

		// Formateador para la fecha en el formato "ddMMyyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		// Fecha actual
		LocalDate currentDate = LocalDate.now();

		// Fecha extraída del UUID
		LocalDate extractedDate = LocalDate.parse(fecha, formatter);

		// Comparar las fechas
		if (!currentDate.equals(extractedDate)) {
			// Si son diferentes, actualizar la fecha a la actual
			fecha = currentDate.format(formatter);
		}

		// Aquí puedes continuar con la lógica que necesites
		// Por ahora, simplemente retornaremos true para indicar que el método fue
		// ejecutado
		System.out.println("Fecha: " + fecha);
		return true;
	}

	/**
	 * Método para actualizar el UUID en un archivo XML dentro de la etiqueta <TimbreFiscalDigital>.
	 *
	 * @param xmlPath la ruta del archivo XML que se desea modificar
	 * @param nuevoUuid el nuevo UUID que reemplazará al existente en el archivo XML
	 * @throws Exception si ocurre algún error al leer o procesar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public String actualizarUuidEnXml(String xmlPath, String nuevoUuid) throws Exception {
		// Leer el contenido del archivo XML desde el path proporcionado
		String xmlContent = new String(Files.readAllBytes(Paths.get(xmlPath)), StandardCharsets.UTF_8);

		// Expresi ón regular para encontrar el UUID en la etiqueta
		// <TimbreFiscalDigital>
		String regex = "(<TimbreFiscalDigital UUID=\")([^\"]+)(\"/>)";

		// Reemplazar el UUID viejo con el nuevo UUID
		String updatedXmlContent = xmlContent.replaceAll(regex, "$1" + nuevoUuid + "$3");

		// Retornar el XML actualizado como String
		System.out.println("xml actualizado: " + updatedXmlContent);
		return updatedXmlContent;
	}

	
	/**
	 * Método para incrementar el consecutivo en un UUID que contiene el prefijo "JM".
	 *
	 * @param uuid el UUID que se desea modificar, debe contener el formato esperado con "JM"
	 * @return el nuevo UUID con el consecutivo incrementado
	 * @throws IllegalArgumentException si el UUID no contiene el formato esperado con "JM"
	 * @author JesusM3@hexaware.com
	 */
	public String incrementarConsecutivoUuid(String uuid) {
		// Buscar la posición del prefijo "JM" en el UUID
		int indexJM = uuid.indexOf("JM");

		// Verificar si "JM" fue encontrado
		if (indexJM == -1 || indexJM + 4 >= uuid.length()) {
			throw new IllegalArgumentException("El UUID no contiene el formato esperado con 'JM'.");
		}

		// Extraer el consecutivo actual
		String consecutivoStr = uuid.substring(indexJM + 2, indexJM + 4);

		// Convertir el consecutivo a entero y sumarle 1
		int consecutivo = Integer.parseInt(consecutivoStr);
		consecutivo++;

		// Formatear el nuevo consecutivo con dos dígitos
		String nuevoConsecutivoStr = String.format("%02d", consecutivo);

		// Construir el nuevo UUID con el consecutivo incrementado
		String nuevoUuid = uuid.substring(0, indexJM + 2) + nuevoConsecutivoStr + uuid.substring(indexJM + 4);

		// Retornar el nuevo UUID
		return nuevoUuid;
	}

	/**
	 * Metodo para extraer el atributo Fecha de un XML desde su locación
	 *
	 * @param filePath donde está el archivo xml
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractFechaFromFile(String filePath) {
		String fecha = null;
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					fecha = comprobante.getAttribute("Fecha");
					System.out.println("Fecha dentro del método por path: " + fecha);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fecha;
	}

	/**
	 * Metodo para extraer el atributo Folio de un XML desde su locación
	 *
	 * @param filePath donde está el archivo xml
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractFolioFromFile(String filePath) {
		String folio = null;
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					folio = comprobante.getAttribute("Folio");
					System.out.println("Folio dentro del método por path: " + folio);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return folio;
	}

	/**
	 * Metodo para extraer el atributo Serie de un XML desde su locaci ón
	 * 
	 * @param filePath donde est á el archivo xml
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractSerieFromFile(String filePath) {
		String serie = null;
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					serie = comprobante.getAttribute("Serie");
					System.out.println("Serie dentro del método por path: " + serie);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serie;
	}

	/**
	 * Metodo para extraer el atributo Rfc de un XML desde su locación
	 *
	 * @param filePath donde está el archivo xml
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractRfcFromFile(String filePath) {
		String rfc = null;
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Emisor
				NodeList emisorNodes = cdataDocument.getElementsByTagName("Emisor");
				if (emisorNodes.getLength() > 0) {
					Element emisor = (Element) emisorNodes.item(0);
					rfc = emisor.getAttribute("Rfc");
					System.out.println("Rfc dentro del método por path: " + rfc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rfc;
	}

	/**
	 * Metodo para extraer el atributo TipoDeComprobante de un XML desde su locación
	 *
	 * @param filePath donde está el archivo xml
	 * @throws FindFailed
	 * @author JesusM3@hexaware.com
	 */
	public String extractTipoDeComprobanteFromFile(String filePath) {
		String tipoDeComprobante = null;
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					tipoDeComprobante = comprobante.getAttribute("TipoDeComprobante");
					System.out.println("TipoDeComprobante dentro del método por path: " + tipoDeComprobante);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoDeComprobante;
	}

	/**
	 * Método para extraer atributos específicos de un archivo XML desde su ruta.
	 *
	 * @param filePath la ruta del archivo XML del cual se desean extraer los atributos
	 * @return un mapa que contiene los atributos extraídos, como Fecha, Folio, Serie, TipoDeComprobante, Rfc y UUID
	 * @throws Exception si ocurre algún error al leer o procesar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public Map<String, String> extractAttributesFromFile(String filePath) {
		Map<String, String> attributes = new HashMap<>();
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					attributes.put("Fecha", comprobante.getAttribute("Fecha"));
					attributes.put("Folio", comprobante.getAttribute("Folio"));
					attributes.put("Serie", comprobante.getAttribute("Serie"));
					attributes.put("TipoDeComprobante", comprobante.getAttribute("TipoDeComprobante"));
				}

				// Obtener el nodo Emisor
				NodeList emisorNodes = cdataDocument.getElementsByTagName("Emisor");
				if (emisorNodes.getLength() > 0) {
					Element emisor = (Element) emisorNodes.item(0);
					attributes.put("Rfc", emisor.getAttribute("Rfc"));
				}

				// Obtener el nodo TimbreFiscalDigital
				NodeList timbreNodes = cdataDocument.getElementsByTagName("TimbreFiscalDigital");
				if (timbreNodes.getLength() > 0) {
					Element timbreFiscalDigital = (Element) timbreNodes.item(0);
					attributes.put("UUID", timbreFiscalDigital.getAttribute("UUID"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attributes;
	}

	/**
	 * Método para obtener atributos específicos de un archivo XML desde su ruta, incluyendo el formato de la fecha.
	 *
	 * @param filePath la ruta del archivo XML del cual se desean extraer los atributos
	 * @return un mapa que contiene los atributos extraídos, como Fecha (formateada), Folio, Serie, Total, TipoDeComprobante, Rfc y UUID
	 * @throws Exception si ocurre algún error al leer o procesar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public Map<String, String> obtenerAtributos(String filePath) {
		Map<String, String> attributes = new HashMap<>();
		try {
			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(filePath);

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Obtener el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);

					// Obtener la fecha original
					String fechaOriginal = comprobante.getAttribute("Fecha");

					String total = comprobante.getAttribute("Total");

					// Formatear la fecha al nuevo formato
					String fechaFormateada = formatearFecha(fechaOriginal);

					// Guardar la fecha formateada en el mapa
					attributes.put("Fecha", fechaFormateada);
					attributes.put("Folio", comprobante.getAttribute("Folio"));
					attributes.put("Serie", comprobante.getAttribute("Serie"));
					attributes.put("Total", comprobante.getAttribute("Total"));
					attributes.put("TipoDeComprobante", comprobante.getAttribute("TipoDeComprobante"));
				}

				// Obtener el nodo Emisor
				NodeList emisorNodes = cdataDocument.getElementsByTagName("Emisor");
				if (emisorNodes.getLength() > 0) {
					Element emisor = (Element) emisorNodes.item(0);
					attributes.put("Rfc", emisor.getAttribute("Rfc"));
				}

				// Obtener el nodo TimbreFiscalDigital
				NodeList timbreNodes = cdataDocument.getElementsByTagName("TimbreFiscalDigital");
				if (timbreNodes.getLength() > 0) {
					Element timbreFiscalDigital = (Element) timbreNodes.item(0);
					attributes.put("UUID", timbreFiscalDigital.getAttribute("UUID"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attributes;
	}

	/**
	 * Método para formatear una fecha de un formato específico a otro.
	 *
	 * @param fechaOriginal la fecha en formato original (yyyy-MM-dd'T'HH:mm:ss)
	 * @return la fecha formateada en el nuevo formato (dd/MM/yyyy), o la fecha original en caso de error
	 * @throws Exception si ocurre algún error durante el formateo de la fecha
	 * @author JesusM3@hexaware.com
	 */
	public String formatearFecha(String fechaOriginal) {
		try {
			// Formato de la fecha original
			SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

			// Formato deseado
			SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");

			// Parsear la fecha original y convertirla al nuevo formato
			Date fecha = formatoEntrada.parse(fechaOriginal);
			return formatoSalida.format(fecha);
		} catch (Exception e) {
			e.printStackTrace();
			return fechaOriginal; // En caso de error, devolver la fecha original
		}
	}

	
	/**
	 * Método para formatear una fecha al formato "dd-MMM-yyyy" con los meses en español.
	 *
	 * @param fechaOriginal la fecha en formato original (dd/MM/yyyy)
	 * @return la fecha formateada en el formato "dd-MMM-yyyy" con los meses en español y sin puntos, 
	 *         o la fecha original en caso de error
	 * @throws Exception si ocurre algún error durante el formateo de la fecha
	 * @author JesusM3@hexaware.com
	 */
	public String formatearFechaParaFactura(String fechaOriginal) {
		try {
			// Formato de la fecha original
			SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");

			// Formato deseado con el mes abreviado en español
			SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));

			// Parsear la fecha original y convertirla al nuevo formato
			Date fecha = formatoEntrada.parse(fechaOriginal);

			// Formatear la fecha y eliminar el punto después del mes
			return formatoSalida.format(fecha).toUpperCase().replace(".", "");
		} catch (Exception e) {
			e.printStackTrace();
			return fechaOriginal; // En caso de error, devolver la fecha original
		}
	}

	
	/**
	 * Método para incrementar los últimos 4 dígitos de un UUID manteniendo la parte fija intacta.
	 *
	 * @param uuid el UUID que se desea modificar
	 * @return el nuevo UUID con los últimos 4 dígitos incrementados
	 * @throws IllegalArgumentException si el formato del UUID no es válido o los últimos 4 dígitos no son un número decimal
	 * @author JesusM3@hexaware.com
	 */
	public String incrementarUUID(String uuid) {
	    // Imprime el UUID que se va a modificar
	    System.out.println("UUID a testear: " + uuid);
 
	    // Encuentra el último guion para separar la parte fija
	    int lastDashIndex = uuid.lastIndexOf('-');
	    if (lastDashIndex == -1) {
	        throw new IllegalArgumentException("El formato del UUID no es válido.");
	    }
 
	    // Extrae la parte fija y los últimos 4 dígitos
	    String prefix = uuid.substring(0, lastDashIndex + 1);
	    String suffix = uuid.substring(lastDashIndex + 1);
 
	    // Verifica que la longitud del sufijo sea suficiente para tomar los últimos 4 dígitos
	    if (suffix.length() < 4) {
	        throw new IllegalArgumentException("La parte final del UUID no tiene suficientes dígitos.");
	    }
 
	    // Toma los últimos 4 dígitos y los convierte a decimal
	    String lastFourDigits = suffix.substring(suffix.length() - 4);
	    try {
	        int number = Integer.parseInt(lastFourDigits); // Base 10 (decimal)
	        number++; // Incrementa el número
 
	        // Reconstruye el UUID con la parte fija y los últimos 4 dígitos incrementados
	        String nuevoUUID = prefix + suffix.substring(0, suffix.length() - 4) + String.format("%04d", number);
 
	        // Imprime el UUID modificado
	        System.out.println("UUID modificado: " + nuevoUUID);
	        return nuevoUUID;
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Los últimos 4 dígitos del UUID no son un número decimal válido.");
	    }
	}

	
	/**
	 * Método para incrementar la parte numérica de un folio manteniendo la parte alfabética intacta.
	 *
	 * @param folio el folio que se desea modificar
	 * @return el nuevo folio con la parte numérica incrementada
	 * @throws IllegalArgumentException si la parte final del folio no es un número válido
	 * @author JesusM3@hexaware.com
	 */
	public String incrementarFolio(String folio) {
		// Imprime el folio que se va a modificar
		System.out.println("Folio a testear: " + folio);

		// Encuentra el índice del primer carácter no numérico desde el final
		int lastNumericIndex = folio.length();
		for (int i = folio.length() - 1; i >= 0; i--) {
			if (!Character.isDigit(folio.charAt(i))) {
				lastNumericIndex = i + 1;
				break;
			}
		}

		// Separa la parte alfabética y la parte numérica
		String prefix = folio.substring(0, lastNumericIndex);
		String numberPart = folio.substring(lastNumericIndex);

		try {
			// Convierte la parte numérica a entero y la incrementa
			int number = Integer.parseInt(numberPart);
			number++; // Incrementa el número

			// Reconstruye el folio con la nueva parte incrementada
			String nuevoFolio = prefix + String.format("%0" + numberPart.length() + "d", number);

			// Imprime el folio modificado
			System.out.println("Folio modificado: " + nuevoFolio);
			return nuevoFolio;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("La parte final del folio no es un número válido.");
		}
	}

	
	/**
	 * Método para modificar un archivo XML, actualizando el valor del UUID y el Folio dentro del contenido CDATA.
	 *
	 * @param path la ruta del archivo XML que se desea modificar
	 * @param uuid el nuevo valor de UUID que se desea introducir
	 * @param folio el nuevo valor de Folio que se desea introducir
	 * @throws Exception si ocurre algún error al leer o modificar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public void modificarXML2(String path, String uuid, String folio) {
		try {
			// Decodificar la ruta en caso de que contenga caracteres como %20
			path = java.net.URLDecoder.decode(path, "UTF-8");
			System.out.println("UUID a introducir: " + uuid);
			System.out.println("Folio a introducir: " + folio);

			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(path);
			if (!xmlFile.exists()) {
				throw new Exception("El archivo no existe: " + path);
			}

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Modificar el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					comprobante.setAttribute("Folio", folio); // Sustituir el Folio
				}

				// Modificar el nodo TimbreFiscalDigital
				NodeList timbreNodes = cdataDocument.getElementsByTagName("TimbreFiscalDigital");
				if (timbreNodes.getLength() > 0) {
					Element timbreFiscalDigital = (Element) timbreNodes.item(0);
					timbreFiscalDigital.setAttribute("UUID", uuid); // Sustituir el UUID
				}

				// Convertir el subdocumento XML modificado de nuevo a una cadena
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(cdataDocument), new StreamResult(writer));
				String updatedCDATAContent = writer.toString();

				// Actualizar el contenido del CDATA en el nodo principal
				node.setTextContent(updatedCDATAContent);

				// Guardar los cambios en el archivo XML original
				transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File(path).getCanonicalPath());
				transformer.transform(source, result);

				System.out.println("XML modificado y guardado exitosamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Método para modificar un archivo XML, actualizando los valores de UUID y Folio dentro del contenido CDATA.
	 *
	 * @param path la ruta del archivo XML que se desea modificar
	 * @param uuid el nuevo valor de UUID que se desea introducir
	 * @param folio el nuevo valor de Folio que se desea introducir
	 * @throws Exception si ocurre algún error al leer o modificar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public void modificarXML(String path, String uuid, String folio) {
		try {
			// Decodificar la ruta en caso de que contenga caracteres como %20
			path = java.net.URLDecoder.decode(path, "UTF-8");
			System.out.println("UUID a introducir: " + uuid);
			System.out.println("Folio a introducir: " + folio);

			// Crear un archivo desde la ruta proporcionada
			File xmlFile = new File(path);
			if (!xmlFile.exists()) {
				throw new Exception("El archivo no existe: " + path);
			}

			// Configurar el analizador de documentos
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // Habilitar el soporte para espacios de nombres
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document document = builder.parse(xmlFile);

			// Obtener el contenido del CDATA
			NodeList nodeList = document.getElementsByTagName("node");
			if (nodeList.getLength() > 0) {
				Node node = nodeList.item(0);

				// Obtener el contenido del nodo como CDATA
				String cdataContent = node.getTextContent().trim();

				// Analizar el contenido del CDATA como un nuevo documento XML
				org.w3c.dom.Document cdataDocument = builder.parse(new InputSource(new StringReader(cdataContent)));

				// Modificar el nodo Comprobante
				NodeList comprobanteNodes = cdataDocument.getElementsByTagName("Comprobante");
				if (comprobanteNodes.getLength() > 0) {
					Element comprobante = (Element) comprobanteNodes.item(0);
					comprobante.setAttribute("Folio", folio); // Sustituir el Folio
				}

				// Modificar el nodo TimbreFiscalDigital
				NodeList timbreNodes = cdataDocument.getElementsByTagName("TimbreFiscalDigital");
				if (timbreNodes.getLength() > 0) {
					Element timbreFiscalDigital = (Element) timbreNodes.item(0);
					timbreFiscalDigital.setAttribute("UUID", uuid); // Sustituir el UUID
				}

				// Convertir el subdocumento XML modificado de nuevo a una cadena
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				transformer.setOutputProperty(OutputKeys.INDENT, "no");

				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(cdataDocument.getDocumentElement()), new StreamResult(writer));
				String updatedCDATAContent = writer.toString();

				// Crear una nueva sección CDATA con el contenido actualizado
				CDATASection cdataSection = document.createCDATASection(updatedCDATAContent);

				// Reemplazar el contenido del nodo con la nueva sección CDATA
				while (node.hasChildNodes()) {
					node.removeChild(node.getFirstChild());
				}
				node.appendChild(cdataSection);

				// Guardar los cambios en el archivo XML original
				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(xmlFile);
				transformer.transform(source, result);

				System.out.println("XML modificado y guardado exitosamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para modificar un archivo XML representado como cadena, actualizando los valores de los atributos "Folio" y "UUID".
	 *
	 * @param path la ruta del archivo XML que se desea modificar
	 * @param uuid el nuevo valor de UUID que se desea introducir
	 * @param folio el nuevo valor de Folio que se desea introducir
	 * @throws Exception si ocurre algún error al leer o modificar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public static void modificarXMLString(String path, String uuid, String folio) {
		try {
			// Leer el contenido del archivo XML como texto
			String xmlContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");

			// Mostrar el contenido original (opcional, para depuración)
			System.out.println("Contenido original del XML:");
			System.out.println(xmlContent);

			// Buscar y reemplazar el atributo "Folio"
			xmlContent = xmlContent.replaceAll("Folio=\"[^\"]*\"", "Folio=\"" + folio + "\"");

			// Buscar y reemplazar el atributo "UUID" en el nodo TimbreFiscalDigital
			xmlContent = xmlContent.replaceAll("UUID=\"[^\"]*\"", "UUID=\"" + uuid + "\"");

			// Mostrar el contenido modificado (opcional, para depuración)
			System.out.println("Contenido modificado del XML:");
			System.out.println(xmlContent);

			// Guardar el contenido modificado de nuevo en el archivo XML
			Files.write(Paths.get(path), xmlContent.getBytes("UTF-8"));

			System.out.println("XML modificado y guardado exitosamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para enviar contenido XML a un servidor mediante una solicitud HTTP POST, con soporte para SSL y múltiples intentos en caso de error.
	 *
	 * @param xmlContent el contenido XML que se desea enviar
	 * @param url la URL del servidor al que se enviará la solicitud
	 * @return true si la solicitud fue exitosa, false en caso contrario
	 * @throws Exception si ocurre algún error durante el envío de la solicitud
	 * @author JesusM3@hexaware.com
	 */
	public boolean sendXml3(String xmlContent, String url) {
		boolean isSuccess = false;
		int maxAttempts = 5; // Intentar enviar hasta 5 veces
		int attempt = 0;

		while (attempt < maxAttempts) {
			attempt++;
			try {
				// Crear un cliente HTTP que no verifica el certificado SSL
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true) // Confiar
																													// en
																													// todos
																													// los
																													// certificados
						.build();

				SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
						NoopHostnameVerifier.INSTANCE // Ignorar la validación del nombre del host
				);

				CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

				// Crear la solicitud HTTP POST
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Content-Type", "application/xml"); // Header Content-Type
				StringEntity requestEntity = new StringEntity(xmlContent, ContentType.APPLICATION_XML);
				httpPost.setEntity(requestEntity);

				// Enviar la solicitud y manejar la respuesta
				try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
					System.out.println("Interfaz: " + url);
					System.out.println("xmlContent: " + xmlContent);
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200) {
						isSuccess = true;
						System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
						break; // Salir del bucle si la solicitud fue exitosa
					} else if (statusCode == 500) {
						System.out.println("Error 500 al enviar la solicitud. Reintentando...");
						Thread.sleep(5000);
					} else if (statusCode == 401) {
						System.out.println("Error 401 al enviar la solicitud. Reintentando...");
						Thread.sleep(5000);
					} else {
						System.out.println("Error al enviar la solicitud. Código de estado: " + statusCode);
						break; // Salir del bucle si es otro error
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				break; // Salir del bucle si hay una excepción
			}
		}
		return isSuccess;
	}

	/**
	 * Método para modificar un archivo XML representado como cadena, actualizando los valores de los atributos "Folio", "Serie" y "UUID".
	 *
	 * @param path la ruta del archivo XML que se desea modificar
	 * @param uuid el nuevo valor de UUID que se desea introducir
	 * @param folio el nuevo valor de Folio que se desea introducir
	 * @param serie el nuevo valor de Serie que se desea introducir
	 * @throws Exception si ocurre algún error al leer o modificar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public static void modificarXMLString2(String path, String uuid, String folio, String serie) {
		try {
			// Leer el contenido del archivo XML como texto
			String xmlContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");

			// Mostrar el contenido original (opcional, para depuraciï¿½n)
			System.out.println("Contenido original del XML:");
			System.out.println(xmlContent);

			// Buscar y reemplazar el atributo "Folio"
			xmlContent = xmlContent.replaceAll("Folio=\"[^\"]*\"", "Folio=\"" + folio + "\"");

			// Buscar y reemplazar el atributo "Serie"
			xmlContent = xmlContent.replaceAll("Serie=\"[^\"]*\"", "Serie=\"" + serie + "\"");

			// Buscar y reemplazar el atributo "UUID" en el nodo TimbreFiscalDigital
			xmlContent = xmlContent.replaceAll("UUID=\"[^\"]*\"", "UUID=\"" + uuid + "\"");

			// Mostrar el contenido modificado (opcional, para depuraciï¿½n)
			System.out.println("Contenido modificado del XML:");
			System.out.println(xmlContent);

			// Guardar el contenido modificado de nuevo en el archivo XML
			Files.write(Paths.get(path), xmlContent.getBytes("UTF-8"));

			System.out.println("XML modificado y guardado exitosamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para modificar un archivo XML representado como cadena, actualizando los valores de los atributos "Folio", "Serie", "UUID" y los valores relacionados con "importe".
	 *
	 * @param path la ruta del archivo XML que se desea modificar
	 * @param importe el nuevo valor de importe que se desea introducir
	 * @param uuid el nuevo valor de UUID que se desea introducir
	 * @param folio el nuevo valor de Folio que se desea introducir
	 * @param serie el nuevo valor de Serie que se desea introducir
	 * @throws Exception si ocurre algún error al leer o modificar el archivo XML
	 * @author JesusM3@hexaware.com
	 */
	public static void modificarXMLString4(String path, String importe, String uuid, String folio, String serie) {
        try {
            // Leer el contenido del archivo XML como texto
            String xmlContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");

            // Mostrar el contenido original (opcional, para depuración)
            System.out.println("Contenido original del XML:");
            System.out.println(xmlContent);

            // Buscar y reemplazar el atributo "Folio"
            xmlContent = xmlContent.replaceAll("Folio=\"[^\"]*\"", "Folio=\"" + folio + "\"");

            // Buscar y reemplazar el atributo "Serie"
            xmlContent = xmlContent.replaceAll("Serie=\"[^\"]*\"", "Serie=\"" + serie + "\"");

            // Buscar y reemplazar el atributo "UUID" en el nodo TimbreFiscalDigital
            xmlContent = xmlContent.replaceAll("UUID=\"[^\"]*\"", "UUID=\"" + uuid + "\"");

            // Buscar y reemplazar todos los valores de importe
            xmlContent = xmlContent.replaceAll("importeTotal=\"[^\"]*\"", "importeTotal=\"" + importe + "\"");
            xmlContent = xmlContent.replaceAll("ImporteNeto=\"[^\"]*\"", "ImporteNeto=\"" + importe + "\"");
            xmlContent = xmlContent.replaceAll("Total=\"[^\"]*\"", "Total=\"" + importe + "\"");

            // Mostrar el contenido modificado (opcional, para depuración)
            System.out.println("Contenido modificado del XML:");
            System.out.println(xmlContent);

            // Guardar el contenido modificado de nuevo en el archivo XML
            Files.write(Paths.get(path), xmlContent.getBytes("UTF-8"));

            System.out.println("XML modificado y guardado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}