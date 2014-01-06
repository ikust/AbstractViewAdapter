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
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import co.infinum.ava.annotations.ListLayout;
import co.infinum.ava.annotations.processor.tools.ViewHolderCreator;

/**
 * Created by ivan on 06/01/14.
 */
@SupportedAnnotationTypes({"co.infinum.ava.annotations.InjectAdapter", "co.infinum.ava.annotations.ListLayout", "co.infinum.ava.annotations.ListView"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AbstractViewAdapterProcessor extends AbstractProcessor {

    protected static final String CLASS_NAME_SUFIX = "$ViewHolder";

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


        generateAdapterSourceFiles(adapterMap);

        return true;
    }

    private Map<String, ViewHolderCreator> findAndParseTargets(RoundEnvironment env) {
        Map<String, ViewHolderCreator> adapterMap = new HashMap<String, ViewHolderCreator>();

        for(Element element : env.getElementsAnnotatedWith(ListLayout.class)) {
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

        return adapterMap;
    }

    private void parseListLayout(Element element, Map<String, ViewHolderCreator> adapterMap) {

    }

    private void parseInjectAdapter() {

    }

    private void generateAdapterSourceFiles(Map<String, ViewHolderCreator> adapterMap) {
        for(String className : adapterMap.keySet()) {
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
}
