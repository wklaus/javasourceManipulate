package de.wklsoft.javamodification;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Iterate over all Files an manipulate it with the configured AbstractMethodManipulator
 * Created by wkl on 16.04.16.
 */
public class Parser {
    private boolean dryRun;
    private String sourcePath = "src/test/resources/";
    private String[] extension = {"java"};
    private String destPath = "target/";
    private List<AbstractMethodManipulator> configList = new ArrayList<>();
    Predicate<ClassOrInterfaceDeclaration> klassenFilter;

    public Parser(Predicate<ClassOrInterfaceDeclaration> klassenFilter){
        this.klassenFilter = klassenFilter;
    }

    public void run() throws Exception {
        int anzahl = 0;
        Collection<File> files = FileUtils.listFiles(new File(sourcePath), extension, true);
        for (File file : files) {
            FileInputStream in = new FileInputStream(file);


            CompilationUnit cu;
            try {
                // parse the file
                cu = JavaParser.parse(in);
            } finally {
                in.close();
            }
            boolean changed = false;
            for(AbstractMethodManipulator config : configList){
                changed = verarbeite(cu, config) || changed;
            }
            if (changed) {
                anzahl++;
                if(!dryRun){
                    FileUtils.writeStringToFile(new File(destPath + file.getName()), cu.toString(), "UTF-8");
                }
                System.out.println(cu);
            }

        }
        System.out.println(anzahl + " Files changed!");
    }

    private boolean verarbeite(CompilationUnit cu, AbstractMethodManipulator config) {
        boolean changed = false;
        //check if class is to change
        boolean klasseBearbeiten = cu.getTypes().stream()
                .filter(t -> t instanceof ClassOrInterfaceDeclaration)
                .map(t -> (ClassOrInterfaceDeclaration) t)
                .filter(getKlassenFilter()).count() > 0;
        if (klasseBearbeiten) {
            changed = config.change(cu);
        }

        return changed;

    }

    public void dryRun() {
        this.dryRun = true;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public void setDestPath(String destPath){
        this.destPath = destPath;
    }

    public void addConfig(AbstractMethodManipulator config){
        configList.add(config);
    }

    public  Predicate<ClassOrInterfaceDeclaration> getKlassenFilter(){
        return klassenFilter;
    }

}
