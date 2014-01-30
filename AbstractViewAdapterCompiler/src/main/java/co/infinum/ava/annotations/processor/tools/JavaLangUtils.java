package co.infinum.ava.annotations.processor.tools;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;

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

        if (declaredType.getTypeArguments().size() > 0) {
            return declaredType.getTypeArguments().get(0).toString();
        } else {
            return null;
        }
    }

    /**
     * Check if the given Element extends a given type. A fully qualified name of another type
     * to check for must be given. Throws an IllegalArgumentException if Element is not an instance of
     * TypeElement. The method will recursively climb up the type hierarchy until it reaches either
     * the desired type or a root type.
     *
     * @param element   element to perform the check for
     * @param superType fully qualified name of a type
     * @return true if given element represents a type that extends another type with the given fully
     * qualified name
     * @throws java.lang.IllegalArgumentException if element is not an instance of TypeElement
     */
    public static boolean checkIfExtends(Element element, String superType) {
        if (!(element instanceof TypeElement)) {
            throw new IllegalArgumentException("Can only check if TypeElement extends a certain superType");
        }

        TypeElement typeElement = (TypeElement) element;

        if (typeElement.getSuperclass() instanceof NoType) {
            return false;
        } else if (((DeclaredType) typeElement.getSuperclass()).asElement().toString().equals(superType)) {
            return true;
        } else {
            return checkIfExtends(((DeclaredType) typeElement.getSuperclass()).asElement(), superType);
        }
    }
}
