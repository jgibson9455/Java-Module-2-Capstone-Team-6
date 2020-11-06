package com.techelevator.tenmo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.accounts.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.transfers.Transfers;
import com.techelevator.tenmo.transfers.TransfersDTO;
import com.techelevator.tenmo.users.Users;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private List<Users> userList;
    private Scanner input;
    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
//		System.out.println("*********************");
//		System.out.println("* Welcome to TEnmo! *");
//		System.out.println("*********************");
		printHeader();
		printLogo();
		System.out.println();
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		populateUserList();
		printLogo();
		System.out.println("-------------------------------------");
		System.out.println("Current user: " + currentUser.getUser().getUsername());
		System.out.println("-------------------------------------");
		while(true) {

			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			printLogo();
			System.out.println("-------------------------------------");
			System.out.println("Current user: " + currentUser.getUser().getUsername());
			System.out.println("-------------------------------------");
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance(currentUser);
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory(currentUser);
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				input = new Scanner(System.in);
				System.out.println("---------Current Users---------");
				for (Users u : userList) {
					System.out.println("(" + u.getUserId() + ")   " + u.getUsername());
				}
				viewCurrentBalance(currentUser);
				System.out.println();
				try {
				System.out.print("\nPlease enter the user ID you would like to send bucks to: ");
				int aNumber = Integer.valueOf(input.nextLine());
				System.out.print("\nPlease enter the amount you would like to send: ");
				double amountToSend = Double.parseDouble(input.nextLine());
				if (amountToSend <= 0) {
					System.out.println("How are you supposed to send that amount? Please, try again.");
					return;
				}
				sendBucks(currentUser, aNumber, amountToSend);
				}catch(NumberFormatException ex) {
					System.out.println("\nSorry, something went wrong! Try again!");
				}
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				System.out.println("Thanks for banking with TEnmo! We hope to see you again soon!");
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance(AuthenticatedUser currentUser) {
		Accounts account = new Accounts();
		RestTemplate restTemplate = new RestTemplate();
		int id = currentUser.getUser().getId();
		ResponseEntity<Accounts> responseEntity = restTemplate.getForEntity
				(API_BASE_URL + "accounts/" + id, Accounts.class);
		account = responseEntity.getBody();
		System.out.println("Your current account balance is: $" + account.getBalance());
		
		
		
		
	}

	private void viewTransferHistory(AuthenticatedUser currentUser) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Transfers[]> entity = restTemplate.getForEntity
				(API_BASE_URL + "transfers/users/" + currentUser.getUser().getId(), Transfers[].class);
		List<Transfers> transferList = Arrays.asList(entity.getBody());
		
		System.out.println("---------Transaction History---------");
		System.out.println("---------------Outgoing--------------");
		
		for (Transfers t : transferList) {
			System.out.println("Bucks Sent: " + t.getAmount() + "\nTo User: " + getNameFromUserList(t.getAccountTo()));	
			System.out.println("-------------------------------------");
		}
		ResponseEntity<Transfers[]> toEntity = restTemplate.getForEntity
				(API_BASE_URL + "/transfers/userTo/" + currentUser.getUser().getId(), Transfers[].class);
		List<Transfers> incomingTransfers = Arrays.asList(toEntity.getBody());
		System.out.println("---------------Incoming--------------");
		for (Transfers t : incomingTransfers) {
			System.out.println("Bucks Received: " + t.getAmount() + "\nFrom User: " + getNameFromUserList(t.getAccountFrom()));	
			System.out.println("-------------------------------------");
		}
	}

	private void viewPendingRequests() {
		System.out.println("\nWe apologize for the inconvinience, but this feature has not yet been implemented!\n");
		
	}

	private void sendBucks(AuthenticatedUser currentUser, int toId, double amountToSend) {
		RestTemplate restTemplate = new RestTemplate();
		int id = currentUser.getUser().getId();
		ResponseEntity<Accounts> entity = restTemplate.getForEntity
				(API_BASE_URL + "users/accounts/searchUserId?userId=" + id, Accounts.class);
		Accounts fromAccount = entity.getBody();
		double currentUserBalance = fromAccount.getBalance();
		if (amountToSend > currentUserBalance) {
			System.out.println("Sorry, but you can't send more money than you have!");
			viewCurrentBalance(currentUser);
			System.out.println();
			return;
		}else {
		restTemplate.put(API_BASE_URL + "withdraw/" + id, amountToSend);
	    restTemplate.put(API_BASE_URL + "deposit/"+ toId, amountToSend);
		viewCurrentBalance(currentUser);
		System.out.println();
		}
		
		Transfers transfer = new Transfers();
		transfer.setAccountTo(toId);
		transfer.setAccountFrom(id);
		transfer.setAmount(amountToSend);
		transfer.setStatusId(2);
		transfer.setTypeId(2);
		
		ResponseEntity<Accounts> toEntity = restTemplate.getForEntity
				(API_BASE_URL + "users/accounts/searchUserId?userId=" + toId, Accounts.class);
		Accounts toAccount = entity.getBody();
		
		restTemplate.put(API_BASE_URL + "transfer", transfer);
		
		//transfer controller to send this DTO to API
		
	}

	private void requestBucks() {
		System.out.println("\nWe apologize for the inconvinience, but this feature has not yet been implemented!\n");
		
	}
	
	private String getNameFromUserList(int id) {
		for (Users u : userList) {
			if(u.getUserId() == id) {
				return u.getUsername();
			}
		}
		return "ID not found";
		
	}
	
	private void populateUserList() {
		RestTemplate apiCall = new RestTemplate();
		ResponseEntity<Users[]> entity = apiCall.getForEntity(API_BASE_URL + "users/", Users[].class);
	    userList = Arrays.asList(entity.getBody());
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				System.out.println("Thanks for banking with TEnmo! We hope to see you again soon!");
				printLogo();
				System.out.println(sayGoodbyeStatement());
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
	
	public void printHeader() {
		System.out.println("  _      __    __                     __    ");
		System.out.println(" | | /| / /__ / /______  __ _  ___   / /____");
		System.out.println(" | |/ |/ / -_) / __/ _ \\/  ' \\/ -_) / __/ _ \\");
		System.out.println(" |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/  \\__/\\___/");
	}
	
	public void printLogo() {
		System.out.println("   ____________                    ");
		System.out.println("  /_  __/ ____/___  ____ ___  ____");
		System.out.println("   / / / __/ / __ \\/ __ `__ \\/ __ \\ ");
		System.out.println("  / / / /___/ / / / / / / / / /_/ /");
		System.out.println(" /_/ /_____/_/ /_/_/ /_/ /_/\\____/");
	}
	
	 public String sayGoodbyeStatement() { 
	        Random rand = new Random(); 
	        List<String> list = new ArrayList<>();
	        list.add("Have a nice day!");
	        list.add("Goodbye!");
	        list.add("See ya later!");
	        list.add("Sayonara!");
	        list.add("Matane!");
	        list.add("Have a good one!");
	        list.add("Adios!");
	        list.add("Hasta manana!");
	        list.add("Until next time!");
	        list.add("Good night!");
	        list.add("Bye!");
	        list.add("Au revoir!");
	        list.add("See ya!");
	        return list.get(rand.nextInt(list.size())); 
	    }
}

