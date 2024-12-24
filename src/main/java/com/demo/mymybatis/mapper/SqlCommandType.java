package com.demo.mymybatis.mapper;

public enum SqlCommandType {
    SELECT("select"),
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");

    private final String commandType;

    SqlCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }

    public static SqlCommandType getSqlCommandType(String commandType) {
        for (SqlCommandType sqlCommandType : SqlCommandType.values()) {
            if (sqlCommandType.getCommandType().equals(commandType)) {
                return sqlCommandType;
            }
        }
        return null;
    }
}
