import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidadorCEP {

    // Método para validar o formato do CEP
    public static boolean validarFormatoCEP(String cep) {
        // Regex para validar: 5 dígitos + hífen opcional + 3 dígitos
        Pattern pattern = Pattern.compile("^\\d{5}-?\\d{3}$");
        return pattern.matcher(cep).matches();
    }

    // Método para verificar se o CEP é válido
    public static boolean validarCEP(String cep) {
        // Verifica o formato
        if (!validarFormatoCEP(cep)) {
            return false;
        }
        
        // Remove o hífen se existir
        cep = cep.replace("-", "");
        
        // Verifica se não é um CEP com todos dígitos iguais (ex: 00000000)
        if (cep.matches("(\\d)\\1{7}")) {
            return false;
        }
        
        return true; // CEP válido
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("🔍 Validador de CEP Brasileiro");
        System.out.println("Digite um CEP (formato: 00000-000 ou 00000000):");
        
        String cep = scanner.nextLine();
        
        if (validarCEP(cep)) {
            System.out.println("✅ CEP válido!");
        } else {
            System.out.println("❌ CEP inválido!");
        }
        
        scanner.close();
    }
}