package emerald_tid_sid_converter;
import java.util.Scanner;
/**
 * @author Jake Grubbs
 *
 * based on Sleipnir's code, found at https://pastebin.com/CcXJcNhB
 */
public class emerald_tid_sid_converter 
{
	//This function forces a valid number input
	public static int numberValidator(Scanner input)
	{
        while (!input.hasNextInt())
        {
            System.out.println("Enter a valid number");
            input.next();
        }
        return input.nextInt();
	}
	
	//helper function for finding the correct character
	public static int indexConverter(int i)
	{
		if (i < 0xAF) 
		{
			i -= 168;
		}
		else if (i < 0xB7)
		{
			i -= 167;
		}
		else if (i < 0xB9)
		{
			i -= 166;
		}
		else
		{
			i -= 165;
		}
		return i;
	}
	
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		
		int languageCheck, deviceCheck, idCheck, id;
		boolean isEmulator = true, isTID = true;
		String language = "", device = "", idType = "", idHex1 = "", idHex2 = "", idHexFull = "", idBox2 = "";

		//asking for language
		System.out.println("What language is your game? \n"); //todo add Japanese later if someone writes the code
		
		System.out.println("1. English");
//        System.out.println("2. Français");
//        System.out.println("3. Deutsch");
//        System.out.println("4. Italiano");
//        System.out.println("5. Español");
        //System.out.println("6. Japanese");
		//todo add other languages without hardcoding because lmao this takes forever
		
        languageCheck = numberValidator(input);
		switch (languageCheck) 
		{
		case 1:
			language = "English";
			break;
//		case 2:
//			language = "French";
//			break;
//		case 3:
//			language = "German";
//			break;
//		case 4:
//			language = "Italian";
//			break;
//		case 5:
//			language = "Spanish";
//			break;
//		case 6:
//			//language = "Japanese";
//			break;
		default:
			System.exit(0);
			break;
		}
		
		//asking for device
		System.out.println("What device are you playing on? \n");
		System.out.println("1. Emulator");
        System.out.println("2. Console");
        
        deviceCheck = numberValidator(input);
        switch (deviceCheck)
        {
        case 1:
        	isEmulator = true;
        	device = "emulator";
        	break;
        case 2:
        	isEmulator = false;
        	device = "console";
        	break;
    	default:
    		break;
        }
        
        //asking for id type
        System.out.println("Modify TID or SID? \n");
		System.out.println("1. TID");
        System.out.println("2. SID");
        
        idCheck = numberValidator(input);
        switch (idCheck)
        {
        case 1:
        	isTID = true;
        	idType = "TID";
        	break;
        case 2:
        	isTID = false;
        	idType = "SID";
        	break;
    	default:
    		break;
        }
        
        //assigning idBox2
        if (isEmulator && !isTID)
        {
        	idBox2 = "p";
        }
        else if (!isEmulator && isTID)
        {
        	idBox2 = "t";
        }
        else
        {
        	idBox2 = "r";
        }
        
        //asking for respective ID
        System.out.println("Enter the value you want for your " + idType);
        id = numberValidator(input);
        
        //no more input needed, closing scanner
        input.close();
        
        //determining index values based on user input
        idHexFull = String.format("%04X", id);
        idHex1 = idHexFull.substring(0, 2);
        idHex2 = idHexFull.substring(2, 4);

        //debug stuff
		System.out.println("Language: " + language);
		System.out.println("Device: " + device);
		System.out.println(idType + ": " + String.format("%05d", id));
		System.out.println("Hex: " + idHexFull + "\n");
		
		//final output of code
		System.out.println("The following code will change the " + idType + " to " + String.format("%05d", id) + " (0x" + idHexFull + ") for " + language + " copies on " + device + ": \n");
		System.out.println(" Box 1:  (VRUnFHRn)\r\n" + " Box 2:  ( ?\"" + idBox2 + "FRn ) [starts with space; righty \"; ends with space]");
		
		//here's where it gets messy. I apologize, programming gods. Have mercy
		//currently ENG only.
		int xx = Integer.parseInt(idHex1, 16);
		int XX = Integer.parseInt(idHex2, 16);
		
