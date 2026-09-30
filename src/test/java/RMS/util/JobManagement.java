//package RMS.util;
//
//import modelo.BaseExecution;
//import modelo.TestCase;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.sikuli.script.FindFailed;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import utils.selenium.SeleniumUtil;
///**
// * Esta clase se encarga de implementar el metodo jobRunner, donde se
// * recorre el arreglo de jobs que se lee del data provider y se crea un
// * objeto ControlM para llamar a los metodos de searchJob() y executeJob()
// * y llevar a cabo la ejecuci�n si el job es encontrado.
// * Si el job no se encuentra, se falla el caso de prueba y se detiene la 
// * ejecucion.
// * Si algun job falla en su ejecucion (status failure), se falla el caso de prueba
// * y se detiene la ejecucion.
// * @author Jesus Hernandez
// * @author JL Chacon
// */
//public class JobManagement {
//    SeleniumUtil u;
//    TestCase testCase;
//
//    JSONObject obj;
//    JSONArray jobsArray;
//
//    protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);
//
//  /**
//   * Constructor para instanciar un objeto Job Management, que permitira
//   * ejecutar un job en Ctrl M
//   * @param u 
//   * @param testCase
//   * @param jobsArray  (arreglo de objetos json, cada objeto representa un job)
//   */
//    public JobManagement(SeleniumUtil u, TestCase testCase, JSONArray jobsArray) {
//        this.u = u;
//        this.testCase = testCase;
//        this.jobsArray = jobsArray;
//
//    }
//
//    /**
//     * Metodo para ejecutar los jobs recibidos en jobsArray
//     * @return EL Resultado de la ejecucion del Job
//     * @throws FindFailed
//     * @throws InterruptedException
//     */
//    public String jobRunner() throws FindFailed, InterruptedException {
//        // "resultado" se utiliza para guardar el estado del Job
//        String resultado = "";
//        // El siguiente ciclo se utiliza para recorrer los elementos del JsonArray
//       
//        for (int i = 0; i < jobsArray.length(); i++) {
//            obj = (JSONObject) jobsArray.get(i);
//            ControlM control = new ControlM(u, testCase, obj);
//            // Busca el Job y asigna a una bandera
//            boolean flag = control.searchJob();
//            testCase.addScreenShotCurrentStep(u, control.getJob());
//            // Si "searchJob" no encuentra el Job retorna que el Job no fue encontrado
//            if (!flag) {
//                return "EL Job "+control.getJob()+ " No fue encontrado";
//            }
//
//            // Establece el estado actual del Job encontrado
//            control.setActualStatus();
//            // Los Jobs pueden ser dependientes o independientes
//            // en base a ello se selecciona si ejecutarlo
//            // o verificar en que estado se encuentra
//            if (!control.getDepend()) {
//                // Si el Job es independiente, lo ejecuta
//                resultado = control.executeJob();
//                logger.info("Job finished with status: " + control.getNewStatus());
//                System.out.println(" JobManagement says Job finished with status: " + control.getNewStatus());
//            } else {
//                // Si el Job es independiente, verifica el estado
//                resultado = control.getCurrentJobStatus();
//                int t = 0;
//                // Si ese Job estuviera corriendo "Executing"
//                // se verificara el estado, siempre y cuando no se exceda el timeOut esperado para dicho Job
//                while (resultado.equals("Executing") && t <= control.getTimeOut()*1000) {
//                    Thread.sleep(5000);
//                    t = t + 5000;
//                    resultado = control.getCurrentJobStatus();
//                }
//                logger.info("Dependent Job Status: " + resultado);
//                System.out.println("Dependent Job Status: " + resultado);
//
//            }
//            // Si el resultado obtenido es Failure se interrumpe la ejecucion de los jobs
//           
//            if (resultado.equals("Failure")) return resultado;
//          
//            Thread.sleep(4000);
//            // Despues de buscar un Job, se debe cerrar el Viewpoint esto para evitar locators repetidos
//            control.closeViewpoint();
//        }
//        // Al final de la Busqueda y Ejecucion de los Jobs proporcionados
//        // se retorna el estado del Job
//        return resultado;
//    }
//
//}
