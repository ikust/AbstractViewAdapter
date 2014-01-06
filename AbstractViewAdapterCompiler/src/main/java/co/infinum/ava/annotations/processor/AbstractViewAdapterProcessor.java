package co.infinum.ava.annotations.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by ivan on 06/01/14.
 */
@SupportedAnnotationTypes({"co.infinum.ava.annotationss.InjectAdapter", "co.infinum.ava.ListLayout", "co.infinum.ava.ListView"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AbstractViewAdapterProcessor extends AbstractProcessor {

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



        return true;
    }

    private void parseInjectAdapter() {

    }
}
