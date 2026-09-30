package Mobile.util.winium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

public class ApplicationManager {

	public static boolean isApplicationRunning(String applicationName) {
        String line;
        boolean isRunning = false;
        try {
            Process process = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(applicationName.toLowerCase())) {
                    isRunning = true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isRunning;
    }
	
	public static void switchToApplication(String applicationName) {
	    //String matchCriteria = applicationName; // Adjust as needed
	    String psScript = "$handle = (Get-Process | Where-Object { $_.MainWindowTitle -eq '"+applicationName+"' } | Select-Object -First 1).MainWindowHandle;\r\n"
	    		+ " " +
                "Add-Type -MemberDefinition '[DllImport(\\\"user32.dll\\\")]" +
                "public static extern bool SetForegroundWindow(IntPtr hWnd);' -Name 'WinAPI' -Namespace 'WinAPI'; " +
                "[WinAPI.WinAPI]::SetForegroundWindow($handle)";
	    System.out.println(psScript);
	    //String command = "powershell.exe -Command \"" + psScript + "\"";
	    try {
	    	ProcessBuilder builder = new ProcessBuilder("powershell.exe", "-Command", psScript);
	    	builder.redirectErrorStream(true); // Combines the error and input streams.
	    	Process process = builder.start();

	    	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    	String line;
	    	while ((line = reader.readLine()) != null) {
	    	    System.out.println(line);
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
//	public static void switchToApplicationByWindowTitle(String windowTitle) {
//		String psScript = "$code = @\"\n" +
//		        "using System;\n" +
//		        "using System.Text;\n" +
//		        "using System.Runtime.InteropServices;\n" +
//		        "public class WinAPI {\n" +
//		        "    [DllImport(\\\"user32.dll\\\", SetLastError = true)]\n" +
//		        "    public static extern bool SetForegroundWindow(IntPtr hWnd);\n" +
//		        "    [DllImport(\\\"user32.dll\\\")]\n" +
//		        "    [return: MarshalAs(UnmanagedType.Bool)]\n" +
//		        "    public static extern bool IsWindowVisible(IntPtr hWnd);\n" +
//		        "    [DllImport(\\\"user32.dll\\\", CharSet = CharSet.Unicode)]\n" +
//		        "    public static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int nMaxCount);\n" +
//		        "    [DllImport(\\\"user32.dll\\\")]\n" +
//		        "    public static extern bool EnumWindows(EnumWindowsProc lpEnumFunc, IntPtr lParam);\n" +
//		        "    public delegate bool EnumWindowsProc(IntPtr hWnd, IntPtr lParam);\n" +
//		        "    public static IntPtr FindWindowByCaption(string windowTitle) {\n" +
//		        "        IntPtr found = IntPtr.Zero;\n" +
//		        "        EnumWindows(delegate (IntPtr wnd, IntPtr param) {\n" +
//		        "            StringBuilder sb = new StringBuilder(256);\n" +
//		        "            GetWindowText(wnd, sb, sb.Capacity);\n" +
//		        "            if (sb.ToString().Contains(windowTitle) && IsWindowVisible(wnd)) {\n" +
//		        "                found = wnd;\n" +
//		        "                return false;\n" +
//		        "            }\n" +
//		        "            return true;\n" +
//		        "        }, IntPtr.Zero);\n" +
//		        "        return found;\n" +
//		        "    }\n" +
//		        "}\n" +
//		        "\"@;\n" +
//		        "Add-Type -TypeDefinition $code -Language CSharp;\n" +
//		        "$windowTitle = \\\"" + windowTitle + "\\\";\n" +
//		        "$hWnd = [WinAPI]::FindWindowByCaption($windowTitle);\n" +
//		        "if ($hWnd -ne [IntPtr]::Zero) {\n" +
//		        "    [WinAPI]::SetForegroundWindow($hWnd);\n" +
//		        "} else {\n" +
//		        "    Write-Host \\\"Window not found.\\\";\n" +
//		        "}\n";
//	    System.out.println(psScript);
//	    String command = "powershell.exe -Command \"" + psScript + "\"";
//	    try {
//	    	ProcessBuilder builder = new ProcessBuilder("powershell.exe", "-Command", psScript);
//	    	builder.redirectErrorStream(true); // Combines the error and input streams.
//	    	Process process = builder.start();
//
//	    	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//	    	String line;
//	    	while ((line = reader.readLine()) != null) {
//	    	    System.out.println(line);
//	    	}
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
	
	public static void switchToSubWindow(String windowTitle) {
		String psScript = "Add-Type @\"\r\n"
				+ "  using System;\r\n"
				+ "  using System.Text;\r\n"
				+ "  using System.Runtime.InteropServices;\r\n"
				+ "  public class WinAPI {\r\n"
				+ "      [DllImport(\"user32.dll\", SetLastError = true)]\r\n"
				+ "      public static extern bool SetForegroundWindow(IntPtr hWnd);\r\n"
				+ "\r\n"
				+ "      [DllImport(\"user32.dll\")]\r\n"
				+ "      [return: MarshalAs(UnmanagedType.Bool)]\r\n"
				+ "      public static extern bool IsWindowVisible(IntPtr hWnd);\r\n"
				+ "\r\n"
				+ "      [DllImport(\"user32.dll\", CharSet = CharSet.Unicode)]\r\n"
				+ "      public static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int nMaxCount);\r\n"
				+ "\r\n"
				+ "      [DllImport(\"user32.dll\")]\r\n"
				+ "      public static extern bool EnumWindows(EnumWindowsProc lpEnumFunc, IntPtr lParam);\r\n"
				+ "\r\n"
				+ "      public delegate bool EnumWindowsProc(IntPtr hWnd, IntPtr lParam);\r\n"
				+ "\r\n"
				+ "      public static IntPtr FindWindowByCaption(string windowTitle) {\r\n"
				+ "          IntPtr found = IntPtr.Zero;\r\n"
				+ "          EnumWindows(delegate (IntPtr wnd, IntPtr param) {\r\n"
				+ "              StringBuilder sb = new StringBuilder(256);\r\n"
				+ "              GetWindowText(wnd, sb, sb.Capacity);\r\n"
				+ "              if (sb.ToString().Contains(windowTitle) && IsWindowVisible(wnd)) {\r\n"
				+ "                  found = wnd;\r\n"
				+ "                  return false; // Found the window, stop enumerating\r\n"
				+ "              }\r\n"
				+ "              return true;\r\n"
				+ "          }, IntPtr.Zero);\r\n"
				+ "          return found;\r\n"
				+ "      }\r\n"
				+ "  }\r\n"
				+ "\"@\r\n"
				+ "\r\n"
				+ "$windowTitle = \""+windowTitle+"\" # The title of the window to focus\r\n"
				+ "$hWnd = [WinAPI]::FindWindowByCaption($windowTitle)\r\n"
				+ "\r\n"
				+ "if ($hWnd -ne [IntPtr]::Zero) {\r\n"
				+ "    [WinAPI]::SetForegroundWindow($hWnd)\r\n"
				+ "} else {\r\n"
				+ "    Write-Host \"Window not found.\"\r\n"
				+ "}\r\n"
				+ "";
		
		
		String base64EncodedScript = encodeScript(psScript);
		//System.out.println("encoded script: " + base64EncodedScript );
		
	    // The encoded script from PowerShell
	    //String base64EncodedScript = "CgAgACAAIwAgAFAAbwB3AGUAcgBTAGgAZQBsAGwAIABjAG8AZABlACAAaABlAHIAZQAKACAAIABBAGQAZAAtAFQAeQBwAGUAIABAACIACgAgACAAIAAgAHUAcwBpAG4AZwAgAFMAeQBzAHQAZQBtADsACgAgACAAIAAgAHUAcwBpAG4AZwAgAFMAeQBzAHQAZQBtAC4AVABlAHgAdAA7AAoAIAAgACAAIAB1AHMAaQBuAGcAIABTAHkAcwB0AGUAbQAuAFIAdQBuAHQAaQBtAGUALgBJAG4AdABlAHIAbwBwAFMAZQByAHYAaQBjAGUAcwA7AAoAIAAgACAAIABwAHUAYgBsAGkAYwAgAGMAbABhAHMAcwAgAFcAaQBuAEEAUABJACAAewAKACAAIAAgACAAIAAgACAAIABbAEQAbABsAEkAbQBwAG8AcgB0ACgAIgB1AHMAZQByADMAMgAuAGQAbABsACIALAAgAFMAZQB0AEwAYQBzAHQARQByAHIAbwByACAAPQAgAHQAcgB1AGUAKQBdAAoAIAAgACAAIAAgACAAIAAgAHAAdQBiAGwAaQBjACAAcwB0AGEAdABpAGMAIABlAHgAdABlAHIAbgAgAGIAbwBvAGwAIABTAGUAdABGAG8AcgBlAGcAcgBvAHUAbgBkAFcAaQBuAGQAbwB3ACgASQBuAHQAUAB0AHIAIABoAFcAbgBkACkAOwAKACAAIAAgACAAIAAgACAAIABbAEQAbABsAEkAbQBwAG8AcgB0ACgAIgB1AHMAZQByADMAMgAuAGQAbABsACIAKQBdAAoAIAAgACAAIAAgACAAIAAgAFsAcgBlAHQAdQByAG4AOgAgAE0AYQByAHMAaABhAGwAQQBzACgAVQBuAG0AYQBuAGEAZwBlAGQAVAB5AHAAZQAuAEIAbwBvAGwAKQBdAAoAIAAgACAAIAAgACAAIAAgAHAAdQBiAGwAaQBjACAAcwB0AGEAdABpAGMAIABlAHgAdABlAHIAbgAgAGIAbwBvAGwAIABJAHMAVwBpAG4AZABvAHcAVgBpAHMAaQBiAGwAZQAoAEkAbgB0AFAAdAByACAAaABXAG4AZAApADsACgAgACAAIAAgACAAIAAgACAAWwBEAGwAbABJAG0AcABvAHIAdAAoACIAdQBzAGUAcgAzADIALgBkAGwAbAAiACwAIABDAGgAYQByAFMAZQB0ACAAPQAgAEMAaABhAHIAUwBlAHQALgBVAG4AaQBjAG8AZABlACkAXQAKACAAIAAgACAAIAAgACAAIABwAHUAYgBsAGkAYwAgAHMAdABhAHQAaQBjACAAZQB4AHQAZQByAG4AIABpAG4AdAAgAEcAZQB0AFcAaQBuAGQAbwB3AFQAZQB4AHQAKABJAG4AdABQAHQAcgAgAGgAVwBuAGQALAAgAFMAdAByAGkAbgBnAEIAdQBpAGwAZABlAHIAIABsAHAAUwB0AHIAaQBuAGcALAAgAGkAbgB0ACAAbgBNAGEAeABDAG8AdQBuAHQAKQA7AAoAIAAgACAAIAAgACAAIAAgAFsARABsAGwASQBtAHAAbwByAHQAKAAiAHUAcwBlAHIAMwAyAC4AZABsAGwAIgApAF0ACgAgACAAIAAgACAAIAAgACAAcAB1AGIAbABpAGMAIABzAHQAYQB0AGkAYwAgAGUAeAB0AGUAcgBuACAAYgBvAG8AbAAgAEUAbgB1AG0AVwBpAG4AZABvAHcAcwAoAEUAbgB1AG0AVwBpAG4AZABvAHcAcwBQAHIAbwBjACAAbABwAEUAbgB1AG0ARgB1AG4AYwAsACAASQBuAHQAUAB0AHIAIABsAFAAYQByAGEAbQApADsACgAgACAAIAAgACAAIAAgACAAcAB1AGIAbABpAGMAIABkAGUAbABlAGcAYQB0AGUAIABiAG8AbwBsACAARQBuAHUAbQBXAGkAbgBkAG8AdwBzAFAAcgBvAGMAKABJAG4AdABQAHQAcgAgAGgAVwBuAGQALAAgAEkAbgB0AFAAdAByACAAbABQAGEAcgBhAG0AKQA7AAoAIAAgACAAIAAgACAAIAAgAHAAdQBiAGwAaQBjACAAcwB0AGEAdABpAGMAIABJAG4AdABQAHQAcgAgAEYAaQBuAGQAVwBpAG4AZABvAHcAQgB5AEMAYQBwAHQAaQBvAG4AKABzAHQAcgBpAG4AZwAgAHcAaQBuAGQAbwB3AFQAaQB0AGwAZQApACAAewAKACAAIAAgACAAIAAgACAAIAAgACAAIAAgAEkAbgB0AFAAdAByACAAZgBvAHUAbgBkACAAPQAgAEkAbgB0AFAAdAByAC4AWgBlAHIAbwA7AAoAIAAgACAAIAAgACAAIAAgACAAIAAgACAARQBuAHUAbQBXAGkAbgBkAG8AdwBzACgAZABlAGwAZQBnAGEAdABlACAAKABJAG4AdABQAHQAcgAgAHcAbgBkACwAIABJAG4AdABQAHQAcgAgAHAAYQByAGEAbQApACAAewAKACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAUwB0AHIAaQBuAGcAQgB1AGkAbABkAGUAcgAgAHMAYgAgAD0AIABuAGUAdwAgAFMAdAByAGkAbgBnAEIAdQBpAGwAZABlAHIAKAAyADUANgApADsACgAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgAEcAZQB0AFcAaQBuAGQAbwB3AFQAZQB4AHQAKAB3AG4AZAAsACAAcwBiACwAIABzAGIALgBDAGEAcABhAGMAaQB0AHkAKQA7AAoAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIABpAGYAIAAoAHMAYgAuAFQAbwBTAHQAcgBpAG4AZwAoACkALgBDAG8AbgB0AGEAaQBuAHMAKAB3AGkAbgBkAG8AdwBUAGkAdABsAGUAKQAgACYAJgAgAEkAcwBXAGkAbgBkAG8AdwBWAGkAcwBpAGIAbABlACgAdwBuAGQAKQApACAAewAKACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIABmAG8AdQBuAGQAIAA9ACAAdwBuAGQAOwAKACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAByAGUAdAB1AHIAbgAgAGYAYQBsAHMAZQA7ACAALwAvACAARgBvAHUAbgBkACAAdABoAGUAIAB3AGkAbgBkAG8AdwAsACAAcwB0AG8AcAAgAGUAbgB1AG0AZQByAGEAdABpAG4AZwAKACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAfQAKACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAIAAgACAAcgBlAHQAdQByAG4AIAB0AHIAdQBlADsACgAgACAAIAAgACAAIAAgACAAIAAgACAAIAB9ACwAIABJAG4AdABQAHQAcgAuAFoAZQByAG8AKQA7AAoAIAAgACAAIAAgACAAIAAgACAAIAAgACAAcgBlAHQAdQByAG4AIABmAG8AdQBuAGQAOwAKACAAIAAgACAAIAAgACAAIAB9AAoAIAAgACAAIAB9AAoAIgBAACAALQBMAGEAbgBnAHUAYQBnAGUAIABDAFMAaABhAHIAcAAKAAoAIAAgACQAdwBpAG4AZABvAHcAVABpAHQAbABlACAAPQAgACIATQBpAGMAcgBvAHMAbwBmAHQAIABQAG8AcwBQAHIAaQBuAHQAZQByACAAUwBpAG0AdQBsAGEAdABvAHIAIgAKACAAIAAkAGgAVwBuAGQAIAA9ACAAWwBXAGkAbgBBAFAASQBdADoAOgBGAGkAbgBkAFcAaQBuAGQAbwB3AEIAeQBDAGEAcAB0AGkAbwBuACgAJAB3AGkAbgBkAG8AdwBUAGkAdABsAGUAKQAKAAoAIAAgAGkAZgAgACgAJABoAFcAbgBkACAALQBuAGUAIABbAEkAbgB0AFAAdAByAF0AOgA6AFoAZQByAG8AKQAgAHsACgAgACAAIAAgACAAIABbAFcAaQBuAEEAUABJAF0AOgA6AFMAZQB0AEYAbwByAGUAZwByAG8AdQBuAGQAVwBpAG4AZABvAHcAKAAkAGgAVwBuAGQAKQAKACAAIAB9ACAAZQBsAHMAZQAgAHsACgAgACAAIAAgACAAIABXAHIAaQB0AGUALQBIAG8AcwB0ACAAIgBXAGkAbgBkAG8AdwAgAG4AbwB0ACAAZgBvAHUAbgBkAC4AIgAKACAAIAB9AAoA"; // Replace with your Base64-encoded script

	    String command = "powershell.exe -EncodedCommand " + base64EncodedScript;
	    
	    ProcessBuilder builder = new ProcessBuilder(command.split(" "));
	    builder.redirectErrorStream(true);
	    try {
	        Process process = builder.start();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private static String encodeScript(String script) {
        return Base64.getEncoder().encodeToString(script.getBytes(java.nio.charset.StandardCharsets.UTF_16LE));
    }
	
	
//	public static void main(String[] args) {
////		String applicationName = "Microsoft Scanner Simulator"; 
////		String applicationName = "Microsoft PosPrinter Simulator"; 
//		String applicationName = "Xpos"; 
////        
////        if (isApplicationRunning(applicationName)) {
////            switchToApplication(applicationName);
////            System.out.println(applicationName + " is running and should now be in focus.");
////        } else {
////            System.out.println(applicationName + " is not running.");
////            // Optionally start the application here if needed
////        }
//		switchToSubWindow(applicationName);
//	}
	
}
