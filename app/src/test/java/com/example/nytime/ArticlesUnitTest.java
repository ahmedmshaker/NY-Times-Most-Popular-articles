package com.example.nytime;



import com.example.nytime.data.entities.ArticlesResponse;
import com.example.nytime.data.services.ArticlesService;

import org.junit.Before;
import org.junit.Test;


import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import retrofit2.Retrofit;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArticlesUnitTest {

    public ArticlesService articlesService;
    public TestObserver testObserver = new TestObserver<ArticlesResponse>();
    public Retrofit retrofit;

    public TestUtils testUtils;

    @Before
   public void before() {
        testUtils = new TestUtils();
        retrofit = testUtils.buildRetrofit();
        articlesService = retrofit.create(ArticlesService.class);
    }
    @Test
    public void testResponse() {
        articlesService.getArticles(TestConstants.API_KEY).subscribe(testObserver);
        testObserver.assertValue(new Predicate() {
            @Override
            public boolean test(Object t) throws Exception {
                ArticlesResponse response = testUtils.getArticles();
                ArticlesResponse testedResponse = (ArticlesResponse) t;
                return testedResponse.getCopyright().equals(response.getCopyright());
            }
        });
    }



}