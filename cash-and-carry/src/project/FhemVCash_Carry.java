package project;

import java.io.*;
import java.util.*;

public class FhemVCash_Carry {
	public static String[][] ctgDairy = new String[25][2];
	public static int[][] costQuantityDairy = new int[25][2]; 
	public static String[][] ctgCookingEssentials = new String[25][2];
	public static int[][] costQuantityCookingEssentials = new int[25][2];
	public static String[][] ctgBeverages = new String[25][2];
	public static int[][] costQuantityBeverages = new int[25][2];
	public static String[][] ctgBathroomSupplies = new String[25][2];
	public static int[][] costQuantityBathroomSupplies = new int[25][2];
	public static String[][] ctgStationary = new String[25][2];
	public static int[][] costQuantityStationary = new int[25][2];
	public static String[][] feedbacks = new String[100][2];
	public static String[][] logInCredentials = new String[100][5];
	public static String [][] mainAdminCredentials = new String[1][4];
	public static String [][] adminCredentials = new String[5][4];

	public static void main(String[] args) throws Exception{	
		Scanner input = new Scanner(System.in);
		//feedbacks and rating from file are stored in array
		feedbackFileToArray();
		//member credentials are stored in the array
		memberFileToArray();
		//admin credentials are stored in the arrays
		adminFileToArray();

		//array containing all the content of product file
		String[][] productList = prodFileToArray();
		//products are added to their relevant arrays
		addProdDetailsToArray(productList);

		while(true) {
			System.out.print(   "\n                 +--------------------------------------------------------------------------------+\n"
					+ "   +~~~~~~~~~~~~~|          W E L C O M E   T O   F H E M - V   C A S H  &  C A R R Y \u2122           |~~~~~~~~~~~+\n"
					+ "   |             +--------------------------------------------------------------------------------+           |\n"
					+ "   |                                                                                                          |\n"
					+ "   |       -----------------------------------                -----------------------------------------       |\n"
					+ "   |         [1]   A D M I N   S E C T I O N                    [2]   C U S T O M E R   S E C T I O N         |\n"
					+ "   |       -----------------------------------                -----------------------------------------       |\n"
					+ "   |                                                                                                          |\n"
					+ "   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n");

			String choice;
			do {
				System.out.print("     ------> ");
				choice = input.nextLine();
				System.out.println((!(choice.equals("1") || choice.equals("2")) || choice.equals(""))? "        INVALID INPUT: Required correct option number!":"");
			}while(!(choice.equals("1") || choice.equals("2")));

			switch (choice) {
			case "1": mainMenu();
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			break;
			case "2": CustomerSection.mainMenu(ctgDairy, costQuantityDairy,ctgCookingEssentials,costQuantityCookingEssentials, 
					ctgBeverages, costQuantityBeverages, ctgBathroomSupplies,costQuantityBathroomSupplies, ctgStationary,
					costQuantityStationary,logInCredentials, feedbacks);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			break;
			}
		}
	}

	//all file products are stored into an array
	public static String[][] prodFileToArray() throws Exception{
		//reads the file
		Scanner scan = new Scanner(new File("ProductsList.txt"));
		//array row size is 126 because each of the 5 product arrays has size 25. 25*5=125. 1 extra space for the heading 
		String[][] productList = new String[126][4];
		//one line of file is split into separate elements and saved in 1D array 
		String[] line;

		//?
		while(scan.hasNextLine()) {
			//reading each line of file
			for (int i=0; i<productList.length; i++) {
				//try catch is used to check if no more data is there in the file, the array will store null in it instead
				try {
					//line is split through ":" symbol in the file
					line = scan.nextLine().trim().split(":");	
				}
				catch(NoSuchElementException ex){
					//when no more line is found in the file, loop will terminate
					break;
				}
				//each line element split through ":" symbol is stored in 2D array. it is trimmed to removed extra spaces
				for (int j=0; j<line.length; j++) {
					//to skip serial number at index 0
					if (j==0)
						continue;
					productList[i][j-1]=line[j].trim();
				}		
			}
		}
		return productList;
	}

	//product name and id are stored in a String[][] array
	public static String[][] productStringArray(String[][] productList, String ID){
		String[][] idName = new String[25][2];

		//loop iterates with value of i. It checks each product in productList for relevant ID, can't be null or heading
		//value of k is for the inner loop that acts as rows for the idName array
		for (int i=0, k=0; i<productList.length; i++) {
			if (productList[i][0] != null && productList[i][0].contains(ID) && !productList[i][0].equals("PROD ID")) {
				//loop iterates for j which is column number for idName array. relevant data is stored in the array
				//the value of k increments only when the control comes into this block
				for (int j=0; j<idName[k].length; j++) {
					idName[k][j]= (j==0)? productList[i][j]:productList[i][j+2];
				}
				k++;
			}	
		}
		return idName;
	}

	//product cost and quantity are stored in an int[][] array
	public static int[][] productIntArray(String[][] productList, String ID){
		int[][] costQuantity = new int[25][2];

		//loop iterates with value of i. It checks each product in productList for relevant ID, can't be null or heading
		//value of k is for the inner loop that acts as rows for the idName array
		for (int i=0, k=0; i<productList.length; i++) {
			if (productList[i][0] != null && productList[i][0].contains(ID) && !productList[i][0].equals("PROD ID")) {
				//loop iterates for j which is column number for idName array. relevant data is stored in the array
				//the value of k increments only when the control comes into this block
				for (int j=0; j<costQuantity[k].length; j++) {
					costQuantity[k][j]=Integer.parseInt(productList[i][j+1]);
				}
				k++;
			}
		}
		return costQuantity;
	}

	//product details are fed into Product details' file
	public static void addProdDetailsToArray(String[][] productList) {
		ctgDairy = productStringArray(productList, "D");
		costQuantityDairy = productIntArray(productList, "D");
		ctgCookingEssentials = productStringArray(productList, "CE");
		costQuantityCookingEssentials = productIntArray(productList, "CE");
		ctgBeverages = productStringArray(productList, "BE");
		costQuantityBeverages = productIntArray(productList, "BE");
		ctgBathroomSupplies = productStringArray(productList, "BS");
		costQuantityBathroomSupplies = productIntArray(productList, "BS");
		ctgStationary = productStringArray(productList, "ST");
		costQuantityStationary = productIntArray(productList, "ST");
	}

	//feedbacks are stored in the feedbacks file
	public static void feedbackFileToArray() throws Exception{
		//reads the file
		Scanner scan = new Scanner(new File("feedbackList.txt"));
		//array row size is 126 because each of the 5 product arrays has size 25. 25*5=125. 1 extra space for the heading 
		String[][] feedbacksList = new String[101][2];
		//one line of file is split into separate elements and saved in 1D array 
		String[] line;

		//loop executes till scanner receives a line from the file
		while(scan.hasNextLine()) {
			//reading each line of file
			for (int i=0; i<feedbacksList.length; i++) {
				//try catch is used to check if no more data is there in the file, the array will store null in it instead
				try {
					//line is split through ":" symbol in the file
					line = scan.nextLine().trim().split(":");	
				}
				catch(NoSuchElementException ex){
					//when no more line is found in the file, loop will terminate
					break;
				}
				//each line element split through ":" symbol is stored in 2D array. it is trimmed to removed extra spaces
				for (int j=0; j<line.length; j++) {
					//to skip serial number at index 0
					if (j==0)
						continue;
					feedbacksList[i][j-1]=line[j].trim();
				}		
			}
		}

		//loop iterates with value of i. It checks each feedback in feedbacksList that it can't be null. i=0 is skipped because of heading
		for (int i=1; i<feedbacksList.length; i++) {
			if (feedbacksList[i][0] != null) {
				//feedback is stored at index 0
				feedbacks[i-1][0]= feedbacksList[i][1];
				//rating is stored at index 1
				feedbacks[i-1][1]= feedbacksList[i][0];

			}	
		}
	}

	//member logIn details are stored in the member details' array
	public static void memberFileToArray() throws Exception{
		//reads the file
		Scanner scan = new Scanner(new File("MembersDetails.txt"));
		//array row size is 101 because each of the there is a space for 100 members. 1 extra space for the heading 
		String[][] memberDataList = new String[101][5];
		//one line of file is split into separate elements and saved in 1D array 
		String[] line;

		//loop executes till scanner receives a line from the file
		while(scan.hasNextLine()) {
			//reading each line of file
			for (int i=0; i<memberDataList.length; i++) {
				//try catch is used to check if no more data is there in the file, the array will store null in it instead
				try {
					//line is split through ":" symbol in the file
					line = scan.nextLine().trim().split(":");	
				}
				catch(NoSuchElementException ex){
					//when no more line is found in the file, loop will terminate
					break;
				}
				//each line element split through ":" symbol is stored in 2D array. it is trimmed to removed extra spaces
				for (int j=0; j<line.length; j++) {
					//to skip serial number at index 0
					if (j==0)
						continue;
					memberDataList[i][j-1]=line[j].trim();
				}		
			}
		}

		//loop iterates with value of i. It checks each member detail in MembersDetails that it can't be null. i=0 is skipped because of heading
		for (int i=1; i<memberDataList.length; i++) {
			if (memberDataList[i][0] != null) {
				//name is stored at index 0
				logInCredentials[i-1][0]= memberDataList[i][1];
				//password is stored at index 1
				logInCredentials[i-1][1]= memberDataList[i][4];
				//email is stored at index 2
				logInCredentials[i-1][2]= memberDataList[i][3];
				//phone no is stored at index 3
				logInCredentials[i-1][3]= memberDataList[i][2];
				//id is stored at index 4
				logInCredentials[i-1][4]= memberDataList[i][0];

			}	
		}
	}

