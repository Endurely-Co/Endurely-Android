package dev.gbenga.endurely.dashboard

data class SettingsItem(val title:String, val subTitle: String, val buttonType: ButtonType = ButtonType.NONE)

enum class ButtonType{
    SWITCH, ACTION, NONE
}