package com.demo.mymybatis.mapper;

import com.demo.mymybatis.config.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * MapperParser 用于解析单个 Mapper 文件并将信息存储到 Configuration 中
 */
public class MapperParser {
    private Configuration configuration;

    public MapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(String mapperLocation) {
        InputStream inputStream = MapperParser.class.getClassLoader().getResourceAsStream(mapperLocation);
        try {
            // 使用 SAXReader 解析 XML
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);

            // 获取根节点 <mapper>
            Element root = document.getRootElement();
            String namespace = root.attributeValue("namespace");

            // 解析所有 <select>、<insert>、<update>、<delete> 节点
            List<Element> elements = root.elements();
            for (Element element : elements) {
                String name = element.getName(); // 节点名称（如 select、insert）
                if ("select".equals(name) || "insert".equals(name) || "update".equals(name) || "delete".equals(name)) {
                    MappedStatement mappedStatement = new MappedStatement();

                    // 获取 SQL 的 ID
                    String id = element.attributeValue("id");
                    String parameterType = element.attributeValue("parameterType");
                    String resultType = element.attributeValue("resultType");
                    String sql = element.getTextTrim();

                    // 设置 MappedStatement 属性
                    mappedStatement.setId(namespace + "." + id);
                    mappedStatement.setParameterType(parameterType);
                    mappedStatement.setResultType(resultType);
                    mappedStatement.setSql(sql);
                    mappedStatement.setSqlCommandType(SqlCommandType.getSqlCommandType(name));

                    // 存储到 Configuration
                    configuration.getMappedStatements().put(mappedStatement.getId(), mappedStatement);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
