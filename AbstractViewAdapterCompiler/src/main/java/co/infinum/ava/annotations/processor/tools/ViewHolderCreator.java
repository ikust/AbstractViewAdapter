package co.infinum.ava.annotations.processor.tools;

import java.util.ArrayList;

/**
 * This class is used to create ViewHolder implementations (from template) that will be "injected".
 * <p>
 * ViewHolder can display two types of data: strings and images. Any other object or primitive type
 * will be cast to string by using String.valueOf() and treated as String type.
 */
public class ViewHolderCreator {

    protected static final String VIEW_HOLDER_TEMPLATE_PATH = "/co/infinum/ava/templates/ViewHolderTemplate.tpl";

    protected static final String TEXT_FIELD_TEMPLATE = "\tTextView ${fieldName};\n";

    protected static final String IMAGE_FIELD_TEMPLATE = "\tImageView ${fieldName};\n";

    protected static final String TEXT_FIELD_INIT_TEMPLATE = "\t\t${fieldName} = (TextView) viewLayout.findViewById(${viewResId});\n";

    protected static final String IMAGE_FIELD_INIT_TEMPLATE = "\t\t${fieldName} = (ImageView) viewLayout.findViewById(${viewResId});\n";

    protected static final String TEXT_FIELD_UPDATE_TEMPLATE = "\t\t${fieldName}.setText(String.valueOf(item.${objectMethodName}()));\n";

    protected static final String IMAGE_FIELD_UPDATE_TEMPLATE = "\t\t${fieldName}.setImageBitmap(item.${objectMethodName}());\n";

    protected static final String PACKAGE_NAME = "${packageName}";

    protected static final String CLASS_NAME = "${className}";

    protected static final String OBJECT_TYPE = "${objectType}";

    protected static final String FIELDS = "${fields}";

    protected static final String LAYOUT_ID = "${layoutId}";

    protected static final String INITIALIZE_VIEW = "${initializeView}";

    protected static final String UPDATE_VIEW = "${updateView}";

    protected static final String FIELD_NAME = "${fieldName}";

    protected static final String VIEW_RES_ID = "${viewResId}";

    protected static final String OBJ_METHOD_NAME = "${objectMethodName}";

    protected String packageName;

    protected String className;

    protected String objectType;

    protected ArrayList<ViewHolderField> fields = new ArrayList<ViewHolderField>();

    protected int layoutId;



    public ViewHolderCreator() {

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void addField(ViewHolderFieldType type, int viewResourceId, String objectMethodName) {
        fields.add(new ViewHolderField(type, viewResourceId, objectMethodName));
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }


    /**
     * Generates fields part of the template.
     *
     * @return
     */
    protected String generateFields() {
        StringBuilder builder = new StringBuilder();

        for(ViewHolderField field : fields) {
            switch(field.getType()) {
                case TEXT:
                    builder.append(TEXT_FIELD_TEMPLATE.replace(FIELD_NAME, field.getObjectMethodName()));
                    break;
                case IMAGE:
                    builder.append(IMAGE_FIELD_TEMPLATE.replace(FIELD_NAME, field.getObjectMethodName()));
                    break;
            }
        }

        return builder.toString();
    }

    /**
     * Generates views init part of the template.
     *
     * @return
     */
    protected String generateFieldsInit() {
        StringBuilder builder = new StringBuilder();

        for(ViewHolderField field : fields) {
            switch(field.getType()) {
                case TEXT:
                    String fieldInit = TEXT_FIELD_INIT_TEMPLATE
                            .replace(FIELD_NAME, field.getObjectMethodName())
                            .replace(VIEW_RES_ID, String.valueOf(field.getViewResourceId()));

                    builder.append(fieldInit);
                    break;
                case IMAGE:
                    String imageFieldInit = IMAGE_FIELD_INIT_TEMPLATE
                            .replace(FIELD_NAME, field.getObjectMethodName())
                            .replace(VIEW_RES_ID, String.valueOf(field.getViewResourceId()));

                    builder.append(imageFieldInit);
                    break;
            }
        }

        return builder.toString();
    }

    protected String generateViewUpdate() {
        StringBuilder builder = new StringBuilder();

        for(ViewHolderField field : fields) {
            switch(field.getType()) {
                case TEXT:
                    String fieldInit = TEXT_FIELD_UPDATE_TEMPLATE
                            .replace(FIELD_NAME, field.getObjectMethodName())
                            .replace(OBJ_METHOD_NAME, field.getObjectMethodName());

                    builder.append(fieldInit);
                    break;
                case IMAGE:
                    String imageFieldInit = IMAGE_FIELD_UPDATE_TEMPLATE
                            .replace(FIELD_NAME, field.getObjectMethodName())
                            .replace(OBJ_METHOD_NAME, field.getObjectMethodName());

                    builder.append(imageFieldInit);
                    break;
            }
        }

        return builder.toString();
    }

    public String createViewHolderImplementation() {
        String template = Templates.getInstance().read(VIEW_HOLDER_TEMPLATE_PATH);

        template = template.replace(PACKAGE_NAME, packageName);
        template = template.replace(CLASS_NAME, className);
        template = template.replace(OBJECT_TYPE, objectType);
        template = template.replace(LAYOUT_ID, String.valueOf(layoutId));

        template = template.replace(FIELDS, generateFields());
        template = template.replace(INITIALIZE_VIEW, generateFieldsInit());
        template = template.replace(UPDATE_VIEW, generateViewUpdate());

        return template;
    }
}
