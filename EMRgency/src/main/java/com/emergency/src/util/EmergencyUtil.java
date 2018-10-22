package com.emergency.src.util;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;

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

	public static <T, E> void convertToEntityForUpdate(Object dto,Object entity) {

		//mapper.addMappings(new EmergencyUtil().new TypeMappingIgnoreFields());
        mapper.map(dto, entity);
		//return convertedEntity;
	}

	class TypeMappingIgnoreFields extends PropertyMap<UserDetails, User> {

		@Override
		protected void configure() {
           
			when(Conditions.isNull()).skip(source.getCellno(),destination.getCellno());
			//skip(source.getCellno());
		}

	}
	
	
	public static void convertDTOToEntity(Object dto, Object entity) {
	    BeanUtils.copyProperties(dto, entity);	
	}
	
	public static  void convertEntityToDTO(Object entity,Object dto) {
		BeanUtils.copyProperties(entity, dto);	
	}
}
