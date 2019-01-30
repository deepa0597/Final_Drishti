package com.example.aakash.drishti;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import java.util.Locale;


public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    KeyboardView keyboardView;
    TextToSpeech tts;
    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        Keyboard keyboard = new Keyboard(this, R.xml.alphabet_pad);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });


        return keyboardView;
        }


    @Override
    public void onPress(int i) {


        char code1 = (char) i;
        tts.speak(String.valueOf(code1), TextToSpeech.QUEUE_ADD, null);
        Toast.makeText(MyInputMethodService.this, String.valueOf(code1), Toast.LENGTH_SHORT).show();
        onExtractedTextClicked();
        }

    @Override
    public void onRelease(int i) {
        char code1 = (char) i;
        InputConnection inputConnection = getCurrentInputConnection();
        tts.speak(String.valueOf(code1), TextToSpeech.QUEUE_ADD, null);
        Toast.makeText(MyInputMethodService.this, String.valueOf(code1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onKey(int primatyCode, int[] keyCodes) {

        InputConnection inputConnection = getCurrentInputConnection();

        if (inputConnection != null) {
            switch(primatyCode) {
                case Keyboard.KEYCODE_DELETE :
                    CharSequence selectedText = inputConnection.getSelectedText(0);
                    char code = (char) primatyCode;

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                        Toast.makeText(MyInputMethodService.this, String.valueOf(code), Toast.LENGTH_SHORT).show();
                        tts.speak(String.valueOf(code), TextToSpeech.QUEUE_FLUSH, null);
                    } else {
                        inputConnection.commitText("", 1);
                        Toast.makeText(MyInputMethodService.this, String.valueOf(code), Toast.LENGTH_SHORT).show();
                        tts.speak(String.valueOf(code), TextToSpeech.QUEUE_FLUSH, null);
                    }

                    break;
                default :
                    char code1 = (char) primatyCode;
                    inputConnection.commitText(String.valueOf(code1), 1);
                    tts.speak(String.valueOf(code1), TextToSpeech.QUEUE_ADD, null);
                    Toast.makeText(MyInputMethodService.this, String.valueOf(code1), Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {
//
//        tts.speak(String.valueOf(code1), TextToSpeech.QUEUE_ADD, null);
//        Toast.makeText(MyInputMethodService.this, "cc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeRight() {
//        Toast.makeText(MyInputMethodService.this, "cc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeDown() {
//        Toast.makeText(MyInputMethodService.this, "cc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeUp() {
//        Toast.makeText(MyInputMethodService.this, "cc", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onEvaluateInputViewShown() {
        Toast.makeText(MyInputMethodService.this, "ccc", Toast.LENGTH_SHORT).show();
        return super.onEvaluateInputViewShown();
    }
}
