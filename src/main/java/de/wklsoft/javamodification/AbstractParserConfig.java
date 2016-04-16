package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.function.Predicate;

/**
 * Created by wkl on 16.04.16.
 */
public abstract class AbstractParserConfig {

    Predicate<ClassOrInterfaceDeclaration> klassenFilter;

    public  Predicate<ClassOrInterfaceDeclaration> getKlassenFilter(){
        return klassenFilter;
    }

    /**
     * Führt die eigentliche Änderung durch;
     * @param cu
     * @return
     */
    public abstract boolean aendere(CompilationUnit cu);

}
