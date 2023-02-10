package com.example.api.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.stereotype.Service;

import com.example.api.model.Person;
import com.google.gson.Gson;

@Service
public class PersonServiceImpl implements PersonService {

//	@Autowired
//	private PersonRepository personRepository;

	public Contract getContract() {
		try {
			// Load a file system based wallet for managing identities.
			Path walletPath = Paths.get("wallet");
			Wallet wallet = Wallets.newFileSystemWallet(walletPath);
			// load a CCP
			Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
			Gateway.Builder builder = Gateway.createBuilder();
			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);		
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
		try {
			Contract contract = getContract();
			byte[] result;
			Gson gson = new Gson();
			String personString = gson.toJson(person);
//			System.out.println("personString: "+ personString);
			System.out.println("submitting person: ");
			result = contract.submitTransaction("savePerson", person.getId(), personString);
			System.out.println(new String(result));
			return "Added person with id: " + person.getId();
		} catch(Exception e) {
			System.out.println("Error while saving person: " + e);
			return "Error while saving person: " + e;
		}
	}

	@Override
	public Optional<Person> getPerson(String id) {
		Person person = null;
		Contract contract = getContract();
		byte[] result;
		try {
			result = contract.evaluateTransaction("queryPerson", id);
			System.out.println(new String(result));
			ByteArrayInputStream bis = new ByteArrayInputStream(result);
		    ObjectInput in = new ObjectInputStream(bis);
		    person = (Person) in.readObject();
		} catch (Exception e) {
			System.out.println("Error while getting person: " + e);
		}
		return Optional.of(person);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPeople() {
		List<Person> personList = null;
		Contract contract = getContract();
		byte[] result;
		try {
			result = contract.evaluateTransaction("queryAllPeople");
			System.out.println(new String(result));
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(result));
			personList = (ArrayList<Person>) ois.readObject();
		} catch (Exception e) {
			System.out.println("Error while getting all people: " + e);
		}
		return personList;
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
