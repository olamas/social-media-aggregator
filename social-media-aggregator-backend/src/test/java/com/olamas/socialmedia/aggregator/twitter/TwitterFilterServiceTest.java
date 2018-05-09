package com.olamas.socialmedia.aggregator.twitter;


import com.olamas.socialmedia.aggregator.exception.ConfigServerException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    public static final String VALID_USER_NAME = "bruce";
    public static final String VALID_FILTER_TEXT = "batman";
    @Mock
    private TwitterConfigRepository twitterConfigRepository;

    @InjectMocks
    private TwitterFilterService twitterFilterService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize() {
        twitterFilterService = new TwitterFilterService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void verifyFilterParametersFilterIsNotNull() throws ConfigServerException{

        // Arrange
        TwitterFilter twitterFilter = new TwitterFilter();
        twitterFilter.setUserName(VALID_USER_NAME);
        twitterFilter.setFilterText(VALID_FILTER_TEXT);
        twitterFilter.setValidFilter(true);
        when(twitterConfigRepository.addNewFilter(twitterFilter)).thenReturn(twitterFilter);

        // Act
        TwitterFilter twitterFilterResult = twitterFilterService.setNewFilter(twitterFilter);

        // Assert
        assertTrue(twitterFilterResult.isValidFilter());
    }

    @Test
    public void verifyFilterParametersFiltersAreNull() throws ConfigServerException{

        // Arrange
        TwitterFilter twitterFilter = new TwitterFilter();
        when(twitterConfigRepository.addNewFilter(twitterFilter)).thenReturn(twitterFilter);

        // Act
        TwitterFilter twitterFilterResult = twitterFilterService.setNewFilter(twitterFilter);

        // Assert
        assertTrue(twitterFilterResult == null);
    }


    @Test
    public void verifyFilterParametersWhenConfigServerThrowsException() throws ConfigServerException{

        // Assert
        thrown.expect(ConfigServerException.class);

        // Arrange
        TwitterFilter twitterFilter = new TwitterFilter();
        twitterFilter.setUserName(VALID_USER_NAME);
        twitterFilter.setFilterText(VALID_FILTER_TEXT);
        when(twitterConfigRepository.addNewFilter(twitterFilter)).thenThrow(new ConfigServerException());

        // Act
        twitterFilterService.setNewFilter(twitterFilter);
    }

}
