package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by wkl on 16.04.16.
 */
public class ChangeParameterLongToSerializable extends AbstractParserConfig {

    public ChangeParameterLongToSerializable(){
        this.klassenFilter = new Predicate<ClassOrInterfaceDeclaration>() {

            @Override
            public boolean test(ClassOrInterfaceDeclaration clazz) {
                return clazz.getName().endsWith("Helper") && ParserHelper.checkExtends(clazz, "Abstract" + clazz.getName());
            }
        };
    }

    @Override
    public  boolean aendere(CompilationUnit cu) {
        final boolean[] changed = new boolean[1];
        List<TypeDeclaration> types = cu.getTypes();

        for (TypeDeclaration type : types) {
            type.getMembers().stream().filter(member -> member instanceof MethodDeclaration).map(member -> (MethodDeclaration)member).
                    forEach(method -> changed[0] = changeMethod(method) | changed[0]);
        }
        if(changed[0]){
            ParserHelper.checkImport(cu,"java.io.Serializable");
        }
        return changed[0];
    }

    private  boolean changeMethod(MethodDeclaration method) {
        boolean changed = false;
        List<Parameter> parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            Type type = parameter.getType();

            if (((ClassOrInterfaceType) ((ReferenceType) type).getType()).getName().equals("Long")) {
                parameter.setType(new ClassOrInterfaceType("Serializable"));
                changed = true;
            }
        }
        return changed;
    }


}
