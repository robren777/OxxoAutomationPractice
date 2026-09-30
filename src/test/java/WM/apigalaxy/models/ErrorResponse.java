package WM.apigalaxy.models;

public class ErrorResponse {

    private String code;
    private String message;

    // Constructor vacío (necesario para deserialización)
    public ErrorResponse() {
    }

    // Constructor con parámetros (opcional pero recomendado)
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter y Setter para code
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // Getter y Setter para message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Opcional: toString (útil para debugging)
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
