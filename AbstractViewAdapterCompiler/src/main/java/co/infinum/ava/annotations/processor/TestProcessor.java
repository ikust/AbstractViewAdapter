package co.infinum.ava.annotations.processor;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
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

import co.infinum.ava.annotations.Test;

/**
 * Test annotation processor, actually a hello world annotation processor.
 *
 * Created by ivan on 04/01/14.
 */
@SupportedAnnotationTypes("co.infinum.ava.annotations.Test")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TestProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

         //try {
         //   JavaFileObject fileOne = filer.createClassFile("co/infinum/ava.FirstTest");

         //   Writer writer = fileOne.openWriter();
         //   writer.write("public class FirstTest {}");
         //   writer.flush();
         //   writer.close();

        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        for(Element element : roundEnv.getElementsAnnotatedWith(Test.class)) {
            Annotation annotation = element.getAnnotation(Test.class);

            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Processing annotation " + annotation.toString() + " for: " + element.getSimpleName() + "(" + element.getKind() + ")");

            try {
                JavaFileObject fileTwo = filer.createSourceFile("co.infinum.ava.SecondTest");
                Writer writer = fileTwo.openWriter();
                writer.write("package co.infinum.ava; public class SecondTest {}");
                writer.flush();
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
