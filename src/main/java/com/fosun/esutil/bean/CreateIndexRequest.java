package com.fosun.esutil.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndexRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Integer shards;
    private Integer replicas;
	private String indexName;
	private String typeName;
	private List<FieldProperty> fieldInfo;
	public CreateIndexRequest(String indexName, String typeName) {
		super();
		this.indexName = indexName;
		this.typeName = typeName;
	}
	public CreateIndexRequest(String indexName, String typeName, List<FieldProperty> fieldInfo) {
		super();
		this.indexName = indexName;
		this.typeName = typeName;
		this.fieldInfo = fieldInfo;
	}
}
