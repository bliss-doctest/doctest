package com.devbliss.doctest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unit tests for the {@link DocTestMachineImpl}
 * 
 * @author bmary
 * 
 */
public class DocTestMachineImplTest {

    @Mock
    private Class<?> mockedClass;

    private DocTestMachineImpl machine;

    @Before
    public void setUp() {
        machine = new DocTestMachineImpl();
    }

    @Test
    public void test() throws Exception {
        machine.sayResponse(500, "{}");
    }
}
