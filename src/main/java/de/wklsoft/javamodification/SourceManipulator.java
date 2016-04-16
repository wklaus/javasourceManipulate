package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.util.List;

/**
 * Created by wkl on 16.04.16.
 */
public interface SourceManipulator {

    /**
     * Do the manipulation of the source
     *
     * @param cu
     * @return
     */
    public boolean change(CompilationUnit cu);
}
