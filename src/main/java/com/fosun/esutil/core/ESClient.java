package com.fosun.esutil.core;

import com.fosun.esutil.config.PropertiesUtil;
import com.fosun.esutil.exception.BizException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class ESClient {

	private static Client client;

	private ESClient(){

	}

	private Client createClient(){
		String clusterName = PropertiesUtil.getProperties().get("es.cluster.name");
		String addr = PropertiesUtil.getProperties().get("es.addr");
		String portStr = PropertiesUtil.getProperties().get("es.port");

		if(StringUtils.isEmpty(clusterName)){
			throw new BizException(-1,"请配置ES集群名");
		}
		if(StringUtils.isEmpty(addr)){
			throw new BizException(-1,"请配置ES地址");
		}
		if(StringUtils.isEmpty(portStr)){
			throw new BizException(-1,"请配置ES端口");
		}
		Integer port = Integer.parseInt(portStr);
		if(null == client){
			Settings settings = Settings.builder().put("cluster.name", clusterName).build();
			try {
				client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(addr), port));
			} catch (UnknownHostException e) {
				e.printStackTrace();
				throw new BizException(-1,"获取es连接失败，请检查配置是否正确");
			}
		}
		return client;
	}


	public static Client getClient() {
    	return new ESClient().createClient();
	}

}
