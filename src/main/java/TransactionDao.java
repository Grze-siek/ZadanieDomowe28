import java.sql.*;
import java.util.Optional;

public class TransactionDao {
    private static final String URL = "jdbc:mysql://localhost:3306/javastart?characterEncoding=utf8&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public TransactionDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException exception) {
            System.err.println("Nie znaleziono sterownika");
        } catch (SQLException exception) {
            System.err.println("Nie można nawiązać połączenia");
        }
    }

    public void save(Transaction transaction) {
        String insertTransactionSql = "INSERT INTO transaction(type, description, amount, date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(insertTransactionSql);
            statement.setString(1, String.valueOf(transaction.getTransactionType()));
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getDate());
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Nie udało się dodać transakcji");
            exception.printStackTrace();
        }
    }

    //R
    public Optional<Transaction> read(TransactionType transactionType ) {
        String selectTransactionSql = "SELECT * FROM transaction WHERE type = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(selectTransactionSql);
            statement.setString(1, String.valueOf(transactionType));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getLong("id"));
                transaction.setTransactionType((TransactionType) resultSet.getObject("type"));
                transaction.setDescription(resultSet.getString("description"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setDate(resultSet.getString("date"));
                return Optional.of(transaction);
            }
        } catch (SQLException exception) {
            System.err.println("Nie udało się wyświetlić transakci");
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    //U
    public void update(Transaction transaction) {
        String updateTransactionSql = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateTransactionSql);
            statement.setObject(1, transaction.getTransactionType());
            statement.setString(2, transaction.getDescription());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getDate());
            statement.setLong(5, transaction.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Nie udało się zmodyfikować transakcji");
            exception.printStackTrace();
        }
    }

    //D
    public void delete(long id) {
        String deleteTransactionSql = "DELETE FROM transaction WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(deleteTransactionSql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Nie udało się usunąć transakcji");
            exception.printStackTrace();
        }

    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.err.println("Nie udało się zamknąć połączenia");
        }
    }
}

