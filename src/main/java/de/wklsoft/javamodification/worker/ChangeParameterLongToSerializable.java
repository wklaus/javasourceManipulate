package de.wklsoft.javamodification.worker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import de.wklsoft.javamodification.SourceMethodManipulator;
import de.wklsoft.javamodification.ParserHelper;

import java.util.List;

/**
 * Define which classes to manipulate.
 * Created by wkl on 16.04.16.
 */
public class ChangeParameterLongToSerializable extends SourceMethodManipulator {



    public boolean modifyImport(CompilationUnit cu){
        return ParserHelper.addImportIfMissing(cu, "java.io.Serializable");
    }

    /**
     * Change the Parameters of the Methods from Long to Serializable
     * @param method
     * @return
     */
    public  boolean changeMethod(MethodDeclaration method) {
        boolean changed = false;
        List<Parameter> parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            Type type = parameter.getType();
            if(type instanceof ReferenceType){
                if (((ClassOrInterfaceType) ((ReferenceType) type).getType()).getName().equals("Long")) {
                    parameter.setType(new ClassOrInterfaceType("Serializable"));
                    changed = true;
                }
            }

        }
        return changed;
    }


}
