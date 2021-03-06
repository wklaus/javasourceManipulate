package de.wklsoft.javamodification;


import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import de.wklsoft.javamodification.worker.AddGeneric;
import de.wklsoft.javamodification.worker.ChangeMethodeName;
import de.wklsoft.javamodification.worker.ChangeParameterLongToSerializable;
import de.wklsoft.javamodification.worker.ChangeParameterSerializableToLong;
import de.wklsoft.javamodification.worker.RemoveMethod;

import java.util.function.Predicate;

/**
 * Created by wkl on 16.04.16.
 */
public class StartParser {
    public static void main(String[] args) throws Exception {

        Parser parser = new Parser(args,new Predicate<ClassOrInterfaceDeclaration>() {
            //Which classes to manipulate
            @Override
            public boolean test(ClassOrInterfaceDeclaration clazz) {
                return clazz.getName().endsWith("Helper") && ParserHelper.checkExtends(clazz, "Abstract" + clazz.getName());
            }
        });

        for(int i=0;i<args.length;i++) {
            if("-d".equals(args[i])){
                parser.dryRun();
            }
            if("-p".equals(args[i])){
                parser.setSourcePath(args[i + 1]);
            }
            if("-t".equals(args[i])){
                parser.setDestPath(args[i+1]);
            }

        }

       // parser.addConfig(new ChangeParameterLongToSerializable(null));
       // parser.addConfig(new ChangeMethodeName("methodeC","methodeZ"));
       // parser.addConfig(new RemoveMethod("deleteMe"));
        parser.addConfig(new ChangeParameterSerializableToLong(null));
        parser.addConfig(new AddGeneric("Long"));
        parser.run();
    }

}
