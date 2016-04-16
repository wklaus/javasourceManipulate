package de.wklsoft.javamodification.worker;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import de.wklsoft.javamodification.SourceManipulator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wkl on 16.04.16.
 */
public class RemoveMethod implements SourceManipulator {
    @Override
    public boolean change(CompilationUnit cu) {
        boolean changed = false;
        List<TypeDeclaration> types = cu.getTypes();

        for (TypeDeclaration type : types) {

            List<MethodDeclaration> methoden = type.getMembers().stream().filter(member -> member instanceof MethodDeclaration).map(member -> (MethodDeclaration) member).
                    collect(Collectors.toList());
            for (MethodDeclaration methodDeclaration : methoden) {
                if(methodDeclaration.getName().equals("deleteMe")){
                    type.getMembers().remove(methodDeclaration);
                    changed = true;
                }
            }
        }
        return changed;
    }
}
