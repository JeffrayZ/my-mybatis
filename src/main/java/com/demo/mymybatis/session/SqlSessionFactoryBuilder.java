package com.demo.mymybatis.session;

import com.demo.mymybatis.config.ConfigParser;
import com.demo.mymybatis.config.Configuration;
import com.demo.mymybatis.mapper.MapperParser;

/**
 * SqlSessionFactoryBuilder 用于通过 Configuration 创建 SqlSessionFactory
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(String configFile) {
        // 解析配置文件
        Configuration configuration = ConfigParser.parseConfig(configFile);

        // 解析 Mapper 文件
        MapperParser mapperParser = new MapperParser(configuration);
        for (String mapperLocation : configuration.getMapperLocations()) {
            mapperParser.parse(mapperLocation);
        }

        // 创建 SqlSessionFactory
        return new DefaultSqlSessionFactory(configuration);
    }
}
