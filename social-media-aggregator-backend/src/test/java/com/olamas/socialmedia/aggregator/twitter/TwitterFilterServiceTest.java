package com.olamas.socialmedia.aggregator.twitter;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterFilterServiceTest {

    @Mock
    private TwitterConfigRepository twitterConfigRepository;

    @InjectMocks
    private TwitterFilterService twitterFilterService;


    @Before
    public void initialize() {
        twitterFilterService = new TwitterFilterService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void verifyFilterParametersNotNull(){

        // Arrange
        TwitterFilter twitterFilter = new TwitterFilter();
        twitterFilter.setResult("OK");
        when(twitterConfigRepository.addNewFilter(twitterFilter)).thenReturn(twitterFilter);

        // Act
        twitterFilterService.setNewFilter(twitterFilter);

        // Assert
        assertTrue(twitterFilter.getResult()!=null);
    }



}