	//admin logIn details are stored in the admin details' array
	public static void adminFileToArray() throws Exception{
		//reads the file
		Scanner scan = new Scanner(new File("AdminDetails.txt"));
		//array row size is 6 because there is 1 main admin and 4 subadmins. 1 extra space for the heading 
		String[][] adminDataList = new String[6][4];
		//one line of file is split into separate elements and saved in 1D array 
		String[] line;

		//loop executes till scanner receives a line from the file
		while(scan.hasNextLine()) {
			//reading each line of file
			for (int i=0; i<adminDataList.length; i++) {
				//try catch is used to check if no more data is there in the file, the array will store null in it instead
				try {
					//line is split through ":" symbol in the file
					line = scan.nextLine().trim().split(":");	
				}
				catch(NoSuchElementException ex){
					//when no more line is found in the file, loop will terminate
					break;
				}
				//each line element split through ":" symbol is stored in 2D array. it is trimmed to removed extra spaces
				for (int j=0; j<line.length; j++) {
					//to skip serial number at index 0
					if (j==0)
						continue;
					adminDataList[i][j-1]=line[j].trim();
				}		
			}
		}

		//loop iterates with value of i. It checks each admin detail in AdminDetails that it can't be null. i=0 is skipped because of heading
		for (int i=1; i<adminDataList.length; i++) {
			if (adminDataList[i][0] != null) {
				if (i==1) {
					//id is stored at index 0
					mainAdminCredentials[i-1][0]= adminDataList[i][0];
					//password is stored at index 1
					mainAdminCredentials[i-1][1]= adminDataList[i][3];
					//email is stored at index 2
					mainAdminCredentials[i-1][2]= adminDataList[i][2];
					//phone no is stored at index 3
					mainAdminCredentials[i-1][3]= adminDataList[i][1];
				}
				else {
					//id is stored at index 0
					adminCredentials[i-2][0]= adminDataList[i][0];
					//password is stored at index 1
					adminCredentials[i-2][1]= adminDataList[i][3];
					//email is stored at index 2
					adminCredentials[i-2][2]= adminDataList[i][2];
					//phone no is stored at index 3
					adminCredentials[i-2][3]= adminDataList[i][1];
				}
			}	
		}
	}

