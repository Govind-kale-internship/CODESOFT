package task4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverterAPI {
    
    private static final String API_KEY = "289e295e09a01fcb835d5cd9";  
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    
    private static final Map<String, String> CURRENCY_SYMBOLS = new HashMap<>();
    
    static {
        CURRENCY_SYMBOLS.put("USD", "$");
        CURRENCY_SYMBOLS.put("EUR", "€");
        CURRENCY_SYMBOLS.put("GBP", "£");
        CURRENCY_SYMBOLS.put("INR", "₹");
        CURRENCY_SYMBOLS.put("JPY", "¥");
        CURRENCY_SYMBOLS.put("CAD", "C$");
        CURRENCY_SYMBOLS.put("AUD", "A$");
        CURRENCY_SYMBOLS.put("CHF", "CHF");
        CURRENCY_SYMBOLS.put("CNY", "¥");
        CURRENCY_SYMBOLS.put("SGD", "S$");
    }
    
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        
        printHeader();
        
        while (true) 
        {
            try 
            {
                System.out.println("\nPopular currencies: USD, EUR, GBP, INR, JPY, CAD, AUD");
                System.out.print("Enter BASE currency (e.g., USD): ");
                String baseCurrency = sc.next().toUpperCase();
                
                System.out.print("Enter TARGET currency (e.g., INR): ");
                String targetCurrency = sc.next().toUpperCase();
                
                if (baseCurrency.equals(targetCurrency))
                {
                    System.out.println("Base and Target must be different!");
                    continue;
                }
                
                System.out.print("Enter amount to convert: ");
                double amount = getValidAmount(sc);
                
                System.out.print("Fetching exchange rate... ");
                double rate = fetchExchangeRate(baseCurrency, targetCurrency);
                System.out.println("DONE");
                
                double convertedAmount = amount * rate;
                displayResult(baseCurrency, targetCurrency, amount, convertedAmount, rate);
                
                if (!askToContinue(sc)) 
                {
                    break;
                }
                
            } 
            catch (Exception e) 
            {
                System.out.println("\n❌ Error: " + e.getMessage());
                System.out.println("Please check:");
                System.out.println("  - Your API key is correct");
                System.out.println("  - Internet connection");
                System.out.println("  - Currency codes (USD, EUR, etc.)");
            }
        }
        
        printFooter();
        sc.close();
    }
    
    private static double fetchExchangeRate(String base, String target) throws Exception 
    {
        String urlStr = API_URL + base;
        @SuppressWarnings("deprecation")
		URL url = new URL(urlStr);
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = in.readLine()) != null) 
            {
                response.append(line);
            }
            
            in.close();
            
             
            String jsonStr = response.toString();
            
            
            String ratesSection = findRatesSection(jsonStr);
            
             
            double rate = extractRate(ratesSection, target);
            
            return rate;
            
        } 
        else 
        {
            throw new Exception("HTTP error code: " + responseCode);
        }
    }
    
    private static String findRatesSection(String json) 
    {
         
        int startIndex = json.indexOf("\"conversion_rates\":{") + 20;
        int endIndex = findMatchingBrace(json, startIndex);
        return json.substring(startIndex, endIndex);
    }
    
    private static int findMatchingBrace(String json, int start) 
    {
        int braceCount = 1;
        
        for (int i = start; i < json.length(); i++) 
        {
            if (json.charAt(i) == '{') braceCount++;
            if (json.charAt(i) == '}') braceCount--;
            if (braceCount == 0) return i;
        }
        
        return json.length();
    }
    
    private static double extractRate(String ratesSection, String target) 
    {
        String searchKey = "\"" + target + "\":";
        int keyIndex = ratesSection.indexOf(searchKey);
        
        if (keyIndex == -1) 
        {
            throw new RuntimeException("Currency " + target + " not found");
        }
        
        int startIndex = keyIndex + searchKey.length();
        int endIndex = ratesSection.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = ratesSection.indexOf("}", startIndex);
        }
        
        String rateStr = ratesSection.substring(startIndex, endIndex).trim();
        return Double.parseDouble(rateStr);
    }
    
    private static double getValidAmount(Scanner sc) 
    {
        while (true) 
        {
            try 
            {
                double amount = sc.nextDouble();
                
                if (amount > 0) 
                {
                    return amount;
                }
                System.out.print("❌ Amount must be positive! Try again: ");
                
            } 
            catch (Exception e) 
            {
                System.out.print("❌ Invalid number! Try again: ");
                sc.next();
            }
        }
    }
    
    private static void displayResult(String base, String target, double amount, 
                                     double converted, double rate) 
    {
        String baseSymbol = CURRENCY_SYMBOLS.getOrDefault(base, "");
        String targetSymbol = CURRENCY_SYMBOLS.getOrDefault(target, "");
        
        
        System.out.println("**********************************************************");
        System.out.println("         CONVERSION RESULT         ");
        System.out.println("__________________________________________________________");
        
        System.out.printf("  %s%.2f %s = %s%.2f %s\n", 
                baseSymbol, amount, base, 
                targetSymbol, converted, target);
            
        System.out.printf("  1 %s = %.4f %s\n", base, rate, target);
        System.out.printf("  1 %s = %.4f %s\n", target, 1/rate, base);
        
        System.out.println("__________________________________________________________");
    }
    
    private static boolean askToContinue(Scanner sc) {
        System.out.print("\nConvert another? (y/n): ");
        String choice = sc.next().toLowerCase();
        return choice.equals("y") || choice.equals("yes");
    }
    
    private static void printHeader() {
        System.out.println("__________________________________________________________");
        System.out.println("               **** CURRENCY CONVERTER ****           ");
        System.out.println("                (Real-time Exchange Rates)           ");
        System.out.println("__________________________________________________________");
         
    }
    
    private static void printFooter() 
    {
        System.out.println("\nThank you for using Currency Converter!");
    }
}