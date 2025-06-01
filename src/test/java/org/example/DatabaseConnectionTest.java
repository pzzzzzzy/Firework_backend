package org.example;

import org.example.entity.TestEntity;
import org.example.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private TestRepository testRepository;

    @Test
    public void testDatabaseConnection() {
        // 創建測試實體
        TestEntity testEntity = new TestEntity("Test Name");
        
        // 保存到數據庫
        TestEntity savedEntity = testRepository.save(testEntity);
        
        // 驗證保存是否成功
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
        
        // 從數據庫讀取
        TestEntity retrievedEntity = testRepository.findById(savedEntity.getId()).orElse(null);
        
        // 驗證讀取是否成功
        assertNotNull(retrievedEntity);
        assertEquals("Test Name", retrievedEntity.getName());
        
        // 清理測試數據
        testRepository.delete(savedEntity);
    }
} 