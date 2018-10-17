package com.emergency.src.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.emergency.src.dto.UserDetails;
import com.emergency.src.entities.User;

public class EmergencyUtil {

	private static ModelMapper mapper = new ModelMapper();

	public static <T, E> T convertToDTO(Object entity, Class<T> dto) {
		T convertedDTO = mapper.map(entity, dto);
		return convertedDTO;
	}

	public static <T, E> E convertToEntity(Object dto, Class<E> entity) {
		E convertedEntity = mapper.map(dto, entity);
		return convertedEntity;
	}

	public static <T, E> E convertToEntityForUpdate(Object dto, Class<E> entity) {

		mapper.addMappings(new EmergencyUtil().new TypeMappingIgnoreFields());
		E convertedEntity = mapper.map(dto, entity);
		return convertedEntity;
	}

	class TypeMappingIgnoreFields extends PropertyMap<UserDetails, User> {

		@Override
		protected void configure() {
			skip(source.getCellno());
		}

	}
}
