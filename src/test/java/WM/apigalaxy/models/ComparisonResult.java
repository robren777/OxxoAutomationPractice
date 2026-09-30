package WM.apigalaxy.models;

import java.util.List;

public class ComparisonResult {
    private boolean success;
    private List<String> details;

    public ComparisonResult(boolean success, List<String> details) {
        this.success = success;
        this.details = details;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getDetails() {
        return details;
    }
}