package de.wklsoft.javamodification.worker;

import java.util.Arrays;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.TypeArguments;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import de.wklsoft.javamodification.SourceManipulator;

/**
 * Created by wkl on 16.04.16.
 */
public class AddGeneric implements SourceManipulator {
    private String name ;

    public AddGeneric(String name){
        this.name=name;
    }
    @Override
    public boolean change(CompilationUnit cu) {
        boolean changed = false;
        ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType) ((ClassOrInterfaceDeclaration) cu.getTypes().get(0)).getExtends().get(0);
        classOrInterfaceType.setTypeArguments(TypeArguments.withArguments(Arrays.asList(new ClassOrInterfaceType(name))));
        return true;
    }
}
