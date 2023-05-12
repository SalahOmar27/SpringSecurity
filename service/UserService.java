package com.salah.springSecurity.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salah.springSecurity.entity.User;
import com.salah.springSecurity.entity.UserRecord;
import com.salah.springSecurity.exceptions.UserAlreadyExistsException;
import com.salah.springSecurity.repository.UserRepository;
import com.salah.springSecurity.security.UserValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EntityManager entityManager;
	private final UserValidator userValidator;

	@Override
	public User add(User user) {
		userValidator.validateUser(user);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root).where(cb.equal(root.get("email"), user.getEmail()));
		TypedQuery<User> typedQuery = entityManager.createQuery(query);
		List<User> results = typedQuery.getResultList();
		if (!results.isEmpty()) {
			throw new UserAlreadyExistsException("A user with " + user.getEmail() + " already exists");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<UserRecord> getAllUsers() {
		return userRepository.findAll().stream()
				.map(user -> new UserRecord(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void delete(String email) {
		// userRepository.deleteByEmail(email);
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
		Root<User> root = delete.from(User.class);
		delete.where(cb.equal(root.get("email"), email));
		entityManager.createQuery(delete).executeUpdate();
	}

	@Override
	public User getUser(String email) {
		// return userRepository.findByEmail(email).orElseThrow(() -> new
		// UserNotFoundException("User not found"));
		String query = "SELECT u FROM User u WHERE u.email = :email";
		TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
		typedQuery.setParameter("email", email);
		return typedQuery.getSingleResult();
	}

	@Override
	public User update(User user) {
		userValidator.validateUser(user);
		user.setRole(user.getRole());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

//	  public User getUserById(int id) {
//	        String query = "SELECT u FROM User u WHERE u.id = :id";
//	        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
//	        typedQuery.setParameter("id", id);
//	        return typedQuery.getSingleResult();
//	    }

}
