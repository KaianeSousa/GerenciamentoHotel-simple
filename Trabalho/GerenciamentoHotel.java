import java.util.ArrayList;
import java.util.Scanner;

// Classe principal que gerencia o sistema do hotel
class GerenciamentoHotel {
    // Listas para armazenar os hóspedes, quartos e reservas
    ArrayList<Hospede> hospedes = new ArrayList<Hospede>();
    ArrayList<Quarto> quartos = new ArrayList<Quarto>();
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    // Constante que define o limite máximo de quartos no hotel
    final int LIMITE_QUARTOS = 200;

    // Método para adicionar um hóspede à lista de hóspedes
    void adicionarHospede(Hospede hospede) {
        hospedes.add(hospede);
    }

    // Método para adicionar um quarto à lista de quartos
    void adicionarQuarto(Quarto quarto) {
        quartos.add(quarto);
    }

    // Método para reservar um quarto
    // Recebe o hóspede, número do quarto e número de dias como parâmetros
    String reservarQuarto(Hospede hospede, int numeroQuarto, int dias) {
        // Itera sobre a lista de quartos para encontrar o quarto desejado
        for (Quarto quarto : quartos) {
            // Verifica se o número do quarto coincide com o solicitado
            if (quarto.numero == numeroQuarto) {
                // Se o quarto não estiver ocupado, realiza a reserva
                if (!quarto.ocupado) {
                    quarto.ocupado = true; // Marca o quarto como ocupado
                    Reserva reserva = new Reserva(hospede, quarto, dias); // Cria uma nova reserva
                    reservas.add(reserva); // Adiciona a reserva à lista de reservas
                    return "Sim"; // Retorna "Sim" indicando que a reserva foi bem-sucedida
                } else {
                    // Se o quarto estiver ocupado, informa ao usuário
                    System.out.println("Quarto ocupado, tente outro.");
                    return "Não"; // Retorna "Não" indicando que a reserva falhou
                }
            }
        }
        // Se o quarto não for encontrado, informa ao usuário
        System.out.println("Quarto não encontrado.");
        return "Não"; // Retorna "Não" indicando que a reserva falhou
    }

    // Método para listar todas as reservas
    void listarReservas() {
        for (Reserva reserva : reservas) {
            // Imprime o número do quarto reservado
            System.out.println("Quarto: " + reserva.quarto.numero);
        }
    }

    // Método para listar todos os hóspedes e seus respectivos quartos reservados
    void listarHospedesEQuartos() {
        for (Reserva reserva : reservas) {
            // Imprime o nome do hóspede, número do quarto e o número de dias reservados
            System.out.println("Hospede: " + reserva.hospede.nome + ", Quarto: " + reserva.quarto.numero + ", Dias: " + reserva.dias);
        }
    }

    // Função para realizar uma reserva
    void fazerReserva() {
        Scanner scanner = new Scanner(System.in);

        // Captura os dados do hóspede
        System.out.print("\nDigite o nome do hospede: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        Hospede hospede = null;
        // Verifica se o hóspede já existe com base no CPF
        for (Hospede h : hospedes) {
            if (h.cpf.equals(cpf)) { // Compara as Strings
                hospede = h;
                break;
            }
        }
        // Se o hóspede não existir, cria um novo e o adiciona à lista
        if (hospede == null) {
            hospede = new Hospede(nome, cpf, telefone);
            adicionarHospede(hospede);  // Usa o método para adicionar o hóspede
        }

        // Inicializa a variável do quarto
        Quarto quarto = null;
        int numeroQuarto; // Variável para receber o número do quarto
        // Loop para verificar se o quarto escolhido está disponível
        while (true) {
            System.out.print("Digite o número do quarto (de 1 a " + LIMITE_QUARTOS + "): ");
            numeroQuarto = scanner.nextInt();
            scanner.nextLine();  // Consumir a nova linha

            // Verifica se o número do quarto está dentro dos limites permitidos
            if (numeroQuarto < 1 || numeroQuarto > LIMITE_QUARTOS) {
                System.out.println("Número do quarto inválido. Temos quartos de número 1 até " + LIMITE_QUARTOS + ".");
                continue; // Reinicia o loop para pedir um novo número de quarto
            }

            boolean quartoExistente = false; // Variável para verificar a existência do quarto
            for (Quarto q : quartos) {
                if (q.numero == numeroQuarto) {
                    quarto = q;
                    quartoExistente = true;
                    break;
                }
            }

            // Se o quarto não existir, cria um novo
            if (!quartoExistente) {
                if (quartos.size() < LIMITE_QUARTOS) {
                    quarto = new Quarto(numeroQuarto);
                    adicionarQuarto(quarto);  // Usa o método para adicionar o quarto
                    break; // Sai do loop
                } else {
                    System.out.println("Número máximo de quartos atingido.");
                    return;  // Sai do método se o limite for atingido
                }
            } else if (!quarto.ocupado) {
                break; // Sai do loop se o quarto existir e não estiver ocupado
            } else {
                System.out.println("Quarto já está ocupado. Tente outro número.");
            }
        }

        // Captura o número de dias que o hóspede deseja reservar o quarto
        int dias;
        System.out.print("Por quantos dias deseja reservar o quarto? ");
        dias = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha

        // Realiza a reserva do quarto e exibe o resultado
        String resultado = reservarQuarto(hospede, numeroQuarto, dias);
        System.out.println("Reserva realizada: " + resultado);
    }

    // Método principal que executa o programa
    public static void main(String[] args) {
        GerenciamentoHotel hotel = new GerenciamentoHotel(); // Cria uma instância da classe GerenciamentoHotel
        Scanner scanner = new Scanner(System.in); // Scanner para capturar entrada do usuário

        // Loop infinito para exibir o menu e receber as opções do usuário
        while (true) {
            // Exibe o menu de opções
            System.out.println("\nMenu:");
            System.out.println("1. Realizar Reserva");
            System.out.println("2. Listar Reservas");
            System.out.println("3. Listar Hospedes e Quartos");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao;
            opcao = scanner.nextInt(); // Recebe a escolha do usuário
            scanner.nextLine();  // Consumir a nova linha

            // Estrutura de controle de fluxo para cada opção escolhida
            switch (opcao) {
                // Opção para realizar uma reserva
                case 1:
                   hotel.fazerReserva();
                    break;

                // Opção para listar as reservas de quartos
                case 2:
                    System.out.println("\nLista de Reservas:");
                    hotel.listarReservas();
                    break;

                // Opção para listar hóspedes e seus respectivos quartos
                case 3:
                    System.out.println("\nLista de Hospedes e Quartos:");
                    hotel.listarHospedesEQuartos();
                    break;

                // Opção para sair do programa
                case 4:
                    System.out.println("Saindo...");
                    scanner.close(); // Fecha o scanner
                    return; // Sai do programa

                // Caso o usuário escolha uma opção inválida
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
