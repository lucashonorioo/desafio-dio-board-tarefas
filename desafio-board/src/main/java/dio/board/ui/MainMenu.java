package dio.board.ui;

import dio.board.persistence.config.ConnectionConfig;
import dio.board.persistence.entity.BoardColumnEntity;
import dio.board.persistence.entity.BoardEntity;
import dio.board.persistence.entity.TipoColumnEnum;
import dio.board.service.BoardQueryService;
import dio.board.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private final Scanner sc = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de boards, escolha uma opção");
        var opcao = -1;
        while (true){
            System.out.println("1 - Criar um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - Excluir um board");
            System.out.println("4 - Sair");
            opcao = sc.nextInt();
            switch (opcao){
                case 1 -> criarBoard();
                case 2 -> selecionarBoard();
                case 3 -> deletarBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void criarBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.print("Informe o nome do seu board: ");
        entity.setNome(sc.next());

        System.out.print("Seu board terá colunas além das 3 padrões? Se sim informe quantas, senão digite '0': ");
        var additionalColumns = sc.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.print("Informe o nome da coluna inicial do board: ");
        var nomeColunaInicial = sc.next();
        var colunaInicial = createColumn(nomeColunaInicial, TipoColumnEnum.INICIAL, 0);
        columns.add(colunaInicial);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.print("Informe o nome da coluna de tarefa pendente do board: ");
            var nomeColunaPendente = sc.next();
            var colunaPendente = createColumn(nomeColunaPendente, TipoColumnEnum.PENDENTE, i + 1);
            columns.add(colunaPendente);
        }

        System.out.print("Informe o nome da coluna final: ");
        var nomeColunaFinal = sc.next();
        var colunaFinal = createColumn(nomeColunaFinal, TipoColumnEnum.FINAL, additionalColumns + 1);
        columns.add(colunaFinal);

        System.out.print("Informe o nome da coluna de cancelamento do baord: ");
        var nomeColunaCancelada = sc.next();
        var colunaCancelada = createColumn(nomeColunaCancelada, TipoColumnEnum.CANCELADO, additionalColumns + 2);
        columns.add(colunaCancelada);

        entity.setBoardColumns(columns);
        try(var connection = ConnectionConfig.getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }

    }

    private void selecionarBoard() throws SQLException {
        System.out.print("Informe o id do board que deseja selecionar: ");
        var id = sc.nextLong();
        try(var connection = ConnectionConfig.getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            optional.ifPresentOrElse(
                    b -> new BoardMenu(b).execute(),
                    () -> System.out.printf("Não foi encontrado um board com id %s\n", id)
            );
        }
    }

    private void deletarBoard() throws SQLException {
        System.out.print("Informe o id do board que será excluido: ");
        var id = sc.nextLong();
        try(var connection = ConnectionConfig.getConnection()){
            var service = new BoardService(connection);
            if (service.delete(id)){
                System.out.printf("O board %s foi excluido\n", id);
            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }
        }
    }

    private BoardColumnEntity createColumn(final String nome, final TipoColumnEnum tipo, final int ordem){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setNome(nome);
        boardColumn.setTipo(tipo);
        boardColumn.setOrdem(ordem);
        return boardColumn;
    }


}
