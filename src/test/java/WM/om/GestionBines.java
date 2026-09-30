package WM.om;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import WM.util.GlobalVariables;
import modelo.TestCase;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class GestionBines {
	
	static TestCase testCase;
	
	public GestionBines(TestCase testCase) {
		this.testCase = testCase;
	}
	
	
	private static final String BINS_ESPERADOS =
	        "235018, 411932, 418095, 423727, 432103, 432851, 440784, 440955, 446621, 450933, " +
	        "453699, 454410, 454622, 455152, 456701, 456823, 473021, 473039, 473078, 473085, " +
	        "480065, 480066, 483301, 483302, 483303, 490786, 491259, 491308, 506220, 506232, " +
	        "506318, 506367, 506486, 506493, 512560, 512811, 513674, 514817, 515459, 515535, " +
	        "515541, 516196, 518524, 518772, 520033, 521391, 521652, 523428, 524013, 526100, " +
	        "528862, 529042, 529360, 530200, 532138, 533347, 534933, 534934, 535003, 537882, " +
	        "540650, 541133, 544781, 544812, 544848, 545475, 545564, 545828, 545980, 546149, " +
	        "546178, 546394, 546431, 546434, 546446, 546506, 546831, 547076, 548379, 548582, " +
	        "555980, 53241090, 54299754, 52737556, 54493712, 54493752, 53415239, 46557990, " +
	        "53241043, 53240633, 52737521, 52737548, 54493761, 47303805, 55775748, 46559397, " +
	        "53239222, 53240659, 54406070, 53063676, 53063697, 53787242, 52737592, 46559399, " +
	        "54492322, 54492389, 54492393, 53415260, 47302921, 47302935, 47302936, 46557906, " +
	        "46559357, 46559358, 53716598, 53239229, 52626311, 52626315, 53063668, 53063689, " +
	        "54493735, 54493757, 53415299, 47302923, 46559301, 52871649, 54299786, 54406019, " +
	        "55775703, 53063650, 53716536, 46559396, 52626341, 52626391, 52871644, 53787282, " +
	        "53063633, 54492318, 54492359, 54492378, 53415294, 47302922, 46559308, 46559359, " +
	        "46559383, 53063615, 53063690, 52737587, 54493740, 54493750, 53415284, 46557957, " +
	        "54492303, 53241069, 52871659, 53415203, 52737555, 47303800, 54492366, 54492367, " +
	        "54492384, 54493736, 53415226, 53415264, 46557991, 54493713, 47302920";

	private static final List<String> BINS_NUEVOS_INSERTADOS = Arrays.asList(
	        "235018",   "411932",   "418095",   "423727",   "432103",   "432851",
	        "440784",   "440955",   "446621",   "450933",   "453699",   "454410",
	        "454622",   "455152",   "456701",   "456823",   "473021",   "473039",
	        "473078",   "473085",   "480065",   "480066",   "483301",   "483302",
	        "483303",   "490786",   "491259",   "491308",   "506220",   "506232",
	        "506318",   "506367",   "506486",   "506493",   "512560",   "512811",
	        "513674",   "514817",   "515459",   "515535",   "515541",   "516196",
	        "518524",   "518772",   "520033",   "521391",   "521652",   "523428",
	        "524013",   "526100",   "528862",   "529042",   "529360",   "530200",
	        "532138",   "533347",   "534933",   "534934",   "535003",   "537882",
	        "540650",   "541133",   "544781",   "544812",   "544848",   "545475",
	        "545564",   "545828",   "545980",   "546149",   "546178",   "546394",
	        "546431",   "546434",   "546446",   "546506",   "546831",   "547076",
	        "548379",   "548582",   "555980",   "53241090", "54299754", "52737556",
	        "54493712", "54493752", "53415239", "46557990", "53241043", "53240633",
	        "52737521", "52737548", "54493761", "47303805", "55775748", "46559397",
	        "53239222", "53240659", "54406070", "53063676", "53063697", "53787242",
	        "52737592", "46559399", "54492322", "54492389", "54492393", "53415260",
	        "47302921", "47302935", "47302936", "46557906", "46559357", "46559358",
	        "53716598", "53239229", "52626311", "52626315", "53063668", "53063689",
	        "54493735", "54493757", "53415299", "47302923", "46559301", "52871649",
	        "54299786", "54406019", "55775703", "53063650", "53716536", "46559396",
	        "52626341", "52626391", "52871644", "53787282", "53063633", "54492318",
	        "54492359", "54492378", "53415294", "47302922", "46559308", "46559359",
	        "46559383", "53063615", "53063690", "52737587", "54493740", "54493750",
	        "53415284", "46557957", "54492303", "53241069", "52871659", "53415203",
	        "52737555", "47303800", "54492366", "54492367", "54492384", "54493736",
	        "53415226", "53415264", "46557991", "54493713", "47302920"
	    );
	
	/**
	 * @Description Realiza multiples intentos de conexion a BD FCDASPFR 
	 * @Autor Gerardo Lopez
	 * @throws Exception
	 * @date 26/Mayo/2026
	 */
    private SQLUtil conectarConReintentos() throws Exception {
        int maxIntentos = 5;
        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                SQLUtil db = new SQLUtil(
                    GlobalVariables.DB_HOST_FCDASPFR_QA,
                    GlobalVariables.DB_USER_FCDASPFR_QA,
                    GlobalVariables.DB_PASSWORD_FCDASPFR_QA
                );
                System.out.println("BD Conexión exitosa en el intento " + intento);
                return db;
            } catch (Exception eConn) {
                System.err.println("BD Intento " + intento + " fallido: " + eConn.getMessage());
                if (intento == maxIntentos) {
                    throw new Exception("No se pudo conectar a la BD tras " + maxIntentos + " intentos: " + eConn.getMessage());
                }
                try {
                    Thread.sleep(2000L * intento);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new Exception("Hilo interrumpido durante reintento de conexión a BD.");
                }
            }
        }
        throw new Exception("No se pudo establecer conexión a la BD.");
    }
    
    /**
     * Obtener conexion realizada en el metodo conectarConReintentos()
     *
     *@author	Oliver Martinez
     *@throws 	Exception Error de conexion a la base de datos.
     *@Date		06/25/2026
     */
    public SQLUtil getConnection() throws Exception {
    	SQLUtil connection = null;
    	try {
    		connection =  conectarConReintentos();
		} catch (InterruptedException e) {
			throw new Exception("No se pudo establecer conexión a la BD.");
		}
		return connection;
    }
    
    /**
	 * @Description Realiza coneccion a la BD FCTDCQA_QRO
	 * @Autor Gerardo Lopez
	 * @throws Exception
	 * @date 28/Mayo/2026
	**/
   private SQLUtil connectFCTDCQA_QRO() throws Exception {

           try {
               SQLUtil db = new SQLUtil(
                   GlobalVariables.DB_HOST_FCTDCQA10,
                   GlobalVariables.DB_USER_FCTDCQA10,
                   GlobalVariables.DB_PASSWORD_FCTDCQA10
               );
               return db;
           } catch (Exception eConn) {
        	   throw new Exception("No se pudo establecer conexión a la BD.");
           }   
   }
    
	/**
	 * @Description Valida el conteo de registros después de la inserción.
	 * @Autor Gerardo Lopez
	 * @return TOTAL_DESPUES, FILAS_NUEVAS, RESULTADO_CONTEO
	 * @throws Exception, SQLException
	 * @date 26/Mayo/2026
	 */
    public Map<String, String> countRegisterInsert(String totalAntes, String filasInsertadas) throws SQLException,Exception {
    	try {
    	
        String queryConteo =
                "SELECT " +
                "    COUNT(1) AS TOTAL_REGISTROS_DESPUES, " +
                "    COUNT(1) - " + totalAntes + " AS FILAS_NUEVAS, " +
                "    CASE " +
                "        WHEN COUNT(1) - " + totalAntes + " = " + filasInsertadas +
                "        THEN 'OK — Conteo correcto' " +
                "        ELSE 'ALERTA — Se encontraron diferencias' " +
                "    END AS RESULTADO_CONTEO " +
                "FROM EO_RMS.TDC_BIN";
        
        SQLUtil dbFCDASPFR = conectarConReintentos();
        
    	SQLResult conteoResult = dbFCDASPFR.executeQuery(queryConteo);
        testCase.addQueryEvidenceCurrentStep(conteoResult);

        String totalDespues  = conteoResult.getData(0, "TOTAL_REGISTROS_DESPUES");
        String filasNuevas   = conteoResult.getData(0, "FILAS_NUEVAS");
        String resultConteo  = conteoResult.getData(0, "RESULTADO_CONTEO");

        testCase.addTextEvidenceCurrentStep(
            "Total registros después : " + totalDespues  + "\n" +
            "Filas nuevas detectadas : " + filasNuevas   + "\n" +
            "Resultado               : " + resultConteo
        );
        
        Map<String, String> resultados = new HashMap<>();
        resultados.put("TOTAL_DESPUES",    totalDespues);
        resultados.put("FILAS_NUEVAS",     filasNuevas);
        resultados.put("RESULTADO_CONTEO", resultConteo);
        
        return resultados;
        
    	}
    	catch (Exception e) {
	    	throw new Exception("Error al validar conteo de registro en el insert: " + e.getMessage());
    		
    	}
    }
    
    /**
     * Valida el conteo de registros después de la inserción.
     * Retorna mapa con: TOTAL_DESPUES, FILAS_NUEVAS, RESULTADO_CONTEO
     */
    public Map<String, String> validarConteoInsert(int totalAntes, int filasInsertadas) throws Exception {
        try {
            String query =
                "SELECT " +
                "    COUNT(1) AS TOTAL_DESPUES, " +
                "    COUNT(1) - " + totalAntes + " AS FILAS_NUEVAS, " +
                "    CASE " +
                "        WHEN COUNT(1) - " + totalAntes + " = " + filasInsertadas +
                "        THEN 'OK — Conteo correcto' " +
                "        ELSE 'ALERTA — Se encontraron diferencias' " +
                "    END AS RESULTADO_CONTEO " +
                "FROM EO_RMS.TDC_BIN";

            SQLUtil db = conectarConReintentos();
            SQLResult result = db.executeQuery(query);
            testCase.addQueryEvidenceCurrentStep(result);

            String totalDespues = result.getData(0, "TOTAL_DESPUES");
            String filasNuevas = result.getData(0, "FILAS_NUEVAS");
            String resultadoConteo = result.getData(0, "RESULTADO_CONTEO");

            testCase.addTextEvidenceCurrentStep(
                "TOTAL_REGISTROS_DESPUES : " + totalDespues + "\n" +
                "FILAS_NUEVAS: " + filasNuevas + "\n" +
                "RESULTADO_CONTEO: " + resultadoConteo
            );

            Map<String, String> res = new HashMap<>();
            res.put("TOTAL_DESPUES", totalDespues);
            res.put("FILAS_NUEVAS", filasNuevas);
            res.put("RESULTADO_CONTEO", resultadoConteo);
            return res;

        } catch (Exception e) {
            throw new Exception("Error en validarConteoInsert: " + e.getMessage());
        }
    }
    
	/**
	 * @Description Valida el conteo de registros antes de insertar bines y despues de insertar bienes
	 * @Autor Gerardo Lopez
	 * @return conteos
	 * @throws Exception, SQLException
	 * @date 26/Mayo/2026
	 */
    public Map<String, Integer> validConteoAntesDespues(String filasInsertadas) throws SQLException,Exception {
    	try {
    		
        String queryValidacionBines =
            "SELECT BIN, BANDERA_ID, BANK_ID, TYPE_ID, BANK_CODE " +
            "FROM EO_RMS.TDC_BIN " +
            "WHERE BIN IN (" + BINS_ESPERADOS + ")";
        
        SQLUtil dbFCDASPFR = conectarConReintentos();
        
        SQLResult validacionResult = dbFCDASPFR.executeQuery(queryValidacionBines);
        testCase.addQueryEvidenceCurrentStep(validacionResult);
        
        List<String[]> registrosEncontrados = validacionResult.getAllData();
        int encontrados = registrosEncontrados.size();
        int esperados   = Integer.parseInt(filasInsertadas);
        
        testCase.addTextEvidenceCurrentStep(
                "BINs encontrados en tabla : " + encontrados + "\n" +
                "BINs esperados : " + esperados
            );
        
        Map<String, Integer> conteos = new HashMap<>();
        conteos.put("encontrados", encontrados);
        conteos.put("esperados", esperados);
        
        return conteos;
    	}
    	catch (Exception e) {
            throw new Exception("Error en validarConteoAntesDespues: " + e.getMessage());
        }
    	
    }
    
	/**
	 * @Description Se valida que los bines insertados sean los correctos y sean identificados si llega a encontrar bines faltantes se menciona en la salida
	 * @Autor Gerardo Lopez
	 * @return binesFaltantes
	 * @throws Exception, SQLException
	 * @date 26/Mayo/2026
	 */
    public List<String[]> IdentiBinsFaltantes() throws SQLException,Exception {
    	try {
    		
    	String queryBinesFaltantes =
    		    "SELECT TO_CHAR(COLUMN_VALUE) AS BIN_FALTANTE " +
    		    "FROM TABLE( " +
    		    "    SYS.ODCINUMBERLIST(" + BINS_ESPERADOS + ") " +
    		    ") " +
    		    "MINUS " +
    		    "SELECT TO_CHAR(BIN) FROM EO_RMS.TDC_BIN " +
    		    "WHERE BIN IN (" + BINS_ESPERADOS + ")";
        
        SQLUtil dbFCDASPFR = conectarConReintentos();
    	
        SQLResult faltantesResult = dbFCDASPFR.executeQuery(queryBinesFaltantes);
        testCase.addQueryEvidenceCurrentStep(faltantesResult);
        
        List<String[]> binesFaltantes = faltantesResult.getAllData();
        
        if (!binesFaltantes.isEmpty()) {
            StringBuilder sb = new StringBuilder("BINs NO encontrados en tabla:\n");
            for (String[] fila : binesFaltantes) {
                sb.append("  - ").append(fila[0]).append("\n");
            }
            testCase.addTextEvidenceCurrentStep(sb.toString());
        } else {
            testCase.addTextEvidenceCurrentStep("Todos los BINs esperados fueron encontrados correctamente.");
        }
        
        return binesFaltantes;
    	}
    	 catch (Exception e) {
             throw new Exception("Error en Identificacion de Bines faltantes: " + e.getMessage());
         }
    	
    	
    }
    
	/**
	 * @Description Realiza diagjnostico del estado de los bines si son de QA (preexistentes) Nuevos(Recien insertados)
	 * @Autor Gerardo Lopez
	 * @return binsQA, binsNuevo, nuevosConProblema, inesperados
	 * @throws Exception
	 * @date 27/Mayo/2026
	 */
    public Map<String, List<String>> diagnosticarEstadoBines() throws Exception {
        try {
            String query =
                "SELECT B.BIN, " +
                "    CASE " +
                "        WHEN B.CREATION_DATE  > TO_DATE('27/05/2026','DD/MM/YYYY') " +
                "         OR BF.CREATION_DATE > TO_DATE('27/05/2026','DD/MM/YYYY') " +
                "         OR BK.CREATION_DATE > TO_DATE('27/05/2026','DD/MM/YYYY') " +
                "         OR CT.CREATION_DATE > TO_DATE('27/05/2026','DD/MM/YYYY') " +
                "         OR BC.CREATION_DATE > TO_DATE('27/05/2026','DD/MM/YYYY') " +
                "        THEN 'NUEVO' " +
                "        ELSE 'QA' " +
                "    END AS ESTADO_GENERAL " +
                "FROM       EO_RMS.TDC_BIN       B " +
                "LEFT JOIN  EO_RMS.TDC_BANDERA   BF ON B.BANDERA_ID = BF.ID " +
                "LEFT JOIN  EO_RMS.TDC_BANK      BK ON B.BANK_ID    = BK.ID " +
                "LEFT JOIN  EO_RMS.TDC_CARD_TYPE CT ON B.TYPE_ID    = CT.ID " +
                "LEFT JOIN  EO_RMS.TDC_BANK_CODE BC ON B.BANK_CODE  = BC.BANK_CODE " +
                "ORDER BY   ESTADO_GENERAL DESC, B.BIN";

            SQLUtil dbFCDASPFR = conectarConReintentos();
            SQLResult resultado = dbFCDASPFR.executeQuery(query);
            testCase.addQueryEvidenceCurrentStep(resultado);

            List<String> binsQA    = new ArrayList<>();
            List<String> binsNuevo = new ArrayList<>();

            for (int i = 0; i < resultado.getRowCount(); i++) {
                
            	String bin    = resultado.getData(i, "BIN").trim();
                String estado = resultado.getData(i, "ESTADO_GENERAL").trim();
                
                if ("QA".equals(estado)) 
                	binsQA.add(bin);
                else 
                	binsNuevo.add(bin);
            }

            // BINs del script de inserción que aparecen con estado QA (fechas incorrectas)
            List<String> nuevosConProblema = BINS_NUEVOS_INSERTADOS.stream().filter(bin -> binsQA.contains(bin)).collect(Collectors.toList());

            // BINs marcados como NUEVO pero que no están en la lista de insertados
            List<String> inesperados = binsNuevo.stream().filter(bin -> !BINS_NUEVOS_INSERTADOS.contains(bin)).collect(Collectors.toList());

            testCase.addTextEvidenceCurrentStep(
                "BINs ESTADO_GENERAL='QA': " + binsQA.size() + "\n" +
                "BINs ESTADO_GENERAL='NUEVO': " + binsNuevo.size() + "\n" +
                "Nuevos con fecha incorrecta: " + nuevosConProblema.size() + "\n" +
                "BINs NUEVO no esperados    : " + inesperados.size()
            );

            if (!nuevosConProblema.isEmpty())
                testCase.addTextEvidenceCurrentStep("BINs insertados con ESTADO='QA' (fechas no son de hoy): " + nuevosConProblema);

            if (!inesperados.isEmpty())
                testCase.addTextEvidenceCurrentStep("BINs marcados NUEVO no presentes en lista de insertados: " + inesperados);

            Map<String, List<String>> diagnostico = new HashMap<>();
            diagnostico.put("binsQA", binsQA);
            diagnostico.put("binsNuevo", binsNuevo);
            diagnostico.put("nuevosConProblema", nuevosConProblema);
            diagnostico.put("inesperados", inesperados);
            
            return diagnostico;

        } catch (Exception e) {
            throw new Exception("Error en diagnosticarEstadoBines: " + e.getMessage());
        }
    }
    
	/**
	 * @Description Realiza validacion de datos validos o completos a los bines completos
	 * @Autor Gerardo Lopez
	 * @return resultado validos y incompletos
	 * @throws Exception
	 * @date 28/Mayo/2026
	 */
    public Map<String, List<String>> checkDataCorrectBins() throws Exception {
        try {
            String queryCheckData =
                "SELECT " +
                "    b.BIN, " +
                "    b.BANDERA_ID, " +
                "    CASE WHEN bf.ID IS NOT NULL THEN 'OK' ELSE 'SIN REGISTRO' END AS ESTADO_BANDERA, " +
                "    b.BANK_ID, " +
                "    CASE WHEN bk.ID IS NOT NULL THEN 'OK' ELSE 'SIN REGISTRO' END AS ESTADO_BANK, " +
                "    b.TYPE_ID, " +
                "    CASE WHEN ct.ID IS NOT NULL THEN 'OK' ELSE 'SIN REGISTRO' END AS ESTADO_CARD_TYPE, " +
                "    b.BANK_CODE, " +
                "    CASE WHEN bc.BANK_CODE IS NOT NULL THEN 'OK' ELSE 'SIN REGISTRO' END AS ESTADO_BANK_CODE, " +
                "    CASE " +
                "        WHEN bf.ID IS NULL OR bk.ID IS NULL OR ct.ID IS NULL OR bc.BANK_CODE IS NULL " +
                "        THEN 'INCOMPLETO' " +
                "        ELSE 'VALIDO' " +
                "    END AS ESTADO_GENERAL " +
                "FROM       EO_RMS.TDC_BIN       b " +
                "LEFT JOIN  EO_RMS.TDC_BANDERA   bf ON b.BANDERA_ID = bf.ID " +
                "LEFT JOIN  EO_RMS.TDC_BANK      bk ON b.BANK_ID    = bk.ID " +
                "LEFT JOIN  EO_RMS.TDC_CARD_TYPE ct ON b.TYPE_ID    = ct.ID " +
                "LEFT JOIN  EO_RMS.TDC_BANK_CODE bc ON b.BANK_CODE  = bc.BANK_CODE " +
                "ORDER BY ESTADO_GENERAL DESC, b.BIN";

            SQLUtil dbFCDASPFR = conectarConReintentos();
            SQLResult resCheck = dbFCDASPFR.executeQuery(queryCheckData);
            testCase.addQueryEvidenceCurrentStep(resCheck);

            List<String> binsValidos     = new ArrayList<>();
            List<String> binsIncompletos = new ArrayList<>();

            // Detalle de qué campo falla por si algun bin esta incompleto
            StringBuilder detalleIncompletos = new StringBuilder();

            for (int i = 0; i < resCheck.getRowCount(); i++) {
                String bin = resCheck.getData(i, "BIN").trim();
                String estadoGeneral = resCheck.getData(i, "ESTADO_GENERAL").trim();

                if ("VALIDO".equals(estadoGeneral)) {
                    binsValidos.add(bin);
                } else {
                    binsIncompletos.add(bin);

                    // detalle de que campos fallan
                    String estadoBandera = resCheck.getData(i, "ESTADO_BANDERA").trim();
                    String estadoBank = resCheck.getData(i, "ESTADO_BANK").trim();
                    String estadoCardType = resCheck.getData(i, "ESTADO_CARD_TYPE").trim();
                    String estadoBankCode = resCheck.getData(i, "ESTADO_BANK_CODE").trim();

                    detalleIncompletos.append("BIN ").append(bin).append(" - ")
                        .append("BANDERA: ").append(estadoBandera).append(" | ")
                        .append("BANK: ").append(estadoBank).append(" | ")
                        .append("CARD_TYPE: ").append(estadoCardType).append(" | ")
                        .append("BANK_CODE: ").append(estadoBankCode)
                        .append("\n");
                }
            }

            testCase.addTextEvidenceCurrentStep(
                "Total BINs analizados : " + resCheck.getRowCount() + "\n" +
                "BINs VÁLIDOS : " + binsValidos.size()     + "\n" +
                "BINs INCOMPLETOS : " + binsIncompletos.size()
            );

            if (!binsIncompletos.isEmpty()) {
                testCase.addTextEvidenceCurrentStep("Detalle de BINs INCOMPLETOS:\n" + detalleIncompletos);
            } else {
                testCase.addTextEvidenceCurrentStep("Todos los BINs tienen sus referencias correctamente registradas.");
            }

            Map<String, List<String>> resultado = new HashMap<>();
            resultado.put("validos", binsValidos);
            resultado.put("incompletos", binsIncompletos);

            return resultado;

        } catch (Exception e) {
            throw new Exception("Error en checkDataCorrectBins: " + e.getMessage());
        }
    }
    
	/**
	 * @Description Realiza validacion de que ningun bin haya quedado incompleto
	 * @Autor Gerardo Lopez
	 * @return
	 * @throws Exception
	 * @date 28/Mayo/2026
	 */
    public List<String> checkIncompleteDataBins() throws Exception {
        try {
            String queryIncompleteData =
                "SELECT " +
                "    b.BIN, " +
                "    b.BANDERA_ID, " +
                "    CASE WHEN bf.ID IS NULL THEN 'FALTA EN TDC_BANDERA'  END AS ERR_BANDERA, " +
                "    b.BANK_ID, " +
                "    CASE WHEN bk.ID IS NULL THEN 'FALTA EN TDC_BANK'     END AS ERR_BANK, " +
                "    b.TYPE_ID, " +
                "    CASE WHEN ct.ID IS NULL THEN 'FALTA EN TDC_CARD_TYPE' END AS ERR_CARD_TYPE, " +
                "    b.BANK_CODE, " +
                "    CASE WHEN bc.BANK_CODE IS NULL THEN 'FALTA EN TDC_BANK_CODE' END AS ERR_BANK_CODE " +
                "FROM       EO_RMS.TDC_BIN b " +
                "LEFT JOIN  EO_RMS.TDC_BANDERA bf ON b.BANDERA_ID = bf.ID " +
                "LEFT JOIN  EO_RMS.TDC_BANK bk ON b.BANK_ID    = bk.ID " +
                "LEFT JOIN  EO_RMS.TDC_CARD_TYPE ct ON b.TYPE_ID    = ct.ID " +
                "LEFT JOIN  EO_RMS.TDC_BANK_CODE bc ON b.BANK_CODE  = bc.BANK_CODE " +
                "WHERE  bf.ID IS NULL " +
                "    OR bk.ID IS NULL " +
                "    OR ct.ID IS NULL " +
                "    OR bc.BANK_CODE IS NULL " +
                "ORDER BY b.BIN";

            SQLUtil dbFCDASPFR = conectarConReintentos();
            SQLResult resIncompleteData = dbFCDASPFR.executeQuery(queryIncompleteData);
            testCase.addQueryEvidenceCurrentStep(resIncompleteData);

            List<String> binsIncompletos = new ArrayList<>();
            StringBuilder detalle = new StringBuilder();

            for (int i = 0; i < resIncompleteData.getRowCount(); i++) {
                String bin = resIncompleteData.getData(i, "BIN").trim();
                String errBandera = resIncompleteData.getData(i, "ERR_BANDERA");
                String errBank = resIncompleteData.getData(i, "ERR_BANK");
                String errCardType = resIncompleteData.getData(i, "ERR_CARD_TYPE");
                String errBankCode = resIncompleteData.getData(i, "ERR_BANK_CODE");

                binsIncompletos.add(bin);

                detalle.append("BIN ").append(bin).append(" - ");
                if (errBandera != null && !errBandera.isEmpty()) detalle.append(errBandera).append(" | ");
                if (errBank != null && !errBank.isEmpty()) detalle.append(errBank).append(" | ");
                if (errCardType != null && !errCardType.isEmpty()) detalle.append(errCardType).append(" | ");
                if (errBankCode != null && !errBankCode.isEmpty()) detalle.append(errBankCode);
                detalle.append("\n");
            }

            if (binsIncompletos.isEmpty()) {
                testCase.addTextEvidenceCurrentStep("Resultado esperado obtenido: no se encontraron BINs con datos incompletos.");
            } else {
                testCase.addTextEvidenceCurrentStep("BINs con datos incompletos encontrados: " + binsIncompletos.size() + "\n\n" + "Detalle:\n" + detalle);
            }

            return binsIncompletos;

        } catch (Exception e) {
            throw new Exception("Error en checkIncompleteDataBins: " + e.getMessage());
        }
    }
    
	/**
	 * @Description Valida el conteo de registros después de la inserción en TDC_BIN
	 * @Autor Gerardo Lopez
	 * @return TOTAL_DESPUES, FILAS_NUEVAS, RESULTADO_CONTEO
	 * @throws Exception, SQLException
	 * @date 28/Mayo/2026
	 */
    public Map<String, String> binInsertTDC(String totalAntes, String filasInsertadas) throws SQLException,Exception {
    	try {
    	
        String queryConteo =
                "SELECT " +
                "    COUNT(1) AS TOTAL_REGISTROS_DESPUES, " +
                "    COUNT(1) - " + totalAntes + " AS FILAS_NUEVAS, " +
                "    CASE " +
                "        WHEN COUNT(1) - " + totalAntes + " = " + filasInsertadas +
                "        THEN 'OK — Conteo correcto' " +
                "        ELSE 'ALERTA — Se encontraron diferencias' " +
                "    END AS RESULTADO_CONTEO " +
                "FROM TPEUSER.TDC_BIN";
        
        SQLUtil dbFCTDCQA_QRO = connectFCTDCQA_QRO();
        
    	SQLResult conteoResult = dbFCTDCQA_QRO.executeQuery(queryConteo);
        testCase.addQueryEvidenceCurrentStep(conteoResult);

        String totalDespues = conteoResult.getData(0, "TOTAL_REGISTROS_DESPUES");
        String filasNuevas = conteoResult.getData(0, "FILAS_NUEVAS");
        String resultConteo = conteoResult.getData(0, "RESULTADO_CONTEO");

        testCase.addTextEvidenceCurrentStep(
            "Total registros después : " + totalDespues  + "\n" +
            "Filas nuevas detectadas : " + filasNuevas   + "\n" +
            "Resultado : " + resultConteo
        );
        
        Map<String, String> resultados = new HashMap<>();
        resultados.put("TOTAL_DESPUES", totalDespues);
        resultados.put("FILAS_NUEVAS", filasNuevas);
        resultados.put("RESULTADO_CONTEO", resultConteo);
        
        return resultados;
        
    	}
    	catch (Exception e) {
	    	throw new Exception("Error al validar conteo de registro en el insert a TDC: " + e.getMessage());
    		
    	}
    }
    
    /**
     * @Description Lista los BINs insertados desde una fecha de corte, para dejar 
     * evidencia de cuales fueron procesados sin comparar contra ninguna lista fija.
     * @Autor Gerardo Lopez
     * @param fechaCorte Fecha de corte en formato "DD/MM/YYYY"
     * @return binsNuevos
     * @throws Exception
     * @date 13/Julio/2026
     */
    public List<String> getBinsInsertadosDesde(String fechaCorte) throws Exception {
        try {
            String query = "SELECT BIN FROM EO_RMS.TDC_BIN "
                    + "WHERE TRUNC(CREATION_DATE) >= TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY') "
                    + "ORDER BY BIN";

            SQLUtil db = conectarConReintentos();
            SQLResult res = db.executeQuery(query);
            testCase.addQueryEvidenceCurrentStep(res);

            List<String> binsNuevos = new ArrayList<>();
            for (int i = 0; i < res.getRowCount(); i++) {
                binsNuevos.add(res.getData(i, "BIN").trim());
            }

            testCase.addTextEvidenceCurrentStep("BINs insertados desde " + fechaCorte + ": " + binsNuevos.size());

            db.close();
            return binsNuevos;
        } catch (Exception e) {
            throw new Exception("Error en getBinsInsertadosDesde: " + e.getMessage());
        }
    }

    /**
     * @Description Valida que los BINs insertados desde una fecha de corte tengan 
     * todas sus referencias completas (bandera, banco, tipo de tarjeta, bank_code).
     * @Autor Gerardo Lopez
     * @param fechaCorte Fecha de corte en formato "DD/MM/YYYY"
     * @return binsIncompletos (vacio si todo esta correcto)
     * @throws Exception
     * @date 13/Julio/2026
     */
    public List<String> checkIncompleteDataBinsDesde(String fechaCorte) throws Exception {
        try {
            String query =
                "SELECT b.BIN, " +
                "    CASE WHEN bf.ID IS NULL THEN 'FALTA EN TDC_BANDERA' END AS ERR_BANDERA, " +
                "    CASE WHEN bk.ID IS NULL THEN 'FALTA EN TDC_BANK' END AS ERR_BANK, " +
                "    CASE WHEN ct.ID IS NULL THEN 'FALTA EN TDC_CARD_TYPE' END AS ERR_CARD_TYPE, " +
                "    CASE WHEN bc.BANK_CODE IS NULL THEN 'FALTA EN TDC_BANK_CODE' END AS ERR_BANK_CODE " +
                "FROM EO_RMS.TDC_BIN b " +
                "LEFT JOIN EO_RMS.TDC_BANDERA bf ON b.BANDERA_ID = bf.ID " +
                "LEFT JOIN EO_RMS.TDC_BANK bk ON b.BANK_ID = bk.ID " +
                "LEFT JOIN EO_RMS.TDC_CARD_TYPE ct ON b.TYPE_ID = ct.ID " +
                "LEFT JOIN EO_RMS.TDC_BANK_CODE bc ON b.BANK_CODE = bc.BANK_CODE " +
                "WHERE TRUNC(b.CREATION_DATE) >= TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY') " +
                "  AND (bf.ID IS NULL OR bk.ID IS NULL OR ct.ID IS NULL OR bc.BANK_CODE IS NULL) " +
                "ORDER BY b.BIN";

            SQLUtil db = conectarConReintentos();
            SQLResult res = db.executeQuery(query);
            testCase.addQueryEvidenceCurrentStep(res);

            List<String> binsIncompletos = new ArrayList<>();
            StringBuilder detalle = new StringBuilder();

            for (int i = 0; i < res.getRowCount(); i++) {
                String bin = res.getData(i, "BIN").trim();
                binsIncompletos.add(bin);

                String errBandera  = res.getData(i, "ERR_BANDERA");
                String errBank     = res.getData(i, "ERR_BANK");
                String errCardType = res.getData(i, "ERR_CARD_TYPE");
                String errBankCode = res.getData(i, "ERR_BANK_CODE");

                detalle.append("BIN ").append(bin).append(" - ");
                if (errBandera  != null && !errBandera.isEmpty())  detalle.append(errBandera).append(" | ");
                if (errBank     != null && !errBank.isEmpty())     detalle.append(errBank).append(" | ");
                if (errCardType != null && !errCardType.isEmpty()) detalle.append(errCardType).append(" | ");
                if (errBankCode != null && !errBankCode.isEmpty()) detalle.append(errBankCode);
                detalle.append("\n");
            }

            testCase.addTextEvidenceCurrentStep(
                binsIncompletos.isEmpty()
                    ? "Todos los BINs insertados desde " + fechaCorte + " tienen sus referencias completas."
                    : "BINs incompletos desde " + fechaCorte + ": " + binsIncompletos.size() + "\n\n" + detalle
            );

            db.close();
            return binsIncompletos;
        } catch (Exception e) {
            throw new Exception("Error en checkIncompleteDataBinsDesde: " + e.getMessage());
        }
    }
    
    /**
     * @Description Valida el conteo de registros en TDC_BIN comparando lo que existia 
     * antes de una fecha de corte fija, contra el total actual. Util mientras no se 
     * tiene un criterio dinamico por dia y se necesita validar contra un corte especifico.
     * @Autor Gerardo Lopez
     * @param fechaCorte Fecha de corte en formato "DD/MM/YYYY" (ej. "01/07/2026")
     * @return TOTAL_ANTES, TOTAL_DESPUES, FILAS_NUEVAS, RESULTADO_CONTEO
     * @throws Exception, SQLException
     * @date 13/Julio/2026
     */
    public Map<String, String> countRegisterInsert(String fechaCorte) throws SQLException, Exception {
        try {
            String queryConteo =
                    "SELECT " +
                    "    (SELECT COUNT(*) FROM EO_RMS.TDC_BIN WHERE TRUNC(CREATION_DATE) < TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY')) AS TOTAL_ANTES, " +
                    "    (SELECT COUNT(*) FROM EO_RMS.TDC_BIN) AS TOTAL_DESPUES, " +
                    "    (SELECT COUNT(*) FROM EO_RMS.TDC_BIN) - " +
                    "    (SELECT COUNT(*) FROM EO_RMS.TDC_BIN WHERE TRUNC(CREATION_DATE) < TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY')) AS FILAS_NUEVAS " +
                    "FROM DUAL";

            SQLUtil dbFCDASPFR = conectarConReintentos();

            SQLResult conteoResult = dbFCDASPFR.executeQuery(queryConteo);
            testCase.addQueryEvidenceCurrentStep(conteoResult);

            String totalAntes   = conteoResult.getData(0, "TOTAL_ANTES");
            String totalDespues = conteoResult.getData(0, "TOTAL_DESPUES");
            String filasNuevas  = conteoResult.getData(0, "FILAS_NUEVAS");

            boolean seInsertoAlgo = Integer.parseInt(filasNuevas) > 0;
            String resultConteo = seInsertoAlgo
                    ? "OK — Conteo correcto"
                    : "ALERTA — No se detectaron BINs nuevos desde la fecha de corte";

            testCase.addTextEvidenceCurrentStep(
                "Fecha de corte             : " + fechaCorte    + "\n" +
                "Total antes de corte       : " + totalAntes    + "\n" +
                "Total actual               : " + totalDespues  + "\n" +
                "Filas agregadas desde corte: " + filasNuevas   + "\n" +
                "Resultado                  : " + resultConteo
            );

            Map<String, String> resultados = new HashMap<>();
            resultados.put("TOTAL_ANTES",      totalAntes);
            resultados.put("TOTAL_DESPUES",    totalDespues);
            resultados.put("FILAS_NUEVAS",     filasNuevas);
            resultados.put("RESULTADO_CONTEO", resultConteo);

            dbFCDASPFR.close();
            return resultados;

        } catch (Exception e) {
            throw new Exception("Error al validar conteo de registro en el insert: " + e.getMessage());
        }
    }
	
    /**
     * @Description Valida que los BINs preexistentes (creados antes de la fecha de corte) 
     * sigan registrados en TDC_BIN. Solo trae el conteo, sin listar filas individuales.
     * @Autor Gerardo Lopez
     * @param fechaCorte Fecha de corte en formato "DD/MM/YYYY"
     * @return cantidadBinsQA Cantidad de BINs preexistentes encontrados
     * @throws Exception
     * @date 13/Julio/2026
     */
    public int validarBinesQAIntactos(String fechaCorte) throws Exception {
        try {
            String queryConteo = "SELECT COUNT(*) AS TOTAL FROM EO_RMS.TDC_BIN "
                    + "WHERE TRUNC(CREATION_DATE) < TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY')";

            SQLUtil db = conectarConReintentos();
            SQLResult resConteo = db.executeQuery(queryConteo);
            int cantidadBinsQA = Integer.parseInt(resConteo.getData(0, "TOTAL"));

            testCase.addTextEvidenceCurrentStep(
                "Total de BINs de QA (preexistentes a " + fechaCorte + "): " + cantidadBinsQA
            );

            db.close();
            return cantidadBinsQA;
        } catch (Exception e) {
            throw new Exception("Error en validarBinesQAIntactos: " + e.getMessage());
        }
    }
    
    /**
     * @Description Valida la integridad de TODOS los BINs en TDC_BIN (referencias completas 
     * en bandera, banco, tipo y bank_code). Solo trae conteos, sin listar filas individuales,
     * para evitar reportes PDF pesados con miles de filas.
     * @Autor Gerardo Lopez
     * @return cantidadIncompletos Cantidad total de BINs con datos incompletos
     * @throws Exception
     * @date 13/Julio/2026
     */
    public int checkBinsIntegrity() throws Exception {
        try {
            String queryTotales = "SELECT COUNT(*) AS TOTAL FROM EO_RMS.TDC_BIN";

            String queryIncompletos =
                "SELECT COUNT(*) AS TOTAL FROM ( " +
                "    SELECT b.BIN " +
                "    FROM EO_RMS.TDC_BIN b " +
                "    LEFT JOIN EO_RMS.TDC_BANDERA bf ON b.BANDERA_ID = bf.ID " +
                "    LEFT JOIN EO_RMS.TDC_BANK bk ON b.BANK_ID = bk.ID " +
                "    LEFT JOIN EO_RMS.TDC_CARD_TYPE ct ON b.TYPE_ID = ct.ID " +
                "    LEFT JOIN EO_RMS.TDC_BANK_CODE bc ON b.BANK_CODE = bc.BANK_CODE " +
                "    WHERE bf.ID IS NULL OR bk.ID IS NULL OR ct.ID IS NULL OR bc.BANK_CODE IS NULL " +
                ")";

            SQLUtil db = conectarConReintentos();

            SQLResult resTotales = db.executeQuery(queryTotales);
            int totalBins = Integer.parseInt(resTotales.getData(0, "TOTAL"));

            SQLResult resIncompletos = db.executeQuery(queryIncompletos);
            int cantidadIncompletos = Integer.parseInt(resIncompletos.getData(0, "TOTAL"));
            int cantidadValidos = totalBins - cantidadIncompletos;

            testCase.addTextEvidenceCurrentStep(
                "Total BINs analizados : " + totalBins          + "\n" +
                "BINs VÁLIDOS          : " + cantidadValidos     + "\n" +
                "BINs INCOMPLETOS      : " + cantidadIncompletos
            );

            db.close();
            return cantidadIncompletos;
        } catch (Exception e) {
            throw new Exception("Error en checkBinsIntegrity: " + e.getMessage());
        }
    }
    
    /**
	 * @Description Valida el conteo de registros en TPEUSER.TDC_BIN (BD FCTDCQA_QRO) 
	 * post ejecucion del job TPE_SYNC, comparando contra una fecha de corte. Solo 
	 * trae conteos (COUNT), nunca filas individuales.
	 * @Autor Gerardo Lopez
	 * @param fechaCorte Fecha de corte en formato "DD/MM/YYYY"
	 * @return TOTAL_ANTES, TOTAL_DESPUES, FILAS_NUEVAS, RESULTADO_CONTEO
	 * @throws Exception, SQLException
	 * @date 13/Julio/2026
	 */
    public Map<String, String> binInsertTDC(String fechaCorte) throws SQLException, Exception {
        try {
            String queryConteo =
                    "SELECT " +
                    "    (SELECT COUNT(*) FROM TPEUSER.TDC_BIN WHERE TRUNC(CREATION_DATE) < TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY')) AS TOTAL_ANTES, " +
                    "    (SELECT COUNT(*) FROM TPEUSER.TDC_BIN) AS TOTAL_DESPUES, " +
                    "    (SELECT COUNT(*) FROM TPEUSER.TDC_BIN) - " +
                    "    (SELECT COUNT(*) FROM TPEUSER.TDC_BIN WHERE TRUNC(CREATION_DATE) < TO_DATE('" + fechaCorte + "', 'DD/MM/YYYY')) AS FILAS_NUEVAS " +
                    "FROM DUAL";

            SQLUtil dbFCTDCQA_QRO = connectFCTDCQA_QRO();

            SQLResult conteoResult = dbFCTDCQA_QRO.executeQuery(queryConteo);
            testCase.addQueryEvidenceCurrentStep(conteoResult);

            String totalAntes   = conteoResult.getData(0, "TOTAL_ANTES");
            String totalDespues = conteoResult.getData(0, "TOTAL_DESPUES");
            String filasNuevas  = conteoResult.getData(0, "FILAS_NUEVAS");

            boolean seInsertoAlgo = Integer.parseInt(filasNuevas) > 0;
            String resultConteo = seInsertoAlgo
                    ? "OK — Conteo correcto"
                    : "ALERTA — No se detectaron BINs nuevos desde la fecha de corte";

            testCase.addTextEvidenceCurrentStep(
                "Fecha de corte              : " + fechaCorte    + "\n" +
                "Total antes de corte        : " + totalAntes    + "\n" +
                "Total actual                : " + totalDespues  + "\n" +
                "Filas agregadas desde corte : " + filasNuevas   + "\n" +
                "Resultado                   : " + resultConteo
            );

            Map<String, String> resultados = new HashMap<>();
            resultados.put("TOTAL_ANTES",      totalAntes);
            resultados.put("TOTAL_DESPUES",    totalDespues);
            resultados.put("FILAS_NUEVAS",     filasNuevas);
            resultados.put("RESULTADO_CONTEO", resultConteo);

            dbFCTDCQA_QRO.close();
            return resultados;

        } catch (Exception e) {
            throw new Exception("Error al validar conteo de registro en el insert a TDC: " + e.getMessage());
        }
    }

} //Aqui termina el OM