package com.app.quench.log.setting;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.quench.log.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class HitAdcActy extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit_adc);
        try {
            // 使用自定义的 ClassLoader 加载插件中的 Test 类
            if (Audnf.INSTANCE.getDexLoad() != null) {
                Class<?> outa = Audnf.INSTANCE.getDexLoad().loadClass("com.example.outdex.Standup");
                Field field = outa.getDeclaredField("fewian");
                field.setAccessible(true);
                DexClassLoader classLoader = (DexClassLoader) field.get(null);
                Class<?> inta = classLoader.loadClass("com.example.innerdex.AdJDWFS");
                // 调用 greet 方法
                Method greetMethod = inta.getMethod("hitshow", AppCompatActivity.class);
                greetMethod.invoke(null, this);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
    }

}
