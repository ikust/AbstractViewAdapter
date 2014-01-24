package co.infinum.ava.annotations.processor.tools;

import java.util.ArrayList;

/**
 * This class is used to create helper class that will contain a method for "injecting" generated
 * ViewHolder implementations to fields in activities.
 * <p/>
 * TODO: dodat podršku i za fragmente - na isti način kao butterknife
 * <p/>
 * Created by ivan on 13/01/14.
 */
public class AdapterInjectorCreator {

    protected static final String ADAPTER_INJECTOR_TEMPLATE_PATH = "/co/infinum/ava/templates/AdapterInjectorTemplate.tpl";

    protected static final String INJECTION_TEMPLATE = "\t\tactivity.${fieldName} = new AbstractViewAdapter(activity, ${viewHolderName}.FACTORY, new ArrayList<${objectType}>());\n" +
            "\t\tListView ${listViewName} = (ListView) activity.findViewById(${listViewId});\n" +
            "\t\t${listViewName}.setAdapter(activity.${fieldName});\n";

    protected static final String PACKAGE_NAME = "${packageName}";

    protected static final String CLASS_NAME = "${className}";

    protected static final String ADAPTER_CLASS_NAME = "${adapterClassName}";

    protected static final String INJECTION_CODE = "${injectionCode}";

    protected static final String FIELD_NAME = "${fieldName}";

    protected static final String VIEW_HOLDER_NAME = "${viewHolderName}";

    protected static final String OBJECT_TYPE = "${objectType}";

    protected static final String LIST_VIEW_NAME = "${listViewName}";

    protected static final String LIST_VIEW_ID = "${listViewId}";

    protected static final String LIST_VIEW_SUFIX = "ListView";


    /**
     * Package for the generated injector class.
     */
    protected String packageName;

    /**
     * Name of the injector class.
     */
    protected String className;

    /**
     * Name of the class that contains adapter field that will be injected with generated adapter.
     * It is important to note that this is not the name of the injector class (but the name of the
     * "host")
     */
    protected String adapterClassName;

    protected int listViewId;

    protected ArrayList<AdapterInjection> injections = new ArrayList<AdapterInjection>();

    public AdapterInjectorCreator() {

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

    public String getAdapterClassName() {
        return adapterClassName;
    }

    public void setAdapterClassName(String adapterClassName) {
        this.adapterClassName = adapterClassName;
    }

    public void addInjection(String fieldName, String viewHolderName, String objectType, int listViewId) {
        injections.add(new AdapterInjection(fieldName, viewHolderName, objectType, listViewId));
    }

    protected String generateInjections() {
        StringBuilder builder = new StringBuilder();

        for (AdapterInjection injection : injections) {
            String injectionCode = INJECTION_TEMPLATE
                    .replace(FIELD_NAME, injection.getFieldName())
                    .replace(VIEW_HOLDER_NAME, injection.getViewHolderName())
                    .replace(OBJECT_TYPE, injection.getObjectType())
                    .replace(LIST_VIEW_NAME, injection.getFieldName() + LIST_VIEW_SUFIX)
                    .replace(LIST_VIEW_ID, String.valueOf(injection.getListViewId()));

            builder.append(injectionCode);
        }

        return builder.toString();
    }


    public String createInjectAdapterImplementation() {
        String template = Templates.getInstance().read(ADAPTER_INJECTOR_TEMPLATE_PATH);

        template = template.replace(PACKAGE_NAME, packageName);
        template = template.replace(CLASS_NAME, className);
        template = template.replace(ADAPTER_CLASS_NAME, adapterClassName);

        template = template.replace(INJECTION_CODE, generateInjections());

        return template;
    }

}
