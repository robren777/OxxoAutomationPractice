package EBS.util.FolioPagoMVP2;

public class GeneradorPaths {

    public static void main(String[] args) {
        // Crear una instancia de PathsXML con 5000 paths
        PathsXML pathsXML = new PathsXML(5000);

        // Obtener y mostrar algunos paths
        System.out.println("Primer path: " + pathsXML.getPath(1));
        System.out.println("Último path: " + pathsXML.getPath(5000));

        // Imprimir todos los paths (opcional, cuidado con 5000 líneas en consola)
        // pathsXML.imprimirPaths();
    }
}
