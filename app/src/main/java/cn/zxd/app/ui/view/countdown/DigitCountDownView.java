package cn.zxd.app.ui.view.countdown;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class DigitCountDownView extends androidx.appcompat.widget.AppCompatTextView {

    private CountDownTimerListener listener = null;
    private CountDownTimer countDownTimer;

    public interface CountDownTimerListener {
        void onFinishCount();
    }

    public void setCountDownTimerListener(CountDownTimerListener listener) {
        this.listener = listener;
    }

    public DigitCountDownView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public DigitCountDownView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DigitCountDownView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setText("60");
        setTextColor(Color.rgb(0x00, 0x99, 0xcc));
        setTextSize(36);
        invalidate();
    }

    public void start() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(String.format("%02d", millisUntilFinished / 1000));
                invalidate();
            }

            @Override
            public void onFinish() {
                setText("00");
                invalidate();
                if (listener != null) {
                    listener.onFinishCount();
                }
            }
        }.start();
    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
