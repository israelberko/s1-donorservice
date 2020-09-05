package org.ssm.demo.donorservice.entity;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface BaseEntity {
	final static String PAYLOAD = "payload";
	final static String AFTER = "after";
	final static Logger LOG = LoggerFactory.getLogger(BaseEntity.class);
	final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	default <T> T buildFrom(Map<?,?> changeEvent, Class<T> valueType) {
		if ( changeEvent.containsKey( PAYLOAD )) {
			System.out.println(changeEvent);
			Object afterField = ((Map<?,?>)changeEvent.get(PAYLOAD)).get(AFTER);
			return OBJECT_MAPPER.convertValue(afterField, valueType);
		} else {
			return OBJECT_MAPPER.convertValue(changeEvent, valueType);
		}
	}
	
	default Map<?,?> toMap() {
		return OBJECT_MAPPER.convertValue(this, new TypeReference<Map<?, ?>>() {});
	}
}
