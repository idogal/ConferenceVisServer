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
import java.util.Properties;

/**
 *
 * @author idog
 */
public class ConfigReader {
    
    private Properties props;
    
    private ConfigReader() {
    }
    
    public String getBaseAddress() {
        String address = props.getProperty("Planc API address");
        
        return address;
    }
    
    public String getCampaignsLocation() {
        String location = props.getProperty("Campaigns Location");
        
        return location;
    }

    public Properties getProps() {
        return props;
    }   
    
    
    public static class ConfigReaderBuilder {
        
        private File readFile(String fileLocation) {
            return new File(fileLocation);
        }

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
            return buildFromFile("/home/idog/NetBeansProjects/EmailServices/ImpMailingMicroservice/ImpMailingService/config.properties");
        }        
        
        public ConfigReader buildFromFile(String fileLocation) throws IOException {
            ConfigReader cfgReader = new ConfigReader();
            File readFile = readFile(fileLocation);
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
