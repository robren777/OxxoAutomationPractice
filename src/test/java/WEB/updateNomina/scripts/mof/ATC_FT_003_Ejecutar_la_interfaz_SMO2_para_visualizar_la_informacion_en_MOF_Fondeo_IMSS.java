package WEB.updateNomina.scripts.mof;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.monitorObligacionesFiscales.om.Contabilidad;
import WEB.monitorObligacionesFiscales.om.MenuPrincipal_MonitorObligaciones;
import WEB.updateNomina.om.Login;
import WEB.updateNomina.om.MofUpdNom;
import WEB.updateNomina.om.UpdateNomina;
import WEB.util.AuxMethods;
import WEB.util.GlobalVariables;
import WEB.util.MOF.MOF;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_Ejecutar_la_interfaz_SMO2_para_visualizar_la_informacion_en_MOF_Fondeo_IMSS extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_Ejecutar_la_interfaz_SMO2_para_visualizar_la_informacion_en_MOF_Fondeo_IMSS_test(HashMap<String, String> data) throws Exception {

        String user = data.get("user");
        String pass = data.get("password");
        //MCR MEX atc 002
        String controlMUser = data.get("controlMUser");
        String controlMPwd = PasswordUtil.decryptPassword(data.get("controlMPwd"));
        JSONObject obj = new JSONObject(data.get("job"));

        String dbMOFHost = data.get("dbMOFHost");
        String dbMOFUser = data.get("dbMOFUser");
        String dbMOFPassword = data.get("dbMOFPassword");

        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

        //AccessControl inicio = new AccessControl(u, testCase);
        Login login = new Login(u, testCase);
        AuxMethods aux = new AuxMethods(u, testCase);

        //Clases con metodos creados para MOF antes del proyecto Update Nomina
        MOF mof = new MOF(u, testCase, aux);
        MenuPrincipal_MonitorObligaciones mofMenu = new MenuPrincipal_MonitorObligaciones(u, testCase);
        Contabilidad mofCont = new Contabilidad(u, testCase);

        //Clases con metodos creados para MOF para el proyecto update nomina
        MofUpdNom mofUpdNom = new MofUpdNom(u, testCase);
        UpdateNomina updNom = new UpdateNomina(u, testCase);

        //Control M
        Control_mInicio controlMInicio = new Control_mInicio(u, controlMUser, controlMPwd);
        ControlM controlMJob = new ControlM(u, testCase, obj);




      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar a la liga 'https://fcowlq08.femcom.net/AccessControl/pages/login_form.jsf' ");
              
              System.out.println(user);
              System.out.println(pass);
              login.loginAccessControlMOF(user, pass);

              testCase.addTextEvidenceCurrentStep("Se accedio correctamente al Portal de Access Control");
      		
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar con usuario, contrasena valido y abrir la aplicacion 'Monitor de Obligaciones Fiscales' ");
              
              mofUpdNom.IngresarAPortalMof();
      		

      //-------------------------------------------------------------------------------------------------------------------------------
              //addStep("Del menu 'Catalogos' seleccionar la opcion 'Despachos' ");
              addStep("Ingresar al  Menu Mantenimiento -> Periodos");
              //mof.SelectMOFCatalogos();
              //mof.OpCatalogosDespachos();
              
              mofUpdNom.SelectMOFMantenimientoPeriodos();
              

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Activar el periodo 3 que es junio");
              
              mofUpdNom.SeleccionarPeriodo("2023");
              mofUpdNom.SeleccionarTamanoListaDeRegistros();
              mofUpdNom.ActivarPeriodo("Julio");
              

      //-------------------------------------------------------------------------------------------------------------------------------
              //addStep("Del menu 'Catalogos' seleccionar la opcion 'Deducciones' ");
              
              addStep("Ingresar a la contabilidad > deducciones -> nomina y seleccionar l�der activo");
              mofMenu.clickContabilidad();
              mofMenu.clickContabilidadPrincipal();
              mofCont.seleccionarContabilidadLiderActivo("ALVARO FLORES ZAVALA");
              mofUpdNom.clicCPDeduccionesNomina();
      		
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Seleccionar semana 3");
              
              mofUpdNom.SeleccionarCPDNSemana("3"); //Contabilidad>Principal>Deduccion>Nomina = CPDN
              

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar que los checkbox de los empleados que envio Xpertal esten desabilitados");
              
              testCase.addScreenShotCurrentStep("Sueldo Empleados");
              

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar a controlM con usuario y contrase�a valido: http://10.184.56.194:18080/ControlM/");
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Se ejecuta la interfaz runSM02 Carpeta: TQA_TEST_WM99_INT");
              //updNom.logInControlMyEjecutarJob(controlMInicio, controlMJob);

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Realizar la conexi�n a la BD FCWMLQA." +
                      "Usuario: QAVIEW" +
                      "host:oxzvmwmiracq01.femcom.net" +
                      "puerto:1521" +
                      "servicio: FCWMLQA.FEMCOM.NET");
              
              SQLUtil dbWMLog = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA,
                      GlobalVariables.DB_PASSWORD_FCWMLQA);
              System.out.println("Conectado a FCWMLQA");
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar que la interfaz termino en S" +
                      "SELECT*FROM WMLOG.WM_LOG_RUN" +
                      "WHERE INTERFACE LIKE '&SO02'" +
                      "ORDER BY START_DT DESC;");
              
              String queryValidarStatusJob = "SELECT*FROM WMLOG.WM_LOG_RUN WHERE INTERFACE LIKE 'SO02' AND STATUS ='S'ORDER BY START_DT DESC";
              SQLResult resultValidarStatusJob = executeQuery(dbWMLog, queryValidarStatusJob);
              testCase.addTextEvidenceCurrentStep("Query utilizado : "+queryValidarStatusJob);
              String runId = resultValidarStatusJob.getData(0, "RUN_ID");
              System.out.println("Run ID: "+runId);
              

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar que la interfaz levanto hilos:" +
                      "SELECT*FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID = AND STATUS ='S'");
              
              String queryValidarHilos = "SELECT*FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID ='"+runId+"' AND STATUS ='S'";
              SQLResult resultValidarHilos = executeQuery(dbWMLog, queryValidarHilos);
              testCase.addTextEvidenceCurrentStep("Query utilizado : "+queryValidarHilos);
              String validacionHilos = resultValidarHilos.getData(0,"THREAD_ID");
              System.out.println("Thread ID: "+validacionHilos);
              System.out.println("Deben de ser iguales ambos id's ");
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar al portal de aplicaciones http://fcowlq02.femcom.net:7032/AccessControl/pages/login_form.jsf");
              
              login.loginAccessControlMOF(user, pass);
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar a Monitor de obligaciones fiscales (segunda opci�n)");
              
              mofUpdNom.IngresarAPortalMof();
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar a la contabilidad > deducciones -> nomina y seleccionar l�der activo");
              
              mofMenu.clickContabilidadPrincipal();
              mofCont.seleccionarContabilidadLiderActivo("ALVARO FLORES ZAVALA");
              mofUpdNom.clicCPDeduccionesNomina();
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Seleccionar semana 3");
              
              mofUpdNom.SeleccionarCPDNSemana("3");
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar que los checkbox despues de la ejecucion de la interfaz SM02 esten habilitados si no estan habilitados habilitarlos manualmente");
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar las columna sueldos se le haya generado el sueldo que expertal envio desde SAP");
              
              testCase.addScreenShotCurrentStep("Sueldo Empleados");
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Ingresar a la BD MOF con usuario y contrase�a valido:" +
                      "host: OXVFCIAS00.FEMCOM.NET" +
                      "puerto: 1535" +
                      "servicio: FCPVEQA.FEMCOM.NET" +
                      "esquema: XXMOF");
              
              SQLUtil dbMOF = new SQLUtil(dbMOFHost, dbMOFUser, dbMOFPassword);
              System.out.println("Conectado dbMOF");
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar que el lider existe BD SELECT*FROM XXMOF.TBL_LIDERES WHERE RFC ='FOZA7705227K2';");
              String queryValidarLider = "SELECT*FROM XXMOF.TBL_LIDERES WHERE RFC ='FOZA7705227K2'"; //da como resultado el lider alvaro flores zavala
              
              SQLResult resultValidarLider = executeQuery(dbMOF, queryValidarLider);
              testCase.addTextEvidenceCurrentStep("Query utilizado : "+queryValidarLider);
              
      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Buscar los empleados y lider que envio expertal en la bd mof SELECT * FROM XXMOF.MOF_EMPLEADO \n" +
                      "where Created_by  in 'FOZA7705227K2'; --sacar el idempleado");
              
              String queryBuscarEmpleadosYLider = "SELECT * FROM XXMOF.MOF_EMPLEADO where Created_by  in 'FOZA7705227K2'";
              SQLResult resultBuscarEmpleadosYLider = executeQuery(dbMOF, queryBuscarEmpleadosYLider);
              

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar cual es el idasignacion que tienen los empleados del lider comisonista: SELECT * FROM XXMOF.MOF_ASIGNACION" +
                      "    WHERE IDEMPLEADO IN (1115418, 1279001) AND created_by  in 'FOZA7705227K2'");
              
              String queryValidarIdAsignacion = "SELECT * FROM XXMOF.MOF_ASIGNACION WHERE IDEMPLEADO IN (1115418, 1279001) AND created_by  in 'FOZA7705227K2'";
              SQLResult resultValidarIdAsignacion = executeQuery(dbMOF, queryValidarIdAsignacion);
              

      //-------------------------------------------------------------------------------------------------------------------------------
              addStep("Validar el envio de nomina hacia MOF" +
                      "SELECT*FROM XXMOF.MOF_NOMINA_EMPLEADO WHERE IDASIGNACION IN ('1099798','1262150') AND IDPERIODO =222 AND IDCONCEPTO='1';\"\n");
              
              String queryValidarValidarEnvio = "SELECT*FROM XXMOF.MOF_NOMINA_EMPLEADO WHERE IDASIGNACION IN ('1099798','1262150') AND IDPERIODO =222 AND IDCONCEPTO='1'";
              SQLResult resultValidarEnvio = executeQuery(dbMOF, queryValidarValidarEnvio);
              
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setTestDescription() {
        return "Se validara que se muestre informacion correcta en la pantalla de Despachos, Deducciones y Parametros";
    }

    @Override
    public String setTestDesigner() {
        return "Mario Alberto De Leon Rodriguez";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_ValidarSubmenusMenuCatalogos_test";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1.- Contar con la URL del aplicativo MOF. "
                + "2.- Contar con Usuario y Contrasena validos. ";
    }
    @AfterMethod
    public void afterMethod() {
        //u.getDriver().quit();
    }
}

