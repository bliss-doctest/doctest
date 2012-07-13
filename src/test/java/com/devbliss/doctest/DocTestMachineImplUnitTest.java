package com.devbliss.doctest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.templates.Templates;

/**
 * Unit test for {@link DocTestMachineImpl}
 * 
 * @author bmary
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class DocTestMachineImplUnitTest {

    @Mock
    private Templates templates;
    @Mock
    private ReportRenderer renderer;
    private DocTestMachineImpl machine;

    @Before
    public void setUp() {
        machine = new DocTestMachineImpl(templates, renderer);
    }

    @Test
    public void test() {
        machine.say("say");
    }
}
