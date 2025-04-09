package dio.board.ui;

import dio.board.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class BoardMenu {

    private final Scanner sc = new Scanner(System.in);

    private final BoardEntity entity;

    public void execute() {

        System.out.printf("Bem vindo ao board %s, selecione uma operação: ");
        var opcao = -1;
        while (opcao != 9){
            System.out.println("1 - Criar um card");
            System.out.println("2 - Mover um card");
            System.out.println("3 - Bloquear um card");
            System.out.println("4 - Desbloquear um card");
            System.out.println("5 - Cancelar um card");
            System.out.println("6 - Ver board");
            System.out.println("7 - Ver coluna com cards");
            System.out.println("8 - Ver card");
            System.out.println("9 - Voltar para o menu anterior um card");
            System.out.println("10 - Sair");
            opcao = sc.nextInt();
            switch (opcao){
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showBoard();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando para o menu anterior");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }






    }
}
