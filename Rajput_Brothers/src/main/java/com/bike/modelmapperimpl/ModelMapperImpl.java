package com.bike.modelmapperimpl;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperImpl {

	@Bean
	public ModelMapper getModelMapper()
	{
		return new ModelMapper();
	}
}
