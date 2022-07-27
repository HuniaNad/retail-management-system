package project;

import java.util.Scanner;

public class Input {
	//INPUTS ID,PASSWORD,EMAIL,PHONE NO
	public static String ID(String string, String sectionType) {
		Scanner input = new Scanner(System.in);
		String ID="";
		//Asking for name with validation check 
		while(!contentCheck.isIDValid(ID)){
			System.out.print(string);
			ID = input.nextLine();

			switch (sectionType) {
			case "admin":
				System.out.print(!contentCheck.isIDValid(ID)? "   |\t\t\t\t\t\t\t\t    |\n   "
						+ "|        ERROR: Invalid ID!\t\t\t\t\t    |\n   "
						+ "|\t\t\t\t\t\t\t\t    |\n":"");break;
			case "customer":
				System.out.println(!contentCheck.isIDValid(ID)? "\n\tInvalid ID!\n":"");break;
			}			
		}
		return ID;
	}

	public static String name(String string) {
		Scanner input = new Scanner(System.in);
		String name="";
		//Asking for name with validation check 
		while(!contentCheck.isNameValid(name)){
			System.out.print(string);
			name = input.nextLine().trim().toLowerCase();
			System.out.println(!contentCheck.isNameValid(name)? "\n\tInvalid Name!\n":"");
		}
		return name;
	}

	public static String password(String string, String sectionType) {
		Scanner input = new Scanner(System.in);
		String password="";
		//Asking for Password with validation check 
		while(!contentCheck.isPasswordValid(password)){
			System.out.print(string);
			password = input.nextLine();

			switch (sectionType) {
			case "admin":
				System.out.print(!contentCheck.isPasswordValid(password)? "   |\t\t\t\t\t\t\t\t    |\n   |"
						+ "        ERROR: Invalid Password!\t\t\t\t    |\n   "
						+ "|\t\t\t\t\t\t\t\t    |\n":"");break;
			case "customer":
				System.out.println(!contentCheck.isPasswordValid(password)? "\n\tInvalid Password!\n":"");break;
			}
		}
		return password;
	}

	public static String email(String string, String sectionType) {
		Scanner input = new Scanner(System.in);
		String email="";
		//Asking for Email with validation check 
		while(!contentCheck.isEmailValid(email)){
			System.out.print(string);
			email = input.nextLine().toLowerCase();

			switch (sectionType) {
			case "admin":
				System.out.print(!contentCheck.isEmailValid(email)? "   |                                                                |\n"
						+ "   |        ERROR: Invalid Email!                                   |\n"
						+ "   |                                                                |\n":"");break;
			case "customer":
				System.out.println(!contentCheck.isEmailValid(email)? "\n\tInvalid Email!\n":"");break;
			}
		}
		return email;
	}

	public static String phoneNo(String string, String sectionType) {
		Scanner input = new Scanner(System.in);
		String phoneNo="";
		//Asking for Phone Number with validation check 
		while(!contentCheck.isPhoneValid(phoneNo)){
			System.out.print(string);
			phoneNo = input.nextLine().trim();

			switch (sectionType) {
			case "admin":
				System.out.print(!contentCheck.isPhoneValid(phoneNo)? "   |                                                                |\n"
						+ "   |        ERROR: Invalid Phone Number!                            |\n"
						+ "   |                                                                |\n":"");break;
			case "customer":
				System.out.println(!contentCheck.isPhoneValid(phoneNo)? "\n\tInvalid Phone Number!\n":"");break;
			}
		}
		return phoneNo;
	}

	public static String address(String string) {
		Scanner input = new Scanner(System.in);
		String address="";
		//Asking for address with validation check 
		while (true){
			System.out.print(string);
			address = input.nextLine().trim();
			System.out.println();
			if (address.length() > 10 && address.length() <= 60 ) {
				break;
			}
			else {
				System.out.println(address.equals("")? "     INVALID INPUT: Required Valid Input Which Is Not Null\n" :
						"     INVALID INPUT: Required Length is 10 to 60 characters!\n");
			}
		}
		return address;
	}
}