		//array with all available characters in non-Japanese Emerald. Except for "/" because Sleipnir forgot lol
		//breaks in the patterns below are at 0xAF (175), 0xB7 (183), 0xB9 (185), and 0xBA (186)
		String[] arr = {"0","1","2","3","4","5","6","7","8","9","!","?",".","-","...","(lefty \")","(righty \"","(lefty ')","(righty ')","♂","♀",",","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		
		//these cases broke a few patterns so they now go first so they don't break the logic
		if (0x01 <= xx && xx <= 0x2F && 0x5C <= XX && XX <= 0x8F)
		{
			System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
					" Box 4:  (E" + arr[indexConverter(0x5F+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
					" Box 5:  (VA!n" + arr[indexConverter(0xBF+xx)] + "B!n)\r\n" + 
					" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
					" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
					" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
					" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
					" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
					" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
					" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
					" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
					" Box 14: (BOX 14) [leave the rest empty]");
		}
		else if (0x2F <= xx && xx <= 0x5F && 0xBB <= XX && XX <= 0xEE)
		{
		
			System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
					" Box 4:  (EiA!n   ) [ends with three spaces]\r\n" + 
					" Box 5:  (" + arr[indexConverter(0x8C+xx)] + "B!n......Rm) [two ...]\r\n" + 
					" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
					" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
					" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
					" Box 9:  (hT-nYN?n)\r\n" + 
					" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
					" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
					" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
					" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
					" Box 14: (BOX 14) [leave the rest empty]");
		}
		else if (0x2F <= xx && xx <= 0x5F && 0xEF <= XX && XX <= 0xFF)
		{
			System.out.println(" Box 3:  (?\"F\"...o  ) [righty \"; lefty \"; one ...; ends with two spaces]\r\n" + 
					" Box 4:  (E*" + arr[indexConverter(XX-0x30)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
					"   -Index of * = XX-30\r\n" + 
					" Box 5:  (iA!n" + arr[indexConverter(0x8C+xx)] + "B!n)\r\n" + 
					" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
					" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
					" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
					" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
					" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
					" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
					" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
					" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
					" Box 14: (BOX 14) [leave the rest empty]");
		}
		else if (0x30 <= xx && xx <= 0x62 && 0x63 <= XX && XX <= 0x8F)
		{
			System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ... ; ends with two spaces]\r\n" + 
					" Box 4:  (E" + arr[indexConverter(0x5F+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
					" Box 5:  (iA!n" + arr[indexConverter(0x8B+xx)] + "B!n)\r\n" + 
					" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
					" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
					" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
					" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
					" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
					" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
					" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
					" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
					" Box 14: (BOX 14) [leave the rest empty]");
		}
		//end special cases
		else if (xx == 0)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\" ......o  ) [righty \"; space between \" and ...; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E......Rm   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (mFloyLRo) [lower case L]\r\n" + 
						" Box 6:  ( ?\"m\"Ro ) [starts with space; righty \"; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"hT-n  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EYN?n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (FNRob ?n) [space between b and ?]\r\n" + 
						" Box 10: ( ?\"lGEn ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 11: (?\" ...?q  ) [righty \"; space between \" and ...; one ...; ends with two spaces]\r\n" + 
						" Box 12: (E) [leave the rest empty]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x32)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ...; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBC+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x3F)
			{
				System.out.println(" Box 3:  (?\"-...lo  ) [righty \"; one ...; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xAF+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x4F)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(0x80+XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E......Fo   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x5B)
			{
				System.out.println(" Box 3:  (?\".......o  ) [righty \"; one . followed by two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x53+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (...DQo......Rm) [one ...; two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x8F)
			{
				System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x5F+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (...DQo......Rm) [one ...; two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xBA)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x34+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xEE)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E......Rm   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (mFloyLRo) [lower case L]\r\n" + 
						" Box 6:  ( ?\"m\"Ro ) [starts with space; righty \"; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"hT-n  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EYN?n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (FNRob ?n) [space between b and ?]\r\n" + 
						" Box 10: ( ?\"lGEn ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 11: (?\" ...?q  ) [righty \"; space between \" and ...; one ...; ends with two spaces]\r\n" + 
						" Box 12: (E) [leave the rest empty]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xFF)
			{
				System.out.println(" Box 3:  (?\"F\"...o  ) [righty \"; lefty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(XX-0x30)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else 
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}

		else if (xx <= 0x2E)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\"VA...o  ) [righty \";  one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xC0+xx)] + "B!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x32)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ...; lower case L;ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBC+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (VA!n" + arr[indexConverter(0xC0+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x3F)
			{
				System.out.println(" Box 3:  (?\"0...lo  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xA2+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (VA!n" + arr[indexConverter(0xC0+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x4F)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(0x80+XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E......Qo   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (VA!n" + arr[indexConverter(0xC0+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x5B)
			{
				System.out.println(" Box 3:  (?\".......o  ) [righty \"; one . followed by two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x53+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (VA!n" + arr[indexConverter(0xBF+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xBA)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; lower case L ;ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x34+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (VA!n" + arr[indexConverter(0xC0+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xEE)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" +  
						" Box 4:  (EVA!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(0xC0+xx)] + "B!n......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xFF)
			{
				System.out.println(" Box 3:  (?\"F\"...o  ) [righty \"; lefty \"; one ... ;ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(XX-0x30)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (VA!n" + arr[indexConverter(0xC0+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}
		else if (xx <= 0x62)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\"iA...o  ) [righty \";  one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x8C+xx)] + "B!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x32)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ... ; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBC+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (iA!n" + arr[indexConverter(0x8C+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x62)
			{
				System.out.println(" Box 3:  (?\"iE...o  ) [righty \"; one ... ; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x8C+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (hA!n" + arr[indexConverter(0x8C+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xBA)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x34+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (iA!n" + arr[indexConverter(0x8C+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}
		else if (xx <= 0x96)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\"7B...o  ) [righty \";  one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x58+xx)] + "B!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x32)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ...; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBC+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (7B!n" + arr[indexConverter(0x58+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x62)
			{
				System.out.println(" Box 3:  (?\"iE...o  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x8C+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (3B!n" + arr[indexConverter(0x58+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x8F)
			{
				System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x5F+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (6B!n" + arr[indexConverter(0x58+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xBA)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x34+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (7B!n" + arr[indexConverter(0x58+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xEE)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E7B!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(0x58+xx)] + "B!n......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xFF)
			{
				System.out.println(" Box 3:  (?\"F\"...o  ) [righty \"; lefty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(XX-0x30)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (7B!n" + arr[indexConverter(0x58+xx)] + "B!n)\r\n" +  
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}
		else if (xx <= 0xBA)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\"hB...o  ) [righty \";  one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x24+xx)] + "B!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x32)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ...; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBC+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (hB!n" + arr[indexConverter(0x24+xx)] + "B!n)\r\n" +  
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x62)
			{
				System.out.println(" Box 3:  (?\"iE...o  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x8C+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (dB!n" + arr[indexConverter(0x24+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x8F)
			{
				System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x5F+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (gB!n" + arr[indexConverter(0x24+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xBA)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x34+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (hB!n" + arr[indexConverter(0x24+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xEE)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (EhB!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(0x24+xx)] + "B!n......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xFF)
			{
				System.out.println(" Box 3:  (?\"F\"lo  ) [righty \"; lefty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(XX-0x30)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (hB!n" + arr[indexConverter(0x24+xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}
		else if (xx <= 0xEE)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "B...o  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E......Rm   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (mFloyLRo) [lower case L]\r\n" + 
						" Box 6:  ( ?\"m\"Ro ) [starts with space; righty \"; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"hT-n  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EYN?n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (FNRob ?n) [space between b and ?]\r\n" + 
						" Box 10: ( ?\"lGEn ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 11: (?\" ...?q  ) [righty \"; space between \" and ...; one ...; ends with two spaces]\r\n" + 
						" Box 12: (E) [leave the rest empty]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x32)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ...; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBC+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(xx)] + "B!n......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x62)
			{
				System.out.println(" Box 3:  (?\"iE...o  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x8C+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (FDQo" + arr[indexConverter(xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]\r\n" + 
						"");
			}
			else if (XX <= 0x8F)
			{
				System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x5F+XX)] + "...!n   ) [two ...; ends with three spaces]\r\n" + 
						" Box 5:  (VDQo" + arr[indexConverter(xx)] + "B!n)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xBA)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x34+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(xx)] + "B!n......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xEE)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(XX)] + "......o  ) [righty \";  two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(xx)] + "B!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (......RmmFlo) [two ...; lower case L]\r\n" + 
						" Box 6:  ( ?\"yLRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 7:  (?\"m\"Ro  ) [righty \"; righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (EhT-n   ) [ends with three spaces]\r\n" + 
						" Box 9:  (YN?nFNRo)\r\n" + 
						" Box 10: ( ?\"b ?n ) [starts with space; righty \"; space between b and ?; ends with space]\r\n" + 
						" Box 11: (?\"lGEn  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 12: (E ...?q   ) [space between E and ...; ends with three spaces]\r\n" + 
						" Box 13: (E) [leave the rest empty]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]\r\n" + 
						"");
			}
			else if (XX <= 0xFF)
			{
				System.out.println(" Box 3:  (?\"F\"...o  ) [righty \"; lefty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(XX-0x30)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(xx)] + "B!n......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}
		else if (xx <= 0xFF)
		{
			if (XX == 0)
			{
				System.out.println(" Box 3:  (?\"FB...o  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (EF'!n   ) [lefty '; ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(0x1C0-xx)] + "BQnF'Qo) [lefty ']\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x31)
			{
				System.out.println(" Box 3:  (?\"A...lo  ) [righty \"; one ...; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0xBD+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (FB!n" + arr[indexConverter(0x1C0-xx)] + "BQn)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]\r\n" + 
						"");
			}
			else if (XX <= 0x61)
			{
				System.out.println(" Box 3:  (?\"iE...o  ) [righty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x8D+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (BB!n" + arr[indexConverter(0x1C0-xx)] + "BQn)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0x8E)
			{
				System.out.println(" Box 3:  (?\"0......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x60+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (EB!n" + arr[indexConverter(0x1C0-xx)] + "BQn)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xB9)
			{
				System.out.println(" Box 3:  (?\"R\"lo  ) [righty \"; lefty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(0x35+XX)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (FB!n" + arr[indexConverter(0x1C0-xx)] + "BQn)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else if (XX <= 0xED)
			{
				System.out.println(" Box 3:  (?\"" + arr[indexConverter(0x01+XX)] + "......o  ) [righty \"; two ...; ends with two spaces]\r\n" + 
						" Box 4:  (EFB!n   ) [ends with three spaces]\r\n" + 
						" Box 5:  (" + arr[indexConverter(xx)] + "BQn......Rm) [two ...]\r\n" + 
						" Box 6:  ( ?\"mFlo ) [starts with space; righty \"; lower case L; ends with space]\r\n" + 
						" Box 7:  (?\"yLRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 8:  (Em\"Ro   ) [righty \"; ends with three spaces]\r\n" + 
						" Box 9:  (hT-nYN?n)\r\n" + 
						" Box 10: ( ?\"FNRo ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"b ?n  ) [righty \"; space between b and ?; ends with two spaces]\r\n" + 
						" Box 12: (ElGEn   ) [lower case L; ends with three spaces]\r\n" + 
						" Box 13: ( ...?q    ) [starts with space; one ...; ends with four spaces]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]\r\n" + 
						"");
			}
			else if (XX <= 0xFF)
			{
				System.out.println(" Box 3:  (?\"J\"...o  ) [righty \"; lefty \"; one ...; ends with two spaces]\r\n" + 
						" Box 4:  (E" + arr[indexConverter(XX-0x30)] + "...!n   ) [one ...; ends with three spaces]\r\n" + 
						" Box 5:  (FB!n" + arr[indexConverter(0x1C0-xx)] + "BQn)\r\n" + 
						" Box 6:  ( ?\"......Rm ) [starts with space; righty \"; two ...; ends with space]\r\n" + 
						" Box 7:  (?\"mFlo  ) [righty \"; lower case L; ends with two spaces]\r\n" + 
						" Box 8:  (EyLRo   ) [ends with three spaces]\r\n" + 
						" Box 9:  (m\"RohT-n) [righty \"]\r\n" + 
						" Box 10: ( ?\"YN?n ) [starts with space; righty \"; ends with space]\r\n" + 
						" Box 11: (?\"FNRo  ) [righty \"; ends with two spaces]\r\n" + 
						" Box 12: (Eb ?n   ) [space between b and ?; ends with three spaces]\r\n" + 
						" Box 13: (lGEn ...?q) [lower case L; space between n and ...; one ...]\r\n" + 
						" Box 14: (BOX 14) [leave the rest empty]");
			}
			else
			{
				System.out.println("looks like there's a bug here. oops!");
			}
		}
		else
		{
			System.out.println("looks like there's a bug here. oops!");
		}
	}
}
