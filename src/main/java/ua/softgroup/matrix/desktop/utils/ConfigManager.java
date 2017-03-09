package ua.softgroup.matrix.desktop.utils;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * @author Vadim Boitsov <sg.vadimbojcov@gmail.com>
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static byte defaultConfig[] = "host = 127.0.0.1\nport = 6666".getBytes();
    private static Path path = Paths.get("config.properties");

    /**
     * Method reads host and port values from config file and sets it to SocketProvider.
     * In case of an error, sets it to default.
     */
    public static void readConfig() {
        try {
            PropertiesConfiguration config = getConfig();
            SocketProvider.setHostName(config.getString("host"));
            SocketProvider.setPortNumber(Integer.parseInt(config.getString("port")));
            logger.debug("Server IP: {}:{}", SocketProvider.getHostName(), SocketProvider.getPortNumber());
        } catch (ConfigurationException | IOException e) {
            logger.debug("Config is not found. Try to set to default.");
            setConfigToDefault();
        }
    }

    /**
     * Method returns a {@link PropertiesConfiguration} object with current configs
     * @return configs
     * @throws IOException
     * @throws ConfigurationException
     */
    private static PropertiesConfiguration getConfig() throws IOException, ConfigurationException {
        checkConfigFileExistence();
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration("config.properties");
        propertiesConfiguration.setAutoSave(true);
        propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
        return propertiesConfiguration;
    }

    /**
     * Method checks existence of config file. In negative case, it creates new config file with default values.
     * @throws IOException
     */
    private static void checkConfigFileExistence() throws IOException {
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE_NEW))) {
            setLocalHost();
            logger.debug("New config file is created.");
        } catch (IOException | ConfigurationException x) {
            logger.debug("Config file is already exist");
        }
    }

    /**
     * Method tries to set config to default values. In case of an exception, it will do nothing.
     */
    public static void setConfigToDefault() {
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE))) {
            setLocalHost();
            logger.debug("Config file was set to default");
        } catch (IOException | ConfigurationException x) {
            logger.debug("Something went wrong with setting default values");
        }
    }

    private static void setLocalHost() throws IOException, ConfigurationException {
        getConfig().setProperty("host","127.0.0.1");
        getConfig().setProperty("port","6666");
    }

    /**
     * Method read and returns current host value from config.
     * @return host's value or empty string in case of exception.
     */
    public static String getHost(){
        try {
            return getConfig().getString("host");
        } catch (IOException | ConfigurationException e) {
            logger.debug("Something went wrong with config. Host not found", e);
        }
        return "";
    }

    /**
     * Method reads and returns current port value from config.
     * @return port's value or empty string in case of exception.
     */
    public static String getPort() {
        try {
            return getConfig().getString("port");
        } catch (IOException | ConfigurationException e) {
            logger.debug("Something went wrong with config. Port not found", e);
        }
        return "";
    }

    /**
     * Method tries to save new config properties.
     * @param host a String object with new host value
     * @param port a String object with new port value
     */
    public static void saveNewConfig(String host, String port) {
        logger.debug("New configs: host = {}; port = {}", host, port);
        try {
            setConfigToDefault();
            PropertiesConfiguration config = getConfig();
            config.setProperty("host", host);
            config.setProperty("port", port);
            logger.debug("New config was saved");
        } catch (IOException | ConfigurationException e) {
            logger.debug("New config wasn't saved", e);
        }
    }
}