package WM.apigalaxy.models;

import java.util.List;

public class StoresSpacesResponse {

    private int limit;
    private int returnedItems;
    private boolean hasMore;
    private int nextCursor;
    private List<StoreSpace> storesSpaces;
    
    public StoresSpacesResponse() {
    }

    // Getters y Setters
    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public int getReturnedItems() { return returnedItems; }
    public void setReturnedItems(int returnedItems) { this.returnedItems = returnedItems; }

    public boolean isHasMore() { return hasMore; }
    public void setHasMore(boolean hasMore) { this.hasMore = hasMore; }

    public int getNextCursor() { return nextCursor; }
    public void setNextCursor(int nextCursor) { this.nextCursor = nextCursor; }

    public List<StoreSpace> getStoresSpaces() { return storesSpaces; }
    public void setStoresSpaces(List<StoreSpace> storesSpaces) { this.storesSpaces = storesSpaces; }

    
   
}
