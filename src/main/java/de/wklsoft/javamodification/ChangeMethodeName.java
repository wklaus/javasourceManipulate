package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by wkl on 16.04.16.
 */
public class ChangeMethodeName extends AbstractParserConfig {


    @Override
    public boolean changeMethod(MethodDeclaration method) {
        boolean changed = false;
        if(method.getName().endsWith("C")){
            method.setName("methodeZ");
            changed = true;
        }

        return changed;
    }
}
