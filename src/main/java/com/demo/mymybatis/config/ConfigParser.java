package com.demo.mymybatis.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * ConfigParser 类，用于解析 mybatis-config.xml 文件
 */
public class ConfigParser {
    public static Configuration parseConfig(String configFile) {
        // 解析 XML 文件并初始化 Configuration 对象
        Configuration configuration = new Configuration();
        InputStream inputStream = ConfigParser.class.getClassLoader().getResourceAsStream(configFile);
        try {
            // 使用 SAXReader 解析 XML 文件
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            // 获取根节点 <configuration>
            Element root = document.getRootElement();

            // 解析 <environments>
            Element environments = root.element("environments");
            String defaultEnv = environments.attributeValue("default");

            // 找到默认环境配置
            List<Element> environmentList = environments.elements("environment");
            for (Element environment : environmentList) {
                if (defaultEnv.equals(environment.attributeValue("id"))) {
                    // 解析 <dataSource>
                    Element dataSource = environment.element("dataSource");
                    List<Element> properties = dataSource.elements("property");

                    for (Element property : properties) {
                        String name = property.attributeValue("name");
                        String value = property.attributeValue("value");
                        switch (name) {
                            case "driver":
                                configuration.setDriver(value);
                                break;
                            case "url":
                                configuration.setUrl(value);
                                break;
                            case "username":
                                configuration.setUsername(value);
                                break;
                            case "password":
                                configuration.setPassword(value);
                                break;
                        }
                    }
                }
            }

            // 解析 <mappers>
            Element mappers = root.element("mappers");
            List<Element> mapperList = mappers.elements("mapper");

            for (Element mapper : mapperList) {
                String resource = mapper.attributeValue("resource");
                configuration.addMapperLocations(resource);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return configuration;
    }
}
