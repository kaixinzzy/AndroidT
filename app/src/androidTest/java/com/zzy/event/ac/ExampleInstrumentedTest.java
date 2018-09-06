package com.zzy.event.ac;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.zzy.json.def.JsonT;
import com.zzy.json.gson.GsonT;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * 运行在Android设备上的单元测试（调用Android api）
 *
 * 参考：https://www.jianshu.com/p/de17655125f5
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zzy.event.ac", appContext.getPackageName());

    }

    @Test
    public void JsonT() {
        JsonT jsonT = new JsonT();
        jsonT.toJson();
        jsonT.toBean();
    }

    @Test
    public void GsonT() {
        GsonT gsonT = new GsonT();
        gsonT.toJson();
        gsonT.toBean();
    }

}
