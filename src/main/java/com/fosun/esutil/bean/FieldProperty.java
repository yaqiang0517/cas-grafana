package com.fosun.esutil.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类型字段属性信息
 * @author think
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldProperty implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 字段名
	 */
	private String field;
	/**
	 * es字段类型
	 */
	private String type;
	/**
	 * 日期类型字段格式
	 */
	private String format;
	/**
	 * 是否建立索引
	 */
	private String index;
	/**
	 * 否在 _source 之外在独立存储一份
	 */
	private String store;
	/**
	 * 分词器
	 */
	private String analyer;
	

}
