/**
 * 
 */
package WM.apigalaxy.models;

/**
 * 
 */
public class Result {

    private String texto;
    private boolean flag;

    public Result(String texto, boolean flag) {
        this.texto = texto;
        this.flag = flag;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isFlag() {
        return flag;
    }
}
