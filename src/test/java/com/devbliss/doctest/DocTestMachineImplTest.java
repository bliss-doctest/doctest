package com.devbliss.doctest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.devbliss.doctest.templates.Templates;

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

    private Templates templates;

    @Before
    public void setUp() {
        machine = new DocTestMachineImpl(templates);
    }

    @Test
    public void test() throws Exception {
        machine.sayResponse(500, "{}");
    }
}
