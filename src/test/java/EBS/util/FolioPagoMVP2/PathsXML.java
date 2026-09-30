package EBS.util.FolioPagoMVP2;

import java.util.ArrayList;
import java.util.List;

public class PathsXML {

    // Lista para almacenar los paths
    private List<String> paths;

    // Constructor para inicializar la lista y generar los paths
    public PathsXML(int totalPaths) {
        paths = new ArrayList<>();
        generarPaths(totalPaths);
    }

    // Método para generar los paths dinámicamente
    private void generarPaths(int totalPaths) {
        String basePath = "Files\\folioDePago2QAWeb\\MVP2\\XML\\Prueba Volumetria\\xml_femsaws";

        for (int i = 1; i <= totalPaths; i++) {
            paths.add(basePath + i + ".xml");
        }
    }

    // Método para obtener un path específico por índice
    public String getPath(int index) {
        if (index < 1 || index > paths.size()) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
        }
        return paths.get(index - 1); // Restamos 1 porque los índices de las listas comienzan en 0
    }

    // Método para obtener todos los paths
    public List<String> getAllPaths() {
        return new ArrayList<>(paths); // Retornamos una copia para proteger la lista original
    }

    // Método para imprimir todos los paths (opcional)
    public void imprimirPaths() {
        for (String path : paths) {
            System.out.println(path);
        }
    }
}
