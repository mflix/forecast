package org.mflix.forecast;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mflix.forecast.component.RoleComponent;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.enumeration.RoleEnumeration;
import org.mflix.forecast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ForecastApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleComponent roleComponent;

	@Test
	void contextLoads() {
		var date = new Date();
		var authorities = roleComponent.generate(RoleEnumeration.ADMIN);
		var password = new BCryptPasswordEncoder().encode("mflix@forecast");
		var username = "root";
		UserEntity userEntity = new UserEntity(true, true, authorities, true, true, password, username, date);
		userEntity = userRepository.save(userEntity);
	}
}
