package com.itheima.sqlsession;

import java.io.Serializable;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 10:15
 */
public class Mapper implements Serializable {
    private String sql;
    private String resultType;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "Mapper{" +
                "sql='" + sql + '\'' +
                ", resultType='" + resultType + '\'' +
                '}';
    }
}
