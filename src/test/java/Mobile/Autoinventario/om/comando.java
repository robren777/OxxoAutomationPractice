package Mobile.Autoinventario.om;

import modelo.TestCase;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class comando {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	public static String ip;
	public static String user;
	public static String password;
	public static String nuevaFechaHora;
	public static int diaAcambiar;
	public static int hora;
	public static int mins;
	private TestCase testCase;
	public static String reseteaFyH;
	/*

	/* Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Comando
	 * @author Joel Rodriguez
	 * @param ip 
	 * @param user 
	 * @param password 
	 * @param testCase 
	 * @param au (AppiumUtil): objeto de la clase
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public comando( String ip, String user, String password, TestCase testCase) {
		comando.ip = ip;
		comando.user = user;
		comando.password = password;
		this.testCase = testCase;
	}

	public void cambiarDiaHora(String nuevaFechaHora, int diaAcambiar) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime originalDateTime = LocalDateTime.parse(comando.nuevaFechaHora, inputFormatter);
		LocalDateTime modifiedDateTime = originalDateTime.plusDays(comando.diaAcambiar).plusHours(comando.hora).plusMinutes(comando.mins);
		comando.nuevaFechaHora = modifiedDateTime.format(outputFormatter);
		cambioFechaRemoto(comando.nuevaFechaHora);
	}
	
	public void cambioFechaRemoto(String nuevaFechaHora) {
		try {
			String[] dateTimeParts = comando.nuevaFechaHora.split(" ");
			String datePart = dateTimeParts[0].replace("-", "/");
			String command = "psexec \\\\" + ip + " -u " + ip + "\\" + user + " -p " + password + " -s cmd /c date " + datePart ;
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			cambioHoraRemoto(comando.nuevaFechaHora);
			testCase.addTextEvidenceCurrentStep("La fecha se actualiza a " + datePart);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public void cambioHoraRemoto(String nuevaFechaHora) {
		try {
			String[] dateTimeParts = comando.nuevaFechaHora.split(" ");
			String timePart = dateTimeParts[1];
			String command = "psexec \\\\" + ip + " -u " + ip + "\\" + user + " -p " + password + " -s cmd /c time " + timePart;
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			testCase.addTextEvidenceCurrentStep("La hora se actualiza a " + timePart);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public void detenerServicio() {
		try {
			String command = "psexec \\\\" + ip + " -u " + ip + "\\" + user + " -p " + password + " -s cmd /c sc stop XposServiceApi";
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		testCase.addTextEvidenceCurrentStep("Se detuvo el servicio XpoServiceApi con exito");
	}

	public void ejecutarServicio() {
		try {
			String command = "psexec \\\\" + ip + " -u " + ip + "\\" + user + " -p " + password + " -s cmd /c sc start XposServiceApi";
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		testCase.addTextEvidenceCurrentStep("Se ejecuta el servicio XpoServiceApi con exito");
	}
	

	public void resetHoraFecha(String reseteaFyH) {
		LocalDateTime horaActual = LocalDateTime.now();
		String formattedDateTime = horaActual.format(DateTimeFormatter.ofPattern("MM-dd-yy HH:mm:ss"));
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-dd-yy HH:mm:ss");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, inputFormatter);
		comando.reseteaFyH = dateTime.format(outputFormatter);
		resetFecha(comando.reseteaFyH);
	}
	
	public void resetFecha(String reseteaFyH) {
		try {
			String[] dateTimeParts = comando.reseteaFyH.split(" ");
			String datePart = dateTimeParts[0].replace("-", "/");
			String command = "psexec \\\\" + ip + " -u " + ip + "\\" + user + " -p " + password + " -s cmd /c date " + datePart ;
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			resetHora(comando.reseteaFyH);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	public void resetHora(String reseteaFyH) {
		try {
			String[] dateTimeParts = comando.reseteaFyH.split(" ");
			String timePart = dateTimeParts[1];
			String command = "psexec \\\\" + ip + " -u " + ip + "\\" + user + " -p " + password + " -s cmd /c time " + timePart;
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}

