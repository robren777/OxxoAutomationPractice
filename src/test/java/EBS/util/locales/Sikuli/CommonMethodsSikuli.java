package EBS.util.locales.Sikuli;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

public class CommonMethodsSikuli {

    static Screen screen = new Screen();
    static DesktopKeyboard keyboard = new DesktopKeyboard();
    static String BASE_DIR = Paths.get(".").toAbsolutePath().normalize().toString();
    static String pathCarpetaLocal = "\\Patterns\\Locales\\";

    /**
     * Metodo para esperar por una imagen y mandar texto en una caja de texto
     *
     * @param imageName
     * @param textToType
     * @param path
     * @return Respuesta de la accion
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitForAndSendKeysImage(String imageName, String textToType, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 20;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    keyboard.type(textToType);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para esperar por la imagen que se dara click
     *
     * @param imageName
     * @param path
     * @return
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitForAndClickImage(String imageName, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 20;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para esperar y haga click en la imagen de la deracha
     *
     * @param imageName
     * @param path
     * @return
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitForClickOnTheImageOnTheRight(String imageName, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 20;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    Location center = match.getCenter();
                    Location rightSide = new Location(center.getX() + (match.getW() / 2), center.getY());
                    screen.click(rightSide);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para esperar por una imagen
     *
     * @param imageName
     * @param path
     * @return Respuesta de la accion
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitForImage(String imageName, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 20;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para esperar y dar click en la imagen de opcion
     *
     * @param imageName
     * @param path
     * @return Respuesta de la accion
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitForAndClickImageOption(String imageName, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 20;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para escojer una responsabilidad dentro del aplicativo de JAVA
     *
     * @param option
     * @param path
     * @return Imagen con responsabilidad
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean optionResponsabilidad(String option, String path) {
        switch (option) {
            case "CHI":
                boolean status1 = waitForAndClickImage("opcionResponsabilidadCHI.png", path);
                return status1;
            case "GUD":
                boolean status2 = waitForAndClickImage("opcionResponsabilidadGUD.png", path);
                return status2;
            case "MON":
                boolean status3 = waitForAndClickImage("opcionResponsabilidadMON.png", path);
                return status3;
            case "TIJ":
                boolean status4 = waitForAndClickImage("opcionResponsabilidadTIJ.png", path);
                return status4;
            default:
                System.out.println("Option not found");
                return false;

        }
    }

    /**
     * Metodo para esperar por una imagen y presinonar la tecla TAB
     *
     * @param imageName
     * @param path
     * @return Respuesta de la accion
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitTABImage(String imageName, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 20;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    screen.type(Keys.TAB);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para escribir texto
     *
     * @param text
     * @author Francisco Javier Mendoza Tamayo
     */
    public static void writeCursor(String text) {
        try {
            screen.type(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para simular la tecla tab
     *
     * @author Francisco Javier Mendoza Tamayo
     */
    public static void simulateTab() {
        try {
            screen.type(Key.TAB);
            screen.type(Key.TAB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para extraer la solicitud ID
     *
     * @return solicitud ID
     * @author Francisco Javier Mendoza Tamayo
     */
    public static String extractSolicitudID() {
        try {
            Region textRegion = new Region(544, 894, 400, 100);
            String captureText = textRegion.text();
            System.out.println("Texto capturado por OCR: " + captureText);
            Pattern pattern = Pattern.compile("ID de Solicitud = (\\d+)");
            Matcher matcher = pattern.matcher(captureText);

            if (matcher.find()) {
                return matcher.group(1);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo para esperar por una imagen y darle click para continuar
     *
     * @param imageName
     * @param path
     * @param imageName2
     * @author Francisco Javier Mendoza Tamayo
     */
    public static void waitForAndClickImageContinue(String imageName, String path, String imageName2) {
        String imagePath1 = BASE_DIR + path + imageName;
        String imagePath2 = BASE_DIR + path + imageName2;
        int timeoutInSeconds = 8;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match1 = screen.exists(imagePath1);
                if (match1 != null) {
                    Location center = match1.getCenter();
                    screen.click(center);
                    System.out.println("First image found and clicked: " + imagePath1);

                    Match match2 = screen.exists(imagePath2);
                    Location center2 = match2.getCenter();
                    screen.click(center2);
                    System.out.println("Second image found and clicked: " + imagePath2);
                    return;

                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Retrying to locate image: " + imagePath1);
            }
        }
        System.out.println("Image not found within # seconds");
    }

    /**
     * Metodo para mover el cursor con movimiento random
     *
     * @param moves
     * @param delay
     * @throws Exception
     * @author Francisco Javier Mendoza Tamayo
     */
    public static void moveMouseRandomly(int moves, int delay) throws Exception {
        Random random = new Random();

        int screenWidth = screen.w;
        int screenHeight = screen.h;

        for (int i = 0; i < moves; i++) {
            try {
                int x = random.nextInt(screenWidth);
                int y = random.nextInt(screenHeight);

                Location randomLocation = new Location(x, y);
                screen.mouseMove(randomLocation);
                System.out.println("Mouse moved to: X=" + x + ", Y=" + y);
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // TODO: handle exception
                System.err.println("Error during mouse movement: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para espera y que se de click en la imagen superior derecha
     *
     * @param imageName
     * @param path
     * @return
     * @author Francisco Javier Mendoza Tamayo
     */
    public static boolean waitForAndClickImageRightTop(String imageName, String path) {
        String imagePath = BASE_DIR + path + imageName;
        int timeoutInSeconds = 15;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    int xOffset = match.getW() - 10;
                    int yOffset = 10;
                    Location closeButton = new Location(match.getX() + xOffset, match.getY() + yOffset);
                    screen.click(closeButton);
                    System.out.println("Close button clicked.");
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Retrying to locate and click close button: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Close button not found.");
        return false;
    }
    //Nuevos metodos

    /**
     * Metodo de espera, para validacion de la pantalla de aprovacion para ejecucion de aplicacion.
     * En caso de que no se muestre la pantalla, se toma como no se requiere autorizacion y se ejecuta la aplicacion.
     *
     * @param imageName
     * @param imageName2
     * @author David Quiroz Rodriguez
     */
    public static void theUserWaitsToApproveAndRunApplication(String imageName, String imageName2) {
        String imagePath1 = BASE_DIR + pathCarpetaLocal + imageName + ".png";
        String imagePath2 = BASE_DIR + pathCarpetaLocal + imageName2 + ".png";
        int timeoutInSeconds = 10;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match1 = screen.exists(imagePath1);
                if (match1 != null) {
                    screen.click(match1);
                    Match match2 = screen.exists(imagePath2);
                    screen.click(match2);
                    System.out.println("Approval is given to run the application");
                    return;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("The image was not found " + imagePath1 + "\n Authorization is not required");
            }
        }
        System.out.println("Application is executed");
    }

    /**
     * Metodo para dar click en un campo especificado.
     *
     * @param imageName
     * @return Respuesta de accion click
     * @author David Quiroz Rodriguez
     */
    public static boolean theUserClicksOn(String imageName) {
        int timeWaiting = 10;
        int elapsedTime = 0;
        String pathImage = BASE_DIR + pathCarpetaLocal + imageName + ".png";

        while (elapsedTime < timeWaiting * 1000) {
            try {
                Match match = screen.exists(pathImage);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    System.out.println("The user click on " + imageName);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imageName);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imageName);
        return false;
    }

    /**
     * Metodo para ingresar texto en un campo especificado.
     *
     * @param imageName
     * @param textValue
     * @return Respuesta de envio de texto
     * @author David Quiroz Rodriguez
     */
    public static boolean theUserSendTextIn(String imageName, String textValue) {
        int timeWaiting = 10;
        int elapsedTime = 0;
        String pathImage = BASE_DIR + pathCarpetaLocal + imageName + ".png";
        while (elapsedTime < timeWaiting * 1000) {
            try {
                Match match = screen.exists(pathImage);
                if (match != null) {
                    screen.click(match);
                    screen.paste(textValue);
                    //keyboard.type(textValue);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("The image was not found " + imageName);
            }
        }
        return false;
    }

    /**
     * Metodo para verificar si todavia se esta ejecutando un reporte o concurrente en en apartado de solicitudes,
     * encaso de seguir ejecutandose este espera 1 minuto para dar click en el boton Refrescar Datos
     *
     * @param imageRunReport
     * @param imageRunRefeshData
     * @author David Quiroz Rodriguez
     */
    public static void theUserChecksIfTheReportIsRunning(String imageRunReport, String imageRunRefeshData) {
        String imagePath1 = BASE_DIR + pathCarpetaLocal + imageRunReport + ".png";
        String imagePath2 = BASE_DIR + pathCarpetaLocal + imageRunRefeshData + ".png";
        int timeoutInSeconds = 240;
        int elapsedTime = 0;
        while (elapsedTime < timeoutInSeconds) {
            try {
                Match match = screen.exists(imagePath1);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    screen.click(imagePath2); // se da click en el boton de refrescar datos
                    System.out.println("Click on the button " + imageRunRefeshData + " have eleapsed " + elapsedTime + " period of 20 seconds");
                    Thread.sleep(1000 * 20); //Minuto de espera
                    elapsedTime += 1;
                } else {
                    elapsedTime = 240;
                    System.out.println("End process, finish timer");
                }
            } catch (Exception e) {
                System.out.println("Report stopped running, end of process");
            }
        }
        System.out.println("The report has finished, check the results");
    }

    public static void closeTabCiclico(String additionalPageImage){
        String imagePath1 = BASE_DIR + pathCarpetaLocal + additionalPageImage + ".png";
        int timeoutInSeconds = 10;
        int elapsedTime = 0;
        while (elapsedTime < timeoutInSeconds) {
            try {
                Match match = screen.exists(imagePath1);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    Thread.sleep(1000);
                    screen.keyDown(org.sikuli.script.Key.CTRL);
                    screen.keyDown("w");
                    screen.keyDown(org.sikuli.script.Key.CTRL);
                    screen.keyDown("w");
                    //Se adiciona el escape ya que se queda presionada la tecla control al validarlo en la ejecucion, se ingresa para ver su comportamiento.
                    screen.click();
                    Thread.sleep(1000 * 3); //3 segundos de espera
                    elapsedTime += 1;
                } else {
                    elapsedTime = 20;
                    System.out.println("Ya se cerro la pestaña adicional");
                }
            } catch (Exception e) {
                System.out.println("Cierre de pagina adicional");
            }
        }
    }
    /**
     * Metodo para cerrar una pestaña
     *
     * @author David Quiroz Rodriguez
     */
    public static void closeTab() {
        screen.keyDown(org.sikuli.script.Key.CTRL);
        screen.keyDown("w");
        screen.keyDown(org.sikuli.script.Key.CTRL);
        screen.keyDown("w");
        //Se adiciona el escape ya que se queda presionada la tecla control al validarlo en la ejecucion, se ingresa para ver su comportamiento.
        screen.click();
    }

    /**
     * Metodo para cambiar de pestaña
     *
     * @author David Quiroz Rodriguez
     */
    public static void theUserSwitchesScreens() {
        screen.type(Key.ALT);
        screen.type(Key.TAB);
        screen.type(Key.ALT);
        screen.type(Key.TAB);

    }

    /**
     * Metodo para obtener todo el texto de una pagina web y guardarlo en un variable
     *
     * @return
     * @throws FindFailed
     * @throws HeadlessException
     * @throws UnsupportedFlavorException
     * @throws IOException
     * @author David Quiroz Rodriguez
     */
    public static String getAllTextPage()
            throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
        //screen.click();
        screen.keyDown(org.sikuli.script.Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
        screen.keyDown("a");
        screen.keyUp(org.sikuli.script.Key.CTRL);
        screen.keyUp("a");

        screen.keyDown(org.sikuli.script.Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
        screen.keyDown("c");
        screen.keyUp(org.sikuli.script.Key.CTRL);
        screen.keyUp("c");
        // El texto que copiamos
        String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        screen.mouseDown(Button.LEFT);
        screen.mouseUp(Button.LEFT);
        return textFound; // Regresamos toda la cadena de texto copiada
    }

    /**
     * Metodo para dar click en una imagen de la pagina de EBS
     *
     * @param imageName
     * @param directoryName
     * @return true
     * @author David Quiroz Rodriguez
     */
    public static boolean theUserClicksOnAnObjectOnTheEBSPage(String imageName, String directoryName) {
        String imagePath = BASE_DIR + pathCarpetaLocal + directoryName + "\\" + imageName + ".png";
        int timeoutInSeconds = 10;
        int elapsedTime = 0;

        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    Location center = match.getCenter();
                    screen.click(center);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("Retrying to  locate image: " + imagePath);
            }
        }
        System.out.println("Timeout reached. Image not found: " + imagePath);
        return false;
    }

    /**
     * Metodo para enviar texto a una caja de texto en la pagina de EBS
     *
     * @param imageName
     * @param textValue
     * @param directoryName
     * @return true
     * @author David Quiroz Rodriguez
     */
    public static boolean theUserSendValueIn(String imageName, String textValue, String directoryName) {
        String imagePath = BASE_DIR + pathCarpetaLocal + directoryName + "\\" + imageName + ".png";
        int timeoutInSeconds = 10;
        int elapsedTime = 0;
        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    screen.click(match);
                    screen.paste(textValue);
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("The image was not found " + imageName);
            }
        }
        return false;
    }

    /**
     * Metodo para esperar que sea visible una imagen en la pagina de EBS
     *
     * @param imageName
     * @param directoryName
     * @return true
     * @author David Quiroz Rodriguez
     */
    public static boolean theUserWaitForImage(String imageName, String directoryName) {
        String imagePath = BASE_DIR + pathCarpetaLocal + directoryName + "\\" + imageName + ".png";
        int timeoutInSeconds = 15;
        int elapsedTime = 0;
        while (elapsedTime < timeoutInSeconds * 1000) {
            try {
                Match match = screen.exists(imagePath);
                if (match != null) {
                    return true;
                }
                Thread.sleep(1000);
                elapsedTime += 1000;
            } catch (Exception e) {
                System.out.println("The image was not found " + imageName);
            }
        }
        return false;
    }

    /**
     * Metodo para eliminar caracteres de una caja de texto
     * @param finCiclo
     * @Author David Quiroz Rodriguez
     */
    //Nuevos metodos por matenimineto de scripts
    public static void eliminarTextoConBackSpace(int finCiclo){
        int inicio=0;
        while(inicio<finCiclo){
            screen.keyUp(org.sikuli.script.Key.BACKSPACE);
            screen.keyUp(org.sikuli.script.Key.BACKSPACE);
        }
    }

}
