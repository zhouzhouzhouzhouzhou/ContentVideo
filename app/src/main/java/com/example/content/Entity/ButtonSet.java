package com.example.content.Entity;

/**
 * Created by 佳南 on 2017/9/12.
 */

public class ButtonSet {

    public String buttonId;
    public boolean isChecked;

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    public ButtonSet(String buttonId) {
        this.buttonId = buttonId;
    }
}
