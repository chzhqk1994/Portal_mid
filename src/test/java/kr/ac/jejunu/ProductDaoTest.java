package kr.ac.jejunu;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        productDao = applicationContext.getBean("getProductDao", ProductDao.class);
    }

    @Test
    public void get() throws SQLException {
        Long id = 1L;
        String title = "제주감귤";
        Integer price = 15000;

        Product product = productDao.get(id);
        assertEquals(id, product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(price, product.getPrice());
    }

    @Test
    public void add() throws SQLException {
        Product product = new Product();
        Long id = addProduct(product);

        Product insertedProduct = productDao.get(id);
        assertEquals(insertedProduct.getId(), id);
        assertEquals(insertedProduct.getTitle(), product.getTitle());
        assertEquals(insertedProduct.getPrice(), product.getPrice());
    }

    @Test
    public void update() throws SQLException {
        Product product = new Product();
        Long id = addProduct(product);

        product.setId(id);
        product.setTitle("수정된 내용");
        product.setPrice(99999);
        productDao.update(product);

        Product updatedProduct = productDao.get(id);
        assertEquals(updatedProduct.getId(), id);
        assertEquals(updatedProduct.getTitle(), product.getTitle());
        assertEquals(updatedProduct.getPrice(), product.getPrice());
    }

    @Test
    public void delete() throws SQLException {
        Product product = new Product();
        Long id = addProduct(product);

        productDao.delete(id);
        Product deletedProduct = productDao.get(id);
        assertEquals(deletedProduct, null);
    }

    private Long addProduct(Product product) throws SQLException {
        product.setTitle("시험");
        product.setPrice(100);
        return productDao.insert(product);
    }
}
