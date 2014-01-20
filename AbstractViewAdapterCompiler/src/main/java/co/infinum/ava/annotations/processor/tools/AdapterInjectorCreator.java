package co.infinum.ava.annotations.processor.tools;

import java.util.ArrayList;

/**
 * This class is used to create helper class that will contain a method for "injecting" generated
 * ViewHolder implementations to fields in activities.
 *
 * TODO: dodat podršku i za fragmente - na isti način kao butterknife
 *
 * Created by ivan on 13/01/14.
 */
public class AdapterInjectorCreator {

    protected static final String ADAPTER_INJECTOR_TEMPLATE_PATH = "/co/infinum/ava/templates/AdapterInjectorTemplate.tpl";

    protected static final String INJECTION_TEMPLATE = "\t\t${fieldName} = new AbstractViewAdapter(activity, ${viewHolderName}.FACTORY, new ArrayList<${objectType}>());\n";

    protected static final String PACKAGE_NAME = "${packageName}";

    protected static final String CLASS_NAME = "${className}";

    protected static final String INJECTION_CODE = "${injectionCode}";

    protected static final String FIELD_NAME = "${fieldName}";

    protected static final String VIEW_HOLDER_NAME = "${viewHolderName}";

    protected static final String OBJECT_TYPE = "${objectType}";



    /**
     * Package for the generated injector class.
     */
    protected String packageName;

    /**
     * Name of the class that contains adapter field that will be injected with generated adapter.
     * It is important to note that this is not the name of the injector class (but the name of the
     * "host")
     */
    protected String className;


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

    public void addInjection(String fieldName, String viewHolderName, String objectType) {
        injections.add(new AdapterInjection(fieldName, viewHolderName, objectType));
    }

    protected String generateInjections() {
        StringBuilder builder = new StringBuilder();

        for(AdapterInjection injection : injections) {
            String injectionCode = INJECTION_TEMPLATE
                    .replace(FIELD_NAME, injection.getFieldName())
                    .replace(VIEW_HOLDER_NAME, injection.getViewHolderName())
                    .replace(OBJECT_TYPE, injection.getObjectType());

            builder.append(injectionCode);
        }

        return builder.toString();
    }

    public String createInjectAdapterImplementation() {
        String template = Templates.getInstance().read(ADAPTER_INJECTOR_TEMPLATE_PATH);

        template = template.replace(PACKAGE_NAME, packageName);
        template = template.replace(CLASS_NAME, className);

        template = template.replace(INJECTION_CODE, generateInjections());

        return template;
    }

}
