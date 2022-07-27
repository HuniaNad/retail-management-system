package project;

public class contentCheck {

//ID
	//method for checking ID validity
	public static boolean isIDValid(String ID) {
		String regex = "^[A-Za-z]{3}-[0-9]{1,3}$";
		return ID.matches(regex);
	}

//PASSWORD	
	//method for checking password validity
	public static boolean isPasswordValid(String password) {
		int countDigit=0, countLetter=0;
		boolean contains = false;
		//characters are checked one by one using Character class
		for(int i=0; i<password.length(); i++) {
			char passChar = password.charAt(i);
			//password is combination of letters and digits. length should be greater than 7
			if ((Character.isDigit(passChar) || Character.isAlphabetic(passChar)) && password.length()>7) {
				contains = true;
				//counting digits and letters
				countDigit = (Character.isDigit(passChar))? countDigit+1:countDigit;
				countLetter = (Character.isAlphabetic(passChar))? countLetter+1:countLetter;
			}
			else {
				//if any other character is detected, false is returned
				return false;
			}	
		}
		//true is returned if there is atleast 1 digit and letter, and no other entity is present in password
		return countDigit>0 && countLetter>0 && contains;
	}
	
//EMAIL
	//method for checking email validity
	public static boolean isEmailValid(String email) {
		//[A-Za-z0-9_.-]+ -> combination of "A-Z a-z 0-9 _.-" last + means any number of combinations
		//@ -> literal @ must be present
		//[A-Za-z]+ -> combination of "A-Z a-z", last + means any number of combinations
		// \\.{1} -> . appears only 1 time after [A-Za-z]+, \\ ensures that "." is considered literally otherwise it will have some other meaning
		//^ -> start, $ -> end
		String regex = "^[A-Za-z0-9_.-]+@[A-Za-z]+\\.{1}[A-Za-z]+$";
		boolean emailExists=false;
		String[] emails = {"gmail.com", "yahoo.com","ieee.org","outlook.com","hotmail.com"};
		for (int i=0; i<emails.length; i++) {
			if (email.substring(email.indexOf("@")+1).equals(emails[i])) {
				emailExists = true;
				break;
			}
		}
		return email.matches(regex) && emailExists;
	}

//PHONE NUMBER
	//method for checking phone no. validity
	public static boolean isPhoneValid(String phone) {
		//any combination of 0-9 but of length 11
		String regex = "^[0-9]{11}$";
		return phone.matches(regex);
	}

//CREDIT CARD INFORMATION
//NAME
	//method for checking name validity
	public static boolean isNameValid(String name) {
		//any combination of letters and spaces
		String regex = "^[A-Za-z ]+$";
		return name.matches(regex) && name.length()>=3;
	}
	
//EXPIRY DATE
	//method for checking expiry validity
	public static boolean isDateValid(String date) {
		if (date.length()==5) {
			int month = Integer.parseInt(date.substring(0, 1));
			int year = Integer.parseInt(date.substring(3, 5));
			if ((month>=1 && month<=12) && (year>=0 && year<=99) && date.charAt(2)=='/')
				return true;
		}
		return false;
		
	}
//CARD NUMBER
	//checking card's validity through its length, prefix and,
	//if sum of sumOfDoubleEvenPlace & sumOfOddPlace is divisible by 10 
	public static boolean isCreditCardValid(long number) {
		int totalSumOfEvenOddPlaces = sumOfDoubleEvenPlace(number) +  sumOfOddPlace(number);
		//number size must range between 13 and 16 inclusively, totalSumOfEvenOddPlaces must be divisible by 10,
		//and, prefix must be 4,5,6, or 37
		if (getSize(number) >=13 && getSize(number) <=16 && (totalSumOfEvenOddPlaces%10==0)
				&& (prefixMatched(number, 4)||prefixMatched(number, 5)||prefixMatched(number, 37)
						||prefixMatched(number, 6)))
			return true;
		else
			return false;
	}

	//calculating size of number passed
	public static int getSize(long number) {
		int count=0;
		while(number>0) {
			//counting digits
			count++;
			//reducing number length by 1 character
			number/=10;
		}
		return count;
	}

	//returning sum of double even places from right to left
	public static int sumOfDoubleEvenPlace(long number) {
		int sum=0;
		while(number>0) {
			//dropping last digit and then using last digit of new number as evenPlace digit  
			int evenPlace=(int)((number/10)%10);
			//doubling digits at even place
			int doubleOfEvenPlace=(evenPlace*2);
			//summing the digits 
			sum += getDigit(doubleOfEvenPlace);
			//reducing number by 2 digits
			number/=100;
		}
		return sum;
	}

	//getting sum of digits or the digit for sumOfEvenNumbers()
	public static int getDigit(int doubleOfEvenPlace) {
		//returning the doubleOfEvenPlace if it is 1 digit...
		if (doubleOfEvenPlace % 100 == 0)
			return doubleOfEvenPlace;
		//...else returning sum of digits of doubleOfEvenPlace
		else
			return (doubleOfEvenPlace / 10) + (doubleOfEvenPlace % 10);
	}

	//returning sum odd places from right to left
	public static int sumOfOddPlace(long number) {
		int sum=0;
		while(number>0) {
			//taking mod of number by 10 to use as oddPlace digit
			int oddPlace=(int)(number%10);
			//summing up oddPlace digits
			sum += oddPlace;
			//reducing number by 2 digits
			number/=100;
		}
		return sum;
	}

	//prefix is matched to determine type of card
	public static boolean prefixMatched(long number, int prefix) {
		//passed prefix length is determined
		int prefixSize= getSize(prefix);
		//prefix matched if prefix of passed number is equal to passed prefix... 
		if(getPrefix(number,prefixSize) == prefix)
			return true;
		//...otherwise prefix not matched
		else
			return false;		
	}

	//for checking correct type of credit card, prefix of credit card number is returned
	public static long getPrefix(long number, int prefixLength) {
		//powerOf10 depends on the length of prefix sent, which is used later for checking desired length of prefix
		int powerOf10= (int)Math.pow(10, prefixLength);
		//if length of number sent is equal to the number itself, method returns the number as prefix
		long prefix=number;
		//finding the prefix whose length will depend on prefixLength passed
		while(number / powerOf10 != 0){
			prefix = number/10;
			number/=10;
		}
		return prefix;
	}

//PAYEMENT
	//validation for checking entered payment
	public static boolean numberValidation(String amount) {
		//loop to iterate through each index of string passed
		for (int i=0; i<amount.length(); i++) {
			//checks if any character is not digit, then returns false
			if (!Character.isDigit(amount.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	
}
