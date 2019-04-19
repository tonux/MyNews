package com.darcos.julie.mynews;

import android.support.test.runner.AndroidJUnit4;


import com.darcos.julie.mynews.Models.MostPopular.MostPopular;
import com.darcos.julie.mynews.Models.Search.Search;
import com.darcos.julie.mynews.Models.TopStories.TopStories;
import com.darcos.julie.mynews.Utils.TimesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;


import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class FragmentTest {

    //test api TopStories
    @Test
    public void topStoriesTest() throws Exception {
        Observable<TopStories> obsTopStrories = TimesStreams.streamTopStories("food");

        TestObserver<TopStories> testObserver = new TestObserver<>();

        obsTopStrories.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStories test = testObserver.values().get(0);
        assertThat("status is ok",test.getStatus().equals("OK"));
    }

    //test MostPopular api
    public void MostPopularTest() throws Exception {
        Observable<MostPopular> obsMostPopular = TimesStreams.streamMostPopular();

        TestObserver<MostPopular> testObserver = new TestObserver<>();

        obsMostPopular.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        MostPopular test = testObserver.values().get(0);
        assertThat("status is ok",test.getStatus().equals("OK"));
    }

    //test api Search
    public void ArticleSearchTest() throws Exception {
        Observable<Search> obsSeartch = TimesStreams.streamSearch("sports","news_desk:(\"Sports\" )","20190101","20190303");

        TestObserver<Search> testObserver = new TestObserver<>();

        obsSeartch.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        Search test = testObserver.values().get(0);
        assertThat("status is ok",test.getStatus().equals("OK"));
    }

}
