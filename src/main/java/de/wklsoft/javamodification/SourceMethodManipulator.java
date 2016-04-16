package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.util.List;

/**
 * Base Class for manipulation
 * Created by wkl on 16.04.16.
 */
public abstract class SourceMethodManipulator implements SourceManipulator {

    /**
     * Do the manipulation of the source
     * @param cu
     * @return
     */
    public boolean change(CompilationUnit cu) {
        final boolean[] changed = new boolean[1];
        List<TypeDeclaration> types = cu.getTypes();


        for (TypeDeclaration type : types) {
            type.getMembers().stream().filter(member -> member instanceof MethodDeclaration).map(member -> (MethodDeclaration)member).
                    forEach(method -> changed[0] = changeMethod(method) | changed[0]);
        }
        if(changed[0]){
            //If something is changed then add import java.io.Serializable if not already there
            modifyImport(cu);
        }
        return changed[0];
    }


    /**
     * Must be implemented to change a Method
     * @param method
     * @return
     */
    public abstract boolean changeMethod(MethodDeclaration method);

    public boolean modifyImport(CompilationUnit cu){
        return false;
    };

}
