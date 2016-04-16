package de.wklsoft.javamodification;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wkl on 16.04.16.
 */
public class Parser {
    public static void main(String[] args) throws Exception {
        int anzahl=0;
        Parser parser = new Parser();
        File f=new File("/Users/wkl/Dev/steilmann/javaparser/src/test/resources/BetriebHelper.java");

        FileInputStream in = new FileInputStream(f);

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
        ChangeParameterLongToSerializable config = new ChangeParameterLongToSerializable();

        boolean changed = parser.verarbeite(cu, config);
        if (changed) {
            anzahl++;
            FileUtils.writeStringToFile(new File("target/"+f.getName()),cu.toString(),"UTF-8");
            System.out.println(cu);
        }
        System.out.println(anzahl +" Files changed!");
    }

    private boolean verarbeite(CompilationUnit cu, AbstractParserConfig config) {
        boolean changed = false;
        //Nur bestimmte Klassen
        boolean klasseBearbeiten = cu.getTypes().stream()
                .filter(t -> t instanceof ClassOrInterfaceDeclaration)
                .map(t -> (ClassOrInterfaceDeclaration) t)
                .filter(config.getKlassenFilter()).count()>0;
        if (klasseBearbeiten) {
            changed = config.aendere(cu);
        }

        return changed;

    }

}
