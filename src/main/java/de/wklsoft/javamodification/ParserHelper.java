package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.List;

/**
 * Some Helper-Methods
 * Created by wkl on 16.04.16.
 */

public class ParserHelper {

    public static  boolean checkExtends(ClassOrInterfaceDeclaration type, String name) {
        boolean toChange = false;
        List<ClassOrInterfaceType> anExtends = ((ClassOrInterfaceDeclaration) type).getExtends();
        for (ClassOrInterfaceType anExtend : anExtends) {
            if (anExtend.getName().equals(name)) {
                toChange = true;
                break;
            }
        }
        return toChange;
    }

    public static boolean addImportIfMissing(CompilationUnit cu, String clzImport){
        boolean changed = false;
        ImportDeclaration importDeclaration = new ImportDeclaration(new NameExpr(clzImport),false,false);
        if(!cu.getImports().contains(importDeclaration)){
            cu.getImports().add(importDeclaration);
            changed = true;
        }
        return changed;
    }
}
