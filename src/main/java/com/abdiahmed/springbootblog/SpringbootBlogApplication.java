package com.abdiahmed.springbootblog;

// import org.modelmapper.ModelMapper;
// import org.modelmapper.convention.MatchingStrategies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootBlogApplication.class, args);
  }

  //	@Bean
  //	public ModelMapper modelMapper() {
  //		ModelMapper modelMapper = new ModelMapper();
  //		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
  //		return modelMapper;
  //	}

}
