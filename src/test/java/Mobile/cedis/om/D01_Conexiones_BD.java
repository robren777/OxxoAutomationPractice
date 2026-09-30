package Mobile.cedis.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.testng.Assert;

import Mobile.util.GlobalVariables;

//import com.mysql.cj.xdevapi.Statement;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class D01_Conexiones_BD {

	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

//================================================= Identificadores =================================================/

	public D01_Conexiones_BD(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {

		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;

	}

	private SQLUtil dbLog;

	public void conexionrdmmty() throws SQLException {

		if (dbLog == null) { // Solo inicializar si no ha sido inicializado
			dbLog = new SQLUtil(GlobalVariables.urlRDMmty, GlobalVariables.usercRDMmty, GlobalVariables.passwordRDMmty);
			System.out.println("Conexión a BD de RDM Mty EXITOSA! \n");
		}
	}

	private SQLUtil dbLogV11;

	public void conexionrdmmtyv11() throws SQLException {

		if (dbLogV11 == null) { // Solo inicializar si no ha sido inicializado
			dbLogV11 = new SQLUtil(GlobalVariables.urlRDMv11, GlobalVariables.userRDMv11,
					GlobalVariables.passwordRDMv11);
			System.out.println("Conexión a BD de RDM Nube V11 Mty EXITOSA! \n");
		}
	}

	public SQLUtil connectionTlajo; // TLAJOMULCO
	public SQLUtil connectionMTY; // MONTERREY
	public SQLUtil connectionRDM100; // TLAJOMULCO
	public SQLUtil connectionSimplexQA; // BD MOVILIDAD
	public SQLUtil connectionRDMv11; // RDM Tradicional V11
	public SQLUtil connectionTP; // Table Plus QA Staging

	// Método para validar la conexión BD RDM Tlajomulco
	public void verificarConexionRdmTlajo() throws SQLException {
		// Connection connectionTlajo = null;
		boolean isConnected;
		try {
			// connectionTlajo = DriverManager.getConnection(GlobalVariables.URLTlajo,
			// GlobalVariables.UsercTlajo, GlobalVariables.passwordTlajo);
			connectionTlajo = new SQLUtil(GlobalVariables.URLTlajo, GlobalVariables.UsercTlajo,
					GlobalVariables.passwordTlajo);
			if (connectionTlajo != null) {
				isConnected = true;
				System.out.println("Conexión a la BD de Tlajomulco EXITOSA.");
				testCase.addTextEvidenceCurrentStep("Conexión a la BD de Tlajomulco EXITOSA.");
				Assert.assertTrue(isConnected, "Conexión a la BD de Tlajomulco EXITOSA.");
			}
		} catch (SQLException e) {
			isConnected = false;
			System.err.println("Error al conectar a la base de datos: " + e.getMessage());
			Assert.fail("Error al conectar a la base de datos.", null);
		}
	}

	// Método para validar la conexión BD RDM Monterrey
	public void verificarConexionRdmMty() throws SQLException {
		// Connection connectionMTY = null;
		boolean successfullConnected;
		try {
			// connectionMTY = DriverManager.getConnection(GlobalVariables.urlRDMmty,
			// GlobalVariables.usercRDMmty, GlobalVariables.passwordRDMmty);
			connectionMTY = new SQLUtil(GlobalVariables.urlRDMmty, GlobalVariables.usercRDMmty,
					GlobalVariables.passwordRDMmty);
			if (connectionMTY != null) {
				successfullConnected = true;
				System.out.println("Conexión a la BD de Monterrey EXITOSA.");
				testCase.addTextEvidenceCurrentStep("Conexión a la BD de Monterrey EXITOSA.");
				Assert.assertTrue(successfullConnected, "Conexión a la BD de Monterrey EXITOSA.");
			}
		} catch (SQLException e) {
			successfullConnected = false;
			System.err.println("Error al conectar a la base de datos: " + e.getMessage());
			Assert.fail("Error al conectar a la base de datos.", null);
		}
	}

	// Método para validar la conexión BD RDM100 Tlajomulco
	public void verificarConexionRDM100() throws SQLException {
		// Connection connectionTlajo = null;
		boolean isConnected;
		try {
			// connectionTlajo = DriverManager.getConnection(GlobalVariables.URLTlajo,
			// GlobalVariables.UsercTlajo, GlobalVariables.passwordTlajo);
			connectionTlajo = new SQLUtil(GlobalVariables.urlRDM100, GlobalVariables.userRDM100,
					GlobalVariables.passwordRDM100);
			if (connectionTlajo != null) {
				isConnected = true;
				System.out.println("Conexión a la BD100 Tlajomulco EXITOSA.");
				testCase.addTextEvidenceCurrentStep("Conexión a la BD RDM100 Tlajomulco EXITOSA.");
				Assert.assertTrue(isConnected, "Conexión a la BD RDM100 Tlajomulco EXITOSA.");
			}
		} catch (SQLException e) {
			isConnected = false;
			System.err.println("Error al conectar a la base de datos: " + e.getMessage());
			Assert.fail("Error al conectar a la base de datos.", null);
		}
	}

	// Método para cerrar o terminar la conexión BD RDM Monterrey
	public void cerrarConexionRdmMty() {
		try {
			if (connectionMTY != null) {
				connectionMTY.close();
				System.out.println("Se cerró la conexión de forma correcta.");
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión.");
			e.printStackTrace();
		}
	}

	// Método para cerrar o terminar la conexión BD RDM Tlajomulco
	public void cerrarConexionRdmTlajo() {
		try {
			if (connectionTlajo != null) {
				connectionTlajo.close();
				System.out.println("Se cerró la conexión de forma correcta.");
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión.");
			e.printStackTrace();
		}
	}

	// Método para cerrar o terminar la conexión BD RDM100 Tlajomulco
	public void cerrarConexionRDM100() {
		try {
			if (connectionRDM100 != null) {
				connectionRDM100.close();
				System.out.println("Se cerró la conexión de forma correcta.");
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión.");
			e.printStackTrace();
		}
	}

	// Metodo de consulta PAV en RDM MTY
	public void consultaPAVZONEG() {

		String consultaPAVZONEG = "select * from rdm100.xxfc_pav where ROWNUM <= 10 order by INSERT_DATE desc";

		if (dbLogV11 == null) {
			throw new IllegalStateException("La conexión a la base de datos no está establecida.");
		}
		try {
			SQLResult resultQueryIS = dbLog.executeQuery(consultaPAVZONEG);
			boolean validacosnulta = resultQueryIS.isEmpty();
			System.out.print(validacosnulta);
			testCase.addQueryEvidenceCurrentStep(resultQueryIS);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}

	}

	// Metodo de consulta PAV ZONE en RDM MTY
	public void consultaPAVZONEe() {

		String consultaPAVZONEe = "Select * from rdm100.xxfc_pav_zone_v WHERE DISTRO_NBR = '3150022305'";

		if (dbLogV11 == null) {
			throw new IllegalStateException("La conexión a la base de datos no está establecida.");
		}
		try {
			SQLResult resultQueryIS = dbLog.executeQuery(consultaPAVZONEe);
			boolean validacosnulta = resultQueryIS.isEmpty();
			System.out.print(validacosnulta);
			testCase.addQueryEvidenceCurrentStep(resultQueryIS);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}

	}

	// Método para validar la conexión BD RDM QA Cloud V11
	public void verificarConexionRdmNubev11() throws SQLException {
		// Connection connectionMTY = null;
		boolean successfullConnected;
		try {
			// connectionMTY = DriverManager.getConnection(GlobalVariables.urlRDMmty,
			// GlobalVariables.usercRDMmty, GlobalVariables.passwordRDMmty);
			connectionRDMv11 = new SQLUtil(GlobalVariables.urlRDMv11, GlobalVariables.userRDMv11,
					GlobalVariables.passwordRDMv11);
			if (connectionRDMv11 != null) {
				successfullConnected = true;
				System.out.println("Conexión a la BD RDM Cloud V11 EXITOSA.");
				testCase.addTextEvidenceCurrentStep("Conexión a la RDM Cloud V11 EXITOSA.");
				Assert.assertTrue(successfullConnected, "Conexión a la BD RDM Cloud V11 EXITOSA.");
			}
		} catch (SQLException e) {
			successfullConnected = false;
			System.err.println("Error al conectar a la base de datos: " + e.getMessage());
			Assert.fail("Error al conectar a la base de datos.", null);
		}
	}

	// Método para cerrar o terminar la conexión BD RDM QA Cloud V11
	public void cerrarConexionRdmNubev11() {
		try {
			if (connectionRDMv11 != null) {
				connectionRDMv11.close();
				System.out.println("Se cerró la conexión de forma correcta.");
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión.");
			e.printStackTrace();
		}
	}

	// Método para validar conexión con TablePlus
	public void verificarConexionTablePlusStaging() throws SQLException {
		String url = GlobalVariables.hosttp;

		try {
			DriverManager.getConnection(url);
			System.out.println("Conexión exitosa a la base de datos MySQL.");
			testCase.addTextEvidenceCurrentStep("Conexión exitosa a la base de datos MySQL.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void cerrarConexionTablePlus() throws SQLException {
		String url = GlobalVariables.hosttp;
		Connection connection = DriverManager.getConnection(url);
		connection.close();

	}

	public void consultatableplus() {

		String query = "SELECT * FROM xxbdo_historico_correos WHERE cr_plaza = '10NLA' AND cr_tienda = '50PZL' "
				+ "AND transferencia = '3132124158' and origen = 'cedis'"; // Cambia

		try (Connection connection = DriverManager.getConnection(GlobalVariables.hosttp)) {
			java.sql.Statement stmt = connection.createStatement();

			boolean esResultSet = stmt.execute(query);

			// Si la consulta es un SELECT, obtener el ResultSet
			if (esResultSet) {
				try (ResultSet rs = stmt.getResultSet()) {
					while (rs.next()) {
						String body = rs.getString("body");
						System.out.println("Body: " + body);
					}
				}
			} else {
				System.out.println("La consulta no devolvió resultados.");
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta.");
			e.printStackTrace();
		}

	}

	public void consultarYValidarClave() {
		String query = "SELECT * FROM xxbdo_historico_correos WHERE cr_plaza = '10NLA' AND cr_tienda = '50PZL' AND transferencia = '3132124158' and origen = 'cedis'"; // Cambia

		try (Connection connection = DriverManager.getConnection(GlobalVariables.hosttp)) {
			// Crear un Statement
			java.sql.Statement stmt = connection.createStatement();

			// Ejecutar la consulta
			boolean esResultSet = stmt.execute(query);

			// Si la consulta es un SELECT, obtener el ResultSet
			if (esResultSet) {
				try (ResultSet rs = stmt.getResultSet()) {
					while (rs.next()) {
						String body = rs.getString("body"); // Cambia "body" por la columna que contiene el JSON
						System.out.println("\n\nBody: " + body + "\n\n");

						// Validar si el JSON contiene las claves requeridas
						if (body != null && !body.isEmpty()) {
							try {
								// Convertir el body a JSONObject
								JSONObject jsonBody = new JSONObject(body);
								testCase.addTextEvidenceCurrentStep(body);
								// Validar clave "tipo_distro"
								if (jsonBody.has("tipo_distro")) {
									String tipoDistro = jsonBody.getString("tipo_distro");
									if (tipoDistro != null && !tipoDistro.isEmpty()) {
										System.out.println("La clave 'tipo_distro' existe con el valor: " + tipoDistro);
										testCase.addTextEvidenceCurrentStep("Clave 'tipo_distro' - Valor: " + tipoDistro);
									} else {
										System.err.println("La clave 'tipo_distro' está vacía.");
									}
								} else {
									System.err.println("La clave 'tipo_distro' no está presente en el JSON.");
								}

								// Validar clave "ruta_original"
								if (jsonBody.has("ruta_original")) {
									String rutaOriginal = jsonBody.getString("ruta_original");
									if (rutaOriginal != null && !rutaOriginal.isEmpty()) {
										System.out.println(
												"La clave 'ruta_original' existe con el valor: " + rutaOriginal);
										testCase.addTextEvidenceCurrentStep("Clave 'ruta_original' - Valor: " + rutaOriginal);

									} else {
										System.err.println("La clave 'ruta_original' está vacía.");
										// Puedes agregar aquí el manejo de error como Assertions o fallos de prueba
									}
								} else {
									System.err.println("La clave 'ruta_original' no está presente en el JSON.");
									// Puedes agregar aquí el manejo de error como Assertions o fallos de prueba
								}

								// Validar clave "ruta_propuesta"
								if (jsonBody.has("ruta_propuesta")) {
									String rutaPropuesta = jsonBody.getString("ruta_propuesta");
									if (rutaPropuesta != null && !rutaPropuesta.isEmpty()) {
										System.out.println(
												"La clave 'ruta_propuesta' existe con el valor: " + rutaPropuesta);
										testCase.addTextEvidenceCurrentStep("Clave 'ruta_propuesta' - Valor: " + rutaPropuesta);

									} else {
										System.err.println("La clave 'ruta_propuesta' está vacía.");
									}
								} else {
									System.err.println("La clave 'ruta_propuesta' no está presente en el JSON.");
								}

							} catch (Exception e) {
								System.err.println("Error al procesar el JSON: " + e.getMessage());
								e.printStackTrace();
							}
						} else {
							System.err.println("El campo 'body' está vacío o nulo.");
						}
					}
				}
			} else {
				System.out.println("La consulta no devolvió resultados.");
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta.");
			e.printStackTrace();
		}
	}
	
	public void consultarYValidarClaveDev() {
		String query = "SELECT * FROM xxbdo_historico_correos WHERE cr_plaza = '10NLA' AND cr_tienda = '50PZL' AND transferencia = '3132124158' and origen = 'cedis-devolucion'"; // Cambia

		try (Connection connection = DriverManager.getConnection(GlobalVariables.hosttp)) {
			// Crear un Statement
			java.sql.Statement stmt = connection.createStatement();

			// Ejecutar la consulta
			boolean esResultSet = stmt.execute(query);

			// Si la consulta es un SELECT, obtener el ResultSet
			if (esResultSet) {
				try (ResultSet rs = stmt.getResultSet()) {
					while (rs.next()) {
						String body = rs.getString("body"); // Cambia "body" por la columna que contiene el JSON
						System.out.println("\n\nBody: " + body + "\n\n");

						// Validar si el JSON contiene las claves requeridas
						if (body != null && !body.isEmpty()) {
							try {
								// Convertir el body a JSONObject
								JSONObject jsonBody = new JSONObject(body);
								testCase.addTextEvidenceCurrentStep(body);
								// Validar clave "tipo_distro"
								if (jsonBody.has("tipo_distro")) {
									String tipoDistro = jsonBody.getString("tipo_distro");
									if (tipoDistro != null && !tipoDistro.isEmpty()) {
										System.out.println("La clave 'tipo_distro' existe con el valor: " + tipoDistro);
										testCase.addTextEvidenceCurrentStep("Clave 'tipo_distro' - Valor: " + tipoDistro);
									} else {
										System.err.println("La clave 'tipo_distro' está vacía.");
									}
								} else {
									System.err.println("La clave 'tipo_distro' no está presente en el JSON.");
								}

								// Validar clave "ruta_original"
								if (jsonBody.has("ruta_original")) {
									String rutaOriginal = jsonBody.getString("ruta_original");
									if (rutaOriginal != null && !rutaOriginal.isEmpty()) {
										System.out.println(
												"La clave 'ruta_original' existe con el valor: " + rutaOriginal);
										testCase.addTextEvidenceCurrentStep("Clave 'ruta_original' - Valor: " + rutaOriginal);

									} else {
										System.err.println("La clave 'ruta_original' está vacía.");
										// Puedes agregar aquí el manejo de error como Assertions o fallos de prueba
									}
								} else {
									System.err.println("La clave 'ruta_original' no está presente en el JSON.");
									// Puedes agregar aquí el manejo de error como Assertions o fallos de prueba
								}

								// Validar clave "ruta_propuesta"
								if (jsonBody.has("ruta_propuesta")) {
									String rutaPropuesta = jsonBody.getString("ruta_propuesta");
									if (rutaPropuesta != null && !rutaPropuesta.isEmpty()) {
										System.out.println(
												"La clave 'ruta_propuesta' existe con el valor: " + rutaPropuesta);
										testCase.addTextEvidenceCurrentStep("Clave 'ruta_propuesta' - Valor: " + rutaPropuesta);

									} else {
										System.err.println("La clave 'ruta_propuesta' está vacía.");
									}
								} else {
									System.err.println("La clave 'ruta_propuesta' no está presente en el JSON.");
								}

							} catch (Exception e) {
								System.err.println("Error al procesar el JSON: " + e.getMessage());
								e.printStackTrace();
							}
						} else {
							System.err.println("El campo 'body' está vacío o nulo.");
						}
					}
				}
			} else {
				System.out.println("La consulta no devolvió resultados.");
			}

		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta.");
			e.printStackTrace();
		}
	}

}