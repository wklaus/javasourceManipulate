package de.wklsoft.javamodification;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.util.List;

/**
 * Created by wkl on 16.04.16.
 */
public abstract class AbstractManipulator {

    /**
     * Do the manipulation of the source
     *
     * @param cu
     * @return
     */
    public abstract boolean change(CompilationUnit cu);
}
