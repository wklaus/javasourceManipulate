package de.wklsoft.javamodification.worker;

import com.github.javaparser.ast.body.MethodDeclaration;
import de.wklsoft.javamodification.SourceMethodManipulator;

/**
 * Created by wkl on 16.04.16.
 */
public class ChangeMethodeName extends SourceMethodManipulator {


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
