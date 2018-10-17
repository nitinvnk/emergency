package com.emergency.src.util;

import org.modelmapper.ModelMapper;

public class EmergencyUtil {

	private static ModelMapper mapper = new ModelMapper();
	
	public static  <T, E> T convertToDTO(Object entity, Class<T> dto) {
		T convertedDTO = mapper.map(entity, dto);
		return convertedDTO;
	}

	public static <T, E> E convertToEntity(Object dto, Class<E> entity) {
		E convertedEntity = mapper.map(dto, entity);
		return convertedEntity;
	}
}
