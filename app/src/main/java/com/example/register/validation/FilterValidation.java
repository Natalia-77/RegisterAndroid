package com.example.register.validation;

import android.text.InputFilter;
import android.text.Spanned;

import com.example.register.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterValidation implements InputFilter {
    private static final String PASSWORD_PATTERN =
           "[_a-zA-Z1-9]+(\\\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\\\.[A-Za-z0-9]+(\\\\.[A-Za-z0-9]*)*";
    private TextInputEditText inputEmail,inputPassword;
    @Override

    public CharSequence filter (CharSequence source , int start , int end , Spanned dest ,
                                int dstart , int dend) {
        for ( int i = start ; i < end ; i++) {

            String checkMe = String. valueOf (source.charAt(i)) ;
            Pattern pattern =
                    Pattern. compile (PASSWORD_PATTERN) ;
            Matcher matcher = pattern.matcher(checkMe) ;
            boolean valid = matcher.matches() ;
            if (!valid) {
                //Log. d ( "" , "invalid" ) ;
                //inputEmail.setError("q");
                return checkMe ;
            }
        }
        return source ;
    }
}
