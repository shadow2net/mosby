/*
 * Copyright 2016 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hannesdorfmann.mosby3.mvi.integrationtest.lifecycle;

import android.content.pm.ActivityInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

// TODO find a better name
@RunWith(AndroidJUnit4.class) public class LifecycleTestActivityTest {

  @Rule public ActivityTestRule<LifecycleTestActivity> rule =
      new ActivityTestRule<>(LifecycleTestActivity.class);

  @Test public void testConfigChange() throws Exception {
    // Context of the app under test.
    LifecycleTestActivity portraitActivity = rule.getActivity();

    LifecycleTestPresenter portraitPresenter = portraitActivity.presenter;
    Assert.assertNotNull(portraitPresenter);
    Assert.assertEquals(1, portraitActivity.createPresenterInvokations);
    Assert.assertEquals(1, portraitPresenter.attachViewInvokations);
    Assert.assertTrue(portraitPresenter.attachedView == portraitActivity);

    Thread.sleep(1000);

    //
    // Screen orientation change
    //
    portraitActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    Thread.sleep(1000);

    Assert.assertEquals(1, portraitPresenter.detachViewInvokations);
    Assert.assertTrue(portraitPresenter.onDettachViewRetainInstance);

    LifecycleTestActivity landscapeActivity = rule.getActivity(); // TODO this one returnes always the same activity instance. No difference between landscape / portrait instance
    LifecycleTestPresenter landscapePresenter = landscapeActivity.presenter;
    Assert.assertNotNull(landscapePresenter);
    Assert.assertTrue(portraitPresenter == landscapePresenter);
//    Assert.assertTrue(portraitActivity != landscapeActivity);
    Assert.assertEquals(1, landscapeActivity.createPresenterInvokations);
    Assert.assertEquals(2, landscapePresenter.attachViewInvokations);
    Assert.assertTrue(landscapePresenter.attachedView != portraitActivity);

    /* // TODO figure out how to run this assertins after Activity.onStop() / onDestroy()
    Assert.assertEquals(2, landscapePresenter.detachViewInvokations);
    Assert.assertFalse(landscapePresenter.onDettachViewRetainInstance);
    */
  }
}
