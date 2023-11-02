package com.zyc.zdh.entity;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;


/**
 * 拼接sql类实现
 * @author Administrator
 *
 */
public class DataAndRuleProvider {

	 public String findDataByTableName(Map<String,Object> map) {
		    String result=map.get("sqlResult").toString();
		    String tableName=map.get("tableName").toString();
	        SQL sql = new SQL().SELECT(result).FROM(tableName);
	       /* String room = param.get("aa").toString();
	        if (StringUtils.isEmpty(room)) {
	            sql.WHERE("room LIKE #{room}");
	        }
	        Date myDate = (Date)param.get("666");
	        if (myDate != null) {
	            sql.WHERE("mydate LIKE #{mydate}");
	        }*/
	        System.out.println(sql.toString());
	        return sql.toString();
	    }
}
