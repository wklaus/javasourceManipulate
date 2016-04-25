package de.wklsoft.javamodification.worker;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import de.wklsoft.javamodification.ParserHelper;
import de.wklsoft.javamodification.SourceMethodManipulator;

/**
 * Define which classes to manipulate.
 * Created by wkl on 16.04.16.
 */
public class ChangeParameterSerializableToLong extends SourceMethodManipulator {


    private final String methodName;

    /**
     *
     * @param methodName the name of the Method to change. If null every Methods with a Parameter of Type Long is changed
     */
    public ChangeParameterSerializableToLong(String methodName){
        this.methodName = methodName;
    }


    public boolean modifyImport(CompilationUnit cu){
        return ParserHelper.removeImport(cu, "Serializable");
    }

    /**
     * Change the Parameters of the Methods from Long to Serializable
     * @param method
     * @return
     */
    public  boolean changeMethod(MethodDeclaration method) {
        boolean changed = false;
        List<Parameter> parameters = method.getParameters();
        if(methodName!=null && ! method.getName().equalsIgnoreCase(methodName)){
            return changed;
        }

        if(!ModifierSet.isPublic(method.getModifiers())){
            return changed;
        }

        for (Parameter parameter : parameters) {
            Type type = parameter.getType();
            if(type instanceof ReferenceType){
                if (((ClassOrInterfaceType) ((ReferenceType) type).getType()).getName().equals("Serializable")) {
                    parameter.setType(new ClassOrInterfaceType("Long"));
                    changed = true;
                }
            }

        }
        return changed;
    }


}
