public class Main {
    public static void main(String[] args) {
        TransactionDao transactionDao = new TransactionDao();
        Transaction transactionToSave = new Transaction(TransactionType.PRZYCHOD, "wypłata z pracy", 1254.6, "04-05-2020" );
        Transaction transactionToSaveTwo = new Transaction(TransactionType.PRZYCHOD, "wypłata z pracy", 1254.6, "04-05-2020" );
        Transaction transactionToSaveThree = new Transaction(TransactionType.WYDATEK, "zakupy", 254, "06-05-2020" );
        transactionDao.save(transactionToSave);
        transactionDao.save(transactionToSaveTwo);
        transactionDao.save(transactionToSaveThree);

        transactionDao.read(TransactionType.WYDATEK);

        transactionDao.close();
    }
}