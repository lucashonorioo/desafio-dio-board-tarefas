package dio.board.ui;

import dio.board.dto.BoardColumnInfoDTO;
import dio.board.persistence.config.ConnectionConfig;
import dio.board.persistence.entity.BoardColumnEntity;
import dio.board.persistence.entity.BoardEntity;
import dio.board.persistence.entity.CardEntity;
import dio.board.service.BoardColumnQueryService;
import dio.board.service.BoardQueryService;
import dio.board.service.CardQueryService;
import dio.board.service.CardService;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Scanner;

@AllArgsConstructor
public class BoardMenu {

    private final Scanner sc = new Scanner(System.in).useDelimiter("\n");

    private BoardEntity entity;

    public void execute() {
        try {
            System.out.printf("Bem vindo ao board %s, selecione uma operação: \n");
            var opcao = -1;
            while (opcao != 9) {
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
                switch (opcao) {
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
        catch (SQLException e){
            e.printStackTrace();
            System.exit(0);
        }

    }




    private void createCard() throws SQLException{
        var card = new CardEntity();
        System.out.println("Informe o título do card");
        card.setTitulo(sc.next());
        System.out.println("Informe a descrição do card");
        card.setDescricao(sc.next());
        card.setBoardColumn(entity.getInitialColumn());
        try(var connection = ConnectionConfig.getConnection()){
            new CardService(connection).create(card);
        }
    }

    private void moveCardToNextColumn() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a próxima coluna");
        var cardId = sc.nextLong();
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrdem(), bc.getTipo()))
                .toList();
        try(var connection = ConnectionConfig.getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }


    private void cancelCard() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a coluna de cancelamento");
        var cardId = sc.nextLong();
        var cancelColumn = entity.getCancelColumn();
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrdem(), bc.getTipo()))
                .toList();
        try(var connection = ConnectionConfig.getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }


    private void showBoard() throws SQLException{
        try(var connection = ConnectionConfig.getConnection()){
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board [%s,%s]\n", b.id(), b.nome());
                b.columns().forEach(c ->
                        System.out.printf("Coluna [%s] tipo: [%s] tem %s cards\n", c.nome(), c.tipo(), c.cardsAmount())
                );
            });
        }
    }

    private void showColumn() throws SQLException {
        var columnsIds = entity.getBoardColumns().stream().map(BoardColumnEntity::getId).toList();
        var selectedColumnId = -1L;
        while (!columnsIds.contains(selectedColumnId)){
            System.out.printf("Escolha uma coluna do board %s pelo id\n", entity.getNome());
            entity.getBoardColumns().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getNome(), c.getTipo()));
            selectedColumnId = sc.nextLong();
        }
        try(var connection = ConnectionConfig.getConnection()){
            var column = new BoardColumnQueryService(connection).findById(selectedColumnId);
            column.ifPresent(co -> {
                System.out.printf("Coluna %s tipo %s\n", co.getNome(), co.getTipo());
                co.getCards().forEach(ca -> System.out.printf("Card %s - %s\nDescrição: %s",
                        ca.getId(), ca.getTitulo(), ca.getDescricao()));
            });
        }
    }

    private void showCard()throws SQLException {
        System.out.println("Informe o id do card que deseja visualizar");
        var selectedCardId = sc.nextLong();
        try(var connection  = ConnectionConfig.getConnection()){
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                            c -> {
                                System.out.printf("Card %s - %s.\n", c.id(), c.titulo());
                                System.out.printf("Descrição: %s\n", c.descricao());
                                System.out.println(c.bloqueio() ?
                                        "Está bloqueado. Motivo: " + c.descricao_bloqueio() :
                                        "Não está bloqueado");
                                System.out.printf("Já foi bloqueado %s vezes\n", c.valor_bloqueio());
                                System.out.printf("Está no momento na coluna %s - %s\n", c.columnId(), c.columnNome());
                            },
                            () -> System.out.printf("Não existe um card com o id %s\n", selectedCardId));
        }
    }


}
