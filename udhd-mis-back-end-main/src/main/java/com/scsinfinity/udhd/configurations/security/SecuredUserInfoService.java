package com.scsinfinity.udhd.configurations.security;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SecuredUserInfoService {

	private final IUserRepository userRepo;

	
	public UserEntity getCurrentUserInfo() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
			Optional<UserEntity> userO = userRepo.findByUsernameIgnoreCase(username);
			if (!userO.isPresent())
				throw new EntityNotFoundException("username not found: " + username);

			UserEntity user = userO.get();

			if (user.isAccountNonExpired() == false || user.isAccountNonLocked() == false
					|| user.isCredentialsNonExpired() == false || user.isEnabled() == false)
				throw new BadRequestAlertException("User is not active:" + username, "PROBLEM_WITH_USER_ACCOUNT",
						"PROBLEM_WITH_USER_ACCOUNT");
			return userO.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
