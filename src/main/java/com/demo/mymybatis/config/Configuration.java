package com.demo.mymybatis.config;

import com.demo.mymybatis.mapper.MappedStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration 是用于存储配置信息的 Java 对象
 */
public class Configuration {
    private String driver;
    private String url;
    private String username;
    private String password;
    private List<String> mapperLocations = new ArrayList<>();
    /**
     * 存储所有的映射信息
     */
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getMapperLocations() {
        return mapperLocations;
    }

    public void addMapperLocations(String mapperLocation) {
        this.mapperLocations.add(mapperLocation);
    }

    // Getters and setters
    public Map<String, MappedStatement> getMappedStatements() {
        return this.mappedStatements;
    }
}
