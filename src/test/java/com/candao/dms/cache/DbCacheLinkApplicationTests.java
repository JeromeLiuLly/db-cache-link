package com.candao.dms.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.candao.dms.cache.bean.JeromeLiu;
import com.candao.dms.cache.service.JeromeLiuTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DbCacheLinkApplication.class)
public class DbCacheLinkApplicationTests {

	@Autowired
	private JeromeLiuTest jeromeLiuTest;

	@Test
	public void test2() {
		JeromeLiu jeromeLiu = new JeromeLiu();
		jeromeLiu.setId("1140120103");
		jeromeLiu.setName("刘练源");
		jeromeLiu.setAge(25);
		System.out.println(jeromeLiuTest.testAnnotation2(jeromeLiu));
	}

	@Test
	public void test3() {
		JeromeLiu jeromeLiu = new JeromeLiu();
		jeromeLiu.setId("1140120103");
		jeromeLiu.setName("刘练源");
		jeromeLiu.setAge(26);
		System.out.println(jeromeLiuTest.testAnnotation3(jeromeLiu));
	}

	@Test
	public void test4() {
		JeromeLiu jeromeLiu = new JeromeLiu();
		jeromeLiu.setId("1140120103");
		jeromeLiu.setName("jeromeLiu");
		jeromeLiu.setAge(26);
		System.out.println(jeromeLiuTest.testAnnotation4(jeromeLiu));
	}

}
