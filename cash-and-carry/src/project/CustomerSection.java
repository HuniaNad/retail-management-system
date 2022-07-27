package project;

import java.io.*;
import java.util.*;

public class CustomerSection {
	//dummy data for online payment 
	public static String[][] paymentInfo = {{"Hunia Nadeem", "4221388149882643", "10/24"},{"Vania Majid", "5237610008684292", "10/92"},
			{"Fatima Tuzzahra", "5301678049311242", "10/19"}};
	public static void mainMenu(String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials,
			int[][] costQuantityCookingEssentials, String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies,
			int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary,String[][] logInCredentials, String[][] feedbacks) throws IOException {
		Scanner input = new Scanner(System.in);
		String loopCondition = "";
		displayMenu("main");
		do {
			System.out.print("     Press any option to continue..  ");
			loopCondition = input.nextLine().trim();
			switch(loopCondition) {
			//to exit from customer section
			case "0": break;
			//login as regular customer
			case "1": 
				String customerName = logIn(logInCredentials, "regular");
				searchMenu(ctgDairy, costQuantityDairy, ctgCookingEssentials, costQuantityCookingEssentials, ctgBeverages, costQuantityBeverages, ctgBathroomSupplies,
						costQuantityBathroomSupplies, ctgStationary, costQuantityStationary, feedbacks, new String[] {customerName});
				break;
				//login as member
			case "2": 
				String ID = logIn(logInCredentials, "member");
				//ID will be null when user wnats to go to main menu
				if (ID == null) {
					displayMenu("main");
					break;
				}
				searchMenu(ctgDairy, costQuantityDairy, ctgCookingEssentials, costQuantityCookingEssentials, ctgBeverages, costQuantityBeverages, ctgBathroomSupplies,
						costQuantityBathroomSupplies, ctgStationary, costQuantityStationary, feedbacks, getMemberCredentials(logInCredentials, ID));
				break;
				//get membership
			case "3": 
				getMembership(logInCredentials);
				break;
			case "4":
				String goBack;
				displayFeedbacks(feedbacks);
				do {
					System.out.print("\n        [m] Main menu"
							+ "\n\n        Press any key to continue.. ");
					goBack = input.nextLine().toLowerCase().trim();
					System.out.println(!goBack.equals("m")? "     INVALID INPUT: Required Valid Input is m":"");
				}while(!goBack.equals("m"));
				displayMenu("main");
				break;
			default : 
				System.out.println(loopCondition.equals("")? "\n     INVALID INPUT: Required Valid Input Which Is Not Null\n":"     \n     INVALID INPUT: Required Correct Option Number\n");
			}
		}while(!loopCondition.equals("0"));
	}

