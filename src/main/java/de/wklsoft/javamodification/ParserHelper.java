package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    public static  boolean checkExtends(ClassOrInterfaceDeclaration type, Pattern pattern) {
        boolean toChange = false;
        List<ClassOrInterfaceType> anExtends = ((ClassOrInterfaceDeclaration) type).getExtends();
        for (ClassOrInterfaceType anExtend : anExtends) {
            if (pattern.matcher(anExtend.getName()).matches()) {
                toChange = true;
                break;
            }
        }
        return toChange;
    }

    public static boolean addImportIfMissing(CompilationUnit cu, String clzImport){
        boolean changed = false;
        List<ImportDeclaration> imports = cu.getImports();
        int pos=0;
        for (int i=0;i<imports.size();i++) {
            ImportDeclaration anImport = imports.get(i);
            if(anImport.getName().getName().indexOf("java.")>-1){
                pos=i;
            }
            if(anImport.getName().getName().equalsIgnoreCase(clzImport)){
                return changed;
            }
        }
        cu.getImports().add(pos,new ImportDeclaration(new NameExpr(clzImport),false,false));
        changed = true;
        return changed;
    }

    public static boolean removeImport(CompilationUnit cu, String clzImport){
        boolean changed = false;
        List<ImportDeclaration> imports = new ArrayList<>(cu.getImports());
        ImportDeclaration toRemove = null;
        for (int i=0;i<imports.size();i++) {
            ImportDeclaration anImport = imports.get(i);
            if(anImport.getName().getName().equalsIgnoreCase(clzImport)){
                toRemove=anImport;
            }
        }
        if(toRemove!=null){
            cu.getImports().remove(toRemove);
        }
        changed = true;
        return changed;
    }
}
