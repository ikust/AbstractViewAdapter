package co.infinum.ava.annotations.processor.tools;

/**
 * Created by ivan on 06/01/14.
 */
public class ViewHolderField {

    /**
     * Type of the field that is displayed in list item.
     */
    private ViewHolderFieldType type;

    /**
     * Resource id of the view.
     */
    private int viewResourceId;

    /**
     * Name of the method that is used to get field value from object.
     */
    private String objectMethodName;

    public ViewHolderField(ViewHolderFieldType type, int viewResourceId, String objectMethodName) {
        this.type = type;
        this.viewResourceId = viewResourceId;
        this.objectMethodName = objectMethodName;
    }

    public ViewHolderFieldType getType() {
        return type;
    }

    public int getViewResourceId() {
        return viewResourceId;
    }

    public String getObjectMethodName() {
        return objectMethodName;
    }
}
