package de.wklsoft.javamodification.worker;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import de.wklsoft.javamodification.SourceMethodManipulator;

/**
 * Created by wkl on 16.04.16.
 */
public class ChangeMethodeName extends SourceMethodManipulator {

    private final String oldName;
    private final String newName;

    public ChangeMethodeName(String oldName, String newName){
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public boolean changeMethod(MethodDeclaration method) {
        boolean changed = false;
        if(!ModifierSet.isPublic(method.getModifiers())){
            return changed;
        }
        if(method.getName().equalsIgnoreCase(oldName)){
            method.setName(newName);
            changed = true;
        }

        return changed;
    }
}
