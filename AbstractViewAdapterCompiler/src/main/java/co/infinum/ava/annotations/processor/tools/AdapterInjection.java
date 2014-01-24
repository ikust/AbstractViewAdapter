package co.infinum.ava.annotations.processor.tools;

/**
 * Created by ivan on 13/01/14.
 */
public class AdapterInjection {

    /**
     * Name of the filed that will be injected with generated adapter.
     */
    private String fieldName;

    /**
     * Name of the generate adapter (actually view holder).
     */
    private String viewHolderName;

    /**
     * Fully qualified class name of the list item type.
     */
    private String objectType;

    /**
     * Resource ID of the listView to inject the Adapter to.
     */
    private int listViewId;


    public AdapterInjection(String fieldName, String viewHolderName, String objectType, int listViewId) {
        this.fieldName = fieldName;
        this.viewHolderName = viewHolderName;
        this.objectType = objectType;
        this.listViewId = listViewId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getViewHolderName() {
        return viewHolderName;
    }

    public String getObjectType() {
        return objectType;
    }

    public int getListViewId() { return listViewId; }
}
