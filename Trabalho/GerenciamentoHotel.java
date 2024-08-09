import java.util.ArrayList;
import java.util.Scanner;

class GerenciamentoHotel {
    ArrayList<Hospede> hospedes = new ArrayList<Hospede>();
    ArrayList<Quarto> quartos = new ArrayList<Quarto>();
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    final int LIMITE_QUARTOS = 500;

    void adicionarHospede(Hospede hospede) {
        hospedes.add(hospede);
    }

    void adicionarQuarto(Quarto quarto) {
        quartos.add(quarto);
    }

    String reservarQuarto(Hospede hospede, int numeroQuarto, int dias) {
        for (Quarto quarto : quartos) {
            if (quarto.numero == numeroQuarto) {
                if (!quarto.ocupado) {
                    quarto.ocupado = true;
                    Reserva reserva = new Reserva(hospede, quarto, dias);
                    reservas.add(reserva);
                    return "Sim";
                } else {
                    System.out.println("Quarto ocupado, tente outro.");
                    return "Não";
                }
            }
        }
        System.out.println("Quarto não encontrado.");
        return "Não";
    }

    void listarReservas() {
        for (Reserva reserva : reservas) {
            System.out.println("Quarto: " + reserva.quarto.numero);
        }
    }

    void listarHospedesEQuartos() {
        for (Reserva reserva : reservas) {
            System.out.println("Hospede: " + reserva.hospede.nome + ", Quarto: " + reserva.quarto.numero + ", Dias: " + reserva.dias);
        }
    }

    void fazerReserva() {

    }


    public static void main(String[] args) {
        GerenciamentoHotel hotel = new GerenciamentoHotel();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Realizar Reserva");
            System.out.println("2. Listar Reservas");
            System.out.println("3. Listar Hospedes e Quartos");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao;
            opcao = scanner.nextInt(); // Variável para receber a escolha do usuário
            scanner.nextLine();  // Consumir a nova linha

            // Estrutura de controle de fluxo para cada opção escolhida
            switch (opcao){
                // Opção para adicionar hospede
                case 1:
                    // Adicionar Hospede
                    System.out.print("\nDigite o nome do hospede: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String email  = scanner.nextLine();

                    Hospede hospede = null;
//////reveja
                    for (Hospede h : hotel.hospedes) {
                        if (h.cpf.equals(cpf)) { // Compara as Strings
                            hospede = h;
                            break;
                        }
                    }
                    if (hospede == null) {
                        hospede = new Hospede(nome, cpf, telefone);
                        hotel.adicionarHospede(hospede);  // Usa o método para adicionar o hóspede
                    }

                    // Adicionar Quarto
                    Quarto quarto = null;

                    int numeroQuarto; // Variável para receber o número do quarto
                    // Verifica se o quarto escolhido está ocupado ou não
                    while (true) {
                        System.out.print("Digite o número do quarto (de 1 a " + hotel.LIMITE_QUARTOS + "): ");
                        numeroQuarto = scanner.nextInt();
                        scanner.nextLine();  // Consumir a nova linha
                        /* Caso o número do quarto seja menor que 1 ou maior que o limite de quartos ofertados, será impressa
                         uma mensagem de quarto inexistente e pede novamente por um número de quarto. */
                        if (numeroQuarto < 1 || numeroQuarto > hotel.LIMITE_QUARTOS) {
                            System.out.println("Número do quarto inválido. Temos quartos de número 1 até " + hotel.LIMITE_QUARTOS + ".");
                            continue;
                        }

                        boolean quartoExistente = false; // Variável para receber a validação da existência de um quarto
                        for (Quarto q : hotel.quartos) {
                            if (q.numero == numeroQuarto) {
                                quarto = q;
                                quartoExistente = true;
                                break;
                            }
                        }
                        if (!quartoExistente) {
                            if (hotel.quartos.size() < hotel.LIMITE_QUARTOS) {
                                quarto = new Quarto(numeroQuarto);
                                hotel.adicionarQuarto(quarto);  // Usa o método para adicionar o quarto
                                break;
                            } else {
                                System.out.println("Número máximo de quartos atingido.");
                                return;  // Sai da função se o limite for atingido
                            }
                        } else if (!quarto.ocupado) {
                            break;
                        } else {
                            System.out.println("Quarto já está ocupado. Tente outro número.");
                        }
                    }

                    // Questiona por quantos dias a pessoa deseja reservar o quarto
                    int dias;
                    System.out.print("Por quantos dias deseja reservar o quarto? ");
                    dias = scanner.nextInt();
                    scanner.nextLine();  // Consumir a nova linha
                    String resultado = hotel.reservarQuarto(hospede, numeroQuarto, dias);
                    System.out.println("Reserva realizada: " + resultado);
                    break;
                // Opção para listar as reservas de quarto feitos e por quantos dias
                case 2:
                    System.out.println("\nLista de Reservas:");
                    hotel.listarReservas();
                    break;
                // Opção para listar hospedes e seus respctivos quartos
                case 3:
                    System.out.println("\nLista de Hospedes e Quartos:");
                    hotel.listarHospedesEQuartos();
                    break;

                case 4:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
