package com.example.api.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.api.model.Person;
import com.google.gson.Gson;

@Service
public class PersonServiceImpl implements PersonService {

	@Value("${network.yml.path}")
	private String networkPath;

	@Value("${app.user.identity}")
	private String userName;

	public Contract getContract() {
		try {
			// Load a file system based wallet for managing identities.
			Path walletPath = Paths.get("wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);
			// load a CCP
			Path networkConfigPath = Paths.get(networkPath);
			Gateway.Builder builder = Gateway.createBuilder();
			builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);
			// create a gateway connection
			Gateway gateway = builder.connect();
			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("travel-history");
			return contract;
		} catch(Exception e) {
			System.out.println("Error while creating contract: " + e);
		}
		return null;
	}

	@Override
	public String savePerson(Person person) {
		Gson gson = new Gson();
		try {
			Contract contract = getContract();
			person.setId();
			Optional<Person> optionalPerson = this.getPerson(person.getId());
			if (optionalPerson.isPresent()) {
				return "Person already exist";
			}
			String personString = gson.toJson(person);
			contract.submitTransaction("savePerson", person.getId(), personString);
			return "Added person with id: " + person.getId();
		} catch(Exception e) {
			return "Error while saving person: " + e;
		}
	}

	@Override
	public Optional<Person> getPerson(String id) {
		byte[] result;
		Person person = null;
		Gson gson = new Gson();
		Contract contract = getContract();
		try {
			result = contract.evaluateTransaction("queryPerson", id);
			String jsonString = new String(result);
			person = gson.fromJson(jsonString, Person.class);
		} catch (Exception e) {
			return Optional.empty();
		}
		return Optional.of(person);
	}

	@Override
	public List<Person> getAllPeople() {
		Gson gson = new Gson();
		List<Person> personList = null;
		Contract contract = getContract();
		byte[] result;
		try {
			result = contract.evaluateTransaction("queryAllPeople");
			String jsonString = new String(result);
			Person[] person = gson.fromJson(jsonString, Person[].class);
			personList = new ArrayList<Person>(Arrays.asList(person));
		} catch (Exception e) {
			System.out.println("Error while getting all people: " + e);
		}
		return personList;
	}

	@Override
	public String updatePerson(Person person) {
		Gson gson = new Gson();
		try {
			Contract contract = getContract();
			person.setId();
			Optional<Person> optionalPerson = this.getPerson(person.getId());
			if (!optionalPerson.isPresent()) {
				return "Person does not exist";
			}
			String personString = gson.toJson(person);
			contract.submitTransaction("updatePerson", person.getId(), personString);
			return "Updated person with id: " + person.getId();
		} catch(Exception e) {
			return "Error while updating person: " + e;
		}
	}

	@Override
	public String deletePerson(String id) {
		Contract contract = getContract();
		try {
			contract.submitTransaction("deletePerson", id);
			return "Deleted person with id: " + id;
		} catch (Exception e) {
			System.out.println("Error while deleting person: " + e);
			return "Error while deleting person: " + e;
		}
	}

}
