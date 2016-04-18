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
 * Iterate over all Files an manipulate it with the configured SourceMethodManipulator
 * Created by wkl on 16.04.16.
 */
public class Parser {
    private boolean dryRun;
    private String sourcePath = "src/test/resources/";
    private String[] extension = {"java"};
    private String destPath = "target/";
    private List<SourceManipulator> configList = new ArrayList<>();
    Predicate<ClassOrInterfaceDeclaration> klassenFilter;

    public Parser(String[] args,Predicate<ClassOrInterfaceDeclaration> klassenFilter){
        this.klassenFilter = klassenFilter;

        for(int i=0;i<args.length;i++) {
            if("-d".equals(args[i])){
                dryRun();
            }
            if("-p".equals(args[i])){
                setSourcePath(args[i + 1]);
            }
            if("-t".equals(args[i])){
                setDestPath(args[i+1]);
            }

        }
    }

    public void run() throws Exception {
        int anzahl = 0;
        Collection<File> files = FileUtils.listFiles(new File(sourcePath), extension, true);
        System.out.println(files.size()+" Files found");
        for (File file : files) {
            if(file.getPath().indexOf("target")>-1){
                continue;
            }
            FileInputStream in = new FileInputStream(file);

            CompilationUnit cu;
            try {
                // parse the file
                cu = JavaParser.parse(in);
            } finally {
                in.close();
            }
            boolean changed = false;
            for(SourceManipulator config : configList){
                changed = verarbeite(cu, config) || changed;
            }
            if (changed) {
                anzahl++;
                if(!dryRun){
                    File destFile=file;
                    if(destPath!=null){
                        destFile = new File(destPath+file.getName());
                    }
                    FileUtils.writeStringToFile(destFile, cu.toString(), "UTF-8");
                }
                System.out.println(cu);
            }

        }
        System.out.println(anzahl + " Files changed!");
    }

    private boolean verarbeite(CompilationUnit cu, SourceManipulator config) {
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

    /**
     * Path where changed File is written.
     * @param destPath  if null the source File would be overwritten
     */
    public void setDestPath(String destPath){
        this.destPath = destPath;
    }

    public void addConfig(SourceManipulator config){
        configList.add(config);
    }

    public  Predicate<ClassOrInterfaceDeclaration> getKlassenFilter(){
        return klassenFilter;
    }

    public void setKlassenFilter(Predicate<ClassOrInterfaceDeclaration> klassenFilter) {
        this.klassenFilter = klassenFilter;
    }
}
