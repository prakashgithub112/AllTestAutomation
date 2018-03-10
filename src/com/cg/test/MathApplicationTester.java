package com.cg.test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cg.pojo.MathApplication;
import com.cg.services.CalculatorService;

import static org.mockito.Mockito.inOrder;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {
	
   //@InjectMocks annotation is used to create and inject the mock object
   @InjectMocks 
   MathApplication mathApplication = new MathApplication();

   //@Mock annotation is used to create the mock object to be injected
   @Mock
   CalculatorService calcService;

   @Test
   public void testAdd(){
      //add the behavior of calc service to add two numbers
      when(calcService.add(10.0,20.0)).thenReturn(30.00);
		
      //test the add functionality
      Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);
   }
   @Test(expected = RuntimeException.class)
   public void testAddWithException(){
      //add the behavior to throw exception
      doThrow(new RuntimeException("Add operation not implemented"))
         .when(calcService).add(10.0,20.0);

      
      //test the add functionality
      Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0); 
      
      //verify call to calcService is made or not
      verify(calcService).add(20.0,10.0);
      verify(calcService).subtract(20.0,10.0);
      
    //create an inOrder verifier for a single mock
      InOrder inOrder = inOrder(calcService);

      //following will make sure that add is first called then subtract is called.
      inOrder.verify(calcService).add(20.0,10.0);
      inOrder.verify(calcService).subtract(20.0,10.0);
   }
}