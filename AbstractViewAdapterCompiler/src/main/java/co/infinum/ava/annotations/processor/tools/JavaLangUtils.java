package co.infinum.ava.annotations.processor.tools;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

/**
 * Created by ivan on 22/01/14.
 */
public class JavaLangUtils {

    /**
     * Returns the package name for the given Element.
     * Assumes that the Element represents class.
     *
     * @param element element to get the package for
     * @return package name for the given element
     */
    public static String getPackage(Element element) {
        String simpleName = element.getSimpleName().toString();
        String fullyQualifiedName = element.toString();

        return fullyQualifiedName.substring(0, fullyQualifiedName.length() - simpleName.length() - 1);
    }

    /**
     * Returns first generic type parameter or null if the given
     * Element doesn't have any.
     * Assumes that the Element represents class field.
     *
     * @param element element to get the generic type for
     * @return fully qualified name of the type that is passed as generic type parameter
     */
    public static String getGenericType(Element element) {
        VariableElement variableElement = (VariableElement) element;

        DeclaredType declaredType = (DeclaredType) variableElement.asType();

        if(declaredType.getTypeArguments().size() > 0) {
            return declaredType.getTypeArguments().get(0).toString();
        } else {
            return null;
        }
    }
}
