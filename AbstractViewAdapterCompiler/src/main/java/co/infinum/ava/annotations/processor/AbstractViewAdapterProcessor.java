package co.infinum.ava.annotations.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import co.infinum.ava.annotations.InjectList;
import co.infinum.ava.annotations.ListLayout;
import co.infinum.ava.annotations.ListView;
import co.infinum.ava.annotations.processor.tools.AdapterInjectorCreator;
import co.infinum.ava.annotations.processor.tools.JavaLangUtils;
import co.infinum.ava.annotations.processor.tools.ViewHolderCreator;
import co.infinum.ava.annotations.processor.tools.ViewHolderFieldType;

/**
 * Created by ivan on 06/01/14.
 *
 * TODO doesn't work for interface types
 * TODO support for onItemClick listener
 */
@SupportedAnnotationTypes({"co.infinum.ava.annotations.InjectList", "co.infinum.ava.annotations.ListLayout", "co.infinum.ava.annotations.ListView"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AbstractViewAdapterProcessor extends AbstractProcessor {

    protected static final String CLASS_NAME_SUFIX = "$$ViewHolder";

    protected static final String INJECTOR_CLASS_NAME_SUFIX = "$$AdapterInjector";

    protected static final String STRING_TYPE = "java.lang.String";

    protected static final String BITMAP_TYPE = "android.graphics.Bitmap";

    protected static final String ACTIVITY_TYPE = "android.app.Activity";

    /**
     * Used to generate source files.
     */
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, ViewHolderCreator> adapterMap = findAndParseTargets(roundEnv);
        Map<String, AdapterInjectorCreator> adapterInjectorMap = findAndParseInjectors(roundEnv);

        generateAdapterSourceFiles(adapterMap);
        generateAdapterInjectorSourceFiles(adapterInjectorMap);
        return true;
    }

    private Map<String, ViewHolderCreator> findAndParseTargets(RoundEnvironment env) {
        Map<String, ViewHolderCreator> adapterMap = new HashMap<String, ViewHolderCreator>();

        for (Element element : env.getElementsAnnotatedWith(ListLayout.class)) {
            TypeElement type = (TypeElement) element;


            String simpleName = type.getSimpleName().toString();
            String objectType = type.getQualifiedName().toString();
            String className = type.getQualifiedName().toString() + CLASS_NAME_SUFIX;
            String packageName = objectType.substring(0, objectType.length() - simpleName.length() - 1);

            ListLayout annotation = element.getAnnotation(ListLayout.class);

            int layoutResId = annotation.value();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ClassName: " + className);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "PackageName: " + packageName);

            ViewHolderCreator creator = new ViewHolderCreator();
            creator.setPackageName(packageName);
            creator.setClassName(simpleName + CLASS_NAME_SUFIX);
            creator.setObjectType(objectType);
            creator.setLayoutId(layoutResId);

            adapterMap.put(className, creator);
        }

        for (Element element : env.getElementsAnnotatedWith(ListView.class)) {
            if (element.getKind() != ElementKind.METHOD) {
                //TODO throw exception
            }

            ExecutableElement type = (ExecutableElement) element;

            String methodName = type.getSimpleName().toString();
            String returnType = type.getReturnType().toString();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Element: " + element.getClass());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ReturnType: " + returnType);

            TypeElement parentType = (TypeElement) element.getEnclosingElement();
            String className = parentType.getQualifiedName() + CLASS_NAME_SUFIX;

            ListView annotation = element.getAnnotation(ListView.class);
            int viewResId = annotation.value();

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "MethodName: " + methodName);

            ViewHolderCreator creator = adapterMap.get(className);

            if (creator == null) {
                //TODO throw exception
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "no class creator defined");
            }

            switch (returnType) {
                case STRING_TYPE:
                    creator.addField(ViewHolderFieldType.TEXT, viewResId, methodName);
                    break;
                case BITMAP_TYPE:
                    creator.addField(ViewHolderFieldType.IMAGE, viewResId, methodName);
                    break;
                default:
                    //TODO throw exception, unsupported return type
            }

        }

        return adapterMap;
    }

    private Map<String, AdapterInjectorCreator> findAndParseInjectors(RoundEnvironment env) {
        Map<String, AdapterInjectorCreator> adapterInjectorMap = new HashMap<>();

        for (Element element : env.getElementsAnnotatedWith(InjectList.class)) {

            if (element.getKind() != ElementKind.FIELD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Element '" + element.toString() + "' is not a FIELD.");
            }

            if (element.getModifiers().contains(Modifier.PROTECTED) ||
                    element.getModifiers().contains(Modifier.PRIVATE)) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Element '" + element + "' is declared PROTECTED or PRIVATE. Fields annotated with @InjectView can't be declared PROTECTED or PRIVATE");
            }

            Element parentClass = element.getEnclosingElement();

            String parentClassName = parentClass.toString(); //parent class of the field
            String injectorClassName = parentClassName + INJECTOR_CLASS_NAME_SUFIX;


            if (!adapterInjectorMap.containsKey(injectorClassName)) {
                String injectorClassSimpleName = parentClass.getSimpleName().toString() + INJECTOR_CLASS_NAME_SUFIX;

                AdapterInjectorCreator injectorCreator = new AdapterInjectorCreator();
                injectorCreator.setPackageName(JavaLangUtils.getPackage(parentClass));
                injectorCreator.setClassName(injectorClassSimpleName);
                injectorCreator.setAdapterClassName(parentClassName);
                injectorCreator.setInjectingIntoActivity(JavaLangUtils.checkIfExtends(parentClass, ACTIVITY_TYPE));
                adapterInjectorMap.put(injectorClassName, injectorCreator);
            }

            String adapterFieldName = element.toString();
            String objectType = JavaLangUtils.getGenericType(element);

            InjectList annotation = element.getAnnotation(InjectList.class);
            int viewResId = annotation.value();

            AdapterInjectorCreator injectorCreator = adapterInjectorMap.get(injectorClassName);
            injectorCreator.addInjection(adapterFieldName, objectType + CLASS_NAME_SUFIX, objectType, viewResId);
        }

        return adapterInjectorMap;
    }

    private void parseListLayout(Element element, Map<String, ViewHolderCreator> adapterMap) {

    }

    private void parseInjectAdapter(RoundEnvironment env) {

    }

    private void generateAdapterSourceFiles(Map<String, ViewHolderCreator> adapterMap) {
        for (String className : adapterMap.keySet()) {
            try {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing class: " + className);

                JavaFileObject adapterSource = filer.createSourceFile(className);

                Writer writer = adapterSource.openWriter();
                writer.write(adapterMap.get(className).createViewHolderImplementation());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateAdapterInjectorSourceFiles(Map<String, AdapterInjectorCreator> adapterInjectorMap) {
        for (String className : adapterInjectorMap.keySet()) {
            try {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing injector: " + className);

                JavaFileObject adapterInjectorSource = filer.createSourceFile(className);

                Writer writer = adapterInjectorSource.openWriter();
                writer.write(adapterInjectorMap.get(className).createInjectAdapterImplementation());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                ;
            }
        }

    }
}
