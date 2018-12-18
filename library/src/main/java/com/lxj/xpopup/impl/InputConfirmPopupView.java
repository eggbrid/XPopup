package com.lxj.xpopup.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.XPopupConfig;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

/**
 * Description: 带输入框，确定和取消的对话框
 * Create by dance, at 2018/12/16
 */
public class InputConfirmPopupView extends ConfirmPopupView implements View.OnClickListener{

    public InputConfirmPopupView(@NonNull Context context) {
        super(context);
    }

    public InputConfirmPopupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InputConfirmPopupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_center_impl_confirm;
    }

    AppCompatEditText tv_input;
    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tv_input = findViewById(R.id.tv_input);
        tv_input.setVisibility(VISIBLE);

        tv_input.setOnClickListener(this);
        tv_input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tv_input==v && hasFocus){
                    getPopupContentView().animate().translationY(-getPopupContentView().getMeasuredHeight()/2)
                            .setDuration(300).start();

                    // 设置返回按下监听
                    tv_input.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dismiss();
                            }
                            return true;
                        }
                    });
                }
            }
        });
    }

    protected void applyPrimaryColor(){
        super.applyPrimaryColor();
    }

    OnCancelListener cancelListener;
    OnInputConfirmListener inputConfirmListener;
    public void setListener( OnInputConfirmListener inputConfirmListener,OnCancelListener cancelListener){
        this.cancelListener = cancelListener;
        this.inputConfirmListener = inputConfirmListener;
    }

    @Override
    public void onClick(View v) {
        if(v==tv_cancel){
            if(cancelListener!=null)cancelListener.onCancel();
            dismiss();
        }else if(v==tv_confirm){
            if(inputConfirmListener!=null)inputConfirmListener.onConfirm(tv_input.getText().toString().trim());
            dismiss();
        }else if(v==tv_input){
            if(getPopupContentView().getTranslationY()!=-getPopupContentView().getMeasuredHeight()/2){
                getPopupContentView().animate().translationY(-getPopupContentView().getMeasuredHeight()/2)
                        .setDuration(300).start();
            }
        }
    }
}
