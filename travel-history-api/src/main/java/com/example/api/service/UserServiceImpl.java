package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.config.UserConfiguration;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConfiguration userConfiguration;

	@Override
	public String saveUser(User user) {
		Optional<User> optionalUser = userRepository.findById(user.getName());
		if (optionalUser.isPresent()) {
			return "User already exist";
		}
		userRepository.save(user);
		return "Added user with name: " + user.getName();
	}

	@Override
	public Optional<User> getUser(String name) {
		return userRepository.findById(name);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String updateUser(User user) {
		Optional<User> optionalUser = userRepository.findById(user.getName());
		if (!optionalUser.isPresent()) {
			return "User does not exist";
		}
		userRepository.save(user);
		return "Updated user with name: " + user.getName();
	}

	@Override
	public String deleteUser(String name) {
		userRepository.deleteById(name);
		return "Deleted user with name: " + name;
	}

	@Override
	public String enrollAdminUser(String name) {
		try {
			Optional<User> adminUser = userRepository.findById(name);
			if (!adminUser.isPresent()) {
				return "User \"" + name + "\" does not exist in database";
			}
			User admin = adminUser.get();
			HFCAClient caClient = userConfiguration.createCAClient();
			Wallet wallet = userConfiguration.getWallet();
			if (wallet.get(name) != null) {
				return "An identity for the \"" + name + "\" user already exists in the wallet";
			}
			EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
			enrollmentRequestTLS.addHost("localhost");
			enrollmentRequestTLS.setProfile("tls");
			Enrollment enrollment = caClient.enroll(name, admin.getPassword(), enrollmentRequestTLS);
			Identity user = Identities.newX509Identity(admin.getMspId(), enrollment);
			wallet.put(name, user);
			return "Successfully enrolled user \"" + name + "\" and imported it into the wallet";
		} catch (Exception e) {
			return "Error with user enrollment: " + e;
		}
	}

	@Override
	public String registerNewUser(String adminName, String userName) {
		try {
			HFCAClient caClient = userConfiguration.createCAClient();
			Wallet wallet = userConfiguration.getWallet();
			if (wallet.get(userName) != null) {
				return "An identity for the \"" + userName + "\" user already exists in the wallet";
			}
			X509Identity adminIdentity = (X509Identity)wallet.get("admin");
			if (adminIdentity == null) {
				return "\"" + adminName + "\" needs to be enrolled and added to the wallet first";
			}
			Optional<User> adminUser = userRepository.findById(adminName);
			if (!adminUser.isPresent()) {
				return "User \"" + adminName + "\" does not exist in database";
			}
			User admin = adminUser.get();
			Optional<User> newUserOptional = userRepository.findById(userName);
			if (!newUserOptional.isPresent()) {
				return "User \"" + userName + "\" does not exist in database";
			}
			User newUser = newUserOptional.get();
			org.hyperledger.fabric.sdk.User userIdentity = userConfiguration.getUserIdentity(adminIdentity, admin);
			// Register the user, enroll the user, and import the new identity into the wallet.
			RegistrationRequest registrationRequest = new RegistrationRequest(userName);
			registrationRequest.setAffiliation(newUser.getAffiliation());
			registrationRequest.setEnrollmentID(userName);
			String enrollmentSecret = caClient.register(registrationRequest, userIdentity);
			Enrollment enrollment = caClient.enroll(userName, enrollmentSecret);
			Identity user = Identities.newX509Identity(newUser.getMspId(), enrollment);
			wallet.put(userName, user);
			return "Successfully enrolled user \"" + userName + "\" and imported it into the wallet";
		} catch (Exception e) {
			return "Error with user registration: " + e;
		}
	}

}