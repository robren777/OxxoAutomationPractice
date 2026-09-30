package EBS.administracionIdentidades;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_050_ValidarAltaUsrModRhNoRespAsignadasModComprasNoSeLlenaSeccionAssignment extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_050_ValidarAltaUsrModRhNoRespAsignadasModComprasNoSeLlenaSeccionAssignment_test(HashMap<String, String> data) throws Exception {
        /*
         * VARIABLES
         */
        String ebsUrl = GlobalVariables.getAvanteServer;
        String userName = GlobalVariables.getJhernandezHexEbsUsr;
        String password = GlobalVariables.getJhernandezHexEbsPwd;

        /*
         * UTILERIAS
         */
        SeleniumUtil u = new SeleniumUtil(new IETest(), true);

        /*
         * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084165
         */

        // PASO 1
        addStep("Ingresar al ambiente de EBS.");
        u.get(ebsUrl);
    }

    @Override
    public String setTestFullName() {
        return null;
    }

    @Override
    public String setTestDescription() {
        return "Todos los campos son obligatorios y ser�n los siguientes:\n" +
                "1.Nombre \n" +
                "2.Apellido Paterno\n" +
                "3.Apellido Materno\n" +
                "4.G�nero*\n" +
                "5.Numero de empleado (# empleado SAP)\n" +
                "6.CURP*\n" +
                "7.Fecha de nacimiento* \n" +
                "Detalles de Oficina\n" +
                "8.Correo electr�nico* (V�lidar la sincronizaci�n de la informaci�n en la parte de dise�o)\n" +
                "Los eventos de alta de IDM no se disparar�n hasta que se cree la cuenta de correo.\n" +
                "9.RFC*\n" +
                "10.CURP\n" +
                "11.D�gito verificador (Son los �ltimos 2 d�gitos del CURP, ser� calculado en base a la CURP, en caso de que no venga se coloca default 00).\n" +
                "12.Fecha efectiva de movimiento (fecha de alta en m�dulo de RH)\n" +
                "Formato fecha '2021-12-06 16:51:50' (YYYY-MM-DD HH24:MI:SS)\n" +
                "13.Tipo de Movimiento = Altas. \n" +
                "14.Clave de Movimiento = AL. \n" +
                "15.Acci�n. Crear empleo (create employement)\n" +
                "ASSIGNMENT\n" +
                "16.Organizaci�n. Siempre ser� FEMCO_MEXICO_BG\n" +
                "17.Job. Siempre ser� comprador \n" +
                "18.Posici�n (ligada a la responsabilidad en Oracle). Compras OS plaza (PO) o Compras Mtto (EAM). \n" +
                "19.Status/Estado. En el alta es Asignaci�n Activa\n" +
                "20.Locaci�n (ligada al puesto).\n" +
                "\uF0B7Ubicaci�n (ACA)- Pdte. Se realizar� una prueba (Daniel Lujan)\n" +
                "\uF0B7Plaza. (para obtener el location y filtrar puestos)\n" +
                "PO10AGC plaza 10AGC\n" +
                "EAM10AGC\n";
    }

    @Override
    public String setTestDesigner() {
        return "JesusH@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Deber�n de agregarse los campos RFC, CURP, Fecha de Nacimiento, G�nero en el IDOC de SAP.";
    }

    @Override
    public void beforeTest() {

    }
}