	public static void displayMenu(String code) {
		switch(code) {
		case "main": //displays main menu
			System.out.println   ("               -------------------------------                \n"
					+ "     +~~~~~~~~~| C U S T O M E R   B L O C K |~~~~~~~~~+\n"
					+ "     |         -------------------------------         |\n"
					+ "     |             Press 1: Log In (Regular)           |\n"
					+ "     |             Press 2: Log In (Member)            |\n"
					+ "     |             Press 3: Sign Up                    |\n"
					+ "     |             Press 4: View Feedbacks             |\n"
					+ "     |             Press 0: Exit                       |\n"
					+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+"); break;
		case "sign up": 
			System.out.println(   "               -------------------------------          \n"
					+ "     +~~~~~~~~| M E M B E R S H I P   M E N U |~~~~~~~~+\n"
					+ "     |         -------------------------------         |\n"
					+ "     |           Press 1: Registration Form            |\n"
					+ "     |           Press 2: Main Menu                    |\n"
					+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+"); break;
		case "log in":
			System.out.println   ("                   -----------------------              \n"
					+ "     +~~~~~~~~~~~~| L O G - I N   M E N U |~~~~~~~~~~~~+\n"
					+ "     |             -----------------------             |\n"
					+ "     |              Press 1: Log In                    |\n"
					+ "     |              Press 2: Forgot Password           |\n"
					+ "     |              Press 0: Main Menu                 |\n"
					+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+"); break;
		case "search":
			System.out.println("                     ---------------------            \n"
					+ "     +~~~~~~~~~~~~~~~| M A I N   M E N U |~~~~~~~~~~~~~+\n"
					+ "     |               ---------------------             |\n"
					+ "     |         Press 1: Search by Product name         |\n"
					+ "     |         Press 2: Search by Product Category     |\n"
					+ "     |         Press 3: Go to Cart                     |\n"
					+ "     |         Press 0: Log out                        |\n"
					+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+"); break;
		case "category": 
			System.out.println("                    ---------------------               \n"
					+ "     +~~~~~~~~~~~~~| C A T E G O R I E S |~~~~~~~~~~~~~+\n"
					+ "     |              ---------------------              |\n"
					+ "     |              1- Dairy                           |\n"
					+ "     |              2- Cooking Essentials              |\n"
					+ "     |              3- Beverages                       |\n"
					+ "     |              4- Bathroom Supplies               |\n"
					+ "     |              5- Stationary                      |\n"
					+ "     |              0- Go to Search Menu               |\n"
					+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");  break;
		case "cart": 
			System.out.println("                     -------------------                \n"
					+ "     +~~~~~~~~~~~~~~| C A R T   M E N U |~~~~~~~~~~~~~~+\n"
					+ "     |               -------------------               |\n"
					+ "     |          Press 1: Delete Cart                   |\n"
					+ "     |          Press 2: Update Product Quantity       |\n"
					+ "     |          Press 3: Remove Product                |\n"
					+ "     |          Press 4: Checkout                      |\n"
					+ "     |          Press 5: Go to Search Menu             |\n"
					+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
		}
	}

	//based on the ID passed, it will return a String array containing name, email, phone number and ID.
	//used in [main menu --> logIn (member)] to send member details to search menu 
	public static String[] getMemberCredentials(String[][] logInCredentials, String ID) {
		for (int i=0; i<lastEntityIndex(logInCredentials); i++) {
			if (logInCredentials[i][4].equals(ID)) {
				return new String[] {logInCredentials[i][0], logInCredentials[i][2], logInCredentials[i][3], ID};
			}
		}
		return null;
	}

//MEMBERSHIP 
	//sign up method for membership
	public static void getMembership(String[][] logInCredentials) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		//variable initialization
		String username="", password="", email="", phoneNo ="";
		String choice;

		//printing membership details
		printMembershipInfoMenu();
		displayMenu("sign up");
		do {
			System.out.print("     Press any option to continue.. ");
			choice = input.nextLine().trim();
			switch(choice) {
			//registration form
			case "1":
				System.out.print("                                -----------------------------------             \n"
						+ "     +~~~~~~~~~~~~~~~~~~~~~~~~~| R E G I S T R A T I O N   F O R M |~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
						+ "                                -----------------------------------             \n");				  
				//input name
				System.out.println("\t---> NOTE: Name should contain only letters & spaces");
				username = Input.name("\tEnter name: ");

				//input password
				System.out.println("\t---> NOTE:\n\t1- Password should contain only letters and numbers\n\t2- Passwords must be atleast 8 characters\n");
				password = Input.password("\tEnter password: ","customer");

				//validation check used for prior existence of entered email in record
				do {
					//input email
					System.out.println("\t---> FORMAT: xyz@email.com");
					email = Input.email("\tEnter email: ","customer");
					System.out.print(findMembershipData(logInCredentials, "email", email)? "\tERROR: Email is already used!\n\n":"");
				}while(findMembershipData(logInCredentials, "email", email));

				//validation check used for prior existence of entered phoneNo in record
				do {
					//input phone number
					System.out.println("\t---> FORMAT: 0332XXXXXXX");
					phoneNo = Input.phoneNo("\tEnter phone number: ","customer");
					System.out.print(findMembershipData(logInCredentials, "phoneNo", phoneNo)? "\tERROR: Phone Number is already used!\n\n":"");
				}while(findMembershipData(logInCredentials, "phoneNo", phoneNo));

				if (searchAgain("\t---> Do you want to Proceed to Payment?\n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")) {
					//taking payment
					cardPayment(logInCredentials, "30000", username,password,email,phoneNo);	
				}
				//printing membership details
				printMembershipInfoMenu();
				displayMenu("sign up");
				break;
				//go to main menu
			case "2": 
				displayMenu("main");
				return;
			default:
				System.out.println(choice.equals("")? "\n     INVALID INPUT: Required Valid Input Which Is Not Null\n":"\n     INVALID INPUT: Required Correct Option Number\n");
			}
		}while(!choice.equals("2"));//again display registration form	
	}

	//displaying membership details
	public static void printMembershipInfoMenu() {
		System.out.println("                                       ---------------------------------                                        \n"
				+ " +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| S    I    G    N    -    U    P |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
				+ "                                       ---------------------------------                                        \n");
		System.out.println(
				" +-------------------------------------------------------------------------------------------------------------+\n"
						+ " |  Welcome to FHEM-V Cash&Carry Membership Registration!                                                      |\n"
						+ " |  We are so happy we could hook you in to becoming a member! Let's get down to the perks you'll be           |\n"
						+ " |  getting in just PKR 30,000:                                                                                |\n"
						+ " |     -> Membership span:          3 years                                                                    |\n"
						+ " |     -> Discount on all products: 15%                                                                        |\n"
						+ " |     -> Extra perks:              5% extra discount on seasonal deals                                        |\n"
						+ " |                                  promotions and updates will be delivered to you through email/number       |\n"
						+ " +-------------------------------------------------------------------------------------------------------------+\n");
	}

	//generates random member id of format (xyz-000)
	public static String generateMemberID(String[][] logInCredentials, String name) {
		Random rand = new Random();
		String memberID;
		//loop checks if it id pre-exists or not
		do {
			memberID = name.substring(0,3).toUpperCase() +"-"+ Integer.toString(rand.nextInt(10)) + rand.nextInt(10) + rand.nextInt(10);
		}while(findMembershipData(logInCredentials, "id", memberID));
		//returns unique id
		return memberID;
	}

	//for entering membership data and to change passwords in case of forgotten
	public static int updateLogInCredentials(String[][] logInCredentials, int code, String... information) throws FileNotFoundException{
		switch(code) {
		//if code 0 is passed, the following body will be used for entering new credentials
		case 0:			
			//credential array is checked row and column wise to enter data in empty place
			for(int i=0; i<logInCredentials.length; i++) {
				//checking if array location(row) is empty
				if (logInCredentials[i][0]==null) {
					//entering the information into the array. information... [0]=name, [1]=password, [2]=email, [3]=phoneNo, [4]=member id
					for(int j=0; j<logInCredentials[i].length; j++) {
						if (j == 4) {
							logInCredentials[i][j] = generateMemberID(logInCredentials,information[0]);
							arrayToMemberFile(logInCredentials);
							return i;	//row index is returned at which member info is saved
						}
						logInCredentials[i][j] = information[j];
					}
				}
			}
			//if code 1 is passed, the following body will be used to change the password in forgotCredentials()
		case 1: 
			int memberIndex = memberIndex(logInCredentials, information[0]);
			//password is updated
			logInCredentials[memberIndex][1] = information[1];
			//displaying new record
			//
			System.out.println( "\n     +-------------------------------------------------+"
					+"\n                **YOUR NEW LOGIN CREDENTIALS**"
					+"\n     +-------------------------------------------------+"
					+"\n      ID:       "+logInCredentials[memberIndex][4]
							+"\n      Name:     "+sentenceCase(logInCredentials[memberIndex][0])
							+"\n      Password: "+logInCredentials[memberIndex][1]
									+"\n     +-------------------------------------------------+"); 
			arrayToMemberFile(logInCredentials);
			return 0;
		default:
			return 0;
		}		
	}

//PAYMENT
	//credit card payment method
	public static void cardPayment(String[][] logInCredentials, String payment, String... credential) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		String name="", expDate="",creditNum="",cvv="";

		//loop to stay in online payment module
		do {
			System.out.println( "\n                          -----------------------------                     \n"
					+ "     +~~~~~~~~~~~~~~~~~~~| O N L I N E   P A Y M E N T |~~~~~~~~~~~~~~~~~~~+\n"
					+ "                          -----------------------------");	
			//input card holder name with validation
			do{
				System.out.print("\tCard Holder Name: ");
				name = input.nextLine().trim().toLowerCase();
				System.out.println(!contentCheck.isNameValid(name)? "\n\tINVALID INPUT: Required Valid Name!\n":"");	
			}while(!contentCheck.isNameValid(name));


			//input card number with validation
			while(true){
				System.out.print("\tCard Number: ");
				creditNum = input.nextLine().trim().toLowerCase(); 
				//using the try catch logic to check if the card number is valid or not
				try {
					long creditCardNum = Long.parseLong(creditNum); 
					//if the input format is correct than check if the input is in range or not
					if (!contentCheck.isCreditCardValid(creditCardNum)){
						System.out.println("\n\tINVALID INPUT: Required Valid Credit Card Number\n");
					}
					//break from infinite while loop
					else {
						break;
					}
				}catch (NumberFormatException e){
					//if the input has a different format than error will be displayed
					System.out.println("\n\tINVALID INPUT: Required Valid Credit Card Number\n");
				}
			}

			//input card expiry date with validation
			do {
				System.out.print("\n\tExpiry Date (mm/yy): ");
				expDate = input.nextLine().trim().toLowerCase();
				System.out.println(!contentCheck.isDateValid(expDate)? "\n\tINVALID INPUT: Required Date In Format (mm/yy)":"");
			}while(!contentCheck.isDateValid(expDate));

			//input cvv with validation
			do {
				//Card Verification Value
				System.out.print("\tCVV: ");									
				cvv = input.nextLine().trim().toLowerCase();
				//cvv length=3, it should be equal to substring of credit number of last 3 digits.
				System.out.println(!(cvv.length()==3 && cvv.equals(creditNum.substring(creditNum.length()-3,creditNum.length())))?
						"\n\tINVALID INPUT: Required Valid Card Verfication Code\n":"");
			}while(!(cvv.length()==3 && cvv.equals(creditNum.substring(creditNum.length()-3,creditNum.length()))));

			System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
			//checking if all the entered information is present in the dummy record
			if (findCreditCardInfo(name, creditNum, expDate)) {
				String amount="";
				System.out.println("\t\t\t     ***Your Total: PKR "+payment+"***");
				System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
				//loop for payment validation. payment should not be in format it can't be parsed to integer and it should be >=amount required
				while(true) {
					System.out.print("\tEnter Amount: ");
					amount = input.nextLine().trim();
					//checks if the entered amount is just combination of numbers or not
					if (!contentCheck.numberValidation(amount) || amount.equals("")) {
						System.out.println("\n\tINVALID INPUT: Required Amount In Numbers Only\n");
					}
					//checks if amount is less than payment and displays a message
					else if (Integer.parseInt(amount)<Float.parseFloat(payment))
						System.out.println("\n\tERROR: Amount is less than the required amount!\n");
					//checks if amount is more than payment and displays a message
					else if (Integer.parseInt(amount)>Float.parseFloat(payment))
						System.out.println("\n\tERROR: Amount is more than the required amount!\n");
					//otherwise payment successful message appears
					else {
						System.out.println("\t\t\t     *** Payment Successful! ***");
						//break from loop asking for amount
						break;
					}
				}
				if (logInCredentials != null) {
					//index 0 passed to enter new record, (name, password, email, phoneNo) as credentials
					int memberIndex = updateLogInCredentials(logInCredentials, 0, credential[0], credential[1], credential[2], credential[3]);
					System.out.println(   "     +---------------------------------------------------------------------+");
					System.out.println(   "      ****CONGRATULATIONS! YOU HAVE BEEN REGISTERED FOR OUR MEMBERSHIP!****");
					System.out.println(   "     +---------------------------------------------------------------------+"
							+"\n                            **YOUR LOGIN CREDENTIALS**"
							+"\n      ID:       "+logInCredentials[memberIndex][4]
									+"\n      Name:     "+sentenceCase(logInCredentials[memberIndex][0])
									+"\n      Password: "+logInCredentials[memberIndex][1]
											+"\n     +--------------------------------------------------------------------+"); 
				}
				//go back to sign up menu
				return;
			}
			//otherwise prints a message displaying invalid information has been entered and user has to 
			//decide whether to go to menu or keep adding more data
			else {
				System.out.println("\n\tINVALID INPUT: Required Valid Credit Card Details");
			}
			//asking to go back to menu or keep entering payment information
		}while(searchAgain("\n\t---> Do you want to enter details again? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. "));	
	}

	//checks if entered payment info i.e card number, expiry date, name etc. are present in record or not
	public static boolean findCreditCardInfo(String... credentials) {
		//each row is checked for entered credentials.
		for(int i=0; i<paymentInfo.length; i++) {
			//keeping row number constant, name(credentials[0]) at index 0, card number(credentials[1])
			//at index 1 and expiry date(credentials[2]) at index 2 are checked for equality and true/false is returned accordingly
			if(paymentInfo[i][0].equalsIgnoreCase(credentials[0]) && paymentInfo[i][1].equals(credentials[1])
					&& paymentInfo[i][2].equalsIgnoreCase(credentials[2]))
				return true;		
		}
		return false;
	}

	//checks for pre-existence of membership data like email, phone number, auto generated id during sign up
	public static boolean findMembershipData(String[][] logInCredentials, String credentialType, String credential) {
		//j is column number for respective credential type
		int j=0;
		//code would be either to check username, email, phoneNo for prior existence in record
		switch (credentialType) {
		case "email":	 j=2; break;
		case "phoneNo":  j=3; break;
		case "id": 		 j=4; 
		}
		//record is checked till last record stored for the presence of credential passed
		for (int i=0; i<lastEntityIndex(logInCredentials); i++) {
			//true/false is returned on the basis of result
			if (logInCredentials[i][j] !=null && logInCredentials[i][j].equals(credential) )
				return true;
		}
		return false;
	}

//LOGIN
	//LogIn method. parameter is the 2D array containing logInCredentials. input -> username/password till correct one is entered
	public static String logIn(String[][] logInCredentials, String code) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);

		switch (code) {
		//login with membership
		case "member":
			String ID, password;
			String choice;     // login menu's choice
			displayMenu("log in");
			//validation check for correct choice entered
			do {
				System.out.print("     Press any option to continue..  ");
				choice = input.nextLine().trim();
				switch(choice) {
				//login
				case "1": 
					do{
						System.out.print( "\n                        -------------                     \n"
								+ "     +~~~~~~~~~~~~~~~~~| L O G - I N |~~~~~~~~~~~~~~~~~+ \n"
								+ "                        -------------                     \n");
						//input id
						System.out.println("\t---> Format: xyz-000");
						ID = Input.ID("\tEnter member ID: ","customer");

						//input password
						System.out.println("\t---> NOTE:\n\t1- Password should contain only letters and numbers\n\t2- Passwords must be atleast 8 characters\n");
						password = Input.password("\tEnter password: ","customer");

						//**login output format					
						//look break if correct input is added
						if (findLoginData(logInCredentials, "login", ID, password)) { 
							System.out.println("\t\t  *** Login Successful! ***");
							System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n");
							return ID; //to go back to main menu
						}
						//else display msg
						else {
							System.out.println("\tERROR: Invalid Username/Password!");
							//asking to go back to menu for changing password if user wants
							if(!searchAgain("\n\t---> Do you want to enter details again? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")) {
								displayMenu("log in");
								//line break for formatted display
								System.out.println();
								break;
							}	
						}
					}while(!findLoginData(logInCredentials, "login", ID, password));
					break;
					//forgot password
				case "2":
					forgotPassword(logInCredentials);
					displayMenu("log in");
					//line break for formatted display
					System.out.println();
					break;
					//go to main menu
				case "0":
					return null;
				default:
					System.out.println(choice.equals("")? "\n     INVALID INPUT: Required Valid Input Which Is Not Null\n":"\n     INVALID INPUT: Required Correct Option Number\n");
				}
			}while(true);
			//regular customer login
		case "regular":
			String name;
			System.out.print( "\n                        -------------                     \n"
					+ "     +~~~~~~~~~~~~~~~~~| L O G - I N |~~~~~~~~~~~~~~~~~+ \n"
					+ "                        -------------                     \n");
			//input name
			name = Input.name("\tEnter your name: ");
			System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n");
			return name;
		}
		return null;
	}

	//checks if data entered at logIn/forgot password is stored in array or not.
	public static boolean findLoginData(String[][] logInCredentials, String code, String... information) {
		//code: "login" for login block; "forgot" for forgot login block
		switch (code) {
		case "login":
			//each row is checked for entered credentials. array is checked till last record stored
			for (int i=0; i<lastEntityIndex(logInCredentials); i++ ) {
				//keeping row number constant, id(info[0]) at index 4 and password(info[1]) at index 1 is checked
				//for equality and true/false is returned accordingly
				if (logInCredentials[i][4].equals(information[0]) && logInCredentials[i][1].equals(information[1]))
					return true;
			}
			return false;	
		case "forgot":
			//each row is checked for entered credentials. array is checked till last record stored
			for (int i=0; i<lastEntityIndex(logInCredentials); i++ ) {
				//keeping row number constant, ID(info[0]) at index 4, email(info[1]) at index 2 and phoneNo(info[2]) at index 3 
				//is checked for equality and true/false is returned accordingly
				if (logInCredentials[i][4].equals(information[0]) && logInCredentials[i][2].equals(information[1])
						&& logInCredentials[i][3].equals(information[2]))
					return true;
			}
			return false;	
		default: 
			return false;
		}					
	}

	//returns index next to the index of last record stored
	public static int lastEntityIndex(String[][] logInCredentials) {
		for (int i=0; i<logInCredentials.length; i++) {
			//null row is searched, when found its index is returned
			if (logInCredentials[i][0]==null) 
				return i;		
		}
		//otherwise length of array is sent as it will be full
		return logInCredentials.length;
	}

	//method for changing password when credentials are forgotten
	public static void forgotPassword(String[][] logInCredentials) throws FileNotFoundException {
		String ID,password,email,phoneNo;
		do {
			System.out.print("              ---------------------------------                        \n"
					+ "     +~~~~~~~~| F O R G O T   P A S S W O R D |~~~~~~~~+\n"
					+ "              ---------------------------------                        \n");	
			//taking ID, email and password as input 
			ID = Input.ID("\tEnter ID: ","customer");
			email = Input.email("\tEnter email: ","customer");
			phoneNo = Input.phoneNo("\tEnter phone number: ","customer");

			//checking if credentials are found
			if (findLoginData(logInCredentials, "forgot", ID,email,phoneNo)) {
				//displaying msg
				System.out.println("\t\t    *** Record Found! ***");
				System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n");
				do {
					//asking for new password
					password = Input.password("\tEnter new password: ","customer");
					System.out.println(logInCredentials[memberIndex(logInCredentials, ID)][1].equals(password)?
							"\t---> Enter a new password that isn't your current password\n":"");
				}while(logInCredentials[memberIndex(logInCredentials, ID)][1].equals(password));

				System.out.print("\t\t  *** Password changed! ***");
				//updating recording and it will display new record aswell
				updateLogInCredentials(logInCredentials, 1, ID,password);
				break;
			}
			else {
				System.out.println("\t\t  *** Record Not Found! ***");
				System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n");
			}	
			//asking to go back to menu for changing password if user wants
		}while(searchAgain("\n\t---> Do you want to enter details again? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. "));			
	}

	//returns the row number at which the record is present in logInCredentails[][]
	public static int memberIndex(String[][] logInCredentials, String ID) {
		//searches the record row wise to find the ID 
		for (int i=0; i<lastEntityIndex(logInCredentials); i++) {
			//when ID is found, the row number is passed 
			if (logInCredentials[i][4].equals(ID))
				return i;
		}
		return -1;
	}

//MAIN MENU
	//controls the whole shopping experience. can add product by category, name to the cart. 
	public static void searchMenu(String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials,
			int[][] costQuantityCookingEssentials, String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies,
			int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary, String[][] feedBacks, String[]... memberCredentials) throws IOException {
		Scanner input = new Scanner(System.in);
		// Initializing the cart
		String [][] cart = new String [50][5];

		String option;

		//array that counts in-cart products for indexing
		int[] incartProductNumber= {0};

		displayMenu("search");
		//loop continues if user enters wrong option
		do {
			System.out.print("     Press any option to continue.. ");
			option = input.nextLine().trim();

			switch(option) {
			//search by product name
			case "1":
				//loop if user wants to enter more products i.e. method returns true
				do{
					String[][] substituteArrayWithSimilarNames = new String[100][4];
					String product;
					do {
						//input product name
						System.out.print("\n     Enter a product name: ");
						product = input.nextLine().trim();
						System.out.println(product.length() < 3? "\n     INVALID INPUT: Required String With Minimum Length Of 3":"");
					}while(product.length() < 3);

					int listEndIndex=0;
					//based on the product name, each case of switch will be executed and product will be checked in all categories' arrays
					//if found, quantity will be asked and it will be added to cart
					switch(0) {
					// For each case, add the listed categories and their respective cost and quantity array
					case 0:
						if (searchAndAddProduct("name", ctgDairy, costQuantityDairy, cart, incartProductNumber, product, substituteArrayWithSimilarNames)) 
							break;
					case 1:
						if (searchAndAddProduct("name", ctgCookingEssentials, costQuantityCookingEssentials,cart, incartProductNumber, product, substituteArrayWithSimilarNames))
							break;
					case 2:
						if (searchAndAddProduct("name", ctgBeverages, costQuantityBeverages, cart, incartProductNumber, product, substituteArrayWithSimilarNames)) 
							break;
					case 3:
						if (searchAndAddProduct("name", ctgBathroomSupplies, costQuantityBathroomSupplies, cart, incartProductNumber, product, substituteArrayWithSimilarNames))
							break;
					case 4:
						if (searchAndAddProduct("name",ctgStationary, costQuantityStationary, cart, incartProductNumber, product, substituteArrayWithSimilarNames)) 
							break;
						//the array of possible items is displayed for selection
						else if (substituteArrayWithSimilarNames[0][0] != null) {
							//the array is displayed and its last index value is returned
							listEndIndex=printingProductList(null, null, "search", substituteArrayWithSimilarNames);
							if (searchAgain("       ---> Did you mean any of these products? \n\n\t\t [y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")) {
								do {
									//input product initialization
									String productIndexInList="";
									int indexOfProductInCtg;
									//loop continues if product number entered is not a number, or if it is not within the range of 1-listEndIndex
									do {
										//input of product from the list
										System.out.print("     Enter product number: ");
										productIndexInList = input.nextLine().trim();
										if (!contentCheck.numberValidation(productIndexInList) || productIndexInList.equals("") || Integer.parseInt(productIndexInList) <1 || Integer.parseInt(productIndexInList)>listEndIndex+1)
											System.out.println("\n     INVALID INPUT: Required Correct Product Number\n");
									}while(!contentCheck.numberValidation(productIndexInList) || productIndexInList.equals("") || Integer.parseInt(productIndexInList)<1 || Integer.parseInt(productIndexInList)>listEndIndex+1);

									//based on the category of the product through its ID, product is added to the cart. 
									//product's index in the category is returned through the method getProductIndexInArray()
									if (substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0].contains("D")) {
										indexOfProductInCtg = getProductIndexInArray(ctgDairy, substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0]);
										addProduct(ctgDairy, costQuantityDairy, indexOfProductInCtg, cart, incartProductNumber, substituteArrayWithSimilarNames,Integer.parseInt(productIndexInList)-1);
									}
									else if (substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0].contains("CE")) {
										indexOfProductInCtg = getProductIndexInArray(ctgCookingEssentials, substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0]);
										addProduct(ctgCookingEssentials, costQuantityCookingEssentials, indexOfProductInCtg, cart, incartProductNumber, substituteArrayWithSimilarNames, Integer.parseInt(productIndexInList)-1);
									}
									else if (substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0].contains("BS")) {
										indexOfProductInCtg = getProductIndexInArray(ctgBathroomSupplies, substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0]);
										addProduct(ctgBathroomSupplies, costQuantityBathroomSupplies, indexOfProductInCtg, cart, incartProductNumber, substituteArrayWithSimilarNames, Integer.parseInt(productIndexInList)-1);
									}
									else if (substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0].contains("BE")) {
										indexOfProductInCtg = getProductIndexInArray(ctgBeverages, substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0]);
										addProduct(ctgBeverages, costQuantityBeverages, indexOfProductInCtg, cart, incartProductNumber, substituteArrayWithSimilarNames, Integer.parseInt(productIndexInList)-1);
									}
									else if (substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0].contains("ST")) {
										indexOfProductInCtg = getProductIndexInArray(ctgStationary, substituteArrayWithSimilarNames[Integer.parseInt(productIndexInList)-1][0]);
										addProduct(ctgStationary, costQuantityStationary, indexOfProductInCtg, cart, incartProductNumber, substituteArrayWithSimilarNames,Integer.parseInt(productIndexInList)-1);
									}

									//if user wants to add more products, the updated list of products is displayed again...
									if (searchAgain("     ---> Do you want to add another product? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. "))
										//the array is displayed and its last index value is returned
										listEndIndex=printingProductList(null, null, "search", substituteArrayWithSimilarNames);
									//else we will go to search menu
									else 
										break;
								}while(true);
							}
							break;
						}	
					default: 
						System.out.println("\n     ***ERROR: Product Not Found!***");
					}

					//method asks from user if they want to search the product again
					if (!searchAgain("     ---> Do you want to search another product by name? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")) {
						displayMenu("search");
						break;
					}
				}while(true); 
				break;
				//search by product category
			case "2": 
				displayMenu("category");
				//loop if user wants to search product from another category i.e. function returns true
				do {
					String category;
					//loop continues if category entered is not number, or if it is not within the range of 1-5
					do{
						System.out.print("     Select any category to continue..  ");
						category = input.nextLine().trim();
						if (category.equals("") || !contentCheck.numberValidation(category) || !(Integer.parseInt(category)>=0 && Integer.parseInt(category)<=5))
							System.out.println(category.equals("")? "\n     INVALID INPUT: Required Valid Input Which Is Not Null\n" : "\n     INVALID INPUT: Required Correct Option Number\n");
					}while(category.equals("") || !contentCheck.numberValidation(category) || !(Integer.parseInt(category)>=0 && Integer.parseInt(category)<=5));

					//goes to search menu
					if (category.equals("0")) {
						displayMenu("search");
						break;
					}

					//loop continues if user wants to add another product of same category i.e. function returns true
					do{
						//variable whose value is returned as the last index+1 in the category list
						int listEndIndex=0;
						//prints the respective category products in a list
						switch(category) {
						case "1": listEndIndex= printingProductList(ctgDairy, costQuantityDairy,"dairy", null); break;
						case "2": listEndIndex= printingProductList(ctgCookingEssentials, costQuantityCookingEssentials,"cookingEssentials", null); break;
						case "3": listEndIndex= printingProductList(ctgBeverages, costQuantityBeverages,"beverages", null); break;
						case "4": listEndIndex= printingProductList(ctgBathroomSupplies, costQuantityBathroomSupplies,"bathroomSupplies", null);break;
						case "5": listEndIndex= printingProductList(ctgStationary, costQuantityStationary,"stationary", null) ;break;
						}
						//input product initialization
						String productIndex="";
						//loop continues if product number entered is not a number, or if it is not within the range of 1-listEndIndex
						do {
							//input of product from the list
							System.out.print("       ---> Enter product number or press 0 to go back: ");
							productIndex = input.nextLine().trim();
							if (!contentCheck.numberValidation(productIndex) || productIndex.equals("") || Integer.parseInt(productIndex)<0 || Integer.parseInt(productIndex)>listEndIndex+1)
								System.out.println("\n       INVALID INPUT: Required Correct Product Number");
						}while(!contentCheck.numberValidation(productIndex) || productIndex.equals("") || Integer.parseInt(productIndex)<0 || Integer.parseInt(productIndex)>listEndIndex+1);

						//goes back to categories
						if (productIndex.equals("0")) {
							break;
						}

						//Depending on the product entered, the product is added in the cart
						switch(category) {
						case "1": searchAndAddProduct("category", ctgDairy, costQuantityDairy, cart, incartProductNumber, productIndex, null); break;
						case "2": searchAndAddProduct("category", ctgCookingEssentials, costQuantityCookingEssentials,cart, incartProductNumber, productIndex, null); break;
						case "3": searchAndAddProduct("category", ctgBeverages, costQuantityBeverages, cart, incartProductNumber, productIndex, null); break;
						case "4": searchAndAddProduct("category", ctgBathroomSupplies, costQuantityBathroomSupplies, cart, incartProductNumber, productIndex, null); break;
						case "5": searchAndAddProduct("category", ctgStationary, costQuantityStationary, cart, incartProductNumber, productIndex, null); break;
						}
					}while(searchAgain("\n\n     ---> Do you want to add another product? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. "));

					if (searchAgain("     ---> Do you want to select another category? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")){
						displayMenu("category");
					}
					else {
						displayMenu("search");
						break;
					}
				}while(true); //function asks from user if they want to search the product again
				break;
				//go to cart
			case "3":
				if (isCartEmpty(cart)) {
					System.out.println(  "          +---------------------------------------+\n"
							+"          |         *** CART IS EMPTY ***         |\n"
							+"          +---------------------------------------+\n");
					displayMenu("search");
				}
				else {
					String choice;
					//displaying cart
					displayCart(cart);
					displayMenu("cart");
					do {
						//INPUT CART CHOICE
						System.out.print("     Press any option to continue.. ");
						choice = input.nextLine().trim(); //declaring variable to get option input
						//deleting the cart by making each index in cart array null
						switch(choice) {
						//delete cart
						case "1":
							for (int i = 0; i < cart.length; i++) {
								if (cart[i][0] != null) {
									//returning the product quantity back to the cost quantity array specified with the product
									if (cart[i][3].contains("D")) {
										returnQuantity(cart,ctgDairy,costQuantityDairy, i+1);
									}
									else if (cart[i][3].contains("CE")) {
										returnQuantity(cart,ctgCookingEssentials,costQuantityCookingEssentials, i+1);
									}
									else if (cart[i][3].contains("BS")) {
										returnQuantity(cart,ctgBathroomSupplies,costQuantityBathroomSupplies, i+1);
									}
									else if (cart[i][3].contains("BE")) {
										returnQuantity(cart,ctgBeverages,costQuantityBeverages, i+1);
									}
									else if (cart[i][3].contains("ST")) {
										returnQuantity(cart,ctgStationary,costQuantityStationary, i+1);
									}
								}								
							}
							if (cart[0][0] == null) {
								System.out.println(  "\t    +------------------------------------------+\n"
										+"\t    |    *** YOUR CART IS ALREADY EMPTY ***    |\n"
										+"\t    +------------------------------------------+");
							}
							else {
								emptyCart(cart);
								incartProductNumber[0]=0;
								System.out.println(  "\t    +------------------------------------+\n"
										+"\t    |    *** YOUR CART IS DELETED ***    |\n"
										+"\t    +------------------------------------+");
							}
							//goes to search menu
							displayMenu("search");
							break; 
							//update product quantity
						case "2":
							//calling UpdateProducts method
							updateCartProducts(incartProductNumber,cart,ctgDairy,costQuantityDairy,ctgCookingEssentials,costQuantityCookingEssentials, 
									ctgBeverages,costQuantityBeverages,ctgBathroomSupplies,costQuantityBathroomSupplies,ctgStationary,costQuantityStationary);
							displayMenu("cart");
							break;
						case "3": 
							//if cart is not empty after removal of product, control goes to cart menu
							//choice is given value 2 because at choice == 2, loop that controls cart menu will not be terminated
							//and cart menu is displayed
							if (!removeCartProducts(incartProductNumber,cart,ctgDairy,costQuantityDairy,ctgCookingEssentials,costQuantityCookingEssentials, 
									ctgBeverages,costQuantityBeverages,ctgBathroomSupplies,costQuantityBathroomSupplies,ctgStationary,costQuantityStationary) == true) {
								choice="2";
								displayMenu("cart");
							}
							//else loop terminates and control goes to search menu
							else
								displayMenu("search");
							break;
							//checkout
						case "4":
							//if false is returned, choice's value is changed to 2, control will go into case "4" and break statement will execute. 
							//  Since do while loop's condition says that till 1,3,4 is not entered, loop will continue. So cart menu will be displayed again
							//if true is returned, control will go into case "4" and break statement will execute and do while loop will be terminated to display search menu
							if (displayBill(cart,ctgDairy,costQuantityDairy,ctgCookingEssentials,costQuantityCookingEssentials,ctgBeverages,costQuantityBeverages,
									ctgBathroomSupplies,costQuantityBathroomSupplies,ctgStationary,costQuantityStationary, memberCredentials[0])==false) {
								choice="2";
								displayMenu("cart");
							}
							else
								askFeedBack(feedBacks);

							//goes to search menu
						case "5": 
							//if choice == -1, only cart menu will be displayed
							if (!choice.equals("2"))
								displayMenu("search");
							break; 
							//invalid input
						default:
							if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"))
								System.out.println(choice.equals("")? "\n     INVALID INPUT: Required Valid Input Which Is Not Null\n":
										"\n     INVALID INPUT: Required Correct Option Number\n");
						}
						//choice = "2" is missing, because we want to go back to cart menu instead of search menu
					}while(!choice.equals("1") && !choice.equals("3") && !choice.equals("4")  && !choice.equals("5"));
				}
				break;
				//logout
			case "0":
				displayMenu("main");
				break; 
			default: 
				System.out.println(option.equals("")? "\n     INVALID INPUT: Required Valid Input Which Is Not Null\n":
						"\n     INVALID INPUT: Required Correct Option Number\n");
			}
		}while(!(option.equals("0")));					
	}

	public static int getProductIndexInArray(String [][] productArrayByCategory, String productID) {
		for (int i=0; i<productArrayByCategory.length; i++) {
			if(productID.equals(productArrayByCategory[i][0])) {
				return i;
			}
		}
		return -1;
	}

	//asks user if they want to search for a product again
	public static boolean searchAgain(String string) {
		Scanner input = new Scanner(System.in);
		String option =""; 
		//asking if customer wants to add another product by name
		do {
			System.out.print(string);
			option = input.nextLine().toLowerCase().trim();
			System.out.println(!(option.equals("y") || option.equals("n"))? "\n\tERROR: INVALID ENTRY!\n":"");
		}while(!(option.equals("y") || option.equals("n")));

		return option.equals("y")? true: false;
	}

	//prints the products in the category and returns last index of list
	public static int printingProductList(String [][] productArrayByCategory, int [][] costQuantityArrayByCategory, String category, String[][] substituteArrayWithSimilarNames) {
		//displays heading
		switch (category) {
		case "dairy": 
			System.out.print("\n                              ---------------------------------------\n"
					+ " +---------------------------|            D   A   I   R   Y          |--------------------------+\n"
					+ "                              --------------------------------------- ");break;
		case "cookingEssentials":
			System.out.print("\n                    --------------------------------------------------------\n"
					+ " +-----------------|  C  O  O  K  I  N  G    E  S  S  E  N  T  I  A  L  S   |-------------------+\n"
					+ "                    -------------------------------------------------------- ");break;
		case "beverages":
			System.out.print("\n                             ----------------------------------------\n"
					+ " +--------------------------|       B  E  V  E  R  A  G  E  S        |--------------------------+\n"
					+ "                             ---------------------------------------- ");break;
		case "bathroomSupplies":
			System.out.print("\n                    ---------------------------------------------------------\n"
					+ " +-----------------|     B  A  T  H  R  O  O  M   S  U  P  P  L  I  E  S     |------------------+\n"
					+ "                    ---------------------------------------------------------");break;
		case "stationary":
			System.out.print("\n                            ----------------------------------------\n"
					+ " +-------------------------|      S  T  A  T  I  O  N  A  R  Y      |---------------------------+\n"
					+ "                            ---------------------------------------- ");break;
		case "search":
			System.out.print("                            ----------------------------------------\n"
					+ " +-------------------------|     S E A R C H E D  R E S U L T S     |---------------------------+\n"
					+ "                            ---------------------------------------- ");break;
		}
		//displays products in formatted output
		System.out.println("\n       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("       |  %-3s%-39s|  %-9s|   %-7s|  %-8s  |","No. |"," Product","ProdID","Cost","In Stock");	
		System.out.println("\n       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//variable initialized outside for loop, so we could return it
		int i;
		if (!category.equals("search")) {
			for (i=0; i<productArrayByCategory.length && productArrayByCategory[i][0] != null; i++) {
				System.out.printf("       |  %-4s| %-38s|   %-8s| PKR %-5d|    %-7d |",i+1,productArrayByCategory[i][1],productArrayByCategory[i][0],costQuantityArrayByCategory[i][0],costQuantityArrayByCategory[i][1]);
				System.out.println("\n       ------------------------------------------------------------------------------------");
			}
		}
		else {
			for (i=0; i<substituteArrayWithSimilarNames.length; i++) {
				if (substituteArrayWithSimilarNames[i][0] != null) {
					System.out.printf("       |  %-4s| %-38s|   %-8s| PKR %-5s|    %-7s |",i+1,substituteArrayWithSimilarNames[i][1],substituteArrayWithSimilarNames[i][0],substituteArrayWithSimilarNames[i][2],substituteArrayWithSimilarNames[i][3]);
					System.out.println("\n       ------------------------------------------------------------------------------------");
				}
				else {
					break;
				}

			}
		}
		return i-1; // returns last index of printed list
	}

	//searches for product by name and adds the product to cart
	//adds the product to cart, if searching by category
	public static boolean searchAndAddProduct(String method, String [][] productArrayByCategory, int [][] costQuantityArrayByCategory, String cart [][], int[] incartProductNumber, String product, String[][] substituteArrayWithSimilarNames) {

		switch(method) {
		//product name is send, it searches index by index to find the product existence
		case "name":

			// loop checks in each category passed to find the exact product entered by the user
			for (int i =0; i<productArrayByCategory.length && productArrayByCategory[i][0] != null; i++) {
				//removes all the spaces in user input and the original product name.
				product = product.replaceAll(" ", "").toLowerCase();
				String Available_product=productArrayByCategory[i][1].replaceAll(" ", "").toLowerCase();

				//Ignoring the string cases and checking product availability. returns true/false on availability basis and adds products to cart
				if ((product.equals(Available_product)) && costQuantityArrayByCategory[i][1]>0) {
					//array is initialized back to null
					substituteArrayWithSimilarNames = new String[100][4];
					addProduct(productArrayByCategory, costQuantityArrayByCategory, i ,cart, incartProductNumber, null, -1);
					return true;
				}
				else if ((product.equals(Available_product)) && costQuantityArrayByCategory[i][1]==0) {
					//array is initialized back to null
					substituteArrayWithSimilarNames = new String[100][4];
					System.out.println("\n\t\t*** Product out of stock! ***");
					return true;
				}
				else if (Available_product.contains(product)) {
					addProductToSubstituteArray(substituteArrayWithSimilarNames, productArrayByCategory[i], costQuantityArrayByCategory[i] );
				}
			}
			break;
			//product number is send, directly product is added since user can only enter a number from the list displayed
		case "category":
			addProduct(productArrayByCategory, costQuantityArrayByCategory, Integer.parseInt(product)-1 ,cart, incartProductNumber, null, -1);
			break;
		}
		//if product not found, returns false
		return false;
	}

	//creating substitute Array With products having Similar Names as user entered
	//arguments have 1D Arrays. as the product row in 2-D is searched in the previous block and that row is passed to this function
	public static void addProductToSubstituteArray(String[][] substituteArrayWithSimilarNames, String[] productArrayByCategory, int[] costQuantityArrayByCategory) {
		for (int i=0; i<substituteArrayWithSimilarNames.length; i++) {
			if (substituteArrayWithSimilarNames[i][0] == null) {
				//product ID
				substituteArrayWithSimilarNames[i][0] = productArrayByCategory[0];
				//product name
				substituteArrayWithSimilarNames[i][1] = productArrayByCategory[1];
				//product cost
				substituteArrayWithSimilarNames[i][2] = Integer.toString(costQuantityArrayByCategory[0]);
				//product quantity
				substituteArrayWithSimilarNames[i][3] = Integer.toString(costQuantityArrayByCategory[1]);
				break;
			}
		}
	}

	//adds product to cart
	public static void addProduct(String[][] productArrayByCategory, int[][] costQuantityArrayByCategory, int indexOfProductInCtg, String[][] cart, int[] incartProductNumber, String[][] substituteArrayWithSimilarNames, int productIndexInList) {
		Scanner input = new Scanner(System.in);
		String quantity; // Gets the quantity of the product from the user and stores it in the cart
		// Displaying the product details
		System.out.printf("     +-------------------------------------------------------+\n"
				+ "                    P R O D U C T   D E T A I L S               "
				+ "\n     |-------------------------------------------------------|"
				+ "\n       Product ID     |  " + productArrayByCategory[indexOfProductInCtg][0]
						+ "\n     |----------------+--------------------------------------|"
						+ "\n       Product Name   |  " + productArrayByCategory[indexOfProductInCtg][1]
								+ "\n     |----------------+--------------------------------------|"
								+ "\n       Cost           |  PKR " + costQuantityArrayByCategory[indexOfProductInCtg][0] + "/-"
								+ "\n     |----------------+--------------------------------------|"
								+ "\n       Quantity       |  " + costQuantityArrayByCategory[indexOfProductInCtg][1]
										+ "\n     +-------------------------------------------------------+\n");

		//for quantity validation. quantity should be a number and less than or equal to available quantity
		//if 0, product will not be entered in the cart. move back to product name entry
		//if >0, product info entered into cart
		while (true) {
			if ((costQuantityArrayByCategory[indexOfProductInCtg][1] != 0)) {
				//quantity input
				System.out.print("     Enter Quantity: ");
				quantity = input.nextLine().trim();
			}
			else {
				System.out.println("\n\t\t*** Product out of stock! ***\n");
				break;
			}

			//quantity validation
			if (!contentCheck.numberValidation(quantity) || quantity.equals("")) 
				System.out.println("\n     ERROR: INVALID ENTRY!\n");
			//if quantity ==0, move back to product name entry part
			else if (Integer.parseInt(quantity) == 0)
				break;
			//checking is entered quantity is available or not
			else if (Integer.parseInt(quantity) > costQuantityArrayByCategory[indexOfProductInCtg][1])
				System.out.println("\n     The amount you entered is currently unavailable!\n");
			//if valid quantity is entered, quantity of product is added, and product info will be entered to cart

			//if the product is already present in the cart than selected quantity will be added to its previous quantity
			else if (productIndexInCart(cart,productArrayByCategory,indexOfProductInCtg) != -1){
				costQuantityArrayByCategory[indexOfProductInCtg][1] = costQuantityArrayByCategory[indexOfProductInCtg][1] - Integer.parseInt(quantity);
				//if the substitute array is not empty then product's quantity is changed in it as well
				if (substituteArrayWithSimilarNames != null) {
					substituteArrayWithSimilarNames[productIndexInList][3] = Integer.toString(Integer.parseInt(substituteArrayWithSimilarNames[productIndexInList][3])-Integer.parseInt(quantity));
				}

				int productIndex = productIndexInCart(cart,productArrayByCategory,indexOfProductInCtg);
				int addedQuantity = Integer.parseInt(cart[productIndex][2]) + Integer.parseInt(quantity);
				cart[productIndex][2] = Integer.toString(addedQuantity);
				System.out.println("\n\t     *** Product added to the cart! ***\n");
				break;
			}
			//else complete product info will be entered in the cart
			else {
				//incartProductNumber[0] is used for cart indexing. 
				costQuantityArrayByCategory[indexOfProductInCtg][1] = costQuantityArrayByCategory[indexOfProductInCtg][1] - Integer.parseInt(quantity);
				//if the substitute array is not empty then product's quantity is changed in it
				if (substituteArrayWithSimilarNames != null) {
					substituteArrayWithSimilarNames[productIndexInList][3] = Integer.toString(Integer.parseInt(substituteArrayWithSimilarNames[productIndexInList][3])-Integer.parseInt(quantity));
				}

				cart[incartProductNumber[0]][0] = productArrayByCategory[indexOfProductInCtg][1];
				cart[incartProductNumber[0]][1] = Integer.toString(costQuantityArrayByCategory[indexOfProductInCtg][0]);
				cart[incartProductNumber[0]][2] = quantity;
				cart[incartProductNumber[0]][3] =  productArrayByCategory[indexOfProductInCtg][0];
				cart[incartProductNumber[0]][4] = Integer.toString(costQuantityArrayByCategory[indexOfProductInCtg][1]);
				//increment cart value by 1
				incartProductNumber[0] +=1;
				System.out.println("\n\t     *** Product added to the cart! ***\n");
				break;
			}
		}
	}

	//METHOD TO GET THE PRODUCT INDEX IN CART
	public static int productIndexInCart (String [][] cart, String [][]productArrayByCategory,int indexOfProduct) {
		int productIndex = -1;
		for (int i = 0; i < cart.length; i++) {
			if (cart[i][0] != null && cart[i][0].equals(productArrayByCategory[indexOfProduct][1])) {
				productIndex = i;
				break;
			}
		}
		return (productIndex);
	}

	//METHOD TO UPDATE PRODUCTS' QUANTITY IN THE CART
	public static void updateCartProducts(int[] incartProductNumber, String[][] cart,String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials, int[][] costQuantityCookingEssentials, 
			String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies, int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary) {
		Scanner input = new Scanner (System.in);
		//loop which will continue if the user wants to update the products again and again
		String prodListIndex;
		int productNum;
		displayCart(cart);
		//prodListIndex validation
		while(true){
			//taking input for the product user wants to update
			System.out.print("     --> Enter product number to be updated: ");
			prodListIndex= input.nextLine().trim();
			//using the try catch logic to check if the product number is valid or not
			try {
				productNum = Integer.parseInt(prodListIndex); 
				//if the input format is correct than check if the input is in range or not
				if (productNum < 1 || productNum > incartProductNumber[0]) {
					System.out.println("\n     INVALID INPUT: Required Correct Product Number\n");
				}
				//break from infinite while loop
				else {
					break;
				}
			}catch (NumberFormatException e){
				//if the input has a different format than error will be displayed
				System.out.println("\n     INVALID INPUT: Required Correct Product Number\n");
			}
		}

		int currentAvailableQuantity = 0;
		//returning the product quantity back to the cost quantity array specified with the product
		if ((cart[productNum-1][3]).contains("D")) {
			currentAvailableQuantity = returnQuantity(cart,ctgDairy,costQuantityDairy, productNum);
		}
		else if ((cart[productNum-1][3]).contains("CE")) {
			currentAvailableQuantity = returnQuantity(cart,ctgCookingEssentials,costQuantityCookingEssentials, productNum);
		}
		else if ((cart[productNum-1][3]).contains("BS")) {
			currentAvailableQuantity = returnQuantity(cart,ctgBathroomSupplies,costQuantityBathroomSupplies, productNum);
		}
		else if ((cart[productNum-1][3]).contains("BE")) {
			currentAvailableQuantity = returnQuantity(cart,ctgBeverages,costQuantityBeverages, productNum);
		}
		else if ((cart[productNum-1][3]).contains("ST")) {
			currentAvailableQuantity = returnQuantity(cart,ctgStationary,costQuantityStationary, productNum);
		}

		String updatedQuantity;
		//checking if the quantity entered by the user is available or not
		while(true) {
			System.out.print("\n     ---> Enter the product quantity you want: ");
			updatedQuantity = input.nextLine().trim(); //taking quantity input
			//checking if the quantity entered by the user is available or not
			if(updatedQuantity.equals("")) {
				System.out.println( "\n\tINVALID INPUT: Required Valid Input Which Is Not Null\n ");
			}
			else if (updatedQuantity.equals("0")) {
				System.out.println( "\n\tINVALID INPUT: Required Valid Input Which Can't Be 0\n ");
			}
			else if(Integer.parseInt(updatedQuantity) < currentAvailableQuantity || Integer.parseInt(updatedQuantity) >= 1) {
				break;
			}
			else {
				System.out.println( "\n\tThe amount you entered is currently unavailable!\n ");
			}	
		}
		//through product category in productID, the quantity of product in main inventory is updated and 
		//new quantity is stored in cart.
		if ((cart[productNum-1][3]).contains("D")) {
			quantityUpdate(cart,ctgDairy,costQuantityDairy, productNum,Integer.parseInt(updatedQuantity));
		}
		else if ((cart[productNum-1][3]).contains("CE")) {
			quantityUpdate(cart,ctgCookingEssentials,costQuantityCookingEssentials, productNum,Integer.parseInt(updatedQuantity));
		}
		else if ((cart[productNum-1][3]).contains("BS")) {
			quantityUpdate(cart,ctgBathroomSupplies,costQuantityBathroomSupplies, productNum,Integer.parseInt(updatedQuantity));
		}
		else if ((cart[productNum-1][3]).contains("BE")) {
			quantityUpdate(cart,ctgBeverages,costQuantityBeverages,productNum,Integer.parseInt(updatedQuantity));
		}
		else if ((cart[productNum-1][3]).contains("ST")) {
			quantityUpdate(cart,ctgStationary,costQuantityStationary, productNum,Integer.parseInt(updatedQuantity));
		}
		cart[productNum-1][2] = updatedQuantity ; //replacing the existing quantity with the updated quantity


		if (isCartEmpty(cart)) {
			System.out.println(   "          +---------------------------------------+\n"
					+ "          |         *** CART IS EMPTY ***         |\n"
					+ "          +---------------------------------------+");
		}
		else {
			displayCart(cart);
		}
	}

	//METHOD TO REMOVE PRODUCTS IN THE CART
	public static boolean removeCartProducts(int[] incartProductNumber, String[][] cart,String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials, int[][] costQuantityCookingEssentials, 
			String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies, int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary) {
		Scanner input = new Scanner (System.in);
		String prodListIndex;
		int productNum;
		displayCart(cart);
		//prodListIndex validation
		while(true){
			//taking input for the product user wants to update
			System.out.print("     --> Enter product number you want to remove: ");
			prodListIndex= input.nextLine().trim();
			//using the try catch logic to check if the product number is valid or not
			try {
				productNum = Integer.parseInt(prodListIndex); 
				//if the input format is correct than check if the input is in range or not
				if (productNum < 1 || productNum > incartProductNumber[0]) {
					System.out.println("\n     INVALID INPUT: Required Correct Product Number\n");
				}
				//break from infinite while loop
				else {
					break;
				}
			}catch (NumberFormatException e){
				//if the input has a different format than error will be displayed
				System.out.println("\n     INVALID INPUT: Required Correct Product Number\n");
			}
		}
		incartProductNumber[0] -=1;
		//returning the product quantity back to the cost quantity array specified with the product
		if ((cart[productNum-1][3]).contains("D")) {
			returnQuantity(cart,ctgDairy,costQuantityDairy, productNum);
		}
		else if ((cart[productNum-1][3]).contains("CE")) {
			returnQuantity(cart,ctgCookingEssentials,costQuantityCookingEssentials, productNum);
		}
		else if ((cart[productNum-1][3]).contains("BS")) {
			returnQuantity(cart,ctgBathroomSupplies,costQuantityBathroomSupplies, productNum);
		}
		else if ((cart[productNum-1][3]).contains("BE")) {
			returnQuantity(cart,ctgBeverages,costQuantityBeverages, productNum);
		}
		else if ((cart[productNum-1][3]).contains("ST")) {
			returnQuantity(cart,ctgStationary,costQuantityStationary, productNum);
		}
		/*This loop will update the current cart array. It will begin from the index 
						 of the product which the user wants to remove and will start by replacing it 
						 with the next product and this process will continue till the index of the 
						 second last product. 
		 */
		for (int i = productNum- 1; i < cart.length - 1; i++) {
			cart [i] = cart [i + 1];
		}
		/*The above loop will remove the product from the array but the last product will 
						 appear twice in the array. To remove this problem we will update the last product 
						 as null which will be neglected while printing the cart in the DisplayCart method.
		 */
		cart [cart.length - 1][0] = null;

		if (isCartEmpty(cart)) {
			System.out.println(   "          +---------------------------------------+\n"
					+ "          |         *** CART IS EMPTY ***         |\n"
					+ "          +---------------------------------------+");
			return true;
		}
		else {
			displayCart(cart);
			return false;
		}
	}

	//METHOD TO RETURN THE QUANTITY BACK IF THE USER DOES NOT BUY THE PRODUCT
	public static int returnQuantity (String [][] cart, String[][] productArrayByCategory, int[][] costQuantityArrayByCategory, int productNum) {
		int currentAvailableQuantity = 0;
		for (int i = 0; i < productArrayByCategory.length && productArrayByCategory[i][0] != null; i++) {
			if (productArrayByCategory[i][1].equals(cart[productNum - 1][0])){
				costQuantityArrayByCategory [i][1] += Integer.parseInt(cart[productNum - 1][2]);
				currentAvailableQuantity = costQuantityArrayByCategory [i][1];
			}
		}
		return (currentAvailableQuantity);
	}

	//updates the quantity of product in the cart by passing the list index at which the product is placed
	public static void quantityUpdate(String [][] cart, String[][] productArrayByCategory, int[][] costQuantityArrayByCategory, int productNum, int updatedQuantity) {
		for (int i = 0; i < productArrayByCategory.length && productArrayByCategory[i][0] != null; i++) {
			if (productArrayByCategory[i][1].equals(cart[productNum - 1][0])) {
				costQuantityArrayByCategory [i][1] -= updatedQuantity;
			}
		}
	}

	//METHOD TO CHECK IF THE CART IS EMPTY OR NOT
	public static boolean isCartEmpty(String[][] cart) {
		boolean cartEmpty = false;
		for (int i = 0; i < cart.length; i++) {
			//checking each cart index
			if (cart[i][0] == null){
				cartEmpty = true; //making cartEmpty true if the index is null
			}
			else {
				cartEmpty = false; //making cartEmpty false if the index is not null
				break; //breaking the loop if even a single index is not empty
			}
		}
		return cartEmpty;
	}

	//METHOD TO EMPTY CART
	public static String[][] emptyCart(String[][] cart){
		for (int i = 0; i < cart.length; i++) {
			//making each cart index null
			if (cart [i][0] != null) {
				cart [i][0] = null;
			}
		}
		return cart;
	}

	//METHOD TO DISPLAY THE CART
	public static void displayCart(String[][] cart) {
		//Cart heading
		System.out.print("\n                                   ---------------------------                            \n"
				+ "      +---------------------------|         C  A  R  T        |--------------------------+\n"
				+ "                                   ---------------------------                             ");
		System.out.println("\n     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
		System.out.printf("     |  %-3s%-39s|    %-9s| %-7s |   %-8s |","No. |"," Product","Price","Quantity","Total");	
		System.out.println("\n     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	

		//DISPLAYING CART PRODUCTS
		int subtotal = 0; //variable for calculating the total amount
		for (int i = 0; i < cart.length; i++) {
			if (cart [i][0] != null) {
				//variable for calculating the the price of each product according to the quantity
				int total = (Integer.parseInt(cart[i][1])*Integer.parseInt(cart[i][2]));
				//adding price of each product to the total amount
				subtotal += total;
				//displaying each product in the cart along with price of single product,quantity and total price wrt quatity
				System.out.printf("     |  %-4d| %-38s| PKR %-8s|    %-5s | PKR %-7d|",i+1,cart[i][0],cart[i][1],cart[i][2],total);
				System.out.println("\n     +------------------------------------------------------------------------------------+");
			}
		}
		//displaying the subtotal amount
		System.out.printf("     |   %65s %8s%4d   |","SUBTOTAL"," PKR ",subtotal);
		System.out.println("\n     |                    Tax Included. Shipping Calculated At Checkout.                  |");
		System.out.println("     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
	}

	//METHOD TO DISPLAY BILL
	public static boolean displayBill(String[][] cart,String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials,
			int[][] costQuantityCookingEssentials, String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies, 
			int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary,String[]... memberCredentials ) throws IOException {
		Scanner input = new Scanner(System.in);
		String [] cities = {"Rawalpindi","Islamabad","Lahore","Karachi","Faisalabad"};
		boolean isOnlinePayment=false;
		//taking contact information
		System.out.print("                                   ---------------------------\n"
				+ "      +---------------------------|  C  H  E  C  K  O  U  T   |--------------------------+\n"
				+ "                                   ---------------------------\n ");
		String name = sentenceCase(memberCredentials[0][0]); 
		//address input	
		String address = sentenceCase(Input.address("\t---> Address: "));
		//email input
		String email = (memberCredentials[0].length == 1)? Input.email("\t---> Email: ","customer"):memberCredentials[0][1];
		//contact input
		String contact = (memberCredentials[0].length == 1)? Input.phoneNo("\t---> Contact No.: ","customer"):memberCredentials[0][2];
		//input city
		String city;

		System.out.println( "\t+-----------------------+\n"
				+"\t|    SELECT YOUR CITY   |\n"
				+"\t+-----------------------+");
		//printing the city names and their number
		for (int i = 0; i < cities.length; i++) {
			System.out.printf("\t| Press %d: %-12s |\n",i+1,cities[i]);
		}
		System.out.print("\t+-----------------------+\n\t---> "); 
		while (true) {

			city = input.nextLine().trim(); 
			if (city.equals("1") || city.equals("2") || city.equals("3") || city.equals("4") || city.equals("5")){
				break;
			}
			else {
				System.out.println(city.equals("")? "\n\tINVALID INPUT: Required Valid Input Which Is Not Null " : "\n\tINVALID INPUT: Required Correct Option Number");
				System.out.print("\t---> "); 
			}
		}

		//payment method input
		String paymentMethod;
		System.out.println( "\t+------------------------------+\n"
				+"\t|    SELECT PAYMENT METHOD     |\n"
				+"\t+------------------------------+");
		System.out.println( "\t| Press 1: Cash On Delivery    |\n"
				+"\t| Press 2: Online Payment      |\n"
				+"\t| Press 0: Go to Cart Menu     |\n"
				+"\t+------------------------------+");
		System.out.print("\t---> "); 
		while (true) {
			paymentMethod = input.nextLine().trim();
			if (paymentMethod.equals("1") || paymentMethod.equals("2")) {
				break;
			}
			//condition to print cart menu
			else if (paymentMethod.equals("0")) {
				return false;
			}
			else {
				System.out.println(paymentMethod.equals("")? "\n\tINVALID INPUT: Required Valid Input Which Is Not Null" : "\n\tINVALID INPUT: Required Correct Option Number");
				System.out.print("\t---> "); 
			}
		}

		/*DISPLAYING BILL*/
		System.out.print("                                   ---------------------------\n"
				+ "      +---------------------------|    I  N  V  O  I  C  E    |--------------------------+\n"
				+ "                                   ---------------------------\n ");
		System.out.print("     Member ID      --> ");
		//there will at least be name present even if customer is not a member
		System.out.println((memberCredentials[0].length!=1)? memberCredentials[0][3]:"---");
		System.out.println("      Name 	     --> "+name);
		System.out.println("      Address        --> "+address);
		System.out.println("      City 	     --> "+cities[Integer.parseInt(city)-1]);
		System.out.println("      Email 	     --> "+email);
		System.out.println("      Phone          --> "+contact);
		if (paymentMethod.equals("1")){
			System.out.println("      Payment Method --> Cash On Delivery");
		}
		else if (paymentMethod.equals("2")){
			System.out.println("      Payment Method --> Online Payment");
			isOnlinePayment = true;
		}
		System.out.println("\n     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
		System.out.printf("     |  %-3s%-39s|    %-9s| %-7s |   %-8s |","No. |"," Product","Price","Quantity","Total");	
		System.out.println("\n     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	

		//DISPLAYING CART PRODUCTS
		int subtotal = 0; //variable for calculating the total amount
		for (int i = 0; i < cart.length; i++) {
			if (cart [i][0] != null) {
				//variable for calculating the the price of each product according to the quantity
				int total = (Integer.parseInt(cart[i][1])*Integer.parseInt(cart[i][2]));
				//adding price of each product to the total amount
				subtotal += total;
				//displaying each product in the cart along with price of single product,quantity and total price wrt quatity
				System.out.printf("     |  %-4d| %-38s| PKR %-8s|    %-5s | PKR %-7d|",i+1,cart[i][0],cart[i][1],cart[i][2],total);
				System.out.println("\n     +------------------------------------------------------------------------------------+");
			}
		}

		//No shopping if total bill is greater than 5000
		//200 shopping if total bill is less than 5000
		int shipping = (subtotal > 5000)? 0 : 200; 
		//calculating total
		float total = shipping + subtotal;
		//displaying the subtotal, shipping and total amount
		System.out.printf("     |  %66s %8s%4d   |","SUBTOTAL"," PKR ",subtotal);
		System.out.println("\n     +------------------------------------------------------------------------------------+");
		System.out.printf("     |  %66s %8s%4d   |","SHIPPING","PKR ",shipping);
		System.out.println("\n     +------------------------------------------------------------------------------------+");
		//if user has membership then display the discounted bill
		if (memberCredentials[0].length != 1) {
			//15% off
			total = (float)(total - (total * 0.15)); //calculating the discounted amount
			System.out.printf("     |  %66s %8s%4d   |","DISCOUNTED TOTAL","PKR ",(int)total);
			System.out.println("\n     +------------------------------------------------------------------------------------+");
		}
		//else display the original bill
		else {
			System.out.printf("     |  %66s %8s%4d   |","TOTAL","PKR ",(int)total);
			System.out.println("\n     +------------------------------------------------------------------------------------+");
		}
		//getting the current date by java.util.Date class
		java.util.Date date=new java.util.Date();  
		System.out.printf("     | %s  %54s",date,"|");
		System.out.println("\n     +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");

		String customer = (memberCredentials[0].length!=1)? memberCredentials[0][3].toUpperCase():name;
		//calling ConfirmOrderOrLeave method to check if user wants to pay or not
		return confirmOrderOrLeave (cart,ctgDairy,costQuantityDairy,ctgCookingEssentials,costQuantityCookingEssentials, 
				ctgBeverages,costQuantityBeverages,ctgBathroomSupplies,costQuantityBathroomSupplies,ctgStationary,costQuantityStationary,
				isOnlinePayment, Float.toString(total), customer, date); 
	}

	//METHOD TO CHECK IF THE USER WANT TO CONFIRM ORDER OR LEAVE
	public static boolean confirmOrderOrLeave (String [][] cart,String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials, int[][] costQuantityCookingEssentials, 
			String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies, int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary,
			boolean isOnlinePayment, String payment, String customer, Date date) throws IOException {
		Scanner input = new Scanner (System.in);
		String leave = ""; //variable to take input from user if he/she wants to leave or not
		//loop which will continue if the user does not want to leave 

		do {
			String orderConfirmation;
			System.out.println( "      +---------------------------+");
			System.out.println( "      | Press 1: Order Confirm    |\n"
					+"      | Press 2: Leave            |\n"
					+"      | Press 0: Go to Cart Menu  |");
			System.out.println( "      +---------------------------+");
			System.out.print("      ---> "); 
			while (true) {
				orderConfirmation = input.nextLine().trim();
				if ((orderConfirmation.equals("1") || orderConfirmation.equals("2") || orderConfirmation.equals("0"))){
					break;
				}
				else {
					System.out.println(orderConfirmation.equals("")? "\n      INVALID INPUT: Required Valid Input Which Is Not Null" : "\n      INVALID INPUT: Required Correct Option Number ");
					System.out.print("      ---> "); 
				}
			}

			//condition when user wants to confirm order
			if (orderConfirmation.equals("1")) {
				if (isOnlinePayment) {
					cardPayment(null, payment);
				}
				arraysToProdFile(ctgDairy, costQuantityDairy, ctgCookingEssentials, costQuantityCookingEssentials, ctgBeverages, costQuantityBeverages,
						ctgBathroomSupplies, costQuantityBathroomSupplies, ctgStationary, costQuantityStationary);
				updateSalesReport(cart, payment, customer, date);
				System.out.println("\n                        *** Dear Customer, your order has been confirmed! ***");
				System.out.println(  "                            +-------------------------------------------+");
				System.out.println(  "                            |           Thankyou for shopping!          |");
				System.out.println(  "                            +-------------------------------------------+");
				emptyCart(cart);
				return true;
			}
			//condition when user wants to leave
			else if (orderConfirmation.equals("2")) {
				// printing the thank you statement if user leaves
				if (searchAgain("\n     ---> Are you sure you want to leave? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")){
					for (int i = 0; i < cart.length; i++) {
						if (cart[i][0] != null) {
							//returning the product quantity back to the cost quantity array specified with the product
							if (cart[i][3].contains("D")) {
								returnQuantity(cart,ctgDairy,costQuantityDairy, i+1);
							}
							else if (cart[i][3].contains("CE")) {
								returnQuantity(cart,ctgCookingEssentials,costQuantityCookingEssentials, i+1);
							}
							else if (cart[i][3].contains("BS")) {
								returnQuantity(cart,ctgBathroomSupplies,costQuantityBathroomSupplies, i+1);
							}
							else if (cart[i][3].contains("B")) {
								returnQuantity(cart,ctgBeverages,costQuantityBeverages, i+1);
							}
							else if (cart[i][3].contains("S")) {
								returnQuantity(cart,ctgStationary,costQuantityStationary, i+1);
							}
						}
					}
					System.out.println("                            +-------------------------------------------+");
					System.out.println("                            |           Thankyou for visiting!          |");
					System.out.println("                            +-------------------------------------------+");
					emptyCart(cart);
					return true;
				}
			} 
			else if (orderConfirmation.equals("0")) 
				return false;
		}while(true);

	}

	//capitalizes 1st letter of a string
	public static String sentenceCase(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}

	public static void askFeedBack(String[][] feedbacks) throws FileNotFoundException {
		//Creating a scanner object to accept input from user
		Scanner input = new Scanner(System.in);

		//An empty string has been declared to accept ratings from the user
		String rating = " ";         
		System.out.println("                                -------------------------------------                                        \n"
				+ " +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~| F E E D B A C K   &   R A T I N G S |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
				+ "                                -------------------------------------                                        ");
		//Asking user whether he/she wants to rate us or not
		//Flow of program when user wants to do the rating
		if (searchAgain("     ---> Will you Rank us and give Feedback? \n\n\t\t[y] Yes\t\t[n] No\n\n\tPress any key to continue.. ")){
			System.out.print("                                  +----------------------------+\n"
					+ "                                  | ***  -----> Excellent      |\n"
					+ "                                  | **   -----> Average        |\n"
					+ "                                  | *    -----> Poor           |\n"
					+ "                                  +----------------------------+\n"
					+ "     ---> Rate us by giving stars in the above mentioned pattern: ");
			//Storing the ranking and feedbacks in the array
			for (int i=0; i<feedbacks.length; i++) {
				if (feedbacks[i][0] == null) {

					//Conditions for rating: *** for excellent, ** for average, * for poor
					while (true) {
						rating = input.nextLine().trim();
						if (rating.equals("***") || (rating.equals("**") || (rating.equals("*")))) {
							System.out.println("\n     +-------------------------+");
							System.out.printf("     | Your rating is ---> %-3s |\n",rating);
							System.out.println("     +-------------------------+\n");
							feedbacks[i][1] = rating;
							break;
						} 
						else {
							System.out.println(rating.equals("")? "\n      INVALID INPUT: Required Valid Input Which Is Not Null" : "\n      INVALID INPUT: Required valid rating stars ");
							System.out.print("\t---> ");
						}
					}

					//Program flow for taking feedbacks
					//This part of program will execute for taking feedback
					System.out.print("     ---> Write a feedback or Press Enter: ");
					while (true) {
						feedbacks [i][0] = input.nextLine();
						if (feedbacks[i][0].length() <= 200 && !feedbacks[i][0].contains(":")) {
							break;
						}
						else {
							System.out.println("\n     INVALID INPUT: Required feedback of atmost 200 characters and no ':' symbol\n      ---> ");
						}
					}
					arrayToFeedbackFile(feedbacks);
					System.out.println("\n        +-------------------------------------------+\n"
							+"        |         Thankyou for your response!       |\n"
							+"        +-------------------------------------------+\n");
					break;
				}	
			}
		}
		//Flow of program when user doesn't want to do the rating
		else
			System.out.println("\n          +---------------------------------------+\n"
					+"          |         Thankyou for your time!       |\n"
					+"          +---------------------------------------+\n"); 
	}

	public static void displayFeedbacks(String[][] feedbacks) {
		System.out.println("\n   +-------------------------------------------------------------------------------------------------------------------+");
		System.out.printf("   | %-2s | %-3s |  %-100s|\n","No.","RATE","FEEDBACKS");
		System.out.println("   +-----+------+------------------------------------------------------------------------------------------------------+");
		for(int m = 0; m < feedbacks.length; m++) {
			if (feedbacks[m][0] != null) {
				if (feedbacks[m][0].length() <= 100) {
					System.out.printf("   |  %-2d |  %-3s |  %-100s|\n",m+1,feedbacks[m][1],feedbacks[m][0].substring(0, feedbacks[m][0].length()));
					System.out.println("   +-----+------+------------------------------------------------------------------------------------------------------+");
				}
				else {
					System.out.printf("   |  %-2d |  %-3s |  %-100s|\n",m+1,feedbacks[m][1],feedbacks[m][0].substring(0, 100));
					System.out.printf("   |     |      |  %-100s|\n",feedbacks[m][0].substring(100));
					System.out.println("   +-----+------+------------------------------------------------------------------------------------------------------+");
				}

			}
		}
	}

	//saves product arrays in file
	public static void arraysToProdFile(String[][] ctgDairy, int[][] costQuantityDairy, String[][] ctgCookingEssentials,
			int[][] costQuantityCookingEssentials, String[][] ctgBeverages, int[][] costQuantityBeverages, String[][] ctgBathroomSupplies,
			int[][] costQuantityBathroomSupplies, String[][] ctgStationary, int[][] costQuantityStationary) throws FileNotFoundException {
		File objFile = new File("ProductsList.txt");

		PrintWriter write = new PrintWriter(objFile);

		//serial numbber variable
		int sno=1;
		//data is saved into the file
		//heading
		write.println("SNO\t:PROD ID\t:COST\t\t:QUANTITY\t:PROD NAME");		
		//Dairy products
		for (int i=0; i<ctgDairy.length; i++ ) {
			if (ctgDairy[i][0] != null) {
				//1st: ID is stored at index [x][0]
				write.print((sno)+")\t:"+ctgDairy[i][0]+"\t\t:");
				//2nd, 3rd: cost and quantity is saved
				for (int j=0; j<costQuantityDairy[i].length; j++) {
					write.print((j==1)? costQuantityDairy[i][j]+"\t\t:": costQuantityDairy[i][j]+"\t\t:");
				}
				//4th: name is stored
				write.print(ctgDairy[i][1]+"\t" );
				//line break so next data enters in next line
				write.println();
				sno++;
			}
			else break;

		}
		//Cooking Essential products
		for (int i=0; i<ctgCookingEssentials.length; i++ ) {
			if (ctgCookingEssentials[i][0] != null) {
				//1st: ID is stored at index [x][0]
				write.print((sno)+")\t:"+ctgCookingEssentials[i][0]+"\t\t:");
				//2nd, 3rd: cost and quantity is saved
				for (int j=0; j<costQuantityCookingEssentials[i].length; j++) {
					write.print((j==1)? costQuantityCookingEssentials[i][j]+"\t\t:": costQuantityCookingEssentials[i][j]+"\t\t:");
				}
				//4th: name is stored
				write.print(ctgCookingEssentials[i][1]+"\t" );
				//line break so next data enters in next line
				write.println();
				sno++;
			}
			else break;

		}
		//Beverages products
		for (int i=0; i<ctgBeverages.length; i++ ) {
			if (ctgBeverages[i][0] != null) {
				//1st: ID is stored at index [x][0]
				write.print((sno)+")\t:"+ctgBeverages[i][0]+"\t\t:");
				//2nd, 3rd: cost and quantity is saved
				for (int j=0; j<costQuantityBeverages[i].length; j++) {
					write.print((j==1)? costQuantityBeverages[i][j]+"\t\t:": costQuantityBeverages[i][j]+"\t\t:");
				}
				//4th: name is stored
				write.print(ctgBeverages[i][1]+"\t" );
				//line break so next data enters in next line
				write.println();
				sno++;
			}
			else break;

		}
		//Bathroom Supplies products
		for (int i=0; i<ctgBathroomSupplies.length; i++ ) {
			if (ctgBathroomSupplies[i][0] != null) {
				//1st: ID is stored at index [x][0]
				write.print((sno)+")\t:"+ctgBathroomSupplies[i][0]+"\t\t:");
				//2nd, 3rd: cost and quantity is saved
				for (int j=0; j<costQuantityBathroomSupplies[i].length; j++) {
					write.print((j==1)? costQuantityBathroomSupplies[i][j]+"\t\t:": costQuantityBathroomSupplies[i][j]+"\t\t:");
				}
				//4th: name is stored
				write.print(ctgBathroomSupplies[i][1]+"\t" );
				//line break so next data enters in next line
				write.println();
				sno++;
			}
			else break;
		}
		//Stationary products
		for (int i=0; i<ctgStationary.length; i++ ) {
			if (ctgStationary[i][0] != null) {
				//1st: ID is stored at index [x][0]
				write.print((sno)+")\t:"+ctgStationary[i][0]+"\t\t:");
				//2nd, 3rd: cost and quantity is saved
				for (int j=0; j<costQuantityStationary[i].length; j++) {
					write.print((j==1)? costQuantityStationary[i][j]+"\t\t:": costQuantityStationary[i][j]+"\t\t:");
				}
				//4th: name is stored
				write.print(ctgStationary[i][1]+"\t" );
				//line break so next data enters in next line
				write.println();
				sno++;
			}
			else break;
		}
		//file closed
		write.close();
	}

	//saves member logInData in file
	public static void arrayToMemberFile(String[][] logInCredentials) throws FileNotFoundException {
		File objFile = new File("MembersDetails.txt");

		PrintWriter write = new PrintWriter(objFile);
		//serial number variable
		int sno=1;
		//data is saved into the file
		//heading
		write.println("SNO\t:MEMBER ID\t:NAME   :PHONE NO   :EMAIL   :PASSWORD");	
		//members' credentials
		for (int i=0; i<logInCredentials.length && logInCredentials[i][0] != null; i++ ) {
			//member data is fed into the file
			write.println((sno)+")\t:"+logInCredentials[i][4]+"\t:"+logInCredentials[i][0]+"   :"+logInCredentials[i][3]+"   :"+logInCredentials[i][2]+"   :"+logInCredentials[i][1]);
			sno++;

		}

		write.close();
	}

	//saves feedback array in file
	public static void arrayToFeedbackFile(String[][] feedbacks) throws FileNotFoundException {
		File objFile = new File("feedbackList.txt");

		PrintWriter write = new PrintWriter(objFile);
		//serial number variable
		int sno=1;
		//data is saved into the file
		//heading
		write.println("SNO\t:RATINGS\t:FEEDBACKS");		
		//feedbacks
		for (int i=0; i<feedbacks.length && feedbacks[i][0] != null; i++ ) {
			//feedbacks and ratings are fed to file
			write.println((sno)+")\t:"+feedbacks[i][1]+"\t\t:"+feedbacks[i][0]); 
			sno++;
		}
		//file closed
		write.close();
	}

	//returns index of the list for adding correct serial number
	public static int returnIndexOfSalesFile() throws FileNotFoundException {
		//File object created to access the file
		File objFile = new File("SalesReport.txt");
		//Scanner object created to read the file
		Scanner scan = new Scanner(objFile);

		//index initialized to 0;
		int index=0;
		//loop turns till Scanner receives a new line tu scan from the file
		while(scan.hasNextLine()) {
			//each line is stripped through ":" symbol, trimmed and saved in 1-D array
			String[] line = scan.nextLine().trim().split(":");
			//try catch used because while converting index[0] elements to int type, there might occur the heading and we want to skip that line.
			try {
				//the serial number at index[0] of line is trimmed and ")" is removed and then converted to int type
				index = Integer.parseInt(line[0].trim().replace(")", ""));
			}
			catch(Exception ex) {
				continue;
			}
		}
		//index of last line is returned
		return index;
	}

	//transfers purchase details to Sales Report File
	public static void updateSalesReport(String[][] cart, String payment, String member, Date date) throws IOException {
		File objFile = new File("SalesReport.txt");
		//FileWriter object created in append mode
		FileWriter fw = new FileWriter(objFile, true);

		//serial number variable
		int sno = returnIndexOfSalesFile();
		//data is saved into the file in this format		
		//               SNO        :TIME/DATE     
		fw.write("\n"+(sno+1)+")\t:"+date+"\t:");
		for (int i=0; i<cart.length && cart[i][0]!=null; i++) {
			if (i==0)
				//			:PROD ID     :COST              :QUANTITY PURCHASED  :SUBTOTAL       :CUSTOMER
				fw.write(cart[i][3]+"\t\t:"+cart[i][1]+"\t\t:"+cart[i][2]+"\t\t\t:"+payment+"\t\t:"+member);
			else
				//			              :PROD ID      :COST              :QUANTITY PURCHASED
				fw.write("\n\t\t\t\t\t:"+cart[i][3]+"\t\t:"+cart[i][1]+"\t\t:"+cart[i][2]);	
		}
		//file closed
		fw.close();
	}
}	
