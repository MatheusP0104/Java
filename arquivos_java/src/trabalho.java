import java.util.Scanner;


public class trabalho {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a quantidade de números a serem inseridos no vetor:");
        int quantidade = scanner.nextInt();
        int[] numeros = new int[quantidade];

        for(int i = 0; i < quantidade; i++){
            System.out.println("Digite o " + (i + 1) + "° número");
            numeros[i] = scanner.nextInt();
        }

        
        int escolha = -1;
        do{
            System.out.print("Escolha uma das opções abaixo:\n1 - Exibir todos os números:\n2 - Calcular a soma dos números:\n" + //
                "3 - Contar quantos números são pares:\n4 - Exibir os números na ordem inversa:\n5 - Buscar um número:\n0 - Sair\nSua escolha:");
            escolha = scanner.nextInt();

        switch (escolha) {
            case 1:
                limparTela();
                MostrarVetor(numeros);
                break;
            case 2:
                limparTela();
                int soma = calcularSoma(numeros, 0);
                System.out.println("A soma dos números é: " + soma);
                break;
            case 3:
                limparTela();
                int par = numerosPares(numeros, 0);
                System.out.println("Existem " + par + " números pares no vetor.");
                break;
            case 4:
                limparTela();
                System.out.println("Os números na ordem inversa são:");
                exibirInverso(numeros, numeros.length - 1);
                System.out.println();
                break;
            case 5:
                limparTela();
                System.out.print("Digite o número a ser buscado: ");
                int alvo = scanner.nextInt();
                boolean encontrado = buscarNumero(numeros, 0, alvo);
                if (encontrado) {
                    System.out.println("O número " + alvo + " foi encontrado no vetor.");
                } else {
                    System.out.println("O número " + alvo + " não foi encontrado no vetor.");
                }
                break;
            case 0:
                limparTela();
                 System.out.println("Encerrando o programa...");
                 break;

            default:
            limparTela();
            System.out.println("Opção inválida");
            break;
    }

        }while(escolha != 0);

        scanner.close();
    }

    public static void MostrarVetor(int num[]) {
        System.out.println("Os números inseridos são:");
                for(int i = 0; i < num.length; i++){
                     System.out.print(num[i] + " ");
                    }
                    System.out.println("\n");
    }

    public static int calcularSoma(int num[], int indice){
        if(indice == num.length){
            return 0;
        } else {
            return num[indice] + calcularSoma(num, indice + 1);
        }
        
    }

    public static int numerosPares(int num[], int indice) {
        if (indice == num.length) {
            return 0;
                
        } else {
            int contador = (num[indice] % 2 == 0) ? 1 : 0;

            return contador + numerosPares(num, indice + 1);
        }    
    }

    public static void exibirInverso(int[] num, int indice) {
        if (indice < 0) {
            return;
        }
        System.out.print(num[indice] + " ");
        exibirInverso(num, indice - 1);
    }

    public static boolean buscarNumero(int[] num, int indice, int alvo) {
        if (indice == num.length) {
            return false;
        }
        if (num[indice] == alvo) {
            return true;
        }
        return buscarNumero(num, indice + 1, alvo);
    }

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
}

    
}
