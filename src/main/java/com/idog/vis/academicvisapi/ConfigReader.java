/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author idog
 */
public class ConfigReader {
    
    private Properties props;
    
    private ConfigReader() {
    }
    
    public String getMongoHost() {
        String host = props.getProperty("mongo.hostname");
        
        return host;
    }
    
    public Integer getMongoPort() {
        String port = props.getProperty("mongo.port");
        
        Integer portAsInt;
        try {
            portAsInt = Integer.valueOf(port);
        } catch (Exception ex) {
            portAsInt = 0;
        }         
        
        return portAsInt;
    }

    public Properties getProps() {
        return props;
    }   
    
    
    public static class ConfigReaderBuilder {

        private InputStream getCfgStreamFromFile(File file) throws FileNotFoundException {        
            InputStream stream = new FileInputStream(file);
            return stream;
        }

        private InputStream getCfgStreamFromFile(String fileContent)  {
            InputStream stream = new ByteArrayInputStream( fileContent.getBytes());
            return stream;
        }            
        
        private Properties read(InputStream stream) throws FileNotFoundException, IOException{
            Properties props = new Properties();
            props.loadFromXML(stream);    
            
            return props;
        }        
        
        public ConfigReader buildDefault() throws IOException {
            String workingDir = System.getProperty("user.dir");
            
            Path get = Paths.get("target");
            java.nio.file.Path resourcesFilePath = Paths.get(workingDir, "src\\main\\resources", "config.properties");            
            
            return buildFromFile(resourcesFilePath);
        }        
        
        public ConfigReader buildFromFile(java.nio.file.Path fileLocation) throws IOException {
            ConfigReader cfgReader = new ConfigReader();
            File readFile = fileLocation.toFile();
            InputStream cfgStreamFromFile = getCfgStreamFromFile(readFile);
            Properties p = read(cfgStreamFromFile);
            cfgReader.props = p;
            
            return cfgReader;
        }
        
        public ConfigReader buildFromString(String fileContent) throws IOException {
            ConfigReader cfgReader = new ConfigReader();
            InputStream cfgStreamFromFile = getCfgStreamFromFile(fileContent);
            Properties p = read(cfgStreamFromFile);
            cfgReader.props = p;
            
            return cfgReader;
        }  
    }
}
