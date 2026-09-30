package Mobile.Mep.om;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class utilPhotoEmulator {    
	
	/**
	* Realiza una serie de operaciones con un archivo PNG ubicado en una carpeta especifica del proyecto:
	* copia el archivo original, lo mueve a una ubicacion del emulador de Android, y finalmente lo renombra.
	*
	* Flujo del metodo:
	* 1. Copia el archivo original `<nombre>.png` desde el directorio del proyecto.
	* 2. Mueve la copia al directorio de recursos del emulador de Android (`imagen_copia.png`).
	* 3. Renombra el archivo movido a `imagen.png`.
	*
	* Este proceso es util para actualizar imágenes de prueba utilizadas por el emulador sin sobrescribir directamente la original.
	*
	* @author Ivan
	* @param nameOriginalFilePng Nombre base del archivo `.png` original (sin extensión), ubicado en 
	*        `src/test/java/User/ArucosAndQrFolder/`. Este será el archivo fuente para copiar, mover y renombrar.
	* @param directorioDestinoFolder Este es el directorio destino donde se guardará la imagen con extension _copy.png
	* @date 07/Agosto/2024
	*/
    public void CopiarMoverRenombrar(String nameOriginalFilePng, String directorioDestinoFolder)
    {
    	try {
            // Ruta del archivo original
            Path archivoOriginal = Paths.get(System.getProperty("user.dir") + "\\src\\test\\java\\Mobile\\Mep\\ArucosAndQrFolder\\" + nameOriginalFilePng + ".png");

            // Ruta de la copia temporal (puede estar en el mismo directorio de origen)
            Path copiaTemporal = Paths.get(System.getProperty("user.dir") + "\\src\\test\\java\\Mobile\\Mep\\ArucosAndQrFolder\\" + nameOriginalFilePng + "_copy" + ".png");

            // Copiar el archivo original
            Files.copy(archivoOriginal, copiaTemporal, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Imagen copiada.");

            // Ruta del directorio destino
            Path directorioDestino = Paths.get(directorioDestinoFolder);

            // Mover la copia al nuevo directorio y establecer el nombre de imagen_copia
            Path copiaMovida = directorioDestino.resolve("imagen_copia.png");
            Files.move(copiaTemporal, copiaMovida, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copia movida al nuevo directorio.");

            // Renombrar la copia movida a imagen.png
            Path nuevaRuta = directorioDestino.resolve("imagen.png");
            Files.move(copiaMovida, nuevaRuta, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copia renombrada como imagen.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
	* Sobrescribe la imagen "objetivo" que el Emulador de Android está enfocando en su VirtualScene.
	* * Requisito previo: El emulador debe tener configurada la cámara en "VirtualScene" y estar
	* apuntando visualmente a la ubicación del archivo definido en 'rutaImagenObjetivoPC'.
	*
	* @param nameOriginalFilePng Nombre del QR/ArUco (sin extensión) ubicado en `src/test/java/...`.
	* @param rutaImagenObjetivoPC Ruta LOCAL en tu PC donde el emulador busca la imagen (ej: "C:\\AutoTestImages\\target.png").
	*/
    public void cambiarImagenCamara(String nameOriginalFilePng, String rutaImagenObjetivoPC)
    {
    	try {
            // 1. Ruta del QR que quieres probar (Fuente)
            Path archivoFuente = Paths.get(System.getProperty("user.dir") 
                    + "\\src\\test\\java\\Mobile\\Mep\\ArucosAndQrFolder\\" 
                    + nameOriginalFilePng + ".png");

            if (!Files.exists(archivoFuente)) {
                System.out.println("ERROR: No encuentro el QR fuente: " + archivoFuente.toString());
                return;
            }

            // 2. Ruta del archivo que el emulador está mirando (Destino Fijo)
            // OJO: Esta debe ser la ruta completa incluyendo el nombre y extensión, ej: target.png
            Path archivoDestinoFijo = Paths.get(rutaImagenObjetivoPC);

            // 3. Sobrescribir
            // Al hacer esto, cambiamos la "pintura de la pared" del emulador
            Files.copy(archivoFuente, archivoDestinoFijo, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("Imagen de cámara actualizada.");
            System.out.println("Fuente: " + nameOriginalFilePng);
            System.out.println("Destino (Emulator Wall): " + rutaImagenObjetivoPC);
            
            // NOTA: A veces el emulador cachea la imagen. 
            // Puede ser necesario poner un pequeño Thread.sleep(1000) o reiniciar la vista de cámara en tu test.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
