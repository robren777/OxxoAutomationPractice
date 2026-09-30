package IMA.OM_API;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import modelo.TestCase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OxxoCelParser {
	TestCase testCase;
	public OxxoCelParser(TestCase testCase) {
        this.testCase = testCase;
	}

	// Variables
	public static String G_Provider = "SANDBOX";
	public static String G_Transact_Time;
	public static String G_EventLog_Time;
	public static String G_EventLog_Error;
	public static String G_SendDateTime = "";
	public static String G_UpdatedJson = "";
	public static Map<String, String> G_Headers = new HashMap<>();
	private static final String JSON_ERROR_VALIDA_RECHAZO = "JSON Error en validaRechazoPorCurrencyIncorrecto -> ";
	private static final String TRANSACTION_ID = "transactionId";
	private static final String MESSAGE = "message";
	private static final String SUCCESSFULLY = "successfully";
	
	/**
	 * Valida que los campos mandatorios del body request o response no sean null
	 * 
	 * @param json  - json del request o response
	 * @param rutas - paths de los campos del json a validar
	 * @return regresa true si todos los campos mandatorios existen en el body
	 *         request si uno falla, regresa null
	 * 
	 *         // * @author alicia.ceja@gtim.mx
	 * @date 25/Marzo/2025
	 */
	public Boolean validaCamposMandatoriosRequest(String json, List<String> rutas) {
		try {
			JsonPath jsonPath = new JsonPath(json);
			for (String ruta : rutas) {
				Object valor = jsonPath.get(ruta);
				if (valor == null || valor.toString().trim().isEmpty()) {
					return false;
				}
			}
			return true;
		} catch (JSONException e) {
		    String error = JSON_ERROR_VALIDA_RECHAZO
		            + e.getMessage();
		    testCase.addCodeEvidenceCurrentStep(error);
		    throw new RuntimeException(error, e);
      }
	}
	
	/**
	 * Metodo para actualizar el transactionId en un Json, se reemplaza por uno random para evitar duplicados
	 * 
	 * @param json  - json del request
	 * @return regresa jsonActualizado con el nuevo transactionID
	 * @author Leonardo Leonel Ramírez Escobedo
	 * @date 25/Mayo/2026
	 */
	  @SuppressWarnings("unchecked")
	  public String jsonActualizado(String jSon) throws Exception {
	      try {
	          ObjectMapper mapper = new ObjectMapper();
	          // Convertir JSON a Map
	          Map<String, Object> jsonMap = mapper.readValue(jSon, Map.class);
	          // Obtener request_payload
	          Map<String, Object> payload = (Map<String, Object>) jsonMap.get("request_payload");
	          if (payload == null) {
	              throw new RuntimeException("No existe request_payload en el JSON");
	          }
	          // Generar nuevo transactionId
	          String nuevoId = OxxoCelUtils.generarTransactionId();
	          // Actualizar transactionId
	          payload.put(TRANSACTION_ID, nuevoId);
	          // Convertir nuevamente a JSON
	          String jsonActualizado = mapper.writeValueAsString(jsonMap);
	          return jsonActualizado;
	      } catch (JsonParseException e) {
		        String error = "Error al parsear JSON en updateJSON -> "
		                + e.getClass().getSimpleName()
		                + ": "
		                + e.getMessage();
		        testCase.addCodeEvidenceCurrentStep(error);
		        throw new RuntimeException(error, e);
	      }
	  }
	
		/**
		 * Se valida campo price contenga un numero decimal valido.
		 * 
		 * @param jsonBody          - json del request o response
		 * @param campos - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 18/Marzo/2026
		 */
		public boolean validaDecimal(String jsonBody, List<String> campos) {
			try {
				JsonPath jsonPath = new JsonPath(jsonBody);
				boolean esValido = true;
				for (String campo : campos) {
					Object valor = jsonPath.get(campo);
					if (valor == null) {
						esValido = false;
						continue;
					}
					// Si ya es número, es válido
					if (valor instanceof Number) {
						continue;
					}
					String strValor = valor.toString();
					if (!strValor.matches("^-?\\d+(\\.\\d+)?$")) {
						esValido = false;
					}
				}
				return esValido;
			} catch (JSONException e) {
			    String error = JSON_ERROR_VALIDA_RECHAZO
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			}
		}

		/**
		 * Validar campo currencyCode correcto (MXN).
		 * 
		 * @param jsonBody          - json del request o response
		 * @param campo - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 20/Marzo/2026
		 */
		public boolean validaCurrencyCodeMXN(String jsonBody, String campo) {

		    if (campo == null) {
		        return false;
		    }

		    String strValor = campo.trim();

		    return strValor.equalsIgnoreCase("MXN");
		}
		
		/**
		 * Validar campo currencyCode incorrecto (Cualquiera diferente a MXN).Solo 3
		 * letras de longitud y mayusculas.
		 * 
		 * @param jsonBody          - json del request o response
		 * @param campos - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 20/Marzo/2026
		 */
		public boolean validaCurrencyCodeIncorrectoFormatoValido(String jsonBody, String campos) {

		    if (campos == null) {
		        return false;
		    }

		    String strValor = campos.trim();

		    // Validar formato: exactamente 3 letras mayúsculas
		    boolean formatoValido = strValor.matches("^[A-Z]{3}$");

		    if (!formatoValido) {
		        return false;
		    }

		    // Validar que NO sea MXN
		    if (strValor.equals("MXN")) {
		        return false;
		    }

		    return true;
		}
		
		/**
		 * Validar freedompop rechaze el request cuando el currencyCode cumple con el
		 * formato pero no es la moneda correcta.(Ejemplo: USD)..
		 * 
		 * @param response         - json del request o response
		 * @param httpCodeEsperado - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 20/Marzo/2026
		 */
		public boolean validaRechazoPorCurrencyIncorrecto(String response, int httpCodeEsperado) {
			try {
				JSONObject json = new JSONObject(response);
				// Validar HTTP code esperado (ej: 400)
				if (httpCodeEsperado != 400) {
					return false;
				}
				// Validar que exista mensaje de error
				String mensaje = json.optString(MESSAGE);
				if (mensaje.isEmpty()) {
					return false;
				}
				boolean contieneErrorCurrency = mensaje.toLowerCase().contains("currency");
				return contieneErrorCurrency;
			} catch (JSONException e) {
			    String error = JSON_ERROR_VALIDA_RECHAZO
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			} 
		}
		
		/**
		 * Validar campo country contenga el valor correcto (MX).
		 * 
		 * @param jsonBody          - json del request o response
		 * @param campos - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 20/Marzo/2026
		 */
		public boolean validaCountryCorrecto(String jsonBody, String campos) {

		    if (campos == null) {
		        return false;
		    }

		    String strValor = campos.trim();

		    // Validar formato: 2 letras mayúsculas
		    if (!strValor.matches("^[A-Z]{2}$")) {
		        return false;
		    }

		    // Validar valor esperado
		    return strValor.equals("MX");
		}
		
		/**
		 * Validar campo country incorrecto (Cualquiera diferente a MX o longitud no
		 * permitida).
		 * 
		 * @param jsonBody          - json del request o response
		 * @param httpCodeEsperado - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 20/Marzo/2026
		 */
		public boolean validaRechazoServicio(String response, int httpCodeEsperado) {
			try {
				JSONObject json = new JSONObject(response);
				if (httpCodeEsperado != 400) {
					return false;
				}
				String mensaje = json.optString(MESSAGE);
				if (mensaje.isEmpty()) {
					return false;
				}
				return true;
			} catch (JSONException e) {
			    String error = JSON_ERROR_VALIDA_RECHAZO
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			} 
		}

		/**
		 * Validar provider_id correcto (freedompop) API acepta el request y continúa
		 * con persistencia.
		 * 
		 * @param response     - json del request o response
		 * @param provider - provider enviado desde el Dataprovider
		 * @param providerEsperado - provider enviado desde el dataprovider
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 23/Marzo/2026
		 */
		public boolean validaProviderCorrectoConPersistencia(String response, String provider, String providerEsperado) {
			try {
				JSONObject json = new JSONObject(response);
				boolean providerCorrecto = provider.equalsIgnoreCase(providerEsperado);
				boolean persistenciaOk = json.optString(MESSAGE).contains("ID")
						|| json.optString(MESSAGE).contains(TRANSACTION_ID)
						|| json.optString(MESSAGE).contains(SUCCESSFULLY);
				if (!(providerCorrecto && persistenciaOk)) {
					return false;
				}
				return true;
			} catch (JSONException e) {
			    String error = "Error de formato JSON en validaProviderCorrectoConPersistencia -> "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			}
		}
		
		/**
		 * Validar provider_id incorrecto (diferente a freedompop), API rechaza el
		 * request con HTTP 400 Bad Request.
		 * 
		 * @param response     - json del request o response
		 * @param provider - provider enviado desde el Dataprovider
		 * @param providerEsperado - provider enviado desde el dataprovider
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 23/Marzo/2026
		 */
		public boolean validaProviderIncorrecto(String response, String provider, String providerEsperado) {
			try {
				JSONObject json = new JSONObject(response);
				boolean rechazoOk = json.optString(MESSAGE).contains("Invalid provider_id");
				boolean providerIncorrecto = !provider.equalsIgnoreCase(providerEsperado);
				if (!providerIncorrecto) {
					return false;
				}
				return rechazoOk;
			} catch (JSONException e) {
			    String error = "Error de formato JSON en validaProviderIncorrecto -> "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			}
		}
		
		  /**
			 * Método para obtener el idTransaction
			 * @param message
			 * @author Mario Garza
			 * @throws Exception
			 * @date 11/06/2026
			 */
		  public String obtenerIdTransaction(String message) {
			    Pattern pattern = Pattern.compile("ID-([^\\)]+)");
			    Matcher matcher = pattern.matcher(message);
			    if (matcher.find()) {
			        String idTransaction = matcher.group(1);
			        return idTransaction;
			    }
			    return null;
			}
		
		/**
		 * Validar que API responda con HTTP 201 Created y Response incluye el ID
		 * generado y transactionId.
		 * 
		 * @param jsonBody          - json del request o response
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 23/Marzo/2026
		 */
		public boolean validaCreacionExitosaConIds(String jsonBody) {
			try {
				JSONObject json = new JSONObject(jsonBody);
				// Validar existencia de campos
				boolean idExitoso = json.optString(MESSAGE).contains("ID");
				boolean transactionExitoso = json.optString(MESSAGE).contains(TRANSACTION_ID);
				String message = json.optString(MESSAGE);
				Pattern patternId = Pattern.compile("\\(ID-(.*?)\\)");
				Pattern patternTransaction = Pattern.compile("\\(transactionId-(.*?)\\)");
				Pattern patternCreacion = Pattern.compile(SUCCESSFULLY);
				Matcher matcherId = patternId.matcher(message);
				Matcher matcherTransaction = patternTransaction.matcher(message);
				Matcher matcher = patternCreacion.matcher(message);
				String id = "";
				String transactionId = "";
				String resultado = "";
				if (matcherId.find()) {
					id = matcherId.group(1);
				}
				if (matcherTransaction.find()) {
					transactionId = matcherTransaction.group(1);
				}
				if (matcher.find()) {
					resultado = matcher.group();
				}
				// Evidencia
				testCase.addTextEvidenceCurrentStep("****************\n" + "ID: " + id + "\n" + "\n" + "****************\n"
						+ "TransactionID: " + transactionId + "\n" + "\n" + "*********************\n"
						+ "Resultado Validación: " + resultado + "\n");
				return idExitoso && transactionExitoso;
			} catch (JSONException e) {
			    String error = "Error de formato JSON en validaCreacionExitosaConIds -> "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			}
		}
		
		/**
		 * Validar registro de error en log codigo 409.
		 * 
		 * @param jsonBody          - json del request o response
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 24/Marzo/2026
		 */
		public boolean validaError409ConLog(String jsonBody) {
			try {
				JSONObject json = new JSONObject(jsonBody);
				// Posibles campos donde viene el error
				String message = json.optString(MESSAGE).toLowerCase();
				String error = json.optString("error").toLowerCase();
				String code = json.optString("code").toLowerCase();
				// Validaciones flexibles (anti-frágil)
				boolean tieneMensajeError = message.contains("conflict") || message.contains("already exists")
						|| message.contains("duplicate");
				boolean tieneErrorField = !error.isEmpty();
				boolean tieneCodigo = code.contains("409");
				return tieneMensajeError || tieneErrorField || tieneCodigo;
			} catch (JSONException e) {
			    String error = "Error de formato JSON en validaError409ConLog -> "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			}
		}
		
		/**
		 * Validar el Health Checks del worker
		 * 
		 * @param jsonBody    - json del request o response
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 31/Marzo/2026
		 */
		public boolean validaHealthWorker(String jsonBody) {
			try {
				JSONObject json = new JSONObject(jsonBody);
				String status = json.optString("status").toLowerCase();
				boolean estaArriba = status.equals("up") || status.equals("ok") || status.equals("healthy");
				return estaArriba;
			} catch (JSONException e) {
			    String error = "Error de formato JSON en validaHealthWorker -> "
			            + e.getMessage();
			    testCase.addCodeEvidenceCurrentStep(error);
			    throw new RuntimeException(error, e);
			}
		}
		
		/**
		 * Validar el Health Checks del worker Dependencias Health
		 * 
		 * @param jsonBody          - json del request o response
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 31/Marzo/2026
		 */
		public boolean validaDependenciasHealth(JSONObject json) {
			
				if (!json.has("components")) {
					return true; // no hay dependencias declaradas
				}
				JSONObject components = json.getJSONObject("components");
				Iterator<String> keys = components.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					JSONObject comp = components.getJSONObject(key);
					String status = comp.optString("status").toLowerCase();
					if (!status.equals("up") && !status.equals("ok")) {
						return false;
					}
				}
				return true;
			
		}
		
		/**
		 * Método para actualizar el valor del Json
		 * 
		 * @author Jazmin Jimenez Torres
		 * @throws Exception
		 * @return true si actualiza el json, falso si falla al actualizar el json.
		 * @date 15/05/2026
		 * @param json a actualizar contenido en el Dataprovider
		 * 
		 */
		public boolean updateJSON(String jSon) throws Exception {
			try {
				ObjectMapper mapper = new ObjectMapper();
				
				// Convertir_JSON_a_Map
				@SuppressWarnings("unchecked")
				Map<String, Object> jsonMap = mapper.readValue(jSon, Map.class);
				// Navegar_al_request_payload
				@SuppressWarnings("unchecked")
				Map<String, Object> payload = (Map<String, Object>) jsonMap.get("request_payload");
				if (payload == null) {
					throw new RuntimeException("No existe request_payload en el JSON");
				}
				// Generar_nuevo_transactionId
				String nuevoId = OxxoCelUtils.generarTransactionId409I();
				// Actualizar_valor
				payload.put(TRANSACTION_ID, nuevoId);
				// Generar fechas
				String fechaEnvio = OxxoCelUtils.formatFechaHora(1);
				String horaEnvio = OxxoCelUtils.formatFechaHora(2);
				String created_at = OxxoCelUtils.formatFechaHora(3);
				// Navegar_al_extra_data
				@SuppressWarnings("unchecked")
				Map<String, Object> extradata = (Map<String, Object>) jsonMap.get("extra_data");
				if (extradata == null) {
					throw new RuntimeException("No existe extra_data en el JSON");
				}
				// Actualizar_fechas
				extradata.put("fechaenvio", fechaEnvio);
				extradata.put("horaenvio", horaEnvio);
				// Seleccionar_al_client_id
				jsonMap.put("created_at", created_at);
				return true;
			} catch (JsonParseException e) {
		        String error = "Error al parsear JSON en updateJSON -> "
		                + e.getClass().getSimpleName()
		                + ": "
		                + e.getMessage();
		        testCase.addCodeEvidenceCurrentStep(error);
		        throw new RuntimeException(error, e);
		    } catch (JsonMappingException e) {
		        String error = "Error al mapear JSON en updateJSON -> "
		                + e.getClass().getSimpleName()
		                + ": "
		                + e.getMessage();
		        testCase.addCodeEvidenceCurrentStep(error);
		        throw new RuntimeException(error, e);
		    } 
		}

		/**
		 * Extrae el ID de transacción del campo "message" en el JSON recibido
		 * 
		 * @author Jazmin Jimenez Torres
		 * @throws Exception
		 * @return El idTransaction si existe, de lo contrario null
		 * @date 15/05/2026
		 * @param jsonResponse Objeto JSON que contiene el campo "message"
		 * 
		 */
		public String extraerIdTransaction(JSONObject jsonResponse) {

		    try {
		        String idTransaction = null;
		        String message = jsonResponse.getString(MESSAGE);
		        // Buscar el patrón ID-XXXX dentro del mensaje
		        Pattern pattern = Pattern.compile("ID-([^\\)]+)");
		        Matcher matcher = pattern.matcher(message);
		        if (matcher.find()) {
		            idTransaction = matcher.group(1);
		            testCase.addCodeEvidenceCurrentStep(
		                "Response Idtransaction: '" + idTransaction + "'"
		            );
		            return idTransaction;
		        }
		        return null;
		    } catch (JSONException e) {
		        String error = String.format(
		            "Error al extraer IdTransaction. Tipo: %s | Mensaje: %s",
		            e.getClass().getSimpleName(),
		            e.getMessage()
		        );
		        testCase.addCodeEvidenceCurrentStep(error);
		        throw new RuntimeException(error, e);
		    }
		}
		
		/**
		 * Validar catalogo
		 * 
		 * @param jsonBody          - json del request o response
		 * @param pathResulEsperado - paths de los campos del json y su respuesta
		 *                          esperada
		 * @return regresa true si la funcionalidad es exitosa
		 * @author mario.garza@gtim.com
		 * @date 20/Marzo/2026
		 */

		public boolean validaCatalogo(String jsonBody, String campo, String regex, String valorEsperado, boolean debeSerValido) {
			if (campo == null) {
				return !debeSerValido;
				}
				String valor = campo.trim();
				boolean formatoValido = valor.matches(regex);
				boolean valorCorrecto = valor.equals(valorEsperado);
				if (debeSerValido) {
				return formatoValido && valorCorrecto;
				}
				return !formatoValido || !valorCorrecto;
			}
			
}
