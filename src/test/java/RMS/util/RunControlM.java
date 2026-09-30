package RMS.util;

import static org.testng.Assert.assertTrue;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import RMS.util.PageObject.Monitoring;
import modelo.BaseExecution;
/**
 * Esta clase ejecuta el job en  Ctrl M y  realiza el monitoreo de
 * su ejecucion usando un thread. Esta clase es instanciada desde
 * la clase CtrlM.
 * @author 54865
 *
 */
public class RunControlM implements Runnable {

    private Monitoring dashboard;
    String actualStatus;
    Boolean release;
    private JSONArray actionsList;
    private boolean exit;   // para detener el hilo si se queda colgado

    protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);
    /**
     * Constructor RunControlM
     * @param dashboard
     * @param actualStatus
     * @actionsList
     *      */
    public RunControlM(Monitoring dashboard, String actualStatus, Boolean release, JSONArray actionsList) {
        this.dashboard = dashboard;
        this.actualStatus = actualStatus;
        this.release = release;         // modificacion para saber si el job se ejecuta con release
        this.actionsList = actionsList;
        exit = false;
        
    }

    @Override
    /**
     * Run para el thread de RunControlM; es donde realmente se le da
     * click al Job dependiendo de su status, cada 8 segundos se monitorea
     * el cambio de status en el job ejecutandose.
     */
    public void run() {
        // Se hace un click en la opcion "Actions"
    	Boolean clickOK = false;
              
        if(actionsList!=null) {    // si la lista de acciones viene en el data provider
          for (int i=0; i<actionsList.length(); i++) {
               String action =actionsList.getString(i);
               dashboard.userClicksActions();
               switch(action){
               case "Hold" :
                  clickOK = dashboard.clickHold();
                  break;
               case "Release":
            	   clickOK = dashboard.clickRelease();
            	   break;
               case "Run Now":
            	   clickOK = dashboard.clickRunNow();
            	   break;
               case "Rerun":
            	   clickOK = dashboard.clickRerun();           	   
               }
               assertTrue(clickOK);
               String resultado = "Executing";
               try {
          	   Thread.sleep(3000); // para tratar de asegurarse que el job inicia su ejecucion en ctrlm
                 while (resultado.equals("Executing") && !exit) {
                  Thread.sleep(8000);
                  resultado = dashboard.getJobStatusValue();
                  System.out.println("Job siendo monitoreado");
                 }

              } catch (InterruptedException exc) {
                 logger.error("Ejecuci�n del Job interrumpida" + exc);
                 System.out.println("Ejecuci�n del Job interrumpida" + exc);
             }
          }
        }
        else {        // cuando no usa lista de acciones (seguir soportando el manejo anteriro de jobs)
           dashboard.userClicksActions();
           if(release)     // si el job de ejecutarse con la opcion de release
             dashboard.userClicksRelease();
           else  dashboard.userClicksExecute(actualStatus);// Se hace un click en la opcion adecuada dependiendo del estado del Job
        	    
           String resultado = "Executing";
           try {
        	   Thread.sleep(3000); // para tratar de asegurarse que el job inicia su ejecucion en ctrlm
               while (resultado.equals("Executing") && !exit) {
                Thread.sleep(8000);
                resultado = dashboard.getJobStatusValue();
                System.out.println("Job siendo monitoreado");
               }

           } catch (InterruptedException exc) {
               logger.error("Ejecuci�n del Job interrumpida" + exc);
               System.out.println("Ejecuci�n del Job interrumpida" + exc);
           }
      }
      logger.info("Thread - Ejecuci�n del Job finalizada en RunCtrlM");
      System.out.println("Thread - Ejecuci�n del Job finalizada en RunCtrlM"); 
    }

    // Para detener la ejecucion del hilo
    // Si el hilo llegara a ciclarse se utiliza este metodo
    public void stopping() {
        exit = true;
        logger.warn("Forzando finalizacion... Thread");
        System.out.println("Forzando finalizacion... Thread");
    }

}
