package application;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class FileGenerator {

    public static void generateFile(String name) throws IOException {
        //Create the velocity engine
        VelocityEngine velocityEngine = new VelocityEngine();
        //set a couple of properties to be able to load resources
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        //initialize the engine
        velocityEngine.init();
        //get the template file
        Template template = velocityEngine.getTemplate("entity/Entity.vm");
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("name", name);
        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);

        Path originalPath = Paths.get("generated/"+name+".java");
        Path directoryPath = Paths.get("generated");
        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
        FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions
                .asFileAttribute(permissions);
        boolean isFileDeleted = Files.deleteIfExists(originalPath);
        Files.deleteIfExists(directoryPath);
        Files.createDirectory(directoryPath, fileAttributes);

        System.out.println("isFileDeleted: "+isFileDeleted);

        Files.createFile(originalPath, fileAttributes);
        Files.write(originalPath, stringWriter.toString().getBytes());
        System.out.println(stringWriter.toString());
    }
}