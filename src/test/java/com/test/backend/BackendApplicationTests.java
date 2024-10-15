package com.test.backend;

import com.test.backend.Model.Products;
import com.test.backend.Repository.ProductR;
import com.test.backend.Service.ProducS;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    //Testing
    @Mock
    private ProductR productr;

    @InjectMocks
    private ProducS products;

    private Products product;

    @Test
    void contextLoads() {
    }

}
