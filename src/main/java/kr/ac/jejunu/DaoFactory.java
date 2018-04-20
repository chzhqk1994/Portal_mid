package kr.ac.jejunu;

public class DaoFactory {
    private ProductDao productDao;

    public ProductDao getProductDao() {
        return new ProductDao(connectionMaker());
    }

    public ConnectionMaker connectionMaker(){
        return new JejuConnectionMaker();
    }
}
