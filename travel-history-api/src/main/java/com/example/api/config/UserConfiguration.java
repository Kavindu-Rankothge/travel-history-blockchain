package com.example.api.config;

import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	@Value("${cert.pem.path}")
	private String certPemPath;

	public HFCAClient createCAClient() throws Exception {
		// create a CA client for interacting with the CA
		Properties props = new Properties();
		props.put("pemFile", certPemPath);
		props.put("allowAllHostNames", "true");
		HFCAClient caClient;
		caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);
		return caClient;
	}

	public Wallet getWallet() throws Exception {
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));
		return wallet;
	}

	public User getUserIdentity(X509Identity identity, com.example.api.model.User user) {
		User newUser = new User() {

			@Override
			public String getName() {
				return user.getName();
			}

			@Override
			public Set<String> getRoles() {
				return user.getRoles();
			}

			@Override
			public String getAccount() {
				return user.getAccount();
			}

			@Override
			public String getAffiliation() {
				return user.getAffiliation();
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return identity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return Identities.toPemString(identity.getCertificate());
					}
				};
			}

			@Override
			public String getMspId() {
				return user.getMspId();
			}

		};
		return newUser;
	}

}
