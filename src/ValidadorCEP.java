import java.util.Scanner;
import java.util.regex.Pattern;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidadorCEP {

    public static boolean validarFormatoCEP(String cep) {
        Pattern pattern = Pattern.compile("^\\d{5}-?\\d{3}$");
        return pattern.matcher(cep).matches();
    }

    public static boolean validarCEP(String cep) {
        if (!validarFormatoCEP(cep)) {
            return false;
        }
        
        cep = cep.replace("-", "");
        
        if (cep.matches("(\\d)\\1{7}")) {
            return false;
        }
        
        // Consulta a API ViaCEP para verificar se o CEP existe
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            HttpURLConnection conexao = (HttpURLConnection) new URL(url).openConnection();
            conexao.setRequestMethod("GET");
            
            int resposta = conexao.getResponseCode();
            if (resposta == 200) { // CEP encontrado
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar API: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        System.out.println("🔍 Validador de CEP Brasileiro");
        
        while (continuar) {
            System.out.println("\nDigite um CEP (formato: 00000-000 ou 00000000) ou 'sair' para encerrar:");
            String entrada = scanner.nextLine().trim();
            
            if (entrada.equalsIgnoreCase("sair")) {
                continuar = false;
                continue;
            }
            
            if (validarCEP(entrada)) {
                System.out.println("✅ CEP válido!");
            } else {
                System.out.println("❌ CEP inválido!");
            }
            
            // Pergunta se deseja continuar (exceto se já escolheu sair)
            if (continuar) {
                System.out.print("\nDeseja verificar outro CEP? (S/N): ");
                String resposta = scanner.nextLine().trim();
                
                if (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("SIM")) {
                    continuar = false;
                }
            }
        }
        
        System.out.println("\nObrigado por usar o Validador de CEP!");
        scanner.close();
    }
}
