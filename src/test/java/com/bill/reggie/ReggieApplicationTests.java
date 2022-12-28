package com.bill.reggie;

import com.bill.reggie.service.EmployeeService;
import com.bill.reggie.transaction.TransactionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReggieApplicationTests {


	@Autowired
	private TransactionTest transactionTest;

	@Test
	void TestTransaction() {
		transactionTest.save();
//		transactionTest.service(1);
		System.out.println("end");
	}

}