	//ADMIN BLOCK	
	public static void displayMenu(String menu) {
		switch (menu) {
		case "logInMenu": 
			System.out.print( "                     +----------------------------+\n"
					+ "   +~~~~~~~~~~~~~~~~~|   A D M I N    B L O C K   |~~~~~~~~~~~~~~~~~+\n"
					+ "   |                 +----------------------------+                 |\n"
					+ "   |                                                                |\n"
					+ "   |      ---> What do you want to sign in with?                    |\n"
					+ "   |               1.   Administrator                               |\n"
					+ "   |               2.   Standard admin                              |\n"
					+ "   |               0.   Exit                                        |\n"
					+ "   |                                                                |\n"
					+ "   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "   Press any key to continue.. ");break;	
		case "administratorLogIn":
			System.out.print( "\n            +---------------------------------------------+\n"
					+ "        +~~~|   A D M I N I S T R A T O R   L O G - I N   |~~~~+\n"
					+ "        |   +---------------------------------------------+    |\n"
					+ "        |                                                      |\n"
					+ "        |            1-      Log in                            |\n"
					+ "        |            2-      Forgot Password                   |\n"
					+ "        |            3-      Admin Block                       |\n"
					+ "        |                                                      |\n"
					+ "        +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "        Press any key to continue.. ");break;
		case "adminLogIn":
			System.out.print( "\n                     +----------------------------+\n"
					+ "        +~~~~~~~~~~~~|   A D M I N  L O G - I N   |~~~~~~~~~~~~+\n"
					+ "        |            +----------------------------+            |\n"
					+ "        |                                                      |\n"
					+ "        |            1-      Log in                            |\n"
					+ "        |            2-      Forgot Password                   |\n"
					+ "        |            3-      Admin Block                       |\n"
					+ "        |                                                      |\n"
					+ "        +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "        Press any key to continue.. ");break;
		case "signIn":
			System.out.print( "\n                      +--------------------------+\n"
					+ "   +~~~~~~~~~~~~~~~~~~|   S I G N I N G   I N    |~~~~~~~~~~~~~~~~~~+\n"
					+ "   |                  +--------------------------+                  |\n");break;	
		case "forgotPassword":
			System.out.print("\n                +--------------------------------------+\n"
					+ "   +~~~~~~~~~~~~|    F O R G O T   P A S S W O R D     |~~~~~~~~~~~~+\n"
					+ "   |            +--------------------------------------+            |\n");break;
		case "newPassword":
			System.out.print( "                   +--------------------------------+\n"
					+ "   +~~~~~~~~~~~~~~~|    N E W   P A S S W O R D     |~~~~~~~~~~~~~~~+\n"
					+ "   |               +--------------------------------+               |\n");break;
		case "changePassword":
			System.out.println("\n                  +-----------------------------------+\n"
					+ "   +~~~~~~~~~~~~~~|   C H A N G E   P A S S W O R D   |~~~~~~~~~~~~~+\n"
					+ "   |              +-----------------------------------+             |");break;
		case "ID":
			System.out.print("   |                                                                |\n"
					+ "   |  --->  NOTE: ID should be in the format 'xyz-000'              |\n"
					+ "   |                                                                |\n");break;
		case "PASSWORD":
			System.out.print("   |                                                                |\n"
					+ "   |  --->  NOTE:                                                   |\n"
					+ "   |        1- Password should contain only letters and numbers     |\n"
					+ "   |        2- Password must be at least 8 characters               |\n"
					+ "   |                                                                |\n");break;
		case "EMAIL":
			System.out.print( "   |                                                                |\n"
					+ "   |  --->  NOTE: Email should be in the format:                    |\n"
					+ "   |      abc@gmail.com OR 'abc@yahoo.com OR 'abc@hotmail.com       |\n"
					+ "   |                                                                |\n");break;
		case "PHONENO":
			System.out.print("   |                                                                |\n"
					+ "   |   --->  NOTE:                                                  |\n"
					+ "   |         1- Phone number should be of 11 digits                 |\n"
					+ "   |         2- Hyphens must be avoided                             |\n"
					+ "   |                                                                |\n");break;

		case "currentPassword":
			System.out.print("   |                                                                |\n"
					+ "   |     ERROR:                                                     |\n"
					+ "   |     You can not enter your current password as new password.   |\n"
					+ "   |                                                                |\n");break;
		case "mainMenu":
			System.out.print("\n                         +-----------------------+\n"
					+ "    +~~~~~~~~~~~~~~~~~~~~|   M A I N   M E N U   |~~~~~~~~~~~~~~~~~~~~+\n"
					+ "    |                    +-----------------------+                    |\n"
					+ "    |                                                                 |\n"
					+ "    |       What function do you want to operate?                     |\n"
					+ "    |                                                                 |\n" 
					+ "    |            1-      Add a new product                            |\n"
					+ "    |            2-      View all products                            |\n" 
					+ "    |            3-      Search a product                             |\n" 
					+ "    |            4-      Remove a product                             |\n"
					+ "    |            5-      Update a product                             |\n"
					+ "    |            6-      Change password                              |\n" 
					+ "    |            7-      Manage accounts                              |\n"
					+ "    |            8-      Show customer feedbacks                      |\n"
					+ "    |            9-      Logout                                       |\n"
					+ "    |                                                                 |\n"
					+ "    +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "    Press any key to continue.. ");break;		
		case "categorySelection":
			System.out.print("\n                  +---------------------------------------+"
					+ "\n          +~~~~~~~|  C A T E G O R Y   S E L E C T I O N  |~~~~~~~+"
					+ "\n          |       +---------------------------------------+       |"
					+ "\n          |                                                       |");break;
		case "categories":
			System.out.print("          |         1-    Dairy                                   |\n"
					+ "          |         2-    Cooking Essentials                      |\n"
					+ "          |         3-    Beverages                               |\n"
					+ "          |         4-    Bathroom Supplies                       |\n"
					+ "          |         5-    Stationary                              |\n"
					+ "          |         0-    None                                    |\n"
					+ "          |                                                       |\n"
					+ "          +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "          Press any key to continue.. "); break;
		case "addingProduct":
			System.out.print("\n                     +---------------------------------+\n"
					+ "          +~~~~~~~~~~|   A D D I N G   P R O D U C T   |~~~~~~~~~~+\n"
					+ "          |          +---------------------------------+          |\n"
					+ "          |                                                       |\n"
					+ "          |      In which category does your product belong?      |\n");break;	
		case "viewingProducts":
			System.out.print("\n            +-----------------------------------------------+\n"
					+ "    +~~~~~~~|   V I E W I N G   P R O D U C T S   L I S T   |~~~~~~~+\n"
					+ "    |       +-----------------------------------------------+       |\n"
					+ "    |                                                               |\n"
					+ "    |         How do you want to view all products list?            |\n"
					+ "    |              1-   By alphabetical products                    |\n"
					+ "    |              2-   By increasing prices                        |\n"
					+ "    |              3-   By categories                               |\n"
					+ "    |                                                               |\n"
					+ "    +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "    Press any key to continue.. ");break;
		case "searchMenu":
			System.out.print("\n               +---------------------------------------+\n"
					+ "       +~~~~~~~|   S E A R C H I N G   P R O D U C T   |~~~~~~~+\n"
					+ "       |       +---------------------------------------+       |\n"
					+ "       |                                                       |\n" 
					+ "       |       How do you want to make a search?               |\n"
					+ "       |          1- By name                                   |\n"
					+ "       |          2- By category?                              |\n"
					+ "       |                                                       |\n"
					+ "       +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
					+ "       Press any key to continue.. ");break;
		case "SearchedResults":
			System.out.print("\n                                    +------------------------------------------+"
					+ "\n       +----------------------------|     S E A R C H E D   R E S U L T S      |-----------------------------------+"
					+ "\n                                    +------------------------------------------+ ");
			System.out.println("\n       +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
			System.out.printf("       |  %-3s%-39s|  %-9s|   %-7s|  %-8s  |        %-16s|","No. |"," Product","ProdID","Cost","In Stock","Cateogry");	
			System.out.println("\n       +~~~~~~+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+~~~~~~~~~~~+~~~~~~~~~~+~~~~~~~~~~~~+~~~~~~~~~~~~~~~~~~~~~~~~+");break;
		case "RemovingProduct":
			System.out.println("\n\t        +-------------------------------------+"
					+ "\n\t+~~~~~~~|   R E M O V I N G   P R O D U C T   |~~~~~~~+"
					+ "\n\t        +-------------------------------------+       ");break;
		case "updatingProduct":
			System.out.println("\n                +-------------------------------------+"
					+ "\n        +~~~~~~~|   U P D A T I N G   P R O D U C T   |~~~~~~~+"
					+ "\n                +-------------------------------------+       ");break;
		case "manageMenu":
			System.out.println("\n                  +-----------------------------------+"
					+ "\n       +~~~~~~~~~~|   M A N A G E   A C C O U N T S   |~~~~~~~~~~+"
					+ "\n       |          +-----------------------------------+          |"
					+ "\n       |                                                         |"
					+ "\n       |       What function do you want to operate?             |"
					+ "\n       |          1-  Create an admin account                    |"
					+ "\n       |          2-  Remove an admin account                    |"
					+ "\n       |          3-  Remove a customer account                  |"
					+ "\n       |                                                         |"
					+ "\n       +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");break;
		case "CreatingAdmin":
			System.out.print("\n           +------------------------------------------------+"
					+ "\n   +~~~~~~~|   C R E A T I N G  A D M I N  A C C O U N T    |~~~~~~~+"
					+ "\n   |       +------------------------------------------------+       |"
					+ "\n   |                                                                |");break;
		}
	}

	//Main menu
	public static void mainMenu() throws IOException {

		String ID = logInMenu();
		//if ID passed is Null, it means flow exit
		if (ID == null) {
			return;
		}		
		//character 'y' is stored in the variable "mainOption" to start the while loop
		String mainOption = "m";

		//while loop continues as long as user wants to go back to the main menu
		//The user informs this by typing 'Y' or 'y'
		while (mainOption.equalsIgnoreCase("m")) {	
			//files update
			CustomerSection.arraysToProdFile(ctgDairy, costQuantityDairy, ctgCookingEssentials, costQuantityCookingEssentials, ctgBeverages,
					costQuantityBeverages, ctgBathroomSupplies, costQuantityBathroomSupplies, ctgStationary, costQuantityStationary);
			CustomerSection.arrayToMemberFile(logInCredentials);
			arrayToAdminFile(adminCredentials, mainAdminCredentials);
			//Printing the main menu options for the admin user
			displayMenu("mainMenu");

			//Created a Scanner object to get input from the user
			Scanner input = new Scanner(System.in);

			//The entered value is stored in the variable "adminOption"
			String adminOption = input.nextLine().trim();

			//while loop continues as long as one of the given conditions below is satisfied
			while(true) {
				//a product is added if the 'adminOption' is 1
				if (adminOption.equals("1")) {

					String addAgain = "a";
					while (addAgain.equalsIgnoreCase("a")) {

						addProduct();
						System.out.print("\n          [a] Add another product               [m] Main menu"
								+ "\n\n          Press any key to continue.. ");

						addAgain = input.nextLine().trim();
						while(!addAgain.equalsIgnoreCase("a") && !addAgain.equalsIgnoreCase("m")) {
							addAgain = validationString(addAgain, 7);
						}
					}
					mainOption = addAgain;
					break;
				}

				//product list is viewed if the 'adminOption' is 2
				else if (adminOption.equals("2")){

					String viewAgain = "v";
					while (viewAgain.equalsIgnoreCase("v")) {
						viewList();
						System.out.print("\n      [v] View products again                    [m] Main menu"
								+ "\n\n      Press any key to continue.. ");
						viewAgain = input.nextLine().trim();
						while(!viewAgain.equalsIgnoreCase("v") && !viewAgain.equalsIgnoreCase("m")) {
							viewAgain = validationString(viewAgain, 7);
						}
					}
					mainOption = viewAgain;
					break;
				}

				//a product is searched if the 'adminOption' is 3
				else if (adminOption.equals("3")) {		

					//character 'y' is stored in the variable "searchAgain" to start the while loop
					String searchAgain = "s";

					//while loop continues as long as the variable "searchAgain" is equal to 'y'
					while (searchAgain.equalsIgnoreCase("s")){

						//Printing the main heading "Searching product"
						displayMenu("searchMenu");

						//The entered option is stored in the integer variable "searchOption"
						String searchOption = input.nextLine().trim();

						while(true) {
							if (searchOption.equals("1")) {//if user wants to search by name

								//SearchProd() is invoked
								searchProd("name");break;}

							if (searchOption.equals("2")) {//if user wants to search by category

								//SearchCategory() is invoked
								searchProd("category");break;}
							else {searchOption = validationString(searchOption, 7);}
						}

						System.out.print("\n         [s] Search another product         [m] Main menu"
								+ "\n\n         Press any key to continue.. ");

						searchAgain = input.nextLine().trim();
						while(!searchAgain.equalsIgnoreCase("s") && !searchAgain.equalsIgnoreCase("m")) {
							searchAgain = validationString(searchAgain, 7);
						}
					}
					mainOption = searchAgain;
					break;
				} 

				//a product is removed if the 'adminOption' is 4
				else if (adminOption.equals("4")) {

					String removeAgain = "r";
					while(removeAgain.equalsIgnoreCase("r")) {	
						//RemoveProd() method is invoked
						removeProd();
						System.out.print("\n        [r] Remove another product     [m] Main menu"
								+ "\n\n        Press any key to continue.. ");

						removeAgain = input.nextLine().trim();
						while(!removeAgain.equalsIgnoreCase("r") && !removeAgain.equalsIgnoreCase("m")) {	
							removeAgain = validationString(removeAgain, 7);
						}
					}
					mainOption = removeAgain;
					break;		
				}				

				//a product is updated if the 'adminOption' is 5
				else if (adminOption.equals("5")) {
					String updateAgain = "u";
					while (updateAgain.equalsIgnoreCase("u")) {
						//Printing the main heading "Updating product"
						displayMenu("updatingProduct");

						//UpdateProduct() method is invoked
						String [][] Products = combineProducts();
						//Invoking SearchProd() method
						int[] selectProd = searchProd("name");

						//Comparing according to the category number, which is stored at 1 index of selectProd()
						switch(selectProd[1]) {
						case 0: updateProduct(Products,selectProd,ctgDairy,costQuantityDairy, selectProd[0],"Dairy"); break; //first category
						case 1: updateProduct(Products,selectProd,ctgCookingEssentials,costQuantityCookingEssentials, selectProd[0],"Cooking Essentials"); break; //second category
						case 2: updateProduct(Products,selectProd,ctgBeverages,costQuantityBeverages, selectProd[0],"Beverages"); break; //third category
						case 3: updateProduct(Products,selectProd,ctgBathroomSupplies,costQuantityBathroomSupplies, selectProd[0],"Bathroom Supplies"); break; //fourth category
						case 4: updateProduct(Products,selectProd,ctgStationary,costQuantityStationary, selectProd[0],"Stationary"); break; //fifth category
						}
						System.out.print("\n         [u] Update another product        [m] Main menu"
								+ "\n\n         Press any key to continue.. ");

						updateAgain = input.nextLine().trim();
						while(!updateAgain.equalsIgnoreCase("u") && !updateAgain.equalsIgnoreCase("m")) {
							updateAgain = validationString(updateAgain, 7);
						}
					}
					mainOption = updateAgain;
					break;//breaks out of the loop
				}		

				else if (adminOption.equals("6")) {
					String changeAgain = "c";
					while (changeAgain.equalsIgnoreCase("c")) {
						if (ID.equals(mainAdminCredentials[0][0])) {changePassword(mainAdminCredentials, ID);}
						else {changePassword(adminCredentials, ID);}

						System.out.print("\n         [c] Change password again        [m] Main menu"
								+ "\n\n         Press any key to continue.. ");

						changeAgain = input.nextLine().trim();
						while(!changeAgain.equalsIgnoreCase("c") && !changeAgain.equalsIgnoreCase("m")) {
							changeAgain = validationString(changeAgain, 7);
						}
					}
					break;
				}

				else if (adminOption.equals("7")) {

					if (ID.equals(mainAdminCredentials[0][0])) {
						displayMenu("manageMenu");

						System.out.print("       Press any key to continue.. ");
						String manageOption = input.nextLine().trim();

						while(true) {
							if (manageOption.equals("1")) {

								String addAdminAgain = "a";
								while (addAdminAgain.equalsIgnoreCase("a")) {
									addNewAdmin(adminCredentials);
									System.out.print("\n         [a] Add another account        [m] Main menu"
											+ "\n\n         Press any key to continue.. ");

									addAdminAgain = input.nextLine().trim();
									while(!addAdminAgain.equalsIgnoreCase("a") && !addAdminAgain.equalsIgnoreCase("m")) {
										addAdminAgain = validationString(addAdminAgain, 7);

									}
								}

								mainOption = addAdminAgain;
								break;
							}

							if (manageOption.equals("2")) {

								String removeAdminAgain = "r";
								while (removeAdminAgain.equalsIgnoreCase("r")) {
									removeID(adminCredentials,"Admin");
									System.out.print("\n         [r] Remove another account        [m] Main menu"
											+ "\n\n         Press any key to continue.. ");

									removeAdminAgain = input.nextLine().trim();
									while(!removeAdminAgain.equalsIgnoreCase("r") && !removeAdminAgain.equalsIgnoreCase("m")) {
										removeAdminAgain = validationString(removeAdminAgain, 7);
									}
								}

								mainOption = removeAdminAgain;
								break;}

							if (manageOption.equals("3")) {
								String removeCustomerAgain = "r";
								while (removeCustomerAgain.equalsIgnoreCase("r")) {
									removeID(logInCredentials,"Customer");
									System.out.print("\n         [r] Remove another account        [m] Main menu"
											+ "\n\n         Press any key to continue.. ");

									removeCustomerAgain = input.nextLine().trim();
									while(!removeCustomerAgain.equalsIgnoreCase("r") && !removeCustomerAgain.equalsIgnoreCase("m")) {
										removeCustomerAgain = validationString(removeCustomerAgain,7);
									}	
								}

								mainOption = removeCustomerAgain;
								break;}

							else {manageOption = validationString(manageOption, 7);}
						}
					}
					else {
						System.out.print("\n   -------------------- A C C E S S   D E N I E D --------------------\n"
								+ "\n        Only administrator has the permission to manage accounts! "
								+ "\n        Please log in with administrator privileges and try again.\n"
								+ "\n   ------------------------------------------------------------------\n"
								+ "          [m] Main menu\n\n"
								+ "     Press any key to continue..");
						String selection = input.nextLine().trim();
						while (!selection.equalsIgnoreCase("m")) {selection = validationString(selection, 3);}
						mainOption = selection;
						break;
					}

					break;}
				else if (adminOption.equals("8")) {
					CustomerSection.displayFeedbacks(feedbacks);
					System.out.print("\n        [m] Main menu"
							+ "\n\n        Press any key to continue.. ");
					String goBack = input.nextLine().trim();
					while(!goBack.equalsIgnoreCase("m")) {goBack = validationString(goBack,8);}
					mainOption = goBack;
					break;}

				//admin is logged out if the 'adminOption' is 6
				else if (adminOption.equals("9")) {break;}

				//else the user is asked to enter a valid serial number
				else {adminOption = validationString(adminOption, 4);}						
			}

			//breaks out of the loop if the 'adminOption' is 9
			if (adminOption.equals("9")) {
				//Printing the main heading "Logging out"
				System.out.println("\n                     ------------------------------"
						+ "\n   +-----------------|     L O G G I N G  O U T    |-----------------+"
						+ "\n                     ------------------------------"
						+ "\n\n              *** Dear "+ID+", you have been logged out ***\n\n"
						+ "  +-----------------------------------------------------------------+\n");
				ID = logInMenu();
				if (ID == null) {break;}
			}
		}

	}

	public static String logInMenu() {
		while(true) {
			displayMenu("logInMenu"); 
			String ID;
			Scanner input = new Scanner (System.in);
			String loginOption = input.nextLine().trim();

			while(true) {
				if (loginOption.equals("1")) {
					ID = logIn(mainAdminCredentials,1);
					if (ID.equals("-1")) {break;}
					else return ID;
				}
				else if (loginOption.equals("2")) {
					ID = logIn(adminCredentials,2);		
					if (ID.equals("-1")) {break;}
					else return ID;
				}
				else if (loginOption.equals("0")) {
					return null;
				}
				else {loginOption = validationString(loginOption, 3);}
			}
		}
	}

	public static String repeat(int count, String string) {
		return new String(new char[count]).replace("\0", string);
	}

	public static String repeat(int count) {
		return repeat(count, " ");
	}

	public static String validationString(String option, int x) {
		if (option.equals("")) {
			System.out.printf("\n"+repeat(x," ")+"INVALID INPUT: Required Valid Input Which Is Not Null\n\n");
		}
		else {
			System.out.printf("\n"+repeat(x," ")+"INVALID INPUT: Required Correct Option Number\n\n");
		}
		System.out.print(repeat(x," ")+"--> Please enter a valid input: ");
		Scanner input = new Scanner(System.in);
		return option = input.nextLine().trim();
	}
//LOGIN
	public static String logIn(String[][] logInCredentials,int loginMethod) {

		if (loginMethod == 1) {displayMenu("administratorLogIn");}
		else if (loginMethod == 2) {displayMenu("adminLogIn");}

		Scanner input = new Scanner(System.in);
		String loginOption = input.nextLine().trim();
		//System.out.println();
		String ID="", password;
		while (true) {
			if (loginOption.equals("1")) {

				String option= " "; // to stay in case 1,
				do{
					displayMenu("signIn");
					//input id
					displayMenu("ID");ID = Input.ID("   |       Enter admin ID: ","admin");	
					//input password
					displayMenu("PASSWORD");password = Input.password("   |       Enter password: ","admin");


					//**login output format					
					//look break if correct input is added
					if (findLoginData(logInCredentials,"login",ID, password)) { 
						System.out.println("   |\t\t\t\t\t\t\t\t    |\n"
								+"   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
						System.out.println("\t\t       *** Login Successful! *** \n");
					}
					//else display msg
					else {
						System.out.println("   |\t\t\t\t\t\t\t\t    |\n"
								+"   |        ERROR: Invalid username/password!\t\t\t    |\n"
								+"   |\t\t\t\t\t\t\t\t    |");
						//asking to go back to menu for changing password if user wants
						System.out.print("   |  ---> Do you want to enter details again? (y/any key): ");

						option = input.nextLine().trim();
						System.out.println("   |\t\t\t\t\t\t\t\t    |\n"
								+ "   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");

						if (!(option.equalsIgnoreCase("y"))) {
							//line break for formatted display
							logInMenu();
							break;
						}
					}
				}while(!findLoginData(logInCredentials,"login", ID, password));	
				return ID;}
			else if (loginOption.equals("2")) {
				return ID = forgotPassword(logInCredentials);
			}
			else if (loginOption.equals("3")) {
				return ID = "-1";}
			else {loginOption = validationString(loginOption, 3);}
		}
	}

	//method for changing password when credentials are forgotten
	public static String forgotPassword(String[][] logInCredentials) {
		String ID,password,email,phoneNo,enterAgain;
		do {
			displayMenu("forgotPassword");

			//taking ID, email and password as input
			displayMenu("ID");ID = Input.ID(		      "   |       Enter admin ID: ","admin");
			displayMenu("EMAIL");email = Input.email(       "   |       Enter email: ","admin");
			displayMenu("PHONENO");phoneNo = Input.phoneNo(   "   |       Enter phone number: ","admin");
			System.out.print(         "   |                                                                |\n"
					+ "   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
			//checking if credentials are found
			if (findLoginData(logInCredentials, "forgot", ID,email,phoneNo)) {
				//displaying msg
				System.out.println( 		"\n                       ***   Record Found!   ***\n");
				displayMenu("newPassword");displayMenu("PASSWORD");

				do {
					//asking for new password
					password = Input.password( "   |       Enter new password: ","admin");
					if(logInCredentials[adminIndex(logInCredentials, ID)][1].equals(password)) {displayMenu("currentPassword");} else{System.out.println();}
				}while(logInCredentials[adminIndex(logInCredentials, ID)][1].equals(password));

				System.out.println(           "   |                                                                |\n"
						+ "   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
						+ "                       ***   Password Changed   ***\n");
				//updating recording and it will display new record aswell
				updateLogInCredentials(logInCredentials, 1, ID,password);
				return ID;
			}
			else {
				System.out.println(         "\n                     ***   Record Not Found!   ***");
			}

			System.out.print(               "\n        ---> Do you want to enter details again?\n"
					+ "           [y] Yes           [n] No\n\n"
					+ "        Press any key to continue.. ");

			Scanner input = new Scanner (System.in);
			enterAgain = input.nextLine().trim();
			while(!enterAgain.equalsIgnoreCase("y") && !enterAgain.equalsIgnoreCase("n")) {
				enterAgain = validationString(enterAgain, 8);
			}
			if (enterAgain.equalsIgnoreCase("n")) {System.out.println();ID = logInMenu();}
			//asking to go back to menu for changing password if user wants
		}while(enterAgain.equalsIgnoreCase("y"));	
		return ID;
	}

	//for entering membership data and to change passwords in case of forgotten
	public static int updateLogInCredentials(String[][] logInCredentials, int code, String... information){
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
							logInCredentials[i][j] = generateAdminID(logInCredentials);
							return i;	//row index is returned at which member info is saved
						}
						logInCredentials[i][j] = information[j];
					}
				}
			}
			//if code 1 is passed, the following body will be used to change the password in forgotCredentials()
		case 1: 
			int adminIndex = adminIndex(logInCredentials, information[0]);
			//password is updated
			logInCredentials[adminIndex][1] = information[1];
			//displaying new record
			System.out.println( "      +--------------------------------------------------------+"
					+"\n                   **YOUR NEW LOGIN CREDENTIALS**"
					+"\n\n                    ID       : " + logInCredentials[adminIndex][0]
							+"\n                    Password : " + logInCredentials[adminIndex][1]
									+"\n\n      +-------------------------------------------------------+");
			return 0;
		default:
			return 0;
		}		
	}

	//checks if data entered at logIn is stored in array or not.
	public static boolean findLoginData(String[][] logInCredentials,String code, String... information) {
		//code: "login" for login block; "forgot" for forgot login block
		switch (code) {
		case "login":
			//each row is checked for entered credentials. array is checked till last record stored
			for (int i=0; i<lastEntityIndex(logInCredentials); i++ ) {
				//keeping row number constant, id(info[0]) at index 0 and password(info[1]) at index 1 is checked
				//for equality and true/false is returned accordingly
				if (logInCredentials[i][0].equals(information[0]) && logInCredentials[i][1].equals(information[1]))
					return true;
			}
			return false;	
		case "forgot":
			//each row is checked for entered credentials. array is checked till last record stored
			for (int i=0; i<lastEntityIndex(logInCredentials); i++ ) {
				//keeping row number constant, ID(info[0]) at index 0, email(info[1]) at index 2 and phoneNo(info[2]) at index 3 
				//is checked for equality and true/false is returned accordingly
				if (logInCredentials[i][0].equals(information[0]) && logInCredentials[i][2].equals(information[1])
						&& logInCredentials[i][3].equals(information[2]))
					return true;
			}
			return false;	
		default: 
			return false;
		}					
	}

	//returns index next to the index of last record stored
	public static int lastEntityIndex(String[][] array) {
		for (int i=0; i<array.length; i++) {
			//null row is searched, when found its index is returned
			if (array[i][0]==null) 
				return i;		
		}
		//otherwise length of array is sent as it will be full
		return array.length;
	}

	//ADD ADMIN
	public static void addNewAdmin(String[][] adminCredentials) throws IOException {
		Scanner input = new Scanner(System.in);

		//check if record is full or not. Index of row is also stored
		boolean recordFull = true;
		int index=-1;
		for (int i=0; i<adminCredentials.length; i++) {
			if (adminCredentials[i][0] == null) {
				recordFull = false;
				index = i;
				break;
			}
		}

		//if record is not full, admin account is created
		if(!recordFull) {
			String option =" ";
			do {

				//heading print
				displayMenu("CreatingAdmin");

				//auto generated id and password
				String id = generateAdminID(adminCredentials);
				String password = generateAdminPassword();

				//credentials are displayed and admin is asked if they want to create an account based on these credentials
				System.out.println(   "\n   |             +------------------------------------+             |"								   
						+   "\n   |                A D M I N  C R E D E N T I A L S                |"
						+   "\n   |                                                                |");
				System.out.printf("   %-21s%-12s%-32s%-2s","|","ID:",id,"|\n");
				System.out.printf("   %-21s%-12s%-32s%-2s","|","Password:",password,"|");
				System.out.println(   "\n   |                                                                |"
						+ "\n   |             +------------------------------------+             |");
				//validation check if user enters correct input
				System.out.print(       "   |                                                                |"
						+ "\n   |   --> Do you want to create account with these credentials?    |"   
						+ "\n   |                    [y]  Yes              [n] No                |" 
						+ "\n   |                                                                |"
						+ "\n   |       Press any key to continue.. ");

				option = input.nextLine().trim();
				while(!option.equalsIgnoreCase("y") && !(option.equalsIgnoreCase("n"))) {
					if (option.equals("")) {
						System.out.printf("\n   |       INVALID INPUT: Required Valid Input Which Is Not Null     |\n\n");
					}
					else {
						System.out.printf("\n   |       INVALID INPUT: Required Correct Option Number             |\n\n");
					}
					System.out.print("   |       --> Please enter a valid input: ");
					option = input.nextLine().trim();
				}

				//if user enters y, account is created otherwise loop is iterated again
				if (option.equalsIgnoreCase("y")) {
					displayMenu("EMAIL");String email = Input.email("   |       --> Enter email: ","admin");
					displayMenu("PHONENO");String phoneNo = Input.phoneNo("   |       --> Enter phone number: ","admin");
					adminCredentials[index][0] = id;
					adminCredentials[index][1] = password;
					adminCredentials[index][2] = email;
					adminCredentials[index][3] = phoneNo;
					System.out.println("   |                                                                |"
							+ "\n   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
					System.out.println( "\n                +-----------------------------------------+"								   
							+ "\n                   A D M I N  C R E D E N T I A L S \n"
							+ "\n                        ID:           "+id
							+ "\n                        Password:     "+password
							+ "\n                        Email:        "+email
							+ "\n                        Phone number: "+phoneNo
							+ "\n\n                +-----------------------------------------+");
					System.out.println("\n             *** An admin account "+adminCredentials[index][0]+" has been created! ***");
					arrayToAdminFile(adminCredentials, mainAdminCredentials);
				}

				if (option.equalsIgnoreCase("n")) {
					System.out.print("   |                                                                |"
							+ "\n   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n");
				}

			}while(option.equalsIgnoreCase("n"));
		}
		//else "record is full" message is displayed
		else
			System.out.println("\n           ***  Record is Full. Account cannot be created!  ***");
	}

	//generate admin id of format (ADM-000)
	public static String generateAdminID(String[][] adminCredentials) {
		Random rand = new Random();
		String adminID;
		boolean idExists;

		//infinite loop ends when id becomes unique i.e idExists = false
		do {
			idExists = false;
			adminID = "ADM-" + Integer.toString(rand.nextInt(10)) + rand.nextInt(10) + rand.nextInt(10);
			//each record's id is checked 
			for (int i=0; i<lastEntityIndex(adminCredentials); i++) {
				//if id found, idExists become true and for loop breaks
				if (adminCredentials[i][0].equals(adminID)) {
					idExists = true;
					break;
				}
			}
		}while(idExists == true);
		return adminID;
	}

	//generates random password of format (xyz123)
	public static String generateAdminPassword() {
		Random rand = new Random();
		String password="";

		for(int i=1; i<=5; i++) {
			if(i<=4)
				password += (char) (rand.nextInt(26) + 'A');
			else
				password += Integer.toString(rand.nextInt(1000)) + (char)(rand.nextInt(26) + 'A');;
		}
		//returns password
		return password;
	}

	//removes the specific admin account
	public static void removeID(String [][] Credentials, String accountType) {
		int count = 0,index;

		index = (accountType == "Admin")? 0: 4; 

		for(int k = 0; k< Credentials.length; k++) {
			if (Credentials[k][0] != null) {count++;}
		}

		if (count > 0) {
			System.out.print("\n                    ---------------------------------\n");
			System.out.printf("                     %-2s |   %-15s\n","No.",""+accountType+" ID");
			System.out.print("                    ---------------------------------\n");
			count = 0;
			for(int k = 0; k< Credentials.length; k++) {
				if (Credentials[k][0] != null) {
					System.out.printf("                      %-2d |   %-15s\n",k+1,Credentials[k][index]);count++;}
			}

			System.out.println("                    _________________________________\n");
			Scanner input = new Scanner (System.in);
			System.out.print("     --> Which "+accountType+" account do you want to remove? Enter serial number: ");
			String choice = input.nextLine().trim();
			while(true) {
				try {
					int item = Integer.parseInt(choice);
					if (item < 1 || item > count) {
						System.out.print("\n     --> Please enter a valid input from the given options: ");
						choice = input.nextLine().trim();
					}
					else {break;}
				}catch (NumberFormatException e){
					System.out.print("\n     --> Please enter a valid integer value: ");
					choice = input.nextLine().trim();
				}
			}

			int item = Integer.parseInt(choice)-1;
			System.out.println("\n                *** "+accountType+" ID "+ Credentials[item][index]+" has been removed ***");
			//System.out.println("\n                *** "+accountType+" ID "+ Credentials[item][0]+" has been removed ***");

			for(int i = item; i < Credentials.length-1; i++) {
				Credentials[i][0] = Credentials[i+1][0];
				Credentials[i][1] = Credentials[i+1][1];
				Credentials[i][2] = Credentials[i+1][2];
				Credentials[i][3] = Credentials[i+1][3];
				if (accountType == "Customer") Credentials[i][4] = Credentials[i+1][4];
			}

			Credentials[Credentials.length-1][0] = Credentials[Credentials.length-1][1] = null;
			Credentials[Credentials.length-1][2] = Credentials[Credentials.length-1][3] = null;
			if (accountType == "Customer") Credentials[Credentials.length-1][4] = null;
		}

		else {
			System.out.println("\n              *** There are no "+accountType+" accounts to remove ***");
		}

	}

	//CHANGE PASSWORD	
	public static void changePassword(String[][] logInCredentials, String ID) {

		//finding row number at which admin index is stored
		int adminIndex = adminIndex(logInCredentials, ID);	
		String password;
		displayMenu("changePassword");displayMenu("PASSWORD");

		do {
			//asking for new password
			password = Input.password("   |         Enter new password: ","admin");
			if(logInCredentials[adminIndex][1].equals(password)) { displayMenu("currentPassword");} else{System.out.println();}
		}while(logInCredentials[adminIndex][1].equals(password));

		//updating password
		logInCredentials[adminIndex][1] = password;
		System.out.println("   |                                                                |"
				+ "\n   +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+"
				+ "\n\t\t       *** Password changed! ***");
		System.out.println( "\n      +----------------------------------------------------------+"
				+"\n                    ** YOUR NEW LOGIN CREDENTIALS **"
				+"\n\n\t\t   ID:          "+logInCredentials[adminIndex][0]
						+"\n\t\t   Password:    "+logInCredentials[adminIndex][1]
								+"\n      +----------------------------------------------------------+");					
	}

	//returns the row number at which the record is present
	public static int adminIndex(String[][] adminCredentials, String ID) {
		//searches the record row wise to find the ID 
		for (int i=0; i<lastEntityIndex(adminCredentials); i++) {
			//when ID is found, the row number is passed 
			if (adminCredentials[i][0].equals(ID))
				return i;
		}
		return -1;
	}

	//generates product ID. index refers to the product row value in array
	public static String generateProductID(String[][] category, String categoryStr, int index) {

		Random rand = new Random();
		String ID;
		boolean found = true;

		do{
			//format letter + index + 2 digits
			switch (categoryStr) {
			case "Dairy":  ID = "D" + index + rand.nextInt(10) + rand.nextInt(10); break;
			case "Cooking Essentials":  ID = "CE" + index + rand.nextInt(10) + rand.nextInt(10); break;
			case "Beverages":  ID = "BE" + index + rand.nextInt(10) + rand.nextInt(10); break;
			case "Bathroom Supplies":  ID = "BS" + index + rand.nextInt(10) + rand.nextInt(10); break;
			case "Stationary":  ID = "ST" + index + rand.nextInt(10) + rand.nextInt(10); break;
			default: ID = "Invalid id"; 
			}

			found = false;
			for(int i=0; i<category.length;i++) {
				if (category[i][0] == ID) {
					found = true;
					break;
				}
			}
		} while (found);

		return ID;		
	}	

	/*AddProduct()
	 * 1. Asks the user in which category does he want to add the product
	 * 2. According to the selected category, the AddProdInfo() method is executed 
	 */
	public static void addProduct() {

		//Printing the main heading "Adding product"
		displayMenu("addingProduct");	
		String [][] Products = combineProducts();
		//Asks the user in which category does he want to add the product
		//Categories are mentioned, user selects the category by entering a serial number b/w 1 and 5
		displayMenu("categories"); 

		//Created a Scanner object to get input from the user
		Scanner input = new Scanner(System.in);

		//Stored the input value in the char variable 'categoryOption' 
		String categoryOption = input.nextLine().trim();

		//Loop continues until one of the conditions is true
		while (true) {
			//category number is used as case value in the switch statement
			if (categoryOption.equals("1")) {addProdInfo(Products, ctgDairy,costQuantityDairy,"Dairy");break;} //Details are added in "Dairy" category
			else if (categoryOption.equals("2")) {addProdInfo(Products, ctgCookingEssentials,costQuantityCookingEssentials,"Cooking Essentials");break;} //Details are added in "Cooking Essentials" category
			else if (categoryOption.equals("3")) {addProdInfo(Products, ctgBeverages,costQuantityBeverages,"Beverages");break;} //Details are added in "Beverages" category
			else if (categoryOption.equals("4")) {addProdInfo(Products, ctgBathroomSupplies,costQuantityBathroomSupplies,"Bathroom Supplies");break;} //Details are added in "Bathroom Supplies" category
			else if (categoryOption.equals("5")) {addProdInfo(Products, ctgStationary,costQuantityStationary,"Stationary");break;} //Details are added in "Stationary" category
			else if (categoryOption.equals("0")) {break;}//no details are added. flow goes to main menu
			else {categoryOption = validationString(categoryOption, 10);}
		}
	}

	/*CombineProducts()
	 * This method is used to combine the products of all the categories in a single String array named as "Products"
	 */
	public static String[][] combineProducts() {
		//A string array "Products" will store the list of all the products
		String [][] Products = new String [100][5];

		//i is declared as 0 to start from the product at 0th index of a specific category
		int i=0;

		//Loop ends when all five category products are stored in "Products" array
		for(int ctg=0;ctg<=4;ctg++) {

			if (ctg == 0) {i = combineCategory(Products, ctgDairy,costQuantityDairy, "Dairy",i);} //Products of "Dairy" category are added in "Products" array
			if (ctg == 1) {i = combineCategory(Products, ctgCookingEssentials,costQuantityCookingEssentials, "Cooking Essentials",i);} //Products of "Cooking Essentials" category are added in "Cooking Essentials" array
			if (ctg == 2) {i = combineCategory(Products, ctgBeverages,costQuantityBeverages, "Beverages",i);} //Products of "Beverages" category are added in "Beverages" array
			if (ctg == 3) {i = combineCategory(Products, ctgBathroomSupplies,costQuantityBathroomSupplies,"Bathroom Supplies",i);} //Products of "Bathroom Supplies" category are added in "Bathroom Supplies" array
			if (ctg == 4) {i = combineCategory(Products, ctgStationary,costQuantityStationary, "Stationary",i);} //Products of "Stationary" category are added in "Stationary" array
		}
		return Products;
	}

	/*uniqueName()
	 * This method is used the new name entered by the user is already stored or not.
	 * Here another method GetIndexofProduct is used. If this method returns something other than -1, this implies that a product with a similar name exists
	 * Otherwise, the user can save the product with this name.
	 */
	public static boolean uniqueName(String name, String [][] Products) {	
		if (getIndexOfProduct(Products,"name",name) == -1) {return true;}
		else {return false;}
	}

	/*Capitalize()
	 * This method is used to capitalize first letter of each word in a string.
	 * This method is used when the user enters a product name to add a new product, or to rename an existing product.
	 */
	public static String capitalize(String s) {
		char ch = ' '; //variable for storing each character
		String s2 = ""; //another string variable for storing new string
		//checking for each string character
		for (int i = 0; i < s.length(); i++) {
			//changing the character to upper case if it is the first letter of a word
			if (ch == ' ' && s.charAt(i) != ' ') {
				char c = Character.toUpperCase(s.charAt(i));
				s2 += c; //concatenating the upper case character in the new string
			}
			else {
				char c = s.charAt(i);
				s2 += c; //concatenating other characters in new string
			}
			ch = s.charAt(i); //storing character are index i in ch
		}
		return s2; //returning the new string
	}

	/*ViewList()
	 * This method is used to view the list of products in alphabetical order, by increasing prices, or by a specific category.
	 * 1- A string array "Products" will be initialized to store all the products
	 * 2- We will loop through all the categories. 
	 * 3- On each category, CombineCategory() method will be invoked, where all the products will be added to "Products" array
	 * 4- The user will be asked if he wants to view the list of products in alphabetical order, by increasing prices, or by a specific category.
	 * 5- For viewing the list of products in alphabetical order, SortByProducts() method will be invoked.
	 * 6- For viewing the list of products by increasing prices, SortByPrices() method will be invoked.
	 * 7- For viewing the list of products of a specific category, ViewCategoryList() method will be invoked, where category products will also be printed.
	 * 8- Then all the products will be printed for the first two options.
	 */
	public static void viewList() {

		String [][] Products = combineProducts();
		//Asks the user how he wants to display the display the list of products
		displayMenu("viewingProducts");

		Scanner input = new Scanner (System.in);

		//Input validation so that user only inputs a number from one of the options available
		String option = input.nextLine().trim();
		while (true) {

			//if user wants to view all products, which are displayed alphabetically
			if(option.equals("1")) {
				if (lastEntityIndex(Products) == 0) {
	                System.out.println("\n           ***  Sorry, there are no products left to view  ***");
				}
				sortProducts(Products,1); 
				break;}

			//if user wants to view all products, where respective prices are displayed in ascending order
			else if(option.equals("2")) {
				if (lastEntityIndex(Products) == 0) {
	                System.out.println("\n           ***  Sorry, there are no products left to view  ***");
				}
				sortProducts(Products,2);break;}

			//If user wants to view products of a specific category
			else if(option.equals("3")) {
				displayMenu("categorySelection");
				System.out.println("\n          |      Which category do you want to view list of?      |");
				viewCategoryList();break;
			}
			else {option = validationString(option, 4);}
		}

		//For first two options where all products are to be displayed
		if ((option.equals("1") || option.equals("2")) && lastEntityIndex(Products) >= 1) {
			System.out.println("\n    +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+");
			System.out.printf("    |  %-3s%-39s|  %-9s|   %-7s|  %-8s  |        %-16s|","No. |"," Product","ProdID","Cost","In Stock","Category");	
			System.out.println("\n    |~~~~~~+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+~~~~~~~~~~~+~~~~~~~~~~+~~~~~~~~~~~~+~~~~~~~~~~~~~~~~~~~~~~~~|");
			for(int m= 0;m<Products.length;m++) {

				//the null items are skipped 
				if(Products[m][0] != null) {
					System.out.printf("    |  %-4s| %-38s|   %-8s| PKR %-5s|    %-7s |   %-21s|",m+1, Products[m][1],Products[m][0], Products[m][3], Products[m][4],Products[m][2]);
					System.out.println("\n    |------+---------------------------------------+-----------+----------+------------+------------------------|");
				}
			}
		}

	}

	/*SortProducts()
	 * Products are sorted in alphabetical order, or in terms of increasing prices
	 * 1- Current product and the adjacent product are compared using loop
	 * 2- The two strings are compared lexicographically in case of names.
	 * 3- If the first string is less than the adjacent string (less than 0 is returned by compareTo() method), swapping the products and their details placements
	 * 4- If the first string is greater than or equal to the adjacent string (greater than 0 or 0 is returned by compareTo() method), we will ignore these.
	 * 5- null items are ignored, which are present in the empty spaces  
	 * 6- Array "Products" will be sorted alphabetically, or in terms of increasing prices, at the end of the loop.  
	 */
	public static void sortProducts(String [][] Products, int choice) {
		String temp;
		for (int j = 0; j < Products.length; j++) {
			for (int i = j + 1; i < Products.length; i++) {
				//Excluding null items
				if (Products[i][2] != null && Products[j][2] != null) {
					//If the first string is less than the adjacent string or the first price is less than the adjacent price
					if ((choice == 1 && (Products[i][1].compareTo(Products[j][1]) < 0)) || 
							(choice == 2 && (Integer.parseInt(Products[i][3])  <  Integer.parseInt(Products[j][3])))) {

						// Products IDs will be swapped
						temp = Products[j][0];
						Products[j][0] = Products[i][0];
						Products[i][0] = temp;

						// Products names will be swapped
						temp = Products[j][1];
						Products[j][1] = Products[i][1];
						Products[i][1] = temp;

						// Category names will be swapped
						temp = Products[j][2];
						Products[j][2] = Products[i][2];
						Products[i][2] = temp;

						// Products prices will be swapped
						temp = Products[j][3];
						Products[j][3] = Products[i][3];
						Products[i][3] = temp;

						// Products quantities will be swapped
						temp = Products[j][4];
						Products[j][4] = Products[i][4];
						Products[i][4] = temp;
					}
				}   
			}
		}
	}

	/*ViewCategoryList()
	 * This method will print the products of a specific category
	 * 1- User is asked the category name through serial numbers
	 * 2- According to that serial number(which corresponds to a specific category), PrintCategoryProducts() method is invoked.
	 * 3- In PrintCategoryProducts(), products and their details are displayed, which are mentioned in the arguments. 
	 */
	public static int viewCategoryList() {
		//Asks the user in which category does his product belong
		displayMenu("categories");  
		Scanner input = new Scanner(System.in);

		//Input validation so that the correct number is entered from the mentioned options
		String categoryOption = input.nextLine().trim();

		//comparing according to entered category option
		while (true) {
			if (categoryOption.equals("1")) {printCategoryProducts(ctgDairy,costQuantityDairy,"Dairy");break;}  //prints first category products
			else if (categoryOption.equals("2")) {printCategoryProducts(ctgCookingEssentials,costQuantityCookingEssentials,"Cooking Essentials");break;} //prints second category products
			else if (categoryOption.equals("3")) {printCategoryProducts(ctgBeverages,costQuantityBeverages,"Beverages");break;} //prints third category products
			else if (categoryOption.equals("4")) {printCategoryProducts(ctgBathroomSupplies,costQuantityBathroomSupplies,"Bathroom Supplies");break;} //prints fourth category products
			else if (categoryOption.equals("5")) {printCategoryProducts(ctgStationary,costQuantityStationary,"Stationary");break;} //prints fifth category products
			else if (categoryOption.equals("0")) {break;}
			else {categoryOption = validationString(categoryOption, 10);}
		}
		return Integer.parseInt(categoryOption);
	}

	/*CombineCategory()
	 * This method will loop through a specific category, and add it to the String array "Products"
	 */
	public static int combineCategory(String [][] Products, String[][] cat,int[][] costAndQty, String category,int i){

		//integer variable ctgCount is initialized and declared as 0 
		//This variable shows how many products have been added of a certain category, this variable will help us heading towards the next category
		int ctgCount = 0;

		//Continues until the end of "Products" is reached
		while (i < Products.length && cat[ctgCount][1] != null) {
			//Product ID, name and category of the specific category at the current CtgCount will be stored at ith index of the "Products" array in first column.
			Products[i][0] = cat[ctgCount][0];Products[i][1] = cat[ctgCount][1]; Products[i][2] = category;
			//Product price and quantity of the specific category at the current CtgCount will be stored at ith index of the "Products" array in third column.
			Products[i][3] = Integer.toString(costAndQty[ctgCount][0]); Products[i][4] = Integer.toString(costAndQty[ctgCount][1]);
			i++; ctgCount++; //i and ctgCount is incremented		
		}
		return i; //i is returned 
	}

	/*AddProdInfo()
	 * 1. Loop through the category array and check if there is a string value 'null'
	 * 2. String 'null' value means there is an empty space in the user-specified category
	 * 3. If there is a string value 'null' --> its index is stored in the int variable 'index', and add the product details 
	 * 4. If there is no string value 'null' --> the int variable 'index' is assigned -1, and tell the user that there is no empty space in 
	 * the specified category
	 */
	public static void addProdInfo(String[][] Products,String[][] category, int[][] costAndQty, String categoryStr) {

		//int variable 'index' is declared and initialized to store the index of the place where there is String 'null' value.
		int index = -1;

		//Loop through the category array
		for(int i=0; i<category.length;i++) {

			//if there is a string value 'null'
			if (category[i][1] == null) {
				index = i; //its index is stored in the int variable 'index'
				break; //break out of the loop 
			}
		}

		//if there is a string value 'null'
		if(index!= -1) {

			category[index][0] = generateProductID(category,categoryStr,index);
			//Asks the user to enter the product details e.g name, price, quantity
			System.out.print("\n          ---> Please enter name of the product: ");
			Scanner input = new Scanner (System.in);
			String name = capitalize(input.nextLine().toLowerCase());
			while(true) {	
				if (name.length() >= 3 && (uniqueName(name, Products))) {
					break;}
				else {
					if (!(uniqueName(name,Products))) {System.out.print("\n          ERROR: A product with a similar name already exists\n\n         ---> Please enter a unique name: ");}
					else if (name.length() == 0) {System.out.print("\n          ERROR: Name can not be null value.\n\n         ---> Please enter a name which is not null: ");}
					else if (name.length() < 3) {System.out.print("\n          ERROR: Required string with minimum length of 3\n\n         ---> Please enter a name with the valid length: ");}
					name = capitalize(input.nextLine().toLowerCase());
				}
			}
			category[index][1] = name; //Replaces the string value 'null' with the name of the product
			String price = inputDetail("price",name);
			//The valid input for "price" is converted into integer and stored in costAndQty[index][0]
			costAndQty[index][0] = Integer.parseInt(price); //Replaces the int value 'null' with the price of the product

			String qty = inputDetail("quantity",name);	
			//The valid input for "qty" is converted into integer and stored in costAndQty[index][1]
			costAndQty[index][1] = Integer.parseInt(qty); //Replaces the int value 'null' with the quantity of the product 

			System.out.print("\n                      *** Product added successfully ***\n"); //Informs the user that product has been added 
			printProduct(category,costAndQty, index, categoryStr); //PrintProduct() method is called which prints the specified product details	
		}

		//if there is no string value 'null'
		if(index == -1) { //tells the user that there is no empty spaces in the specified category
			System.out.println("\n           +---------------------------------------------------+"
					+ "\n                Sorry you can't overload new products in the "
					+ "\n                specified category."
					+ "\n           +---------------------------------------------------+");
		}
	}

	/*inputDetail()
	 * This method is used to get correct input from the user, when he tries to enter price or quantity for a product.
	 */
	public static String inputDetail(String string, String name) {
		Scanner input = new Scanner (System.in);
		//User is asked to input the price of the product
		System.out.print("\n          ---> What is the "+string+" of "+name+": ");

		//Input is stored in the string variable "price"
		String detail = input.nextLine();

		//Loop continues as long as valid input is not entered 
		while (true) {	
			try {		
				//We try to convert the "price" into integer and store in item
				int item = Integer.parseInt(detail);

				if(item == 0) {		
					System.out.print("\n          ERROR: "+string.toUpperCase()+" CAN NOT BE 0."
							+ "\n\n          ---> Please enter a valid "+string+" of "+name+": ");
					detail = input.nextLine();
				}
				else if (item < 0) { //If the current input is negative
					System.out.print("\n          ERROR: "+string.toUpperCase()+" CAN NOT BE NEGATIVE."
							+ "\n\n          ---> Please enter a valid "+string+" of "+name+": ");
					detail = input.nextLine();
				}
				//When valid input is entered 
				else {return detail;}

			} catch (NumberFormatException e) { //If an exception is raised while trying to convert "price" into integer 
				//User is asked to enter an integer value
				System.out.print("\n          ERROR: "+string.toUpperCase()+" MUST BE AN INTEGER VALUE."
						+ "\n\n          ---> Please enter a valid "+string+" of "+name+": ");
				detail = input.nextLine();
			}
		}
	} 

	/*UpdateProduct()
	 * This method asks about the user requirements and updates the product according to it
	 * 1. Asks user what he wants to change about a specific product
	 * 2. If name, then asks the new name, and updates it.
	 * 3. If price, then asks the new price, and updates it.
	 * 4. If quantity, then asks the new quantity, and updates it.
	 * 5. Prints the updated product and its details
	 */
	public static void updateProduct(String[][] Products,int[] selectProd,String[][] category,int[][] CostAndQty, int index, String categoryName) {

		//if product found 
		if (selectProd[0] != -1) {

			//Asks user what he wants to change about a specific product
			System.out.print("\n       --> What do you want to change?"
					+ "\n       [1]  Name    [2]  Price    [3]  Quantity   [4] All of them\n"
					+ "\n       Press any key to continue.. ");

			Scanner input = new Scanner (System.in);
			String option = input.nextLine().trim();
			while(true) {
				//If name, then asks the new name, and updates it
				if (option.equals("1") || option.equals("4")) {
					System.out.print("\n       ---> Enter the new name of the product: ");
					String name = capitalize(input.nextLine().toLowerCase());

					while(true) {	
						//If the product is not null value and is a unique name, it breaks
						if (name.length() != 0 && (uniqueName(name, Products))) {break;}
						else {
							if (!(uniqueName(name,Products))) {System.out.print("\n       ERROR: A product with a similar name already exists\n\n       ---> Please enter a unique name: ");}
							else if (name.length() == 0) {System.out.print("\n       ERROR: Name can not be null value.\n\n       ---> Please enter a name which is not null: ");}
							else if (name.length() < 3) {System.out.print("\n       ERRORs: Required string with minimum length of 3.\n\n       ---> Please enter a name with the valid length: ");}
							name = capitalize(input.nextLine().toLowerCase());}}
					category[index][1] = name;
					if (option.equals("1")) {break;}
				}

				//If price, then asks the new price, and updates it.
				if (option.equals("2") || option.equals("4")) {
					String price = inputDetail("new price",category[index][1]); //Input is stored in the string variable "price"
					CostAndQty[index][0] = Integer.parseInt(price); //The valid input for "price" is converted into integer and stored in costAndQty[index][0]
					if (option.equals("2")) {break;}
				}
				//If quantity, then asks the new quantity, and updates it.
				if (option.equals("3") || option.equals("4")) {
					String qty = inputDetail("new quantity",category[index][1]); //Input is stored in the string variable "qty"
					CostAndQty[index][1] = Integer.parseInt(qty); //The valid input for "qty" is converted into integer and stored in costAndQty[index][1]
					break;
				}
				//else asks the user to enter a correct serial number
				else {option = validationString(option, 7);}
			}
		}
		//Prints the updated product and its details
		if (selectProd[0] != -1) {
			printProduct(category, CostAndQty, index, categoryName);}
	}	

	public static void removeAndShiftEntities(int itemIndex, String[][] category, int[][] costAndQty) {
		for(int i = itemIndex; i < lastEntityIndex(category); i++) {
			category[i][0] = category[i+1][0];
			category[i][1] = category[i+1][1];
			costAndQty[i][0] = costAndQty[i+1][0];
			costAndQty[i][1] = costAndQty[i+1][1];
		}

		category[lastEntityIndex(category)][0] = category[lastEntityIndex(category)][1] = null;
		costAndQty[lastEntityIndex(category)][0] = costAndQty[lastEntityIndex(category)][1] = -1;
	}

	/*RemoveProd()
	 * This method searches for a product and removes it
	 * 1. Invokes SearchProd() method to search for the product
	 * 2. SearchProd() returns an array, where index of product is stored at 0th index of the array.
	 * 3. If the index is a number other than -1(which represents that the product is not found), then asks for a confirmation message.
	 * 4. If yes than delete the product
	 */
	public static void removeProd() {

		//Printing the main heading "Removing product"
		displayMenu("RemovingProduct");

		//SearchProd() method is invoked and the returned array is stored in selectProd array.
		int[] selectProd = searchProd("name");

		//If product is found
		if (selectProd[0] != -1) {
			System.out.print("\n       ---> Do you really want to delete this product?"
					+ "\n\n              [y] Yes                [n] No"
					+ "\n\n       Press any key to continue.. ");
			Scanner input = new Scanner(System.in);
			String option = input.nextLine().trim();
			while(true) {
				//if user agrees on removing the product
				if (option.equalsIgnoreCase("y")) {
					switch (selectProd[1]) {

					//product name is deleted by initialized it null-product name and price are deleted by initializing them 0 
					case 0: removeAndShiftEntities(selectProd[0], ctgDairy, costQuantityDairy);
					System.out.print("\n                *** THE PRODUCT HAS BEEN REMOVED ***\n");break;
					case 1: removeAndShiftEntities(selectProd[0], ctgCookingEssentials, costQuantityCookingEssentials);
					System.out.print("\n                *** THE PRODUCT HAS BEEN REMOVED ***\n");break;
					case 2: removeAndShiftEntities(selectProd[0], ctgBeverages, costQuantityBeverages);
					System.out.print("\n                *** THE PRODUCT HAS BEEN REMOVED ***\n");break;
					case 3: removeAndShiftEntities(selectProd[0], ctgBathroomSupplies, costQuantityBathroomSupplies);
					System.out.print("\n                *** THE PRODUCT HAS BEEN REMOVED ***\n"); break;
					case 4: removeAndShiftEntities(selectProd[0], ctgStationary, costQuantityStationary); 
					System.out.print("\n                *** THE PRODUCT HAS BEEN REMOVED ***\n");break;}
					break;}

				if(option.equalsIgnoreCase("n")) {break;}

				//if wrong 
				else {option = validationString(option, 7);}
			}
		}
	}

	/*SearchProd()
	 * This method is used to search for a product depending on the search method asked for.
	 */
	public static int [] searchProd(String searchMethod) {

		Scanner input = new Scanner (System.in);
		int index = -1,categoryOption = -1;
		int[] selectProd = new int [2]; //Declared and initialized "selectProd", "index"- selectProd[1] = "Category" selectProd[0]= index

		switch (searchMethod) {
		case "name": //If the user wants to search product by name
			//Gets product name from the user as a key to search
			System.out.print("\n       ---> What is the name of your desired product? ");
			String key = input.nextLine().trim();

			while(key.length() < 2 ) {
				System.out.print("\n       INVALID ERRORs: Required string with minimum length of 2"
						+ "\n       ---> Please enter a valid name of your desired product:  ");
				key = input.nextLine().trim();
			}

			String [][] Products = combineProducts();

			int itemNo = 0;
			String[][] found = new String [50][5];
			boolean foundProduct = false;

			//Searching for the product
			//Key should be at least three letters to make a search
			if (key.length() >= 2) {

				for(int j = 0; j < lastEntityIndex(Products); j++) {

					if ((Products[j][1].toLowerCase()).contains(key.toLowerCase())) { //Checking if the substring is in any of the products
						found[itemNo][0] = Products[j][0]; //Product ID is added to 'found'
						found[itemNo][1] = Products[j][1]; //Product name is added to 'found'
						found[itemNo][2] = Products[j][2]; //Product category is added to 'found'
						found[itemNo][3] = Products[j][3]; //Product cost is added to 'found'
						found[itemNo][4] = Products[j][4]; //Product quantity is added to 'found'
						itemNo++;
						foundProduct = true;
					}
				}
			}

			//Removing duplicate elements
			for(int s=0;s<found.length-1;s++)
			{
				for(int m=s + 1;m<found.length;m++)
				{
					//if a match is found (excluding null items)
					if(found[s][1] != null && (found[s][1].equals(found[m][1])))
					{	
						for(int i = m; i < lastEntityIndex(found); i++) {
							found[i][0] = found[i+1][0];
							found[i][1] = found[i+1][1];
							found[i][2] = found[i+1][2];
							found[i][3] = found[i+1][3];
							found[i][4] = found[i+1][4];
						}

						found[lastEntityIndex(found)][0] = found[lastEntityIndex(found)][1] = found[lastEntityIndex(found)][2] 
								= found[lastEntityIndex(found)][3] = found[lastEntityIndex(found)][4] = null;
					}
				} 
			}

			String option = "";

			//If no product is found till the end, then the user is informed that there is no product with such name.
			if(!foundProduct) {System.out.print("\n                    *** No results found for '"+key+"' ***\n");}

			else {
				//if more than one product is found
				if (lastEntityIndex(found) > 1 &&  !(key.equalsIgnoreCase(found[0][1]))) {

					//Printing the searched results 
					displayMenu("SearchedResults");
					for(int itemIndex = 0; itemIndex < found.length; itemIndex++) {
						if (found[itemIndex][1] != null) {
							System.out.printf("       |  %-4s| %-38s|   %-8s| PKR %-5s|    %-7s |   %-21s|",itemIndex+1,found[itemIndex][1],found[itemIndex][0],found[itemIndex][3], 
									found[itemIndex][4],found[itemIndex][2]);
							if (itemIndex < lastEntityIndex(found)-1) {System.out.println("\n       |------+---------------------------------------+-----------+----------+------------+------------------------|");}
							else {System.out.println("\n       +------+---------------------------------------+-----------+----------+------------+------------------------+");}
						}
					}

					System.out.print("\n          --> Did you mean any one of these products?"
							+ "\n                   [y] Yes                [n] No"
							+ "\n\n          Press any key to continue.. ");
					option = input.nextLine().trim();

					while (true) {
						if (option.equalsIgnoreCase("y") || option.equalsIgnoreCase("n")) {break;}
						else {
							if (option.equals("")) {System.out.print("\n          --> Please enter a valid input which is not null: ");}
							else {System.out.print("\n          --> Please enter a valid input: ");}
							option = input.nextLine().trim();}
					}

				}else if (lastEntityIndex(found) == 1 && key.equalsIgnoreCase(found[0][1])) {option = "y";} //if only one exact product is found 

				if (option.equalsIgnoreCase("y")) {
					String prodNum = "";

					if (lastEntityIndex(found) > 1) {
						System.out.print("\n          --> Enter the product number: ");
						while (true) {
							try {
								prodNum = input.nextLine().trim();
								int num = Integer.parseInt(prodNum);
								if (num < 1 || num > lastEntityIndex(found)) {
									System.out.print("\n          --> Please enter a valid input from the given options: ");
								}
								else {
									break;}
							}catch (NumberFormatException e) {
								System.out.print("\n          --> Please enter a valid integer value: ");
							}
						}
					}

					else if (lastEntityIndex(found) == 1) {prodNum = "1";}

					if (found[Integer.parseInt(prodNum)-1][0].contains("D")) {
						index = getIndexOfProduct(ctgDairy,"name",found[Integer.parseInt(prodNum)-1][1]); printProduct(ctgDairy, costQuantityDairy, index, "Dairy"); selectProd[1]=0;}
					else if (found[Integer.parseInt(prodNum)-1][0].contains("CE")){
						index = getIndexOfProduct(ctgCookingEssentials,"name",found[Integer.parseInt(prodNum)-1][1]); printProduct(ctgCookingEssentials, costQuantityCookingEssentials, index, "Cooking Essentials"); selectProd[1]=1;}
					else if (found[Integer.parseInt(prodNum)-1][0].contains("BS")){
						index = getIndexOfProduct(ctgBathroomSupplies,"name",found[Integer.parseInt(prodNum)-1][1]); printProduct(ctgBathroomSupplies,costQuantityBathroomSupplies, index, "Bathroom Supplies"); selectProd[1]=3;}
					else if (found[Integer.parseInt(prodNum)-1][0].contains("BE")){
						index = getIndexOfProduct(ctgBeverages,"name",found[Integer.parseInt(prodNum)-1][1]); printProduct(ctgBeverages, costQuantityBeverages, index, "Beverages"); selectProd[1]=2;}
					else if (found[Integer.parseInt(prodNum)-1][0].contains("ST")){
						index = getIndexOfProduct(ctgStationary,"name",found[Integer.parseInt(prodNum)-1][1]); printProduct(ctgStationary, costQuantityStationary, index, "Stationary"); selectProd[1]=4;}
				}

				else if (option.equalsIgnoreCase("n")) {break;}
			}

			break;

		case "category": //If the user wants to search product by category
			//Asks the user in which category does his product belong
			displayMenu("categorySelection");
			System.out.println("\n\t  |      In which category does your product belong?      |");
			categoryOption = viewCategoryList();
			if (categoryOption == 1) {index = getIndexOfProduct(ctgDairy,"category","");if (index != -1) printProduct(ctgDairy, costQuantityDairy, index, "Dairy");}
			else if (categoryOption == 2) {index = getIndexOfProduct(ctgCookingEssentials,"category","");if (index != -1)printProduct(ctgCookingEssentials, costQuantityCookingEssentials, index, "Cooking Essentials");}
			else if (categoryOption == 3) {index = getIndexOfProduct(ctgBeverages,"category","");if (index != -1)printProduct(ctgBeverages, costQuantityBeverages, index, "Beverages");}
			else if (categoryOption == 4) {index = getIndexOfProduct(ctgBathroomSupplies,"category","");if (index != -1)printProduct(ctgBathroomSupplies, costQuantityBathroomSupplies, index, "Bathroom Supplies");}
			else if (categoryOption == 5) {index = getIndexOfProduct(ctgStationary,"category","");if (index != -1)printProduct(ctgStationary, costQuantityStationary, index, "Stationary");}break;
		} 

		selectProd[0]=index; //Stored the value of index in selectProd at 0 index}
		return selectProd; //returns selectProd array
	}

	/*GetIndexOfProduct()
	 * This method loops through a mentioned category and checks for the product name
	 * 
	 * BY NAME 
	 * 1. Declared and initialized count =0 to calculate the index 
	 * 2. Declared and initialized index = -1, that remains -1 if the product is not found in the specified category
	 * 3. Declared and initialized found = false, that remains false if the product is not found in the specified category 
	 * 4. Using for..each loop to loop through the specific category
	 * 5. If the product is found, then count is stored in the index and found is said to be true
	 * 6. If the product is not found, then count is incremented, found remains false, and index remains -1
	 * 
	 * BY CATEGORY
	 * 1. User is asked the product number.
	 * 2. Using that product number as an index, the product details are displayed.
	 */
	public static int getIndexOfProduct(String [][] category,String searchMethod,String key) {

		//Declared and initialized count,index and found
		int count = 0, index = -1;

		switch (searchMethod) {
		case "name":

			//Loop through the specific category
			for(int i = 0; i < category.length; i++) {

				//If the product name is found in the category
				if (key.equalsIgnoreCase(category[i][1])) {
					index = count;
					return index; //current index is returned
				} else {
					count++; //count is incremented
				}
			}
			return index;

		case "category":
			
			if (lastEntityIndex(category) == 0) {
				System.out.println("\n                ***  Sorry, there are no products in the specified category  ***");
				return -1;
			}
			else {
				Scanner input = new Scanner (System.in);
				System.out.print("\n          ---> Enter the product number: ");
				String productNum = input.nextLine().trim();

				while (true) {
					try {
						int item = Integer.parseInt(productNum);
						if (item < 1 || item > lastEntityIndex(category)) {
							System.out.print("\n          ---> Please enter a valid product number from the given list: ");productNum = input.nextLine().trim();}
						else {break;}
					}catch (NumberFormatException e) {
						System.out.print("\n          ---> Please enter a valid product number: ");productNum = input.nextLine().trim();}
				}
				return Integer.parseInt(productNum)-1;		
			}		
		}
		return index;
	}

	/*PrintProduct()
	 * This method prints the specific product and its details
	 * Details are mentioned in the arguments enclosed within the brackets.
	 */
	public static void printProduct(String[][] category, int[][] CostAndQty, int index, String categoryName) {
		System.out.print("\n       +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+\n"
				+ "       |              P R O D U C T   D E T A I L S            |   "
				+ "\n       |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.printf("\n       | %-15s|  %-36s|","Product ID",category[index][0]);
		System.out.print("\n       |----------------+--------------------------------------|");
		System.out.printf("\n       | %-15s|  %-36s|","Product Name",category[index][1]);
		System.out.print("\n       |----------------+--------------------------------------|");
		System.out.printf("\n       | %-15s|  %-36s|","Category",categoryName);
		System.out.print("\n       |----------------+--------------------------------------|");
		System.out.printf("\n       | %-15s|  PKR %-4s%-28s%-2s","Cost",CostAndQty[index][0],"/-","|");
		System.out.print("\n       |----------------+--------------------------------------|");
		System.out.printf("\n       | %-15s|  %-36s|","In stock",CostAndQty[index][1]);
		System.out.print("\n       +----------------+--------------------------------------+\n");
	}

	/*PrintCategoryProducts()
	 * This method prints the specific product and its details
	 * Details are mentioned in the arguments enclosed within the brackets.
	 */
	public static void printCategoryProducts(String [][] category,int[][] CostAndQty, String categoryName) {
		switch (categoryName) {
		case "Dairy":
			System.out.print("\n                             ----------------------------------------"
					+ "\n +---------------------------|          D   A   I   R   Y            |---------------------------+"
					+ "\n                             ---------------------------------------- ");break;
		case "Cooking Essentials":
			System.out.print("\n                   ---------------------------------------------------------"
					+ "\n +-----------------|  C  O  O  K  I  N  G    E  S  S  E  N  T  I  A  L  S   |-------------------+"
					+ "\n                   --------------------------------------------------------- ");break;
		case "Beverages":
			System.out.print("\n                            -----------------------------------------"
					+ "\n +--------------------------|      B  E  V  E  R  A  G  E  S         |--------------------------+"
					+ "\n                            ----------------------------------------- ");break;
		case "Bathroom Supplies":
			System.out.print("\n                   ----------------------------------------------------------"
					+ "\n +-----------------|    B  A  T  H  R  O  O  M   S  U  P  P  L  I  E  S      |------------------+"
					+ "\n                   ----------------------------------------------------------");break;
		case "Stationary":
			System.out.print("\n                           -----------------------------------------"
					+ "\n +-------------------------|     S  T  A  T  I  O  N  A  R  Y       |---------------------------+"
					+ "\n                           ----------------------------------------- ");break;
		}
		System.out.println("\n       +~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("       |  %-3s%-39s|  %-9s|   %-7s|  %-8s  |","No. |"," Product","ProdID","Cost","In Stock");	
		System.out.println("\n       |~~~~~~+~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~+~~~~~~~~~~~+~~~~~~~~~~+~~~~~~~~~~~~+");
		for(int i=0; i< lastEntityIndex(category);i++) {
			if(category[i][1] != null) {
				System.out.printf("       |  %-4s| %-38s|   %-8s| PKR %-5d|    %-7d |",i+1,category[i][1],category[i][0],CostAndQty[i][0], CostAndQty[i][1]);
				if (i < (lastEntityIndex(category)-1)) {System.out.println("\n       |------+---------------------------------------+-----------+----------+------------|");}
				else {System.out.println("\n       +------+---------------------------------------+-----------+----------+------------+");}
			}
		}
	}

	//all admin details are fed into the Admin file
	public static void arrayToAdminFile(String[][] adminCredentials, String[][] mainAdminCredentials) throws FileNotFoundException {
		File objFile = new File("AdminDetails.txt");

		PrintWriter write = new PrintWriter(objFile);
		//serial number variable
		int sno=1;
		//data is saved into the file
		//heading
		write.println("SNO\t:ADMIN ID\t:PHONE NO\t:EMAIL\t\t\t\t\t\t:PASSWORD");
		//on 2nd line of file, main admin credentials are stored
		//index 0:	admin id
		//index 1:	password
		//index 2:	email
		//index 3:	phone no
		write.println("1)\t:"+mainAdminCredentials[0][0]+"\t:"+mainAdminCredentials[0][3]+"\t:"+mainAdminCredentials[0][2]+"\t\t\t\t:"+mainAdminCredentials[0][1]);
		//sub-admins' credentials added
		for (int i=0; i<adminCredentials.length; i++ ) {
			if (adminCredentials[i][0] != null) {
				write.println((sno+1)+")\t:"+adminCredentials[i][0]+"\t:"+adminCredentials[i][3]+"\t:"+adminCredentials[i][2]+"\t\t\t\t:"+adminCredentials[i][1]);
				sno++;
			}
			else break;
		}
		//file closed
		write.close();
	}
}

