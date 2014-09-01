package com.wangjie.androidbucket.present;

import android.app.Activity;
import android.os.Bundle;
import com.wangjie.androidbucket.manager.BaseActivityManager;

/**
 * Created by wangjie on 6/15/14.
 */
public class ABActivity extends Activity{

    private BaseActivityManager baseActivityManager;
    private boolean isActivityLifeCycleAutoCallBack;
    public boolean isActivityLifeCycleAutoCallBack() {
        return isActivityLifeCycleAutoCallBack;
    }
    public void setActivityLifeCycleAutoCallBack(boolean isActivityLifeCycleAutoCallBack) {
        this.isActivityLifeCycleAutoCallBack = isActivityLifeCycleAutoCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(shouldCallBack()){
            baseActivityManager.dispatchActivityCreate(savedInstanceState);
        }

    }


    private boolean isFirstFocused = true;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        synchronized (this.getClass()){
            if(isFirstFocused && hasFocus){
                isFirstFocused = false;
                onWindowInitialized();
            }
        }

    }
    /**
     * 界面渲染完毕，可在这里进行初始化工作，建议在这里启动线程进行初始化工作
     */
    public void onWindowInitialized(){}



    @Override
    protected void onResume() {
        super.onResume();
        if(shouldCallBack()){
            baseActivityManager.dispatchActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(shouldCallBack()){
            baseActivityManager.dispatchActivityPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(shouldCallBack()){
            baseActivityManager.dispatchActivityDestory();
        }

    }

    public BaseActivityManager getBaseActivityManager() {
        return baseActivityManager;
    }
    public void setBaseActivityManager(BaseActivityManager baseActivityManager) {
        this.baseActivityManager = baseActivityManager;
    }
    public void setBaseActivityMananger(){
        if(null == baseActivityManager){
            this.baseActivityManager = new BaseActivityManager(this);
        }
    }

    private boolean shouldCallBack(){
        return null != baseActivityManager && isActivityLifeCycleAutoCallBack;
    }


}
